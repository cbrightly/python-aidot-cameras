package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.i;
import org.apache.http.util.a;

/* compiled from: IdentityOutputStream */
public class q extends OutputStream {
    private final i c;
    private boolean d = false;

    public q(i out) {
        this.c = (i) a.i(out, "Session output buffer");
    }

    public void close() {
        if (!this.d) {
            this.d = true;
            this.c.flush();
        }
    }

    public void flush() {
        this.c.flush();
    }

    public void write(byte[] b, int off, int len) {
        if (!this.d) {
            this.c.write(b, off, len);
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(int b) {
        if (!this.d) {
            this.c.write(b);
            return;
        }
        throw new IOException("Attempted write to closed stream.");
    }
}
