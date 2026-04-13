package com.leedarson.smartcamera.utils;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: FileIOUtils */
public final class a {
    private static int a = 524288;
    public static ChangeQuickRedirect changeQuickRedirect;

    /* renamed from: com.leedarson.smartcamera.utils.a$a  reason: collision with other inner class name */
    /* compiled from: FileIOUtils */
    public interface C0181a {
        void onProgressUpdate(double d);
    }

    public static boolean g(File file, InputStream is, boolean append, C0181a listener) {
        Object[] objArr = {file, is, new Byte(append ? (byte) 1 : 0), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10337, new Class[]{File.class, InputStream.class, cls, C0181a.class}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (is == null || !b(file)) {
            return false;
        }
        OutputStream os = null;
        try {
            OutputStream os2 = new BufferedOutputStream(new FileOutputStream(file, append), a);
            if (listener == null) {
                byte[] data = new byte[a];
                while (true) {
                    int read = is.read(data);
                    int len = read;
                    if (read == -1) {
                        break;
                    }
                    os2.write(data, 0, len);
                }
            } else {
                double totalSize = (double) is.available();
                int curSize = 0;
                listener.onProgressUpdate(0.0d);
                byte[] data2 = new byte[a];
                while (true) {
                    int read2 = is.read(data2);
                    int len2 = read2;
                    if (read2 != -1) {
                        os2.write(data2, 0, len2);
                        curSize += len2;
                        listener.onProgressUpdate(((double) curSize) / totalSize);
                    }
                }
                is.close();
                os2.close();
                return true;
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os2.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return true;
        } catch (IOException e3) {
            e3.printStackTrace();
            try {
                is.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean f(String filePath, byte[] bytes, boolean append) {
        Object[] objArr = {filePath, bytes, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10339, new Class[]{String.class, byte[].class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : e(c(filePath), bytes, append, (C0181a) null);
    }

    public static boolean e(File file, byte[] bytes, boolean append, C0181a listener) {
        Object[] objArr = {file, bytes, new Byte(append ? (byte) 1 : 0), listener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 10345, new Class[]{File.class, byte[].class, cls, C0181a.class}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (bytes == null) {
            return false;
        }
        return g(file, new ByteArrayInputStream(bytes), append, listener);
    }

    public static File c(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 10378, new Class[]{String.class}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        if (d(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    private static boolean b(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 10380, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!a(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean a(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 10381, new Class[]{File.class}, Boolean.TYPE);
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

    private static boolean d(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 10383, new Class[]{String.class}, Boolean.TYPE);
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
