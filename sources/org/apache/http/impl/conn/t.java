package org.apache.http.impl.conn;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: LoggingInputStream */
public class t extends InputStream {
    private final InputStream c;
    private final e0 d;

    public t(InputStream in, e0 wire) {
        this.c = in;
        this.d = wire;
    }

    public int read() {
        try {
            int b = this.c.read();
            if (b == -1) {
                this.d.c("end of stream");
            } else {
                this.d.b(b);
            }
            return b;
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.c("[read] I/O error: " + ex.getMessage());
            throw ex;
        }
    }

    public int read(byte[] b) {
        try {
            int bytesRead = this.c.read(b);
            if (bytesRead == -1) {
                this.d.c("end of stream");
            } else if (bytesRead > 0) {
                this.d.e(b, 0, bytesRead);
            }
            return bytesRead;
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.c("[read] I/O error: " + ex.getMessage());
            throw ex;
        }
    }

    public int read(byte[] b, int off, int len) {
        try {
            int bytesRead = this.c.read(b, off, len);
            if (bytesRead == -1) {
                this.d.c("end of stream");
            } else if (bytesRead > 0) {
                this.d.e(b, off, bytesRead);
            }
            return bytesRead;
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.c("[read] I/O error: " + ex.getMessage());
            throw ex;
        }
    }

    public long skip(long n) {
        try {
            return super.skip(n);
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.c("[skip] I/O error: " + ex.getMessage());
            throw ex;
        }
    }

    public int available() {
        try {
            return this.c.available();
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.c("[available] I/O error : " + ex.getMessage());
            throw ex;
        }
    }

    public void mark(int readlimit) {
        super.mark(readlimit);
    }

    public void reset() {
        super.reset();
    }

    public boolean markSupported() {
        return false;
    }

    public void close() {
        try {
            this.c.close();
        } catch (IOException ex) {
            e0 e0Var = this.d;
            e0Var.c("[close] I/O error: " + ex.getMessage());
            throw ex;
        }
    }
}
