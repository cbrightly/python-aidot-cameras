package androidx.camera.core;

import androidx.annotation.RestrictTo;

public final class CameraClosedException extends RuntimeException {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    CameraClosedException(String s, Throwable e) {
        super(s, e);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    CameraClosedException(String s) {
        super(s);
    }
}
