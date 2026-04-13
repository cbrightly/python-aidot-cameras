package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleQuery extends AppleService {
    /* access modifiers changed from: protected */
    public native int CreateQuery(int i, int i2, String str, int i3, int i4);

    public AppleQuery(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, InternalQueryListener client) {
        super(client);
        ThrowOnErr(CreateQuery(flags, ifIndex, serviceName, rrtype, rrclass));
        if (!AppleDNSSD.hasAutoCallbacks) {
            new Thread(this).start();
        }
    }
}
