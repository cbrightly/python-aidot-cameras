package com.leedarson.serviceimpl;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class c implements e {
    public final /* synthetic */ BodyFatScaleServiceImpl c;
    public final /* synthetic */ String d;
    public final /* synthetic */ String f;

    public /* synthetic */ c(BodyFatScaleServiceImpl bodyFatScaleServiceImpl, String str, String str2) {
        this.c = bodyFatScaleServiceImpl;
        this.d = str;
        this.f = str2;
    }

    public final void accept(Object obj) {
        this.c.u(this.d, this.f, (Integer) obj);
    }
}
