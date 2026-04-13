package com.android.volley;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: NetworkResponse */
public class h {
    public final int a;
    public final byte[] b;
    @Nullable
    public final Map<String, String> c;
    @Nullable
    public final List<e> d;
    public final boolean e;
    public final long f;

    @Deprecated
    public h(int statusCode, byte[] data, @Nullable Map<String, String> headers, boolean notModified, long networkTimeMs) {
        this(statusCode, data, headers, a(headers), notModified, networkTimeMs);
    }

    public h(int statusCode, byte[] data, boolean notModified, long networkTimeMs, @Nullable List<e> allHeaders) {
        this(statusCode, data, b(allHeaders), allHeaders, notModified, networkTimeMs);
    }

    @Deprecated
    public h(byte[] data, @Nullable Map<String, String> headers) {
        this(200, data, headers, false, 0);
    }

    private h(int statusCode, byte[] data, @Nullable Map<String, String> headers, @Nullable List<e> allHeaders, boolean notModified, long networkTimeMs) {
        this.a = statusCode;
        this.b = data;
        this.c = headers;
        if (allHeaders == null) {
            this.d = null;
        } else {
            this.d = Collections.unmodifiableList(allHeaders);
        }
        this.e = notModified;
        this.f = networkTimeMs;
    }

    @Nullable
    private static Map<String, String> b(@Nullable List<e> allHeaders) {
        if (allHeaders == null) {
            return null;
        }
        if (allHeaders.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (e header : allHeaders) {
            headers.put(header.a(), header.b());
        }
        return headers;
    }

    @Nullable
    private static List<e> a(@Nullable Map<String, String> headers) {
        if (headers == null) {
            return null;
        }
        if (headers.isEmpty()) {
            return Collections.emptyList();
        }
        List<Header> allHeaders = new ArrayList<>(headers.size());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            allHeaders.add(new e(header.getKey(), header.getValue()));
        }
        return allHeaders;
    }
}
