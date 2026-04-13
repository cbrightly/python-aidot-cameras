package coil.decode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.WorkerThread;
import coil.bitmap.c;
import coil.size.OriginalSize;
import coil.size.PixelSize;
import coil.size.Size;
import coil.size.e;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DrawableDecoderService.kt */
public final class f {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final c b;

    public f(@NotNull c bitmapPool) {
        k.e(bitmapPool, "bitmapPool");
        this.b = bitmapPool;
    }

    @NotNull
    @WorkerThread
    public final Bitmap a(@NotNull Drawable drawable, @NotNull Bitmap.Config config, @NotNull Size size, @NotNull e scale, boolean allowInexactSize) {
        Drawable drawable2 = drawable;
        Bitmap.Config config2 = config;
        Size size2 = size;
        e eVar = scale;
        k.e(drawable2, "drawable");
        k.e(config2, "config");
        k.e(size2, "size");
        k.e(eVar, "scale");
        if (drawable2 instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable2).getBitmap();
            k.d(bitmap, "bitmap");
            if (!b(bitmap, config2)) {
                boolean z = allowInexactSize;
            } else if (c(allowInexactSize, size2, bitmap, eVar)) {
                return bitmap;
            }
        } else {
            boolean z2 = allowInexactSize;
        }
        Drawable safeDrawable = drawable.mutate();
        k.d(safeDrawable, "drawable.mutate()");
        int it = coil.util.f.i(safeDrawable);
        int i = 512;
        if (it <= 0) {
            it = 512;
        }
        int it2 = coil.util.f.d(safeDrawable);
        if (it2 > 0) {
            i = it2;
        }
        int srcHeight = i;
        d dVar = d.a;
        PixelSize b2 = d.b(it, srcHeight, size2, eVar);
        int width = b2.a();
        int height = b2.b();
        Bitmap bitmap2 = this.b.c(width, height, coil.util.c.e(config));
        Drawable $this$convert_u24lambda_u2d2 = safeDrawable;
        Rect $this$component4$iv = safeDrawable.getBounds();
        k.d($this$component4$iv, "bounds");
        int oldLeft = $this$component4$iv.left;
        Rect $this$component3$iv = $this$component4$iv;
        int oldTop = $this$component3$iv.top;
        int oldRight = $this$component3$iv.right;
        int oldBottom = $this$component4$iv.bottom;
        $this$convert_u24lambda_u2d2.setBounds(0, 0, width, height);
        $this$convert_u24lambda_u2d2.draw(new Canvas(bitmap2));
        $this$convert_u24lambda_u2d2.setBounds(oldLeft, oldTop, oldRight, oldBottom);
        return bitmap2;
    }

    private final boolean b(Bitmap bitmap, Bitmap.Config config) {
        return bitmap.getConfig() == coil.util.c.e(config);
    }

    private final boolean c(boolean allowInexactSize, Size size, Bitmap bitmap, e scale) {
        if (!allowInexactSize && !(size instanceof OriginalSize)) {
            d dVar = d.a;
            return k.a(size, d.b(bitmap.getWidth(), bitmap.getHeight(), size, scale));
        }
    }

    /* compiled from: DrawableDecoderService.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
