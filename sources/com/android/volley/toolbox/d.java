package com.android.volley.toolbox;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: ByteArrayPool */
public class d {
    protected static final Comparator<byte[]> a = new a();
    private final List<byte[]> b = new ArrayList();
    private final List<byte[]> c = new ArrayList(64);
    private int d = 0;
    private final int e;

    /* compiled from: ByteArrayPool */
    public class a implements Comparator<byte[]> {
        a() {
        }

        /* renamed from: a */
        public int compare(byte[] lhs, byte[] rhs) {
            return lhs.length - rhs.length;
        }
    }

    public d(int sizeLimit) {
        this.e = sizeLimit;
    }

    public synchronized byte[] a(int len) {
        for (int i = 0; i < this.c.size(); i++) {
            byte[] buf = this.c.get(i);
            if (buf.length >= len) {
                this.d -= buf.length;
                this.c.remove(i);
                this.b.remove(buf);
                return buf;
            }
        }
        return new byte[len];
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(byte[] r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L_0x002e
            int r0 = r4.length     // Catch:{ all -> 0x002b }
            int r1 = r3.e     // Catch:{ all -> 0x002b }
            if (r0 <= r1) goto L_0x0009
            goto L_0x002e
        L_0x0009:
            java.util.List<byte[]> r0 = r3.b     // Catch:{ all -> 0x002b }
            r0.add(r4)     // Catch:{ all -> 0x002b }
            java.util.List<byte[]> r0 = r3.c     // Catch:{ all -> 0x002b }
            java.util.Comparator<byte[]> r1 = a     // Catch:{ all -> 0x002b }
            int r0 = java.util.Collections.binarySearch(r0, r4, r1)     // Catch:{ all -> 0x002b }
            if (r0 >= 0) goto L_0x001b
            int r1 = -r0
            int r0 = r1 + -1
        L_0x001b:
            java.util.List<byte[]> r1 = r3.c     // Catch:{ all -> 0x002b }
            r1.add(r0, r4)     // Catch:{ all -> 0x002b }
            int r1 = r3.d     // Catch:{ all -> 0x002b }
            int r2 = r4.length     // Catch:{ all -> 0x002b }
            int r1 = r1 + r2
            r3.d = r1     // Catch:{ all -> 0x002b }
            r3.c()     // Catch:{ all -> 0x002b }
            monitor-exit(r3)
            return
        L_0x002b:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        L_0x002e:
            monitor-exit(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.d.b(byte[]):void");
    }

    private synchronized void c() {
        while (this.d > this.e) {
            byte[] buf = this.b.remove(0);
            this.c.remove(buf);
            this.d -= buf.length;
        }
    }
}
