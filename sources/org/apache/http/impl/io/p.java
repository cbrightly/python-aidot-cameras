package org.apache.http.impl.io;

import java.io.InputStream;
import org.apache.http.io.h;
import org.apache.http.util.a;

/* compiled from: IdentityInputStream */
public class p extends InputStream {
    private final h c;
    private boolean d = false;

    public p(h in) {
        this.c = (h) a.i(in, "Session input buffer");
    }

    public int available() {
        h hVar = this.c;
        if (hVar instanceof org.apache.http.io.a) {
            return ((org.apache.http.io.a) hVar).length();
        }
        return 0;
    }

    public void close() {
        this.d = true;
    }

    public int read() {
        if (this.d) {
            return -1;
        }
        return this.c.read();
    }

    public int read(byte[] b, int off, int len) {
        if (this.d) {
            return -1;
        }
        return this.c.read(b, off, len);
    }
}
