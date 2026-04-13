package com.lzf.easyfloat.core;

import android.animation.Animator;
import android.view.View;
import org.jetbrains.annotations.Nullable;

/* compiled from: TouchUtils.kt */
public final class TouchUtils$sideAnim$2 implements Animator.AnimatorListener {
    final /* synthetic */ View $view;
    final /* synthetic */ TouchUtils this$0;

    TouchUtils$sideAnim$2(TouchUtils $receiver, View $view2) {
        this.this$0 = $receiver;
        this.$view = $view2;
    }

    public void onAnimationRepeat(@Nullable Animator animation) {
    }

    public void onAnimationEnd(@Nullable Animator animation) {
        this.this$0.dragEnd(this.$view);
    }

    public void onAnimationCancel(@Nullable Animator animation) {
        this.this$0.dragEnd(this.$view);
    }

    public void onAnimationStart(@Nullable Animator animation) {
        this.this$0.getConfig().setAnim(true);
    }
}
