package org.apache.httpcore.impl;

import org.apache.httpcore.io.f;

/* compiled from: HttpConnectionMetricsImpl */
public class i {
    private final f a;
    private final f b;
    private long c = 0;
    private long d = 0;

    public i(f inTransportMetric, f outTransportMetric) {
        this.a = inTransportMetric;
        this.b = outTransportMetric;
    }

    public void a() {
        this.c++;
    }

    public void b() {
        this.d++;
    }
}
