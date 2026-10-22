"""The account region is checked before it is put into a cloud hostname.

The known regions are us, eu and jp: the only values in upstream python-aidot's
country table, the only region codes in the vendor app's bundled region table,
and the only ones seen in recorded cloud hosts. AIDOT_EXTRA_REGIONS accepts a
region the cloud adds later.
"""

import pytest

from aidot_cameras import AidotError, AidotUnknownRegion
from aidot_cameras.camera.constants import (
    KNOWN_REGIONS,
    aidot_api_base,
    mqtt_fallback_url,
    smarthome_base,
    validate_region,
)


@pytest.fixture(autouse=True)
def _clean(monkeypatch):
    for name in (
        "AIDOT_EXTRA_REGIONS",
        "AIDOT_API_BASE_TEMPLATE",
        "AIDOT_SMARTHOME_URL_TEMPLATE",
    ):
        monkeypatch.delenv(name, raising=False)


def test_the_known_regions():
    assert KNOWN_REGIONS == {"us", "eu", "jp"}


def test_the_known_regions_match_upstream():
    """Upstream's country table is where the region is chosen at login."""
    from aidot.const import SUPPORTED_COUNTRYS

    assert {c["region"].lower() for c in SUPPORTED_COUNTRYS} <= KNOWN_REGIONS


@pytest.mark.parametrize("region", ["us", "eu", "jp", "US", "Eu"])
def test_known_regions_pass_lowercased(region):
    assert validate_region(region) == region.lower()
    assert aidot_api_base(region) == f"https://prod-{region.lower()}-api.arnoo.com"
    assert smarthome_base(region) == f"https://{region.lower()}-smarthome.arnoo.com:443"
    assert mqtt_fallback_url(region) == (
        f"wss://{region.lower()}-mqtt.arnoo.com:8443/mqtt"
    )


@pytest.mark.parametrize(
    "region",
    [
        "uk",  # shaped right, not a known region
        "",
        "us.evil.example",
        "us/",
        "us@x",
        "us:8443",
        "-us",
        "us-",
        "u s",
        "us\n",
        "x" * 40,
        None,
        7,
    ],
)
def test_anything_else_is_refused(region):
    with pytest.raises(AidotUnknownRegion) as ei:
        validate_region(region)
    assert ei.value.region == region
    with pytest.raises(AidotUnknownRegion):
        aidot_api_base(region)
    with pytest.raises(AidotUnknownRegion):
        smarthome_base(region)
    with pytest.raises(AidotUnknownRegion):
        mqtt_fallback_url(region)


def test_the_error_names_the_override():
    with pytest.raises(AidotUnknownRegion) as ei:
        validate_region("uk")
    assert "AIDOT_EXTRA_REGIONS" in str(ei.value)
    assert isinstance(ei.value, AidotError) and isinstance(ei.value, ValueError)


def test_the_override_adds_a_region(monkeypatch):
    monkeypatch.setenv("AIDOT_EXTRA_REGIONS", "uk, sa")
    assert validate_region("uk") == "uk"
    assert validate_region("SA") == "sa"
    assert aidot_api_base("uk") == "https://prod-uk-api.arnoo.com"


def test_the_override_cannot_admit_a_bad_shape(monkeypatch):
    monkeypatch.setenv("AIDOT_EXTRA_REGIONS", "us.evil.example,a/b")
    for bad in ("us.evil.example", "a/b"):
        with pytest.raises(AidotUnknownRegion):
            validate_region(bad)


def test_an_endpoint_template_still_validates(monkeypatch):
    monkeypatch.setenv("AIDOT_API_BASE_TEMPLATE", "http://localhost:9001/{region}")
    assert aidot_api_base("eu") == "http://localhost:9001/eu"
    with pytest.raises(AidotUnknownRegion):
        aidot_api_base("eu/../x")


def test_a_device_client_with_a_bad_stored_region_fails_the_request(
    make_camera_device_client, login_info
):
    dc = make_camera_device_client("A001513", login=login_info(region="xx"))
    with pytest.raises(AidotUnknownRegion):
        dc._smarthome_base
