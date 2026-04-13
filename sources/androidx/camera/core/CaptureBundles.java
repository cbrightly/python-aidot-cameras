package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureStage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class CaptureBundles {
    @NonNull
    static CaptureBundle singleDefaultCaptureBundle() {
        return createCaptureBundle(new CaptureStage.DefaultCaptureStage());
    }

    @NonNull
    static CaptureBundle createCaptureBundle(@NonNull CaptureStage... captureStages) {
        return new CaptureBundleImpl(Arrays.asList(captureStages));
    }

    @NonNull
    static CaptureBundle createCaptureBundle(@NonNull List<CaptureStage> captureStageList) {
        return new CaptureBundleImpl(captureStageList);
    }

    public static final class CaptureBundleImpl implements CaptureBundle {
        final List<CaptureStage> mCaptureStageList;

        CaptureBundleImpl(List<CaptureStage> captureStageList) {
            if (captureStageList == null || captureStageList.isEmpty()) {
                throw new IllegalArgumentException("Cannot set an empty CaptureStage list.");
            }
            this.mCaptureStageList = Collections.unmodifiableList(new ArrayList(captureStageList));
        }

        public List<CaptureStage> getCaptureStages() {
            return this.mCaptureStageList;
        }
    }

    private CaptureBundles() {
    }
}
