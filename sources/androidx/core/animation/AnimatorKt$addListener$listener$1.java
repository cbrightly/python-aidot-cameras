package androidx.core.animation;

import android.animation.Animator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Animator.kt */
public final class AnimatorKt$addListener$listener$1 implements Animator.AnimatorListener {
    final /* synthetic */ l<Animator, x> $onCancel;
    final /* synthetic */ l<Animator, x> $onEnd;
    final /* synthetic */ l<Animator, x> $onRepeat;
    final /* synthetic */ l<Animator, x> $onStart;

    public AnimatorKt$addListener$listener$1(l<? super Animator, x> $onRepeat2, l<? super Animator, x> $onEnd2, l<? super Animator, x> $onCancel2, l<? super Animator, x> $onStart2) {
        this.$onRepeat = $onRepeat2;
        this.$onEnd = $onEnd2;
        this.$onCancel = $onCancel2;
        this.$onStart = $onStart2;
    }

    public void onAnimationRepeat(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onRepeat.invoke(animator);
    }

    public void onAnimationEnd(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onEnd.invoke(animator);
    }

    public void onAnimationCancel(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onCancel.invoke(animator);
    }

    public void onAnimationStart(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onStart.invoke(animator);
    }
}
