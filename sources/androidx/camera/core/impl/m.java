package androidx.camera.core.impl;

import androidx.camera.core.impl.LiveDataObservable;

/* compiled from: lambda */
public final /* synthetic */ class m implements Runnable {
    public final /* synthetic */ LiveDataObservable.LiveDataObserverAdapter c;
    public final /* synthetic */ LiveDataObservable.Result d;

    public /* synthetic */ m(LiveDataObservable.LiveDataObserverAdapter liveDataObserverAdapter, LiveDataObservable.Result result) {
        this.c = liveDataObserverAdapter;
        this.d = result;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
