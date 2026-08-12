"""Is there a card in the slot, and do we actually know?

A camera with no SD card and a camera that could not be asked look identical to
every listing call: both return nothing. The cloud has been carrying the answer
all along in `SDcardExistFlag` / `SDcardBaseInfo`, and nothing read it - so a
user with an empty slot was told "the camera did not answer", which is false and
not actionable.

The whole value of the field is in the THIRD state. Two of the fleet's seven
cameras report the flag as false, one reports true, and four carry neither key -
including an A000088, the same model as all three that do report. So absence
cannot mean "this model cannot report it", and anything that collapses unknown
into "no card" would tell someone with a working camera it has no card.
"""
from aidot_cameras.camera.models import CameraStatusData


def test_unknown_by_default():
    assert CameraStatusData().sd_card_present is None


def test_a_card_that_is_there():
    s = CameraStatusData()
    s.update({"SDcardExistFlag": True})
    assert s.sd_card_present is True


def test_a_slot_that_is_empty():
    s = CameraStatusData()
    s.update({"SDcardExistFlag": False})
    assert s.sd_card_present is False


def test_a_camera_that_says_nothing_stays_unknown():
    # The regression that matters. Four of seven cameras look like this, and
    # `False` here would put "there is no SD card in this camera" under a
    # camera nobody has any evidence about.
    s = CameraStatusData()
    s.update({"Battery_remaining": "80", "SDcardStatus": "1"})
    assert s.sd_card_present is None


def test_the_base_info_answers_when_the_flag_is_absent():
    # Measured shape: a STRING holding a JSON array, not an array. Element 0
    # agreed with the flag on every camera that carried both.
    s = CameraStatusData()
    s.update({"SDcardBaseInfo": "[true,30432,0,1,0]"})
    assert s.sd_card_present is True
    s2 = CameraStatusData()
    s2.update({"SDcardBaseInfo": "[false,0,0,0,0]"})
    assert s2.sd_card_present is False


def test_the_base_info_is_also_accepted_as_a_real_list():
    # The same keys arrive in different shapes across cameras - the neighbouring
    # SdcardRecord_Enable is "1" on five and true on one - so shape is not
    # something to rely on.
    s = CameraStatusData()
    s.update({"SDcardBaseInfo": [True, 30432, 0, 1, 0]})
    assert s.sd_card_present is True


def test_the_flag_wins_over_the_base_info():
    s = CameraStatusData()
    s.update({"SDcardExistFlag": False, "SDcardBaseInfo": "[true,30432,0,1,0]"})
    assert s.sd_card_present is False


def test_a_reply_that_cannot_be_read_is_unknown_not_absent():
    # A decode failure is not evidence about the slot. Silence about a card and
    # a card that is not there are the two answers this whole subsystem exists
    # to keep apart.
    for junk in ("", "[", "not json", "[]", None, 5, {}):
        s = CameraStatusData()
        s.update({"SDcardBaseInfo": junk})
        assert s.sd_card_present is None, junk


def test_a_later_silent_update_does_not_clobber_a_known_reading():
    # Camera attribute pushes are partial: a notif about motion carries no card
    # keys, and must not erase what the last full poll established.
    s = CameraStatusData()
    s.update({"SDcardExistFlag": True})
    s.update({"Occupancy": True})
    assert s.sd_card_present is True


def test_it_arrives_through_the_cloud_properties_path_too():
    # Both shapes reach the same parser: setDevAttrNotif attrs and the cloud
    # device record's `properties`. The probe read it off the latter.
    s = CameraStatusData()
    s.update_from_camera_attributes(
        {"SDcardExistFlag": False, "SDcardBaseInfo": "[false,0,0,0,0]"})
    assert s.sd_card_present is False
