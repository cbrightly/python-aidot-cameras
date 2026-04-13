package androidx.core.util;

import android.util.LruCache;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.r;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: LruCache.kt */
public final class LruCacheKt {
    public static /* synthetic */ LruCache lruCache$default(int maxSize, p sizeOf, l create, r onEntryRemoved, int i, Object obj) {
        if ((i & 2) != 0) {
            sizeOf = LruCacheKt$lruCache$1.INSTANCE;
        }
        if ((i & 4) != 0) {
            create = LruCacheKt$lruCache$2.INSTANCE;
        }
        if ((i & 8) != 0) {
            onEntryRemoved = LruCacheKt$lruCache$3.INSTANCE;
        }
        k.e(sizeOf, "sizeOf");
        k.e(create, "create");
        k.e(onEntryRemoved, "onEntryRemoved");
        return new LruCacheKt$lruCache$4(sizeOf, create, onEntryRemoved, maxSize);
    }

    @NotNull
    public static final <K, V> LruCache<K, V> lruCache(int maxSize, @NotNull p<? super K, ? super V, Integer> sizeOf, @NotNull l<? super K, ? extends V> create, @NotNull r<? super Boolean, ? super K, ? super V, ? super V, x> onEntryRemoved) {
        k.e(sizeOf, "sizeOf");
        k.e(create, "create");
        k.e(onEntryRemoved, "onEntryRemoved");
        return new LruCacheKt$lruCache$4(sizeOf, create, onEntryRemoved, maxSize);
    }
}
