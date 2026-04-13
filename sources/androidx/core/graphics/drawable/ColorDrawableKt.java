package androidx.core.graphics.drawable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ColorDrawable.kt */
public final class ColorDrawableKt {
    @NotNull
    public static final ColorDrawable toDrawable(@ColorInt int $this$toDrawable) {
        return new ColorDrawable($this$toDrawable);
    }

    @RequiresApi(26)
    @NotNull
    public static final ColorDrawable toDrawable(@NotNull Color $this$toDrawable) {
        k.e($this$toDrawable, "<this>");
        return new ColorDrawable($this$toDrawable.toArgb());
    }
}
