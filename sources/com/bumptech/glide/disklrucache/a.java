package com.bumptech.glide.disklrucache;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: DiskLruCache */
public final class a implements Closeable {
    /* access modifiers changed from: private */
    public Writer a1;
    /* access modifiers changed from: private */
    public int a2;
    /* access modifiers changed from: private */
    public final File c;
    private final File d;
    private final File f;
    private long p0 = 0;
    private final LinkedHashMap<String, d> p1 = new LinkedHashMap<>(0, 0.75f, true);
    private long p2 = 0;
    final ThreadPoolExecutor p3 = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new b((C0021a) null));
    private final Callable<Void> p4 = new C0021a();
    private final File q;
    private final int x;
    private long y;
    /* access modifiers changed from: private */
    public final int z;

    /* renamed from: com.bumptech.glide.disklrucache.a$a  reason: collision with other inner class name */
    /* compiled from: DiskLruCache */
    public class C0021a implements Callable<Void> {
        C0021a() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
            return null;
         */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void call() {
            /*
                r4 = this;
                com.bumptech.glide.disklrucache.a r0 = com.bumptech.glide.disklrucache.a.this
                monitor-enter(r0)
                com.bumptech.glide.disklrucache.a r1 = com.bumptech.glide.disklrucache.a.this     // Catch:{ all -> 0x0028 }
                java.io.Writer r1 = r1.a1     // Catch:{ all -> 0x0028 }
                r2 = 0
                if (r1 != 0) goto L_0x000e
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                return r2
            L_0x000e:
                com.bumptech.glide.disklrucache.a r1 = com.bumptech.glide.disklrucache.a.this     // Catch:{ all -> 0x0028 }
                r1.P0()     // Catch:{ all -> 0x0028 }
                com.bumptech.glide.disklrucache.a r1 = com.bumptech.glide.disklrucache.a.this     // Catch:{ all -> 0x0028 }
                boolean r1 = r1.I()     // Catch:{ all -> 0x0028 }
                if (r1 == 0) goto L_0x0026
                com.bumptech.glide.disklrucache.a r1 = com.bumptech.glide.disklrucache.a.this     // Catch:{ all -> 0x0028 }
                r1.o0()     // Catch:{ all -> 0x0028 }
                com.bumptech.glide.disklrucache.a r1 = com.bumptech.glide.disklrucache.a.this     // Catch:{ all -> 0x0028 }
                r3 = 0
                int unused = r1.a2 = r3     // Catch:{ all -> 0x0028 }
            L_0x0026:
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                return r2
            L_0x0028:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0028 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.disklrucache.a.C0021a.call():java.lang.Void");
        }
    }

    private a(File directory, int appVersion, int valueCount, long maxSize) {
        File file = directory;
        this.c = file;
        this.x = appVersion;
        this.d = new File(file, okhttp3.internal.cache.d.c);
        this.f = new File(file, okhttp3.internal.cache.d.d);
        this.q = new File(file, okhttp3.internal.cache.d.f);
        this.z = valueCount;
        this.y = maxSize;
    }

    public static a J(File directory, int appVersion, int valueCount, long maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        } else if (valueCount > 0) {
            File backupFile = new File(directory, okhttp3.internal.cache.d.f);
            if (backupFile.exists()) {
                File journalFile = new File(directory, okhttp3.internal.cache.d.c);
                if (journalFile.exists()) {
                    backupFile.delete();
                } else {
                    F0(backupFile, journalFile, false);
                }
            }
            a aVar = new a(directory, appVersion, valueCount, maxSize);
            if (aVar.d.exists()) {
                try {
                    aVar.T();
                    aVar.P();
                    return aVar;
                } catch (IOException journalIsCorrupt) {
                    PrintStream printStream = System.out;
                    printStream.println("DiskLruCache " + directory + " is corrupt: " + journalIsCorrupt.getMessage() + ", removing");
                    aVar.t();
                }
            }
            directory.mkdirs();
            a cache = new a(directory, appVersion, valueCount, maxSize);
            cache.o0();
            return cache;
        } else {
            throw new IllegalArgumentException("valueCount <= 0");
        }
    }

    private void T() {
        int lineCount;
        b reader = new b(new FileInputStream(this.d), c.a);
        try {
            String magic = reader.i();
            String version = reader.i();
            String appVersionString = reader.i();
            String valueCountString = reader.i();
            String blank = reader.i();
            if (!okhttp3.internal.cache.d.q.equals(magic) || !"1".equals(version) || !Integer.toString(this.x).equals(appVersionString) || !Integer.toString(this.z).equals(valueCountString) || !"".equals(blank)) {
                throw new IOException("unexpected journal header: [" + magic + ", " + version + ", " + valueCountString + ", " + blank + "]");
            }
            lineCount = 0;
            while (true) {
                W(reader.i());
                lineCount++;
            }
        } catch (EOFException e2) {
            this.a2 = lineCount - this.p1.size();
            if (reader.g()) {
                o0();
            } else {
                this.a1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.d, true), c.a));
            }
            c.a(reader);
        } catch (Throwable th) {
            c.a(reader);
            throw th;
        }
    }

    private void W(String line) {
        String key;
        int firstSpace = line.indexOf(32);
        if (firstSpace != -1) {
            int keyBegin = firstSpace + 1;
            int secondSpace = line.indexOf(32, keyBegin);
            if (secondSpace == -1) {
                key = line.substring(keyBegin);
                if (firstSpace == okhttp3.internal.cache.d.p1.length() && line.startsWith(okhttp3.internal.cache.d.p1)) {
                    this.p1.remove(key);
                    return;
                }
            } else {
                key = line.substring(keyBegin, secondSpace);
            }
            d entry = this.p1.get(key);
            if (entry == null) {
                entry = new d(this, key, (C0021a) null);
                this.p1.put(key, entry);
            }
            if (secondSpace != -1 && firstSpace == okhttp3.internal.cache.d.p0.length() && line.startsWith(okhttp3.internal.cache.d.p0)) {
                String[] parts = line.substring(secondSpace + 1).split(" ");
                boolean unused = entry.e = true;
                c unused2 = entry.f = null;
                entry.n(parts);
            } else if (secondSpace == -1 && firstSpace == okhttp3.internal.cache.d.a1.length() && line.startsWith(okhttp3.internal.cache.d.a1)) {
                c unused3 = entry.f = new c(this, entry, (C0021a) null);
            } else if (secondSpace != -1 || firstSpace != okhttp3.internal.cache.d.a2.length() || !line.startsWith(okhttp3.internal.cache.d.a2)) {
                throw new IOException("unexpected journal line: " + line);
            }
        } else {
            throw new IOException("unexpected journal line: " + line);
        }
    }

    private void P() {
        u(this.f);
        Iterator<d> it = this.p1.values().iterator();
        while (it.hasNext()) {
            d entry = it.next();
            if (entry.f == null) {
                for (int t = 0; t < this.z; t++) {
                    this.p0 += entry.b[t];
                }
            } else {
                c unused = entry.f = null;
                for (int t2 = 0; t2 < this.z; t2++) {
                    u(entry.j(t2));
                    u(entry.k(t2));
                }
                it.remove();
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public synchronized void o0() {
        Writer writer = this.a1;
        if (writer != null) {
            r(writer);
        }
        Writer writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.f), c.a));
        try {
            writer2.write(okhttp3.internal.cache.d.q);
            writer2.write("\n");
            writer2.write("1");
            writer2.write("\n");
            writer2.write(Integer.toString(this.x));
            writer2.write("\n");
            writer2.write(Integer.toString(this.z));
            writer2.write("\n");
            writer2.write("\n");
            for (d entry : this.p1.values()) {
                if (entry.f != null) {
                    writer2.write("DIRTY " + entry.a + 10);
                } else {
                    writer2.write("CLEAN " + entry.a + entry.l() + 10);
                }
            }
            r(writer2);
            if (this.d.exists()) {
                F0(this.d, this.q, true);
            }
            F0(this.f, this.d, false);
            this.q.delete();
            this.a1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.d, true), c.a));
        } catch (Throwable th) {
            r(writer2);
            throw th;
        }
    }

    private static void u(File file) {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void F0(File from, File to, boolean deleteDestination) {
        if (deleteDestination) {
            u(to);
        }
        if (!from.renameTo(to)) {
            throw new IOException();
        }
    }

    public synchronized e E(String key) {
        o();
        d entry = this.p1.get(key);
        if (entry == null) {
            return null;
        }
        if (!entry.e) {
            return null;
        }
        for (File file : entry.c) {
            if (!file.exists()) {
                return null;
            }
        }
        this.a2++;
        this.a1.append(okhttp3.internal.cache.d.a2);
        this.a1.append(' ');
        this.a1.append(key);
        this.a1.append(10);
        if (I()) {
            this.p3.submit(this.p4);
        }
        return new e(this, key, entry.g, entry.c, entry.b, (C0021a) null);
    }

    public c v(String key) {
        return x(key, -1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001e, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.bumptech.glide.disklrucache.a.c x(java.lang.String r6, long r7) {
        /*
            r5 = this;
            monitor-enter(r5)
            r5.o()     // Catch:{ all -> 0x005e }
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.a$d> r0 = r5.p1     // Catch:{ all -> 0x005e }
            java.lang.Object r0 = r0.get(r6)     // Catch:{ all -> 0x005e }
            com.bumptech.glide.disklrucache.a$d r0 = (com.bumptech.glide.disklrucache.a.d) r0     // Catch:{ all -> 0x005e }
            r1 = -1
            int r1 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            r2 = 0
            if (r1 == 0) goto L_0x001f
            if (r0 == 0) goto L_0x001d
            long r3 = r0.g     // Catch:{ all -> 0x005e }
            int r1 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r1 == 0) goto L_0x001f
        L_0x001d:
            monitor-exit(r5)
            return r2
        L_0x001f:
            if (r0 != 0) goto L_0x002d
            com.bumptech.glide.disklrucache.a$d r1 = new com.bumptech.glide.disklrucache.a$d     // Catch:{ all -> 0x005e }
            r1.<init>(r5, r6, r2)     // Catch:{ all -> 0x005e }
            r0 = r1
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.a$d> r1 = r5.p1     // Catch:{ all -> 0x005e }
            r1.put(r6, r0)     // Catch:{ all -> 0x005e }
            goto L_0x0035
        L_0x002d:
            com.bumptech.glide.disklrucache.a$c r1 = r0.f     // Catch:{ all -> 0x005e }
            if (r1 == 0) goto L_0x0035
            monitor-exit(r5)
            return r2
        L_0x0035:
            com.bumptech.glide.disklrucache.a$c r1 = new com.bumptech.glide.disklrucache.a$c     // Catch:{ all -> 0x005e }
            r1.<init>(r5, r0, r2)     // Catch:{ all -> 0x005e }
            com.bumptech.glide.disklrucache.a.c unused = r0.f = r1     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.a1     // Catch:{ all -> 0x005e }
            java.lang.String r3 = "DIRTY"
            r2.append(r3)     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.a1     // Catch:{ all -> 0x005e }
            r3 = 32
            r2.append(r3)     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.a1     // Catch:{ all -> 0x005e }
            r2.append(r6)     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.a1     // Catch:{ all -> 0x005e }
            r3 = 10
            r2.append(r3)     // Catch:{ all -> 0x005e }
            java.io.Writer r2 = r5.a1     // Catch:{ all -> 0x005e }
            z(r2)     // Catch:{ all -> 0x005e }
            monitor-exit(r5)
            return r1
        L_0x005e:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.disklrucache.a.x(java.lang.String, long):com.bumptech.glide.disklrucache.a$c");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0108, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void s(com.bumptech.glide.disklrucache.a.c r11, boolean r12) {
        /*
            r10 = this;
            monitor-enter(r10)
            com.bumptech.glide.disklrucache.a$d r0 = r11.a     // Catch:{ all -> 0x010f }
            com.bumptech.glide.disklrucache.a$c r1 = r0.f     // Catch:{ all -> 0x010f }
            if (r1 != r11) goto L_0x0109
            if (r12 == 0) goto L_0x004c
            boolean r1 = r0.e     // Catch:{ all -> 0x010f }
            if (r1 != 0) goto L_0x004c
            r1 = 0
        L_0x0014:
            int r2 = r10.z     // Catch:{ all -> 0x010f }
            if (r1 >= r2) goto L_0x004c
            boolean[] r2 = r11.b     // Catch:{ all -> 0x010f }
            boolean r2 = r2[r1]     // Catch:{ all -> 0x010f }
            if (r2 == 0) goto L_0x0032
            java.io.File r2 = r0.k(r1)     // Catch:{ all -> 0x010f }
            boolean r2 = r2.exists()     // Catch:{ all -> 0x010f }
            if (r2 != 0) goto L_0x002f
            r11.a()     // Catch:{ all -> 0x010f }
            monitor-exit(r10)
            return
        L_0x002f:
            int r1 = r1 + 1
            goto L_0x0014
        L_0x0032:
            r11.a()     // Catch:{ all -> 0x010f }
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException     // Catch:{ all -> 0x010f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x010f }
            r3.<init>()     // Catch:{ all -> 0x010f }
            java.lang.String r4 = "Newly created entry didn't create value for index "
            r3.append(r4)     // Catch:{ all -> 0x010f }
            r3.append(r1)     // Catch:{ all -> 0x010f }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x010f }
            r2.<init>(r3)     // Catch:{ all -> 0x010f }
            throw r2     // Catch:{ all -> 0x010f }
        L_0x004c:
            r1 = 0
        L_0x004d:
            int r2 = r10.z     // Catch:{ all -> 0x010f }
            if (r1 >= r2) goto L_0x0082
            java.io.File r2 = r0.k(r1)     // Catch:{ all -> 0x010f }
            if (r12 == 0) goto L_0x007c
            boolean r3 = r2.exists()     // Catch:{ all -> 0x010f }
            if (r3 == 0) goto L_0x007f
            java.io.File r3 = r0.j(r1)     // Catch:{ all -> 0x010f }
            r2.renameTo(r3)     // Catch:{ all -> 0x010f }
            long[] r4 = r0.b     // Catch:{ all -> 0x010f }
            r5 = r4[r1]     // Catch:{ all -> 0x010f }
            r4 = r5
            long r6 = r3.length()     // Catch:{ all -> 0x010f }
            long[] r8 = r0.b     // Catch:{ all -> 0x010f }
            r8[r1] = r6     // Catch:{ all -> 0x010f }
            long r8 = r10.p0     // Catch:{ all -> 0x010f }
            long r8 = r8 - r4
            long r8 = r8 + r6
            r10.p0 = r8     // Catch:{ all -> 0x010f }
            goto L_0x007f
        L_0x007c:
            u(r2)     // Catch:{ all -> 0x010f }
        L_0x007f:
            int r1 = r1 + 1
            goto L_0x004d
        L_0x0082:
            int r1 = r10.a2     // Catch:{ all -> 0x010f }
            r2 = 1
            int r1 = r1 + r2
            r10.a2 = r1     // Catch:{ all -> 0x010f }
            r1 = 0
            com.bumptech.glide.disklrucache.a.c unused = r0.f = r1     // Catch:{ all -> 0x010f }
            boolean r1 = r0.e     // Catch:{ all -> 0x010f }
            r1 = r1 | r12
            r3 = 10
            r4 = 32
            if (r1 == 0) goto L_0x00ca
            boolean unused = r0.e = r2     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            java.lang.String r2 = "CLEAN"
            r1.append(r2)     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            r1.append(r4)     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x010f }
            r1.append(r2)     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            java.lang.String r2 = r0.l()     // Catch:{ all -> 0x010f }
            r1.append(r2)     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            r1.append(r3)     // Catch:{ all -> 0x010f }
            if (r12 == 0) goto L_0x00ed
            long r1 = r10.p2     // Catch:{ all -> 0x010f }
            r3 = 1
            long r3 = r3 + r1
            r10.p2 = r3     // Catch:{ all -> 0x010f }
            long unused = r0.g = r1     // Catch:{ all -> 0x010f }
            goto L_0x00ed
        L_0x00ca:
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.a$d> r1 = r10.p1     // Catch:{ all -> 0x010f }
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x010f }
            r1.remove(r2)     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            java.lang.String r2 = "REMOVE"
            r1.append(r2)     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            r1.append(r4)     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            java.lang.String r2 = r0.a     // Catch:{ all -> 0x010f }
            r1.append(r2)     // Catch:{ all -> 0x010f }
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            r1.append(r3)     // Catch:{ all -> 0x010f }
        L_0x00ed:
            java.io.Writer r1 = r10.a1     // Catch:{ all -> 0x010f }
            z(r1)     // Catch:{ all -> 0x010f }
            long r1 = r10.p0     // Catch:{ all -> 0x010f }
            long r3 = r10.y     // Catch:{ all -> 0x010f }
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x0100
            boolean r1 = r10.I()     // Catch:{ all -> 0x010f }
            if (r1 == 0) goto L_0x0107
        L_0x0100:
            java.util.concurrent.ThreadPoolExecutor r1 = r10.p3     // Catch:{ all -> 0x010f }
            java.util.concurrent.Callable<java.lang.Void> r2 = r10.p4     // Catch:{ all -> 0x010f }
            r1.submit(r2)     // Catch:{ all -> 0x010f }
        L_0x0107:
            monitor-exit(r10)
            return
        L_0x0109:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x010f }
            r1.<init>()     // Catch:{ all -> 0x010f }
            throw r1     // Catch:{ all -> 0x010f }
        L_0x010f:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.disklrucache.a.s(com.bumptech.glide.disklrucache.a$c, boolean):void");
    }

    /* access modifiers changed from: private */
    public boolean I() {
        int i = this.a2;
        return i >= 2000 && i >= this.p1.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008c, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean u0(java.lang.String r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r8.o()     // Catch:{ all -> 0x0090 }
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.a$d> r0 = r8.p1     // Catch:{ all -> 0x0090 }
            java.lang.Object r0 = r0.get(r9)     // Catch:{ all -> 0x0090 }
            com.bumptech.glide.disklrucache.a$d r0 = (com.bumptech.glide.disklrucache.a.d) r0     // Catch:{ all -> 0x0090 }
            if (r0 == 0) goto L_0x008d
            com.bumptech.glide.disklrucache.a$c r1 = r0.f     // Catch:{ all -> 0x0090 }
            if (r1 == 0) goto L_0x0016
            goto L_0x008d
        L_0x0016:
            r1 = 0
        L_0x0017:
            int r2 = r8.z     // Catch:{ all -> 0x0090 }
            if (r1 >= r2) goto L_0x0059
            java.io.File r2 = r0.j(r1)     // Catch:{ all -> 0x0090 }
            boolean r3 = r2.exists()     // Catch:{ all -> 0x0090 }
            if (r3 == 0) goto L_0x0043
            boolean r3 = r2.delete()     // Catch:{ all -> 0x0090 }
            if (r3 == 0) goto L_0x002c
            goto L_0x0043
        L_0x002c:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x0090 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0090 }
            r4.<init>()     // Catch:{ all -> 0x0090 }
            java.lang.String r5 = "failed to delete "
            r4.append(r5)     // Catch:{ all -> 0x0090 }
            r4.append(r2)     // Catch:{ all -> 0x0090 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0090 }
            r3.<init>(r4)     // Catch:{ all -> 0x0090 }
            throw r3     // Catch:{ all -> 0x0090 }
        L_0x0043:
            long r3 = r8.p0     // Catch:{ all -> 0x0090 }
            long[] r5 = r0.b     // Catch:{ all -> 0x0090 }
            r6 = r5[r1]     // Catch:{ all -> 0x0090 }
            long r3 = r3 - r6
            r8.p0 = r3     // Catch:{ all -> 0x0090 }
            long[] r3 = r0.b     // Catch:{ all -> 0x0090 }
            r4 = 0
            r3[r1] = r4     // Catch:{ all -> 0x0090 }
            int r1 = r1 + 1
            goto L_0x0017
        L_0x0059:
            int r1 = r8.a2     // Catch:{ all -> 0x0090 }
            r2 = 1
            int r1 = r1 + r2
            r8.a2 = r1     // Catch:{ all -> 0x0090 }
            java.io.Writer r1 = r8.a1     // Catch:{ all -> 0x0090 }
            java.lang.String r3 = "REMOVE"
            r1.append(r3)     // Catch:{ all -> 0x0090 }
            java.io.Writer r1 = r8.a1     // Catch:{ all -> 0x0090 }
            r3 = 32
            r1.append(r3)     // Catch:{ all -> 0x0090 }
            java.io.Writer r1 = r8.a1     // Catch:{ all -> 0x0090 }
            r1.append(r9)     // Catch:{ all -> 0x0090 }
            java.io.Writer r1 = r8.a1     // Catch:{ all -> 0x0090 }
            r3 = 10
            r1.append(r3)     // Catch:{ all -> 0x0090 }
            java.util.LinkedHashMap<java.lang.String, com.bumptech.glide.disklrucache.a$d> r1 = r8.p1     // Catch:{ all -> 0x0090 }
            r1.remove(r9)     // Catch:{ all -> 0x0090 }
            boolean r1 = r8.I()     // Catch:{ all -> 0x0090 }
            if (r1 == 0) goto L_0x008b
            java.util.concurrent.ThreadPoolExecutor r1 = r8.p3     // Catch:{ all -> 0x0090 }
            java.util.concurrent.Callable<java.lang.Void> r3 = r8.p4     // Catch:{ all -> 0x0090 }
            r1.submit(r3)     // Catch:{ all -> 0x0090 }
        L_0x008b:
            monitor-exit(r8)
            return r2
        L_0x008d:
            r1 = 0
            monitor-exit(r8)
            return r1
        L_0x0090:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.disklrucache.a.u0(java.lang.String):boolean");
    }

    private void o() {
        if (this.a1 == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    public synchronized void close() {
        if (this.a1 != null) {
            Iterator it = new ArrayList(this.p1.values()).iterator();
            while (it.hasNext()) {
                d entry = (d) it.next();
                if (entry.f != null) {
                    entry.f.a();
                }
            }
            P0();
            r(this.a1);
            this.a1 = null;
        }
    }

    /* access modifiers changed from: private */
    public void P0() {
        while (this.p0 > this.y) {
            u0(this.p1.entrySet().iterator().next().getKey());
        }
    }

    public void t() {
        close();
        c.b(this.c);
    }

    @TargetApi(26)
    private static void r(Writer writer) {
        if (Build.VERSION.SDK_INT < 26) {
            writer.close();
            return;
        }
        StrictMode.ThreadPolicy oldPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(oldPolicy).permitUnbufferedIo().build());
        try {
            writer.close();
        } finally {
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    @TargetApi(26)
    private static void z(Writer writer) {
        if (Build.VERSION.SDK_INT < 26) {
            writer.flush();
            return;
        }
        StrictMode.ThreadPolicy oldPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(oldPolicy).permitUnbufferedIo().build());
        try {
            writer.flush();
        } finally {
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    /* compiled from: DiskLruCache */
    public final class e {
        private final String a;
        private final long b;
        private final long[] c;
        private final File[] d;

        /* synthetic */ e(a x0, String x1, long x2, File[] x3, long[] x4, C0021a x5) {
            this(x1, x2, x3, x4);
        }

        private e(String key, long sequenceNumber, File[] files, long[] lengths) {
            this.a = key;
            this.b = sequenceNumber;
            this.d = files;
            this.c = lengths;
        }

        public File a(int index) {
            return this.d[index];
        }
    }

    /* compiled from: DiskLruCache */
    public final class c {
        /* access modifiers changed from: private */
        public final d a;
        /* access modifiers changed from: private */
        public final boolean[] b;
        private boolean c;

        /* synthetic */ c(a x0, d x1, C0021a x2) {
            this(x1);
        }

        private c(d entry) {
            this.a = entry;
            this.b = entry.e ? null : new boolean[a.this.z];
        }

        public File f(int index) {
            File dirtyFile;
            synchronized (a.this) {
                if (this.a.f == this) {
                    if (!this.a.e) {
                        this.b[index] = true;
                    }
                    dirtyFile = this.a.k(index);
                    a.this.c.mkdirs();
                } else {
                    throw new IllegalStateException();
                }
            }
            return dirtyFile;
        }

        public void e() {
            a.this.s(this, true);
            this.c = true;
        }

        public void a() {
            a.this.s(this, false);
        }

        public void b() {
            if (!this.c) {
                try {
                    a();
                } catch (IOException e) {
                }
            }
        }
    }

    /* compiled from: DiskLruCache */
    public final class d {
        /* access modifiers changed from: private */
        public final String a;
        /* access modifiers changed from: private */
        public final long[] b;
        File[] c;
        File[] d;
        /* access modifiers changed from: private */
        public boolean e;
        /* access modifiers changed from: private */
        public c f;
        /* access modifiers changed from: private */
        public long g;

        /* synthetic */ d(a x0, String x1, C0021a x2) {
            this(x1);
        }

        private d(String key) {
            this.a = key;
            this.b = new long[a.this.z];
            this.c = new File[a.this.z];
            this.d = new File[a.this.z];
            StringBuilder fileBuilder = new StringBuilder(key).append('.');
            int truncateTo = fileBuilder.length();
            for (int i = 0; i < a.this.z; i++) {
                fileBuilder.append(i);
                this.c[i] = new File(a.this.c, fileBuilder.toString());
                fileBuilder.append(".tmp");
                this.d[i] = new File(a.this.c, fileBuilder.toString());
                fileBuilder.setLength(truncateTo);
            }
        }

        public String l() {
            StringBuilder result = new StringBuilder();
            for (long size : this.b) {
                result.append(' ');
                result.append(size);
            }
            return result.toString();
        }

        /* access modifiers changed from: private */
        public void n(String[] strings) {
            if (strings.length == a.this.z) {
                int i = 0;
                while (i < strings.length) {
                    try {
                        this.b[i] = Long.parseLong(strings[i]);
                        i++;
                    } catch (NumberFormatException e2) {
                        throw m(strings);
                    }
                }
                return;
            }
            throw m(strings);
        }

        private IOException m(String[] strings) {
            throw new IOException("unexpected journal line: " + Arrays.toString(strings));
        }

        public File j(int i) {
            return this.c[i];
        }

        public File k(int i) {
            return this.d[i];
        }
    }

    /* compiled from: DiskLruCache */
    public static final class b implements ThreadFactory {
        private b() {
        }

        /* synthetic */ b(C0021a x0) {
            this();
        }

        public synchronized Thread newThread(Runnable runnable) {
            Thread result;
            result = new Thread(runnable, "glide-disk-lru-cache-thread");
            result.setPriority(1);
            return result;
        }
    }
}
