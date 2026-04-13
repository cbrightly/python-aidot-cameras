package androidx.camera.view;

/* compiled from: lambda */
public final /* synthetic */ class b implements Runnable {
    public final /* synthetic */ CameraController c;
    public final /* synthetic */ int d;

    public /* synthetic */ b(CameraController cameraController, int i) {
        this.c = cameraController;
        this.d = i;
    }

    public final void run() {
        this.c.c(this.d);
    }
}
