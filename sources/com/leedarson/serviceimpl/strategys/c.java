package com.leedarson.serviceimpl.strategys;

import com.leedarson.serviceimpl.strategys.e;
import io.reactivex.functions.e;
import meshsdk.model.NetworkingDevice;
import meshsdk.model.json.AddDevicesBean;

/* compiled from: lambda */
public final /* synthetic */ class c implements e {
    public final /* synthetic */ e.b c;
    public final /* synthetic */ String d;
    public final /* synthetic */ NetworkingDevice f;

    public /* synthetic */ c(e.b bVar, String str, NetworkingDevice networkingDevice) {
        this.c = bVar;
        this.d = str;
        this.f = networkingDevice;
    }

    public final void accept(Object obj) {
        this.c.c(this.d, this.f, (AddDevicesBean) obj);
    }
}
