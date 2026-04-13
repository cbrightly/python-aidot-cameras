package androidx.camera.core;

import android.view.Surface;

/* compiled from: lambda */
public final /* synthetic */ class y0 implements Runnable {
    public final /* synthetic */ Surface c;

    public /* synthetic */ y0(Surface surface) {
        this.c = surface;
    }

    public final void run() {
        this.c.release();
    }
}
