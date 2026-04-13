package coil.bitmap;

import android.graphics.Bitmap;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BitmapReferenceCounter.kt */
public final class g implements e {
    @NotNull
    public static final g a = new g();

    private g() {
    }

    public void c(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
    }

    public boolean b(@NotNull Bitmap bitmap) {
        k.e(bitmap, "bitmap");
        return false;
    }

    public void a(@NotNull Bitmap bitmap, boolean isValid) {
        k.e(bitmap, "bitmap");
    }
}
