package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleRegistration extends AppleService implements DNSSDRegistration {
    /* access modifiers changed from: protected */
    public native int AddRecord(int i, int i2, byte[] bArr, int i3, AppleDNSRecord appleDNSRecord);

    /* access modifiers changed from: protected */
    public native int BeginRegister(int i, int i2, String str, String str2, String str3, String str4, int i3, byte[] bArr);

    public AppleRegistration(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, byte[] txtRecord, InternalRegisterListener client) {
        super(client);
        ThrowOnErr(BeginRegister(ifIndex, flags, serviceName, regType, domain, host, port, txtRecord));
        if (!AppleDNSSD.hasAutoCallbacks) {
            new Thread(this).start();
        }
    }

    public DNSRecord addRecord(int flags, int rrType, byte[] rData, int ttl) {
        AppleDNSRecord newRecord = new AppleDNSRecord(this);
        ThrowOnErr(AddRecord(flags, rrType, rData, ttl, newRecord));
        return newRecord;
    }

    public DNSRecord getTXTRecord() {
        return new AppleDNSRecord(this);
    }
}
