package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.l;
import kotlinx.coroutines.internal.e0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\bf\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0017J\u0014\u0010\u0014\u001a\u00020\u00042\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0017H'J\u001a\u0010\u0014\u001a\u00020\u00152\u0010\b\u0002\u0010\u0016\u001a\n\u0018\u00010\u0018j\u0004\u0018\u0001`\u0019H&J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001bH¦\u0002J\u000f\u0010\u001c\u001a\u0004\u0018\u00018\u0000H\u0017¢\u0006\u0002\u0010\u001dJ\u0011\u0010\u001e\u001a\u00028\u0000H¦@ø\u0001\u0000¢\u0006\u0002\u0010\u001fJ\"\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH¦@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b!\u0010\u001fJ\u0013\u0010\"\u001a\u0004\u0018\u00018\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u001fJ\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH&ø\u0001\u0000ø\u0001\u0001ø\u0001\u0002¢\u0006\u0004\b$\u0010\u001dR\u001a\u0010\u0003\u001a\u00020\u00048&X§\u0004¢\u0006\f\u0012\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0007R\u001a\u0010\b\u001a\u00020\u00048&X§\u0004¢\u0006\f\u0012\u0004\b\t\u0010\u0006\u001a\u0004\b\b\u0010\u0007R\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR!\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000f0\u000bX¦\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0010\u0010\rR\"\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000b8VX\u0004¢\u0006\f\u0012\u0004\b\u0012\u0010\u0006\u001a\u0004\b\u0013\u0010\r\u0002\u000f\n\u0002\b\u0019\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006%"}, d2 = {"Lkotlinx/coroutines/channels/ReceiveChannel;", "E", "", "isClosedForReceive", "", "isClosedForReceive$annotations", "()V", "()Z", "isEmpty", "isEmpty$annotations", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveCatching", "Lkotlinx/coroutines/channels/ChannelResult;", "getOnReceiveCatching", "onReceiveOrNull", "getOnReceiveOrNull$annotations", "getOnReceiveOrNull", "cancel", "", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "poll", "()Ljava/lang/Object;", "receive", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveCatching", "receiveCatching-JP2dKIU", "receiveOrNull", "tryReceive", "tryReceive-PtdJZtk", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Channel.kt */
public interface w<E> {
    @Nullable
    Object C(@NotNull d<? super E> dVar);

    void c(@Nullable CancellationException cancellationException);

    @Nullable
    E poll();

    @NotNull
    Object q();

    @Nullable
    Object w(@NotNull d<? super E> dVar);

    @Nullable
    Object y(@NotNull d<? super l<? extends E>> dVar);

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Channel.kt */
    public static final class a {

        @l(k = 3, mv = {1, 6, 0}, xi = 48)
        @f(c = "kotlinx.coroutines.channels.ReceiveChannel$DefaultImpls", f = "Channel.kt", l = {354}, m = "receiveOrNull")
        /* renamed from: kotlinx.coroutines.channels.w$a$a  reason: collision with other inner class name */
        /* compiled from: Channel.kt */
        public static final class C0441a<E> extends kotlin.coroutines.jvm.internal.d {
            int label;
            /* synthetic */ Object result;

            C0441a(d<? super C0441a> dVar) {
                super(dVar);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return a.b((w) null, this);
            }
        }

        @Nullable
        public static <E> E a(@NotNull w<? extends E> wVar) {
            Object result = wVar.q();
            if (l.j(result)) {
                return l.g(result);
            }
            Throwable e = l.e(result);
            if (e == null) {
                return null;
            }
            throw e0.k(e);
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static <E> java.lang.Object b(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.w<? extends E> r4, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super E> r5) {
            /*
                boolean r0 = r5 instanceof kotlinx.coroutines.channels.w.a.C0441a
                if (r0 == 0) goto L_0x0013
                r0 = r5
                kotlinx.coroutines.channels.w$a$a r0 = (kotlinx.coroutines.channels.w.a.C0441a) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L_0x0013
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0018
            L_0x0013:
                kotlinx.coroutines.channels.w$a$a r0 = new kotlinx.coroutines.channels.w$a$a
                r0.<init>(r5)
            L_0x0018:
                r5 = r0
                java.lang.Object r0 = r5.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r5.label
                switch(r2) {
                    case 0: goto L_0x0037;
                    case 1: goto L_0x002c;
                    default: goto L_0x0024;
                }
            L_0x0024:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L_0x002c:
                kotlin.p.b(r0)
                r4 = r0
                kotlinx.coroutines.channels.l r4 = (kotlinx.coroutines.channels.l) r4
                java.lang.Object r4 = r4.l()
                goto L_0x0044
            L_0x0037:
                kotlin.p.b(r0)
                r2 = 1
                r5.label = r2
                java.lang.Object r4 = r4.y(r5)
                if (r4 != r1) goto L_0x0044
                return r1
            L_0x0044:
                java.lang.Object r4 = kotlinx.coroutines.channels.l.f(r4)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.w.a.b(kotlinx.coroutines.channels.w, kotlin.coroutines.d):java.lang.Object");
        }
    }
}
