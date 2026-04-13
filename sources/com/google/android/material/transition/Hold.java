package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.TransitionValues;
import androidx.transition.Visibility;

public final class Hold extends Visibility {
    @NonNull
    public Animator onAppear(@NonNull ViewGroup sceneRoot, @NonNull View view, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        return ValueAnimator.ofFloat(new float[]{0.0f});
    }

    @NonNull
    public Animator onDisappear(@NonNull ViewGroup sceneRoot, @NonNull View view, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        return ValueAnimator.ofFloat(new float[]{0.0f});
    }
}
