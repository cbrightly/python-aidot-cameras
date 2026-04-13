package com.leedarson.serviceinterface.utils;

import android.content.Context;
import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.io.InputStream;

public final class DownloadVideoUtils {
    public static ChangeQuickRedirect changeQuickRedirect;

    private DownloadVideoUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static String getCachePath(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9263, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (context == null) {
            return "";
        }
        return context.getFilesDir() + File.separator + "stream_cache";
    }

    public static boolean createCacheDir(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9264, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String cachePath = getCachePath(context);
        if (!TextUtils.isEmpty(cachePath)) {
            return FileUtils.createOrExistsDir(cachePath);
        }
        return false;
    }

    public static boolean createDevCacheDir(Context context, String devid) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid}, (Object) null, changeQuickRedirect, true, 9265, new Class[]{Context.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!createCacheDir(context) || TextUtils.isEmpty(devid)) {
            return false;
        }
        return FileUtils.createOrExistsDir(getCachePath(context) + File.separator + devid);
    }

    public static boolean createDownloadFile(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9266, new Class[]{Context.class, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath(context));
        String str = File.separator;
        sb.append(str);
        sb.append(devid);
        if (!FileUtils.isFileExists(sb.toString())) {
            return false;
        }
        return FileUtils.createFileByDeleteOldFile(getCachePath(context) + str + devid + str + startTime + "-dowanload");
    }

    public static boolean writeDownloadFile(Context context, String devid, String startTime, InputStream is) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime, is}, (Object) null, changeQuickRedirect, true, 9267, new Class[]{Context.class, cls, cls, InputStream.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath(context));
        String str = File.separator;
        sb.append(str);
        sb.append(devid);
        sb.append(str);
        sb.append(startTime);
        sb.append("-dowanload");
        return FileIOUtils.writeFileFromIS(sb.toString(), is);
    }

    public static boolean writeCacheFileComplete(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9268, new Class[]{Context.class, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath(context));
        String str = File.separator;
        sb.append(str);
        sb.append(devid);
        sb.append(str);
        sb.append(startTime);
        String filepath = sb.toString();
        if (!FileUtils.isFileExists(filepath)) {
            return false;
        }
        return FileUtils.rename(filepath, startTime + "-whole");
    }

    public static boolean isDownloadFileExit(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9269, new Class[]{Context.class, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath(context));
        String str = File.separator;
        sb.append(str);
        sb.append(devid);
        sb.append(str);
        sb.append(startTime);
        sb.append("-dowanload");
        return FileUtils.isFileExists(sb.toString());
    }

    public static byte[] readDownloadFile(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9270, new Class[]{Context.class, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath(context));
        String str = File.separator;
        sb.append(str);
        sb.append(devid);
        sb.append(str);
        sb.append(startTime);
        sb.append("-dowanload");
        return FileIOUtils.readFile2BytesByStream(sb.toString());
    }

    public static String getSavePath(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9271, new Class[]{Context.class, cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath(context));
        String str = File.separator;
        sb.append(str);
        sb.append(devid);
        sb.append(str);
        sb.append(startTime);
        sb.append("-dowanload.mp4");
        return sb.toString();
    }

    public static boolean ismp4FileExit(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9272, new Class[]{Context.class, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getCachePath(context));
        String str = File.separator;
        sb.append(str);
        sb.append(devid);
        sb.append(str);
        sb.append(startTime);
        sb.append("-dowanload.mp4");
        return FileUtils.isFileExists(sb.toString());
    }

    public static File getmp4File(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9273, new Class[]{Context.class, cls, cls}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        return new File(getSavePath(context, devid, startTime + ""));
    }
}
