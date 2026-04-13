package org.spongycastle.asn1;

import java.io.EOFException;
import java.io.InputStream;

public class IndefiniteLengthInputStream extends LimitedInputStream {
    private int f;
    private int q;
    private boolean x = false;
    private boolean y = true;

    IndefiniteLengthInputStream(InputStream in, int limit) {
        super(in, limit);
        this.f = in.read();
        int read = in.read();
        this.q = read;
        if (read >= 0) {
            g();
            return;
        }
        throw new EOFException();
    }

    /* access modifiers changed from: package-private */
    public void i(boolean eofOn00) {
        this.y = eofOn00;
        g();
    }

    private boolean g() {
        if (!this.x && this.y && this.f == 0 && this.q == 0) {
            this.x = true;
            c(true);
        }
        return this.x;
    }

    public int read(byte[] b, int off, int len) {
        if (this.y || len < 3) {
            return super.read(b, off, len);
        }
        if (this.x) {
            return -1;
        }
        int numRead = this.c.read(b, off + 2, len - 2);
        if (numRead >= 0) {
            b[off] = (byte) this.f;
            b[off + 1] = (byte) this.q;
            this.f = this.c.read();
            int read = this.c.read();
            this.q = read;
            if (read >= 0) {
                return numRead + 2;
            }
            throw new EOFException();
        }
        throw new EOFException();
    }

    public int read() {
        if (g()) {
            return -1;
        }
        int b = this.c.read();
        if (b >= 0) {
            int v = this.f;
            this.f = this.q;
            this.q = b;
            return v;
        }
        throw new EOFException();
    }
}
