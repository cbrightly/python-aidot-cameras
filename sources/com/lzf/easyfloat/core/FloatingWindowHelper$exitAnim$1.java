package com.lzf.easyfloat.core;

import android.animation.Animator;
import org.jetbrains.annotations.Nullable;

/* compiled from: FloatingWindowHelper.kt */
public final class FloatingWindowHelper$exitAnim$1 implements Animator.AnimatorListener {
    final /* synthetic */ FloatingWindowHelper this$0;

    FloatingWindowHelper$exitAnim$1(FloatingWindowHelper $receiver) {
        this.this$0 = $receiver;
    }

    public void onAnimationRepeat(@Nullable Animator animation) {
    }

    public void onAnimationEnd(@Nullable Animator animation) {
        FloatingWindowHelper.remove$default(this.this$0, false, 1, (Object) null);
    }

    public void onAnimationCancel(@Nullable Animator animation) {
    }

    public void onAnimationStart(@Nullable Animator animation) {
    }
}
