package com.leedarson.serviceinterface.utils;

import android.content.Context;
import android.text.TextUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;

public final class PlayBackCacheUtils {
    public static ChangeQuickRedirect changeQuickRedirect;

    private PlayBackCacheUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static String getCachePath(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9429, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (context == null) {
            return "";
        }
        return context.getFilesDir() + File.separator + "stream_cache";
    }

    public static boolean createCacheDir(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 9430, new Class[]{Context.class}, Boolean.TYPE);
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
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid}, (Object) null, changeQuickRedirect, true, 9431, new Class[]{Context.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!createCacheDir(context) || TextUtils.isEmpty(devid)) {
            return false;
        }
        return FileUtils.createOrExistsDir(getCachePath(context) + File.separator + devid);
    }

    public static boolean createDevCacheFile(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9432, new Class[]{Context.class, cls, cls}, Boolean.TYPE);
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
        return FileUtils.createFileByDeleteOldFile(getCachePath(context) + str + devid + str + startTime);
    }

    public static boolean writeCacheFile(Context context, String devid, String startTime, byte[] bytes) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime, bytes}, (Object) null, changeQuickRedirect2, true, 9433, new Class[]{Context.class, cls, cls, byte[].class}, Boolean.TYPE);
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
        if (!FileUtils.isFileExists(sb.toString())) {
            return false;
        }
        return FileIOUtils.writeFileFromBytesByStream(getCachePath(context) + str + devid + str + startTime, bytes, true);
    }

    public static boolean writeCacheFileComplete(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9434, new Class[]{Context.class, cls, cls}, Boolean.TYPE);
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

    public static boolean isCacheFileExit(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9435, new Class[]{Context.class, cls, cls}, Boolean.TYPE);
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
        sb.append("-whole");
        return FileUtils.isFileExists(sb.toString());
    }

    public static byte[] readCacheFile(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9436, new Class[]{Context.class, cls, cls}, byte[].class);
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
        sb.append("-whole");
        return FileIOUtils.readFile2BytesByStream(sb.toString());
    }

    public static String getFilePath(Context context, String devid, String startTime) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, devid, startTime}, (Object) null, changeQuickRedirect2, true, 9437, new Class[]{Context.class, cls, cls}, String.class);
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
        sb.append("-whole");
        return sb.toString();
    }
}
