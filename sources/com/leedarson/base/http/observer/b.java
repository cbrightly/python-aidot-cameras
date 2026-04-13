package com.leedarson.base.http.observer;

import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class b implements f {
    public final /* synthetic */ k c;

    public /* synthetic */ b(k kVar) {
        this.c = kVar;
    }

    public final Object apply(Object obj) {
        return this.c.e((Throwable) obj);
    }
}
