package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import androidx.work.Data;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/* compiled from: BUGLY */
public final class y {
    public static boolean a = true;
    private static SimpleDateFormat b;
    private static int c = 5120;
    private static StringBuilder d;
    private static StringBuilder e;
    private static boolean f;
    private static a g;
    private static String h;
    private static String i;
    private static Context j;
    private static String k;
    private static boolean l;
    private static boolean m = false;
    private static ExecutorService n;
    private static int o;
    private static final Object p = new Object();

    static {
        b = null;
        try {
            b = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
        }
    }

    /* access modifiers changed from: private */
    public static boolean d(String str, String str2, String str3) {
        com.tencent.bugly.crashreport.a aVar;
        try {
            com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
            if (b2 == null || (aVar = b2.D) == null) {
                return false;
            }
            return aVar.appendLogToNative(str, str2, str3);
        } catch (Throwable th) {
            if (x.a(th)) {
                return false;
            }
            th.printStackTrace();
            return false;
        }
    }

    private static String b() {
        com.tencent.bugly.crashreport.a aVar;
        try {
            com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
            if (b2 == null || (aVar = b2.D) == null) {
                return null;
            }
            return aVar.getLogFromNative();
        } catch (Throwable th) {
            if (x.a(th)) {
                return null;
            }
            th.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0073, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(android.content.Context r3) {
        /*
            java.lang.Class<com.tencent.bugly.proguard.y> r0 = com.tencent.bugly.proguard.y.class
            monitor-enter(r0)
            boolean r1 = l     // Catch:{ all -> 0x0074 }
            if (r1 != 0) goto L_0x0072
            if (r3 == 0) goto L_0x0072
            boolean r1 = a     // Catch:{ all -> 0x0074 }
            if (r1 != 0) goto L_0x000e
            goto L_0x0072
        L_0x000e:
            java.util.concurrent.ExecutorService r1 = java.util.concurrent.Executors.newSingleThreadExecutor()     // Catch:{ all -> 0x006c }
            n = r1     // Catch:{ all -> 0x006c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x006c }
            r2 = 0
            r1.<init>(r2)     // Catch:{ all -> 0x006c }
            e = r1     // Catch:{ all -> 0x006c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x006c }
            r1.<init>(r2)     // Catch:{ all -> 0x006c }
            d = r1     // Catch:{ all -> 0x006c }
            j = r3     // Catch:{ all -> 0x006c }
            com.tencent.bugly.crashreport.common.info.a r3 = com.tencent.bugly.crashreport.common.info.a.a((android.content.Context) r3)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = r3.d     // Catch:{ all -> 0x006c }
            h = r1     // Catch:{ all -> 0x006c }
            r3.getClass()     // Catch:{ all -> 0x006c }
            java.lang.String r3 = ""
            i = r3     // Catch:{ all -> 0x006c }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x006c }
            r3.<init>()     // Catch:{ all -> 0x006c }
            android.content.Context r1 = j     // Catch:{ all -> 0x006c }
            java.io.File r1 = r1.getFilesDir()     // Catch:{ all -> 0x006c }
            java.lang.String r1 = r1.getPath()     // Catch:{ all -> 0x006c }
            r3.append(r1)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = "/buglylog_"
            r3.append(r1)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = h     // Catch:{ all -> 0x006c }
            r3.append(r1)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = "_"
            r3.append(r1)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = i     // Catch:{ all -> 0x006c }
            r3.append(r1)     // Catch:{ all -> 0x006c }
            java.lang.String r1 = ".txt"
            r3.append(r1)     // Catch:{ all -> 0x006c }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x006c }
            k = r3     // Catch:{ all -> 0x006c }
            int r3 = android.os.Process.myPid()     // Catch:{ all -> 0x006c }
            o = r3     // Catch:{ all -> 0x006c }
            goto L_0x006d
        L_0x006c:
            r3 = move-exception
        L_0x006d:
            r3 = 1
            l = r3     // Catch:{ all -> 0x0074 }
            monitor-exit(r0)
            return
        L_0x0072:
            monitor-exit(r0)
            return
        L_0x0074:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.y.a(android.content.Context):void");
    }

    public static void a(int i2) {
        synchronized (p) {
            c = i2;
            if (i2 < 0) {
                c = 0;
            } else if (i2 > 10240) {
                c = Data.MAX_DATA_BYTES;
            }
        }
    }

    public static void a(boolean z) {
        x.a("[LogUtil] Whether can record user log into native: " + z, new Object[0]);
        m = z;
    }

    public static void a(String str, String str2, Throwable th) {
        if (th != null) {
            String message = th.getMessage();
            if (message == null) {
                message = "";
            }
            a(str, str2, message + 10 + z.b(th));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(final java.lang.String r3, final java.lang.String r4, final java.lang.String r5) {
        /*
            java.lang.Class<com.tencent.bugly.proguard.y> r0 = com.tencent.bugly.proguard.y.class
            monitor-enter(r0)
            boolean r1 = l     // Catch:{ all -> 0x002f }
            if (r1 == 0) goto L_0x002d
            boolean r1 = a     // Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x000c
            goto L_0x002d
        L_0x000c:
            boolean r1 = m     // Catch:{ Exception -> 0x0027 }
            if (r1 == 0) goto L_0x001b
            java.util.concurrent.ExecutorService r1 = n     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.proguard.y$1 r2 = new com.tencent.bugly.proguard.y$1     // Catch:{ Exception -> 0x0027 }
            r2.<init>(r3, r4, r5)     // Catch:{ Exception -> 0x0027 }
            r1.execute(r2)     // Catch:{ Exception -> 0x0027 }
            goto L_0x002b
        L_0x001b:
            java.util.concurrent.ExecutorService r1 = n     // Catch:{ Exception -> 0x0027 }
            com.tencent.bugly.proguard.y$2 r2 = new com.tencent.bugly.proguard.y$2     // Catch:{ Exception -> 0x0027 }
            r2.<init>(r3, r4, r5)     // Catch:{ Exception -> 0x0027 }
            r1.execute(r2)     // Catch:{ Exception -> 0x0027 }
            monitor-exit(r0)
            return
        L_0x0027:
            r3 = move-exception
            com.tencent.bugly.proguard.x.b(r3)     // Catch:{ all -> 0x002f }
        L_0x002b:
            monitor-exit(r0)
            return
        L_0x002d:
            monitor-exit(r0)
            return
        L_0x002f:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.y.a(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    public static synchronized void e(String str, String str2, String str3) {
        synchronized (y.class) {
            long myTid = (long) Process.myTid();
            d.setLength(0);
            if (str3.length() > 30720) {
                str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
            }
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = b;
            String format = simpleDateFormat != null ? simpleDateFormat.format(date) : date.toString();
            StringBuilder sb = d;
            sb.append(format);
            sb.append(" ");
            sb.append(o);
            sb.append(" ");
            sb.append(myTid);
            sb.append(" ");
            sb.append(str);
            sb.append(" ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str3);
            sb.append("\u0001\r\n");
            String sb2 = d.toString();
            synchronized (p) {
                try {
                    e.append(sb2);
                    if (e.length() > c) {
                        if (!f) {
                            f = true;
                            a aVar = g;
                            if (aVar == null) {
                                g = new a(k);
                            } else if (aVar.b == null || g.b.length() + ((long) e.length()) > g.e) {
                                boolean unused = g.a();
                            }
                            if (g.a(e.toString())) {
                                e.setLength(0);
                                f = false;
                            }
                        }
                    }
                } catch (Throwable th) {
                }
            }
        }
    }

    public static byte[] a() {
        if (!a) {
            return null;
        }
        if (m) {
            x.a("[LogUtil] Get user log from native.", new Object[0]);
            String b2 = b();
            if (b2 != null) {
                x.a("[LogUtil] Got user log from native: %d bytes", Integer.valueOf(b2.length()));
                return z.a((File) null, b2, "BuglyNativeLog.txt");
            }
        }
        StringBuilder sb = new StringBuilder();
        synchronized (p) {
            a aVar = g;
            if (aVar != null && aVar.a && g.b != null && g.b.length() > 0) {
                sb.append(z.a(g.b, 30720, true));
            }
            StringBuilder sb2 = e;
            if (sb2 != null && sb2.length() > 0) {
                sb.append(e.toString());
            }
        }
        return z.a((File) null, sb.toString(), "BuglyLog.txt");
    }

    /* compiled from: BUGLY */
    public static final class a {
        /* access modifiers changed from: private */
        public boolean a;
        /* access modifiers changed from: private */
        public File b;
        private String c;
        private long d;
        /* access modifiers changed from: private */
        public long e = 30720;

        public a(String str) {
            if (str != null && !str.equals("")) {
                this.c = str;
                this.a = a();
            }
        }

        /* access modifiers changed from: private */
        public boolean a() {
            try {
                File file = new File(this.c);
                this.b = file;
                if (file.exists() && !this.b.delete()) {
                    this.a = false;
                    return false;
                } else if (this.b.createNewFile()) {
                    return true;
                } else {
                    this.a = false;
                    return false;
                }
            } catch (Throwable th) {
                x.a(th);
                this.a = false;
                return false;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x0039 A[SYNTHETIC, Splitter:B:19:0x0039] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean a(java.lang.String r10) {
            /*
                r9 = this;
                boolean r0 = r9.a
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                r0 = 0
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ all -> 0x0031 }
                java.io.File r3 = r9.b     // Catch:{ all -> 0x0031 }
                r4 = 1
                r2.<init>(r3, r4)     // Catch:{ all -> 0x0031 }
                java.lang.String r0 = "UTF-8"
                byte[] r10 = r10.getBytes(r0)     // Catch:{ all -> 0x002e }
                r2.write(r10)     // Catch:{ all -> 0x002e }
                r2.flush()     // Catch:{ all -> 0x002e }
                r2.close()     // Catch:{ all -> 0x002e }
                long r5 = r9.d     // Catch:{ all -> 0x002e }
                int r10 = r10.length     // Catch:{ all -> 0x002e }
                long r7 = (long) r10     // Catch:{ all -> 0x002e }
                long r5 = r5 + r7
                r9.d = r5     // Catch:{ all -> 0x002e }
                r9.a = r4     // Catch:{ all -> 0x002e }
                r2.close()     // Catch:{ IOException -> 0x002b }
            L_0x002a:
                goto L_0x002d
            L_0x002b:
                r10 = move-exception
                goto L_0x002a
            L_0x002d:
                return r4
            L_0x002e:
                r10 = move-exception
                r0 = r2
                goto L_0x0032
            L_0x0031:
                r10 = move-exception
            L_0x0032:
                com.tencent.bugly.proguard.x.a(r10)     // Catch:{ all -> 0x003f }
                r9.a = r1     // Catch:{ all -> 0x003f }
                if (r0 == 0) goto L_0x003e
                r0.close()     // Catch:{ IOException -> 0x003d }
                goto L_0x003e
            L_0x003d:
                r10 = move-exception
            L_0x003e:
                return r1
            L_0x003f:
                r10 = move-exception
                if (r0 == 0) goto L_0x0047
                r0.close()     // Catch:{ IOException -> 0x0046 }
                goto L_0x0047
            L_0x0046:
                r0 = move-exception
            L_0x0047:
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.y.a.a(java.lang.String):boolean");
        }
    }
}
