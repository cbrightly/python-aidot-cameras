package com.bumptech.glide.load.data;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.model.g;
import com.bumptech.glide.util.e;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/* compiled from: HttpUrlFetcher */
public class j implements d<InputStream> {
    @VisibleForTesting
    static final b c = new a();
    private final g d;
    private final int f;
    private final b q;
    private HttpURLConnection x;
    private InputStream y;
    private volatile boolean z;

    /* compiled from: HttpUrlFetcher */
    public interface b {
        HttpURLConnection a(URL url);
    }

    public j(g glideUrl, int timeout) {
        this(glideUrl, timeout, c);
    }

    @VisibleForTesting
    j(g glideUrl, int timeout, b connectionFactory) {
        this.d = glideUrl;
        this.f = timeout;
        this.q = connectionFactory;
    }

    public void d(@NonNull com.bumptech.glide.g priority, @NonNull d.a<? super InputStream> callback) {
        StringBuilder sb;
        long startTime = e.b();
        try {
            callback.e(i(this.d.f(), 0, (URL) null, this.d.c()));
            if (Log.isLoggable("HttpUrlFetcher", 2)) {
                sb = new StringBuilder();
                sb.append("Finished http url fetcher fetch in ");
                sb.append(e.a(startTime));
                Log.v("HttpUrlFetcher", sb.toString());
            }
        } catch (IOException e) {
            if (Log.isLoggable("HttpUrlFetcher", 3)) {
                Log.d("HttpUrlFetcher", "Failed to load data for url", e);
            }
            callback.c(e);
            if (Log.isLoggable("HttpUrlFetcher", 2)) {
                sb = new StringBuilder();
            }
        } catch (Throwable th) {
            if (Log.isLoggable("HttpUrlFetcher", 2)) {
                Log.v("HttpUrlFetcher", "Finished http url fetcher fetch in " + e.a(startTime));
            }
            throw th;
        }
    }

    private InputStream i(URL url, int redirects, URL lastUrl, Map<String, String> headers) {
        if (redirects < 5) {
            if (lastUrl != null) {
                try {
                    if (url.toURI().equals(lastUrl.toURI())) {
                        throw new HttpException("In re-direct loop", -1);
                    }
                } catch (URISyntaxException e) {
                }
            }
            HttpURLConnection c2 = c(url, headers);
            this.x = c2;
            try {
                c2.connect();
                this.y = this.x.getInputStream();
                if (this.z) {
                    return null;
                }
                int statusCode = e(this.x);
                if (g(statusCode)) {
                    return f(this.x);
                }
                if (h(statusCode)) {
                    String redirectUrlString = this.x.getHeaderField("Location");
                    if (!TextUtils.isEmpty(redirectUrlString)) {
                        try {
                            URL redirectUrl = new URL(url, redirectUrlString);
                            b();
                            return i(redirectUrl, redirects + 1, url, headers);
                        } catch (MalformedURLException e2) {
                            throw new HttpException("Bad redirect url: " + redirectUrlString, statusCode, e2);
                        }
                    } else {
                        throw new HttpException("Received empty or null redirect url", statusCode);
                    }
                } else if (statusCode == -1) {
                    throw new HttpException(statusCode);
                } else {
                    try {
                        throw new HttpException(this.x.getResponseMessage(), statusCode);
                    } catch (IOException e3) {
                        throw new HttpException("Failed to get a response message", statusCode, e3);
                    }
                }
            } catch (IOException e4) {
                throw new HttpException("Failed to connect or obtain data", e(this.x), e4);
            }
        } else {
            throw new HttpException("Too many (> 5) redirects!", -1);
        }
    }

    private static int e(HttpURLConnection urlConnection) {
        try {
            return urlConnection.getResponseCode();
        } catch (IOException e) {
            if (!Log.isLoggable("HttpUrlFetcher", 3)) {
                return -1;
            }
            Log.d("HttpUrlFetcher", "Failed to get a response code", e);
            return -1;
        }
    }

    private HttpURLConnection c(URL url, Map<String, String> headers) {
        try {
            HttpURLConnection urlConnection = this.q.a(url);
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                urlConnection.addRequestProperty(headerEntry.getKey(), headerEntry.getValue());
            }
            urlConnection.setConnectTimeout(this.f);
            urlConnection.setReadTimeout(this.f);
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setInstanceFollowRedirects(false);
            return urlConnection;
        } catch (IOException e) {
            throw new HttpException("URL.openConnection threw", 0, e);
        }
    }

    private static boolean g(int statusCode) {
        return statusCode / 100 == 2;
    }

    private static boolean h(int statusCode) {
        return statusCode / 100 == 3;
    }

    private InputStream f(HttpURLConnection urlConnection) {
        try {
            if (TextUtils.isEmpty(urlConnection.getContentEncoding())) {
                this.y = com.bumptech.glide.util.b.c(urlConnection.getInputStream(), (long) urlConnection.getContentLength());
            } else {
                if (Log.isLoggable("HttpUrlFetcher", 3)) {
                    Log.d("HttpUrlFetcher", "Got non empty content encoding: " + urlConnection.getContentEncoding());
                }
                this.y = urlConnection.getInputStream();
            }
            return this.y;
        } catch (IOException e) {
            throw new HttpException("Failed to obtain InputStream", e(urlConnection), e);
        }
    }

    public void b() {
        InputStream inputStream = this.y;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        HttpURLConnection httpURLConnection = this.x;
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        this.x = null;
    }

    public void cancel() {
        this.z = true;
    }

    @NonNull
    public Class<InputStream> a() {
        return InputStream.class;
    }

    @NonNull
    public com.bumptech.glide.load.a getDataSource() {
        return com.bumptech.glide.load.a.REMOTE;
    }

    /* compiled from: HttpUrlFetcher */
    public static class a implements b {
        a() {
        }

        public HttpURLConnection a(URL url) {
            return (HttpURLConnection) url.openConnection();
        }
    }
}
