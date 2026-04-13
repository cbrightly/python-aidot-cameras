package com.leedarson.serviceimpl.business;

import android.app.Activity;
import android.net.Uri;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class p implements e {
    public final /* synthetic */ Activity c;
    public final /* synthetic */ String d;
    public final /* synthetic */ int f;
    public final /* synthetic */ int q;
    public final /* synthetic */ int x;
    public final /* synthetic */ int y;

    public /* synthetic */ p(Activity activity, String str, int i, int i2, int i3, int i4) {
        this.c = activity;
        this.d = str;
        this.f = i;
        this.q = i2;
        this.x = i3;
        this.y = i4;
    }

    public final void accept(Object obj) {
        BusinessServiceImpl.lambda$saveScreenShot$3(this.c, this.d, this.f, this.q, this.x, this.y, (Uri) obj);
    }
}
