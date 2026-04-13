package com.google.android.datatransport.runtime.dagger.internal;

import com.google.android.datatransport.runtime.dagger.Lazy;
import com.google.android.datatransport.runtime.dagger.internal.AbstractMapFactory;
import java.util.Map;
import javax.inject.a;

public final class MapProviderFactory<K, V> extends AbstractMapFactory<K, V, a<V>> implements Lazy<Map<K, a<V>>> {
    public static <K, V> Builder<K, V> builder(int size) {
        return new Builder<>(size);
    }

    private MapProviderFactory(Map<K, a<V>> contributingMap) {
        super(contributingMap);
    }

    public Map<K, a<V>> get() {
        return contributingMap();
    }

    public static final class Builder<K, V> extends AbstractMapFactory.Builder<K, V, a<V>> {
        private Builder(int size) {
            super(size);
        }

        public Builder<K, V> put(K key, a<V> providerOfValue) {
            super.put(key, providerOfValue);
            return this;
        }

        public Builder<K, V> putAll(a<Map<K, a<V>>> mapProviderFactory) {
            super.putAll(mapProviderFactory);
            return this;
        }

        public MapProviderFactory<K, V> build() {
            return new MapProviderFactory<>(this.map);
        }
    }
}
