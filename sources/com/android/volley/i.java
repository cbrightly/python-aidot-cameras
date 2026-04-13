package com.android.volley;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.annotation.CallSuper;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.android.volley.a;
import com.android.volley.k;
import com.android.volley.n;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

/* compiled from: Request */
public abstract class i<T> implements Comparable<i<T>> {
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    @Nullable
    private a.C0017a mCacheEntry;
    @GuardedBy("mLock")
    private boolean mCanceled;
    private final int mDefaultTrafficStatsTag;
    @GuardedBy("mLock")
    @Nullable
    private k.a mErrorListener;
    /* access modifiers changed from: private */
    public final n.a mEventLog;
    private final Object mLock;
    private final int mMethod;
    @GuardedBy("mLock")
    private b mRequestCompleteListener;
    private j mRequestQueue;
    @GuardedBy("mLock")
    private boolean mResponseDelivered;
    private m mRetryPolicy;
    private Integer mSequence;
    private boolean mShouldCache;
    private boolean mShouldRetryConnectionErrors;
    private boolean mShouldRetryServerErrors;
    private Object mTag;
    private final String mUrl;

    /* compiled from: Request */
    public interface b {
        void a(i<?> iVar, k<?> kVar);

        void b(i<?> iVar);
    }

    /* compiled from: Request */
    public enum c {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    /* access modifiers changed from: protected */
    public abstract void deliverResponse(T t);

    /* access modifiers changed from: protected */
    public abstract k<T> parseNetworkResponse(h hVar);

    @Deprecated
    public i(String url, k.a errorListener) {
        this(-1, url, errorListener);
    }

    public i(int method, String url, @Nullable k.a errorListener) {
        this.mEventLog = n.a.a ? new n.a() : null;
        this.mLock = new Object();
        this.mShouldCache = true;
        this.mCanceled = false;
        this.mResponseDelivered = false;
        this.mShouldRetryServerErrors = false;
        this.mShouldRetryConnectionErrors = false;
        this.mCacheEntry = null;
        this.mMethod = method;
        this.mUrl = url;
        this.mErrorListener = errorListener;
        setRetryPolicy(new c());
        this.mDefaultTrafficStatsTag = findDefaultTrafficStatsTag(url);
    }

    public int getMethod() {
        return this.mMethod;
    }

    public i<?> setTag(Object tag) {
        this.mTag = tag;
        return this;
    }

    public Object getTag() {
        return this.mTag;
    }

    @Nullable
    public k.a getErrorListener() {
        k.a aVar;
        synchronized (this.mLock) {
            aVar = this.mErrorListener;
        }
        return aVar;
    }

    public int getTrafficStatsTag() {
        return this.mDefaultTrafficStatsTag;
    }

    private static int findDefaultTrafficStatsTag(String url) {
        Uri uri;
        String host;
        if (TextUtils.isEmpty(url) || (uri = Uri.parse(url)) == null || (host = uri.getHost()) == null) {
            return 0;
        }
        return host.hashCode();
    }

    public i<?> setRetryPolicy(m retryPolicy) {
        this.mRetryPolicy = retryPolicy;
        return this;
    }

    public void addMarker(String tag) {
        if (n.a.a) {
            this.mEventLog.a(tag, Thread.currentThread().getId());
        }
    }

    /* access modifiers changed from: package-private */
    public void finish(String tag) {
        j jVar = this.mRequestQueue;
        if (jVar != null) {
            jVar.c(this);
        }
        if (n.a.a) {
            long threadId = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new a(tag, threadId));
                return;
            }
            this.mEventLog.a(tag, threadId);
            this.mEventLog.b(toString());
        }
    }

    /* compiled from: Request */
    public class a implements Runnable {
        final /* synthetic */ String c;
        final /* synthetic */ long d;

        a(String str, long j) {
            this.c = str;
            this.d = j;
        }

        public void run() {
            i.this.mEventLog.a(this.c, this.d);
            i.this.mEventLog.b(i.this.toString());
        }
    }

    /* access modifiers changed from: package-private */
    public void sendEvent(int event) {
        j jVar = this.mRequestQueue;
        if (jVar != null) {
            jVar.e(this, event);
        }
    }

    public i<?> setRequestQueue(j requestQueue) {
        this.mRequestQueue = requestQueue;
        return this;
    }

    public final i<?> setSequence(int sequence) {
        this.mSequence = Integer.valueOf(sequence);
        return this;
    }

    public final int getSequence() {
        Integer num = this.mSequence;
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalStateException("getSequence called before setSequence");
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getCacheKey() {
        String url = getUrl();
        int method = getMethod();
        if (method == 0 || method == -1) {
            return url;
        }
        return Integer.toString(method) + '-' + url;
    }

    public i<?> setCacheEntry(a.C0017a entry) {
        this.mCacheEntry = entry;
        return this;
    }

    @Nullable
    public a.C0017a getCacheEntry() {
        return this.mCacheEntry;
    }

    @CallSuper
    public void cancel() {
        synchronized (this.mLock) {
            this.mCanceled = true;
            this.mErrorListener = null;
        }
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mCanceled;
        }
        return z;
    }

    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    @Nullable
    public Map<String, String> getPostParams() {
        return getParams();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    @Deprecated
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Deprecated
    public byte[] getPostBody() {
        Map<String, String> postParams = getPostParams();
        if (postParams == null || postParams.size() <= 0) {
            return null;
        }
        return encodeParameters(postParams, getPostParamsEncoding());
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Map<String, String> getParams() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String getParamsEncoding() {
        return "UTF-8";
    }

    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }

    public byte[] getBody() {
        Map<String, String> params = getParams();
        if (params == null || params.size() <= 0) {
            return null;
        }
        return encodeParameters(params, getParamsEncoding());
    }

    private byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    throw new IllegalArgumentException(String.format("Request#getParams() or Request#getPostParams() returned a map containing a null key or value: (%s, %s). All keys and values must be non-null.", new Object[]{entry.getKey(), entry.getValue()}));
                }
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    public final i<?> setShouldCache(boolean shouldCache) {
        this.mShouldCache = shouldCache;
        return this;
    }

    public final boolean shouldCache() {
        return this.mShouldCache;
    }

    public final i<?> setShouldRetryServerErrors(boolean shouldRetryServerErrors) {
        this.mShouldRetryServerErrors = shouldRetryServerErrors;
        return this;
    }

    public final boolean shouldRetryServerErrors() {
        return this.mShouldRetryServerErrors;
    }

    public final i<?> setShouldRetryConnectionErrors(boolean shouldRetryConnectionErrors) {
        this.mShouldRetryConnectionErrors = shouldRetryConnectionErrors;
        return this;
    }

    public final boolean shouldRetryConnectionErrors() {
        return this.mShouldRetryConnectionErrors;
    }

    public c getPriority() {
        return c.NORMAL;
    }

    public final int getTimeoutMs() {
        return getRetryPolicy().c();
    }

    public m getRetryPolicy() {
        return this.mRetryPolicy;
    }

    public void markDelivered() {
        synchronized (this.mLock) {
            this.mResponseDelivered = true;
        }
    }

    public boolean hasHadResponseDelivered() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mResponseDelivered;
        }
        return z;
    }

    /* access modifiers changed from: protected */
    public VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }

    public void deliverError(VolleyError error) {
        k.a listener;
        synchronized (this.mLock) {
            listener = this.mErrorListener;
        }
        if (listener != null) {
            listener.onErrorResponse(error);
        }
    }

    /* access modifiers changed from: package-private */
    public void setNetworkRequestCompleteListener(b requestCompleteListener) {
        synchronized (this.mLock) {
            this.mRequestCompleteListener = requestCompleteListener;
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyListenerResponseReceived(k<?> response) {
        b listener;
        synchronized (this.mLock) {
            listener = this.mRequestCompleteListener;
        }
        if (listener != null) {
            listener.a(this, response);
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyListenerResponseNotUsable() {
        b listener;
        synchronized (this.mLock) {
            listener = this.mRequestCompleteListener;
        }
        if (listener != null) {
            listener.b(this);
        }
    }

    public int compareTo(i<T> other) {
        int i;
        int i2;
        c left = getPriority();
        c right = other.getPriority();
        if (left == right) {
            i2 = this.mSequence.intValue();
            i = other.mSequence.intValue();
        } else {
            i2 = right.ordinal();
            i = left.ordinal();
        }
        return i2 - i;
    }

    public String toString() {
        String trafficStatsTag = "0x" + Integer.toHexString(getTrafficStatsTag());
        StringBuilder sb = new StringBuilder();
        sb.append(isCanceled() ? "[X] " : "[ ] ");
        sb.append(getUrl());
        sb.append(" ");
        sb.append(trafficStatsTag);
        sb.append(" ");
        sb.append(getPriority());
        sb.append(" ");
        sb.append(this.mSequence);
        return sb.toString();
    }
}
