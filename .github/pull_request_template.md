## What and why

<!-- What changes, and the problem it solves. Link the issue if there is one. -->

## What it touches

<!-- Tick everything the change can affect. -->

- [ ] DTLS cameras (for example the M3 Pro family)
- [ ] SDES cameras (for example the L2 battery and PTZ families)
- [ ] Battery cameras: wake, sleep, sparse audio, cold opens
- [ ] Live media: WebRTC session, direct publish to go2rtc, HTTP serve
- [ ] Recordings, HLS or SD card
- [ ] Cloud or MQTT: login, tokens, device control, events
- [ ] Nothing above: docs, CI or tooling only

Camera models tested on:

## How it was tested

- [ ] `pytest tests/ --ignore=tests/e2e` passes
- [ ] `pytest tests/e2e -m e2e` passes (needed for media, publish or session changes)
- [ ] `ruff check .` passes, and `ruff format --check` on the files you changed
- [ ] Tested on real cameras (describe below)

<!-- A single clean session proves little on these cameras. Say how many
     sessions you ran, and give numbers - durations, frame counts, error
     counts, before and after - rather than "works for me". -->

## Checklist

- [ ] The title is a conventional commit (`fix(camera): ...`, `feat(publish): ...`, `docs: ...`)
- [ ] `CHANGELOG.md` has an entry under `[Unreleased]` for anything a user can notice
- [ ] README and `docs/` cover any new or changed env var, stat key or log line
- [ ] Defaults keep today's behaviour, or the change of default is called out above
- [ ] Code, tests, logs and this description contain no credentials, tokens, account emails, device ids, MAC or IP addresses, or camera names
