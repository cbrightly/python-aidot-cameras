package org.spongycastle.util.io;

import java.io.InputStream;
import java.io.OutputStream;

public class TeeInputStream extends InputStream {
    private final InputStream c;
    private final OutputStream d;

    public TeeInputStream(InputStream input, OutputStream output) {
        this.c = input;
        this.d = output;
    }

    public int read(byte[] buf) {
        return read(buf, 0, buf.length);
    }

    public int read(byte[] buf, int off, int len) {
        int i = this.c.read(buf, off, len);
        if (i > 0) {
            this.d.write(buf, off, i);
        }
        return i;
    }

    public int read() {
        int i = this.c.read();
        if (i >= 0) {
            this.d.write(i);
        }
        return i;
    }

    public void close() {
        this.c.close();
        this.d.close();
    }
}
