package kotlinx.coroutines.channels;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.h0;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0001\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2-\b\u0002\u0010\n\u001a'\u0012\u0015\u0012\u0013\u0018\u00010\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000bj\u0004\u0018\u0001`\u00112-\u0010\u0012\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0013¢\u0006\u0002\b\u0017H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, d2 = {"actor", "Lkotlinx/coroutines/channels/SendChannel;", "E", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "start", "Lkotlinx/coroutines/CoroutineStart;", "onCompletion", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ActorScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/SendChannel;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: Actor.kt */
public final class e {
    public static /* synthetic */ a0 b(o0 o0Var, g gVar, int i, q0 q0Var, kotlin.jvm.functions.l lVar, p pVar, int i2, Object obj) {
        h hVar;
        int i3;
        q0 q0Var2;
        kotlin.jvm.functions.l lVar2;
        if ((i2 & 1) != 0) {
            hVar = h.INSTANCE;
        } else {
            hVar = gVar;
        }
        if ((i2 & 2) != 0) {
            i3 = 0;
        } else {
            i3 = i;
        }
        if ((i2 & 4) != 0) {
            q0Var2 = q0.DEFAULT;
        } else {
            q0Var2 = q0Var;
        }
        if ((i2 & 8) != 0) {
            lVar2 = null;
        } else {
            lVar2 = lVar;
        }
        return a(o0Var, hVar, i3, q0Var2, lVar2, pVar);
    }

    @NotNull
    public static final <E> a0<E> a(@NotNull o0 $this$actor, @NotNull g context, int capacity, @NotNull q0 start, @Nullable kotlin.jvm.functions.l<? super Throwable, x> onCompletion, @NotNull p<? super f<E>, ? super d<? super x>, ? extends Object> block) {
        d coroutine;
        g newContext = h0.e($this$actor, context);
        i channel = k.b(capacity, (h) null, (kotlin.jvm.functions.l) null, 6, (Object) null);
        if (start.isLazy()) {
            coroutine = new q(newContext, channel, block);
        } else {
            coroutine = new d(newContext, channel, true);
        }
        if (onCompletion != null) {
            coroutine.t(onCompletion);
        }
        coroutine.U0(start, coroutine, block);
        return coroutine;
    }
}
