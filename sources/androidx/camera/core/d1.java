package androidx.camera.core;

/* compiled from: lambda */
public final /* synthetic */ class d1 implements Runnable {
    public final /* synthetic */ ProcessingSurface c;

    public /* synthetic */ d1(ProcessingSurface processingSurface) {
        this.c = processingSurface;
    }

    public final void run() {
        this.c.release();
    }
}
