package com.leedarson.serviceimpl.blec075.ldsblecaches;

import com.leedarson.serviceimpl.blec075.reports.BleConnectStepBean;
import com.leedarson.serviceimpl.blec075.reports.a;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class d implements e {
    public final /* synthetic */ i c;
    public final /* synthetic */ BleConnectStepBean d;
    public final /* synthetic */ a f;
    public final /* synthetic */ LDSBleCacheItemBean q;

    public /* synthetic */ d(i iVar, BleConnectStepBean bleConnectStepBean, a aVar, LDSBleCacheItemBean lDSBleCacheItemBean) {
        this.c = iVar;
        this.d = bleConnectStepBean;
        this.f = aVar;
        this.q = lDSBleCacheItemBean;
    }

    public final void accept(Object obj) {
        this.c.z(this.d, this.f, this.q, (Throwable) obj);
    }
}
