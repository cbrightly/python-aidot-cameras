package com.leedarson.serviceimpl.ctrl;

import chip.devicecontroller.DiscoveredDevice;
import com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo;
import com.leedarson.serviceimpl.bean.AddDeviceBean;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class d implements e {
    public final /* synthetic */ CHIPDeviceInfo c;
    public final /* synthetic */ p d;
    public final /* synthetic */ AddDeviceBean f;

    public /* synthetic */ d(CHIPDeviceInfo cHIPDeviceInfo, p pVar, AddDeviceBean addDeviceBean) {
        this.c = cHIPDeviceInfo;
        this.d = pVar;
        this.f = addDeviceBean;
    }

    public final void accept(Object obj) {
        p.q(this.c, this.d, this.f, (DiscoveredDevice) obj);
    }
}
