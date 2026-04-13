package com.amazonaws.http;

public interface HttpClient {
    HttpResponse execute(HttpRequest httpRequest);

    void shutdown();
}
