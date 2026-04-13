package com.didichuxing.doraemonkit.okgo.cache.policy;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import okhttp3.d0;

public class NoCachePolicy<T> extends BaseCachePolicy<T> {
    public NoCachePolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    public void onSuccess(final Response<T> success) {
        runOnUiThread(new Runnable() {
            public void run() {
                NoCachePolicy.this.mCallback.onSuccess(success);
                NoCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    public void onError(final Response<T> error) {
        runOnUiThread(new Runnable() {
            public void run() {
                NoCachePolicy.this.mCallback.onError(error);
                NoCachePolicy.this.mCallback.onFinish();
            }
        });
    }

    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
            return requestNetworkSync();
        } catch (Throwable throwable) {
            return Response.error(false, this.rawCall, (d0) null, throwable);
        }
    }

    public void requestAsync(CacheEntity<T> cacheEntity, Callback<T> callback) {
        this.mCallback = callback;
        runOnUiThread(new Runnable() {
            public void run() {
                NoCachePolicy noCachePolicy = NoCachePolicy.this;
                noCachePolicy.mCallback.onStart(noCachePolicy.request);
                try {
                    NoCachePolicy.this.prepareRawCall();
                    NoCachePolicy.this.requestNetworkAsync();
                } catch (Throwable throwable) {
                    NoCachePolicy.this.mCallback.onError(Response.error(false, NoCachePolicy.this.rawCall, (d0) null, throwable));
                }
            }
        });
    }
}
