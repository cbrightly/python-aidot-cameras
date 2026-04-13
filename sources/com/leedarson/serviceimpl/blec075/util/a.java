package com.leedarson.serviceimpl.blec075.util;

import io.reactivex.functions.e;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: lambda */
public final /* synthetic */ class a implements e {
    public final /* synthetic */ e c;
    public final /* synthetic */ AtomicInteger d;
    public final /* synthetic */ String f;

    public /* synthetic */ a(e eVar, AtomicInteger atomicInteger, String str) {
        this.c = eVar;
        this.d = atomicInteger;
        this.f = str;
    }

    public final void accept(Object obj) {
        this.c.i(this.d, this.f, (Boolean) obj);
    }
}
