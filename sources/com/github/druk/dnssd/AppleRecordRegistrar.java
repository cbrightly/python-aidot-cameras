package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleRecordRegistrar extends AppleService implements DNSSDRecordRegistrar {
    /* access modifiers changed from: protected */
    public native int CreateConnection();

    /* access modifiers changed from: protected */
    public native int RegisterRecord(int i, int i2, String str, int i3, int i4, byte[] bArr, int i5, AppleDNSRecord appleDNSRecord);

    public AppleRecordRegistrar(RegisterRecordListener listener) {
        super(listener);
        ThrowOnErr(CreateConnection());
        if (!AppleDNSSD.hasAutoCallbacks) {
            new Thread(this).start();
        }
    }

    public DNSRecord registerRecord(int flags, int ifIndex, String fullname, int rrtype, int rrclass, byte[] rdata, int ttl) {
        AppleDNSRecord newRecord = new AppleDNSRecord(this);
        ThrowOnErr(RegisterRecord(flags, ifIndex, fullname, rrtype, rrclass, rdata, ttl, newRecord));
        return newRecord;
    }
}
