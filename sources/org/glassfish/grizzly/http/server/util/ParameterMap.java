package org.glassfish.grizzly.http.server.util;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ParameterMap extends LinkedHashMap<String, String[]> {
    private boolean locked = false;

    public ParameterMap() {
    }

    public ParameterMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ParameterMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ParameterMap(Map<String, String[]> map) {
        super(map);
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked2) {
        this.locked = locked2;
    }

    public void clear() {
        if (!this.locked) {
            super.clear();
            return;
        }
        throw new IllegalStateException("Illegal attempt to modify ParameterMap while locked.");
    }

    public String[] put(String key, String[] value) {
        if (!this.locked) {
            return (String[]) super.put(key, value);
        }
        throw new IllegalStateException("Illegal attempt to modify ParameterMap while locked.");
    }

    public void putAll(Map map) {
        if (!this.locked) {
            super.putAll(map);
            return;
        }
        throw new IllegalStateException("Illegal attempt to modify ParameterMap while locked.");
    }

    public Object remove(String key) {
        if (!this.locked) {
            return super.remove(key);
        }
        throw new IllegalStateException("Illegal attempt to modify ParameterMap while locked.");
    }
}
