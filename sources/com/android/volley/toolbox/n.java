package com.android.volley.toolbox;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.android.volley.Response;
import com.android.volley.h;
import com.android.volley.i;
import com.android.volley.k;
import java.io.UnsupportedEncodingException;

/* compiled from: JsonRequest */
public abstract class n<T> extends i<T> {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", new Object[]{PROTOCOL_CHARSET});
    @GuardedBy("mLock")
    @Nullable
    private k.b<T> mListener;
    private final Object mLock;
    @Nullable
    private final String mRequestBody;

    /* access modifiers changed from: protected */
    public abstract k<T> parseNetworkResponse(h hVar);

    @Deprecated
    public n(String url, String requestBody, k.b<T> listener, k.a errorListener) {
        this(-1, url, requestBody, listener, errorListener);
    }

    public n(int method, String url, @Nullable String requestBody, k.b<T> listener, @Nullable k.a errorListener) {
        super(method, url, errorListener);
        this.mLock = new Object();
        this.mListener = listener;
        this.mRequestBody = requestBody;
    }

    public void cancel() {
        super.cancel();
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    /* access modifiers changed from: protected */
    public void deliverResponse(T response) {
        Response.Listener<T> listener;
        synchronized (this.mLock) {
            listener = this.mListener;
        }
        if (listener != null) {
            listener.onResponse(response);
        }
    }

    @Deprecated
    public String getPostBodyContentType() {
        return getBodyContentType();
    }

    @Deprecated
    public byte[] getPostBody() {
        return getBody();
    }

    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    public byte[] getBody() {
        byte[] bArr = null;
        try {
            String str = this.mRequestBody;
            if (str != null) {
                bArr = str.getBytes(PROTOCOL_CHARSET);
            }
            return bArr;
        } catch (UnsupportedEncodingException e) {
            com.android.volley.n.f("Unsupported Encoding while trying to get the bytes of %s using %s", this.mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }
}
