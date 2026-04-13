package kotlinx.coroutines.flow;

import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002H\u0000\u001as\u0010\u0003\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00022D\u0010\u0005\u001a@\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0002\u0012\u0015\u0012\u0013\u0018\u00010\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\u0002\b\r2\b\u0010\n\u001a\u0004\u0018\u00010\u0007H@ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001aj\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00112D\u0010\u0005\u001a@\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0002\u0012\u0015\u0012\u0013\u0018\u00010\u0007¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aS\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00112-\u0010\u0005\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0014¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001aS\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0011\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00112-\u0010\u0005\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0014¢\u0006\u0002\b\rø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001as\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0011\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00040\u00112D\b\u0005\u0010\u0017\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u0002\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\u0002\b\rH\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001as\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0011\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0018*\b\u0012\u0004\u0012\u0002H\u00040\u00112D\b\u0005\u0010\u0017\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u0002\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\u0002\b\rH\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, d2 = {"ensureActive", "", "Lkotlinx/coroutines/flow/FlowCollector;", "invokeSafely", "T", "action", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "cause", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "invokeSafely$FlowKt__EmittersKt", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/jvm/functions/Function3;Ljava/lang/Throwable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCompletion", "Lkotlinx/coroutines/flow/Flow;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "onEmpty", "Lkotlin/Function2;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "onStart", "transform", "R", "value", "unsafeTransform", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = 48)
/* compiled from: Emitters.kt */
public final /* synthetic */ class j {

    @l(k = 3, mv = {1, 6, 0}, xi = 48)
    @f(c = "kotlinx.coroutines.flow.FlowKt__EmittersKt", f = "Emitters.kt", l = {216}, m = "invokeSafely$FlowKt__EmittersKt")
    /* compiled from: Emitters.kt */
    public static final class a<T> extends d {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return j.c((d) null, (q) null, (Throwable) null, this);
        }
    }

    @NotNull
    public static final <T> c<T> e(@NotNull c<? extends T> $this$onStart, @NotNull p<? super d<? super T>, ? super kotlin.coroutines.d<? super x>, ? extends Object> action) {
        return new c(action, $this$onStart);
    }

    @l(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SafeCollector.common.kt */
    public static final class b implements c<T> {
        final /* synthetic */ c c;
        final /* synthetic */ q d;

        @l(k = 3, mv = {1, 6, 0}, xi = 48)
        @f(c = "kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1", f = "Emitters.kt", l = {114, 121, 128}, m = "collect")
        /* compiled from: SafeCollector.common.kt */
        public static final class a extends d {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(b bVar, kotlin.coroutines.d dVar) {
                super(dVar);
                this.this$0 = bVar;
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.a((d) null, this);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0066, code lost:
            r2 = new kotlinx.coroutines.flow.internal.o(r2, r11.getContext());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r5 = r4.d;
            r11.L$0 = r2;
            r11.L$1 = null;
            r11.label = 3;
            kotlin.jvm.internal.j.c(6);
            r3 = r5.invoke(r2, null, r11);
            kotlin.jvm.internal.j.c(7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0088, code lost:
            if (r3 != r1) goto L_0x008b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x008a, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x008b, code lost:
            r1 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x008c, code lost:
            r1.releaseIntercepted();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0093, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0094, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0095, code lost:
            r8 = r2;
            r2 = r1;
            r1 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0098, code lost:
            r1.releaseIntercepted();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x009b, code lost:
            throw r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b3, code lost:
            throw r1;
         */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x0038  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0042  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x004f  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.flow.d<? super T> r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
            /*
                r9 = this;
                boolean r0 = r11 instanceof kotlinx.coroutines.flow.j.b.a
                if (r0 == 0) goto L_0x0013
                r0 = r11
                kotlinx.coroutines.flow.j$b$a r0 = (kotlinx.coroutines.flow.j.b.a) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L_0x0013
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0018
            L_0x0013:
                kotlinx.coroutines.flow.j$b$a r0 = new kotlinx.coroutines.flow.j$b$a
                r0.<init>(r9, r11)
            L_0x0018:
                r11 = r0
                java.lang.Object r0 = r11.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r11.label
                r3 = 0
                switch(r2) {
                    case 0: goto L_0x004f;
                    case 1: goto L_0x0042;
                    case 2: goto L_0x0038;
                    case 3: goto L_0x002d;
                    default: goto L_0x0025;
                }
            L_0x0025:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r11)
                throw r10
            L_0x002d:
                r10 = 0
                java.lang.Object r1 = r11.L$0
                kotlinx.coroutines.flow.internal.o r1 = (kotlinx.coroutines.flow.internal.o) r1
                kotlin.p.b(r0)     // Catch:{ all -> 0x0036 }
                goto L_0x008c
            L_0x0036:
                r2 = move-exception
                goto L_0x0098
            L_0x0038:
                r10 = 0
                java.lang.Object r1 = r11.L$0
                java.lang.Throwable r1 = (java.lang.Throwable) r1
                kotlin.p.b(r0)
                goto L_0x00b3
            L_0x0042:
                r10 = 0
                java.lang.Object r2 = r11.L$1
                kotlinx.coroutines.flow.d r2 = (kotlinx.coroutines.flow.d) r2
                java.lang.Object r4 = r11.L$0
                kotlinx.coroutines.flow.j$b r4 = (kotlinx.coroutines.flow.j.b) r4
                kotlin.p.b(r0)     // Catch:{ all -> 0x009c }
                goto L_0x0066
            L_0x004f:
                kotlin.p.b(r0)
                r4 = r9
                r2 = r10
                r10 = 0
                kotlinx.coroutines.flow.c r5 = r4.c     // Catch:{ all -> 0x009c }
                r11.L$0 = r4     // Catch:{ all -> 0x009c }
                r11.L$1 = r2     // Catch:{ all -> 0x009c }
                r6 = 1
                r11.label = r6     // Catch:{ all -> 0x009c }
                java.lang.Object r5 = r5.a(r2, r11)     // Catch:{ all -> 0x009c }
                if (r5 != r1) goto L_0x0066
                return r1
            L_0x0066:
                kotlinx.coroutines.flow.internal.o r5 = new kotlinx.coroutines.flow.internal.o
                r6 = 0
                kotlin.coroutines.g r7 = r11.getContext()
                r5.<init>(r2, r7)
                r2 = r5
                kotlin.jvm.functions.q r5 = r4.d     // Catch:{ all -> 0x0094 }
                r11.L$0 = r2     // Catch:{ all -> 0x0094 }
                r11.L$1 = r3     // Catch:{ all -> 0x0094 }
                r6 = 3
                r11.label = r6     // Catch:{ all -> 0x0094 }
                r6 = 6
                kotlin.jvm.internal.j.c(r6)     // Catch:{ all -> 0x0094 }
                java.lang.Object r3 = r5.invoke(r2, r3, r11)     // Catch:{ all -> 0x0094 }
                r5 = 7
                kotlin.jvm.internal.j.c(r5)     // Catch:{ all -> 0x0094 }
                if (r3 != r1) goto L_0x008b
                return r1
            L_0x008b:
                r1 = r2
            L_0x008c:
                r1.releaseIntercepted()
                kotlin.x r10 = kotlin.x.a
                return r10
            L_0x0094:
                r1 = move-exception
                r8 = r2
                r2 = r1
                r1 = r8
            L_0x0098:
                r1.releaseIntercepted()
                throw r2
            L_0x009c:
                r2 = move-exception
                kotlinx.coroutines.flow.d0 r5 = new kotlinx.coroutines.flow.d0
                r5.<init>(r2)
                kotlin.jvm.functions.q r6 = r4.d
                r11.L$0 = r2
                r11.L$1 = r3
                r3 = 2
                r11.label = r3
                java.lang.Object r3 = kotlinx.coroutines.flow.j.c(r5, r6, r2, r11)
                if (r3 != r1) goto L_0x00b2
                return r1
            L_0x00b2:
                r1 = r2
            L_0x00b3:
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.j.b.a(kotlinx.coroutines.flow.d, kotlin.coroutines.d):java.lang.Object");
        }

        public b(c cVar, q qVar) {
            this.c = cVar;
            this.d = qVar;
        }
    }

    @l(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: SafeCollector.common.kt */
    public static final class c implements c<T> {
        final /* synthetic */ p c;
        final /* synthetic */ c d;

        @l(k = 3, mv = {1, 6, 0}, xi = 48)
        @f(c = "kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1", f = "Emitters.kt", l = {116, 120}, m = "collect")
        /* compiled from: SafeCollector.common.kt */
        public static final class a extends d {
            Object L$0;
            Object L$1;
            Object L$2;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ c this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(c cVar, kotlin.coroutines.d dVar) {
                super(dVar);
                this.this$0 = cVar;
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
        /* JADX WARNING: Removed duplicated region for block: B:14:0x0042  */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0083 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.flow.d<? super T> r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r9) {
            /*
                r7 = this;
                boolean r0 = r9 instanceof kotlinx.coroutines.flow.j.c.a
                if (r0 == 0) goto L_0x0013
                r0 = r9
                kotlinx.coroutines.flow.j$c$a r0 = (kotlinx.coroutines.flow.j.c.a) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L_0x0013
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0018
            L_0x0013:
                kotlinx.coroutines.flow.j$c$a r0 = new kotlinx.coroutines.flow.j$c$a
                r0.<init>(r7, r9)
            L_0x0018:
                r9 = r0
                java.lang.Object r0 = r9.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r9.label
                switch(r2) {
                    case 0: goto L_0x0042;
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
                goto L_0x0084
            L_0x0031:
                r8 = 0
                java.lang.Object r2 = r9.L$2
                kotlinx.coroutines.flow.internal.o r2 = (kotlinx.coroutines.flow.internal.o) r2
                java.lang.Object r3 = r9.L$1
                kotlinx.coroutines.flow.d r3 = (kotlinx.coroutines.flow.d) r3
                java.lang.Object r4 = r9.L$0
                kotlinx.coroutines.flow.j$c r4 = (kotlinx.coroutines.flow.j.c) r4
                kotlin.p.b(r0)     // Catch:{ all -> 0x0087 }
                goto L_0x006d
            L_0x0042:
                kotlin.p.b(r0)
                r4 = r7
                r3 = r8
                r8 = 0
                kotlinx.coroutines.flow.internal.o r2 = new kotlinx.coroutines.flow.internal.o
                r5 = 0
                kotlin.coroutines.g r6 = r9.getContext()
                r2.<init>(r3, r6)
                kotlin.jvm.functions.p r5 = r4.c     // Catch:{ all -> 0x0087 }
                r9.L$0 = r4     // Catch:{ all -> 0x0087 }
                r9.L$1 = r3     // Catch:{ all -> 0x0087 }
                r9.L$2 = r2     // Catch:{ all -> 0x0087 }
                r6 = 1
                r9.label = r6     // Catch:{ all -> 0x0087 }
                r6 = 6
                kotlin.jvm.internal.j.c(r6)     // Catch:{ all -> 0x0087 }
                java.lang.Object r5 = r5.invoke(r2, r9)     // Catch:{ all -> 0x0087 }
                r6 = 7
                kotlin.jvm.internal.j.c(r6)     // Catch:{ all -> 0x0087 }
                if (r5 != r1) goto L_0x006d
                return r1
            L_0x006d:
                r2.releaseIntercepted()
                kotlinx.coroutines.flow.c r2 = r4.d
                r5 = 0
                r9.L$0 = r5
                r9.L$1 = r5
                r9.L$2 = r5
                r5 = 2
                r9.label = r5
                java.lang.Object r2 = r2.a(r3, r9)
                if (r2 != r1) goto L_0x0084
                return r1
            L_0x0084:
                kotlin.x r8 = kotlin.x.a
                return r8
            L_0x0087:
                r1 = move-exception
                r2.releaseIntercepted()
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.j.c.a(kotlinx.coroutines.flow.d, kotlin.coroutines.d):java.lang.Object");
        }

        public c(p pVar, c cVar) {
            this.c = pVar;
            this.d = cVar;
        }
    }

    @NotNull
    public static final <T> c<T> d(@NotNull c<? extends T> $this$onCompletion, @NotNull q<? super d<? super T>, ? super Throwable, ? super kotlin.coroutines.d<? super x>, ? extends Object> action) {
        return new b($this$onCompletion, action);
    }

    public static final void b(@NotNull d<?> $this$ensureActive) {
        if ($this$ensureActive instanceof d0) {
            throw ((d0) $this$ensureActive).c;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004a, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object c(kotlinx.coroutines.flow.d<? super T> r4, kotlin.jvm.functions.q<? super kotlinx.coroutines.flow.d<? super T>, ? super java.lang.Throwable, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends java.lang.Object> r5, java.lang.Throwable r6, kotlin.coroutines.d<? super kotlin.x> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.flow.j.a
            if (r0 == 0) goto L_0x0013
            r0 = r7
            kotlinx.coroutines.flow.j$a r0 = (kotlinx.coroutines.flow.j.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            kotlinx.coroutines.flow.j$a r0 = new kotlinx.coroutines.flow.j$a
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
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002c:
            java.lang.Object r4 = r7.L$0
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            kotlin.p.b(r0)     // Catch:{ all -> 0x0034 }
            goto L_0x0047
        L_0x0034:
            r5 = move-exception
            goto L_0x004d
        L_0x0036:
            kotlin.p.b(r0)
            r7.L$0 = r6     // Catch:{ all -> 0x004b }
            r2 = 1
            r7.label = r2     // Catch:{ all -> 0x004b }
            java.lang.Object r2 = r5.invoke(r4, r6, r7)     // Catch:{ all -> 0x004b }
            if (r2 != r1) goto L_0x0046
            return r1
        L_0x0046:
            r4 = r6
        L_0x0047:
            kotlin.x r5 = kotlin.x.a
            return r5
        L_0x004b:
            r5 = move-exception
            r4 = r6
        L_0x004d:
            if (r4 == 0) goto L_0x0056
            if (r4 == r5) goto L_0x0056
            r6 = r5
            r1 = 0
            kotlin.b.a(r6, r4)
        L_0x0056:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.j.c(kotlinx.coroutines.flow.d, kotlin.jvm.functions.q, java.lang.Throwable, kotlin.coroutines.d):java.lang.Object");
    }
}
