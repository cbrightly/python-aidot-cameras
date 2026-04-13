package com.google.android.material.transition.platform;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(21)
public final class FadeProvider implements VisibilityAnimatorProvider {
    private float incomingEndThreshold = 1.0f;

    public float getIncomingEndThreshold() {
        return this.incomingEndThreshold;
    }

    public void setIncomingEndThreshold(float incomingEndThreshold2) {
        this.incomingEndThreshold = incomingEndThreshold2;
    }

    @Nullable
    public Animator createAppear(@NonNull ViewGroup sceneRoot, @NonNull View view) {
        float originalAlpha = view.getAlpha() == 0.0f ? 1.0f : view.getAlpha();
        return createFadeAnimator(view, 0.0f, originalAlpha, 0.0f, this.incomingEndThreshold, originalAlpha);
    }

    @Nullable
    public Animator createDisappear(@NonNull ViewGroup sceneRoot, @NonNull View view) {
        float originalAlpha = view.getAlpha() == 0.0f ? 1.0f : view.getAlpha();
        return createFadeAnimator(view, originalAlpha, 0.0f, 0.0f, 1.0f, originalAlpha);
    }

    private static Animator createFadeAnimator(final View view, float startValue, float endValue, @FloatRange(from = 0.0d, to = 1.0d) float startFraction, @FloatRange(from = 0.0d, to = 1.0d) float endFraction, final float originalAlpha) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        final View view2 = view;
        final float f = startValue;
        final float f2 = endValue;
        final float f3 = startFraction;
        final float f4 = endFraction;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                view2.setAlpha(TransitionUtils.lerp(f, f2, f3, f4, ((Float) animation.getAnimatedValue()).floatValue()));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.setAlpha(originalAlpha);
            }
        });
        return animator;
    }
}
