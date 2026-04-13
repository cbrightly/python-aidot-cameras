package com.leedarson.serviceimpl.business;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class a implements e {
    public final /* synthetic */ BleBusinessClient c;

    public /* synthetic */ a(BleBusinessClient bleBusinessClient) {
        this.c = bleBusinessClient;
    }

    public final void accept(Object obj) {
        this.c.c((Throwable) obj);
    }
}
