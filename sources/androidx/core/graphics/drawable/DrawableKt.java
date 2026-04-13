package androidx.core.graphics.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.Px;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Drawable.kt */
public final class DrawableKt {
    public static /* synthetic */ Bitmap toBitmap$default(Drawable drawable, int i, int i2, Bitmap.Config config, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = drawable.getIntrinsicWidth();
        }
        if ((i3 & 2) != 0) {
            i2 = drawable.getIntrinsicHeight();
        }
        if ((i3 & 4) != 0) {
            config = null;
        }
        return toBitmap(drawable, i, i2, config);
    }

    @NotNull
    public static final Bitmap toBitmap(@NotNull Drawable $this$toBitmap, @Px int width, @Px int height, @Nullable Bitmap.Config config) {
        k.e($this$toBitmap, "<this>");
        if (!($this$toBitmap instanceof BitmapDrawable) || !(config == null || ((BitmapDrawable) $this$toBitmap).getBitmap().getConfig() == config)) {
            Rect $this$component4$iv = $this$toBitmap.getBounds();
            k.d($this$component4$iv, "bounds");
            int oldLeft = $this$component4$iv.left;
            int oldTop = $this$component4$iv.top;
            int oldRight = $this$component4$iv.right;
            int oldBottom = $this$component4$iv.bottom;
            Bitmap bitmap = Bitmap.createBitmap(width, height, config == null ? Bitmap.Config.ARGB_8888 : config);
            $this$toBitmap.setBounds(0, 0, width, height);
            $this$toBitmap.draw(new Canvas(bitmap));
            $this$toBitmap.setBounds(oldLeft, oldTop, oldRight, oldBottom);
            k.d(bitmap, "bitmap");
            return bitmap;
        } else if (width == ((BitmapDrawable) $this$toBitmap).getIntrinsicWidth() && height == ((BitmapDrawable) $this$toBitmap).getIntrinsicHeight()) {
            Bitmap bitmap2 = ((BitmapDrawable) $this$toBitmap).getBitmap();
            k.d(bitmap2, "bitmap");
            return bitmap2;
        } else {
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(((BitmapDrawable) $this$toBitmap).getBitmap(), width, height, true);
            k.d(createScaledBitmap, "createScaledBitmap(bitmap, width, height, true)");
            return createScaledBitmap;
        }
    }

    public static /* synthetic */ void updateBounds$default(Drawable drawable, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = drawable.getBounds().left;
        }
        if ((i5 & 2) != 0) {
            i2 = drawable.getBounds().top;
        }
        if ((i5 & 4) != 0) {
            i3 = drawable.getBounds().right;
        }
        if ((i5 & 8) != 0) {
            i4 = drawable.getBounds().bottom;
        }
        updateBounds(drawable, i, i2, i3, i4);
    }

    public static final void updateBounds(@NotNull Drawable $this$updateBounds, @Px int left, @Px int top, @Px int right, @Px int bottom) {
        k.e($this$updateBounds, "<this>");
        $this$updateBounds.setBounds(left, top, right, bottom);
    }
}
