package org.spongycastle.util.io;

import java.io.OutputStream;

public class TeeOutputStream extends OutputStream {
    private OutputStream c;
    private OutputStream d;

    public TeeOutputStream(OutputStream output1, OutputStream output2) {
        this.c = output1;
        this.d = output2;
    }

    public void write(byte[] buf) {
        this.c.write(buf);
        this.d.write(buf);
    }

    public void write(byte[] buf, int off, int len) {
        this.c.write(buf, off, len);
        this.d.write(buf, off, len);
    }

    public void write(int b) {
        this.c.write(b);
        this.d.write(b);
    }

    public void flush() {
        this.c.flush();
        this.d.flush();
    }

    public void close() {
        this.c.close();
        this.d.close();
    }
}
