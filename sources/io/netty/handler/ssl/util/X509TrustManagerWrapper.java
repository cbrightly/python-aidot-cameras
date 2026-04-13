package io.netty.handler.ssl.util;

import io.netty.util.internal.ObjectUtil;
import java.net.Socket;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

public final class X509TrustManagerWrapper extends X509ExtendedTrustManager {
    private final X509TrustManager delegate;

    X509TrustManagerWrapper(X509TrustManager delegate2) {
        this.delegate = (X509TrustManager) ObjectUtil.checkNotNull(delegate2, "delegate");
    }

    public void checkClientTrusted(X509Certificate[] chain, String s) {
        this.delegate.checkClientTrusted(chain, s);
    }

    public void checkClientTrusted(X509Certificate[] chain, String s, Socket socket) {
        this.delegate.checkClientTrusted(chain, s);
    }

    public void checkClientTrusted(X509Certificate[] chain, String s, SSLEngine sslEngine) {
        this.delegate.checkClientTrusted(chain, s);
    }

    public void checkServerTrusted(X509Certificate[] chain, String s) {
        this.delegate.checkServerTrusted(chain, s);
    }

    public void checkServerTrusted(X509Certificate[] chain, String s, Socket socket) {
        this.delegate.checkServerTrusted(chain, s);
    }

    public void checkServerTrusted(X509Certificate[] chain, String s, SSLEngine sslEngine) {
        this.delegate.checkServerTrusted(chain, s);
    }

    public X509Certificate[] getAcceptedIssuers() {
        return this.delegate.getAcceptedIssuers();
    }
}
