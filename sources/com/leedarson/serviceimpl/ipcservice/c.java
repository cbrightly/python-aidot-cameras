package com.leedarson.serviceimpl.ipcservice;

import io.reactivex.f;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class c implements e {
    public final /* synthetic */ f c;
    public final /* synthetic */ String d;

    public /* synthetic */ c(f fVar, String str) {
        this.c = fVar;
        this.d = str;
    }

    public final void accept(Object obj) {
        IpcServiceImpl.y(this.c, this.d, (Throwable) obj);
    }
}
