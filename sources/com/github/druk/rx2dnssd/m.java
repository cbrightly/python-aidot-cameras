package com.github.druk.rx2dnssd;

import io.reactivex.functions.f;

/* compiled from: lambda */
public final /* synthetic */ class m implements f {
    public final /* synthetic */ Rx2DnssdCommon c;

    public /* synthetic */ m(Rx2DnssdCommon rx2DnssdCommon) {
        this.c = rx2DnssdCommon;
    }

    public final Object apply(Object obj) {
        return this.c.i((BonjourService) obj);
    }
}
