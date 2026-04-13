package kotlinx.coroutines.channels;

import kotlin.l;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.p;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\u0014\u0010\u0016\u001a\u00020\u00122\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0000H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u001f\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00028\u00002\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016¢\u0006\u0002\u0010\u001eJ\u0012\u0010\u001f\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00008VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00008VX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\f\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000e¨\u0006 "}, d2 = {"Lkotlinx/coroutines/channels/Closed;", "E", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "closeCause", "", "(Ljava/lang/Throwable;)V", "offerResult", "getOfferResult", "()Lkotlinx/coroutines/channels/Closed;", "pollResult", "getPollResult", "receiveException", "getReceiveException", "()Ljava/lang/Throwable;", "sendException", "getSendException", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "completeResumeSend", "resumeSendClosed", "closed", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "tryResumeSend", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: AbstractChannel.kt */
public final class o<E> extends z implements x<E> {
    @Nullable
    public final Throwable q;

    public o(@Nullable Throwable closeCause) {
        this.q = closeCause;
    }

    @NotNull
    public final Throwable H() {
        Throwable th = this.q;
        return th == null ? new ClosedSendChannelException("Channel was closed") : th;
    }

    @NotNull
    public final Throwable G() {
        Throwable th = this.q;
        return th == null ? new ClosedReceiveChannelException("Channel was closed") : th;
    }

    @NotNull
    /* renamed from: E */
    public o<E> a() {
        return this;
    }

    @NotNull
    /* renamed from: F */
    public o<E> z() {
        return this;
    }

    @NotNull
    public f0 B(@Nullable s.c otherOp) {
        f0 f0Var = p.a;
        f0 f0Var2 = f0Var;
        if (otherOp != null) {
            otherOp.d();
        }
        return f0Var;
    }

    public void y() {
    }

    @NotNull
    public f0 e(E value, @Nullable s.c otherOp) {
        f0 f0Var = p.a;
        f0 f0Var2 = f0Var;
        if (otherOp != null) {
            otherOp.d();
        }
        return f0Var;
    }

    public void d(E value) {
    }

    public void A(@NotNull o<?> closed) {
        if (s0.a()) {
            throw new AssertionError();
        }
    }

    @NotNull
    public String toString() {
        return "Closed@" + t0.b(this) + '[' + this.q + ']';
    }
}
