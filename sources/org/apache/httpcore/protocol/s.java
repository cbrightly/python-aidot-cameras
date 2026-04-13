package org.apache.httpcore.protocol;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.httpcore.util.a;
import org.slf4j.e;

/* compiled from: UriPatternMatcher */
public class s<T> {
    private final Map<String, T> a = new LinkedHashMap();

    public synchronized void c(String pattern, T obj) {
        a.g(pattern, "URI request pattern");
        this.a.put(pattern, obj);
    }

    public synchronized T a(String path) {
        T obj;
        a.g(path, "Request path");
        obj = this.a.get(path);
        if (obj == null) {
            String bestMatch = null;
            for (String pattern : this.a.keySet()) {
                if (b(pattern, path) && (bestMatch == null || bestMatch.length() < pattern.length() || (bestMatch.length() == pattern.length() && pattern.endsWith(e.ANY_MARKER)))) {
                    obj = this.a.get(pattern);
                    bestMatch = pattern;
                }
            }
        }
        return obj;
    }

    /* access modifiers changed from: protected */
    public boolean b(String pattern, String path) {
        if (pattern.equals(e.ANY_MARKER)) {
            return true;
        }
        if (pattern.endsWith(e.ANY_MARKER) && path.startsWith(pattern.substring(0, pattern.length() - 1))) {
            return true;
        }
        if (!pattern.startsWith(e.ANY_MARKER) || !path.endsWith(pattern.substring(1, pattern.length()))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return this.a.toString();
    }
}
