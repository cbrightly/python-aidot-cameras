package com.leedarson.newui.cloud_play_back.repos;

import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class v implements g {
    public final /* synthetic */ c0 a;
    public final /* synthetic */ String b;
    public final /* synthetic */ boolean c;
    public final /* synthetic */ String d;

    public /* synthetic */ v(c0 c0Var, String str, boolean z, String str2) {
        this.a = c0Var;
        this.b = str;
        this.c = z;
        this.d = str2;
    }

    public final void subscribe(f fVar) {
        this.a.C(this.b, this.c, this.d, fVar);
    }
}
