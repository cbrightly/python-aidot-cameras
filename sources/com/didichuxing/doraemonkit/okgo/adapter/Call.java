package com.didichuxing.doraemonkit.okgo.adapter;

import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;

public interface Call<T> {
    void cancel();

    Call<T> clone();

    Response<T> execute();

    void execute(Callback<T> callback);

    Request getRequest();

    boolean isCanceled();

    boolean isExecuted();
}
