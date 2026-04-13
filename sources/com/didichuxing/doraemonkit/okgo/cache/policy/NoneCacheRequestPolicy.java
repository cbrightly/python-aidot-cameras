package com.didichuxing.doraemonkit.okgo.cache.policy;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import okhttp3.d0;

public class NoneCacheRequestPolicy<T> extends BaseCachePolicy<T> {
    public NoneCacheRequestPolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    public void onSuccess(final Response<T> success) {
        runOnUiThread(new Runnable() {
            public void run() {
                NoneCacheRequestPolicy.this.mCallback.onSuccess(success);
                NoneCacheRequestPolicy.this.mCallback.onFinish();
            }
        });
    }

    public void onError(final Response<T> error) {
        runOnUiThread(new Runnable() {
            public void run() {
                NoneCacheRequestPolicy.this.mCallback.onError(error);
                NoneCacheRequestPolicy.this.mCallback.onFinish();
            }
        });
    }

    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            Response<T> response = null;
            if (cacheEntity != null) {
                response = Response.success(true, cacheEntity.getData(), this.rawCall, (d0) null);
            }
            if (response == null) {
                return requestNetworkSync();
            }
            return response;
        } catch (Throwable throwable) {
            return Response.error(false, this.rawCall, (d0) null, throwable);
        }
    }

    public void requestAsync(final CacheEntity<T> cacheEntity, Callback<T> callback) {
        this.mCallback = callback;
        runOnUiThread(new Runnable() {
            public void run() {
                NoneCacheRequestPolicy noneCacheRequestPolicy = NoneCacheRequestPolicy.this;
                noneCacheRequestPolicy.mCallback.onStart(noneCacheRequestPolicy.request);
                try {
                    NoneCacheRequestPolicy.this.prepareRawCall();
                    CacheEntity cacheEntity = cacheEntity;
                    if (cacheEntity != null) {
                        NoneCacheRequestPolicy.this.mCallback.onCacheSuccess(Response.success(true, cacheEntity.getData(), NoneCacheRequestPolicy.this.rawCall, (d0) null));
                        NoneCacheRequestPolicy.this.mCallback.onFinish();
                        return;
                    }
                    NoneCacheRequestPolicy.this.requestNetworkAsync();
                } catch (Throwable throwable) {
                    NoneCacheRequestPolicy.this.mCallback.onError(Response.error(false, NoneCacheRequestPolicy.this.rawCall, (d0) null, throwable));
                }
            }
        });
    }
}
