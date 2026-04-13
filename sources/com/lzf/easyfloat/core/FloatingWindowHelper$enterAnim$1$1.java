package com.lzf.easyfloat.core;

import android.animation.Animator;
import android.view.View;
import org.jetbrains.annotations.Nullable;

/* compiled from: FloatingWindowHelper.kt */
public final class FloatingWindowHelper$enterAnim$1$1 implements Animator.AnimatorListener {
    final /* synthetic */ View $floatingView;
    final /* synthetic */ FloatingWindowHelper this$0;

    FloatingWindowHelper$enterAnim$1$1(FloatingWindowHelper $receiver, View $floatingView2) {
        this.this$0 = $receiver;
        this.$floatingView = $floatingView2;
    }

    public void onAnimationRepeat(@Nullable Animator animation) {
    }

    public void onAnimationEnd(@Nullable Animator animation) {
        this.this$0.getConfig().setAnim(false);
        if (!this.this$0.getConfig().getImmersionStatusBar()) {
            this.this$0.getParams().flags = 40;
        }
        this.this$0.initEditText();
    }

    public void onAnimationCancel(@Nullable Animator animation) {
    }

    public void onAnimationStart(@Nullable Animator animation) {
        this.$floatingView.setVisibility(0);
        this.this$0.getConfig().setAnim(true);
    }
}
