package com.squareup.okhttp.internal.framed;

import java.io.Closeable;
import java.util.List;
import okio.e;
import okio.f;

/* compiled from: FrameReader */
public interface b extends Closeable {

    /* compiled from: FrameReader */
    public interface a {
        void b(int i, long j);

        void f(int i, int i2, List<f> list);

        void h(boolean z, int i, int i2);

        void k(int i, a aVar);

        void l();

        void m(boolean z, int i, e eVar, int i2);

        void n(int i, int i2, int i3, boolean z);

        void o(boolean z, n nVar);

        void p(boolean z, boolean z2, int i, int i2, List<f> list, g gVar);

        void q(int i, a aVar, f fVar);
    }

    boolean S(a aVar);

    void c0();
}
