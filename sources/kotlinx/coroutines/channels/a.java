package kotlinx.coroutines.channels;

import java.util.ArrayList;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.h;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.channels.i;
import kotlinx.coroutines.internal.f0;
import kotlinx.coroutines.internal.r;
import kotlinx.coroutines.internal.s;
import kotlinx.coroutines.internal.z;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o;
import kotlinx.coroutines.p;
import kotlinx.coroutines.q;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.t0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0007STUVWXYB'\u0012 \u0010\u0004\u001a\u001c\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u0019\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007J\u0016\u0010\u0019\u001a\u00020\u00062\u000e\u0010\u001a\u001a\n\u0018\u00010\u001cj\u0004\u0018\u0001`\u001dJ\u0017\u0010\u001e\u001a\u00020\n2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0000¢\u0006\u0002\b\u001fJ\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0004J\u0016\u0010\"\u001a\u00020\n2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0002J\u0016\u0010%\u001a\u00020\n2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\u0014JR\u0010&\u001a\u00020\n\"\u0004\b\u0001\u0010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2$\u0010*\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+2\u0006\u0010.\u001a\u00020/H\u0002ø\u0001\u0000¢\u0006\u0002\u00100J\u000f\u00101\u001a\b\u0012\u0004\u0012\u00028\u000002H\u0002J\u0010\u00103\u001a\u00020\u00062\u0006\u00104\u001a\u00020\nH\u0014J/\u00105\u001a\u00020\u00062\f\u00106\u001a\b\u0012\u0004\u0012\u000208072\n\u00109\u001a\u0006\u0012\u0002\b\u00030:H\u0014ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b;\u0010<J\b\u0010=\u001a\u00020\u0006H\u0014J\b\u0010>\u001a\u00020\u0006H\u0014J\n\u0010?\u001a\u0004\u0018\u00010,H\u0014J\u0016\u0010@\u001a\u0004\u0018\u00010,2\n\u0010(\u001a\u0006\u0012\u0002\b\u00030)H\u0014J\u0011\u0010#\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010AJ\"\u0010B\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017H@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0002ø\u0001\u0001¢\u0006\u0004\bC\u0010AJ\u001f\u0010D\u001a\u0002H'\"\u0004\b\u0001\u0010'2\u0006\u0010.\u001a\u00020/H@ø\u0001\u0000¢\u0006\u0002\u0010EJR\u0010F\u001a\u00020\u0006\"\u0004\b\u0001\u0010'2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2\u0006\u0010.\u001a\u00020/2$\u0010*\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+H\u0002ø\u0001\u0000¢\u0006\u0002\u0010GJ \u0010H\u001a\u00020\u00062\n\u0010I\u001a\u0006\u0012\u0002\b\u00030J2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0002J\u0010\u0010K\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010LH\u0014J\u001c\u0010M\u001a\b\u0012\u0004\u0012\u00028\u00000\u0017ø\u0001\u0000ø\u0001\u0002ø\u0001\u0001¢\u0006\u0004\bN\u0010OJX\u0010P\u001a\u00020\u0006\"\u0004\b\u0001\u0010'* \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010,\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0-\u0012\u0006\u0012\u0004\u0018\u00010,0+2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H'0)2\u0006\u0010.\u001a\u00020/2\b\u0010Q\u001a\u0004\u0018\u00010,H\u0002ø\u0001\u0000¢\u0006\u0002\u0010RR\u0014\u0010\t\u001a\u00020\n8DX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\r\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0012\u0010\u000e\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\n8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\fR\u0014\u0010\u0010\u001a\u00020\n8VX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\fR\u0014\u0010\u0011\u001a\u00020\n8DX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\fR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R \u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00170\u00138Fø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0015\u0002\u000f\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006Z"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/Channel;", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlin/jvm/functions/Function1;)V", "hasReceiveOrClosed", "", "getHasReceiveOrClosed", "()Z", "isBufferAlwaysEmpty", "isBufferEmpty", "isClosedForReceive", "isEmpty", "isEmptyImpl", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "cancelInternal$kotlinx_coroutines_core", "describeTryPoll", "Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "enqueueReceive", "receive", "Lkotlinx/coroutines/channels/Receive;", "enqueueReceiveInternal", "enqueueReceiveSelect", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)Z", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "onCancelIdempotent", "wasClosed", "onCancelIdempotentList", "list", "Lkotlinx/coroutines/internal/InlineList;", "Lkotlinx/coroutines/channels/Send;", "closed", "Lkotlinx/coroutines/channels/Closed;", "onCancelIdempotentList-w-w6eGU", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/Closed;)V", "onReceiveDequeued", "onReceiveEnqueued", "pollInternal", "pollSelectInternal", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveSuspend", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerSelectReceiveMode", "(Lkotlinx/coroutines/selects/SelectInstance;ILkotlin/jvm/functions/Function2;)V", "removeReceiveOnCancel", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "takeFirstReceiveOrPeekClosed", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "tryReceive", "tryReceive-PtdJZtk", "()Ljava/lang/Object;", "tryStartBlockUnintercepted", "value", "(Lkotlin/jvm/functions/Function2;Lkotlinx/coroutines/selects/SelectInstance;ILjava/lang/Object;)V", "Itr", "ReceiveElement", "ReceiveElementWithUndeliveredHandler", "ReceiveHasNext", "ReceiveSelect", "RemoveReceiveOnCancel", "TryPollDesc", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: AbstractChannel.kt */
public abstract class a<E> extends c<E> implements i<E> {

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.channels.AbstractChannel", f = "AbstractChannel.kt", l = {633}, m = "receiveCatching-JP2dKIU")
    /* compiled from: AbstractChannel.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a<E> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(a<E> aVar, kotlin.coroutines.d<? super e> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            Object y = this.this$0.y(this);
            return y == kotlin.coroutines.intrinsics.c.d() ? y : l.b(y);
        }
    }

    /* access modifiers changed from: protected */
    public abstract boolean L();

    /* access modifiers changed from: protected */
    public abstract boolean M();

    @l(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006H\u0016¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/internal/LockFreeLinkedListNode$makeCondAddOp$1", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "prepare", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: LockFreeLinkedList.kt */
    public static final class d extends s.b {
        final /* synthetic */ s d;
        final /* synthetic */ a e;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(s $node, a aVar) {
            super($node);
            this.d = $node;
            this.e = aVar;
        }

        @Nullable
        /* renamed from: i */
        public Object g(@NotNull s affected) {
            if (this.e.M() != 0) {
                return null;
            }
            return r.a();
        }
    }

    @Nullable
    public E poll() {
        return i.a.a(this);
    }

    @Nullable
    public Object w(@NotNull kotlin.coroutines.d<? super E> $completion) {
        return i.a.b(this, $completion);
    }

    public a(@Nullable kotlin.jvm.functions.l<? super E, x> onUndeliveredElement) {
        super(onUndeliveredElement);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Object S() {
        while (true) {
            z send = E();
            if (send == null) {
                return b.d;
            }
            f0 token = send.B((s.c) null);
            if (token != null) {
                if (s0.a()) {
                    if (!(token == p.a)) {
                        throw new AssertionError();
                    }
                }
                send.y();
                return send.z();
            }
            send.C();
        }
    }

    public boolean N() {
        return h() != null && M();
    }

    @Nullable
    public final Object C(@NotNull kotlin.coroutines.d<? super E> $completion) {
        Object result = S();
        if (result == b.d || (result instanceof o)) {
            return T(0, $completion);
        }
        return result;
    }

    private final <R> Object T(int receiveMode, kotlin.coroutines.d<? super R> $completion) {
        C0440a receive;
        o cancellable$iv = q.b(kotlin.coroutines.intrinsics.b.c($completion));
        n cont = cancellable$iv;
        if (this.d == null) {
            receive = new C0440a(cont, receiveMode);
        } else {
            receive = new b(cont, receiveMode, this.d);
        }
        while (true) {
            if (!J(receive)) {
                Object result = S();
                if (!(result instanceof o)) {
                    if (result != b.d) {
                        cont.h(receive.B(result), receive.z(result));
                        break;
                    }
                } else {
                    receive.A((o) result);
                    break;
                }
            } else {
                U(cont, receive);
                break;
            }
        }
        Object t = cancellable$iv.t();
        if (t == kotlin.coroutines.intrinsics.c.d()) {
            h.c($completion);
        }
        return t;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0031 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean K(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.v<? super E> r10) {
        /*
            r9 = this;
            boolean r0 = r9.L()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0023
            kotlinx.coroutines.internal.q r0 = r9.j()
            r3 = 0
        L_0x000d:
            kotlinx.coroutines.internal.s r4 = r0.p()
            r5 = r4
            r6 = 0
            boolean r7 = r5 instanceof kotlinx.coroutines.channels.z
            r5 = r7 ^ 1
            if (r5 != 0) goto L_0x001b
            goto L_0x0049
        L_0x001b:
            boolean r5 = r4.h(r10, r0)
            if (r5 == 0) goto L_0x000d
            r1 = r2
            goto L_0x0049
        L_0x0023:
            kotlinx.coroutines.internal.q r0 = r9.j()
            r3 = 0
            r4 = r0
            r5 = 0
            kotlinx.coroutines.channels.a$d r6 = new kotlinx.coroutines.channels.a$d
            r6.<init>(r10, r9)
            r4 = r6
        L_0x0031:
            kotlinx.coroutines.internal.s r5 = r0.p()
            r6 = r5
            r7 = 0
            boolean r8 = r6 instanceof kotlinx.coroutines.channels.z
            r6 = r8 ^ 1
            if (r6 != 0) goto L_0x003f
            goto L_0x0049
        L_0x003f:
            int r6 = r5.x(r10, r0, r4)
            switch(r6) {
                case 1: goto L_0x0048;
                case 2: goto L_0x0047;
                default: goto L_0x0046;
            }
        L_0x0046:
            goto L_0x0031
        L_0x0047:
            goto L_0x0049
        L_0x0048:
            r1 = r2
        L_0x0049:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.a.K(kotlinx.coroutines.channels.v):boolean");
    }

    /* access modifiers changed from: private */
    public final boolean J(v<? super E> receive) {
        boolean result = K(receive);
        if (result) {
            R();
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object y(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlinx.coroutines.channels.l<? extends E>> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof kotlinx.coroutines.channels.a.e
            if (r0 == 0) goto L_0x0013
            r0 = r6
            kotlinx.coroutines.channels.a$e r0 = (kotlinx.coroutines.channels.a.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.channels.a$e r0 = new kotlinx.coroutines.channels.a$e
            r0.<init>(r5, r6)
        L_0x0018:
            r6 = r0
            java.lang.Object r0 = r6.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r6.label
            switch(r2) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r0)
            throw r6
        L_0x002c:
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x0060
        L_0x0031:
            kotlin.p.b(r0)
            r2 = r5
            java.lang.Object r3 = r2.S()
            kotlinx.coroutines.internal.f0 r4 = kotlinx.coroutines.channels.b.d
            if (r3 == r4) goto L_0x0056
            r1 = r3
            r2 = 0
            boolean r3 = r1 instanceof kotlinx.coroutines.channels.o
            if (r3 == 0) goto L_0x004f
            kotlinx.coroutines.channels.l$b r3 = kotlinx.coroutines.channels.l.a
            r4 = r1
            kotlinx.coroutines.channels.o r4 = (kotlinx.coroutines.channels.o) r4
            java.lang.Throwable r4 = r4.q
            java.lang.Object r3 = r3.a(r4)
            goto L_0x0055
        L_0x004f:
            kotlinx.coroutines.channels.l$b r3 = kotlinx.coroutines.channels.l.a
            java.lang.Object r3 = r3.c(r1)
        L_0x0055:
            return r3
        L_0x0056:
            r3 = 1
            r6.label = r3
            java.lang.Object r2 = r2.T(r3, r6)
            if (r2 != r1) goto L_0x0060
            return r1
        L_0x0060:
            kotlinx.coroutines.channels.l r2 = (kotlinx.coroutines.channels.l) r2
            java.lang.Object r1 = r2.l()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.a.y(kotlin.coroutines.d):java.lang.Object");
    }

    @NotNull
    public final Object q() {
        Object result = S();
        if (result == b.d) {
            return l.a.b();
        }
        if (result instanceof o) {
            return l.a.a(((o) result).q);
        }
        return l.a.c(result);
    }

    public final void c(@Nullable CancellationException cause) {
        if (!N()) {
            I(cause == null ? new CancellationException(k.l(t0.a(this), " was cancelled")) : cause);
        }
    }

    public final boolean I(@Nullable Throwable cause) {
        boolean it = d(cause);
        O(it);
        return it;
    }

    /* access modifiers changed from: protected */
    public void O(boolean wasClosed) {
        o closed = i();
        if (closed != null) {
            Object list = kotlinx.coroutines.internal.n.b((Object) null, 1, (DefaultConstructorMarker) null);
            while (true) {
                s previous = closed.p();
                if (previous instanceof kotlinx.coroutines.internal.q) {
                    P(list, closed);
                    return;
                } else if (s0.a() && (previous instanceof z) == 0) {
                    throw new AssertionError();
                } else if (!previous.t()) {
                    previous.q();
                } else {
                    list = kotlinx.coroutines.internal.n.c(list, (z) previous);
                }
            }
        } else {
            throw new IllegalStateException("Cannot happen".toString());
        }
    }

    /* access modifiers changed from: protected */
    public void P(@NotNull Object list, @NotNull o<?> closed) {
        if (list != null) {
            if (!(list instanceof ArrayList)) {
                ((z) list).A(closed);
                return;
            }
            ArrayList list$iv = (ArrayList) list;
            int size = list$iv.size() - 1;
            if (size >= 0) {
                do {
                    int i$iv = size;
                    size--;
                    ((z) list$iv.get(i$iv)).A(closed);
                } while (size >= 0);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public x<E> B() {
        x B = super.B();
        x it = B;
        if (it != null && !(it instanceof o)) {
            Q();
        }
        return B;
    }

    /* access modifiers changed from: protected */
    public void R() {
    }

    /* access modifiers changed from: protected */
    public void Q() {
    }

    /* access modifiers changed from: private */
    public final void U(n<?> cont, v<?> receive) {
        cont.f(new c(receive));
    }

    @l(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$RemoveReceiveOnCancel;", "Lkotlinx/coroutines/BeforeResumeCancelHandler;", "receive", "Lkotlinx/coroutines/channels/Receive;", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/channels/Receive;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: AbstractChannel.kt */
    public final class c extends kotlinx.coroutines.e {
        @NotNull
        private final v<?> c;

        public c(@NotNull v<?> receive) {
            this.c = receive;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            a((Throwable) p1);
            return x.a;
        }

        public void a(@Nullable Throwable cause) {
            if (this.c.t()) {
                a.this.Q();
            }
        }

        @NotNull
        public String toString() {
            return "RemoveReceiveOnCancel[" + this.c + ']';
        }
    }

    @l(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0012\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\u00020\n2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0016J\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00028\u0001¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016J!\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u000b\u001a\u00028\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016¢\u0006\u0002\u0010\u0018R\u0018\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "E", "Lkotlinx/coroutines/channels/Receive;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "(Lkotlinx/coroutines/CancellableContinuation;I)V", "completeResumeReceive", "", "value", "(Ljava/lang/Object;)V", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "resumeValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "tryResumeReceive", "Lkotlinx/coroutines/internal/Symbol;", "otherOp", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListNode$PrepareOp;)Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* renamed from: kotlinx.coroutines.channels.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractChannel.kt */
    public static class C0440a<E> extends v<E> {
        @NotNull
        public final n<Object> q;
        public final int x;

        public C0440a(@NotNull n<Object> cont, int receiveMode) {
            this.q = cont;
            this.x = receiveMode;
        }

        @Nullable
        public final Object B(E value) {
            if (this.x == 1) {
                return l.b(l.a.c(value));
            }
            return value;
        }

        @Nullable
        public f0 e(E value, @Nullable s.c otherOp) {
            Object token = this.q.A(B(value), otherOp == null ? null : otherOp.a, z(value));
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

        public void d(E value) {
            this.q.G(p.a);
        }

        public void A(@NotNull o<?> closed) {
            if (this.x == 1) {
                n<Object> nVar = this.q;
                o.a aVar = kotlin.o.Companion;
                nVar.resumeWith(kotlin.o.m17constructorimpl(l.b(l.a.a(closed.q))));
                return;
            }
            n<Object> nVar2 = this.q;
            o.a aVar2 = kotlin.o.Companion;
            nVar2.resumeWith(kotlin.o.m17constructorimpl(kotlin.p.a(closed.G())));
        }

        @NotNull
        public String toString() {
            return "ReceiveElement@" + t0.b(this) + "[receiveMode=" + this.x + ']';
        }
    }

    @l(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B;\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u001c\u0010\b\u001a\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00028\u0001`\u000b¢\u0006\u0002\u0010\fJ#\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u0006\u0010\u000f\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0010R&\u0010\b\u001a\u0018\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00028\u0001`\u000b8\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElementWithUndeliveredHandler;", "E", "Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "onUndeliveredElement", "Lkotlin/Function1;", "", "Lkotlinx/coroutines/internal/OnUndeliveredElement;", "(Lkotlinx/coroutines/CancellableContinuation;ILkotlin/jvm/functions/Function1;)V", "resumeOnCancellationFun", "", "value", "(Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: AbstractChannel.kt */
    public static final class b<E> extends C0440a<E> {
        @NotNull
        public final kotlin.jvm.functions.l<E, x> y;

        public b(@NotNull n<Object> cont, int receiveMode, @NotNull kotlin.jvm.functions.l<? super E, x> onUndeliveredElement) {
            super(cont, receiveMode);
            this.y = onUndeliveredElement;
        }

        @Nullable
        public kotlin.jvm.functions.l<Throwable, x> z(E value) {
            return z.a(this.y, value, this.q.getContext());
        }
    }
}
