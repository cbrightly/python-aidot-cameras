package com.petterp.floatingx.assist;

import android.animation.Animator;
import android.widget.FrameLayout;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxAnimation.kt */
public abstract class a {
    @Nullable
    private Animator a;
    @Nullable
    private Animator b;

    @NotNull
    public abstract Animator c(@Nullable FrameLayout frameLayout);

    @NotNull
    public abstract Animator g(@Nullable FrameLayout frameLayout);

    private final long f(Animator $this$animatorDuration) {
        return $this$animatorDuration.getDuration() + $this$animatorDuration.getStartDelay();
    }

    public final void a() {
        Animator animator = this.a;
        if (animator != null) {
            animator.cancel();
        }
        Animator animator2 = this.b;
        if (animator2 != null) {
            animator2.cancel();
        }
        this.a = null;
        this.b = null;
    }

    public final /* synthetic */ boolean d() {
        Animator animator = this.a;
        if (animator == null) {
            return false;
        }
        return animator.isRunning();
    }

    public final /* synthetic */ boolean b() {
        Animator animator = this.b;
        if (animator == null) {
            return false;
        }
        return animator.isRunning();
    }

    public final /* synthetic */ long e(FrameLayout view) {
        Animator animator = this.a;
        if (animator != null) {
            animator.cancel();
        }
        Animator c = c(view);
        this.a = c;
        boolean z = false;
        if (c != null && c.isRunning()) {
            z = true;
        }
        if (z) {
            Animator animator2 = this.a;
            if (animator2 == null) {
                return 0;
            }
            return animator2.getDuration();
        }
        Animator animator3 = this.a;
        if (animator3 != null) {
            animator3.start();
        }
        Animator animator4 = this.a;
        if (animator4 == null) {
            return 0;
        }
        return f(animator4);
    }

    public final /* synthetic */ long h(FrameLayout view) {
        Animator animator = this.b;
        if (animator != null) {
            animator.cancel();
        }
        Animator g = g(view);
        this.b = g;
        boolean z = false;
        if (g != null && g.isRunning()) {
            z = true;
        }
        if (z) {
            Animator animator2 = this.b;
            if (animator2 == null) {
                return 0;
            }
            return animator2.getDuration();
        }
        Animator animator3 = this.b;
        if (animator3 != null) {
            animator3.start();
        }
        Animator animator4 = this.b;
        if (animator4 == null) {
            return 0;
        }
        return f(animator4);
    }
}
