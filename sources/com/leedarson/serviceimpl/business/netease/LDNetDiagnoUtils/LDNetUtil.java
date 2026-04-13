package com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressLint({"DefaultLocale"})
public class LDNetUtil {
    public static final String NETWORKTYPE_INVALID = "UNKNOWN";
    public static final String NETWORKTYPE_MOBILE = "MOBILE";
    public static final String NETWORKTYPE_WAP = "WAP";
    public static final String NETWORKTYPE_WIFI = "WIFI";
    public static final String OPEN_IP = "";
    public static final String OPERATOR_URL = "";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String getNetWorkType(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7212, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService("connectivity");
        if (manager == null) {
            return "ConnectivityManager not found";
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return NETWORKTYPE_INVALID;
        }
        String type = networkInfo.getTypeName();
        if (type.equalsIgnoreCase(NETWORKTYPE_WIFI)) {
            return NETWORKTYPE_WIFI;
        }
        if (type.equalsIgnoreCase(NETWORKTYPE_MOBILE)) {
            return NETWORKTYPE_MOBILE;
        }
        return null;
    }

    public static Boolean isNetworkConnected(Context context) {
        NetworkInfo networkinfo;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7213, new Class[]{Context.class}, Boolean.class);
        if (proxy.isSupported) {
            return (Boolean) proxy.result;
        }
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (manager == null || (networkinfo = manager.getActiveNetworkInfo()) == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }

    public static String getMobileOperator(Context context) {
        String operator;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7214, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        TelephonyManager telManager = (TelephonyManager) context.getSystemService("phone");
        if (!(telManager == null || (operator = telManager.getSimOperator()) == null)) {
            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                return "中国移动";
            }
            if (operator.equals("46001")) {
                return "中国联通";
            }
            if (operator.equals("46003")) {
                return "中国电信";
            }
        }
        return "未知运营商";
    }

    public static String getLocalIpByWifi(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7215, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null) {
            return "wifiManager not found";
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return "wifiInfo not found";
        }
        int ipAddress = wifiInfo.getIpAddress();
        return String.format(Locale.US, "%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)});
    }

    public static String getLocalIpBy3G() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 7216, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses();
                while (true) {
                    if (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String pingGateWayInWifi(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7217, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null) {
            return "wifiManager not found";
        }
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo == null) {
            return null;
        }
        int tmp = dhcpInfo.gateway;
        return String.format(Locale.US, "%d.%d.%d.%d", new Object[]{Integer.valueOf(tmp & 255), Integer.valueOf((tmp >> 8) & 255), Integer.valueOf((tmp >> 16) & 255), Integer.valueOf((tmp >> 24) & 255)});
    }

    public static String getLocalDns(String dns) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dns}, (Object) null, changeQuickRedirect, true, 7218, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Process process = null;
        String str = "";
        BufferedReader reader = null;
        try {
            Process process2 = Runtime.getRuntime().exec("getprop net." + dns);
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            while (true) {
                String readLine = reader2.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                str = str + line;
            }
            reader2.close();
            process2.waitFor();
            try {
                reader2.close();
                process2.destroy();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            process.destroy();
        } catch (InterruptedException e3) {
            e3.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            process.destroy();
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e4) {
                    throw th;
                }
            }
            process.destroy();
            throw th;
        }
        return str.trim();
    }

    public static Map<String, Object> getDomainIp(String _dormain) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_dormain}, (Object) null, changeQuickRedirect, true, 7219, new Class[]{String.class}, Map.class);
        if (proxy.isSupported) {
            return (Map) proxy.result;
        }
        Map<String, Object> map = new HashMap<>();
        String time = null;
        InetAddress[] remoteInet = null;
        try {
            long start = System.currentTimeMillis();
            InetAddress[] remoteInet2 = InetAddress.getAllByName(_dormain);
            if (remoteInet2 != null) {
                long end = System.currentTimeMillis();
                time = (end - start) + "";
            }
            map.put("remoteInet", remoteInet2);
        } catch (UnknownHostException e) {
            long end2 = System.currentTimeMillis();
            time = (end2 - 0) + "";
            remoteInet = null;
            e.printStackTrace();
            map.put("remoteInet", (Object) null);
        } catch (Throwable th) {
            map.put("remoteInet", remoteInet);
            map.put("useTime", time);
            throw th;
        }
        map.put("useTime", time);
        return map;
    }

    private static String mobileNetworkType(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7220, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return "TM==null";
        }
        switch (telephonyManager.getNetworkType()) {
            case 0:
                return NETWORKTYPE_INVALID;
            case 1:
                return "2G";
            case 2:
                return "2G";
            case 3:
                return "3G";
            case 4:
                return "2G";
            case 5:
                return "3G";
            case 6:
                return "3G";
            case 7:
                return "2G";
            case 8:
                return "3G";
            case 9:
                return "3G";
            case 10:
                return "3G";
            case 11:
                return "2G";
            case 12:
                return "3G";
            case 13:
                return "4G";
            case 14:
                return "3G";
            case 15:
                return "3G";
            default:
                return "4G";
        }
    }

    public static String getStringFromStream(InputStream is) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{is}, (Object) null, changeQuickRedirect, true, 7221, new Class[]{InputStream.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        byte[] bytes = new byte[1024];
        String res = "";
        while (true) {
            try {
                int read = is.read(bytes);
                int len = read;
                if (read != -1) {
                    res = res + new String(bytes, 0, len, "gbk");
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (is != null) {
                    is.close();
                }
            } catch (Throwable th) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
        }
        is.close();
        return res;
    }
}
