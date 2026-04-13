package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import androidx.annotation.RequiresPermission;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public final class NetworkUtils {

    public enum a {
        NETWORK_ETHERNET,
        NETWORK_WIFI,
        NETWORK_5G,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    public interface b {
        void onConnected(a aVar);

        void onDisconnected();
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    public static a d() {
        if (e()) {
            return a.NETWORK_ETHERNET;
        }
        NetworkInfo info = a();
        if (info == null || !info.isAvailable()) {
            return a.NETWORK_NO;
        }
        if (info.getType() == 1) {
            return a.NETWORK_WIFI;
        }
        if (info.getType() != 0) {
            return a.NETWORK_UNKNOWN;
        }
        switch (info.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return a.NETWORK_2G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return a.NETWORK_3G;
            case 13:
            case 18:
                return a.NETWORK_4G;
            case 20:
                return a.NETWORK_5G;
            default:
                String subtypeName = info.getSubtypeName();
                if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) {
                    return a.NETWORK_3G;
                }
                return a.NETWORK_UNKNOWN;
        }
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    private static boolean e() {
        NetworkInfo info;
        NetworkInfo.State state;
        ConnectivityManager cm = (ConnectivityManager) f0.a().getSystemService("connectivity");
        if (cm == null || (info = cm.getNetworkInfo(9)) == null || (state = info.getState()) == null) {
            return false;
        }
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return true;
        }
        return false;
    }

    @RequiresPermission("android.permission.ACCESS_NETWORK_STATE")
    private static NetworkInfo a() {
        ConnectivityManager cm = (ConnectivityManager) f0.a().getSystemService("connectivity");
        if (cm == null) {
            return null;
        }
        return cm.getActiveNetworkInfo();
    }

    @RequiresPermission("android.permission.INTERNET")
    public static String b(boolean useIPv4) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> adds = new LinkedList<>();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (ni.isUp()) {
                    if (!ni.isLoopback()) {
                        Enumeration<InetAddress> addresses = ni.getInetAddresses();
                        while (addresses.hasMoreElements()) {
                            adds.addFirst(addresses.nextElement());
                        }
                    }
                }
            }
            Iterator it = adds.iterator();
            while (it.hasNext()) {
                InetAddress add = (InetAddress) it.next();
                if (!add.isLoopbackAddress()) {
                    String hostAddress = add.getHostAddress();
                    boolean isIPv4 = hostAddress.indexOf(58) < 0;
                    if (useIPv4) {
                        if (isIPv4) {
                            return hostAddress;
                        }
                    } else if (!isIPv4) {
                        int index = hostAddress.indexOf(37);
                        if (index < 0) {
                            return hostAddress.toUpperCase();
                        }
                        return hostAddress.substring(0, index).toUpperCase();
                    }
                }
            }
            return "";
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static String c() {
        WifiManager wm = (WifiManager) f0.a().getSystemService("wifi");
        if (wm == null) {
            return "";
        }
        return Formatter.formatIpAddress(wm.getDhcpInfo().ipAddress);
    }

    public static void registerNetworkStatusChangedListener(b listener) {
        NetworkChangedReceiver.e().registerListener(listener);
    }

    public static void unregisterNetworkStatusChangedListener(b listener) {
        NetworkChangedReceiver.e().unregisterListener(listener);
    }

    public static final class NetworkChangedReceiver extends BroadcastReceiver {
        /* access modifiers changed from: private */
        public a a;
        /* access modifiers changed from: private */
        public Set<b> b = new HashSet();

        public static class d {
            /* access modifiers changed from: private */
            public static final NetworkChangedReceiver a = new NetworkChangedReceiver();
        }

        /* access modifiers changed from: private */
        public static NetworkChangedReceiver e() {
            return d.a;
        }

        public class a implements Runnable {
            final /* synthetic */ b c;

            a(b bVar) {
                this.c = bVar;
            }

            @SuppressLint({"MissingPermission"})
            public void run() {
                int preSize = NetworkChangedReceiver.this.b.size();
                NetworkChangedReceiver.this.b.add(this.c);
                if (preSize == 0 && NetworkChangedReceiver.this.b.size() == 1) {
                    a unused = NetworkChangedReceiver.this.a = NetworkUtils.d();
                    f0.a().registerReceiver(NetworkChangedReceiver.e(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void registerListener(b listener) {
            if (listener != null) {
                h0.I(new a(listener));
            }
        }

        public class b implements Runnable {
            final /* synthetic */ b c;

            b(b bVar) {
                this.c = bVar;
            }

            public void run() {
                int preSize = NetworkChangedReceiver.this.b.size();
                NetworkChangedReceiver.this.b.remove(this.c);
                if (preSize == 1 && NetworkChangedReceiver.this.b.size() == 0) {
                    f0.a().unregisterReceiver(NetworkChangedReceiver.e());
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void unregisterListener(b listener) {
            if (listener != null) {
                h0.I(new b(listener));
            }
        }

        @SuppressLint({"MissingPermission"})
        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                h0.J(new c(), 1000);
            }
        }

        public class c implements Runnable {
            c() {
            }

            public void run() {
                a networkType = NetworkUtils.d();
                if (NetworkChangedReceiver.this.a != networkType) {
                    a unused = NetworkChangedReceiver.this.a = networkType;
                    if (networkType == a.NETWORK_NO) {
                        for (b listener : NetworkChangedReceiver.this.b) {
                            listener.onDisconnected();
                        }
                        return;
                    }
                    for (b listener2 : NetworkChangedReceiver.this.b) {
                        listener2.onConnected(networkType);
                    }
                }
            }
        }
    }
}
