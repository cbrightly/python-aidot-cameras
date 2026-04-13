package com.amazonaws.kinesisvideo.http;

import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import org.apache.http.client.methods.c;
import org.apache.http.client.methods.k;
import org.apache.http.client.methods.p;
import org.apache.http.config.f;
import org.apache.http.conn.ssl.e;
import org.apache.http.impl.client.i;
import org.apache.http.impl.client.z;
import org.apache.http.ssl.a;

public final class KinesisVideoApacheHttpClient implements HttpClient {
    private static final int DEFAULT_CONNECTION_TIMEOUT_IN_MILLIS = 10000;
    private static final int DEFAULT_SOCKET_TIMEOUT_IN_MILLIS = 10000;
    private static final String HOST_HEADER = "Host";
    private final Builder mBuilder;
    private final i mHttpClient;

    private KinesisVideoApacheHttpClient(Builder builder) {
        this.mBuilder = builder;
        this.mHttpClient = buildHttpClient();
    }

    public static Builder builder() {
        return new Builder();
    }

    public c executeRequest() {
        k request = new k(this.mBuilder.mUri);
        for (Map.Entry<String, String> entry : this.mBuilder.mHeaders.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }
        try {
            request.l(new org.apache.http.entity.i(this.mBuilder.mContentInJson, this.mBuilder.mContentType.getCharset().toString()));
            return this.mHttpClient.execute((p) request);
        } catch (IOException e) {
            throw new RuntimeException("Exception while doing a POST on the URI " + this.mBuilder.mUri, e);
        }
    }

    private i buildHttpClient() {
        a builder = new a();
        try {
            builder.d(new TrustAllStrategy());
            return z.a().h(new e(builder.a(), new String[]{"TLSv1.2"}, (String[]) null, (HostnameVerifier) new NoOpHostNameVerifier())).f(org.apache.http.client.config.a.c().d(this.mBuilder.mConnectionTimeoutInMillis).a()).g(f.b().b(this.mBuilder.mSocketTimeoutInMillis).a()).a();
        } catch (KeyManagementException e) {
            throw new RuntimeException("Exception while building Apache http client", e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Exception while building Apache http client", e2);
        } catch (KeyStoreException e3) {
            throw new RuntimeException("Exception while building Apache http client", e3);
        }
    }

    public void addHeaderUnsafe(String key, String value) {
        this.mBuilder.withHeader(key, value);
    }

    public HttpMethodName getMethod() {
        return this.mBuilder.mMethod;
    }

    public URI getUri() {
        return this.mBuilder.mUri;
    }

    public Map<String, String> getHeaders() {
        return this.mBuilder.mHeaders;
    }

    public InputStream getContent() {
        return new ByteArrayInputStream(this.mBuilder.mContentInJson.getBytes(StandardCharsets.US_ASCII));
    }

    public void close() {
        this.mHttpClient.close();
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public int mConnectionTimeoutInMillis;
        /* access modifiers changed from: private */
        public String mContentInJson;
        /* access modifiers changed from: private */
        public org.apache.http.entity.f mContentType;
        /* access modifiers changed from: private */
        public final Map<String, String> mHeaders;
        /* access modifiers changed from: private */
        public HttpMethodName mMethod;
        /* access modifiers changed from: private */
        public int mSocketTimeoutInMillis;
        /* access modifiers changed from: private */
        public URI mUri;

        private Builder() {
            this.mHeaders = new HashMap();
            this.mConnectionTimeoutInMillis = 10000;
            this.mSocketTimeoutInMillis = 10000;
        }

        public Builder withUri(URI uri) {
            this.mUri = uri;
            this.mHeaders.put("Host", uri.getHost());
            return this;
        }

        public Builder withMethod(HttpMethodName method) {
            this.mMethod = method;
            return this;
        }

        public Builder withHeader(String key, String value) {
            this.mHeaders.put(key, value);
            return this;
        }

        public Builder withConnectionTimeoutInMillis(int connectionTimeoutInMillis) {
            this.mConnectionTimeoutInMillis = connectionTimeoutInMillis;
            return this;
        }

        public Builder withSocketTimeoutInMillis(int socketTimeoutInMillis) {
            this.mSocketTimeoutInMillis = socketTimeoutInMillis;
            return this;
        }

        public Builder withContentType(org.apache.http.entity.f contentType) {
            this.mContentType = contentType;
            return this;
        }

        public Builder withContentInJson(String contentInJson) {
            this.mContentInJson = contentInJson;
            return this;
        }

        public KinesisVideoApacheHttpClient build() {
            Preconditions.checkNotNull(this.mUri);
            return new KinesisVideoApacheHttpClient(this);
        }
    }

    public static class TrustAllStrategy implements org.apache.http.ssl.c {
        private TrustAllStrategy() {
        }

        public boolean isTrusted(X509Certificate[] chain, String authType) {
            return true;
        }
    }

    public static class NoOpHostNameVerifier implements HostnameVerifier {
        private NoOpHostNameVerifier() {
        }

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
