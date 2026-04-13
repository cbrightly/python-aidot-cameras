package com.didichuxing.doraemonkit.okgo.cache.policy;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import okhttp3.d0;

public class FirstCacheRequestPolicy<T> extends BaseCachePolicy<T> {
    public FirstCacheRequestPolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    public void onSuccess(final Response<T> success) {
        runOnUiThread(new Runnable() {
            public void run() {
                FirstCacheRequestPolicy.this.mCallback.onSuccess(success);
                FirstCacheRequestPolicy.this.mCallback.onFinish();
            }
        });
    }

    public void onError(final Response<T> error) {
        runOnUiThread(new Runnable() {
            public void run() {
                FirstCacheRequestPolicy.this.mCallback.onError(error);
                FirstCacheRequestPolicy.this.mCallback.onFinish();
            }
        });
    }

    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            if (cacheEntity != null) {
                Response.success(true, cacheEntity.getData(), this.rawCall, (d0) null);
            }
            Response<T> response = requestNetworkSync();
            if (response.isSuccessful() || cacheEntity == null) {
                return response;
            }
            return Response.success(true, cacheEntity.getData(), this.rawCall, response.getRawResponse());
        } catch (Throwable throwable) {
            return Response.error(false, this.rawCall, (d0) null, throwable);
        }
    }

    public void requestAsync(final CacheEntity<T> cacheEntity, Callback<T> callback) {
        this.mCallback = callback;
        runOnUiThread(new Runnable() {
            public void run() {
                FirstCacheRequestPolicy firstCacheRequestPolicy = FirstCacheRequestPolicy.this;
                firstCacheRequestPolicy.mCallback.onStart(firstCacheRequestPolicy.request);
                try {
                    FirstCacheRequestPolicy.this.prepareRawCall();
                    CacheEntity cacheEntity = cacheEntity;
                    if (cacheEntity != null) {
                        FirstCacheRequestPolicy.this.mCallback.onCacheSuccess(Response.success(true, cacheEntity.getData(), FirstCacheRequestPolicy.this.rawCall, (d0) null));
                    }
                    FirstCacheRequestPolicy.this.requestNetworkAsync();
                } catch (Throwable throwable) {
                    FirstCacheRequestPolicy.this.mCallback.onError(Response.error(false, FirstCacheRequestPolicy.this.rawCall, (d0) null, throwable));
                }
            }
        });
    }
}
