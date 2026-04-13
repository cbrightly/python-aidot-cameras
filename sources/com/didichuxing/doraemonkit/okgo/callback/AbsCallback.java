package com.didichuxing.doraemonkit.okgo.callback;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import com.didichuxing.doraemonkit.okgo.utils.OkLogger;

public abstract class AbsCallback<T> implements Callback<T> {
    public void onStart(Request<T, ? extends Request> request) {
    }

    public void onCacheSuccess(Response<T> response) {
    }

    public void onError(Response<T> response) {
        OkLogger.printStackTrace(response.getException());
    }

    public void onFinish() {
    }

    public void uploadProgress(Progress progress) {
    }

    public void downloadProgress(Progress progress) {
    }
}
