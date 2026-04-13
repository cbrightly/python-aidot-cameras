package androidx.camera.core.impl;

import androidx.camera.core.impl.LiveDataObservable;

/* compiled from: lambda */
public final /* synthetic */ class n implements Runnable {
    public final /* synthetic */ LiveDataObservable c;
    public final /* synthetic */ LiveDataObservable.LiveDataObserverAdapter d;
    public final /* synthetic */ LiveDataObservable.LiveDataObserverAdapter f;

    public /* synthetic */ n(LiveDataObservable liveDataObservable, LiveDataObservable.LiveDataObserverAdapter liveDataObserverAdapter, LiveDataObservable.LiveDataObserverAdapter liveDataObserverAdapter2) {
        this.c = liveDataObservable;
        this.d = liveDataObserverAdapter;
        this.f = liveDataObserverAdapter2;
    }

    public final void run() {
        this.c.a(this.d, this.f);
    }
}
