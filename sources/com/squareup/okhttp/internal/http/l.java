package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.p;
import com.squareup.okhttp.y;
import okio.e;

/* compiled from: RealResponseBody */
public final class l extends y {
    private final p c;
    private final e d;

    public l(p headers, e source) {
        this.c = headers;
        this.d = source;
    }

    public long c() {
        return k.c(this.c);
    }

    public e g() {
        return this.d;
    }
}
