package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSDService;
import com.github.druk.rx2dnssd.BonjourService;
import com.github.druk.rx2dnssd.Rx2DnssdCommon;
import io.reactivex.f;

/* compiled from: lambda */
public final /* synthetic */ class v implements Rx2DnssdCommon.DNSSDServiceCreator {
    public final /* synthetic */ Rx2DnssdCommon a;
    public final /* synthetic */ BonjourService b;
    public final /* synthetic */ BonjourService.Builder c;

    public /* synthetic */ v(Rx2DnssdCommon rx2DnssdCommon, BonjourService bonjourService, BonjourService.Builder builder) {
        this.a = rx2DnssdCommon;
        this.b = bonjourService;
        this.c = builder;
    }

    public final DNSSDService getService(f fVar) {
        return this.a.f(this.b, this.c, fVar);
    }
}
