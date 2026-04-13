package androidx.camera.core.impl;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface UseCaseConfigFactory {

    public enum CaptureType {
        IMAGE_CAPTURE,
        PREVIEW,
        IMAGE_ANALYSIS,
        VIDEO_CAPTURE
    }

    public interface Provider {
        @NonNull
        UseCaseConfigFactory newInstance(@NonNull Context context);
    }

    @Nullable
    Config getConfig(@NonNull CaptureType captureType);
}
