package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import android.util.Base64;
import androidx.work.WorkRequest;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.google.maps.android.BuildConfig;
import com.tencent.bugly.b;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* compiled from: BUGLY */
public final class u {
    private static u b = null;
    public boolean a = true;
    private final p c;
    private final Context d;
    private Map<Integer, Long> e = new HashMap();
    private long f;
    private long g;
    private LinkedBlockingQueue<Runnable> h = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Runnable> i = new LinkedBlockingQueue<>();
    /* access modifiers changed from: private */
    public final Object j = new Object();
    private String k = null;
    private byte[] l = null;
    private long m = 0;
    /* access modifiers changed from: private */
    public byte[] n = null;
    private long o = 0;
    /* access modifiers changed from: private */
    public String p = null;
    private long q = 0;
    private final Object r = new Object();
    /* access modifiers changed from: private */
    public boolean s = false;
    /* access modifiers changed from: private */
    public final Object t = new Object();
    private int u = 0;

    static /* synthetic */ int b(u uVar) {
        int i2 = uVar.u - 1;
        uVar.u = i2;
        return i2;
    }

    private u(Context context) {
        this.d = context;
        this.c = p.a();
        try {
            Class.forName("android.util.Base64");
        } catch (ClassNotFoundException e2) {
            x.a("[UploadManager] Error: Can not find Base64 class, will not use stronger security way to upload", new Object[0]);
            this.a = false;
        }
        if (this.a) {
            this.k = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDP9x32s5pPtZBXzJBz2GWM/sbTvVO2+RvW0PH01IdaBxc/" + "fB6fbHZocC9T3nl1+J5eAFjIRVuV8vHDky7Qo82Mnh0PVvcZIEQvMMVKU8dsMQopxgsOs2gkSHJwgWdinKNS8CmWobo6pFwPUW11lMv714jAUZRq2GBOqiO2vQI6iwIDAQAB";
        }
    }

    public static synchronized u a(Context context) {
        u uVar;
        synchronized (u.class) {
            if (b == null) {
                b = new u(context);
            }
            uVar = b;
        }
        return uVar;
    }

    public static synchronized u a() {
        u uVar;
        synchronized (u.class) {
            uVar = b;
        }
        return uVar;
    }

    public final void a(int i2, ap apVar, String str, String str2, t tVar, long j2, boolean z) {
        try {
            a(new v(this.d, i2, apVar.g, a.a((Object) apVar), str, str2, tVar, this.a, z), true, true, j2);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void a(int i2, int i3, byte[] bArr, String str, String str2, t tVar, int i4, int i5, boolean z, Map<String, String> map) {
        try {
            a(new v(this.d, i2, i3, bArr, str, str2, tVar, this.a, i4, i5, false, map), z, false, 0);
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
    }

    public final void a(int i2, ap apVar, String str, String str2, t tVar, boolean z) {
        a(i2, apVar.g, a.a((Object) apVar), str, str2, tVar, 0, 0, z, (Map<String, String>) null);
    }

    public final long a(boolean z) {
        long j2;
        long b2 = z.b();
        int i2 = z ? 5 : 3;
        List<r> a2 = this.c.a(i2);
        if (a2 == null || a2.size() <= 0) {
            j2 = z ? this.g : this.f;
        } else {
            j2 = 0;
            try {
                r rVar = a2.get(0);
                if (rVar.e >= b2) {
                    j2 = z.c(rVar.g);
                    if (i2 == 3) {
                        this.f = j2;
                    } else {
                        this.g = j2;
                    }
                    a2.remove(rVar);
                }
            } catch (Throwable th) {
                x.a(th);
            }
            if (a2.size() > 0) {
                this.c.a(a2);
            }
        }
        x.c("[UploadManager] Local network consume: %d KB", Long.valueOf(j2 / 1024));
        return j2;
    }

    /* access modifiers changed from: protected */
    public final synchronized void a(long j2, boolean z) {
        int i2 = z ? 5 : 3;
        r rVar = new r();
        rVar.b = i2;
        rVar.e = z.b();
        rVar.c = "";
        rVar.d = "";
        rVar.g = z.c(j2);
        this.c.b(i2);
        this.c.a(rVar);
        if (z) {
            this.g = j2;
        } else {
            this.f = j2;
        }
        x.c("[UploadManager] Network total consume: %d KB", Long.valueOf(j2 / 1024));
    }

    public final synchronized void a(int i2, long j2) {
        if (i2 >= 0) {
            this.e.put(Integer.valueOf(i2), Long.valueOf(j2));
            r rVar = new r();
            rVar.b = i2;
            rVar.e = j2;
            rVar.c = "";
            rVar.d = "";
            rVar.g = new byte[0];
            this.c.b(i2);
            this.c.a(rVar);
            x.c("[UploadManager] Uploading(ID:%d) time: %s", Integer.valueOf(i2), z.a(j2));
            return;
        }
        x.e("[UploadManager] Unknown uploading ID: %d", Integer.valueOf(i2));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0069, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized long a(int r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            r0 = 0
            r2 = 0
            r3 = 1
            if (r7 < 0) goto L_0x005b
            java.util.Map<java.lang.Integer, java.lang.Long> r4 = r6.e     // Catch:{ all -> 0x006a }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x006a }
            java.lang.Object r4 = r4.get(r5)     // Catch:{ all -> 0x006a }
            java.lang.Long r4 = (java.lang.Long) r4     // Catch:{ all -> 0x006a }
            if (r4 == 0) goto L_0x001b
            long r0 = r4.longValue()     // Catch:{ all -> 0x006a }
            monitor-exit(r6)
            return r0
        L_0x001b:
            com.tencent.bugly.proguard.p r4 = r6.c     // Catch:{ all -> 0x006a }
            java.util.List r4 = r4.a((int) r7)     // Catch:{ all -> 0x006a }
            if (r4 == 0) goto L_0x005a
            int r5 = r4.size()     // Catch:{ all -> 0x006a }
            if (r5 <= 0) goto L_0x005a
            int r5 = r4.size()     // Catch:{ all -> 0x006a }
            if (r5 <= r3) goto L_0x004d
            java.util.Iterator r2 = r4.iterator()     // Catch:{ all -> 0x006a }
        L_0x0033:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x006a }
            if (r3 == 0) goto L_0x0047
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x006a }
            com.tencent.bugly.proguard.r r3 = (com.tencent.bugly.proguard.r) r3     // Catch:{ all -> 0x006a }
            long r3 = r3.e     // Catch:{ all -> 0x006a }
            int r5 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r5 <= 0) goto L_0x0046
            r0 = r3
        L_0x0046:
            goto L_0x0033
        L_0x0047:
            com.tencent.bugly.proguard.p r2 = r6.c     // Catch:{ all -> 0x006a }
            r2.b((int) r7)     // Catch:{ all -> 0x006a }
            goto L_0x0068
        L_0x004d:
            java.lang.Object r7 = r4.get(r2)     // Catch:{ all -> 0x0056 }
            com.tencent.bugly.proguard.r r7 = (com.tencent.bugly.proguard.r) r7     // Catch:{ all -> 0x0056 }
            long r0 = r7.e     // Catch:{ all -> 0x0056 }
            goto L_0x0068
        L_0x0056:
            r7 = move-exception
            com.tencent.bugly.proguard.x.a(r7)     // Catch:{ all -> 0x006a }
        L_0x005a:
            goto L_0x0068
        L_0x005b:
            java.lang.String r4 = "[UploadManager] Unknown upload ID: %d"
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x006a }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x006a }
            r3[r2] = r7     // Catch:{ all -> 0x006a }
            com.tencent.bugly.proguard.x.e(r4, r3)     // Catch:{ all -> 0x006a }
        L_0x0068:
            monitor-exit(r6)
            return r0
        L_0x006a:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.u.a(int):long");
    }

    public final boolean b(int i2) {
        if (b.c) {
            x.c("Uploading frequency will not be checked if SDK is in debug mode.", new Object[0]);
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis() - a(i2);
        x.c("[UploadManager] Time interval is %d seconds since last uploading(ID: %d).", Long.valueOf(currentTimeMillis / 1000), Integer.valueOf(i2));
        if (currentTimeMillis >= WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS) {
            return true;
        }
        x.a("[UploadManager] Data only be uploaded once in %d seconds.", 30L);
        return false;
    }

    private static boolean c() {
        x.c("[UploadManager] Drop security info of database (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            p a2 = p.a();
            if (a2 != null) {
                return a2.a(555, "security_info", (o) null, true);
            }
            x.d("[UploadManager] Failed to get Database", new Object[0]);
            return false;
        } catch (Throwable th) {
            x.a(th);
            return false;
        }
    }

    private boolean d() {
        x.c("[UploadManager] Record security info to database (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            p a2 = p.a();
            if (a2 == null) {
                x.d("[UploadManager] Failed to get database", new Object[0]);
                return false;
            }
            StringBuilder sb = new StringBuilder();
            byte[] bArr = this.n;
            if (bArr != null) {
                sb.append(Base64.encodeToString(bArr, 0));
                sb.append("#");
                long j2 = this.o;
                if (j2 != 0) {
                    sb.append(Long.toString(j2));
                } else {
                    sb.append(BuildConfig.TRAVIS);
                }
                sb.append("#");
                String str = this.p;
                if (str != null) {
                    sb.append(str);
                } else {
                    sb.append(BuildConfig.TRAVIS);
                }
                sb.append("#");
                long j3 = this.q;
                if (j3 != 0) {
                    sb.append(Long.toString(j3));
                } else {
                    sb.append(BuildConfig.TRAVIS);
                }
                a2.a(555, "security_info", sb.toString().getBytes(), (o) null, true);
                return true;
            }
            x.c("[UploadManager] AES key is null, will not record", new Object[0]);
            return false;
        } catch (Throwable th) {
            x.a(th);
            c();
            return false;
        }
    }

    /* access modifiers changed from: private */
    public boolean e() {
        boolean z;
        x.c("[UploadManager] Load security info from database (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        try {
            p a2 = p.a();
            if (a2 == null) {
                x.d("[UploadManager] Failed to get database", new Object[0]);
                return false;
            }
            Map<String, byte[]> a3 = a2.a(555, (o) null, true);
            if (a3 != null && a3.containsKey("security_info")) {
                String str = new String(a3.get("security_info"));
                String[] split = str.split("#");
                if (split.length == 4) {
                    if (!split[0].isEmpty()) {
                        if (!split[0].equals(BuildConfig.TRAVIS)) {
                            this.n = Base64.decode(split[0], 0);
                        }
                    }
                    z = false;
                    if (!z && !split[1].isEmpty() && !split[1].equals(BuildConfig.TRAVIS)) {
                        try {
                            this.o = Long.parseLong(split[1]);
                        } catch (Throwable th) {
                            x.a(th);
                            z = true;
                        }
                    }
                    if (!z && !split[2].isEmpty() && !split[2].equals(BuildConfig.TRAVIS)) {
                        this.p = split[2];
                    }
                    if (!z && !split[3].isEmpty() && !split[3].equals(BuildConfig.TRAVIS)) {
                        try {
                            this.q = Long.parseLong(split[3]);
                        } catch (Throwable th2) {
                            x.a(th2);
                            z = true;
                        }
                    }
                } else {
                    x.a("SecurityInfo = %s, Strings.length = %d", str, Integer.valueOf(split.length));
                    z = true;
                }
                if (z) {
                    c();
                }
            }
            return true;
        } catch (Throwable th3) {
            x.a(th3);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean b() {
        if (this.p == null || this.q == 0) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis() + this.m;
        long j2 = this.q;
        if (j2 >= currentTimeMillis) {
            return true;
        }
        x.c("[UploadManager] Session ID expired time from server is: %d(%s), but now is: %d(%s)", Long.valueOf(j2), new Date(this.q).toString(), Long.valueOf(currentTimeMillis), new Date(currentTimeMillis).toString());
        return false;
    }

    /* access modifiers changed from: protected */
    public final void b(boolean z) {
        synchronized (this.r) {
            x.c("[UploadManager] Clear security context (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            this.n = null;
            this.p = null;
            this.q = 0;
        }
        if (z) {
            c();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00bb, code lost:
        if (r14 <= 0) goto L_0x00de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00bd, code lost:
        com.tencent.bugly.proguard.x.c("[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)", java.lang.Integer.valueOf(r14), java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00de, code lost:
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00df, code lost:
        if (r5 >= r14) goto L_0x012d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e1, code lost:
        r8 = (java.lang.Runnable) r2.poll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00e7, code lost:
        if (r8 == null) goto L_0x012d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00e9, code lost:
        r10 = r13.j;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00eb, code lost:
        monitor-enter(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00ee, code lost:
        if (r13.u < 2) goto L_0x00f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00f0, code lost:
        if (r1 == null) goto L_0x00f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00f2, code lost:
        r1.a(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00f5, code lost:
        monitor-exit(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00f7, code lost:
        monitor-exit(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00f8, code lost:
        com.tencent.bugly.proguard.x.a("[UploadManager] Create and start a new thread to execute a upload task: %s", "BUGLY_ASYNC_UPLOAD");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x010e, code lost:
        if (com.tencent.bugly.proguard.z.a((java.lang.Runnable) new com.tencent.bugly.proguard.u.AnonymousClass1(r13), "BUGLY_ASYNC_UPLOAD") == null) goto L_0x011d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0110, code lost:
        r8 = r13.j;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0112, code lost:
        monitor-enter(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
        r13.u++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0118, code lost:
        monitor-exit(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x011d, code lost:
        com.tencent.bugly.proguard.x.d("[UploadManager] Failed to start a thread to execute asynchronous upload task, will try again next time.", new java.lang.Object[0]);
        a(r8, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0127, code lost:
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x012d, code lost:
        if (r7 <= 0) goto L_0x0151;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x012f, code lost:
        com.tencent.bugly.proguard.x.c("[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)", java.lang.Integer.valueOf(r7), java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0151, code lost:
        if (r1 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0153, code lost:
        r1.a(new com.tencent.bugly.proguard.u.AnonymousClass2(r13));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006e A[Catch:{ all -> 0x0081 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0095 A[Catch:{ all -> 0x0081 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(int r14) {
        /*
            r13 = this;
            r0 = 0
            if (r14 >= 0) goto L_0x000b
            java.lang.String r14 = "[UploadManager] Number of task to execute should >= 0"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.a(r14, r0)
            return
        L_0x000b:
            com.tencent.bugly.proguard.w r1 = com.tencent.bugly.proguard.w.a()
            java.util.concurrent.LinkedBlockingQueue r2 = new java.util.concurrent.LinkedBlockingQueue
            r2.<init>()
            java.util.concurrent.LinkedBlockingQueue r3 = new java.util.concurrent.LinkedBlockingQueue
            r3.<init>()
            java.lang.Object r4 = r13.j
            monitor-enter(r4)
            java.lang.String r5 = "[UploadManager] Try to poll all upload task need and put them into temp queue (pid=%d | tid=%d)"
            r6 = 2
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ all -> 0x015c }
            int r8 = android.os.Process.myPid()     // Catch:{ all -> 0x015c }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x015c }
            r7[r0] = r8     // Catch:{ all -> 0x015c }
            int r8 = android.os.Process.myTid()     // Catch:{ all -> 0x015c }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x015c }
            r9 = 1
            r7[r9] = r8     // Catch:{ all -> 0x015c }
            com.tencent.bugly.proguard.x.c(r5, r7)     // Catch:{ all -> 0x015c }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r5 = r13.h     // Catch:{ all -> 0x015c }
            int r5 = r5.size()     // Catch:{ all -> 0x015c }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r7 = r13.i     // Catch:{ all -> 0x015c }
            int r7 = r7.size()     // Catch:{ all -> 0x015c }
            if (r5 != 0) goto L_0x0052
            if (r7 != 0) goto L_0x0052
            java.lang.String r14 = "[UploadManager] There is no upload task in queue."
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x015c }
            com.tencent.bugly.proguard.x.c(r14, r0)     // Catch:{ all -> 0x015c }
            monitor-exit(r4)     // Catch:{ all -> 0x015c }
            return
        L_0x0052:
            if (r14 == 0) goto L_0x0061
            if (r14 >= r5) goto L_0x0059
            r7 = r0
            goto L_0x0062
        L_0x0059:
            int r8 = r5 + r7
            if (r14 >= r8) goto L_0x0061
            int r7 = r14 - r5
            r14 = r5
            goto L_0x0062
        L_0x0061:
            r14 = r5
        L_0x0062:
            if (r1 == 0) goto L_0x006a
            boolean r5 = r1.c()     // Catch:{ all -> 0x015c }
            if (r5 != 0) goto L_0x006b
        L_0x006a:
            r7 = r0
        L_0x006b:
            r5 = r0
        L_0x006c:
            if (r5 >= r14) goto L_0x0092
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r8 = r13.h     // Catch:{ all -> 0x015c }
            java.lang.Object r8 = r8.peek()     // Catch:{ all -> 0x015c }
            java.lang.Runnable r8 = (java.lang.Runnable) r8     // Catch:{ all -> 0x015c }
            if (r8 == 0) goto L_0x0092
            r2.put(r8)     // Catch:{ all -> 0x0081 }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r8 = r13.h     // Catch:{ all -> 0x0081 }
            r8.poll()     // Catch:{ all -> 0x0081 }
            goto L_0x008f
        L_0x0081:
            r8 = move-exception
            java.lang.String r10 = "[UploadManager] Failed to add upload task to temp urgent queue: %s"
            java.lang.Object[] r11 = new java.lang.Object[r9]     // Catch:{ all -> 0x015c }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x015c }
            r11[r0] = r8     // Catch:{ all -> 0x015c }
            com.tencent.bugly.proguard.x.e(r10, r11)     // Catch:{ all -> 0x015c }
        L_0x008f:
            int r5 = r5 + 1
            goto L_0x006c
        L_0x0092:
            r5 = r0
        L_0x0093:
            if (r5 >= r7) goto L_0x00b9
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r8 = r13.i     // Catch:{ all -> 0x015c }
            java.lang.Object r8 = r8.peek()     // Catch:{ all -> 0x015c }
            java.lang.Runnable r8 = (java.lang.Runnable) r8     // Catch:{ all -> 0x015c }
            if (r8 == 0) goto L_0x00b9
            r3.put(r8)     // Catch:{ all -> 0x00a8 }
            java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> r8 = r13.i     // Catch:{ all -> 0x00a8 }
            r8.poll()     // Catch:{ all -> 0x00a8 }
            goto L_0x00b6
        L_0x00a8:
            r8 = move-exception
            java.lang.String r10 = "[UploadManager] Failed to add upload task to temp urgent queue: %s"
            java.lang.Object[] r11 = new java.lang.Object[r9]     // Catch:{ all -> 0x015c }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x015c }
            r11[r0] = r8     // Catch:{ all -> 0x015c }
            com.tencent.bugly.proguard.x.e(r10, r11)     // Catch:{ all -> 0x015c }
        L_0x00b6:
            int r5 = r5 + 1
            goto L_0x0093
        L_0x00b9:
            monitor-exit(r4)     // Catch:{ all -> 0x015c }
            r4 = 3
            if (r14 <= 0) goto L_0x00de
            java.lang.String r5 = "[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)"
            java.lang.Object[] r8 = new java.lang.Object[r4]
            java.lang.Integer r10 = java.lang.Integer.valueOf(r14)
            r8[r0] = r10
            int r10 = android.os.Process.myPid()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r8[r9] = r10
            int r10 = android.os.Process.myTid()
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r8[r6] = r10
            com.tencent.bugly.proguard.x.c(r5, r8)
        L_0x00de:
            r5 = r0
        L_0x00df:
            if (r5 >= r14) goto L_0x012d
            java.lang.Object r8 = r2.poll()
            java.lang.Runnable r8 = (java.lang.Runnable) r8
            if (r8 == 0) goto L_0x012d
            java.lang.Object r10 = r13.j
            monitor-enter(r10)
            int r11 = r13.u     // Catch:{ all -> 0x012a }
            if (r11 < r6) goto L_0x00f7
            if (r1 == 0) goto L_0x00f7
            r1.a(r8)     // Catch:{ all -> 0x012a }
            monitor-exit(r10)     // Catch:{ all -> 0x012a }
            goto L_0x0127
        L_0x00f7:
            monitor-exit(r10)
            java.lang.String r10 = "[UploadManager] Create and start a new thread to execute a upload task: %s"
            java.lang.Object[] r11 = new java.lang.Object[r9]
            java.lang.String r12 = "BUGLY_ASYNC_UPLOAD"
            r11[r0] = r12
            com.tencent.bugly.proguard.x.a(r10, r11)
            com.tencent.bugly.proguard.u$1 r10 = new com.tencent.bugly.proguard.u$1
            r10.<init>(r8)
            java.lang.String r11 = "BUGLY_ASYNC_UPLOAD"
            java.lang.Thread r10 = com.tencent.bugly.proguard.z.a((java.lang.Runnable) r10, (java.lang.String) r11)
            if (r10 == 0) goto L_0x011d
            java.lang.Object r8 = r13.j
            monitor-enter(r8)
            int r10 = r13.u     // Catch:{ all -> 0x011a }
            int r10 = r10 + r9
            r13.u = r10     // Catch:{ all -> 0x011a }
            monitor-exit(r8)     // Catch:{ all -> 0x011a }
            goto L_0x0127
        L_0x011a:
            r14 = move-exception
            monitor-exit(r8)
            throw r14
        L_0x011d:
            java.lang.String r10 = "[UploadManager] Failed to start a thread to execute asynchronous upload task, will try again next time."
            java.lang.Object[] r11 = new java.lang.Object[r0]
            com.tencent.bugly.proguard.x.d(r10, r11)
            r13.a((java.lang.Runnable) r8, (boolean) r9)
        L_0x0127:
            int r5 = r5 + 1
            goto L_0x00df
        L_0x012a:
            r14 = move-exception
            monitor-exit(r10)
            throw r14
        L_0x012d:
            if (r7 <= 0) goto L_0x0150
            java.lang.String r14 = "[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)"
            java.lang.Object[] r2 = new java.lang.Object[r4]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r7)
            r2[r0] = r4
            int r0 = android.os.Process.myPid()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r2[r9] = r0
            int r0 = android.os.Process.myTid()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r2[r6] = r0
            com.tencent.bugly.proguard.x.c(r14, r2)
        L_0x0150:
            if (r1 == 0) goto L_0x015b
            com.tencent.bugly.proguard.u$2 r14 = new com.tencent.bugly.proguard.u$2
            r14.<init>(r13, r7, r3)
            r1.a(r14)
        L_0x015b:
            return
        L_0x015c:
            r14 = move-exception
            monitor-exit(r4)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.u.c(int):void");
    }

    private boolean a(Runnable runnable, boolean z) {
        if (runnable == null) {
            x.a("[UploadManager] Upload task should not be null", new Object[0]);
            return false;
        }
        try {
            x.c("[UploadManager] Add upload task to queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            synchronized (this.j) {
                if (z) {
                    this.h.put(runnable);
                } else {
                    this.i.put(runnable);
                }
            }
            return true;
        } catch (Throwable th) {
            x.e("[UploadManager] Failed to add upload task to queue: %s", th.getMessage());
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void a(Runnable runnable, long j2) {
        if (runnable == null) {
            x.d("[UploadManager] Upload task should not be null", new Object[0]);
            return;
        }
        x.c("[UploadManager] Execute synchronized upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        Thread a2 = z.a(runnable, "BUGLY_SYNC_UPLOAD");
        if (a2 == null) {
            x.e("[UploadManager] Failed to start a thread to execute synchronized upload task, add it to queue.", new Object[0]);
            a(runnable, true);
            return;
        }
        try {
            a2.join(j2);
        } catch (Throwable th) {
            x.e("[UploadManager] Failed to join upload synchronized task with message: %s. Add it to queue.", th.getMessage());
            a(runnable, true);
            c(0);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008c, code lost:
        com.tencent.bugly.proguard.x.c("[UploadManager] Initialize security context now (pid=%d | tid=%d)", java.lang.Integer.valueOf(android.os.Process.myPid()), java.lang.Integer.valueOf(android.os.Process.myTid()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a7, code lost:
        if (r14 == false) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a9, code lost:
        a((java.lang.Runnable) new com.tencent.bugly.proguard.u.a(r11, r7.d, r12, r15), 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b9, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ba, code lost:
        a(r12, r13);
        r0 = new com.tencent.bugly.proguard.u.a(r11, r7.d);
        com.tencent.bugly.proguard.x.a("[UploadManager] Create and start a new thread to execute a task of initializing security context: %s", "BUGLY_ASYNC_UPLOAD");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d5, code lost:
        if (com.tencent.bugly.proguard.z.a((java.lang.Runnable) r0, "BUGLY_ASYNC_UPLOAD") != null) goto L_0x00f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00d7, code lost:
        com.tencent.bugly.proguard.x.d("[UploadManager] Failed to start a thread to execute task of initializing security context, try to post it into thread pool.", new java.lang.Object[0]);
        r2 = com.tencent.bugly.proguard.w.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00e2, code lost:
        if (r2 == null) goto L_0x00e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00e4, code lost:
        r2.a(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00e7, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00e8, code lost:
        com.tencent.bugly.proguard.x.e("[UploadManager] Asynchronous thread pool is unavailable now, try next time.", new java.lang.Object[0]);
        r2 = r7.t;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00f1, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        r7.s = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00f4, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00f5, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00f9, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.lang.Runnable r12, boolean r13, boolean r14, long r15) {
        /*
            r11 = this;
            r7 = r11
            r0 = r12
            r1 = 0
            if (r0 != 0) goto L_0x000c
            java.lang.String r2 = "[UploadManager] Upload task should not be null"
            java.lang.Object[] r3 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.x.d(r2, r3)
        L_0x000c:
            java.lang.String r2 = "[UploadManager] Add upload task (pid=%d | tid=%d)"
            r3 = 2
            java.lang.Object[] r4 = new java.lang.Object[r3]
            int r5 = android.os.Process.myPid()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r1] = r5
            int r5 = android.os.Process.myTid()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r6 = 1
            r4[r6] = r5
            com.tencent.bugly.proguard.x.c(r2, r4)
            java.lang.String r2 = r7.p
            if (r2 == 0) goto L_0x007c
            boolean r2 = r11.b()
            if (r2 == 0) goto L_0x005c
            java.lang.String r2 = "[UploadManager] Sucessfully got session ID, try to execute upload task now (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            int r4 = android.os.Process.myPid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r1] = r4
            int r4 = android.os.Process.myTid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r6] = r4
            com.tencent.bugly.proguard.x.c(r2, r3)
            if (r14 == 0) goto L_0x0055
            r8 = r15
            r11.a((java.lang.Runnable) r12, (long) r8)
            return
        L_0x0055:
            r11.a((java.lang.Runnable) r12, (boolean) r13)
            r11.c((int) r1)
            return
        L_0x005c:
            r8 = r15
            java.lang.String r2 = "[UploadManager] Session ID is expired, drop it (pid=%d | tid=%d)"
            java.lang.Object[] r4 = new java.lang.Object[r3]
            int r5 = android.os.Process.myPid()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r1] = r5
            int r5 = android.os.Process.myTid()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r6] = r5
            com.tencent.bugly.proguard.x.a(r2, r4)
            r11.b((boolean) r1)
            goto L_0x007d
        L_0x007c:
            r8 = r15
        L_0x007d:
            java.lang.Object r2 = r7.t
            monitor-enter(r2)
            boolean r4 = r7.s     // Catch:{ all -> 0x00fa }
            if (r4 == 0) goto L_0x0089
            r11.a((java.lang.Runnable) r12, (boolean) r13)     // Catch:{ all -> 0x00fa }
            monitor-exit(r2)     // Catch:{ all -> 0x00fa }
            return
        L_0x0089:
            r7.s = r6     // Catch:{ all -> 0x00fa }
            monitor-exit(r2)     // Catch:{ all -> 0x00fa }
            java.lang.String r2 = "[UploadManager] Initialize security context now (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r3]
            int r4 = android.os.Process.myPid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r1] = r4
            int r4 = android.os.Process.myTid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r6] = r4
            com.tencent.bugly.proguard.x.c(r2, r3)
            if (r14 == 0) goto L_0x00ba
            com.tencent.bugly.proguard.u$a r10 = new com.tencent.bugly.proguard.u$a
            android.content.Context r3 = r7.d
            r1 = r10
            r2 = r11
            r4 = r12
            r5 = r15
            r1.<init>(r3, r4, r5)
            r0 = 0
            r11.a((java.lang.Runnable) r10, (long) r0)
            return
        L_0x00ba:
            r11.a((java.lang.Runnable) r12, (boolean) r13)
            com.tencent.bugly.proguard.u$a r0 = new com.tencent.bugly.proguard.u$a
            android.content.Context r2 = r7.d
            r0.<init>(r2)
            java.lang.String r2 = "[UploadManager] Create and start a new thread to execute a task of initializing security context: %s"
            java.lang.Object[] r3 = new java.lang.Object[r6]
            java.lang.String r4 = "BUGLY_ASYNC_UPLOAD"
            r3[r1] = r4
            com.tencent.bugly.proguard.x.a(r2, r3)
            java.lang.String r2 = "BUGLY_ASYNC_UPLOAD"
            java.lang.Thread r2 = com.tencent.bugly.proguard.z.a((java.lang.Runnable) r0, (java.lang.String) r2)
            if (r2 != 0) goto L_0x00f9
            java.lang.String r2 = "[UploadManager] Failed to start a thread to execute task of initializing security context, try to post it into thread pool."
            java.lang.Object[] r3 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.x.d(r2, r3)
            com.tencent.bugly.proguard.w r2 = com.tencent.bugly.proguard.w.a()
            if (r2 == 0) goto L_0x00e8
            r2.a(r0)
            return
        L_0x00e8:
            java.lang.String r0 = "[UploadManager] Asynchronous thread pool is unavailable now, try next time."
            java.lang.Object[] r2 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.x.e(r0, r2)
            java.lang.Object r2 = r7.t
            monitor-enter(r2)
            r7.s = r1     // Catch:{ all -> 0x00f6 }
            monitor-exit(r2)     // Catch:{ all -> 0x00f6 }
            return
        L_0x00f6:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        L_0x00f9:
            return
        L_0x00fa:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.u.a(java.lang.Runnable, boolean, boolean, long):void");
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processHandlersOutBlocks(RegionMaker.java:1008)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:978)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    public final void a(int r9, com.tencent.bugly.proguard.aq r10) {
        /*
            r8 = this;
            boolean r0 = r8.a
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 2
            r1 = 1
            r2 = 0
            if (r9 != r0) goto L_0x002a
            java.lang.String r9 = "[UploadManager] Session ID is invalid, will clear security context (pid=%d | tid=%d)"
            java.lang.Object[] r10 = new java.lang.Object[r0]
            int r0 = android.os.Process.myPid()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r10[r2] = r0
            int r0 = android.os.Process.myTid()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r10[r1] = r0
            com.tencent.bugly.proguard.x.c(r9, r10)
            r8.b((boolean) r1)
            goto L_0x0121
        L_0x002a:
            java.lang.Object r9 = r8.t
            monitor-enter(r9)
            boolean r3 = r8.s     // Catch:{ all -> 0x0137 }
            if (r3 != 0) goto L_0x0033
            monitor-exit(r9)     // Catch:{ all -> 0x0137 }
            return
        L_0x0033:
            monitor-exit(r9)
            if (r10 == 0) goto L_0x0103
            java.lang.String r9 = "[UploadManager] Record security context (pid=%d | tid=%d)"
            java.lang.Object[] r3 = new java.lang.Object[r0]
            int r4 = android.os.Process.myPid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r2] = r4
            int r4 = android.os.Process.myTid()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r1] = r4
            com.tencent.bugly.proguard.x.c(r9, r3)
            java.util.Map<java.lang.String, java.lang.String> r9 = r10.g     // Catch:{ all -> 0x00f9 }
            if (r9 == 0) goto L_0x00f8
            java.lang.String r3 = "S1"
            boolean r3 = r9.containsKey(r3)     // Catch:{ all -> 0x00f9 }
            if (r3 == 0) goto L_0x00f8
            java.lang.String r3 = "S2"
            boolean r3 = r9.containsKey(r3)     // Catch:{ all -> 0x00f9 }
            if (r3 == 0) goto L_0x00f8
            long r3 = r10.e     // Catch:{ all -> 0x00f9 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00f9 }
            long r3 = r3 - r5
            r8.m = r3     // Catch:{ all -> 0x00f9 }
            java.lang.String r10 = "[UploadManager] Time lag of server is: %d"
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ all -> 0x00f9 }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ all -> 0x00f9 }
            r5[r2] = r3     // Catch:{ all -> 0x00f9 }
            com.tencent.bugly.proguard.x.c(r10, r5)     // Catch:{ all -> 0x00f9 }
            java.lang.String r10 = "S1"
            java.lang.Object r10 = r9.get(r10)     // Catch:{ all -> 0x00f9 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x00f9 }
            r8.p = r10     // Catch:{ all -> 0x00f9 }
            java.lang.String r3 = "[UploadManager] Session ID from server is: %s"
            java.lang.Object[] r4 = new java.lang.Object[r1]     // Catch:{ all -> 0x00f9 }
            r4[r2] = r10     // Catch:{ all -> 0x00f9 }
            com.tencent.bugly.proguard.x.c(r3, r4)     // Catch:{ all -> 0x00f9 }
            java.lang.String r10 = r8.p     // Catch:{ all -> 0x00f9 }
            int r10 = r10.length()     // Catch:{ all -> 0x00f9 }
            if (r10 <= 0) goto L_0x00f1
            r3 = 259200000(0xf731400, double:1.280618154E-315)
            java.lang.String r10 = "S2"
            java.lang.Object r9 = r9.get(r10)     // Catch:{ NumberFormatException -> 0x00d4 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ NumberFormatException -> 0x00d4 }
            long r9 = java.lang.Long.parseLong(r9)     // Catch:{ NumberFormatException -> 0x00d4 }
            r8.q = r9     // Catch:{ NumberFormatException -> 0x00d4 }
            java.lang.String r5 = "[UploadManager] Session expired time from server is: %d(%s)"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ NumberFormatException -> 0x00d4 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ NumberFormatException -> 0x00d4 }
            r0[r2] = r9     // Catch:{ NumberFormatException -> 0x00d4 }
            java.util.Date r9 = new java.util.Date     // Catch:{ NumberFormatException -> 0x00d4 }
            long r6 = r8.q     // Catch:{ NumberFormatException -> 0x00d4 }
            r9.<init>(r6)     // Catch:{ NumberFormatException -> 0x00d4 }
            java.lang.String r9 = r9.toString()     // Catch:{ NumberFormatException -> 0x00d4 }
            r0[r1] = r9     // Catch:{ NumberFormatException -> 0x00d4 }
            com.tencent.bugly.proguard.x.c(r5, r0)     // Catch:{ NumberFormatException -> 0x00d4 }
            long r9 = r8.q     // Catch:{ NumberFormatException -> 0x00d4 }
            r5 = 1000(0x3e8, double:4.94E-321)
            int r9 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r9 >= 0) goto L_0x00d3
            java.lang.String r9 = "[UploadManager] Session expired time from server is less than 1 second, will set to default value"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ NumberFormatException -> 0x00d4 }
            com.tencent.bugly.proguard.x.d(r9, r10)     // Catch:{ NumberFormatException -> 0x00d4 }
            r8.q = r3     // Catch:{ NumberFormatException -> 0x00d4 }
        L_0x00d3:
            goto L_0x00de
        L_0x00d4:
            r9 = move-exception
            java.lang.String r9 = "[UploadManager] Session expired time is invalid, will set to default value"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ all -> 0x00f9 }
            com.tencent.bugly.proguard.x.d(r9, r10)     // Catch:{ all -> 0x00f9 }
            r8.q = r3     // Catch:{ all -> 0x00f9 }
        L_0x00de:
            boolean r9 = r8.d()     // Catch:{ all -> 0x00f9 }
            if (r9 == 0) goto L_0x00e6
            r1 = r2
            goto L_0x00ed
        L_0x00e6:
            java.lang.String r9 = "[UploadManager] Failed to record database"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ all -> 0x00f9 }
            com.tencent.bugly.proguard.x.c(r9, r10)     // Catch:{ all -> 0x00f9 }
        L_0x00ed:
            r8.c((int) r2)     // Catch:{ all -> 0x00f9 }
            goto L_0x00fd
        L_0x00f1:
            java.lang.String r9 = "[UploadManager] Session ID from server is invalid, try next time"
            java.lang.Object[] r10 = new java.lang.Object[r2]     // Catch:{ all -> 0x00f9 }
            com.tencent.bugly.proguard.x.c(r9, r10)     // Catch:{ all -> 0x00f9 }
        L_0x00f8:
            goto L_0x00fd
        L_0x00f9:
            r9 = move-exception
            com.tencent.bugly.proguard.x.a(r9)
        L_0x00fd:
            if (r1 == 0) goto L_0x0102
            r8.b((boolean) r2)
        L_0x0102:
            goto L_0x0121
        L_0x0103:
            java.lang.String r9 = "[UploadManager] Fail to init security context and clear local info (pid=%d | tid=%d)"
            java.lang.Object[] r10 = new java.lang.Object[r0]
            int r0 = android.os.Process.myPid()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r10[r2] = r0
            int r0 = android.os.Process.myTid()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r10[r1] = r0
            com.tencent.bugly.proguard.x.c(r9, r10)
            r8.b((boolean) r2)
        L_0x0121:
            java.lang.Object r9 = r8.t
            monitor-enter(r9)
            boolean r10 = r8.s     // Catch:{ all -> 0x0134 }
            if (r10 == 0) goto L_0x0132
            r8.s = r2     // Catch:{ all -> 0x0134 }
            android.content.Context r10 = r8.d     // Catch:{ all -> 0x0134 }
            java.lang.String r0 = "security_info"
            com.tencent.bugly.proguard.z.b((android.content.Context) r10, (java.lang.String) r0)     // Catch:{ all -> 0x0134 }
        L_0x0132:
            monitor-exit(r9)     // Catch:{ all -> 0x0134 }
            return
        L_0x0134:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        L_0x0137:
            r10 = move-exception
            monitor-exit(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.u.a(int, com.tencent.bugly.proguard.aq):void");
    }

    /* compiled from: BUGLY */
    public final class a implements Runnable {
        private final Context a;
        private final Runnable b;
        private final long c;

        public a(Context context) {
            this.a = context;
            this.b = null;
            this.c = 0;
        }

        public a(Context context, Runnable runnable, long j) {
            this.a = context;
            this.b = runnable;
            this.c = j;
        }

        public final void run() {
            if (!z.a(this.a, "security_info", (long) WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS)) {
                x.c("[UploadManager] Sleep %d try to lock security file again (pid=%d | tid=%d)", 5000, Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                z.b((long) KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
                if (z.a((Runnable) this, "BUGLY_ASYNC_UPLOAD") == null) {
                    x.d("[UploadManager] Failed to start a thread to execute task of initializing security context, try to post it into thread pool.", new Object[0]);
                    w a2 = w.a();
                    if (a2 != null) {
                        a2.a(this);
                    } else {
                        x.e("[UploadManager] Asynchronous thread pool is unavailable now, try next time.", new Object[0]);
                    }
                }
            } else {
                if (!u.this.e()) {
                    x.d("[UploadManager] Failed to load security info from database", new Object[0]);
                    u.this.b(false);
                }
                if (u.this.p != null) {
                    if (u.this.b()) {
                        x.c("[UploadManager] Sucessfully got session ID, try to execute upload tasks now (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                        Runnable runnable = this.b;
                        if (runnable != null) {
                            u.this.a(runnable, this.c);
                        }
                        u.this.c(0);
                        z.b(this.a, "security_info");
                        synchronized (u.this.t) {
                            boolean unused = u.this.s = false;
                        }
                        return;
                    }
                    x.a("[UploadManager] Session ID is expired, drop it.", new Object[0]);
                    u.this.b(true);
                }
                byte[] a3 = z.a(128);
                if (a3 == null || (a3.length << 3) != 128) {
                    x.d("[UploadManager] Failed to create AES key (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                    u.this.b(false);
                    z.b(this.a, "security_info");
                    synchronized (u.this.t) {
                        boolean unused2 = u.this.s = false;
                    }
                    return;
                }
                byte[] unused3 = u.this.n = a3;
                x.c("[UploadManager] Execute one upload task for requesting session ID (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
                Runnable runnable2 = this.b;
                if (runnable2 != null) {
                    u.this.a(runnable2, this.c);
                } else {
                    u.this.c(1);
                }
            }
        }
    }

    public final byte[] a(byte[] bArr) {
        byte[] bArr2 = this.n;
        if (bArr2 != null && (bArr2.length << 3) == 128) {
            return z.a(1, bArr, bArr2);
        }
        x.d("[UploadManager] AES key is invalid (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        return null;
    }

    public final byte[] b(byte[] bArr) {
        byte[] bArr2 = this.n;
        if (bArr2 != null && (bArr2.length << 3) == 128) {
            return z.a(2, bArr, bArr2);
        }
        x.d("[UploadManager] AES key is invalid (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        return null;
    }

    public final boolean a(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        x.c("[UploadManager] Integrate security to HTTP headers (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        String str = this.p;
        if (str != null) {
            map.put("secureSessionId", str);
            return true;
        }
        byte[] bArr = this.n;
        if (bArr == null || (bArr.length << 3) != 128) {
            x.d("[UploadManager] AES key is invalid", new Object[0]);
            return false;
        }
        if (this.l == null) {
            byte[] decode = Base64.decode(this.k, 0);
            this.l = decode;
            if (decode == null) {
                x.d("[UploadManager] Failed to decode RSA public key", new Object[0]);
                return false;
            }
        }
        byte[] b2 = z.b(1, this.n, this.l);
        if (b2 == null) {
            x.d("[UploadManager] Failed to encrypt AES key", new Object[0]);
            return false;
        }
        String encodeToString = Base64.encodeToString(b2, 0);
        if (encodeToString == null) {
            x.d("[UploadManager] Failed to encode AES key", new Object[0]);
            return false;
        }
        map.put("raKey", encodeToString);
        return true;
    }
}
