package kotlinx.coroutines.intrinsics;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.e;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.b0;
import kotlinx.coroutines.h2;
import kotlinx.coroutines.internal.d0;
import kotlinx.coroutines.internal.j0;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a9\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00042\u001a\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\b\u001a>\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\t\u001aR\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u0002*\u001e\b\u0001\u0012\u0004\u0012\u0002H\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000b2\u0006\u0010\f\u001a\u0002H\n2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\r\u001a>\u0010\u000e\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\t\u001aR\u0010\u000e\u001a\u00020\u0001\"\u0004\b\u0000\u0010\n\"\u0004\b\u0001\u0010\u0002*\u001e\b\u0001\u0012\u0004\u0012\u0002H\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000b2\u0006\u0010\f\u001a\u0002H\n2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\r\u001aY\u0010\u000f\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u00020\u00102\u0006\u0010\f\u001a\u0002H\n2'\u0010\u0005\u001a#\b\u0001\u0012\u0004\u0012\u0002H\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000b¢\u0006\u0002\b\u0011H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aY\u0010\u0013\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u00020\u00102\u0006\u0010\f\u001a\u0002H\n2'\u0010\u0005\u001a#\b\u0001\u0012\u0004\u0012\u0002H\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u000b¢\u0006\u0002\b\u0011H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a?\u0010\u0014\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00102\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00170\u00062\u000e\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0019H\b\u0002\u0004\n\u0002\b\u0019¨\u0006\u001a"}, d2 = {"startDirect", "", "T", "completion", "Lkotlin/coroutines/Continuation;", "block", "Lkotlin/Function1;", "", "startCoroutineUndispatched", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "startCoroutineUnintercepted", "startUndispatchedOrReturn", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/internal/ScopeCoroutine;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "startUndispatchedOrReturnIgnoreTimeout", "undispatchedResult", "shouldThrow", "", "", "startBlock", "Lkotlin/Function0;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: Undispatched.kt */
public final class b {
    public static final <T> void a(@NotNull kotlin.jvm.functions.l<? super d<? super T>, ? extends Object> $this$startCoroutineUndispatched, @NotNull d<? super T> completion) {
        g context$iv;
        Object oldValue$iv;
        d a = h.a(completion);
        d actualCompletion = a;
        try {
            context$iv = completion.getContext();
            oldValue$iv = j0.c(context$iv, (Object) null);
            Object invoke = ((kotlin.jvm.functions.l) e0.e($this$startCoroutineUndispatched, 1)).invoke(actualCompletion);
            j0.a(context$iv, oldValue$iv);
            Object value$iv = invoke;
            if (value$iv != c.d()) {
                o.a aVar = o.Companion;
                a.resumeWith(o.m17constructorimpl(value$iv));
            }
        } catch (Throwable e$iv) {
            o.a aVar2 = o.Companion;
            a.resumeWith(o.m17constructorimpl(p.a(e$iv)));
        }
    }

    public static final <R, T> void b(@NotNull kotlin.jvm.functions.p<? super R, ? super d<? super T>, ? extends Object> $this$startCoroutineUndispatched, R receiver, @NotNull d<? super T> completion) {
        g context$iv;
        Object oldValue$iv;
        d a = h.a(completion);
        d actualCompletion = a;
        try {
            context$iv = completion.getContext();
            oldValue$iv = j0.c(context$iv, (Object) null);
            Object invoke = ((kotlin.jvm.functions.p) e0.e($this$startCoroutineUndispatched, 2)).invoke(receiver, actualCompletion);
            j0.a(context$iv, oldValue$iv);
            Object value$iv = invoke;
            if (value$iv != c.d()) {
                o.a aVar = o.Companion;
                a.resumeWith(o.m17constructorimpl(value$iv));
            }
        } catch (Throwable e$iv) {
            o.a aVar2 = o.Companion;
            a.resumeWith(o.m17constructorimpl(p.a(e$iv)));
        }
    }

    @Nullable
    public static final <T, R> Object c(@NotNull d0<? super T> $this$startUndispatchedOrReturn, R receiver, @NotNull kotlin.jvm.functions.p<? super R, ? super d<? super T>, ? extends Object> block) {
        Object obj;
        d0 $this$undispatchedResult$iv = $this$startUndispatchedOrReturn;
        try {
            obj = ((kotlin.jvm.functions.p) e0.e(block, 2)).invoke(receiver, $this$startUndispatchedOrReturn);
        } catch (Throwable e$iv) {
            obj = new b0(e$iv, false, 2, (DefaultConstructorMarker) null);
        }
        Object result$iv = obj;
        if (result$iv == c.d()) {
            return c.d();
        }
        Object state$iv = $this$undispatchedResult$iv.u0(result$iv);
        if (state$iv == h2.b) {
            return c.d();
        }
        if (!(state$iv instanceof b0)) {
            return h2.h(state$iv);
        }
        Throwable th = ((b0) state$iv).b;
        Throwable exception$iv$iv = ((b0) state$iv).b;
        d<T> dVar = $this$undispatchedResult$iv.f;
        if (s0.d() && (dVar instanceof e)) {
            exception$iv$iv = kotlinx.coroutines.internal.e0.j(exception$iv$iv, (e) dVar);
        }
        throw exception$iv$iv;
    }

    @Nullable
    public static final <T, R> Object d(@NotNull d0<? super T> $this$startUndispatchedOrReturnIgnoreTimeout, R receiver, @NotNull kotlin.jvm.functions.p<? super R, ? super d<? super T>, ? extends Object> block) {
        Object obj;
        d0 $this$undispatchedResult$iv = $this$startUndispatchedOrReturnIgnoreTimeout;
        boolean z = false;
        try {
            obj = ((kotlin.jvm.functions.p) e0.e(block, 2)).invoke(receiver, $this$startUndispatchedOrReturnIgnoreTimeout);
        } catch (Throwable e$iv) {
            obj = new b0(e$iv, false, 2, (DefaultConstructorMarker) null);
        }
        Object result$iv = obj;
        if (result$iv == c.d()) {
            return c.d();
        }
        Object state$iv = $this$undispatchedResult$iv.u0(result$iv);
        if (state$iv == h2.b) {
            return c.d();
        }
        if (!(state$iv instanceof b0)) {
            return h2.h(state$iv);
        }
        Throwable e = ((b0) state$iv).b;
        if (!(e instanceof TimeoutCancellationException) || ((TimeoutCancellationException) e).coroutine != $this$startUndispatchedOrReturnIgnoreTimeout) {
            z = true;
        }
        if (z) {
            Throwable exception$iv$iv = ((b0) state$iv).b;
            d<T> dVar = $this$undispatchedResult$iv.f;
            if (s0.d() && (dVar instanceof e)) {
                exception$iv$iv = kotlinx.coroutines.internal.e0.j(exception$iv$iv, (e) dVar);
            }
            throw exception$iv$iv;
        } else if (!(result$iv instanceof b0)) {
            return result$iv;
        } else {
            Throwable exception$iv$iv2 = ((b0) result$iv).b;
            d<T> dVar2 = $this$undispatchedResult$iv.f;
            if (s0.d() && (dVar2 instanceof e)) {
                exception$iv$iv2 = kotlinx.coroutines.internal.e0.j(exception$iv$iv2, (e) dVar2);
            }
            throw exception$iv$iv2;
        }
    }
}
