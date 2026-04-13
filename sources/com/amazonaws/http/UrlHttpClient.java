package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.glassfish.grizzly.http.server.Constants;

public class UrlHttpClient implements HttpClient {
    private static final int BUFFER_SIZE_MULTIPLIER = 8;
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final String TAG = "amazonaws";
    private static final Log log = LogFactory.getLog(UrlHttpClient.class);
    private final ClientConfiguration config;
    private SSLContext sc = null;

    public UrlHttpClient(ClientConfiguration config2) {
        this.config = config2;
    }

    public HttpResponse execute(HttpRequest request) {
        HttpURLConnection connection = (HttpURLConnection) request.getUri().toURL().openConnection();
        CurlBuilder curlBuilder = this.config.isCurlLogging() ? new CurlBuilder(request.getUri().toURL()) : null;
        configureConnection(request, connection);
        applyHeadersAndMethod(request, connection, curlBuilder);
        writeContentToConnection(request, connection, curlBuilder);
        if (curlBuilder != null) {
            if (curlBuilder.isValid()) {
                printToLog(curlBuilder.build());
            } else {
                printToLog("Failed to create curl, content too long");
            }
        }
        return createHttpResponse(request, connection);
    }

    /* access modifiers changed from: package-private */
    public HttpResponse createHttpResponse(HttpRequest request, HttpURLConnection connection) {
        String statusText = connection.getResponseMessage();
        int statusCode = connection.getResponseCode();
        InputStream content = connection.getErrorStream();
        if (content == null && !Constants.HEAD.equals(request.getMethod())) {
            try {
                content = connection.getInputStream();
            } catch (IOException e) {
            }
        }
        HttpResponse.Builder builder = HttpResponse.builder().statusCode(statusCode).statusText(statusText).content(content);
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                builder.header(header.getKey(), (String) header.getValue().get(0));
            }
        }
        return builder.build();
    }

    public void shutdown() {
    }

    /* access modifiers changed from: package-private */
    public void writeContentToConnection(HttpRequest request, HttpURLConnection connection) {
        writeContentToConnection(request, connection, (CurlBuilder) null);
    }

    /* access modifiers changed from: package-private */
    public void writeContentToConnection(HttpRequest request, HttpURLConnection connection, CurlBuilder curlBuilder) {
        if (request.getContent() != null && request.getContentLength() >= 0) {
            connection.setDoOutput(true);
            if (!request.isStreaming()) {
                connection.setFixedLengthStreamingMode((int) request.getContentLength());
            }
            OutputStream os = connection.getOutputStream();
            ByteBuffer curlBuffer = null;
            if (curlBuilder != null) {
                if (request.getContentLength() < 2147483647L) {
                    curlBuffer = ByteBuffer.allocate((int) request.getContentLength());
                } else {
                    curlBuilder.setContentOverflow(true);
                }
            }
            write(request.getContent(), os, curlBuilder, curlBuffer);
            if (!(curlBuilder == null || curlBuffer == null || curlBuffer.position() == 0)) {
                curlBuilder.setContent(new String(curlBuffer.array(), "UTF-8"));
            }
            os.flush();
            os.close();
        }
    }

    /* access modifiers changed from: package-private */
    public HttpURLConnection applyHeadersAndMethod(HttpRequest request, HttpURLConnection connection) {
        return applyHeadersAndMethod(request, connection, (CurlBuilder) null);
    }

    /* access modifiers changed from: package-private */
    public HttpURLConnection applyHeadersAndMethod(HttpRequest request, HttpURLConnection connection, CurlBuilder curlBuilder) {
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            if (curlBuilder != null) {
                curlBuilder.setHeaders(request.getHeaders());
            }
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                String key = header.getKey();
                if (!key.equals("Content-Length") && !key.equals("Host")) {
                    key.equals(HttpHeader.EXPECT);
                    connection.setRequestProperty(key, header.getValue());
                }
            }
        }
        String method = request.getMethod();
        connection.setRequestMethod(method);
        if (curlBuilder != null) {
            curlBuilder.setMethod(method);
        }
        return connection;
    }

    /* access modifiers changed from: protected */
    public void printToLog(String message) {
        log.debug(message);
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection getUrlConnection(URL url) {
        return (HttpURLConnection) url.openConnection();
    }

    private void write(InputStream is, OutputStream os, CurlBuilder curlBuilder, ByteBuffer curlBuffer) {
        byte[] buf = new byte[8192];
        while (true) {
            int read = is.read(buf);
            int len = read;
            if (read != -1) {
                if (curlBuffer != null) {
                    try {
                        curlBuffer.put(buf, 0, len);
                    } catch (BufferOverflowException e) {
                        curlBuilder.setContentOverflow(true);
                    }
                }
                os.write(buf, 0, len);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void configureConnection(HttpRequest request, HttpURLConnection connection) {
        connection.setConnectTimeout(this.config.getConnectionTimeout());
        connection.setReadTimeout(this.config.getSocketTimeout());
        connection.setInstanceFollowRedirects(false);
        connection.setUseCaches(false);
        if (request.isStreaming()) {
            connection.setChunkedStreamingMode(0);
        }
        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection https = (HttpsURLConnection) connection;
            if (this.config.getTrustManager() != null) {
                enableCustomTrustManager(https);
            }
        }
    }

    private void enableCustomTrustManager(HttpsURLConnection connection) {
        if (this.sc == null) {
            TrustManager[] customTrustManagers = {this.config.getTrustManager()};
            try {
                SSLContext instance = SSLContext.getInstance("TLS");
                this.sc = instance;
                instance.init((KeyManager[]) null, customTrustManagers, (SecureRandom) null);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }
        connection.setSSLSocketFactory(this.sc.getSocketFactory());
    }

    public final class CurlBuilder {
        private String content = null;
        private boolean contentOverflow = false;
        private final HashMap<String, String> headers = new HashMap<>();
        private String method = null;
        private final URL url;

        public CurlBuilder(URL url2) {
            if (url2 != null) {
                this.url = url2;
                return;
            }
            throw new IllegalArgumentException("Must have a valid url");
        }

        public CurlBuilder setMethod(String method2) {
            this.method = method2;
            return this;
        }

        public CurlBuilder setHeaders(Map<String, String> headers2) {
            this.headers.clear();
            this.headers.putAll(headers2);
            return this;
        }

        public CurlBuilder setContent(String content2) {
            this.content = content2;
            return this;
        }

        public CurlBuilder setContentOverflow(boolean contentOverflow2) {
            this.contentOverflow = contentOverflow2;
            return this;
        }

        public boolean isValid() {
            return !this.contentOverflow;
        }

        public String build() {
            if (isValid()) {
                StringBuilder stringBuilder = new StringBuilder("curl");
                if (this.method != null) {
                    stringBuilder.append(" -X ");
                    stringBuilder.append(this.method);
                }
                for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                    stringBuilder.append(" -H \"");
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append(":");
                    stringBuilder.append(entry.getValue());
                    stringBuilder.append("\"");
                }
                if (this.content != null) {
                    stringBuilder.append(" -d '");
                    stringBuilder.append(this.content);
                    stringBuilder.append("'");
                }
                stringBuilder.append(" ");
                stringBuilder.append(this.url.toString());
                return stringBuilder.toString();
            }
            throw new IllegalStateException("Invalid state, cannot create curl command");
        }
    }
}
