package coil.memory;

import android.graphics.Bitmap;
import coil.bitmap.e;
import coil.memory.MemoryCache;
import coil.memory.o;
import coil.util.m;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StrongMemoryCache.kt */
public interface r {
    @NotNull
    public static final a a = a.a;

    void a(int i);

    @Nullable
    o.a b(@NotNull MemoryCache.Key key);

    void c(@NotNull MemoryCache.Key key, @NotNull Bitmap bitmap, boolean z);

    /* compiled from: StrongMemoryCache.kt */
    public static final class a {
        static final /* synthetic */ a a = new a();

        private a() {
        }

        @NotNull
        public final r a(@NotNull u weakMemoryCache, @NotNull e referenceCounter, int maxSize, @Nullable m logger) {
            k.e(weakMemoryCache, "weakMemoryCache");
            k.e(referenceCounter, "referenceCounter");
            if (maxSize > 0) {
                return new RealStrongMemoryCache(weakMemoryCache, referenceCounter, maxSize, logger);
            }
            if (weakMemoryCache instanceof p) {
                return new f(weakMemoryCache);
            }
            return c.b;
        }
    }
}
