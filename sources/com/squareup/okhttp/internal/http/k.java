package com.squareup.okhttp.internal.http;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.b;
import com.squareup.okhttp.g;
import com.squareup.okhttp.internal.h;
import com.squareup.okhttp.internal.j;
import com.squareup.okhttp.p;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.slf4j.e;

/* compiled from: OkHeaders */
public final class k {
    private static final Comparator<String> a = new a();
    static final String b;
    public static final String c;
    public static final String d;
    public static final String e;
    public static final String f;

    /* compiled from: OkHeaders */
    public static final class a implements Comparator<String> {
        a() {
        }

        /* renamed from: a */
        public int compare(String a, String b) {
            if (a == b) {
                return 0;
            }
            if (a == null) {
                return -1;
            }
            if (b == null) {
                return 1;
            }
            return String.CASE_INSENSITIVE_ORDER.compare(a, b);
        }
    }

    static {
        String g = h.f().g();
        b = g;
        c = g + "-Sent-Millis";
        d = g + "-Received-Millis";
        e = g + "-Selected-Protocol";
        f = g + "-Response-Source";
    }

    public static long d(v request) {
        return c(request.i());
    }

    public static long e(x response) {
        return c(response.s());
    }

    public static long c(p headers) {
        return k(headers.a("Content-Length"));
    }

    private static long k(String s) {
        if (s == null) {
            return -1;
        }
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e2) {
            return -1;
        }
    }

    public static Map<String, List<String>> l(p headers, String valueForNullKey) {
        Map<String, List<String>> result = new TreeMap<>(a);
        int size = headers.f();
        for (int i = 0; i < size; i++) {
            String fieldName = headers.d(i);
            String value = headers.g(i);
            List<String> allValues = new ArrayList<>();
            List<String> otherValues = result.get(fieldName);
            if (otherValues != null) {
                allValues.addAll(otherValues);
            }
            allValues.add(value);
            result.put(fieldName, Collections.unmodifiableList(allValues));
        }
        if (valueForNullKey != null) {
            result.put((Object) null, Collections.unmodifiableList(Collections.singletonList(valueForNullKey)));
        }
        return Collections.unmodifiableMap(result);
    }

    public static void a(v.b builder, Map<String, List<String>> cookieHeaders) {
        for (Map.Entry<String, List<String>> entry : cookieHeaders.entrySet()) {
            String key = entry.getKey();
            if ((HttpHeaders.HEAD_KEY_COOKIE.equalsIgnoreCase(key) || HttpHeaders.HEAD_KEY_COOKIE2.equalsIgnoreCase(key)) && !entry.getValue().isEmpty()) {
                builder.f(key, b(entry.getValue()));
            }
        }
    }

    private static String b(List<String> cookies) {
        if (cookies.size() == 1) {
            return cookies.get(0);
        }
        StringBuilder sb = new StringBuilder();
        int size = cookies.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append("; ");
            }
            sb.append(cookies.get(i));
        }
        return sb.toString();
    }

    public static boolean q(x cachedResponse, p cachedRequest, v newRequest) {
        for (String field : n(cachedResponse)) {
            if (!j.h(cachedRequest.h(field), newRequest.j(field))) {
                return false;
            }
        }
        return true;
    }

    public static boolean g(x response) {
        return f(response.s());
    }

    public static boolean f(p responseHeaders) {
        return m(responseHeaders).contains(e.ANY_MARKER);
    }

    private static Set<String> n(x response) {
        return m(response.s());
    }

    public static Set<String> m(p responseHeaders) {
        Set<String> result = Collections.emptySet();
        int size = responseHeaders.f();
        for (int i = 0; i < size; i++) {
            if ("Vary".equalsIgnoreCase(responseHeaders.d(i))) {
                String value = responseHeaders.g(i);
                if (result.isEmpty()) {
                    result = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
                }
                for (String varyField : value.split(",")) {
                    result.add(varyField.trim());
                }
            }
        }
        return result;
    }

    public static p p(x response) {
        return o(response.u().x().i(), response.s());
    }

    public static p o(p requestHeaders, p responseHeaders) {
        Set<String> varyFields = m(responseHeaders);
        if (varyFields.isEmpty()) {
            return new p.b().e();
        }
        p.b result = new p.b();
        int size = requestHeaders.f();
        for (int i = 0; i < size; i++) {
            String fieldName = requestHeaders.d(i);
            if (varyFields.contains(fieldName)) {
                result.b(fieldName, requestHeaders.g(i));
            }
        }
        return result.e();
    }

    static boolean h(String fieldName) {
        return !"Connection".equalsIgnoreCase(fieldName) && !"Keep-Alive".equalsIgnoreCase(fieldName) && !"Proxy-Authenticate".equalsIgnoreCase(fieldName) && !"Proxy-Authorization".equalsIgnoreCase(fieldName) && !"TE".equalsIgnoreCase(fieldName) && !"Trailers".equalsIgnoreCase(fieldName) && !Constants.TRANSFERENCODING.equalsIgnoreCase(fieldName) && !UpgradeRequest.UPGRADE.equalsIgnoreCase(fieldName);
    }

    public static List<g> i(p responseHeaders, String challengeHeader) {
        List<Challenge> result = new ArrayList<>();
        int size = responseHeaders.f();
        for (int i = 0; i < size; i++) {
            if (challengeHeader.equalsIgnoreCase(responseHeaders.d(i))) {
                String value = responseHeaders.g(i);
                int realmStart = 0;
                while (realmStart < value.length()) {
                    int tokenStart = realmStart;
                    int pos = d.b(value, realmStart, " ");
                    String scheme = value.substring(tokenStart, pos).trim();
                    int pos2 = d.c(value, pos);
                    if (!value.regionMatches(true, pos2, "realm=\"", 0, "realm=\"".length())) {
                        break;
                    }
                    int pos3 = pos2 + "realm=\"".length();
                    int pos4 = d.b(value, pos3, "\"");
                    String realm = value.substring(pos3, pos4);
                    int pos5 = d.c(value, d.b(value, pos4 + 1, ",") + 1);
                    result.add(new g(scheme, realm));
                    realmStart = pos5;
                }
            }
        }
        return result;
    }

    public static v j(b authenticator, x response, Proxy proxy) {
        if (response.o() == 407) {
            return authenticator.a(proxy, response);
        }
        return authenticator.b(proxy, response);
    }
}
