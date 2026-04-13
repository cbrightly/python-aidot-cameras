package chip.platform;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import chip.platform.NsdManagerServiceBrowser;
import com.github.druk.dnssd.DNSSDException;
import com.github.druk.dnssd.DNSSDRegistration;
import com.github.druk.dnssd.DNSSDService;
import com.github.druk.dnssd.QueryListener;
import com.github.druk.dnssd.RegisterListener;
import com.github.druk.dnssd.ResolveListener;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.k;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import timber.log.a;

public class NsdManagerServiceResolver implements ServiceResolver {
    private static final long RESOLVE_SERVICE_TIMEOUT = 30000;
    /* access modifiers changed from: private */
    public static final String TAG = NsdManagerServiceResolver.class.getSimpleName();
    private NsdManagerServiceBrowser.DNSSDServiceInfo lastServiceInfo;
    /* access modifiers changed from: private */
    public Handler mainThreadHandler;
    /* access modifiers changed from: private */
    public WifiManager.MulticastLock multicastLock;
    private final NsdManager nsdManager;
    /* access modifiers changed from: private */
    public List<QueryListener> queryListeners = new ArrayList();
    private DNSSDService registerService;
    private List<RegisterListener> registrationListeners = new ArrayList();

    public NsdManagerServiceResolver(Context context) {
        this.nsdManager = (NsdManager) context.getSystemService("servicediscovery");
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        WifiManager.MulticastLock createMulticastLock = ((WifiManager) context.getSystemService("wifi")).createMulticastLock("chipMulticastLock");
        this.multicastLock = createMulticastLock;
        createMulticastLock.setReferenceCounted(true);
    }

    public void resolve(String instanceName, String serviceType, long callbackHandle, long contextHandle, ChipMdnsCallback chipMdnsCallback) {
        k kVar = k.a;
        kVar.a("####Nsd服务启动尝试解析: resolve serviceName:" + instanceName + ",serviceType:" + serviceType, TAG);
        Runnable timeoutRunnable = new Runnable() {
            public void run() {
                Log.d(NsdManagerServiceResolver.TAG, "resolve: Timing out");
                if (NsdManagerServiceResolver.this.multicastLock.isHeld()) {
                    NsdManagerServiceResolver.this.multicastLock.release();
                }
            }
        };
        final String str = instanceName;
        final String str2 = serviceType;
        final long j = callbackHandle;
        final long j2 = contextHandle;
        final ChipMdnsCallback chipMdnsCallback2 = chipMdnsCallback;
        final Runnable runnable = timeoutRunnable;
        this.mainThreadHandler.post(new Runnable() {
            public void run() {
                NsdManagerServiceResolver.this.multicastLock.acquire();
                NsdManagerServiceResolver.this.startResolve(str, str2, j, j2, chipMdnsCallback2, runnable);
            }
        });
        this.mainThreadHandler.postDelayed(timeoutRunnable, 30000);
    }

    public void publish(final String serviceName, String hostName, final String type, int port, String[] textEntriesKeys, byte[][] textEntriesDatas, String[] subTypes) {
        k.a.a("####注册服务: serviceName:" + serviceName + ",serviceType:" + type + ",添加到nsd服务中", TAG);
        StringBuilder sb = new StringBuilder(type);
        for (String subType : subTypes) {
            sb.append(",");
            sb.append(subType);
        }
        try {
            this.registerService = NsdManagerServiceBrowser.getDnssd().register(serviceName, sb.toString(), port, new RegisterListener() {
                public void serviceRegistered(DNSSDRegistration registration, int flags, String serviceName, String regType, String domain) {
                    k kVar = k.a;
                    kVar.a("####注册服务: serviceName:" + serviceName + ",serviceType:" + type + ",成功", NsdManagerServiceResolver.TAG);
                }

                public void operationFailed(DNSSDService service, int errorCode) {
                    k kVar = k.a;
                    kVar.a("####注册服务: serviceName:" + serviceName + ",serviceType:" + type + ",失败 error:" + errorCode, NsdManagerServiceResolver.TAG);
                }
            });
        } catch (DNSSDException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "publish " + serviceName);
    }

    public void removeServices() {
        Log.d(TAG, "removeServices: ");
        DNSSDService dNSSDService = this.registerService;
        if (dNSSDService != null) {
            dNSSDService.stop();
            this.registerService = null;
        }
    }

    /* access modifiers changed from: private */
    public void startResolve(String instanceName, String serviceType, long callbackHandle, long contextHandle, ChipMdnsCallback chipMdnsCallback, Runnable timeoutRunnable) {
        NsdManagerServiceBrowser.DNSSDServiceInfo serviceInfo;
        String resolveType;
        String str;
        String str2 = instanceName;
        String str3 = serviceType;
        NsdManagerServiceBrowser.DNSSDServiceInfo serviceInfo2 = NsdManagerServiceBrowser.getServiceInfoCache(instanceName);
        if (serviceInfo2 == null && this.lastServiceInfo == null) {
            k kVar = k.a;
            kVar.a("nsd服务缓存为空，尝试使用系统nsd服务去解析 instanceName:" + str2 + ",serviceType:" + str3, TAG);
            startSystemNsdResolve(instanceName, serviceType, callbackHandle, contextHandle, chipMdnsCallback, timeoutRunnable);
            return;
        }
        k kVar2 = k.a;
        kVar2.a("使用三方nsd服务去解析 instanceName:" + str2 + ",serviceType:" + str3, TAG);
        if (serviceInfo2 == null) {
            serviceInfo = this.lastServiceInfo;
        } else {
            serviceInfo = serviceInfo2;
        }
        this.lastServiceInfo = serviceInfo;
        if (str3.contains("_matterc._tcp")) {
            str = "_matterc._udp.";
        } else if (str3.contains("_matter._tcp")) {
            str = "_matter._tcp.";
        } else {
            resolveType = str3;
            NsdManagerServiceBrowser.DNSSDServiceInfo dNSSDServiceInfo = serviceInfo;
            thirdDnssdResolve(serviceInfo.flags, serviceInfo.ifIndex, instanceName, serviceInfo.domain, callbackHandle, contextHandle, chipMdnsCallback, timeoutRunnable, resolveType);
        }
        resolveType = str;
        try {
            NsdManagerServiceBrowser.DNSSDServiceInfo dNSSDServiceInfo2 = serviceInfo;
            try {
                thirdDnssdResolve(serviceInfo.flags, serviceInfo.ifIndex, instanceName, serviceInfo.domain, callbackHandle, contextHandle, chipMdnsCallback, timeoutRunnable, resolveType);
            } catch (DNSSDException e) {
                e = e;
            }
        } catch (DNSSDException e2) {
            e = e2;
            NsdManagerServiceBrowser.DNSSDServiceInfo dNSSDServiceInfo3 = serviceInfo;
            e.printStackTrace();
        }
    }

    private void thirdDnssdResolve(int flags, int ifIndex, String instanceName, String domain, long callbackHandle, long contextHandle, ChipMdnsCallback chipMdnsCallback, Runnable timeoutRunnable, String resolveType) {
        final String str = instanceName;
        final String str2 = resolveType;
        final ChipMdnsCallback chipMdnsCallback2 = chipMdnsCallback;
        final long j = callbackHandle;
        final long j2 = contextHandle;
        final Runnable runnable = timeoutRunnable;
        NsdManagerServiceBrowser.getDnssd().resolve(flags, ifIndex, str, str2, domain, new ResolveListener() {
            public void serviceResolved(DNSSDService resolver, int flags, int ifIndex, String fullName, final String hostName, final int port, final Map<String, String> txtRecord) {
                k kVar = k.a;
                kVar.a("三方NSD服务解析成功回调 serviceName:" + str + ",resolveType:" + str2 + ",hostName:" + hostName + ",fullName:" + fullName + ",port:" + port, NsdManagerServiceResolver.TAG);
                QueryListener listener = new QueryListener() {
                    public void queryAnswered(DNSSDService query, int flags, int ifIndex, String fullName, int rrtype, int rrclass, byte[] rdata, int ttl) {
                        Log.i(Constants.SERVICE_Matter, "nsdserviceresolver queryAnswered:111");
                        Handler access$300 = NsdManagerServiceResolver.this.mainThreadHandler;
                        Map map = txtRecord;
                        AnonymousClass4 r0 = AnonymousClass4.this;
                        c cVar = r0;
                        c cVar2 = new c(this, map, str2, rdata, hostName, chipMdnsCallback2, str, port, j, j2, runnable);
                        access$300.post(cVar);
                    }

                    /* access modifiers changed from: private */
                    /* renamed from: lambda$queryAnswered$0 */
                    public /* synthetic */ void a(Map txtRecord, String resolveType, byte[] rdata, String hostName, ChipMdnsCallback chipMdnsCallback, String instanceName, int port, long callbackHandle, long contextHandle, Runnable timeoutRunnable) {
                        String str = resolveType;
                        try {
                            HashMap hashMap = new HashMap();
                            for (Map.Entry<String, String> entry : txtRecord.entrySet()) {
                                hashMap.put(entry.getKey(), (byte[]) entry.getValue().getBytes().clone());
                                a.i("queryAnswered key: " + entry.getKey() + ", value: " + entry.getValue(), new Object[0]);
                            }
                            String chipServiceType = str.endsWith(".") ? str.substring(0, resolveType.length() - 1) : str;
                            InetAddress address = InetAddress.getByAddress(rdata);
                            Log.i(Constants.SERVICE_Matter, "Query address " + hostName + ", " + address.getHostAddress() + ", chipServiceType " + chipServiceType + ",thread:" + Thread.currentThread().getName());
                            boolean isIpv6 = address instanceof Inet6Address;
                            if (!(address instanceof Inet4Address)) {
                                if (!isIpv6) {
                                    InetAddress inetAddress = address;
                                    Log.i(Constants.SERVICE_Matter, "queryAnswered:333");
                                    NsdManagerServiceResolver.this.mainThreadHandler.removeCallbacks(timeoutRunnable);
                                }
                            }
                            if (NsdManagerServiceResolver.this.multicastLock.isHeld()) {
                                Log.i(Constants.SERVICE_Matter, "nsdserviceresolver queryAnswered:222 success");
                                InetAddress inetAddress2 = address;
                                chipMdnsCallback.handleServiceResolve(instanceName, chipServiceType, hostName, address.getHostAddress(), port, hashMap, callbackHandle, contextHandle);
                                NsdManagerServiceResolver.this.multicastLock.release();
                            }
                            Log.i(Constants.SERVICE_Matter, "queryAnswered:333");
                            try {
                                NsdManagerServiceResolver.this.mainThreadHandler.removeCallbacks(timeoutRunnable);
                            } catch (UnknownHostException e) {
                                e = e;
                            }
                        } catch (UnknownHostException e2) {
                            e = e2;
                            Runnable runnable = timeoutRunnable;
                            e.printStackTrace();
                        }
                    }

                    public void operationFailed(DNSSDService service, int errorCode) {
                        Log.w(Constants.SERVICE_Matter, "Failed to query record " + errorCode + ",thread:" + Thread.currentThread().getName());
                    }
                };
                NsdManagerServiceResolver.this.queryListeners.add(listener);
                NsdManagerServiceResolver.this.mainThreadHandler.post(new d(hostName, ifIndex, listener));
            }

            static /* synthetic */ void lambda$serviceResolved$0(String hostName, int ifIndex, QueryListener listener) {
                try {
                    k kVar = k.a;
                    kVar.a("尝试去queryRecord hostName:" + hostName, NsdManagerServiceResolver.TAG);
                    NsdManagerServiceBrowser.getDnssd().queryRecord(0, ifIndex, hostName, 1, 1, listener);
                    NsdManagerServiceBrowser.getDnssd().queryRecord(0, ifIndex, hostName, 28, 1, listener);
                } catch (DNSSDException e) {
                    e.printStackTrace();
                }
            }

            public void operationFailed(DNSSDService service, int errorCode) {
                k kVar = k.a;
                kVar.a("三方NSD服务解析失败 serviceName:" + str + ",errCode:" + errorCode, NsdManagerServiceResolver.TAG);
                NsdManagerServiceResolver.this.mainThreadHandler.post(new e(this, chipMdnsCallback2, str, str2, j, j2));
                NsdManagerServiceResolver.this.mainThreadHandler.removeCallbacks(runnable);
            }

            /* access modifiers changed from: private */
            /* renamed from: lambda$operationFailed$1 */
            public /* synthetic */ void a(ChipMdnsCallback chipMdnsCallback, String instanceName, String resolveType, long callbackHandle, long contextHandle) {
                chipMdnsCallback.handleServiceResolve(instanceName, resolveType, (String) null, (String) null, 0, (Map<String, byte[]>) null, callbackHandle, contextHandle);
                if (NsdManagerServiceResolver.this.multicastLock.isHeld()) {
                    NsdManagerServiceResolver.this.multicastLock.release();
                }
            }
        });
    }

    private void startSystemNsdResolve(String instanceName, String serviceType, long callbackHandle, long contextHandle, ChipMdnsCallback chipMdnsCallback, Runnable timeoutRunnable) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(instanceName);
        serviceInfo.setServiceType(serviceType);
        final ChipMdnsCallback chipMdnsCallback2 = chipMdnsCallback;
        final String str = instanceName;
        final String str2 = serviceType;
        final long j = callbackHandle;
        final long j2 = contextHandle;
        final Runnable runnable = timeoutRunnable;
        this.nsdManager.resolveService(serviceInfo, new NsdManager.ResolveListener() {
            public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
                k kVar = k.a;
                kVar.a("使用系统nsd服务解析失败,service:" + serviceInfo.getServiceName() + ",errCode:" + errorCode, NsdManagerServiceResolver.TAG);
                chipMdnsCallback2.handleServiceResolve(str, str2, (String) null, (String) null, 0, (Map<String, byte[]>) null, j, j2);
                if (NsdManagerServiceResolver.this.multicastLock.isHeld()) {
                    NsdManagerServiceResolver.this.multicastLock.release();
                }
                NsdManagerServiceResolver.this.mainThreadHandler.removeCallbacks(runnable);
            }

            public void onServiceResolved(NsdServiceInfo serviceInfo) {
                k kVar = k.a;
                kVar.a("使用系统nsd服务解析服务成功,service:" + serviceInfo.getServiceName() + ",host:" + serviceInfo.getHost(), NsdManagerServiceResolver.TAG);
                chipMdnsCallback2.handleServiceResolve(str, str2, serviceInfo.getHost().getHostName(), serviceInfo.getHost().getHostAddress(), serviceInfo.getPort(), serviceInfo.getAttributes(), j, j2);
                if (NsdManagerServiceResolver.this.multicastLock.isHeld()) {
                    NsdManagerServiceResolver.this.multicastLock.release();
                }
                NsdManagerServiceResolver.this.mainThreadHandler.removeCallbacks(runnable);
            }
        });
    }
}
