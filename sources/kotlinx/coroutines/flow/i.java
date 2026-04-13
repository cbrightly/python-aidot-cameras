package kotlinx.coroutines.flow;

import kotlin.coroutines.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.l;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.flow.internal.k;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0015\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0002\b\u0004\u001a(\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a0\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0006\u001a\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0006\u001a$\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u000f"}, d2 = {"checkFlowContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "checkFlowContext$FlowKt__ContextKt", "buffer", "Lkotlinx/coroutines/flow/Flow;", "T", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "cancellable", "conflate", "flowOn", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48)
/* compiled from: Context.kt */
public final /* synthetic */ class i {
    public static /* synthetic */ c b(c cVar, int i, h hVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        if ((i2 & 2) != 0) {
            hVar = h.SUSPEND;
        }
        return e.c(cVar, i, hVar);
    }

    @NotNull
    public static final <T> c<T> a(@NotNull c<? extends T> $this$buffer, int capacity, @NotNull h onBufferOverflow) {
        boolean z = false;
        if (capacity >= 0 || capacity == -2 || capacity == -1) {
            if (capacity != -1 || onBufferOverflow == h.SUSPEND) {
                z = true;
            }
            if (z) {
                int capacity2 = capacity;
                h onBufferOverflow2 = onBufferOverflow;
                if (capacity2 == -1) {
                    capacity2 = 0;
                    onBufferOverflow2 = h.DROP_OLDEST;
                }
                if ($this$buffer instanceof k) {
                    return k.a.a((k) $this$buffer, (g) null, capacity2, onBufferOverflow2, 1, (Object) null);
                }
                return new kotlinx.coroutines.flow.internal.g($this$buffer, (g) null, capacity2, onBufferOverflow2, 2, (DefaultConstructorMarker) null);
            }
            throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
        }
        throw new IllegalArgumentException(kotlin.jvm.internal.k.l("Buffer size should be non-negative, BUFFERED, or CONFLATED, but was ", Integer.valueOf(capacity)).toString());
    }
}
