package io.ktor.features;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CallLogging.kt */
public final class c {
    /* access modifiers changed from: private */
    @NotNull
    public static final io.ktor.util.a<c> a = new io.ktor.util.a<>("Call Logging");
    public static final C0242c b = new C0242c((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public final l<io.ktor.application.a, x> c;
    /* access modifiers changed from: private */
    public final l<io.ktor.application.a, x> d;
    /* access modifiers changed from: private */
    public final l<io.ktor.application.a, x> e;
    /* access modifiers changed from: private */
    public l<? super io.ktor.application.a, x> f;
    private final org.slf4j.b g;
    /* access modifiers changed from: private */
    public final io.ktor.application.e h;
    private final org.slf4j.event.b i;
    private final List<l<io.ktor.application.b, Boolean>> j;
    /* access modifiers changed from: private */
    public final List<e> k;
    private final l<io.ktor.application.b, String> l;

    /* compiled from: CallLogging.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<io.ktor.application.a, x> {
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(c cVar) {
            super(1);
            this.this$0 = cVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((io.ktor.application.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull io.ktor.application.a it) {
            k.f(it, "it");
            c cVar = this.this$0;
            cVar.k("Application stopped: " + it);
            this.this$0.h.c(io.ktor.application.i.b(), this.this$0.c);
            this.this$0.h.c(io.ktor.application.i.a(), this.this$0.d);
            this.this$0.h.c(io.ktor.application.i.e(), this.this$0.e);
            this.this$0.h.c(io.ktor.application.i.d(), this.this$0.f);
        }
    }

    /* compiled from: CallLogging.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<io.ktor.application.a, x> {
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(c cVar) {
            super(1);
            this.this$0 = cVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((io.ktor.application.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull io.ktor.application.a it) {
            k.f(it, "it");
            c cVar = this.this$0;
            cVar.k("Application started: " + it);
        }
    }

    /* compiled from: CallLogging.kt */
    public static final class g extends kotlin.jvm.internal.l implements l<io.ktor.application.a, x> {
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(c cVar) {
            super(1);
            this.this$0 = cVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((io.ktor.application.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull io.ktor.application.a it) {
            k.f(it, "it");
            c cVar = this.this$0;
            cVar.k("Application starting: " + it);
        }
    }

    /* compiled from: CallLogging.kt */
    public static final class h extends kotlin.jvm.internal.l implements l<io.ktor.application.a, x> {
        public static final h INSTANCE = new h();

        h() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((io.ktor.application.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull io.ktor.application.a it) {
            k.f(it, "it");
        }
    }

    /* compiled from: CallLogging.kt */
    public static final class i extends kotlin.jvm.internal.l implements l<io.ktor.application.a, x> {
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(c cVar) {
            super(1);
            this.this$0 = cVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((io.ktor.application.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull io.ktor.application.a it) {
            k.f(it, "it");
            c cVar = this.this$0;
            cVar.k("Application stopping: " + it);
        }
    }

    private c(org.slf4j.b bVar, io.ktor.application.e eVar, org.slf4j.event.b bVar2, List<? extends l<? super io.ktor.application.b, Boolean>> list, List<e> list2, l<? super io.ktor.application.b, String> lVar) {
        this.g = bVar;
        this.h = eVar;
        this.i = bVar2;
        this.j = list;
        this.k = list2;
        this.l = lVar;
        g gVar = new g(this);
        this.c = gVar;
        f fVar = new f(this);
        this.d = fVar;
        i iVar = new i(this);
        this.e = iVar;
        this.f = h.INSTANCE;
        this.f = new a(this);
        eVar.b(io.ktor.application.i.b(), gVar);
        eVar.b(io.ktor.application.i.a(), fVar);
        eVar.b(io.ktor.application.i.e(), iVar);
        eVar.b(io.ktor.application.i.d(), this.f);
    }

    public /* synthetic */ c(org.slf4j.b log, io.ktor.application.e monitor, org.slf4j.event.b level, List filters, List mdcEntries, l formatCall, DefaultConstructorMarker $constructor_marker) {
        this(log, monitor, level, filters, mdcEntries, formatCall);
    }

    /* compiled from: CallLogging.kt */
    public static final class e {
        @NotNull
        private final String a;
        @NotNull
        private final l<io.ktor.application.b, String> b;

        @NotNull
        public final String a() {
            return this.a;
        }

        @NotNull
        public final l<io.ktor.application.b, String> b() {
            return this.b;
        }
    }

    /* compiled from: CallLogging.kt */
    public static final class b {
        @NotNull
        private final List<l<io.ktor.application.b, Boolean>> a = new ArrayList();
        @NotNull
        private final List<e> b = new ArrayList();
        @NotNull
        private l<? super io.ktor.application.b, String> c = a.INSTANCE;
        @NotNull
        private org.slf4j.event.b d = org.slf4j.event.b.TRACE;
        @Nullable
        private org.slf4j.b e;

        @NotNull
        public final List<l<io.ktor.application.b, Boolean>> a() {
            return this.a;
        }

        /* compiled from: CallLogging.kt */
        public static final /* synthetic */ class a extends kotlin.jvm.internal.h implements l<io.ktor.application.b, String> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            public final String getName() {
                return "defaultFormat";
            }

            public final kotlin.reflect.e getOwner() {
                return a0.d(e.class, "ktor-server-core");
            }

            public final String getSignature() {
                return "defaultFormat(Lio/ktor/application/ApplicationCall;)Ljava/lang/String;";
            }

            @NotNull
            public final String invoke(@NotNull io.ktor.application.b p1) {
                k.f(p1, "p1");
                return e.b(p1);
            }
        }

        @NotNull
        public final List<e> e() {
            return this.b;
        }

        @NotNull
        public final l<io.ktor.application.b, String> b() {
            return this.c;
        }

        @NotNull
        public final org.slf4j.event.b c() {
            return this.d;
        }

        @Nullable
        public final org.slf4j.b d() {
            return this.e;
        }
    }

    @NotNull
    public final Map<String, String> m(@NotNull io.ktor.application.b call) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        HashMap result = new HashMap();
        for (e entry : this.k) {
            String mdcValue = entry.b().invoke(call);
            if (mdcValue != null) {
                result.put(entry.a(), mdcValue);
            }
        }
        return result;
    }

    public final void j() {
        for (e it : this.k) {
            org.slf4j.d.e(it.a());
        }
    }

    /* renamed from: io.ktor.features.c$c  reason: collision with other inner class name */
    /* compiled from: CallLogging.kt */
    public static final class C0242c implements io.ktor.application.f<io.ktor.application.a, b, c> {
        private C0242c() {
        }

        public /* synthetic */ C0242c(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public io.ktor.util.a<c> getKey() {
            return c.a;
        }

        @NotNull
        /* renamed from: b */
        public c a(@NotNull io.ktor.application.a pipeline, @NotNull l<? super b, x> configure) {
            k.f(pipeline, "pipeline");
            k.f(configure, "configure");
            io.ktor.util.pipeline.g loggingPhase = new io.ktor.util.pipeline.g("Logging");
            b configuration = new b();
            configure.invoke(configuration);
            org.slf4j.b d = configuration.d();
            if (d == null) {
                d = io.ktor.application.h.a(pipeline);
            }
            c feature = new c(d, pipeline.F().b(), configuration.c(), y.C0(configuration.a()), y.C0(configuration.e()), configuration.b(), (DefaultConstructorMarker) null);
            pipeline.n(io.ktor.application.c.a2.c(), loggingPhase);
            if (!feature.k.isEmpty()) {
                pipeline.p(loggingPhase, new a(feature, (kotlin.coroutines.d) null));
            } else {
                pipeline.p(loggingPhase, new b(feature, (kotlin.coroutines.d) null));
            }
            return feature;
        }

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.CallLogging$Feature$install$1", f = "CallLogging.kt", l = {133, 230}, m = "invokeSuspend")
        /* renamed from: io.ktor.features.c$c$a */
        /* compiled from: CallLogging.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<x, io.ktor.application.b>, x, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ c $feature;
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            int label;
            private io.ktor.util.pipeline.d p$;
            private x p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(c cVar, kotlin.coroutines.d dVar) {
                super(3, dVar);
                this.$feature = cVar;
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

            @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.CallLogging$Feature$install$1$invokeSuspend$$inlined$withMDC$1", f = "CallLogging.kt", l = {226}, m = "invokeSuspend")
            /* renamed from: io.ktor.features.c$c$a$a  reason: collision with other inner class name */
            /* compiled from: CallLogging.kt */
            public static final class C0243a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
                final /* synthetic */ c $feature;
                final /* synthetic */ io.ktor.util.pipeline.d $this_withMDC;
                Object L$0;
                Object L$1;
                Object L$2;
                int label;
                private o0 p$;
                final /* synthetic */ a this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                public C0243a(io.ktor.util.pipeline.d dVar, c cVar, kotlin.coroutines.d dVar2, a aVar) {
                    super(2, dVar2);
                    this.$this_withMDC = dVar;
                    this.$feature = cVar;
                    this.this$0 = aVar;
                }

                @NotNull
                public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                    k.f(dVar, "completion");
                    C0243a aVar = new C0243a(this.$this_withMDC, this.$feature, dVar, this.this$0);
                    o0 o0Var = (o0) obj;
                    aVar.p$ = (o0) obj;
                    return aVar;
                }

                public final Object invoke(Object obj, Object obj2) {
                    return ((C0243a) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
                }

                /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
                /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: io.ktor.util.pipeline.d} */
                /* JADX WARNING: Multi-variable type inference failed */
                @org.jetbrains.annotations.Nullable
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
                    /*
                        r8 = this;
                        java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                        int r1 = r8.label
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
                        r1 = 0
                        r2 = r1
                        r3 = r1
                        java.lang.Object r4 = r8.L$2
                        r1 = r4
                        io.ktor.util.pipeline.d r1 = (io.ktor.util.pipeline.d) r1
                        java.lang.Object r4 = r8.L$1
                        r2 = r4
                        kotlin.coroutines.d r2 = (kotlin.coroutines.d) r2
                        java.lang.Object r4 = r8.L$0
                        r3 = r4
                        kotlinx.coroutines.o0 r3 = (kotlinx.coroutines.o0) r3
                        kotlin.p.b(r9)     // Catch:{ all -> 0x005c }
                        goto L_0x0043
                    L_0x0028:
                        kotlin.p.b(r9)
                        kotlinx.coroutines.o0 r3 = r8.p$
                        io.ktor.util.pipeline.d r1 = r8.$this_withMDC     // Catch:{ all -> 0x005c }
                        r2 = r8
                        r4 = 0
                        r8.L$0 = r3     // Catch:{ all -> 0x005c }
                        r8.L$1 = r2     // Catch:{ all -> 0x005c }
                        r8.L$2 = r1     // Catch:{ all -> 0x005c }
                        r5 = 1
                        r8.label = r5     // Catch:{ all -> 0x005c }
                        java.lang.Object r5 = r1.j(r8)     // Catch:{ all -> 0x005c }
                        if (r5 != r0) goto L_0x0042
                        return r0
                    L_0x0042:
                        r0 = r4
                    L_0x0043:
                        io.ktor.features.c$c$a r4 = r8.this$0     // Catch:{ all -> 0x005c }
                        io.ktor.features.c r4 = r4.$feature     // Catch:{ all -> 0x005c }
                        r5 = r1
                        r6 = 0
                        java.lang.Object r7 = r5.getContext()     // Catch:{ all -> 0x005c }
                        io.ktor.application.b r7 = (io.ktor.application.b) r7     // Catch:{ all -> 0x005c }
                        r4.l(r7)     // Catch:{ all -> 0x005c }
                        io.ktor.features.c r0 = r8.$feature
                        r0.j()
                        kotlin.x r0 = kotlin.x.a
                        return r0
                    L_0x005c:
                        r0 = move-exception
                        io.ktor.features.c r1 = r8.$feature
                        r1.j()
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.c.C0242c.a.C0243a.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }

            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: io.ktor.util.pipeline.d} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v8, resolved type: io.ktor.util.pipeline.d} */
            /* JADX WARNING: Can't fix incorrect switch cases order */
            /* JADX WARNING: Multi-variable type inference failed */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r13) {
                /*
                    r12 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r12.label
                    r2 = 0
                    r3 = 0
                    switch(r1) {
                        case 0: goto L_0x005e;
                        case 1: goto L_0x0035;
                        case 2: goto L_0x0013;
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
                    r4 = r3
                    r5 = r3
                    java.lang.Object r6 = r12.L$4
                    r4 = r6
                    io.ktor.application.b r4 = (io.ktor.application.b) r4
                    java.lang.Object r6 = r12.L$3
                    r1 = r6
                    io.ktor.features.c r1 = (io.ktor.features.c) r1
                    java.lang.Object r6 = r12.L$2
                    r3 = r6
                    io.ktor.util.pipeline.d r3 = (io.ktor.util.pipeline.d) r3
                    java.lang.Object r6 = r12.L$1
                    r0 = r6
                    kotlin.x r0 = (kotlin.x) r0
                    java.lang.Object r6 = r12.L$0
                    r5 = r6
                    io.ktor.util.pipeline.d r5 = (io.ktor.util.pipeline.d) r5
                    kotlin.p.b(r13)
                    goto L_0x00a6
                L_0x0035:
                    r0 = r3
                    r1 = r3
                    r4 = r2
                    r5 = r3
                    r6 = r3
                    r7 = r3
                    java.lang.Object r8 = r12.L$5
                    r1 = r8
                    io.ktor.util.pipeline.d r1 = (io.ktor.util.pipeline.d) r1
                    java.lang.Object r8 = r12.L$4
                    r5 = r8
                    kotlin.coroutines.d r5 = (kotlin.coroutines.d) r5
                    java.lang.Object r8 = r12.L$3
                    r6 = r8
                    io.ktor.application.b r6 = (io.ktor.application.b) r6
                    java.lang.Object r8 = r12.L$2
                    r3 = r8
                    io.ktor.util.pipeline.d r3 = (io.ktor.util.pipeline.d) r3
                    java.lang.Object r8 = r12.L$1
                    r0 = r8
                    kotlin.x r0 = (kotlin.x) r0
                    java.lang.Object r8 = r12.L$0
                    r7 = r8
                    io.ktor.util.pipeline.d r7 = (io.ktor.util.pipeline.d) r7
                    kotlin.p.b(r13)
                    goto L_0x00c7
                L_0x005e:
                    kotlin.p.b(r13)
                    io.ktor.util.pipeline.d r5 = r12.p$
                    kotlin.x r1 = r12.p$0
                    r2 = r5
                    r4 = 0
                    r6 = r2
                    r7 = 0
                    java.lang.Object r8 = r6.getContext()
                    io.ktor.application.b r8 = (io.ktor.application.b) r8
                    r6 = r8
                    io.ktor.application.a r7 = r6.a()
                    io.ktor.features.c$c r8 = io.ktor.features.c.b
                    java.lang.Object r7 = io.ktor.application.g.a(r7, r8)
                    io.ktor.features.c r7 = (io.ktor.features.c) r7
                    if (r7 == 0) goto L_0x00a7
                    io.ktor.features.l r8 = new io.ktor.features.l
                    java.util.Map r9 = r7.m(r6)
                    r8.<init>(r9)
                    io.ktor.features.c$c$a$a r9 = new io.ktor.features.c$c$a$a
                    r9.<init>(r2, r7, r3, r12)
                    r12.L$0 = r5
                    r12.L$1 = r1
                    r12.L$2 = r2
                    r12.L$3 = r7
                    r12.L$4 = r6
                    r3 = 2
                    r12.label = r3
                    java.lang.Object r3 = kotlinx.coroutines.h.g(r8, r9, r12)
                    if (r3 != r0) goto L_0x00a1
                    return r0
                L_0x00a1:
                    r0 = r1
                    r3 = r2
                    r2 = r4
                    r4 = r6
                    r1 = r7
                L_0x00a6:
                    goto L_0x00d5
                L_0x00a7:
                    r3 = r12
                    r7 = r2
                    r8 = 0
                    r12.L$0 = r5
                    r12.L$1 = r1
                    r12.L$2 = r2
                    r12.L$3 = r6
                    r12.L$4 = r3
                    r12.L$5 = r7
                    r9 = 1
                    r12.label = r9
                    java.lang.Object r9 = r7.j(r12)
                    if (r9 != r0) goto L_0x00c0
                    return r0
                L_0x00c0:
                    r0 = r1
                    r1 = r7
                    r7 = r5
                    r5 = r3
                    r3 = r2
                    r2 = r4
                    r4 = r8
                L_0x00c7:
                    io.ktor.features.c r8 = r12.$feature
                    r9 = r1
                    r10 = 0
                    java.lang.Object r11 = r9.getContext()
                    io.ktor.application.b r11 = (io.ktor.application.b) r11
                    r8.l(r11)
                    r5 = r7
                L_0x00d5:
                    kotlin.x r1 = kotlin.x.a
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.c.C0242c.a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.CallLogging$Feature$install$2", f = "CallLogging.kt", l = {139}, m = "invokeSuspend")
        /* renamed from: io.ktor.features.c$c$b */
        /* compiled from: CallLogging.kt */
        public static final class b extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<x, io.ktor.application.b>, x, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ c $feature;
            Object L$0;
            Object L$1;
            int label;
            private io.ktor.util.pipeline.d p$;
            private x p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(c cVar, kotlin.coroutines.d dVar) {
                super(3, dVar);
                this.$feature = cVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<x, io.ktor.application.b> dVar, @NotNull x xVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(xVar, "it");
                k.f(dVar2, "continuation");
                b bVar = new b(this.$feature, dVar2);
                bVar.p$ = dVar;
                bVar.p$0 = xVar;
                return bVar;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((b) create((io.ktor.util.pipeline.d) obj, (x) obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
            }

            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: io.ktor.util.pipeline.d} */
            /* JADX WARNING: Multi-variable type inference failed */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
                /*
                    r6 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r6.label
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
                    java.lang.Object r2 = r6.L$1
                    r1 = r2
                    kotlin.x r1 = (kotlin.x) r1
                    java.lang.Object r2 = r6.L$0
                    r0 = r2
                    io.ktor.util.pipeline.d r0 = (io.ktor.util.pipeline.d) r0
                    kotlin.p.b(r7)
                    goto L_0x0038
                L_0x0021:
                    kotlin.p.b(r7)
                    io.ktor.util.pipeline.d r1 = r6.p$
                    kotlin.x r2 = r6.p$0
                    r6.L$0 = r1
                    r6.L$1 = r2
                    r3 = 1
                    r6.label = r3
                    java.lang.Object r3 = r1.j(r6)
                    if (r3 != r0) goto L_0x0036
                    return r0
                L_0x0036:
                    r0 = r1
                    r1 = r2
                L_0x0038:
                    io.ktor.features.c r2 = r6.$feature
                    r3 = r0
                    r4 = 0
                    java.lang.Object r5 = r3.getContext()
                    io.ktor.application.b r5 = (io.ktor.application.b) r5
                    r2.l(r5)
                    kotlin.x r2 = kotlin.x.a
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.c.C0242c.b.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }

    /* compiled from: CallLogging.kt */
    public static final class d {
        public static final d a = new d();

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.CallLogging$Internals", f = "CallLogging.kt", l = {228, 230}, m = "withMDCBlock")
        /* compiled from: CallLogging.kt */
        public static final class b extends kotlin.coroutines.jvm.internal.d {
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ d this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(d dVar, kotlin.coroutines.d dVar2) {
                super(dVar2);
                this.this$0 = dVar;
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.a(null, (p) null, this);
            }
        }

        private d() {
        }

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.CallLogging$Internals$withMDCBlock$$inlined$withMDC$1", f = "CallLogging.kt", l = {182}, m = "invokeSuspend")
        /* compiled from: CallLogging.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ p $block;
            final /* synthetic */ c $feature;
            final /* synthetic */ io.ktor.util.pipeline.d $this_withMDC;
            Object L$0;
            int label;
            private o0 p$;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(io.ktor.util.pipeline.d dVar, p pVar, c cVar, kotlin.coroutines.d dVar2) {
                super(2, dVar2);
                this.$this_withMDC = dVar;
                this.$block = pVar;
                this.$feature = cVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                k.f(dVar, "completion");
                a aVar = new a(this.$this_withMDC, this.$block, this.$feature, dVar);
                o0 o0Var = (o0) obj;
                aVar.p$ = (o0) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((a) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
            }

            /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
                r6.$feature.j();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
                return kotlin.x.a;
             */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
                /*
                    r6 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r6.label
                    switch(r1) {
                        case 0: goto L_0x001d;
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
                    java.lang.Object r1 = r6.L$0
                    r0 = r1
                    kotlinx.coroutines.o0 r0 = (kotlinx.coroutines.o0) r0
                    kotlin.p.b(r7)     // Catch:{ all -> 0x001b }
                    goto L_0x0034
                L_0x001b:
                    r1 = move-exception
                    goto L_0x0041
                L_0x001d:
                    kotlin.p.b(r7)
                    kotlinx.coroutines.o0 r1 = r6.p$
                    kotlin.jvm.functions.p r2 = r6.$block     // Catch:{ all -> 0x003d }
                    io.ktor.util.pipeline.d r3 = r6.$this_withMDC     // Catch:{ all -> 0x003d }
                    r6.L$0 = r1     // Catch:{ all -> 0x003d }
                    r4 = 1
                    r6.label = r4     // Catch:{ all -> 0x003d }
                    java.lang.Object r2 = r2.invoke(r3, r6)     // Catch:{ all -> 0x003d }
                    if (r2 != r0) goto L_0x0033
                    return r0
                L_0x0033:
                    r0 = r1
                L_0x0034:
                    io.ktor.features.c r1 = r6.$feature
                    r1.j()
                    kotlin.x r1 = kotlin.x.a
                    return r1
                L_0x003d:
                    r0 = move-exception
                    r5 = r1
                    r1 = r0
                    r0 = r5
                L_0x0041:
                    io.ktor.features.c r2 = r6.$feature
                    r2.j()
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.c.d.a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        /* Debug info: failed to restart local var, previous not found, register: 10 */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x00b5, code lost:
            r7 = kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00d0, code lost:
            r6 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00d3, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0051  */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0070  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final <C extends io.ktor.util.pipeline.d<?, io.ktor.application.b>> java.lang.Object a(@org.jetbrains.annotations.NotNull C r11, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.p<? super C, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends java.lang.Object> r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r13) {
            /*
                r10 = this;
                boolean r0 = r13 instanceof io.ktor.features.c.d.b
                if (r0 == 0) goto L_0x0013
                r0 = r13
                io.ktor.features.c$d$b r0 = (io.ktor.features.c.d.b) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L_0x0013
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0018
            L_0x0013:
                io.ktor.features.c$d$b r0 = new io.ktor.features.c$d$b
                r0.<init>(r10, r13)
            L_0x0018:
                java.lang.Object r1 = r0.result
                java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
                int r3 = r0.label
                r4 = 0
                r5 = 0
                switch(r3) {
                    case 0: goto L_0x0070;
                    case 1: goto L_0x0051;
                    case 2: goto L_0x002d;
                    default: goto L_0x0025;
                }
            L_0x0025:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x002d:
                r2 = r5
                r3 = r4
                r5 = r4
                java.lang.Object r6 = r0.L$5
                r5 = r6
                io.ktor.application.b r5 = (io.ktor.application.b) r5
                java.lang.Object r6 = r0.L$4
                r3 = r6
                io.ktor.features.c r3 = (io.ktor.features.c) r3
                java.lang.Object r6 = r0.L$3
                r4 = r6
                io.ktor.util.pipeline.d r4 = (io.ktor.util.pipeline.d) r4
                java.lang.Object r6 = r0.L$2
                r12 = r6
                kotlin.jvm.functions.p r12 = (kotlin.jvm.functions.p) r12
                java.lang.Object r6 = r0.L$1
                r11 = r6
                io.ktor.util.pipeline.d r11 = (io.ktor.util.pipeline.d) r11
                java.lang.Object r6 = r0.L$0
                io.ktor.features.c$d r6 = (io.ktor.features.c.d) r6
                kotlin.p.b(r1)
                goto L_0x00b5
            L_0x0051:
                r2 = r5
                r3 = r4
                java.lang.Object r5 = r0.L$4
                r3 = r5
                io.ktor.application.b r3 = (io.ktor.application.b) r3
                java.lang.Object r5 = r0.L$3
                r4 = r5
                io.ktor.util.pipeline.d r4 = (io.ktor.util.pipeline.d) r4
                java.lang.Object r5 = r0.L$2
                r12 = r5
                kotlin.jvm.functions.p r12 = (kotlin.jvm.functions.p) r12
                java.lang.Object r5 = r0.L$1
                r11 = r5
                io.ktor.util.pipeline.d r11 = (io.ktor.util.pipeline.d) r11
                java.lang.Object r5 = r0.L$0
                io.ktor.features.c$d r5 = (io.ktor.features.c.d) r5
                kotlin.p.b(r1)
                goto L_0x00d0
            L_0x0070:
                kotlin.p.b(r1)
                r3 = r11
                r5 = 0
                r6 = r3
                r7 = 0
                java.lang.Object r8 = r6.getContext()
                io.ktor.application.b r8 = (io.ktor.application.b) r8
                r6 = r8
                io.ktor.application.a r7 = r6.a()
                io.ktor.features.c$c r8 = io.ktor.features.c.b
                java.lang.Object r7 = io.ktor.application.g.a(r7, r8)
                io.ktor.features.c r7 = (io.ktor.features.c) r7
                if (r7 == 0) goto L_0x00b8
                io.ktor.features.l r8 = new io.ktor.features.l
                java.util.Map r9 = r7.m(r6)
                r8.<init>(r9)
                io.ktor.features.c$d$a r9 = new io.ktor.features.c$d$a
                r9.<init>(r3, r12, r7, r4)
                r0.L$0 = r10
                r0.L$1 = r11
                r0.L$2 = r12
                r0.L$3 = r3
                r0.L$4 = r7
                r0.L$5 = r6
                r4 = 2
                r0.label = r4
                java.lang.Object r4 = kotlinx.coroutines.h.g(r8, r9, r0)
                if (r4 != r2) goto L_0x00b0
                return r2
            L_0x00b0:
                r4 = r3
                r2 = r5
                r5 = r6
                r3 = r7
                r6 = r10
            L_0x00b5:
                kotlin.x r7 = kotlin.x.a
                goto L_0x00d1
            L_0x00b8:
                r0.L$0 = r10
                r0.L$1 = r11
                r0.L$2 = r12
                r0.L$3 = r3
                r0.L$4 = r6
                r4 = 1
                r0.label = r4
                java.lang.Object r4 = r12.invoke(r3, r0)
                if (r4 != r2) goto L_0x00cc
                return r2
            L_0x00cc:
                r4 = r3
                r2 = r5
                r3 = r6
                r5 = r10
            L_0x00d0:
                r6 = r5
            L_0x00d1:
                kotlin.x r2 = kotlin.x.a
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.c.d.a(io.ktor.util.pipeline.d, kotlin.jvm.functions.p, kotlin.coroutines.d):java.lang.Object");
        }
    }

    /* access modifiers changed from: private */
    public final void k(String message) {
        switch (d.a[this.i.ordinal()]) {
            case 1:
                this.g.error(message);
                return;
            case 2:
                this.g.warn(message);
                return;
            case 3:
                this.g.info(message);
                return;
            case 4:
                this.g.debug(message);
                return;
            case 5:
                this.g.trace(message);
                return;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* access modifiers changed from: private */
    public final void l(io.ktor.application.b call) {
        if (!this.j.isEmpty()) {
            List<l<io.ktor.application.b, Boolean>> list = this.j;
            boolean z = false;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((Boolean) ((l) it.next()).invoke(call)).booleanValue()) {
                            z = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (!z) {
                return;
            }
        }
        k(this.l.invoke(call));
    }
}
