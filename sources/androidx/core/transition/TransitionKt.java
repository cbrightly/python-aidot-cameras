package androidx.core.transition;

import android.transition.Transition;
import androidx.annotation.RequiresApi;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Transition.kt */
public final class TransitionKt {
    @RequiresApi(19)
    @NotNull
    public static final Transition.TransitionListener doOnEnd(@NotNull Transition $this$doOnEnd, @NotNull l<? super Transition, x> action) {
        k.e($this$doOnEnd, "<this>");
        k.e(action, "action");
        TransitionKt$doOnEnd$$inlined$addListener$default$1 listener$iv = new TransitionKt$doOnEnd$$inlined$addListener$default$1(action);
        $this$doOnEnd.addListener(listener$iv);
        return listener$iv;
    }

    @RequiresApi(19)
    @NotNull
    public static final Transition.TransitionListener doOnStart(@NotNull Transition $this$doOnStart, @NotNull l<? super Transition, x> action) {
        k.e($this$doOnStart, "<this>");
        k.e(action, "action");
        TransitionKt$doOnStart$$inlined$addListener$default$1 listener$iv = new TransitionKt$doOnStart$$inlined$addListener$default$1(action);
        $this$doOnStart.addListener(listener$iv);
        return listener$iv;
    }

    @RequiresApi(19)
    @NotNull
    public static final Transition.TransitionListener doOnCancel(@NotNull Transition $this$doOnCancel, @NotNull l<? super Transition, x> action) {
        k.e($this$doOnCancel, "<this>");
        k.e(action, "action");
        TransitionKt$doOnCancel$$inlined$addListener$default$1 listener$iv = new TransitionKt$doOnCancel$$inlined$addListener$default$1(action);
        $this$doOnCancel.addListener(listener$iv);
        return listener$iv;
    }

    @RequiresApi(19)
    @NotNull
    public static final Transition.TransitionListener doOnResume(@NotNull Transition $this$doOnResume, @NotNull l<? super Transition, x> action) {
        k.e($this$doOnResume, "<this>");
        k.e(action, "action");
        TransitionKt$doOnResume$$inlined$addListener$default$1 listener$iv = new TransitionKt$doOnResume$$inlined$addListener$default$1(action);
        $this$doOnResume.addListener(listener$iv);
        return listener$iv;
    }

    @RequiresApi(19)
    @NotNull
    public static final Transition.TransitionListener doOnPause(@NotNull Transition $this$doOnPause, @NotNull l<? super Transition, x> action) {
        k.e($this$doOnPause, "<this>");
        k.e(action, "action");
        TransitionKt$doOnPause$$inlined$addListener$default$1 listener$iv = new TransitionKt$doOnPause$$inlined$addListener$default$1(action);
        $this$doOnPause.addListener(listener$iv);
        return listener$iv;
    }

    public static /* synthetic */ Transition.TransitionListener addListener$default(Transition $this$addListener_u24default, l onEnd, l onStart, l onCancel, l onResume, l onPause, int i, Object obj) {
        l onStart2;
        l onCancel2;
        l onResume2;
        l onPause2;
        if ((i & 1) != 0) {
            onEnd = TransitionKt$addListener$1.INSTANCE;
        }
        if ((i & 2) != 0) {
            onStart2 = TransitionKt$addListener$2.INSTANCE;
        } else {
            onStart2 = onStart;
        }
        if ((i & 4) != 0) {
            onCancel2 = TransitionKt$addListener$3.INSTANCE;
        } else {
            onCancel2 = onCancel;
        }
        if ((i & 8) != 0) {
            onResume2 = TransitionKt$addListener$4.INSTANCE;
        } else {
            onResume2 = onResume;
        }
        if ((i & 16) != 0) {
            onPause2 = TransitionKt$addListener$5.INSTANCE;
        } else {
            onPause2 = onPause;
        }
        k.e($this$addListener_u24default, "<this>");
        k.e(onEnd, "onEnd");
        k.e(onStart2, "onStart");
        k.e(onCancel2, "onCancel");
        k.e(onResume2, "onResume");
        k.e(onPause2, "onPause");
        TransitionKt$addListener$listener$1 listener = new TransitionKt$addListener$listener$1(onEnd, onResume2, onPause2, onCancel2, onStart2);
        $this$addListener_u24default.addListener(listener);
        return listener;
    }

    @RequiresApi(19)
    @NotNull
    public static final Transition.TransitionListener addListener(@NotNull Transition $this$addListener, @NotNull l<? super Transition, x> onEnd, @NotNull l<? super Transition, x> onStart, @NotNull l<? super Transition, x> onCancel, @NotNull l<? super Transition, x> onResume, @NotNull l<? super Transition, x> onPause) {
        k.e($this$addListener, "<this>");
        k.e(onEnd, "onEnd");
        k.e(onStart, "onStart");
        k.e(onCancel, "onCancel");
        k.e(onResume, "onResume");
        k.e(onPause, "onPause");
        TransitionKt$addListener$listener$1 listener = new TransitionKt$addListener$listener$1(onEnd, onResume, onPause, onCancel, onStart);
        $this$addListener.addListener(listener);
        return listener;
    }
}
