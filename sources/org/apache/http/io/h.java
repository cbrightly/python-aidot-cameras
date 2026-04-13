package org.apache.http.io;

import org.apache.http.util.d;

/* compiled from: SessionInputBuffer */
public interface h {
    int a(d dVar);

    @Deprecated
    boolean b(int i);

    g getMetrics();

    int read();

    int read(byte[] bArr, int i, int i2);
}
