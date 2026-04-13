package androidx.core.animation;

import android.animation.Animator;
import androidx.annotation.RequiresApi;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Animator.kt */
public final class AnimatorKt {
    @NotNull
    public static final Animator.AnimatorListener doOnEnd(@NotNull Animator $this$doOnEnd, @NotNull l<? super Animator, x> action) {
        k.e($this$doOnEnd, "<this>");
        k.e(action, "action");
        AnimatorKt$doOnEnd$$inlined$addListener$default$1 listener$iv = new AnimatorKt$doOnEnd$$inlined$addListener$default$1(action);
        $this$doOnEnd.addListener(listener$iv);
        return listener$iv;
    }

    @NotNull
    public static final Animator.AnimatorListener doOnStart(@NotNull Animator $this$doOnStart, @NotNull l<? super Animator, x> action) {
        k.e($this$doOnStart, "<this>");
        k.e(action, "action");
        AnimatorKt$doOnStart$$inlined$addListener$default$1 listener$iv = new AnimatorKt$doOnStart$$inlined$addListener$default$1(action);
        $this$doOnStart.addListener(listener$iv);
        return listener$iv;
    }

    @NotNull
    public static final Animator.AnimatorListener doOnCancel(@NotNull Animator $this$doOnCancel, @NotNull l<? super Animator, x> action) {
        k.e($this$doOnCancel, "<this>");
        k.e(action, "action");
        AnimatorKt$doOnCancel$$inlined$addListener$default$1 listener$iv = new AnimatorKt$doOnCancel$$inlined$addListener$default$1(action);
        $this$doOnCancel.addListener(listener$iv);
        return listener$iv;
    }

    @NotNull
    public static final Animator.AnimatorListener doOnRepeat(@NotNull Animator $this$doOnRepeat, @NotNull l<? super Animator, x> action) {
        k.e($this$doOnRepeat, "<this>");
        k.e(action, "action");
        AnimatorKt$doOnRepeat$$inlined$addListener$default$1 listener$iv = new AnimatorKt$doOnRepeat$$inlined$addListener$default$1(action);
        $this$doOnRepeat.addListener(listener$iv);
        return listener$iv;
    }

    @RequiresApi(19)
    @NotNull
    public static final Animator.AnimatorPauseListener doOnResume(@NotNull Animator $this$doOnResume, @NotNull l<? super Animator, x> action) {
        k.e($this$doOnResume, "<this>");
        k.e(action, "action");
        AnimatorKt$doOnResume$$inlined$addPauseListener$default$1 listener$iv = new AnimatorKt$doOnResume$$inlined$addPauseListener$default$1(action);
        $this$doOnResume.addPauseListener(listener$iv);
        return listener$iv;
    }

    @RequiresApi(19)
    @NotNull
    public static final Animator.AnimatorPauseListener doOnPause(@NotNull Animator $this$doOnPause, @NotNull l<? super Animator, x> action) {
        k.e($this$doOnPause, "<this>");
        k.e(action, "action");
        AnimatorKt$doOnPause$$inlined$addPauseListener$default$1 listener$iv = new AnimatorKt$doOnPause$$inlined$addPauseListener$default$1(action);
        $this$doOnPause.addPauseListener(listener$iv);
        return listener$iv;
    }

    public static /* synthetic */ Animator.AnimatorListener addListener$default(Animator $this$addListener_u24default, l onEnd, l onStart, l onCancel, l onRepeat, int i, Object obj) {
        if ((i & 1) != 0) {
            onEnd = AnimatorKt$addListener$1.INSTANCE;
        }
        if ((i & 2) != 0) {
            onStart = AnimatorKt$addListener$2.INSTANCE;
        }
        if ((i & 4) != 0) {
            onCancel = AnimatorKt$addListener$3.INSTANCE;
        }
        if ((i & 8) != 0) {
            onRepeat = AnimatorKt$addListener$4.INSTANCE;
        }
        k.e($this$addListener_u24default, "<this>");
        k.e(onEnd, "onEnd");
        k.e(onStart, "onStart");
        k.e(onCancel, "onCancel");
        k.e(onRepeat, "onRepeat");
        AnimatorKt$addListener$listener$1 listener = new AnimatorKt$addListener$listener$1(onRepeat, onEnd, onCancel, onStart);
        $this$addListener_u24default.addListener(listener);
        return listener;
    }

    @NotNull
    public static final Animator.AnimatorListener addListener(@NotNull Animator $this$addListener, @NotNull l<? super Animator, x> onEnd, @NotNull l<? super Animator, x> onStart, @NotNull l<? super Animator, x> onCancel, @NotNull l<? super Animator, x> onRepeat) {
        k.e($this$addListener, "<this>");
        k.e(onEnd, "onEnd");
        k.e(onStart, "onStart");
        k.e(onCancel, "onCancel");
        k.e(onRepeat, "onRepeat");
        AnimatorKt$addListener$listener$1 listener = new AnimatorKt$addListener$listener$1(onRepeat, onEnd, onCancel, onStart);
        $this$addListener.addListener(listener);
        return listener;
    }

    public static /* synthetic */ Animator.AnimatorPauseListener addPauseListener$default(Animator $this$addPauseListener_u24default, l onResume, l onPause, int i, Object obj) {
        if ((i & 1) != 0) {
            onResume = AnimatorKt$addPauseListener$1.INSTANCE;
        }
        if ((i & 2) != 0) {
            onPause = AnimatorKt$addPauseListener$2.INSTANCE;
        }
        k.e($this$addPauseListener_u24default, "<this>");
        k.e(onResume, "onResume");
        k.e(onPause, "onPause");
        AnimatorKt$addPauseListener$listener$1 listener = new AnimatorKt$addPauseListener$listener$1(onPause, onResume);
        $this$addPauseListener_u24default.addPauseListener(listener);
        return listener;
    }

    @RequiresApi(19)
    @NotNull
    public static final Animator.AnimatorPauseListener addPauseListener(@NotNull Animator $this$addPauseListener, @NotNull l<? super Animator, x> onResume, @NotNull l<? super Animator, x> onPause) {
        k.e($this$addPauseListener, "<this>");
        k.e(onResume, "onResume");
        k.e(onPause, "onPause");
        AnimatorKt$addPauseListener$listener$1 listener = new AnimatorKt$addPauseListener$listener$1(onPause, onResume);
        $this$addPauseListener.addPauseListener(listener);
        return listener;
    }
}
