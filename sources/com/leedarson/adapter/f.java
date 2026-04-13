package com.leedarson.adapter;

import com.leedarson.adapter.SDCardListAdapter;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class f implements Callable {
    public final /* synthetic */ SDCardListAdapter.b c;
    public final /* synthetic */ long d;

    public /* synthetic */ f(SDCardListAdapter.b bVar, long j) {
        this.c = bVar;
        this.d = j;
    }

    public final Object call() {
        return this.c.b(this.d);
    }
}
