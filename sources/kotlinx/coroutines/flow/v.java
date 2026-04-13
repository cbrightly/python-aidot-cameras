package kotlinx.coroutines.flow;

import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.internal.f0;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a0\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a6\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00060\r\"\u0004\b\u0000\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00060\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a#\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002¢\u0006\u0002\u0010\u0017\u001a+\u0010\u0018\u001a\u00020\u0019*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u001a\u001a\u0004\u0018\u00010\u0013H\u0002¢\u0006\u0002\u0010\u001b\"\u0016\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003¨\u0006\u001c"}, d2 = {"NO_VALUE", "Lkotlinx/coroutines/internal/Symbol;", "getNO_VALUE$annotations", "()V", "MutableSharedFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "T", "replay", "", "extraBufferCapacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "fuseSharedFlow", "Lkotlinx/coroutines/flow/Flow;", "Lkotlinx/coroutines/flow/SharedFlow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "getBufferAt", "", "", "index", "", "([Ljava/lang/Object;J)Ljava/lang/Object;", "setBufferAt", "", "item", "([Ljava/lang/Object;JLjava/lang/Object;)V", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: SharedFlow.kt */
public final class v {
    @NotNull
    public static final f0 a = new f0("NO_VALUE");

    public static /* synthetic */ p b(int i, int i2, h hVar, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        if ((i3 & 4) != 0) {
            hVar = h.SUSPEND;
        }
        return a(i, i2, hVar);
    }

    @NotNull
    public static final <T> p<T> a(int replay, int extraBufferCapacity, @NotNull h onBufferOverflow) {
        boolean z = true;
        if (replay >= 0) {
            if (extraBufferCapacity >= 0) {
                if (replay <= 0 && extraBufferCapacity <= 0 && onBufferOverflow != h.SUSPEND) {
                    z = false;
                }
                if (z) {
                    int bufferCapacity0 = replay + extraBufferCapacity;
                    return new u(replay, bufferCapacity0 < 0 ? Integer.MAX_VALUE : bufferCapacity0, onBufferOverflow);
                }
                throw new IllegalArgumentException(k.l("replay or extraBufferCapacity must be positive with non-default onBufferOverflow strategy ", onBufferOverflow).toString());
            }
            throw new IllegalArgumentException(k.l("extraBufferCapacity cannot be negative, but was ", Integer.valueOf(extraBufferCapacity)).toString());
        }
        throw new IllegalArgumentException(k.l("replay cannot be negative, but was ", Integer.valueOf(replay)).toString());
    }

    /* access modifiers changed from: private */
    public static final Object f(Object[] $this$getBufferAt, long index) {
        return $this$getBufferAt[((int) index) & ($this$getBufferAt.length - 1)];
    }

    /* access modifiers changed from: private */
    public static final void g(Object[] $this$setBufferAt, long index, Object item) {
        $this$setBufferAt[((int) index) & ($this$setBufferAt.length - 1)] = item;
    }

    @NotNull
    public static final <T> c<T> e(@NotNull t<? extends T> $this$fuseSharedFlow, @NotNull g context, int capacity, @NotNull h onBufferOverflow) {
        if ((capacity == 0 || capacity == -3) && onBufferOverflow == h.SUSPEND) {
            return $this$fuseSharedFlow;
        }
        return new kotlinx.coroutines.flow.internal.g($this$fuseSharedFlow, context, capacity, onBufferOverflow);
    }
}
