package kotlinx.coroutines.flow;

import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.channels.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a/\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a9\u0010\u000b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\f\u001a\u00020\rH@ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a&\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0007\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, d2 = {"asFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "Lkotlinx/coroutines/channels/BroadcastChannel;", "consumeAsFlow", "Lkotlinx/coroutines/channels/ReceiveChannel;", "emitAll", "", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitAllImpl", "consume", "", "emitAllImpl$FlowKt__ChannelsKt", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produceIn", "scope", "Lkotlinx/coroutines/CoroutineScope;", "receiveAsFlow", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48)
/* compiled from: Channels.kt */
public final /* synthetic */ class g {

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.FlowKt__ChannelsKt", f = "Channels.kt", l = {51, 62}, m = "emitAllImpl$FlowKt__ChannelsKt")
    /* compiled from: Channels.kt */
    public static final class a<T> extends d {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return g.c((d) null, (w) null, false, this);
        }
    }

    @Nullable
    public static final <T> Object b(@NotNull d<? super T> $this$emitAll, @NotNull w<? extends T> channel, @NotNull kotlin.coroutines.d<? super x> $completion) {
        Object c = c($this$emitAll, channel, true, $completion);
        return c == c.d() ? c : x.a;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r10.L$0 = r2;
        r10.L$1 = r9;
        r10.Z$0 = r7;
        r10.label = 1;
        r4 = r9.y(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007b, code lost:
        if (r4 != r1) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007d, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007e, code lost:
        r6 = r8;
        r8 = r7;
        r3 = r2;
        r2 = r9;
        r9 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0084, code lost:
        r7 = r9;
        r9 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008b, code lost:
        if (kotlinx.coroutines.channels.l.i(r9) == false) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008d, code lost:
        r1 = kotlinx.coroutines.channels.l.e(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0091, code lost:
        if (r1 != null) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0094, code lost:
        if (r8 == false) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0096, code lost:
        kotlinx.coroutines.channels.m.a(r2, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009c, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a1, code lost:
        r4 = kotlinx.coroutines.channels.l.g(r9);
        r10.L$0 = r3;
        r10.L$1 = r2;
        r10.Z$0 = r8;
        r10.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b2, code lost:
        if (r3.emit(r4, r10) != r1) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b4, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b5, code lost:
        r9 = r2;
        r2 = r3;
        r6 = r8;
        r8 = r7;
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00bb, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00bc, code lost:
        r9 = r2;
        r2 = r3;
        r6 = r8;
        r8 = r7;
        r7 = r6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object c(kotlinx.coroutines.flow.d<? super T> r7, kotlinx.coroutines.channels.w<? extends T> r8, boolean r9, kotlin.coroutines.d<? super kotlin.x> r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.g.a
            if (r0 == 0) goto L_0x0013
            r0 = r10
            kotlinx.coroutines.flow.g$a r0 = (kotlinx.coroutines.flow.g.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.flow.g$a r0 = new kotlinx.coroutines.flow.g$a
            r0.<init>(r10)
        L_0x0018:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x005f;
                case 1: goto L_0x003f;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002c:
            boolean r7 = r10.Z$0
            r8 = 0
            java.lang.Object r9 = r10.L$1
            kotlinx.coroutines.channels.w r9 = (kotlinx.coroutines.channels.w) r9
            java.lang.Object r2 = r10.L$0
            kotlinx.coroutines.flow.d r2 = (kotlinx.coroutines.flow.d) r2
            kotlin.p.b(r0)     // Catch:{ all -> 0x003c }
            goto L_0x00ba
        L_0x003c:
            r1 = move-exception
            goto L_0x00c1
        L_0x003f:
            r7 = 0
            boolean r8 = r10.Z$0
            r9 = 0
            java.lang.Object r2 = r10.L$1
            kotlinx.coroutines.channels.w r2 = (kotlinx.coroutines.channels.w) r2
            java.lang.Object r3 = r10.L$0
            kotlinx.coroutines.flow.d r3 = (kotlinx.coroutines.flow.d) r3
            kotlin.p.b(r0)     // Catch:{ all -> 0x0056 }
            r4 = r0
            kotlinx.coroutines.channels.l r4 = (kotlinx.coroutines.channels.l) r4     // Catch:{ all -> 0x0056 }
            java.lang.Object r4 = r4.l()     // Catch:{ all -> 0x0056 }
            goto L_0x0084
        L_0x0056:
            r1 = move-exception
            r7 = r9
            r9 = r2
            r2 = r3
            r6 = r8
            r8 = r7
            r7 = r6
            goto L_0x00c1
        L_0x005f:
            kotlin.p.b(r0)
            kotlinx.coroutines.flow.e.k(r7)
            r2 = 0
            r6 = r2
            r2 = r7
            r7 = r9
            r9 = r8
            r8 = r6
        L_0x006b:
            r3 = 0
            r10.L$0 = r2     // Catch:{ all -> 0x003c }
            r10.L$1 = r9     // Catch:{ all -> 0x003c }
            r10.Z$0 = r7     // Catch:{ all -> 0x003c }
            r4 = 1
            r10.label = r4     // Catch:{ all -> 0x003c }
            java.lang.Object r4 = r9.y(r10)     // Catch:{ all -> 0x003c }
            if (r4 != r1) goto L_0x007e
            return r1
        L_0x007e:
            r6 = r8
            r8 = r7
            r7 = r3
            r3 = r2
            r2 = r9
            r9 = r6
        L_0x0084:
            r7 = r9
            r9 = r4
            boolean r4 = kotlinx.coroutines.channels.l.i(r9)     // Catch:{ all -> 0x00bb }
            if (r4 == 0) goto L_0x00a1
            java.lang.Throwable r1 = kotlinx.coroutines.channels.l.e(r9)     // Catch:{ all -> 0x00bb }
            if (r1 != 0) goto L_0x009d
            if (r8 == 0) goto L_0x0099
            kotlinx.coroutines.channels.m.a(r2, r7)
        L_0x0099:
            kotlin.x r9 = kotlin.x.a
            return r9
        L_0x009d:
            r9 = r1
            r1 = 0
            throw r9     // Catch:{ all -> 0x00bb }
        L_0x00a1:
            java.lang.Object r4 = kotlinx.coroutines.channels.l.g(r9)     // Catch:{ all -> 0x00bb }
            r10.L$0 = r3     // Catch:{ all -> 0x00bb }
            r10.L$1 = r2     // Catch:{ all -> 0x00bb }
            r10.Z$0 = r8     // Catch:{ all -> 0x00bb }
            r5 = 2
            r10.label = r5     // Catch:{ all -> 0x00bb }
            java.lang.Object r4 = r3.emit(r4, r10)     // Catch:{ all -> 0x00bb }
            if (r4 != r1) goto L_0x00b5
            return r1
        L_0x00b5:
            r9 = r2
            r2 = r3
            r6 = r8
            r8 = r7
            r7 = r6
        L_0x00ba:
            goto L_0x006b
        L_0x00bb:
            r1 = move-exception
            r9 = r2
            r2 = r3
            r6 = r8
            r8 = r7
            r7 = r6
        L_0x00c1:
            r8 = r1
            throw r1     // Catch:{ all -> 0x00c4 }
        L_0x00c4:
            r1 = move-exception
            if (r7 == 0) goto L_0x00ca
            kotlinx.coroutines.channels.m.a(r9, r8)
        L_0x00ca:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.g.c(kotlinx.coroutines.flow.d, kotlinx.coroutines.channels.w, boolean, kotlin.coroutines.d):java.lang.Object");
    }
}
