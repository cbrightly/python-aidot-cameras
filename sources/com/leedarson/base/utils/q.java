package com.leedarson.base.utils;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.regex.Pattern;
import timber.log.a;

/* compiled from: LdsPingUtil */
public class q {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static int c(String url) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url}, (Object) null, changeQuickRedirect, true, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CONNECTION_CHECK_RESP, new Class[]{String.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : d(url, 2);
    }

    public static int d(String url, int type) {
        Object[] objArr = {url, new Integer(type)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 498, new Class[]{String.class, cls}, cls);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : e(url, type, 1, 100);
    }

    public static int e(String url, int type, int count, int timeout) {
        String pingString;
        Object[] objArr = {url, new Integer(type), new Integer(count), new Integer(timeout)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 499, new Class[]{String.class, cls, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        String domain = b(url);
        if (!(domain == null || (pingString = h(a(count, timeout, domain))) == null || TextUtils.isEmpty(pingString))) {
            try {
                String[] temps = pingString.substring(pingString.indexOf("min/avg/max/mdev") + 19).split("/");
                switch (type) {
                    case 1:
                        return Math.round(Float.parseFloat(temps[0]));
                    case 2:
                        if (temps.length > 1) {
                            return Math.round(Float.parseFloat(temps[1]));
                        }
                        return -1;
                    case 3:
                        if (temps.length > 2) {
                            return Math.round(Float.parseFloat(temps[2]));
                        }
                        return -1;
                    case 4:
                        if (temps.length > 3) {
                            return Math.round(Float.parseFloat(temps[3]));
                        }
                        return -1;
                    default:
                        return -1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public static a f(String url, int type, int count, int timeout) {
        Object[] objArr = {url, new Integer(type), new Integer(count), new Integer(timeout)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 500, new Class[]{String.class, cls, cls, cls}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        String domain = b(url);
        if (domain == null) {
            return new a();
        }
        a result = new a();
        String pingString = h(a(count, timeout, domain));
        a.b g = timber.log.a.g("PingUtil");
        g.m("PingUtil:   pingStr=" + pingString, new Object[0]);
        if (pingString != null && !TextUtils.isEmpty(pingString)) {
            try {
                String[] temps = pingString.substring(pingString.indexOf("min/avg/max/mdev") + 19).split("/");
                switch (type) {
                    case 1:
                        result.a = Math.round(Float.parseFloat(temps[0]));
                        break;
                    case 2:
                        if (temps.length <= 1) {
                            result.a = -1;
                            break;
                        } else {
                            result.a = Math.round(Float.parseFloat(temps[1]));
                            break;
                        }
                    case 3:
                        if (temps.length <= 2) {
                            result.a = -1;
                            break;
                        } else {
                            result.a = Math.round(Float.parseFloat(temps[2]));
                            break;
                        }
                    case 4:
                        if (temps.length <= 3) {
                            result.a = -1;
                            break;
                        } else {
                            result.a = Math.round(Float.parseFloat(temps[3]));
                            break;
                        }
                    default:
                        result.a = -1;
                        break;
                }
                String tempInfo2 = pingString.substring(pingString.indexOf("received,"));
                result.b = Float.parseFloat(tempInfo2.substring(9, tempInfo2.indexOf("packet")).replace("%", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static String b(String url) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url}, (Object) null, changeQuickRedirect, true, TypedValues.PositionType.TYPE_SIZE_PERCENT, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            String domain = URI.create(url).getHost();
            if (domain != null || !g("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))", url)) {
                return domain;
            }
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean g(String regex, String string) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{regex, string}, (Object) null, changeQuickRedirect, true, TypedValues.PositionType.TYPE_PERCENT_X, new Class[]{cls, cls}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : Pattern.matches(regex, string);
    }

    private static String h(String command) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{command}, (Object) null, changeQuickRedirect, true, TypedValues.PositionType.TYPE_PERCENT_Y, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Process process = null;
        try {
            Process process2 = Runtime.getRuntime().exec(command);
            InputStream is = process2.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine != null) {
                    sb.append(line);
                    sb.append("\n");
                } else {
                    reader.close();
                    is.close();
                    String sb2 = sb.toString();
                    process2.destroy();
                    return sb2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (process == null) {
                return null;
            }
            process.destroy();
            return null;
        } catch (Throwable th) {
            if (process != null) {
                process.destroy();
            }
            throw th;
        }
    }

    private static String a(int count, int timeout, String domain) {
        Object[] objArr = {new Integer(count), new Integer(timeout), domain};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, TypedValues.PositionType.TYPE_CURVE_FIT, new Class[]{cls, cls, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "/system/bin/ping -c " + count + " -w " + timeout + " " + domain;
    }

    /* compiled from: LdsPingUtil */
    public static class a {
        public static ChangeQuickRedirect changeQuickRedirect;
        public int a = -1;
        public float b = -1.0f;

        public String toString() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 509, new Class[0], String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            return "PingResult{pingRtt=" + this.a + ", pingLostRate=" + this.b + '}';
        }
    }
}
