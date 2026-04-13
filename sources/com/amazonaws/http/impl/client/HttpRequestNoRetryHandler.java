package com.amazonaws.http.impl.client;

import java.io.IOException;
import org.apache.http.impl.client.o;
import org.apache.http.protocol.f;

public final class HttpRequestNoRetryHandler extends o {
    public static final HttpRequestNoRetryHandler Singleton = new HttpRequestNoRetryHandler();

    private HttpRequestNoRetryHandler() {
    }

    public boolean retryRequest(IOException exception, int executionCount, f context) {
        return false;
    }
}
