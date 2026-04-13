package androidx.camera.core;

import androidx.camera.core.ImageReaderFormatRecommender;

public final class AutoValue_ImageReaderFormatRecommender_FormatCombo extends ImageReaderFormatRecommender.FormatCombo {
    private final int imageAnalysisFormat;
    private final int imageCaptureFormat;

    AutoValue_ImageReaderFormatRecommender_FormatCombo(int imageCaptureFormat2, int imageAnalysisFormat2) {
        this.imageCaptureFormat = imageCaptureFormat2;
        this.imageAnalysisFormat = imageAnalysisFormat2;
    }

    /* access modifiers changed from: package-private */
    public int imageCaptureFormat() {
        return this.imageCaptureFormat;
    }

    /* access modifiers changed from: package-private */
    public int imageAnalysisFormat() {
        return this.imageAnalysisFormat;
    }

    public String toString() {
        return "FormatCombo{imageCaptureFormat=" + this.imageCaptureFormat + ", imageAnalysisFormat=" + this.imageAnalysisFormat + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImageReaderFormatRecommender.FormatCombo)) {
            return false;
        }
        ImageReaderFormatRecommender.FormatCombo that = (ImageReaderFormatRecommender.FormatCombo) o;
        if (this.imageCaptureFormat == that.imageCaptureFormat() && this.imageAnalysisFormat == that.imageAnalysisFormat()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((1 * 1000003) ^ this.imageCaptureFormat) * 1000003) ^ this.imageAnalysisFormat;
    }
}
