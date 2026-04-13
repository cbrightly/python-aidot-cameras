package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.e0;
import kotlin.l;
import kotlinx.coroutines.flow.d;
import kotlinx.coroutines.internal.j0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a[\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u0002H\u00022\b\b\u0002\u0010\u0006\u001a\u00020\u00072\"\u0010\b\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\tH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00010\r\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u000eH\u0000\u001a&\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0010\"\u0004\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0004H\u0002\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"withContextUndispatched", "T", "V", "newContext", "Lkotlin/coroutines/CoroutineContext;", "value", "countOrElement", "", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asChannelFlow", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "Lkotlinx/coroutines/flow/Flow;", "withUndispatchedContextCollector", "Lkotlinx/coroutines/flow/FlowCollector;", "emitContext", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: ChannelFlow.kt */
public final class e {
    /* access modifiers changed from: private */
    public static final <T> d<T> d(d<? super T> $this$withUndispatchedContextCollector, g emitContext) {
        if ($this$withUndispatchedContextCollector instanceof r ? true : $this$withUndispatchedContextCollector instanceof m) {
            return $this$withUndispatchedContextCollector;
        }
        return new u($this$withUndispatchedContextCollector, emitContext);
    }

    public static /* synthetic */ Object c(g gVar, Object obj, Object obj2, p pVar, kotlin.coroutines.d dVar, int i, Object obj3) {
        if ((i & 4) != 0) {
            obj2 = j0.b(gVar);
        }
        return b(gVar, obj, obj2, pVar, dVar);
    }

    /* JADX INFO: finally extract failed */
    @Nullable
    public static final <T, V> Object b(@NotNull g newContext, V value, @NotNull Object countOrElement, @NotNull p<? super V, ? super kotlin.coroutines.d<? super T>, ? extends Object> block, @NotNull kotlin.coroutines.d<? super T> $completion) {
        kotlin.coroutines.d uCont = $completion;
        Object oldValue$iv = j0.c(newContext, countOrElement);
        try {
            Object invoke = ((p) e0.e(block, 2)).invoke(value, new s(uCont, newContext));
            j0.a(newContext, oldValue$iv);
            if (invoke == c.d()) {
                h.c($completion);
            }
            return invoke;
        } catch (Throwable th) {
            j0.a(newContext, oldValue$iv);
            throw th;
        }
    }
}
