package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSDService;
import com.github.druk.rx2dnssd.Rx2DnssdCommon;
import io.reactivex.f;

/* compiled from: lambda */
public final /* synthetic */ class d implements Rx2DnssdCommon.DNSSDServiceCreator {
    public final /* synthetic */ Rx2DnssdCommon a;
    public final /* synthetic */ BonjourService b;
    public final /* synthetic */ int c;

    public /* synthetic */ d(Rx2DnssdCommon rx2DnssdCommon, BonjourService bonjourService, int i) {
        this.a = rx2DnssdCommon;
        this.b = bonjourService;
        this.c = i;
    }

    public final DNSSDService getService(f fVar) {
        return this.a.n(this.b, this.c, fVar);
    }
}
