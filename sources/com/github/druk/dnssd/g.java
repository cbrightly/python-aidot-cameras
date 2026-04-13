package com.github.druk.dnssd;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    public final /* synthetic */ RegisterListener c;
    public final /* synthetic */ DNSSDRegistration[] d;
    public final /* synthetic */ int f;
    public final /* synthetic */ String q;
    public final /* synthetic */ String x;
    public final /* synthetic */ String y;

    public /* synthetic */ g(RegisterListener registerListener, DNSSDRegistration[] dNSSDRegistrationArr, int i, String str, String str2, String str3) {
        this.c = registerListener;
        this.d = dNSSDRegistrationArr;
        this.f = i;
        this.q = str;
        this.x = str2;
        this.y = str3;
    }

    public final void run() {
        this.c.serviceRegistered(this.d[0], this.f, this.q, this.x, this.y);
    }
}
