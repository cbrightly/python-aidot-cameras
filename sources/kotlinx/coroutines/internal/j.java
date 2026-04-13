package kotlinx.coroutines.internal;

import kotlin.coroutines.d;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.b1;
import kotlinx.coroutines.j1;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.x2;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a;\u0010\u0006\u001a\u00020\u0007*\u0006\u0012\u0002\b\u00030\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u00072\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\b\u001aU\u0010\u0011\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0012*\b\u0012\u0004\u0012\u0002H\u00120\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00120\u00152%\b\u0002\u0010\u0016\u001a\u001f\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0017H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a\u0012\u0010\u001d\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00100\bH\u0000\"\u0016\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\u00020\u00018\u0002X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0003\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, d2 = {"REUSABLE_CLAIMED", "Lkotlinx/coroutines/internal/Symbol;", "getREUSABLE_CLAIMED$annotations", "()V", "UNDEFINED", "getUNDEFINED$annotations", "executeUnconfined", "", "Lkotlinx/coroutines/internal/DispatchedContinuation;", "contState", "", "mode", "", "doYield", "block", "Lkotlin/Function0;", "", "resumeCancellableWith", "T", "Lkotlin/coroutines/Continuation;", "result", "Lkotlin/Result;", "onCancellation", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "yieldUndispatched", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: DispatchedContinuation.kt */
public final class j {
    /* access modifiers changed from: private */
    @NotNull
    public static final f0 a = new f0("UNDEFINED");
    @NotNull
    public static final f0 b = new f0("REUSABLE_CLAIMED");

    public static /* synthetic */ void c(d dVar, Object obj, kotlin.jvm.functions.l lVar, int i, Object obj2) {
        if ((i & 2) != 0) {
            lVar = null;
        }
        b(dVar, obj, lVar);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00dc, code lost:
        if (r17.W0() != false) goto L_0x00de;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00fd A[Catch:{ all -> 0x00e3, all -> 0x010c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> void b(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super T> r22, @org.jetbrains.annotations.NotNull java.lang.Object r23, @org.jetbrains.annotations.Nullable kotlin.jvm.functions.l<? super java.lang.Throwable, kotlin.x> r24) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.internal.i
            if (r0 == 0) goto L_0x012a
            r2 = r1
            kotlinx.coroutines.internal.i r2 = (kotlinx.coroutines.internal.i) r2
            r3 = 0
            java.lang.Object r4 = kotlinx.coroutines.e0.b(r23, r24)
            kotlinx.coroutines.i0 r0 = r2.x
            kotlin.coroutines.g r5 = r2.getContext()
            boolean r0 = r0.isDispatchNeeded(r5)
            r5 = 1
            if (r0 == 0) goto L_0x0030
            r2.z = r4
            r2.f = r5
            kotlinx.coroutines.i0 r0 = r2.x
            kotlin.coroutines.g r5 = r2.getContext()
            r0.dispatch(r5, r2)
            r1 = r23
            r18 = r2
            r19 = r3
            goto L_0x0121
        L_0x0030:
            r0 = 1
            r6 = r2
            r7 = r0
            r8 = 0
            r9 = 0
            boolean r0 = kotlinx.coroutines.s0.a()
            if (r0 == 0) goto L_0x003e
            r0 = 0
        L_0x003e:
            kotlinx.coroutines.x2 r0 = kotlinx.coroutines.x2.a
            kotlinx.coroutines.j1 r10 = r0.b()
            boolean r0 = r10.c1()
            if (r0 == 0) goto L_0x005a
            r6.z = r4
            r6.f = r7
            r10.F0(r6)
            r1 = r23
            r18 = r2
            r19 = r3
            goto L_0x0120
        L_0x005a:
            r11 = r6
            r12 = 0
            r10.a1(r5)
            r13 = 0
            r0 = r2
            r14 = 0
            kotlin.coroutines.g r15 = r0.getContext()     // Catch:{ all -> 0x010e }
            kotlinx.coroutines.z1$b r5 = kotlinx.coroutines.z1.g     // Catch:{ all -> 0x010e }
            kotlin.coroutines.g$b r5 = r15.get(r5)     // Catch:{ all -> 0x010e }
            kotlinx.coroutines.z1 r5 = (kotlinx.coroutines.z1) r5     // Catch:{ all -> 0x010e }
            if (r5 == 0) goto L_0x0096
            boolean r15 = r5.isActive()     // Catch:{ all -> 0x008d }
            if (r15 != 0) goto L_0x0096
            java.util.concurrent.CancellationException r15 = r5.n()     // Catch:{ all -> 0x008d }
            r0.a(r4, r15)     // Catch:{ all -> 0x008d }
            kotlin.o$a r16 = kotlin.o.Companion     // Catch:{ all -> 0x008d }
            java.lang.Object r16 = kotlin.p.a(r15)     // Catch:{ all -> 0x008d }
            java.lang.Object r1 = kotlin.o.m17constructorimpl(r16)     // Catch:{ all -> 0x008d }
            r0.resumeWith(r1)     // Catch:{ all -> 0x008d }
            r1 = 1
            goto L_0x0097
        L_0x008d:
            r0 = move-exception
            r1 = r23
            r18 = r2
            r19 = r3
            goto L_0x0115
        L_0x0096:
            r1 = 0
        L_0x0097:
            if (r1 != 0) goto L_0x00fd
            r1 = r2
            r5 = 0
            kotlin.coroutines.d<T> r0 = r1.y     // Catch:{ all -> 0x010e }
            java.lang.Object r14 = r1.p0     // Catch:{ all -> 0x010e }
            r15 = r0
            r16 = 0
            kotlin.coroutines.g r0 = r15.getContext()     // Catch:{ all -> 0x010e }
            r17 = r0
            r18 = r2
            r2 = r17
            java.lang.Object r0 = kotlinx.coroutines.internal.j0.c(r2, r14)     // Catch:{ all -> 0x00f7 }
            r17 = r0
            kotlinx.coroutines.internal.f0 r0 = kotlinx.coroutines.internal.j0.a     // Catch:{ all -> 0x00f7 }
            r19 = r3
            r3 = r17
            if (r3 == r0) goto L_0x00c3
            kotlinx.coroutines.b3 r0 = kotlinx.coroutines.h0.g(r15, r2, r3)     // Catch:{ all -> 0x00bf }
            goto L_0x00c4
        L_0x00bf:
            r0 = move-exception
            r1 = r23
            goto L_0x0115
        L_0x00c3:
            r0 = 0
        L_0x00c4:
            r17 = r0
            r0 = 0
            r20 = r0
            kotlin.coroutines.d<T> r0 = r1.y     // Catch:{ all -> 0x00e5 }
            r21 = r1
            r1 = r23
            r0.resumeWith(r1)     // Catch:{ all -> 0x00e3 }
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x00e3 }
            if (r17 == 0) goto L_0x00de
            boolean r0 = r17.W0()     // Catch:{ all -> 0x010c }
            if (r0 == 0) goto L_0x00e1
        L_0x00de:
            kotlinx.coroutines.internal.j0.a(r2, r3)     // Catch:{ all -> 0x010c }
        L_0x00e1:
            goto L_0x0103
        L_0x00e3:
            r0 = move-exception
            goto L_0x00ea
        L_0x00e5:
            r0 = move-exception
            r21 = r1
            r1 = r23
        L_0x00ea:
            if (r17 == 0) goto L_0x00f2
            boolean r20 = r17.W0()     // Catch:{ all -> 0x010c }
            if (r20 == 0) goto L_0x00f5
        L_0x00f2:
            kotlinx.coroutines.internal.j0.a(r2, r3)     // Catch:{ all -> 0x010c }
        L_0x00f5:
            throw r0     // Catch:{ all -> 0x010c }
        L_0x00f7:
            r0 = move-exception
            r1 = r23
            r19 = r3
            goto L_0x0115
        L_0x00fd:
            r1 = r23
            r18 = r2
            r19 = r3
        L_0x0103:
        L_0x0104:
            boolean r0 = r10.f1()     // Catch:{ all -> 0x010c }
            if (r0 != 0) goto L_0x0104
            goto L_0x0119
        L_0x010c:
            r0 = move-exception
            goto L_0x0115
        L_0x010e:
            r0 = move-exception
            r1 = r23
            r18 = r2
            r19 = r3
        L_0x0115:
            r2 = 0
            r11.i(r0, r2)     // Catch:{ all -> 0x0123 }
        L_0x0119:
            r2 = 1
            r10.W(r2)
        L_0x0120:
        L_0x0121:
            goto L_0x012f
        L_0x0123:
            r0 = move-exception
            r2 = r0
            r3 = 1
            r10.W(r3)
            throw r2
        L_0x012a:
            r1 = r23
            r22.resumeWith(r23)
        L_0x012f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.j.b(kotlin.coroutines.d, java.lang.Object, kotlin.jvm.functions.l):void");
    }

    public static final boolean d(@NotNull i<? super x> $this$yieldUndispatched) {
        Object contState$iv = x.a;
        i<? super x> iVar = $this$yieldUndispatched;
        if (s0.a()) {
        }
        j1 eventLoop$iv = x2.a.b();
        if (eventLoop$iv.d1()) {
            return false;
        }
        if (eventLoop$iv.c1()) {
            iVar.z = contState$iv;
            iVar.f = 1;
            eventLoop$iv.F0(iVar);
            return true;
        }
        b1 $this$runUnconfinedEventLoop$iv$iv = iVar;
        eventLoop$iv.a1(true);
        try {
            $this$yieldUndispatched.run();
            do {
            } while (eventLoop$iv.f1());
        } catch (Throwable th) {
            eventLoop$iv.W(true);
            throw th;
        }
        eventLoop$iv.W(true);
        return false;
    }
}
