package com.petterp.floatingx.util;

import android.app.Activity;
import android.widget.FrameLayout;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxUiExt.kt */
public final class d {
    @Nullable
    public static final FrameLayout a(@NotNull Activity $this$decorView) {
        k.e($this$decorView, "<this>");
        try {
            return (FrameLayout) $this$decorView.getWindow().getDecorView();
        } catch (Exception e) {
            return null;
        }
    }
}
