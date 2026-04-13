package com.leedarson.serviceimpl.ctrl;

import chip.devicecontroller.SubscriptionEstablishedCallback;
import com.leedarson.serviceimpl.listener.b;

/* compiled from: lambda */
public final /* synthetic */ class c implements SubscriptionEstablishedCallback {
    public final /* synthetic */ b a;
    public final /* synthetic */ long b;

    public /* synthetic */ c(b bVar, long j) {
        this.a = bVar;
        this.b = j;
    }

    public final void onSubscriptionEstablished() {
        o.h(this.a, this.b);
    }
}
