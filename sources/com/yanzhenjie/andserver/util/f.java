package com.yanzhenjie.andserver.util;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* compiled from: LinkedCaseInsensitiveMap */
public class f<V> implements Map<String, V>, Serializable, Cloneable {
    /* access modifiers changed from: private */
    public final HashMap<String, String> mCaseInsensitiveKeys;
    private final Locale mLocale;
    private final LinkedHashMap<String, V> mSource;

    public f() {
        this((Locale) null);
    }

    public f(Locale locale) {
        this(16, locale);
    }

    public f(int initialCapacity) {
        this(initialCapacity, (Locale) null);
    }

    /* compiled from: LinkedCaseInsensitiveMap */
    public class a extends LinkedHashMap<String, V> {
        a(int x0) {
            super(x0);
        }

        public boolean containsKey(Object key) {
            return f.this.containsKey(key);
        }

        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Map.Entry<String, V> eldest) {
            boolean isRemoved = f.this.removeEldestEntry(eldest);
            if (isRemoved) {
                f.this.mCaseInsensitiveKeys.remove(f.this.convertKey(eldest.getKey()));
            }
            return isRemoved;
        }
    }

    public f(int initialCapacity, Locale locale) {
        this.mSource = new a(initialCapacity);
        this.mCaseInsensitiveKeys = new HashMap<>(initialCapacity);
        this.mLocale = locale != null ? locale : Locale.getDefault();
    }

    private f(f<V> other) {
        this.mSource = (LinkedHashMap) other.mSource.clone();
        this.mCaseInsensitiveKeys = (HashMap) other.mCaseInsensitiveKeys.clone();
        this.mLocale = other.mLocale;
    }

    public int size() {
        return this.mSource.size();
    }

    public boolean isEmpty() {
        return this.mSource.isEmpty();
    }

    public boolean containsKey(Object key) {
        return (key instanceof String) && this.mCaseInsensitiveKeys.containsKey(convertKey((String) key));
    }

    public boolean containsValue(Object value) {
        return this.mSource.containsValue(value);
    }

    public V get(Object key) {
        String caseInsensitiveKey;
        if (!(key instanceof String) || (caseInsensitiveKey = this.mCaseInsensitiveKeys.get(convertKey((String) key))) == null) {
            return null;
        }
        return this.mSource.get(caseInsensitiveKey);
    }

    public V getOrDefault(Object key, V defaultValue) {
        String caseInsensitiveKey;
        if (!(key instanceof String) || (caseInsensitiveKey = this.mCaseInsensitiveKeys.get(convertKey((String) key))) == null) {
            return defaultValue;
        }
        return this.mSource.get(caseInsensitiveKey);
    }

    public V put(String key, V value) {
        String oldKey = this.mCaseInsensitiveKeys.put(convertKey(key), key);
        if (oldKey != null && !oldKey.equals(key)) {
            this.mSource.remove(oldKey);
        }
        return this.mSource.put(key, value);
    }

    public void putAll(@NonNull Map<? extends String, ? extends V> map) {
        if (!map.isEmpty()) {
            for (Map.Entry<? extends String, ? extends V> entry : map.entrySet()) {
                put((String) entry.getKey(), entry.getValue());
            }
        }
    }

    public V remove(Object key) {
        String caseInsensitiveKey;
        if (!(key instanceof String) || (caseInsensitiveKey = this.mCaseInsensitiveKeys.remove(convertKey((String) key))) == null) {
            return null;
        }
        return this.mSource.remove(caseInsensitiveKey);
    }

    public void clear() {
        this.mCaseInsensitiveKeys.clear();
        this.mSource.clear();
    }

    @NonNull
    public Set<String> keySet() {
        return this.mSource.keySet();
    }

    @NonNull
    public Collection<V> values() {
        return this.mSource.values();
    }

    @NonNull
    public Set<Map.Entry<String, V>> entrySet() {
        return this.mSource.entrySet();
    }

    public f<V> clone() {
        return new f<>(this);
    }

    public boolean equals(Object obj) {
        return this.mSource.equals(obj);
    }

    public int hashCode() {
        return this.mSource.hashCode();
    }

    public String toString() {
        return this.mSource.toString();
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    /* access modifiers changed from: protected */
    public String convertKey(String key) {
        return key.toLowerCase(getLocale());
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Map.Entry<String, V> entry) {
        return false;
    }
}
