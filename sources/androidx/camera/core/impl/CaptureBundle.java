package androidx.camera.core.impl;

import androidx.annotation.Nullable;
import java.util.List;

public interface CaptureBundle {
    @Nullable
    List<CaptureStage> getCaptureStages();
}
