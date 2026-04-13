package com.leedarson.serviceimpl.ipcservice;

import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import io.reactivex.f;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class b implements e {
    public final /* synthetic */ f c;
    public final /* synthetic */ String d;

    public /* synthetic */ b(f fVar, String str) {
        this.c = fVar;
        this.d = str;
    }

    public final void accept(Object obj) {
        IpcServiceImpl.x(this.c, this.d, (LDSBaseBean) obj);
    }
}
