package org.spongycastle.crypto.io;

import java.io.OutputStream;
import org.spongycastle.crypto.Signer;

public class SignerOutputStream extends OutputStream {
    protected Signer c;

    public void write(int b) {
        this.c.d((byte) b);
    }

    public void write(byte[] b, int off, int len) {
        this.c.update(b, off, len);
    }
}
