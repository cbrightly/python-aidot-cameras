package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import java.util.Collections;
import java.util.LinkedHashSet;

/* compiled from: CameraInternal */
public final /* synthetic */ class w {
    @NonNull
    public static CameraControl a(CameraInternal _this) {
        return _this.getCameraControlInternal();
    }

    @NonNull
    public static CameraInfo b(CameraInternal _this) {
        return _this.getCameraInfoInternal();
    }

    @NonNull
    public static LinkedHashSet c(CameraInternal _this) {
        return new LinkedHashSet(Collections.singleton(_this));
    }

    @NonNull
    public static CameraConfig d(CameraInternal _this) {
        return CameraConfigs.emptyConfig();
    }

    public static void e(@Nullable CameraInternal _this, CameraConfig cameraConfig) {
    }
}
