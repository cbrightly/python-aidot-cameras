package com.google.android.datatransport.runtime.dagger.internal;

import com.google.android.datatransport.runtime.dagger.internal.AbstractMapFactory;
import java.util.Collections;
import java.util.Map;
import javax.inject.Provider;
import javax.inject.a;

public final class MapFactory<K, V> extends AbstractMapFactory<K, V, V> {
    private static final a<Map<Object, Object>> EMPTY = InstanceFactory.create(Collections.emptyMap());

    public static <K, V> Builder<K, V> builder(int size) {
        return new Builder<>(size);
    }

    public static <K, V> a<Map<K, V>> emptyMapProvider() {
        return EMPTY;
    }

    private MapFactory(Map<K, a<V>> map) {
        super(map);
    }

    public Map<K, V> get() {
        Map<K, V> result = DaggerCollections.newLinkedHashMapWithExpectedSize(contributingMap().size());
        for (Map.Entry<K, Provider<V>> entry : contributingMap().entrySet()) {
            result.put(entry.getKey(), ((a) entry.getValue()).get());
        }
        return Collections.unmodifiableMap(result);
    }

    public static final class Builder<K, V> extends AbstractMapFactory.Builder<K, V, V> {
        private Builder(int size) {
            super(size);
        }

        public Builder<K, V> put(K key, a<V> providerOfValue) {
            super.put(key, providerOfValue);
            return this;
        }

        public Builder<K, V> putAll(a<Map<K, V>> mapFactory) {
            super.putAll(mapFactory);
            return this;
        }

        public MapFactory<K, V> build() {
            return new MapFactory<>(this.map);
        }
    }
}
