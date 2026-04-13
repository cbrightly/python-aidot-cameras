package androidx.core.graphics;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PorterDuff.kt */
public final class PorterDuffKt {
    @NotNull
    public static final PorterDuffXfermode toXfermode(@NotNull PorterDuff.Mode $this$toXfermode) {
        k.e($this$toXfermode, "<this>");
        return new PorterDuffXfermode($this$toXfermode);
    }

    @NotNull
    public static final PorterDuffColorFilter toColorFilter(@NotNull PorterDuff.Mode $this$toColorFilter, int color) {
        k.e($this$toColorFilter, "<this>");
        return new PorterDuffColorFilter(color, $this$toColorFilter);
    }
}
