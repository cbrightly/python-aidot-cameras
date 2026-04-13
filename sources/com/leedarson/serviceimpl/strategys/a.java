package com.leedarson.serviceimpl.strategys;

import android.content.Context;
import io.reactivex.functions.e;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class a implements e {
    public final /* synthetic */ e c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;
    public final /* synthetic */ Context q;

    public /* synthetic */ a(e eVar, String str, String str2, Context context) {
        this.c = eVar;
        this.d = str;
        this.f = str2;
        this.q = context;
    }

    public final void accept(Object obj) {
        this.c.p(this.d, this.f, this.q, (List) obj);
    }
}
