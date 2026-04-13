package com.github.druk.dnssd;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    public final /* synthetic */ BrowseListener c;
    public final /* synthetic */ InternalDNSSDService[] d;
    public final /* synthetic */ int f;
    public final /* synthetic */ int q;
    public final /* synthetic */ String x;
    public final /* synthetic */ String y;
    public final /* synthetic */ String z;

    public /* synthetic */ a(BrowseListener browseListener, InternalDNSSDService[] internalDNSSDServiceArr, int i, int i2, String str, String str2, String str3) {
        this.c = browseListener;
        this.d = internalDNSSDServiceArr;
        this.f = i;
        this.q = i2;
        this.x = str;
        this.y = str2;
        this.z = str3;
    }

    public final void run() {
        this.c.serviceFound(this.d[0], this.f, this.q, this.x, this.y, this.z);
    }
}
