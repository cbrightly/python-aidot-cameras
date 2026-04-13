package androidx.core.transition;

import android.transition.Transition;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Transition.kt */
public final class TransitionKt$doOnPause$$inlined$addListener$default$1 implements Transition.TransitionListener {
    final /* synthetic */ l $onPause;

    public TransitionKt$doOnPause$$inlined$addListener$default$1(l $onPause2) {
        this.$onPause = $onPause2;
    }

    public void onTransitionEnd(@NotNull Transition transition) {
        k.e(transition, "transition");
        Transition transition2 = transition;
    }

    public void onTransitionResume(@NotNull Transition transition) {
        k.e(transition, "transition");
        Transition transition2 = transition;
    }

    public void onTransitionPause(@NotNull Transition transition) {
        k.e(transition, "transition");
        this.$onPause.invoke(transition);
    }

    public void onTransitionCancel(@NotNull Transition transition) {
        k.e(transition, "transition");
        Transition transition2 = transition;
    }

    public void onTransitionStart(@NotNull Transition transition) {
        k.e(transition, "transition");
        Transition transition2 = transition;
    }
}
