package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.common.info.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class n {
    public static final long a = System.currentTimeMillis();
    private static n b = null;
    private Context c;
    /* access modifiers changed from: private */
    public String d = a.b().d;
    /* access modifiers changed from: private */
    public Map<Integer, Map<String, m>> e = new HashMap();
    /* access modifiers changed from: private */
    public SharedPreferences f;

    private n(Context context) {
        this.c = context;
        this.f = context.getSharedPreferences("crashrecord", 0);
    }

    public static synchronized n a(Context context) {
        n nVar;
        synchronized (n.class) {
            if (b == null) {
                b = new n(context);
            }
            nVar = b;
        }
        return nVar;
    }

    public static synchronized n a() {
        n nVar;
        synchronized (n.class) {
            nVar = b;
        }
        return nVar;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0075, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean b(int r13) {
        /*
            r12 = this;
            monitor-enter(r12)
            r0 = 0
            java.util.List r1 = r12.c((int) r13)     // Catch:{ Exception -> 0x0080 }
            if (r1 != 0) goto L_0x000a
            monitor-exit(r12)
            return r0
        L_0x000a:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0080 }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x0080 }
            r4.<init>()     // Catch:{ Exception -> 0x0080 }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x0080 }
            r5.<init>()     // Catch:{ Exception -> 0x0080 }
            java.util.Iterator r6 = r1.iterator()     // Catch:{ Exception -> 0x0080 }
        L_0x001c:
            boolean r7 = r6.hasNext()     // Catch:{ Exception -> 0x0080 }
            r8 = 86400000(0x5265c00, double:4.2687272E-316)
            if (r7 == 0) goto L_0x0049
            java.lang.Object r7 = r6.next()     // Catch:{ Exception -> 0x0080 }
            com.tencent.bugly.proguard.m r7 = (com.tencent.bugly.proguard.m) r7     // Catch:{ Exception -> 0x0080 }
            java.lang.String r10 = r7.b     // Catch:{ Exception -> 0x0080 }
            if (r10 == 0) goto L_0x003e
            java.lang.String r11 = r12.d     // Catch:{ Exception -> 0x0080 }
            boolean r10 = r10.equalsIgnoreCase(r11)     // Catch:{ Exception -> 0x0080 }
            if (r10 == 0) goto L_0x003e
            int r10 = r7.d     // Catch:{ Exception -> 0x0080 }
            if (r10 <= 0) goto L_0x003e
            r4.add(r7)     // Catch:{ Exception -> 0x0080 }
        L_0x003e:
            long r10 = r7.c     // Catch:{ Exception -> 0x0080 }
            long r10 = r10 + r8
            int r8 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r8 >= 0) goto L_0x0048
            r5.add(r7)     // Catch:{ Exception -> 0x0080 }
        L_0x0048:
            goto L_0x001c
        L_0x0049:
            java.util.Collections.sort(r4)     // Catch:{ Exception -> 0x0080 }
            int r6 = r4.size()     // Catch:{ Exception -> 0x0080 }
            r7 = 2
            if (r6 < r7) goto L_0x0076
            int r5 = r4.size()     // Catch:{ Exception -> 0x0080 }
            r6 = 1
            if (r5 <= 0) goto L_0x0074
            int r5 = r4.size()     // Catch:{ Exception -> 0x0080 }
            int r5 = r5 - r6
            java.lang.Object r4 = r4.get(r5)     // Catch:{ Exception -> 0x0080 }
            com.tencent.bugly.proguard.m r4 = (com.tencent.bugly.proguard.m) r4     // Catch:{ Exception -> 0x0080 }
            long r4 = r4.c     // Catch:{ Exception -> 0x0080 }
            long r4 = r4 + r8
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 >= 0) goto L_0x0074
            r1.clear()     // Catch:{ Exception -> 0x0080 }
            r12.a((int) r13, r1)     // Catch:{ Exception -> 0x0080 }
            monitor-exit(r12)
            return r0
        L_0x0074:
            monitor-exit(r12)
            return r6
        L_0x0076:
            r1.removeAll(r5)     // Catch:{ Exception -> 0x0080 }
            r12.a((int) r13, r1)     // Catch:{ Exception -> 0x0080 }
            monitor-exit(r12)
            return r0
        L_0x007e:
            r13 = move-exception
            goto L_0x008a
        L_0x0080:
            r13 = move-exception
            java.lang.String r13 = "isFrequentCrash failed"
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x007e }
            com.tencent.bugly.proguard.x.e(r13, r1)     // Catch:{ all -> 0x007e }
            monitor-exit(r12)
            return r0
        L_0x008a:
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.n.b(int):boolean");
    }

    public final void a(int i, final int i2) {
        w.a().a(new Runnable(1004) {
            public final void run() {
                m mVar;
                String str;
                String str2;
                try {
                    if (!TextUtils.isEmpty(n.this.d)) {
                        List<m> a2 = n.this.c(1004);
                        if (a2 == null) {
                            a2 = new ArrayList<>();
                        }
                        if (n.this.e.get(Integer.valueOf(1004)) == null) {
                            n.this.e.put(Integer.valueOf(1004), new HashMap());
                        }
                        if (((Map) n.this.e.get(Integer.valueOf(1004))).get(n.this.d) == null) {
                            mVar = new m();
                            mVar.a = (long) 1004;
                            mVar.g = n.a;
                            mVar.b = n.this.d;
                            mVar.f = a.b().j;
                            a.b().getClass();
                            mVar.e = "3.1.0";
                            mVar.c = System.currentTimeMillis();
                            mVar.d = i2;
                            ((Map) n.this.e.get(Integer.valueOf(1004))).put(n.this.d, mVar);
                        } else {
                            mVar = (m) ((Map) n.this.e.get(Integer.valueOf(1004))).get(n.this.d);
                            mVar.d = i2;
                        }
                        ArrayList arrayList = new ArrayList();
                        boolean z = false;
                        for (m mVar2 : a2) {
                            if (mVar2.g == mVar.g && (str2 = mVar2.b) != null && str2.equalsIgnoreCase(mVar.b)) {
                                z = true;
                                mVar2.d = mVar.d;
                            }
                            String str3 = mVar2.e;
                            if ((str3 != null && !str3.equalsIgnoreCase(mVar.e)) || (((str = mVar2.f) != null && !str.equalsIgnoreCase(mVar.f)) || mVar2.d <= 0)) {
                                arrayList.add(mVar2);
                            }
                        }
                        a2.removeAll(arrayList);
                        if (!z) {
                            a2.add(mVar);
                        }
                        n.this.a(1004, a2);
                    }
                } catch (Exception e) {
                    x.e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v17, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v20, resolved type: java.io.ObjectInputStream} */
    /* JADX WARNING: type inference failed for: r6v6, types: [java.io.ObjectInputStream] */
    /* JADX WARNING: type inference failed for: r6v15 */
    /* JADX WARNING: type inference failed for: r6v18 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0058, code lost:
        if (r6 == null) goto L_0x005e;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004d A[Catch:{ IOException -> 0x004e, ClassNotFoundException -> 0x0042, all -> 0x003f, all -> 0x005f }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0062 A[Catch:{ Exception -> 0x0068 }] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <T extends java.util.List<?>> T c(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0068 }
            android.content.Context r3 = r5.c     // Catch:{ Exception -> 0x0068 }
            java.lang.String r4 = "crashrecord"
            java.io.File r3 = r3.getDir(r4, r1)     // Catch:{ Exception -> 0x0068 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0068 }
            r4.<init>()     // Catch:{ Exception -> 0x0068 }
            r4.append(r6)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r6 = r4.toString()     // Catch:{ Exception -> 0x0068 }
            r2.<init>(r3, r6)     // Catch:{ Exception -> 0x0068 }
            boolean r6 = r2.exists()     // Catch:{ Exception -> 0x0068 }
            if (r6 != 0) goto L_0x0024
            monitor-exit(r5)
            return r0
        L_0x0024:
            java.io.ObjectInputStream r6 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x004e, ClassNotFoundException -> 0x0042, all -> 0x003f }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x004e, ClassNotFoundException -> 0x0042, all -> 0x003f }
            r3.<init>(r2)     // Catch:{ IOException -> 0x004e, ClassNotFoundException -> 0x0042, all -> 0x003f }
            r6.<init>(r3)     // Catch:{ IOException -> 0x004e, ClassNotFoundException -> 0x0042, all -> 0x003f }
            java.lang.Object r2 = r6.readObject()     // Catch:{ IOException -> 0x003d, ClassNotFoundException -> 0x003b }
            java.util.List r2 = (java.util.List) r2     // Catch:{ IOException -> 0x003d, ClassNotFoundException -> 0x003b }
            r6.close()     // Catch:{ Exception -> 0x0068 }
            monitor-exit(r5)
            return r2
        L_0x003b:
            r2 = move-exception
            goto L_0x0044
        L_0x003d:
            r2 = move-exception
            goto L_0x0050
        L_0x003f:
            r2 = move-exception
            r6 = r0
            goto L_0x0060
        L_0x0042:
            r6 = move-exception
            r6 = r0
        L_0x0044:
            java.lang.String r2 = "get object error"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x005f }
            com.tencent.bugly.proguard.x.a(r2, r3)     // Catch:{ all -> 0x005f }
            if (r6 == 0) goto L_0x005e
            goto L_0x005a
        L_0x004e:
            r6 = move-exception
            r6 = r0
        L_0x0050:
            java.lang.String r2 = "open record file error"
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x005f }
            com.tencent.bugly.proguard.x.a(r2, r3)     // Catch:{ all -> 0x005f }
            if (r6 == 0) goto L_0x005e
        L_0x005a:
            r6.close()     // Catch:{ Exception -> 0x0068 }
            goto L_0x0071
        L_0x005e:
            goto L_0x0071
        L_0x005f:
            r2 = move-exception
        L_0x0060:
            if (r6 == 0) goto L_0x0065
            r6.close()     // Catch:{ Exception -> 0x0068 }
        L_0x0065:
            throw r2     // Catch:{ Exception -> 0x0068 }
        L_0x0066:
            r6 = move-exception
            goto L_0x0073
        L_0x0068:
            r6 = move-exception
            java.lang.String r6 = "readCrashRecord error"
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0066 }
            com.tencent.bugly.proguard.x.e(r6, r1)     // Catch:{ all -> 0x0066 }
        L_0x0071:
            monitor-exit(r5)
            return r0
        L_0x0073:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.n.c(int):java.util.List");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0047 A[SYNTHETIC, Splitter:B:22:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004b A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0050 A[SYNTHETIC, Splitter:B:28:0x0050] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <T extends java.util.List<?>> void a(int r5, T r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r6 != 0) goto L_0x0005
            monitor-exit(r4)
            return
        L_0x0005:
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0056 }
            android.content.Context r2 = r4.c     // Catch:{ Exception -> 0x0056 }
            java.lang.String r3 = "crashrecord"
            java.io.File r2 = r2.getDir(r3, r0)     // Catch:{ Exception -> 0x0056 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0056 }
            r3.<init>()     // Catch:{ Exception -> 0x0056 }
            r3.append(r5)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r5 = r3.toString()     // Catch:{ Exception -> 0x0056 }
            r1.<init>(r2, r5)     // Catch:{ Exception -> 0x0056 }
            r5 = 0
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0037, all -> 0x0033 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0037, all -> 0x0033 }
            r3.<init>(r1)     // Catch:{ IOException -> 0x0037, all -> 0x0033 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0037, all -> 0x0033 }
            r2.writeObject(r6)     // Catch:{ IOException -> 0x0031 }
            r2.close()     // Catch:{ Exception -> 0x0056 }
            goto L_0x005f
        L_0x0031:
            r5 = move-exception
            goto L_0x003a
        L_0x0033:
            r6 = move-exception
            r2 = r5
            r5 = r6
            goto L_0x004e
        L_0x0037:
            r6 = move-exception
            r2 = r5
            r5 = r6
        L_0x003a:
            r5.printStackTrace()     // Catch:{ all -> 0x004d }
            java.lang.String r5 = "open record file error"
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch:{ all -> 0x004d }
            com.tencent.bugly.proguard.x.a(r5, r6)     // Catch:{ all -> 0x004d }
            if (r2 == 0) goto L_0x004b
            r2.close()     // Catch:{ Exception -> 0x0056 }
            goto L_0x005f
        L_0x004b:
            monitor-exit(r4)
            return
        L_0x004d:
            r5 = move-exception
        L_0x004e:
            if (r2 == 0) goto L_0x0053
            r2.close()     // Catch:{ Exception -> 0x0056 }
        L_0x0053:
            throw r5     // Catch:{ Exception -> 0x0056 }
        L_0x0054:
            r5 = move-exception
            goto L_0x0061
        L_0x0056:
            r5 = move-exception
            java.lang.String r5 = "writeCrashRecord error"
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch:{ all -> 0x0054 }
            com.tencent.bugly.proguard.x.e(r5, r6)     // Catch:{ all -> 0x0054 }
        L_0x005f:
            monitor-exit(r4)
            return
        L_0x0061:
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.n.a(int, java.util.List):void");
    }

    public final synchronized boolean a(final int i) {
        boolean z;
        z = true;
        try {
            SharedPreferences sharedPreferences = this.f;
            z = sharedPreferences.getBoolean(i + "_" + this.d, true);
            w.a().a(new Runnable() {
                public final void run() {
                    boolean b2 = n.this.b(i);
                    SharedPreferences.Editor edit = n.this.f.edit();
                    edit.putBoolean(i + "_" + n.this.d, !b2).commit();
                }
            });
        } catch (Exception e2) {
            x.e("canInit error", new Object[0]);
            return z;
        }
        return z;
    }
}
