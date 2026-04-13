package org.apache.httpcore.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.httpcore.io.h;

/* compiled from: ChunkedOutputStream */
public class d extends OutputStream {
    private final h c;
    private final byte[] d;
    private int f = 0;
    private boolean q = false;
    private boolean x = false;

    public d(int bufferSize, h out) {
        this.d = new byte[bufferSize];
        this.c = out;
    }

    /* access modifiers changed from: protected */
    public void c() {
        int i = this.f;
        if (i > 0) {
            this.c.a(Integer.toHexString(i));
            this.c.write(this.d, 0, this.f);
            this.c.a("");
            this.f = 0;
        }
    }

    /* access modifiers changed from: protected */
    public void g(byte[] bufferToAppend, int off, int len) {
        this.c.a(Integer.toHexString(this.f + len));
        this.c.write(this.d, 0, this.f);
        this.c.write(bufferToAppend, off, len);
        this.c.a("");
        this.f = 0;
    }

    /* access modifiers changed from: protected */
    public void i() {
        this.c.a("0");
        this.c.a("");
    }

    public void a() {
        if (!this.q) {
            c();
            i();
            this.q = true;
        }
    }

    public void write(int b) {
        if (!this.x) {
            byte[] bArr = this.d;
            int i = this.f;
            bArr[i] = (byte) b;
            int i2 = i + 1;
            this.f = i2;
            if (i2 == bArr.length) {
                c();
                return;
            }
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] src, int off, int len) {
        if (!this.x) {
            byte[] bArr = this.d;
            int length = bArr.length;
            int i = this.f;
            if (len >= length - i) {
                g(src, off, len);
                return;
            }
            System.arraycopy(src, off, bArr, i, len);
            this.f += len;
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }

    public void flush() {
        c();
        this.c.flush();
    }

    public void close() {
        if (!this.x) {
            this.x = true;
            a();
            this.c.flush();
        }
    }
}
