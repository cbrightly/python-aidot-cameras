# Testing tiers

> **Status: all three tiers are on `main`.** The unit tier is 708 passed /
> 5 skipped and the fake lab is 20 passed; the `e2e` and `live` markers, the
> `[tool.pytest.ini_options]` block with `addopts`, `tests/e2e/` and the env
> seams are all in place. The live tier's workflow lives in a private repo and
> gates publishing on a commit status - see [`CI-RUNNER.md`](CI-RUNNER.md).
>
> The fake lab needs `amqtt`, `pytest-asyncio` and `pytest-timeout`, which the
> unit tier does not install; `tests/e2e/conftest.py` skips the whole tier when
> `amqtt` is missing rather than erroring at collection.

Streaming breaks in ways unit tests structurally cannot see: the cloud accepts
the call, signaling looks healthy, and only the media tells the truth. So the
suite is in three tiers, each catching a class the one below it cannot.

| Tier | Marker | Needs | Runs | Catches |
| --- | --- | --- | --- | --- |
| unit | *(none)* | nothing | every push/PR | pure logic, policy, parsing, invariants |
| e2e ("fake lab") | `e2e` | ffmpeg, local ports | every push/PR | signaling order, cross-component behaviour, terminal acks, fleets |
| live | `live` | **real cameras on the LAN** | release only, self-hosted | everything that only real firmware and the real cloud do |

```bash
pytest tests/                      # unit + e2e; NEVER touches hardware
pytest tests/ -m "not e2e and not live"    # unit only (fast)
pytest tests/e2e -m e2e -n 4       # the fake lab, parallel (~2 min)
pytest tests/ -m live              # live tier - requires cameras, opt-in
```

`addopts = -m 'not live'` in `pyproject.toml` means a bare `pytest` can never
reach for hardware by accident. The live tier has to be asked for by name.

Note the `and not live` above: pytest's `-m` takes a single expression and the
command line REPLACES `addopts`, it does not merge with it. So any command that
passes its own `-m` drops the default live guard and has to restate it - plain
`-m "not e2e"` would happily collect the live tier and drive real cameras.

## The fake lab (`tests/e2e/`)

Drives the **real** client stack - real `CameraDeviceClient`, real paho MQTT
over websockets, real `aiohttp`, real ffmpeg - against fakes on 127.0.0.1. No
secrets, no egress, so it is safe on fork PRs.

| Fake | What it is |
| --- | --- |
| `fakes/mqtt_broker.py` | a real amqtt broker with a websocket listener |
| `fakes/cloud_http.py` | the cloud endpoints an open calls, with a request log |
| `fakes/signaling.py` | the camera side of the MQTT contract |
| `fakes/go2rtc_stub.py` | the go2rtc REST API, with a request log |

The library is pointed at them by env seams (`AIDOT_MQTT_URL`,
`AIDOT_API_BASE_TEMPLATE`, `AIDOT_SMARTHOME_URL_TEMPLATE`,
`AIDOT_STUN_SERVERS`, `AIDOT_TURN_SERVERS`). Each defaults to today's
production value, so an unset environment behaves byte-identically -
`tests/test_env_seams.py` locks that in both directions.

### Two traps worth knowing before you edit these

**The fake broker runs on the test's event loop.** paho's blocking
`connect()` therefore deadlocks against the very coroutine that has to answer
its handshake. Use `connect_async()` + `loop_start()`, and never block that
loop waiting for a connection.

**Never raise inside a paho callback.** It kills the network thread, and the
failure then surfaces as an unrelated timeout somewhere else entirely.

### Why some things are patched rather than made configurable

`_FIRST_MEDIA_WAIT_S` (75 s) is shortened for the tier by monkeypatch, not by
an env knob. A too-low value in production is itself one of the regressions
this suite exists to prevent, so the constant stays un-tunable and
`tests/test_media_wait_floor.py` asserts it clears the documented 25-70 s
cold-start window.

Measured on hardware 2026-08-01, after the SDES answer-harvest fix: **DTLS
reaches first media in 1.6-5.0 s, SDES in 7.9-16.7 s** - so 75 s now carries a
large margin over that window. Do not read that as licence to lower it: a
*sleeping* battery L2 still runs past 107 s before failing, and this wait is
what separates "slow to wake" from "broken". What the margin does mean is that a
stuck SDES open burns 75 s per attempt, which dominates the wall-clock of a
failing fleet run.

A companion constant, `_PRE_LAUNCH_ANSWER_WAIT_S` (8 s), bounds how long the
open waits for the camera's `webrtcResp` before parsing it for the ICE
credentials the nomination needs. It exists because giving that answer a single
event-loop cycle - what the code did before - meant it was never actually there,
and no SDES camera ever streamed.

## The live tier

See [`CI-RUNNER.md`](CI-RUNNER.md). `scripts/live_validate.py` is the harness;
it gates PyPI publishes through the private repo's `live-validate.yml` + `publish.yml`'s
`live-gate`.

Note that the two transports report media differently, and anything asserting
"is it streaming?" has to handle both: the DTLS path decodes in-process and
calls `on_frame`, while **the SDES path never calls `on_frame`** (ffmpeg owns
the media). For SDES, use `SdesSession.media_stats()` or recorded bytes.

## Adding a test: which tier?

- Can you express it as inputs -> outputs on a function? **Unit.** Prefer
  extracting a pure helper over reaching into a 4000-line coroutine - that is
  how `_sdes_await_answer_or_terminal` and `_is_self_referential_source` became
  testable.
- Does it depend on the *order* of network calls, on another component's view
  (go2rtc), on more than two cameras, or on a restart? **e2e.**
- Does it depend on real firmware timing, real ICE, or the real cloud's
  behaviour? **Live** - and expect it to be flaky by nature, which is why the
  live harness retries and the e2e tier does not.

A test that flakes twice gets quarantined immediately rather than being left
to erode trust in the tier.
