package com.didichuxing.doraemonkit.okgo.cache.policy;

import android.graphics.Bitmap;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.cache.CacheMode;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.db.CacheManager;
import com.didichuxing.doraemonkit.okgo.exception.HttpException;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import com.didichuxing.doraemonkit.okgo.utils.HeaderParser;
import com.didichuxing.doraemonkit.okgo.utils.HttpUtils;
import java.io.IOException;
import java.net.SocketTimeoutException;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.f;
import okhttp3.u;

public abstract class BaseCachePolicy<T> implements CachePolicy<T> {
    protected CacheEntity<T> cacheEntity;
    protected volatile boolean canceled;
    protected volatile int currentRetryCount = 0;
    protected boolean executed;
    protected Callback<T> mCallback;
    protected e rawCall;
    protected Request<T, ? extends Request> request;

    public BaseCachePolicy(Request<T, ? extends Request> request2) {
        this.request = request2;
    }

    public boolean onAnalysisResponse(e call, d0 response) {
        return false;
    }

    public CacheEntity<T> prepareCache() {
        if (this.request.getCacheKey() == null) {
            Request<T, ? extends Request> request2 = this.request;
            request2.cacheKey(HttpUtils.createUrlFromParams(request2.getBaseUrl(), this.request.getParams().urlParamsMap));
        }
        if (this.request.getCacheMode() == null) {
            this.request.cacheMode(CacheMode.NO_CACHE);
        }
        CacheMode cacheMode = this.request.getCacheMode();
        if (cacheMode != CacheMode.NO_CACHE) {
            CacheEntity<?> cacheEntity2 = CacheManager.getInstance().get(this.request.getCacheKey());
            this.cacheEntity = cacheEntity2;
            HeaderParser.addCacheHeaders(this.request, cacheEntity2, cacheMode);
            CacheEntity<T> cacheEntity3 = this.cacheEntity;
            if (cacheEntity3 != null) {
                if (cacheEntity3.checkExpire(cacheMode, this.request.getCacheTime(), System.currentTimeMillis())) {
                    this.cacheEntity.setExpire(true);
                }
            }
        }
        CacheEntity<T> cacheEntity4 = this.cacheEntity;
        if (cacheEntity4 == null || cacheEntity4.isExpire() || this.cacheEntity.getData() == null || this.cacheEntity.getResponseHeaders() == null) {
            this.cacheEntity = null;
        }
        return this.cacheEntity;
    }

    public synchronized e prepareRawCall() {
        if (!this.executed) {
            this.executed = true;
            this.rawCall = this.request.getRawCall();
            if (this.canceled) {
                this.rawCall.cancel();
            }
        } else {
            throw HttpException.COMMON("Already executed!");
        }
        return this.rawCall;
    }

    /* access modifiers changed from: protected */
    public Response<T> requestNetworkSync() {
        try {
            d0 response = this.rawCall.execute();
            int responseCode = response.j();
            if (responseCode != 404) {
                if (responseCode < 500) {
                    T body = this.request.getConverter().convertResponse(response);
                    saveCache(response.s(), body);
                    return Response.success(false, body, this.rawCall, response);
                }
            }
            return Response.error(false, this.rawCall, response, HttpException.NET_ERROR());
        } catch (Throwable throwable) {
            if ((throwable instanceof SocketTimeoutException) && this.currentRetryCount < this.request.getRetryCount()) {
                this.currentRetryCount++;
                this.rawCall = this.request.getRawCall();
                if (this.canceled) {
                    this.rawCall.cancel();
                } else {
                    requestNetworkSync();
                }
            }
            return Response.error(false, this.rawCall, (d0) null, throwable);
        }
    }

    /* access modifiers changed from: protected */
    public void requestNetworkAsync() {
        this.rawCall.o0(new f() {
            public void onFailure(e call, IOException e) {
                if ((e instanceof SocketTimeoutException) && BaseCachePolicy.this.currentRetryCount < BaseCachePolicy.this.request.getRetryCount()) {
                    BaseCachePolicy.this.currentRetryCount++;
                    BaseCachePolicy baseCachePolicy = BaseCachePolicy.this;
                    baseCachePolicy.rawCall = baseCachePolicy.request.getRawCall();
                    if (BaseCachePolicy.this.canceled) {
                        BaseCachePolicy.this.rawCall.cancel();
                    } else {
                        BaseCachePolicy.this.rawCall.o0(this);
                    }
                } else if (!call.isCanceled()) {
                    BaseCachePolicy.this.onError(Response.error(false, call, (d0) null, e));
                }
            }

            public void onResponse(e call, d0 response) {
                int responseCode = response.j();
                if (responseCode == 404 || responseCode >= 500) {
                    BaseCachePolicy.this.onError(Response.error(false, call, response, HttpException.NET_ERROR()));
                } else if (!BaseCachePolicy.this.onAnalysisResponse(call, response)) {
                    try {
                        T body = BaseCachePolicy.this.request.getConverter().convertResponse(response);
                        BaseCachePolicy.this.saveCache(response.s(), body);
                        BaseCachePolicy.this.onSuccess(Response.success(false, body, call, response));
                    } catch (Throwable throwable) {
                        BaseCachePolicy.this.onError(Response.error(false, call, response, throwable));
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveCache(u headers, T data) {
        if (this.request.getCacheMode() != CacheMode.NO_CACHE && !(data instanceof Bitmap)) {
            CacheEntity<T> cache = HeaderParser.createCacheEntity(headers, data, this.request.getCacheMode(), this.request.getCacheKey());
            if (cache == null) {
                CacheManager.getInstance().remove(this.request.getCacheKey());
            } else {
                CacheManager.getInstance().replace(this.request.getCacheKey(), cache);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void runOnUiThread(Runnable run) {
        DokitOkGo.getInstance().getDelivery().post(run);
    }

    public boolean isExecuted() {
        return this.executed;
    }

    public void cancel() {
        this.canceled = true;
        e eVar = this.rawCall;
        if (eVar != null) {
            eVar.cancel();
        }
    }

    public boolean isCanceled() {
        boolean z = true;
        if (this.canceled) {
            return true;
        }
        synchronized (this) {
            e eVar = this.rawCall;
            if (eVar == null || !eVar.isCanceled()) {
                z = false;
            }
        }
        return z;
    }
}
