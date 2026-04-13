package org.apache.commons.io.output;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ByteArrayOutputStream */
public class a extends OutputStream {
    private static final byte[] c = new byte[0];
    private final List<byte[]> d = new ArrayList();
    private int f;
    private int q;
    private byte[] x;
    private int y;
    private boolean z = true;

    public a(int size) {
        if (size >= 0) {
            synchronized (this) {
                a(size);
            }
            return;
        }
        throw new IllegalArgumentException("Negative initial size: " + size);
    }

    private void a(int newcount) {
        int newBufferSize;
        if (this.f < this.d.size() - 1) {
            this.q += this.x.length;
            int i = this.f + 1;
            this.f = i;
            this.x = this.d.get(i);
            return;
        }
        byte[] bArr = this.x;
        if (bArr == null) {
            newBufferSize = newcount;
            this.q = 0;
        } else {
            newBufferSize = Math.max(bArr.length << 1, newcount - this.q);
            this.q += this.x.length;
        }
        this.f++;
        byte[] bArr2 = new byte[newBufferSize];
        this.x = bArr2;
        this.d.add(bArr2);
    }

    public void write(byte[] b, int off, int len) {
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len != 0) {
            synchronized (this) {
                int i = this.y;
                int newcount = i + len;
                int remaining = len;
                int inBufferPos = i - this.q;
                while (remaining > 0) {
                    int part = Math.min(remaining, this.x.length - inBufferPos);
                    System.arraycopy(b, (off + len) - remaining, this.x, inBufferPos, part);
                    remaining -= part;
                    if (remaining > 0) {
                        a(newcount);
                        inBufferPos = 0;
                    }
                }
                this.y = newcount;
            }
        }
    }

    public synchronized void write(int b) {
        int i = this.y;
        int inBufferPos = i - this.q;
        if (inBufferPos == this.x.length) {
            a(i + 1);
            inBufferPos = 0;
        }
        this.x[inBufferPos] = (byte) b;
        this.y++;
    }

    public void close() {
    }

    public synchronized void g(OutputStream out) {
        int remaining = this.y;
        for (byte[] buf : this.d) {
            int c2 = Math.min(buf.length, remaining);
            out.write(buf, 0, c2);
            remaining -= c2;
            if (remaining == 0) {
                break;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002e, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized byte[] c() {
        /*
            r7 = this;
            monitor-enter(r7)
            int r0 = r7.y     // Catch:{ all -> 0x002f }
            if (r0 != 0) goto L_0x0009
            byte[] r1 = c     // Catch:{ all -> 0x002f }
            monitor-exit(r7)
            return r1
        L_0x0009:
            byte[] r1 = new byte[r0]     // Catch:{ all -> 0x002f }
            r2 = 0
            java.util.List<byte[]> r3 = r7.d     // Catch:{ all -> 0x002f }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x002f }
        L_0x0012:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x002f }
            if (r4 == 0) goto L_0x002d
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x002f }
            byte[] r4 = (byte[]) r4     // Catch:{ all -> 0x002f }
            int r5 = r4.length     // Catch:{ all -> 0x002f }
            int r5 = java.lang.Math.min(r5, r0)     // Catch:{ all -> 0x002f }
            r6 = 0
            java.lang.System.arraycopy(r4, r6, r1, r2, r5)     // Catch:{ all -> 0x002f }
            int r2 = r2 + r5
            int r0 = r0 - r5
            if (r0 != 0) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            goto L_0x0012
        L_0x002d:
            monitor-exit(r7)
            return r1
        L_0x002f:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.io.output.a.c():byte[]");
    }

    @Deprecated
    public String toString() {
        return new String(c(), Charset.defaultCharset());
    }
}
