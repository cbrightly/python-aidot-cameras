package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleDomainEnum extends AppleService {
    /* access modifiers changed from: protected */
    public native int BeginEnum(int i, int i2);

    public AppleDomainEnum(int flags, int ifIndex, InternalDomainListener client) {
        super(client);
        ThrowOnErr(BeginEnum(flags, ifIndex));
        if (!AppleDNSSD.hasAutoCallbacks) {
            new Thread(this).start();
        }
    }
}
