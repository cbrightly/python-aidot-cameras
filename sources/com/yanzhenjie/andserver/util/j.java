package com.yanzhenjie.andserver.util;

import java.util.List;
import java.util.Map;

/* compiled from: MultiValueMap */
public interface j<K, V> extends Map<K, List<V>> {
    void add(K k, V v);

    V getFirst(K k);
}
