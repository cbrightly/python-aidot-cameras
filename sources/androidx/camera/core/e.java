package androidx.camera.core;

import androidx.arch.core.util.Function;

/* compiled from: lambda */
public final /* synthetic */ class e implements Function {
    public final /* synthetic */ CameraX a;

    public /* synthetic */ e(CameraX cameraX) {
        this.a = cameraX;
    }

    public final Object apply(Object obj) {
        CameraX cameraX = this.a;
        CameraX.lambda$getInstanceLocked$6(cameraX, (Void) obj);
        return cameraX;
    }
}
