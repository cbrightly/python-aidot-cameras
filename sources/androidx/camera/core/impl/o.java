package androidx.camera.core.impl;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    public final /* synthetic */ LiveDataObservable c;
    public final /* synthetic */ CallbackToFutureAdapter.Completer d;

    public /* synthetic */ o(LiveDataObservable liveDataObservable, CallbackToFutureAdapter.Completer completer) {
        this.c = liveDataObservable;
        this.d = completer;
    }

    public final void run() {
        this.c.b(this.d);
    }
}
