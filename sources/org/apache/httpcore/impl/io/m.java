package org.apache.httpcore.impl.io;

import java.io.InputStream;
import org.apache.httpcore.io.g;
import org.apache.httpcore.util.a;

/* compiled from: IdentityInputStream */
public class m extends InputStream {
    private final g c;
    private boolean d = false;

    public m(g in) {
        this.c = (g) a.g(in, "Session input buffer");
    }

    public int available() {
        g gVar = this.c;
        if (gVar instanceof org.apache.httpcore.io.a) {
            return ((org.apache.httpcore.io.a) gVar).length();
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
