package org.spongycastle.util.test;

import java.io.FilterOutputStream;

public class UncloseableOutputStream extends FilterOutputStream {
    public void close() {
        throw new RuntimeException("close() called on UncloseableOutputStream");
    }

    public void write(byte[] b, int off, int len) {
        this.out.write(b, off, len);
    }
}
