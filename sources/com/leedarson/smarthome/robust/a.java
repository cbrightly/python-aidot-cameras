package com.leedarson.smarthome.robust;

import io.reactivex.f;
import io.reactivex.g;

/* compiled from: lambda */
public final /* synthetic */ class a implements g {
    public final /* synthetic */ e a;
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;

    public /* synthetic */ a(e eVar, String str, String str2) {
        this.a = eVar;
        this.b = str;
        this.c = str2;
    }

    public final void subscribe(f fVar) {
        this.a.g(this.b, this.c, fVar);
    }
}
