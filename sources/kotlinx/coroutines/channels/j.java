package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.intrinsics.c;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.JobCancellationException;
import kotlinx.coroutines.a;
import kotlinx.coroutines.g2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0004B+\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\b\u0010\"\u001a\u00020\u0003H\u0016J\u0012\u0010\"\u001a\u00020\t2\b\u0010#\u001a\u0004\u0018\u00010$H\u0007J\u0016\u0010\"\u001a\u00020\u00032\u000e\u0010#\u001a\n\u0018\u00010%j\u0004\u0018\u0001`&J\u0010\u0010'\u001a\u00020\u00032\u0006\u0010#\u001a\u00020$H\u0016J\u0013\u0010(\u001a\u00020\t2\b\u0010#\u001a\u0004\u0018\u00010$H\u0001J.\u0010)\u001a\u00020\u00032#\u0010*\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010$¢\u0006\f\b,\u0012\b\b-\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u00030+H\u0001J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00000/H\u0003J\u0016\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00028\u0000H\u0001¢\u0006\u0002\u00102J\u0010\u00103\u001a\u0004\u0018\u00018\u0000H\u0001¢\u0006\u0002\u00104J\u0011\u00105\u001a\u00028\u0000HAø\u0001\u0000¢\u0006\u0002\u00106J\"\u00107\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019HAø\u0001\u0000ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b8\u00106J\u0013\u00109\u001a\u0004\u0018\u00018\u0000HAø\u0001\u0000¢\u0006\u0002\u00106J\u0019\u0010:\u001a\u00020\u00032\u0006\u00101\u001a\u00028\u0000HAø\u0001\u0000¢\u0006\u0002\u0010;J\u001f\u0010<\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019H\u0001ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b=\u00104J'\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00030\u00192\u0006\u00101\u001a\u00028\u0000H\u0001ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b?\u0010@R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0014\u0010\u0010\u001a\u00020\t8\u0016X\u0005¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\t8\u0016X\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0013\u001a\u00020\t8\u0016X\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0011R\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015X\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R!\u0010\u0018\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00190\u0015X\u0005ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0017R\u001c\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u00158VX\u0005¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0017R$\u0010\u001d\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001f0\u001eX\u0005¢\u0006\u0006\u001a\u0004\b \u0010!\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006A"}, d2 = {"Lkotlinx/coroutines/channels/ChannelCoroutine;", "E", "Lkotlinx/coroutines/AbstractCoroutine;", "", "Lkotlinx/coroutines/channels/Channel;", "parentContext", "Lkotlin/coroutines/CoroutineContext;", "_channel", "initParentJob", "", "active", "(Lkotlin/coroutines/CoroutineContext;Lkotlinx/coroutines/channels/Channel;ZZ)V", "get_channel", "()Lkotlinx/coroutines/channels/Channel;", "channel", "getChannel", "isClosedForReceive", "()Z", "isClosedForSend", "isEmpty", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "onReceiveOrNull", "getOnReceiveOrNull", "onSend", "Lkotlinx/coroutines/selects/SelectClause2;", "Lkotlinx/coroutines/channels/SendChannel;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "close", "invokeOnClose", "handler", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "offer", "element", "(Ljava/lang/Object;)Z", "poll", "()Ljava/lang/Object;", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveOrNull", "send", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryReceive", "tryReceive-PtdJZtk", "trySend", "trySend-JP2dKIU", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: ChannelCoroutine.kt */
public class j<E> extends a<x> implements i<E> {
    @NotNull
    private final i<E> f;

    @Nullable
    public Object C(@NotNull d<? super E> dVar) {
        return this.f.C(dVar);
    }

    @Nullable
    public Object D(E e, @NotNull d<? super x> dVar) {
        return this.f.D(e, dVar);
    }

    public boolean F() {
        return this.f.F();
    }

    public boolean d(@Nullable Throwable th) {
        return this.f.d(th);
    }

    public void k(@NotNull kotlin.jvm.functions.l<? super Throwable, x> lVar) {
        this.f.k(lVar);
    }

    public boolean offer(E e) {
        return this.f.offer(e);
    }

    @NotNull
    public Object p(E e) {
        return this.f.p(e);
    }

    @Nullable
    public E poll() {
        return this.f.poll();
    }

    @NotNull
    public Object q() {
        return this.f.q();
    }

    @Nullable
    public Object w(@NotNull d<? super E> dVar) {
        return this.f.w(dVar);
    }

    @Nullable
    public Object y(@NotNull d<? super l<? extends E>> dVar) {
        Object y = this.f.y(dVar);
        Object d = c.d();
        return y;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final i<E> V0() {
        return this.f;
    }

    public j(@NotNull g parentContext, @NotNull i<E> _channel, boolean initParentJob, boolean active) {
        super(parentContext, initParentJob, active);
        this.f = _channel;
    }

    @NotNull
    public final i<E> getChannel() {
        return this;
    }

    public final void c(@Nullable CancellationException cause) {
        CancellationException cancellationException;
        if (!isCancelled()) {
            if (cause == null) {
                cancellationException = new JobCancellationException(X(), (Throwable) null, this);
            } else {
                cancellationException = cause;
            }
            U(cancellationException);
        }
    }

    public void U(@NotNull Throwable cause) {
        CancellationException exception = g2.K0(this, cause, (String) null, 1, (Object) null);
        this.f.c(exception);
        R(exception);
    }
}
