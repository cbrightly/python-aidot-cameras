"""Pick the fastest video decoder this host can actually use.

Enumeration is not enough. ``ffmpeg -decoders`` lists what the binary was
COMPILED with, not what works on the machine: a Raspberry Pi 4 happily advertises
``h264_cuvid`` and ``hevc_cuvid`` with no Nvidia hardware anywhere, and the same
Pi advertises ``h264_v4l2m2m`` while failing to open it ("No such file or
directory") even with the kernel device present and the user in the ``video``
group. Selecting a decoder from that list would not accelerate decoding, it would
stop decoding working at all.

So every candidate is PROVEN before it is offered: a clip at the resolution the
cameras actually send is generated and decoded with it, and only a clean run
that produces frames qualifies. The winner is the fastest qualifying candidate,
which is a hardware path when one genuinely works and software otherwise.

Nor is hardware automatically faster. Measured on an Apple M1, VideoToolbox
decodes H.264 about three times SLOWER than software, in the same pipeline
production runs. Ranking by measured time rather than by preference is what
keeps that from becoming a regression sold as an optimisation.

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
import glob
import subprocess
import sys
import threading
import time
from typing import List, Optional

from .protocol import _SPROP_DIR  # reuse the same per-user cache root

_LOGGER = logging.getLogger(__name__)

# Ways to decode, best first; the empty list is plain software and always works.
#
# There are TWO forms and they are not interchangeable:
#
#   ["-c:v", "<name>"]      a named decoder, e.g. h264_v4l2m2m on a Pi
#   ["-hwaccel", "<method>"] an acceleration method, e.g. videotoolbox on macOS
#
# Getting this wrong silently costs the whole feature. VideoToolbox and VAAPI
# expose no decoder at all - ffmpeg lists h264_videotoolbox and h264_vaapi as
# ENCODERS - so naming them with -c:v can never work, and every macOS and VAAPI
# machine would fall back to software while genuinely having hardware decoding
# available through -hwaccel. Verified on an Apple M1 (videotoolbox appears in
# -hwaccels, nowhere in -decoders) and on a Pi 4 (same for vaapi).
#
# Both forms go before -i. Ordering is only a starting preference; whichever
# proves fastest wins, and anything that cannot decode is dropped.
_CANDIDATES = {
    "h264": [
        ["-hwaccel", "videotoolbox"],   # macOS / Apple silicon
        ["-hwaccel", "vaapi"],          # generic Linux (Intel/AMD)
        ["-c:v", "h264_v4l2m2m"],       # Raspberry Pi 4 / many ARM SoCs
        ["-c:v", "h264_rkmpp"],         # Rockchip
        ["-c:v", "h264_qsv"],           # Intel QuickSync
        ["-c:v", "h264_cuvid"],         # Nvidia
        [],                             # software
    ],
    "hevc": [
        ["-hwaccel", "videotoolbox"],
        ["-hwaccel", "vaapi"],
        ["-c:v", "hevc_v4l2m2m"],
        ["-c:v", "hevc_rkmpp"],
        ["-c:v", "hevc_qsv"],
        ["-c:v", "hevc_cuvid"],
        [],
    ],
}

# Bumped when the shape of a cached verdict changes, so an older entry is
# re-probed instead of being read as something it is not.
_CACHE_SCHEMA = 2

# Force a choice and skip probing. Takes either form: a bare name is a decoder
# ("h264_v4l2m2m"), a "hwaccel:" prefix an acceleration method
# ("hwaccel:videotoolbox"). The prefix is needed because the two are not
# interchangeable - VideoToolbox and VAAPI have no decoder to name.
_ENV_OVERRIDE = "AIDOT_VIDEO_DECODER"
_ENV_DISABLE = "AIDOT_DISABLE_HWACCEL"    # stay on software decoding

_PROBE_TIMEOUT_S = 40.0
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
_hwaccels_memo: Optional[set] = None


def _list_names(flag: str) -> set:
    try:
        out = subprocess.run(
            [_ffmpeg(), "-hide_banner", flag],
            capture_output=True, text=True, timeout=10,
        ).stdout
    except Exception:
        return set()
    names = set()
    for ln in out.splitlines():
        parts = ln.split()
        if not parts or ln.startswith(" -") or ln.rstrip().endswith(":"):
            continue
        # -decoders lines are " V....D name  description"; -hwaccels is one
        # bare name per line under a heading.
        names.add(parts[1] if flag == "-decoders" and len(parts) > 1 else parts[0])
    return names


def _plausible(cand: List[str]) -> bool:
    """Could this candidate possibly work, judged without spawning anything?

    A build advertises support compiled in, not hardware present. Trying a
    candidate costs an ffmpeg process and a couple of seconds on a small board,
    and on a machine with no video hardware at all EVERY trial is wasted - as is
    encoding the sample they would have decoded. Checking for the device first
    costs a stat.

    Deliberately permissive: it rules out only what is impossible, never what is
    merely unlikely. Anything that survives still has to prove itself.
    """
    if not cand:
        return True                                  # software
    name = cand[1]
    if name == "videotoolbox":
        return sys.platform == "darwin"
    if name == "vaapi" or name.endswith("_vaapi") or name.endswith("_qsv"):
        return bool(glob.glob("/dev/dri/render*"))
    if name.endswith("_cuvid") or name.endswith("_nvdec"):
        return bool(glob.glob("/dev/nvidia*"))
    if name.endswith("_v4l2m2m"):
        return bool(glob.glob("/dev/video*"))
    if name.endswith("_rkmpp"):
        return bool(glob.glob("/dev/mpp_service") or glob.glob("/dev/rga"))
    return True


def _available(cand: List[str]) -> bool:
    """Cheap pre-filter. Never sufficient on its own - a Pi advertises
    h264_cuvid with no Nvidia hardware present, which is why everything that
    passes here still has to prove it can decode."""
    global _decoders_memo, _hwaccels_memo
    if not cand:
        return True                      # software
    if cand[0] == "-c:v":
        if _decoders_memo is None:
            _decoders_memo = _list_names("-decoders")
        return cand[1] in _decoders_memo
    if _hwaccels_memo is None:
        _hwaccels_memo = _list_names("-hwaccels")
    return cand[1] in _hwaccels_memo


def _compiled_in(name: str) -> bool:
    """Back-compat shim: is this named decoder in the build?"""
    return _available(["-c:v", name])


# The cameras send 1280x720, and the probe clip matches that deliberately.
# A smaller clip is cheaper but ranks candidates badly: hardware decoding pays a
# fixed session-setup cost that dominates a tiny job, so a 320x240 clip can make
# a decoder that wins comfortably on real frames look like a loser. Measured on
# a Pi 4: 2.75s per candidate at 720p against 1.73s at 320x240 - a fair price
# for a measurement that reflects the actual workload, and it is paid once per
# host in the background.
_SAMPLE = "testsrc=size=1280x720:rate=15:duration=1"


def _make_sample(codec: str, path: str) -> bool:
    """Generate a clip to decode. Encoding is software and that is fine -
    it happens once per probe, not per stream."""
    enc = "libx264" if codec == "h264" else "libx265"
    try:
        r = subprocess.run(
            [_ffmpeg(), "-hide_banner", "-loglevel", "error",
             "-f", "lavfi", "-i", _SAMPLE,
             "-c:v", enc, "-preset", "ultrafast", "-g", "15",
             "-f", codec, "-y", path],
            capture_output=True, text=True, timeout=_PROBE_TIMEOUT_S,
        )
        return r.returncode == 0 and os.path.getsize(path) > 0
    except Exception:
        return False


def _try_decoder(cand: List[str], sample: str) -> Optional[float]:
    """Decode the sample with ``cand``; seconds taken, or None if it cannot.

    Decodes to rawvideo and requires bytes to come out, not merely a zero exit.
    A decoder that opens, consumes the input and emits nothing would otherwise
    look like a pass - and that is precisely the failure that matters here,
    since it presents downstream as a permanently black picture rather than as
    an error. A non-zero exit or anything on stderr also disqualifies it: a
    decoder that warns its way through is not one to rely on.
    """
    t0 = time.monotonic()
    try:
        r = subprocess.run(
            [_ffmpeg(), "-hide_banner", "-loglevel", "error",
             *cand, "-i", sample,
             "-f", "rawvideo", "-pix_fmt", "yuv420p", "-"],
            capture_output=True, timeout=_PROBE_TIMEOUT_S,
        )
    except Exception:
        return None
    if r.returncode != 0 or r.stderr.strip() or not r.stdout:
        return None
    return time.monotonic() - t0


def probe_decoder(codec: str, force: bool = False) -> List[str]:
    """ffmpeg input arguments for the fastest way to decode ``codec`` here.

    Returns ``["-hwaccel", m]`` or ``["-c:v", name]`` for a hardware path that
    was proven to work on this machine, or ``[]`` for plain software decoding -
    which is also the answer when acceleration is disabled or nothing else
    qualifies. The result always belongs BEFORE ``-i``.
    """
    codec = codec.lower()
    if codec not in _CANDIDATES:
        return []

    forced = os.environ.get(_ENV_OVERRIDE, "").strip()
    if forced:
        # Accept either form: a bare name is a decoder, "hwaccel:x" a method.
        if forced.startswith("hwaccel:"):
            return ["-hwaccel", forced.split(":", 1)[1]]
        return ["-c:v", forced]
    if os.environ.get(_ENV_DISABLE, "").strip().lower() in ("1", "true", "yes", "on"):
        return []

    key = f"v{_CACHE_SCHEMA}:{codec}:{_ffmpeg_identity()}"
    if not force and key in _cache_mem:
        return list(_cache_mem[key])

    cache = _load_cache()
    if not force and isinstance(cache.get(key), list):
        _cache_mem[key] = list(cache[key])
        return list(cache[key])

    # Everything below spawns processes, so decide first whether it is worth
    # spawning any. A machine with no video hardware - a VPS, a container, most
    # cloud installs - can be answered here for free, and encoding a sample it
    # would never decode is the single largest cost in this module.
    hw = [c for c in _CANDIDATES[codec] if c and _plausible(c) and _available(c)]
    if not hw:
        _LOGGER.debug("decoder probe: no hardware candidate is possible for %s "
                      "on this machine; software, nothing probed", codec)
        cache[key] = []
        _cache_mem[key] = []
        _save_cache(cache)
        return []

    import tempfile
    winner: List[str] = []
    best = None
    with tempfile.TemporaryDirectory() as td:
        sample = os.path.join(td, f"probe.{codec}")
        if not _make_sample(codec, sample):
            _LOGGER.debug("decoder probe: could not build a %s sample", codec)
            return []
        for cand in hw:
            took = _try_decoder(cand, sample)
            if took is None:
                _LOGGER.debug("decoder probe: %s cannot decode %s here",
                              " ".join(cand), codec)
                continue
            if best is None or took < best:
                winner, best = list(cand), took
        # Software is only worth timing if something beat it to the line. When
        # every hardware candidate failed - the common case on small boards -
        # software wins by default and the measurement would change nothing.
        if winner:
            sw = _try_decoder([], sample)
            if sw is not None and sw <= (best or 0.0):
                _LOGGER.debug("decoder probe: software (%.2fs) beats %s (%.2fs) "
                              "for %s", sw, " ".join(winner), best or 0.0, codec)
                winner, best = [], sw

    if winner:
        _LOGGER.info("decoder probe: using %s for %s (%.2fs on the probe clip)",
                     " ".join(winner), codec, best or 0.0)
    else:
        _LOGGER.debug("decoder probe: software decoding for %s", codec)
    cache[key] = winner
    _cache_mem[key] = list(winner)
    _save_cache(cache)
    return list(winner)


def warm_decoder_cache(codecs: tuple = ("h264",)) -> "threading.Thread":
    """Probe in the background so the first real use is already cached.

    Probing costs about ten seconds per codec the first time on a small ARM
    board, which is far too long to block a startup path - but it only ever
    happens once per host per ffmpeg build, and the answer is wanted before the
    first stream rather than during it. So run it on a daemon thread and let the
    caller carry on: anything asking for a decoder meanwhile simply computes it
    inline, and the result is shared through the same cache either way.

    Only H.264 is warmed by default, because only H.264 is ingested: probing
    H.265 as well doubled the cost of this module on a Pi 4 (22s of 47s wall,
    15s of 26s CPU) to answer a question nothing asks. If H.265 ingest lands,
    add it here - and until then a stray H.265 stream still works, it just lets
    ffmpeg choose on the first session while the answer is worked out.

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
        # Prime the in-memory cache from disk HERE, on this thread, so
        # cached_decoder() never has to read a file on the event loop.
        try:
            for k, v in _load_cache().items():
                if isinstance(v, list):
                    _cache_mem.setdefault(k, list(v))
        except Exception:
            _LOGGER.debug("decoder cache preload failed", exc_info=True)
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


def cached_decoder(codec: str) -> Optional[List[str]]:
    """The cached verdict for ``codec``, or None if it has not been probed yet.

    Never probes, so it is safe to call from an event loop - probing shells out
    for several seconds on a first run and would stall every other camera. A
    caller that gets None should simply let ffmpeg choose, which is the
    behaviour that existed before any of this; ``warm_decoder_cache()`` fills
    the cache in the background so the answer is normally already there.

    An empty list is a real answer meaning "software decoding", and is
    deliberately distinct from None.
    """
    codec = codec.lower()
    if codec not in _CANDIDATES:
        return None

    forced = os.environ.get(_ENV_OVERRIDE, "").strip()
    if forced:
        if forced.startswith("hwaccel:"):
            return ["-hwaccel", forced.split(":", 1)[1]]
        return ["-c:v", forced]
    if os.environ.get(_ENV_DISABLE, "").strip().lower() in ("1", "true", "yes", "on"):
        return []

    # MEMORY ONLY. This runs on the event loop, so it must not touch the disk:
    # Home Assistant detects a blocking open() here and reports it as a
    # stability problem, correctly. The disk cache is read by
    # warm_decoder_cache() on its own thread, which populates _cache_mem; a miss
    # here simply means "not known yet", and the caller lets ffmpeg choose,
    # which is the behaviour that existed before any of this.
    key = f"v{_CACHE_SCHEMA}:{codec}:{_ffmpeg_identity()}"
    if key in _cache_mem:
        return list(_cache_mem[key])
    return None


def decoder_args(codec: str) -> List[str]:
    """Input arguments for decoding ``codec``, probing if necessary."""
    return probe_decoder(codec)
