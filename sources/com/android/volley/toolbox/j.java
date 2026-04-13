package com.android.volley.toolbox;

import androidx.annotation.VisibleForTesting;
import com.android.volley.Header;
import com.android.volley.e;
import com.android.volley.i;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HurlStack */
public class j extends b {
    private final b a;
    private final SSLSocketFactory b;

    /* compiled from: HurlStack */
    public interface b extends q {
    }

    public j() {
        this((b) null);
    }

    public j(b urlRewriter) {
        this(urlRewriter, (SSLSocketFactory) null);
    }

    public j(b urlRewriter, SSLSocketFactory sslSocketFactory) {
        this.a = urlRewriter;
        this.b = sslSocketFactory;
    }

    public h b(i<?> request, Map<String, String> additionalHeaders) {
        String url = request.getUrl();
        HashMap<String, String> map = new HashMap<>();
        map.putAll(additionalHeaders);
        map.putAll(request.getHeaders());
        b bVar = this.a;
        if (bVar != null) {
            String rewritten = bVar.a(url);
            if (rewritten != null) {
                url = rewritten;
            } else {
                throw new IOException("URL blocked by rewriter: " + url);
            }
        }
        HttpURLConnection connection = l(new URL(url), request);
        boolean keepConnectionOpen = false;
        try {
            for (String headerName : map.keySet()) {
                connection.setRequestProperty(headerName, map.get(headerName));
            }
            m(connection, request);
            int responseCode = connection.getResponseCode();
            if (responseCode == -1) {
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            } else if (!j(request.getMethod(), responseCode)) {
                return new h(responseCode, f(connection.getHeaderFields()));
            } else {
                keepConnectionOpen = true;
                h hVar = new h(responseCode, f(connection.getHeaderFields()), connection.getContentLength(), h(request, connection));
                if (!keepConnectionOpen) {
                    connection.disconnect();
                }
                return hVar;
            }
        } finally {
            if (!keepConnectionOpen) {
                connection.disconnect();
            }
        }
    }

    @VisibleForTesting
    static List<e> f(Map<String, List<String>> responseHeaders) {
        List<Header> headerList = new ArrayList<>(responseHeaders.size());
        for (Map.Entry<String, List<String>> entry : responseHeaders.entrySet()) {
            if (entry.getKey() != null) {
                for (String value : entry.getValue()) {
                    headerList.add(new e(entry.getKey(), value));
                }
            }
        }
        return headerList;
    }

    private static boolean j(int requestMethod, int responseCode) {
        return (requestMethod == 4 || (100 <= responseCode && responseCode < 200) || responseCode == 204 || responseCode == 304) ? false : true;
    }

    /* compiled from: HurlStack */
    public static class a extends FilterInputStream {
        private final HttpURLConnection c;

        a(HttpURLConnection connection) {
            super(j.k(connection));
            this.c = connection;
        }

        public void close() {
            super.close();
            this.c.disconnect();
        }
    }

    /* access modifiers changed from: protected */
    public InputStream h(i<?> iVar, HttpURLConnection connection) {
        return new a(connection);
    }

    /* access modifiers changed from: private */
    public static InputStream k(HttpURLConnection connection) {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            return connection.getErrorStream();
        }
    }

    /* access modifiers changed from: protected */
    public HttpURLConnection g(URL url) {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        return connection;
    }

    private HttpURLConnection l(URL url, i<?> request) {
        SSLSocketFactory sSLSocketFactory;
        HttpURLConnection connection = g(url);
        int timeoutMs = request.getTimeoutMs();
        connection.setConnectTimeout(timeoutMs);
        connection.setReadTimeout(timeoutMs);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        if ("https".equals(url.getProtocol()) && (sSLSocketFactory = this.b) != null) {
            ((HttpsURLConnection) connection).setSSLSocketFactory(sSLSocketFactory);
        }
        return connection;
    }

    /* access modifiers changed from: package-private */
    public void m(HttpURLConnection connection, i<?> request) {
        switch (request.getMethod()) {
            case -1:
                byte[] postBody = request.getPostBody();
                if (postBody != null) {
                    connection.setRequestMethod(Constants.POST);
                    d(connection, request, postBody);
                    return;
                }
                return;
            case 0:
                connection.setRequestMethod(Constants.GET);
                return;
            case 1:
                connection.setRequestMethod(Constants.POST);
                e(connection, request);
                return;
            case 2:
                connection.setRequestMethod("PUT");
                e(connection, request);
                return;
            case 3:
                connection.setRequestMethod("DELETE");
                return;
            case 4:
                connection.setRequestMethod(Constants.HEAD);
                return;
            case 5:
                connection.setRequestMethod("OPTIONS");
                return;
            case 6:
                connection.setRequestMethod("TRACE");
                return;
            case 7:
                connection.setRequestMethod("PATCH");
                e(connection, request);
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private void e(HttpURLConnection connection, i<?> request) {
        byte[] body = request.getBody();
        if (body != null) {
            d(connection, request, body);
        }
    }

    private void d(HttpURLConnection connection, i<?> request, byte[] body) {
        connection.setDoOutput(true);
        if (!connection.getRequestProperties().containsKey("Content-Type")) {
            connection.setRequestProperty("Content-Type", request.getBodyContentType());
        }
        DataOutputStream out = new DataOutputStream(i(request, connection, body.length));
        out.write(body);
        out.close();
    }

    /* access modifiers changed from: protected */
    public OutputStream i(i<?> iVar, HttpURLConnection connection, int length) {
        return connection.getOutputStream();
    }
}
