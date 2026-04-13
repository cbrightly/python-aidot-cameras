package com.didichuxing.doraemonkit.okgo.adapter;

import com.didichuxing.doraemonkit.okgo.cache.CacheMode;
import com.didichuxing.doraemonkit.okgo.cache.policy.CachePolicy;
import com.didichuxing.doraemonkit.okgo.cache.policy.DefaultCachePolicy;
import com.didichuxing.doraemonkit.okgo.cache.policy.FirstCacheRequestPolicy;
import com.didichuxing.doraemonkit.okgo.cache.policy.NoCachePolicy;
import com.didichuxing.doraemonkit.okgo.cache.policy.NoneCacheRequestPolicy;
import com.didichuxing.doraemonkit.okgo.cache.policy.RequestFailedCachePolicy;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import com.didichuxing.doraemonkit.okgo.utils.HttpUtils;

public class CacheCall<T> implements Call<T> {
    private CachePolicy<T> policy = null;
    private Request<T, ? extends Request> request;

    public CacheCall(Request<T, ? extends Request> request2) {
        this.request = request2;
        this.policy = preparePolicy();
    }

    public Response<T> execute() {
        return this.policy.requestSync(this.policy.prepareCache());
    }

    public void execute(Callback<T> callback) {
        HttpUtils.checkNotNull(callback, "callback == null");
        this.policy.requestAsync(this.policy.prepareCache(), callback);
    }

    /* renamed from: com.didichuxing.doraemonkit.okgo.adapter.CacheCall$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$didichuxing$doraemonkit$okgo$cache$CacheMode;

        static {
            int[] iArr = new int[CacheMode.values().length];
            $SwitchMap$com$didichuxing$doraemonkit$okgo$cache$CacheMode = iArr;
            try {
                iArr[CacheMode.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$okgo$cache$CacheMode[CacheMode.NO_CACHE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$okgo$cache$CacheMode[CacheMode.IF_NONE_CACHE_REQUEST.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$okgo$cache$CacheMode[CacheMode.FIRST_CACHE_THEN_REQUEST.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$okgo$cache$CacheMode[CacheMode.REQUEST_FAILED_READ_CACHE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private CachePolicy<T> preparePolicy() {
        switch (AnonymousClass1.$SwitchMap$com$didichuxing$doraemonkit$okgo$cache$CacheMode[this.request.getCacheMode().ordinal()]) {
            case 1:
                this.policy = new DefaultCachePolicy(this.request);
                break;
            case 2:
                this.policy = new NoCachePolicy(this.request);
                break;
            case 3:
                this.policy = new NoneCacheRequestPolicy(this.request);
                break;
            case 4:
                this.policy = new FirstCacheRequestPolicy(this.request);
                break;
            case 5:
                this.policy = new RequestFailedCachePolicy(this.request);
                break;
        }
        if (this.request.getCachePolicy() != null) {
            this.policy = this.request.getCachePolicy();
        }
        HttpUtils.checkNotNull(this.policy, "policy == null");
        return this.policy;
    }

    public boolean isExecuted() {
        return this.policy.isExecuted();
    }

    public void cancel() {
        this.policy.cancel();
    }

    public boolean isCanceled() {
        return this.policy.isCanceled();
    }

    public Call<T> clone() {
        return new CacheCall(this.request);
    }

    public Request getRequest() {
        return this.request;
    }
}
