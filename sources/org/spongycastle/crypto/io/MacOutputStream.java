package org.spongycastle.crypto.io;

import java.io.OutputStream;
import org.spongycastle.crypto.Mac;

public class MacOutputStream extends OutputStream {
    protected Mac c;

    public MacOutputStream(Mac mac) {
        this.c = mac;
    }

    public void write(int b) {
        this.c.d((byte) b);
    }

    public void write(byte[] b, int off, int len) {
        this.c.update(b, off, len);
    }
}
