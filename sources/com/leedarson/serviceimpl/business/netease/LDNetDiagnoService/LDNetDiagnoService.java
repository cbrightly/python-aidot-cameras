package com.leedarson.serviceimpl.business.netease.LDNetDiagnoService;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.business.bean.NetDiagnosisDomain;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetPing;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetSocket;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.leedarson.serviceimpl.business.utils.FileIOUtils;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NetWorkStatueCheckResultEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.disposables.b;
import io.reactivex.e;
import java.io.File;
import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import meshsdk.BaseResp;
import org.apache.http.l;
import org.greenrobot.eventbus.c;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class LDNetDiagnoService extends LDNetAsyncTaskEx<String, String, String> implements LDNetPing.LDNetPingListener, LDNetTraceRoute.LDNetTraceRouteListener, LDNetSocket.LDNetSocketListener {
    private static final int CORE_POOL_SIZE = 1;
    private static final int KEEP_ALIVE = 10;
    private static final int MAXIMUM_POOL_SIZE = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static ThreadPoolExecutor sExecutor = null;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        public static ChangeQuickRedirect changeQuickRedirect;
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{r}, this, changeQuickRedirect, false, 7198, new Class[]{Runnable.class}, Thread.class);
            if (proxy.isSupported) {
                return (Thread) proxy.result;
            }
            Thread t = new Thread(r, "Trace #" + this.mCount.getAndIncrement());
            t.setPriority(1);
            return t;
        }
    };
    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue(2);
    private String _ISOCountryCode;
    private String _MobileCountryCode;
    private String _MobileNetCode;
    private String _UID;
    private String _appCode;
    private String _appName;
    private String _appVersion;
    private String _carrierName;
    private Context _context;
    private String _deviceID;
    b _diposOfNetWorkCheckDelay;
    private String _dns1;
    private String _dns2;
    private List<NetDiagnosisDomain> _dormains;
    private String _gateWay;
    private boolean _isDomainParseOk;
    private boolean _isNetConnected;
    private boolean _isRunning;
    private boolean _isSocketConnected;
    private boolean _isUseJNICConn = false;
    private boolean _isUseJNICTrace = true;
    private String _localIp;
    private final StringBuilder _logInfo = new StringBuilder(256);
    private LDNetDiagnoListener _netDiagnolistener;
    private LDNetPing _netPinger;
    private LDNetSocket _netSocker;
    private String _netType;
    private InetAddress[] _remoteInet;
    private List<String> _remoteIpList;
    private TelephonyManager _telManager = null;
    private LDNetTraceRoute _traceRouter;
    private String callbackKey;
    private int currentIndex = 0;
    boolean isCancel = false;
    private List<NetDiagnosisDomain> privateDomains = new ArrayList();
    private int privateFailCount = 0;
    private List<NetDiagnosisDomain> publicDomains = new ArrayList();
    private int publicPingSuccessCount = 0;
    private int publicSuccessCount = 0;
    private int resultCode = 200;

    public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 7196, new Class[]{Object[].class}, Object.class);
        return proxy.isSupported ? proxy.result : doInBackground((String[]) objArr);
    }

    public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
        if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7195, new Class[]{Object.class}, Void.TYPE).isSupported) {
            onPostExecute((String) obj);
        }
    }

    public /* bridge */ /* synthetic */ void onProgressUpdate(Object[] objArr) {
        if (!PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 7194, new Class[]{Object[].class}, Void.TYPE).isSupported) {
            onProgressUpdate((String[]) objArr);
        }
    }

    public LDNetDiagnoService() {
    }

    public LDNetDiagnoService(Context context, String theAppCode, String theAppName, String theAppVersion, String theUID, String theDeviceID, List<NetDiagnosisDomain> domains, String theCarrierName, String theISOCountryCode, String theMobileCountryCode, String theMobileNetCode, LDNetDiagnoListener theListener) {
        Context context2 = context;
        this._context = context2;
        this._appCode = theAppCode;
        this._appName = theAppName;
        this._appVersion = theAppVersion;
        this._UID = theUID;
        this._deviceID = theDeviceID;
        this._dormains = domains;
        this._carrierName = theCarrierName;
        this._ISOCountryCode = theISOCountryCode;
        this._MobileCountryCode = theMobileCountryCode;
        this._MobileNetCode = theMobileNetCode;
        this._netDiagnolistener = theListener;
        this._isRunning = false;
        this._remoteIpList = new ArrayList();
        this._telManager = (TelephonyManager) context2.getSystemService("phone");
        sExecutor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);
    }

    public String doInBackground(String... strArr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{strArr}, this, changeQuickRedirect, false, 7176, new Class[]{String[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (isCancelled()) {
            return null;
        }
        return startNetDiagnosis();
    }

    public void onPostExecute(String result) {
        if (!PatchProxy.proxy(new Object[]{result}, this, changeQuickRedirect, false, 7177, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!isCancelled()) {
                super.onPostExecute(result);
                recordStepInfo("\n网络诊断结束\n");
                stopNetDialogsis();
                LDNetDiagnoListener lDNetDiagnoListener = this._netDiagnolistener;
                if (lDNetDiagnoListener != null) {
                    lDNetDiagnoListener.OnNetDiagnoFinished(this._logInfo.toString());
                }
            }
        }
    }

    public void setOnNetDiagMessageKey(String callbackKey2) {
        this.callbackKey = callbackKey2;
    }

    public void onProgressUpdate(String... values) {
        if (!PatchProxy.proxy(new Object[]{values}, this, changeQuickRedirect, false, 7178, new Class[]{String[].class}, Void.TYPE).isSupported) {
            if (!isCancelled()) {
                super.onProgressUpdate(values);
                LDNetDiagnoListener lDNetDiagnoListener = this._netDiagnolistener;
                if (lDNetDiagnoListener != null) {
                    lDNetDiagnoListener.OnNetDiagnoUpdated(values[0]);
                }
            }
        }
    }

    public void onCancelled() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7179, new Class[0], Void.TYPE).isSupported) {
            stopNetDialogsis();
        }
    }

    public String startNetDiagnosis() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7180, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        this.isCancel = false;
        List<NetDiagnosisDomain> list = this._dormains;
        if (list == null || list.size() == 0) {
            return "";
        }
        this.publicSuccessCount = 0;
        this.privateFailCount = 0;
        this._isRunning = true;
        this._logInfo.setLength(0);
        recordStepInfo("                       Android                         \n");
        recordStepInfo("-------------------------------------------------------\n");
        recordStepInfo("开始诊断...\n");
        recordStepInfo("\n");
        recordCurrentAppVersion();
        recordLocalNetEnvironmentInfo();
        boolean z = this._isNetConnected;
        if (z) {
            if (!z || !this._isDomainParseOk || !this._isSocketConnected) {
                recordStepInfo("\n开始ping...");
                this._netPinger = new LDNetPing(this, 4);
                recordStepInfo("ping...127.0.0.1");
                this._netPinger.exec("127.0.0.1", false);
                recordStepInfo("ping本机IP..." + this._localIp);
                this._netPinger.exec(this._localIp, false);
                if (LDNetUtil.NETWORKTYPE_WIFI.equals(this._netType)) {
                    recordStepInfo("ping本地网关..." + this._gateWay);
                    this._netPinger.exec(this._gateWay, false);
                }
                recordStepInfo("ping本地DNS1..." + this._dns1);
                this._netPinger.exec(this._dns1, false);
                recordStepInfo("ping本地DNS2..." + this._dns2);
                this._netPinger.exec(this._dns2, false);
            }
            if (this._netPinger == null) {
                this._netPinger = new LDNetPing(this, 3);
            }
            for (NetDiagnosisDomain domain : this._dormains) {
                if ("private".equals(domain.category)) {
                    this.privateDomains.add(domain);
                } else {
                    this.publicDomains.add(domain);
                }
            }
            for (int i = 0; i < this.publicDomains.size(); i++) {
                doDiag(i, this.publicDomains.get(i));
            }
            for (int i2 = 0; i2 < this.privateDomains.size(); i2++) {
                doDiag(this.publicDomains.size() + i2, this.privateDomains.get(i2));
            }
            if (this.publicSuccessCount == 0) {
                uploadNetDiagMessage(403, 1, "", 0, (NetDiagnosisDomain) null);
            } else if (this.publicPingSuccessCount == 0) {
                uploadNetDiagMessage(404, 1, "", 0, (NetDiagnosisDomain) null);
            } else if (this.privateFailCount != 0) {
                uploadNetDiagMessage(BaseResp.ERR_CONNECT_FAIL, 1, "", 0, (NetDiagnosisDomain) null);
            } else {
                uploadNetDiagMessage(200, 1, "", 0, (NetDiagnosisDomain) null);
            }
            return this._logInfo.toString();
        }
        recordStepInfo("\n\n当前主机未联网,请检查网络！");
        uploadNetDiagMessage(401, 1, "", 0, (NetDiagnosisDomain) null);
        return this._logInfo.toString();
    }

    private void doDiag(int i, NetDiagnosisDomain netDiagnosisDomain) {
        URI uri;
        int port;
        if (!PatchProxy.proxy(new Object[]{new Integer(i), netDiagnosisDomain}, this, changeQuickRedirect, false, 7181, new Class[]{Integer.TYPE, NetDiagnosisDomain.class}, Void.TYPE).isSupported) {
            if (!this.isCancel) {
                uploadNetDiagMessage(102, i, "nslookup", ((i * 100) + 10) / this._dormains.size(), netDiagnosisDomain);
                if (netDiagnosisDomain.domain.startsWith(l.DEFAULT_SCHEME_NAME)) {
                    uri = URI.create(netDiagnosisDomain.domain);
                } else {
                    uri = URI.create(NetworkManager.MOCK_SCHEME_HTTP + netDiagnosisDomain.domain);
                }
                String domain = uri.getHost();
                int port2 = uri.getPort();
                if (port2 == 0) {
                    port = 80;
                } else {
                    port = port2;
                }
                recordStepInfo("#######################################################################\n");
                if (this._isNetConnected) {
                    recordStepInfo("远端域名:\t" + domain);
                    this._isDomainParseOk = parseDomain(domain);
                }
                if (netDiagnosisDomain.category.equals("public") && this._isDomainParseOk) {
                    this.publicSuccessCount++;
                }
                if (netDiagnosisDomain.category.equals("private") && !this._isDomainParseOk) {
                    this.privateFailCount++;
                }
                if (!this.isCancel) {
                    recordStepInfo("\n--------------------------------------------------------------");
                    recordStepInfo("\n开始TCP连接测试...");
                    uploadNetDiagMessage(102, i, "ping", ((i * 100) + 35) / this._dormains.size(), netDiagnosisDomain);
                    LDNetSocket instance = LDNetSocket.getInstance();
                    this._netSocker = instance;
                    instance._remoteInet = this._remoteInet;
                    instance._remoteIpList = this._remoteIpList;
                    instance.initListener(this);
                    LDNetSocket lDNetSocket = this._netSocker;
                    lDNetSocket.isCConn = this._isUseJNICConn;
                    this._isSocketConnected = lDNetSocket.exec(domain, port);
                    if (!netDiagnosisDomain.category.equals("public") || !this._isSocketConnected) {
                        boolean equals = netDiagnosisDomain.category.equals("private");
                    } else {
                        this.publicPingSuccessCount++;
                    }
                    if (!this.isCancel) {
                        recordStepInfo("\n开始traceroute...");
                        uploadNetDiagMessage(102, i, "traceroute", ((i * 100) + 75) / this._dormains.size(), netDiagnosisDomain);
                        LDNetTraceRoute instance2 = LDNetTraceRoute.getInstance();
                        this._traceRouter = instance2;
                        instance2.initListenter(this);
                        LDNetTraceRoute lDNetTraceRoute = this._traceRouter;
                        lDNetTraceRoute.isCTrace = this._isUseJNICTrace;
                        lDNetTraceRoute.startTraceRoute(domain);
                    }
                }
            }
        }
    }

    private void uploadNetDiagMessage(int code, int index, String action, int progress, NetDiagnosisDomain netDiagnosisDomain) {
        Object[] objArr = {new Integer(code), new Integer(index), action, new Integer(progress), netDiagnosisDomain};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7182, new Class[]{cls, cls, String.class, cls, NetDiagnosisDomain.class}, Void.TYPE).isSupported) {
            if (!this.isCancel) {
                JSONObject jsonObjectUpload = new JSONObject();
                try {
                    jsonObjectUpload.put("code", code);
                    jsonObjectUpload.put("progress", progress);
                    if (netDiagnosisDomain != null) {
                        jsonObjectUpload.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) netDiagnosisDomain.type);
                    }
                    jsonObjectUpload.put("action", (Object) action);
                    jsonObjectUpload.put(FirebaseAnalytics.Param.INDEX, index);
                    jsonObjectUpload.put(Progress.FILE_PATH, (Object) this._context.getFilesDir().getPath() + "/Arnoo_network_diagnose.log");
                    if (progress == 0) {
                        c.c().l(new NetWorkStatueCheckResultEvent(FileIOUtils.readFile2String(new File(this._context.getFilesDir().getPath() + "/Arnoo_network_diagnose.log")), jsonObjectUpload.toString(), "LDNetDiagnoService"));
                    }
                    b bVar = this._diposOfNetWorkCheckDelay;
                    if (bVar != null && !bVar.isDisposed()) {
                        this._diposOfNetWorkCheckDelay.dispose();
                    }
                    this._diposOfNetWorkCheckDelay = e.w(1).g(2, TimeUnit.SECONDS).c(com.leedarson.base.http.observer.l.c()).I(new b(jsonObjectUpload), a.c);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static /* synthetic */ void lambda$uploadNetDiagMessage$0(JSONObject jsonObjectUpload, Integer num) {
        Class[] clsArr = {JSONObject.class, Integer.class};
        if (!PatchProxy.proxy(new Object[]{jsonObjectUpload, num}, (Object) null, changeQuickRedirect, true, 7197, clsArr, Void.TYPE).isSupported) {
            c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BUSINESS, "onNetworkDiagnosisMessage", jsonObjectUpload.toString()));
        }
    }

    static /* synthetic */ void lambda$uploadNetDiagMessage$1(Throwable throwable) {
    }

    public void stopNetDialogsis() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7183, new Class[0], Void.TYPE).isSupported) {
            this.isCancel = true;
            if (this._isRunning) {
                LDNetSocket lDNetSocket = this._netSocker;
                if (lDNetSocket != null) {
                    lDNetSocket.resetInstance();
                    this._netSocker = null;
                }
                if (this._netPinger != null) {
                    this._netPinger = null;
                }
                LDNetTraceRoute lDNetTraceRoute = this._traceRouter;
                if (lDNetTraceRoute != null) {
                    lDNetTraceRoute.resetInstance();
                    this._traceRouter = null;
                }
                cancel(true);
                ThreadPoolExecutor threadPoolExecutor = sExecutor;
                if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
                    sExecutor.shutdown();
                    sExecutor = null;
                }
                this._isRunning = false;
            }
        }
    }

    public void setIfUseJNICConn(boolean use) {
        this._isUseJNICConn = use;
    }

    public void setIfUseJNICTrace(boolean use) {
        this._isUseJNICTrace = use;
    }

    public void printLogInfo() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7184, new Class[0], Void.TYPE).isSupported) {
            System.out.print(this._logInfo);
        }
    }

    private void recordStepInfo(String stepInfo) {
        if (!PatchProxy.proxy(new Object[]{stepInfo}, this, changeQuickRedirect, false, 7185, new Class[]{String.class}, Void.TYPE).isSupported) {
            StringBuilder sb = this._logInfo;
            sb.append(stepInfo + "\n");
            publishProgress(stepInfo + "\n");
        }
    }

    public void OnNetTraceFinished() {
    }

    public void OnNetTraceUpdated(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 7186, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (log != null) {
                LDNetTraceRoute lDNetTraceRoute = this._traceRouter;
                if (lDNetTraceRoute == null || !lDNetTraceRoute.isCTrace) {
                    recordStepInfo(log);
                    return;
                }
                if (log.contains("ms") || log.contains("***")) {
                    log = log + "\n";
                }
                this._logInfo.append(log);
                publishProgress(log);
            }
        }
    }

    public void OnNetSocketFinished(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 7187, new Class[]{String.class}, Void.TYPE).isSupported) {
            this._logInfo.append(log);
            publishProgress(log);
        }
    }

    public void OnNetSocketUpdated(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 7188, new Class[]{String.class}, Void.TYPE).isSupported) {
            this._logInfo.append(log);
            publishProgress(log);
        }
    }

    private void recordCurrentAppVersion() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7189, new Class[0], Void.TYPE).isSupported) {
            recordStepInfo("AppCode:\t" + this._appCode);
            recordStepInfo("AppName:\t" + this._appName);
            recordStepInfo("AppVersion:\t" + this._appVersion);
            recordStepInfo("DeviceType:\t" + Build.MANUFACTURER + ":" + Build.BRAND + ":" + Build.MODEL);
            StringBuilder sb = new StringBuilder();
            sb.append("SystemVersion:\t");
            sb.append(Build.VERSION.RELEASE);
            recordStepInfo(sb.toString());
            if (TextUtils.isEmpty(this._carrierName)) {
                this._carrierName = LDNetUtil.getMobileOperator(this._context);
            }
            recordStepInfo("Mobile operator:\t" + this._carrierName);
            if (this._telManager != null && TextUtils.isEmpty(this._ISOCountryCode)) {
                this._ISOCountryCode = this._telManager.getNetworkCountryIso();
            }
            recordStepInfo("ISOCountryCode:\t" + this._ISOCountryCode);
            if (this._telManager != null && TextUtils.isEmpty(this._MobileCountryCode)) {
                String tmp = this._telManager.getNetworkOperator();
                if (tmp.length() >= 3) {
                    this._MobileCountryCode = tmp.substring(0, 3);
                }
                if (tmp.length() >= 5) {
                    this._MobileNetCode = tmp.substring(3, 5);
                }
            }
            recordStepInfo("MobileCountryCode:\t" + this._MobileCountryCode);
            recordStepInfo("MobileNetworkCode:\t" + this._MobileNetCode + "\n");
            recordStepInfo("\n");
        }
    }

    private void recordLocalNetEnvironmentInfo() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7190, new Class[0], Void.TYPE).isSupported) {
            recordStepInfo("Start diagnosis...");
            if (LDNetUtil.isNetworkConnected(this._context).booleanValue()) {
                this._isNetConnected = true;
                recordStepInfo("当前是否联网:\t已联网");
            } else {
                this._isNetConnected = false;
                recordStepInfo("当前是否联网:\t未联网");
            }
            this._netType = LDNetUtil.getNetWorkType(this._context);
            recordStepInfo("当前联网类型:\t" + this._netType);
            if (this._isNetConnected) {
                if (LDNetUtil.NETWORKTYPE_WIFI.equals(this._netType)) {
                    this._localIp = LDNetUtil.getLocalIpByWifi(this._context);
                    this._gateWay = LDNetUtil.pingGateWayInWifi(this._context);
                } else {
                    this._localIp = LDNetUtil.getLocalIpBy3G();
                }
                recordStepInfo("本地IP:\t" + this._localIp);
            } else {
                recordStepInfo("本地IP:\t127.0.0.1");
            }
            if (this._gateWay != null) {
                recordStepInfo("本地网关:\t" + this._gateWay);
            }
            if (this._isNetConnected) {
                this._dns1 = LDNetUtil.getLocalDns("dns1");
                this._dns2 = LDNetUtil.getLocalDns("dns2");
                recordStepInfo("本地DNS:\t" + this._dns1 + "," + this._dns2);
                return;
            }
            recordStepInfo("本地DNS:\t0.0.0.0,0.0.0.0");
        }
    }

    private boolean parseDomain(String str) {
        String timeShow;
        String timeShow2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7191, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String _dormain = str;
        String ipString = "";
        Map<String, Object> map = LDNetUtil.getDomainIp(_dormain);
        String useTime = (String) map.get("useTime");
        this._remoteInet = (InetAddress[]) map.get("remoteInet");
        if (Integer.parseInt(useTime) > 5000) {
            timeShow = " (" + (Integer.parseInt(useTime) / 1000) + "s)";
        } else {
            timeShow = " (" + useTime + "ms)";
        }
        InetAddress[] inetAddressArr = this._remoteInet;
        if (inetAddressArr != null) {
            int len = inetAddressArr.length;
            for (int i = 0; i < len; i++) {
                this._remoteIpList.add(this._remoteInet[i].getHostAddress());
                ipString = ipString + this._remoteInet[i].getHostAddress() + ",";
            }
            recordStepInfo("DNS解析结果:\t" + ipString.substring(0, ipString.length() - 1) + timeShow);
            return true;
        } else if (Integer.parseInt(useTime) > 10000) {
            Map<String, Object> map2 = LDNetUtil.getDomainIp(_dormain);
            String useTime2 = (String) map2.get("useTime");
            this._remoteInet = (InetAddress[]) map2.get("remoteInet");
            if (Integer.parseInt(useTime2) > 5000) {
                timeShow2 = " (" + (Integer.parseInt(useTime2) / 1000) + "s)";
            } else {
                timeShow2 = " (" + useTime2 + "ms)";
            }
            InetAddress[] inetAddressArr2 = this._remoteInet;
            if (inetAddressArr2 != null) {
                int len2 = inetAddressArr2.length;
                for (int i2 = 0; i2 < len2; i2++) {
                    this._remoteIpList.add(this._remoteInet[i2].getHostAddress());
                    ipString = ipString + this._remoteInet[i2].getHostAddress() + ",";
                }
                recordStepInfo("DNS解析结果:\t" + ipString.substring(0, ipString.length() - 1) + timeShow2);
                return true;
            }
            recordStepInfo("DNS解析结果:\t解析失败" + timeShow2);
            return false;
        } else {
            recordStepInfo("DNS解析结果:\t解析失败" + timeShow);
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
        if (r3 == null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005b, code lost:
        r3.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0063, code lost:
        if (r3 == null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0066, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String requestOperatorInfo() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r4 = 0
            r5 = 7192(0x1c18, float:1.0078E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x001a
            java.lang.Object r0 = r0.result
            java.lang.String r0 = (java.lang.String) r0
            return r0
        L_0x001a:
            r0 = r8
            r1 = 0
            java.lang.String r2 = ""
            r3 = 0
            java.net.URL r4 = new java.net.URL     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            r4.<init>(r2)     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            java.net.URLConnection r5 = r4.openConnection()     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            r3 = r5
            java.lang.String r5 = "GET"
            r3.setRequestMethod(r5)     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            r5 = 10000(0x2710, float:1.4013E-41)
            r3.setConnectTimeout(r5)     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            r3.connect()     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            int r5 = r3.getResponseCode()     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            r6 = 200(0xc8, float:2.8E-43)
            if (r5 != r6) goto L_0x004d
            java.io.InputStream r6 = r3.getInputStream()     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            java.lang.String r6 = com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil.getStringFromStream(r6)     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
            r1 = r6
            r3.disconnect()     // Catch:{ MalformedURLException -> 0x005f, IOException -> 0x0055 }
        L_0x004d:
            r3.disconnect()
            return r1
        L_0x0053:
            r4 = move-exception
            goto L_0x0067
        L_0x0055:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x0053 }
            if (r3 == 0) goto L_0x0066
        L_0x005b:
            r3.disconnect()
            goto L_0x0066
        L_0x005f:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x0053 }
            if (r3 == 0) goto L_0x0066
            goto L_0x005b
        L_0x0066:
            return r1
        L_0x0067:
            if (r3 == 0) goto L_0x006c
            r3.disconnect()
        L_0x006c:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetDiagnoService.requestOperatorInfo():java.lang.String");
    }

    public void OnNetPingFinished(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 7193, new Class[]{String.class}, Void.TYPE).isSupported) {
            recordStepInfo(log);
        }
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return sExecutor;
    }
}
