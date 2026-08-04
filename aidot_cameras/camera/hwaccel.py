"""Pick the fastest video decoder this host can actually use.

Enumeration is not enough. ``ffmpeg -decoders`` lists what the binary was
COMPILED with, not what works on the machine: a Raspberry Pi 4 happily advertises
``h264_cuvid`` and ``hevc_cuvid`` with no Nvidia hardware anywhere, and the same
Pi advertises ``h264_v4l2m2m`` while failing to open it ("No such file or
directory") even with the kernel device present and the user in the ``video``
group. Selecting a decoder from that list would not accelerate decoding, it would
stop decoding working at all.

So every candidate is PROVEN before it is offered: a tiny clip is generated and
decoded with that decoder, and only a clean exit qualifies it. The winner is the
fastest qualifying candidate, which is a hardware decoder when one genuinely
works and the software decoder otherwise.

Probing costs a second or two per codec, so the verdict is cached on disk. The
cache key includes the ffmpeg build identity, so upgrading ffmpeg re-probes
rather than trusting a verdict made about a different binary.

Where this matters: the streaming paths use ``-c copy`` and never decode, so they
are unaffected by design. The decode cost is in the diagnostic/validation path
(``-f null``), which decodes deliberately - that is what proves a stream is not
merely arriving but actually decodable, and it is what catches a parameter-set
mismatch that would otherwise present as a permanently black picture.
"""

import hashlib
import json
import logging
import os
import subprocess
import threading
import time
from typing import List, Optional

from .protocol import _SPROP_DIR  # reuse the same per-user cache root

_LOGGER = logging.getLogger(__name__)

# Candidates per codec, best-effort first. Software last so it always qualifies.
_CANDIDATES = {
    "h264": [
        "h264_v4l2m2m",     # Raspberry Pi 4 / many ARM SoCs
        "h264_rkmpp",       # Rockchip
        "h264_qsv",         # Intel QuickSync
        "h264_cuvid",       # Nvidia
        "h264_vaapi",       # generic Linux VA-API
        "h264_videotoolbox",  # macOS
        "h264",             # software (always works)
    ],
    "hevc": [
        "hevc_v4l2m2m",
        "hevc_rkmpp",
        "hevc_qsv",
        "hevc_cuvid",
        "hevc_vaapi",
        "hevc_videotoolbox",
        "hevc",
    ],
}

_ENV_OVERRIDE = "AIDOT_VIDEO_DECODER"     # force one decoder, skip probing
_ENV_DISABLE = "AIDOT_DISABLE_HWACCEL"    # stay on software decoders

_PROBE_TIMEOUT_S = 15.0
_cache_mem: dict = {}
_warm_thread: Optional["threading.Thread"] = None
_warm_lock = threading.Lock()


def _ffmpeg() -> str:
    return os.environ.get("AIDOT_FFMPEG", "ffmpeg")


_identity_memo: Optional[str] = None


def _ffmpeg_identity() -> str:
    """Identify the ffmpeg build, so a verdict never outlives the binary.

    Derived by STATTING the binary (path, size, mtime) rather than running
    ``ffmpeg -version``. Reading a cached verdict has to be nearly free: this is
    consulted on a startup path, and shelling out cost ~3.4s per process on a
    loaded Pi - which is most of a cache hit's budget spent proving the cache is
    still valid. An upgrade changes size or mtime and re-probes, which is the
    property that actually matters.
    """
    global _identity_memo
    if _identity_memo is not None:
        return _identity_memo
    import shutil
    ident = "unknown"
    try:
        path = shutil.which(_ffmpeg()) or _ffmpeg()
        st = os.stat(path)
        ident = f"{path}:{st.st_size}:{int(st.st_mtime)}"
    except Exception:
        # No resolvable binary: fall back to the version string so a verdict is
        # still keyed to something, at the cost of one subprocess.
        try:
            ident = subprocess.run(
                [_ffmpeg(), "-hide_banner", "-version"],
                capture_output=True, text=True, timeout=10,
            ).stdout.splitlines()[0]
        except Exception:
            pass
    _identity_memo = hashlib.sha256(ident.encode("utf-8", "replace")).hexdigest()[:16]
    return _identity_memo


def _cache_path() -> str:
    return os.path.join(_SPROP_DIR, "decoders.json")


def _load_cache() -> dict:
    try:
        with open(_cache_path()) as fh:
            return json.load(fh)
    except (OSError, ValueError):
        return {}


def _save_cache(data: dict) -> None:
    try:
        os.makedirs(_SPROP_DIR, exist_ok=True)
        tmp = _cache_path() + ".tmp"
        with open(tmp, "w") as fh:
            json.dump(data, fh)
        os.replace(tmp, _cache_path())
    except OSError as exc:
        _LOGGER.debug("decoder cache write failed: %s", exc)


_decoders_memo: Optional[set] = None


def _compiled_in(name: str) -> bool:
    """Cheap pre-filter: is the decoder even in this build? Never sufficient -
    a Pi advertises h264_cuvid with no Nvidia hardware present."""
    global _decoders_memo
    if _decoders_memo is None:
        try:
            out = subprocess.run(
                [_ffmpeg(), "-hide_banner", "-decoders"],
                capture_output=True, text=True, timeout=10,
            ).stdout
        except Exception:
            out = ""
        _decoders_memo = {
            ln.split()[1] for ln in out.splitlines()
            if len(ln.split()) > 1 and not ln.startswith(" -")
        }
    return name in _decoders_memo


def _make_sample(codec: str, path: str) -> bool:
    """Generate a tiny clip to decode. Encoding is software and that is fine -
    it happens once per probe, not per stream."""
    enc = "libx264" if codec == "h264" else "libx265"
    try:
        r = subprocess.run(
            [_ffmpeg(), "-hide_banner", "-loglevel", "error",
             "-f", "lavfi", "-i", "testsrc=size=320x240:rate=10:duration=1",
             "-c:v", enc, "-preset", "ultrafast", "-g", "5",
             "-f", codec, "-y", path],
            capture_output=True, text=True, timeout=_PROBE_TIMEOUT_S,
        )
        return r.returncode == 0 and os.path.getsize(path) > 0
    except Exception:
        return False


def _try_decoder(name: str, sample: str) -> Optional[float]:
    """Decode the sample with ``name``; return seconds taken, or None if it
    cannot decode. A non-zero exit OR anything on stderr disqualifies it -
    a decoder that warns its way through is not one to rely on."""
    t0 = time.monotonic()
    try:
        r = subprocess.run(
            [_ffmpeg(), "-hide_banner", "-loglevel", "error",
             "-c:v", name, "-i", sample, "-f", "null", "-"],
            capture_output=True, text=True, timeout=_PROBE_TIMEOUT_S,
        )
    except Exception:
        return None
    if r.returncode != 0 or r.stderr.strip():
        return None
    return time.monotonic() - t0


def probe_decoder(codec: str, force: bool = False) -> str:
    """Fastest decoder for ``codec`` ("h264" or "hevc") proven to work here.

    Falls back to the software decoder, which is also the answer whenever
    hardware acceleration is disabled or nothing else qualifies.
    """
    codec = codec.lower()
    if codec not in _CANDIDATES:
        return codec

    forced = os.environ.get(_ENV_OVERRIDE, "").strip()
    if forced:
        return forced
    if os.environ.get(_ENV_DISABLE, "").strip().lower() in ("1", "true", "yes", "on"):
        return codec

    ident = _ffmpeg_identity()
    key = f"{codec}:{ident}"
    if not force and key in _cache_mem:
        return _cache_mem[key]

    cache = _load_cache()
    if not force and isinstance(cache.get(key), str):
        _cache_mem[key] = cache[key]
        return cache[key]

    import tempfile
    winner, best = codec, None
    with tempfile.TemporaryDirectory() as td:
        sample = os.path.join(td, f"probe.{codec}")
        if not _make_sample(codec, sample):
            _LOGGER.debug("decoder probe: could not build a %s sample", codec)
            return codec
        for name in _CANDIDATES[codec]:
            if name != codec and not _compiled_in(name):
                continue
            took = _try_decoder(name, sample)
            if took is None:
                _LOGGER.debug("decoder probe: %s cannot decode here", name)
                continue
            if best is None or took < best:
                winner, best = name, took

    if winner != codec:
        _LOGGER.info(
            "decoder probe: using %s for %s (%.2fs on the probe clip)",
            winner, codec, best or 0.0)
    cache[key] = winner
    _cache_mem[key] = winner
    _save_cache(cache)
    return winner


def warm_decoder_cache(codecs: tuple = ("h264", "hevc")) -> "threading.Thread":
    """Probe in the background so the first real use is already cached.

    Probing costs about ten seconds per codec the first time on a small ARM
    board, which is far too long to block a startup path - but it only ever
    happens once per host per ffmpeg build, and the answer is wanted before the
    first stream rather than during it. So run it on a daemon thread and let the
    caller carry on: anything asking for a decoder meanwhile simply computes it
    inline, and the result is shared through the same cache either way.

    Idempotent: a second call while the first is still running is a no-op, so
    every config entry can call it at setup without spawning a probe each.

    Returns the thread so a caller that wants to can join it; ignoring it is the
    expected use. Returns the in-flight thread if one is already running.
    """
    global _warm_thread
    with _warm_lock:
        if _warm_thread is not None and _warm_thread.is_alive():
            return _warm_thread

    def _run() -> None:
        for codec in codecs:
            try:
                probe_decoder(codec)
            except Exception:
                _LOGGER.debug("decoder probe failed for %s", codec, exc_info=True)

    with _warm_lock:
        if _warm_thread is not None and _warm_thread.is_alive():
            return _warm_thread
        _warm_thread = threading.Thread(
            target=_run, name="aidot-decoder-probe", daemon=True)
        _warm_thread.start()
        return _warm_thread


def cached_decoder(codec: str) -> Optional[str]:
    """The cached verdict for ``codec``, or None if it has not been probed yet.

    Never probes, so it is safe to call from an event loop - probing shells out
    for about ten seconds on a first run and would stall every other camera.
    A caller that gets None should simply let ffmpeg choose, which is the
    behaviour that existed before any of this; ``warm_decoder_cache()`` fills
    the cache in the background so the answer is normally already there.
    """
    codec = codec.lower()
    if codec not in _CANDIDATES:
        return None

    forced = os.environ.get(_ENV_OVERRIDE, "").strip()
    if forced:
        return forced
    if os.environ.get(_ENV_DISABLE, "").strip().lower() in ("1", "true", "yes", "on"):
        return codec

    key = f"{codec}:{_ffmpeg_identity()}"
    if key in _cache_mem:
        return _cache_mem[key]
    val = _load_cache().get(key)
    if isinstance(val, str):
        _cache_mem[key] = val
        return val
    return None


def decoder_args(codec: str) -> List[str]:
    """``-c:v <decoder>`` for ``codec``, or [] to let ffmpeg choose."""
    dec = probe_decoder(codec)
    return ["-c:v", dec] if dec else []
