package com.leedarson.serviceimpl.blec075.strategy;

import com.clj.fastble.data.BleDevice;
import io.reactivex.functions.e;

/* compiled from: lambda */
public final /* synthetic */ class f implements e {
    public final /* synthetic */ j c;

    public /* synthetic */ f(j jVar) {
        this.c = jVar;
    }

    public final void accept(Object obj) {
        this.c.k((BleDevice) obj);
    }
}
