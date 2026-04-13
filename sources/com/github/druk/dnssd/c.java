package com.github.druk.dnssd;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    public final /* synthetic */ BrowseListener c;
    public final /* synthetic */ InternalDNSSDService[] d;
    public final /* synthetic */ int f;

    public /* synthetic */ c(BrowseListener browseListener, InternalDNSSDService[] internalDNSSDServiceArr, int i) {
        this.c = browseListener;
        this.d = internalDNSSDServiceArr;
        this.f = i;
    }

    public final void run() {
        this.c.operationFailed(this.d[0], this.f);
    }
}
