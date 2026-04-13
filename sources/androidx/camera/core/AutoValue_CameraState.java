package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraState;

public final class AutoValue_CameraState extends CameraState {
    private final CameraState.StateError error;
    private final CameraState.Type type;

    AutoValue_CameraState(CameraState.Type type2, @Nullable CameraState.StateError error2) {
        if (type2 != null) {
            this.type = type2;
            this.error = error2;
            return;
        }
        throw new NullPointerException("Null type");
    }

    @NonNull
    public CameraState.Type getType() {
        return this.type;
    }

    @Nullable
    public CameraState.StateError getError() {
        return this.error;
    }

    public String toString() {
        return "CameraState{type=" + this.type + ", error=" + this.error + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CameraState)) {
            return false;
        }
        CameraState that = (CameraState) o;
        if (this.type.equals(that.getType())) {
            CameraState.StateError stateError = this.error;
            if (stateError == null) {
                if (that.getError() == null) {
                    return true;
                }
            } else if (stateError.equals(that.getError())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int h$ = ((1 * 1000003) ^ this.type.hashCode()) * 1000003;
        CameraState.StateError stateError = this.error;
        return h$ ^ (stateError == null ? 0 : stateError.hashCode());
    }
}
