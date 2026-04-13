package coil.memory;

import android.graphics.Bitmap;
import coil.memory.MemoryCache;
import coil.memory.o;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WeakMemoryCache.kt */
public final class e implements u {
    @NotNull
    public static final e a = new e();

    private e() {
    }

    @Nullable
    public o.a b(@NotNull MemoryCache.Key key) {
        k.e(key, CacheEntity.KEY);
        return null;
    }

    public void d(@NotNull MemoryCache.Key key, @NotNull Bitmap bitmap, boolean isSampled, int size) {
        k.e(key, CacheEntity.KEY);
        k.e(bitmap, "bitmap");
    }

    public boolean c(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
        return false;
    }

    public void a(int level) {
    }
}
