package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageCaptureException extends Exception {
    private final int mImageCaptureError;

    public ImageCaptureException(int imageCaptureError, @NonNull String message, @Nullable Throwable cause) {
        super(message, cause);
        this.mImageCaptureError = imageCaptureError;
    }

    public int getImageCaptureError() {
        return this.mImageCaptureError;
    }
}
