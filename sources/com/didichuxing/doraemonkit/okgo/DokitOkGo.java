package com.didichuxing.doraemonkit.okgo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.didichuxing.doraemonkit.okgo.cache.CacheMode;
import com.didichuxing.doraemonkit.okgo.cookie.CookieJarImpl;
import com.didichuxing.doraemonkit.okgo.https.HttpsUtils;
import com.didichuxing.doraemonkit.okgo.interceptor.HttpLoggingInterceptor;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.okgo.model.HttpParams;
import com.didichuxing.doraemonkit.okgo.request.DeleteRequest;
import com.didichuxing.doraemonkit.okgo.request.GetRequest;
import com.didichuxing.doraemonkit.okgo.request.HeadRequest;
import com.didichuxing.doraemonkit.okgo.request.OptionsRequest;
import com.didichuxing.doraemonkit.okgo.request.PatchRequest;
import com.didichuxing.doraemonkit.okgo.request.PostRequest;
import com.didichuxing.doraemonkit.okgo.request.PutRequest;
import com.didichuxing.doraemonkit.okgo.request.TraceRequest;
import com.didichuxing.doraemonkit.okgo.utils.HttpUtils;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import okhttp3.e;
import okhttp3.z;

@Deprecated
public class DokitOkGo {
    public static final long DEFAULT_MILLISECONDS = 60000;
    public static long REFRESH_TIME = 300;
    private Application context;
    private CacheMode mCacheMode;
    private long mCacheTime;
    private HttpHeaders mCommonHeaders;
    private HttpParams mCommonParams;
    private Handler mDelivery;
    private int mRetryCount;
    private z okHttpClient;

    private DokitOkGo() {
        this.mDelivery = new Handler(Looper.getMainLooper());
        this.mRetryCount = 3;
        this.mCacheTime = -1;
        this.mCacheMode = CacheMode.NO_CACHE;
        z.a builder = new z.a();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.a(loggingInterceptor);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        builder.R(60000, timeUnit);
        builder.V(60000, timeUnit);
        builder.e(60000, timeUnit);
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.U(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.M(HttpsUtils.UnSafeHostnameVerifier);
        this.okHttpClient = builder.c();
    }

    public static DokitOkGo getInstance() {
        return OkGoHolder.holder;
    }

    public static class OkGoHolder {
        public static DokitOkGo holder = new DokitOkGo();

        private OkGoHolder() {
        }
    }

    public static <T> GetRequest<T> get(String url) {
        return new GetRequest<>(url);
    }

    public static <T> PostRequest<T> post(String url) {
        return new PostRequest<>(url);
    }

    public static <T> PutRequest<T> put(String url) {
        return new PutRequest<>(url);
    }

    public static <T> HeadRequest<T> head(String url) {
        return new HeadRequest<>(url);
    }

    public static <T> DeleteRequest<T> delete(String url) {
        return new DeleteRequest<>(url);
    }

    public static <T> OptionsRequest<T> options(String url) {
        return new OptionsRequest<>(url);
    }

    public static <T> PatchRequest<T> patch(String url) {
        return new PatchRequest<>(url);
    }

    public static <T> TraceRequest<T> trace(String url) {
        return new TraceRequest<>(url);
    }

    public DokitOkGo init(Application app) {
        this.context = app;
        return this;
    }

    public Context getContext() {
        HttpUtils.checkNotNull(this.context, "please call OkGo.getInstance().init() first in application!");
        return this.context;
    }

    public Handler getDelivery() {
        return this.mDelivery;
    }

    public z getOkHttpClient() {
        HttpUtils.checkNotNull(this.okHttpClient, "please call OkGo.getInstance().setOkHttpClient() first in application!");
        return this.okHttpClient;
    }

    public DokitOkGo setOkHttpClient(z okHttpClient2) {
        HttpUtils.checkNotNull(okHttpClient2, "okHttpClient == null");
        this.okHttpClient = okHttpClient2;
        return this;
    }

    public CookieJarImpl getCookieJar() {
        return (CookieJarImpl) this.okHttpClient.n();
    }

    public DokitOkGo setRetryCount(int retryCount) {
        if (retryCount >= 0) {
            this.mRetryCount = retryCount;
            return this;
        }
        throw new IllegalArgumentException("retryCount must > 0");
    }

    public int getRetryCount() {
        return this.mRetryCount;
    }

    public DokitOkGo setCacheMode(CacheMode cacheMode) {
        this.mCacheMode = cacheMode;
        return this;
    }

    public CacheMode getCacheMode() {
        return this.mCacheMode;
    }

    public DokitOkGo setCacheTime(long cacheTime) {
        if (cacheTime <= -1) {
            cacheTime = -1;
        }
        this.mCacheTime = cacheTime;
        return this;
    }

    public long getCacheTime() {
        return this.mCacheTime;
    }

    public HttpParams getCommonParams() {
        return this.mCommonParams;
    }

    public DokitOkGo addCommonParams(HttpParams commonParams) {
        if (this.mCommonParams == null) {
            this.mCommonParams = new HttpParams();
        }
        this.mCommonParams.put(commonParams);
        return this;
    }

    public HttpHeaders getCommonHeaders() {
        return this.mCommonHeaders;
    }

    public DokitOkGo addCommonHeaders(HttpHeaders commonHeaders) {
        if (this.mCommonHeaders == null) {
            this.mCommonHeaders = new HttpHeaders();
        }
        this.mCommonHeaders.put(commonHeaders);
        return this;
    }

    public void cancelTag(Object tag) {
        if (tag != null) {
            for (e call : getOkHttpClient().o().i()) {
                if (tag.equals(call.g().j())) {
                    call.cancel();
                }
            }
            for (e call2 : getOkHttpClient().o().j()) {
                if (tag.equals(call2.g().j())) {
                    call2.cancel();
                }
            }
        }
    }

    public static void cancelTag(z client, Object tag) {
        if (client != null && tag != null) {
            for (e call : client.o().i()) {
                if (tag.equals(call.g().j())) {
                    call.cancel();
                }
            }
            for (e call2 : client.o().j()) {
                if (tag.equals(call2.g().j())) {
                    call2.cancel();
                }
            }
        }
    }

    public void cancelAll() {
        for (e call : getOkHttpClient().o().i()) {
            call.cancel();
        }
        for (e call2 : getOkHttpClient().o().j()) {
            call2.cancel();
        }
    }

    public static void cancelAll(z client) {
        if (client != null) {
            for (e call : client.o().i()) {
                call.cancel();
            }
            for (e call2 : client.o().j()) {
                call2.cancel();
            }
        }
    }
}
