package androidx.camera.view;

import androidx.camera.core.CameraSelector;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ CameraController c;
    public final /* synthetic */ CameraSelector d;

    public /* synthetic */ d(CameraController cameraController, CameraSelector cameraSelector) {
        this.c = cameraController;
        this.d = cameraSelector;
    }

    public final void run() {
        this.c.b(this.d);
    }
}
