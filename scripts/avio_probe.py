#!/usr/bin/env python3
"""Live probe: send AVIO control commands and print what the camera says back.

Every AVIO control the library sends is fire-and-forget - the camera replies and
we discard it - so three outcomes are indistinguishable from outside: the camera
applied the command, accepted and ignored it, or refused it. The library can now
wait for a reply (``session.async_avio_request``); this harness is how we find
out what actually comes back, because none of it can be learned offline.

Two things are unknown and this answers both in one run:

*   **The inbound frame layout.** ``parse_avio_response`` decodes ``<IIqII4x``,
    which is the layout we *send*. The receive side has only ever been read for
    its command id (bytes 4:8). If the reply's length field does not sit at
    offset 16, every valid reply is rejected and the symptom is "the camera
    never answers" - the same thing a firmware with no response looks like.
    So this prints the raw header bytes of anything inbound.
*   **Which command ids the camera replies with.** The one list the library has
    (``{5376, 5377, 5156, 5157, 768, 769, 511, 804}``) was assembled from frames
    seen on the wire, not from a spec.

**Run it against a DTLS camera first (A000088).** That path logs every inbound
DataChannel frame with its hex, so a layout mismatch shows up as ``cmd=None``
next to the bytes that caused it. On the SDES path a frame that fails to parse
is forwarded to ffmpeg as audio and leaves no trace.

The default probe is SPEAKERSTART (848), which the library's own notes record
the camera acking with 851 - an observed pair, so it tests the plumbing itself
rather than a guess. If 851 comes back, dispatch and correlation are proven and
anything else that stays silent is a firmware answer, not a bug here.

Credentials come from ``aidot.credentials.load_credentials`` (env
``AIDOT_USERNAME`` / ``AIDOT_PASSWORD`` / ``AIDOT_COUNTRY``, or the encrypted
file pair under ``$XDG_CONFIG_HOME/aidot``). No secrets live in this file.

Examples::

    # list cameras
    python scripts/avio_probe.py --list

    # default probe (speaker start, expect the 851 ack) on one camera
    python scripts/avio_probe.py --name <substr>

    # ask about resolution too: SETSTREAMCTRL sd, expect 801
    python scripts/avio_probe.py --name <substr> --probe 800:801:sd

Requires the ``[webrtc]`` extra.
"""

import argparse
import asyncio
import inspect
import logging
import os
import struct
import sys
import time

import aiohttp

from aidot_cameras.client import AidotClient
from aidot_cameras.const import CONF_DEVICE_LIST, CONF_ID, CONF_NAME
from aidot_cameras.credentials import load_credentials

#: Payloads for the commands we know how to build, by command id.  Anything not
#: listed is sent with an empty payload unless one is given on the command line.
_PAYLOADS = {
    848: b"\x00" * 8,                       # SPEAKERSTART, channel 0
    849: b"\x00" * 8,                       # SPEAKERSTOP, channel 0
    800: struct.pack("<IB3x", 0, 5),        # SETSTREAMCTRL, channel 0, quality sd
    802: struct.pack("<I", 0),              # GETSTREAMCTRL, channel 0
}
_QUALITY = {"hd": 1, "sd": 5}

#: Default probe: the pair the library's own notes record as observed.
_DEFAULT_PROBES = [(848, 851, None)]


class _AvioOnly(logging.Filter):
    """Pass only the receive-path AVIO lines.

    The library at DEBUG is far too loud to read, and the one thing this harness
    needs from it is whether ANY inbound control frame reached the dispatch
    point. That distinction is the whole diagnosis: a camera that answers
    nothing looks identical to a receive path wired to the wrong place, and only
    the presence of unrelated inbound frames (the heartbeat ack, say) tells them
    apart.
    """

    def filter(self, record) -> bool:
        return "AVIO" in record.getMessage()


def _enable_avio_logging() -> None:
    handler = logging.StreamHandler()
    handler.setFormatter(logging.Formatter("    [log] %(message)s"))
    handler.addFilter(_AvioOnly())
    for name in ("aidot_cameras.camera.sdes_open", "aidot_cameras.camera.webrtc_open"):
        log = logging.getLogger(name)
        log.setLevel(logging.DEBUG)
        log.addHandler(handler)


def _is_camera(device_client) -> bool:
    model = getattr(getattr(device_client, "info", None), "model_id", "") or ""
    return "IPC" in model


def _parse_probe(spec: str):
    """``cmd:expect[:quality]`` -> (cmd, expect, payload)."""
    parts = spec.split(":")
    if len(parts) not in (2, 3):
        raise argparse.ArgumentTypeError(
            f"probe {spec!r} must be cmd:expect or cmd:expect:quality")
    cmd, expect = int(parts[0]), int(parts[1])
    payload = None
    if len(parts) == 3:
        quality = _QUALITY.get(parts[2].lower())
        if quality is None:
            raise argparse.ArgumentTypeError(
                f"quality {parts[2]!r} must be one of {sorted(_QUALITY)}")
        payload = struct.pack("<IB3x", 0, quality)
    return cmd, expect, payload


async def _stop(session) -> None:
    stop = getattr(session, "stop", None)
    if stop is None:
        return
    result = stop()
    if inspect.isawaitable(result):
        await result


async def _probe_one(client, device, probes, settle: float, timeout: float,
                     record: float = 0.0, out_path: str = "",
                     verbose: bool = False) -> bool:
    """Open a session, run each probe, report every reply.  True if any answered.

    With ``record``, the session is written to ``out_path`` and held that long
    after the probes. The recording necessarily starts before the probes do -
    the camera's stream setting does not survive a session, so every session
    begins at the camera's own default and there is no way to open one already
    switched. Read the result per frame (``ffprobe -show_entries frame=width,
    height``) rather than from the container header: what matters is whether the
    dimensions change partway through, after the command lands.
    """
    name = device.get(CONF_NAME)
    dc = client.get_device_client(device)
    model = getattr(getattr(dc, "info", None), "model_id", "") or ""

    # Echo the receive-path status lines - on DTLS these carry the inbound frame
    # hex, which is the whole point of running this against a DTLS camera.
    def _on_status(msg: str) -> None:
        # Under --debug print everything: on SDES the inbound control frames are
        # reported as "SDES DC: enc DATA ...", which no receive-shaped filter
        # would have matched. Guessing at which lines matter is how a wired
        # receive path can look like a silent camera.
        if verbose or " RX " in msg or "AVIO" in msg:
            print(f"    [rx] {msg}")

    print(f"\n>>> {name!r} ({model}) opening stream...")
    session = None
    answered = False
    try:
        t0 = time.time()
        open_kwargs = {"timeout": 45.0, "status_callback": _on_status}
        if record > 0.0 and out_path:
            if os.path.exists(out_path):
                os.remove(out_path)
            open_kwargs["output_path"] = out_path
            open_kwargs["max_seconds"] = int(settle + record + 5)
        session = await dc.async_open_webrtc_stream(**open_kwargs)
        transport = type(session).__name__
        print(f"    established in {time.time() - t0:.1f}s via {transport}")

        if not hasattr(session, "async_avio_request"):
            print("    this library build has no async_avio_request - nothing to probe")
            return False

        # Let the control channel come up.  On SDES the send function is only
        # installed once the bridge has the SCTP channel; asking before that
        # fails to send and reports "no channel" rather than "no answer".
        await asyncio.sleep(settle)

        for cmd, expect, payload in probes:
            body = payload if payload is not None else _PAYLOADS.get(cmd, b"")
            print(f"    -> cmd={cmd} payload={body.hex() or '<empty>'}"
                  f" (waiting {timeout:.1f}s for {expect})")
            t1 = time.time()
            reply = await session.async_avio_request(
                cmd, body, response_cmd=expect, timeout=timeout
            )
            waited = time.time() - t1
            if reply is None:
                print(f"    <- cmd={cmd}: NO REPLY for {expect} after {waited:.1f}s")
            else:
                answered = True
                print(f"    <- cmd={cmd}: REPLY {expect} in {waited:.2f}s"
                      f" seq={reply.seq} payload={reply.payload.hex() or '<empty>'}")

        if record > 0.0 and out_path:
            print(f"    recording {record:.0f}s to {out_path} ...")
            await asyncio.sleep(record)
            size = os.path.getsize(out_path) if os.path.exists(out_path) else 0
            print(f"    recorded {size}B"
                  f" (~{size * 8 / 1000.0 / max(record, 1):.0f} kbps average)")

        # Leave the camera as we found it: if we opened the speaker, close it.
        if any(cmd == 848 for cmd, _, _ in probes):
            session._avio_cmd(849, _PAYLOADS[849])
        return answered
    except Exception as exc:
        print(f"    {name!r}: ERROR {type(exc).__name__}: {exc}")
        return False
    finally:
        if session is not None:
            try:
                await _stop(session)
            except Exception:
                pass


async def _run(args) -> int:
    creds = load_credentials()
    async with aiohttp.ClientSession() as http:
        client = AidotClient(
            http,
            country_code=creds.get("country", "US"),
            username=creds["username"],
            password=creds["password"],
        )
        try:
            await client.async_post_login()
            devices = (await client.async_get_all_device())[CONF_DEVICE_LIST]
            cameras = [d for d in devices if _is_camera(client.get_device_client(d))]
            print(f"found {len(cameras)} camera(s) of {len(devices)} device(s)")
            for cam in cameras:
                cam_dc = client.get_device_client(cam)
                model = getattr(getattr(cam_dc, "info", None), "model_id", "") or ""
                print(f"  - {cam.get(CONF_NAME)!r:32} {model} ({cam.get(CONF_ID)[:8]})")
            if args.list:
                return 0

            selected = cameras
            if args.name:
                wanted = [n.lower() for n in args.name]
                selected = [c for c in cameras
                            if any(w in (c.get(CONF_NAME) or "").lower() for w in wanted)]
            if not selected:
                print("no camera matched", file=sys.stderr)
                return 2

            probes = args.probe or _DEFAULT_PROBES
            results = [
                (c.get(CONF_NAME),
                 await _probe_one(client, c, probes, args.settle, args.timeout,
                                  args.record, args.out, args.debug))
                for c in selected
            ]

            print("\n==== SUMMARY ====")
            for name, answered in results:
                print(f"  {'ANSWERED' if answered else 'silent  '}  {name}")
            print("\nA silent camera is a real result, not a failure: it means"
                  " either the firmware\nhas no response for that command, or the"
                  " reply does not parse. The [rx] lines\nabove tell them apart -"
                  " a frame logged with cmd=None arrived and was rejected.")
            return 0
        finally:
            await client.async_cleanup()


def main() -> int:
    p = argparse.ArgumentParser(
        description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    p.add_argument("--list", action="store_true", help="list cameras and exit")
    p.add_argument("--name", action="append",
                   help="camera name substring (repeatable); default is all")
    p.add_argument("--probe", action="append", type=_parse_probe,
                   help="cmd:expect[:quality] (repeatable); default 848:851")
    p.add_argument("--settle", type=float, default=5.0,
                   help="seconds to wait for the control channel (default 5)")
    p.add_argument("--timeout", type=float, default=5.0,
                   help="seconds to wait for each reply (default 5)")
    p.add_argument("--record", type=float, default=0.0,
                   help="hold and record the session this long after the probes")
    p.add_argument("--out", default="",
                   help="where to write the recording (with --record)")
    p.add_argument("--debug", action="store_true",
                   help="show every inbound AVIO frame the receive path sees")
    args = p.parse_args()
    if args.debug:
        _enable_avio_logging()
    return asyncio.run(_run(args))


if __name__ == "__main__":
    raise SystemExit(main())
