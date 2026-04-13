package com.squareup.okhttp;

import java.io.Closeable;
import java.io.InputStream;
import okio.e;

/* compiled from: ResponseBody */
public abstract class y implements Closeable {
    public abstract long c();

    public abstract e g();

    public final InputStream a() {
        return g().Y0();
    }

    public void close() {
        g().close();
    }
}
