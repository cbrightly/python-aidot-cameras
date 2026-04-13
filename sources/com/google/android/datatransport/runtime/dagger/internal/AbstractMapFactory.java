package com.google.android.datatransport.runtime.dagger.internal;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.a;

public abstract class AbstractMapFactory<K, V, V2> implements Factory<Map<K, V2>> {
    /* access modifiers changed from: private */
    public final Map<K, a<V>> contributingMap;

    public abstract /* synthetic */ T get();

    AbstractMapFactory(Map<K, a<V>> map) {
        this.contributingMap = Collections.unmodifiableMap(map);
    }

    /* access modifiers changed from: package-private */
    public final Map<K, a<V>> contributingMap() {
        return this.contributingMap;
    }

    public static abstract class Builder<K, V, V2> {
        final LinkedHashMap<K, a<V>> map;

        Builder(int size) {
            this.map = DaggerCollections.newLinkedHashMapWithExpectedSize(size);
        }

        /* access modifiers changed from: package-private */
        public Builder<K, V, V2> put(K key, a<V> providerOfValue) {
            this.map.put(Preconditions.checkNotNull(key, CacheEntity.KEY), Preconditions.checkNotNull(providerOfValue, "provider"));
            return this;
        }

        /* access modifiers changed from: package-private */
        public Builder<K, V, V2> putAll(a<Map<K, V2>> mapOfProviders) {
            if (mapOfProviders instanceof DelegateFactory) {
                return putAll(((DelegateFactory) mapOfProviders).getDelegate());
            }
            this.map.putAll(((AbstractMapFactory) mapOfProviders).contributingMap);
            return this;
        }
    }
}
