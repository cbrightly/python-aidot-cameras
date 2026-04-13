package com.github.druk.dnssd;

import com.github.druk.dnssd.DNSSD;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    public final /* synthetic */ ResolveListener c;
    public final /* synthetic */ DNSSDService[] d;
    public final /* synthetic */ int f;
    public final /* synthetic */ Map p0;
    public final /* synthetic */ int q;
    public final /* synthetic */ String x;
    public final /* synthetic */ String y;
    public final /* synthetic */ int z;

    public /* synthetic */ d(ResolveListener resolveListener, DNSSDService[] dNSSDServiceArr, int i, int i2, String str, String str2, int i3, Map map) {
        this.c = resolveListener;
        this.d = dNSSDServiceArr;
        this.f = i;
        this.q = i2;
        this.x = str;
        this.y = str2;
        this.z = i3;
        this.p0 = map;
    }

    public final void run() {
        DNSSD.AnonymousClass2.lambda$serviceResolved$0(this.c, this.d, this.f, this.q, this.x, this.y, this.z, this.p0);
    }
}
