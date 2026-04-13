package com.leedarson.base.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.asm.Opcodes;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.List;
import timber.log.a;

/* compiled from: WifiUtil */
public class x {
    private static final HashMap<String, ScanResult> a = new HashMap<>();
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String d(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 623, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return g(context, (String) null)[0];
    }

    public static int c(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 624, new Class[]{Context.class}, Integer.TYPE);
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
                    return b(result.frequency);
                }
            }
            return -1;
        } catch (Exception e) {
            a.c("getCurrentWifi-Channel:  e=" + e.toString(), new Object[0]);
            return -1;
        }
    }

    private static int b(int frequency) {
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

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a2 A[Catch:{ Exception -> 0x00b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a8 A[Catch:{ Exception -> 0x00b5 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String[] g(android.content.Context r12, java.lang.String r13) {
        /*
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r12
            r9 = 1
            r1[r9] = r13
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r6[r8] = r2
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r9] = r2
            java.lang.Class<java.lang.String[]> r7 = java.lang.String[].class
            r2 = 0
            r4 = 1
            r5 = 625(0x271, float:8.76E-43)
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0028
            java.lang.Object r12 = r1.result
            java.lang.String[] r12 = (java.lang.String[]) r12
            return r12
        L_0x0028:
            java.lang.String r1 = ""
            java.lang.String r2 = "unknown"
            java.lang.String r3 = "0"
            java.lang.String r4 = ""
            java.lang.String r5 = ""
            android.content.Context r6 = r12.getApplicationContext()     // Catch:{ Exception -> 0x00b5 }
            java.lang.String r7 = "wifi"
            java.lang.Object r6 = r6.getSystemService(r7)     // Catch:{ Exception -> 0x00b5 }
            android.net.wifi.WifiManager r6 = (android.net.wifi.WifiManager) r6     // Catch:{ Exception -> 0x00b5 }
            int r7 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x00b5 }
            r10 = 26
            if (r7 <= r10) goto L_0x0079
            r10 = 28
            if (r7 < r10) goto L_0x004b
            goto L_0x0079
        L_0x004b:
            android.content.Context r7 = r12.getApplicationContext()     // Catch:{ Exception -> 0x00b5 }
            java.lang.String r10 = "connectivity"
            java.lang.Object r7 = r7.getSystemService(r10)     // Catch:{ Exception -> 0x00b5 }
            android.net.ConnectivityManager r7 = (android.net.ConnectivityManager) r7     // Catch:{ Exception -> 0x00b5 }
            if (r7 == 0) goto L_0x0073
            android.net.NetworkInfo r10 = r7.getActiveNetworkInfo()     // Catch:{ Exception -> 0x00b5 }
            boolean r11 = r10.isConnected()     // Catch:{ Exception -> 0x00b5 }
            if (r11 == 0) goto L_0x009c
            java.lang.String r11 = r10.getExtraInfo()     // Catch:{ Exception -> 0x00b5 }
            if (r11 == 0) goto L_0x009c
            java.lang.String r11 = r10.getExtraInfo()     // Catch:{ Exception -> 0x00b5 }
            java.lang.String r11 = j(r11)     // Catch:{ Exception -> 0x00b5 }
            r1 = r11
            goto L_0x009c
        L_0x0073:
            java.lang.AssertionError r10 = new java.lang.AssertionError     // Catch:{ Exception -> 0x00b5 }
            r10.<init>()     // Catch:{ Exception -> 0x00b5 }
            throw r10     // Catch:{ Exception -> 0x00b5 }
        L_0x0079:
            if (r6 == 0) goto L_0x00af
            android.net.wifi.WifiInfo r7 = r6.getConnectionInfo()     // Catch:{ Exception -> 0x00b5 }
            java.lang.String r10 = r7.getSSID()     // Catch:{ Exception -> 0x00b5 }
            java.lang.String r10 = j(r10)     // Catch:{ Exception -> 0x00b5 }
            r1 = r10
            java.lang.String r10 = f(r12, r7)     // Catch:{ Exception -> 0x00b5 }
            r2 = r10
            int r10 = r7.getRssi()     // Catch:{ Exception -> 0x00b5 }
            java.lang.String r10 = java.lang.String.valueOf(r10)     // Catch:{ Exception -> 0x00b5 }
            r3 = r10
            java.lang.String r10 = r7.getBSSID()     // Catch:{ Exception -> 0x00b5 }
            r4 = r10
        L_0x009c:
            boolean r7 = android.text.TextUtils.isEmpty(r13)     // Catch:{ Exception -> 0x00b5 }
            if (r7 == 0) goto L_0x00a8
            java.lang.String r7 = e(r6, r1)     // Catch:{ Exception -> 0x00b5 }
            r5 = r7
            goto L_0x00ae
        L_0x00a8:
            r1 = r13
            java.lang.String r7 = e(r6, r13)     // Catch:{ Exception -> 0x00b5 }
            r5 = r7
        L_0x00ae:
            goto L_0x00b9
        L_0x00af:
            java.lang.AssertionError r7 = new java.lang.AssertionError     // Catch:{ Exception -> 0x00b5 }
            r7.<init>()     // Catch:{ Exception -> 0x00b5 }
            throw r7     // Catch:{ Exception -> 0x00b5 }
        L_0x00b5:
            r6 = move-exception
            r6.printStackTrace()
        L_0x00b9:
            r6 = 6
            java.lang.String[] r6 = new java.lang.String[r6]
            r6[r8] = r1
            r6[r9] = r2
            r6[r0] = r3
            r0 = 3
            r6[r0] = r4
            r0 = 4
            r6[r0] = r5
            r0 = 5
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            com.leedarson.base.application.BaseApplication r8 = com.leedarson.base.application.BaseApplication.b()
            int r8 = c(r8)
            r7.append(r8)
            java.lang.String r8 = ""
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6[r0] = r7
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.utils.x.g(android.content.Context, java.lang.String):java.lang.String[]");
    }

    private static String j(String originSsid) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{originSsid}, (Object) null, changeQuickRedirect, true, 626, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (originSsid.charAt(0) == '\"') {
            originSsid = originSsid.substring(1);
        }
        if (originSsid.endsWith("\"")) {
            return originSsid.substring(0, originSsid.length() - 1);
        }
        return originSsid;
    }

    private static String e(WifiManager wifiManager, String ssid) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{wifiManager, ssid}, (Object) null, changeQuickRedirect2, true, 627, new Class[]{WifiManager.class, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        HashMap<String, ScanResult> hashMap = a;
        if (hashMap.containsKey(ssid)) {
            return hashMap.get(ssid).capabilities;
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (scanResults.size() <= 0) {
            return "unknown";
        }
        for (ScanResult scanResult : scanResults) {
            if (!TextUtils.isEmpty(scanResult.SSID)) {
                a.put(scanResult.SSID, scanResult);
            }
        }
        HashMap<String, ScanResult> hashMap2 = a;
        if (hashMap2.containsKey(ssid)) {
            return hashMap2.get(ssid).capabilities;
        }
        return "unknown";
    }

    public static String f(Context context, WifiInfo info) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, info}, (Object) null, changeQuickRedirect, true, 628, new Class[]{Context.class, WifiInfo.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (!h(info.getFrequency())) {
            return "2.4G";
        }
        return i(context, info.getSSID().replace("\"", "")) ? "2.4G/5G" : "5G";
    }

    public static String a(int freq) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(freq)}, (Object) null, changeQuickRedirect, true, 629, new Class[]{Integer.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return h(freq) ? "5G" : "2.4G";
    }

    public static boolean h(int freq) {
        return freq > 4900 && freq < 5900;
    }

    public static boolean i(Context context, String ssid) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, ssid}, (Object) null, changeQuickRedirect, true, 630, new Class[]{Context.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int count = 0;
        for (ScanResult result : ((WifiManager) context.getSystemService("wifi")).getScanResults()) {
            if (ssid.equals(result.SSID)) {
                count++;
            }
        }
        if ("TP-LINK_B64A".equals(ssid)) {
            Log.e("LdsCore", "count:" + count);
        }
        if (count >= 2) {
            return true;
        }
        return false;
    }
}
