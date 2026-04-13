package com.tencent.bugly.crashreport.crash.jni;

import android.content.Context;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.b;
import com.tencent.bugly.crashreport.crash.c;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.y;
import com.tencent.bugly.proguard.z;
import java.util.Map;

/* compiled from: BUGLY */
public final class a implements NativeExceptionHandler {
    private final Context a;
    private final b b;
    private final com.tencent.bugly.crashreport.common.info.a c;
    private final com.tencent.bugly.crashreport.common.strategy.a d;

    public a(Context context, com.tencent.bugly.crashreport.common.info.a aVar, b bVar, com.tencent.bugly.crashreport.common.strategy.a aVar2) {
        this.a = context;
        this.b = bVar;
        this.c = aVar;
        this.d = aVar2;
    }

    public final CrashDetailBean packageCrashDatas(String str, String str2, long j, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, byte[] bArr, Map<String, String> map, boolean z, boolean z2) {
        String str12;
        int length;
        String str13;
        int indexOf;
        String str14 = str;
        String str15 = str8;
        byte[] bArr2 = bArr;
        boolean k = c.a().k();
        if (k) {
            x.e("This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful!", new Object[0]);
        }
        CrashDetailBean crashDetailBean = new CrashDetailBean();
        crashDetailBean.b = 1;
        crashDetailBean.e = this.c.h();
        com.tencent.bugly.crashreport.common.info.a aVar = this.c;
        crashDetailBean.f = aVar.j;
        crashDetailBean.g = aVar.w();
        crashDetailBean.m = this.c.g();
        crashDetailBean.n = str3;
        String str16 = "";
        crashDetailBean.o = k ? " This Crash Caused By ANR , PLS To Fix ANR , This Trace May Be Not Useful![Bugly]" : str16;
        crashDetailBean.p = str4;
        if (str5 != null) {
            str16 = str5;
        }
        crashDetailBean.q = str16;
        crashDetailBean.r = j;
        crashDetailBean.u = z.b(str16.getBytes());
        crashDetailBean.A = str14;
        crashDetailBean.B = str2;
        crashDetailBean.I = this.c.y();
        crashDetailBean.h = this.c.v();
        crashDetailBean.i = this.c.J();
        crashDetailBean.v = str15;
        NativeCrashHandler instance = NativeCrashHandler.getInstance();
        if (instance != null) {
            str12 = instance.getDumpFilePath();
        } else {
            str12 = null;
        }
        String a2 = b.a(str12, str15);
        if (!z.a(a2)) {
            crashDetailBean.U = a2;
        }
        crashDetailBean.V = b.b(str12);
        crashDetailBean.w = b.a(str9, c.e, (String) null, false);
        crashDetailBean.x = b.a(str10, c.e, (String) null, true);
        crashDetailBean.J = str7;
        crashDetailBean.K = str6;
        crashDetailBean.L = str11;
        crashDetailBean.F = this.c.p();
        crashDetailBean.G = this.c.o();
        crashDetailBean.H = this.c.q();
        if (z) {
            crashDetailBean.C = com.tencent.bugly.crashreport.common.info.b.k();
            crashDetailBean.D = com.tencent.bugly.crashreport.common.info.b.i();
            crashDetailBean.E = com.tencent.bugly.crashreport.common.info.b.m();
            if (crashDetailBean.w == null) {
                crashDetailBean.w = z.a(this.a, c.e, (String) null);
            }
            crashDetailBean.y = y.a();
            com.tencent.bugly.crashreport.common.info.a aVar2 = this.c;
            crashDetailBean.M = aVar2.a;
            crashDetailBean.N = aVar2.a();
            crashDetailBean.P = this.c.H();
            crashDetailBean.Q = this.c.I();
            crashDetailBean.R = this.c.B();
            crashDetailBean.S = this.c.G();
            crashDetailBean.z = z.a(c.f, false);
            int indexOf2 = crashDetailBean.q.indexOf("java:\n");
            if (indexOf2 > 0 && (length = indexOf2 + "java:\n".length()) < crashDetailBean.q.length()) {
                String str17 = crashDetailBean.q;
                String substring = str17.substring(length, str17.length() - 1);
                if (substring.length() > 0 && crashDetailBean.z.containsKey(crashDetailBean.B) && (indexOf = str13.indexOf(substring)) > 0) {
                    String substring2 = (str13 = crashDetailBean.z.get(crashDetailBean.B)).substring(indexOf);
                    crashDetailBean.z.put(crashDetailBean.B, substring2);
                    crashDetailBean.q = crashDetailBean.q.substring(0, length);
                    crashDetailBean.q += substring2;
                }
            }
            if (str14 == null) {
                crashDetailBean.A = this.c.d;
            }
            this.b.c(crashDetailBean);
        } else {
            crashDetailBean.C = -1;
            crashDetailBean.D = -1;
            crashDetailBean.E = -1;
            if (crashDetailBean.w == null) {
                crashDetailBean.w = "this crash is occurred at last process! Log is miss, when get an terrible ABRT Native Exception etc.";
            }
            crashDetailBean.M = -1;
            crashDetailBean.P = -1;
            crashDetailBean.Q = -1;
            crashDetailBean.R = map;
            crashDetailBean.S = this.c.G();
            crashDetailBean.z = null;
            if (str14 == null) {
                crashDetailBean.A = "unknown(record)";
            }
            if (bArr2 != null) {
                crashDetailBean.y = bArr2;
            }
        }
        return crashDetailBean;
    }

    public final void handleNativeException(int i, int i2, long j, long j2, String str, String str2, String str3, String str4, int i3, String str5, int i4, int i5, int i6, String str6, String str7) {
        int i7 = i;
        x.a("Native Crash Happen v1", new Object[0]);
        handleNativeException2(i, i2, j, j2, str, str2, str3, str4, i3, str5, i4, i5, i6, str6, str7, (String[]) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0106 A[Catch:{ all -> 0x02ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x017b A[Catch:{ all -> 0x02ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01c2 A[Catch:{ all -> 0x02ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01d5 A[SYNTHETIC, Splitter:B:64:0x01d5] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x023d A[Catch:{ all -> 0x02aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0247 A[Catch:{ all -> 0x02aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x02b6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void handleNativeException2(int r28, int r29, long r30, long r32, java.lang.String r34, java.lang.String r35, java.lang.String r36, java.lang.String r37, int r38, java.lang.String r39, int r40, int r41, int r42, java.lang.String r43, java.lang.String r44, java.lang.String[] r45) {
        /*
            r27 = this;
            r14 = r27
            r0 = r35
            r13 = r38
            r1 = r40
            r2 = r45
            r12 = 0
            java.lang.Object[] r3 = new java.lang.Object[r12]
            java.lang.String r4 = "Native Crash Happen v2"
            com.tencent.bugly.proguard.x.a(r4, r3)
            java.lang.String r11 = com.tencent.bugly.crashreport.crash.jni.b.a((java.lang.String) r36)     // Catch:{ all -> 0x02ae }
            java.lang.String r3 = "UNKNOWN"
            java.lang.String r4 = ")"
            java.lang.String r5 = "("
            if (r13 <= 0) goto L_0x003f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ae }
            r1.<init>()     // Catch:{ all -> 0x02ae }
            r6 = r34
            r1.append(r6)     // Catch:{ all -> 0x02ae }
            r1.append(r5)     // Catch:{ all -> 0x02ae }
            r7 = r39
            r1.append(r7)     // Catch:{ all -> 0x02ae }
            r1.append(r4)     // Catch:{ all -> 0x02ae }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x02ae }
            java.lang.String r6 = "KERNEL"
            r10 = r1
            r18 = r3
            r9 = r6
            goto L_0x0071
        L_0x003f:
            r6 = r34
            r7 = r39
            if (r1 <= 0) goto L_0x0049
            java.lang.String r3 = com.tencent.bugly.crashreport.common.info.AppInfo.a((int) r40)     // Catch:{ all -> 0x02ae }
        L_0x0049:
            java.lang.String r8 = java.lang.String.valueOf(r40)     // Catch:{ all -> 0x02ae }
            boolean r8 = r3.equals(r8)     // Catch:{ all -> 0x02ae }
            if (r8 != 0) goto L_0x006d
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ae }
            r8.<init>()     // Catch:{ all -> 0x02ae }
            r8.append(r3)     // Catch:{ all -> 0x02ae }
            r8.append(r5)     // Catch:{ all -> 0x02ae }
            r8.append(r1)     // Catch:{ all -> 0x02ae }
            r8.append(r4)     // Catch:{ all -> 0x02ae }
            java.lang.String r1 = r8.toString()     // Catch:{ all -> 0x02ae }
            r18 = r1
            r10 = r6
            r9 = r7
            goto L_0x0071
        L_0x006d:
            r18 = r3
            r10 = r6
            r9 = r7
        L_0x0071:
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x02ae }
            r1.<init>()     // Catch:{ all -> 0x02ae }
            if (r2 == 0) goto L_0x00b1
            r3 = r12
        L_0x0079:
            int r6 = r2.length     // Catch:{ all -> 0x02ae }
            if (r3 >= r6) goto L_0x00b8
            r6 = r2[r3]     // Catch:{ all -> 0x02ae }
            if (r6 == 0) goto L_0x00ae
            java.lang.String r7 = "Extra message[%d]: %s"
            r15 = 2
            java.lang.Object[] r8 = new java.lang.Object[r15]     // Catch:{ all -> 0x02ae }
            java.lang.Integer r16 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x02ae }
            r8[r12] = r16     // Catch:{ all -> 0x02ae }
            r16 = 1
            r8[r16] = r6     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.x.a(r7, r8)     // Catch:{ all -> 0x02ae }
            java.lang.String r7 = "="
            java.lang.String[] r7 = r6.split(r7)     // Catch:{ all -> 0x02ae }
            int r8 = r7.length     // Catch:{ all -> 0x02ae }
            if (r8 != r15) goto L_0x00a4
            r6 = r7[r12]     // Catch:{ all -> 0x02ae }
            r8 = 1
            r7 = r7[r8]     // Catch:{ all -> 0x02ae }
            r1.put(r6, r7)     // Catch:{ all -> 0x02ae }
            goto L_0x00ae
        L_0x00a4:
            java.lang.String r7 = "bad extraMsg %s"
            r8 = 1
            java.lang.Object[] r15 = new java.lang.Object[r8]     // Catch:{ all -> 0x02ae }
            r15[r12] = r6     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.x.d(r7, r15)     // Catch:{ all -> 0x02ae }
        L_0x00ae:
            int r3 = r3 + 1
            goto L_0x0079
        L_0x00b1:
            java.lang.String r2 = "not found extraMsg"
            java.lang.Object[] r3 = new java.lang.Object[r12]     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.x.c(r2, r3)     // Catch:{ all -> 0x02ae }
        L_0x00b8:
            java.lang.String r2 = "HasPendingException"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ all -> 0x02ae }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x02ae }
            if (r2 == 0) goto L_0x00d6
            java.lang.String r3 = "true"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x02ae }
            if (r2 == 0) goto L_0x00d6
            java.lang.String r2 = "Native crash happened with a Java pending exception."
            java.lang.Object[] r3 = new java.lang.Object[r12]     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.x.a(r2, r3)     // Catch:{ all -> 0x02ae }
            r19 = 1
            goto L_0x00d8
        L_0x00d6:
            r19 = r12
        L_0x00d8:
            java.lang.String r2 = "ExceptionProcessName"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ all -> 0x02ae }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x02ae }
            if (r2 == 0) goto L_0x00f6
            int r3 = r2.length()     // Catch:{ all -> 0x02ae }
            if (r3 != 0) goto L_0x00e9
            goto L_0x00f6
        L_0x00e9:
            java.lang.String r3 = "Name of crash process: %s"
            r6 = 1
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ all -> 0x02ae }
            r7[r12] = r2     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.x.c(r3, r7)     // Catch:{ all -> 0x02ae }
            r20 = r2
            goto L_0x00fc
        L_0x00f6:
            com.tencent.bugly.crashreport.common.info.a r2 = r14.c     // Catch:{ all -> 0x02ae }
            java.lang.String r2 = r2.d     // Catch:{ all -> 0x02ae }
            r20 = r2
        L_0x00fc:
            java.lang.String r2 = "ExceptionThreadName"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ all -> 0x02ae }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x02ae }
            if (r2 == 0) goto L_0x017b
            int r3 = r2.length()     // Catch:{ all -> 0x02ae }
            if (r3 != 0) goto L_0x010f
            r8 = 1
            goto L_0x017c
        L_0x010f:
            java.lang.String r3 = "Name of crash thread: %s"
            r8 = 1
            java.lang.Object[] r6 = new java.lang.Object[r8]     // Catch:{ all -> 0x02ae }
            r6[r12] = r2     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.x.c(r3, r6)     // Catch:{ all -> 0x02ae }
            java.util.Map r3 = java.lang.Thread.getAllStackTraces()     // Catch:{ all -> 0x02ae }
            java.util.Set r3 = r3.keySet()     // Catch:{ all -> 0x02ae }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x02ae }
        L_0x0126:
            boolean r6 = r3.hasNext()     // Catch:{ all -> 0x02ae }
            if (r6 == 0) goto L_0x015a
            java.lang.Object r6 = r3.next()     // Catch:{ all -> 0x02ae }
            java.lang.Thread r6 = (java.lang.Thread) r6     // Catch:{ all -> 0x02ae }
            java.lang.String r7 = r6.getName()     // Catch:{ all -> 0x02ae }
            boolean r7 = r7.equals(r2)     // Catch:{ all -> 0x02ae }
            if (r7 == 0) goto L_0x0159
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ae }
            r3.<init>()     // Catch:{ all -> 0x02ae }
            r3.append(r2)     // Catch:{ all -> 0x02ae }
            r3.append(r5)     // Catch:{ all -> 0x02ae }
            long r6 = r6.getId()     // Catch:{ all -> 0x02ae }
            r3.append(r6)     // Catch:{ all -> 0x02ae }
            r3.append(r4)     // Catch:{ all -> 0x02ae }
            java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x02ae }
            r16 = r8
            goto L_0x015c
        L_0x0159:
            goto L_0x0126
        L_0x015a:
            r16 = r12
        L_0x015c:
            if (r16 != 0) goto L_0x0178
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ae }
            r3.<init>()     // Catch:{ all -> 0x02ae }
            r3.append(r2)     // Catch:{ all -> 0x02ae }
            r3.append(r5)     // Catch:{ all -> 0x02ae }
            r2 = r29
            r3.append(r2)     // Catch:{ all -> 0x02ae }
            r3.append(r4)     // Catch:{ all -> 0x02ae }
            java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x02ae }
            r21 = r2
            goto L_0x019f
        L_0x0178:
            r21 = r2
            goto L_0x019f
        L_0x017b:
            r8 = 1
        L_0x017c:
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x02ae }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ae }
            r3.<init>()     // Catch:{ all -> 0x02ae }
            java.lang.String r6 = r2.getName()     // Catch:{ all -> 0x02ae }
            r3.append(r6)     // Catch:{ all -> 0x02ae }
            r3.append(r5)     // Catch:{ all -> 0x02ae }
            long r5 = r2.getId()     // Catch:{ all -> 0x02ae }
            r3.append(r5)     // Catch:{ all -> 0x02ae }
            r3.append(r4)     // Catch:{ all -> 0x02ae }
            java.lang.String r2 = r3.toString()     // Catch:{ all -> 0x02ae }
            r21 = r2
        L_0x019f:
            r2 = 1000(0x3e8, double:4.94E-321)
            long r4 = r30 * r2
            long r2 = r32 / r2
            long r4 = r4 + r2
            java.lang.String r2 = "SysLogPath"
            java.lang.Object r2 = r1.get(r2)     // Catch:{ all -> 0x02ae }
            r22 = r2
            java.lang.String r22 = (java.lang.String) r22     // Catch:{ all -> 0x02ae }
            java.lang.String r2 = "JniLogPath"
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x02ae }
            r23 = r1
            java.lang.String r23 = (java.lang.String) r23     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.crashreport.common.strategy.a r1 = r14.d     // Catch:{ all -> 0x02ae }
            boolean r1 = r1.b()     // Catch:{ all -> 0x02ae }
            if (r1 != 0) goto L_0x01c9
            java.lang.String r1 = "no remote but still store!"
            java.lang.Object[] r2 = new java.lang.Object[r12]     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.x.d(r1, r2)     // Catch:{ all -> 0x02ae }
        L_0x01c9:
            com.tencent.bugly.crashreport.common.strategy.a r1 = r14.d     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r1 = r1.c()     // Catch:{ all -> 0x02ae }
            boolean r1 = r1.g     // Catch:{ all -> 0x02ae }
            java.lang.String r7 = "\n"
            if (r1 != 0) goto L_0x0216
            com.tencent.bugly.crashreport.common.strategy.a r1 = r14.d     // Catch:{ all -> 0x02ae }
            boolean r1 = r1.b()     // Catch:{ all -> 0x02ae }
            if (r1 == 0) goto L_0x0216
            java.lang.String r1 = "crash report was closed by remote , will not upload to Bugly , print local for helpful!"
            java.lang.Object[] r2 = new java.lang.Object[r12]     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.x.e(r1, r2)     // Catch:{ all -> 0x02ae }
            java.lang.String r1 = "NATIVE_CRASH"
            java.lang.String r2 = com.tencent.bugly.proguard.z.a()     // Catch:{ all -> 0x02ae }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x02ae }
            r3.<init>()     // Catch:{ all -> 0x02ae }
            r3.append(r10)     // Catch:{ all -> 0x02ae }
            r3.append(r7)     // Catch:{ all -> 0x02ae }
            r3.append(r0)     // Catch:{ all -> 0x02ae }
            r3.append(r7)     // Catch:{ all -> 0x02ae }
            r3.append(r11)     // Catch:{ all -> 0x02ae }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x02ae }
            r3 = 0
            r29 = r1
            r30 = r2
            r31 = r20
            r32 = r21
            r33 = r0
            r34 = r3
            com.tencent.bugly.crashreport.crash.b.a(r29, r30, r31, r32, r33, r34)     // Catch:{ all -> 0x02ae }
            com.tencent.bugly.proguard.z.b((java.lang.String) r37)     // Catch:{ all -> 0x02ae }
            return
        L_0x0216:
            r15 = 0
            r16 = 0
            r17 = 1
            r1 = r27
            r2 = r20
            r3 = r21
            r6 = r10
            r24 = r7
            r7 = r35
            r8 = r11
            r25 = r10
            r10 = r18
            r26 = r11
            r11 = r37
            r12 = r22
            r13 = r23
            r14 = r44
            r18 = r19
            com.tencent.bugly.crashreport.crash.CrashDetailBean r1 = r1.packageCrashDatas(r2, r3, r4, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)     // Catch:{ all -> 0x02aa }
            if (r1 != 0) goto L_0x0247
            java.lang.String r0 = "pkg crash datas fail!"
            r2 = 0
            java.lang.Object[] r1 = new java.lang.Object[r2]     // Catch:{ all -> 0x02aa }
            com.tencent.bugly.proguard.x.e(r0, r1)     // Catch:{ all -> 0x02aa }
            return
        L_0x0247:
            r2 = 0
            java.lang.String r3 = "NATIVE_CRASH"
            java.lang.String r4 = com.tencent.bugly.proguard.z.a()     // Catch:{ all -> 0x02aa }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x02aa }
            r5.<init>()     // Catch:{ all -> 0x02aa }
            r6 = r25
            r5.append(r6)     // Catch:{ all -> 0x02aa }
            r6 = r24
            r5.append(r6)     // Catch:{ all -> 0x02aa }
            r5.append(r0)     // Catch:{ all -> 0x02aa }
            r5.append(r6)     // Catch:{ all -> 0x02aa }
            r0 = r26
            r5.append(r0)     // Catch:{ all -> 0x02aa }
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x02aa }
            r29 = r3
            r30 = r4
            r31 = r20
            r32 = r21
            r33 = r0
            r34 = r1
            com.tencent.bugly.crashreport.crash.b.a(r29, r30, r31, r32, r33, r34)     // Catch:{ all -> 0x02aa }
            r3 = r27
            com.tencent.bugly.crashreport.crash.b r0 = r3.b     // Catch:{ all -> 0x02a8 }
            r4 = r38
            boolean r0 = r0.a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r1, (int) r4)     // Catch:{ all -> 0x02a8 }
            if (r0 != 0) goto L_0x0289
            r12 = 1
            goto L_0x028a
        L_0x0289:
            r12 = r2
        L_0x028a:
            r0 = 0
            com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler r2 = com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler.getInstance()     // Catch:{ all -> 0x02a8 }
            if (r2 == 0) goto L_0x0295
            java.lang.String r0 = r2.getDumpFilePath()     // Catch:{ all -> 0x02a8 }
        L_0x0295:
            r2 = 1
            com.tencent.bugly.crashreport.crash.jni.b.a((boolean) r2, (java.lang.String) r0)     // Catch:{ all -> 0x02a8 }
            if (r12 == 0) goto L_0x02a2
            com.tencent.bugly.crashreport.crash.b r0 = r3.b     // Catch:{ all -> 0x02a8 }
            r4 = 3000(0xbb8, double:1.482E-320)
            r0.a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r1, (long) r4, (boolean) r2)     // Catch:{ all -> 0x02a8 }
        L_0x02a2:
            com.tencent.bugly.crashreport.crash.b r0 = r3.b     // Catch:{ all -> 0x02a8 }
            r0.b((com.tencent.bugly.crashreport.crash.CrashDetailBean) r1)     // Catch:{ all -> 0x02a8 }
            return
        L_0x02a8:
            r0 = move-exception
            goto L_0x02b0
        L_0x02aa:
            r0 = move-exception
            r3 = r27
            goto L_0x02b0
        L_0x02ae:
            r0 = move-exception
            r3 = r14
        L_0x02b0:
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x02b9
            r0.printStackTrace()
        L_0x02b9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.jni.a.handleNativeException2(int, int, long, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, int, int, int, java.lang.String, java.lang.String, java.lang.String[]):void");
    }
}
