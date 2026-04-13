package org.apache.http.config;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: Registry */
public final class d<I> implements b<I> {
    private final Map<String, I> a;

    d(Map<String, I> map) {
        this.a = new ConcurrentHashMap(map);
    }

    public I lookup(String key) {
        if (key == null) {
            return null;
        }
        return this.a.get(key.toLowerCase(Locale.ROOT));
    }

    public String toString() {
        return this.a.toString();
    }
}
