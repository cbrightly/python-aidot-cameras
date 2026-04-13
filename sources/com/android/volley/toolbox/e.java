package com.android.volley.toolbox;

import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.VisibleForTesting;
import com.android.volley.Header;
import com.android.volley.a;
import com.android.volley.n;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DiskBasedCache */
public class e implements com.android.volley.a {
    private final Map<String, a> a;
    private long b;
    private final c c;
    private final int d;

    /* compiled from: DiskBasedCache */
    public interface c {
        File get();
    }

    public e(c rootDirectorySupplier, int maxCacheSizeInBytes) {
        this.a = new LinkedHashMap(16, 0.75f, true);
        this.b = 0;
        this.c = rootDirectorySupplier;
        this.d = maxCacheSizeInBytes;
    }

    public e(c rootDirectorySupplier) {
        this(rootDirectorySupplier, 5242880);
    }

    public synchronized a.C0017a get(String key) {
        b cis;
        a entry = this.a.get(key);
        if (entry == null) {
            return null;
        }
        File file = e(key);
        try {
            cis = new b(new BufferedInputStream(c(file)), file.length());
            a entryOnDisk = a.b(cis);
            if (!TextUtils.equals(key, entryOnDisk.b)) {
                n.b("%s: key=%s, found=%s", file.getAbsolutePath(), key, entryOnDisk.b);
                p(key);
                cis.close();
                return null;
            }
            a.C0017a c2 = entry.c(q(cis, cis.a()));
            cis.close();
            return c2;
        } catch (IOException e) {
            n.b("%s: %s", file.getAbsolutePath(), e.toString());
            o(key);
            return null;
        } catch (Throwable th) {
            cis.close();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0023, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void initialize() {
        /*
            r10 = this;
            monitor-enter(r10)
            com.android.volley.toolbox.e$c r0 = r10.c     // Catch:{ all -> 0x0061 }
            java.io.File r0 = r0.get()     // Catch:{ all -> 0x0061 }
            boolean r1 = r0.exists()     // Catch:{ all -> 0x0061 }
            r2 = 0
            if (r1 != 0) goto L_0x0024
            boolean r1 = r0.mkdirs()     // Catch:{ all -> 0x0061 }
            if (r1 != 0) goto L_0x0022
            java.lang.String r1 = "Unable to create cache dir %s"
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0061 }
            java.lang.String r4 = r0.getAbsolutePath()     // Catch:{ all -> 0x0061 }
            r3[r2] = r4     // Catch:{ all -> 0x0061 }
            com.android.volley.n.c(r1, r3)     // Catch:{ all -> 0x0061 }
        L_0x0022:
            monitor-exit(r10)
            return
        L_0x0024:
            java.io.File[] r1 = r0.listFiles()     // Catch:{ all -> 0x0061 }
            if (r1 != 0) goto L_0x002c
            monitor-exit(r10)
            return
        L_0x002c:
            int r3 = r1.length     // Catch:{ all -> 0x0061 }
        L_0x002d:
            if (r2 >= r3) goto L_0x005f
            r4 = r1[r2]     // Catch:{ all -> 0x0061 }
            long r5 = r4.length()     // Catch:{ IOException -> 0x0058 }
            com.android.volley.toolbox.e$b r7 = new com.android.volley.toolbox.e$b     // Catch:{ IOException -> 0x0058 }
            java.io.BufferedInputStream r8 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0058 }
            java.io.InputStream r9 = r10.c(r4)     // Catch:{ IOException -> 0x0058 }
            r8.<init>(r9)     // Catch:{ IOException -> 0x0058 }
            r7.<init>(r8, r5)     // Catch:{ IOException -> 0x0058 }
            com.android.volley.toolbox.e$a r8 = com.android.volley.toolbox.e.a.b(r7)     // Catch:{ all -> 0x0053 }
            r8.a = r5     // Catch:{ all -> 0x0053 }
            java.lang.String r9 = r8.b     // Catch:{ all -> 0x0053 }
            r10.i(r9, r8)     // Catch:{ all -> 0x0053 }
            r7.close()     // Catch:{ IOException -> 0x0058 }
            goto L_0x005c
        L_0x0053:
            r8 = move-exception
            r7.close()     // Catch:{ IOException -> 0x0058 }
            throw r8     // Catch:{ IOException -> 0x0058 }
        L_0x0058:
            r5 = move-exception
            r4.delete()     // Catch:{ all -> 0x0061 }
        L_0x005c:
            int r2 = r2 + 1
            goto L_0x002d
        L_0x005f:
            monitor-exit(r10)
            return
        L_0x0061:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.e.initialize():void");
    }

    public synchronized void a(String key, boolean fullExpire) {
        a.C0017a entry = get(key);
        if (entry != null) {
            entry.f = 0;
            if (fullExpire) {
                entry.e = 0;
            }
            b(key, entry);
        }
    }

    public synchronized void b(String key, a.C0017a entry) {
        long j = this.b;
        byte[] bArr = entry.a;
        long length = j + ((long) bArr.length);
        int i = this.d;
        if (length <= ((long) i) || ((float) bArr.length) <= ((float) i) * 0.9f) {
            File file = e(key);
            try {
                BufferedOutputStream fos = new BufferedOutputStream(d(file));
                a e = new a(key, entry);
                if (e.d(fos)) {
                    fos.write(entry.a);
                    fos.close();
                    e.a = file.length();
                    i(key, e);
                    h();
                } else {
                    fos.close();
                    n.b("Failed to write header for %s", file.getAbsolutePath());
                    throw new IOException();
                }
            } catch (IOException e2) {
                if (!file.delete()) {
                    n.b("Could not clean up file %s", file.getAbsolutePath());
                }
                g();
            }
        }
    }

    public synchronized void o(String key) {
        boolean deleted = e(key).delete();
        p(key);
        if (!deleted) {
            n.b("Could not delete cache entry for key=%s, filename=%s", key, f(key));
        }
    }

    private String f(String key) {
        int firstHalfLength = key.length() / 2;
        String localFilename = String.valueOf(key.substring(0, firstHalfLength).hashCode());
        return localFilename + String.valueOf(key.substring(firstHalfLength).hashCode());
    }

    public File e(String key) {
        return new File(this.c.get(), f(key));
    }

    private void g() {
        if (!this.c.get().exists()) {
            n.b("Re-initializing cache after external clearing.", new Object[0]);
            this.a.clear();
            this.b = 0;
            initialize();
        }
    }

    private void h() {
        long before;
        if (this.b >= ((long) this.d)) {
            if (n.b) {
                n.e("Pruning old cache entries.", new Object[0]);
            }
            long before2 = this.b;
            int prunedFiles = 0;
            long startTime = SystemClock.elapsedRealtime();
            Iterator<Map.Entry<String, a>> it = this.a.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    before = before2;
                    break;
                }
                a e = (a) it.next().getValue();
                if (e(e.b).delete()) {
                    before = before2;
                    this.b -= e.a;
                } else {
                    before = before2;
                    String str = e.b;
                    n.b("Could not delete cache entry for key=%s, filename=%s", str, f(str));
                }
                it.remove();
                prunedFiles++;
                if (((float) this.b) < ((float) this.d) * 0.9f) {
                    break;
                }
                before2 = before;
            }
            if (n.b) {
                n.e("pruned %d files, %d bytes, %d ms", Integer.valueOf(prunedFiles), Long.valueOf(this.b - before), Long.valueOf(SystemClock.elapsedRealtime() - startTime));
            }
        }
    }

    private void i(String key, a entry) {
        if (!this.a.containsKey(key)) {
            this.b += entry.a;
        } else {
            this.b += entry.a - this.a.get(key).a;
        }
        this.a.put(key, entry);
    }

    private void p(String key) {
        a removed = this.a.remove(key);
        if (removed != null) {
            this.b -= removed.a;
        }
    }

    @VisibleForTesting
    static byte[] q(b cis, long length) {
        long maxLength = cis.a();
        if (length < 0 || length > maxLength || ((long) ((int) length)) != length) {
            throw new IOException("streamToBytes length=" + length + ", maxLength=" + maxLength);
        }
        byte[] bytes = new byte[((int) length)];
        new DataInputStream(cis).readFully(bytes);
        return bytes;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public InputStream c(File file) {
        return new FileInputStream(file);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public OutputStream d(File file) {
        return new FileOutputStream(file);
    }

    @VisibleForTesting
    /* compiled from: DiskBasedCache */
    public static class a {
        long a;
        final String b;
        final String c;
        final long d;
        final long e;
        final long f;
        final long g;
        final List<com.android.volley.e> h;

        private a(String key, String etag, long serverDate, long lastModified, long ttl, long softTtl, List<com.android.volley.e> allResponseHeaders) {
            this.b = key;
            this.c = "".equals(etag) ? null : etag;
            this.d = serverDate;
            this.e = lastModified;
            this.f = ttl;
            this.g = softTtl;
            this.h = allResponseHeaders;
        }

        a(String key, a.C0017a entry) {
            this(key, entry.b, entry.c, entry.d, entry.e, entry.f, a(entry));
        }

        private static List<com.android.volley.e> a(a.C0017a entry) {
            List<com.android.volley.e> list = entry.h;
            if (list != null) {
                return list;
            }
            return g.h(entry.g);
        }

        static a b(b is) {
            if (e.l(is) == 538247942) {
                return new a(e.n(is), e.n(is), e.m(is), e.m(is), e.m(is), e.m(is), e.k(is));
            }
            throw new IOException();
        }

        /* access modifiers changed from: package-private */
        public a.C0017a c(byte[] data) {
            a.C0017a e2 = new a.C0017a();
            e2.a = data;
            e2.b = this.c;
            e2.c = this.d;
            e2.d = this.e;
            e2.e = this.f;
            e2.f = this.g;
            e2.g = g.i(this.h);
            e2.h = Collections.unmodifiableList(this.h);
            return e2;
        }

        /* access modifiers changed from: package-private */
        public boolean d(OutputStream os) {
            try {
                e.s(os, 538247942);
                e.u(os, this.b);
                String str = this.c;
                if (str == null) {
                    str = "";
                }
                e.u(os, str);
                e.t(os, this.d);
                e.t(os, this.e);
                e.t(os, this.f);
                e.t(os, this.g);
                e.r(this.h, os);
                os.flush();
                return true;
            } catch (IOException e2) {
                n.b("%s", e2.toString());
                return false;
            }
        }
    }

    @VisibleForTesting
    /* compiled from: DiskBasedCache */
    public static class b extends FilterInputStream {
        private final long c;
        private long d;

        b(InputStream in, long length) {
            super(in);
            this.c = length;
        }

        public int read() {
            int result = super.read();
            if (result != -1) {
                this.d++;
            }
            return result;
        }

        public int read(byte[] buffer, int offset, int count) {
            int result = super.read(buffer, offset, count);
            if (result != -1) {
                this.d += (long) result;
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        public long a() {
            return this.c - this.d;
        }
    }

    private static int j(InputStream is) {
        int b2 = is.read();
        if (b2 != -1) {
            return b2;
        }
        throw new EOFException();
    }

    static void s(OutputStream os, int n) {
        os.write((n >> 0) & 255);
        os.write((n >> 8) & 255);
        os.write((n >> 16) & 255);
        os.write((n >> 24) & 255);
    }

    static int l(InputStream is) {
        return 0 | (j(is) << 0) | (j(is) << 8) | (j(is) << 16) | (j(is) << 24);
    }

    static void t(OutputStream os, long n) {
        os.write((byte) ((int) (n >>> 0)));
        os.write((byte) ((int) (n >>> 8)));
        os.write((byte) ((int) (n >>> 16)));
        os.write((byte) ((int) (n >>> 24)));
        os.write((byte) ((int) (n >>> 32)));
        os.write((byte) ((int) (n >>> 40)));
        os.write((byte) ((int) (n >>> 48)));
        os.write((byte) ((int) (n >>> 56)));
    }

    static long m(InputStream is) {
        return 0 | ((((long) j(is)) & 255) << 0) | ((((long) j(is)) & 255) << 8) | ((((long) j(is)) & 255) << 16) | ((((long) j(is)) & 255) << 24) | ((((long) j(is)) & 255) << 32) | ((((long) j(is)) & 255) << 40) | ((((long) j(is)) & 255) << 48) | ((((long) j(is)) & 255) << 56);
    }

    static void u(OutputStream os, String s) {
        byte[] b2 = s.getBytes("UTF-8");
        t(os, (long) b2.length);
        os.write(b2, 0, b2.length);
    }

    static String n(b cis) {
        return new String(q(cis, m(cis)), "UTF-8");
    }

    static void r(List<com.android.volley.e> headers, OutputStream os) {
        if (headers != null) {
            s(os, headers.size());
            for (com.android.volley.e header : headers) {
                u(os, header.a());
                u(os, header.b());
            }
            return;
        }
        s(os, 0);
    }

    static List<com.android.volley.e> k(b cis) {
        int size = l(cis);
        if (size >= 0) {
            List<Header> result = size == 0 ? Collections.emptyList() : new ArrayList<>();
            for (int i = 0; i < size; i++) {
                result.add(new com.android.volley.e(n(cis).intern(), n(cis).intern()));
            }
            return result;
        }
        throw new IOException("readHeaderList size=" + size);
    }
}
