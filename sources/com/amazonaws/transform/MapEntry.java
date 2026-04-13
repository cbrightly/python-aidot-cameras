package com.amazonaws.transform;

import java.util.Map;

public class MapEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V value2) {
        this.value = value2;
        return value2;
    }

    public K setKey(K key2) {
        this.key = key2;
        return key2;
    }
}
