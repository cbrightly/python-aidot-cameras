package com.leedarson.serviceimpl.http.manager;

import com.trello.rxlifecycle3.b;
import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class h implements g {
    public final /* synthetic */ b a;
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;
    public final /* synthetic */ String d;

    public /* synthetic */ h(b bVar, String str, String str2, String str3) {
        this.a = bVar;
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    public final void subscribe(f fVar) {
        b0.G(this.a, this.b, this.c, this.d, fVar);
    }
}
