package androidx.camera.core;

import androidx.annotation.Nullable;
import androidx.camera.core.CameraState;

public final class AutoValue_CameraState_StateError extends CameraState.StateError {
    private final Throwable cause;
    private final int code;

    AutoValue_CameraState_StateError(int code2, @Nullable Throwable cause2) {
        this.code = code2;
        this.cause = cause2;
    }

    public int getCode() {
        return this.code;
    }

    @Nullable
    public Throwable getCause() {
        return this.cause;
    }

    public String toString() {
        return "StateError{code=" + this.code + ", cause=" + this.cause + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CameraState.StateError)) {
            return false;
        }
        CameraState.StateError that = (CameraState.StateError) o;
        if (this.code == that.getCode()) {
            Throwable th = this.cause;
            if (th == null) {
                if (that.getCause() == null) {
                    return true;
                }
            } else if (th.equals(that.getCause())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int h$ = ((1 * 1000003) ^ this.code) * 1000003;
        Throwable th = this.cause;
        return h$ ^ (th == null ? 0 : th.hashCode());
    }
}
