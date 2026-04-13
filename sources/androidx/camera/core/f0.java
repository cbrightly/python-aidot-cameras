package androidx.camera.core;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class f0 implements Runnable {
    public final /* synthetic */ CallbackToFutureAdapter.Completer c;

    public /* synthetic */ f0(CallbackToFutureAdapter.Completer completer) {
        this.c = completer;
    }

    public final void run() {
        this.c.set(null);
    }
}
