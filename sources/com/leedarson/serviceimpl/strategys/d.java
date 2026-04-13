package com.leedarson.serviceimpl.strategys;

import android.content.Context;
import io.reactivex.functions.e;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class d implements e {
    public final /* synthetic */ e c;
    public final /* synthetic */ List d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;
    public final /* synthetic */ Context x;

    public /* synthetic */ d(e eVar, List list, String str, String str2, Context context) {
        this.c = eVar;
        this.d = list;
        this.f = str;
        this.q = str2;
        this.x = context;
    }

    public final void accept(Object obj) {
        this.c.r(this.d, this.f, this.q, this.x, (Throwable) obj);
    }
}
