package com.tencent.bugly.crashreport.crash.jni;

import android.annotation.SuppressLint;
import android.content.Context;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.a;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.b;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.io.File;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: BUGLY */
public class NativeCrashHandler implements a {
    private static NativeCrashHandler a;
    private static boolean l = false;
    private static boolean m = false;
    /* access modifiers changed from: private */
    public static boolean o = true;
    /* access modifiers changed from: private */
    public final Context b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final w d;
    /* access modifiers changed from: private */
    public NativeExceptionHandler e;
    /* access modifiers changed from: private */
    public String f;
    private final boolean g;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    /* access modifiers changed from: private */
    public b n;

    /* access modifiers changed from: protected */
    public native boolean appendNativeLog(String str, String str2, String str3);

    /* access modifiers changed from: protected */
    public native boolean appendWholeNativeLog(String str);

    /* access modifiers changed from: protected */
    public native String getNativeKeyValueList();

    /* access modifiers changed from: protected */
    public native String getNativeLog();

    /* access modifiers changed from: protected */
    public native boolean putNativeKeyValue(String str, String str2);

    /* access modifiers changed from: protected */
    public native String regist(String str, boolean z, int i2);

    /* access modifiers changed from: protected */
    public native String removeNativeKeyValue(String str);

    /* access modifiers changed from: protected */
    public native void setNativeInfo(int i2, String str);

    /* access modifiers changed from: protected */
    public native void testCrash();

    /* access modifiers changed from: protected */
    public native String unregist();

    @SuppressLint({"SdCardPath"})
    private NativeCrashHandler(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, w wVar, boolean z, String str) {
        this.b = z.a(context);
        try {
            if (z.a(str)) {
                str = context.getDir("bugly", 0).getAbsolutePath();
            }
        } catch (Throwable th) {
            str = "/data/data/" + com.tencent.bugly.crashreport.common.info.a.a(context).c + "/app_bugly";
        }
        this.n = bVar;
        this.f = str;
        this.c = aVar;
        this.d = wVar;
        this.g = z;
        this.e = new a(context, aVar, bVar, com.tencent.bugly.crashreport.common.strategy.a.a());
    }

    public static synchronized NativeCrashHandler getInstance(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2, w wVar, boolean z, String str) {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            if (a == null) {
                a = new NativeCrashHandler(context, aVar, bVar, wVar, z, str);
            }
            nativeCrashHandler = a;
        }
        return nativeCrashHandler;
    }

    public static synchronized NativeCrashHandler getInstance() {
        NativeCrashHandler nativeCrashHandler;
        synchronized (NativeCrashHandler.class) {
            nativeCrashHandler = a;
        }
        return nativeCrashHandler;
    }

    public synchronized String getDumpFilePath() {
        return this.f;
    }

    public synchronized void setDumpFilePath(String str) {
        this.f = str;
    }

    public static void setShouldHandleInJava(boolean z) {
        o = z;
        NativeCrashHandler nativeCrashHandler = a;
        if (nativeCrashHandler != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(z);
            nativeCrashHandler.a(999, sb.toString());
        }
    }

    public static boolean isShouldHandleInJava() {
        return o;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0081 A[Catch:{ all -> 0x0090 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x008d A[Catch:{ all -> 0x0090 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0095 A[Catch:{ all -> 0x00c6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009d A[Catch:{ all -> 0x00c6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a8 A[Catch:{ all -> 0x00c6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b0 A[Catch:{ all -> 0x00c6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void a(boolean r13) {
        /*
            r12 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            monitor-enter(r12)
            boolean r1 = r12.j     // Catch:{ all -> 0x019a }
            r2 = 0
            if (r1 == 0) goto L_0x0011
            java.lang.String r13 = "[Native] Native crash report has already registered."
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x019a }
            com.tencent.bugly.proguard.x.d(r13, r0)     // Catch:{ all -> 0x019a }
            monitor-exit(r12)
            return
        L_0x0011:
            boolean r1 = r12.i     // Catch:{ all -> 0x019a }
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L_0x00cf
            java.lang.String r0 = r12.f     // Catch:{ all -> 0x00c6 }
            java.lang.String r13 = r12.regist(r0, r13, r4)     // Catch:{ all -> 0x00c6 }
            if (r13 == 0) goto L_0x00c4
            java.lang.String r0 = "[Native] Native Crash Report enable."
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x00c6 }
            com.tencent.bugly.proguard.x.a(r0, r1)     // Catch:{ all -> 0x00c6 }
            java.lang.String r0 = "[Native] Check extra jni for Bugly NDK v%s"
            java.lang.Object[] r1 = new java.lang.Object[r4]     // Catch:{ all -> 0x00c6 }
            r1[r2] = r13     // Catch:{ all -> 0x00c6 }
            com.tencent.bugly.proguard.x.c(r0, r1)     // Catch:{ all -> 0x00c6 }
            java.lang.String r0 = "2.1.1"
            java.lang.String r1 = "."
            java.lang.String r5 = ""
            java.lang.String r0 = r0.replace(r1, r5)     // Catch:{ all -> 0x00c6 }
            java.lang.String r1 = "2.3.0"
            java.lang.String r5 = "."
            java.lang.String r6 = ""
            java.lang.String r1 = r1.replace(r5, r6)     // Catch:{ all -> 0x00c6 }
            java.lang.String r5 = "."
            java.lang.String r6 = ""
            java.lang.String r5 = r13.replace(r5, r6)     // Catch:{ all -> 0x00c6 }
            int r6 = r5.length()     // Catch:{ all -> 0x00c6 }
            if (r6 != r3) goto L_0x0063
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c6 }
            r3.<init>()     // Catch:{ all -> 0x00c6 }
            r3.append(r5)     // Catch:{ all -> 0x00c6 }
            java.lang.String r5 = "0"
            r3.append(r5)     // Catch:{ all -> 0x00c6 }
        L_0x005e:
            java.lang.String r5 = r3.toString()     // Catch:{ all -> 0x00c6 }
            goto L_0x0077
        L_0x0063:
            int r3 = r5.length()     // Catch:{ all -> 0x00c6 }
            if (r3 != r4) goto L_0x0077
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c6 }
            r3.<init>()     // Catch:{ all -> 0x00c6 }
            r3.append(r5)     // Catch:{ all -> 0x00c6 }
            java.lang.String r5 = "00"
            r3.append(r5)     // Catch:{ all -> 0x00c6 }
            goto L_0x005e
        L_0x0077:
            int r3 = java.lang.Integer.parseInt(r5)     // Catch:{ all -> 0x0090 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ all -> 0x0090 }
            if (r3 < r0) goto L_0x0083
            l = r4     // Catch:{ all -> 0x0090 }
        L_0x0083:
            int r0 = java.lang.Integer.parseInt(r5)     // Catch:{ all -> 0x0090 }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ all -> 0x0090 }
            if (r0 < r1) goto L_0x0091
            m = r4     // Catch:{ all -> 0x0090 }
            goto L_0x0091
        L_0x0090:
            r0 = move-exception
        L_0x0091:
            boolean r0 = m     // Catch:{ all -> 0x00c6 }
            if (r0 == 0) goto L_0x009d
            java.lang.String r0 = "[Native] Info setting jni can be accessed."
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x00c6 }
            com.tencent.bugly.proguard.x.a(r0, r1)     // Catch:{ all -> 0x00c6 }
            goto L_0x00a4
        L_0x009d:
            java.lang.String r0 = "[Native] Info setting jni can not be accessed."
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x00c6 }
            com.tencent.bugly.proguard.x.d(r0, r1)     // Catch:{ all -> 0x00c6 }
        L_0x00a4:
            boolean r0 = l     // Catch:{ all -> 0x00c6 }
            if (r0 == 0) goto L_0x00b0
            java.lang.String r0 = "[Native] Extra jni can be accessed."
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x00c6 }
            com.tencent.bugly.proguard.x.a(r0, r1)     // Catch:{ all -> 0x00c6 }
            goto L_0x00b7
        L_0x00b0:
            java.lang.String r0 = "[Native] Extra jni can not be accessed."
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x00c6 }
            com.tencent.bugly.proguard.x.d(r0, r1)     // Catch:{ all -> 0x00c6 }
        L_0x00b7:
            com.tencent.bugly.crashreport.common.info.a r0 = r12.c     // Catch:{ all -> 0x00c6 }
            r0.n = r13     // Catch:{ all -> 0x00c6 }
            boolean r13 = l     // Catch:{ all -> 0x00c6 }
            com.tencent.bugly.proguard.y.a((boolean) r13)     // Catch:{ all -> 0x00c6 }
            r12.j = r4     // Catch:{ all -> 0x00c6 }
            monitor-exit(r12)
            return
        L_0x00c4:
            goto L_0x0194
        L_0x00c6:
            r13 = move-exception
            java.lang.String r13 = "[Native] Failed to load Bugly SO file."
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x019a }
            com.tencent.bugly.proguard.x.c(r13, r0)     // Catch:{ all -> 0x019a }
            goto L_0x00c4
        L_0x00cf:
            boolean r1 = r12.h     // Catch:{ all -> 0x019a }
            if (r1 == 0) goto L_0x0194
            java.lang.String r1 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r5 = "registNativeExceptionHandler2"
            r6 = 4
            java.lang.Class[] r7 = new java.lang.Class[r6]     // Catch:{ all -> 0x0193 }
            r7[r2] = r0     // Catch:{ all -> 0x0193 }
            r7[r4] = r0     // Catch:{ all -> 0x0193 }
            java.lang.Class r8 = java.lang.Integer.TYPE     // Catch:{ all -> 0x0193 }
            r7[r3] = r8     // Catch:{ all -> 0x0193 }
            r9 = 3
            r7[r9] = r8     // Catch:{ all -> 0x0193 }
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0193 }
            java.lang.String r10 = r12.f     // Catch:{ all -> 0x0193 }
            r6[r2] = r10     // Catch:{ all -> 0x0193 }
            android.content.Context r10 = r12.b     // Catch:{ all -> 0x0193 }
            java.lang.String r10 = com.tencent.bugly.crashreport.common.info.b.a(r10, r2)     // Catch:{ all -> 0x0193 }
            r6[r4] = r10     // Catch:{ all -> 0x0193 }
            r10 = 5
            if (r13 == 0) goto L_0x00f9
            r11 = r4
            goto L_0x00fa
        L_0x00f9:
            r11 = r10
        L_0x00fa:
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x0193 }
            r6[r3] = r11     // Catch:{ all -> 0x0193 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r4)     // Catch:{ all -> 0x0193 }
            r6[r9] = r11     // Catch:{ all -> 0x0193 }
            r11 = 0
            java.lang.Object r1 = com.tencent.bugly.proguard.z.a(r1, r5, r11, r7, r6)     // Catch:{ all -> 0x0193 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0193 }
            if (r1 != 0) goto L_0x013d
            java.lang.String r1 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r5 = "registNativeExceptionHandler"
            java.lang.Class[] r6 = new java.lang.Class[r9]     // Catch:{ all -> 0x0193 }
            r6[r2] = r0     // Catch:{ all -> 0x0193 }
            r6[r4] = r0     // Catch:{ all -> 0x0193 }
            r6[r3] = r8     // Catch:{ all -> 0x0193 }
            java.lang.Object[] r7 = new java.lang.Object[r9]     // Catch:{ all -> 0x0193 }
            java.lang.String r9 = r12.f     // Catch:{ all -> 0x0193 }
            r7[r2] = r9     // Catch:{ all -> 0x0193 }
            android.content.Context r9 = r12.b     // Catch:{ all -> 0x0193 }
            java.lang.String r9 = com.tencent.bugly.crashreport.common.info.b.a(r9, r2)     // Catch:{ all -> 0x0193 }
            r7[r4] = r9     // Catch:{ all -> 0x0193 }
            com.tencent.bugly.crashreport.common.info.a.b()     // Catch:{ all -> 0x0193 }
            int r9 = com.tencent.bugly.crashreport.common.info.a.K()     // Catch:{ all -> 0x0193 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ all -> 0x0193 }
            r7[r3] = r9     // Catch:{ all -> 0x0193 }
            java.lang.Object r1 = com.tencent.bugly.proguard.z.a(r1, r5, r11, r6, r7)     // Catch:{ all -> 0x0193 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0193 }
        L_0x013d:
            if (r1 == 0) goto L_0x0192
            r12.j = r4     // Catch:{ all -> 0x0193 }
            com.tencent.bugly.crashreport.common.info.a r3 = com.tencent.bugly.crashreport.common.info.a.b()     // Catch:{ all -> 0x0193 }
            r3.n = r1     // Catch:{ all -> 0x0193 }
            java.lang.String r3 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r5 = "checkExtraJni"
            java.lang.Class[] r6 = new java.lang.Class[r4]     // Catch:{ all -> 0x0193 }
            r6[r2] = r0     // Catch:{ all -> 0x0193 }
            java.lang.Object[] r0 = new java.lang.Object[r4]     // Catch:{ all -> 0x0193 }
            r0[r2] = r1     // Catch:{ all -> 0x0193 }
            java.lang.Object r0 = com.tencent.bugly.proguard.z.a(r3, r5, r11, r6, r0)     // Catch:{ all -> 0x0193 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x0193 }
            if (r0 == 0) goto L_0x0164
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x0193 }
            l = r0     // Catch:{ all -> 0x0193 }
            com.tencent.bugly.proguard.y.a((boolean) r0)     // Catch:{ all -> 0x0193 }
        L_0x0164:
            java.lang.String r0 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r1 = "enableHandler"
            java.lang.Class[] r3 = new java.lang.Class[r4]     // Catch:{ all -> 0x0193 }
            java.lang.Class r5 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x0193 }
            r3[r2] = r5     // Catch:{ all -> 0x0193 }
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x0193 }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)     // Catch:{ all -> 0x0193 }
            r5[r2] = r6     // Catch:{ all -> 0x0193 }
            com.tencent.bugly.proguard.z.a(r0, r1, r11, r3, r5)     // Catch:{ all -> 0x0193 }
            if (r13 == 0) goto L_0x017c
            r10 = r4
        L_0x017c:
            java.lang.String r13 = "com.tencent.feedback.eup.jni.NativeExceptionUpload"
            java.lang.String r0 = "setLogMode"
            java.lang.Class[] r1 = new java.lang.Class[r4]     // Catch:{ all -> 0x0193 }
            r1[r2] = r8     // Catch:{ all -> 0x0193 }
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ all -> 0x0193 }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x0193 }
            r3[r2] = r4     // Catch:{ all -> 0x0193 }
            com.tencent.bugly.proguard.z.a(r13, r0, r11, r1, r3)     // Catch:{ all -> 0x0193 }
            monitor-exit(r12)
            return
        L_0x0192:
            goto L_0x0194
        L_0x0193:
            r13 = move-exception
        L_0x0194:
            r12.i = r2     // Catch:{ all -> 0x019a }
            r12.h = r2     // Catch:{ all -> 0x019a }
            monitor-exit(r12)
            return
        L_0x019a:
            r13 = move-exception
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.a(boolean):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006b, code lost:
        return;
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:21:0x0033=Splitter:B:21:0x0033, B:27:0x006c=Splitter:B:27:0x006c} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void startNativeMonitor() {
        /*
            r4 = this;
            monitor-enter(r4)
            boolean r0 = r4.i     // Catch:{ all -> 0x0073 }
            if (r0 != 0) goto L_0x006c
            boolean r0 = r4.h     // Catch:{ all -> 0x0073 }
            if (r0 == 0) goto L_0x000a
            goto L_0x006c
        L_0x000a:
            java.lang.String r0 = "Bugly"
            com.tencent.bugly.crashreport.common.info.a r1 = r4.c     // Catch:{ all -> 0x0073 }
            java.lang.String r1 = r1.m     // Catch:{ all -> 0x0073 }
            boolean r1 = com.tencent.bugly.proguard.z.a((java.lang.String) r1)     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0018
            r1 = 1
            goto L_0x0019
        L_0x0018:
            r1 = 0
        L_0x0019:
            com.tencent.bugly.crashreport.common.info.a r2 = r4.c     // Catch:{ all -> 0x0073 }
            java.lang.String r3 = r2.m     // Catch:{ all -> 0x0073 }
            if (r1 != 0) goto L_0x0024
            r2.getClass()     // Catch:{ all -> 0x0073 }
            goto L_0x0025
        L_0x0024:
            r0 = r3
        L_0x0025:
            boolean r0 = a((java.lang.String) r0, (boolean) r1)     // Catch:{ all -> 0x0073 }
            r4.i = r0     // Catch:{ all -> 0x0073 }
            if (r0 != 0) goto L_0x0033
            boolean r0 = r4.h     // Catch:{ all -> 0x0073 }
            if (r0 != 0) goto L_0x0033
            monitor-exit(r4)
            return
        L_0x0033:
            boolean r0 = r4.g     // Catch:{ all -> 0x0073 }
            r4.a((boolean) r0)     // Catch:{ all -> 0x0073 }
            boolean r0 = l     // Catch:{ all -> 0x0073 }
            if (r0 == 0) goto L_0x006a
            com.tencent.bugly.crashreport.common.info.a r0 = r4.c     // Catch:{ all -> 0x0073 }
            java.lang.String r0 = r0.j     // Catch:{ all -> 0x0073 }
            r4.setNativeAppVersion(r0)     // Catch:{ all -> 0x0073 }
            com.tencent.bugly.crashreport.common.info.a r0 = r4.c     // Catch:{ all -> 0x0073 }
            java.lang.String r0 = r0.l     // Catch:{ all -> 0x0073 }
            r4.setNativeAppChannel(r0)     // Catch:{ all -> 0x0073 }
            com.tencent.bugly.crashreport.common.info.a r0 = r4.c     // Catch:{ all -> 0x0073 }
            java.lang.String r0 = r0.c     // Catch:{ all -> 0x0073 }
            r4.setNativeAppPackage(r0)     // Catch:{ all -> 0x0073 }
            com.tencent.bugly.crashreport.common.info.a r0 = r4.c     // Catch:{ all -> 0x0073 }
            java.lang.String r0 = r0.g()     // Catch:{ all -> 0x0073 }
            r4.setNativeUserId(r0)     // Catch:{ all -> 0x0073 }
            com.tencent.bugly.crashreport.common.info.a r0 = r4.c     // Catch:{ all -> 0x0073 }
            boolean r0 = r0.a()     // Catch:{ all -> 0x0073 }
            r4.setNativeIsAppForeground(r0)     // Catch:{ all -> 0x0073 }
            com.tencent.bugly.crashreport.common.info.a r0 = r4.c     // Catch:{ all -> 0x0073 }
            long r0 = r0.a     // Catch:{ all -> 0x0073 }
            r4.setNativeLaunchTime(r0)     // Catch:{ all -> 0x0073 }
        L_0x006a:
            monitor-exit(r4)
            return
        L_0x006c:
            boolean r0 = r4.g     // Catch:{ all -> 0x0073 }
            r4.a((boolean) r0)     // Catch:{ all -> 0x0073 }
            monitor-exit(r4)
            return
        L_0x0073:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.startNativeMonitor():void");
    }

    public void checkUploadRecordCrash() {
        this.d.a(new Runnable() {
            public final void run() {
                if (!z.a(NativeCrashHandler.this.b, "native_record_lock", 10000)) {
                    x.a("[Native] Failed to lock file for handling native crash record.", new Object[0]);
                    return;
                }
                if (!NativeCrashHandler.o) {
                    boolean unused = NativeCrashHandler.this.a(999, Bugly.SDK_IS_DEV);
                }
                CrashDetailBean a2 = b.a(NativeCrashHandler.this.b, NativeCrashHandler.this.f, NativeCrashHandler.this.e);
                if (a2 != null) {
                    x.a("[Native] Get crash from native record.", new Object[0]);
                    if (!NativeCrashHandler.this.n.a(a2)) {
                        NativeCrashHandler.this.n.a(a2, (long) GroupCtrlAdapter.RETRY_TIMEOUT, false);
                    }
                    b.a(false, NativeCrashHandler.this.f);
                }
                NativeCrashHandler.this.a();
                z.b(NativeCrashHandler.this.b, "native_record_lock");
            }
        });
    }

    private static boolean a(String str, boolean z) {
        boolean z2;
        try {
            x.a("[Native] Trying to load so: %s", str);
            if (z) {
                System.load(str);
            } else {
                System.loadLibrary(str);
            }
            try {
                x.a("[Native] Successfully loaded SO: %s", str);
                return true;
            } catch (Throwable th) {
                th = th;
                z2 = true;
            }
        } catch (Throwable th2) {
            th = th2;
            z2 = false;
            x.d(th.getMessage(), new Object[0]);
            x.d("[Native] Failed to load so: %s", str);
            return z2;
        }
    }

    private synchronized void c() {
        if (!this.j) {
            x.d("[Native] Native crash report has already unregistered.", new Object[0]);
            return;
        }
        try {
            if (unregist() != null) {
                x.a("[Native] Successfully closed native crash report.", new Object[0]);
                this.j = false;
                return;
            }
        } catch (Throwable th) {
            x.c("[Native] Failed to close native crash report.", new Object[0]);
        }
        try {
            z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "enableHandler", (Object) null, new Class[]{Boolean.TYPE}, new Object[]{false});
            this.j = false;
            x.a("[Native] Successfully closed native crash report.", new Object[0]);
            return;
        } catch (Throwable th2) {
            x.c("[Native] Failed to close native crash report.", new Object[0]);
            this.i = false;
            this.h = false;
            return;
        }
    }

    public void testNativeCrash() {
        if (!this.i) {
            x.d("[Native] Bugly SO file has not been load.", new Object[0]);
        } else {
            testCrash();
        }
    }

    public void testNativeCrash(boolean z, boolean z2, boolean z3) {
        StringBuilder sb = new StringBuilder();
        sb.append(z);
        a(16, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(z2);
        a(17, sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(z3);
        a(18, sb3.toString());
        testNativeCrash();
    }

    public NativeExceptionHandler getNativeExceptionHandler() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final void a() {
        long b2 = z.b() - c.g;
        long b3 = z.b() + CostTimeUtil.DAY;
        File file = new File(this.f);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    return;
                }
                if (listFiles.length != 0) {
                    int i2 = 0;
                    int i3 = 0;
                    for (File file2 : listFiles) {
                        long lastModified = file2.lastModified();
                        if (lastModified < b2 || lastModified >= b3) {
                            x.a("[Native] Delete record file: %s", file2.getAbsolutePath());
                            i2++;
                            if (file2.delete()) {
                                i3++;
                            }
                        }
                    }
                    x.c("[Native] Number of record files overdue: %d, has deleted: %d", Integer.valueOf(i2), Integer.valueOf(i3));
                }
            } catch (Throwable th) {
                x.a(th);
            }
        }
    }

    public void removeEmptyNativeRecordFiles() {
        b.c(this.f);
    }

    private synchronized void b(boolean z) {
        if (z) {
            startNativeMonitor();
        } else {
            c();
        }
    }

    public synchronized boolean isUserOpened() {
        return this.k;
    }

    private synchronized void c(boolean z) {
        if (this.k != z) {
            x.a("user change native %b", Boolean.valueOf(z));
            this.k = z;
        }
    }

    public synchronized void setUserOpened(boolean z) {
        c(z);
        boolean isUserOpened = isUserOpened();
        com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (a2 != null) {
            isUserOpened = isUserOpened && a2.c().g;
        }
        if (isUserOpened != this.j) {
            x.a("native changed to %b", Boolean.valueOf(isUserOpened));
            b(isUserOpened);
        }
    }

    public synchronized void onStrategyChanged(StrategyBean strategyBean) {
        if (strategyBean != null) {
            boolean z = strategyBean.g;
            if (z != this.j) {
                x.d("server native changed to %b", Boolean.valueOf(z));
            }
        }
        boolean z2 = com.tencent.bugly.crashreport.common.strategy.a.a().c().g && this.k;
        if (z2 != this.j) {
            x.a("native changed to %b", Boolean.valueOf(z2));
            b(z2);
        }
    }

    public boolean appendLogToNative(String str, String str2, String str3) {
        Class<String> cls = String.class;
        if ((!this.h && !this.i) || !l || str == null || str2 == null || str3 == null) {
            return false;
        }
        try {
            if (this.i) {
                return appendNativeLog(str, str2, str3);
            }
            Boolean bool = (Boolean) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "appendNativeLog", (Object) null, new Class[]{cls, cls, cls}, new Object[]{str, str2, str3});
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public String getLogFromNative() {
        if ((!this.h && !this.i) || !l) {
            return null;
        }
        try {
            if (this.i) {
                return getNativeLog();
            }
            return (String) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "getNativeLog", (Object) null, (Class<?>[]) null, (Object[]) null);
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return null;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public boolean putKeyValueToNative(String str, String str2) {
        Class<String> cls = String.class;
        if ((!this.h && !this.i) || !l || str == null || str2 == null) {
            return false;
        }
        try {
            if (this.i) {
                return putNativeKeyValue(str, str2);
            }
            Boolean bool = (Boolean) z.a("com.tencent.feedback.eup.jni.NativeExceptionUpload", "putNativeKeyValue", (Object) null, new Class[]{cls, cls}, new Object[]{str, str2});
            if (bool != null) {
                return bool.booleanValue();
            }
            return false;
        } catch (UnsatisfiedLinkError e2) {
            l = false;
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public boolean a(int i2, String str) {
        if (!this.i || !m) {
            return false;
        }
        try {
            setNativeInfo(i2, str);
            return true;
        } catch (UnsatisfiedLinkError e2) {
            m = false;
            return false;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public boolean filterSigabrtSysLog() {
        return a(998, "true");
    }

    public boolean setNativeAppVersion(String str) {
        return a(10, str);
    }

    public boolean setNativeAppChannel(String str) {
        return a(12, str);
    }

    public boolean setNativeAppPackage(String str) {
        return a(13, str);
    }

    public boolean setNativeUserId(String str) {
        return a(11, str);
    }

    public boolean setNativeIsAppForeground(boolean z) {
        return a(14, z ? "true" : Bugly.SDK_IS_DEV);
    }

    public boolean setNativeLaunchTime(long j2) {
        try {
            return a(15, String.valueOf(j2));
        } catch (NumberFormatException e2) {
            if (x.a(e2)) {
                return false;
            }
            e2.printStackTrace();
            return false;
        }
    }
}
