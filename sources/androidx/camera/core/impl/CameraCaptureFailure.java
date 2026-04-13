package androidx.camera.core.impl;

import androidx.annotation.NonNull;

public final class CameraCaptureFailure {
    private final Reason mReason;

    public enum Reason {
        ERROR
    }

    public CameraCaptureFailure(@NonNull Reason reason) {
        this.mReason = reason;
    }

    @NonNull
    public Reason getReason() {
        return this.mReason;
    }
}
