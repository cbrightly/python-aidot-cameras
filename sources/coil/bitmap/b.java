package coil.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.Px;
import androidx.annotation.VisibleForTesting;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@VisibleForTesting
/* compiled from: BitmapPoolStrategy.kt */
public final class b implements d {
    @NotNull
    private final coil.collection.a<a, Bitmap> b = new coil.collection.a<>();

    public void b(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
        coil.collection.a<a, Bitmap> aVar = this.b;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap.Config config = bitmap.getConfig();
        k.d(config, "bitmap.config");
        aVar.d(new a(width, height, config), bitmap);
    }

    @Nullable
    public Bitmap c(@Px int width, @Px int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        return this.b.g(new a(width, height, config));
    }

    @Nullable
    public Bitmap removeLast() {
        return this.b.f();
    }

    @NotNull
    public String d(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap.Config config = bitmap.getConfig();
        k.d(config, "bitmap.config");
        return a(width, height, config);
    }

    @NotNull
    public String a(int width, int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        return '[' + width + " x " + height + "], " + config;
    }

    @NotNull
    public String toString() {
        return k.l("AttributeStrategy: entries=", this.b);
    }

    /* compiled from: BitmapPoolStrategy.kt */
    public static final class a {
        private final int a;
        private final int b;
        @NotNull
        private final Bitmap.Config c;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return this.a == aVar.a && this.b == aVar.b && this.c == aVar.c;
        }

        public int hashCode() {
            return (((this.a * 31) + this.b) * 31) + this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return "Key(width=" + this.a + ", height=" + this.b + ", config=" + this.c + ')';
        }

        public a(@Px int width, @Px int height, @NotNull Bitmap.Config config) {
            k.e(config, "config");
            this.a = width;
            this.b = height;
            this.c = config;
        }
    }
}
