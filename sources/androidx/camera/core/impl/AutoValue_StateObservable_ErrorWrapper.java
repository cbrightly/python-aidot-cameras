package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.StateObservable;

public final class AutoValue_StateObservable_ErrorWrapper extends StateObservable.ErrorWrapper {
    private final Throwable error;

    AutoValue_StateObservable_ErrorWrapper(Throwable error2) {
        if (error2 != null) {
            this.error = error2;
            return;
        }
        throw new NullPointerException("Null error");
    }

    @NonNull
    public Throwable getError() {
        return this.error;
    }

    public String toString() {
        return "ErrorWrapper{error=" + this.error + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof StateObservable.ErrorWrapper) {
            return this.error.equals(((StateObservable.ErrorWrapper) o).getError());
        }
        return false;
    }

    public int hashCode() {
        return (1 * 1000003) ^ this.error.hashCode();
    }
}
