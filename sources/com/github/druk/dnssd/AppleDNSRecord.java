package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleDNSRecord implements DNSRecord {
    protected AppleService fOwner;
    protected long fRecord = 0;

    /* access modifiers changed from: protected */
    public native int Remove();

    /* access modifiers changed from: protected */
    public native int Update(int i, byte[] bArr, int i2);

    public AppleDNSRecord(AppleService owner) {
        this.fOwner = owner;
    }

    public void update(int flags, byte[] rData, int ttl) {
        ThrowOnErr(Update(flags, rData, ttl));
    }

    public void remove() {
        ThrowOnErr(Remove());
    }

    /* access modifiers changed from: protected */
    public void ThrowOnErr(int rc) {
        if (rc != 0) {
            throw new AppleDNSSDException(rc);
        }
    }
}
