package com.yanzhenjie.andserver.framework.mapping;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.yanzhenjie.andserver.framework.mapping.Path;
import com.yanzhenjie.andserver.util.k;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* compiled from: Path */
public class f implements k {
    private List<a> h = new LinkedList();

    @NonNull
    public List<a> b() {
        return this.h;
    }

    public void a(@NonNull String ruleText) {
        a rule = new a();
        rule.b(d(ruleText));
        this.h.add(rule);
    }

    /* compiled from: Path */
    public static class a {
        private List<b> a;

        public List<b> a() {
            return this.a;
        }

        public void b(List<b> segments) {
            this.a = segments;
        }
    }

    /* compiled from: Path */
    public static class b {
        private final String a;
        private final boolean b;

        public b(String value, boolean isBlurred) {
            this.a = value;
            this.b = isBlurred;
        }

        public String a() {
            return this.a;
        }

        public boolean b() {
            return this.b;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            return this.a.equals(((b) obj).a);
        }

        public String toString() {
            return this.a;
        }
    }

    @NonNull
    public static List<b> d(@NonNull String path) {
        int i;
        List<Path.Segment> segmentList = new LinkedList<>();
        if (!TextUtils.isEmpty(path)) {
            while (path.startsWith("/")) {
                path = path.substring(1);
            }
            while (true) {
                if (!path.endsWith("/")) {
                    break;
                }
                path = path.substring(0, path.length() - 1);
            }
            for (String segmentText : path.split("/")) {
                segmentList.add(new b(segmentText, segmentText.contains("{")));
            }
        }
        return Collections.unmodifiableList(segmentList);
    }

    @NonNull
    public static String c(@NonNull List<b> segments) {
        StringBuilder builder = new StringBuilder("");
        if (segments.isEmpty()) {
            builder.append("/");
        }
        for (b segment : segments) {
            builder.append("/");
            builder.append(segment.a());
        }
        return builder.toString();
    }
}
