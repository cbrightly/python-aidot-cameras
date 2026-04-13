package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.Map;

/* compiled from: BUGLY */
public final class d {
    /* access modifiers changed from: private */
    public static d a = null;
    private a b;
    private com.tencent.bugly.crashreport.common.info.a c;
    private b d;
    private Context e;

    static /* synthetic */ void a(d dVar) {
        x.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class<?> cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            String str = "com.tencent.bugly";
            dVar.c.getClass();
            if (!"".equals("")) {
                str = str + "." + "";
            }
            z.a(cls, "sdkPackageName", (Object) str, (Object) null);
            x.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable th) {
            x.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x023d A[Catch:{ all -> 0x0236, all -> 0x0246 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.tencent.bugly.crashreport.crash.d r15, java.lang.Thread r16, int r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.util.Map r21) {
        /*
            r0 = r15
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r21
            java.lang.String r6 = "[ExtraCrashManager] Successfully handled."
            if (r16 != 0) goto L_0x0014
            java.lang.Thread r7 = java.lang.Thread.currentThread()
            goto L_0x0016
        L_0x0014:
            r7 = r16
        L_0x0016:
            r8 = 1
            r9 = 0
            switch(r1) {
                case 4: goto L_0x002f;
                case 5: goto L_0x002c;
                case 6: goto L_0x002c;
                case 7: goto L_0x001b;
                case 8: goto L_0x0029;
                default: goto L_0x001b;
            }
        L_0x001b:
            java.lang.Object[] r0 = new java.lang.Object[r8]
            java.lang.Integer r1 = java.lang.Integer.valueOf(r17)
            r0[r9] = r1
            java.lang.String r1 = "[ExtraCrashManager] Unknown extra crash type: %d"
            com.tencent.bugly.proguard.x.d(r1, r0)
            return
        L_0x0029:
            java.lang.String r10 = "H5"
            goto L_0x0031
        L_0x002c:
            java.lang.String r10 = "Cocos"
            goto L_0x0031
        L_0x002f:
            java.lang.String r10 = "Unity"
        L_0x0031:
            java.lang.Object[] r11 = new java.lang.Object[r8]
            r11[r9] = r10
            java.lang.String r12 = "[ExtraCrashManager] %s Crash Happen"
            com.tencent.bugly.proguard.x.e(r12, r11)
            com.tencent.bugly.crashreport.common.strategy.a r11 = r0.b     // Catch:{ all -> 0x0236 }
            boolean r11 = r11.b()     // Catch:{ all -> 0x0236 }
            if (r11 != 0) goto L_0x0049
            java.lang.String r11 = "[ExtraCrashManager] There is no remote strategy, but still store it."
            java.lang.Object[] r12 = new java.lang.Object[r9]     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.proguard.x.d(r11, r12)     // Catch:{ all -> 0x0236 }
        L_0x0049:
            com.tencent.bugly.crashreport.common.strategy.a r11 = r0.b     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r11 = r11.c()     // Catch:{ all -> 0x0236 }
            boolean r12 = r11.g     // Catch:{ all -> 0x0236 }
            java.lang.String r13 = "\n"
            if (r12 != 0) goto L_0x009d
            com.tencent.bugly.crashreport.common.strategy.a r12 = r0.b     // Catch:{ all -> 0x0236 }
            boolean r12 = r12.b()     // Catch:{ all -> 0x0236 }
            if (r12 == 0) goto L_0x009d
            java.lang.String r1 = "[ExtraCrashManager] Crash report was closed by remote , will not upload to Bugly , print local for helpful!"
            java.lang.Object[] r5 = new java.lang.Object[r9]     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.proguard.x.e(r1, r5)     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = com.tencent.bugly.proguard.z.a()     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r0 = r0.c     // Catch:{ all -> 0x0236 }
            java.lang.String r0 = r0.d     // Catch:{ all -> 0x0236 }
            java.lang.String r5 = r7.getName()     // Catch:{ all -> 0x0236 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0236 }
            r7.<init>()     // Catch:{ all -> 0x0236 }
            r7.append(r2)     // Catch:{ all -> 0x0236 }
            r7.append(r13)     // Catch:{ all -> 0x0236 }
            r7.append(r3)     // Catch:{ all -> 0x0236 }
            r7.append(r13)     // Catch:{ all -> 0x0236 }
            r7.append(r4)     // Catch:{ all -> 0x0236 }
            java.lang.String r2 = r7.toString()     // Catch:{ all -> 0x0236 }
            r3 = 0
            r15 = r10
            r16 = r1
            r17 = r0
            r18 = r5
            r19 = r2
            r20 = r3
            com.tencent.bugly.crashreport.crash.b.a(r15, r16, r17, r18, r19, r20)     // Catch:{ all -> 0x0236 }
            java.lang.Object[] r0 = new java.lang.Object[r9]
            com.tencent.bugly.proguard.x.e(r6, r0)
            return
        L_0x009d:
            java.lang.String r12 = "[ExtraCrashManager] %s report is disabled."
            switch(r1) {
                case 4: goto L_0x00a2;
                case 5: goto L_0x00b4;
                case 6: goto L_0x00b4;
                case 7: goto L_0x00a2;
                case 8: goto L_0x00a3;
                default: goto L_0x00a2;
            }
        L_0x00a2:
            goto L_0x00c5
        L_0x00a3:
            boolean r11 = r11.m     // Catch:{ all -> 0x0236 }
            if (r11 != 0) goto L_0x00a2
            java.lang.Object[] r0 = new java.lang.Object[r8]     // Catch:{ all -> 0x0236 }
            r0[r9] = r10     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.proguard.x.e(r12, r0)     // Catch:{ all -> 0x0236 }
            java.lang.Object[] r0 = new java.lang.Object[r9]
            com.tencent.bugly.proguard.x.e(r6, r0)
            return
        L_0x00b4:
            boolean r11 = r11.l     // Catch:{ all -> 0x0236 }
            if (r11 != 0) goto L_0x00a2
            java.lang.Object[] r0 = new java.lang.Object[r8]     // Catch:{ all -> 0x0236 }
            r0[r9] = r10     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.proguard.x.e(r12, r0)     // Catch:{ all -> 0x0236 }
            java.lang.Object[] r0 = new java.lang.Object[r9]
            com.tencent.bugly.proguard.x.e(r6, r0)
            return
        L_0x00c5:
            r8 = 8
            if (r1 != r8) goto L_0x00ca
            r1 = 5
        L_0x00ca:
            com.tencent.bugly.crashreport.crash.CrashDetailBean r8 = new com.tencent.bugly.crashreport.crash.CrashDetailBean     // Catch:{ all -> 0x0236 }
            r8.<init>()     // Catch:{ all -> 0x0236 }
            long r11 = com.tencent.bugly.crashreport.common.info.b.k()     // Catch:{ all -> 0x0236 }
            r8.C = r11     // Catch:{ all -> 0x0236 }
            long r11 = com.tencent.bugly.crashreport.common.info.b.i()     // Catch:{ all -> 0x0236 }
            r8.D = r11     // Catch:{ all -> 0x0236 }
            long r11 = com.tencent.bugly.crashreport.common.info.b.m()     // Catch:{ all -> 0x0236 }
            r8.E = r11     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r11 = r0.c     // Catch:{ all -> 0x0236 }
            long r11 = r11.p()     // Catch:{ all -> 0x0236 }
            r8.F = r11     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r11 = r0.c     // Catch:{ all -> 0x0236 }
            long r11 = r11.o()     // Catch:{ all -> 0x0236 }
            r8.G = r11     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r11 = r0.c     // Catch:{ all -> 0x0236 }
            long r11 = r11.q()     // Catch:{ all -> 0x0236 }
            r8.H = r11     // Catch:{ all -> 0x0236 }
            android.content.Context r11 = r0.e     // Catch:{ all -> 0x0236 }
            int r12 = com.tencent.bugly.crashreport.crash.c.e     // Catch:{ all -> 0x0236 }
            r14 = 0
            java.lang.String r11 = com.tencent.bugly.proguard.z.a((android.content.Context) r11, (int) r12, (java.lang.String) r14)     // Catch:{ all -> 0x0236 }
            r8.w = r11     // Catch:{ all -> 0x0236 }
            r8.b = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r1.h()     // Catch:{ all -> 0x0236 }
            r8.e = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            java.lang.String r11 = r1.j     // Catch:{ all -> 0x0236 }
            r8.f = r11     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r1.w()     // Catch:{ all -> 0x0236 }
            r8.g = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r1.g()     // Catch:{ all -> 0x0236 }
            r8.m = r1     // Catch:{ all -> 0x0236 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0236 }
            r1.<init>()     // Catch:{ all -> 0x0236 }
            r1.append(r2)     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0236 }
            r8.n = r1     // Catch:{ all -> 0x0236 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0236 }
            r1.<init>()     // Catch:{ all -> 0x0236 }
            r1.append(r3)     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0236 }
            r8.o = r1     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = ""
            if (r4 == 0) goto L_0x014d
            java.lang.String[] r11 = r4.split(r13)     // Catch:{ all -> 0x0236 }
            int r12 = r11.length     // Catch:{ all -> 0x0236 }
            if (r12 <= 0) goto L_0x014b
            r1 = r11[r9]     // Catch:{ all -> 0x0236 }
        L_0x014b:
            r11 = r4
            goto L_0x014e
        L_0x014d:
            r11 = r1
        L_0x014e:
            r8.p = r1     // Catch:{ all -> 0x0236 }
            r8.q = r11     // Catch:{ all -> 0x0236 }
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0236 }
            r8.r = r11     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r8.q     // Catch:{ all -> 0x0236 }
            byte[] r1 = r1.getBytes()     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = com.tencent.bugly.proguard.z.b((byte[]) r1)     // Catch:{ all -> 0x0236 }
            r8.u = r1     // Catch:{ all -> 0x0236 }
            int r1 = com.tencent.bugly.crashreport.crash.c.f     // Catch:{ all -> 0x0236 }
            java.util.Map r1 = com.tencent.bugly.proguard.z.a((int) r1, (boolean) r9)     // Catch:{ all -> 0x0236 }
            r8.z = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r1.d     // Catch:{ all -> 0x0236 }
            r8.A = r1     // Catch:{ all -> 0x0236 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0236 }
            r1.<init>()     // Catch:{ all -> 0x0236 }
            java.lang.String r11 = r7.getName()     // Catch:{ all -> 0x0236 }
            r1.append(r11)     // Catch:{ all -> 0x0236 }
            java.lang.String r11 = "("
            r1.append(r11)     // Catch:{ all -> 0x0236 }
            long r11 = r7.getId()     // Catch:{ all -> 0x0236 }
            r1.append(r11)     // Catch:{ all -> 0x0236 }
            java.lang.String r11 = ")"
            r1.append(r11)     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0236 }
            r8.B = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            java.lang.String r1 = r1.y()     // Catch:{ all -> 0x0236 }
            r8.I = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            java.util.Map r1 = r1.v()     // Catch:{ all -> 0x0236 }
            r8.h = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            long r11 = r1.a     // Catch:{ all -> 0x0236 }
            r8.M = r11     // Catch:{ all -> 0x0236 }
            boolean r1 = r1.a()     // Catch:{ all -> 0x0236 }
            r8.N = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            int r1 = r1.H()     // Catch:{ all -> 0x0236 }
            r8.P = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            int r1 = r1.I()     // Catch:{ all -> 0x0236 }
            r8.Q = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            java.util.Map r1 = r1.B()     // Catch:{ all -> 0x0236 }
            r8.R = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r1 = r0.c     // Catch:{ all -> 0x0236 }
            java.util.Map r1 = r1.G()     // Catch:{ all -> 0x0236 }
            r8.S = r1     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.crash.b r1 = r0.d     // Catch:{ all -> 0x0236 }
            r1.c((com.tencent.bugly.crashreport.crash.CrashDetailBean) r8)     // Catch:{ all -> 0x0236 }
            byte[] r1 = com.tencent.bugly.proguard.y.a()     // Catch:{ all -> 0x0236 }
            r8.y = r1     // Catch:{ all -> 0x0236 }
            java.util.Map<java.lang.String, java.lang.String> r1 = r8.O     // Catch:{ all -> 0x0236 }
            if (r1 != 0) goto L_0x01e7
            java.util.LinkedHashMap r1 = new java.util.LinkedHashMap     // Catch:{ all -> 0x0236 }
            r1.<init>()     // Catch:{ all -> 0x0236 }
            r8.O = r1     // Catch:{ all -> 0x0236 }
        L_0x01e7:
            if (r5 == 0) goto L_0x01ee
            java.util.Map<java.lang.String, java.lang.String> r1 = r8.O     // Catch:{ all -> 0x0236 }
            r1.putAll(r5)     // Catch:{ all -> 0x0236 }
        L_0x01ee:
            java.lang.String r1 = com.tencent.bugly.proguard.z.a()     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.common.info.a r5 = r0.c     // Catch:{ all -> 0x0236 }
            java.lang.String r5 = r5.d     // Catch:{ all -> 0x0236 }
            java.lang.String r7 = r7.getName()     // Catch:{ all -> 0x0236 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0236 }
            r11.<init>()     // Catch:{ all -> 0x0236 }
            r11.append(r2)     // Catch:{ all -> 0x0236 }
            r11.append(r13)     // Catch:{ all -> 0x0236 }
            r11.append(r3)     // Catch:{ all -> 0x0236 }
            r11.append(r13)     // Catch:{ all -> 0x0236 }
            r11.append(r4)     // Catch:{ all -> 0x0236 }
            java.lang.String r2 = r11.toString()     // Catch:{ all -> 0x0236 }
            r16 = r10
            r17 = r1
            r18 = r5
            r19 = r7
            r20 = r2
            r21 = r8
            com.tencent.bugly.crashreport.crash.b.a(r16, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x0236 }
            com.tencent.bugly.crashreport.crash.b r1 = r0.d     // Catch:{ all -> 0x0236 }
            boolean r1 = r1.a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r8)     // Catch:{ all -> 0x0236 }
            if (r1 != 0) goto L_0x0230
            com.tencent.bugly.crashreport.crash.b r0 = r0.d     // Catch:{ all -> 0x0236 }
            r1 = 3000(0xbb8, double:1.482E-320)
            r0.a((com.tencent.bugly.crashreport.crash.CrashDetailBean) r8, (long) r1, (boolean) r9)     // Catch:{ all -> 0x0236 }
        L_0x0230:
            java.lang.Object[] r0 = new java.lang.Object[r9]
            com.tencent.bugly.proguard.x.e(r6, r0)
            return
        L_0x0236:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)     // Catch:{ all -> 0x0246 }
            if (r1 != 0) goto L_0x0240
            r0.printStackTrace()     // Catch:{ all -> 0x0246 }
        L_0x0240:
            java.lang.Object[] r0 = new java.lang.Object[r9]
            com.tencent.bugly.proguard.x.e(r6, r0)
            return
        L_0x0246:
            r0 = move-exception
            java.lang.Object[] r1 = new java.lang.Object[r9]
            com.tencent.bugly.proguard.x.e(r6, r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.d.a(com.tencent.bugly.crashreport.crash.d, java.lang.Thread, int, java.lang.String, java.lang.String, java.lang.String, java.util.Map):void");
    }

    private d(Context context) {
        c a2 = c.a();
        if (a2 != null) {
            this.b = a.a();
            this.c = com.tencent.bugly.crashreport.common.info.a.a(context);
            this.d = a2.p;
            this.e = context;
            w.a().a(new Runnable() {
                public final void run() {
                    d.a(d.this);
                }
            });
        }
    }

    public static d a(Context context) {
        if (a == null) {
            a = new d(context);
        }
        return a;
    }

    public static void a(Thread thread, int i, String str, String str2, String str3, Map<String, String> map) {
        final Thread thread2 = thread;
        final int i2 = i;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Map<String, String> map2 = map;
        w.a().a(new Runnable() {
            public final void run() {
                try {
                    if (d.a == null) {
                        x.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        d.a(d.a, thread2, i2, str4, str5, str6, map2);
                    }
                } catch (Throwable th) {
                    if (!x.b(th)) {
                        th.printStackTrace();
                    }
                    x.e("[ExtraCrashManager] Crash error %s %s %s", str4, str5, str6);
                }
            }
        });
    }
}
