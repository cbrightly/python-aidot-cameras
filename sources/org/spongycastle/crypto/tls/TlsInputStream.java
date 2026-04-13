package org.spongycastle.crypto.tls;

import java.io.InputStream;

public class TlsInputStream extends InputStream {
    private byte[] c = new byte[1];
    private TlsProtocol d = null;

    TlsInputStream(TlsProtocol handler) {
        this.d = handler;
    }

    public int available() {
        return this.d.a();
    }

    public int read(byte[] buf, int offset, int len) {
        return this.d.H(buf, offset, len);
    }

    public int read() {
        if (read(this.c) < 0) {
            return -1;
        }
        return this.c[0] & 255;
    }

    public void close() {
        this.d.f();
    }
}
