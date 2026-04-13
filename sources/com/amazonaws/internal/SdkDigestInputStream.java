package com.amazonaws.internal;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class SdkDigestInputStream extends DigestInputStream implements MetricAware {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int SKIP_BUF_SIZE = 2048;

    static {
        Class<SdkDigestInputStream> cls = SdkDigestInputStream.class;
    }

    public SdkDigestInputStream(InputStream stream, MessageDigest digest) {
        super(stream, digest);
    }

    @Deprecated
    public final boolean isMetricActivated() {
        if (this.in instanceof MetricAware) {
            return ((MetricAware) this.in).isMetricActivated();
        }
        return false;
    }

    public final long skip(long n) {
        if (n <= 0) {
            return n;
        }
        byte[] b = new byte[((int) Math.min(2048, n))];
        long m = n;
        while (m > 0) {
            int len = read(b, 0, (int) Math.min(m, (long) b.length));
            if (len != -1) {
                m -= (long) len;
            } else if (m == n) {
                return -1;
            } else {
                return n - m;
            }
        }
        if (m == 0) {
            return n;
        }
        throw new AssertionError();
    }
}
