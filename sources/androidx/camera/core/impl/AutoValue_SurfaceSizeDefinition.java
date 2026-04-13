package androidx.camera.core.impl;

import android.util.Size;

public final class AutoValue_SurfaceSizeDefinition extends SurfaceSizeDefinition {
    private final Size analysisSize;
    private final Size previewSize;
    private final Size recordSize;

    AutoValue_SurfaceSizeDefinition(Size analysisSize2, Size previewSize2, Size recordSize2) {
        if (analysisSize2 != null) {
            this.analysisSize = analysisSize2;
            if (previewSize2 != null) {
                this.previewSize = previewSize2;
                if (recordSize2 != null) {
                    this.recordSize = recordSize2;
                    return;
                }
                throw new NullPointerException("Null recordSize");
            }
            throw new NullPointerException("Null previewSize");
        }
        throw new NullPointerException("Null analysisSize");
    }

    public Size getAnalysisSize() {
        return this.analysisSize;
    }

    public Size getPreviewSize() {
        return this.previewSize;
    }

    public Size getRecordSize() {
        return this.recordSize;
    }

    public String toString() {
        return "SurfaceSizeDefinition{analysisSize=" + this.analysisSize + ", previewSize=" + this.previewSize + ", recordSize=" + this.recordSize + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SurfaceSizeDefinition)) {
            return false;
        }
        SurfaceSizeDefinition that = (SurfaceSizeDefinition) o;
        if (!this.analysisSize.equals(that.getAnalysisSize()) || !this.previewSize.equals(that.getPreviewSize()) || !this.recordSize.equals(that.getRecordSize())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((1 * 1000003) ^ this.analysisSize.hashCode()) * 1000003) ^ this.previewSize.hashCode()) * 1000003) ^ this.recordSize.hashCode();
    }
}
