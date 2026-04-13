package com.github.druk.dnssd;

import com.github.druk.dnssd.DNSSD;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    public final /* synthetic */ int a1;
    public final /* synthetic */ QueryListener c;
    public final /* synthetic */ DNSSDService[] d;
    public final /* synthetic */ int f;
    public final /* synthetic */ byte[] p0;
    public final /* synthetic */ boolean p1;
    public final /* synthetic */ int q;
    public final /* synthetic */ String x;
    public final /* synthetic */ int y;
    public final /* synthetic */ int z;

    public /* synthetic */ i(QueryListener queryListener, DNSSDService[] dNSSDServiceArr, int i, int i2, String str, int i3, int i4, byte[] bArr, int i5, boolean z2) {
        this.c = queryListener;
        this.d = dNSSDServiceArr;
        this.f = i;
        this.q = i2;
        this.x = str;
        this.y = i3;
        this.z = i4;
        this.p0 = bArr;
        this.a1 = i5;
        this.p1 = z2;
    }

    public final void run() {
        DNSSD.AnonymousClass4.lambda$queryAnswered$0(this.c, this.d, this.f, this.q, this.x, this.y, this.z, this.p0, this.a1, this.p1);
    }
}
