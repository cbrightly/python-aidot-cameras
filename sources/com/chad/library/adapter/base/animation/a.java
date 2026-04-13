package com.chad.library.adapter.base.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AlphaInAnimation.kt */
public final class a implements b {
    public static final C0056a a = new C0056a((DefaultConstructorMarker) null);
    private final float b;

    public a(float mFrom) {
        this.b = mFrom;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 0.0f : f);
    }

    @NotNull
    public Animator[] animators(@NotNull View view) {
        k.e(view, "view");
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", new float[]{this.b, 1.0f});
        k.d(animator, "animator");
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return new Animator[]{animator};
    }

    /* renamed from: com.chad.library.adapter.base.animation.a$a  reason: collision with other inner class name */
    /* compiled from: AlphaInAnimation.kt */
    public static final class C0056a {
        private C0056a() {
        }

        public /* synthetic */ C0056a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
