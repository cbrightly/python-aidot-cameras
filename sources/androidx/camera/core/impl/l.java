package androidx.camera.core.impl;

import androidx.camera.core.impl.LiveDataObservable;

/* compiled from: lambda */
public final /* synthetic */ class l implements Runnable {
    public final /* synthetic */ LiveDataObservable c;
    public final /* synthetic */ LiveDataObservable.LiveDataObserverAdapter d;

    public /* synthetic */ l(LiveDataObservable liveDataObservable, LiveDataObservable.LiveDataObserverAdapter liveDataObserverAdapter) {
        this.c = liveDataObservable;
        this.d = liveDataObserverAdapter;
    }

    public final void run() {
        this.c.d(this.d);
    }
}
