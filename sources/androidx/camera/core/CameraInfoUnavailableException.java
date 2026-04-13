package androidx.camera.core;

import androidx.annotation.RestrictTo;

public final class CameraInfoUnavailableException extends Exception {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraInfoUnavailableException(String s, Throwable e) {
        super(s, e);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public CameraInfoUnavailableException(String s) {
        super(s);
    }
}
