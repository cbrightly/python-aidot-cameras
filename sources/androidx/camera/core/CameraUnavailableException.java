package androidx.camera.core;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CameraUnavailableException extends Exception {
    public static final int CAMERA_DISABLED = 1;
    public static final int CAMERA_DISCONNECTED = 2;
    public static final int CAMERA_ERROR = 3;
    public static final int CAMERA_IN_USE = 4;
    public static final int CAMERA_MAX_IN_USE = 5;
    public static final int CAMERA_UNAVAILABLE_DO_NOT_DISTURB = 6;
    public static final int CAMERA_UNKNOWN_ERROR = 0;
    private final int mReason;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Reason {
    }

    public CameraUnavailableException(int reason) {
        this.mReason = reason;
    }

    public CameraUnavailableException(int reason, @Nullable String message) {
        super(message);
        this.mReason = reason;
    }

    public CameraUnavailableException(int reason, @Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
        this.mReason = reason;
    }

    public CameraUnavailableException(int reason, @Nullable Throwable cause) {
        super(cause);
        this.mReason = reason;
    }

    public int getReason() {
        return this.mReason;
    }
}
