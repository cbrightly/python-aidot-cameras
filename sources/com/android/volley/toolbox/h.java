package com.android.volley.toolbox;

import androidx.annotation.Nullable;
import com.android.volley.e;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: HttpResponse */
public final class h {
    private final int a;
    private final List<e> b;
    private final int c;
    @Nullable
    private final InputStream d;
    @Nullable
    private final byte[] e;

    public h(int statusCode, List<e> headers) {
        this(statusCode, headers, -1, (InputStream) null);
    }

    public h(int statusCode, List<e> headers, int contentLength, InputStream content) {
        this.a = statusCode;
        this.b = headers;
        this.c = contentLength;
        this.d = content;
        this.e = null;
    }

    public final int d() {
        return this.a;
    }

    public final List<e> c() {
        return Collections.unmodifiableList(this.b);
    }

    public final int b() {
        return this.c;
    }

    @Nullable
    public final InputStream a() {
        InputStream inputStream = this.d;
        if (inputStream != null) {
            return inputStream;
        }
        if (this.e != null) {
            return new ByteArrayInputStream(this.e);
        }
        return null;
    }
}
