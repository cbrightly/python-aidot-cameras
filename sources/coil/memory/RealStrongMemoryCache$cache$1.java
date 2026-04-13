package coil.memory;

import androidx.collection.LruCache;
import coil.memory.MemoryCache;
import coil.memory.RealStrongMemoryCache;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StrongMemoryCache.kt */
public final class RealStrongMemoryCache$cache$1 extends LruCache<MemoryCache.Key, RealStrongMemoryCache.b> {
    final /* synthetic */ RealStrongMemoryCache a;
    final /* synthetic */ int b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    RealStrongMemoryCache$cache$1(RealStrongMemoryCache $receiver, int $maxSize) {
        super($maxSize);
        this.a = $receiver;
        this.b = $maxSize;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void entryRemoved(boolean evicted, @NotNull MemoryCache.Key key, @NotNull RealStrongMemoryCache.b oldValue, @Nullable RealStrongMemoryCache.b newValue) {
        k.e(key, CacheEntity.KEY);
        k.e(oldValue, "oldValue");
        if (!this.a.d.b(oldValue.b())) {
            this.a.c.d(key, oldValue.b(), oldValue.a(), oldValue.c());
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public int sizeOf(@NotNull MemoryCache.Key key, @NotNull RealStrongMemoryCache.b value) {
        k.e(key, CacheEntity.KEY);
        k.e(value, "value");
        return value.c();
    }
}
