package com.android.volley.toolbox;

import androidx.annotation.Nullable;
import com.android.volley.Header;
import com.android.volley.a;
import com.android.volley.e;
import com.android.volley.h;
import com.android.volley.n;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.meituan.robust.Constants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;

/* compiled from: HttpHeaderParser */
public class g {
    @Nullable
    public static a.C0017a e(h response) {
        long lastModified;
        boolean hasCacheControl;
        long lastModified2;
        long j;
        h hVar = response;
        long now = System.currentTimeMillis();
        Map<String, String> headers = hVar.c;
        if (headers == null) {
            return null;
        }
        long serverDate = 0;
        long serverExpires = 0;
        long softExpire = 0;
        long finalExpire = 0;
        long maxAge = 0;
        long staleWhileRevalidate = 0;
        boolean mustRevalidate = false;
        String headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = g(headerValue);
        }
        String headerValue2 = headers.get(HttpHeaders.HEAD_KEY_CACHE_CONTROL);
        if (headerValue2 != null) {
            lastModified = 0;
            String[] tokens = headerValue2.split(",", 0);
            int i = 0;
            while (i < tokens.length) {
                String headerValue3 = headerValue2;
                String token = tokens[i].trim();
                if (token.equals("no-cache") || token.equals("no-store")) {
                    return null;
                }
                if (token.startsWith("max-age=")) {
                    try {
                        maxAge = Long.parseLong(token.substring(8));
                    } catch (Exception e) {
                    }
                } else if (token.startsWith("stale-while-revalidate=")) {
                    try {
                        staleWhileRevalidate = Long.parseLong(token.substring(23));
                    } catch (Exception e2) {
                    }
                } else if (token.equals("must-revalidate") || token.equals("proxy-revalidate")) {
                    mustRevalidate = true;
                }
                i++;
                headerValue2 = headerValue3;
            }
            hasCacheControl = true;
        } else {
            lastModified = 0;
            hasCacheControl = false;
        }
        String headerValue4 = headers.get(HttpHeaders.HEAD_KEY_EXPIRES);
        if (headerValue4 != null) {
            serverExpires = g(headerValue4);
        }
        String headerValue5 = headers.get(HttpHeaders.HEAD_KEY_LAST_MODIFIED);
        if (headerValue5 != null) {
            lastModified2 = g(headerValue5);
        } else {
            lastModified2 = lastModified;
        }
        String str = headerValue5;
        String serverEtag = headers.get(HttpHeaders.HEAD_KEY_E_TAG);
        if (hasCacheControl) {
            softExpire = now + (maxAge * 1000);
            if (mustRevalidate) {
                j = softExpire;
            } else {
                Long.signum(staleWhileRevalidate);
                j = softExpire + (1000 * staleWhileRevalidate);
            }
            finalExpire = j;
        } else if (serverDate > 0 && serverExpires >= serverDate) {
            softExpire = now + (serverExpires - serverDate);
            finalExpire = softExpire;
        }
        boolean z = hasCacheControl;
        long j2 = now;
        a.C0017a entry = new a.C0017a();
        entry.a = hVar.b;
        entry.b = serverEtag;
        entry.f = softExpire;
        entry.e = finalExpire;
        entry.c = serverDate;
        entry.d = lastModified2;
        entry.g = headers;
        entry.h = hVar.d;
        return entry;
    }

    public static long g(String dateStr) {
        try {
            return d("EEE, dd MMM yyyy HH:mm:ss zzz").parse(dateStr).getTime();
        } catch (ParseException e) {
            if ("0".equals(dateStr) || "-1".equals(dateStr)) {
                n.e("Unable to parse dateStr: %s, falling back to 0", dateStr);
                return 0;
            }
            n.d(e, "Unable to parse dateStr: %s, falling back to 0", dateStr);
            return 0;
        }
    }

    static String b(long epoch) {
        return d("EEE, dd MMM yyyy HH:mm:ss 'GMT'").format(new Date(epoch));
    }

    private static SimpleDateFormat d(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter;
    }

    public static String f(@Nullable Map<String, String> headers, String defaultCharset) {
        String contentType;
        if (!(headers == null || (contentType = headers.get("Content-Type")) == null)) {
            String[] params = contentType.split(Constants.PACKNAME_END, 0);
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=", 0);
                if (pair.length == 2 && pair[0].equals("charset")) {
                    return pair[1];
                }
            }
        }
        return defaultCharset;
    }

    static Map<String, String> i(List<e> allHeaders) {
        Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (e header : allHeaders) {
            headers.put(header.a(), header.b());
        }
        return headers;
    }

    static List<e> h(Map<String, String> headers) {
        List<Header> allHeaders = new ArrayList<>(headers.size());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            allHeaders.add(new e(header.getKey(), header.getValue()));
        }
        return allHeaders;
    }

    static List<e> a(List<e> responseHeaders, a.C0017a entry) {
        Set<String> headerNamesFromNetworkResponse = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if (!responseHeaders.isEmpty()) {
            for (e header : responseHeaders) {
                headerNamesFromNetworkResponse.add(header.a());
            }
        }
        List<Header> combinedHeaders = new ArrayList<>(responseHeaders);
        List<e> list = entry.h;
        if (list != null) {
            if (!list.isEmpty()) {
                for (e header2 : entry.h) {
                    if (!headerNamesFromNetworkResponse.contains(header2.a())) {
                        combinedHeaders.add(header2);
                    }
                }
            }
        } else if (!entry.g.isEmpty()) {
            for (Map.Entry<String, String> header3 : entry.g.entrySet()) {
                if (!headerNamesFromNetworkResponse.contains(header3.getKey())) {
                    combinedHeaders.add(new e(header3.getKey(), header3.getValue()));
                }
            }
        }
        return combinedHeaders;
    }

    static Map<String, String> c(a.C0017a entry) {
        if (entry == null) {
            return Collections.emptyMap();
        }
        Map<String, String> headers = new HashMap<>();
        String str = entry.b;
        if (str != null) {
            headers.put(HttpHeaders.HEAD_KEY_IF_NONE_MATCH, str);
        }
        long j = entry.d;
        if (j > 0) {
            headers.put(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE, b(j));
        }
        return headers;
    }
}
