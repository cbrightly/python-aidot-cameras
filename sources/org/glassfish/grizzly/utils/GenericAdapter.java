package org.glassfish.grizzly.utils;

public interface GenericAdapter<K, V> {
    V adapt(K k);
}
