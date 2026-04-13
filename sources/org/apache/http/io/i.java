package org.apache.http.io;

import org.apache.http.util.d;

/* compiled from: SessionOutputBuffer */
public interface i {
    void a(String str);

    void b(d dVar);

    void flush();

    g getMetrics();

    void write(int i);

    void write(byte[] bArr, int i, int i2);
}
