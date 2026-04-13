package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001aI\u0010\b\u001a\u0004\u0018\u00010\u0004\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012%\b\u0002\u0010\t\u001a\u001f\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u000f\u0018\u00010\nH\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a.\u0010\b\u001a\u0004\u0018\u00010\u0004\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, d2 = {"recoverResult", "Lkotlin/Result;", "T", "state", "", "uCont", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toState", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "caller", "Lkotlinx/coroutines/CancellableContinuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: CompletionState.kt */
public final class e0 {
    public static /* synthetic */ Object d(Object obj, kotlin.jvm.functions.l lVar, int i, Object obj2) {
        if ((i & 1) != 0) {
            lVar = null;
        }
        return b(obj, lVar);
    }

    @Nullable
    public static final <T> Object b(@NotNull Object $this$toState, @Nullable kotlin.jvm.functions.l<? super Throwable, x> onCancellation) {
        Throwable it = o.m20exceptionOrNullimpl($this$toState);
        if (it != null) {
            return new b0(it, false, 2, (DefaultConstructorMarker) null);
        }
        Object it2 = $this$toState;
        if (onCancellation != null) {
            return new c0(it2, onCancellation);
        }
        return it2;
    }

    @Nullable
    public static final <T> Object c(@NotNull Object $this$toState, @NotNull n<?> caller) {
        Throwable th;
        Throwable it = o.m20exceptionOrNullimpl($this$toState);
        if (it == null) {
            return $this$toState;
        }
        if (!s0.d() || !(caller instanceof e)) {
            th = it;
        } else {
            th = kotlinx.coroutines.internal.e0.j(it, (e) caller);
        }
        return new b0(th, false, 2, (DefaultConstructorMarker) null);
    }

    @NotNull
    public static final <T> Object a(@Nullable Object state, @NotNull d<? super T> uCont) {
        if (state instanceof b0) {
            o.a aVar = o.Companion;
            Throwable exception$iv = ((b0) state).b;
            if (s0.d() && (uCont instanceof e)) {
                exception$iv = kotlinx.coroutines.internal.e0.j(exception$iv, (e) uCont);
            }
            return o.m17constructorimpl(p.a(exception$iv));
        }
        o.a aVar2 = o.Companion;
        return o.m17constructorimpl(state);
    }
}
