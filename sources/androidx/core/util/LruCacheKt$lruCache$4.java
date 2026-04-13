package androidx.core.util;

import android.util.LruCache;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.r;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LruCache.kt */
public final class LruCacheKt$lruCache$4 extends LruCache<K, V> {
    final /* synthetic */ l<K, V> $create;
    final /* synthetic */ int $maxSize;
    final /* synthetic */ r<Boolean, K, V, V, x> $onEntryRemoved;
    final /* synthetic */ p<K, V, Integer> $sizeOf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LruCacheKt$lruCache$4(p<? super K, ? super V, Integer> $sizeOf2, l<? super K, ? extends V> $create2, r<? super Boolean, ? super K, ? super V, ? super V, x> $onEntryRemoved2, int $maxSize2) {
        super($maxSize2);
        this.$sizeOf = $sizeOf2;
        this.$create = $create2;
        this.$onEntryRemoved = $onEntryRemoved2;
        this.$maxSize = $maxSize2;
    }

    /* access modifiers changed from: protected */
    public int sizeOf(@NotNull K key, @NotNull V value) {
        k.e(key, CacheEntity.KEY);
        k.e(value, "value");
        return this.$sizeOf.invoke(key, value).intValue();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public V create(@NotNull K key) {
        k.e(key, CacheEntity.KEY);
        return this.$create.invoke(key);
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(boolean evicted, @NotNull K key, @NotNull V oldValue, @Nullable V newValue) {
        k.e(key, CacheEntity.KEY);
        k.e(oldValue, "oldValue");
        this.$onEntryRemoved.invoke(Boolean.valueOf(evicted), key, oldValue, newValue);
    }
}
