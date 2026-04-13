package coil.request;

import android.graphics.drawable.Drawable;
import coil.decode.b;
import coil.memory.MemoryCache;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageResult.kt */
public abstract class j {
    public /* synthetic */ j(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Nullable
    public abstract Drawable a();

    @NotNull
    public abstract i b();

    private j() {
    }

    /* compiled from: ImageResult.kt */
    public static final class a {
        @Nullable
        private final MemoryCache.Key a;
        private final boolean b;
        @NotNull
        private final b c;
        private final boolean d;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return k.a(this.a, aVar.a) && this.b == aVar.b && this.c == aVar.c && this.d == aVar.d;
        }

        public int hashCode() {
            MemoryCache.Key key = this.a;
            int hashCode = (key == null ? 0 : key.hashCode()) * 31;
            boolean z = this.b;
            boolean z2 = true;
            if (z) {
                z = true;
            }
            int hashCode2 = (((hashCode + (z ? 1 : 0)) * 31) + this.c.hashCode()) * 31;
            boolean z3 = this.d;
            if (!z3) {
                z2 = z3;
            }
            return hashCode2 + (z2 ? 1 : 0);
        }

        @NotNull
        public String toString() {
            return "Metadata(memoryCacheKey=" + this.a + ", isSampled=" + this.b + ", dataSource=" + this.c + ", isPlaceholderMemoryCacheKeyPresent=" + this.d + ')';
        }

        public a(@Nullable MemoryCache.Key memoryCacheKey, boolean isSampled, @NotNull b dataSource, boolean isPlaceholderMemoryCacheKeyPresent) {
            k.e(dataSource, "dataSource");
            this.a = memoryCacheKey;
            this.b = isSampled;
            this.c = dataSource;
            this.d = isPlaceholderMemoryCacheKeyPresent;
        }

        @NotNull
        public final b a() {
            return this.c;
        }

        public final boolean b() {
            return this.d;
        }
    }
}
