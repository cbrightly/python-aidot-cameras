package kotlinx.coroutines.flow.internal;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.flow.c;
import kotlinx.coroutines.flow.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00010\u0002B1\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ&\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0014J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0016J\u001f\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012H@ø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlowOperatorImpl;", "T", "Lkotlinx/coroutines/flow/internal/ChannelFlowOperator;", "flow", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;)V", "create", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "dropChannelOperators", "flowCollect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ChannelFlow.kt */
public final class g<T> extends f<T, T> {
    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ g(c cVar, kotlin.coroutines.g gVar, int i, h hVar, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(cVar, (i2 & 2) != 0 ? kotlin.coroutines.h.INSTANCE : gVar, (i2 & 4) != 0 ? -3 : i, (i2 & 8) != 0 ? h.SUSPEND : hVar);
    }

    public g(@NotNull c<? extends T> flow, @NotNull kotlin.coroutines.g context, int capacity, @NotNull h onBufferOverflow) {
        super(flow, context, capacity, onBufferOverflow);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public d<T> f(@NotNull kotlin.coroutines.g context, int capacity, @NotNull h onBufferOverflow) {
        return new g(this.q, context, capacity, onBufferOverflow);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object m(@NotNull d<? super T> collector, @NotNull kotlin.coroutines.d<? super x> $completion) {
        Object a = this.q.a(collector, $completion);
        return a == kotlin.coroutines.intrinsics.c.d() ? a : x.a;
    }
}
