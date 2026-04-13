package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.l0;
import kotlinx.coroutines.o1;
import kotlinx.coroutines.t0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0012\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\rH\u0014¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/channels/ActorCoroutine;", "E", "Lkotlinx/coroutines/channels/ChannelCoroutine;", "Lkotlinx/coroutines/channels/ActorScope;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "channel", "Lkotlinx/coroutines/channels/Channel;", "active", "", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;Z)V", "handleJobException", "exception", "", "onCancelling", "", "cause", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Actor.kt */
public class d<E> extends j<E> implements f<E> {
    public d(@NotNull g parentContext, @NotNull i<E> channel, boolean active) {
        super(parentContext, channel, false, active);
        o0((z1) parentContext.get(z1.g));
    }

    /* access modifiers changed from: protected */
    public void A0(@Nullable Throwable cause) {
        i V0 = V0();
        CancellationException cancellationException = null;
        if (cause != null) {
            Throwable it = cause;
            if (it instanceof CancellationException) {
                cancellationException = (CancellationException) it;
            }
            if (cancellationException == null) {
                cancellationException = o1.a(k.l(t0.a(this), " was cancelled"), it);
            }
        }
        V0.c(cancellationException);
    }

    /* access modifiers changed from: protected */
    public boolean m0(@NotNull Throwable exception) {
        l0.a(getContext(), exception);
        return true;
    }
}
