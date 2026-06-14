# Changelog

All notable changes to `python-aidot-cameras` are documented here. The format is
based on [Keep a Changelog](https://keepachangelog.com/), and this project uses
date-less, incrementing patch versions published to PyPI via GitHub Releases.

## [0.7.11]

### Changed
- Began splitting the monolithic `camera/client.py`: wire/protocol constants now
  live in `aidot/camera/constants.py` and are re-imported into `client.py`
  (behavior-preserving; all existing imports keep working).
- Renamed the worst cryptic private STUN/TURN helper closures for readability
  (`_mi_ta` → `_stun_message_integrity`, `_br_a`/`_si_a` → `_build_stun_attr`,
  `_rr_accept_cam_cert` → `_accept_camera_cert`). No behavior change.

### Fixed
- `GETSTREAMCTRL_CMD` is re-exported from `aidot.camera.client` again (it is the
  public pair of `SETSTREAMCTRL_CMD`); the constants extraction had dropped it.
- Corrected a debug-log function-name label that the rename pass had rewritten.

### Added
- `scripts/smoke_stream.py`: manual on-hardware smoke test that logs in,
  enumerates cameras, and briefly streams them (DTLS + SDES paths), reporting
  frames/recorded bytes per camera.

### Internal
- Moved `cleanup-branches.sh` into `scripts/`.

> Validated live against real cameras (DTLS A000088 and SDES A001064) in addition
> to the mocked unit suite.

## [0.7.10]

### Added
- PEP 561 `py.typed` marker so consumers' type checkers use the library's hints.
- README "Environment variables" reference documenting every `AIDOT_*` knob.
- Non-blocking `mypy` and coverage reporting in CI.

### Changed
- `camera/client.py`: added diagnosable `debug(..., exc_info=True)` logging to 93
  previously-silent exception handlers; proactive ffmpeg-presence guards with
  actionable errors; `XDG_CONFIG_HOME` support for the sprop cache.
- `credentials.py` honors `XDG_CONFIG_HOME`; `discover.py` resolves network tools
  via `PATH` and degrades gracefully instead of raising.
- Translated remaining non-English source comments to English; added docstrings
  to the public `DeviceClient` / `AidotClient` / `Discover` APIs.
- Modernized tooling: ruff-based pre-commit (replacing a stale 2019 config) and
  `ruff check` in CI.

### Fixed
- Corrected the distribution name / repository URLs in the README install
  commands and project metadata.
