package com.google.android.material.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.TransitionValues;
import androidx.transition.Visibility;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.transition.VisibilityAnimatorProvider;
import java.util.ArrayList;
import java.util.List;

public abstract class MaterialVisibility<P extends VisibilityAnimatorProvider> extends Visibility {
    private final List<VisibilityAnimatorProvider> additionalAnimatorProviders = new ArrayList();
    private final P primaryAnimatorProvider;
    @Nullable
    private VisibilityAnimatorProvider secondaryAnimatorProvider;

    protected MaterialVisibility(P primaryAnimatorProvider2, @Nullable VisibilityAnimatorProvider secondaryAnimatorProvider2) {
        this.primaryAnimatorProvider = primaryAnimatorProvider2;
        this.secondaryAnimatorProvider = secondaryAnimatorProvider2;
    }

    @NonNull
    public P getPrimaryAnimatorProvider() {
        return this.primaryAnimatorProvider;
    }

    @Nullable
    public VisibilityAnimatorProvider getSecondaryAnimatorProvider() {
        return this.secondaryAnimatorProvider;
    }

    public void setSecondaryAnimatorProvider(@Nullable VisibilityAnimatorProvider secondaryAnimatorProvider2) {
        this.secondaryAnimatorProvider = secondaryAnimatorProvider2;
    }

    public void addAdditionalAnimatorProvider(@NonNull VisibilityAnimatorProvider additionalAnimatorProvider) {
        this.additionalAnimatorProviders.add(additionalAnimatorProvider);
    }

    public boolean removeAdditionalAnimatorProvider(@NonNull VisibilityAnimatorProvider additionalAnimatorProvider) {
        return this.additionalAnimatorProviders.remove(additionalAnimatorProvider);
    }

    public void clearAdditionalAnimatorProvider() {
        this.additionalAnimatorProviders.clear();
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return createAnimator(sceneRoot, view, true);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return createAnimator(sceneRoot, view, false);
    }

    private Animator createAnimator(@NonNull ViewGroup sceneRoot, @NonNull View view, boolean appearing) {
        AnimatorSet set = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        addAnimatorIfNeeded(animators, this.primaryAnimatorProvider, sceneRoot, view, appearing);
        addAnimatorIfNeeded(animators, this.secondaryAnimatorProvider, sceneRoot, view, appearing);
        for (VisibilityAnimatorProvider additionalAnimatorProvider : this.additionalAnimatorProviders) {
            addAnimatorIfNeeded(animators, additionalAnimatorProvider, sceneRoot, view, appearing);
        }
        maybeApplyThemeValues(sceneRoot.getContext(), appearing);
        AnimatorSetCompat.playTogether(set, animators);
        return set;
    }

    private static void addAnimatorIfNeeded(List<Animator> animators, @Nullable VisibilityAnimatorProvider animatorProvider, ViewGroup sceneRoot, View view, boolean appearing) {
        Animator animator;
        if (animatorProvider != null) {
            if (appearing) {
                animator = animatorProvider.createAppear(sceneRoot, view);
            } else {
                animator = animatorProvider.createDisappear(sceneRoot, view);
            }
            if (animator != null) {
                animators.add(animator);
            }
        }
    }

    private void maybeApplyThemeValues(@NonNull Context context, boolean appearing) {
        TransitionUtils.maybeApplyThemeDuration(this, context, getDurationThemeAttrResId(appearing));
        TransitionUtils.maybeApplyThemeInterpolator(this, context, getEasingThemeAttrResId(appearing), getDefaultEasingInterpolator(appearing));
    }

    /* access modifiers changed from: package-private */
    @AttrRes
    public int getDurationThemeAttrResId(boolean appearing) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    @AttrRes
    public int getEasingThemeAttrResId(boolean appearing) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public TimeInterpolator getDefaultEasingInterpolator(boolean appearing) {
        return AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
    }
}
