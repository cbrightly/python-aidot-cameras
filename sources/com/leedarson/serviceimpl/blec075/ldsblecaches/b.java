package com.leedarson.serviceimpl.blec075.ldsblecaches;

import com.clj.fastble.data.BleDevice;
import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class b implements f {
    public final /* synthetic */ int c;
    public final /* synthetic */ h d;

    public /* synthetic */ b(int i, h hVar) {
        this.c = i;
        this.d = hVar;
    }

    public final Object apply(Object obj) {
        return i.r(this.c, this.d, (BleDevice) obj);
    }
}
