package com.bumptech.glide.disklrucache;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* compiled from: StrictLineReader */
public class b implements Closeable {
    private final InputStream c;
    /* access modifiers changed from: private */
    public final Charset d;
    private byte[] f;
    private int q;
    private int x;

    public b(InputStream in, Charset charset) {
        this(in, 8192, charset);
    }

    public b(InputStream in, int capacity, Charset charset) {
        if (in == null || charset == null) {
            throw new NullPointerException();
        } else if (capacity < 0) {
            throw new IllegalArgumentException("capacity <= 0");
        } else if (charset.equals(c.a)) {
            this.c = in;
            this.d = charset;
            this.f = new byte[capacity];
        } else {
            throw new IllegalArgumentException("Unsupported encoding");
        }
    }

    public void close() {
        synchronized (this.c) {
            if (this.f != null) {
                this.f = null;
                this.c.close();
            }
        }
    }

    public String i() {
        int i;
        byte[] bArr;
        synchronized (this.c) {
            if (this.f != null) {
                if (this.q >= this.x) {
                    c();
                }
                int i2 = this.q;
                while (i2 != this.x) {
                    byte[] bArr2 = this.f;
                    if (bArr2[i2] == 10) {
                        int lineEnd = (i2 == this.q || bArr2[i2 + -1] != 13) ? i2 : i2 - 1;
                        byte[] bArr3 = this.f;
                        int i3 = this.q;
                        String res = new String(bArr3, i3, lineEnd - i3, this.d.name());
                        this.q = i2 + 1;
                        return res;
                    }
                    i2++;
                }
                ByteArrayOutputStream out = new a((this.x - this.q) + 80);
                loop1:
                while (true) {
                    byte[] bArr4 = this.f;
                    int i4 = this.q;
                    out.write(bArr4, i4, this.x - i4);
                    this.x = -1;
                    c();
                    i = this.q;
                    while (i != this.x) {
                        bArr = this.f;
                        if (bArr[i] == 10) {
                            break loop1;
                        }
                        i++;
                    }
                }
                int i5 = this.q;
                if (i != i5) {
                    out.write(bArr, i5, i - i5);
                }
                this.q = i + 1;
                String byteArrayOutputStream = out.toString();
                return byteArrayOutputStream;
            }
            throw new IOException("LineReader is closed");
        }
    }

    /* compiled from: StrictLineReader */
    public class a extends ByteArrayOutputStream {
        a(int x0) {
            super(x0);
        }

        public String toString() {
            int length = this.count;
            if (length > 0 && this.buf[length - 1] == 13) {
                length--;
            }
            try {
                return new String(this.buf, 0, length, b.this.d.name());
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }
    }

    public boolean g() {
        return this.x == -1;
    }

    private void c() {
        InputStream inputStream = this.c;
        byte[] bArr = this.f;
        int result = inputStream.read(bArr, 0, bArr.length);
        if (result != -1) {
            this.q = 0;
            this.x = result;
            return;
        }
        throw new EOFException();
    }
}
