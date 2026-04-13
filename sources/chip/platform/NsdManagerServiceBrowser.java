package chip.platform;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.github.druk.dnssd.BrowseListener;
import com.github.druk.dnssd.DNSSD;
import com.github.druk.dnssd.DNSSDEmbedded;
import com.github.druk.dnssd.DNSSDException;
import com.github.druk.dnssd.DNSSDService;
import com.leedarson.serviceimpl.k;
import java.util.ArrayList;
import java.util.HashMap;

public class NsdManagerServiceBrowser implements ServiceBrowser {
    private static final long BROWSE_SERVICE_TIMEOUT = 20000;
    /* access modifiers changed from: private */
    public static final String TAG = NsdManagerServiceBrowser.class.getSimpleName();
    private static DNSSD dnssd;
    /* access modifiers changed from: private */
    public static HashMap<String, DNSSDServiceInfo> serviceInfoMap = new HashMap<>();
    private Long _callbackHandle;
    /* access modifiers changed from: private */
    public ChipMdnsCallback _chipMdnsCallback;
    private DNSSDService browseService;
    private HashMap<Long, NsdManagerDiscovery> callbackMap;
    Runnable discoverRunnable;
    /* access modifiers changed from: private */
    public Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private WifiManager.MulticastLock multicastLock;
    Runnable timeoutRunnable;

    public static DNSSD getDnssd() {
        return dnssd;
    }

    public static DNSSDServiceInfo getServiceInfoCache(String serviceName) {
        if (serviceInfoMap.containsKey(serviceName)) {
            return serviceInfoMap.get(serviceName);
        }
        return null;
    }

    public NsdManagerServiceBrowser(Context context) {
        dnssd = new DNSSDEmbedded(context);
        WifiManager.MulticastLock createMulticastLock = ((WifiManager) context.getSystemService("wifi")).createMulticastLock("chipBrowseMulticastLock");
        this.multicastLock = createMulticastLock;
        createMulticastLock.setReferenceCounted(true);
        this.callbackMap = new HashMap<>();
    }

    public void browse(String serviceType, long callbackHandle, long contextHandle, ChipMdnsCallback chipMdnsCallback) {
        k kVar = k.a;
        kVar.a("####Nsd服务开始扫描 browse serviceType:" + serviceType, TAG);
        this._callbackHandle = Long.valueOf(callbackHandle);
        this._chipMdnsCallback = chipMdnsCallback;
        this.timeoutRunnable = new b(this, callbackHandle, chipMdnsCallback);
        a aVar = new a(this, serviceType, callbackHandle, contextHandle, chipMdnsCallback);
        this.discoverRunnable = aVar;
        this.mainThreadHandler.post(aVar);
        this.mainThreadHandler.postDelayed(this.timeoutRunnable, BROWSE_SERVICE_TIMEOUT);
    }

    /* renamed from: startDiscover */
    public void lambda$browse$1(String serviceType, long callbackHandle, long contextHandle, ChipMdnsCallback chipMdnsCallback) {
        String str = TAG;
        Log.d(str, "Starting service discovering for '" + serviceType + "'");
        this._chipMdnsCallback = chipMdnsCallback;
        if (this.callbackMap.containsKey(Long.valueOf(callbackHandle))) {
            Log.d(str, "Invalid callbackHandle");
            return;
        }
        NsdManagerDiscovery nsdManagerDiscovery = new NsdManagerDiscovery(serviceType, callbackHandle, contextHandle);
        this.multicastLock.acquire();
        try {
            this.browseService = dnssd.browse(serviceType, nsdManagerDiscovery);
        } catch (DNSSDException e) {
            e.printStackTrace();
            Log.e("TAG", "error", e);
        }
        this.callbackMap.put(Long.valueOf(callbackHandle), nsdManagerDiscovery);
    }

    /* renamed from: stopDiscover */
    public void lambda$browse$0(long callbackHandle, ChipMdnsCallback chipMdnsCallback) {
        String str = TAG;
        Log.d(str, "Stop service discovering");
        if (!this.callbackMap.containsKey(Long.valueOf(callbackHandle))) {
            Log.d(str, "Invalid callbackHandle");
            return;
        }
        NsdManagerDiscovery discovery = this.callbackMap.remove(Long.valueOf(callbackHandle));
        if (this.multicastLock.isHeld()) {
            this.multicastLock.release();
        }
        DNSSDService dNSSDService = this.browseService;
        if (dNSSDService != null) {
            dNSSDService.stop();
            this.browseService = null;
        }
        discovery.handleServiceBrowse(chipMdnsCallback);
    }

    public class NsdManagerDiscovery implements BrowseListener {
        private long callbackHandle;
        private long contextHandle;
        private ArrayList<String> serviceNameList = new ArrayList<>();
        private String serviceType;

        public NsdManagerDiscovery(String serviceType2, long callbackHandle2, long contextHandle2) {
            this.serviceType = serviceType2;
            this.callbackHandle = callbackHandle2;
            this.contextHandle = contextHandle2;
        }

        public void serviceFound(DNSSDService browser, int flags, int ifIndex, String serviceName, String regType, String domain) {
            String access$000 = NsdManagerServiceBrowser.TAG;
            Log.i(access$000, "Found service '" + serviceName + "'");
            k kVar = k.a;
            kVar.a("####Nsd服务扫描发现服务,service:" + serviceName, NsdManagerServiceBrowser.TAG);
            NsdManagerServiceBrowser.serviceInfoMap.put(serviceName, new DNSSDServiceInfo(flags, ifIndex, serviceName, regType, domain));
            this.serviceNameList.add(serviceName);
            if (NsdManagerServiceBrowser.this._chipMdnsCallback != null && this.serviceNameList.size() > 0) {
                NsdManagerServiceBrowser nsdManagerServiceBrowser = NsdManagerServiceBrowser.this;
                if (nsdManagerServiceBrowser.timeoutRunnable != null) {
                    nsdManagerServiceBrowser.mainThreadHandler.removeCallbacks(NsdManagerServiceBrowser.this.timeoutRunnable);
                }
                NsdManagerServiceBrowser nsdManagerServiceBrowser2 = NsdManagerServiceBrowser.this;
                nsdManagerServiceBrowser2.lambda$browse$0(this.callbackHandle, nsdManagerServiceBrowser2._chipMdnsCallback);
            }
        }

        public void serviceLost(DNSSDService browser, int flags, int ifIndex, String serviceName, String regType, String domain) {
            String access$000 = NsdManagerServiceBrowser.TAG;
            Log.i(access$000, "Lost service '" + this.serviceType + "'");
            NsdManagerServiceBrowser.serviceInfoMap.remove(serviceName);
        }

        public void operationFailed(DNSSDService service, int errorCode) {
            String access$000 = NsdManagerServiceBrowser.TAG;
            Log.w(access$000, "Failed to stop discovery service '" + this.serviceType + "': " + errorCode);
        }

        public void handleServiceBrowse(ChipMdnsCallback chipMdnsCallback) {
            if (this.serviceNameList.isEmpty()) {
                Log.w(NsdManagerServiceBrowser.TAG, "service name empty");
                return;
            }
            String access$000 = NsdManagerServiceBrowser.TAG;
            Log.i(access$000, "handleServiceBrowse " + this.serviceType + ", " + this.serviceNameList.get(0));
            ArrayList<String> arrayList = this.serviceNameList;
            chipMdnsCallback.handleServiceBrowse((String[]) arrayList.toArray(new String[arrayList.size()]), this.serviceType, this.callbackHandle, this.contextHandle);
        }
    }

    public class DNSSDServiceInfo {
        public String domain;
        public int flags;
        public int ifIndex;
        public String regType;
        public String serviceName;

        public DNSSDServiceInfo(int flags2, int ifIndex2, String serviceName2, String regType2, String domain2) {
            this.flags = flags2;
            this.ifIndex = ifIndex2;
            this.serviceName = serviceName2;
            this.regType = regType2;
            this.domain = domain2;
        }
    }
}
