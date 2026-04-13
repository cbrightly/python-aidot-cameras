package io.netty.handler.codec.serialization;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

public final class WeakReferenceMap<K, V> extends ReferenceMap<K, V> {
    WeakReferenceMap(Map<K, Reference<V>> delegate) {
        super(delegate);
    }

    /* access modifiers changed from: package-private */
    public Reference<V> fold(V value) {
        return new WeakReference(value);
    }
}
