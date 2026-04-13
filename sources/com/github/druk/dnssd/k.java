package com.github.druk.dnssd;

/* compiled from: lambda */
public final /* synthetic */ class k implements Runnable {
    public final /* synthetic */ DomainListener c;
    public final /* synthetic */ DNSSDService[] d;
    public final /* synthetic */ int f;
    public final /* synthetic */ int q;
    public final /* synthetic */ String x;

    public /* synthetic */ k(DomainListener domainListener, DNSSDService[] dNSSDServiceArr, int i, int i2, String str) {
        this.c = domainListener;
        this.d = dNSSDServiceArr;
        this.f = i;
        this.q = i2;
        this.x = str;
    }

    public final void run() {
        this.c.domainLost(this.d[0], this.f, this.q, this.x);
    }
}
