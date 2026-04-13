package com.leedarson.newui.repos;

import io.reactivex.f;
import io.reactivex.g;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class l implements g {
    public final /* synthetic */ p a;
    public final /* synthetic */ List b;
    public final /* synthetic */ String c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String e;

    public /* synthetic */ l(p pVar, List list, String str, String str2, String str3) {
        this.a = pVar;
        this.b = list;
        this.c = str;
        this.d = str2;
        this.e = str3;
    }

    public final void subscribe(f fVar) {
        this.a.d(this.b, this.c, this.d, this.e, fVar);
    }
}
