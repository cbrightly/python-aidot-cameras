package kotlinx.coroutines;

import kotlin.coroutines.d;
import kotlin.coroutines.e;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.jvm.functions.p;
import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aT\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\n\u0002\u0004\n\u0002\b\u0019¨\u0006\u000b"}, d2 = {"runBlocking", "T", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48)
/* compiled from: Builders.kt */
public final /* synthetic */ class i {
    public static /* synthetic */ Object b(g gVar, p pVar, int i, Object obj) {
        if ((i & 1) != 0) {
            gVar = h.INSTANCE;
        }
        return h.e(gVar, pVar);
    }

    public static final <T> T a(@NotNull g context, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block) {
        g newContext;
        j1 eventLoop;
        Thread currentThread = Thread.currentThread();
        e contextInterceptor = (e) context.get(e.a);
        if (contextInterceptor == null) {
            eventLoop = x2.a.b();
            newContext = h0.e(s1.c, context.plus(eventLoop));
        } else {
            j1 j1Var = null;
            j1 it = contextInterceptor instanceof j1 ? (j1) contextInterceptor : null;
            if (it != null && it.g1()) {
                j1Var = it;
            }
            if (j1Var == null) {
                j1Var = x2.a.a();
            }
            eventLoop = j1Var;
            newContext = h0.e(s1.c, context);
        }
        f coroutine = new f(newContext, currentThread, eventLoop);
        coroutine.U0(q0.DEFAULT, coroutine, block);
        return coroutine.V0();
    }
}
