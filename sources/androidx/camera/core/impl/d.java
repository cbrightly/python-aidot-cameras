package androidx.camera.core.impl;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ CameraRepository c;
    public final /* synthetic */ CameraInternal d;

    public /* synthetic */ d(CameraRepository cameraRepository, CameraInternal cameraInternal) {
        this.c = cameraRepository;
        this.d = cameraInternal;
    }

    public final void run() {
        this.c.b(this.d);
    }
}
