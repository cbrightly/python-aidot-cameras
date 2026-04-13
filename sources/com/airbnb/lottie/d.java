package com.airbnb.lottie;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: lambda */
public final /* synthetic */ class d implements g0 {
    public final /* synthetic */ String a;
    public final /* synthetic */ AtomicBoolean b;

    public /* synthetic */ d(String str, AtomicBoolean atomicBoolean) {
        this.a = str;
        this.b = atomicBoolean;
    }

    public final void onResult(Object obj) {
        d0.v(this.a, this.b, (Throwable) obj);
    }
}
