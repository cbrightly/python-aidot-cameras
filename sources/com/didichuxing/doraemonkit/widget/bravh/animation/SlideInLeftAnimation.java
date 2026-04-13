package com.didichuxing.doraemonkit.widget.bravh.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SlideInLeftAnimation.kt */
public final class SlideInLeftAnimation implements BaseAnimation {
    @NotNull
    public Animator[] animators(@NotNull View view) {
        k.f(view, "view");
        View rootView = view.getRootView();
        k.b(rootView, "view.rootView");
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", new float[]{-((float) rootView.getWidth()), 0.0f});
        k.b(animator, "animator");
        animator.setDuration(400);
        animator.setInterpolator(new DecelerateInterpolator(1.8f));
        return new Animator[]{animator};
    }
}
