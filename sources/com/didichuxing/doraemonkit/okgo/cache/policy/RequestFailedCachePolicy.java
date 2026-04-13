package com.didichuxing.doraemonkit.okgo.cache.policy;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import okhttp3.d0;

public class RequestFailedCachePolicy<T> extends BaseCachePolicy<T> {
    public RequestFailedCachePolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    public void onSuccess(final Response<T> success) {
        runOnUiThread(new Runnable() {
            public void run() {
                RequestFailedCachePolicy.this.mCallback.onSuccess(success);
                RequestFailedCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    public void onError(final Response<T> error) {
        CacheEntity<T> cacheEntity = this.cacheEntity;
        if (cacheEntity != null) {
            final Response<T> cacheSuccess = Response.success(true, cacheEntity.getData(), error.getRawCall(), error.getRawResponse());
            runOnUiThread(new Runnable() {
                public void run() {
                    RequestFailedCachePolicy.this.mCallback.onCacheSuccess(cacheSuccess);
                    RequestFailedCachePolicy.this.mCallback.onFinish();
                }
            });
            return;
        }
        runOnUiThread(new Runnable() {
            public void run() {
                RequestFailedCachePolicy.this.mCallback.onError(error);
                RequestFailedCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            Response<T> response = requestNetworkSync();
            if (response.isSuccessful() || cacheEntity == null) {
                return response;
            }
            return Response.success(true, cacheEntity.getData(), this.rawCall, response.getRawResponse());
        } catch (Throwable throwable) {
            return Response.error(false, this.rawCall, (d0) null, throwable);
        }
    }

    public void requestAsync(CacheEntity<T> cacheEntity, Callback<T> callback) {
        this.mCallback = callback;
        runOnUiThread(new Runnable() {
            public void run() {
                RequestFailedCachePolicy requestFailedCachePolicy = RequestFailedCachePolicy.this;
                requestFailedCachePolicy.mCallback.onStart(requestFailedCachePolicy.request);
                try {
                    RequestFailedCachePolicy.this.prepareRawCall();
                    RequestFailedCachePolicy.this.requestNetworkAsync();
                } catch (Throwable throwable) {
                    RequestFailedCachePolicy.this.mCallback.onError(Response.error(false, RequestFailedCachePolicy.this.rawCall, (d0) null, throwable));
                }
            }
        });
    }
}
