package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.channels.a0;
import kotlinx.coroutines.internal.UndeliveredElementException;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.internal.q;
import kotlinx.coroutines.internal.r;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.internal.z;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import kotlinx.coroutines.p;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u000006:\u0004defgB)\u0012 \u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016¢\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\u000e\u001a\u00020\rH\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ#\u0010\u0013\u001a\u000e\u0012\u0002\b\u00030\u0011j\u0006\u0012\u0002\b\u0003`\u00122\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0013\u0010\u0014J\u001d\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00152\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0016\u0010\u0017J\u0019\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u0019\u001a\u00020\u0018H\u0014¢\u0006\u0004\b\u001b\u0010\u001cJ\u001b\u0010\u001f\u001a\u00020\u00032\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b\u001f\u0010 J#\u0010!\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b!\u0010\"J\u001b\u0010!\u001a\u00020\b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\b!\u0010#J)\u0010&\u001a\u00020\u00032\u0018\u0010%\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\u00030\u0002j\u0002`$H\u0016¢\u0006\u0004\b&\u0010\u0007J\u0019\u0010'\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0002¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0004\b)\u0010*J\u0017\u0010+\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00028\u0000H\u0014¢\u0006\u0004\b+\u0010,J#\u0010/\u001a\u00020\u001a2\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010.\u001a\u0006\u0012\u0002\b\u00030-H\u0014¢\u0006\u0004\b/\u00100J\u0017\u00102\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u000201H\u0014¢\u0006\u0004\b2\u00103JX\u00109\u001a\u00020\u0003\"\u0004\b\u0001\u001042\f\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00010-2\u0006\u0010\u0010\u001a\u00028\u00002(\u00108\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000006\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000107\u0012\u0006\u0012\u0004\u0018\u00010\u001a05H\u0002ø\u0001\u0000¢\u0006\u0004\b9\u0010:J\u001b\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b\u0019\u0010;J\u001d\u0010=\u001a\b\u0012\u0002\b\u0003\u0018\u00010<2\u0006\u0010\u0010\u001a\u00028\u0000H\u0004¢\u0006\u0004\b=\u0010>J\u001b\u0010?\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b?\u0010;J\u0017\u0010@\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010<H\u0014¢\u0006\u0004\b@\u0010AJ\u0011\u0010B\u001a\u0004\u0018\u00010\u0018H\u0004¢\u0006\u0004\bB\u0010CJ\u000f\u0010E\u001a\u00020DH\u0016¢\u0006\u0004\bE\u0010FJ$\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00030G2\u0006\u0010\u0010\u001a\u00028\u0000ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\bH\u0010,J+\u0010J\u001a\u00020\u0003*\u0006\u0012\u0002\b\u0003072\u0006\u0010\u0010\u001a\u00028\u00002\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001dH\u0002¢\u0006\u0004\bJ\u0010KR\u0014\u0010M\u001a\u00020D8TX\u0004¢\u0006\u0006\u001a\u0004\bL\u0010FR\u001a\u0010P\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d8DX\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u001a\u0010R\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001d8DX\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010OR\u0014\u0010S\u001a\u00020\n8$X¤\u0004¢\u0006\u0006\u001a\u0004\bS\u0010TR\u0014\u0010U\u001a\u00020\n8$X¤\u0004¢\u0006\u0006\u001a\u0004\bU\u0010TR\u0011\u0010V\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\bV\u0010TR\u0014\u0010W\u001a\u00020\n8BX\u0004¢\u0006\u0006\u001a\u0004\bW\u0010TR#\u0010[\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u0000060X8F¢\u0006\u0006\u001a\u0004\bY\u0010ZR.\u0010\u0005\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0002j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u00048\u0004X\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\\R\u001a\u0010^\u001a\u00020]8\u0004X\u0004¢\u0006\f\n\u0004\b^\u0010_\u001a\u0004\b`\u0010aR\u0014\u0010c\u001a\u00020D8BX\u0004¢\u0006\u0006\u001a\u0004\bb\u0010F\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006h"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel;", "E", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "onUndeliveredElement", "<init>", "(Lkotlin/jvm/functions/Function1;)V", "", "cause", "", "close", "(Ljava/lang/Throwable;)Z", "", "countQueueSize", "()I", "element", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/AddLastDesc;", "describeSendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "describeTryOffer", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "closed", "helpClose", "(Lkotlinx/coroutines/channels/Closed;)V", "helpCloseAndGetSendException", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "(Lkotlinx/coroutines/channels/Closed;)Ljava/lang/Throwable;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "offer", "(Ljava/lang/Object;)Z", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onClosedIdempotent", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/SendChannel;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendSuspend", "takeFirstReceiveOrPeekClosed", "()Lkotlinx/coroutines/channels/ReceiveOrClosed;", "takeFirstSendOrPeekClosed", "()Lkotlinx/coroutines/channels/Send;", "", "toString", "()Ljava/lang/String;", "Lkotlinx/coroutines/channels/ChannelResult;", "trySend-JP2dKIU", "trySend", "helpCloseAndResumeWithSendException", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "getBufferDebugString", "bufferDebugString", "getClosedForReceive", "()Lkotlinx/coroutines/channels/Closed;", "closedForReceive", "getClosedForSend", "closedForSend", "isBufferAlwaysFull", "()Z", "isBufferFull", "isClosedForSend", "isFullImpl", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueue", "()Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueueDebugStateString", "queueDebugStateString", "SendBuffered", "SendBufferedDesc", "SendSelect", "TryOfferDesc", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: AbstractChannel.kt */
public abstract class c<E> implements a0<E> {
    private static final /* synthetic */ AtomicReferenceFieldUpdater c = AtomicReferenceFieldUpdater.newUpdater(c.class, Object.class, "onCloseHandler");
    @Nullable
    protected final kotlin.jvm.functions.l<E, x> d;
    @NotNull
    private final q f = new q();
    @NotNull
    private volatile /* synthetic */ Object onCloseHandler = null;

    /* access modifiers changed from: protected */
    public abstract boolean s();

    /* access modifiers changed from: protected */
    public abstract boolean t();

    public c(@Nullable kotlin.jvm.functions.l<? super E, x> onUndeliveredElement) {
        this.d = onUndeliveredElement;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final q j() {
        return this.f;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Object v(E element) {
        x receive;
        f0 token;
        do {
            receive = B();
            if (receive == null) {
                return b.c;
            }
            token = receive.e(element, (s.c) null);
        } while (token == null);
        if (s0.a()) {
            if (!(token == p.a)) {
                throw new AssertionError();
            }
        }
        receive.d(element);
        return receive.a();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final o<?> i() {
        s p = this.f.p();
        o it = p instanceof o ? (o) p : null;
        if (it == null) {
            return null;
        }
        m(it);
        return it;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final o<?> h() {
        s n = this.f.n();
        o it = n instanceof o ? (o) n : null;
        if (it == null) {
            return null;
        }
        m(it);
        return it;
    }

    @l(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006H\u0016¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/internal/LockFreeLinkedListNode$makeCondAddOp$1", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "prepare", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: LockFreeLinkedList.kt */
    public static final class b extends s.b {
        final /* synthetic */ s d;
        final /* synthetic */ c e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(s $node, c cVar) {
            super($node);
            this.d = $node;
            this.e = cVar;
        }

        @Nullable
        /* renamed from: i */
        public Object g(@NotNull s affected) {
            if (this.e.t() != 0) {
                return null;
            }
            return r.a();
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final z E() {
        s first$iv;
        s next$iv;
        s this_$iv = this.f;
        while (true) {
            first$iv = (s) this_$iv.m();
            if (first$iv != this_$iv) {
                if (first$iv instanceof z) {
                    if (((((z) first$iv) instanceof o) && !first$iv.s()) || (next$iv = first$iv.v()) == null) {
                        break;
                    }
                    next$iv.r();
                } else {
                    first$iv = null;
                    break;
                }
            } else {
                first$iv = null;
                break;
            }
        }
        return (z) first$iv;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final x<?> z(E element) {
        s prev$iv;
        s this_$iv = this.f;
        s node$iv = new a(element);
        do {
            prev$iv = this_$iv.p();
            s prev = prev$iv;
            if (prev instanceof x) {
                return (x) prev;
            }
        } while (!prev$iv.h(node$iv, this_$iv));
        return null;
    }

    public final boolean F() {
        return i() != null;
    }

    /* access modifiers changed from: private */
    public final boolean u() {
        return !(this.f.n() instanceof x) && t();
    }

    @Nullable
    public final Object D(E element, @NotNull d<? super x> $completion) {
        if (v(element) == b.b) {
            return x.a;
        }
        Object A = A(element, $completion);
        return A == kotlin.coroutines.intrinsics.c.d() ? A : x.a;
    }

    public boolean offer(E element) {
        UndeliveredElementException it;
        try {
            return a0.a.b(this, element);
        } catch (Throwable e) {
            kotlin.jvm.functions.l<E, x> lVar = this.d;
            if (lVar == null || (it = z.d(lVar, element, (UndeliveredElementException) null, 2, (Object) null)) == null) {
                throw e;
            }
            kotlin.b.a(it, e);
            throw it;
        }
    }

    @NotNull
    public final Object p(E element) {
        Object result = v(element);
        if (result == b.b) {
            return l.a.c(x.a);
        }
        if (result == b.c) {
            o closedForSend = i();
            if (closedForSend == null) {
                return l.a.b();
            }
            return l.a.a(n(closedForSend));
        } else if (result instanceof o) {
            return l.a.a(n((o) result));
        } else {
            throw new IllegalStateException(k.l("trySend returned ", result).toString());
        }
    }

    private final Throwable n(o<?> closed) {
        m(closed);
        return closed.H();
    }

    private final Object A(E element, d<? super x> $completion) {
        b0 send;
        o cancellable$iv = kotlinx.coroutines.q.b(kotlin.coroutines.intrinsics.b.c($completion));
        n cont = cancellable$iv;
        while (true) {
            if (u()) {
                if (this.d == null) {
                    send = new b0(element, cont);
                } else {
                    send = new c0(element, cont, this.d);
                }
                Object enqueueResult = f(send);
                if (enqueueResult == null) {
                    kotlinx.coroutines.q.c(cont, send);
                    break;
                } else if (enqueueResult instanceof o) {
                    o(cont, element, (o) enqueueResult);
                    break;
                } else if (enqueueResult != b.e && !(enqueueResult instanceof v)) {
                    throw new IllegalStateException(k.l("enqueueSend returned ", enqueueResult).toString());
                }
            }
            Object offerResult = v(element);
            if (offerResult == b.b) {
                o.a aVar = kotlin.o.Companion;
                cont.resumeWith(kotlin.o.m17constructorimpl(x.a));
                break;
            } else if (offerResult != b.c) {
                if (offerResult instanceof o) {
                    o(cont, element, (o) offerResult);
                } else {
                    throw new IllegalStateException(k.l("offerInternal returned ", offerResult).toString());
                }
            }
        }
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return t == kotlin.coroutines.intrinsics.c.d() ? t : x.a;
    }

    /* access modifiers changed from: private */
    public final void o(d<?> $this$helpCloseAndResumeWithSendException, E element, o<?> closed) {
        UndeliveredElementException it;
        m(closed);
        Throwable sendException = closed.H();
        kotlin.jvm.functions.l<E, x> lVar = this.d;
        if (lVar == null || (it = z.d(lVar, element, (UndeliveredElementException) null, 2, (Object) null)) == null) {
            o.a aVar = kotlin.o.Companion;
            $this$helpCloseAndResumeWithSendException.resumeWith(kotlin.o.m17constructorimpl(kotlin.p.a(sendException)));
            return;
        }
        kotlin.b.a(it, sendException);
        o.a aVar2 = kotlin.o.Companion;
        $this$helpCloseAndResumeWithSendException.resumeWith(kotlin.o.m17constructorimpl(kotlin.p.a(it)));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0029 A[LOOP_START] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object f(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.z r8) {
        /*
            r7 = this;
            boolean r0 = r7.s()
            if (r0 == 0) goto L_0x001d
            kotlinx.coroutines.internal.q r0 = r7.f
            r1 = 0
        L_0x0009:
            kotlinx.coroutines.internal.s r2 = r0.p()
            r3 = r2
            r4 = 0
            boolean r5 = r3 instanceof kotlinx.coroutines.channels.x
            if (r5 == 0) goto L_0x0015
            return r3
        L_0x0015:
            boolean r3 = r2.h(r8, r0)
            if (r3 == 0) goto L_0x0009
            goto L_0x0046
        L_0x001d:
            kotlinx.coroutines.internal.q r0 = r7.f
            r1 = 0
            r2 = r0
            r3 = 0
            kotlinx.coroutines.channels.c$b r4 = new kotlinx.coroutines.channels.c$b
            r4.<init>(r8, r7)
            r2 = r4
        L_0x0029:
            kotlinx.coroutines.internal.s r3 = r0.p()
            r4 = r3
            r5 = 0
            boolean r6 = r4 instanceof kotlinx.coroutines.channels.x
            if (r6 == 0) goto L_0x0035
            return r4
        L_0x0035:
            int r4 = r3.x(r8, r0, r2)
            switch(r4) {
                case 1: goto L_0x0040;
                case 2: goto L_0x003e;
                default: goto L_0x003d;
            }
        L_0x003d:
            goto L_0x0029
        L_0x003e:
            r4 = 0
            goto L_0x0041
        L_0x0040:
            r4 = 1
        L_0x0041:
            if (r4 != 0) goto L_0x0046
            kotlinx.coroutines.internal.f0 r0 = kotlinx.coroutines.channels.b.e
            return r0
        L_0x0046:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.c.f(kotlinx.coroutines.channels.z):java.lang.Object");
    }

    public boolean d(@Nullable Throwable cause) {
        boolean z;
        o closed = new o(cause);
        s this_$iv = this.f;
        while (true) {
            s prev$iv = this_$iv.p();
            z = true;
            if (!(prev$iv instanceof o)) {
                if (prev$iv.h(closed, this_$iv)) {
                    break;
                }
            } else {
                z = false;
                break;
            }
        }
        boolean closeAdded = z;
        m(closeAdded ? closed : (o) this.f.p());
        if (closeAdded) {
            r(cause);
        }
        return closeAdded;
    }

    private final void r(Throwable cause) {
        f0 f0Var;
        Object handler = this.onCloseHandler;
        if (handler != null && handler != (f0Var = b.f) && c.compareAndSet(this, handler, f0Var)) {
            ((kotlin.jvm.functions.l) e0.e(handler, 1)).invoke(cause);
        }
    }

    public void k(@NotNull kotlin.jvm.functions.l<? super Throwable, x> handler) {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = c;
        if (!atomicReferenceFieldUpdater.compareAndSet(this, (Object) null, handler)) {
            Object value = this.onCloseHandler;
            if (value == b.f) {
                throw new IllegalStateException("Another handler was already registered and successfully invoked");
            }
            throw new IllegalStateException(k.l("Another handler was already registered: ", value));
        }
        o closedToken = i();
        if (closedToken != null && atomicReferenceFieldUpdater.compareAndSet(this, handler, b.f)) {
            handler.invoke(closedToken.q);
        }
    }

    private final void m(o<?> closed) {
        Object closedList = kotlinx.coroutines.internal.n.b((Object) null, 1, (DefaultConstructorMarker) null);
        while (true) {
            s p = closed.p();
            v previous = p instanceof v ? (v) p : null;
            if (previous == null) {
                break;
            } else if (!previous.t()) {
                previous.q();
            } else {
                closedList = kotlinx.coroutines.internal.n.c(closedList, previous);
            }
        }
        if (closedList != null) {
            if (!(closedList instanceof ArrayList)) {
                ((v) closedList).A(closed);
            } else {
                ArrayList list$iv = (ArrayList) closedList;
                int size = list$iv.size() - 1;
                if (size >= 0) {
                    do {
                        int i$iv = size;
                        size--;
                        ((v) list$iv.get(i$iv)).A(closed);
                    } while (size >= 0);
                }
            }
        }
        x(closed);
    }

    /* access modifiers changed from: protected */
    public void x(@NotNull s closed) {
    }

    /* access modifiers changed from: protected */
    @Nullable
    public x<E> B() {
        s first$iv;
        s next$iv;
        s this_$iv = this.f;
        while (true) {
            first$iv = (s) this_$iv.m();
            if (first$iv != this_$iv) {
                if (first$iv instanceof x) {
                    if (((((x) first$iv) instanceof o) && !first$iv.s()) || (next$iv = first$iv.v()) == null) {
                        break;
                    }
                    next$iv.r();
                } else {
                    first$iv = null;
                    break;
                }
            } else {
                first$iv = null;
                break;
            }
        }
        return (x) first$iv;
    }

    @NotNull
    public String toString() {
        return t0.a(this) + '@' + t0.b(this) + '{' + l() + '}' + g();
    }

    private final String l() {
        String result;
        s head = this.f.n();
        if (head == this.f) {
            return "EmptyQueue";
        }
        if (head instanceof o) {
            result = head.toString();
        } else if (head instanceof v) {
            result = "ReceiveQueued";
        } else if (head instanceof z) {
            result = "SendQueued";
        } else {
            result = k.l("UNEXPECTED:", head);
        }
        s tail = this.f.p();
        if (tail == head) {
            return result;
        }
        String result2 = result + ",queueSize=" + e();
        if (!(tail instanceof o)) {
            return result2;
        }
        return result2 + ",closedForSend=" + tail;
    }

    private final int e() {
        int size = 0;
        q this_$iv = this.f;
        for (s cur$iv = (s) this_$iv.m(); !k.a(cur$iv, this_$iv); cur$iv = cur$iv.n()) {
            if (cur$iv instanceof s) {
                s sVar = cur$iv;
                size++;
            }
        }
        return size;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public String g() {
        return "";
    }

    @l(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0012\u0010\u0003\u001a\u00028\u00018\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "E", "Lkotlinx/coroutines/channels/Send;", "element", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: AbstractChannel.kt */
    public static final class a<E> extends z {
        public final E q;

        public a(E element) {
            this.q = element;
        }

        @Nullable
        public Object z() {
            return this.q;
        }

        @Nullable
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

        public void A(@NotNull o<?> closed) {
            if (s0.a()) {
                throw new AssertionError();
            }
        }

        @NotNull
        public String toString() {
            return "SendBuffered@" + t0.b(this) + '(' + this.q + ')';
        }
    }
}
