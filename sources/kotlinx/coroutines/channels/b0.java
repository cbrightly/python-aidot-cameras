package kotlinx.coroutines.channels;

import kotlin.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.n;
import kotlinx.coroutines.p;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\u0006H\u0016J\u0014\u0010\f\u001a\u00020\u00062\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0003\u001a\u00028\u0000X\u0004¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/channels/SendElement;", "E", "Lkotlinx/coroutines/channels/Send;", "pollResult", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)V", "getPollResult", "()Ljava/lang/Object;", "Ljava/lang/Object;", "completeResumeSend", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: AbstractChannel.kt */
public class b0<E> extends z {
    private final E q;
    @NotNull
    public final n<x> x;

    public E z() {
        return this.q;
    }

    public b0(E pollResult, @NotNull n<? super x> cont) {
        this.q = pollResult;
        this.x = cont;
    }

    @Nullable
    public f0 B(@Nullable s.c otherOp) {
        Object token = this.x.e(x.a, otherOp == null ? null : otherOp.a);
        if (token == null) {
            return null;
        }
        if (s0.a()) {
            if (!(token == p.a)) {
                throw new AssertionError();
            }
        }
        if (otherOp != null) {
            otherOp.d();
        }
        return p.a;
    }

    public void y() {
        this.x.G(p.a);
    }

    public void A(@NotNull o<?> closed) {
        n<x> nVar = this.x;
        o.a aVar = o.Companion;
        nVar.resumeWith(o.m17constructorimpl(kotlin.p.a(closed.H())));
    }

    @NotNull
    public String toString() {
        return t0.a(this) + '@' + t0.b(this) + '(' + z() + ')';
    }
}
