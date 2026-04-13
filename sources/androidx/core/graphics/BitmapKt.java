package androidx.core.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorSpace;
import android.graphics.PointF;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Bitmap.kt */
public final class BitmapKt {
    @NotNull
    public static final Bitmap applyCanvas(@NotNull Bitmap $this$applyCanvas, @NotNull l<? super Canvas, x> block) {
        k.e($this$applyCanvas, "<this>");
        k.e(block, "block");
        block.invoke(new Canvas($this$applyCanvas));
        return $this$applyCanvas;
    }

    public static final int get(@NotNull Bitmap $this$get, int x, int y) {
        k.e($this$get, "<this>");
        return $this$get.getPixel(x, y);
    }

    public static final void set(@NotNull Bitmap $this$set, int x, int y, @ColorInt int color) {
        k.e($this$set, "<this>");
        $this$set.setPixel(x, y, color);
    }

    public static /* synthetic */ Bitmap scale$default(Bitmap $this$scale_u24default, int width, int height, boolean filter, int i, Object obj) {
        if ((i & 4) != 0) {
            filter = true;
        }
        k.e($this$scale_u24default, "<this>");
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap($this$scale_u24default, width, height, filter);
        k.d(createScaledBitmap, "createScaledBitmap(this, width, height, filter)");
        return createScaledBitmap;
    }

    @NotNull
    public static final Bitmap scale(@NotNull Bitmap $this$scale, int width, int height, boolean filter) {
        k.e($this$scale, "<this>");
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap($this$scale, width, height, filter);
        k.d(createScaledBitmap, "createScaledBitmap(this, width, height, filter)");
        return createScaledBitmap;
    }

    public static /* synthetic */ Bitmap createBitmap$default(int width, int height, Bitmap.Config config, int i, Object obj) {
        if ((i & 4) != 0) {
            config = Bitmap.Config.ARGB_8888;
        }
        k.e(config, "config");
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config);
        k.d(createBitmap, "createBitmap(width, height, config)");
        return createBitmap;
    }

    @NotNull
    public static final Bitmap createBitmap(int width, int height, @NotNull Bitmap.Config config) {
        k.e(config, "config");
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config);
        k.d(createBitmap, "createBitmap(width, height, config)");
        return createBitmap;
    }

    public static /* synthetic */ Bitmap createBitmap$default(int width, int height, Bitmap.Config config, boolean hasAlpha, ColorSpace colorSpace, int i, Object obj) {
        if ((i & 4) != 0) {
            config = Bitmap.Config.ARGB_8888;
        }
        if ((i & 8) != 0) {
            hasAlpha = true;
        }
        if ((i & 16) != 0) {
            ColorSpace colorSpace2 = ColorSpace.get(ColorSpace.Named.SRGB);
            k.d(colorSpace2, "get(ColorSpace.Named.SRGB)");
            colorSpace = colorSpace2;
        }
        k.e(config, "config");
        k.e(colorSpace, "colorSpace");
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config, hasAlpha, colorSpace);
        k.d(createBitmap, "createBitmap(width, height, config, hasAlpha, colorSpace)");
        return createBitmap;
    }

    @RequiresApi(26)
    @NotNull
    public static final Bitmap createBitmap(int width, int height, @NotNull Bitmap.Config config, boolean hasAlpha, @NotNull ColorSpace colorSpace) {
        k.e(config, "config");
        k.e(colorSpace, "colorSpace");
        Bitmap createBitmap = Bitmap.createBitmap(width, height, config, hasAlpha, colorSpace);
        k.d(createBitmap, "createBitmap(width, height, config, hasAlpha, colorSpace)");
        return createBitmap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0015, code lost:
        r1 = r4.y;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final boolean contains(@org.jetbrains.annotations.NotNull android.graphics.Bitmap r3, @org.jetbrains.annotations.NotNull android.graphics.Point r4) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.k.e(r3, r0)
            java.lang.String r0 = "p"
            kotlin.jvm.internal.k.e(r4, r0)
            r0 = 0
            int r1 = r4.x
            if (r1 < 0) goto L_0x0021
            int r2 = r3.getWidth()
            if (r1 >= r2) goto L_0x0021
            int r1 = r4.y
            if (r1 < 0) goto L_0x0021
            int r2 = r3.getHeight()
            if (r1 >= r2) goto L_0x0021
            r1 = 1
            goto L_0x0022
        L_0x0021:
            r1 = 0
        L_0x0022:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.BitmapKt.contains(android.graphics.Bitmap, android.graphics.Point):boolean");
    }

    public static final boolean contains(@NotNull Bitmap $this$contains, @NotNull PointF p) {
        k.e($this$contains, "<this>");
        k.e(p, "p");
        float f = p.x;
        if (f >= 0.0f && f < ((float) $this$contains.getWidth())) {
            float f2 = p.y;
            return f2 >= 0.0f && f2 < ((float) $this$contains.getHeight());
        }
    }
}
