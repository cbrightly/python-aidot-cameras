package androidx.core.transition;

import android.transition.Transition;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Transition.kt */
public final class TransitionKt$doOnStart$$inlined$addListener$default$1 implements Transition.TransitionListener {
    final /* synthetic */ l $onStart;

    public TransitionKt$doOnStart$$inlined$addListener$default$1(l $onStart2) {
        this.$onStart = $onStart2;
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
        Transition transition2 = transition;
    }

    public void onTransitionCancel(@NotNull Transition transition) {
        k.e(transition, "transition");
        Transition transition2 = transition;
    }

    public void onTransitionStart(@NotNull Transition transition) {
        k.e(transition, "transition");
        this.$onStart.invoke(transition);
    }
}
