package com.amazonaws.http;

import com.amazonaws.util.StringUtils;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

public class HttpRequest {
    private final InputStream content;
    private final Map<String, String> headers;
    private boolean isStreaming;
    private final String method;
    private URI uri;

    public HttpRequest(String method2, URI uri2) {
        this(method2, uri2, (Map<String, String>) null, (InputStream) null);
    }

    public HttpRequest(String method2, URI uri2, Map<String, String> headers2, InputStream content2) {
        Map<String, String> map;
        this.method = StringUtils.upperCase(method2);
        this.uri = uri2;
        if (headers2 == null) {
            map = Collections.EMPTY_MAP;
        } else {
            map = Collections.unmodifiableMap(headers2);
        }
        this.headers = map;
        this.content = content2;
    }

    public String getMethod() {
        return this.method;
    }

    public URI getUri() {
        return this.uri;
    }

    /* access modifiers changed from: package-private */
    public void setUri(URI uri2) {
        this.uri = uri2;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public InputStream getContent() {
        return this.content;
    }

    public long getContentLength() {
        String len;
        Map<String, String> map = this.headers;
        if (map == null || (len = map.get("Content-Length")) == null || len.isEmpty()) {
            return 0;
        }
        return Long.valueOf(len).longValue();
    }

    public boolean isStreaming() {
        return this.isStreaming;
    }

    public void setStreaming(boolean isStreaming2) {
        this.isStreaming = isStreaming2;
    }
}
