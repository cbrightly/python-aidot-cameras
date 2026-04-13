package com.github.druk.dnssd;

public abstract class InternalDNSSD {
    public static final int ALL_INTERFACES = 0;
    public static final int BROWSE_DOMAINS = 64;
    public static final int DEFAULT = 4;
    public static final int LOCALHOST_ONLY = -1;
    public static final int MAX_DOMAIN_NAME = 1009;
    public static final int MORE_COMING = 1;
    public static final int NO_AUTO_RENAME = 8;
    public static final int REGISTRATION_DOMAINS = 128;
    public static final int SHARED = 16;
    public static final int UNIQUE = 32;
    protected static InternalDNSSD fInstance;

    /* access modifiers changed from: protected */
    public abstract String _constructFullName(String str, String str2, String str3);

    /* access modifiers changed from: protected */
    public abstract DNSSDRecordRegistrar _createRecordRegistrar(RegisterRecordListener registerRecordListener);

    /* access modifiers changed from: protected */
    public abstract DNSSDService _enumerateDomains(int i, int i2, InternalDomainListener internalDomainListener);

    /* access modifiers changed from: protected */
    public abstract int _getIfIndexForName(String str);

    /* access modifiers changed from: protected */
    public abstract String _getNameForIfIndex(int i);

    /* access modifiers changed from: protected */
    public abstract void _init(String str);

    /* access modifiers changed from: protected */
    public abstract DNSSDService _makeBrowser(int i, int i2, String str, String str2, InternalBrowseListener internalBrowseListener);

    /* access modifiers changed from: protected */
    public abstract DNSSDService _queryRecord(int i, int i2, String str, int i3, int i4, InternalQueryListener internalQueryListener);

    /* access modifiers changed from: protected */
    public abstract int _reconfirmRecord(int i, int i2, String str, int i3, int i4, byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract DNSSDRegistration _register(int i, int i2, String str, String str2, String str3, String str4, int i3, TXTRecord tXTRecord, InternalRegisterListener internalRegisterListener);

    /* access modifiers changed from: protected */
    public abstract DNSSDService _resolve(int i, int i2, String str, String str2, String str3, InternalResolveListener internalResolveListener);

    public static void init(String lib) {
        getInstance()._init(lib);
    }

    public static DNSSDService browse(int flags, int ifIndex, String regType, String domain, InternalBrowseListener listener) {
        return getInstance()._makeBrowser(flags, ifIndex, regType, domain, listener);
    }

    public static DNSSDService browse(String regType, InternalBrowseListener listener) {
        return browse(0, 0, regType, "", listener);
    }

    public static DNSSDService resolve(int flags, int ifIndex, String serviceName, String regType, String domain, InternalResolveListener listener) {
        return getInstance()._resolve(flags, ifIndex, serviceName, regType, domain, listener);
    }

    public static DNSSDRegistration register(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, TXTRecord txtRecord, InternalRegisterListener listener) {
        return getInstance()._register(flags, ifIndex, serviceName, regType, domain, host, port, txtRecord, listener);
    }

    public static DNSSDRegistration register(String serviceName, String regType, int port, InternalRegisterListener listener) {
        return register(0, 0, serviceName, regType, (String) null, (String) null, port, (TXTRecord) null, listener);
    }

    public static DNSSDRecordRegistrar createRecordRegistrar(RegisterRecordListener listener) {
        return getInstance()._createRecordRegistrar(listener);
    }

    public static DNSSDService queryRecord(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, InternalQueryListener listener) {
        return getInstance()._queryRecord(flags, ifIndex, serviceName, rrtype, rrclass, listener);
    }

    public static DNSSDService enumerateDomains(int flags, int ifIndex, InternalDomainListener listener) {
        return getInstance()._enumerateDomains(flags, ifIndex, listener);
    }

    public static String constructFullName(String serviceName, String regType, String domain) {
        return getInstance()._constructFullName(serviceName, regType, domain);
    }

    public static int reconfirmRecord(int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata) {
        return getInstance()._reconfirmRecord(flags, ifIndex, fullName, rrtype, rrclass, rdata);
    }

    public static String getNameForIfIndex(int ifIndex) {
        return getInstance()._getNameForIfIndex(ifIndex);
    }

    public static int getIfIndexForName(String ifName) {
        return getInstance()._getIfIndexForName(ifName);
    }

    protected InternalDNSSD() {
    }

    protected static final InternalDNSSD getInstance() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("getDNSSDInstance"));
        }
        return fInstance;
    }

    static {
        try {
            String name = System.getProperty("com.github.druk.dnssd.DNSSD");
            if (name == null) {
                name = "com.github.druk.dnssd.AppleDNSSD";
            }
            fInstance = (InternalDNSSD) Class.forName(name).newInstance();
        } catch (Exception e) {
            throw new InternalError("cannot instantiate DNSSD" + e);
        }
    }
}
