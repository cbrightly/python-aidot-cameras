package org.osgi.framework;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class CapabilityPermission$Properties extends AbstractMap<String, Object> {
    private final Map<String, Object> c;
    private final Map<String, Object> d;
    private volatile transient Set<Map.Entry<String, Object>> f;

    public Object get(Object k) {
        if (!(k instanceof String)) {
            return null;
        }
        String key = (String) k;
        if (key.charAt(0) == '@') {
            return this.d.get(key.substring(1));
        }
        Object value = this.c.get(key);
        if (value != null) {
            return value;
        }
        return this.d.get(key);
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        if (this.f != null) {
            return this.f;
        }
        Set<Map.Entry<String, Object>> all = new HashSet<>(this.d.size() + this.c.size());
        all.addAll(this.d.entrySet());
        all.addAll(this.c.entrySet());
        Set<T> unmodifiableSet = Collections.unmodifiableSet(all);
        this.f = unmodifiableSet;
        return unmodifiableSet;
    }
}
