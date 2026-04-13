package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.internal.d0;
import kotlinx.coroutines.internal.g;
import kotlinx.coroutines.intrinsics.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000F\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000e\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u0006\u0010\t\u001a\u00020\u0002\u001aM\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b2'\u0010\f\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000b0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\r¢\u0006\u0002\b\u0010H@ø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0011\u001a\u0011\u0010\u0012\u001a\u00020\bHHø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a\u001e\u0010\u0014\u001a\u00020\u0015*\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0019\u001a\u001c\u0010\u0014\u001a\u00020\u0015*\u00020\u00022\u0010\b\u0002\u0010\u0018\u001a\n\u0018\u00010\u001aj\u0004\u0018\u0001`\u001b\u001a\n\u0010\u001c\u001a\u00020\u0015*\u00020\u0002\u001a\u0015\u0010\u001d\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002\"\u001b\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\f\u0012\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0000\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, d2 = {"isActive", "", "Lkotlinx/coroutines/CoroutineScope;", "isActive$annotations", "(Lkotlinx/coroutines/CoroutineScope;)V", "(Lkotlinx/coroutines/CoroutineScope;)Z", "CoroutineScope", "context", "Lkotlin/coroutines/CoroutineContext;", "MainScope", "coroutineScope", "R", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "currentCoroutineContext", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancel", "", "message", "", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "ensureActive", "plus", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: CoroutineScope.kt */
public final class p0 {
    @NotNull
    public static final o0 b() {
        return new g(v2.b((z1) null, 1, (Object) null).plus(d1.c()));
    }

    public static final boolean f(@NotNull o0 $this$isActive) {
        z1 z1Var = (z1) $this$isActive.getCoroutineContext().get(z1.g);
        if (z1Var == null) {
            return true;
        }
        return z1Var.isActive();
    }

    @Nullable
    public static final <R> Object e(@NotNull p<? super o0, ? super d<? super R>, ? extends Object> block, @NotNull d<? super R> $completion) {
        d uCont = $completion;
        d0 coroutine = new d0(uCont.getContext(), uCont);
        Object c = b.c(coroutine, coroutine, block);
        if (c == c.d()) {
            h.c($completion);
        }
        return c;
    }

    @NotNull
    public static final o0 a(@NotNull kotlin.coroutines.g context) {
        return new g(context.get(z1.g) != null ? context : context.plus(e2.b((z1) null, 1, (Object) null)));
    }

    public static /* synthetic */ void d(o0 o0Var, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        c(o0Var, cancellationException);
    }

    public static final void c(@NotNull o0 $this$cancel, @Nullable CancellationException cause) {
        z1 job = (z1) $this$cancel.getCoroutineContext().get(z1.g);
        if (job != null) {
            job.c(cause);
            return;
        }
        throw new IllegalStateException(k.l("Scope cannot be cancelled because it does not have a job: ", $this$cancel).toString());
    }
}
