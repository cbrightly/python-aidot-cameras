package org.spongycastle.asn1;

import java.io.EOFException;
import java.io.InputStream;
import org.spongycastle.util.io.Streams;

public class DefiniteLengthInputStream extends LimitedInputStream {
    private static final byte[] f = new byte[0];
    private final int q;
    private int x;

    DefiniteLengthInputStream(InputStream in, int length) {
        super(in, length);
        if (length >= 0) {
            this.q = length;
            this.x = length;
            if (length == 0) {
                c(true);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("negative lengths not allowed");
    }

    /* access modifiers changed from: package-private */
    public int a() {
        return this.x;
    }

    public int read() {
        if (this.x == 0) {
            return -1;
        }
        int b = this.c.read();
        if (b >= 0) {
            int i = this.x - 1;
            this.x = i;
            if (i == 0) {
                c(true);
            }
            return b;
        }
        throw new EOFException("DEF length " + this.q + " object truncated by " + this.x);
    }

    public int read(byte[] buf, int off, int len) {
        int i = this.x;
        if (i == 0) {
            return -1;
        }
        int numRead = this.c.read(buf, off, Math.min(len, i));
        if (numRead >= 0) {
            int i2 = this.x - numRead;
            this.x = i2;
            if (i2 == 0) {
                c(true);
            }
            return numRead;
        }
        throw new EOFException("DEF length " + this.q + " object truncated by " + this.x);
    }

    /* access modifiers changed from: package-private */
    public byte[] g() {
        int i = this.x;
        if (i == 0) {
            return f;
        }
        byte[] bytes = new byte[i];
        int c = i - Streams.c(this.c, bytes);
        this.x = c;
        if (c == 0) {
            c(true);
            return bytes;
        }
        throw new EOFException("DEF length " + this.q + " object truncated by " + this.x);
    }
}
