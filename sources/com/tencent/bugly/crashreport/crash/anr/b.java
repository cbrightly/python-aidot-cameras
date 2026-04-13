package com.tencent.bugly.crashreport.crash.anr;

import android.app.ActivityManager;
import android.content.Context;
import android.os.FileObserver;
import android.os.Process;
import com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.ab;
import com.tencent.bugly.proguard.ac;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.tencent.bugly.proguard.z;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: BUGLY */
public final class b implements ac {
    private AtomicInteger a = new AtomicInteger(0);
    private long b = -1;
    private final Context c;
    private final a d;
    private final w e;
    private final com.tencent.bugly.crashreport.common.strategy.a f;
    private final String g;
    private final com.tencent.bugly.crashreport.crash.b h;
    private FileObserver i;
    private boolean j = true;
    private ab k;
    private int l;

    public b(Context context, com.tencent.bugly.crashreport.common.strategy.a aVar, a aVar2, w wVar, com.tencent.bugly.crashreport.crash.b bVar) {
        this.c = z.a(context);
        this.g = context.getDir("bugly", 0).getAbsolutePath();
        this.d = aVar2;
        this.e = wVar;
        this.f = aVar;
        this.h = bVar;
    }

    private static ActivityManager.ProcessErrorStateInfo a(Context context, long j2) {
        try {
            x.c("to find!", new Object[0]);
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int i2 = 0;
            while (true) {
                x.c("waiting!", new Object[0]);
                List<ActivityManager.ProcessErrorStateInfo> processesInErrorState = activityManager.getProcessesInErrorState();
                if (processesInErrorState != null) {
                    for (ActivityManager.ProcessErrorStateInfo next : processesInErrorState) {
                        if (next.condition == 2) {
                            x.c("found!", new Object[0]);
                            return next;
                        }
                    }
                }
                z.b(500);
                int i3 = i2 + 1;
                if (((long) i2) >= 20) {
                    x.c("end!", new Object[0]);
                    return null;
                }
                i2 = i3;
            }
        } catch (Exception e2) {
            x.b(e2);
            return null;
        }
    }

    private CrashDetailBean a(a aVar) {
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        try {
            crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.k();
            crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.i();
            crashDetailBean.E = com.tencent.bugly.crashreport.common.info.b.m();
            crashDetailBean.F = this.d.p();
            crashDetailBean.G = this.d.o();
            crashDetailBean.H = this.d.q();
            crashDetailBean.w = z.a(this.c, c.e, (String) null);
            crashDetailBean.b = 3;
            crashDetailBean.e = this.d.h();
            a aVar2 = this.d;
            crashDetailBean.f = aVar2.j;
            crashDetailBean.g = aVar2.w();
            crashDetailBean.m = this.d.g();
            crashDetailBean.n = "ANR_EXCEPTION";
            crashDetailBean.o = aVar.f;
            crashDetailBean.q = aVar.g;
            HashMap hashMap = new HashMap();
            crashDetailBean.O = hashMap;
            hashMap.put("BUGLY_CR_01", aVar.e);
            int i2 = -1;
            String str = crashDetailBean.q;
            if (str != null) {
                i2 = str.indexOf("\n");
            }
            crashDetailBean.p = i2 > 0 ? crashDetailBean.q.substring(0, i2) : "GET_FAIL";
            crashDetailBean.r = aVar.c;
            String str2 = crashDetailBean.q;
            if (str2 != null) {
                crashDetailBean.u = z.b(str2.getBytes());
            }
            crashDetailBean.z = aVar.b;
            crashDetailBean.A = aVar.a;
            crashDetailBean.B = "main(1)";
            crashDetailBean.I = this.d.y();
            crashDetailBean.h = this.d.v();
            crashDetailBean.i = this.d.J();
            crashDetailBean.v = aVar.d;
            a aVar3 = this.d;
            crashDetailBean.L = aVar3.n;
            crashDetailBean.M = aVar3.a;
            crashDetailBean.N = aVar3.a();
            crashDetailBean.P = this.d.H();
            crashDetailBean.Q = this.d.I();
            crashDetailBean.R = this.d.B();
            crashDetailBean.S = this.d.G();
            this.h.c(crashDetailBean);
            crashDetailBean.y = y.a();
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return crashDetailBean;
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x0134 A[Catch:{ all -> 0x012a }] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0160 A[SYNTHETIC, Splitter:B:61:0x0160] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0172 A[SYNTHETIC, Splitter:B:69:0x0172] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.lang.String r16, java.lang.String r17, java.lang.String r18) {
        /*
            r1 = r17
            r0 = r18
            java.lang.String r2 = "main"
            java.lang.String r3 = ":"
            r4 = 1
            r5 = r16
            com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$a r5 = com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readTargetDumpInfo(r0, r5, r4)
            r6 = 0
            if (r5 == 0) goto L_0x01c0
            java.util.Map<java.lang.String, java.lang.String[]> r7 = r5.d
            if (r7 == 0) goto L_0x01c0
            int r7 = r7.size()
            if (r7 > 0) goto L_0x001e
            goto L_0x01c0
        L_0x001e:
            java.io.File r0 = new java.io.File
            r0.<init>(r1)
            r7 = 2
            boolean r8 = r0.exists()     // Catch:{ Exception -> 0x018c }
            if (r8 != 0) goto L_0x003e
            java.io.File r8 = r0.getParentFile()     // Catch:{ Exception -> 0x018c }
            boolean r8 = r8.exists()     // Catch:{ Exception -> 0x018c }
            if (r8 != 0) goto L_0x003b
            java.io.File r8 = r0.getParentFile()     // Catch:{ Exception -> 0x018c }
            r8.mkdirs()     // Catch:{ Exception -> 0x018c }
        L_0x003b:
            r0.createNewFile()     // Catch:{ Exception -> 0x018c }
        L_0x003e:
            boolean r8 = r0.exists()
            if (r8 == 0) goto L_0x0182
            boolean r8 = r0.canWrite()
            if (r8 != 0) goto L_0x004d
            goto L_0x0182
        L_0x004d:
            r1 = 0
            java.io.BufferedWriter r8 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x012d }
            java.io.FileWriter r9 = new java.io.FileWriter     // Catch:{ IOException -> 0x012d }
            r9.<init>(r0, r6)     // Catch:{ IOException -> 0x012d }
            r8.<init>(r9)     // Catch:{ IOException -> 0x012d }
            java.util.Map<java.lang.String, java.lang.String[]> r0 = r5.d     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.Object r0 = r0.get(r2)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String r1 = "\n\n"
            java.lang.String r9 = "\n"
            java.lang.String r10 = " :\n"
            r11 = 3
            if (r0 == 0) goto L_0x0095
            int r12 = r0.length     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            if (r12 < r11) goto L_0x0095
            r12 = r0[r6]     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r13 = r0[r4]     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r0 = r0[r7]     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String r15 = "\"main\" tid="
            r14.<init>(r15)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r14.append(r0)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r14.append(r10)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r14.append(r12)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r14.append(r9)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r14.append(r13)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r14.append(r1)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String r0 = r14.toString()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r8.write(r0)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r8.flush()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
        L_0x0095:
            java.util.Map<java.lang.String, java.lang.String[]> r0 = r5.d     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
        L_0x009f:
            boolean r5 = r0.hasNext()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            if (r5 == 0) goto L_0x0113
            java.lang.Object r5 = r0.next()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.Object r12 = r5.getKey()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            boolean r12 = r12.equals(r2)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            if (r12 != 0) goto L_0x0111
            java.lang.Object r12 = r5.getValue()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            if (r12 == 0) goto L_0x010f
            java.lang.Object r12 = r5.getValue()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String[] r12 = (java.lang.String[]) r12     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            int r12 = r12.length     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            if (r12 < r11) goto L_0x010f
            java.lang.Object r12 = r5.getValue()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String[] r12 = (java.lang.String[]) r12     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r12 = r12[r6]     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.Object r13 = r5.getValue()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String[] r13 = (java.lang.String[]) r13     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r13 = r13[r4]     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.Object r14 = r5.getValue()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String[] r14 = (java.lang.String[]) r14     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r14 = r14[r7]     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String r11 = "\""
            r15.<init>(r11)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.Object r5 = r5.getKey()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r15.append(r5)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String r5 = "\" tid="
            r15.append(r5)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r15.append(r14)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r15.append(r10)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r15.append(r12)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r15.append(r9)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r15.append(r13)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r15.append(r1)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            java.lang.String r5 = r15.toString()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r8.write(r5)     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
            r8.flush()     // Catch:{ IOException -> 0x0127, all -> 0x0123 }
        L_0x010f:
            r11 = 3
            goto L_0x009f
        L_0x0111:
            r11 = 3
            goto L_0x009f
        L_0x0113:
            r8.close()     // Catch:{ IOException -> 0x0117 }
            goto L_0x0122
        L_0x0117:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r1)
            if (r0 != 0) goto L_0x0122
            r1.printStackTrace()
        L_0x0122:
            return r4
        L_0x0123:
            r0 = move-exception
            r2 = r0
            r1 = r8
            goto L_0x0170
        L_0x0127:
            r0 = move-exception
            r1 = r8
            goto L_0x012e
        L_0x012a:
            r0 = move-exception
            r2 = r0
            goto L_0x0170
        L_0x012d:
            r0 = move-exception
        L_0x012e:
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x012a }
            if (r2 != 0) goto L_0x0137
            r0.printStackTrace()     // Catch:{ all -> 0x012a }
        L_0x0137:
            java.lang.String r2 = "dump trace fail %s"
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x012a }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x012a }
            r5.<init>()     // Catch:{ all -> 0x012a }
            java.lang.Class r7 = r0.getClass()     // Catch:{ all -> 0x012a }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x012a }
            r5.append(r7)     // Catch:{ all -> 0x012a }
            r5.append(r3)     // Catch:{ all -> 0x012a }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x012a }
            r5.append(r0)     // Catch:{ all -> 0x012a }
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x012a }
            r4[r6] = r0     // Catch:{ all -> 0x012a }
            com.tencent.bugly.proguard.x.e(r2, r4)     // Catch:{ all -> 0x012a }
            if (r1 == 0) goto L_0x016f
            r1.close()     // Catch:{ IOException -> 0x0164 }
            goto L_0x016f
        L_0x0164:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r1)
            if (r0 != 0) goto L_0x016f
            r1.printStackTrace()
        L_0x016f:
            return r6
        L_0x0170:
            if (r1 == 0) goto L_0x0181
            r1.close()     // Catch:{ IOException -> 0x0176 }
            goto L_0x0181
        L_0x0176:
            r0 = move-exception
            r1 = r0
            boolean r0 = com.tencent.bugly.proguard.x.a(r1)
            if (r0 != 0) goto L_0x0181
            r1.printStackTrace()
        L_0x0181:
            throw r2
        L_0x0182:
            java.lang.Object[] r0 = new java.lang.Object[r4]
            r0[r6] = r1
            java.lang.String r1 = "backup file create fail %s"
            com.tencent.bugly.proguard.x.e(r1, r0)
            return r6
        L_0x018c:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.x.a(r0)
            if (r2 != 0) goto L_0x0196
            r0.printStackTrace()
        L_0x0196:
            java.lang.Object[] r2 = new java.lang.Object[r7]
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.Class r7 = r0.getClass()
            java.lang.String r7 = r7.getName()
            r5.append(r7)
            r5.append(r3)
            java.lang.String r0 = r0.getMessage()
            r5.append(r0)
            java.lang.String r0 = r5.toString()
            r2[r6] = r0
            r2[r4] = r1
            java.lang.String r0 = "backup file create error! %s  %s"
            com.tencent.bugly.proguard.x.e(r0, r2)
            return r6
        L_0x01c0:
            java.lang.Object[] r1 = new java.lang.Object[r4]
            r1[r6] = r0
            java.lang.String r0 = "not found trace dump for %s"
            com.tencent.bugly.proguard.x.e(r0, r1)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.anr.b.a(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public final boolean a() {
        return this.a.get() != 0;
    }

    private boolean a(Context context, String str, ActivityManager.ProcessErrorStateInfo processErrorStateInfo, long j2, Map<String, String> map) {
        File filesDir = context.getFilesDir();
        File file = new File(filesDir, "bugly/bugly_trace_" + j2 + ".txt");
        a aVar = new a();
        aVar.c = j2;
        aVar.d = file.getAbsolutePath();
        String str2 = "";
        aVar.a = processErrorStateInfo != null ? processErrorStateInfo.processName : str2;
        aVar.f = processErrorStateInfo != null ? processErrorStateInfo.shortMsg : str2;
        if (processErrorStateInfo != null) {
            str2 = processErrorStateInfo.longMsg;
        }
        aVar.e = str2;
        aVar.b = map;
        if (map != null) {
            for (String next : map.keySet()) {
                if (next.startsWith("main(")) {
                    aVar.g = map.get(next);
                }
            }
        }
        Object[] objArr = new Object[6];
        objArr[0] = Long.valueOf(aVar.c);
        objArr[1] = aVar.d;
        objArr[2] = aVar.a;
        objArr[3] = aVar.f;
        objArr[4] = aVar.e;
        Map<String, String> map2 = aVar.b;
        objArr[5] = Integer.valueOf(map2 == null ? 0 : map2.size());
        x.c("anr tm:%d\ntr:%s\nproc:%s\nsMsg:%s\n lMsg:%s\n threads:%d", objArr);
        if (!this.f.b()) {
            x.e("crash report sync remote fail, will not upload to Bugly , print local for helpful!", new Object[0]);
            com.tencent.bugly.crashreport.crash.b.a("ANR", z.a(), aVar.a, LogcatHelper.BUFFER_MAIN, aVar.e, (CrashDetailBean) null);
            return false;
        } else if (!this.f.c().j) {
            x.d("ANR Report is closed!", new Object[0]);
            return false;
        } else {
            x.a("found visiable anr , start to upload!", new Object[0]);
            CrashDetailBean a2 = a(aVar);
            if (a2 == null) {
                x.e("pack anr fail!", new Object[0]);
                return false;
            }
            c.a().a(a2);
            if (a2.a >= 0) {
                x.a("backup anr record success!", new Object[0]);
            } else {
                x.d("backup anr record fail!", new Object[0]);
            }
            if (str != null && new File(str).exists()) {
                this.a.set(3);
                if (a(str, aVar.d, aVar.a)) {
                    x.a("backup trace success", new Object[0]);
                }
            }
            com.tencent.bugly.crashreport.crash.b.a("ANR", z.a(), aVar.a, LogcatHelper.BUFFER_MAIN, aVar.e, a2);
            if (!this.h.a(a2)) {
                this.h.a(a2, (long) GroupCtrlAdapter.RETRY_TIMEOUT, true);
            }
            this.h.b(a2);
            return true;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        com.tencent.bugly.proguard.x.c("read trace first dump for create time!", new java.lang.Object[0]);
        r0 = com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readFirstDumpInfo(r11, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
        if (r0 == null) goto L_0x002f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        r5 = r0.c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002f, code lost:
        r5 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0032, code lost:
        if (r5 != -1) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0034, code lost:
        com.tencent.bugly.proguard.x.d("trace dump fail could not get time!", new java.lang.Object[0]);
        r7 = java.lang.System.currentTimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0042, code lost:
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004f, code lost:
        if (java.lang.Math.abs(r7 - r10.b) >= 10000) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0051, code lost:
        com.tencent.bugly.proguard.x.d("should not process ANR too Fre in %d", 10000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r10.b = r7;
        r10.a.set(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r9 = com.tencent.bugly.proguard.z.a(com.tencent.bugly.crashreport.crash.c.f, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0075, code lost:
        if (r9 == null) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007b, code lost:
        if (r9.size() > 0) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007e, code lost:
        r6 = a(r10.c, 10000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0084, code lost:
        if (r6 != null) goto L_0x008f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0086, code lost:
        com.tencent.bugly.proguard.x.c("proc state is unvisiable!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0095, code lost:
        if (r6.pid == android.os.Process.myPid()) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0097, code lost:
        com.tencent.bugly.proguard.x.c("not mind proc!", r6.processName);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a3, code lost:
        com.tencent.bugly.proguard.x.a("found visiable anr , start to process!", new java.lang.Object[0]);
        a(r10.c, r11, r6, r7, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        com.tencent.bugly.proguard.x.d("can't get all thread skip this anr", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00bf, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c0, code lost:
        com.tencent.bugly.proguard.x.a(r11);
        com.tencent.bugly.proguard.x.e("get all thread stack fail!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00cb, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00d0, code lost:
        if (com.tencent.bugly.proguard.x.a(r11) == false) goto L_0x00d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d2, code lost:
        r11.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d5, code lost:
        com.tencent.bugly.proguard.x.e("handle anr error %s", r11.getClass().toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e7, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e8, code lost:
        r10.a.set(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ed, code lost:
        throw r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a     // Catch:{ all -> 0x00ee }
            int r0 = r0.get()     // Catch:{ all -> 0x00ee }
            r1 = 0
            if (r0 == 0) goto L_0x0014
            java.lang.String r11 = "trace started return "
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ all -> 0x00ee }
            com.tencent.bugly.proguard.x.c(r11, r0)     // Catch:{ all -> 0x00ee }
            monitor-exit(r10)     // Catch:{ all -> 0x00ee }
            return
        L_0x0014:
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a     // Catch:{ all -> 0x00ee }
            r2 = 1
            r0.set(r2)     // Catch:{ all -> 0x00ee }
            monitor-exit(r10)     // Catch:{ all -> 0x00ee }
            java.lang.String r0 = "read trace first dump for create time!"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.proguard.x.c(r0, r3)     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$a r0 = com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readFirstDumpInfo(r11, r1)     // Catch:{ all -> 0x00cb }
            r3 = -1
            if (r0 == 0) goto L_0x002f
            long r5 = r0.c     // Catch:{ all -> 0x00cb }
            goto L_0x0030
        L_0x002f:
            r5 = r3
        L_0x0030:
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0042
            java.lang.String r0 = "trace dump fail could not get time!"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.proguard.x.d(r0, r3)     // Catch:{ all -> 0x00cb }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00cb }
            r7 = r5
            goto L_0x0043
        L_0x0042:
            r7 = r5
        L_0x0043:
            long r3 = r10.b     // Catch:{ all -> 0x00cb }
            long r3 = r7 - r3
            long r3 = java.lang.Math.abs(r3)     // Catch:{ all -> 0x00cb }
            r5 = 10000(0x2710, double:4.9407E-320)
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0067
            java.lang.String r11 = "should not process ANR too Fre in %d"
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x00cb }
            r3 = 10000(0x2710, float:1.4013E-41)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x00cb }
            r0[r1] = r3     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.proguard.x.d(r11, r0)     // Catch:{ all -> 0x00cb }
        L_0x0061:
            java.util.concurrent.atomic.AtomicInteger r11 = r10.a
            r11.set(r1)
            return
        L_0x0067:
            r10.b = r7     // Catch:{ all -> 0x00cb }
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a     // Catch:{ all -> 0x00cb }
            r0.set(r2)     // Catch:{ all -> 0x00cb }
            int r0 = com.tencent.bugly.crashreport.crash.c.f     // Catch:{ all -> 0x00bf }
            java.util.Map r9 = com.tencent.bugly.proguard.z.a((int) r0, (boolean) r1)     // Catch:{ all -> 0x00bf }
            if (r9 == 0) goto L_0x00b7
            int r0 = r9.size()     // Catch:{ all -> 0x00cb }
            if (r0 > 0) goto L_0x007e
            goto L_0x00b7
        L_0x007e:
            android.content.Context r0 = r10.c     // Catch:{ all -> 0x00cb }
            android.app.ActivityManager$ProcessErrorStateInfo r6 = a(r0, r5)     // Catch:{ all -> 0x00cb }
            if (r6 != 0) goto L_0x008f
            java.lang.String r11 = "proc state is unvisiable!"
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.proguard.x.c(r11, r0)     // Catch:{ all -> 0x00cb }
            goto L_0x0061
        L_0x008f:
            int r0 = r6.pid     // Catch:{ all -> 0x00cb }
            int r3 = android.os.Process.myPid()     // Catch:{ all -> 0x00cb }
            if (r0 == r3) goto L_0x00a3
            java.lang.String r11 = "not mind proc!"
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ all -> 0x00cb }
            java.lang.String r3 = r6.processName     // Catch:{ all -> 0x00cb }
            r0[r1] = r3     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.proguard.x.c(r11, r0)     // Catch:{ all -> 0x00cb }
            goto L_0x0061
        L_0x00a3:
            java.lang.String r0 = "found visiable anr , start to process!"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.proguard.x.a(r0, r3)     // Catch:{ all -> 0x00cb }
            android.content.Context r4 = r10.c     // Catch:{ all -> 0x00cb }
            r3 = r10
            r5 = r11
            r3.a(r4, r5, r6, r7, r9)     // Catch:{ all -> 0x00cb }
        L_0x00b1:
            java.util.concurrent.atomic.AtomicInteger r11 = r10.a
            r11.set(r1)
            return
        L_0x00b7:
            java.lang.String r11 = "can't get all thread skip this anr"
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.proguard.x.d(r11, r0)     // Catch:{ all -> 0x00cb }
            goto L_0x0061
        L_0x00bf:
            r11 = move-exception
            com.tencent.bugly.proguard.x.a(r11)     // Catch:{ all -> 0x00cb }
            java.lang.String r11 = "get all thread stack fail!"
            java.lang.Object[] r0 = new java.lang.Object[r1]     // Catch:{ all -> 0x00cb }
            com.tencent.bugly.proguard.x.e(r11, r0)     // Catch:{ all -> 0x00cb }
            goto L_0x0061
        L_0x00cb:
            r11 = move-exception
            boolean r0 = com.tencent.bugly.proguard.x.a(r11)     // Catch:{ all -> 0x00e7 }
            if (r0 != 0) goto L_0x00d5
            r11.printStackTrace()     // Catch:{ all -> 0x00e7 }
        L_0x00d5:
            java.lang.String r0 = "handle anr error %s"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x00e7 }
            java.lang.Class r11 = r11.getClass()     // Catch:{ all -> 0x00e7 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x00e7 }
            r2[r1] = r11     // Catch:{ all -> 0x00e7 }
            com.tencent.bugly.proguard.x.e(r0, r2)     // Catch:{ all -> 0x00e7 }
            goto L_0x00b1
        L_0x00e7:
            r11 = move-exception
            java.util.concurrent.atomic.AtomicInteger r0 = r10.a
            r0.set(r1)
            throw r11
        L_0x00ee:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.anr.b.a(java.lang.String):void");
    }

    private synchronized void e() {
        if (g()) {
            x.d("start when started!", new Object[0]);
            return;
        }
        AnonymousClass1 r0 = new FileObserver("/data/anr/", 8) {
            public final void onEvent(int i, String str) {
                if (str != null) {
                    String str2 = "/data/anr/" + str;
                    if (!str2.contains("trace")) {
                        x.d("not anr file %s", str2);
                        return;
                    }
                    b.this.a(str2);
                }
            }
        };
        this.i = r0;
        try {
            r0.startWatching();
            x.a("start anr monitor!", new Object[0]);
            this.e.a(new Runnable() {
                public final void run() {
                    b.this.b();
                }
            });
        } catch (Throwable th) {
            this.i = null;
            x.d("start anr monitor failed!", new Object[0]);
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    private synchronized void f() {
        if (!g()) {
            x.d("close when closed!", new Object[0]);
            return;
        }
        try {
            this.i.stopWatching();
            this.i = null;
            x.d("close anr monitor!", new Object[0]);
        } catch (Throwable th) {
            x.d("stop anr monitor failed!", new Object[0]);
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    private synchronized boolean g() {
        return this.i != null;
    }

    private synchronized void b(boolean z) {
        if (z) {
            e();
        } else {
            f();
        }
    }

    private synchronized boolean h() {
        return this.j;
    }

    private synchronized void c(boolean z) {
        if (this.j != z) {
            x.a("user change anr %b", Boolean.valueOf(z));
            this.j = z;
        }
    }

    public final void a(boolean z) {
        c(z);
        boolean h2 = h();
        com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (a2 != null) {
            h2 = h2 && a2.c().g;
        }
        if (h2 != g()) {
            x.a("anr changed to %b", Boolean.valueOf(h2));
            b(h2);
        }
    }

    /* access modifiers changed from: protected */
    public final void b() {
        String name;
        long b2 = z.b() - c.g;
        File file = new File(this.g);
        if (file.exists() && file.isDirectory()) {
            try {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    return;
                }
                if (listFiles.length != 0) {
                    int length = "bugly_trace_".length();
                    int i2 = 0;
                    for (File file2 : listFiles) {
                        name = file2.getName();
                        if (name.startsWith("bugly_trace_")) {
                            int indexOf = name.indexOf(".txt");
                            if (indexOf > 0 && Long.parseLong(name.substring(length, indexOf)) >= b2) {
                            }
                            if (file2.delete()) {
                                i2++;
                            }
                        }
                    }
                    x.c("Number of overdue trace files that has deleted: " + i2, new Object[0]);
                }
            } catch (Throwable th) {
                x.a(th);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0049, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(com.tencent.bugly.crashreport.common.strategy.StrategyBean r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L_0x0005
            monitor-exit(r5)
            return
        L_0x0005:
            boolean r0 = r6.j     // Catch:{ all -> 0x0058 }
            boolean r1 = r5.g()     // Catch:{ all -> 0x0058 }
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x001f
            java.lang.String r0 = "server anr changed to %b"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x0058 }
            boolean r4 = r6.j     // Catch:{ all -> 0x0058 }
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)     // Catch:{ all -> 0x0058 }
            r1[r2] = r4     // Catch:{ all -> 0x0058 }
            com.tencent.bugly.proguard.x.d(r0, r1)     // Catch:{ all -> 0x0058 }
        L_0x001f:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0058 }
            r1 = 19
            if (r0 > r1) goto L_0x004a
            boolean r6 = r6.j     // Catch:{ all -> 0x0058 }
            if (r6 == 0) goto L_0x0031
            boolean r6 = r5.h()     // Catch:{ all -> 0x0058 }
            if (r6 == 0) goto L_0x0031
            r6 = r3
            goto L_0x0032
        L_0x0031:
            r6 = r2
        L_0x0032:
            boolean r0 = r5.g()     // Catch:{ all -> 0x0058 }
            if (r6 == r0) goto L_0x0048
            java.lang.String r0 = "anr changed to %b"
            java.lang.Object[] r1 = new java.lang.Object[r3]     // Catch:{ all -> 0x0058 }
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r6)     // Catch:{ all -> 0x0058 }
            r1[r2] = r3     // Catch:{ all -> 0x0058 }
            com.tencent.bugly.proguard.x.a(r0, r1)     // Catch:{ all -> 0x0058 }
            r5.b(r6)     // Catch:{ all -> 0x0058 }
        L_0x0048:
            monitor-exit(r5)
            return
        L_0x004a:
            boolean r6 = r6.j     // Catch:{ all -> 0x0058 }
            if (r6 == 0) goto L_0x0053
            r5.c()     // Catch:{ all -> 0x0058 }
            monitor-exit(r5)
            return
        L_0x0053:
            r5.d()     // Catch:{ all -> 0x0058 }
            monitor-exit(r5)
            return
        L_0x0058:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.anr.b.a(com.tencent.bugly.crashreport.common.strategy.StrategyBean):void");
    }

    public final boolean a(Thread thread) {
        boolean z;
        new HashMap();
        if (thread.getName().contains(LogcatHelper.BUFFER_MAIN)) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            ActivityManager.ProcessErrorStateInfo a2 = a(this.c, 10000);
            if (a2 == null) {
                x.c("anr handler onThreadBlock proc state is unvisiable!", new Object[0]);
                return false;
            } else if (a2.pid != Process.myPid()) {
                x.c("onThreadBlock not mind proc!", a2.processName);
                return false;
            } else {
                try {
                    Map<String, String> a3 = z.a(200000, false);
                    x.a("onThreadBlock found visiable anr , start to process!", new Object[0]);
                    a(this.c, "", a2, System.currentTimeMillis(), a3);
                } catch (Throwable th) {
                    return false;
                }
            }
        } else {
            x.c("anr handler onThreadBlock only care main thread", new Object[0]);
        }
        return true;
    }

    public final boolean c() {
        ab abVar = this.k;
        if (abVar != null && abVar.isAlive()) {
            return false;
        }
        ab abVar2 = new ab();
        this.k = abVar2;
        StringBuilder sb = new StringBuilder("Bugly-ThreadMonitor");
        int i2 = this.l;
        this.l = i2 + 1;
        sb.append(i2);
        abVar2.setName(sb.toString());
        this.k.a();
        this.k.a((ac) this);
        return this.k.d();
    }

    public final boolean d() {
        ab abVar = this.k;
        if (abVar == null) {
            return false;
        }
        abVar.b();
        this.k.b(this);
        boolean c2 = this.k.c();
        this.k = null;
        return c2;
    }
}
