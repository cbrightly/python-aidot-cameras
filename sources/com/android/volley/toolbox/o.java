package com.android.volley.toolbox;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.a;
import com.android.volley.e;
import com.android.volley.h;
import com.android.volley.i;
import com.android.volley.m;
import com.android.volley.n;
import com.google.maps.android.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.List;
import meshsdk.ctrl.GroupCtrlAdapter;

/* compiled from: NetworkUtility */
public final class o {
    static void d(long requestLifetime, i<?> request, byte[] responseContents, int statusCode) {
        if (n.b || requestLifetime > GroupCtrlAdapter.RETRY_TIMEOUT) {
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(requestLifetime);
            objArr[2] = responseContents != null ? Integer.valueOf(responseContents.length) : BuildConfig.TRAVIS;
            objArr[3] = Integer.valueOf(statusCode);
            objArr[4] = Integer.valueOf(request.getRetryPolicy().a());
            n.b("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", objArr);
        }
    }

    static h b(i<?> request, long requestDuration, List<e> responseHeaders) {
        a.C0017a entry = request.getCacheEntry();
        if (entry == null) {
            return new h(304, (byte[]) null, true, requestDuration, responseHeaders);
        }
        return new h(304, entry.a, true, requestDuration, g.a(responseHeaders, entry));
    }

    static byte[] c(InputStream in, int contentLength, d pool) {
        p bytes = new p(pool, contentLength);
        try {
            byte[] buffer = pool.a(1024);
            while (true) {
                int read = in.read(buffer);
                int count = read;
                if (read == -1) {
                    break;
                }
                bytes.write(buffer, 0, count);
            }
            byte[] byteArray = bytes.toByteArray();
            try {
                in.close();
            } catch (IOException e) {
                n.e("Error occurred when closing InputStream", new Object[0]);
            }
            pool.b(buffer);
            bytes.close();
            return byteArray;
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e2) {
                    n.e("Error occurred when closing InputStream", new Object[0]);
                }
            }
            pool.b((byte[]) null);
            bytes.close();
            throw th;
        }
    }

    static void a(i<?> request, b retryInfo) {
        m retryPolicy = request.getRetryPolicy();
        int oldTimeout = request.getTimeoutMs();
        try {
            retryPolicy.b(retryInfo.b);
            request.addMarker(String.format("%s-retry [timeout=%s]", new Object[]{retryInfo.a, Integer.valueOf(oldTimeout)}));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", new Object[]{retryInfo.a, Integer.valueOf(oldTimeout)}));
            throw e;
        }
    }

    /* compiled from: NetworkUtility */
    public static class b {
        /* access modifiers changed from: private */
        public final String a;
        /* access modifiers changed from: private */
        public final VolleyError b;

        private b(String logPrefix, VolleyError errorToRetry) {
            this.a = logPrefix;
            this.b = errorToRetry;
        }
    }

    static b e(i<?> request, IOException exception, long requestStartMs, @Nullable h httpResponse, @Nullable byte[] responseContents) {
        IOException iOException = exception;
        if (iOException instanceof SocketTimeoutException) {
            return new b("socket", new TimeoutError());
        }
        if (iOException instanceof MalformedURLException) {
            throw new RuntimeException("Bad URL " + request.getUrl(), exception);
        } else if (httpResponse != null) {
            int statusCode = httpResponse.d();
            n.c("Unexpected response code %d for %s", Integer.valueOf(statusCode), request.getUrl());
            if (responseContents == null) {
                return new b("network", new NetworkError());
            }
            int i = statusCode;
            byte[] bArr = responseContents;
            h networkResponse = new h(i, bArr, false, SystemClock.elapsedRealtime() - requestStartMs, httpResponse.c());
            if (statusCode == 401 || statusCode == 403) {
                return new b("auth", new AuthFailureError(networkResponse));
            }
            if (statusCode >= 400 && statusCode <= 499) {
                throw new ClientError(networkResponse);
            } else if (statusCode >= 500 && statusCode <= 599 && request.shouldRetryServerErrors()) {
                return new b("server", new ServerError(networkResponse));
            } else {
                throw new ServerError(networkResponse);
            }
        } else if (request.shouldRetryConnectionErrors() != 0) {
            return new b("connection", new NoConnectionError());
        } else {
            throw new NoConnectionError(exception);
        }
    }
}
