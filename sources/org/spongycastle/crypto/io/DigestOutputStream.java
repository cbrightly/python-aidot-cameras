package org.spongycastle.crypto.io;

import java.io.OutputStream;
import org.spongycastle.crypto.Digest;

public class DigestOutputStream extends OutputStream {
    protected Digest c;

    public DigestOutputStream(Digest Digest) {
        this.c = Digest;
    }

    public void write(int b) {
        this.c.d((byte) b);
    }

    public void write(byte[] b, int off, int len) {
        this.c.update(b, off, len);
    }

    public byte[] a() {
        byte[] res = new byte[this.c.e()];
        this.c.c(res, 0);
        return res;
    }
}
