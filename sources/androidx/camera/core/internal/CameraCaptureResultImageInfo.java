package androidx.camera.core.internal;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageInfo;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.TagBundle;
import androidx.camera.core.impl.utils.ExifData;

public final class CameraCaptureResultImageInfo implements ImageInfo {
    private final CameraCaptureResult mCameraCaptureResult;

    public CameraCaptureResultImageInfo(@NonNull CameraCaptureResult cameraCaptureResult) {
        this.mCameraCaptureResult = cameraCaptureResult;
    }

    @NonNull
    public TagBundle getTagBundle() {
        return this.mCameraCaptureResult.getTagBundle();
    }

    public long getTimestamp() {
        return this.mCameraCaptureResult.getTimestamp();
    }

    public int getRotationDegrees() {
        return 0;
    }

    public void populateExifData(@NonNull ExifData.Builder exifBuilder) {
        this.mCameraCaptureResult.populateExifData(exifBuilder);
    }

    @NonNull
    public CameraCaptureResult getCameraCaptureResult() {
        return this.mCameraCaptureResult;
    }
}
