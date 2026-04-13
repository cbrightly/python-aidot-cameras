package com.leedarson.serviceimpl.blec075.ldsblecaches;

import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceimpl.blec075.reports.BleConnectStepBean;
import com.leedarson.serviceimpl.blec075.reports.a;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class c implements e {
    public final /* synthetic */ i c;
    public final /* synthetic */ h d;
    public final /* synthetic */ BleConnectStepBean f;
    public final /* synthetic */ a q;
    public final /* synthetic */ int x;

    public /* synthetic */ c(i iVar, h hVar, BleConnectStepBean bleConnectStepBean, a aVar, int i) {
        this.c = iVar;
        this.d = hVar;
        this.f = bleConnectStepBean;
        this.q = aVar;
        this.x = i;
    }

    public final void accept(Object obj) {
        this.c.t(this.d, this.f, this.q, this.x, (BleDevice) obj);
    }
}
