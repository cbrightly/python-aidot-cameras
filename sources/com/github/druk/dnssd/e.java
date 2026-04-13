package com.github.druk.dnssd;

import com.github.druk.dnssd.DNSSD;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    public final /* synthetic */ ResolveListener c;
    public final /* synthetic */ DNSSDService[] d;
    public final /* synthetic */ int f;

    public /* synthetic */ e(ResolveListener resolveListener, DNSSDService[] dNSSDServiceArr, int i) {
        this.c = resolveListener;
        this.d = dNSSDServiceArr;
        this.f = i;
    }

    public final void run() {
        DNSSD.AnonymousClass2.lambda$operationFailed$1(this.c, this.d, this.f);
    }
}
