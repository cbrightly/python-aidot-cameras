package io.ktor.server.cio;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import io.ktor.application.i;
import io.ktor.http.cio.m;
import io.ktor.server.engine.h;
import io.ktor.server.engine.s;
import io.ktor.util.n;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CIOApplicationEngine.kt */
public final class c extends h {
    private final a c;
    private final int d;
    /* access modifiers changed from: private */
    public final kotlinx.coroutines.scheduling.d e;
    /* access modifiers changed from: private */
    public final n f;
    /* access modifiers changed from: private */
    public final z g = e2.b((z1) null, 1, (Object) null);
    /* access modifiers changed from: private */
    public final z1 h;
    private final o0 i;

    /* renamed from: io.ktor.server.cio.c$c  reason: collision with other inner class name */
    /* compiled from: CIOApplicationEngine.kt */
    public static final class C0267c extends l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0267c(c cVar) {
            super(1);
            this.this$0 = cVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            try {
                this.this$0.b().stop();
            } finally {
                this.this$0.f.W();
            }
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public c(@NotNull io.ktor.server.engine.b environment, @NotNull kotlin.jvm.functions.l<? super a, x> configure) {
        super(environment, (s) null, 2, (DefaultConstructorMarker) null);
        k.f(environment, "environment");
        k.f(configure, "configure");
        a aVar = new a();
        configure.invoke(aVar);
        this.c = aVar;
        int max = Math.max(aVar.b() + aVar.c(), environment.c().size() + 1);
        this.d = max;
        kotlinx.coroutines.scheduling.d dVar = new kotlinx.coroutines.scheduling.d(max, 0, (String) null, 6, (DefaultConstructorMarker) null);
        this.e = dVar;
        this.f = new n(dVar.W(aVar.a()));
        z1 d2 = j.d(p0.a(environment.e().plus(dVar)), (g) null, q0.LAZY, new b(this, environment, (kotlin.coroutines.d) null), 1, (Object) null);
        this.h = d2;
        this.i = p0.a(environment.e().plus(dVar).plus(d2));
    }

    /* compiled from: CIOApplicationEngine.kt */
    public static final class a extends h.c {
        private int e = 45;

        public final int d() {
            return this.e;
        }
    }

    @f(c = "io.ktor.server.cio.CIOApplicationEngine$serverJob$1", f = "CIOApplicationEngine.kt", l = {52, 71, 76}, m = "invokeSuspend")
    /* compiled from: CIOApplicationEngine.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ io.ktor.server.engine.b $environment;
        Object L$0;
        Object L$1;
        int label;
        private o0 p$;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(c cVar, io.ktor.server.engine.b bVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.this$0 = cVar;
            this.$environment = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            b bVar = new b(this.this$0, this.$environment, dVar);
            o0 o0Var = (o0) obj;
            bVar.p$ = (o0) obj;
            return bVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((b) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x005a, code lost:
            r4 = new java.util.ArrayList(r14.$environment.c().size());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r7 = r14.$environment.c().iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0079, code lost:
            if (r7.hasNext() == false) goto L_0x00aa;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x007b, code lost:
            r9 = (io.ktor.server.engine.r) r7.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0091, code lost:
            if (kotlin.jvm.internal.k.a(r9.getType(), io.ktor.server.engine.l.c.b()) != false) goto L_0x00a2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0093, code lost:
            r4.add(io.ktor.server.cio.c.i(r14.this$0, r9.a()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x00a9, code lost:
            throw new java.lang.UnsupportedOperationException("HTTPS is not supported");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x00aa, code lost:
            r5 = io.ktor.server.cio.c.g(r14.this$0);
            r14.L$0 = r1;
            r14.L$1 = r4;
            r14.label = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x00bd, code lost:
            if (r5.J(r14) != r0) goto L_0x00c0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x00bf, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00c0, code lost:
            r13 = r4;
            r4 = r1;
            r1 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x00c3, code lost:
            r7 = r1.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00cd, code lost:
            if (r7.hasNext() == false) goto L_0x00df;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00cf, code lost:
            kotlinx.coroutines.z1.a.a(((io.ktor.server.cio.f) r7.next()).a(), (java.util.concurrent.CancellationException) null, 1, (java.lang.Object) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00df, code lost:
            r2 = io.ktor.server.cio.c.h(r14.this$0);
            r5 = new io.ktor.server.cio.c.b.C0266b(r14, (kotlin.coroutines.d) null);
            r14.L$0 = r4;
            r14.L$1 = r1;
            r14.label = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00f6, code lost:
            if (kotlinx.coroutines.h.g(r2, r5, r14) != r0) goto L_0x00f9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00f8, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00f9, code lost:
            r0 = r1;
            r1 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00fd, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00fe, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ff, code lost:
            r7 = r4.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0109, code lost:
            if (r7.hasNext() != false) goto L_0x010b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x010b, code lost:
            kotlinx.coroutines.z1.a.a(((io.ktor.server.cio.f) r7.next()).b(), (java.util.concurrent.CancellationException) null, 1, (java.lang.Object) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x011b, code lost:
            io.ktor.server.cio.c.g(r14.this$0).a(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0125, code lost:
            throw r0;
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r15) {
            /*
                r14 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r14.label
                r2 = 1
                r3 = 0
                switch(r1) {
                    case 0: goto L_0x003f;
                    case 1: goto L_0x0035;
                    case 2: goto L_0x0024;
                    case 3: goto L_0x0013;
                    default: goto L_0x000b;
                }
            L_0x000b:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0013:
                r0 = r3
                r1 = r3
                java.lang.Object r2 = r14.L$1
                r0 = r2
                java.util.ArrayList r0 = (java.util.ArrayList) r0
                java.lang.Object r2 = r14.L$0
                r1 = r2
                kotlinx.coroutines.o0 r1 = (kotlinx.coroutines.o0) r1
                kotlin.p.b(r15)
                goto L_0x00fb
            L_0x0024:
                r1 = r3
                r4 = r3
                java.lang.Object r5 = r14.L$1
                r1 = r5
                java.util.ArrayList r1 = (java.util.ArrayList) r1
                java.lang.Object r5 = r14.L$0
                r4 = r5
                kotlinx.coroutines.o0 r4 = (kotlinx.coroutines.o0) r4
                kotlin.p.b(r15)
                goto L_0x00c3
            L_0x0035:
                r1 = r3
                java.lang.Object r4 = r14.L$0
                r1 = r4
                kotlinx.coroutines.o0 r1 = (kotlinx.coroutines.o0) r1
                kotlin.p.b(r15)
                goto L_0x005a
            L_0x003f:
                kotlin.p.b(r15)
                kotlinx.coroutines.o0 r1 = r14.p$
                io.ktor.server.cio.c r4 = r14.this$0
                io.ktor.util.n r4 = r4.f
                io.ktor.server.cio.c$b$a r5 = new io.ktor.server.cio.c$b$a
                r5.<init>(r14, r3)
                r14.L$0 = r1
                r14.label = r2
                java.lang.Object r4 = kotlinx.coroutines.h.g(r4, r5, r14)
                if (r4 != r0) goto L_0x005a
                return r0
            L_0x005a:
                java.util.ArrayList r4 = new java.util.ArrayList
                io.ktor.server.engine.b r5 = r14.$environment
                java.util.List r5 = r5.c()
                int r5 = r5.size()
                r4.<init>(r5)
                io.ktor.server.engine.b r5 = r14.$environment     // Catch:{ all -> 0x00fe }
                java.util.List r5 = r5.c()     // Catch:{ all -> 0x00fe }
                r6 = 0
                java.util.Iterator r7 = r5.iterator()     // Catch:{ all -> 0x00fe }
            L_0x0075:
                boolean r8 = r7.hasNext()     // Catch:{ all -> 0x00fe }
                if (r8 == 0) goto L_0x00aa
                java.lang.Object r8 = r7.next()     // Catch:{ all -> 0x00fe }
                r9 = r8
                io.ktor.server.engine.r r9 = (io.ktor.server.engine.r) r9     // Catch:{ all -> 0x00fe }
                r10 = 0
                io.ktor.server.engine.l r11 = r9.getType()     // Catch:{ all -> 0x00fe }
                io.ktor.server.engine.l$a r12 = io.ktor.server.engine.l.c     // Catch:{ all -> 0x00fe }
                io.ktor.server.engine.l r12 = r12.b()     // Catch:{ all -> 0x00fe }
                boolean r11 = kotlin.jvm.internal.k.a(r11, r12)     // Catch:{ all -> 0x00fe }
                if (r11 != 0) goto L_0x00a2
                io.ktor.server.cio.c r11 = r14.this$0     // Catch:{ all -> 0x00fe }
                int r12 = r9.a()     // Catch:{ all -> 0x00fe }
                io.ktor.server.cio.f r11 = r11.j(r12)     // Catch:{ all -> 0x00fe }
                r4.add(r11)     // Catch:{ all -> 0x00fe }
                goto L_0x0075
            L_0x00a2:
                java.lang.UnsupportedOperationException r0 = new java.lang.UnsupportedOperationException     // Catch:{ all -> 0x00fe }
                java.lang.String r7 = "HTTPS is not supported"
                r0.<init>(r7)     // Catch:{ all -> 0x00fe }
                throw r0     // Catch:{ all -> 0x00fe }
            L_0x00aa:
                io.ktor.server.cio.c r5 = r14.this$0
                kotlinx.coroutines.z r5 = r5.g
                r14.L$0 = r1
                r14.L$1 = r4
                r6 = 2
                r14.label = r6
                java.lang.Object r5 = r5.J(r14)
                if (r5 != r0) goto L_0x00c0
                return r0
            L_0x00c0:
                r13 = r4
                r4 = r1
                r1 = r13
            L_0x00c3:
                r5 = r1
                r6 = 0
                java.util.Iterator r7 = r5.iterator()
            L_0x00c9:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x00df
                java.lang.Object r8 = r7.next()
                r9 = r8
                io.ktor.server.cio.f r9 = (io.ktor.server.cio.f) r9
                r10 = 0
                kotlinx.coroutines.z1 r11 = r9.a()
                kotlinx.coroutines.z1.a.a(r11, r3, r2, r3)
                goto L_0x00c9
            L_0x00df:
                io.ktor.server.cio.c r2 = r14.this$0
                io.ktor.util.n r2 = r2.f
                io.ktor.server.cio.c$b$b r5 = new io.ktor.server.cio.c$b$b
                r5.<init>(r14, r3)
                r14.L$0 = r4
                r14.L$1 = r1
                r3 = 3
                r14.label = r3
                java.lang.Object r2 = kotlinx.coroutines.h.g(r2, r5, r14)
                if (r2 != r0) goto L_0x00f9
                return r0
            L_0x00f9:
                r0 = r1
                r1 = r4
            L_0x00fb:
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x00fe:
                r0 = move-exception
                r5 = r4
                r6 = 0
                java.util.Iterator r7 = r5.iterator()
            L_0x0105:
                boolean r8 = r7.hasNext()
                if (r8 == 0) goto L_0x011b
                java.lang.Object r8 = r7.next()
                r9 = r8
                io.ktor.server.cio.f r9 = (io.ktor.server.cio.f) r9
                r10 = 0
                kotlinx.coroutines.z1 r11 = r9.b()
                kotlinx.coroutines.z1.a.a(r11, r3, r2, r3)
                goto L_0x0105
            L_0x011b:
                io.ktor.server.cio.c r2 = r14.this$0
                kotlinx.coroutines.z r2 = r2.g
                r2.a(r0)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.c.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @f(c = "io.ktor.server.cio.CIOApplicationEngine$serverJob$1$1", f = "CIOApplicationEngine.kt", l = {}, m = "invokeSuspend")
        /* compiled from: CIOApplicationEngine.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
            int label;
            private o0 p$;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar, kotlin.coroutines.d dVar) {
                super(2, dVar);
                this.this$0 = bVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                k.f(dVar, "completion");
                a aVar = new a(this.this$0, dVar);
                o0 o0Var = (o0) obj;
                aVar.p$ = (o0) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((a) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        o0 o0Var = this.p$;
                        this.this$0.$environment.start();
                        return x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        @f(c = "io.ktor.server.cio.CIOApplicationEngine$serverJob$1$5", f = "CIOApplicationEngine.kt", l = {}, m = "invokeSuspend")
        /* renamed from: io.ktor.server.cio.c$b$b  reason: collision with other inner class name */
        /* compiled from: CIOApplicationEngine.kt */
        public static final class C0266b extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
            int label;
            private o0 p$;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0266b(b bVar, kotlin.coroutines.d dVar) {
                super(2, dVar);
                this.this$0 = bVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                k.f(dVar, "completion");
                C0266b bVar = new C0266b(this.this$0, dVar);
                o0 o0Var = (o0) obj;
                bVar.p$ = (o0) obj;
                return bVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((C0266b) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        o0 o0Var = this.p$;
                        this.this$0.$environment.b().a(i.c(), this.this$0.$environment);
                        return x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    @NotNull
    public io.ktor.server.engine.a a(boolean wait) {
        this.h.start();
        this.h.t(new C0267c(this));
        if (wait) {
            Object unused = kotlinx.coroutines.i.b((g) null, new d(this, (kotlin.coroutines.d) null), 1, (Object) null);
        }
        return this;
    }

    @f(c = "io.ktor.server.cio.CIOApplicationEngine$start$2", f = "CIOApplicationEngine.kt", l = {96}, m = "invokeSuspend")
    /* compiled from: CIOApplicationEngine.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        Object L$0;
        int label;
        private o0 p$;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(c cVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.this$0 = cVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            d dVar2 = new d(this.this$0, dVar);
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
                    o0 $this$runBlocking = this.p$;
                    z1 f = this.this$0.h;
                    this.L$0 = $this$runBlocking;
                    this.label = 1;
                    if (f.J(this) != d) {
                        o0 o0Var = $this$runBlocking;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    o0 $this$runBlocking2 = (o0) this.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    /* access modifiers changed from: private */
    public final f j(int port) {
        return io.ktor.server.cio.backend.a.a(this.i, new g((String) null, port, (long) this.c.d(), 1, (DefaultConstructorMarker) null), new e(this, (kotlin.coroutines.d) null));
    }

    @f(c = "io.ktor.server.cio.CIOApplicationEngine$startConnector$server$1", f = "CIOApplicationEngine.kt", l = {147}, m = "invokeSuspend")
    /* compiled from: CIOApplicationEngine.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.server.cio.backend.d, m, kotlin.coroutines.d<? super x>, Object> {
        Object L$0;
        Object L$1;
        int label;
        private io.ktor.server.cio.backend.d p$;
        private m p$0;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(c cVar, kotlin.coroutines.d dVar) {
            super(3, dVar);
            this.this$0 = cVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull io.ktor.server.cio.backend.d dVar, @NotNull m mVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
            k.f(dVar, "$this$create");
            k.f(mVar, Progress.REQUEST);
            k.f(dVar2, "continuation");
            e eVar = new e(this.this$0, dVar2);
            eVar.p$ = dVar;
            eVar.p$0 = mVar;
            return eVar;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((e) create((io.ktor.server.cio.backend.d) obj, (m) obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
        }

        @f(c = "io.ktor.server.cio.CIOApplicationEngine$startConnector$server$1$1", f = "CIOApplicationEngine.kt", l = {167}, m = "invokeSuspend")
        /* compiled from: CIOApplicationEngine.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ m $request;
            final /* synthetic */ io.ktor.server.cio.backend.d $this_httpServer;
            Object L$0;
            Object L$1;
            Object L$2;
            int label;
            private o0 p$;
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(e eVar, io.ktor.server.cio.backend.d dVar, m mVar, kotlin.coroutines.d dVar2) {
                super(2, dVar2);
                this.this$0 = eVar;
                this.$this_httpServer = dVar;
                this.$request = mVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                k.f(dVar, "completion");
                a aVar = new a(this.this$0, this.$this_httpServer, this.$request, dVar);
                o0 o0Var = (o0) obj;
                aVar.p$ = (o0) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((a) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
            }

            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: io.ktor.server.cio.b} */
            /* JADX WARNING: Multi-variable type inference failed */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r14) {
                /*
                    r13 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r13.label
                    switch(r1) {
                        case 0: goto L_0x0028;
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
                    r2 = r0
                    r3 = 0
                    java.lang.Object r4 = r13.L$2
                    r0 = r4
                    io.ktor.util.pipeline.c r0 = (io.ktor.util.pipeline.c) r0
                    java.lang.Object r4 = r13.L$1
                    r1 = r4
                    io.ktor.server.cio.b r1 = (io.ktor.server.cio.b) r1
                    java.lang.Object r4 = r13.L$0
                    r2 = r4
                    kotlinx.coroutines.o0 r2 = (kotlinx.coroutines.o0) r2
                    kotlin.p.b(r14)     // Catch:{ all -> 0x0092 }
                    goto L_0x0089
                L_0x0028:
                    kotlin.p.b(r14)
                    kotlinx.coroutines.o0 r2 = r13.p$
                    io.ktor.server.cio.b r1 = new io.ktor.server.cio.b
                    io.ktor.server.cio.c$e r3 = r13.this$0
                    io.ktor.server.cio.c r3 = r3.this$0
                    io.ktor.application.a r4 = r3.c()
                    io.ktor.http.cio.m r5 = r13.$request
                    io.ktor.server.cio.backend.d r3 = r13.$this_httpServer
                    io.ktor.utils.io.i r6 = r3.a()
                    io.ktor.server.cio.backend.d r3 = r13.$this_httpServer
                    io.ktor.utils.io.l r7 = r3.c()
                    io.ktor.server.cio.c$e r3 = r13.this$0
                    io.ktor.server.cio.c r3 = r3.this$0
                    kotlinx.coroutines.scheduling.d r8 = r3.e
                    io.ktor.server.cio.c$e r3 = r13.this$0
                    io.ktor.server.cio.c r3 = r3.this$0
                    io.ktor.util.n r9 = r3.f
                    io.ktor.server.cio.backend.d r3 = r13.$this_httpServer
                    kotlinx.coroutines.w r10 = r3.e()
                    io.ktor.server.cio.backend.d r3 = r13.$this_httpServer
                    java.net.SocketAddress r11 = r3.d()
                    io.ktor.server.cio.backend.d r3 = r13.$this_httpServer
                    java.net.SocketAddress r12 = r3.b()
                    r3 = r1
                    r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12)
                    io.ktor.server.cio.c$e r3 = r13.this$0     // Catch:{ all -> 0x0092 }
                    io.ktor.server.cio.c r3 = r3.this$0     // Catch:{ all -> 0x0092 }
                    io.ktor.server.engine.s r3 = r3.d()     // Catch:{ all -> 0x0092 }
                    r4 = 0
                    kotlin.x r5 = kotlin.x.a     // Catch:{ all -> 0x0092 }
                    r13.L$0 = r2     // Catch:{ all -> 0x0092 }
                    r13.L$1 = r1     // Catch:{ all -> 0x0092 }
                    r13.L$2 = r3     // Catch:{ all -> 0x0092 }
                    r6 = 1
                    r13.label = r6     // Catch:{ all -> 0x0092 }
                    java.lang.Object r5 = r3.e(r1, r5, r13)     // Catch:{ all -> 0x0092 }
                    if (r5 != r0) goto L_0x0087
                    return r0
                L_0x0087:
                    r0 = r3
                    r3 = r4
                L_0x0089:
                    r1.i()
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0092:
                    r0 = move-exception
                    r1.i()
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.c.e.a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    io.ktor.server.cio.backend.d $this$httpServer = this.p$;
                    m request = this.p$0;
                    n h = this.this$0.f;
                    a aVar = new a(this, $this$httpServer, request, (kotlin.coroutines.d) null);
                    this.L$0 = $this$httpServer;
                    this.L$1 = request;
                    this.label = 1;
                    if (kotlinx.coroutines.h.g(h, aVar, this) != d) {
                        m mVar = request;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    m request2 = (m) this.L$1;
                    io.ktor.server.cio.backend.d $this$httpServer2 = (io.ktor.server.cio.backend.d) this.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }
}
