package coil.memory;

import android.graphics.Bitmap;
import coil.bitmap.c;
import coil.bitmap.e;
import coil.memory.MemoryCache;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealMemoryCache.kt */
public final class o implements MemoryCache {
    @NotNull
    private final r a;
    @NotNull
    private final u b;
    @NotNull
    private final e c;
    @NotNull
    private final c d;

    /* compiled from: RealMemoryCache.kt */
    public interface a {
        boolean a();

        @NotNull
        Bitmap b();
    }

    public o(@NotNull r strongMemoryCache, @NotNull u weakMemoryCache, @NotNull e referenceCounter, @NotNull c bitmapPool) {
        k.e(strongMemoryCache, "strongMemoryCache");
        k.e(weakMemoryCache, "weakMemoryCache");
        k.e(referenceCounter, "referenceCounter");
        k.e(bitmapPool, "bitmapPool");
        this.a = strongMemoryCache;
        this.b = weakMemoryCache;
        this.c = referenceCounter;
        this.d = bitmapPool;
    }

    @NotNull
    public final r d() {
        return this.a;
    }

    @NotNull
    public final u e() {
        return this.b;
    }

    @NotNull
    public final e c() {
        return this.c;
    }

    @NotNull
    public final c a() {
        return this.d;
    }

    @Nullable
    public Bitmap b(@NotNull MemoryCache.Key key) {
        k.e(key, CacheEntity.KEY);
        a value = this.a.b(key);
        if (value == null) {
            value = this.b.b(key);
        }
        if (value == null) {
            return null;
        }
        Bitmap it = value.b();
        c().a(it, false);
        return it;
    }
}
