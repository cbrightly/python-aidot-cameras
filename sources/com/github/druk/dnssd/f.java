package com.github.druk.dnssd;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    public final /* synthetic */ RegisterListener c;
    public final /* synthetic */ DNSSDRegistration[] d;
    public final /* synthetic */ int f;

    public /* synthetic */ f(RegisterListener registerListener, DNSSDRegistration[] dNSSDRegistrationArr, int i) {
        this.c = registerListener;
        this.d = dNSSDRegistrationArr;
        this.f = i;
    }

    public final void run() {
        this.c.operationFailed(this.d[0], this.f);
    }
}
