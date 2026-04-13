package com.lzf.easyfloat.interfaces;

import android.animation.Animator;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.enums.SidePattern;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OnFloatAnimator.kt */
public interface OnFloatAnimator {
    @Nullable
    Animator enterAnim(@NotNull View view, @NotNull WindowManager.LayoutParams layoutParams, @NotNull WindowManager windowManager, @NotNull SidePattern sidePattern);

    @Nullable
    Animator exitAnim(@NotNull View view, @NotNull WindowManager.LayoutParams layoutParams, @NotNull WindowManager windowManager, @NotNull SidePattern sidePattern);

    @l(d1 = {}, d2 = {}, k = 3, mv = {1, 5, 1})
    /* compiled from: OnFloatAnimator.kt */
    public static final class DefaultImpls {
        @Nullable
        public static Animator enterAnim(@NotNull OnFloatAnimator onFloatAnimator, @NotNull View view, @NotNull WindowManager.LayoutParams params, @NotNull WindowManager windowManager, @NotNull SidePattern sidePattern) {
            k.e(onFloatAnimator, "this");
            k.e(view, "view");
            k.e(params, "params");
            k.e(windowManager, "windowManager");
            k.e(sidePattern, "sidePattern");
            return null;
        }

        @Nullable
        public static Animator exitAnim(@NotNull OnFloatAnimator onFloatAnimator, @NotNull View view, @NotNull WindowManager.LayoutParams params, @NotNull WindowManager windowManager, @NotNull SidePattern sidePattern) {
            k.e(onFloatAnimator, "this");
            k.e(view, "view");
            k.e(params, "params");
            k.e(windowManager, "windowManager");
            k.e(sidePattern, "sidePattern");
            return null;
        }
    }
}
