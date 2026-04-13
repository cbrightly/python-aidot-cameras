package io.ktor.routing;

import io.ktor.application.j;
import io.ktor.http.y;
import java.util.ArrayList;
import java.util.List;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Routing.kt */
public final class l extends i {
    @NotNull
    private static final j<m> C4 = new j<>();
    @NotNull
    private static final j<m> D4 = new j<>();
    /* access modifiers changed from: private */
    @NotNull
    public static final io.ktor.util.a<l> E4 = new io.ktor.util.a<>("Routing");
    public static final a F4 = new a((DefaultConstructorMarker) null);
    private final List<kotlin.jvm.functions.l<x, x>> G4 = new ArrayList();
    @NotNull
    private final io.ktor.application.a H4;

    @f(c = "io.ktor.routing.Routing", f = "Routing.kt", l = {147}, m = "executeResult")
    /* compiled from: Routing.kt */
    public static final class b extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(l lVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = lVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.M((io.ktor.util.pipeline.d<x, io.ktor.application.b>) null, (i) null, (y) null, this);
        }
    }

    @f(c = "io.ktor.routing.Routing", f = "Routing.kt", l = {34}, m = "interceptor")
    /* compiled from: Routing.kt */
    public static final class c extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(l lVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = lVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.N((io.ktor.util.pipeline.d<x, io.ktor.application.b>) null, this);
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public l(@NotNull io.ktor.application.a application) {
        super((i) null, new h(application.F().d()));
        k.f(application, "application");
        this.H4 = application;
    }

    /* Debug info: failed to restart local var, previous not found, register: 9 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: io.ktor.routing.w} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: io.ktor.routing.v} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object N(@org.jetbrains.annotations.NotNull io.ktor.util.pipeline.d<kotlin.x, io.ktor.application.b> r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof io.ktor.routing.l.c
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.routing.l$c r0 = (io.ktor.routing.l.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.routing.l$c r0 = new io.ktor.routing.l$c
            r0.<init>(r9, r11)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0044;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r2 = 0
            r3 = r2
            java.lang.Object r4 = r0.L$3
            r3 = r4
            io.ktor.routing.w r3 = (io.ktor.routing.w) r3
            java.lang.Object r4 = r0.L$2
            r2 = r4
            io.ktor.routing.v r2 = (io.ktor.routing.v) r2
            java.lang.Object r4 = r0.L$1
            r10 = r4
            io.ktor.util.pipeline.d r10 = (io.ktor.util.pipeline.d) r10
            java.lang.Object r4 = r0.L$0
            io.ktor.routing.l r4 = (io.ktor.routing.l) r4
            kotlin.p.b(r1)
            goto L_0x007c
        L_0x0044:
            kotlin.p.b(r1)
            r3 = r10
            r4 = 0
            java.lang.Object r5 = r3.getContext()
            io.ktor.application.b r5 = (io.ktor.application.b) r5
            java.util.List<kotlin.jvm.functions.l<io.ktor.routing.x, kotlin.x>> r3 = r9.G4
            io.ktor.routing.v r4 = new io.ktor.routing.v
            r4.<init>(r9, r5, r3)
            r3 = r4
            io.ktor.routing.w r4 = r3.d()
            boolean r5 = r4 instanceof io.ktor.routing.w.b
            if (r5 == 0) goto L_0x0080
            io.ktor.routing.i r5 = r4.b()
            io.ktor.http.y r6 = r4.a()
            r0.L$0 = r9
            r0.L$1 = r10
            r0.L$2 = r3
            r0.L$3 = r4
            r7 = 1
            r0.label = r7
            java.lang.Object r5 = r9.M(r10, r5, r6, r0)
            if (r5 != r2) goto L_0x0079
            return r2
        L_0x0079:
            r2 = r3
            r3 = r4
            r4 = r9
        L_0x007c:
            r8 = r3
            r3 = r2
            r2 = r8
            goto L_0x0082
        L_0x0080:
            r2 = r4
            r4 = r9
        L_0x0082:
            kotlin.x r5 = kotlin.x.a
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.routing.l.N(io.ktor.util.pipeline.d, kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 16 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x013b, code lost:
        r13.H4.F().b().a(D4, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x014e, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object M(@org.jetbrains.annotations.NotNull io.ktor.util.pipeline.d<kotlin.x, io.ktor.application.b> r17, @org.jetbrains.annotations.NotNull io.ktor.routing.i r18, @org.jetbrains.annotations.NotNull io.ktor.http.y r19, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r20) {
        /*
            r16 = this;
            r1 = r16
            r2 = r20
            boolean r0 = r2 instanceof io.ktor.routing.l.b
            if (r0 == 0) goto L_0x0017
            r0 = r2
            io.ktor.routing.l$b r0 = (io.ktor.routing.l.b) r0
            int r3 = r0.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L_0x0017
            int r3 = r3 - r4
            r0.label = r3
            goto L_0x001c
        L_0x0017:
            io.ktor.routing.l$b r0 = new io.ktor.routing.l$b
            r0.<init>(r1, r2)
        L_0x001c:
            r3 = r0
            java.lang.Object r4 = r3.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r5 = r3.label
            switch(r5) {
                case 0: goto L_0x0072;
                case 1: goto L_0x0036;
                default: goto L_0x0028;
            }
        L_0x0028:
            r12 = r17
            r13 = r18
            r14 = r19
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r3 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r3)
            throw r0
        L_0x0036:
            r0 = 0
            r5 = 0
            r6 = r5
            r7 = r5
            r8 = r5
            r9 = r5
            java.lang.Object r10 = r3.L$8
            r8 = r10
            io.ktor.util.pipeline.c r8 = (io.ktor.util.pipeline.c) r8
            java.lang.Object r10 = r3.L$7
            r9 = r10
            io.ktor.routing.m r9 = (io.ktor.routing.m) r9
            java.lang.Object r10 = r3.L$6
            r7 = r10
            io.ktor.response.d r7 = (io.ktor.response.d) r7
            java.lang.Object r10 = r3.L$5
            r5 = r10
            io.ktor.request.b r5 = (io.ktor.request.b) r5
            java.lang.Object r10 = r3.L$4
            r6 = r10
            io.ktor.application.c r6 = (io.ktor.application.c) r6
            java.lang.Object r10 = r3.L$3
            io.ktor.http.y r10 = (io.ktor.http.y) r10
            java.lang.Object r11 = r3.L$2
            io.ktor.routing.i r11 = (io.ktor.routing.i) r11
            java.lang.Object r12 = r3.L$1
            io.ktor.util.pipeline.d r12 = (io.ktor.util.pipeline.d) r12
            java.lang.Object r13 = r3.L$0
            io.ktor.routing.l r13 = (io.ktor.routing.l) r13
            kotlin.p.b(r4)     // Catch:{ all -> 0x006b }
            r14 = r10
            goto L_0x013b
        L_0x006b:
            r0 = move-exception
            r8 = r0
            r14 = r10
            r0 = r13
            r13 = r11
            goto L_0x015e
        L_0x0072:
            kotlin.p.b(r4)
            io.ktor.application.c r6 = r18.D()
            r5 = r17
            r7 = 0
            java.lang.Object r8 = r5.getContext()
            io.ktor.application.b r8 = (io.ktor.application.b) r8
            io.ktor.request.d r5 = r8.getRequest()
            io.ktor.request.b r5 = r5.a()
            io.ktor.request.b r7 = r6.B()
            r8 = r16
            r9 = 0
            boolean r10 = r5.q()
            if (r10 == 0) goto L_0x0099
            goto L_0x00b1
        L_0x0099:
            boolean r10 = r7.q()
            if (r10 == 0) goto L_0x00a1
            r7 = r5
            goto L_0x00b1
        L_0x00a1:
            r10 = 0
            io.ktor.request.b r11 = new io.ktor.request.b
            r11.<init>()
            r10 = r11
            r12 = 0
            r10.r(r5)
            r10.r(r7)
            r7 = r11
        L_0x00b1:
            r5 = r7
            r7 = r17
            r8 = 0
            java.lang.Object r9 = r7.getContext()
            io.ktor.application.b r9 = (io.ktor.application.b) r9
            io.ktor.response.a r7 = r9.b()
            io.ktor.response.d r7 = r7.a()
            io.ktor.response.d r8 = r6.C()
            r9 = r16
            r10 = 0
            boolean r11 = r7.q()
            if (r11 == 0) goto L_0x00d2
            goto L_0x00ea
        L_0x00d2:
            boolean r11 = r8.q()
            if (r11 == 0) goto L_0x00da
            r8 = r7
            goto L_0x00ea
        L_0x00da:
            r11 = 0
            io.ktor.response.d r12 = new io.ktor.response.d
            r12.<init>()
            r11 = r12
            r13 = 0
            r11.r(r7)
            r11.r(r8)
            r8 = r12
        L_0x00ea:
            r7 = r8
            r8 = r17
            r9 = 0
            java.lang.Object r10 = r8.getContext()
            r9 = r10
            io.ktor.application.b r9 = (io.ktor.application.b) r9
            io.ktor.routing.m r14 = new io.ktor.routing.m
            r8 = r14
            r10 = r18
            r11 = r5
            r12 = r7
            r13 = r19
            r8.<init>(r9, r10, r11, r12, r13)
            r9 = r14
            io.ktor.application.a r8 = r1.H4
            io.ktor.application.d r8 = r8.F()
            io.ktor.application.e r8 = r8.b()
            io.ktor.application.j<io.ktor.routing.m> r10 = C4
            r8.a(r10, r9)
            r8 = r6
            r10 = 0
            kotlin.x r11 = kotlin.x.a     // Catch:{ all -> 0x0155 }
            r3.L$0 = r1     // Catch:{ all -> 0x0155 }
            r12 = r17
            r3.L$1 = r12     // Catch:{ all -> 0x0153 }
            r13 = r18
            r3.L$2 = r13     // Catch:{ all -> 0x0151 }
            r14 = r19
            r3.L$3 = r14     // Catch:{ all -> 0x014f }
            r3.L$4 = r6     // Catch:{ all -> 0x014f }
            r3.L$5 = r5     // Catch:{ all -> 0x014f }
            r3.L$6 = r7     // Catch:{ all -> 0x014f }
            r3.L$7 = r9     // Catch:{ all -> 0x014f }
            r3.L$8 = r8     // Catch:{ all -> 0x014f }
            r15 = 1
            r3.label = r15     // Catch:{ all -> 0x014f }
            java.lang.Object r11 = r8.e(r9, r11, r3)     // Catch:{ all -> 0x014f }
            if (r11 != r0) goto L_0x0138
            return r0
        L_0x0138:
            r0 = r10
            r11 = r13
            r13 = r1
        L_0x013b:
            io.ktor.application.a r0 = r13.H4
            io.ktor.application.d r0 = r0.F()
            io.ktor.application.e r0 = r0.b()
            io.ktor.application.j<io.ktor.routing.m> r8 = D4
            r0.a(r8, r9)
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x014f:
            r0 = move-exception
            goto L_0x015c
        L_0x0151:
            r0 = move-exception
            goto L_0x015a
        L_0x0153:
            r0 = move-exception
            goto L_0x0158
        L_0x0155:
            r0 = move-exception
            r12 = r17
        L_0x0158:
            r13 = r18
        L_0x015a:
            r14 = r19
        L_0x015c:
            r8 = r0
            r0 = r1
        L_0x015e:
            io.ktor.application.a r10 = r0.H4
            io.ktor.application.d r10 = r10.F()
            io.ktor.application.e r10 = r10.b()
            io.ktor.application.j<io.ktor.routing.m> r11 = D4
            r10.a(r11, r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.routing.l.M(io.ktor.util.pipeline.d, io.ktor.routing.i, io.ktor.http.y, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: Routing.kt */
    public static final class a implements io.ktor.application.f<io.ktor.application.a, l, l> {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public io.ktor.util.a<l> getKey() {
            return l.E4;
        }

        @f(c = "io.ktor.routing.Routing$Feature$install$1", f = "Routing.kt", l = {99}, m = "invokeSuspend")
        /* renamed from: io.ktor.routing.l$a$a  reason: collision with other inner class name */
        /* compiled from: Routing.kt */
        public static final class C0260a extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<x, io.ktor.application.b>, x, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ l $routing;
            Object L$0;
            Object L$1;
            int label;
            private io.ktor.util.pipeline.d p$;
            private x p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0260a(l lVar, kotlin.coroutines.d dVar) {
                super(3, dVar);
                this.$routing = lVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<x, io.ktor.application.b> dVar, @NotNull x xVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(xVar, "it");
                k.f(dVar2, "continuation");
                C0260a aVar = new C0260a(this.$routing, dVar2);
                aVar.p$ = dVar;
                aVar.p$0 = xVar;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((C0260a) create((io.ktor.util.pipeline.d) obj, (x) obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        p.b($result);
                        io.ktor.util.pipeline.d $this$intercept = this.p$;
                        x it = this.p$0;
                        l lVar = this.$routing;
                        this.L$0 = $this$intercept;
                        this.L$1 = it;
                        this.label = 1;
                        if (lVar.N($this$intercept, this) != d) {
                            io.ktor.util.pipeline.d dVar = $this$intercept;
                            x xVar = it;
                            break;
                        } else {
                            return d;
                        }
                    case 1:
                        x it2 = (x) this.L$1;
                        io.ktor.util.pipeline.d $this$intercept2 = (io.ktor.util.pipeline.d) this.L$0;
                        p.b($result);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                return x.a;
            }
        }

        @NotNull
        /* renamed from: b */
        public l a(@NotNull io.ktor.application.a pipeline, @NotNull kotlin.jvm.functions.l<? super l, x> configure) {
            k.f(pipeline, "pipeline");
            k.f(configure, "configure");
            l routing = new l(pipeline);
            configure.invoke(routing);
            pipeline.p(io.ktor.application.c.a2.a(), new C0260a(routing, (kotlin.coroutines.d) null));
            return routing;
        }
    }
}
