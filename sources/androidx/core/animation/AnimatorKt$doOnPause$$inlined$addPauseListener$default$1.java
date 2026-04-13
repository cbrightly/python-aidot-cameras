package androidx.core.animation;

import android.animation.Animator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Animator.kt */
public final class AnimatorKt$doOnPause$$inlined$addPauseListener$default$1 implements Animator.AnimatorPauseListener {
    final /* synthetic */ l $onPause;

    public AnimatorKt$doOnPause$$inlined$addPauseListener$default$1(l $onPause2) {
        this.$onPause = $onPause2;
    }

    public void onAnimationPause(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onPause.invoke(animator);
    }

    public void onAnimationResume(@NotNull Animator animator) {
        k.e(animator, "animator");
        Animator animator2 = animator;
    }
}
