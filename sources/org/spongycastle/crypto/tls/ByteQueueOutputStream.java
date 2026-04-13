package org.spongycastle.crypto.tls;

import java.io.OutputStream;

public class ByteQueueOutputStream extends OutputStream {
    private ByteQueue c = new ByteQueue();

    public void write(int b) {
        this.c.a(new byte[]{(byte) b}, 0, 1);
    }

    public void write(byte[] b, int off, int len) {
        this.c.a(b, off, len);
    }
}
