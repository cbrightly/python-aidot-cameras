"""The request sequence id the app puts on its MQTT envelopes."""

import random


def _ap_seq() -> str:
    """A fresh ``ap`` + seven digit ``seq``, the shape the app sends."""
    return f"ap{random.randint(1000000, 9999999)}"
