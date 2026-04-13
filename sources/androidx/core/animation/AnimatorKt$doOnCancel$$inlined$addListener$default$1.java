package androidx.core.animation;

import android.animation.Animator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Animator.kt */
public final class AnimatorKt$doOnCancel$$inlined$addListener$default$1 implements Animator.AnimatorListener {
    final /* synthetic */ l $onCancel;

    public AnimatorKt$doOnCancel$$inlined$addListener$default$1(l $onCancel2) {
        this.$onCancel = $onCancel2;
    }

    public void onAnimationRepeat(@NotNull Animator animator) {
        k.e(animator, "animator");
        Animator animator2 = animator;
    }

    public void onAnimationEnd(@NotNull Animator animator) {
        k.e(animator, "animator");
        Animator animator2 = animator;
    }

    public void onAnimationCancel(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onCancel.invoke(animator);
    }

    public void onAnimationStart(@NotNull Animator animator) {
        k.e(animator, "animator");
        Animator animator2 = animator;
    }
}
