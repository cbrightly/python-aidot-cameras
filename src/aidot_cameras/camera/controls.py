"""Camera device-control methods, split out of CameraMixin.

A behavior-preserving mixin holding the high-level control setters (lights,
siren, mic, LED, night-vision, PTZ, resolution, etc.). They issue commands via
the inherited async_set_device_attribute / async_trigger_device_action, so this
mixin must be combined into CameraMixin (it is not usable standalone).
"""

import logging
import struct

from .constants import _PTZ_DIR_CODES, _STREAM_QUALITY, SETSTREAMCTRL_CMD

_LOGGER = logging.getLogger(__name__)


class _CameraControlsMixin:
    """Device-control setters mixed into CameraMixin."""

    async def async_set_motion_detection(self, enabled: bool) -> bool:
        """Enable or disable the camera's motion detection."""
        # MotionDetection_Enable is read as getString() in IpcServiceImpl → use str
        return await self.async_set_device_attribute(
            "MotionDetection_Enable", "1" if enabled else "0")

    async def async_set_floodlight(self, on: bool, brightness: int = 100) -> bool:
        """Turn the floodlight on/off (with optional 0-100 brightness)."""
        # Confirmed 2026-05-05: cameras with autoLightEnable=1 (A001064 PTZ, A000088) ignore
        # manual LightOnOff commands unless auto-light is disabled first.
        # Turning on: disable auto-light, set LightOnOff=1 (and optional Dimming).
        # Turning off: set LightOnOff=0 only (leave autoLightEnable as-is so the
        # camera can resume auto mode on its own schedule).
        if on:
            # Fire prereq without blocking - 1.5s is enough to catch most ACKs but
            # we don't need confirmation before sending LightOnOff.
            await self.async_set_device_attribute("autoLightEnable", 0, timeout=1.5)
        ok = await self.async_set_device_attribute("LightOnOff", 1 if on else 0)
        if ok and on and brightness != 100:
            await self.async_set_device_attribute(
                "Dimming", max(0, min(100, brightness)))
        return ok

    async def async_set_status_led(self, enabled: bool) -> bool:
        """Turn the camera's status LED on or off."""
        return await self.async_set_device_attribute("LedOnOff", 1 if enabled else 0)

    async def async_set_microphone(self, enabled: bool) -> bool:
        """Enable or disable the camera's microphone."""
        # micEnable: 1=on (default), 0=off
        return await self.async_set_device_attribute("micEnable", 1 if enabled else 0)

    async def async_set_speaker_volume(self, level: int) -> bool:
        """Set the camera's speaker volume (0-100, clamped)."""
        return await self.async_set_device_attribute(
            "SoundLevel", max(0, min(100, level)))

    async def async_set_siren(self, on: bool) -> bool:
        """Turn the camera's siren on or off."""
        # Siren uses devActionReq(action="playSound", in=[on,1,30])
        # in[0]=1/0, in[1]=type?, in[2]=duration seconds
        # No attr notification comes back - track state locally.
        result = await self.async_trigger_device_action(
            "playSound", [1 if on else 0, 1, 30])
        self.status.siren = on
        return result

    async def async_set_night_vision(self, mode: str) -> bool:
        """mode: 'auto' (0), 'on' (1), 'off' (2)"""
        _modes = {"auto": 0, "on": 1, "off": 2}
        value = _modes.get(mode.lower())
        if value is None:
            raise ValueError(f"Invalid night vision mode: {mode!r}. Expected 'auto', 'on', or 'off'.")
        return await self.async_set_device_attribute("nightVisionMode", value)

    async def async_set_ptz_tracking(self, enabled: bool) -> bool:
        """Enable or disable PTZ motion auto-tracking."""
        return await self.async_set_device_attribute(
            "trackingMode", 1 if enabled else 0)

    async def async_set_motion_sensitivity(self, level: int) -> bool:
        """Set motion detection sensitivity.

        level: 1 (lowest) - 5 (highest).
        Attribute: MotionDetection_Sen (observed value '2' on A000088).
        """
        return await self.async_set_device_attribute(
            "MotionDetection_Sen", max(1, min(5, int(level))))

    async def async_set_ir_light(self, enabled: bool) -> bool:
        """Control the IR illumination LEDs independently of night-vision mode.

        Attribute: nightVisionIRLight (0=off, 1=on).
        Note: nightVisionMode still controls the IR cut filter / B&W switch.
        """
        return await self.async_set_device_attribute(
            "nightVisionIRLight", 1 if enabled else 0)

    async def async_ptz_move(
        self,
        direction: str,
        speed: int = 4,
        preset: int = 0,
    ) -> bool:
        """Send a PTZ pan/tilt/zoom command.

        direction: "up"|"down"|"left"|"right"|"stop"|"goto"|
                   "zoom_in"|"zoom_out"
        speed:     0-255, default 4 (matches app default)
        preset:    preset slot for "goto" command

        Source: a.java d1() / f0.java A2() → avSendIOCtrl(4097, 8B payload)
        Payload: [direction_code, speed, preset, 0, 0, 0, 0, 0]

        Direction codes (AVIOCTRLDEFs.java):
          STOP=0, UP=1, DOWN=2, LEFT=3, RIGHT=6
          LEFT_UP=4, LEFT_DOWN=5, RIGHT_UP=7, RIGHT_DOWN=8
          GOTO_POINT=12, SET_POINT=10, ZOOM_IN=23, ZOOM_OUT=24
        """
        code = _PTZ_DIR_CODES.get(direction.lower())
        if code is None:
            _LOGGER.error("async_ptz_move: unknown direction %r", direction)
            return False

        payload = bytes([code, min(255, max(0, speed)), preset, 0, 0, 0, 0, 0])
        session = self._stream_session
        if session is None:
            _LOGGER.warning("async_ptz_move: no active stream session")
            return False
        ok = session._avio_cmd(4097, payload)
        _LOGGER.debug(
            "PTZ %s (code=%d speed=%d preset=%d) → %s",
            direction, code, speed, preset, "sent" if ok else "no channel yet",
        )
        return ok

    async def async_ptz_stop(self) -> bool:
        """Send PTZ stop command (direction_code=0, speed=0)."""
        return await self.async_ptz_move("stop", speed=0)

    async def async_set_resolution(self, quality: str) -> bool:
        """Switch the live-stream resolution.

        quality: "hd" (AVIOCTRL_QUALITY_MAX) or "sd" (AVIOCTRL_QUALITY_MIDDLE),
        mirroring the official app's HD/SD toggle.  Rides the active stream
        session (SETSTREAMCTRL=800), so the camera must be streaming.

        Source: f0.java g3() → X2(800, SetStreamCtrlReq.parseContent(0, quality)).
        Payload <IB3x> = channel(0) + quality byte + 3 reserved.
        """
        q = _STREAM_QUALITY.get(quality.lower())
        if q is None:
            _LOGGER.error("async_set_resolution: unknown quality %r", quality)
            return False
        payload = struct.pack("<IB3x", 0, q)
        session = self._stream_session
        if session is None:
            _LOGGER.warning("async_set_resolution: no active stream session")
            return False
        ok = session._avio_cmd(SETSTREAMCTRL_CMD, payload)
        _LOGGER.debug(
            "set resolution %s (quality=%d) → %s",
            quality, q, "sent" if ok else "no channel yet",
        )
        return ok
