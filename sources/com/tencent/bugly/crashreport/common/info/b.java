package com.tencent.bugly.crashreport.common.info;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper;
import com.google.maps.android.BuildConfig;
import com.leedarson.serviceimpl.business.netease.LDNetDiagnoUtils.LDNetUtil;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: BUGLY */
public class b {
    private static final String[] a = {"/su", "/su/bin/su", "/sbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su", "/system/xbin/su", "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr", "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd", "/system/bin/conbb", "/system/xbin/conbb"};
    private static final String[] b = {"com.ami.duosupdater.ui", "com.ami.launchmetro", "com.ami.syncduosservices", "com.bluestacks.home", "com.bluestacks.windowsfilemanager", "com.bluestacks.settings", "com.bluestacks.bluestackslocationprovider", "com.bluestacks.appsettings", "com.bluestacks.bstfolder", "com.bluestacks.BstCommandProcessor", "com.bluestacks.s2p", "com.bluestacks.setup", "com.kaopu001.tiantianserver", "com.kpzs.helpercenter", "com.kaopu001.tiantianime", "com.android.development_settings", "com.android.development", "com.android.customlocale2", "com.genymotion.superuser", "com.genymotion.clipboardproxy", "com.uc.xxzs.keyboard", "com.uc.xxzs", "com.blue.huang17.agent", "com.blue.huang17.launcher", "com.blue.huang17.ime", "com.microvirt.guide", "com.microvirt.market", "com.microvirt.memuime", "cn.itools.vm.launcher", "cn.itools.vm.proxy", "cn.itools.vm.softkeyboard", "cn.itools.avdmarket", "com.syd.IME", "com.bignox.app.store.hd", "com.bignox.launcher", "com.bignox.app.phone", "com.bignox.app.noxservice", "com.android.noxpush", "com.haimawan.push", "me.haima.helpcenter", "com.windroy.launcher", "com.windroy.superuser", "com.windroy.launcher", "com.windroy.ime", "com.android.flysilkworm", "com.android.emu.inputservice", "com.tiantian.ime", "com.microvirt.launcher", "me.le8.androidassist", "com.vphone.helper", "com.vphone.launcher", "com.duoyi.giftcenter.giftcenter"};
    private static final String[] c = {"/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/qemud", "/dev/qemu_pipe", "/dev/socket/baseband_genyd", "/dev/socket/genyd"};
    private static String d = null;
    private static String e = null;

    public static String a() {
        try {
            return Build.MODEL;
        } catch (Throwable th) {
            if (x.a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    public static String b() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable th) {
            if (x.a(th)) {
                return "fail";
            }
            th.printStackTrace();
            return "fail";
        }
    }

    public static int c() {
        try {
            return Build.VERSION.SDK_INT;
        } catch (Throwable th) {
            if (x.a(th)) {
                return -1;
            }
            th.printStackTrace();
            return -1;
        }
    }

    public static String d() {
        return BuildConfig.TRAVIS;
    }

    public static String e() {
        return BuildConfig.TRAVIS;
    }

    public static String a(Context context) {
        if (context == null) {
            return "fail";
        }
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            if (string == null) {
                return BuildConfig.TRAVIS;
            }
            return string.toLowerCase();
        } catch (Throwable th) {
            if (!x.a(th)) {
                x.a("Failed to get Android ID.", new Object[0]);
            }
            return "fail";
        }
    }

    public static String f() {
        return BuildConfig.TRAVIS;
    }

    public static String b(Context context) {
        if (context == null) {
            return "fail";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return "fail";
            }
            String simSerialNumber = telephonyManager.getSimSerialNumber();
            if (simSerialNumber == null) {
                return BuildConfig.TRAVIS;
            }
            return simSerialNumber;
        } catch (Throwable th) {
            x.a("Failed to get SIM serial number.", new Object[0]);
            return "fail";
        }
    }

    public static String g() {
        try {
            return Build.SERIAL;
        } catch (Throwable th) {
            x.a("Failed to get hardware serial number.", new Object[0]);
            return "fail";
        }
    }

    private static boolean t() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            if (x.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    public static String a(Context context, boolean z) {
        String str = null;
        if (z) {
            try {
                String a2 = z.a(context, "ro.product.cpu.abilist");
                if (z.a(a2) || a2.equals("fail")) {
                    a2 = z.a(context, "ro.product.cpu.abi");
                }
                if (!z.a(a2)) {
                    if (!a2.equals("fail")) {
                        x.b(b.class, "ABI list: " + a2, new Object[0]);
                        str = a2.split(",")[0];
                    }
                }
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
                return "fail";
            }
        }
        if (str == null) {
            str = System.getProperty("os.arch");
        }
        return str;
    }

    public static long h() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    public static long i() {
        try {
            StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return -1;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: java.io.BufferedReader} */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: type inference failed for: r0v13 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0085 A[Catch:{ all -> 0x00ac }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x008a A[SYNTHETIC, Splitter:B:47:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x009a A[SYNTHETIC, Splitter:B:54:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:79:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long j() {
        /*
            java.lang.String r0 = "/proc/meminfo"
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ all -> 0x007b }
            r2.<init>(r0)     // Catch:{ all -> 0x007b }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ all -> 0x0076 }
            r3 = 2048(0x800, float:2.87E-42)
            r0.<init>(r2, r3)     // Catch:{ all -> 0x0076 }
            java.lang.String r1 = r0.readLine()     // Catch:{ all -> 0x0074 }
            if (r1 != 0) goto L_0x0036
            r0.close()     // Catch:{ IOException -> 0x001b }
            goto L_0x0025
        L_0x001b:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0025
            r0.printStackTrace()
        L_0x0025:
            r2.close()     // Catch:{ IOException -> 0x0029 }
            goto L_0x0033
        L_0x0029:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0033
            r0.printStackTrace()
        L_0x0033:
            r0 = -1
            return r0
        L_0x0036:
            java.lang.String r3 = ":\\s+"
            r4 = 2
            java.lang.String[] r1 = r1.split(r3, r4)     // Catch:{ all -> 0x0074 }
            r3 = 1
            r1 = r1[r3]     // Catch:{ all -> 0x0074 }
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ all -> 0x0074 }
            java.lang.String r3 = "kb"
            java.lang.String r4 = ""
            java.lang.String r1 = r1.replace(r3, r4)     // Catch:{ all -> 0x0074 }
            java.lang.String r1 = r1.trim()     // Catch:{ all -> 0x0074 }
            long r3 = java.lang.Long.parseLong(r1)     // Catch:{ all -> 0x0074 }
            r1 = 10
            long r3 = r3 << r1
            r0.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x0065
        L_0x005b:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0065
            r0.printStackTrace()
        L_0x0065:
            r2.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x0073
        L_0x0069:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0073
            r0.printStackTrace()
        L_0x0073:
            return r3
        L_0x0074:
            r1 = move-exception
            goto L_0x007f
        L_0x0076:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x007f
        L_0x007b:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x007f:
            boolean r3 = com.tencent.bugly.proguard.x.a(r1)     // Catch:{ all -> 0x00ac }
            if (r3 != 0) goto L_0x0088
            r1.printStackTrace()     // Catch:{ all -> 0x00ac }
        L_0x0088:
            if (r0 == 0) goto L_0x0098
            r0.close()     // Catch:{ IOException -> 0x008e }
            goto L_0x0098
        L_0x008e:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0098
            r0.printStackTrace()
        L_0x0098:
            if (r2 == 0) goto L_0x00a9
            r2.close()     // Catch:{ IOException -> 0x009e }
        L_0x009d:
            goto L_0x00a9
        L_0x009e:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x009d
            r0.printStackTrace()
            goto L_0x009d
        L_0x00a9:
            r0 = -2
            return r0
        L_0x00ac:
            r1 = move-exception
            if (r0 == 0) goto L_0x00bd
            r0.close()     // Catch:{ IOException -> 0x00b3 }
            goto L_0x00bd
        L_0x00b3:
            r0 = move-exception
            boolean r3 = com.tencent.bugly.proguard.x.a(r0)
            if (r3 != 0) goto L_0x00bd
            r0.printStackTrace()
        L_0x00bd:
            if (r2 == 0) goto L_0x00cd
            r2.close()     // Catch:{ IOException -> 0x00c3 }
            goto L_0x00cd
        L_0x00c3:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)
            if (r2 != 0) goto L_0x00cd
            r0.printStackTrace()
        L_0x00cd:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.j():long");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: java.io.FileReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: java.io.FileReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.io.BufferedReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.io.FileReader} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.io.FileReader} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:110:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x00fd A[Catch:{ all -> 0x0124 }] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0102 A[SYNTHETIC, Splitter:B:81:0x0102] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0112 A[SYNTHETIC, Splitter:B:88:0x0112] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long k() {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = "kb"
            java.lang.String r2 = ":\\s+"
            java.lang.String r3 = "/proc/meminfo"
            r4 = 0
            java.io.FileReader r5 = new java.io.FileReader     // Catch:{ all -> 0x00f5 }
            r5.<init>(r3)     // Catch:{ all -> 0x00f5 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x00f3 }
            r6 = 2048(0x800, float:2.87E-42)
            r3.<init>(r5, r6)     // Catch:{ all -> 0x00f3 }
            r3.readLine()     // Catch:{ all -> 0x00f0 }
            java.lang.String r4 = r3.readLine()     // Catch:{ all -> 0x00f0 }
            r6 = -1
            if (r4 != 0) goto L_0x003f
            r3.close()     // Catch:{ IOException -> 0x0026 }
            goto L_0x0030
        L_0x0026:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0030
            r0.printStackTrace()
        L_0x0030:
            r5.close()     // Catch:{ IOException -> 0x0034 }
            goto L_0x003e
        L_0x0034:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x003e
            r0.printStackTrace()
        L_0x003e:
            return r6
        L_0x003f:
            r8 = 2
            java.lang.String[] r4 = r4.split(r2, r8)     // Catch:{ all -> 0x00f0 }
            r9 = 1
            r4 = r4[r9]     // Catch:{ all -> 0x00f0 }
            java.lang.String r4 = r4.toLowerCase()     // Catch:{ all -> 0x00f0 }
            java.lang.String r4 = r4.replace(r1, r0)     // Catch:{ all -> 0x00f0 }
            java.lang.String r4 = r4.trim()     // Catch:{ all -> 0x00f0 }
            r10 = 0
            long r12 = java.lang.Long.parseLong(r4)     // Catch:{ all -> 0x00f0 }
            r4 = 10
            long r12 = r12 << r4
            long r12 = r12 + r10
            java.lang.String r10 = r3.readLine()     // Catch:{ all -> 0x00f0 }
            if (r10 != 0) goto L_0x0080
            r3.close()     // Catch:{ IOException -> 0x0067 }
            goto L_0x0071
        L_0x0067:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0071
            r0.printStackTrace()
        L_0x0071:
            r5.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x007f
        L_0x0075:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x007f
            r0.printStackTrace()
        L_0x007f:
            return r6
        L_0x0080:
            java.lang.String[] r10 = r10.split(r2, r8)     // Catch:{ all -> 0x00f0 }
            r10 = r10[r9]     // Catch:{ all -> 0x00f0 }
            java.lang.String r10 = r10.toLowerCase()     // Catch:{ all -> 0x00f0 }
            java.lang.String r10 = r10.replace(r1, r0)     // Catch:{ all -> 0x00f0 }
            java.lang.String r10 = r10.trim()     // Catch:{ all -> 0x00f0 }
            long r10 = java.lang.Long.parseLong(r10)     // Catch:{ all -> 0x00f0 }
            long r10 = r10 << r4
            long r12 = r12 + r10
            java.lang.String r10 = r3.readLine()     // Catch:{ all -> 0x00f0 }
            if (r10 != 0) goto L_0x00bb
            r3.close()     // Catch:{ IOException -> 0x00a2 }
            goto L_0x00ac
        L_0x00a2:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x00ac
            r0.printStackTrace()
        L_0x00ac:
            r5.close()     // Catch:{ IOException -> 0x00b0 }
            goto L_0x00ba
        L_0x00b0:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x00ba
            r0.printStackTrace()
        L_0x00ba:
            return r6
        L_0x00bb:
            java.lang.String[] r2 = r10.split(r2, r8)     // Catch:{ all -> 0x00f0 }
            r2 = r2[r9]     // Catch:{ all -> 0x00f0 }
            java.lang.String r2 = r2.toLowerCase()     // Catch:{ all -> 0x00f0 }
            java.lang.String r0 = r2.replace(r1, r0)     // Catch:{ all -> 0x00f0 }
            java.lang.String r0 = r0.trim()     // Catch:{ all -> 0x00f0 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ all -> 0x00f0 }
            long r0 = r0 << r4
            long r12 = r12 + r0
            r3.close()     // Catch:{ IOException -> 0x00d7 }
            goto L_0x00e1
        L_0x00d7:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x00e1
            r0.printStackTrace()
        L_0x00e1:
            r5.close()     // Catch:{ IOException -> 0x00e5 }
            goto L_0x00ef
        L_0x00e5:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x00ef
            r0.printStackTrace()
        L_0x00ef:
            return r12
        L_0x00f0:
            r0 = move-exception
            r4 = r3
            goto L_0x00f7
        L_0x00f3:
            r0 = move-exception
            goto L_0x00f7
        L_0x00f5:
            r0 = move-exception
            r5 = r4
        L_0x00f7:
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x0124 }
            if (r1 != 0) goto L_0x0100
            r0.printStackTrace()     // Catch:{ all -> 0x0124 }
        L_0x0100:
            if (r4 == 0) goto L_0x0110
            r4.close()     // Catch:{ IOException -> 0x0106 }
            goto L_0x0110
        L_0x0106:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0110
            r0.printStackTrace()
        L_0x0110:
            if (r5 == 0) goto L_0x0121
            r5.close()     // Catch:{ IOException -> 0x0116 }
        L_0x0115:
            goto L_0x0121
        L_0x0116:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0115
            r0.printStackTrace()
            goto L_0x0115
        L_0x0121:
            r0 = -2
            return r0
        L_0x0124:
            r0 = move-exception
            if (r4 == 0) goto L_0x0135
            r4.close()     // Catch:{ IOException -> 0x012b }
            goto L_0x0135
        L_0x012b:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x0135
            r1.printStackTrace()
        L_0x0135:
            if (r5 == 0) goto L_0x0145
            r5.close()     // Catch:{ IOException -> 0x013b }
            goto L_0x0145
        L_0x013b:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r1)
            if (r2 != 0) goto L_0x0145
            r1.printStackTrace()
        L_0x0145:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.k():long");
    }

    public static long l() {
        if (!t()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (x.a(th)) {
                return -2;
            }
            th.printStackTrace();
            return -2;
        }
    }

    public static long m() {
        if (!t()) {
            return 0;
        }
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        } catch (Throwable th) {
            if (x.a(th)) {
                return -2;
            }
            th.printStackTrace();
            return -2;
        }
    }

    public static String n() {
        try {
            return Locale.getDefault().getCountry();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static String o() {
        try {
            return Build.BRAND;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return "fail";
        }
    }

    public static String c(Context context) {
        TelephonyManager telephonyManager;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return null;
            }
            if (activeNetworkInfo.getType() == 1) {
                return LDNetUtil.NETWORKTYPE_WIFI;
            }
            if (activeNetworkInfo.getType() != 0 || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null) {
                return "unknown";
            }
            int networkType = telephonyManager.getNetworkType();
            switch (networkType) {
                case 1:
                    return "GPRS";
                case 2:
                    return "EDGE";
                case 3:
                    return "UMTS";
                case 4:
                    return "CDMA";
                case 5:
                    return "EVDO_0";
                case 6:
                    return "EVDO_A";
                case 7:
                    return "1xRTT";
                case 8:
                    return "HSDPA";
                case 9:
                    return "HSUPA";
                case 10:
                    return "HSPA";
                case 11:
                    return "iDen";
                case 12:
                    return "EVDO_B";
                case 13:
                    return "LTE";
                case 14:
                    return "eHRPD";
                case 15:
                    return "HSPA+";
                default:
                    return "MOBILE(" + networkType + ")";
            }
        } catch (Exception e2) {
            if (x.a(e2)) {
                return "unknown";
            }
            e2.printStackTrace();
            return "unknown";
        }
    }

    public static String d(Context context) {
        String a2 = z.a(context, "ro.miui.ui.version.name");
        if (z.a(a2) || a2.equals("fail")) {
            String a3 = z.a(context, "ro.build.version.emui");
            if (z.a(a3) || a3.equals("fail")) {
                String a4 = z.a(context, "ro.lenovo.series");
                if (z.a(a4) || a4.equals("fail")) {
                    String a5 = z.a(context, "ro.build.nubia.rom.name");
                    if (z.a(a5) || a5.equals("fail")) {
                        String a6 = z.a(context, "ro.meizu.product.model");
                        if (z.a(a6) || a6.equals("fail")) {
                            String a7 = z.a(context, "ro.build.version.opporom");
                            if (z.a(a7) || a7.equals("fail")) {
                                String a8 = z.a(context, "ro.vivo.os.build.display.id");
                                if (z.a(a8) || a8.equals("fail")) {
                                    String a9 = z.a(context, "ro.aa.romver");
                                    if (z.a(a9) || a9.equals("fail")) {
                                        String a10 = z.a(context, "ro.lewa.version");
                                        if (z.a(a10) || a10.equals("fail")) {
                                            String a11 = z.a(context, "ro.gn.gnromvernumber");
                                            if (z.a(a11) || a11.equals("fail")) {
                                                String a12 = z.a(context, "ro.build.tyd.kbstyle_version");
                                                if (z.a(a12) || a12.equals("fail")) {
                                                    return z.a(context, "ro.build.fingerprint") + "/" + z.a(context, "ro.build.rom.id");
                                                }
                                                return "dido/" + a12;
                                            }
                                            return "amigo/" + a11 + "/" + z.a(context, "ro.build.display.id");
                                        }
                                        return "tcl/" + a10 + "/" + z.a(context, "ro.build.display.id");
                                    }
                                    return "htc/" + a9 + "/" + z.a(context, "ro.build.description");
                                }
                                return "vivo/FUNTOUCH/" + a8;
                            }
                            return "Oppo/COLOROS/" + a7;
                        }
                        return "Meizu/FLYME/" + z.a(context, "ro.build.display.id");
                    }
                    return "Zte/NUBIA/" + a5 + "_" + z.a(context, "ro.build.nubia.rom.code");
                }
                String a13 = z.a(context, "ro.build.version.incremental");
                return "Lenovo/VIBE/" + a13;
            }
            return "HuaWei/EMOTION/" + a3;
        }
        return "XiaoMi/MIUI/" + a2;
    }

    public static String e(Context context) {
        return z.a(context, "ro.board.platform");
    }

    public static boolean p() {
        boolean z;
        String[] strArr = a;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (new File(strArr[i]).exists()) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        String str = Build.TAGS;
        return (str != null && str.contains("test-keys")) || z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0095 A[SYNTHETIC, Splitter:B:45:0x0095] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String q() {
        /*
            java.lang.String r0 = "/sys/block/mmcblk0/device/cid"
            java.lang.String r1 = "/sys/block/mmcblk0/device/name"
            java.lang.String r2 = ","
            java.lang.String r3 = "/sys/block/mmcblk0/device/type"
            r4 = 0
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0091 }
            r5.<init>()     // Catch:{ all -> 0x0091 }
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x0091 }
            r6.<init>(r3)     // Catch:{ all -> 0x0091 }
            boolean r6 = r6.exists()     // Catch:{ all -> 0x0091 }
            if (r6 == 0) goto L_0x0030
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ all -> 0x0091 }
            java.io.FileReader r7 = new java.io.FileReader     // Catch:{ all -> 0x0091 }
            r7.<init>(r3)     // Catch:{ all -> 0x0091 }
            r6.<init>(r7)     // Catch:{ all -> 0x0091 }
            java.lang.String r3 = r6.readLine()     // Catch:{ all -> 0x008f }
            if (r3 == 0) goto L_0x002c
            r5.append(r3)     // Catch:{ all -> 0x008f }
        L_0x002c:
            r6.close()     // Catch:{ all -> 0x008f }
            goto L_0x0031
        L_0x0030:
            r6 = r4
        L_0x0031:
            r5.append(r2)     // Catch:{ all -> 0x008f }
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x008f }
            r3.<init>(r1)     // Catch:{ all -> 0x008f }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x008f }
            if (r3 == 0) goto L_0x005a
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x008f }
            java.io.FileReader r7 = new java.io.FileReader     // Catch:{ all -> 0x008f }
            r7.<init>(r1)     // Catch:{ all -> 0x008f }
            r3.<init>(r7)     // Catch:{ all -> 0x008f }
            java.lang.String r1 = r3.readLine()     // Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x0052
            r5.append(r1)     // Catch:{ all -> 0x0057 }
        L_0x0052:
            r3.close()     // Catch:{ all -> 0x0057 }
            r6 = r3
            goto L_0x005a
        L_0x0057:
            r0 = move-exception
            r6 = r3
            goto L_0x0093
        L_0x005a:
            r5.append(r2)     // Catch:{ all -> 0x008f }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x008f }
            r1.<init>(r0)     // Catch:{ all -> 0x008f }
            boolean r1 = r1.exists()     // Catch:{ all -> 0x008f }
            if (r1 == 0) goto L_0x0080
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ all -> 0x008f }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ all -> 0x008f }
            r2.<init>(r0)     // Catch:{ all -> 0x008f }
            r1.<init>(r2)     // Catch:{ all -> 0x008f }
            java.lang.String r0 = r1.readLine()     // Catch:{ all -> 0x007d }
            if (r0 == 0) goto L_0x007b
            r5.append(r0)     // Catch:{ all -> 0x007d }
        L_0x007b:
            r6 = r1
            goto L_0x0080
        L_0x007d:
            r0 = move-exception
            r6 = r1
            goto L_0x0093
        L_0x0080:
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x008f }
            if (r6 == 0) goto L_0x008e
            r6.close()     // Catch:{ IOException -> 0x008a }
            goto L_0x008e
        L_0x008a:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
        L_0x008e:
            return r0
        L_0x008f:
            r0 = move-exception
            goto L_0x0093
        L_0x0091:
            r0 = move-exception
            r6 = r4
        L_0x0093:
            if (r6 == 0) goto L_0x009d
            r6.close()     // Catch:{ IOException -> 0x0099 }
            goto L_0x009d
        L_0x0099:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
        L_0x009d:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.q():java.lang.String");
    }

    public static String f(Context context) {
        StringBuilder sb = new StringBuilder();
        String a2 = z.a(context, "ro.genymotion.version");
        if (a2 != null) {
            sb.append("ro.genymotion.version");
            sb.append("|");
            sb.append(a2);
            sb.append("\n");
        }
        String a3 = z.a(context, "androVM.vbox_dpi");
        if (a3 != null) {
            sb.append("androVM.vbox_dpi");
            sb.append("|");
            sb.append(a3);
            sb.append("\n");
        }
        String a4 = z.a(context, "qemu.sf.fake_camera");
        if (a4 != null) {
            sb.append("qemu.sf.fake_camera");
            sb.append("|");
            sb.append(a4);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0066 A[Catch:{ all -> 0x0089 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String g(android.content.Context r6) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = d
            java.lang.String r2 = "ro.secure"
            if (r1 != 0) goto L_0x0012
            java.lang.String r1 = com.tencent.bugly.proguard.z.a((android.content.Context) r6, (java.lang.String) r2)
            d = r1
        L_0x0012:
            java.lang.String r1 = d
            java.lang.String r3 = "\n"
            java.lang.String r4 = "|"
            if (r1 == 0) goto L_0x0029
            r0.append(r2)
            r0.append(r4)
            java.lang.String r1 = d
            r0.append(r1)
            r0.append(r3)
        L_0x0029:
            java.lang.String r1 = e
            java.lang.String r2 = "ro.debuggable"
            if (r1 != 0) goto L_0x0036
            java.lang.String r6 = com.tencent.bugly.proguard.z.a((android.content.Context) r6, (java.lang.String) r2)
            e = r6
        L_0x0036:
            java.lang.String r6 = e
            if (r6 == 0) goto L_0x0048
            r0.append(r2)
            r0.append(r4)
            java.lang.String r6 = e
            r0.append(r6)
            r0.append(r3)
        L_0x0048:
            r6 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ all -> 0x008b }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ all -> 0x008b }
            java.lang.String r3 = "/proc/self/status"
            r2.<init>(r3)     // Catch:{ all -> 0x008b }
            r1.<init>(r2)     // Catch:{ all -> 0x008b }
        L_0x0056:
            java.lang.String r6 = r1.readLine()     // Catch:{ all -> 0x0089 }
            if (r6 == 0) goto L_0x0064
            java.lang.String r2 = "TracerPid:"
            boolean r2 = r6.startsWith(r2)     // Catch:{ all -> 0x0089 }
            if (r2 == 0) goto L_0x0056
        L_0x0064:
            if (r6 == 0) goto L_0x007c
            r2 = 10
            java.lang.String r6 = r6.substring(r2)     // Catch:{ all -> 0x0089 }
            java.lang.String r6 = r6.trim()     // Catch:{ all -> 0x0089 }
            java.lang.String r2 = "tracer_pid"
            r0.append(r2)     // Catch:{ all -> 0x0089 }
            r0.append(r4)     // Catch:{ all -> 0x0089 }
            r0.append(r6)     // Catch:{ all -> 0x0089 }
        L_0x007c:
            java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x0089 }
            r1.close()     // Catch:{ IOException -> 0x0084 }
            goto L_0x0088
        L_0x0084:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
        L_0x0088:
            return r6
        L_0x0089:
            r6 = move-exception
            goto L_0x008f
        L_0x008b:
            r1 = move-exception
            r5 = r1
            r1 = r6
            r6 = r5
        L_0x008f:
            com.tencent.bugly.proguard.x.a(r6)     // Catch:{ all -> 0x00a2 }
            if (r1 == 0) goto L_0x009d
            r1.close()     // Catch:{ IOException -> 0x0098 }
        L_0x0097:
            goto L_0x009d
        L_0x0098:
            r6 = move-exception
            com.tencent.bugly.proguard.x.a(r6)
            goto L_0x0097
        L_0x009d:
            java.lang.String r6 = r0.toString()
            return r6
        L_0x00a2:
            r6 = move-exception
            if (r1 == 0) goto L_0x00ad
            r1.close()     // Catch:{ IOException -> 0x00a9 }
            goto L_0x00ad
        L_0x00a9:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
        L_0x00ad:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.g(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00ad A[Catch:{ IOException -> 0x00b1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String r() {
        /*
            java.lang.String r0 = "/sys/class/power_supply/battery/capacity"
            java.lang.String r1 = "/sys/class/power_supply/usb/online"
            java.lang.String r2 = "\n"
            java.lang.String r3 = "/sys/class/power_supply/ac/online"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r5 = 0
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x00aa }
            r6.<init>(r3)     // Catch:{ all -> 0x00aa }
            boolean r6 = r6.exists()     // Catch:{ all -> 0x00aa }
            java.lang.String r7 = "|"
            if (r6 == 0) goto L_0x0041
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ all -> 0x00aa }
            java.io.FileReader r8 = new java.io.FileReader     // Catch:{ all -> 0x00aa }
            r8.<init>(r3)     // Catch:{ all -> 0x00aa }
            r6.<init>(r8)     // Catch:{ all -> 0x00aa }
            java.lang.String r3 = r6.readLine()     // Catch:{ all -> 0x003d }
            if (r3 == 0) goto L_0x0038
            java.lang.String r5 = "ac_online"
            r4.append(r5)     // Catch:{ all -> 0x003d }
            r4.append(r7)     // Catch:{ all -> 0x003d }
            r4.append(r3)     // Catch:{ all -> 0x003d }
        L_0x0038:
            r6.close()     // Catch:{ all -> 0x003d }
            r5 = r6
            goto L_0x0041
        L_0x003d:
            r0 = move-exception
            r5 = r6
            goto L_0x00ab
        L_0x0041:
            r4.append(r2)     // Catch:{ all -> 0x00aa }
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x00aa }
            r3.<init>(r1)     // Catch:{ all -> 0x00aa }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x00aa }
            if (r3 == 0) goto L_0x0073
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x00aa }
            java.io.FileReader r6 = new java.io.FileReader     // Catch:{ all -> 0x00aa }
            r6.<init>(r1)     // Catch:{ all -> 0x00aa }
            r3.<init>(r6)     // Catch:{ all -> 0x00aa }
            java.lang.String r1 = r3.readLine()     // Catch:{ all -> 0x0070 }
            if (r1 == 0) goto L_0x006b
            java.lang.String r5 = "usb_online"
            r4.append(r5)     // Catch:{ all -> 0x0070 }
            r4.append(r7)     // Catch:{ all -> 0x0070 }
            r4.append(r1)     // Catch:{ all -> 0x0070 }
        L_0x006b:
            r3.close()     // Catch:{ all -> 0x0070 }
            r5 = r3
            goto L_0x0073
        L_0x0070:
            r0 = move-exception
            r5 = r3
            goto L_0x00ab
        L_0x0073:
            r4.append(r2)     // Catch:{ all -> 0x00aa }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x00aa }
            r1.<init>(r0)     // Catch:{ all -> 0x00aa }
            boolean r1 = r1.exists()     // Catch:{ all -> 0x00aa }
            if (r1 == 0) goto L_0x00a4
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ all -> 0x00aa }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ all -> 0x00aa }
            r2.<init>(r0)     // Catch:{ all -> 0x00aa }
            r1.<init>(r2)     // Catch:{ all -> 0x00aa }
            java.lang.String r0 = r1.readLine()     // Catch:{ all -> 0x00a1 }
            if (r0 == 0) goto L_0x009c
            java.lang.String r2 = "battery_capacity"
            r4.append(r2)     // Catch:{ all -> 0x00a1 }
            r4.append(r7)     // Catch:{ all -> 0x00a1 }
            r4.append(r0)     // Catch:{ all -> 0x00a1 }
        L_0x009c:
            r1.close()     // Catch:{ all -> 0x00a1 }
            r5 = r1
            goto L_0x00a4
        L_0x00a1:
            r0 = move-exception
            r5 = r1
            goto L_0x00ab
        L_0x00a4:
            if (r5 == 0) goto L_0x00b6
            r5.close()     // Catch:{ IOException -> 0x00b1 }
            goto L_0x00b0
        L_0x00aa:
            r0 = move-exception
        L_0x00ab:
            if (r5 == 0) goto L_0x00b6
            r5.close()     // Catch:{ IOException -> 0x00b1 }
        L_0x00b0:
            goto L_0x00b6
        L_0x00b1:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
            goto L_0x00b0
        L_0x00b6:
            java.lang.String r0 = r4.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.r():java.lang.String");
    }

    public static String h(Context context) {
        StringBuilder sb = new StringBuilder();
        String a2 = z.a(context, "gsm.sim.state");
        if (a2 != null) {
            sb.append("gsm.sim.state");
            sb.append("|");
            sb.append(a2);
        }
        sb.append("\n");
        String a3 = z.a(context, "gsm.sim.state2");
        if (a3 != null) {
            sb.append("gsm.sim.state2");
            sb.append("|");
            sb.append(a3);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043 A[SYNTHETIC, Splitter:B:19:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long s() {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x0039 }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ all -> 0x0039 }
            java.lang.String r5 = "/proc/uptime"
            r4.<init>(r5)     // Catch:{ all -> 0x0039 }
            r3.<init>(r4)     // Catch:{ all -> 0x0039 }
            java.lang.String r2 = r3.readLine()     // Catch:{ all -> 0x0036 }
            if (r2 == 0) goto L_0x002d
            java.lang.String r4 = " "
            java.lang.String[] r2 = r2.split(r4)     // Catch:{ all -> 0x0036 }
            r2 = r2[r0]     // Catch:{ all -> 0x0036 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0036 }
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r6
            float r4 = (float) r4     // Catch:{ all -> 0x0036 }
            float r0 = java.lang.Float.parseFloat(r2)     // Catch:{ all -> 0x0036 }
            float r4 = r4 - r0
            r1 = r4
        L_0x002d:
            r3.close()     // Catch:{ IOException -> 0x0031 }
        L_0x0030:
            goto L_0x0047
        L_0x0031:
            r0 = move-exception
            com.tencent.bugly.proguard.x.a(r0)
            goto L_0x0030
        L_0x0036:
            r2 = move-exception
            r2 = r3
            goto L_0x003a
        L_0x0039:
            r3 = move-exception
        L_0x003a:
            java.lang.String r3 = "Failed to get boot time of device."
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x0049 }
            com.tencent.bugly.proguard.x.a(r3, r0)     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x0047
            r2.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0030
        L_0x0047:
            long r0 = (long) r1
            return r0
        L_0x0049:
            r0 = move-exception
            if (r2 == 0) goto L_0x0054
            r2.close()     // Catch:{ IOException -> 0x0050 }
            goto L_0x0054
        L_0x0050:
            r1 = move-exception
            com.tencent.bugly.proguard.x.a(r1)
        L_0x0054:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.s():long");
    }

    public static boolean i(Context context) {
        File file;
        if (k(context) != null) {
            return true;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            String[] strArr = c;
            if (i >= strArr.length) {
                break;
            }
            if (i == 0) {
                String str = strArr[i];
                if (file.exists()) {
                    i++;
                }
            } else {
                file = new File(strArr[i]);
                if (!file.exists()) {
                    i++;
                }
            }
            arrayList.add(Integer.valueOf(i));
            i++;
        }
        return (arrayList.isEmpty() ? null : arrayList.toString()) != null;
    }

    private static String k(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            String[] strArr = b;
            if (i >= strArr.length) {
                break;
            }
            try {
                packageManager.getPackageInfo(strArr[i], 1);
                arrayList.add(Integer.valueOf(i));
            } catch (PackageManager.NameNotFoundException e2) {
            }
            i++;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList.toString();
    }

    public static boolean j(Context context) {
        return (((l(context) | v()) | w()) | u()) > 0;
    }

    private static int u() {
        try {
            Method method = Class.forName("android.app.ActivityManagerNative").getMethod("getDefault", new Class[0]);
            method.setAccessible(true);
            if (method.invoke((Object) null, new Object[0]).getClass().getName().startsWith("$Proxy")) {
                return 256;
            }
            return 0;
        } catch (Exception e2) {
            return 256;
        }
    }

    private static int l(Context context) {
        int i;
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getInstallerPackageName("de.robv.android.xposed.installer");
            i = 1;
        } catch (Exception e2) {
            i = 0;
        }
        try {
            packageManager.getInstallerPackageName("com.saurik.substrate");
            return i | 2;
        } catch (Exception e3) {
            return i;
        }
    }

    private static int v() {
        try {
            throw new Exception("detect hook");
        } catch (Exception e2) {
            int i = 0;
            int i2 = 0;
            for (StackTraceElement stackTraceElement : e2.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals(LogcatHelper.BUFFER_MAIN)) {
                    i |= 4;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    i |= 8;
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") && stackTraceElement.getMethodName().equals("invoked")) {
                    i |= 16;
                }
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit") && (i2 = i2 + 1) == 2) {
                    i |= 32;
                }
            }
            return i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x009e A[SYNTHETIC, Splitter:B:33:0x009e] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00aa A[SYNTHETIC, Splitter:B:40:0x00aa] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b6 A[SYNTHETIC, Splitter:B:47:0x00b6] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00bf A[SYNTHETIC, Splitter:B:53:0x00bf] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:44:0x00b1=Splitter:B:44:0x00b1, B:37:0x00a5=Splitter:B:37:0x00a5, B:30:0x0099=Splitter:B:30:0x0099} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int w() {
        /*
            r0 = 0
            r1 = 0
            java.util.HashSet r2 = new java.util.HashSet     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            r2.<init>()     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            java.lang.String r7 = "/proc/"
            r6.<init>(r7)     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            int r7 = android.os.Process.myPid()     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            r6.append(r7)     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            java.lang.String r7 = "/maps"
            r6.append(r7)     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            java.lang.String r6 = r6.toString()     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            r5.<init>(r6)     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            java.lang.String r6 = "utf-8"
            r4.<init>(r5, r6)     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
            r3.<init>(r4)     // Catch:{ UnsupportedEncodingException -> 0x00ae, FileNotFoundException -> 0x00a2, IOException -> 0x0096, all -> 0x0094 }
        L_0x0032:
            java.lang.String r1 = r3.readLine()     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            if (r1 == 0) goto L_0x0058
            java.lang.String r4 = ".so"
            boolean r4 = r1.endsWith(r4)     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            if (r4 != 0) goto L_0x0048
            java.lang.String r4 = ".jar"
            boolean r4 = r1.endsWith(r4)     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            if (r4 == 0) goto L_0x0032
        L_0x0048:
            java.lang.String r4 = " "
            int r4 = r1.lastIndexOf(r4)     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            int r4 = r4 + 1
            java.lang.String r1 = r1.substring(r4)     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            r2.add(r1)     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            goto L_0x0032
        L_0x0058:
            java.util.Iterator r1 = r2.iterator()     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
        L_0x005c:
            boolean r2 = r1.hasNext()     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            if (r2 == 0) goto L_0x0085
            java.lang.Object r2 = r1.next()     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            r4 = r2
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            java.lang.String r4 = r4.toLowerCase()     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            java.lang.String r5 = "xposed"
            boolean r4 = r4.contains(r5)     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            if (r4 == 0) goto L_0x0078
            r0 = r0 | 64
        L_0x0078:
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            java.lang.String r4 = "com.saurik.substrate"
            boolean r2 = r2.contains(r4)     // Catch:{ UnsupportedEncodingException -> 0x0092, FileNotFoundException -> 0x0090, IOException -> 0x008e }
            if (r2 == 0) goto L_0x0084
            r0 = r0 | 128(0x80, float:1.794E-43)
        L_0x0084:
            goto L_0x005c
        L_0x0085:
            r3.close()     // Catch:{ IOException -> 0x0089 }
        L_0x0088:
            goto L_0x00ba
        L_0x0089:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0088
        L_0x008e:
            r1 = move-exception
            goto L_0x0099
        L_0x0090:
            r1 = move-exception
            goto L_0x00a5
        L_0x0092:
            r1 = move-exception
            goto L_0x00b1
        L_0x0094:
            r0 = move-exception
            goto L_0x00bd
        L_0x0096:
            r2 = move-exception
            r3 = r1
            r1 = r2
        L_0x0099:
            r1.printStackTrace()     // Catch:{ all -> 0x00bb }
            if (r3 == 0) goto L_0x00ba
            r3.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x0088
        L_0x00a2:
            r2 = move-exception
            r3 = r1
            r1 = r2
        L_0x00a5:
            r1.printStackTrace()     // Catch:{ all -> 0x00bb }
            if (r3 == 0) goto L_0x00ba
            r3.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x0088
        L_0x00ae:
            r2 = move-exception
            r3 = r1
            r1 = r2
        L_0x00b1:
            r1.printStackTrace()     // Catch:{ all -> 0x00bb }
            if (r3 == 0) goto L_0x00ba
            r3.close()     // Catch:{ IOException -> 0x0089 }
            goto L_0x0088
        L_0x00ba:
            return r0
        L_0x00bb:
            r0 = move-exception
            r1 = r3
        L_0x00bd:
            if (r1 == 0) goto L_0x00c7
            r1.close()     // Catch:{ IOException -> 0x00c3 }
            goto L_0x00c7
        L_0x00c3:
            r1 = move-exception
            r1.printStackTrace()
        L_0x00c7:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.common.info.b.w():int");
    }
}
