package com.didichuxing.doraemonkit.widget.bravh.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SlideInBottomAnimation.kt */
public final class SlideInBottomAnimation implements BaseAnimation {
    @NotNull
    public Animator[] animators(@NotNull View view) {
        k.f(view, "view");
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) view.getMeasuredHeight(), 0.0f});
        k.b(animator, "animator");
        animator.setDuration(400);
        animator.setInterpolator(new DecelerateInterpolator(1.3f));
        return new Animator[]{animator};
    }
}
