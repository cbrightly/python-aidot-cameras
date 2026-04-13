package com.didichuxing.doraemonkit.widget.bravh.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ScaleInAnimation.kt */
public final class ScaleInAnimation implements BaseAnimation {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final float DEFAULT_SCALE_FROM = 0.5f;
    private final float mFrom;

    public ScaleInAnimation() {
        this(0.0f, 1, (DefaultConstructorMarker) null);
    }

    public ScaleInAnimation(float mFrom2) {
        this.mFrom = mFrom2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ScaleInAnimation(float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.5f : f);
    }

    @NotNull
    public Animator[] animators(@NotNull View view) {
        k.f(view, "view");
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", new float[]{this.mFrom, 1.0f});
        k.b(scaleX, "scaleX");
        scaleX.setDuration(300);
        scaleX.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", new float[]{this.mFrom, 1.0f});
        k.b(scaleY, "scaleY");
        scaleY.setDuration(300);
        scaleY.setInterpolator(new DecelerateInterpolator());
        return new Animator[]{scaleX, scaleY};
    }

    /* compiled from: ScaleInAnimation.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
