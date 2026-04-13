package com.didichuxing.doraemonkit.okgo.cache.policy;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import okhttp3.d0;
import okhttp3.e;

public interface CachePolicy<T> {
    void cancel();

    boolean isCanceled();

    boolean isExecuted();

    boolean onAnalysisResponse(e eVar, d0 d0Var);

    void onError(Response<T> response);

    void onSuccess(Response<T> response);

    CacheEntity<T> prepareCache();

    e prepareRawCall();

    void requestAsync(CacheEntity<T> cacheEntity, Callback<T> callback);

    Response<T> requestSync(CacheEntity<T> cacheEntity);
}
