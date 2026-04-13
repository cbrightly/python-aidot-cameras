package com.amazonaws.kinesisvideo.socket;

import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SocketFactory {
    private static final int DEFAULT_HTTPS_PORT = 443;
    private static final int DEFAULT_HTTP_PORT = 80;
    private static final KeyManager[] NO_KEY_MANAGERS = null;

    public Socket createSocket(URI uri) {
        try {
            return openSocket(uri);
        } catch (Throwable e) {
            throw new RuntimeException("Exception while creating socket ! ", e);
        }
    }

    private Socket openSocket(URI uri) {
        InetAddress address = toInetAddr(uri);
        int port = getPort(uri);
        return isHttps(uri) ? createSslSocket(address, port) : new Socket(address, port);
    }

    private Socket createSslSocket(InetAddress address, int port) {
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(NO_KEY_MANAGERS, trustAllCertificates(), new SecureRandom());
        return context.getSocketFactory().createSocket(address, port);
    }

    public TrustManager[] trustAllCertificates() {
        return new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
    }

    private boolean isHttps(URI uri) {
        return "https".equalsIgnoreCase(uri.getScheme());
    }

    private InetAddress toInetAddr(URI uri) {
        return InetAddress.getByName(getHost(uri));
    }

    private String getHost(URI uri) {
        return uri.getHost();
    }

    private int getPort(URI uri) {
        if (uri.getPort() > 0) {
            return uri.getPort();
        }
        if (isHttps(uri)) {
            return DEFAULT_HTTPS_PORT;
        }
        return 80;
    }
}
