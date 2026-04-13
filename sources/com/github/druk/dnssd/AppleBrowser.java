package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleBrowser extends AppleService {
    /* access modifiers changed from: protected */
    public native int CreateBrowser(int i, int i2, String str, String str2);

    public AppleBrowser(int flags, int ifIndex, String regType, String domain, InternalBrowseListener client) {
        super(client);
        ThrowOnErr(CreateBrowser(flags, ifIndex, regType, domain));
        if (!AppleDNSSD.hasAutoCallbacks) {
            new Thread(this).start();
        }
    }
}
