package com.yanzhenjie.andserver.http;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.util.MultiValueMap;
import com.yanzhenjie.andserver.util.g;
import com.yanzhenjie.andserver.util.j;
import com.yanzhenjie.andserver.util.k;
import com.yanzhenjie.andserver.util.l;
import java.net.URI;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/* compiled from: Uri */
public class m implements k {
    private final String h;
    private final String i;
    private final int j;
    private final String k;
    private final String l;
    private final String m;

    public static b d(String uri) {
        return new b(uri);
    }

    private m(b builder) {
        this.h = builder.a;
        this.i = builder.b;
        this.j = builder.c;
        this.k = g(builder.d);
        this.l = e(builder.e);
        this.m = builder.f;
    }

    @NonNull
    public String c() {
        return this.k;
    }

    @NonNull
    public j<String, String> b() {
        return h(this.l);
    }

    @NonNull
    public b a() {
        return new b(toString());
    }

    @NonNull
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(this.h)) {
            builder.append(this.h);
            builder.append(":");
        }
        if (!TextUtils.isEmpty(this.i) && this.j > 0) {
            builder.append("//");
            builder.append(this.i);
            builder.append(":");
            builder.append(this.j);
        }
        if (!TextUtils.isEmpty(this.k)) {
            builder.append(this.k);
        }
        if (!TextUtils.isEmpty(this.l)) {
            builder.append("?");
            builder.append(this.l);
        }
        if (!TextUtils.isEmpty(this.m)) {
            builder.append("#");
            builder.append(this.m);
        }
        return builder.toString();
    }

    /* compiled from: Uri */
    public static class b {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public List<String> d;
        /* access modifiers changed from: private */
        public j<String, String> e;
        /* access modifiers changed from: private */
        public String f;

        private b(@NonNull String url) {
            URI uri = URI.create(url);
            this.a = uri.getScheme();
            this.b = uri.getHost();
            this.c = uri.getPort();
            this.d = m.f(uri.getPath());
            this.e = m.h(uri.getRawQuery());
            this.f = uri.getFragment();
        }

        public b h(@NonNull String path) {
            this.d = m.f(path);
            return this;
        }

        public m g() {
            return new m(this);
        }
    }

    public static List<String> f(String path) {
        List<String> pathList = new LinkedList<>();
        if (TextUtils.isEmpty(path)) {
            return pathList;
        }
        while (path.contains("//")) {
            path = path.replace("//", "/");
        }
        while (path.contains("/")) {
            if (path.startsWith("/")) {
                pathList.add("");
                path = path.substring(1);
            } else {
                int index = path.indexOf("/");
                pathList.add(path.substring(0, index));
                path = path.substring(index + 1);
            }
            if (path.contains("/") == 0) {
                pathList.add(path);
            }
        }
        return pathList;
    }

    public static j<String, String> h(String query) {
        MultiValueMap<String, String> valueMap = new g<>();
        if (!TextUtils.isEmpty(query)) {
            if (query.startsWith("?")) {
                query = query.substring(1);
            }
            StringTokenizer tokenizer = new StringTokenizer(query, "&");
            while (tokenizer.hasMoreElements()) {
                String element = tokenizer.nextToken();
                int end = element.indexOf("=");
                if (end > 0 && end < element.length() - 1) {
                    valueMap.add(element.substring(0, end), l.b(element.substring(end + 1), org.apache.commons.io.a.a("utf-8")));
                }
            }
        }
        return valueMap;
    }

    public static String g(List<String> pathList) {
        if (pathList == null || pathList.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String path : pathList) {
            builder.append("/");
            builder.append(path);
        }
        String path2 = builder.toString();
        while (path2.contains("//")) {
            path2 = path2.replace("//", "/");
        }
        return path2;
    }

    public static String e(j<String, String> params) {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry<String, List<String>> param = it.next();
            String key = param.getKey();
            List<String> valueList = param.getValue();
            if (valueList == null || valueList.isEmpty()) {
                builder.append(key);
                builder.append("=");
            } else {
                for (String value : valueList) {
                    builder.append(key);
                    builder.append("=");
                    builder.append(l.c(value, "utf-8"));
                }
            }
        }
        while (it.hasNext()) {
            Map.Entry<String, List<String>> param2 = it.next();
            String key2 = param2.getKey();
            List<String> valueList2 = param2.getValue();
            if (valueList2 == null || valueList2.isEmpty()) {
                builder.append("&");
                builder.append(key2);
                builder.append("=");
            } else {
                for (String value2 : valueList2) {
                    builder.append("&");
                    builder.append(key2);
                    builder.append("=");
                    builder.append(l.c(value2, "utf-8"));
                }
            }
        }
        return builder.toString();
    }
}
