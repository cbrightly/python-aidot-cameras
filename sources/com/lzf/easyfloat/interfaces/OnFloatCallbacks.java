package com.lzf.easyfloat.interfaces;

import android.view.MotionEvent;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OnFloatCallbacks.kt */
public interface OnFloatCallbacks {
    void createdResult(boolean z, @Nullable String str, @Nullable View view);

    void dismiss();

    void drag(@NotNull View view, @NotNull MotionEvent motionEvent);

    void dragEnd(@NotNull View view);

    void hide(@NotNull View view);

    void show(@NotNull View view);

    void touchEvent(@NotNull View view, @NotNull MotionEvent motionEvent);
}
