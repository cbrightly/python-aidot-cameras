package com.didichuxing.doraemonkit.okgo.request.base;

import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.adapter.AdapterParam;
import com.didichuxing.doraemonkit.okgo.adapter.CacheCall;
import com.didichuxing.doraemonkit.okgo.adapter.Call;
import com.didichuxing.doraemonkit.okgo.adapter.CallAdapter;
import com.didichuxing.doraemonkit.okgo.cache.CacheMode;
import com.didichuxing.doraemonkit.okgo.cache.policy.CachePolicy;
import com.didichuxing.doraemonkit.okgo.callback.Callback;
import com.didichuxing.doraemonkit.okgo.convert.Converter;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.okgo.model.HttpMethod;
import com.didichuxing.doraemonkit.okgo.model.HttpParams;
import com.didichuxing.doraemonkit.okgo.request.base.ProgressRequestBody;
import com.didichuxing.doraemonkit.okgo.request.base.Request;
import com.didichuxing.doraemonkit.okgo.utils.HttpUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.z;

public abstract class Request<T, R extends Request> implements Serializable {
    private static final long serialVersionUID = -7174118653689916252L;
    protected String baseUrl;
    protected String cacheKey;
    protected CacheMode cacheMode;
    protected transient CachePolicy<T> cachePolicy;
    protected long cacheTime;
    protected transient Call<T> call;
    protected transient Callback<T> callback;
    protected transient z client;
    protected transient Converter<T> converter;
    protected HttpHeaders headers = new HttpHeaders();
    protected transient b0 mRequest;
    protected HttpParams params = new HttpParams();
    protected int retryCount;
    protected transient Object tag;
    protected transient ProgressRequestBody.UploadInterceptor uploadInterceptor;
    protected String url;

    public abstract b0 generateRequest(c0 c0Var);

    /* access modifiers changed from: protected */
    public abstract c0 generateRequestBody();

    public abstract HttpMethod getMethod();

    public Request(String url2) {
        this.url = url2;
        this.baseUrl = url2;
        DokitOkGo go = DokitOkGo.getInstance();
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if (!TextUtils.isEmpty(acceptLanguage)) {
            headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        }
        String userAgent = HttpHeaders.getUserAgent();
        if (!TextUtils.isEmpty(userAgent)) {
            headers("User-Agent", userAgent);
        }
        if (go.getCommonParams() != null) {
            params(go.getCommonParams());
        }
        if (go.getCommonHeaders() != null) {
            headers(go.getCommonHeaders());
        }
        this.retryCount = go.getRetryCount();
        this.cacheMode = go.getCacheMode();
        this.cacheTime = go.getCacheTime();
    }

    public R tag(Object tag2) {
        this.tag = tag2;
        return this;
    }

    public R retryCount(int retryCount2) {
        if (retryCount2 >= 0) {
            this.retryCount = retryCount2;
            return this;
        }
        throw new IllegalArgumentException("retryCount must > 0");
    }

    public R client(z client2) {
        HttpUtils.checkNotNull(client2, "OkHttpClient == null");
        this.client = client2;
        return this;
    }

    public R call(Call<T> call2) {
        HttpUtils.checkNotNull(call2, "call == null");
        this.call = call2;
        return this;
    }

    public R converter(Converter<T> converter2) {
        HttpUtils.checkNotNull(converter2, "converter == null");
        this.converter = converter2;
        return this;
    }

    public R cacheMode(CacheMode cacheMode2) {
        this.cacheMode = cacheMode2;
        return this;
    }

    public R cachePolicy(CachePolicy<T> cachePolicy2) {
        HttpUtils.checkNotNull(cachePolicy2, "cachePolicy == null");
        this.cachePolicy = cachePolicy2;
        return this;
    }

    public R cacheKey(String cacheKey2) {
        HttpUtils.checkNotNull(cacheKey2, "cacheKey == null");
        this.cacheKey = cacheKey2;
        return this;
    }

    public R cacheTime(long cacheTime2) {
        if (cacheTime2 <= -1) {
            cacheTime2 = -1;
        }
        this.cacheTime = cacheTime2;
        return this;
    }

    public R headers(HttpHeaders headers2) {
        this.headers.put(headers2);
        return this;
    }

    public R headers(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public R removeHeader(String key) {
        this.headers.remove(key);
        return this;
    }

    public R removeAllHeaders() {
        this.headers.clear();
        return this;
    }

    public R params(HttpParams params2) {
        this.params.put(params2);
        return this;
    }

    public R params(Map<String, String> params2, boolean... isReplace) {
        this.params.put(params2, isReplace);
        return this;
    }

    public R params(String key, String value, boolean... isReplace) {
        this.params.put(key, value, isReplace);
        return this;
    }

    public R params(String key, int value, boolean... isReplace) {
        this.params.put(key, value, isReplace);
        return this;
    }

    public R params(String key, float value, boolean... isReplace) {
        this.params.put(key, value, isReplace);
        return this;
    }

    public R params(String key, double value, boolean... isReplace) {
        this.params.put(key, value, isReplace);
        return this;
    }

    public R params(String key, long value, boolean... isReplace) {
        this.params.put(key, value, isReplace);
        return this;
    }

    public R params(String key, char value, boolean... isReplace) {
        this.params.put(key, value, isReplace);
        return this;
    }

    public R params(String key, boolean value, boolean... isReplace) {
        this.params.put(key, value, isReplace);
        return this;
    }

    public R addUrlParams(String key, List<String> values) {
        this.params.putUrlParams(key, values);
        return this;
    }

    public R removeParam(String key) {
        this.params.remove(key);
        return this;
    }

    public R removeAllParams() {
        this.params.clear();
        return this;
    }

    public R uploadInterceptor(ProgressRequestBody.UploadInterceptor uploadInterceptor2) {
        this.uploadInterceptor = uploadInterceptor2;
        return this;
    }

    public String getUrlParam(String key) {
        List<String> values = this.params.urlParamsMap.get(key);
        if (values == null || values.size() <= 0) {
            return null;
        }
        return values.get(0);
    }

    public HttpParams.FileWrapper getFileParam(String key) {
        List<HttpParams.FileWrapper> values = this.params.fileParamsMap.get(key);
        if (values == null || values.size() <= 0) {
            return null;
        }
        return values.get(0);
    }

    public HttpParams getParams() {
        return this.params;
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }

    public String getUrl() {
        return this.url;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public Object getTag() {
        return this.tag;
    }

    public CacheMode getCacheMode() {
        return this.cacheMode;
    }

    public CachePolicy<T> getCachePolicy() {
        return this.cachePolicy;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public long getCacheTime() {
        return this.cacheTime;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public b0 getRequest() {
        return this.mRequest;
    }

    public void setCallback(Callback<T> callback2) {
        this.callback = callback2;
    }

    public Converter<T> getConverter() {
        if (this.converter == null) {
            this.converter = this.callback;
        }
        HttpUtils.checkNotNull(this.converter, "converter == null, do you forget to call Request#converter(Converter<T>) ?");
        return this.converter;
    }

    public e getRawCall() {
        c0 requestBody = generateRequestBody();
        if (requestBody != null) {
            ProgressRequestBody<T> progressRequestBody = new ProgressRequestBody<>(requestBody, this.callback);
            progressRequestBody.setInterceptor(this.uploadInterceptor);
            this.mRequest = generateRequest(progressRequestBody);
        } else {
            this.mRequest = generateRequest((c0) null);
        }
        if (this.client == null) {
            this.client = DokitOkGo.getInstance().getOkHttpClient();
        }
        return this.client.a(this.mRequest);
    }

    public Call<T> adapt() {
        Call<T> call2 = this.call;
        if (call2 == null) {
            return new CacheCall(this);
        }
        return call2;
    }

    public <E> E adapt(CallAdapter<T, E> adapter) {
        Call<T> innerCall = this.call;
        if (innerCall == null) {
            innerCall = new CacheCall<>(this);
        }
        return adapter.adapt(innerCall, (AdapterParam) null);
    }

    public <E> E adapt(AdapterParam param, CallAdapter<T, E> adapter) {
        Call<T> innerCall = this.call;
        if (innerCall == null) {
            innerCall = new CacheCall<>(this);
        }
        return adapter.adapt(innerCall, param);
    }

    public d0 execute() {
        return getRawCall().execute();
    }

    public void execute(Callback<T> callback2) {
        HttpUtils.checkNotNull(callback2, "callback == null");
        this.callback = callback2;
        adapt().execute(callback2);
    }
}
