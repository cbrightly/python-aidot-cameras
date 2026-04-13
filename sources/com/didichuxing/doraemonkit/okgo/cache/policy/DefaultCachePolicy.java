package com.didichuxing.doraemonkit.okgo.cache.policy;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.exception.CacheException;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import okhttp3.d0;
import okhttp3.e;

public class DefaultCachePolicy<T> extends BaseCachePolicy<T> {
    public DefaultCachePolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    public void onSuccess(final Response<T> success) {
        runOnUiThread(new Runnable() {
            public void run() {
                DefaultCachePolicy.this.mCallback.onSuccess(success);
                DefaultCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    public void onError(final Response<T> error) {
        runOnUiThread(new Runnable() {
            public void run() {
                DefaultCachePolicy.this.mCallback.onError(error);
                DefaultCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    public boolean onAnalysisResponse(e call, d0 response) {
        if (response.j() != 304) {
            return false;
        }
        CacheEntity<T> cacheEntity = this.cacheEntity;
        if (cacheEntity == null) {
            final Response<T> error = Response.error(true, call, response, CacheException.NON_AND_304(this.request.getCacheKey()));
            runOnUiThread(new Runnable() {
                public void run() {
                    DefaultCachePolicy.this.mCallback.onError(error);
                    DefaultCachePolicy.this.mCallback.onFinish();
                }
            });
        } else {
            final Response<T> success = Response.success(true, cacheEntity.getData(), call, response);
            runOnUiThread(new Runnable() {
                public void run() {
                    DefaultCachePolicy.this.mCallback.onCacheSuccess(success);
                    DefaultCachePolicy.this.mCallback.onFinish();
                }
            });
        }
        return true;
    }

    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            Response<T> response = requestNetworkSync();
            if (!response.isSuccessful() || response.code() != 304) {
                return response;
            }
            if (cacheEntity == null) {
                return Response.error(true, this.rawCall, response.getRawResponse(), CacheException.NON_AND_304(this.request.getCacheKey()));
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
                DefaultCachePolicy defaultCachePolicy = DefaultCachePolicy.this;
                defaultCachePolicy.mCallback.onStart(defaultCachePolicy.request);
                try {
                    DefaultCachePolicy.this.prepareRawCall();
                    DefaultCachePolicy.this.requestNetworkAsync();
                } catch (Throwable throwable) {
                    DefaultCachePolicy.this.mCallback.onError(Response.error(false, DefaultCachePolicy.this.rawCall, (d0) null, throwable));
                }
            }
        });
    }
}
