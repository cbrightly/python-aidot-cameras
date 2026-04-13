package com.squareup.okhttp.internal;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import okio.b0;
import okio.e0;
import okio.f0;
import okio.p;

/* compiled from: DiskLruCache */
public final class b implements Closeable {
    static final Pattern c = Pattern.compile("[a-z0-9_-]{1,120}");
    /* access modifiers changed from: private */
    public static final b0 d = new c();
    /* access modifiers changed from: private */
    public boolean A4;
    /* access modifiers changed from: private */
    public boolean B4;
    private long C4 = 0;
    private final Executor D4;
    private final Runnable E4 = new a();
    private long a1;
    private long a2 = 0;
    /* access modifiers changed from: private */
    public final com.squareup.okhttp.internal.io.a f;
    private final int p0;
    /* access modifiers changed from: private */
    public final int p1;
    private okio.d p2;
    private final LinkedHashMap<String, e> p3 = new LinkedHashMap<>(0, 0.75f, true);
    /* access modifiers changed from: private */
    public int p4;
    /* access modifiers changed from: private */
    public final File q;
    private final File x;
    private final File y;
    private final File z;
    /* access modifiers changed from: private */
    public boolean z4;

    static {
        Class<b> cls = b.class;
    }

    /* compiled from: DiskLruCache */
    public class a implements Runnable {
        a() {
        }

        public void run() {
            synchronized (b.this) {
                if (!(!b.this.A4) && !b.this.B4) {
                    try {
                        b.this.e1();
                        if (b.this.o0()) {
                            b.this.b1();
                            int unused = b.this.p4 = 0;
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    b(com.squareup.okhttp.internal.io.a fileSystem, File directory, int appVersion, int valueCount, long maxSize, Executor executor) {
        this.f = fileSystem;
        this.q = directory;
        this.p0 = appVersion;
        this.x = new File(directory, okhttp3.internal.cache.d.c);
        this.y = new File(directory, okhttp3.internal.cache.d.d);
        this.z = new File(directory, okhttp3.internal.cache.d.f);
        this.p1 = valueCount;
        this.a1 = maxSize;
        this.D4 = executor;
    }

    public synchronized void W() {
        if (!Thread.holdsLock(this)) {
            throw new AssertionError();
        } else if (!this.A4) {
            if (this.f.b(this.z)) {
                if (this.f.b(this.x)) {
                    this.f.h(this.z);
                } else {
                    this.f.g(this.z, this.x);
                }
            }
            if (this.f.b(this.x)) {
                try {
                    P0();
                    F0();
                    this.A4 = true;
                    return;
                } catch (IOException journalIsCorrupt) {
                    h f2 = h.f();
                    f2.i("DiskLruCache " + this.q + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing");
                    I();
                    this.B4 = false;
                }
            }
            b1();
            this.A4 = true;
        }
    }

    public static b E(com.squareup.okhttp.internal.io.a fileSystem, File directory, int appVersion, int valueCount, long maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (valueCount > 0) {
            return new b(fileSystem, directory, appVersion, valueCount, maxSize, new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), j.s("OkHttp DiskLruCache", true)));
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    private void P0() {
        int lineCount;
        okio.e source = p.d(this.f.e(this.x));
        try {
            String magic = source.d0();
            String version = source.d0();
            String appVersionString = source.d0();
            String valueCountString = source.d0();
            String blank = source.d0();
            if (!okhttp3.internal.cache.d.q.equals(magic) || !"1".equals(version) || !Integer.toString(this.p0).equals(appVersionString) || !Integer.toString(this.p1).equals(valueCountString) || !"".equals(blank)) {
                throw new IOException("unexpected journal header: [" + magic + ", " + version + ", " + valueCountString + ", " + blank + "]");
            }
            lineCount = 0;
            while (true) {
                a1(source.d0());
                lineCount++;
            }
        } catch (EOFException e2) {
            this.p4 = lineCount - this.p3.size();
            if (!source.r0()) {
                b1();
            } else {
                this.p2 = u0();
            }
            j.c(source);
        } catch (Throwable th) {
            j.c(source);
            throw th;
        }
    }

    /* renamed from: com.squareup.okhttp.internal.b$b  reason: collision with other inner class name */
    /* compiled from: DiskLruCache */
    public class C0208b extends c {
        static {
            Class<b> cls = b.class;
        }

        C0208b(b0 delegate) {
            super(delegate);
        }

        /* access modifiers changed from: protected */
        public void a(IOException e) {
            if (Thread.holdsLock(b.this)) {
                boolean unused = b.this.z4 = true;
                return;
            }
            throw new AssertionError();
        }
    }

    private okio.d u0() {
        return p.c(new C0208b(this.f.c(this.x)));
    }

    private void a1(String line) {
        String key;
        int firstSpace = line.indexOf(32);
        if (firstSpace != -1) {
            int keyBegin = firstSpace + 1;
            int secondSpace = line.indexOf(32, keyBegin);
            if (secondSpace == -1) {
                key = line.substring(keyBegin);
                if (firstSpace == okhttp3.internal.cache.d.p1.length() && line.startsWith(okhttp3.internal.cache.d.p1)) {
                    this.p3.remove(key);
                    return;
                }
            } else {
                key = line.substring(keyBegin, secondSpace);
            }
            e entry = this.p3.get(key);
            if (entry == null) {
                entry = new e(this, key, (a) null);
                this.p3.put(key, entry);
            }
            if (secondSpace != -1 && firstSpace == okhttp3.internal.cache.d.p0.length() && line.startsWith(okhttp3.internal.cache.d.p0)) {
                String[] parts = line.substring(secondSpace + 1).split(" ");
                boolean unused = entry.e = true;
                d unused2 = entry.f = null;
                entry.m(parts);
            } else if (secondSpace == -1 && firstSpace == okhttp3.internal.cache.d.a1.length() && line.startsWith(okhttp3.internal.cache.d.a1)) {
                d unused3 = entry.f = new d(this, entry, (a) null);
            } else if (secondSpace != -1 || firstSpace != okhttp3.internal.cache.d.a2.length() || !line.startsWith(okhttp3.internal.cache.d.a2)) {
                throw new IOException("unexpected journal line: " + line);
            }
        } else {
            throw new IOException("unexpected journal line: " + line);
        }
    }

    private void F0() {
        this.f.h(this.y);
        Iterator<e> it = this.p3.values().iterator();
        while (it.hasNext()) {
            e entry = it.next();
            if (entry.f == null) {
                for (int t = 0; t < this.p1; t++) {
                    this.a2 += entry.b[t];
                }
            } else {
                d unused = entry.f = null;
                for (int t2 = 0; t2 < this.p1; t2++) {
                    this.f.h(entry.c[t2]);
                    this.f.h(entry.d[t2]);
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public synchronized void b1() {
        okio.d dVar = this.p2;
        if (dVar != null) {
            dVar.close();
        }
        okio.d writer = p.c(this.f.f(this.y));
        try {
            writer.writeUtf8(okhttp3.internal.cache.d.q).writeByte(10);
            writer.writeUtf8("1").writeByte(10);
            writer.writeDecimalLong((long) this.p0).writeByte(10);
            writer.writeDecimalLong((long) this.p1).writeByte(10);
            writer.writeByte(10);
            for (e entry : this.p3.values()) {
                if (entry.f != null) {
                    writer.writeUtf8(okhttp3.internal.cache.d.a1).writeByte(32);
                    writer.writeUtf8(entry.a);
                    writer.writeByte(10);
                } else {
                    writer.writeUtf8(okhttp3.internal.cache.d.p0).writeByte(32);
                    writer.writeUtf8(entry.a);
                    entry.o(writer);
                    writer.writeByte(10);
                }
            }
            writer.close();
            if (this.f.b(this.x)) {
                this.f.g(this.x, this.z);
            }
            this.f.g(this.y, this.x);
            this.f.h(this.z);
            this.p2 = u0();
            this.z4 = false;
        } catch (Throwable th) {
            writer.close();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004f, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0051, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.squareup.okhttp.internal.b.f T(java.lang.String r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            r4.W()     // Catch:{ all -> 0x0052 }
            r4.x()     // Catch:{ all -> 0x0052 }
            r4.f1(r5)     // Catch:{ all -> 0x0052 }
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.b$e> r0 = r4.p3     // Catch:{ all -> 0x0052 }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x0052 }
            com.squareup.okhttp.internal.b$e r0 = (com.squareup.okhttp.internal.b.e) r0     // Catch:{ all -> 0x0052 }
            r1 = 0
            if (r0 == 0) goto L_0x0050
            boolean r2 = r0.e     // Catch:{ all -> 0x0052 }
            if (r2 != 0) goto L_0x001c
            goto L_0x0050
        L_0x001c:
            com.squareup.okhttp.internal.b$f r2 = r0.n()     // Catch:{ all -> 0x0052 }
            if (r2 != 0) goto L_0x0024
            monitor-exit(r4)
            return r1
        L_0x0024:
            int r1 = r4.p4     // Catch:{ all -> 0x0052 }
            int r1 = r1 + 1
            r4.p4 = r1     // Catch:{ all -> 0x0052 }
            okio.d r1 = r4.p2     // Catch:{ all -> 0x0052 }
            java.lang.String r3 = "READ"
            okio.d r1 = r1.writeUtf8(r3)     // Catch:{ all -> 0x0052 }
            r3 = 32
            okio.d r1 = r1.writeByte(r3)     // Catch:{ all -> 0x0052 }
            okio.d r1 = r1.writeUtf8(r5)     // Catch:{ all -> 0x0052 }
            r3 = 10
            r1.writeByte(r3)     // Catch:{ all -> 0x0052 }
            boolean r1 = r4.o0()     // Catch:{ all -> 0x0052 }
            if (r1 == 0) goto L_0x004e
            java.util.concurrent.Executor r1 = r4.D4     // Catch:{ all -> 0x0052 }
            java.lang.Runnable r3 = r4.E4     // Catch:{ all -> 0x0052 }
            r1.execute(r3)     // Catch:{ all -> 0x0052 }
        L_0x004e:
            monitor-exit(r4)
            return r2
        L_0x0050:
            monitor-exit(r4)
            return r1
        L_0x0052:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.b.T(java.lang.String):com.squareup.okhttp.internal.b$f");
    }

    public d J(String key) {
        return P(key, -1);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.squareup.okhttp.internal.b.d P(java.lang.String r6, long r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            r5.W()     // Catch:{ all -> 0x0068 }
            r5.x()     // Catch:{ all -> 0x0068 }
            r5.f1(r6)     // Catch:{ all -> 0x0068 }
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.b$e> r0 = r5.p3     // Catch:{ all -> 0x0068 }
            java.lang.Object r0 = r0.get(r6)     // Catch:{ all -> 0x0068 }
            com.squareup.okhttp.internal.b$e r0 = (com.squareup.okhttp.internal.b.e) r0     // Catch:{ all -> 0x0068 }
            r1 = -1
            int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            r2 = 0
            if (r1 == 0) goto L_0x0025
            if (r0 == 0) goto L_0x0023
            long r3 = r0.g     // Catch:{ all -> 0x0068 }
            int r1 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r1 == 0) goto L_0x0025
        L_0x0023:
            monitor-exit(r5)
            return r2
        L_0x0025:
            if (r0 == 0) goto L_0x002f
            com.squareup.okhttp.internal.b$d r1 = r0.f     // Catch:{ all -> 0x0068 }
            if (r1 == 0) goto L_0x002f
            monitor-exit(r5)
            return r2
        L_0x002f:
            okio.d r1 = r5.p2     // Catch:{ all -> 0x0068 }
            java.lang.String r3 = "DIRTY"
            okio.d r1 = r1.writeUtf8(r3)     // Catch:{ all -> 0x0068 }
            r3 = 32
            okio.d r1 = r1.writeByte(r3)     // Catch:{ all -> 0x0068 }
            okio.d r1 = r1.writeUtf8(r6)     // Catch:{ all -> 0x0068 }
            r3 = 10
            r1.writeByte(r3)     // Catch:{ all -> 0x0068 }
            okio.d r1 = r5.p2     // Catch:{ all -> 0x0068 }
            r1.flush()     // Catch:{ all -> 0x0068 }
            boolean r1 = r5.z4     // Catch:{ all -> 0x0068 }
            if (r1 == 0) goto L_0x0051
            monitor-exit(r5)
            return r2
        L_0x0051:
            if (r0 != 0) goto L_0x005e
            com.squareup.okhttp.internal.b$e r1 = new com.squareup.okhttp.internal.b$e     // Catch:{ all -> 0x0068 }
            r1.<init>(r5, r6, r2)     // Catch:{ all -> 0x0068 }
            r0 = r1
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.b$e> r1 = r5.p3     // Catch:{ all -> 0x0068 }
            r1.put(r6, r0)     // Catch:{ all -> 0x0068 }
        L_0x005e:
            com.squareup.okhttp.internal.b$d r1 = new com.squareup.okhttp.internal.b$d     // Catch:{ all -> 0x0068 }
            r1.<init>(r5, r0, r2)     // Catch:{ all -> 0x0068 }
            com.squareup.okhttp.internal.b.d unused = r0.f = r1     // Catch:{ all -> 0x0068 }
            monitor-exit(r5)
            return r1
        L_0x0068:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.b.P(java.lang.String, long):com.squareup.okhttp.internal.b$d");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0112, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void z(com.squareup.okhttp.internal.b.d r11, boolean r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            com.squareup.okhttp.internal.b$e r0 = r11.a     // Catch:{ all -> 0x0119 }
            com.squareup.okhttp.internal.b$d r1 = r0.f     // Catch:{ all -> 0x0119 }
            if (r1 != r11) goto L_0x0113
            if (r12 == 0) goto L_0x0050
            boolean r1 = r0.e     // Catch:{ all -> 0x0119 }
            if (r1 != 0) goto L_0x0050
            r1 = 0
        L_0x0014:
            int r2 = r10.p1     // Catch:{ all -> 0x0119 }
            if (r1 >= r2) goto L_0x0050
            boolean[] r2 = r11.b     // Catch:{ all -> 0x0119 }
            boolean r2 = r2[r1]     // Catch:{ all -> 0x0119 }
            if (r2 == 0) goto L_0x0036
            com.squareup.okhttp.internal.io.a r2 = r10.f     // Catch:{ all -> 0x0119 }
            java.io.File[] r3 = r0.d     // Catch:{ all -> 0x0119 }
            r3 = r3[r1]     // Catch:{ all -> 0x0119 }
            boolean r2 = r2.b(r3)     // Catch:{ all -> 0x0119 }
            if (r2 != 0) goto L_0x0033
            r11.a()     // Catch:{ all -> 0x0119 }
            monitor-exit(r10)
            return
        L_0x0033:
            int r1 = r1 + 1
            goto L_0x0014
        L_0x0036:
            r11.a()     // Catch:{ all -> 0x0119 }
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0119 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0119 }
            r3.<init>()     // Catch:{ all -> 0x0119 }
            java.lang.String r4 = "Newly created entry didn't create value for index "
            r3.append(r4)     // Catch:{ all -> 0x0119 }
            r3.append(r1)     // Catch:{ all -> 0x0119 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0119 }
            r2.<init>(r3)     // Catch:{ all -> 0x0119 }
            throw r2     // Catch:{ all -> 0x0119 }
        L_0x0050:
            r1 = 0
        L_0x0051:
            int r2 = r10.p1     // Catch:{ all -> 0x0119 }
            if (r1 >= r2) goto L_0x0092
            java.io.File[] r2 = r0.d     // Catch:{ all -> 0x0119 }
            r2 = r2[r1]     // Catch:{ all -> 0x0119 }
            if (r12 == 0) goto L_0x008a
            com.squareup.okhttp.internal.io.a r3 = r10.f     // Catch:{ all -> 0x0119 }
            boolean r3 = r3.b(r2)     // Catch:{ all -> 0x0119 }
            if (r3 == 0) goto L_0x008f
            java.io.File[] r3 = r0.c     // Catch:{ all -> 0x0119 }
            r3 = r3[r1]     // Catch:{ all -> 0x0119 }
            com.squareup.okhttp.internal.io.a r4 = r10.f     // Catch:{ all -> 0x0119 }
            r4.g(r2, r3)     // Catch:{ all -> 0x0119 }
            long[] r4 = r0.b     // Catch:{ all -> 0x0119 }
            r5 = r4[r1]     // Catch:{ all -> 0x0119 }
            r4 = r5
            com.squareup.okhttp.internal.io.a r6 = r10.f     // Catch:{ all -> 0x0119 }
            long r6 = r6.d(r3)     // Catch:{ all -> 0x0119 }
            long[] r8 = r0.b     // Catch:{ all -> 0x0119 }
            r8[r1] = r6     // Catch:{ all -> 0x0119 }
            long r8 = r10.a2     // Catch:{ all -> 0x0119 }
            long r8 = r8 - r4
            long r8 = r8 + r6
            r10.a2 = r8     // Catch:{ all -> 0x0119 }
            goto L_0x008f
        L_0x008a:
            com.squareup.okhttp.internal.io.a r3 = r10.f     // Catch:{ all -> 0x0119 }
            r3.h(r2)     // Catch:{ all -> 0x0119 }
        L_0x008f:
            int r1 = r1 + 1
            goto L_0x0051
        L_0x0092:
            int r1 = r10.p4     // Catch:{ all -> 0x0119 }
            r2 = 1
            int r1 = r1 + r2
            r10.p4 = r1     // Catch:{ all -> 0x0119 }
            r1 = 0
            com.squareup.okhttp.internal.b.d unused = r0.f = r1     // Catch:{ all -> 0x0119 }
            boolean r1 = r0.e     // Catch:{ all -> 0x0119 }
            r1 = r1 | r12
            r3 = 10
            r4 = 32
            if (r1 == 0) goto L_0x00d5
            boolean unused = r0.e = r2     // Catch:{ all -> 0x0119 }
            okio.d r1 = r10.p2     // Catch:{ all -> 0x0119 }
            java.lang.String r2 = "CLEAN"
            okio.d r1 = r1.writeUtf8(r2)     // Catch:{ all -> 0x0119 }
            r1.writeByte(r4)     // Catch:{ all -> 0x0119 }
            okio.d r1 = r10.p2     // Catch:{ all -> 0x0119 }
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x0119 }
            r1.writeUtf8(r2)     // Catch:{ all -> 0x0119 }
            okio.d r1 = r10.p2     // Catch:{ all -> 0x0119 }
            r0.o(r1)     // Catch:{ all -> 0x0119 }
            okio.d r1 = r10.p2     // Catch:{ all -> 0x0119 }
            r1.writeByte(r3)     // Catch:{ all -> 0x0119 }
            if (r12 == 0) goto L_0x00f7
            long r1 = r10.C4     // Catch:{ all -> 0x0119 }
            r3 = 1
            long r3 = r3 + r1
            r10.C4 = r3     // Catch:{ all -> 0x0119 }
            long unused = r0.g = r1     // Catch:{ all -> 0x0119 }
            goto L_0x00f7
        L_0x00d5:
            java.util.LinkedHashMap<java.lang.String, com.squareup.okhttp.internal.b$e> r1 = r10.p3     // Catch:{ all -> 0x0119 }
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x0119 }
            r1.remove(r2)     // Catch:{ all -> 0x0119 }
            okio.d r1 = r10.p2     // Catch:{ all -> 0x0119 }
            java.lang.String r2 = "REMOVE"
            okio.d r1 = r1.writeUtf8(r2)     // Catch:{ all -> 0x0119 }
            r1.writeByte(r4)     // Catch:{ all -> 0x0119 }
            okio.d r1 = r10.p2     // Catch:{ all -> 0x0119 }
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x0119 }
            r1.writeUtf8(r2)     // Catch:{ all -> 0x0119 }
            okio.d r1 = r10.p2     // Catch:{ all -> 0x0119 }
            r1.writeByte(r3)     // Catch:{ all -> 0x0119 }
        L_0x00f7:
            okio.d r1 = r10.p2     // Catch:{ all -> 0x0119 }
            r1.flush()     // Catch:{ all -> 0x0119 }
            long r1 = r10.a2     // Catch:{ all -> 0x0119 }
            long r3 = r10.a1     // Catch:{ all -> 0x0119 }
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x010a
            boolean r1 = r10.o0()     // Catch:{ all -> 0x0119 }
            if (r1 == 0) goto L_0x0111
        L_0x010a:
            java.util.concurrent.Executor r1 = r10.D4     // Catch:{ all -> 0x0119 }
            java.lang.Runnable r2 = r10.E4     // Catch:{ all -> 0x0119 }
            r1.execute(r2)     // Catch:{ all -> 0x0119 }
        L_0x0111:
            monitor-exit(r10)
            return
        L_0x0113:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0119 }
            r1.<init>()     // Catch:{ all -> 0x0119 }
            throw r1     // Catch:{ all -> 0x0119 }
        L_0x0119:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.b.z(com.squareup.okhttp.internal.b$d, boolean):void");
    }

    /* access modifiers changed from: private */
    public boolean o0() {
        int i = this.p4;
        return i >= 2000 && i >= this.p3.size();
    }

    public synchronized boolean c1(String key) {
        W();
        x();
        f1(key);
        e entry = this.p3.get(key);
        if (entry == null) {
            return false;
        }
        return d1(entry);
    }

    /* access modifiers changed from: private */
    public boolean d1(e entry) {
        if (entry.f != null) {
            boolean unused = entry.f.c = true;
        }
        for (int i = 0; i < this.p1; i++) {
            this.f.h(entry.c[i]);
            this.a2 -= entry.b[i];
            entry.b[i] = 0;
        }
        this.p4++;
        this.p2.writeUtf8(okhttp3.internal.cache.d.p1).writeByte(32).writeUtf8(entry.a).writeByte(10);
        this.p3.remove(entry.a);
        if (o0()) {
            this.D4.execute(this.E4);
        }
        return true;
    }

    public synchronized boolean isClosed() {
        return this.B4;
    }

    private synchronized void x() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void close() {
        if (this.A4) {
            if (!this.B4) {
                for (e entry : (e[]) this.p3.values().toArray(new e[this.p3.size()])) {
                    if (entry.f != null) {
                        entry.f.a();
                    }
                }
                e1();
                this.p2.close();
                this.p2 = null;
                this.B4 = true;
                return;
            }
        }
        this.B4 = true;
    }

    /* access modifiers changed from: private */
    public void e1() {
        while (this.a2 > this.a1) {
            d1(this.p3.values().iterator().next());
        }
    }

    public void I() {
        close();
        this.f.a(this.q);
    }

    private void f1(String key) {
        if (!c.matcher(key).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + key + "\"");
        }
    }

    /* compiled from: DiskLruCache */
    public final class f implements Closeable {
        private final String c;
        private final long d;
        private final e0[] f;
        private final long[] q;

        /* synthetic */ f(b x0, String x1, long x2, e0[] x3, long[] x4, a x5) {
            this(x1, x2, x3, x4);
        }

        private f(String key, long sequenceNumber, e0[] sources, long[] lengths) {
            this.c = key;
            this.d = sequenceNumber;
            this.f = sources;
            this.q = lengths;
        }

        public d a() {
            return b.this.P(this.c, this.d);
        }

        public e0 c(int index) {
            return this.f[index];
        }

        public void close() {
            for (e0 in : this.f) {
                j.c(in);
            }
        }
    }

    /* compiled from: DiskLruCache */
    public static final class c implements b0 {
        c() {
        }

        public void write(okio.c source, long byteCount) {
            source.skip(byteCount);
        }

        public void flush() {
        }

        public f0 timeout() {
            return f0.a;
        }

        public void close() {
        }
    }

    /* compiled from: DiskLruCache */
    public final class d {
        /* access modifiers changed from: private */
        public final e a;
        /* access modifiers changed from: private */
        public final boolean[] b;
        /* access modifiers changed from: private */
        public boolean c;
        private boolean d;

        /* synthetic */ d(b x0, e x1, a x2) {
            this(x1);
        }

        private d(e entry) {
            this.a = entry;
            this.b = entry.e ? null : new boolean[b.this.p1];
        }

        public b0 f(int index) {
            a aVar;
            synchronized (b.this) {
                if (this.a.f == this) {
                    if (!this.a.e) {
                        this.b[index] = true;
                    }
                    try {
                        aVar = new a(b.this.f.f(this.a.d[index]));
                    } catch (FileNotFoundException e2) {
                        return b.d;
                    }
                } else {
                    throw new IllegalStateException();
                }
            }
            return aVar;
        }

        /* compiled from: DiskLruCache */
        public class a extends c {
            a(b0 delegate) {
                super(delegate);
            }

            /* access modifiers changed from: protected */
            public void a(IOException e) {
                synchronized (b.this) {
                    boolean unused = d.this.c = true;
                }
            }
        }

        public void e() {
            synchronized (b.this) {
                if (this.c) {
                    b.this.z(this, false);
                    boolean unused = b.this.d1(this.a);
                } else {
                    b.this.z(this, true);
                }
                this.d = true;
            }
        }

        public void a() {
            synchronized (b.this) {
                b.this.z(this, false);
            }
        }
    }

    /* compiled from: DiskLruCache */
    public final class e {
        /* access modifiers changed from: private */
        public final String a;
        /* access modifiers changed from: private */
        public final long[] b;
        /* access modifiers changed from: private */
        public final File[] c;
        /* access modifiers changed from: private */
        public final File[] d;
        /* access modifiers changed from: private */
        public boolean e;
        /* access modifiers changed from: private */
        public d f;
        /* access modifiers changed from: private */
        public long g;

        /* synthetic */ e(b x0, String x1, a x2) {
            this(x1);
        }

        private e(String key) {
            this.a = key;
            this.b = new long[b.this.p1];
            this.c = new File[b.this.p1];
            this.d = new File[b.this.p1];
            StringBuilder fileBuilder = new StringBuilder(key).append('.');
            int truncateTo = fileBuilder.length();
            for (int i = 0; i < b.this.p1; i++) {
                fileBuilder.append(i);
                this.c[i] = new File(b.this.q, fileBuilder.toString());
                fileBuilder.append(".tmp");
                this.d[i] = new File(b.this.q, fileBuilder.toString());
                fileBuilder.setLength(truncateTo);
            }
        }

        /* access modifiers changed from: private */
        public void m(String[] strings) {
            if (strings.length == b.this.p1) {
                int i = 0;
                while (i < strings.length) {
                    try {
                        this.b[i] = Long.parseLong(strings[i]);
                        i++;
                    } catch (NumberFormatException e2) {
                        throw l(strings);
                    }
                }
                return;
            }
            throw l(strings);
        }

        /* access modifiers changed from: package-private */
        public void o(okio.d writer) {
            for (long length : this.b) {
                writer.writeByte(32).writeDecimalLong(length);
            }
        }

        private IOException l(String[] strings) {
            throw new IOException("unexpected journal line: " + Arrays.toString(strings));
        }

        /* access modifiers changed from: package-private */
        public f n() {
            if (Thread.holdsLock(b.this)) {
                e0[] sources = new e0[b.this.p1];
                long[] lengths = (long[]) this.b.clone();
                int i = 0;
                while (i < b.this.p1) {
                    try {
                        sources[i] = b.this.f.e(this.c[i]);
                        i++;
                    } catch (FileNotFoundException e2) {
                        int i2 = 0;
                        while (i2 < b.this.p1 && sources[i2] != null) {
                            j.c(sources[i2]);
                            i2++;
                        }
                        return null;
                    }
                }
                return new f(b.this, this.a, this.g, sources, lengths, (a) null);
            }
            throw new AssertionError();
        }
    }
}
