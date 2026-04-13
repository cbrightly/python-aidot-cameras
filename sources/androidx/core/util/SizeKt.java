package androidx.core.util;

import android.util.Size;
import android.util.SizeF;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Size.kt */
public final class SizeKt {
    @RequiresApi(21)
    public static final int component1(@NotNull Size $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.getWidth();
    }

    @RequiresApi(21)
    public static final int component2(@NotNull Size $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.getHeight();
    }

    @RequiresApi(21)
    public static final float component1(@NotNull SizeF $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.getWidth();
    }

    @RequiresApi(21)
    public static final float component2(@NotNull SizeF $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.getHeight();
    }
}
