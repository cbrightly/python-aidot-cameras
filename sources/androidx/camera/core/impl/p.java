package androidx.camera.core.impl;

import androidx.concurrent.futures.CallbackToFutureAdapter;

/* compiled from: lambda */
public final /* synthetic */ class p implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ LiveDataObservable a;

    public /* synthetic */ p(LiveDataObservable liveDataObservable) {
        this.a = liveDataObservable;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        return this.a.c(completer);
    }
}
