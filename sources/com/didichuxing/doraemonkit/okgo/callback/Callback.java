package com.didichuxing.doraemonkit.okgo.callback;

import com.didichuxing.doraemonkit.okgo.convert.Converter;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;

public interface Callback<T> extends Converter<T> {
    void downloadProgress(Progress progress);

    void onCacheSuccess(Response<T> response);

    void onError(Response<T> response);

    void onFinish();

    void onStart(Request<T, ? extends Request> request);

    void onSuccess(Response<T> response);

    void uploadProgress(Progress progress);
}
