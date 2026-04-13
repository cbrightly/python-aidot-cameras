package com.leedarson.newui.cloud_play_back.repos;

import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class b implements g {
    public final /* synthetic */ x a;
    public final /* synthetic */ com.trello.rxlifecycle3.b b;
    public final /* synthetic */ String c;
    public final /* synthetic */ String d;

    public /* synthetic */ b(x xVar, com.trello.rxlifecycle3.b bVar, String str, String str2) {
        this.a = xVar;
        this.b = bVar;
        this.c = str;
        this.d = str2;
    }

    public final void subscribe(f fVar) {
        this.a.c(this.b, this.c, this.d, fVar);
    }
}
