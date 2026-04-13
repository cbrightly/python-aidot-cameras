package com.github.druk.dnssd;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.github.druk.dnssd.InternalDNSSDService;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public abstract class DNSSD implements InternalDNSSDService.DnssdServiceListener {
    public static final int ALL_INTERFACES = 0;
    public static final int BROWSE_DOMAINS = 64;
    public static final int DEFAULT = 4;
    public static final int DNSSD_DEFAULT_TIMEOUT = 60000;
    public static final int LOCALHOST_ONLY = -1;
    public static final int MAX_DOMAIN_NAME = 1009;
    public static final int MORE_COMING = 1;
    private static final String MULTICAST_LOCK_NAME = "com.github.druk.dnssd.DNSSD";
    public static final int NO_AUTO_RENAME = 8;
    public static final int REGISTRATION_DOMAINS = 128;
    public static final int SHARED = 16;
    public static final int UNIQUE = 32;
    /* access modifiers changed from: private */
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Context context;
    /* access modifiers changed from: private */
    public final Handler handler;
    private volatile WifiManager.MulticastLock multicastLock;
    private final int serviceTimeout;

    DNSSD(Context context2, String lib) {
        this(context2, lib, Looper.getMainLooper());
    }

    DNSSD(Context context2, String lib, Looper looper) {
        this.multicastLock = null;
        this.context = context2.getApplicationContext();
        InternalDNSSD.init(lib);
        this.handler = new Handler(looper);
        this.serviceTimeout = DNSSD_DEFAULT_TIMEOUT;
    }

    DNSSD(Context context2, String lib, Handler handler2) {
        this.multicastLock = null;
        this.context = context2.getApplicationContext();
        InternalDNSSD.init(lib);
        this.handler = handler2;
        this.serviceTimeout = DNSSD_DEFAULT_TIMEOUT;
    }

    DNSSD(Context context2, String lib, Handler handler2, int serviceTimeout2) {
        this.multicastLock = null;
        this.context = context2.getApplicationContext();
        InternalDNSSD.init(lib);
        this.handler = handler2;
        this.serviceTimeout = serviceTimeout2;
    }

    public DNSSDService browse(int flags, int ifIndex, String regType, String domain, final BrowseListener listener) {
        onServiceStarting();
        final InternalDNSSDService[] services = {new InternalDNSSDService(this, InternalDNSSD.browse(flags, ifIndex, regType, domain, new InternalBrowseListener() {
            public void serviceFound(DNSSDService browser, int flags, int ifIndex, byte[] serviceName, byte[] regType, byte[] domain) {
                DNSSD.this.handler.post(new a(listener, services, flags, ifIndex, new String(serviceName, DNSSD.UTF_8), new String(regType, DNSSD.UTF_8), new String(domain, DNSSD.UTF_8)));
            }

            public void serviceLost(DNSSDService browser, int flags, int ifIndex, byte[] serviceName, byte[] regType, byte[] domain) {
                DNSSD.this.handler.post(new b(listener, services, flags, ifIndex, new String(serviceName, DNSSD.UTF_8), new String(regType, DNSSD.UTF_8), new String(domain, DNSSD.UTF_8)));
            }

            public void operationFailed(DNSSDService service, int errorCode) {
                DNSSD.this.handler.post(new c(listener, services, errorCode));
            }
        }))};
        return services[0];
    }

    public DNSSDService browse(String regType, BrowseListener listener) {
        return browse(0, 0, regType, "", listener);
    }

    public DNSSDService resolve(int flags, int ifIndex, String serviceName, String regType, String domain, final ResolveListener listener) {
        onServiceStarting();
        final Runnable timeoutRunnable = new m(services);
        final DNSSDService[] services = {new InternalDNSSDService(this, InternalDNSSD.resolve(flags, ifIndex, serviceName, regType, domain, new InternalResolveListener() {
            public void serviceResolved(DNSSDService resolver, int flags, int ifIndex, byte[] fullName, byte[] hostName, int port, TXTRecord txtRecord) {
                String fullNameStr = new String(fullName, DNSSD.UTF_8);
                String hostNameStr = new String(hostName, DNSSD.UTF_8);
                Map<String, String> record = DNSSD.parseTXTRecords(txtRecord);
                DNSSD.this.handler.removeCallbacks(timeoutRunnable);
                DNSSD.this.handler.post(new d(listener, services, flags, ifIndex, fullNameStr, hostNameStr, port, record));
            }

            static /* synthetic */ void lambda$serviceResolved$0(ResolveListener listener, DNSSDService[] services, int flags, int ifIndex, String fullNameStr, String hostNameStr, int port, Map record) {
                listener.serviceResolved(services[0], flags, ifIndex, fullNameStr, hostNameStr, port, record);
                services[0].stop();
            }

            public void operationFailed(DNSSDService service, int errorCode) {
                DNSSD.this.handler.removeCallbacks(timeoutRunnable);
                DNSSD.this.handler.post(new e(listener, services, errorCode));
            }

            static /* synthetic */ void lambda$operationFailed$1(ResolveListener listener, DNSSDService[] services, int errorCode) {
                listener.operationFailed(services[0], errorCode);
                services[0].stop();
            }
        }))};
        this.handler.postDelayed(timeoutRunnable, (long) this.serviceTimeout);
        return services[0];
    }

    public DNSSDRegistration register(int flags, int ifIndex, String serviceName, String regType, String domain, String host, int port, TXTRecord txtRecord, RegisterListener listener) {
        onServiceStarting();
        final RegisterListener registerListener = listener;
        final DNSSDRegistration[] services = {new InternalDNSSDRegistration(this, InternalDNSSD.register(flags, ifIndex, serviceName, regType, domain, host, port, txtRecord, new InternalRegisterListener() {
            public void serviceRegistered(DNSSDRegistration registration, int flags, byte[] serviceName, byte[] regType, byte[] domain) {
                DNSSD.this.handler.post(new g(registerListener, services, flags, new String(serviceName, DNSSD.UTF_8), new String(regType, DNSSD.UTF_8), new String(domain, DNSSD.UTF_8)));
            }

            public void operationFailed(DNSSDService service, int errorCode) {
                DNSSD.this.handler.post(new f(registerListener, services, errorCode));
            }
        }))};
        return services[0];
    }

    public DNSSDService register(String serviceName, String regType, int port, RegisterListener listener) {
        return register(0, 0, serviceName, regType, (String) null, (String) null, port, (TXTRecord) null, listener);
    }

    public DNSSDRecordRegistrar createRecordRegistrar(RegisterRecordListener listener) {
        onServiceStarting();
        return new InternalDNSSDRecordRegistrar(this, InternalDNSSD.createRecordRegistrar(listener));
    }

    public DNSSDService queryRecord(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, QueryListener listener) {
        return queryRecord(flags, ifIndex, serviceName, rrtype, rrclass, false, listener);
    }

    public DNSSDService queryRecord(int flags, int ifIndex, String serviceName, int rrtype, int rrclass, boolean autoStop, QueryListener listener) {
        onServiceStarting();
        DNSSDService[] services = new DNSSDService[1];
        Runnable timeoutRunnable = new n(services);
        final Runnable runnable = timeoutRunnable;
        final QueryListener queryListener = listener;
        final DNSSDService[] dNSSDServiceArr = services;
        final boolean z = autoStop;
        services[0] = new InternalDNSSDService(this, InternalDNSSD.queryRecord(flags, ifIndex, serviceName, rrtype, rrclass, new InternalQueryListener() {
            public void queryAnswered(DNSSDService query, int flags, int ifIndex, byte[] fullName, int rrtype, int rrclass, byte[] rdata, int ttl) {
                String fullNameStr = new String(fullName, DNSSD.UTF_8);
                DNSSD.this.handler.removeCallbacks(runnable);
                DNSSD.this.handler.post(new i(queryListener, dNSSDServiceArr, flags, ifIndex, fullNameStr, rrtype, rrclass, rdata, ttl, z));
            }

            static /* synthetic */ void lambda$queryAnswered$0(QueryListener listener, DNSSDService[] services, int flags, int ifIndex, String fullNameStr, int rrtype, int rrclass, byte[] rdata, int ttl, boolean autoStop) {
                if (listener != null) {
                    listener.queryAnswered(services[0], flags, ifIndex, fullNameStr, rrtype, rrclass, rdata, ttl);
                }
                if (autoStop) {
                    services[0].stop();
                }
            }

            public void operationFailed(DNSSDService service, int errorCode) {
                DNSSD.this.handler.removeCallbacks(runnable);
                DNSSD.this.handler.post(new h(queryListener, dNSSDServiceArr, errorCode));
            }

            static /* synthetic */ void lambda$operationFailed$1(QueryListener listener, DNSSDService[] services, int errorCode) {
                listener.operationFailed(services[0], errorCode);
                services[0].stop();
            }
        }));
        if (autoStop) {
            this.handler.postDelayed(timeoutRunnable, (long) this.serviceTimeout);
        }
        return services[0];
    }

    public DNSSDService enumerateDomains(int flags, int ifIndex, final DomainListener listener) {
        onServiceStarting();
        final DNSSDService[] services = {new InternalDNSSDService(this, InternalDNSSD.enumerateDomains(flags, ifIndex, new InternalDomainListener() {
            public void domainFound(DNSSDService domainEnum, int flags, int ifIndex, byte[] domain) {
                DNSSD.this.handler.post(new l(listener, services, flags, ifIndex, new String(domain, DNSSD.UTF_8)));
            }

            public void domainLost(DNSSDService domainEnum, int flags, int ifIndex, byte[] domain) {
                DNSSD.this.handler.post(new k(listener, services, flags, ifIndex, new String(domain, DNSSD.UTF_8)));
            }

            public void operationFailed(DNSSDService service, int errorCode) {
                DNSSD.this.handler.post(new j(listener, services, errorCode));
            }
        }))};
        return services[0];
    }

    public String constructFullName(String serviceName, String regType, String domain) {
        onServiceStarting();
        String result = InternalDNSSD.constructFullName(serviceName, regType, domain);
        onServiceStopped();
        return result;
    }

    public int reconfirmRecord(int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata) {
        onServiceStarting();
        int error = InternalDNSSD.reconfirmRecord(flags, ifIndex, fullName, rrtype, rrclass, rdata);
        onServiceStopped();
        return error;
    }

    public void onServiceStarting() {
        if (this.multicastLock == null) {
            synchronized (this) {
                if (this.multicastLock == null) {
                    WifiManager wifi = (WifiManager) this.context.getApplicationContext().getSystemService("wifi");
                    if (wifi == null) {
                        Log.wtf("DNSSD", "Can't get WIFI Service");
                        return;
                    } else {
                        this.multicastLock = wifi.createMulticastLock(MULTICAST_LOCK_NAME);
                        this.multicastLock.setReferenceCounted(true);
                    }
                }
            }
        }
        this.multicastLock.acquire();
    }

    public void onServiceStopped() {
        if (this.multicastLock == null) {
            Log.wtf("DNSSD", "Multicast lock doesn't exist");
        } else {
            this.multicastLock.release();
        }
    }

    public static int getIfIndexForName(String ifName) {
        return InternalDNSSD.getIfIndexForName(ifName);
    }

    public static Map<String, String> parseTXTRecords(byte[] data) {
        return parseTXTRecords(new TXTRecord(data));
    }

    static Map<String, String> parseTXTRecords(TXTRecord record) {
        Map<String, String> result = new HashMap<>(record.size());
        for (int i = 0; i < record.size(); i++) {
            try {
                if (!TextUtils.isEmpty(record.getKey(i))) {
                    result.put(record.getKey(i), record.getValueAsString(i));
                }
            } catch (Exception e) {
                Log.w("RxResolveListener", "Parsing error of " + i + " TXT record", e);
            }
        }
        return result;
    }
}
