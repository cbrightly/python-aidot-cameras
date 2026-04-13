package com.tencent.bugly.proguard;

import android.content.Context;
import com.tencent.bugly.crashreport.common.info.a;
import java.util.Map;
import java.util.UUID;
import meshsdk.model.json.RoutineRule;

/* compiled from: BUGLY */
public final class v implements Runnable {
    private int a;
    private int b;
    private final Context c;
    private final int d;
    private final byte[] e;
    private final a f;
    private final com.tencent.bugly.crashreport.common.strategy.a g;
    private final s h;
    private final u i;
    private final int j;
    private final t k;
    private final t l;
    private String m;
    private final String n;
    private final Map<String, String> o;
    private int p;
    private long q;
    private long r;
    private boolean s;
    private boolean t;

    public v(Context context, int i2, int i3, byte[] bArr, String str, String str2, t tVar, boolean z, boolean z2) {
        this(context, i2, i3, bArr, str, str2, tVar, z, 2, 30000, z2, (Map<String, String>) null);
    }

    public v(Context context, int i2, int i3, byte[] bArr, String str, String str2, t tVar, boolean z, int i4, int i5, boolean z2, Map<String, String> map) {
        int i6 = i4;
        int i7 = i5;
        this.a = 2;
        this.b = 30000;
        this.m = null;
        this.p = 0;
        this.q = 0;
        this.r = 0;
        this.s = true;
        this.t = false;
        this.c = context;
        this.f = a.a(context);
        this.e = bArr;
        this.g = com.tencent.bugly.crashreport.common.strategy.a.a();
        this.h = s.a(context);
        this.i = u.a();
        this.j = i2;
        this.m = str;
        this.n = str2;
        this.k = tVar;
        this.l = null;
        this.s = z;
        this.d = i3;
        if (i6 > 0) {
            this.a = i6;
        }
        if (i7 > 0) {
            this.b = i7;
        }
        this.t = z2;
        this.o = map;
    }

    private void a(aq aqVar, boolean z, int i2, String str, int i3) {
        String str2;
        int i4 = this.d;
        switch (i4) {
            case 630:
            case 830:
                str2 = "crash";
                break;
            case 640:
            case 840:
                str2 = "userinfo";
                break;
            default:
                str2 = String.valueOf(i4);
                break;
        }
        if (z) {
            x.a("[Upload] Success: %s", str2);
        } else {
            x.e("[Upload] Failed to upload(%d) %s: %s", Integer.valueOf(i2), str2, str);
            if (this.s) {
                this.i.a(i3, (aq) null);
            }
        }
        if (this.q + this.r > 0) {
            this.i.a(this.i.a(this.t) + this.q + this.r, this.t);
        }
        t tVar = this.k;
        if (tVar != null) {
            tVar.a(z);
        }
        t tVar2 = this.l;
        if (tVar2 != null) {
            tVar2.a(z);
        }
    }

    private static boolean a(aq aqVar, a aVar, com.tencent.bugly.crashreport.common.strategy.a aVar2) {
        if (aqVar == null) {
            x.d("resp == null!", new Object[0]);
            return false;
        }
        byte b2 = aqVar.a;
        if (b2 != 0) {
            x.e("resp result error %d", Byte.valueOf(b2));
            return false;
        }
        try {
            if (!z.a(aqVar.d)) {
                if (!a.b().i().equals(aqVar.d)) {
                    p.a().a(com.tencent.bugly.crashreport.common.strategy.a.a, "gateway", aqVar.d.getBytes("UTF-8"), (o) null, true);
                    aVar.d(aqVar.d);
                }
            }
            if (!z.a(aqVar.f) && !a.b().j().equals(aqVar.f)) {
                p.a().a(com.tencent.bugly.crashreport.common.strategy.a.a, RoutineRule.THEN_TYPE_DEVICE, aqVar.f.getBytes("UTF-8"), (o) null, true);
                aVar.e(aqVar.f);
            }
        } catch (Throwable th) {
            x.a(th);
        }
        aVar.i = aqVar.e;
        int i2 = aqVar.b;
        if (i2 == 510) {
            byte[] bArr = aqVar.c;
            if (bArr == null) {
                x.e("[Upload] Strategy data is null. Response cmd: %d", Integer.valueOf(i2));
                return false;
            }
            as asVar = (as) a.a(bArr, as.class);
            if (asVar == null) {
                x.e("[Upload] Failed to decode strategy from server. Response cmd: %d", Integer.valueOf(aqVar.b));
                return false;
            }
            aVar2.a(asVar);
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x02a6, code lost:
        if (r11 != 2) goto L_0x030b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x02b1, code lost:
        if ((r7.q + r7.r) <= 0) goto L_0x02c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x02b3, code lost:
        r7.i.a((r7.i.a(r7.t) + r7.q) + r7.r, r7.t);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x02c8, code lost:
        r7.i.a(r11, (com.tencent.bugly.proguard.aq) null);
        com.tencent.bugly.proguard.x.a("[Upload] Session ID is invalid, will try again immediately (pid=%d | tid=%d).", java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
        r7.i.a(r7.j, r7.d, r7.e, r7.m, r7.n, r7.k, r7.a, r7.b, true, r7.o);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x030a, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x030b, code lost:
        a((com.tencent.bugly.proguard.aq) null, false, 1, "status of server is " + r11, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0323, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0324, code lost:
        r12 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x02a3, code lost:
        if (r11 == 0) goto L_0x0324;
     */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0205 A[Catch:{ all -> 0x0326, all -> 0x044e }] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0274 A[SYNTHETIC, Splitter:B:94:0x0274] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r21 = this;
            r7 = r21
            java.lang.String r1 = "[Upload] Failed to upload for no status header."
            java.lang.String r2 = "Bugly-Version"
            r3 = 0
            r7.p = r3     // Catch:{ all -> 0x044e }
            r4 = 0
            r7.q = r4     // Catch:{ all -> 0x044e }
            r7.r = r4     // Catch:{ all -> 0x044e }
            byte[] r0 = r7.e     // Catch:{ all -> 0x044e }
            android.content.Context r6 = r7.c     // Catch:{ all -> 0x044e }
            java.lang.String r6 = com.tencent.bugly.crashreport.common.info.b.c(r6)     // Catch:{ all -> 0x044e }
            if (r6 != 0) goto L_0x0025
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.String r5 = "network is not available"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x0025:
            if (r0 == 0) goto L_0x0441
            int r6 = r0.length     // Catch:{ all -> 0x044e }
            if (r6 != 0) goto L_0x002c
            goto L_0x0441
        L_0x002c:
            java.lang.String r6 = "[Upload] Run upload task with cmd: %d"
            r8 = 1
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            int r10 = r7.d     // Catch:{ all -> 0x044e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x044e }
            r9[r3] = r10     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r6, r9)     // Catch:{ all -> 0x044e }
            android.content.Context r6 = r7.c     // Catch:{ all -> 0x044e }
            if (r6 == 0) goto L_0x0435
            com.tencent.bugly.crashreport.common.info.a r6 = r7.f     // Catch:{ all -> 0x044e }
            if (r6 == 0) goto L_0x0435
            com.tencent.bugly.crashreport.common.strategy.a r6 = r7.g     // Catch:{ all -> 0x044e }
            if (r6 == 0) goto L_0x0435
            com.tencent.bugly.proguard.s r9 = r7.h     // Catch:{ all -> 0x044e }
            if (r9 != 0) goto L_0x004e
            goto L_0x0435
        L_0x004e:
            com.tencent.bugly.crashreport.common.strategy.StrategyBean r6 = r6.c()     // Catch:{ all -> 0x044e }
            if (r6 != 0) goto L_0x0060
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.String r5 = "illegal local strategy"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x0060:
            java.util.HashMap r9 = new java.util.HashMap     // Catch:{ all -> 0x044e }
            r9.<init>()     // Catch:{ all -> 0x044e }
            java.lang.String r10 = "prodId"
            com.tencent.bugly.crashreport.common.info.a r11 = r7.f     // Catch:{ all -> 0x044e }
            java.lang.String r11 = r11.f()     // Catch:{ all -> 0x044e }
            r9.put(r10, r11)     // Catch:{ all -> 0x044e }
            java.lang.String r10 = "bundleId"
            com.tencent.bugly.crashreport.common.info.a r11 = r7.f     // Catch:{ all -> 0x044e }
            java.lang.String r11 = r11.c     // Catch:{ all -> 0x044e }
            r9.put(r10, r11)     // Catch:{ all -> 0x044e }
            java.lang.String r10 = "appVer"
            com.tencent.bugly.crashreport.common.info.a r11 = r7.f     // Catch:{ all -> 0x044e }
            java.lang.String r11 = r11.j     // Catch:{ all -> 0x044e }
            r9.put(r10, r11)     // Catch:{ all -> 0x044e }
            java.util.Map<java.lang.String, java.lang.String> r10 = r7.o     // Catch:{ all -> 0x044e }
            if (r10 == 0) goto L_0x008b
            r9.putAll(r10)     // Catch:{ all -> 0x044e }
        L_0x008b:
            boolean r10 = r7.s     // Catch:{ all -> 0x044e }
            r11 = 2
            if (r10 == 0) goto L_0x00fa
            java.lang.String r10 = "cmd"
            int r12 = r7.d     // Catch:{ all -> 0x044e }
            java.lang.String r12 = java.lang.Integer.toString(r12)     // Catch:{ all -> 0x044e }
            r9.put(r10, r12)     // Catch:{ all -> 0x044e }
            java.lang.String r10 = "platformId"
            java.lang.String r12 = java.lang.Byte.toString(r8)     // Catch:{ all -> 0x044e }
            r9.put(r10, r12)     // Catch:{ all -> 0x044e }
            java.lang.String r10 = "sdkVer"
            com.tencent.bugly.crashreport.common.info.a r12 = r7.f     // Catch:{ all -> 0x044e }
            r12.getClass()     // Catch:{ all -> 0x044e }
            java.lang.String r12 = "3.1.0"
            r9.put(r10, r12)     // Catch:{ all -> 0x044e }
            java.lang.String r10 = "strategylastUpdateTime"
            long r12 = r6.p     // Catch:{ all -> 0x044e }
            java.lang.String r6 = java.lang.Long.toString(r12)     // Catch:{ all -> 0x044e }
            r9.put(r10, r6)     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.u r6 = r7.i     // Catch:{ all -> 0x044e }
            boolean r6 = r6.a((java.util.Map<java.lang.String, java.lang.String>) r9)     // Catch:{ all -> 0x044e }
            if (r6 != 0) goto L_0x00d2
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.String r5 = "failed to add security info to HTTP headers"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x00d2:
            byte[] r0 = com.tencent.bugly.proguard.z.a((byte[]) r0, (int) r11)     // Catch:{ all -> 0x044e }
            if (r0 != 0) goto L_0x00e4
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.String r5 = "failed to zip request body"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x00e4:
            com.tencent.bugly.proguard.u r6 = r7.i     // Catch:{ all -> 0x044e }
            byte[] r0 = r6.a((byte[]) r0)     // Catch:{ all -> 0x044e }
            if (r0 != 0) goto L_0x00f8
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.String r5 = "failed to encrypt request body"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x00f8:
            r6 = r0
            goto L_0x00fb
        L_0x00fa:
            r6 = r0
        L_0x00fb:
            com.tencent.bugly.proguard.u r0 = r7.i     // Catch:{ all -> 0x044e }
            int r10 = r7.j     // Catch:{ all -> 0x044e }
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x044e }
            r0.a((int) r10, (long) r12)     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.t r0 = r7.k     // Catch:{ all -> 0x044e }
            if (r0 == 0) goto L_0x010a
        L_0x010a:
            com.tencent.bugly.proguard.t r0 = r7.l     // Catch:{ all -> 0x044e }
            if (r0 == 0) goto L_0x010e
        L_0x010e:
            java.lang.String r0 = r7.m     // Catch:{ all -> 0x044e }
            r10 = -1
            r13 = r3
            r12 = r10
            r10 = r13
        L_0x0114:
            int r14 = r13 + 1
            int r15 = r7.a     // Catch:{ all -> 0x044e }
            if (r13 >= r15) goto L_0x0429
            if (r14 <= r8) goto L_0x0140
            java.lang.String r10 = "[Upload] Failed to upload last time, wait and try(%d) again."
            java.lang.Object[] r13 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            java.lang.Integer r15 = java.lang.Integer.valueOf(r14)     // Catch:{ all -> 0x044e }
            r13[r3] = r15     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.d(r10, r13)     // Catch:{ all -> 0x044e }
            int r10 = r7.b     // Catch:{ all -> 0x044e }
            long r4 = (long) r10     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.z.b((long) r4)     // Catch:{ all -> 0x044e }
            int r4 = r7.a     // Catch:{ all -> 0x044e }
            if (r14 != r4) goto L_0x0140
            java.lang.String r0 = "[Upload] Use the back-up url at the last time: %s"
            java.lang.Object[] r4 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            java.lang.String r5 = r7.n     // Catch:{ all -> 0x044e }
            r4[r3] = r5     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.d(r0, r4)     // Catch:{ all -> 0x044e }
            java.lang.String r0 = r7.n     // Catch:{ all -> 0x044e }
        L_0x0140:
            java.lang.String r4 = "[Upload] Send %d bytes"
            java.lang.Object[] r5 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            int r10 = r6.length     // Catch:{ all -> 0x044e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x044e }
            r5[r3] = r10     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r4, r5)     // Catch:{ all -> 0x044e }
            boolean r4 = r7.s     // Catch:{ all -> 0x044e }
            if (r4 == 0) goto L_0x0158
            java.lang.String r0 = a((java.lang.String) r0)     // Catch:{ all -> 0x044e }
            r4 = r0
            goto L_0x0159
        L_0x0158:
            r4 = r0
        L_0x0159:
            java.lang.String r0 = "[Upload] Upload to %s with cmd %d (pid=%d | tid=%d)."
            r5 = 4
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x044e }
            r5[r3] = r4     // Catch:{ all -> 0x044e }
            int r10 = r7.d     // Catch:{ all -> 0x044e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x044e }
            r5[r8] = r10     // Catch:{ all -> 0x044e }
            int r10 = android.os.Process.myPid()     // Catch:{ all -> 0x044e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x044e }
            r5[r11] = r10     // Catch:{ all -> 0x044e }
            int r10 = android.os.Process.myTid()     // Catch:{ all -> 0x044e }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ all -> 0x044e }
            r13 = 3
            r5[r13] = r10     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r0, r5)     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.s r0 = r7.h     // Catch:{ all -> 0x044e }
            byte[] r0 = r0.a((java.lang.String) r4, (byte[]) r6, (com.tencent.bugly.proguard.v) r7, (java.util.Map<java.lang.String, java.lang.String>) r9)     // Catch:{ all -> 0x044e }
            java.lang.String r5 = "[Upload] Failed to upload(%d): %s"
            if (r0 != 0) goto L_0x01a1
            java.lang.String r0 = "Failed to upload for no response!"
            java.lang.Object[] r10 = new java.lang.Object[r11]     // Catch:{ all -> 0x044e }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x044e }
            r10[r3] = r13     // Catch:{ all -> 0x044e }
            r10[r8] = r0     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.e(r5, r10)     // Catch:{ all -> 0x044e }
            r0 = r4
            r10 = r8
            r13 = r14
            r4 = 0
            goto L_0x0114
        L_0x01a1:
            com.tencent.bugly.proguard.s r10 = r7.h     // Catch:{ all -> 0x044e }
            java.util.Map<java.lang.String, java.lang.String> r10 = r10.a     // Catch:{ all -> 0x044e }
            boolean r15 = r7.s     // Catch:{ all -> 0x044e }
            if (r15 == 0) goto L_0x0359
            java.lang.String r15 = "status"
            if (r10 == 0) goto L_0x01fa
            int r16 = r10.size()     // Catch:{ all -> 0x044e }
            if (r16 != 0) goto L_0x01b5
            goto L_0x01fa
        L_0x01b5:
            boolean r16 = r10.containsKey(r15)     // Catch:{ all -> 0x044e }
            java.lang.String r13 = "[Upload] Headers does not contain %s"
            if (r16 != 0) goto L_0x01c5
            java.lang.Object[] r11 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            r11[r3] = r15     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.d(r13, r11)     // Catch:{ all -> 0x044e }
            goto L_0x0203
        L_0x01c5:
            boolean r11 = r10.containsKey(r2)     // Catch:{ all -> 0x044e }
            if (r11 != 0) goto L_0x01d3
            java.lang.Object[] r11 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            r11[r3] = r2     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.d(r13, r11)     // Catch:{ all -> 0x044e }
            goto L_0x0203
        L_0x01d3:
            java.lang.Object r11 = r10.get(r2)     // Catch:{ all -> 0x044e }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x044e }
            java.lang.String r13 = "bugly"
            boolean r13 = r11.contains(r13)     // Catch:{ all -> 0x044e }
            if (r13 != 0) goto L_0x01ed
            java.lang.String r13 = "[Upload] Bugly version is not valid: %s"
            java.lang.Object[] r3 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            r20 = 0
            r3[r20] = r11     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.d(r13, r3)     // Catch:{ all -> 0x044e }
            goto L_0x0202
        L_0x01ed:
            java.lang.String r3 = "[Upload] Bugly version from headers is: %s"
            java.lang.Object[] r13 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            r20 = 0
            r13[r20] = r11     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r3, r13)     // Catch:{ all -> 0x044e }
            r3 = r8
            goto L_0x0203
        L_0x01fa:
            java.lang.String r3 = "[Upload] Headers is empty."
            r11 = 0
            java.lang.Object[] r13 = new java.lang.Object[r11]     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.d(r3, r13)     // Catch:{ all -> 0x044e }
        L_0x0202:
            r3 = 0
        L_0x0203:
            if (r3 != 0) goto L_0x0274
            java.lang.String r0 = "[Upload] Headers from server is not valid, just try again (pid=%d | tid=%d)."
            r3 = 2
            java.lang.Object[] r11 = new java.lang.Object[r3]     // Catch:{ all -> 0x044e }
            int r3 = android.os.Process.myPid()     // Catch:{ all -> 0x044e }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x044e }
            r13 = 0
            r11[r13] = r3     // Catch:{ all -> 0x044e }
            int r3 = android.os.Process.myTid()     // Catch:{ all -> 0x044e }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x044e }
            r11[r8] = r3     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r0, r11)     // Catch:{ all -> 0x044e }
            r3 = 2
            java.lang.Object[] r0 = new java.lang.Object[r3]     // Catch:{ all -> 0x044e }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x044e }
            r11 = 0
            r0[r11] = r3     // Catch:{ all -> 0x044e }
            r0[r8] = r1     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.e(r5, r0)     // Catch:{ all -> 0x044e }
            if (r10 == 0) goto L_0x0264
            java.util.Set r0 = r10.entrySet()     // Catch:{ all -> 0x044e }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x044e }
        L_0x023b:
            boolean r3 = r0.hasNext()     // Catch:{ all -> 0x044e }
            if (r3 == 0) goto L_0x0264
            java.lang.Object r3 = r0.next()     // Catch:{ all -> 0x044e }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x044e }
            java.lang.String r5 = "[key]: %s, [value]: %s"
            r10 = 2
            java.lang.Object[] r11 = new java.lang.Object[r10]     // Catch:{ all -> 0x044e }
            java.lang.Object r10 = r3.getKey()     // Catch:{ all -> 0x044e }
            r13 = 0
            r11[r13] = r10     // Catch:{ all -> 0x044e }
            java.lang.Object r3 = r3.getValue()     // Catch:{ all -> 0x044e }
            r11[r8] = r3     // Catch:{ all -> 0x044e }
            java.lang.String r3 = java.lang.String.format(r5, r11)     // Catch:{ all -> 0x044e }
            r5 = 0
            java.lang.Object[] r10 = new java.lang.Object[r5]     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r3, r10)     // Catch:{ all -> 0x044e }
            goto L_0x023b
        L_0x0264:
            r3 = 0
            java.lang.Object[] r0 = new java.lang.Object[r3]     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r1, r0)     // Catch:{ all -> 0x044e }
            r0 = r4
            r10 = r8
            r13 = r14
            r3 = 0
            r4 = 0
            r11 = 2
            goto L_0x0114
        L_0x0274:
            java.lang.Object r3 = r10.get(r15)     // Catch:{ all -> 0x032b }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x032b }
            int r11 = java.lang.Integer.parseInt(r3)     // Catch:{ all -> 0x032b }
            java.lang.String r3 = "[Upload] Status from server is %d (pid=%d | tid=%d)."
            r12 = 3
            java.lang.Object[] r12 = new java.lang.Object[r12]     // Catch:{ all -> 0x0326 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x0326 }
            r15 = 0
            r12[r15] = r13     // Catch:{ all -> 0x0326 }
            int r13 = android.os.Process.myPid()     // Catch:{ all -> 0x0326 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x0326 }
            r12[r8] = r13     // Catch:{ all -> 0x0326 }
            int r13 = android.os.Process.myTid()     // Catch:{ all -> 0x0326 }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x0326 }
            r15 = 2
            r12[r15] = r13     // Catch:{ all -> 0x0326 }
            com.tencent.bugly.proguard.x.c(r3, r12)     // Catch:{ all -> 0x0326 }
            if (r11 == 0) goto L_0x0324
            r1 = 2
            if (r11 != r1) goto L_0x030b
            long r0 = r7.q     // Catch:{ all -> 0x044e }
            long r2 = r7.r     // Catch:{ all -> 0x044e }
            long r0 = r0 + r2
            r17 = 0
            int r0 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r0 <= 0) goto L_0x02c8
            com.tencent.bugly.proguard.u r0 = r7.i     // Catch:{ all -> 0x044e }
            boolean r1 = r7.t     // Catch:{ all -> 0x044e }
            long r0 = r0.a((boolean) r1)     // Catch:{ all -> 0x044e }
            long r2 = r7.q     // Catch:{ all -> 0x044e }
            long r0 = r0 + r2
            long r2 = r7.r     // Catch:{ all -> 0x044e }
            long r0 = r0 + r2
            com.tencent.bugly.proguard.u r2 = r7.i     // Catch:{ all -> 0x044e }
            boolean r3 = r7.t     // Catch:{ all -> 0x044e }
            r2.a((long) r0, (boolean) r3)     // Catch:{ all -> 0x044e }
        L_0x02c8:
            com.tencent.bugly.proguard.u r0 = r7.i     // Catch:{ all -> 0x044e }
            r1 = 0
            r0.a((int) r11, (com.tencent.bugly.proguard.aq) r1)     // Catch:{ all -> 0x044e }
            java.lang.String r0 = "[Upload] Session ID is invalid, will try again immediately (pid=%d | tid=%d)."
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x044e }
            int r2 = android.os.Process.myPid()     // Catch:{ all -> 0x044e }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x044e }
            r3 = 0
            r1[r3] = r2     // Catch:{ all -> 0x044e }
            int r2 = android.os.Process.myTid()     // Catch:{ all -> 0x044e }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x044e }
            r1[r8] = r2     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.a(r0, r1)     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.u r9 = r7.i     // Catch:{ all -> 0x044e }
            int r10 = r7.j     // Catch:{ all -> 0x044e }
            int r11 = r7.d     // Catch:{ all -> 0x044e }
            byte[] r12 = r7.e     // Catch:{ all -> 0x044e }
            java.lang.String r13 = r7.m     // Catch:{ all -> 0x044e }
            java.lang.String r14 = r7.n     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.t r15 = r7.k     // Catch:{ all -> 0x044e }
            int r0 = r7.a     // Catch:{ all -> 0x044e }
            int r1 = r7.b     // Catch:{ all -> 0x044e }
            r18 = 1
            java.util.Map<java.lang.String, java.lang.String> r2 = r7.o     // Catch:{ all -> 0x044e }
            r16 = r0
            r17 = r1
            r19 = r2
            r9.a(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)     // Catch:{ all -> 0x044e }
            return
        L_0x030b:
            r2 = 0
            r3 = 0
            r4 = 1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x044e }
            java.lang.String r1 = "status of server is "
            r0.<init>(r1)     // Catch:{ all -> 0x044e }
            r0.append(r11)     // Catch:{ all -> 0x044e }
            java.lang.String r5 = r0.toString()     // Catch:{ all -> 0x044e }
            r1 = r21
            r6 = r11
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x0324:
            r12 = r11
            goto L_0x0359
        L_0x0326:
            r0 = move-exception
            r17 = 0
            r12 = r11
            goto L_0x032e
        L_0x032b:
            r0 = move-exception
            r17 = 0
        L_0x032e:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x044e }
            java.lang.String r3 = "[Upload] Failed to upload for format of status header is invalid: "
            r0.<init>(r3)     // Catch:{ all -> 0x044e }
            java.lang.String r3 = java.lang.Integer.toString(r12)     // Catch:{ all -> 0x044e }
            r0.append(r3)     // Catch:{ all -> 0x044e }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x044e }
            r3 = 2
            java.lang.Object[] r10 = new java.lang.Object[r3]     // Catch:{ all -> 0x044e }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x044e }
            r11 = 0
            r10[r11] = r3     // Catch:{ all -> 0x044e }
            r10[r8] = r0     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.e(r5, r10)     // Catch:{ all -> 0x044e }
            r0 = r4
            r10 = r8
            r13 = r14
            r4 = r17
            r3 = 0
            r11 = 2
            goto L_0x0114
        L_0x0359:
            java.lang.String r1 = "[Upload] Received %d bytes"
            java.lang.Object[] r2 = new java.lang.Object[r8]     // Catch:{ all -> 0x044e }
            int r3 = r0.length     // Catch:{ all -> 0x044e }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x044e }
            r4 = 0
            r2[r4] = r3     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r1, r2)     // Catch:{ all -> 0x044e }
            boolean r1 = r7.s     // Catch:{ all -> 0x044e }
            if (r1 == 0) goto L_0x03cd
            int r1 = r0.length     // Catch:{ all -> 0x044e }
            if (r1 != 0) goto L_0x03a6
            java.util.Set r0 = r10.entrySet()     // Catch:{ all -> 0x044e }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x044e }
        L_0x0377:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x044e }
            if (r1 == 0) goto L_0x0399
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x044e }
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ all -> 0x044e }
            java.lang.String r2 = "[Upload] HTTP headers from server: key = %s, value = %s"
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch:{ all -> 0x044e }
            java.lang.Object r3 = r1.getKey()     // Catch:{ all -> 0x044e }
            r5 = 0
            r4[r5] = r3     // Catch:{ all -> 0x044e }
            java.lang.Object r1 = r1.getValue()     // Catch:{ all -> 0x044e }
            r4[r8] = r1     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r2, r4)     // Catch:{ all -> 0x044e }
            goto L_0x0377
        L_0x0399:
            r2 = 0
            r3 = 0
            r4 = 1
            java.lang.String r5 = "response data from server is empty"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x03a6:
            com.tencent.bugly.proguard.u r1 = r7.i     // Catch:{ all -> 0x044e }
            byte[] r0 = r1.b((byte[]) r0)     // Catch:{ all -> 0x044e }
            if (r0 != 0) goto L_0x03ba
            r2 = 0
            r3 = 0
            r4 = 1
            java.lang.String r5 = "failed to decrypt response from server"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x03ba:
            r1 = 2
            byte[] r0 = com.tencent.bugly.proguard.z.b((byte[]) r0, (int) r1)     // Catch:{ all -> 0x044e }
            if (r0 != 0) goto L_0x03cd
            r2 = 0
            r3 = 0
            r4 = 1
            java.lang.String r5 = "failed unzip(Gzip) response from server"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x03cd:
            boolean r1 = r7.s     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.aq r2 = com.tencent.bugly.proguard.a.a((byte[]) r0, (boolean) r1)     // Catch:{ all -> 0x044e }
            if (r2 != 0) goto L_0x03e1
            r2 = 0
            r3 = 0
            r4 = 1
            java.lang.String r5 = "failed to decode response package"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x03e1:
            boolean r0 = r7.s     // Catch:{ all -> 0x044e }
            if (r0 == 0) goto L_0x03ea
            com.tencent.bugly.proguard.u r0 = r7.i     // Catch:{ all -> 0x044e }
            r0.a((int) r12, (com.tencent.bugly.proguard.aq) r2)     // Catch:{ all -> 0x044e }
        L_0x03ea:
            java.lang.String r0 = "[Upload] Response cmd is: %d, length of sBuffer is: %d"
            r1 = 2
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x044e }
            int r3 = r2.b     // Catch:{ all -> 0x044e }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x044e }
            r4 = 0
            r1[r4] = r3     // Catch:{ all -> 0x044e }
            byte[] r3 = r2.c     // Catch:{ all -> 0x044e }
            if (r3 != 0) goto L_0x03fe
            r3 = r4
            goto L_0x03ff
        L_0x03fe:
            int r3 = r3.length     // Catch:{ all -> 0x044e }
        L_0x03ff:
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x044e }
            r1[r8] = r3     // Catch:{ all -> 0x044e }
            com.tencent.bugly.proguard.x.c(r0, r1)     // Catch:{ all -> 0x044e }
            com.tencent.bugly.crashreport.common.info.a r0 = r7.f     // Catch:{ all -> 0x044e }
            com.tencent.bugly.crashreport.common.strategy.a r1 = r7.g     // Catch:{ all -> 0x044e }
            boolean r0 = a(r2, r0, r1)     // Catch:{ all -> 0x044e }
            if (r0 != 0) goto L_0x041d
            r3 = 0
            r4 = 2
            java.lang.String r5 = "failed to process response package"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x041d:
            r3 = 1
            r4 = 2
            java.lang.String r5 = "successfully uploaded"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x0429:
            r2 = 0
            r3 = 0
            java.lang.String r5 = "failed after many attempts"
            r6 = 0
            r1 = r21
            r4 = r10
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x0435:
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.String r5 = "illegal access error"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x0441:
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.String r5 = "request package is empty!"
            r6 = 0
            r1 = r21
            r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x044e }
            return
        L_0x044e:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.x.a(r0)
            if (r1 != 0) goto L_0x0458
            r0.printStackTrace()
        L_0x0458:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.v.run():void");
    }

    public final void a(long j2) {
        this.p++;
        this.q += j2;
    }

    public final void b(long j2) {
        this.r += j2;
    }

    private static String a(String str) {
        if (z.a(str)) {
            return str;
        }
        try {
            return String.format("%s?aid=%s", new Object[]{str, UUID.randomUUID().toString()});
        } catch (Throwable th) {
            x.a(th);
            return str;
        }
    }
}
