package androidx.camera.core.impl;

import android.util.Size;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SurfaceSizeDefinition {
    public abstract Size getAnalysisSize();

    public abstract Size getPreviewSize();

    public abstract Size getRecordSize();

    SurfaceSizeDefinition() {
    }

    public static SurfaceSizeDefinition create(Size analysisSize, Size previewSize, Size recordSize) {
        return new AutoValue_SurfaceSizeDefinition(analysisSize, previewSize, recordSize);
    }
}
