package com.leedarson.serviceimpl.business;

import io.reactivex.m;
import io.reactivex.n;

/* compiled from: lambda */
public final /* synthetic */ class e implements n {
    public final /* synthetic */ BleBusinessClient a;
    public final /* synthetic */ String b;

    public /* synthetic */ e(BleBusinessClient bleBusinessClient, String str) {
        this.a = bleBusinessClient;
        this.b = str;
    }

    public final void subscribe(m mVar) {
        this.a.a(this.b, mVar);
    }
}
