package com.google.android.material.progressindicator;

import android.animation.Animator;
import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

public abstract class IndeterminateAnimatorDelegate<T extends Animator> {
    protected IndeterminateDrawable drawable;
    protected final int[] segmentColors;
    protected final float[] segmentPositions;

    /* access modifiers changed from: package-private */
    public abstract void cancelAnimatorImmediately();

    public abstract void invalidateSpecValues();

    public abstract void registerAnimatorsCompleteCallback(@NonNull Animatable2Compat.AnimationCallback animationCallback);

    /* access modifiers changed from: package-private */
    public abstract void requestCancelAnimatorAfterCurrentCycle();

    /* access modifiers changed from: package-private */
    public abstract void startAnimator();

    public abstract void unregisterAnimatorsCompleteCallback();

    protected IndeterminateAnimatorDelegate(int segmentCount) {
        this.segmentPositions = new float[(segmentCount * 2)];
        this.segmentColors = new int[segmentCount];
    }

    /* access modifiers changed from: protected */
    public void registerDrawable(@NonNull IndeterminateDrawable drawable2) {
        this.drawable = drawable2;
    }

    /* access modifiers changed from: protected */
    public float getFractionInRange(int playtime, int start, int duration) {
        return ((float) (playtime - start)) / ((float) duration);
    }
}
