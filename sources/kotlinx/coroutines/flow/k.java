package kotlinx.coroutines.flow;

import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.z;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.internal.e0;
import kotlinx.coroutines.s0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000V\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001ah\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012B\u0010\u0003\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0004¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u000e\u001a1\u0010\u000f\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u0019\u0010\u0012\u001a\u00020\u0013*\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0015H\u0002¢\u0006\u0002\b\u0016\u001a\u001b\u0010\u0017\u001a\u00020\u0013*\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0002\b\u0019\u001ac\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\b\b\u0002\u0010\u001b\u001a\u00020\u001c23\b\u0002\u0010\u001d\u001a-\b\u0001\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u001eø\u0001\u0000¢\u0006\u0002\u0010\u001f\u001a}\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012W\u0010\u001d\u001aS\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0005\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\u001c¢\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\n\u0012\u0006\u0012\u0004\u0018\u00010\f0!¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010#\u0002\u0004\n\u0002\b\u0019¨\u0006$"}, d2 = {"catch", "Lkotlinx/coroutines/flow/Flow;", "T", "action", "Lkotlin/Function3;", "Lkotlinx/coroutines/flow/FlowCollector;", "", "Lkotlin/ParameterName;", "name", "cause", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "catchImpl", "collector", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isCancellationCause", "", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "isCancellationCause$FlowKt__ErrorsKt", "isSameExceptionAs", "other", "isSameExceptionAs$FlowKt__ErrorsKt", "retry", "retries", "", "predicate", "Lkotlin/Function2;", "(Lkotlinx/coroutines/flow/Flow;JLkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "retryWhen", "Lkotlin/Function4;", "attempt", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function4;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48)
/* compiled from: Errors.kt */
public final /* synthetic */ class k {

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.FlowKt__ErrorsKt", f = "Errors.kt", l = {156}, m = "catchImpl")
    /* compiled from: Errors.kt */
    public static final class b<T> extends d {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        b(kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return e.g((c) null, (d) null, this);
        }
    }

    @NotNull
    public static final <T> c<T> a(@NotNull c<? extends T> $this$catch, @NotNull q<? super d<? super T>, ? super Throwable, ? super kotlin.coroutines.d<? super x>, ? extends Object> action) {
        return new a($this$catch, action);
    }

    @l(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SafeCollector.common.kt */
    public static final class a implements c<T> {
        final /* synthetic */ c c;
        final /* synthetic */ q d;

        @l(k = 3, mv = {1, 6, 0}, xi = 48)
        @f(c = "kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1", f = "Errors.kt", l = {113, 114}, m = "collect")
        /* renamed from: kotlinx.coroutines.flow.k$a$a  reason: collision with other inner class name */
        /* compiled from: SafeCollector.common.kt */
        public static final class C0445a extends d {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0445a(a aVar, kotlin.coroutines.d dVar) {
                super(dVar);
                this.this$0 = aVar;
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.a((d) null, this);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x003f  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0059  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.flow.d<? super T> r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r9) {
            /*
                r7 = this;
                boolean r0 = r9 instanceof kotlinx.coroutines.flow.k.a.C0445a
                if (r0 == 0) goto L_0x0013
                r0 = r9
                kotlinx.coroutines.flow.k$a$a r0 = (kotlinx.coroutines.flow.k.a.C0445a) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L_0x0013
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0018
            L_0x0013:
                kotlinx.coroutines.flow.k$a$a r0 = new kotlinx.coroutines.flow.k$a$a
                r0.<init>(r7, r9)
            L_0x0018:
                r9 = r0
                java.lang.Object r0 = r9.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r9.label
                switch(r2) {
                    case 0: goto L_0x003f;
                    case 1: goto L_0x0031;
                    case 2: goto L_0x002c;
                    default: goto L_0x0024;
                }
            L_0x0024:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r9)
                throw r8
            L_0x002c:
                r8 = 0
                kotlin.p.b(r0)
                goto L_0x0072
            L_0x0031:
                r8 = 0
                java.lang.Object r2 = r9.L$1
                kotlinx.coroutines.flow.d r2 = (kotlinx.coroutines.flow.d) r2
                java.lang.Object r3 = r9.L$0
                kotlinx.coroutines.flow.k$a r3 = (kotlinx.coroutines.flow.k.a) r3
                kotlin.p.b(r0)
                r4 = r0
                goto L_0x0055
            L_0x003f:
                kotlin.p.b(r0)
                r3 = r7
                r2 = r8
                r8 = 0
                kotlinx.coroutines.flow.c r4 = r3.c
                r9.L$0 = r3
                r9.L$1 = r2
                r5 = 1
                r9.label = r5
                java.lang.Object r4 = kotlinx.coroutines.flow.e.g(r4, r2, r9)
                if (r4 != r1) goto L_0x0055
                return r1
            L_0x0055:
                java.lang.Throwable r4 = (java.lang.Throwable) r4
                if (r4 == 0) goto L_0x0073
                kotlin.jvm.functions.q r5 = r3.d
                r6 = 0
                r9.L$0 = r6
                r9.L$1 = r6
                r6 = 2
                r9.label = r6
                r6 = 6
                kotlin.jvm.internal.j.c(r6)
                java.lang.Object r5 = r5.invoke(r2, r4, r9)
                r6 = 7
                kotlin.jvm.internal.j.c(r6)
                if (r5 != r1) goto L_0x0072
                return r1
            L_0x0072:
            L_0x0073:
                kotlin.x r8 = kotlin.x.a
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.k.a.a(kotlinx.coroutines.flow.d, kotlin.coroutines.d):java.lang.Object");
        }

        public a(c cVar, q qVar) {
            this.c = cVar;
            this.d = qVar;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object b(@org.jetbrains.annotations.NotNull kotlinx.coroutines.flow.c<? extends T> r5, @org.jetbrains.annotations.NotNull kotlinx.coroutines.flow.d<? super T> r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Throwable> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.flow.k.b
            if (r0 == 0) goto L_0x0013
            r0 = r7
            kotlinx.coroutines.flow.k$b r0 = (kotlinx.coroutines.flow.k.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.flow.k$b r0 = new kotlinx.coroutines.flow.k$b
            r0.<init>(r7)
        L_0x0018:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002c:
            java.lang.Object r5 = r7.L$0
            kotlin.jvm.internal.z r5 = (kotlin.jvm.internal.z) r5
            kotlin.p.b(r0)     // Catch:{ all -> 0x0034 }
            goto L_0x0051
        L_0x0034:
            r6 = move-exception
            goto L_0x0056
        L_0x0036:
            kotlin.p.b(r0)
            kotlin.jvm.internal.z r2 = new kotlin.jvm.internal.z
            r2.<init>()
            kotlinx.coroutines.flow.k$c r3 = new kotlinx.coroutines.flow.k$c     // Catch:{ all -> 0x0054 }
            r3.<init>(r6, r2)     // Catch:{ all -> 0x0054 }
            r7.L$0 = r2     // Catch:{ all -> 0x0054 }
            r4 = 1
            r7.label = r4     // Catch:{ all -> 0x0054 }
            java.lang.Object r3 = r5.a(r3, r7)     // Catch:{ all -> 0x0054 }
            if (r3 != r1) goto L_0x0050
            return r1
        L_0x0050:
            r5 = r2
        L_0x0051:
            r6 = 0
            return r6
        L_0x0054:
            r6 = move-exception
            r5 = r2
        L_0x0056:
            T r1 = r5.element
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            boolean r2 = d(r6, r1)
            if (r2 != 0) goto L_0x0079
            kotlin.coroutines.g r2 = r7.getContext()
            boolean r2 = c(r6, r2)
            if (r2 != 0) goto L_0x0079
            if (r1 != 0) goto L_0x006d
            return r6
        L_0x006d:
            boolean r2 = r6 instanceof java.util.concurrent.CancellationException
            if (r2 == 0) goto L_0x0075
            kotlin.b.a(r1, r6)
            throw r1
        L_0x0075:
            kotlin.b.a(r6, r1)
            throw r6
        L_0x0079:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.k.b(kotlinx.coroutines.flow.c, kotlinx.coroutines.flow.d, kotlin.coroutines.d):java.lang.Object");
    }

    @l(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "T", "it", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;"}, k = 3, mv = {1, 6, 0}, xi = 48)
    /* compiled from: Errors.kt */
    public static final class c<T> implements d {
        final /* synthetic */ d<T> c;
        final /* synthetic */ z<Throwable> d;

        @l(k = 3, mv = {1, 6, 0}, xi = 48)
        @f(c = "kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$2", f = "Errors.kt", l = {158}, m = "emit")
        /* compiled from: Errors.kt */
        public static final class a extends d {
            Object L$0;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ c<T> this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(c<? super T> cVar, kotlin.coroutines.d<? super a> dVar) {
                super(dVar);
                this.this$0 = cVar;
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.emit(null, this);
            }
        }

        c(d<? super T> dVar, z<Throwable> zVar) {
            this.c = dVar;
            this.d = zVar;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:21:0x004d, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x0036  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object emit(T r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r7) {
            /*
                r5 = this;
                boolean r0 = r7 instanceof kotlinx.coroutines.flow.k.c.a
                if (r0 == 0) goto L_0x0013
                r0 = r7
                kotlinx.coroutines.flow.k$c$a r0 = (kotlinx.coroutines.flow.k.c.a) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L_0x0013
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0018
            L_0x0013:
                kotlinx.coroutines.flow.k$c$a r0 = new kotlinx.coroutines.flow.k$c$a
                r0.<init>(r5, r7)
            L_0x0018:
                r7 = r0
                java.lang.Object r0 = r7.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r7.label
                switch(r2) {
                    case 0: goto L_0x0036;
                    case 1: goto L_0x002c;
                    default: goto L_0x0024;
                }
            L_0x0024:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                r6.<init>(r7)
                throw r6
            L_0x002c:
                java.lang.Object r6 = r7.L$0
                kotlinx.coroutines.flow.k$c r6 = (kotlinx.coroutines.flow.k.c) r6
                kotlin.p.b(r0)     // Catch:{ all -> 0x0034 }
                goto L_0x004a
            L_0x0034:
                r1 = move-exception
                goto L_0x0050
            L_0x0036:
                kotlin.p.b(r0)
                r2 = r5
                kotlinx.coroutines.flow.d<T> r3 = r2.c     // Catch:{ all -> 0x004e }
                r7.L$0 = r2     // Catch:{ all -> 0x004e }
                r4 = 1
                r7.label = r4     // Catch:{ all -> 0x004e }
                java.lang.Object r3 = r3.emit(r6, r7)     // Catch:{ all -> 0x004e }
                if (r3 != r1) goto L_0x0049
                return r1
            L_0x0049:
                r6 = r2
            L_0x004a:
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x004e:
                r1 = move-exception
                r6 = r2
            L_0x0050:
                kotlin.jvm.internal.z<java.lang.Throwable> r2 = r6.d
                r2.element = r1
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.k.c.emit(java.lang.Object, kotlin.coroutines.d):java.lang.Object");
        }
    }

    private static final boolean c(Throwable $this$isCancellationCause, g coroutineContext) {
        z1 job = (z1) coroutineContext.get(z1.g);
        if (job == null || !job.isCancelled()) {
            return false;
        }
        return d($this$isCancellationCause, job.n());
    }

    private static final boolean d(Throwable $this$isSameExceptionAs, Throwable other) {
        if (other != null) {
            if (kotlin.jvm.internal.k.a(!s0.d() ? other : e0.n(other), !s0.d() ? $this$isSameExceptionAs : e0.n($this$isSameExceptionAs)) != 0) {
                return true;
            }
        }
        return false;
    }
}
