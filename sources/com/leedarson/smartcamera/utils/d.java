package com.leedarson.smartcamera.utils;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.util.Locale;

/* compiled from: ImageCacheUtils */
public final class d {
    private static String a;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void f(String path) {
        a = path;
    }

    public static String c() {
        return a;
    }

    public static String g(Context context) {
        String str;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 10479, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (context != null) {
            str = context.getFilesDir() + File.separator + "image_cache";
        } else {
            str = "";
        }
        a = str;
        return str;
    }

    public static boolean b(String event, String devid, String timestamp) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event, devid, timestamp}, (Object) null, changeQuickRedirect, true, 10480, new Class[]{cls, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String cacheFileName = String.format(Locale.US, "%s-%s-%s.jpg", new Object[]{event, devid, timestamp});
        return b.b(c() + File.separator + cacheFileName);
    }

    public static boolean h(String event, String p2pId, String timestamp, byte[] bArr) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event, p2pId, timestamp, bArr}, (Object) null, changeQuickRedirect, true, 10481, new Class[]{cls, cls, cls, byte[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] bytes = bArr;
        String cacheFileName = String.format(Locale.US, "%s-%s-%s.jpg", new Object[]{event, p2pId, timestamp});
        StringBuilder sb = new StringBuilder();
        sb.append(c());
        String str = File.separator;
        sb.append(str);
        sb.append(cacheFileName);
        if (!b.j(sb.toString())) {
            return false;
        }
        return a.f(c() + str + cacheFileName, bytes, true);
    }

    public static String d(String event, String p2pId, String timestamp) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event, p2pId, timestamp}, (Object) null, changeQuickRedirect, true, 10482, new Class[]{cls, cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String cacheFileName = String.format(Locale.US, "%s-%s-%s.jpg", new Object[]{event, p2pId, timestamp});
        return c() + File.separator + cacheFileName;
    }

    public static boolean e(String event, String devid, String timestamp) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event, devid, timestamp}, (Object) null, changeQuickRedirect, true, 10484, new Class[]{cls, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String cacheFileName = String.format(Locale.US, "%s-%s-%s.jpg", new Object[]{event, devid, timestamp});
        return b.j(c() + File.separator + cacheFileName);
    }

    public static void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 10485, new Class[]{Context.class}, Void.TYPE).isSupported) {
            for (File f : new File(context.getFilesDir().getPath() + "/web/static/media/").listFiles()) {
                if (!f.isDirectory() && f.getAbsolutePath().endsWith(".jpg") && System.currentTimeMillis() - f.lastModified() >= 432000000) {
                    f.delete();
                }
            }
        }
    }
}
