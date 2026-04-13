package com.leedarson.serviceimpl.base;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class a implements e {
    public final /* synthetic */ c c;
    public final /* synthetic */ long d;

    public /* synthetic */ a(c cVar, long j) {
        this.c = cVar;
        this.d = j;
    }

    public final void accept(Object obj) {
        this.c.o(this.d, (String) obj);
    }
}
