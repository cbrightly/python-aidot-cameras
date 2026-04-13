package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.g;
import kotlin.l;
import kotlinx.coroutines.channels.h;
import kotlinx.coroutines.flow.c;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002J,\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lkotlinx/coroutines/flow/internal/FusibleFlow;", "T", "Lkotlinx/coroutines/flow/Flow;", "fuse", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ChannelFlow.kt */
public interface k<T> extends c<T> {
    @NotNull
    c<T> b(@NotNull g gVar, int i, @NotNull h hVar);

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: ChannelFlow.kt */
    public static final class a {
        public static /* synthetic */ c a(k kVar, g gVar, int i, h hVar, int i2, Object obj) {
            if (obj == null) {
                if ((i2 & 1) != 0) {
                    gVar = kotlin.coroutines.h.INSTANCE;
                }
                if ((i2 & 2) != 0) {
                    i = -3;
                }
                if ((i2 & 4) != 0) {
                    hVar = h.SUSPEND;
                }
                return kVar.b(gVar, i, hVar);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: fuse");
        }
    }
}
