package androidx.camera.core.impl;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ DeferrableSurface c;
    public final /* synthetic */ String d;

    public /* synthetic */ f(DeferrableSurface deferrableSurface, String str) {
        this.c = deferrableSurface;
        this.d = str;
    }

    public final void run() {
        this.c.b(this.d);
    }
}
