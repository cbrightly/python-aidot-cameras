package com.didichuxing.doraemonkit.kit.network.okhttp;

import java.io.InputStream;
import okhttp3.e0;
import okhttp3.x;
import okio.e;
import okio.p;

public class ForwardingResponseBody extends e0 {
    private final e0 mBody;
    private final e mInterceptedSource;

    public ForwardingResponseBody(e0 body, InputStream interceptedStream) {
        this.mBody = body;
        this.mInterceptedSource = p.d(p.l(interceptedStream));
    }

    public x contentType() {
        return this.mBody.contentType();
    }

    public long contentLength() {
        return this.mBody.contentLength();
    }

    public e source() {
        return this.mInterceptedSource;
    }
}
