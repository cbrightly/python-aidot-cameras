"""Every exception type is reachable from the package root.

docs/API-STABILITY.md promises every exception type in
``aidot_cameras.exceptions``. ``AidotCameraWrongCodec`` was defined there but
left out of the package's imports and ``__all__``.
"""

import inspect

import aidot_cameras
from aidot_cameras import exceptions


def _exception_types():
    return {
        name: obj
        for name, obj in vars(exceptions).items()
        if inspect.isclass(obj) and issubclass(obj, BaseException)
    }


def test_the_exceptions_module_has_the_camera_errors():
    found = _exception_types()
    assert issubclass(found["AidotCameraWrongCodec"], exceptions.AidotError)
    assert "AidotError" in found


def test_every_exception_type_is_exported_from_the_package():
    missing = sorted(
        name
        for name, cls in _exception_types().items()
        if name not in aidot_cameras.__all__
        or getattr(aidot_cameras, name, None) is not cls
    )
    assert not missing, f"not exported from aidot_cameras: {missing}"
