package com.squareup.okhttp.internal.framed;

import java.io.Closeable;
import java.util.List;

/* compiled from: FrameWriter */
public interface c extends Closeable {
    void B0(n nVar);

    void C(int i, a aVar, byte[] bArr);

    void G();

    void K(boolean z, int i, okio.c cVar, int i2);

    void T0(boolean z, boolean z2, int i, int i2, List<f> list);

    void W0(n nVar);

    void b(int i, long j);

    void f(int i, int i2, List<f> list);

    void flush();

    void h(boolean z, int i, int i2);

    int j0();

    void k(int i, a aVar);
}
