package com.leedarson.serviceimpl.business;

import com.leedarson.serviceimpl.business.bean.TRVCommandBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ BleBusinessClient c;
    public final /* synthetic */ String d;
    public final /* synthetic */ TRVCommandBean f;

    public /* synthetic */ b(BleBusinessClient bleBusinessClient, String str, TRVCommandBean tRVCommandBean) {
        this.c = bleBusinessClient;
        this.d = str;
        this.f = tRVCommandBean;
    }

    public final void accept(Object obj) {
        this.c.b(this.d, this.f, (Boolean) obj);
    }
}
