package com.leedarson.serviceimpl.tcp;

import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class a implements e {
    public final /* synthetic */ TcpServiceImpl c;

    public /* synthetic */ a(TcpServiceImpl tcpServiceImpl) {
        this.c = tcpServiceImpl;
    }

    public final void accept(Object obj) {
        this.c.C((Long) obj);
    }
}
