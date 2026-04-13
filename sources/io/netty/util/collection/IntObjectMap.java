package io.netty.util.collection;

import java.util.Collection;

public interface IntObjectMap<V> {

    public interface Entry<V> {
        int key();

        void setValue(V v);

        V value();
    }

    void clear();

    boolean containsKey(int i);

    boolean containsValue(V v);

    Iterable<Entry<V>> entries();

    V get(int i);

    boolean isEmpty();

    int[] keys();

    V put(int i, V v);

    void putAll(IntObjectMap<V> intObjectMap);

    V remove(int i);

    int size();

    Collection<V> values();

    V[] values(Class<V> cls);
}
