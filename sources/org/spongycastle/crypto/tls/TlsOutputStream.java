package org.spongycastle.crypto.tls;

import java.io.OutputStream;

public class TlsOutputStream extends OutputStream {
    private byte[] c = new byte[1];
    private TlsProtocol d;

    TlsOutputStream(TlsProtocol handler) {
        this.d = handler;
    }

    public void write(byte[] buf, int offset, int len) {
        this.d.R(buf, offset, len);
    }

    public void write(int arg0) {
        byte[] bArr = this.c;
        bArr[0] = (byte) arg0;
        write(bArr, 0, 1);
    }

    public void close() {
        this.d.f();
    }

    public void flush() {
        this.d.k();
    }
}
