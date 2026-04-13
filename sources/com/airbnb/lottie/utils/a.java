package com.airbnb.lottie.utils;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Build;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: BaseLottieAnimator */
public abstract class a extends ValueAnimator {
    private final Set<ValueAnimator.AnimatorUpdateListener> c = new CopyOnWriteArraySet();
    private final Set<Animator.AnimatorListener> d = new CopyOnWriteArraySet();
    private final Set<Animator.AnimatorPauseListener> f = new CopyOnWriteArraySet();

    public long getStartDelay() {
        throw new UnsupportedOperationException("LottieAnimator does not support getStartDelay.");
    }

    public void setStartDelay(long startDelay) {
        throw new UnsupportedOperationException("LottieAnimator does not support setStartDelay.");
    }

    public ValueAnimator setDuration(long duration) {
        throw new UnsupportedOperationException("LottieAnimator does not support setDuration.");
    }

    public void setInterpolator(TimeInterpolator value) {
        throw new UnsupportedOperationException("LottieAnimator does not support setInterpolator.");
    }

    public void addUpdateListener(ValueAnimator.AnimatorUpdateListener listener) {
        this.c.add(listener);
    }

    public void removeUpdateListener(ValueAnimator.AnimatorUpdateListener listener) {
        this.c.remove(listener);
    }

    public void removeAllUpdateListeners() {
        this.c.clear();
    }

    public void addListener(Animator.AnimatorListener listener) {
        this.d.add(listener);
    }

    public void removeListener(Animator.AnimatorListener listener) {
        this.d.remove(listener);
    }

    public void removeAllListeners() {
        this.d.clear();
    }

    /* access modifiers changed from: package-private */
    public void f(boolean isReverse) {
        for (Animator.AnimatorListener listener : this.d) {
            if (Build.VERSION.SDK_INT >= 26) {
                listener.onAnimationStart(this, isReverse);
            } else {
                listener.onAnimationStart(this);
            }
        }
    }

    public void addPauseListener(Animator.AnimatorPauseListener listener) {
        this.f.add(listener);
    }

    public void removePauseListener(Animator.AnimatorPauseListener listener) {
        this.f.remove(listener);
    }

    /* access modifiers changed from: package-private */
    public void d() {
        for (Animator.AnimatorListener listener : this.d) {
            listener.onAnimationRepeat(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void b(boolean isReverse) {
        for (Animator.AnimatorListener listener : this.d) {
            if (Build.VERSION.SDK_INT >= 26) {
                listener.onAnimationEnd(this, isReverse);
            } else {
                listener.onAnimationEnd(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        for (Animator.AnimatorListener listener : this.d) {
            listener.onAnimationCancel(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void h() {
        for (ValueAnimator.AnimatorUpdateListener listener : this.c) {
            listener.onAnimationUpdate(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (Build.VERSION.SDK_INT >= 19) {
            for (Animator.AnimatorPauseListener pauseListener : this.f) {
                pauseListener.onAnimationPause(this);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void e() {
        if (Build.VERSION.SDK_INT >= 19) {
            for (Animator.AnimatorPauseListener pauseListener : this.f) {
                pauseListener.onAnimationResume(this);
            }
        }
    }
}
