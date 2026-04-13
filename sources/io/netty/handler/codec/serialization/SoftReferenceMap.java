package io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;

public final class SoftReferenceMap<K, V> extends ReferenceMap<K, V> {
    SoftReferenceMap(Map<K, Reference<V>> delegate) {
        super(delegate);
    }

    /* access modifiers changed from: package-private */
    public Reference<V> fold(V value) {
        return new SoftReference(value);
    }
}
