package androidx.collection;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.r;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: LruCache.kt */
public final class LruCacheKt$lruCache$4 extends LruCache<K, V> {
    final /* synthetic */ l $create;
    final /* synthetic */ int $maxSize;
    final /* synthetic */ r $onEntryRemoved;
    final /* synthetic */ p $sizeOf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LruCacheKt$lruCache$4(p $captured_local_variable$0, l $captured_local_variable$1, r $captured_local_variable$2, int $captured_local_variable$3, int $super_call_param$4) {
        super($super_call_param$4);
        this.$sizeOf = $captured_local_variable$0;
        this.$create = $captured_local_variable$1;
        this.$onEntryRemoved = $captured_local_variable$2;
        this.$maxSize = $captured_local_variable$3;
    }

    /* access modifiers changed from: protected */
    public int sizeOf(@NotNull K key, @NotNull V value) {
        k.f(key, CacheEntity.KEY);
        k.f(value, "value");
        return ((Number) this.$sizeOf.invoke(key, value)).intValue();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public V create(@NotNull K key) {
        k.f(key, CacheEntity.KEY);
        return this.$create.invoke(key);
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(boolean evicted, @NotNull K key, @NotNull V oldValue, @Nullable V newValue) {
        k.f(key, CacheEntity.KEY);
        k.f(oldValue, "oldValue");
        this.$onEntryRemoved.invoke(Boolean.valueOf(evicted), key, oldValue, newValue);
    }
}
