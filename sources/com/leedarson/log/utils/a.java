package com.leedarson.log.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: IntenetUtil */
public class a {
    static String a;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String a(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 1381, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connManager == null) {
                return "NoNetwork";
            }
            NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
            if (activeNetInfo != null) {
                if (activeNetInfo.isAvailable()) {
                    NetworkInfo wifiInfo = connManager.getNetworkInfo(1);
                    if (wifiInfo != null) {
                        NetworkInfo.State state = wifiInfo.getState();
                        if (state != null && (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING)) {
                            return "“WifiNetwork(xxx)".replace("xxx", "" + b());
                        }
                    }
                    NetworkInfo networkInfo = connManager.getNetworkInfo(0);
                    if (networkInfo == null) {
                        return "Unknown";
                    }
                    NetworkInfo.State state2 = networkInfo.getState();
                    String strSubTypeName = networkInfo.getSubtypeName();
                    if (state2 == null) {
                        return "Unknown";
                    }
                    if (state2 != NetworkInfo.State.CONNECTED && state2 != NetworkInfo.State.CONNECTING) {
                        return "Unknown";
                    }
                    switch (activeNetInfo.getSubtype()) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            return "MobileNetwork(xxx)".replace("xxx", "2G_" + strSubTypeName);
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            return "MobileNetwork(xxx)".replace("xxx", "3G_" + strSubTypeName);
                        case 13:
                            return "MobileNetwork(xxx)".replace("xxx", "4G_" + strSubTypeName);
                        case 20:
                            return "MobileNetwork(xxx)".replace("xxx", "5G_" + strSubTypeName);
                        default:
                            if (!strSubTypeName.equalsIgnoreCase("TD-SCDMA") && !strSubTypeName.equalsIgnoreCase("WCDMA")) {
                                if (!strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                    return "MobileNetwork(xxx)".replace("xxx", "Unknown_" + strSubTypeName);
                                }
                            }
                            return "MobileNetwork(xxx)".replace("xxx", "3G_" + strSubTypeName);
                    }
                    e.printStackTrace();
                    Log.e("intentUtil", "getNetworkState --->" + e.toString());
                    return "Unknown";
                }
            }
            return "NoNetwork";
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("intentUtil", "getNetworkState --->" + e.toString());
            return "Unknown";
        }
    }

    public static String b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1382, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            if (TextUtils.isEmpty(a)) {
                a = SharePreferenceUtils.getPrefString(BaseApplication.b(), "repositoryName", "");
            }
            if ("leedarson-Leedarson".equals(a) || "leedarson-NewLeedarson".equals(a)) {
                if (BaseApplication.d) {
                    return "Unknown";
                }
                if (!BaseApplication.b().p3) {
                    return "Unknown";
                }
            }
            int i = Build.VERSION.SDK_INT;
            if (i <= 26 || i >= 28) {
                WifiManager mWifiManager = (WifiManager) BaseApplication.b().getApplicationContext().getSystemService("wifi");
                if (mWifiManager != null) {
                    WifiInfo info = mWifiManager.getConnectionInfo();
                    if (i < 19) {
                        return info.getSSID();
                    }
                    return info.getSSID().replace("\"", "");
                }
                throw new AssertionError();
            }
            if (i == 27) {
                ConnectivityManager connManager = (ConnectivityManager) BaseApplication.b().getApplicationContext().getSystemService("connectivity");
                if (connManager != null) {
                    NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
                    if (networkInfo.isConnected() && networkInfo.getExtraInfo() != null) {
                        return networkInfo.getExtraInfo().replace("\"", "");
                    }
                } else {
                    throw new AssertionError();
                }
            }
            return "Unknown";
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getWifiSSID", "get wifi ssid==null");
        }
    }
}
