package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.impl.utils.ExifData;

/* compiled from: CameraCaptureResult */
public final /* synthetic */ class u {
    public static void a(@NonNull CameraCaptureResult _this, ExifData.Builder exifBuilder) {
        exifBuilder.setFlashState(_this.getFlashState());
    }
}
