package com.leedarson.smartcamera.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.io.IOException;

/* compiled from: FileUtils */
public final class b {
    private static final String a = System.getProperty("line.separator");
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static ChangeQuickRedirect changeQuickRedirect;

    public static File h(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 10385, new Class[]{String.class}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        if (k(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    public static boolean j(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 10386, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : i(h(filePath));
    }

    public static boolean i(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 10387, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return file != null && file.exists();
    }

    public static boolean c(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 10395, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file != null) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    return true;
                }
            } else if (file.mkdirs()) {
                return true;
            }
        }
        return false;
    }

    public static boolean b(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 10398, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a(h(filePath));
    }

    public static boolean a(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 10399, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null) {
            return false;
        }
        if ((file.exists() && !file.delete()) || !c(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean e(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 10420, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : d(h(filePath));
    }

    public static boolean d(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 10421, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return f(file);
        }
        return g(file);
    }

    public static boolean f(File dir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir}, (Object) null, changeQuickRedirect, true, 10423, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory() && !f(file)) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean g(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 10425, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file != null) {
            if (!file.exists()) {
                return true;
            }
            if (file.isFile() && file.delete()) {
                return true;
            }
        }
        return false;
    }

    private static boolean k(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 10470, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
