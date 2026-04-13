package com.leedarson.serviceimpl.blec075.onekeyreset;

import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceimpl.blec075.util.c;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class a implements e {
    public final /* synthetic */ h c;
    public final /* synthetic */ int d;
    public final /* synthetic */ c f;

    public /* synthetic */ a(h hVar, int i, c cVar) {
        this.c = hVar;
        this.d = i;
        this.f = cVar;
    }

    public final void accept(Object obj) {
        this.c.f(this.d, this.f, (BleDevice) obj);
    }
}
