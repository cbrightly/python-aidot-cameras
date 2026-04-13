package com.leedarson.serviceimpl.system;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.leedarson.base.utils.x;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import io.reactivex.functions.e;
import java.util.List;
import meshsdk.BaseResp;
import org.json.JSONArray;
import org.json.JSONObject;

public class WifiScanner {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a = "WifiScanner";
    private WifiResultReceiver b;
    private Context c;
    /* access modifiers changed from: private */
    public WifiManager d;
    /* access modifiers changed from: private */
    public c e;

    public interface c {
        void a(JSONArray jSONArray, int i);
    }

    static /* synthetic */ void a(WifiScanner x0, String x1) {
        Class[] clsArr = {WifiScanner.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8877, clsArr, Void.TYPE).isSupported) {
            x0.f(x1);
        }
    }

    static /* synthetic */ JSONArray d(WifiScanner x0, List x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8878, new Class[]{WifiScanner.class, List.class}, JSONArray.class);
        return proxy.isSupported ? (JSONArray) proxy.result : x0.g(x1);
    }

    public WifiScanner(Context context) {
        this.c = context;
        this.d = (WifiManager) context.getSystemService("wifi");
    }

    public void i(Activity activity, boolean z, c cVar) {
        Object[] objArr = {activity, new Byte(z ? (byte) 1 : 0), cVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8869, new Class[]{Activity.class, Boolean.TYPE, c.class}, Void.TYPE).isSupported) {
            boolean rescan = z;
            c resultListener = cVar;
            WifiManager wifiManager = this.d;
            if (wifiManager == null || !wifiManager.isWifiEnabled()) {
                resultListener.a(new JSONArray(), BaseResp.ERR_MSG_SEND_FAIL);
            } else if (!k.h(this.c)) {
                resultListener.a(new JSONArray(), BaseResp.ERR_WAIT_RESPONSE);
            } else {
                com.tbruyelle.rxpermissions2.b rxPermissions = new com.tbruyelle.rxpermissions2.b(activity);
                rxPermissions.l("android.permission.ACCESS_FINE_LOCATION").Y(new a(rescan, resultListener, rxPermissions, activity), new b());
            }
        }
    }

    public class a implements e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean c;
        final /* synthetic */ c d;
        final /* synthetic */ com.tbruyelle.rxpermissions2.b f;
        final /* synthetic */ Activity q;

        a(boolean z, c cVar, com.tbruyelle.rxpermissions2.b bVar, Activity activity) {
            this.c = z;
            this.d = cVar;
            this.f = bVar;
            this.q = activity;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8880, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 8879, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                if (permission.b) {
                    WifiScanner.a(WifiScanner.this, "定位权限已授权");
                    WifiScanner.this.j(this.c, this.d);
                    return;
                }
                WifiScanner wifiScanner = WifiScanner.this;
                WifiScanner.a(wifiScanner, "定位权限被拒绝 shouldShowRequestPermissionRationale:" + this.f.o(this.q, "android.permission.ACCESS_FINE_LOCATION"));
                this.d.a(new JSONArray(), 403);
            }
        }
    }

    public class b implements e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8881, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
        }
    }

    public void j(boolean rescan, c resultListener) {
        Object[] objArr = {new Byte(rescan ? (byte) 1 : 0), resultListener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8870, new Class[]{Boolean.TYPE, c.class}, Void.TYPE).isSupported) {
            this.e = resultListener;
            if (!rescan) {
                JSONArray array = e();
                if (array.length() <= 0 || resultListener == null) {
                    f("getSystemList 返回空 ,发起扫描");
                } else {
                    resultListener.a(array, 200);
                    return;
                }
            }
            h();
            if (this.d.startScan()) {
                f("wifi 开始扫描");
            } else {
                f("扫描失败");
            }
        }
    }

    private JSONArray e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8871, new Class[0], JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        List<ScanResult> scanResults = this.d.getScanResults();
        f("getSystemList:" + scanResults.size());
        return g(scanResults);
    }

    private JSONArray g(List<ScanResult> scanResults) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{scanResults}, this, changeQuickRedirect, false, 8872, new Class[]{List.class}, JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        JSONArray joWifiListData = new JSONArray();
        try {
            for (ScanResult scanResult : scanResults) {
                if (!TextUtils.isEmpty(scanResult.SSID)) {
                    JSONObject joResult = new JSONObject();
                    joResult.put("name", (Object) scanResult.SSID);
                    joResult.put("mac", (Object) scanResult.BSSID);
                    joResult.put("bssid", (Object) scanResult.BSSID);
                    joResult.put("frequency", (Object) x.a(scanResult.frequency));
                    joResult.put("enctype", (Object) scanResult.capabilities);
                    joResult.put("rssi", scanResult.level);
                    joWifiListData.put((Object) joResult);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return joWifiListData;
    }

    public void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8873, new Class[0], Void.TYPE).isSupported) {
            f("停止扫描，解除广播监听");
            l();
        }
    }

    private void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8874, new Class[0], Void.TYPE).isSupported) {
            try {
                f("unregisterReceiver 注销wifi广播接收");
                WifiResultReceiver wifiResultReceiver = this.b;
                if (wifiResultReceiver != null) {
                    this.c.unregisterReceiver(wifiResultReceiver);
                    this.b = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8875, new Class[0], Void.TYPE).isSupported) {
            f("registerReceiver 注册wifi广播接收");
            this.b = new WifiResultReceiver();
            this.c.registerReceiver(this.b, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        }
    }

    public class WifiResultReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;

        WifiResultReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            Class[] clsArr = {Context.class, Intent.class};
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 8882, clsArr, Void.TYPE).isSupported) {
                if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
                    List<ScanResult> results = WifiScanner.this.d.getScanResults();
                    WifiScanner wifiScanner = WifiScanner.this;
                    WifiScanner.a(wifiScanner, "wifi 扫描结果" + results.size());
                    if (WifiScanner.this.e != null) {
                        try {
                            WifiScanner.this.e.a(WifiScanner.d(WifiScanner.this, results), 200);
                        } catch (Exception e) {
                            e.printStackTrace();
                            WifiScanner wifiScanner2 = WifiScanner.this;
                            WifiScanner.a(wifiScanner2, "parseWifiList 异常:" + e.getMessage());
                        }
                    }
                    WifiScanner.this.k();
                }
            }
        }
    }

    private void f(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 8876, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(this.a).a(log, new Object[0]);
        }
    }
}
