package com.leedarson.newui.cloud_play_back.repos;

import android.content.Context;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class n implements e {
    public final /* synthetic */ c0 c;
    public final /* synthetic */ Context d;
    public final /* synthetic */ String f;
    public final /* synthetic */ String q;

    public /* synthetic */ n(c0 c0Var, Context context, String str, String str2) {
        this.c = c0Var;
        this.d = context;
        this.f = str;
        this.q = str2;
    }

    public final void accept(Object obj) {
        this.c.L(this.d, this.f, this.q, (Boolean) obj);
    }
}
