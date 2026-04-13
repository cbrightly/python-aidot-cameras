package androidx.camera.core.impl;

import androidx.camera.core.impl.Observable;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    public final /* synthetic */ ConstantObservable c;
    public final /* synthetic */ Observable.Observer d;

    public /* synthetic */ e(ConstantObservable constantObservable, Observable.Observer observer) {
        this.c = constantObservable;
        this.d = observer;
    }

    public final void run() {
        this.c.a(this.d);
    }
}
