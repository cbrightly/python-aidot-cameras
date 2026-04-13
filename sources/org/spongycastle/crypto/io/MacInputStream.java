package org.spongycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.InputStream;
import org.spongycastle.crypto.Mac;

public class MacInputStream extends FilterInputStream {
    protected Mac c;

    public MacInputStream(InputStream stream, Mac mac) {
        super(stream);
        this.c = mac;
    }

    public int read() {
        int b = this.in.read();
        if (b >= 0) {
            this.c.d((byte) b);
        }
        return b;
    }

    public int read(byte[] b, int off, int len) {
        int n = this.in.read(b, off, len);
        if (n >= 0) {
            this.c.update(b, off, n);
        }
        return n;
    }
}
