package org.glassfish.tyrus.core.uri.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultivaluedHashMap<K, V> extends AbstractMultivaluedMap<K, V> implements Serializable {
    private static final long serialVersionUID = -6052320403766368902L;

    public MultivaluedHashMap() {
        super(new HashMap());
    }

    public MultivaluedHashMap(int initialCapacity) {
        super(new HashMap(initialCapacity));
    }

    public MultivaluedHashMap(int initialCapacity, float loadFactor) {
        super(new HashMap(initialCapacity, loadFactor));
    }

    public MultivaluedHashMap(MultivaluedMap<? extends K, ? extends V> map) {
        this();
        putAll(map);
    }

    private <T extends K, U extends V> void putAll(MultivaluedMap<T, U> map) {
        for (Map.Entry<T, List<U>> e : map.entrySet()) {
            this.store.put(e.getKey(), new ArrayList(e.getValue()));
        }
    }

    public MultivaluedHashMap(Map<? extends K, ? extends V> map) {
        this();
        for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
            putSingle(e.getKey(), e.getValue());
        }
    }
}
