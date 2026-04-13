package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.http.impl.client.HttpRequestNoRetryHandler;
import com.amazonaws.http.impl.client.SdkHttpClient;
import com.amazonaws.logging.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import org.apache.http.auth.n;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.e;
import org.apache.http.conn.scheme.f;
import org.apache.http.conn.scheme.j;
import org.apache.http.conn.scheme.m;
import org.apache.http.impl.client.q;
import org.apache.http.impl.conn.tsccm.g;
import org.apache.http.l;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientFactory {
    private static final int HTTPS_PORT = 443;
    private static final int HTTP_PORT = 80;

    HttpClientFactory() {
    }

    public HttpClient createHttpClient(ClientConfiguration config) {
        HttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, config.getConnectionTimeout());
        HttpConnectionParams.setSoTimeout(basicHttpParams, config.getSocketTimeout());
        HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, true);
        HttpConnectionParams.setTcpNoDelay(basicHttpParams, true);
        int socketSendBufferSizeHint = config.getSocketBufferSizeHints()[0];
        int socketReceiveBufferSizeHint = config.getSocketBufferSizeHints()[1];
        if (socketSendBufferSizeHint > 0 || socketReceiveBufferSizeHint > 0) {
            HttpConnectionParams.setSocketBufferSize(basicHttpParams, Math.max(socketSendBufferSizeHint, socketReceiveBufferSizeHint));
        }
        g connectionManager = ConnectionManagerFactory.createThreadSafeClientConnManager(config, basicHttpParams);
        SdkHttpClient httpClient = new SdkHttpClient(connectionManager, basicHttpParams);
        httpClient.setHttpRequestRetryHandler(HttpRequestNoRetryHandler.Singleton);
        httpClient.setRedirectHandler(new LocationHeaderNotRequiredRedirectHandler());
        if (config.getLocalAddress() != null) {
            ConnRouteParams.setLocalAddress(basicHttpParams, config.getLocalAddress());
        }
        f http = new f(l.DEFAULT_SCHEME_NAME, (m) e.h(), 80);
        org.apache.http.conn.ssl.g sslSocketFactory = org.apache.http.conn.ssl.g.l();
        sslSocketFactory.o(org.apache.http.conn.ssl.g.c);
        f https = new f("https", (m) sslSocketFactory, (int) HTTPS_PORT);
        j sr = connectionManager.e();
        sr.d(http);
        sr.d(https);
        String proxyHost = config.getProxyHost();
        int proxyPort = config.getProxyPort();
        if (proxyHost == null || proxyPort <= 0) {
            HttpParams httpClientParams = basicHttpParams;
            int i = socketReceiveBufferSizeHint;
            int i2 = socketSendBufferSizeHint;
        } else {
            Log log = AmazonHttpClient.log;
            log.info("Configuring Proxy. Proxy Host: " + proxyHost + " Proxy Port: " + proxyPort);
            httpClient.getParams().setParameter("http.route.default-proxy", new l(proxyHost, proxyPort));
            String proxyUsername = config.getProxyUsername();
            String proxyPassword = config.getProxyPassword();
            String proxyDomain = config.getProxyDomain();
            BasicHttpParams basicHttpParams2 = basicHttpParams;
            String proxyWorkstation = config.getProxyWorkstation();
            if (proxyUsername == null || proxyPassword == null) {
                int i3 = socketSendBufferSizeHint;
            } else {
                int i4 = socketReceiveBufferSizeHint;
                int i5 = socketSendBufferSizeHint;
                httpClient.getCredentialsProvider().a(new org.apache.http.auth.g(proxyHost, proxyPort), new n(proxyUsername, proxyPassword, proxyWorkstation, proxyDomain));
            }
        }
        return httpClient;
    }

    public static final class LocationHeaderNotRequiredRedirectHandler extends q {
        private LocationHeaderNotRequiredRedirectHandler() {
        }

        public boolean isRedirectRequested(org.apache.http.q response, org.apache.http.protocol.f context) {
            int statusCode = response.j().getStatusCode();
            if (response.u(FirebaseAnalytics.Param.LOCATION) == null && statusCode == 301) {
                return false;
            }
            return super.isRedirectRequested(response, context);
        }
    }
}
