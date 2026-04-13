package com.petterp.floatingx.util;

import android.view.MotionEvent;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FxExt.kt */
public final class b {
    public static final /* synthetic */ float a(float $this$coerceInFx, float min, float max) {
        if ($this$coerceInFx < min) {
            return min;
        }
        if ($this$coerceInFx > max) {
            return max;
        }
        return $this$coerceInFx;
    }

    public static final boolean c(float $this$withIn, @NotNull Number min, @NotNull Number max) {
        k.e(min, "min");
        k.e(max, "max");
        return $this$withIn <= max.floatValue() && min.floatValue() <= $this$withIn;
    }

    public static final int b(@NotNull MotionEvent $this$pointerId) {
        k.e($this$pointerId, "<this>");
        try {
            return $this$pointerId.getPointerId($this$pointerId.getActionIndex());
        } catch (Exception e) {
            return -1;
        }
    }
}
