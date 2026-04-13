package coil.bitmap;

import android.graphics.Bitmap;
import coil.util.c;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: EmptyBitmapPool.kt */
public final class f implements c {
    public void b(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
        bitmap.recycle();
    }

    @NotNull
    public Bitmap c(int width, int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        return e(width, height, config);
    }

    @NotNull
    public Bitmap e(int width, int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        d(config);
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config);
        k.d(createBitmap, "createBitmap(width, height, config)");
        return createBitmap;
    }

    public void a(int level) {
    }

    private final void d(Bitmap.Config config) {
        if (!(!c.d(config))) {
            throw new IllegalArgumentException("Cannot create a mutable hardware bitmap.".toString());
        }
    }
}
