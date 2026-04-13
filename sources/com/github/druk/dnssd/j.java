package com.github.druk.dnssd;

/* compiled from: lambda */
public final /* synthetic */ class j implements Runnable {
    public final /* synthetic */ DomainListener c;
    public final /* synthetic */ DNSSDService[] d;
    public final /* synthetic */ int f;

    public /* synthetic */ j(DomainListener domainListener, DNSSDService[] dNSSDServiceArr, int i) {
        this.c = domainListener;
        this.d = dNSSDServiceArr;
        this.f = i;
    }

    public final void run() {
        this.c.operationFailed(this.d[0], this.f);
    }
}
