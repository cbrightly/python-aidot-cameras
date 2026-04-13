package com.leedarson.serviceimpl.business;

import io.reactivex.m;
import io.reactivex.n;

/* compiled from: lambda */
public final /* synthetic */ class c implements n {
    public final /* synthetic */ BleBusinessClient a;
    public final /* synthetic */ String b;
    public final /* synthetic */ byte c;
    public final /* synthetic */ byte d;
    public final /* synthetic */ byte e;
    public final /* synthetic */ byte f;
    public final /* synthetic */ byte g;
    public final /* synthetic */ String h;

    public /* synthetic */ c(BleBusinessClient bleBusinessClient, String str, byte b2, byte b3, byte b4, byte b5, byte b6, String str2) {
        this.a = bleBusinessClient;
        this.b = str;
        this.c = b2;
        this.d = b3;
        this.e = b4;
        this.f = b5;
        this.g = b6;
        this.h = str2;
    }

    public final void subscribe(m mVar) {
        this.a.e(this.b, this.c, this.d, this.e, this.f, this.g, this.h, mVar);
    }
}
