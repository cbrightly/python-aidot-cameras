package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.io.InputStream;

/* compiled from: B2CConverterBlocking */
public final class IntermediateInputStream extends InputStream {
    ByteChunk bc = null;
    boolean initialized = false;

    public void close() {
        throw new IOException("close() called - shouldn't happen ");
    }

    public int read(byte[] cbuf, int off, int len) {
        if (!this.initialized) {
            return -1;
        }
        return this.bc.substract(cbuf, off, len);
    }

    public int read() {
        if (!this.initialized) {
            return -1;
        }
        return this.bc.substract();
    }

    public int available() {
        if (!this.initialized) {
            return 0;
        }
        return this.bc.getLength();
    }

    /* access modifiers changed from: package-private */
    public void setByteChunk(ByteChunk mb) {
        this.initialized = mb != null;
        this.bc = mb;
    }
}
