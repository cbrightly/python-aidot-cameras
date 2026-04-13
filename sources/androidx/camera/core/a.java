package androidx.camera.core;

import android.os.HandlerThread;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ HandlerThread c;

    public /* synthetic */ a(HandlerThread handlerThread) {
        this.c = handlerThread;
    }

    public final void run() {
        this.c.quitSafely();
    }
}
