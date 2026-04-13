package org.spongycastle.jcajce.io;

import java.io.OutputStream;
import javax.crypto.Mac;

public final class MacOutputStream extends OutputStream {
    private Mac c;

    public void write(int b) {
        this.c.update((byte) b);
    }

    public void write(byte[] bytes, int off, int len) {
        this.c.update(bytes, off, len);
    }
}
