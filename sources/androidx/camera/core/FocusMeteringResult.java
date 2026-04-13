package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

public final class FocusMeteringResult {
    private boolean mIsFocusSuccessful;

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static FocusMeteringResult emptyInstance() {
        return new FocusMeteringResult(false);
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static FocusMeteringResult create(boolean isFocusSuccess) {
        return new FocusMeteringResult(isFocusSuccess);
    }

    private FocusMeteringResult(boolean isFocusSuccess) {
        this.mIsFocusSuccessful = isFocusSuccess;
    }

    public boolean isFocusSuccessful() {
        return this.mIsFocusSuccessful;
    }
}
