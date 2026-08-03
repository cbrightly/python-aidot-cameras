"""Fake-lab end-to-end tier.

Drives the REAL client stack (real CameraDeviceClient, real paho MQTT over
websockets, real pylibsrtp / aiortc / ffmpeg) against a fake cloud, a fake MQTT
broker, and fake cameras on 127.0.0.1 - no secrets, no hardware, no egress.

Everything here is selected by the ``e2e`` marker.
"""
