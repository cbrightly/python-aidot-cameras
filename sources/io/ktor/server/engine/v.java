package io.ktor.server.engine;

import io.ktor.application.i;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ShutDownUrl.kt */
public final class v {
    @NotNull
    private final String a;
    @NotNull
    private final l<io.ktor.application.b, Integer> b;

    @f(c = "io.ktor.server.engine.ShutDownUrl", f = "ShutDownUrl.kt", l = {110}, m = "doShutdown")
    /* compiled from: ShutDownUrl.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ v this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(v vVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = vVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a((io.ktor.application.b) null, this);
        }
    }

    public v(@NotNull String url, @NotNull l<? super io.ktor.application.b, Integer> exitCode) {
        k.f(url, "url");
        k.f(exitCode, "exitCode");
        this.a = url;
        this.b = exitCode;
    }

    @NotNull
    public final String b() {
        return this.a;
    }

    /* Debug info: failed to restart local var, previous not found, register: 22 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: kotlinx.coroutines.w} */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00e2, code lost:
        kotlinx.coroutines.z1.a.a(r10, (java.util.concurrent.CancellationException) null, 1, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00eb, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(@org.jetbrains.annotations.NotNull io.ktor.application.b r23, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r24) {
        /*
            r22 = this;
            r1 = r22
            r2 = r23
            r3 = r24
            boolean r0 = r3 instanceof io.ktor.server.engine.v.c
            if (r0 == 0) goto L_0x0019
            r0 = r3
            io.ktor.server.engine.v$c r0 = (io.ktor.server.engine.v.c) r0
            int r4 = r0.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r6 = r4 & r5
            if (r6 == 0) goto L_0x0019
            int r4 = r4 - r5
            r0.label = r4
            goto L_0x001e
        L_0x0019:
            io.ktor.server.engine.v$c r0 = new io.ktor.server.engine.v$c
            r0.<init>(r1, r3)
        L_0x001e:
            r4 = r0
            java.lang.Object r5 = r4.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r6 = r4.label
            r7 = 1
            r8 = 0
            switch(r6) {
                case 0: goto L_0x0067;
                case 1: goto L_0x0034;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r4)
            throw r0
        L_0x0034:
            r0 = r8
            r6 = 0
            r9 = r6
            r10 = r8
            r11 = r8
            r12 = r8
            r13 = r8
            java.lang.Object r14 = r4.L$6
            r11 = r14
            io.ktor.http.v r11 = (io.ktor.http.v) r11
            java.lang.Object r14 = r4.L$5
            r12 = r14
            io.ktor.application.b r12 = (io.ktor.application.b) r12
            java.lang.Object r14 = r4.L$4
            r10 = r14
            kotlinx.coroutines.w r10 = (kotlinx.coroutines.w) r10
            int r6 = r4.I$0
            java.lang.Object r14 = r4.L$3
            r13 = r14
            io.ktor.application.d r13 = (io.ktor.application.d) r13
            java.lang.Object r14 = r4.L$2
            io.ktor.application.a r14 = (io.ktor.application.a) r14
            java.lang.Object r0 = r4.L$1
            r2 = r0
            io.ktor.application.b r2 = (io.ktor.application.b) r2
            java.lang.Object r0 = r4.L$0
            r15 = r0
            io.ktor.server.engine.v r15 = (io.ktor.server.engine.v) r15
            kotlin.p.b(r5)     // Catch:{ all -> 0x0064 }
            goto L_0x00e2
        L_0x0064:
            r0 = move-exception
            goto L_0x00f2
        L_0x0067:
            kotlin.p.b(r5)
            io.ktor.application.a r6 = r23.a()
            org.slf4j.b r6 = io.ktor.application.h.a(r6)
            java.lang.String r9 = "Shutdown URL was called: server is going down"
            r6.warn(r9)
            io.ktor.application.a r6 = r23.a()
            io.ktor.application.d r9 = r6.F()
            kotlin.jvm.functions.l<io.ktor.application.b, java.lang.Integer> r10 = r1.b
            java.lang.Object r10 = r10.invoke(r2)
            java.lang.Number r10 = (java.lang.Number) r10
            int r15 = r10.intValue()
            kotlinx.coroutines.w r14 = kotlinx.coroutines.y.b(r8, r7, r8)
            io.ktor.application.a r16 = r23.a()
            r17 = 0
            r18 = 0
            io.ktor.server.engine.v$d r19 = new io.ktor.server.engine.v$d
            r20 = 0
            r10 = r19
            r11 = r14
            r12 = r9
            r13 = r6
            r8 = r14
            r14 = r15
            r7 = r15
            r15 = r20
            r10.<init>(r11, r12, r13, r14, r15)
            r20 = 3
            r21 = 0
            kotlinx.coroutines.z1 unused = kotlinx.coroutines.j.d(r16, r17, r18, r19, r20, r21)
            io.ktor.http.v$a r10 = io.ktor.http.v.c0     // Catch:{ all -> 0x00ec }
            io.ktor.http.v r10 = r10.l()     // Catch:{ all -> 0x00ec }
            r11 = r10
            r12 = r23
            r10 = 0
            io.ktor.response.a r13 = r12.b()     // Catch:{ all -> 0x00ec }
            io.ktor.response.d r13 = r13.a()     // Catch:{ all -> 0x00ec }
            r4.L$0 = r1     // Catch:{ all -> 0x00ec }
            r4.L$1 = r2     // Catch:{ all -> 0x00ec }
            r4.L$2 = r6     // Catch:{ all -> 0x00ec }
            r4.L$3 = r9     // Catch:{ all -> 0x00ec }
            r4.I$0 = r7     // Catch:{ all -> 0x00ec }
            r4.L$4 = r8     // Catch:{ all -> 0x00ec }
            r4.L$5 = r12     // Catch:{ all -> 0x00ec }
            r4.L$6 = r11     // Catch:{ all -> 0x00ec }
            r14 = 1
            r4.label = r14     // Catch:{ all -> 0x00ec }
            java.lang.Object r13 = r13.e(r12, r11, r4)     // Catch:{ all -> 0x00ec }
            if (r13 != r0) goto L_0x00dc
            return r0
        L_0x00dc:
            r15 = r1
            r14 = r6
            r6 = r7
            r13 = r9
            r9 = r10
            r10 = r8
        L_0x00e2:
            r7 = 1
            r8 = 0
            kotlinx.coroutines.z1.a.a(r10, r8, r7, r8)
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x00ec:
            r0 = move-exception
            r15 = r1
            r14 = r6
            r6 = r7
            r10 = r8
            r13 = r9
        L_0x00f2:
            r7 = 1
            r8 = 0
            kotlinx.coroutines.z1.a.a(r10, r8, r7, r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.v.a(io.ktor.application.b, kotlin.coroutines.d):java.lang.Object");
    }

    @f(c = "io.ktor.server.engine.ShutDownUrl$doShutdown$2", f = "ShutDownUrl.kt", l = {33}, m = "invokeSuspend")
    /* compiled from: ShutDownUrl.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ io.ktor.application.a $application;
        final /* synthetic */ io.ktor.application.d $environment;
        final /* synthetic */ int $exitCode;
        final /* synthetic */ w $latch;
        Object L$0;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(w wVar, io.ktor.application.d dVar, io.ktor.application.a aVar, int i, kotlin.coroutines.d dVar2) {
            super(2, dVar2);
            this.$latch = wVar;
            this.$environment = dVar;
            this.$application = aVar;
            this.$exitCode = i;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            d dVar2 = new d(this.$latch, this.$environment, this.$application, this.$exitCode, dVar);
            o0 o0Var = (o0) obj;
            dVar2.p$ = (o0) obj;
            return dVar2;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((d) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o0 $this$launch = this.p$;
                    w wVar = this.$latch;
                    this.L$0 = $this$launch;
                    this.label = 1;
                    if (wVar.J(this) != d) {
                        o0 o0Var = $this$launch;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    o0 $this$launch2 = (o0) this.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this.$environment.b().a(i.c(), this.$environment);
            io.ktor.application.d dVar = this.$environment;
            if (dVar instanceof b) {
                ((b) dVar).stop();
            } else {
                this.$application.D();
            }
            System.exit(this.$exitCode);
            throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
        }
    }

    /* compiled from: ShutDownUrl.kt */
    public static final class b implements io.ktor.application.f<s, a, v> {
        @NotNull
        private static final io.ktor.util.a<v> a = new io.ktor.util.a<>("shutdown.url");
        public static final b b = new b();

        private b() {
        }

        @NotNull
        public io.ktor.util.a<v> getKey() {
            return a;
        }

        @NotNull
        /* renamed from: b */
        public v a(@NotNull s pipeline, @NotNull l<? super a, x> configure) {
            k.f(pipeline, "pipeline");
            k.f(configure, "configure");
            a config = new a();
            configure.invoke(config);
            v feature = new v(config.b(), config.a());
            pipeline.p(s.p0.a(), new a(feature, (kotlin.coroutines.d) null));
            return feature;
        }

        @f(c = "io.ktor.server.engine.ShutDownUrl$EngineFeature$install$1", f = "ShutDownUrl.kt", l = {111}, m = "invokeSuspend")
        /* compiled from: ShutDownUrl.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<x, io.ktor.application.b>, x, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ v $feature;
            Object L$0;
            Object L$1;
            int label;
            private io.ktor.util.pipeline.d p$;
            private x p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(v vVar, kotlin.coroutines.d dVar) {
                super(3, dVar);
                this.$feature = vVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<x, io.ktor.application.b> dVar, @NotNull x xVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(xVar, "it");
                k.f(dVar2, "continuation");
                a aVar = new a(this.$feature, dVar2);
                aVar.p$ = dVar;
                aVar.p$0 = xVar;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((a) create((io.ktor.util.pipeline.d) obj, (x) obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
            }

            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: kotlin.x} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: io.ktor.util.pipeline.d} */
            /* JADX WARNING: Can't fix incorrect switch cases order */
            /* JADX WARNING: Multi-variable type inference failed */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
                /*
                    r7 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r7.label
                    switch(r1) {
                        case 0: goto L_0x0021;
                        case 1: goto L_0x0011;
                        default: goto L_0x0009;
                    }
                L_0x0009:
                    java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                    java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                    r0.<init>(r1)
                    throw r0
                L_0x0011:
                    r0 = 0
                    r1 = r0
                    java.lang.Object r2 = r7.L$1
                    r1 = r2
                    kotlin.x r1 = (kotlin.x) r1
                    java.lang.Object r2 = r7.L$0
                    r0 = r2
                    io.ktor.util.pipeline.d r0 = (io.ktor.util.pipeline.d) r0
                    kotlin.p.b(r8)
                    goto L_0x005e
                L_0x0021:
                    kotlin.p.b(r8)
                    io.ktor.util.pipeline.d r1 = r7.p$
                    kotlin.x r2 = r7.p$0
                    r3 = r1
                    r4 = 0
                    java.lang.Object r5 = r3.getContext()
                    io.ktor.application.b r5 = (io.ktor.application.b) r5
                    io.ktor.request.d r3 = r5.getRequest()
                    java.lang.String r3 = io.ktor.request.e.e(r3)
                    io.ktor.server.engine.v r4 = r7.$feature
                    java.lang.String r4 = r4.b()
                    boolean r3 = kotlin.jvm.internal.k.a(r3, r4)
                    if (r3 == 0) goto L_0x0060
                    io.ktor.server.engine.v r3 = r7.$feature
                    r4 = r1
                    r5 = 0
                    java.lang.Object r6 = r4.getContext()
                    io.ktor.application.b r6 = (io.ktor.application.b) r6
                    r7.L$0 = r1
                    r7.L$1 = r2
                    r4 = 1
                    r7.label = r4
                    java.lang.Object r3 = r3.a(r6, r7)
                    if (r3 != r0) goto L_0x005c
                    return r0
                L_0x005c:
                    r0 = r1
                    r1 = r2
                L_0x005e:
                    r2 = r1
                    r1 = r0
                L_0x0060:
                    kotlin.x r0 = kotlin.x.a
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.v.b.a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }

    /* compiled from: ShutDownUrl.kt */
    public static final class a {
        @NotNull
        private String a = "/ktor/application/shutdown";
        @NotNull
        private l<? super io.ktor.application.b, Integer> b = C0273a.INSTANCE;

        /* renamed from: io.ktor.server.engine.v$a$a  reason: collision with other inner class name */
        /* compiled from: ShutDownUrl.kt */
        public static final class C0273a extends kotlin.jvm.internal.l implements l<io.ktor.application.b, Integer> {
            public static final C0273a INSTANCE = new C0273a();

            C0273a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Integer.valueOf(invoke((io.ktor.application.b) obj));
            }

            public final int invoke(@NotNull io.ktor.application.b $receiver) {
                k.f($receiver, "$receiver");
                return 0;
            }
        }

        @NotNull
        public final String b() {
            return this.a;
        }

        public final void c(@NotNull String str) {
            k.f(str, "<set-?>");
            this.a = str;
        }

        @NotNull
        public final l<io.ktor.application.b, Integer> a() {
            return this.b;
        }
    }
}
