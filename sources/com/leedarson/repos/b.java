package com.leedarson.repos;

import io.reactivex.m;
import io.reactivex.n;

/* compiled from: lambda */
public final /* synthetic */ class b implements n {
    public final /* synthetic */ c a;
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;

    public /* synthetic */ b(c cVar, String str, String str2) {
        this.a = cVar;
        this.b = str;
        this.c = str2;
    }

    public final void subscribe(m mVar) {
        this.a.e(this.b, this.c, mVar);
    }
}
