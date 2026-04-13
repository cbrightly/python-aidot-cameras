package com.github.druk.dnssd;

import com.github.druk.dnssd.DNSSD;

/* compiled from: lambda */
public final /* synthetic */ class h implements Runnable {
    public final /* synthetic */ QueryListener c;
    public final /* synthetic */ DNSSDService[] d;
    public final /* synthetic */ int f;

    public /* synthetic */ h(QueryListener queryListener, DNSSDService[] dNSSDServiceArr, int i) {
        this.c = queryListener;
        this.d = dNSSDServiceArr;
        this.f = i;
    }

    public final void run() {
        DNSSD.AnonymousClass4.lambda$operationFailed$1(this.c, this.d, this.f);
    }
}
