package androidx.camera.core;

import androidx.camera.core.CameraXConfig;

/* compiled from: lambda */
public final /* synthetic */ class h implements CameraXConfig.Provider {
    public final /* synthetic */ CameraXConfig a;

    public /* synthetic */ h(CameraXConfig cameraXConfig) {
        this.a = cameraXConfig;
    }

    public final CameraXConfig getCameraXConfig() {
        CameraXConfig cameraXConfig = this.a;
        CameraX.lambda$configureInstance$1(cameraXConfig);
        return cameraXConfig;
    }
}
