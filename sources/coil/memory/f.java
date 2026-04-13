package coil.memory;

import android.graphics.Bitmap;
import coil.memory.MemoryCache;
import coil.memory.o;
import coil.util.c;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StrongMemoryCache.kt */
public final class f implements r {
    @NotNull
    private final u b;

    public f(@NotNull u weakMemoryCache) {
        k.e(weakMemoryCache, "weakMemoryCache");
        this.b = weakMemoryCache;
    }

    @Nullable
    public o.a b(@NotNull MemoryCache.Key key) {
        k.e(key, CacheEntity.KEY);
        return null;
    }

    public void c(@NotNull MemoryCache.Key key, @NotNull Bitmap bitmap, boolean isSampled) {
        k.e(key, CacheEntity.KEY);
        k.e(bitmap, "bitmap");
        this.b.d(key, bitmap, isSampled, c.a(bitmap));
    }

    public void a(int level) {
    }
}
