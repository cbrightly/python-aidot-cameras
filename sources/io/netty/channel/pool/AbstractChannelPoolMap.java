package io.netty.channel.pool;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.netty.channel.pool.ChannelPool;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReadOnlyIterator;
import java.io.Closeable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractChannelPoolMap<K, P extends ChannelPool> implements ChannelPoolMap<K, P>, Iterable<Map.Entry<K, P>>, Closeable {
    private final ConcurrentMap<K, P> map = PlatformDependent.newConcurrentHashMap();

    /* access modifiers changed from: protected */
    public abstract P newPool(K k);

    public final P get(K key) {
        P pool = (ChannelPool) this.map.get(ObjectUtil.checkNotNull(key, CacheEntity.KEY));
        if (pool != null) {
            return pool;
        }
        P pool2 = newPool(key);
        P old = (ChannelPool) this.map.putIfAbsent(key, pool2);
        if (old == null) {
            return pool2;
        }
        pool2.close();
        return old;
    }

    public final boolean remove(K key) {
        P pool = (ChannelPool) this.map.remove(ObjectUtil.checkNotNull(key, CacheEntity.KEY));
        if (pool == null) {
            return false;
        }
        pool.close();
        return true;
    }

    public final Iterator<Map.Entry<K, P>> iterator() {
        return new ReadOnlyIterator(this.map.entrySet().iterator());
    }

    public final int size() {
        return this.map.size();
    }

    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    public final boolean contains(K key) {
        return this.map.containsKey(ObjectUtil.checkNotNull(key, CacheEntity.KEY));
    }

    public final void close() {
        for (K key : this.map.keySet()) {
            remove(key);
        }
    }
}
