package androidx.core.graphics;

import android.graphics.Paint;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Paint.kt */
public final class PaintKt {
    public static final boolean setBlendMode(@NotNull Paint $this$setBlendMode, @Nullable BlendModeCompat blendModeCompat) {
        k.e($this$setBlendMode, "<this>");
        return PaintCompat.setBlendMode($this$setBlendMode, blendModeCompat);
    }
}
