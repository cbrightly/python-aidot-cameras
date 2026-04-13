package com.google.maps.android.data.geojson;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BiMultiMap<K> extends HashMap<K, Object> {
    private final Map<Object, K> mValuesToKeys = new HashMap();

    public void putAll(Map<? extends K, ?> map) {
        for (Map.Entry<? extends K, ?> entry : map.entrySet()) {
            put(entry.getKey(), (Object) entry.getValue());
        }
    }

    public Object put(K key, Object value) {
        if (value instanceof Collection) {
            return put(key, (Collection) value);
        }
        this.mValuesToKeys.put(value, key);
        return super.put(key, value);
    }

    public Object put(K key, Collection values) {
        for (Object value : values) {
            this.mValuesToKeys.put(value, key);
        }
        return super.put(key, values);
    }

    public Object remove(Object key) {
        Object value = super.remove(key);
        if (value instanceof Collection) {
            for (Object valueItem : (Collection) value) {
                this.mValuesToKeys.remove(valueItem);
            }
        } else {
            this.mValuesToKeys.remove(value);
        }
        return value;
    }

    public void clear() {
        super.clear();
        this.mValuesToKeys.clear();
    }

    public BiMultiMap<K> clone() {
        BiMultiMap<K> cloned = new BiMultiMap<>();
        cloned.putAll((Map) super.clone());
        return cloned;
    }

    public K getKey(Object value) {
        return this.mValuesToKeys.get(value);
    }
}
