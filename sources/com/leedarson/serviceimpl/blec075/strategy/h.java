package com.leedarson.serviceimpl.blec075.strategy;

import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class h implements f {
    public final /* synthetic */ l c;
    public final /* synthetic */ i d;

    public /* synthetic */ h(l lVar, i iVar) {
        this.c = lVar;
        this.d = iVar;
    }

    public final Object apply(Object obj) {
        return this.c.d(this.d, (Long) obj);
    }
}
