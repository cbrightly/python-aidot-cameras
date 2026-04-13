package org.spongycastle.crypto.io;

import java.io.FilterInputStream;
import org.spongycastle.crypto.Signer;

public class SignerInputStream extends FilterInputStream {
    protected Signer c;

    public int read() {
        int b = this.in.read();
        if (b >= 0) {
            this.c.d((byte) b);
        }
        return b;
    }

    public int read(byte[] b, int off, int len) {
        int n = this.in.read(b, off, len);
        if (n > 0) {
            this.c.update(b, off, n);
        }
        return n;
    }
}
