package org.apache.http.impl.client;

import org.apache.http.client.protocol.c;
import org.apache.http.client.protocol.d;
import org.apache.http.client.protocol.f;
import org.apache.http.client.protocol.g;
import org.apache.http.client.protocol.i;
import org.apache.http.client.protocol.j;
import org.apache.http.conn.b;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.e;
import org.apache.http.protocol.l;
import org.apache.http.protocol.m;
import org.apache.http.protocol.o;
import org.apache.http.t;
import org.apache.http.util.k;

@Deprecated
/* compiled from: DefaultHttpClient */
public class n extends b {
    public n(b conman, HttpParams params) {
        super(conman, params);
    }

    public n(b conman) {
        super(conman, (HttpParams) null);
    }

    public n(HttpParams params) {
        super((b) null, params);
    }

    public n() {
        super((b) null, (HttpParams) null);
    }

    /* access modifiers changed from: protected */
    public HttpParams createHttpParams() {
        HttpParams params = new SyncBasicHttpParams();
        setDefaultHttpParams(params);
        return params;
    }

    public static void setDefaultHttpParams(HttpParams params) {
        HttpProtocolParams.setVersion(params, t.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, e.a.name());
        HttpConnectionParams.setTcpNoDelay(params, true);
        HttpConnectionParams.setSocketBufferSize(params, 8192);
        HttpProtocolParams.setUserAgent(params, k.c("Apache-HttpClient", "org.apache.http.client", n.class));
    }

    /* access modifiers changed from: protected */
    public org.apache.http.protocol.b createHttpProcessor() {
        org.apache.http.protocol.b httpproc = new org.apache.http.protocol.b();
        httpproc.c(new g());
        httpproc.c(new l());
        httpproc.c(new org.apache.http.protocol.n());
        httpproc.c(new f());
        httpproc.c(new o());
        httpproc.c(new m());
        httpproc.c(new c());
        httpproc.e(new org.apache.http.client.protocol.l());
        httpproc.c(new d());
        httpproc.c(new j());
        httpproc.c(new i());
        return httpproc;
    }
}
