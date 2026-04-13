package com.leedarson.serviceimpl.blec075.ldsblecaches;

import com.leedarson.serviceimpl.blec075.reports.BleConnectStepBean;
import com.leedarson.serviceimpl.blec075.reports.a;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class f implements e {
    public final /* synthetic */ i c;
    public final /* synthetic */ BleConnectStepBean d;
    public final /* synthetic */ a f;
    public final /* synthetic */ h q;

    public /* synthetic */ f(i iVar, BleConnectStepBean bleConnectStepBean, a aVar, h hVar) {
        this.c = iVar;
        this.d = bleConnectStepBean;
        this.f = aVar;
        this.q = hVar;
    }

    public final void accept(Object obj) {
        this.c.v(this.d, this.f, this.q, (Throwable) obj);
    }
}
