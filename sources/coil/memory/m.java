package coil.memory;

import coil.bitmap.e;
import coil.memory.MemoryCache;
import coil.memory.o;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MemoryCacheService.kt */
public final class m {
    @NotNull
    private final e a;
    @NotNull
    private final r b;
    @NotNull
    private final u c;

    public m(@NotNull e referenceCounter, @NotNull r strongMemoryCache, @NotNull u weakMemoryCache) {
        k.e(referenceCounter, "referenceCounter");
        k.e(strongMemoryCache, "strongMemoryCache");
        k.e(weakMemoryCache, "weakMemoryCache");
        this.a = referenceCounter;
        this.b = strongMemoryCache;
        this.c = weakMemoryCache;
    }

    @Nullable
    public final o.a a(@Nullable MemoryCache.Key key) {
        if (key == null) {
            return null;
        }
        o.a value = this.b.b(key);
        if (value == null) {
            value = this.c.b(key);
        }
        if (value != null) {
            this.a.c(value.b());
        }
        return value;
    }
}
