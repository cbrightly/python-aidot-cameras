package com.didichuxing.doraemonkit.okgo.model;

import okhttp3.d0;
import okhttp3.e;
import okhttp3.u;

public final class Response<T> {
    private T body;
    private boolean isFromCache;
    private e rawCall;
    private d0 rawResponse;
    private Throwable throwable;

    public static <T> Response<T> success(boolean isFromCache2, T body2, e rawCall2, d0 rawResponse2) {
        Response<T> response = new Response<>();
        response.setFromCache(isFromCache2);
        response.setBody(body2);
        response.setRawCall(rawCall2);
        response.setRawResponse(rawResponse2);
        return response;
    }

    public static <T> Response<T> error(boolean isFromCache2, e rawCall2, d0 rawResponse2, Throwable throwable2) {
        Response<T> response = new Response<>();
        response.setFromCache(isFromCache2);
        response.setRawCall(rawCall2);
        response.setRawResponse(rawResponse2);
        response.setException(throwable2);
        return response;
    }

    public int code() {
        d0 d0Var = this.rawResponse;
        if (d0Var == null) {
            return -1;
        }
        return d0Var.j();
    }

    public String message() {
        d0 d0Var = this.rawResponse;
        if (d0Var == null) {
            return null;
        }
        return d0Var.t();
    }

    public u headers() {
        d0 d0Var = this.rawResponse;
        if (d0Var == null) {
            return null;
        }
        return d0Var.s();
    }

    public boolean isSuccessful() {
        return this.throwable == null;
    }

    public void setBody(T body2) {
        this.body = body2;
    }

    public T body() {
        return this.body;
    }

    public Throwable getException() {
        return this.throwable;
    }

    public void setException(Throwable exception) {
        this.throwable = exception;
    }

    public e getRawCall() {
        return this.rawCall;
    }

    public void setRawCall(e rawCall2) {
        this.rawCall = rawCall2;
    }

    public d0 getRawResponse() {
        return this.rawResponse;
    }

    public void setRawResponse(d0 rawResponse2) {
        this.rawResponse = rawResponse2;
    }

    public boolean isFromCache() {
        return this.isFromCache;
    }

    public void setFromCache(boolean fromCache) {
        this.isFromCache = fromCache;
    }
}
