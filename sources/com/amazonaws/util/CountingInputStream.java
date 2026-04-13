package com.amazonaws.util;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.InputStream;

@Deprecated
public class CountingInputStream extends SdkFilterInputStream {
    private long byteCount = 0;

    public CountingInputStream(InputStream in) {
        super(in);
    }

    public long getByteCount() {
        return this.byteCount;
    }

    public int read() {
        int tmp = super.read();
        this.byteCount += tmp >= 0 ? 1 : 0;
        return tmp;
    }

    public int read(byte[] b, int off, int len) {
        int tmp = super.read(b, off, len);
        this.byteCount += tmp >= 0 ? (long) tmp : 0;
        return tmp;
    }
}
