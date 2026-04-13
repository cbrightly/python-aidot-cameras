package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.flow.d;
import kotlinx.coroutines.internal.j0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0019\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0010R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R/\u0010\t\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\b0\nX\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\r\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/flow/internal/UndispatchedContextCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "downstream", "emitContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/CoroutineContext;)V", "countOrElement", "", "emitRef", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/jvm/functions/Function2;", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ChannelFlow.kt */
public final class u<T> implements d<T> {
    @NotNull
    private final g c;
    @NotNull
    private final Object d;
    @NotNull
    private final p<T, kotlin.coroutines.d<? super x>, Object> f;

    public u(@NotNull d<? super T> downstream, @NotNull g emitContext) {
        this.c = emitContext;
        this.d = j0.b(emitContext);
        this.f = new a(downstream, (kotlin.coroutines.d<? super a>) null);
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@"}, d2 = {"<anonymous>", "", "T", "it"}, k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.internal.UndispatchedContextCollector$emitRef$1", f = "ChannelFlow.kt", l = {212}, m = "invokeSuspend")
    /* compiled from: ChannelFlow.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.l implements p<T, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ d<T> $downstream;
        /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(d<? super T> dVar, kotlin.coroutines.d<? super a> dVar2) {
            super(2, dVar2);
            this.$downstream = dVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            a aVar = new a(this.$downstream, dVar);
            aVar.L$0 = obj;
            return aVar;
        }

        @Nullable
        public final Object invoke(T t, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((a) create(t, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    Object it = this.L$0;
                    d<T> dVar = this.$downstream;
                    this.label = 1;
                    if (dVar.emit(it, this) != d) {
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

    @Nullable
    public Object emit(T value, @NotNull kotlin.coroutines.d<? super x> $completion) {
        Object b = e.b(this.c, value, this.d, this.f, $completion);
        return b == c.d() ? b : x.a;
    }
}
