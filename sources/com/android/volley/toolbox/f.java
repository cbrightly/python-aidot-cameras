package com.android.volley.toolbox;

import com.android.volley.i;
import java.net.URI;
import java.util.Map;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.e;
import org.apache.http.client.methods.h;
import org.apache.http.client.methods.j;
import org.apache.http.client.methods.k;
import org.apache.http.client.methods.l;
import org.apache.http.client.methods.o;
import org.apache.http.client.methods.p;
import org.apache.http.entity.d;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.q;

@Deprecated
/* compiled from: HttpClientStack */
public class f implements i {
    protected final HttpClient a;

    public f(HttpClient client) {
        this.a = client;
    }

    private static void e(p httpRequest, Map<String, String> headers) {
        for (String key : headers.keySet()) {
            httpRequest.setHeader(key, headers.get(key));
        }
    }

    public q a(i<?> request, Map<String, String> additionalHeaders) {
        p httpRequest = b(request, additionalHeaders);
        e(httpRequest, additionalHeaders);
        e(httpRequest, request.getHeaders());
        c(httpRequest);
        HttpParams httpParams = httpRequest.getParams();
        int timeoutMs = request.getTimeoutMs();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, timeoutMs);
        return this.a.execute(httpRequest);
    }

    static p b(i<?> request, Map<String, String> map) {
        switch (request.getMethod()) {
            case -1:
                byte[] postBody = request.getPostBody();
                if (postBody == null) {
                    return new h(request.getUrl());
                }
                k postRequest = new k(request.getUrl());
                postRequest.addHeader("Content-Type", request.getPostBodyContentType());
                postRequest.l(new d(postBody));
                return postRequest;
            case 0:
                return new h(request.getUrl());
            case 1:
                k postRequest2 = new k(request.getUrl());
                postRequest2.addHeader("Content-Type", request.getBodyContentType());
                d(postRequest2, request);
                return postRequest2;
            case 2:
                l putRequest = new l(request.getUrl());
                putRequest.addHeader("Content-Type", request.getBodyContentType());
                d(putRequest, request);
                return putRequest;
            case 3:
                return new e(request.getUrl());
            case 4:
                return new org.apache.http.client.methods.i(request.getUrl());
            case 5:
                return new j(request.getUrl());
            case 6:
                return new o(request.getUrl());
            case 7:
                a patchRequest = new a(request.getUrl());
                patchRequest.addHeader("Content-Type", request.getBodyContentType());
                d(patchRequest, request);
                return patchRequest;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }

    private static void d(org.apache.http.client.methods.f httpRequest, i<?> request) {
        byte[] body = request.getBody();
        if (body != null) {
            httpRequest.l(new d(body));
        }
    }

    /* access modifiers changed from: protected */
    public void c(p request) {
    }

    /* compiled from: HttpClientStack */
    public static final class a extends org.apache.http.client.methods.f {
        public a(String uri) {
            k(URI.create(uri));
        }

        public String getMethod() {
            return "PATCH";
        }
    }
}
