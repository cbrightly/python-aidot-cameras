package com.amazonaws.async;

public interface Callback<R> {
    void onError(Exception exc);

    void onResult(R r);
}
