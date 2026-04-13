package androidx.camera.core;

import androidx.camera.core.CameraXConfig;

/* compiled from: lambda */
public final /* synthetic */ class i implements CameraXConfig.Provider {
    public final /* synthetic */ CameraXConfig a;

    public /* synthetic */ i(CameraXConfig cameraXConfig) {
        this.a = cameraXConfig;
    }

    public final CameraXConfig getCameraXConfig() {
        CameraXConfig cameraXConfig = this.a;
        CameraX.lambda$initialize$0(cameraXConfig);
        return cameraXConfig;
    }
}
