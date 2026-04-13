package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.OutputStream;

/* compiled from: LoggingOutputStream */
public class v extends OutputStream {
    private final OutputStream c;
    private final e0 d;

    public v(OutputStream out, e0 wire) {
        this.c = out;
        this.d = wire;
    }

    public void write(int b) {
        try {
            this.d.f(b);
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.g("[write] I/O error: " + ex.getMessage());
            throw ex;
        }
    }

    public void write(byte[] b) {
        try {
            this.d.h(b);
            this.c.write(b);
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.g("[write] I/O error: " + ex.getMessage());
            throw ex;
        }
    }

    public void write(byte[] b, int off, int len) {
        try {
            this.d.i(b, off, len);
            this.c.write(b, off, len);
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.g("[write] I/O error: " + ex.getMessage());
            throw ex;
        }
    }

    public void flush() {
        try {
            this.c.flush();
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.g("[flush] I/O error: " + ex.getMessage());
            throw ex;
        }
    }

    public void close() {
        try {
            this.c.close();
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.g("[close] I/O error: " + ex.getMessage());
            throw ex;
        }
    }
}
