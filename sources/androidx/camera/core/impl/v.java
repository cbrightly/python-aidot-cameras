package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraFilter;

/* compiled from: CameraConfig */
public final /* synthetic */ class v {
    @NonNull
    public static CameraFilter a(CameraConfig _this) {
        return (CameraFilter) _this.retrieveOption(CameraConfig.OPTION_CAMERA_FILTER, CameraFilters.ANY);
    }
}
