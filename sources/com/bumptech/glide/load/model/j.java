package com.bumptech.glide.load.model;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.netty.util.internal.StringUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: LazyHeaders */
public final class j implements h {
    private final Map<String, List<i>> c;
    private volatile Map<String, String> d;

    j(Map<String, List<i>> headers) {
        this.c = Collections.unmodifiableMap(headers);
    }

    public Map<String, String> getHeaders() {
        if (this.d == null) {
            synchronized (this) {
                if (this.d == null) {
                    this.d = Collections.unmodifiableMap(b());
                }
            }
        }
        return this.d;
    }

    private Map<String, String> b() {
        Map<String, String> combinedHeaders = new HashMap<>();
        for (Map.Entry<String, List<LazyHeaderFactory>> entry : this.c.entrySet()) {
            String values = a(entry.getValue());
            if (!TextUtils.isEmpty(values)) {
                combinedHeaders.put(entry.getKey(), values);
            }
        }
        return combinedHeaders;
    }

    @NonNull
    private String a(@NonNull List<i> factories) {
        StringBuilder sb = new StringBuilder();
        int size = factories.size();
        for (int i = 0; i < size; i++) {
            String header = factories.get(i).a();
            if (!TextUtils.isEmpty(header)) {
                sb.append(header);
                if (i != factories.size() - 1) {
                    sb.append(StringUtil.COMMA);
                }
            }
        }
        return sb.toString();
    }

    public String toString() {
        return "LazyHeaders{headers=" + this.c + '}';
    }

    public boolean equals(Object o) {
        if (o instanceof j) {
            return this.c.equals(((j) o).c);
        }
        return false;
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    /* compiled from: LazyHeaders */
    public static final class a {
        private static final String a;
        private static final Map<String, List<i>> b;
        private boolean c = true;
        private Map<String, List<i>> d = b;
        private boolean e = true;

        static {
            String b2 = b();
            a = b2;
            Map<String, List<LazyHeaderFactory>> temp = new HashMap<>(2);
            if (!TextUtils.isEmpty(b2)) {
                temp.put("User-Agent", Collections.singletonList(new b(b2)));
            }
            b = Collections.unmodifiableMap(temp);
        }

        public j a() {
            this.c = true;
            return new j(this.d);
        }

        @VisibleForTesting
        static String b() {
            String defaultUserAgent = System.getProperty("http.agent");
            if (TextUtils.isEmpty(defaultUserAgent)) {
                return defaultUserAgent;
            }
            int length = defaultUserAgent.length();
            StringBuilder sb = new StringBuilder(defaultUserAgent.length());
            for (int i = 0; i < length; i++) {
                char c2 = defaultUserAgent.charAt(i);
                if ((c2 > 31 || c2 == 9) && c2 < 127) {
                    sb.append(c2);
                } else {
                    sb.append('?');
                }
            }
            return sb.toString();
        }
    }

    /* compiled from: LazyHeaders */
    public static final class b implements i {
        @NonNull
        private final String a;

        b(@NonNull String value) {
            this.a = value;
        }

        public String a() {
            return this.a;
        }

        public String toString() {
            return "StringHeaderFactory{value='" + this.a + '\'' + '}';
        }

        public boolean equals(Object o) {
            if (o instanceof b) {
                return this.a.equals(((b) o).a);
            }
            return false;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }
}
