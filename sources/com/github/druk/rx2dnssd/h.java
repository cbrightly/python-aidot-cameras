package com.github.druk.rx2dnssd;

import com.github.druk.dnssd.DNSSDService;
import com.github.druk.rx2dnssd.Rx2DnssdCommon;
import io.reactivex.f;

/* compiled from: lambda */
public final /* synthetic */ class h implements Rx2DnssdCommon.DNSSDServiceCreator {
    public final /* synthetic */ Rx2DnssdCommon a;
    public final /* synthetic */ String b;
    public final /* synthetic */ String c;

    public /* synthetic */ h(Rx2DnssdCommon rx2DnssdCommon, String str, String str2) {
        this.a = rx2DnssdCommon;
        this.b = str;
        this.c = str2;
    }

    public final DNSSDService getService(f fVar) {
        return this.a.a(this.b, this.c, fVar);
    }
}
