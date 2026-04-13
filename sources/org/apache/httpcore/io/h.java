package org.apache.httpcore.io;

import org.apache.httpcore.util.d;

/* compiled from: SessionOutputBuffer */
public interface h {
    void a(String str);

    void b(d dVar);

    void flush();

    void write(int i);

    void write(byte[] bArr, int i, int i2);
}
