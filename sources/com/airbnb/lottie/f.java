package com.airbnb.lottie;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class f implements Callable {
    public final /* synthetic */ WeakReference c;
    public final /* synthetic */ Context d;
    public final /* synthetic */ int f;
    public final /* synthetic */ String q;

    public /* synthetic */ f(WeakReference weakReference, Context context, int i, String str) {
        this.c = weakReference;
        this.d = context;
        this.f = i;
        this.q = str;
    }

    public final Object call() {
        return d0.A(this.c, this.d, this.f, this.q);
    }
}
