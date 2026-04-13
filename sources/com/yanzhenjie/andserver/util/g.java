package com.yanzhenjie.andserver.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: LinkedMultiValueMap */
public class g<K, V> implements j<K, V>, Cloneable {
    private final Map<K, List<V>> c;

    public g() {
        this.c = new LinkedHashMap();
    }

    public g(Map<K, List<V>> otherMap) {
        this.c = new LinkedHashMap(otherMap);
    }

    public void add(K key, V value) {
        List<V> values = this.c.get(key);
        if (values == null) {
            values = new LinkedList<>();
            this.c.put(key, values);
        }
        values.add(value);
    }

    public V getFirst(K key) {
        List<V> values = this.c.get(key);
        if (values != null) {
            return values.get(0);
        }
        return null;
    }

    public int size() {
        return this.c.size();
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.c.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.c.containsValue(value);
    }

    /* renamed from: b */
    public List<V> get(Object key) {
        return this.c.get(key);
    }

    /* renamed from: c */
    public List<V> put(K key, List<V> value) {
        return this.c.put(key, value);
    }

    /* renamed from: e */
    public List<V> remove(Object key) {
        return this.c.remove(key);
    }

    public void putAll(Map<? extends K, ? extends List<V>> map) {
        this.c.putAll(map);
    }

    public void clear() {
        this.c.clear();
    }

    public Set<K> keySet() {
        return this.c.keySet();
    }

    public Collection<List<V>> values() {
        return this.c.values();
    }

    public Set<Map.Entry<K, List<V>>> entrySet() {
        return this.c.entrySet();
    }

    /* renamed from: a */
    public g<K, V> clone() {
        return new g<>(this);
    }

    public boolean equals(Object obj) {
        return this.c.equals(obj);
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public String toString() {
        return this.c.toString();
    }
}
