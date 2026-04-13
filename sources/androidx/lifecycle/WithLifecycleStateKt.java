package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.d;
import kotlin.coroutines.h;
import kotlin.coroutines.intrinsics.b;
import kotlin.coroutines.intrinsics.c;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.j;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.k2;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000,\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u001aA\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a+\u0010\f\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\f\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a+\u0010\u0010\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\u0010\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a+\u0010\u0011\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\r\u001a+\u0010\u0011\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a3\u0010\u0012\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a3\u0010\u0012\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a3\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u000e\b\u0004\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00010\nHHø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"}, d2 = {"suspendWithStateAtLeastUnchecked", "R", "Landroidx/lifecycle/Lifecycle;", "state", "Landroidx/lifecycle/Lifecycle$State;", "dispatchNeeded", "", "lifecycleDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "block", "Lkotlin/Function0;", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;ZLkotlinx/coroutines/CoroutineDispatcher;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withCreated", "(Landroidx/lifecycle/Lifecycle;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/lifecycle/LifecycleOwner;", "(Landroidx/lifecycle/LifecycleOwner;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withResumed", "withStarted", "withStateAtLeast", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Landroidx/lifecycle/LifecycleOwner;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withStateAtLeastUnchecked", "lifecycle-runtime-ktx_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: WithLifecycleState.kt */
public final class WithLifecycleStateKt {
    @Nullable
    public static final <R> Object withStateAtLeast(@NotNull Lifecycle $this$withStateAtLeast, @NotNull Lifecycle.State state, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        if (state.compareTo(Lifecycle.State.CREATED) >= 0) {
            Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStateAtLeast;
            k2 lifecycleDispatcher$iv = d1.c().W();
            boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
            if (!dispatchNeeded$iv) {
                if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                    throw new LifecycleDestroyedException();
                } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state) >= 0) {
                    return block.invoke();
                }
            }
            return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
        }
        throw new IllegalArgumentException(("target state must be CREATED or greater, found " + state).toString());
    }

    /* JADX WARNING: type inference failed for: r4v3, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withStateAtLeast$$forInline(Lifecycle $this$withStateAtLeast, Lifecycle.State state, a<? extends R> block, d<? super R> $completion) {
        if (!(state.compareTo(Lifecycle.State.CREATED) >= 0)) {
            throw new IllegalArgumentException(("target state must be CREATED or greater, found " + state).toString());
        }
        d1.c().W();
        j.c(3);
        ? r4 = 0;
        r4.getContext();
        throw r4;
    }

    @Nullable
    public static final <R> Object withCreated(@NotNull Lifecycle $this$withCreated, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        Lifecycle.State state$iv = Lifecycle.State.CREATED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withCreated;
        k2 lifecycleDispatcher$iv = d1.c().W();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    /* JADX WARNING: type inference failed for: r0v4, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withCreated$$forInline(Lifecycle $this$withCreated, a<? extends R> block, d<? super R> $completion) {
        Lifecycle.State state = Lifecycle.State.CREATED;
        d1.c().W();
        j.c(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }

    @Nullable
    public static final <R> Object withStarted(@NotNull Lifecycle $this$withStarted, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        Lifecycle.State state$iv = Lifecycle.State.STARTED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStarted;
        k2 lifecycleDispatcher$iv = d1.c().W();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    /* JADX WARNING: type inference failed for: r0v4, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withStarted$$forInline(Lifecycle $this$withStarted, a<? extends R> block, d<? super R> $completion) {
        Lifecycle.State state = Lifecycle.State.STARTED;
        d1.c().W();
        j.c(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }

    @Nullable
    public static final <R> Object withResumed(@NotNull Lifecycle $this$withResumed, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        Lifecycle.State state$iv = Lifecycle.State.RESUMED;
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withResumed;
        k2 lifecycleDispatcher$iv = d1.c().W();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    /* JADX WARNING: type inference failed for: r0v4, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withResumed$$forInline(Lifecycle $this$withResumed, a<? extends R> block, d<? super R> $completion) {
        Lifecycle.State state = Lifecycle.State.RESUMED;
        d1.c().W();
        j.c(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }

    @Nullable
    public static final <R> Object withStateAtLeast(@NotNull LifecycleOwner $this$withStateAtLeast, @NotNull Lifecycle.State state, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        Lifecycle $this$withStateAtLeast$iv = $this$withStateAtLeast.getLifecycle();
        k.d($this$withStateAtLeast$iv, "lifecycle");
        if (state.compareTo(Lifecycle.State.CREATED) >= 0) {
            Lifecycle $this$withStateAtLeastUnchecked$iv$iv = $this$withStateAtLeast$iv;
            k2 lifecycleDispatcher$iv$iv = d1.c().W();
            boolean dispatchNeeded$iv$iv = lifecycleDispatcher$iv$iv.isDispatchNeeded($completion.getContext());
            if (!dispatchNeeded$iv$iv) {
                if ($this$withStateAtLeastUnchecked$iv$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                    throw new LifecycleDestroyedException();
                } else if ($this$withStateAtLeastUnchecked$iv$iv.getCurrentState().compareTo(state) >= 0) {
                    return block.invoke();
                }
            }
            return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv$iv, state, dispatchNeeded$iv$iv, lifecycleDispatcher$iv$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
        }
        throw new IllegalArgumentException(("target state must be CREATED or greater, found " + state).toString());
    }

    /* JADX WARNING: type inference failed for: r6v3, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withStateAtLeast$$forInline(LifecycleOwner $this$withStateAtLeast, Lifecycle.State state, a<? extends R> block, d<? super R> $completion) {
        k.d($this$withStateAtLeast.getLifecycle(), "lifecycle");
        if (!(state.compareTo(Lifecycle.State.CREATED) >= 0)) {
            throw new IllegalArgumentException(("target state must be CREATED or greater, found " + state).toString());
        }
        d1.c().W();
        j.c(3);
        ? r6 = 0;
        r6.getContext();
        throw r6;
    }

    @Nullable
    public static final <R> Object withCreated(@NotNull LifecycleOwner $this$withCreated, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withCreated.getLifecycle();
        k.d($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.CREATED;
        k2 lifecycleDispatcher$iv = d1.c().W();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    /* JADX WARNING: type inference failed for: r0v5, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withCreated$$forInline(LifecycleOwner $this$withCreated, a<? extends R> block, d<? super R> $completion) {
        k.d($this$withCreated.getLifecycle(), "lifecycle");
        Lifecycle.State state = Lifecycle.State.CREATED;
        d1.c().W();
        j.c(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }

    @Nullable
    public static final <R> Object withStarted(@NotNull LifecycleOwner $this$withStarted, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withStarted.getLifecycle();
        k.d($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.STARTED;
        k2 lifecycleDispatcher$iv = d1.c().W();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    /* JADX WARNING: type inference failed for: r0v5, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withStarted$$forInline(LifecycleOwner $this$withStarted, a<? extends R> block, d<? super R> $completion) {
        k.d($this$withStarted.getLifecycle(), "lifecycle");
        Lifecycle.State state = Lifecycle.State.STARTED;
        d1.c().W();
        j.c(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }

    @Nullable
    public static final <R> Object withResumed(@NotNull LifecycleOwner $this$withResumed, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        Lifecycle $this$withStateAtLeastUnchecked$iv = $this$withResumed.getLifecycle();
        k.d($this$withStateAtLeastUnchecked$iv, "lifecycle");
        Lifecycle.State state$iv = Lifecycle.State.RESUMED;
        k2 lifecycleDispatcher$iv = d1.c().W();
        boolean dispatchNeeded$iv = lifecycleDispatcher$iv.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded$iv) {
            if ($this$withStateAtLeastUnchecked$iv.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked$iv.getCurrentState().compareTo(state$iv) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked$iv, state$iv, dispatchNeeded$iv, lifecycleDispatcher$iv, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    /* JADX WARNING: type inference failed for: r0v5, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withResumed$$forInline(LifecycleOwner $this$withResumed, a<? extends R> block, d<? super R> $completion) {
        k.d($this$withResumed.getLifecycle(), "lifecycle");
        Lifecycle.State state = Lifecycle.State.RESUMED;
        d1.c().W();
        j.c(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }

    @Nullable
    public static final <R> Object withStateAtLeastUnchecked(@NotNull Lifecycle $this$withStateAtLeastUnchecked, @NotNull Lifecycle.State state, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        k2 lifecycleDispatcher = d1.c().W();
        boolean dispatchNeeded = lifecycleDispatcher.isDispatchNeeded($completion.getContext());
        if (!dispatchNeeded) {
            if ($this$withStateAtLeastUnchecked.getCurrentState() == Lifecycle.State.DESTROYED) {
                throw new LifecycleDestroyedException();
            } else if ($this$withStateAtLeastUnchecked.getCurrentState().compareTo(state) >= 0) {
                return block.invoke();
            }
        }
        return suspendWithStateAtLeastUnchecked($this$withStateAtLeastUnchecked, state, dispatchNeeded, lifecycleDispatcher, new WithLifecycleStateKt$withStateAtLeastUnchecked$2(block), $completion);
    }

    /* JADX WARNING: type inference failed for: r0v3, types: [java.lang.Throwable, kotlin.coroutines.d] */
    private static final <R> Object withStateAtLeastUnchecked$$forInline(Lifecycle $this$withStateAtLeastUnchecked, Lifecycle.State state, a<? extends R> block, d<? super R> $completion) {
        d1.c().W();
        j.c(3);
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }

    @Nullable
    public static final <R> Object suspendWithStateAtLeastUnchecked(@NotNull Lifecycle $this$suspendWithStateAtLeastUnchecked, @NotNull Lifecycle.State state, boolean dispatchNeeded, @NotNull i0 lifecycleDispatcher, @NotNull a<? extends R> block, @NotNull d<? super R> $completion) {
        o cancellable$iv = new o(b.c($completion), 1);
        cancellable$iv.w();
        n co = cancellable$iv;
        WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 observer = new WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1(state, $this$suspendWithStateAtLeastUnchecked, co, block);
        if (dispatchNeeded) {
            lifecycleDispatcher.dispatch(h.INSTANCE, new WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$1($this$suspendWithStateAtLeastUnchecked, observer));
        } else {
            $this$suspendWithStateAtLeastUnchecked.addObserver(observer);
        }
        co.f(new WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$2(lifecycleDispatcher, $this$suspendWithStateAtLeastUnchecked, observer));
        Object t = cancellable$iv.t();
        if (t == c.d()) {
            kotlin.coroutines.jvm.internal.h.c($completion);
        }
        return t;
    }
}
