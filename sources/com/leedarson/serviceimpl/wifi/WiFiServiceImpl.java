package com.leedarson.serviceimpl.wifi;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.leedarson.base.utils.x;
import com.leedarson.serviceinterface.WiFiService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.greenrobot.eventbus.c;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

public class WiFiServiceImpl implements WiFiService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private Activity b;
    private ConcurrentHashMap<String, JSONObject> c = new ConcurrentHashMap<>();

    static /* synthetic */ void a(WiFiServiceImpl x0, String[] x1, String x2) {
        Class[] clsArr = {WiFiServiceImpl.class, String[].class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 9162, clsArr, Void.TYPE).isSupported) {
            x0.h(x1, x2);
        }
    }

    public void handleData(String callbackKey, Activity activity, String action, String data) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, activity, action, data}, this, changeQuickRedirect, false, 9156, new Class[]{cls, Activity.class, cls, cls}, Void.TYPE).isSupported) {
            this.b = activity;
            char c2 = 65535;
            switch (action.hashCode()) {
                case -815366715:
                    if (action.equals("getPermission")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case 631266189:
                    if (action.equals("getRouterInfo")) {
                        c2 = 1;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    JSONObject joGetPermission = new JSONObject();
                    JSONObject joGetPermissionData = new JSONObject();
                    WifiManager manager = (WifiManager) this.a.getSystemService("wifi");
                    try {
                        joGetPermission.put("code", 200);
                        if (manager.isWifiEnabled()) {
                            joGetPermissionData.put("status", 1);
                        } else {
                            joGetPermissionData.put("status", 0);
                        }
                        joGetPermission.put("data", (Object) joGetPermissionData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    c.c().l(new JsBridgeCallbackEvent(callbackKey, joGetPermission.toString()));
                    return;
                case 1:
                    try {
                        getRouterInfo(callbackKey, new JSONObject(data).optString("ssid"));
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void init(Context context) {
        this.a = context;
    }

    public void getRouterInfo(String callbackKey, String targetSSID) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, targetSSID}, this, changeQuickRedirect, false, 9157, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                if (TextUtils.isEmpty(callbackKey)) {
                    String[] permsLocation = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
                    if (Build.VERSION.SDK_INT >= 27) {
                        if (!k(permsLocation)) {
                            c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":403}"));
                            return;
                        }
                    }
                    if (!w.f(this.a)) {
                        c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":406}"));
                    } else {
                        String[] currentSSIDFreq = x.g(this.a, targetSSID);
                        if (!TextUtils.isEmpty(currentSSIDFreq[0])) {
                            new a(currentSSIDFreq, callbackKey).start();
                        } else if (!l(this.b)) {
                            c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":401}"));
                        } else {
                            c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":403}"));
                        }
                    }
                    return;
                }
                JSONObject infoObj = new JSONObject();
                JSONArray dataArray = new JSONArray();
                for (Map.Entry<String, JSONObject> entry : this.c.entrySet()) {
                    a.b g = timber.log.a.g("WiFiServiceImpl");
                    g.a("Key = " + entry.getKey() + ", Value = " + entry.getValue(), new Object[0]);
                    if (TextUtils.isEmpty(targetSSID)) {
                        dataArray.put((Object) entry.getValue());
                    } else if (!TextUtils.isEmpty(entry.getKey()) && entry.getKey().contains(targetSSID)) {
                        dataArray.put((Object) entry.getValue());
                    }
                }
                infoObj.put("data", (Object) dataArray);
                infoObj.put("code", 200);
                c.c().l(new JsBridgeCallbackEvent(callbackKey, infoObj.toString()));
            } catch (Exception ex) {
                timber.log.a.e(ex.getMessage(), new Object[0]);
            }
        }
    }

    public class a extends Thread {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] c;
        final /* synthetic */ String d;

        a(String[] strArr, String str) {
            this.c = strArr;
            this.d = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9163, new Class[0], Void.TYPE).isSupported) {
                WiFiServiceImpl.a(WiFiServiceImpl.this, this.c, this.d);
            }
        }
    }

    private boolean l(Context mContext) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mContext}, this, changeQuickRedirect, false, 9158, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        NetworkInfo activeNetInfo = ((ConnectivityManager) mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 1;
    }

    public boolean k(String[] perms) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{perms}, this, changeQuickRedirect, false, 9159, new Class[]{String[].class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : EasyPermissions.a(this.a, perms);
    }

    private void h(String[] strArr, String str) {
        net.sbbi.upnp.impls.a[] IGDs;
        if (!PatchProxy.proxy(new Object[]{strArr, str}, this, changeQuickRedirect, false, 9160, new Class[]{String[].class, String.class}, Void.TYPE).isSupported) {
            String callbackKey = str;
            String[] currentSSIDFreq = strArr;
            char c2 = 5000;
            JSONObject wifiInfoObj = new JSONObject();
            try {
                net.sbbi.upnp.impls.a[] IGDs2 = net.sbbi.upnp.impls.a.a(5000);
                String str2 = "enctype";
                String str3 = "wifiInfoObj : ";
                if (IGDs2 != null) {
                    int i = 0;
                    while (i < IGDs2.length) {
                        net.sbbi.upnp.impls.a igd = IGDs2[i];
                        net.sbbi.upnp.devices.c upnpRootDevice = igd.b();
                        String callbackKey2 = callbackKey;
                        char c3 = c2;
                        try {
                            wifiInfoObj.put("UDN", (Object) upnpRootDevice.l());
                            wifiInfoObj.put("deviceType", (Object) upnpRootDevice.b());
                            wifiInfoObj.put("friendlyName", (Object) upnpRootDevice.c());
                            wifiInfoObj.put("manufacturer", (Object) upnpRootDevice.d());
                            wifiInfoObj.put("manufacturerURL", (Object) upnpRootDevice.e());
                            wifiInfoObj.put("modelDescription", (Object) upnpRootDevice.f());
                            wifiInfoObj.put("modelName", (Object) upnpRootDevice.g());
                            wifiInfoObj.put("modelNumber", (Object) upnpRootDevice.h());
                            wifiInfoObj.put("modelURL", (Object) upnpRootDevice.i());
                            wifiInfoObj.put("serialNumber", (Object) upnpRootDevice.j());
                            String ssid = null;
                            String bssid = null;
                            if (currentSSIDFreq != null) {
                                String ssid2 = currentSSIDFreq[0];
                                bssid = currentSSIDFreq[3];
                                wifiInfoObj.put("ssid", (Object) ssid2);
                                wifiInfoObj.put("frequency", (Object) currentSSIDFreq[1]);
                                wifiInfoObj.put("channel", j(BaseApplication.b()));
                                IGDs = IGDs2;
                                wifiInfoObj.put("rssi", (Object) currentSSIDFreq[2]);
                                wifiInfoObj.put("bssid", (Object) bssid);
                                wifiInfoObj.put(str2, (Object) currentSSIDFreq[4]);
                                ssid = ssid2;
                            } else {
                                IGDs = IGDs2;
                            }
                            a.b g = timber.log.a.g("WiFiServiceImpl");
                            net.sbbi.upnp.impls.a aVar = igd;
                            StringBuilder sb = new StringBuilder();
                            sb.append(str3);
                            String str4 = str3;
                            sb.append(wifiInfoObj.toString());
                            String str5 = str2;
                            g.h(sb.toString(), new Object[0]);
                            if (!TextUtils.isEmpty(ssid)) {
                                this.c.put(ssid + "-" + bssid, wifiInfoObj);
                            }
                            i++;
                            callbackKey = callbackKey2;
                            c2 = c3;
                            IGDs2 = IGDs;
                            str3 = str4;
                            str2 = str5;
                        } catch (Exception e) {
                            ex = e;
                            timber.log.a.e(ex.getMessage(), new Object[0]);
                        }
                    }
                    char c4 = c2;
                    net.sbbi.upnp.impls.a[] aVarArr = IGDs2;
                    return;
                }
                net.sbbi.upnp.impls.a[] aVarArr2 = IGDs2;
                String str6 = str3;
                String str7 = str2;
                String ssid3 = null;
                String bssid2 = null;
                if (currentSSIDFreq != null) {
                    ssid3 = currentSSIDFreq[0];
                    bssid2 = currentSSIDFreq[3];
                    wifiInfoObj.put("ssid", (Object) ssid3);
                    wifiInfoObj.put("frequency", (Object) currentSSIDFreq[1]);
                    wifiInfoObj.put("channel", j(BaseApplication.b()));
                    wifiInfoObj.put("rssi", (Object) currentSSIDFreq[2]);
                    wifiInfoObj.put("bssid", (Object) bssid2);
                    wifiInfoObj.put(str7, (Object) currentSSIDFreq[4]);
                }
                timber.log.a.g("WiFiServiceImpl").h(str6 + wifiInfoObj.toString(), new Object[0]);
                if (!TextUtils.isEmpty(ssid3)) {
                    this.c.put(ssid3 + "-" + bssid2, wifiInfoObj);
                }
            } catch (Exception e2) {
                ex = e2;
                String str8 = callbackKey;
                char c5 = c2;
                timber.log.a.e(ex.getMessage(), new Object[0]);
            }
        }
    }

    public static int j(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9161, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo == null) {
                return -1;
            }
            for (ScanResult result : wifiManager.getScanResults()) {
                if (result.BSSID.equalsIgnoreCase(wifiInfo.getBSSID()) && result.SSID.equalsIgnoreCase(wifiInfo.getSSID().substring(1, wifiInfo.getSSID().length() - 1))) {
                    return i(result.frequency);
                }
            }
            return -1;
        } catch (Exception e) {
            timber.log.a.c("getCurrentWifi-Channel:  e=" + e.toString(), new Object[0]);
            return -1;
        }
    }

    private static int i(int frequency) {
        switch (frequency) {
            case 2412:
                return 1;
            case 2417:
                return 2;
            case 2422:
                return 3;
            case 2427:
                return 4;
            case 2432:
                return 5;
            case 2437:
                return 6;
            case 2442:
                return 7;
            case 2447:
                return 8;
            case 2452:
                return 9;
            case 2457:
                return 10;
            case 2462:
                return 11;
            case 2467:
                return 12;
            case 2472:
                return 13;
            case 2484:
                return 14;
            case 5745:
                return Opcodes.FCMPL;
            case 5765:
                return Opcodes.IFEQ;
            case 5785:
                return 157;
            case 5805:
                return Opcodes.IF_ICMPLT;
            case 5825:
                return Opcodes.IF_ACMPEQ;
            default:
                return -1;
        }
    }
}
