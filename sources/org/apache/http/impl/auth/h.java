package org.apache.http.impl.auth;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

/* compiled from: HttpEntityDigester */
public class h extends OutputStream {
    private final MessageDigest c;
    private boolean d;
    private byte[] f;

    h(MessageDigest digester) {
        this.c = digester;
        digester.reset();
    }

    public void write(int b) {
        if (!this.d) {
            this.c.update((byte) b);
            return;
        }
        throw new IOException("Stream has been already closed");
    }

    public void write(byte[] b, int off, int len) {
        if (!this.d) {
            this.c.update(b, off, len);
            return;
        }
        throw new IOException("Stream has been already closed");
    }

    public void close() {
        if (!this.d) {
            this.d = true;
            this.f = this.c.digest();
            super.close();
        }
    }

    public byte[] a() {
        return this.f;
    }
}
