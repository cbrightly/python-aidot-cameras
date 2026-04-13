package com.downloader.httpclient;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: DefaultHttpClient */
public class a implements b {
    private URLConnection c;

    public b clone() {
        return new a();
    }

    public void P(com.downloader.request.a request) {
        URLConnection openConnection = new URL(request.B()).openConnection();
        this.c = openConnection;
        openConnection.setReadTimeout(request.w());
        this.c.setConnectTimeout(request.n());
        this.c.addRequestProperty(HttpHeaders.HEAD_KEY_RANGE, String.format(Locale.ENGLISH, "bytes=%d-", new Object[]{Long.valueOf(request.r())}));
        this.c.addRequestProperty("User-Agent", request.C());
        a(request);
        this.c.connect();
    }

    public int F0() {
        URLConnection uRLConnection = this.c;
        if (uRLConnection instanceof HttpURLConnection) {
            return ((HttpURLConnection) uRLConnection).getResponseCode();
        }
        return 0;
    }

    public InputStream getInputStream() {
        return this.c.getInputStream();
    }

    public long getContentLength() {
        try {
            return Long.parseLong(this.c.getHeaderField("Content-Length"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String E(String name) {
        return this.c.getHeaderField(name);
    }

    public void close() {
    }

    public Map<String, List<String>> W() {
        return this.c.getHeaderFields();
    }

    public InputStream x() {
        URLConnection uRLConnection = this.c;
        if (uRLConnection instanceof HttpURLConnection) {
            return ((HttpURLConnection) uRLConnection).getErrorStream();
        }
        return null;
    }

    private void a(com.downloader.request.a request) {
        HashMap<String, List<String>> headers = request.t();
        if (headers != null) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String name = entry.getKey();
                List<String> list = entry.getValue();
                if (list != null) {
                    for (String value : list) {
                        this.c.addRequestProperty(name, value);
                    }
                }
            }
        }
    }
}
