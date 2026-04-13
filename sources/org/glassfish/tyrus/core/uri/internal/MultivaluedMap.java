package org.glassfish.tyrus.core.uri.internal;

import java.util.List;
import java.util.Map;

public interface MultivaluedMap<K, V> extends Map<K, List<V>> {
    void add(K k, V v);

    void addAll(K k, List<V> list);

    void addAll(K k, V... vArr);

    void addFirst(K k, V v);

    boolean equalsIgnoreValueOrder(MultivaluedMap<K, V> multivaluedMap);

    V getFirst(K k);

    void putSingle(K k, V v);
}
