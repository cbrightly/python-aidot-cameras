package com.amazonaws.http.impl.client;

import com.amazonaws.http.conn.ClientConnectionManagerFactory;
import com.amazonaws.http.protocol.SdkHttpRequestExecutor;
import org.apache.http.conn.b;
import org.apache.http.impl.client.n;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.j;

public class SdkHttpClient extends n {
    public SdkHttpClient(b conman, HttpParams params) {
        super(ClientConnectionManagerFactory.wrap(conman), params);
    }

    /* access modifiers changed from: protected */
    public j createRequestExecutor() {
        return new SdkHttpRequestExecutor();
    }
}
