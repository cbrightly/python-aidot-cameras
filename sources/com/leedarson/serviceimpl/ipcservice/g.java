package com.leedarson.serviceimpl.ipcservice;

import io.reactivex.f;

/* compiled from: lambda */
public final /* synthetic */ class g implements io.reactivex.g {
    public final /* synthetic */ IpcServiceImpl a;
    public final /* synthetic */ String b;

    public /* synthetic */ g(IpcServiceImpl ipcServiceImpl, String str) {
        this.a = ipcServiceImpl;
        this.b = str;
    }

    public final void subscribe(f fVar) {
        this.a.A(this.b, fVar);
    }
}
