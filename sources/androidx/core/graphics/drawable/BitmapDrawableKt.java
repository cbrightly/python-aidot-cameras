package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BitmapDrawable.kt */
public final class BitmapDrawableKt {
    @NotNull
    public static final BitmapDrawable toDrawable(@NotNull Bitmap $this$toDrawable, @NotNull Resources resources) {
        k.e($this$toDrawable, "<this>");
        k.e(resources, "resources");
        return new BitmapDrawable(resources, $this$toDrawable);
    }
}
