package com.didichuxing.doraemonkit.widget.bravh.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AlphaInAnimation.kt */
public final class AlphaInAnimation implements BaseAnimation {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final float DEFAULT_ALPHA_FROM = 0.0f;
    private final float mFrom;

    public AlphaInAnimation() {
        this(0.0f, 1, (DefaultConstructorMarker) null);
    }

    public AlphaInAnimation(float mFrom2) {
        this.mFrom = mFrom2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ AlphaInAnimation(float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f);
    }

    @NotNull
    public Animator[] animators(@NotNull View view) {
        k.f(view, "view");
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", new float[]{this.mFrom, 1.0f});
        k.b(animator, "animator");
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return new Animator[]{animator};
    }

    /* compiled from: AlphaInAnimation.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
