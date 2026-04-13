package org.apache.http.impl;

/* compiled from: HttpConnectionMetricsImpl */
public class g {
    private final org.apache.http.io.g a;
    private final org.apache.http.io.g b;
    private long c = 0;
    private long d = 0;

    public g(org.apache.http.io.g inTransportMetric, org.apache.http.io.g outTransportMetric) {
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
