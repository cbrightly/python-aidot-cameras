package androidx.camera.view;

/* compiled from: lambda */
public final /* synthetic */ class t implements Runnable {
    public final /* synthetic */ SurfaceViewImplementation c;

    public /* synthetic */ t(SurfaceViewImplementation surfaceViewImplementation) {
        this.c = surfaceViewImplementation;
    }

    public final void run() {
        this.c.notifySurfaceNotInUse();
    }
}
