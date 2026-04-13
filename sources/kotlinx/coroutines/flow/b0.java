package kotlinx.coroutines.flow;

import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002BE\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012-\u0010\u0004\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tø\u0001\u0000¢\u0006\u0002\u0010\nJ\u0019\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00028\u0000HAø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0011\u0010\u000f\u001a\u00020\u0007H@ø\u0001\u0000¢\u0006\u0002\u0010\u0010R:\u0010\u0004\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tX\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u000bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/flow/SubscribedFlowCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "action", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onSubscription", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Share.kt */
public final class b0<T> implements d<T> {
    @NotNull
    private final d<T> c;
    @NotNull
    private final p<d<? super T>, d<? super x>, Object> d;

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.SubscribedFlowCollector", f = "Share.kt", l = {419, 423}, m = "onSubscription")
    /* compiled from: Share.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b0<T> this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(b0<T> b0Var, d<? super a> dVar) {
            super(dVar);
            this.this$0 = b0Var;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a(this);
        }
    }

    @Nullable
    public Object emit(T t, @NotNull d<? super x> dVar) {
        return this.c.emit(t, dVar);
    }

    public b0(@NotNull d<? super T> collector, @NotNull p<? super d<? super T>, ? super d<? super x>, ? extends Object> action) {
        this.c = collector;
        this.d = action;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.b0.a
            if (r0 == 0) goto L_0x0013
            r0 = r8
            kotlinx.coroutines.flow.b0$a r0 = (kotlinx.coroutines.flow.b0.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.flow.b0$a r0 = new kotlinx.coroutines.flow.b0$a
            r0.<init>(r7, r8)
        L_0x0018:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x003c;
                case 1: goto L_0x0030;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002c:
            kotlin.p.b(r0)
            goto L_0x0078
        L_0x0030:
            java.lang.Object r2 = r8.L$1
            kotlinx.coroutines.flow.internal.o r2 = (kotlinx.coroutines.flow.internal.o) r2
            java.lang.Object r3 = r8.L$0
            kotlinx.coroutines.flow.b0 r3 = (kotlinx.coroutines.flow.b0) r3
            kotlin.p.b(r0)     // Catch:{ all -> 0x007c }
            goto L_0x005d
        L_0x003c:
            kotlin.p.b(r0)
            r3 = r7
            kotlinx.coroutines.flow.internal.o r2 = new kotlinx.coroutines.flow.internal.o
            kotlinx.coroutines.flow.d<T> r4 = r3.c
            r5 = 0
            kotlin.coroutines.g r6 = r8.getContext()
            r2.<init>(r4, r6)
            kotlin.jvm.functions.p<kotlinx.coroutines.flow.d<? super T>, kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r4 = r3.d     // Catch:{ all -> 0x007c }
            r8.L$0 = r3     // Catch:{ all -> 0x007c }
            r8.L$1 = r2     // Catch:{ all -> 0x007c }
            r5 = 1
            r8.label = r5     // Catch:{ all -> 0x007c }
            java.lang.Object r4 = r4.invoke(r2, r8)     // Catch:{ all -> 0x007c }
            if (r4 != r1) goto L_0x005d
            return r1
        L_0x005d:
            r2.releaseIntercepted()
            kotlinx.coroutines.flow.d<T> r2 = r3.c
            boolean r4 = r2 instanceof kotlinx.coroutines.flow.b0
            if (r4 == 0) goto L_0x007b
            kotlinx.coroutines.flow.b0 r2 = (kotlinx.coroutines.flow.b0) r2
            r4 = 0
            r8.L$0 = r4
            r8.L$1 = r4
            r4 = 2
            r8.label = r4
            java.lang.Object r2 = r2.a(r8)
            if (r2 != r1) goto L_0x0078
            return r1
        L_0x0078:
            kotlin.x r1 = kotlin.x.a
            return r1
        L_0x007b:
            goto L_0x0078
        L_0x007c:
            r1 = move-exception
            r2.releaseIntercepted()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.b0.a(kotlin.coroutines.d):java.lang.Object");
    }
}
