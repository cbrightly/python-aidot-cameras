package com.amazonaws.http;

public interface HttpResponseHandler<T> {
    T handle(HttpResponse httpResponse);

    boolean needsConnectionLeftOpen();
}
