package coil.memory;

import android.graphics.Bitmap;
import coil.memory.MemoryCache;
import coil.memory.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: WeakMemoryCache.kt */
public interface u {
    void a(int i);

    @Nullable
    o.a b(@NotNull MemoryCache.Key key);

    boolean c(@NotNull Bitmap bitmap);

    void d(@NotNull MemoryCache.Key key, @NotNull Bitmap bitmap, boolean z, int i);
}
