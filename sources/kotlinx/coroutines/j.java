package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.coroutines.e;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.coroutines.intrinsics.c;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.internal.d0;
import kotlinx.coroutines.internal.j0;
import kotlinx.coroutines.intrinsics.a;
import kotlinx.coroutines.intrinsics.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000J\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001aU\u0010\u0004\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u00072'\u0010\b\u001a#\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\t¢\u0006\u0002\b\rH@ø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\u000e\u001a[\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0010\"\u0004\b\u0000\u0010\u0005*\u00020\n2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00122'\u0010\b\u001a#\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\t¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001aF\u0010\u0014\u001a\u0002H\u0005\"\u0004\b\u0000\u0010\u0005*\u00020\u00152)\b\b\u0010\b\u001a#\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\t¢\u0006\u0002\b\rHJø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001aO\u0010\u0017\u001a\u00020\u0018*\u00020\n2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0011\u001a\u00020\u00122'\u0010\b\u001a#\b\u0001\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\t¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u001a\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, d2 = {"RESUMED", "", "SUSPENDED", "UNDECIDED", "withContext", "T", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "async", "Lkotlinx/coroutines/Deferred;", "start", "Lkotlinx/coroutines/CoroutineStart;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/Deferred;", "invoke", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "launch", "Lkotlinx/coroutines/Job;", "", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/Job;", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48)
/* compiled from: Builders.common.kt */
public final /* synthetic */ class j {
    public static /* synthetic */ z1 d(o0 o0Var, g gVar, q0 q0Var, p pVar, int i, Object obj) {
        if ((i & 1) != 0) {
            gVar = h.INSTANCE;
        }
        if ((i & 2) != 0) {
            q0Var = q0.DEFAULT;
        }
        return h.c(o0Var, gVar, q0Var, pVar);
    }

    @NotNull
    public static final z1 c(@NotNull o0 $this$launch, @NotNull g context, @NotNull q0 start, @NotNull p<? super o0, ? super d<? super x>, ? extends Object> block) {
        t2 coroutine;
        g newContext = h0.e($this$launch, context);
        if (start.isLazy()) {
            coroutine = new j2(newContext, block);
        } else {
            coroutine = new t2(newContext, true);
        }
        coroutine.U0(start, coroutine, block);
        return coroutine;
    }

    public static /* synthetic */ w0 b(o0 o0Var, g gVar, q0 q0Var, p pVar, int i, Object obj) {
        if ((i & 1) != 0) {
            gVar = h.INSTANCE;
        }
        if ((i & 2) != 0) {
            q0Var = q0.DEFAULT;
        }
        return h.a(o0Var, gVar, q0Var, pVar);
    }

    @NotNull
    public static final <T> w0<T> a(@NotNull o0 $this$async, @NotNull g context, @NotNull q0 start, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block) {
        x0 coroutine;
        g newContext = h0.e($this$async, context);
        if (start.isLazy()) {
            coroutine = new i2(newContext, block);
        } else {
            coroutine = new x0(newContext, true);
        }
        coroutine.U0(start, coroutine, block);
        return coroutine;
    }

    @Nullable
    public static final <T> Object e(@NotNull g context, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        Object obj;
        d uCont = $completion;
        g oldContext = uCont.getContext();
        g newContext = h0.d(oldContext, context);
        c2.i(newContext);
        if (newContext == oldContext) {
            d0 coroutine = new d0(newContext, uCont);
            obj = b.c(coroutine, coroutine, block);
        } else {
            e.b bVar = e.a;
            if (k.a(newContext.get(bVar), oldContext.get(bVar))) {
                b3 coroutine2 = new b3(newContext, uCont);
                Object oldValue$iv = j0.c(newContext, (Object) null);
                try {
                    Object c = b.c(coroutine2, coroutine2, block);
                    j0.a(newContext, oldValue$iv);
                    obj = c;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    j0.a(newContext, oldValue$iv);
                    throw th2;
                }
            } else {
                a1 coroutine3 = new a1(newContext, uCont);
                a.e(block, coroutine3, coroutine3, (kotlin.jvm.functions.l) null, 4, (Object) null);
                obj = coroutine3.W0();
            }
        }
        if (obj == c.d()) {
            kotlin.coroutines.jvm.internal.h.c($completion);
        }
        return obj;
    }
}
