package com.amazonaws.kinesisvideo.http;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

public interface HttpClient {
    void close();

    InputStream getContent();

    Map<String, String> getHeaders();

    HttpMethodName getMethod();

    URI getUri();
}
