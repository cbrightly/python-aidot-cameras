package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.e;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.channels.u;
import kotlinx.coroutines.flow.c;
import kotlinx.coroutines.flow.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\b \u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B+\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u0010H@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\u001f\u0010\u0012\u001a\u00020\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u0014H@ø\u0001\u0000¢\u0006\u0002\u0010\u0015J'\u0010\u0016\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u00102\u0006\u0010\u0017\u001a\u00020\u0007H@ø\u0001\u0000¢\u0006\u0002\u0010\u0018J\u001f\u0010\u0019\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\u0010H¤@ø\u0001\u0000¢\u0006\u0002\u0010\u0011J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058\u0004X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001c"}, d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "S", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectTo", "scope", "Lkotlinx/coroutines/channels/ProducerScope;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectWithContextUndispatched", "newContext", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "flowCollect", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ChannelFlow.kt */
public abstract class f<S, T> extends d<T> {
    @NotNull
    protected final c<S> q;

    @Nullable
    public Object a(@NotNull d<? super T> dVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
        return j(this, dVar, dVar2);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object e(@NotNull u<? super T> uVar, @NotNull kotlin.coroutines.d<? super x> dVar) {
        return k(this, uVar, dVar);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public abstract Object m(@NotNull d<? super T> dVar, @NotNull kotlin.coroutines.d<? super x> dVar2);

    public f(@NotNull c<? extends S> flow, @NotNull g context, int capacity, @NotNull h onBufferOverflow) {
        super(context, capacity, onBufferOverflow);
        this.q = flow;
    }

    private final Object l(d<? super T> collector, g newContext, kotlin.coroutines.d<? super x> $completion) {
        Object c = e.c(newContext, e.d(collector, $completion.getContext()), (Object) null, new a(this, (kotlin.coroutines.d<? super a>) null), $completion, 4, (Object) null);
        return c == kotlin.coroutines.intrinsics.c.d() ? c : x.a;
    }

    @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H@"}, d2 = {"<anonymous>", "", "S", "T", "it", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @kotlin.coroutines.jvm.internal.f(c = "kotlinx.coroutines.flow.internal.ChannelFlowOperator$collectWithContextUndispatched$2", f = "ChannelFlow.kt", l = {152}, m = "invokeSuspend")
    /* compiled from: ChannelFlow.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.l implements p<d<? super T>, kotlin.coroutines.d<? super x>, Object> {
        /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ f<S, T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(f<S, T> fVar, kotlin.coroutines.d<? super a> dVar) {
            super(2, dVar);
            this.this$0 = fVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            a aVar = new a(this.this$0, dVar);
            aVar.L$0 = obj;
            return aVar;
        }

        @Nullable
        public final Object invoke(@NotNull d<? super T> dVar, @Nullable kotlin.coroutines.d<? super x> dVar2) {
            return ((a) create(dVar, dVar2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    f<S, T> fVar = this.this$0;
                    this.label = 1;
                    if (fVar.m((d) this.L$0, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    static /* synthetic */ Object k(f fVar, u scope, kotlin.coroutines.d $completion) {
        Object m = fVar.m(new r(scope), $completion);
        return m == kotlin.coroutines.intrinsics.c.d() ? m : x.a;
    }

    static /* synthetic */ Object j(f fVar, d collector, kotlin.coroutines.d $completion) {
        if (fVar.d == -3) {
            g collectContext = $completion.getContext();
            g newContext = collectContext.plus(fVar.c);
            if (k.a(newContext, collectContext)) {
                Object m = fVar.m(collector, $completion);
                return m == kotlin.coroutines.intrinsics.c.d() ? m : x.a;
            }
            e.b bVar = e.a;
            if (k.a(newContext.get(bVar), collectContext.get(bVar))) {
                Object l = fVar.l(collector, newContext, $completion);
                return l == kotlin.coroutines.intrinsics.c.d() ? l : x.a;
            }
        }
        Object a2 = super.a(collector, $completion);
        return a2 == kotlin.coroutines.intrinsics.c.d() ? a2 : x.a;
    }

    @NotNull
    public String toString() {
        return this.q + " -> " + super.toString();
    }
}
