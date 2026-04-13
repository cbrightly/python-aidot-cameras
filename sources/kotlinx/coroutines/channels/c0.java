package kotlinx.coroutines.channels;

import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.internal.z;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B9\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u001c\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\bj\b\u0012\u0004\u0012\u00028\u0000`\t¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0006H\u0016R&\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\bj\b\u0012\u0004\u0012\u00028\u0000`\t8\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/channels/SendElementWithUndeliveredHandler;", "E", "Lkotlinx/coroutines/channels/SendElement;", "pollResult", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "onUndeliveredElement", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;Lkotlin/jvm/functions/Function1;)V", "remove", "", "undeliveredElement", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: AbstractChannel.kt */
public final class c0<E> extends b0<E> {
    @NotNull
    public final kotlin.jvm.functions.l<E, x> y;

    public c0(E pollResult, @NotNull n<? super x> cont, @NotNull kotlin.jvm.functions.l<? super E, x> onUndeliveredElement) {
        super(pollResult, cont);
        this.y = onUndeliveredElement;
    }

    public boolean t() {
        if (!super.t()) {
            return false;
        }
        C();
        return true;
    }

    public void C() {
        z.b(this.y, z(), this.x.getContext());
    }
}
