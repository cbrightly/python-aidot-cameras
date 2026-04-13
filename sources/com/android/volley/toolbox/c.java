package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.Header;
import com.android.volley.e;
import com.android.volley.f;
import com.android.volley.h;
import com.android.volley.i;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/* compiled from: BasicNetwork */
public class c implements f {
    @Deprecated
    protected final i a;
    private final b b;
    protected final d c;

    @Deprecated
    public c(i httpStack) {
        this(httpStack, new d(4096));
    }

    @Deprecated
    public c(i httpStack, d pool) {
        this.a = httpStack;
        this.b = new a(httpStack);
        this.c = pool;
    }

    public c(b httpStack) {
        this(httpStack, new d(4096));
    }

    public c(b httpStack, d pool) {
        this.b = httpStack;
        this.a = httpStack;
        this.c = pool;
    }

    public h a(i<?> request) {
        h httpResponse;
        int statusCode;
        List<e> c2;
        byte[] responseContents;
        i<?> iVar = request;
        long requestStart = SystemClock.elapsedRealtime();
        while (true) {
            List<Header> responseHeaders = Collections.emptyList();
            try {
                httpResponse = this.b.b(iVar, g.c(request.getCacheEntry()));
                statusCode = httpResponse.d();
                c2 = httpResponse.c();
                break;
            } catch (IOException e) {
                List<Header> list = responseHeaders;
                o.a(iVar, o.e(request, e, requestStart, (h) null, (byte[]) null));
            }
        }
        if (statusCode == 304) {
            return o.b(iVar, SystemClock.elapsedRealtime() - requestStart, c2);
        }
        InputStream inputStream = httpResponse.a();
        if (inputStream != null) {
            responseContents = o.c(inputStream, httpResponse.b(), this.c);
        } else {
            responseContents = new byte[0];
        }
        long requestLifetime = SystemClock.elapsedRealtime() - requestStart;
        o.d(requestLifetime, iVar, responseContents, statusCode);
        if (statusCode < 200 || statusCode > 299) {
            throw new IOException();
        }
        long j = requestLifetime;
        return new h(statusCode, responseContents, false, SystemClock.elapsedRealtime() - requestStart, c2);
    }
}
