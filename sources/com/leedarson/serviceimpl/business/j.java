package com.leedarson.serviceimpl.business;

import android.app.Activity;
import io.reactivex.m;
import io.reactivex.n;

/* compiled from: lambda */
public final /* synthetic */ class j implements n {
    public final /* synthetic */ Activity a;
    public final /* synthetic */ int b;
    public final /* synthetic */ int c;
    public final /* synthetic */ int d;
    public final /* synthetic */ int e;

    public /* synthetic */ j(Activity activity, int i, int i2, int i3, int i4) {
        this.a = activity;
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = i4;
    }

    public final void subscribe(m mVar) {
        BusinessServiceImpl.lambda$saveScreenShot$2(this.a, this.b, this.c, this.d, this.e, mVar);
    }
}
