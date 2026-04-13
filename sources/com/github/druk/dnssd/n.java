package com.github.druk.dnssd;

/* compiled from: lambda */
public final /* synthetic */ class n implements Runnable {
    public final /* synthetic */ DNSSDService[] c;

    public /* synthetic */ n(DNSSDService[] dNSSDServiceArr) {
        this.c = dNSSDServiceArr;
    }

    public final void run() {
        this.c[0].stop();
    }
}
