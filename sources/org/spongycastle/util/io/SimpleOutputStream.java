package org.spongycastle.util.io;

import java.io.OutputStream;

public abstract class SimpleOutputStream extends OutputStream {
    public void close() {
    }

    public void flush() {
    }

    public void write(int b) {
        write(new byte[]{(byte) b}, 0, 1);
    }
}
