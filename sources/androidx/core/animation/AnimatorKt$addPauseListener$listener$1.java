package androidx.core.animation;

import android.animation.Animator;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Animator.kt */
public final class AnimatorKt$addPauseListener$listener$1 implements Animator.AnimatorPauseListener {
    final /* synthetic */ l<Animator, x> $onPause;
    final /* synthetic */ l<Animator, x> $onResume;

    public AnimatorKt$addPauseListener$listener$1(l<? super Animator, x> $onPause2, l<? super Animator, x> $onResume2) {
        this.$onPause = $onPause2;
        this.$onResume = $onResume2;
    }

    public void onAnimationPause(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onPause.invoke(animator);
    }

    public void onAnimationResume(@NotNull Animator animator) {
        k.e(animator, "animator");
        this.$onResume.invoke(animator);
    }
}
