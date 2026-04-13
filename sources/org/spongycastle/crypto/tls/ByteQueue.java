package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

public class ByteQueue {
    private byte[] a;
    private int b;
    private int c;
    private boolean d;

    public static int d(int i) {
        int i2 = i | (i >> 1);
        int i3 = i2 | (i2 >> 2);
        int i4 = i3 | (i3 >> 4);
        int i5 = i4 | (i4 >> 8);
        return (i5 | (i5 >> 16)) + 1;
    }

    public ByteQueue() {
        this(1024);
    }

    public ByteQueue(int capacity) {
        this.b = 0;
        this.c = 0;
        this.d = false;
        this.a = capacity == 0 ? TlsUtils.a : new byte[capacity];
    }

    public ByteQueue(byte[] buf, int off, int len) {
        this.b = 0;
        this.c = 0;
        this.d = false;
        this.a = buf;
        this.b = off;
        this.c = len;
        this.d = true;
    }

    public void a(byte[] buf, int off, int len) {
        if (!this.d) {
            int i = this.b;
            int i2 = this.c;
            if (i + i2 + len > this.a.length) {
                int desiredSize = d(i2 + len);
                byte[] bArr = this.a;
                if (desiredSize > bArr.length) {
                    byte[] tmp = new byte[desiredSize];
                    System.arraycopy(bArr, this.b, tmp, 0, this.c);
                    this.a = tmp;
                } else {
                    System.arraycopy(bArr, this.b, bArr, 0, this.c);
                }
                this.b = 0;
            }
            System.arraycopy(buf, off, this.a, this.b + this.c, len);
            this.c += len;
            return;
        }
        throw new IllegalStateException("Cannot add data to read-only buffer");
    }

    public int b() {
        return this.c;
    }

    public void c(OutputStream output, int length) {
        if (length <= this.c) {
            output.write(this.a, this.b, length);
            return;
        }
        throw new IllegalStateException("Cannot copy " + length + " bytes, only got " + this.c);
    }

    public void e(byte[] buf, int offset, int len, int skip) {
        if (buf.length - offset < len) {
            throw new IllegalArgumentException("Buffer size of " + buf.length + " is too small for a read of " + len + " bytes");
        } else if (this.c - skip >= len) {
            System.arraycopy(this.a, this.b + skip, buf, offset, len);
        } else {
            throw new IllegalStateException("Not enough data to read");
        }
    }

    public ByteArrayInputStream f(int length) {
        int i = this.c;
        if (length <= i) {
            int position = this.b;
            this.c = i - length;
            this.b += length;
            return new ByteArrayInputStream(this.a, position, length);
        }
        throw new IllegalStateException("Cannot read " + length + " bytes, only got " + this.c);
    }

    public void g(int i) {
        int i2 = this.c;
        if (i <= i2) {
            this.c = i2 - i;
            this.b += i;
            return;
        }
        throw new IllegalStateException("Cannot remove " + i + " bytes, only got " + this.c);
    }

    public void h(byte[] buf, int off, int len, int skip) {
        e(buf, off, len, skip);
        g(skip + len);
    }

    public byte[] i(int len, int skip) {
        byte[] buf = new byte[len];
        h(buf, 0, len, skip);
        return buf;
    }

    public void j() {
        int i = this.c;
        if (i == 0) {
            this.a = TlsUtils.a;
            this.b = 0;
            return;
        }
        int desiredSize = d(i);
        byte[] bArr = this.a;
        if (desiredSize < bArr.length) {
            byte[] tmp = new byte[desiredSize];
            System.arraycopy(bArr, this.b, tmp, 0, this.c);
            this.a = tmp;
            this.b = 0;
        }
    }
}
