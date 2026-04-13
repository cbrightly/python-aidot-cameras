package androidx.core.animation;

import android.animation.Animator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Animator.kt */
public final class AnimatorKt$doOnResume$$inlined$addPauseListener$default$1 implements Animator.AnimatorPauseListener {
    final /* synthetic */ l $onResume;

    public AnimatorKt$doOnResume$$inlined$addPauseListener$default$1(l $onResume2) {
        this.$onResume = $onResume2;
    }

    public void onAnimationPause(@NotNull Animator animator) {
        k.e(animator, "animator");
        Animator animator2 = animator;
    }

    public void onAnimationResume(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onResume.invoke(animator);
    }
}
