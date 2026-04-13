package com.lzf.easyfloat.anim;

import android.animation.Animator;
import android.view.View;
import android.view.WindowManager;
import com.lzf.easyfloat.data.FloatConfig;
import com.lzf.easyfloat.interfaces.OnFloatAnimator;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnimatorManager.kt */
public final class AnimatorManager {
    @NotNull
    private final FloatConfig config;
    @NotNull
    private final WindowManager.LayoutParams params;
    @NotNull
    private final View view;
    @NotNull
    private final WindowManager windowManager;

    public AnimatorManager(@NotNull View view2, @NotNull WindowManager.LayoutParams params2, @NotNull WindowManager windowManager2, @NotNull FloatConfig config2) {
        k.e(view2, "view");
        k.e(params2, "params");
        k.e(windowManager2, "windowManager");
        k.e(config2, "config");
        this.view = view2;
        this.params = params2;
        this.windowManager = windowManager2;
        this.config = config2;
    }

    @Nullable
    public final Animator enterAnim() {
        OnFloatAnimator floatAnimator = this.config.getFloatAnimator();
        if (floatAnimator == null) {
            return null;
        }
        return floatAnimator.enterAnim(this.view, this.params, this.windowManager, this.config.getSidePattern());
    }

    @Nullable
    public final Animator exitAnim() {
        OnFloatAnimator floatAnimator = this.config.getFloatAnimator();
        if (floatAnimator == null) {
            return null;
        }
        return floatAnimator.exitAnim(this.view, this.params, this.windowManager, this.config.getSidePattern());
    }
}
