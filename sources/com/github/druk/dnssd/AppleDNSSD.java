package com.github.druk.dnssd;

/* compiled from: InternalDNSSD */
public class AppleDNSSD extends InternalDNSSD {
    public static boolean hasAutoCallbacks;

    protected static native int InitLibrary(int i);

    /* access modifiers changed from: protected */
    public native int ConstructName(String str, String str2, String str3, String[] strArr);

    /* access modifiers changed from: protected */
    public native int GetIfIndexForName(String str);

    /* access modifiers changed from: protected */
    public native byte[] GetNameForIfIndex(int i);

    /* access modifiers changed from: protected */
    public native int ReconfirmRecord(int i, int i2, String str, int i3, int i4, byte[] bArr);

    AppleDNSSD() {
    }

    /* access modifiers changed from: protected */
    public void _init(String lib) {
        System.loadLibrary(lib);
        int libInitResult = InitLibrary(2);
        if (libInitResult != 0) {
            throw new InternalError("cannot instantiate DNSSD: " + new AppleDNSSDException(libInitResult).getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public DNSSDService _makeBrowser(int flags, int ifIndex, String regType, String domain, InternalBrowseListener client) {
        return new AppleBrowser(flags, ifIndex, regType, domain, client);
    }

    /* access modifiers changed from: protected */
    public DNSSDService _resolve(int flags, int ifIndex, String serviceName, String regType, String domain, InternalResolveListener client) {
        return new AppleResolver(flags, ifIndex, serviceName, regType, domain, client);
    }

    /* access modifiers changed from: protected */
    public DNSSDRegistration _register(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, TXTRecord txtRecord, InternalRegisterListener client) {
        return new AppleRegistration(flags, ifIndex, serviceName, regType, domain, host, port, txtRecord != null ? txtRecord.getRawBytes() : null, client);
    }

    /* access modifiers changed from: protected */
    public DNSSDRecordRegistrar _createRecordRegistrar(RegisterRecordListener listener) {
        return new AppleRecordRegistrar(listener);
    }

    /* access modifiers changed from: protected */
    public DNSSDService _queryRecord(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, InternalQueryListener client) {
        return new AppleQuery(flags, ifIndex, serviceName, rrtype, rrclass, client);
    }

    /* access modifiers changed from: protected */
    public DNSSDService _enumerateDomains(int flags, int ifIndex, InternalDomainListener listener) {
        return new AppleDomainEnum(flags, ifIndex, listener);
    }

    /* access modifiers changed from: protected */
    public String _constructFullName(String serviceName, String regType, String domain) {
        String[] responseHolder = new String[1];
        int rc = ConstructName(serviceName, regType, domain, responseHolder);
        if (rc == 0) {
            return responseHolder[0];
        }
        throw new AppleDNSSDException(rc);
    }

    /* access modifiers changed from: protected */
    public int _reconfirmRecord(int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata) {
        return ReconfirmRecord(flags, ifIndex, fullName, rrtype, rrclass, rdata);
    }

    /* access modifiers changed from: protected */
    public String _getNameForIfIndex(int ifIndex) {
        return new String(GetNameForIfIndex(ifIndex));
    }

    /* access modifiers changed from: protected */
    public int _getIfIndexForName(String ifName) {
        return GetIfIndexForName(ifName);
    }
}
