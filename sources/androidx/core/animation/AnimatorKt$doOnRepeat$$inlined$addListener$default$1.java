package androidx.core.animation;

import android.animation.Animator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Animator.kt */
public final class AnimatorKt$doOnRepeat$$inlined$addListener$default$1 implements Animator.AnimatorListener {
    final /* synthetic */ l $onRepeat;

    public AnimatorKt$doOnRepeat$$inlined$addListener$default$1(l $onRepeat2) {
        this.$onRepeat = $onRepeat2;
    }

    public void onAnimationRepeat(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onRepeat.invoke(animator);
    }

    public void onAnimationEnd(@NotNull Animator animator) {
        k.e(animator, "animator");
        Animator animator2 = animator;
    }

    public void onAnimationCancel(@NotNull Animator animator) {
        k.e(animator, "animator");
        Animator animator2 = animator;
    }

    public void onAnimationStart(@NotNull Animator animator) {
        k.e(animator, "animator");
        Animator animator2 = animator;
    }
}
