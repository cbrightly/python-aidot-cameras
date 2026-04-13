package kotlinx.coroutines.channels;

import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.o;
import kotlin.x;
import kotlinx.coroutines.h0;
import kotlinx.coroutines.n;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a)\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u001a\u0001\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2-\b\u0002\u0010\u0010\u001a'\u0012\u0015\u0012\u0013\u0018\u00010\u0012¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0011j\u0004\u0018\u0001`\u00162/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0017¢\u0006\u0002\b\u001aH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001b\u001a¨\u0001\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2-\b\u0002\u0010\u0010\u001a'\u0012\u0015\u0012\u0013\u0018\u00010\u0012¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0011j\u0004\u0018\u0001`\u00162/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0017¢\u0006\u0002\b\u001aH\u0000ø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001ae\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b*\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2/\b\u0001\u0010\u0003\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0017¢\u0006\u0002\b\u001aH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001f\u0002\u0004\n\u0002\b\u0019¨\u0006 "}, d2 = {"awaitClose", "", "Lkotlinx/coroutines/channels/ProducerScope;", "block", "Lkotlin/Function0;", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/jvm/functions/Function0;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produce", "Lkotlinx/coroutines/channels/ReceiveChannel;", "E", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "start", "Lkotlinx/coroutines/CoroutineStart;", "onCompletion", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "Lkotlinx/coroutines/CompletionHandler;", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlinx/coroutines/channels/BufferOverflow;Lkotlinx/coroutines/CoroutineStart;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "(Lkotlinx/coroutines/CoroutineScope;Lkotlin/coroutines/CoroutineContext;ILkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: Produce.kt */
public final class s {

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.channels.ProduceKt", f = "Produce.kt", l = {153}, m = "awaitClose")
    /* compiled from: Produce.kt */
    public static final class a extends d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return s.a((u<?>) null, (kotlin.jvm.functions.a<x>) null, this);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlin.jvm.functions.a<kotlin.x>, code=kotlin.jvm.functions.a, for r10v0, types: [kotlin.jvm.functions.a<kotlin.x>, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.u<?> r9, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.a r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.channels.s.a
            if (r0 == 0) goto L_0x0013
            r0 = r11
            kotlinx.coroutines.channels.s$a r0 = (kotlinx.coroutines.channels.s.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.channels.s$a r0 = new kotlinx.coroutines.channels.s$a
            r0.<init>(r11)
        L_0x0018:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r11.label
            switch(r2) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002c:
            r9 = 0
            java.lang.Object r10 = r11.L$1
            kotlin.jvm.functions.a r10 = (kotlin.jvm.functions.a) r10
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.channels.u r1 = (kotlinx.coroutines.channels.u) r1
            kotlin.p.b(r0)     // Catch:{ all -> 0x0039 }
            goto L_0x0083
        L_0x0039:
            r9 = move-exception
            goto L_0x008b
        L_0x003b:
            kotlin.p.b(r0)
            kotlin.coroutines.g r2 = r11.getContext()
            kotlinx.coroutines.z1$b r3 = kotlinx.coroutines.z1.g
            kotlin.coroutines.g$b r2 = r2.get(r3)
            r3 = 1
            if (r2 != r9) goto L_0x004d
            r2 = r3
            goto L_0x004e
        L_0x004d:
            r2 = 0
        L_0x004e:
            if (r2 == 0) goto L_0x008f
            r2 = 0
            r11.L$0 = r9     // Catch:{ all -> 0x0039 }
            r11.L$1 = r10     // Catch:{ all -> 0x0039 }
            r11.label = r3     // Catch:{ all -> 0x0039 }
            r4 = r11
            r5 = 0
            kotlinx.coroutines.o r6 = new kotlinx.coroutines.o     // Catch:{ all -> 0x0039 }
            kotlin.coroutines.d r7 = kotlin.coroutines.intrinsics.b.c(r4)     // Catch:{ all -> 0x0039 }
            r6.<init>(r7, r3)     // Catch:{ all -> 0x0039 }
            r3 = r6
            r3.w()     // Catch:{ all -> 0x0039 }
            r6 = r3
            r7 = 0
            kotlinx.coroutines.channels.s$b r8 = new kotlinx.coroutines.channels.s$b     // Catch:{ all -> 0x0039 }
            r8.<init>(r6)     // Catch:{ all -> 0x0039 }
            r9.k(r8)     // Catch:{ all -> 0x0039 }
            java.lang.Object r6 = r3.t()     // Catch:{ all -> 0x0039 }
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()     // Catch:{ all -> 0x0039 }
            if (r6 != r3) goto L_0x007f
            kotlin.coroutines.jvm.internal.h.c(r11)     // Catch:{ all -> 0x0039 }
        L_0x007f:
            if (r6 != r1) goto L_0x0082
            return r1
        L_0x0082:
            r9 = r2
        L_0x0083:
            r10.invoke()
            kotlin.x r9 = kotlin.x.a
            return r9
        L_0x008b:
            r10.invoke()
            throw r9
        L_0x008f:
            r1 = 0
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "awaitClose() can only be invoked from the producer context"
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.s.a(kotlinx.coroutines.channels.u, kotlin.jvm.functions.a, kotlin.coroutines.d):java.lang.Object");
    }

    @l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Produce.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ n<x> $cont;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(n<? super x> nVar) {
            super(1);
            this.$cont = nVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((Throwable) p1);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            n<x> nVar = this.$cont;
            o.a aVar = o.Companion;
            nVar.resumeWith(o.m17constructorimpl(x.a));
        }
    }

    public static /* synthetic */ w d(o0 o0Var, g gVar, int i, p pVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            gVar = h.INSTANCE;
        }
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return b(o0Var, gVar, i, pVar);
    }

    @NotNull
    public static final <E> w<E> b(@NotNull o0 $this$produce, @NotNull g context, int capacity, @NotNull p<? super u<? super E>, ? super kotlin.coroutines.d<? super x>, ? extends Object> block) {
        return c($this$produce, context, capacity, h.SUSPEND, q0.DEFAULT, (kotlin.jvm.functions.l<? super Throwable, x>) null, block);
    }

    public static /* synthetic */ w e(o0 o0Var, g gVar, int i, h hVar, q0 q0Var, kotlin.jvm.functions.l lVar, p pVar, int i2, Object obj) {
        h hVar2;
        int i3;
        h hVar3;
        q0 q0Var2;
        if ((i2 & 1) != 0) {
            hVar2 = h.INSTANCE;
        } else {
            hVar2 = gVar;
        }
        if ((i2 & 2) != 0) {
            i3 = 0;
        } else {
            i3 = i;
        }
        if ((i2 & 4) != 0) {
            hVar3 = h.SUSPEND;
        } else {
            hVar3 = hVar;
        }
        if ((i2 & 8) != 0) {
            q0Var2 = q0.DEFAULT;
        } else {
            q0Var2 = q0Var;
        }
        return c(o0Var, hVar2, i3, hVar3, q0Var2, (i2 & 16) != 0 ? null : lVar, pVar);
    }

    @NotNull
    public static final <E> w<E> c(@NotNull o0 $this$produce, @NotNull g context, int capacity, @NotNull h onBufferOverflow, @NotNull q0 start, @Nullable kotlin.jvm.functions.l<? super Throwable, x> onCompletion, @NotNull p<? super u<? super E>, ? super kotlin.coroutines.d<? super x>, ? extends Object> block) {
        t coroutine = new t(h0.e($this$produce, context), k.b(capacity, onBufferOverflow, (kotlin.jvm.functions.l) null, 4, (Object) null));
        if (onCompletion != null) {
            coroutine.t(onCompletion);
        }
        coroutine.U0(start, coroutine, block);
        return coroutine;
    }
}
