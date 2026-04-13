package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.CameraCaptureMetaData;
import androidx.camera.core.impl.utils.ExifData;

public interface CameraCaptureResult {
    @NonNull
    CameraCaptureMetaData.AeState getAeState();

    @NonNull
    CameraCaptureMetaData.AfMode getAfMode();

    @NonNull
    CameraCaptureMetaData.AfState getAfState();

    @NonNull
    CameraCaptureMetaData.AwbState getAwbState();

    @NonNull
    CameraCaptureMetaData.FlashState getFlashState();

    @NonNull
    TagBundle getTagBundle();

    long getTimestamp();

    void populateExifData(@NonNull ExifData.Builder builder);

    public static final class EmptyCameraCaptureResult implements CameraCaptureResult {
        public /* synthetic */ void populateExifData(ExifData.Builder builder) {
            u.a(this, builder);
        }

        @NonNull
        public static CameraCaptureResult create() {
            return new EmptyCameraCaptureResult();
        }

        @NonNull
        public CameraCaptureMetaData.AfMode getAfMode() {
            return CameraCaptureMetaData.AfMode.UNKNOWN;
        }

        @NonNull
        public CameraCaptureMetaData.AfState getAfState() {
            return CameraCaptureMetaData.AfState.UNKNOWN;
        }

        @NonNull
        public CameraCaptureMetaData.AeState getAeState() {
            return CameraCaptureMetaData.AeState.UNKNOWN;
        }

        @NonNull
        public CameraCaptureMetaData.AwbState getAwbState() {
            return CameraCaptureMetaData.AwbState.UNKNOWN;
        }

        @NonNull
        public CameraCaptureMetaData.FlashState getFlashState() {
            return CameraCaptureMetaData.FlashState.UNKNOWN;
        }

        public long getTimestamp() {
            return -1;
        }

        @NonNull
        public TagBundle getTagBundle() {
            return TagBundle.emptyBundle();
        }
    }
}
