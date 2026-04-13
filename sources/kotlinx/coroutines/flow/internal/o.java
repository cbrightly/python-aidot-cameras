package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.c2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u00032\u00020\u0004B\u001b\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ'\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0019J\u0019\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u001bJ%\u0010\u001a\u001a\u0004\u0018\u00010\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0018\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001eJ\u001a\u0010\u001f\u001a\u00020\u00102\u0006\u0010 \u001a\u00020!2\b\u0010\u0018\u001a\u0004\u0018\u00010\u001cH\u0002J\n\u0010\"\u001a\u0004\u0018\u00010#H\u0016J \u0010$\u001a\u00020\u001c2\u000e\u0010%\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001c0&H\u0016ø\u0001\u0000¢\u0006\u0002\u0010'J\b\u0010(\u001a\u00020\u0010H\u0016R\u0016\u0010\t\u001a\u0004\u0018\u00010\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u0006\u001a\u00020\u00078\u0000X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\r8\u0000X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0000X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fX\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0007X\u000e¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006)"}, d2 = {"Lkotlinx/coroutines/flow/internal/SafeCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "collector", "collectContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "collectContextSize", "", "completion", "Lkotlin/coroutines/Continuation;", "", "context", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "lastEmissionContext", "checkContext", "currentContext", "previousContext", "value", "(Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "uCont", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)Ljava/lang/Object;", "exceptionTransparencyViolated", "exception", "Lkotlinx/coroutines/flow/internal/DownstreamExceptionContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "invokeSuspend", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "releaseIntercepted", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: SafeCollector.kt */
public final class o<T> extends d implements kotlinx.coroutines.flow.d<T>, e {
    @NotNull
    public final g collectContext;
    public final int collectContextSize;
    @NotNull
    public final kotlinx.coroutines.flow.d<T> collector;
    @Nullable
    private kotlin.coroutines.d<? super x> completion;
    @Nullable
    private g lastEmissionContext;

    public o(@NotNull kotlinx.coroutines.flow.d<? super T> collector2, @NotNull g collectContext2) {
        super(l.c, h.INSTANCE);
        this.collector = collector2;
        this.collectContext = collectContext2;
        this.collectContextSize = ((Number) collectContext2.fold(0, a.INSTANCE)).intValue();
    }

    @Nullable
    public e getCallerFrame() {
        kotlin.coroutines.d<? super x> dVar = this.completion;
        if (dVar instanceof e) {
            return (e) dVar;
        }
        return null;
    }

    @Nullable
    public StackTraceElement getStackTraceElement() {
        return null;
    }

    @l(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"<anonymous>", "", "T", "count", "<anonymous parameter 1>", "Lkotlin/coroutines/CoroutineContext$Element;", "invoke", "(ILkotlin/coroutines/CoroutineContext$Element;)Ljava/lang/Integer;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SafeCollector.kt */
    public static final class a extends kotlin.jvm.internal.l implements p<Integer, g.b, Integer> {
        public static final a INSTANCE = new a();

        a() {
            super(2);
        }

        @NotNull
        public final Integer invoke(int count, @NotNull g.b $noName_1) {
            return Integer.valueOf(count + 1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
            return invoke(((Number) p1).intValue(), (g.b) p2);
        }
    }

    @NotNull
    public g getContext() {
        g gVar = this.lastEmissionContext;
        return gVar == null ? h.INSTANCE : gVar;
    }

    @NotNull
    public Object invokeSuspend(@NotNull Object result) {
        Throwable it = kotlin.o.m20exceptionOrNullimpl(result);
        if (it != null) {
            this.lastEmissionContext = new i(it, getContext());
        }
        kotlin.coroutines.d<? super x> dVar = this.completion;
        if (dVar != null) {
            dVar.resumeWith(result);
        }
        return c.d();
    }

    public void releaseIntercepted() {
        super.releaseIntercepted();
    }

    @Nullable
    public Object emit(T value, @NotNull kotlin.coroutines.d<? super x> $completion) {
        kotlin.coroutines.d uCont = $completion;
        try {
            Object c = c(uCont, value);
            if (c == c.d()) {
                kotlin.coroutines.jvm.internal.h.c($completion);
            }
            return c == c.d() ? c : x.a;
        } catch (Throwable e) {
            this.lastEmissionContext = new i(e, uCont.getContext());
            throw e;
        }
    }

    private final Object c(kotlin.coroutines.d<? super x> uCont, T value) {
        g currentContext = uCont.getContext();
        c2.i(currentContext);
        g previousContext = this.lastEmissionContext;
        if (previousContext != currentContext) {
            a(currentContext, previousContext, value);
            this.lastEmissionContext = currentContext;
        }
        this.completion = uCont;
        Object result = p.a.invoke(this.collector, value, this);
        if (!k.a(result, c.d())) {
            this.completion = null;
        }
        return result;
    }

    private final void a(g currentContext, g previousContext, T value) {
        if (previousContext instanceof i) {
            d((i) previousContext, value);
        }
        q.a(this, currentContext);
    }

    private final void d(i exception, Object value) {
        throw new IllegalStateException(kotlin.text.p.f("\n            Flow exception transparency is violated:\n                Previous 'emit' call has thrown exception " + exception.c + ", but then emission attempt of value '" + value + "' has been detected.\n                Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.\n                For a more detailed explanation, please refer to Flow documentation.\n            ").toString());
    }
}
