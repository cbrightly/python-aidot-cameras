package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.RequiresPermission;
import com.blankj.utilcode.util.y;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

/* compiled from: DeviceUtils */
public final class g {
    private static volatile String a;

    private g() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean r() {
        for (String location : new String[]{"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/", "/system/sbin/", "/usr/bin/", "/vendor/bin/"}) {
            if (new File(location + "su").exists()) {
                return true;
            }
        }
        return false;
    }

    public static String k() {
        return Build.VERSION.RELEASE;
    }

    @SuppressLint({"HardwareIds"})
    public static String a() {
        String id = Settings.Secure.getString(f0.a().getContentResolver(), "android_id");
        if (!"9774d56d682e549c".equals(id) && id != null) {
            return id;
        }
        return "";
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.INTERNET", "android.permission.CHANGE_WIFI_STATE"})
    public static String c() {
        String macAddress = d((String[]) null);
        if (!macAddress.equals("") || p()) {
            return macAddress;
        }
        t(true);
        t(false);
        return d((String[]) null);
    }

    private static boolean p() {
        WifiManager manager = (WifiManager) f0.a().getSystemService("wifi");
        if (manager == null) {
            return false;
        }
        return manager.isWifiEnabled();
    }

    @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
    private static void t(boolean enabled) {
        WifiManager manager = (WifiManager) f0.a().getSystemService("wifi");
        if (manager != null && enabled != manager.isWifiEnabled()) {
            manager.setWifiEnabled(enabled);
        }
    }

    @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.INTERNET"})
    public static String d(String... excepts) {
        String macAddress = g();
        if (q(macAddress, excepts)) {
            return macAddress;
        }
        String macAddress2 = f();
        if (q(macAddress2, excepts)) {
            return macAddress2;
        }
        String macAddress3 = h();
        if (q(macAddress3, excepts)) {
            return macAddress3;
        }
        String macAddress4 = e();
        if (q(macAddress4, excepts)) {
            return macAddress4;
        }
        return "";
    }

    private static boolean q(String address, String... excepts) {
        if (excepts == null || excepts.length == 0) {
            return true ^ "02:00:00:00:00:00".equals(address);
        }
        for (String filter : excepts) {
            if (address.equals(filter)) {
                return false;
            }
        }
        return true;
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    private static String h() {
        WifiInfo info;
        try {
            WifiManager wifi = (WifiManager) f0.a().getApplicationContext().getSystemService("wifi");
            if (wifi == null || (info = wifi.getConnectionInfo()) == null) {
                return "02:00:00:00:00:00";
            }
            return info.getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static String g() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (ni != null) {
                    if (ni.getName().equalsIgnoreCase("wlan0")) {
                        byte[] macBytes = ni.getHardwareAddress();
                        if (macBytes != null && macBytes.length > 0) {
                            StringBuilder sb = new StringBuilder();
                            int length = macBytes.length;
                            for (int i = 0; i < length; i++) {
                                sb.append(String.format("%02x:", new Object[]{Byte.valueOf(macBytes[i])}));
                            }
                            return sb.substring(0, sb.length() - 1);
                        }
                    }
                }
            }
            return "02:00:00:00:00:00";
        } catch (Exception e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static String f() {
        NetworkInterface ni;
        byte[] macBytes;
        try {
            InetAddress inetAddress = b();
            if (inetAddress == null || (ni = NetworkInterface.getByInetAddress(inetAddress)) == null || (macBytes = ni.getHardwareAddress()) == null || macBytes.length <= 0) {
                return "02:00:00:00:00:00";
            }
            StringBuilder sb = new StringBuilder();
            int length = macBytes.length;
            for (int i = 0; i < length; i++) {
                sb.append(String.format("%02x:", new Object[]{Byte.valueOf(macBytes[i])}));
            }
            return sb.substring(0, sb.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }

    private static InetAddress b() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (ni.isUp()) {
                    Enumeration<InetAddress> addresses = ni.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress inetAddress = addresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress.getHostAddress().indexOf(58) < 0) {
                            return inetAddress;
                        }
                    }
                    continue;
                }
            }
            return null;
        } catch (SocketException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String e() {
        String name;
        String address;
        y.a result = h0.h("getprop wifi.interface", false);
        if (result.a != 0 || (name = result.b) == null) {
            return "02:00:00:00:00:00";
        }
        y.a result2 = h0.h("cat /sys/class/net/" + name + "/address", false);
        if (result2.a != 0 || (address = result2.b) == null || address.length() <= 0) {
            return "02:00:00:00:00:00";
        }
        return address;
    }

    public static String i() {
        return Build.MANUFACTURER;
    }

    public static String j() {
        String model = Build.MODEL;
        if (model != null) {
            return model.trim().replaceAll("\\s*", "");
        }
        return "";
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String m() {
        return n("", true);
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String n(String prefix, boolean useCache) {
        if (!useCache) {
            return o(prefix);
        }
        if (a == null) {
            synchronized (g.class) {
                if (a == null) {
                    String id = h0.u().d("KEY_UDID", (String) null);
                    if (id != null) {
                        a = id;
                        String str = a;
                        return str;
                    }
                    String o = o(prefix);
                    return o;
                }
            }
        }
        return a;
    }

    private static String o(String prefix) {
        try {
            String androidId = a();
            if (!TextUtils.isEmpty(androidId)) {
                return s(prefix + 2, androidId);
            }
        } catch (Exception e) {
        }
        return s(prefix + 9, "");
    }

    private static String s(String prefix, String id) {
        a = l(prefix, id);
        h0.u().f("KEY_UDID", a);
        return a;
    }

    private static String l(String prefix, String id) {
        if (id.equals("")) {
            return prefix + UUID.randomUUID().toString().replace("-", "");
        }
        return prefix + UUID.nameUUIDFromBytes(id.getBytes()).toString().replace("-", "");
    }
}
