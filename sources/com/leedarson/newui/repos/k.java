package com.leedarson.newui.repos;

import com.ldf.calendar.model.a;
import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class k implements g {
    public final /* synthetic */ p a;
    public final /* synthetic */ a b;
    public final /* synthetic */ String c;
    public final /* synthetic */ String d;

    public /* synthetic */ k(p pVar, a aVar, String str, String str2) {
        this.a = pVar;
        this.b = aVar;
        this.c = str;
        this.d = str2;
    }

    public final void subscribe(f fVar) {
        this.a.f(this.b, this.c, this.d, fVar);
    }
}
