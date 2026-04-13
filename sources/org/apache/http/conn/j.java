package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.util.a;

/* compiled from: EofSensorInputStream */
public class j extends InputStream implements g {
    protected InputStream c;
    private boolean d = false;
    private final k f;

    public j(InputStream in, k watcher) {
        a.i(in, "Wrapped stream");
        this.c = in;
        this.f = watcher;
    }

    /* access modifiers changed from: protected */
    public boolean l() {
        if (!this.d) {
            return this.c != null;
        }
        throw new IOException("Attempted read on closed stream.");
    }

    public int read() {
        if (!l()) {
            return -1;
        }
        try {
            int l = this.c.read();
            j(l);
            return l;
        } catch (IOException ex) {
            a();
            throw ex;
        }
    }

    public int read(byte[] b, int off, int len) {
        if (!l()) {
            return -1;
        }
        try {
            int l = this.c.read(b, off, len);
            j(l);
            return l;
        } catch (IOException ex) {
            a();
            throw ex;
        }
    }

    public int read(byte[] b) {
        return read(b, 0, b.length);
    }

    public int available() {
        if (!l()) {
            return 0;
        }
        try {
            return this.c.available();
        } catch (IOException ex) {
            a();
            throw ex;
        }
    }

    public void close() {
        this.d = true;
        i();
    }

    /* access modifiers changed from: protected */
    public void j(int eof) {
        InputStream toCheckStream = this.c;
        if (toCheckStream != null && eof < 0) {
            boolean scws = true;
            try {
                k kVar = this.f;
                if (kVar != null) {
                    scws = kVar.a(toCheckStream);
                }
                if (scws) {
                    toCheckStream.close();
                }
            } finally {
                this.c = null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void i() {
        InputStream toCloseStream = this.c;
        if (toCloseStream != null) {
            boolean scws = true;
            try {
                k kVar = this.f;
                if (kVar != null) {
                    scws = kVar.d(toCloseStream);
                }
                if (scws) {
                    toCloseStream.close();
                }
            } finally {
                this.c = null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a() {
        InputStream toAbortStream = this.c;
        if (toAbortStream != null) {
            boolean scws = true;
            try {
                k kVar = this.f;
                if (kVar != null) {
                    scws = kVar.b(toAbortStream);
                }
                if (scws) {
                    toAbortStream.close();
                }
            } finally {
                this.c = null;
            }
        }
    }

    public void c() {
        this.d = true;
        a();
    }
}
