package com.leedarson.base.http.observer;

import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class a implements f {
    public final /* synthetic */ j c;

    public /* synthetic */ a(j jVar) {
        this.c = jVar;
    }

    public final Object apply(Object obj) {
        return this.c.c((Throwable) obj);
    }
}
