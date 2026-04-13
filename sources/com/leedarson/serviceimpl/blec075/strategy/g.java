package com.leedarson.serviceimpl.blec075.strategy;

import com.clj.fastble.data.BleDevice;
import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class g implements f {
    public final /* synthetic */ l c;

    public /* synthetic */ g(l lVar) {
        this.c = lVar;
    }

    public final Object apply(Object obj) {
        return this.c.f((BleDevice) obj);
    }
}
