package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSDService;
import com.github.druk.rx2dnssd.Rx2DnssdCommon;
import io.reactivex.f;

/* compiled from: lambda */
public final /* synthetic */ class i implements Rx2DnssdCommon.DNSSDServiceCreator {
    public final /* synthetic */ Rx2DnssdCommon a;
    public final /* synthetic */ BonjourService b;

    public /* synthetic */ i(Rx2DnssdCommon rx2DnssdCommon, BonjourService bonjourService) {
        this.a = rx2DnssdCommon;
        this.b = bonjourService;
    }

    public final DNSSDService getService(f fVar) {
        return this.a.k(this.b, fVar);
    }
}
