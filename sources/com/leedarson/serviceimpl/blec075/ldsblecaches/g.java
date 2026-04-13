package com.leedarson.serviceimpl.blec075.ldsblecaches;

import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceimpl.blec075.reports.BleConnectStepBean;
import com.leedarson.serviceimpl.blec075.reports.a;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class g implements e {
    public final /* synthetic */ i c;
    public final /* synthetic */ LDSBleCacheItemBean d;
    public final /* synthetic */ a f;
    public final /* synthetic */ BleConnectStepBean q;

    public /* synthetic */ g(i iVar, LDSBleCacheItemBean lDSBleCacheItemBean, a aVar, BleConnectStepBean bleConnectStepBean) {
        this.c = iVar;
        this.d = lDSBleCacheItemBean;
        this.f = aVar;
        this.q = bleConnectStepBean;
    }

    public final void accept(Object obj) {
        this.c.x(this.d, this.f, this.q, (BleDevice) obj);
    }
}
