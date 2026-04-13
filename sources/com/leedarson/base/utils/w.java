package com.leedarson.base.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.util.SystemUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import net.sqlcipher.database.SQLiteDatabase;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: Util */
public class w {
    private static Vibrator a;
    public static final char[] b = "0123456789ABCDEF".toCharArray();
    private static final char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static ChangeQuickRedirect changeQuickRedirect;
    private static String d = "https://www.facebook.com/groups/587187662603791";

    public static void a0(Context context, String url, String cookie) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, url, cookie}, (Object) null, changeQuickRedirect, true, 539, new Class[]{Context.class, cls, cls}, Void.TYPE).isSupported && !TextUtils.isEmpty(cookie)) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            String[] splitCookie = cookie.split("; ");
            if (splitCookie != null) {
                for (String cook : splitCookie) {
                    cookieManager.setCookie(url, cook);
                }
            }
            a.g(SerializableCookie.COOKIE).h("NewCookie = " + cookieManager.getCookie(url), new Object[0]);
        }
    }

    private static PackageInfo y(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 544, new Class[]{Context.class}, PackageInfo.class);
        if (proxy.isSupported) {
            return (PackageInfo) proxy.result;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String z(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 545, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return y(context) == null ? "" : y(context).packageName;
    }

    public static String u(Context context) {
        Locale locale;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 546, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (Build.VERSION.SDK_INT < 24) {
            locale = context.getResources().getConfiguration().locale;
        } else {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        }
        if (locale == null) {
            return "";
        }
        if (locale.getLanguage().equals("ja")) {
            return locale.getLanguage();
        }
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    public static String H(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 547, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return y(context) == null ? "" : y(context).versionName;
    }

    public static long F(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 548, new Class[]{Context.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        PackageInfo packageInfo = y(context);
        if (packageInfo == null) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            return packageInfo.getLongVersionCode();
        }
        return (long) packageInfo.versionCode;
    }

    public static String E(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 549, new Class[]{Context.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : H(context);
    }

    public static String p(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 550, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            return y(context) == null ? "" : context.getString(y(context).applicationInfo.labelRes);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void e0(Context context, int milliseconds) {
        if (!PatchProxy.proxy(new Object[]{context, new Integer(milliseconds)}, (Object) null, changeQuickRedirect, true, 551, new Class[]{Context.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
                a = vibrator;
                vibrator.vibrate((long) milliseconds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void V(Context context, int milliseconds) {
        if (!PatchProxy.proxy(new Object[]{context, new Integer(milliseconds)}, (Object) null, changeQuickRedirect, true, 552, new Class[]{Context.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                int repeat = (int) Math.ceil((double) (((float) milliseconds) / 1000.0f));
                long[] pa = new long[(repeat * 2)];
                for (int i = 0; i < repeat; i++) {
                    pa[i * 2] = 700;
                    pa[(i * 2) + 1] = 300;
                }
                Vibrator vibrator = (Vibrator) context.getSystemService("vibrator");
                a = vibrator;
                vibrator.vibrate(pa, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void d() {
        Vibrator vibrator;
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 553, new Class[0], Void.TYPE).isSupported && (vibrator = a) != null) {
            vibrator.cancel();
        }
    }

    public static void j(Context context, String copyPath, String savePath, boolean isAssets) {
        String[] fileNames;
        InputStream is;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{context, copyPath, savePath, new Byte(isAssets ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 554, new Class[]{Context.class, cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                File saveFile = new File(savePath);
                if (!saveFile.getParentFile().exists()) {
                    saveFile.getParentFile().mkdirs();
                }
                if (isAssets) {
                    fileNames = context.getAssets().list(copyPath);
                } else {
                    fileNames = new File(copyPath).list();
                }
                if (fileNames == null || fileNames.length <= 0) {
                    if (isAssets) {
                        is = context.getAssets().open(copyPath);
                    } else {
                        is = new FileInputStream(new File(copyPath));
                    }
                    FileOutputStream fos = new FileOutputStream(new File(savePath));
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int read = is.read(buffer);
                        int byteCount = read;
                        if (read != -1) {
                            fos.write(buffer, 0, byteCount);
                        } else {
                            fos.flush();
                            is.close();
                            fos.close();
                            return;
                        }
                    }
                } else {
                    new File(savePath).mkdirs();
                    for (String fileName : fileNames) {
                        j(context, copyPath + "/" + fileName, savePath + "/" + fileName, isAssets);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String r() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 558, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date(System.currentTimeMillis()));
    }

    public static boolean d0(String str, String str2) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str, str2}, (Object) null, changeQuickRedirect, true, 559, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String zipFile = str;
        String folderPath = str2;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        try {
            ZipFile zfile = new ZipFile(zipFile);
            Enumeration zList = zfile.entries();
            int i = 1024;
            byte[] buf = new byte[1024];
            while (zList.hasMoreElements()) {
                ZipEntry ze = (ZipEntry) zList.nextElement();
                if (ze.isDirectory()) {
                    String dirstr = folderPath + "/" + ze.getName();
                    a.g("Util").h("dirstr=" + dirstr, new Object[0]);
                    dirstr.trim();
                    new File(dirstr).mkdirs();
                } else {
                    File realFile = B(folderPath, ze.getName());
                    try {
                        a.g("Util").h("upZipFile-----: " + realFile.getName(), new Object[0]);
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(realFile));
                        try {
                            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
                            while (true) {
                                try {
                                    int read = is.read(buf, 0, i);
                                    int readLen = read;
                                    if (read != -1) {
                                        os.write(buf, 0, readLen);
                                        i = 1024;
                                    } else {
                                        try {
                                            break;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            IOException iOException = e;
                                            a.g("Util").c(e.getMessage(), new Object[0]);
                                            return false;
                                        }
                                    }
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                    IOException iOException2 = e2;
                                    a.g("Util").c(e2.getMessage(), new Object[0]);
                                    return false;
                                }
                            }
                            is.close();
                            os.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                            a.g("Util").c(e3.getMessage(), new Object[0]);
                            return false;
                        }
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        a.g("Util").c(e4.getMessage(), new Object[0]);
                        return false;
                    }
                }
                i = 1024;
            }
            try {
                zfile.close();
                return true;
            } catch (IOException e5) {
                IOException e6 = e5;
                e6.printStackTrace();
                a.g("Util").c(e6.getMessage(), new Object[0]);
                return false;
            }
        } catch (IOException e7) {
            e7.printStackTrace();
            return false;
        }
    }

    public static void c0(Context context, String str, String str2, boolean z) {
        Context context2;
        Class<String> cls = String.class;
        Object[] objArr = {context, str, str2, new Byte(z ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 560, new Class[]{Context.class, cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            Context context3 = context;
            String outputDirectory = str2;
            String assetName = str;
            boolean isReWrite = z;
            long startTime = System.currentTimeMillis();
            Log.e("unzip", " unAssertZip===>startTime=" + startTime);
            File file = new File(outputDirectory);
            if (!file.exists()) {
                file.mkdirs();
            }
            AssetManager assets = context3.getAssets();
            ZipInputStream zipInputStream = new ZipInputStream(assets.open(assetName + ".zip"));
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            byte[] buffer = new byte[1048576];
            while (zipEntry != null) {
                if (zipEntry.isDirectory()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(outputDirectory);
                    context2 = context3;
                    sb.append(File.separator);
                    sb.append(zipEntry.getName().replace(assetName, ""));
                    File file2 = new File(sb.toString());
                    if (isReWrite || !file2.exists()) {
                        file2.mkdir();
                    }
                    File file3 = file2;
                } else {
                    context2 = context3;
                    File file4 = new File(outputDirectory + File.separator + zipEntry.getName().replace(assetName, ""));
                    if (isReWrite || !file4.exists()) {
                        file4.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file4);
                        while (true) {
                            int read = zipInputStream.read(buffer);
                            int count = read;
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(buffer, 0, count);
                        }
                        fileOutputStream.close();
                        File file5 = file4;
                    } else {
                        File file6 = file4;
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
                context3 = context2;
            }
            zipInputStream.close();
            Log.e("unzip", " unAssertZip===>totalTime=" + (System.currentTimeMillis() - startTime));
        }
    }

    public static File B(String baseDir, String absFileName) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{baseDir, absFileName}, (Object) null, changeQuickRedirect, true, 561, new Class[]{cls, cls}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        a.b g = a.g("Util");
        g.h("baseDir=" + baseDir + "------absFileName=" + absFileName, new Object[0]);
        String absFileName2 = absFileName.replace("\\", "/");
        a.b g2 = a.g("Util");
        g2.h("absFileName=" + absFileName2, new Object[0]);
        String[] dirs = absFileName2.split("/");
        a.b g3 = a.g("Util");
        g3.h("dirs=" + dirs.length, new Object[0]);
        File ret = new File(baseDir);
        if (dirs.length <= 1) {
            return new File(ret, absFileName2);
        }
        for (int i = 0; i < dirs.length - 1; i++) {
            ret = new File(ret, dirs[i]);
        }
        a.b g4 = a.g("Util");
        g4.h("====================: " + ret.getPath(), new Object[0]);
        if (!ret.exists()) {
            ret.mkdirs();
        }
        return new File(ret, dirs[dirs.length - 1]);
    }

    public static boolean n(String strFile) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{strFile}, (Object) null, changeQuickRedirect, true, 562, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            return new File(strFile).exists();
        } catch (Exception e) {
            return false;
        }
    }

    public static void k(String pPath) {
        if (!PatchProxy.proxy(new Object[]{pPath}, (Object) null, changeQuickRedirect, true, 563, new Class[]{String.class}, Void.TYPE).isSupported) {
            l(new File(pPath));
        }
    }

    public static void l(File dir) {
        if (!PatchProxy.proxy(new Object[]{dir}, (Object) null, changeQuickRedirect, true, 564, new Class[]{File.class}, Void.TYPE).isSupported && dir != null && dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    l(file);
                }
            }
            dir.delete();
        }
    }

    public static File Z(Context context, Bitmap bitmap, String path, String fileName) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, bitmap, path, fileName}, (Object) null, changeQuickRedirect2, true, 569, new Class[]{Context.class, Bitmap.class, cls, cls}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        if (bitmap == null) {
            return null;
        }
        File file = new File(path);
        if (fileName == null || "".equals(fileName)) {
            fileName = "leedarson_head_icon.png";
        }
        File destFile = new File(file, fileName);
        a.b g = a.g("Util");
        g.h("savePicToStorage: " + destFile.getPath(), new Object[0]);
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            destFile.createNewFile();
            OutputStream os = new FileOutputStream(destFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
            return destFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File i(int i, String str, boolean z) {
        Bitmap bitmap1;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i), str, new Byte(z ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 573, new Class[]{Integer.TYPE, String.class, Boolean.TYPE}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        int byteSize = i;
        boolean needLocate = z;
        String fileUri = str;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap decodeFile = BitmapFactory.decodeFile(fileUri, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        int be = 1;
        if (w > h && ((float) w) > 1080.0f) {
            be = (int) (((float) newOpts.outWidth) / 1080.0f);
        } else if (w < h && ((float) h) > 1920.0f) {
            be = (int) (((float) newOpts.outHeight) / 1920.0f);
        }
        if (be <= 0) {
            be = 1;
        }
        a.g("Util").h("compressImageFile: be--" + be, new Object[0]);
        newOpts.inSampleSize = be;
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri, newOpts);
        if (needLocate) {
            bitmap1 = e(fileUri, bitmap);
        } else {
            bitmap1 = bitmap;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        boolean z2 = needLocate;
        a.g("Util").h("filesize222222: " + baos.toByteArray().length, new Object[0]);
        int options = 100;
        while (baos.toByteArray().length > byteSize && options > 0) {
            baos.reset();
            if (options > 10) {
                options -= 10;
            } else {
                options--;
            }
            bitmap1.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        File destFile = new File(fileUri);
        try {
            if (destFile.exists()) {
                try {
                    destFile.delete();
                } catch (IOException e) {
                    e = e;
                    int i2 = byteSize;
                    int i3 = options;
                }
            }
            destFile.createNewFile();
            try {
                FileOutputStream fos = new FileOutputStream(destFile);
                try {
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
            X(bitmap, bitmap1);
            a.b g = a.g("Util");
            StringBuilder sb = new StringBuilder();
            sb.append("compressImageFilesize: ");
            int i4 = byteSize;
            int i5 = options;
            try {
                sb.append(destFile.length());
                g.h(sb.toString(), new Object[0]);
                return destFile;
            } catch (IOException e4) {
                e = e4;
                e.printStackTrace();
                return null;
            }
        } catch (IOException e5) {
            e = e5;
            int i6 = byteSize;
            int i7 = options;
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap e(String filepath, Bitmap bitmap) {
        int degree;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filepath, bitmap}, (Object) null, changeQuickRedirect, true, 574, new Class[]{String.class, Bitmap.class}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        int degree2 = 0;
        try {
            switch (new ExifInterface(filepath).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1)) {
                case 3:
                    degree = 180;
                    break;
                case 6:
                    degree = 90;
                    break;
                case 8:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }
            try {
                a.g("Util").h("degree============" + degree + "", new Object[0]);
                if (degree != 0) {
                    a.g("Util").h("degree============degree != 0", new Object[0]);
                    Matrix m = new Matrix();
                    m.postRotate((float) degree);
                    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                }
            } catch (IOException e) {
                e = e;
                degree2 = degree;
                e.printStackTrace();
                int i = degree2;
                return bitmap;
            }
        } catch (IOException e2) {
            e = e2;
            e.printStackTrace();
            int i2 = degree2;
            return bitmap;
        }
        return bitmap;
    }

    public static void X(Bitmap... bitmaps) {
        if (!PatchProxy.proxy(new Object[]{bitmaps}, (Object) null, changeQuickRedirect, true, 575, new Class[]{Bitmap[].class}, Void.TYPE).isSupported && bitmaps != null) {
            for (Bitmap bm : bitmaps) {
                if (bm != null && !bm.isRecycled()) {
                    bm.recycle();
                }
            }
        }
    }

    public static boolean Q() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 576, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String C(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, IjkMediaMeta.FF_PROFILE_H264_CONSTRAINED_BASELINE, new Class[]{Context.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : x.d(context);
    }

    public static boolean U() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 581, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (new File("/system/bin/su").exists() && S("/system/bin/su")) {
            return true;
        }
        if (!new File("/system/xbin/su").exists() || !S("/system/xbin/su")) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0079, code lost:
        if (r1 == null) goto L_0x007c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean S(java.lang.String r9) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r2 = 0
            r4 = 1
            r5 = 582(0x246, float:8.16E-43)
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0025
            java.lang.Object r9 = r1.result
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            return r9
        L_0x0025:
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0075 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0075 }
            r3.<init>()     // Catch:{ Exception -> 0x0075 }
            java.lang.String r4 = "ls -l "
            r3.append(r4)     // Catch:{ Exception -> 0x0075 }
            r3.append(r9)     // Catch:{ Exception -> 0x0075 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0075 }
            java.lang.Process r2 = r2.exec(r3)     // Catch:{ Exception -> 0x0075 }
            r1 = r2
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0075 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0075 }
            java.io.InputStream r4 = r1.getInputStream()     // Catch:{ Exception -> 0x0075 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0075 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0075 }
            java.lang.String r3 = r2.readLine()     // Catch:{ Exception -> 0x0075 }
            if (r3 == 0) goto L_0x006e
            int r4 = r3.length()     // Catch:{ Exception -> 0x0075 }
            r5 = 4
            if (r4 < r5) goto L_0x006e
            r4 = 3
            char r4 = r3.charAt(r4)     // Catch:{ Exception -> 0x0075 }
            r5 = 115(0x73, float:1.61E-43)
            if (r4 == r5) goto L_0x0068
            r5 = 120(0x78, float:1.68E-43)
            if (r4 != r5) goto L_0x006e
        L_0x0068:
            r1.destroy()
            return r0
        L_0x006e:
        L_0x006f:
            r1.destroy()
            goto L_0x007c
        L_0x0073:
            r0 = move-exception
            goto L_0x007d
        L_0x0075:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x0073 }
            if (r1 == 0) goto L_0x007c
            goto L_0x006f
        L_0x007c:
            return r8
        L_0x007d:
            if (r1 == 0) goto L_0x0082
            r1.destroy()
        L_0x0082:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.utils.w.S(java.lang.String):boolean");
    }

    public static String[] W(String host) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{host}, (Object) null, changeQuickRedirect, true, 586, new Class[]{String.class}, String[].class);
        if (proxy.isSupported) {
            return (String[]) proxy.result;
        }
        String[] ipAddressArr = null;
        try {
            InetAddress[] inetAddressArr = InetAddress.getAllByName(host);
            if (inetAddressArr != null && inetAddressArr.length > 0) {
                ipAddressArr = new String[inetAddressArr.length];
                for (int i = 0; i < inetAddressArr.length; i++) {
                    ipAddressArr[i] = inetAddressArr[i].getHostAddress();
                }
            }
            return ipAddressArr;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void Y(Bitmap bitmap, String saveFile) {
        Class[] clsArr = {Bitmap.class, String.class};
        if (!PatchProxy.proxy(new Object[]{bitmap, saveFile}, (Object) null, changeQuickRedirect, true, 588, clsArr, Void.TYPE).isSupported) {
            try {
                File file = new File(saveFile);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean P(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 589, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(FirebaseAnalytics.Param.LOCATION);
            return locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int w(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 590, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                return 0;
            }
            String typeName = activeNetworkInfo.getTypeName();
            if (activeNetworkInfo.getType() == 1) {
                return 2;
            }
            return activeNetworkInfo.getType() == 0 ? 1 : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String x(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 591, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        int netWorkState = w(context);
        if (netWorkState == 2) {
            return "wifi";
        }
        if (netWorkState == 1) {
            return "mobile";
        }
        return "no network";
    }

    public static boolean g(Context context, String packname) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, packname}, (Object) null, changeQuickRedirect, true, 592, new Class[]{Context.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return true;
        }
        return false;
    }

    public static void m(Context context, String packname) {
        Class[] clsArr = {Context.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, packname}, (Object) null, changeQuickRedirect, true, 595, clsArr, Void.TYPE).isSupported) {
            try {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packname)).setFlags(SQLiteDatabase.CREATE_IF_NECESSARY));
            } catch (ActivityNotFoundException e) {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packname)).setFlags(SQLiteDatabase.CREATE_IF_NECESSARY));
            }
        }
    }

    public static String s(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 596, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        try {
            if (context.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + d;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String t(String v1, String v2) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v1, v2}, (Object) null, changeQuickRedirect, true, 599, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return G(v1) > G(v2) ? v1 : v2;
    }

    public static long G(String versionString) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{versionString}, (Object) null, changeQuickRedirect, true, 600, new Class[]{String.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (TextUtils.isEmpty(versionString)) {
            return 0;
        }
        String[] versionSplit = versionString.split("[.]");
        for (int i = 0; i < versionSplit.length; i++) {
            if (versionSplit[i].contains("-")) {
                versionSplit[i] = versionSplit[i].split("-")[0];
            }
        }
        if (versionSplit.length == 4) {
            return (((long) Integer.parseInt(versionSplit[0])) * 1000000000) + ((long) (Integer.parseInt(versionSplit[1]) * 1000000)) + ((long) (Integer.parseInt(versionSplit[2]) * 1000)) + ((long) Integer.parseInt(versionSplit[3]));
        }
        if (versionSplit.length == 3) {
            return (((long) Integer.parseInt(versionSplit[0])) * 1000000000) + ((long) (Integer.parseInt(versionSplit[1]) * 1000000)) + ((long) (Integer.parseInt(versionSplit[2]) * 1000));
        }
        return 0;
    }

    public static boolean T(Context context, String packageName, Long minVersion) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, packageName, minVersion}, (Object) null, changeQuickRedirect, true, 601, new Class[]{Context.class, String.class, Long.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            if (Build.VERSION.SDK_INT < 28 || minVersion == null) {
                if (packageInfo != null) {
                    return true;
                }
                return false;
            } else if (packageInfo == null || packageInfo.getLongVersionCode() <= minVersion.longValue()) {
                return false;
            } else {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String I(String ua) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ua}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_QUANTIZE_MOTION_PHASE, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Matcher m = Pattern.compile("Chrome/(\\d+.?)+ ").matcher(ua);
        if (m.find()) {
            return m.group(0).split("/")[1].trim();
        }
        return "";
    }

    public static String a(byte[] bytes) {
        int a2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_EASING, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (bytes == null) {
            return "";
        }
        char[] buf = new char[(bytes.length * 2)];
        int index = 0;
        for (byte b2 : bytes) {
            if (b2 < 0) {
                a2 = b2 + 256;
            } else {
                a2 = b2;
            }
            int index2 = index + 1;
            char[] cArr = c;
            buf[index] = cArr[a2 / 16];
            index = index2 + 1;
            buf[index2] = cArr[a2 % 16];
        }
        return new String(buf);
    }

    public static String b(byte[] array) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{array}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR, new Class[]{byte[].class}, String.class);
        return proxy.isSupported ? (String) proxy.result : c(array, "");
    }

    public static String c(byte[] array, String separator) {
        boolean z = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{array, separator}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_ANIMATE_RELATIVE_TO, new Class[]{byte[].class, String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (array == null || array.length == 0) {
            return "";
        }
        if (separator == null || separator.length() == 0) {
            z = true;
        }
        boolean sepNul = z;
        StringBuilder hexResult = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            int ai = array[i] & 255;
            if (i != 0 && !sepNul) {
                hexResult.append(separator);
            }
            char[] cArr = b;
            hexResult.append(cArr[ai >>> 4]);
            hexResult.append(cArr[ai & 15]);
        }
        return hexResult.toString();
    }

    public static boolean f(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_ANIMATE_CIRCLEANGLE_TO, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(FirebaseAnalytics.Param.LOCATION);
        if (locationManager == null) {
            return false;
        }
        if (locationManager.isProviderEnabled("gps") || locationManager.isProviderEnabled("network")) {
            return true;
        }
        return false;
    }

    public static int v(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_PATHMOTION_ARC, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] q(byte[] r8) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r8
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r2] = r0
            java.lang.Class<byte[]> r7 = byte[].class
            r2 = 0
            r4 = 1
            r5 = 609(0x261, float:8.53E-43)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0021
            java.lang.Object r8 = r0.result
            byte[] r8 = (byte[]) r8
            return r8
        L_0x0021:
            r0 = 65535(0xffff, float:9.1834E-41)
            r1 = 40961(0xa001, float:5.7399E-41)
            r2 = 0
        L_0x0028:
            int r3 = r8.length
            if (r2 >= r3) goto L_0x0045
            byte r3 = r8[r2]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r0 = r0 ^ r3
            r3 = 0
        L_0x0031:
            r4 = 8
            if (r3 >= r4) goto L_0x0042
            r4 = r0 & 1
            if (r4 == 0) goto L_0x003d
            int r0 = r0 >> 1
            r0 = r0 ^ r1
            goto L_0x003f
        L_0x003d:
            int r0 = r0 >> 1
        L_0x003f:
            int r3 = r3 + 1
            goto L_0x0031
        L_0x0042:
            int r2 = r2 + 1
            goto L_0x0028
        L_0x0045:
            java.lang.String r3 = java.lang.Integer.toHexString(r0)
            byte[] r3 = b0(r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.utils.w.q(byte[]):byte[]");
    }

    public static byte[] b0(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, new Class[]{String.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
        }
        return bytes;
    }

    public static int D(Activity activity) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_TYPE, new Class[]{Activity.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            return Settings.System.getInt(activity.getContentResolver(), "screen_brightness");
        } catch (Settings.SettingNotFoundException e) {
            return 0;
        }
    }

    public static void K(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_ID, new Class[]{Context.class}, Void.TYPE).isSupported) {
            String str = Build.VERSION.SDK;
            String str2 = Build.MODEL;
            String str3 = Build.VERSION.RELEASE;
            String brand = Build.BRAND;
            if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), SystemUtil.PHONE_XIAOMI)) {
                N(context);
            } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
                M(context);
            } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
                L(context);
            } else if (TextUtils.equals(brand.toLowerCase(), SystemUtil.PHONE_VIVO)) {
                O(context);
            } else {
                context.startActivity(o(context));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0031, code lost:
        if (r0.contains("Y85A") != false) goto L_0x0033;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void O(android.content.Context r8) {
        /*
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r8
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.content.Context> r0 = android.content.Context.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r2 = 0
            r4 = 1
            r5 = 613(0x265, float:8.59E-43)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            java.lang.String r0 = android.os.Build.MODEL     // Catch:{ Exception -> 0x0073 }
            java.lang.String r1 = "Y85"
            boolean r1 = r0.contains(r1)     // Catch:{ Exception -> 0x0073 }
            java.lang.String r2 = "packagename"
            java.lang.String r3 = "com.vivo.permissionmanager"
            if (r1 == 0) goto L_0x0033
            java.lang.String r1 = "Y85A"
            boolean r1 = r0.contains(r1)     // Catch:{ Exception -> 0x0073 }
            if (r1 == 0) goto L_0x003c
        L_0x0033:
            java.lang.String r1 = "vivo Y53L"
            boolean r0 = r0.contains(r1)     // Catch:{ Exception -> 0x0073 }
            if (r0 == 0) goto L_0x0059
        L_0x003c:
            android.content.Intent r0 = new android.content.Intent     // Catch:{ Exception -> 0x0073 }
            r0.<init>()     // Catch:{ Exception -> 0x0073 }
            java.lang.String r1 = "com.vivo.permissionmanager.activity.PurviewTabActivity"
            r0.setClassName(r3, r1)     // Catch:{ Exception -> 0x0073 }
            java.lang.String r1 = r8.getPackageName()     // Catch:{ Exception -> 0x0073 }
            r0.putExtra(r2, r1)     // Catch:{ Exception -> 0x0073 }
            java.lang.String r1 = "tabId"
            java.lang.String r2 = "1"
            r0.putExtra(r1, r2)     // Catch:{ Exception -> 0x0073 }
            r8.startActivity(r0)     // Catch:{ Exception -> 0x0073 }
            goto L_0x0072
        L_0x0059:
            android.content.Intent r0 = new android.content.Intent     // Catch:{ Exception -> 0x0073 }
            r0.<init>()     // Catch:{ Exception -> 0x0073 }
            java.lang.String r1 = "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"
            r0.setClassName(r3, r1)     // Catch:{ Exception -> 0x0073 }
            java.lang.String r1 = "secure.intent.action.softPermissionDetail"
            r0.setAction(r1)     // Catch:{ Exception -> 0x0073 }
            java.lang.String r1 = r8.getPackageName()     // Catch:{ Exception -> 0x0073 }
            r0.putExtra(r2, r1)     // Catch:{ Exception -> 0x0073 }
            r8.startActivity(r0)     // Catch:{ Exception -> 0x0073 }
        L_0x0072:
            goto L_0x007e
        L_0x0073:
            r0 = move-exception
            r0.printStackTrace()
            android.content.Intent r1 = o(r8)
            r8.startActivity(r1)
        L_0x007e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.utils.w.O(android.content.Context):void");
    }

    private static void N(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 614, new Class[]{Context.class}, Void.TYPE).isSupported) {
            try {
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e) {
                try {
                    Intent localIntent2 = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    localIntent2.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    localIntent2.putExtra("extra_pkgname", context.getPackageName());
                    context.startActivity(localIntent2);
                } catch (Exception e2) {
                    context.startActivity(o(context));
                }
            }
        }
    }

    private static void M(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 615, new Class[]{Context.class}, Void.TYPE).isSupported) {
            try {
                Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra("packageName", z(context));
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                context.startActivity(o(context));
            }
        }
    }

    private static void L(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 616, new Class[]{Context.class}, Void.TYPE).isSupported) {
            try {
                Intent intent = new Intent();
                intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                context.startActivity(o(context));
            }
        }
    }

    public static Intent o(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 617, new Class[]{Context.class}, Intent.class);
        if (proxy.isSupported) {
            return (Intent) proxy.result;
        }
        Intent localIntent = new Intent();
        localIntent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        int i = Build.VERSION.SDK_INT;
        if (i >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), (String) null));
        } else if (i <= 8) {
            localIntent.setAction("android.intent.action.VIEW");
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    public static void J(Activity activity) {
        Intent intent;
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 618, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            try {
                if (R()) {
                    if (Build.MANUFACTURER.equalsIgnoreCase(SystemUtil.PHONE_SAMSUNG)) {
                        intent = new Intent();
                        intent.setAction("android.settings.MANAGE_DOMAIN_URLS");
                    } else {
                        intent = new Intent();
                        intent.setAction("android.settings.APP_OPEN_BY_DEFAULT_SETTINGS");
                        intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        a.a("vary goToAppOpenByDefault: " + activity.getPackageName(), new Object[0]);
                    }
                    intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                    activity.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", activity.getPackageName(), (String) null));
                intent2.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                activity.startActivity(intent2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean R() {
        return Build.VERSION.SDK_INT >= 31;
    }

    public static String A(Context ctx, Intent externalIntent) {
        Bundle extras;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ctx, externalIntent}, (Object) null, changeQuickRedirect, true, 619, new Class[]{Context.class, Intent.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String pushData = externalIntent.getStringExtra("push_data");
        if (pushData == null && (extras = externalIntent.getExtras()) != null) {
            pushData = extras.getString("push_data");
        }
        if (TextUtils.isEmpty(pushData)) {
            pushData = SharePreferenceUtils.getPrefString(ctx, "push_data", "");
            if (!TextUtils.isEmpty(pushData)) {
                a.b g = a.g("Util");
                g.a("###### get sp pushData ######:" + pushData, new Object[0]);
            } else {
                pushData = Constans.push_data;
                if (!TextUtils.isEmpty(pushData)) {
                    a.b g2 = a.g("Util");
                    g2.a("###### get static pushData ######:" + pushData, new Object[0]);
                }
            }
        }
        return pushData;
    }

    public static void h(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 620, new Class[]{Context.class}, Void.TYPE).isSupported) {
            SharePreferenceUtils.setPrefString(context, "push_data", "");
            Constans.push_data = "";
        }
    }
}
