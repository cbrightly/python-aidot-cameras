package androidx.core.transition;

import android.transition.Transition;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Transition.kt */
public final class TransitionKt$addListener$listener$1 implements Transition.TransitionListener {
    final /* synthetic */ l<Transition, x> $onCancel;
    final /* synthetic */ l<Transition, x> $onEnd;
    final /* synthetic */ l<Transition, x> $onPause;
    final /* synthetic */ l<Transition, x> $onResume;
    final /* synthetic */ l<Transition, x> $onStart;

    public TransitionKt$addListener$listener$1(l<? super Transition, x> $onEnd2, l<? super Transition, x> $onResume2, l<? super Transition, x> $onPause2, l<? super Transition, x> $onCancel2, l<? super Transition, x> $onStart2) {
        this.$onEnd = $onEnd2;
        this.$onResume = $onResume2;
        this.$onPause = $onPause2;
        this.$onCancel = $onCancel2;
        this.$onStart = $onStart2;
    }

    public void onTransitionEnd(@NotNull Transition transition) {
        k.e(transition, "transition");
        this.$onEnd.invoke(transition);
    }

    public void onTransitionResume(@NotNull Transition transition) {
        k.e(transition, "transition");
        this.$onResume.invoke(transition);
    }

    public void onTransitionPause(@NotNull Transition transition) {
        k.e(transition, "transition");
        this.$onPause.invoke(transition);
    }

    public void onTransitionCancel(@NotNull Transition transition) {
        k.e(transition, "transition");
        this.$onCancel.invoke(transition);
    }

    public void onTransitionStart(@NotNull Transition transition) {
        k.e(transition, "transition");
        this.$onStart.invoke(transition);
    }
}
