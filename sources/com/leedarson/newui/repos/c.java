package com.leedarson.newui.repos;

import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class c implements g {
    public final /* synthetic */ n a;
    public final /* synthetic */ String b;

    public /* synthetic */ c(n nVar, String str) {
        this.a = nVar;
        this.b = str;
    }

    public final void subscribe(f fVar) {
        this.a.f(this.b, fVar);
    }
}
