package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.http.HttpResponse;
import java.io.InputStream;
import java.util.Map;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.e;
import org.apache.http.client.methods.i;
import org.apache.http.client.methods.k;
import org.apache.http.client.methods.l;
import org.apache.http.client.methods.p;
import org.apache.http.conn.ssl.g;
import org.apache.http.d;
import org.apache.http.entity.h;
import org.apache.http.impl.client.b;
import org.apache.http.impl.client.o;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.q;
import org.glassfish.grizzly.http.server.Constants;

public class ApacheHttpClient implements HttpClient {
    private final HttpClient httpClient;
    private HttpParams params = null;

    public ApacheHttpClient(ClientConfiguration config) {
        HttpClient createHttpClient = new HttpClientFactory().createHttpClient(config);
        this.httpClient = createHttpClient;
        ((b) createHttpClient).setHttpRequestRetryHandler(new o(0, false));
        ((g) createHttpClient.getConnectionManager().e().b("https").d()).o(g.b);
    }

    public HttpResponse execute(HttpRequest request) {
        q httpResponse = this.httpClient.execute(createHttpRequest(request));
        String statusText = httpResponse.j().getReasonPhrase();
        int statusCode = httpResponse.j().getStatusCode();
        InputStream content = null;
        if (httpResponse.a() != null) {
            content = httpResponse.a().getContent();
        }
        HttpResponse.Builder builder = HttpResponse.builder().statusCode(statusCode).statusText(statusText).content(content);
        for (d header : httpResponse.v()) {
            builder.header(header.getName(), header.getValue());
        }
        return builder.build();
    }

    public void shutdown() {
        this.httpClient.getConnectionManager().shutdown();
    }

    private p createHttpRequest(HttpRequest request) {
        p httpRequest;
        String method = request.getMethod();
        if (Constants.POST.equals(method)) {
            k postRequest = new k(request.getUri());
            httpRequest = postRequest;
            if (request.getContent() != null) {
                postRequest.l(new h(request.getContent(), request.getContentLength()));
                httpRequest = postRequest;
            }
        } else if (Constants.GET.equals(method)) {
            httpRequest = new org.apache.http.client.methods.h(request.getUri());
        } else if ("PUT".equals(method)) {
            l putRequest = new l(request.getUri());
            httpRequest = putRequest;
            if (request.getContent() != null) {
                putRequest.l(new h(request.getContent(), request.getContentLength()));
                httpRequest = putRequest;
            }
        } else if ("DELETE".equals(method)) {
            httpRequest = new e(request.getUri());
        } else if (Constants.HEAD.equals(method)) {
            httpRequest = new i(request.getUri());
        } else {
            throw new UnsupportedOperationException("Unsupported method: " + method);
        }
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                String key = header.getKey();
                if (!key.equals("Content-Length") && !key.equals("Host")) {
                    httpRequest.addHeader(header.getKey(), header.getValue());
                }
            }
        }
        if (this.params == null) {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            this.params = basicHttpParams;
            basicHttpParams.setParameter("http.protocol.handle-redirects", false);
        }
        httpRequest.z(this.params);
        return httpRequest;
    }
}
