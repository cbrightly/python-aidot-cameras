package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleResolver extends AppleService {
    /* access modifiers changed from: protected */
    public native int CreateResolver(int i, int i2, String str, String str2, String str3);

    public AppleResolver(int flags, int ifIndex, String serviceName, String regType, String domain, InternalResolveListener client) {
        super(client);
        ThrowOnErr(CreateResolver(flags, ifIndex, serviceName, regType, domain));
        if (!AppleDNSSD.hasAutoCallbacks) {
            new Thread(this).start();
        }
    }
}
