package com.leedarson.newui.repos;

import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class b implements g {
    public final /* synthetic */ n a;
    public final /* synthetic */ boolean b;
    public final /* synthetic */ String c;

    public /* synthetic */ b(n nVar, boolean z, String str) {
        this.a = nVar;
        this.b = z;
        this.c = str;
    }

    public final void subscribe(f fVar) {
        this.a.j(this.b, this.c, fVar);
    }
}
