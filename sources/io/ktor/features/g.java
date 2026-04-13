package io.ktor.features;

import io.ktor.application.f;
import java.util.ArrayList;
import java.util.List;
import kotlin.coroutines.d;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContentNegotiation.kt */
public final class g {
    /* access modifiers changed from: private */
    @NotNull
    public static final io.ktor.util.a<g> a = new io.ktor.util.a<>("ContentNegotiation");
    public static final c b = new c((DefaultConstructorMarker) null);
    @NotNull
    private final List<b> c;
    /* access modifiers changed from: private */
    public final List<p<io.ktor.application.b, List<i>, List<i>>> d;

    public g(@NotNull List<b> registrations, @NotNull List<? extends p<? super io.ktor.application.b, ? super List<i>, ? extends List<i>>> acceptContributors) {
        k.f(registrations, "registrations");
        k.f(acceptContributors, "acceptContributors");
        this.c = registrations;
        this.d = acceptContributors;
    }

    @NotNull
    public final List<b> c() {
        return this.c;
    }

    /* compiled from: ContentNegotiation.kt */
    public static final class b {
        @NotNull
        private final io.ktor.http.c a;
        @NotNull
        private final f b;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return k.a(this.a, bVar.a) && k.a(this.b, bVar.b);
        }

        public int hashCode() {
            io.ktor.http.c cVar = this.a;
            int i = 0;
            int hashCode = (cVar != null ? cVar.hashCode() : 0) * 31;
            f fVar = this.b;
            if (fVar != null) {
                i = fVar.hashCode();
            }
            return hashCode + i;
        }

        @NotNull
        public String toString() {
            return "ConverterRegistration(contentType=" + this.a + ", converter=" + this.b + ")";
        }

        public b(@NotNull io.ktor.http.c contentType, @NotNull f converter) {
            k.f(contentType, "contentType");
            k.f(converter, "converter");
            this.a = contentType;
            this.b = converter;
        }

        @NotNull
        public final io.ktor.http.c a() {
            return this.a;
        }

        @NotNull
        public final f b() {
            return this.b;
        }
    }

    /* compiled from: ContentNegotiation.kt */
    public static final class a {
        @NotNull
        private final List<b> a = new ArrayList();
        @NotNull
        private final List<p<io.ktor.application.b, List<i>, List<i>>> b = new ArrayList();

        /* renamed from: io.ktor.features.g$a$a  reason: collision with other inner class name */
        /* compiled from: ContentNegotiation.kt */
        public static final class C0244a extends l implements kotlin.jvm.functions.l<T, x> {
            public static final C0244a INSTANCE = new C0244a();

            C0244a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((f) obj);
                return x.a;
            }

            public final void invoke(@NotNull T $receiver) {
                k.f($receiver, "$receiver");
            }
        }

        @NotNull
        public final List<b> b() {
            return this.a;
        }

        @NotNull
        public final List<p<io.ktor.application.b, List<i>, List<i>>> a() {
            return this.b;
        }

        public static /* synthetic */ void d(a aVar, io.ktor.http.c cVar, f fVar, kotlin.jvm.functions.l lVar, int i, Object obj) {
            if ((i & 4) != 0) {
                lVar = C0244a.INSTANCE;
            }
            aVar.c(cVar, fVar, lVar);
        }

        public final <T extends f> void c(@NotNull io.ktor.http.c contentType, @NotNull T converter, @NotNull kotlin.jvm.functions.l<? super T, x> configuration) {
            k.f(contentType, "contentType");
            k.f(converter, "converter");
            k.f(configuration, "configuration");
            configuration.invoke(converter);
            this.a.add(new b(contentType, converter));
        }
    }

    /* compiled from: ContentNegotiation.kt */
    public static final class c implements f<io.ktor.application.c, a, g> {
        private c() {
        }

        public /* synthetic */ c(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public io.ktor.util.a<g> getKey() {
            return g.a;
        }

        @NotNull
        /* renamed from: b */
        public g a(@NotNull io.ktor.application.c pipeline, @NotNull kotlin.jvm.functions.l<? super a, x> configure) {
            k.f(pipeline, "pipeline");
            k.f(configure, "configure");
            a configuration = new a();
            configure.invoke(configuration);
            g feature = new g(configuration.b(), configuration.a());
            pipeline.p(io.ktor.application.c.a2.b(), new a((d) null));
            pipeline.C().p(io.ktor.response.d.p3.b(), new b(feature, (d) null));
            pipeline.B().p(io.ktor.request.b.a1.a(), new C0245c(feature, (d) null));
            return feature;
        }

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.ContentNegotiation$Feature$install$1", f = "ContentNegotiation.kt", l = {107, 254}, m = "invokeSuspend")
        /* compiled from: ContentNegotiation.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<x, io.ktor.application.b>, x, d<? super x>, Object> {
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            int label;
            private io.ktor.util.pipeline.d p$;
            private x p$0;

            a(d dVar) {
                super(3, dVar);
            }

            @NotNull
            public final d<x> create(@NotNull io.ktor.util.pipeline.d<x, io.ktor.application.b> dVar, @NotNull x xVar, @NotNull d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(xVar, "it");
                k.f(dVar2, "continuation");
                a aVar = new a(dVar2);
                aVar.p$ = dVar;
                aVar.p$0 = xVar;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((a) create((io.ktor.util.pipeline.d) obj, (x) obj2, (d) obj3)).invokeSuspend(x.a);
            }

            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: io.ktor.util.pipeline.d} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: io.ktor.util.pipeline.d} */
            /* JADX WARNING: Multi-variable type inference failed */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
                /*
                    r9 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r9.label
                    r2 = 0
                    switch(r1) {
                        case 0: goto L_0x0043;
                        case 1: goto L_0x0034;
                        case 2: goto L_0x0012;
                        default: goto L_0x000a;
                    }
                L_0x000a:
                    java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                    java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                    r0.<init>(r1)
                    throw r0
                L_0x0012:
                    r0 = r2
                    r1 = r2
                    r3 = 0
                    r4 = r2
                    r5 = r2
                    java.lang.Object r6 = r9.L$4
                    r5 = r6
                    io.ktor.http.v r5 = (io.ktor.http.v) r5
                    java.lang.Object r6 = r9.L$3
                    r0 = r6
                    io.ktor.application.b r0 = (io.ktor.application.b) r0
                    java.lang.Object r6 = r9.L$2
                    r2 = r6
                    io.ktor.features.UnsupportedMediaTypeException r2 = (io.ktor.features.UnsupportedMediaTypeException) r2
                    java.lang.Object r6 = r9.L$1
                    r1 = r6
                    kotlin.x r1 = (kotlin.x) r1
                    java.lang.Object r6 = r9.L$0
                    r4 = r6
                    io.ktor.util.pipeline.d r4 = (io.ktor.util.pipeline.d) r4
                    kotlin.p.b(r10)
                    goto L_0x008b
                L_0x0034:
                    r1 = r2
                    java.lang.Object r3 = r9.L$1
                    r1 = r3
                    kotlin.x r1 = (kotlin.x) r1
                    java.lang.Object r3 = r9.L$0
                    r2 = r3
                    io.ktor.util.pipeline.d r2 = (io.ktor.util.pipeline.d) r2
                    kotlin.p.b(r10)     // Catch:{ UnsupportedMediaTypeException -> 0x005a }
                    goto L_0x0059
                L_0x0043:
                    kotlin.p.b(r10)
                    io.ktor.util.pipeline.d r2 = r9.p$
                    kotlin.x r1 = r9.p$0
                    r9.L$0 = r2     // Catch:{ UnsupportedMediaTypeException -> 0x005a }
                    r9.L$1 = r1     // Catch:{ UnsupportedMediaTypeException -> 0x005a }
                    r3 = 1
                    r9.label = r3     // Catch:{ UnsupportedMediaTypeException -> 0x005a }
                    java.lang.Object r3 = r2.j(r9)     // Catch:{ UnsupportedMediaTypeException -> 0x005a }
                    if (r3 != r0) goto L_0x0059
                    return r0
                L_0x0059:
                    goto L_0x008c
                L_0x005a:
                    r3 = move-exception
                    r4 = r2
                    r2 = r3
                    r3 = r4
                    r5 = 0
                    java.lang.Object r6 = r3.getContext()
                    io.ktor.application.b r6 = (io.ktor.application.b) r6
                    io.ktor.http.v$a r3 = io.ktor.http.v.c0
                    io.ktor.http.v r5 = r3.V()
                    r3 = r6
                    r6 = 0
                    io.ktor.response.a r7 = r3.b()
                    io.ktor.response.d r7 = r7.a()
                    r9.L$0 = r4
                    r9.L$1 = r1
                    r9.L$2 = r2
                    r9.L$3 = r3
                    r9.L$4 = r5
                    r8 = 2
                    r9.label = r8
                    java.lang.Object r7 = r7.e(r3, r5, r9)
                    if (r7 != r0) goto L_0x0089
                    return r0
                L_0x0089:
                    r0 = r3
                    r3 = r6
                L_0x008b:
                    r2 = r4
                L_0x008c:
                    kotlin.x r0 = kotlin.x.a
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.g.c.a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.ContentNegotiation$Feature$install$2", f = "ContentNegotiation.kt", l = {143, 148}, m = "invokeSuspend")
        /* compiled from: ContentNegotiation.kt */
        public static final class b extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<Object, io.ktor.application.b>, Object, d<? super x>, Object> {
            final /* synthetic */ g $feature;
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            Object L$6;
            Object L$7;
            Object L$8;
            Object L$9;
            int label;
            private io.ktor.util.pipeline.d p$;
            private Object p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(g gVar, d dVar) {
                super(3, dVar);
                this.$feature = gVar;
            }

            @NotNull
            public final d<x> create(@NotNull io.ktor.util.pipeline.d<Object, io.ktor.application.b> dVar, @NotNull Object obj, @NotNull d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(obj, "subject");
                k.f(dVar2, "continuation");
                b bVar = new b(this.$feature, dVar2);
                bVar.p$ = dVar;
                bVar.p$0 = obj;
                return bVar;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((b) create((io.ktor.util.pipeline.d) obj, obj2, (d) obj3)).invokeSuspend(x.a);
            }

            /* Debug info: failed to restart local var, previous not found, register: 26 */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v7, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: java.util.List} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v9, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: io.ktor.util.pipeline.d} */
            /* JADX WARNING: Code restructure failed: missing block: B:46:0x01e4, code lost:
                if (r14.hasNext() == false) goto L_0x0229;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:47:0x01e6, code lost:
                r6 = r14.next();
                r5 = (io.ktor.features.g.b) r6;
                r15 = r5.b();
                r27 = r0;
                r0 = r5.a();
                r3.L$0 = r11;
                r3.L$1 = r4;
                r3.L$2 = r12;
                r3.L$3 = r13;
                r3.L$4 = r2;
                r3.L$5 = r7;
                r3.L$6 = r10;
                r3.L$7 = r14;
                r3.L$8 = r6;
                r3.L$9 = r5;
                r16 = r2;
                r3.label = 1;
                r0 = r15.convertForSend(r11, r0, r4, r3);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:48:0x0215, code lost:
                if (r0 != r1) goto L_0x0218;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:49:0x0217, code lost:
                return r1;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:50:0x0218, code lost:
                r2 = r1;
                r1 = r27;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:51:0x021c, code lost:
                if (r0 == null) goto L_0x0224;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:52:0x021e, code lost:
                r5 = r3;
                r8 = r12;
                r3 = r0;
                r0 = r16;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:53:0x0224, code lost:
                r0 = r1;
                r1 = r2;
                r2 = r16;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:54:0x0229, code lost:
                r27 = r0;
                r16 = r2;
                r2 = r1;
                r5 = r3;
                r8 = r12;
                r0 = r16;
                r3 = null;
                r1 = r27;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:55:0x0236, code lost:
                if (r3 == null) goto L_0x0241;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:56:0x0238, code lost:
                r6 = io.ktor.http.content.b.a(r11, r3);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:57:0x023e, code lost:
                if (r6 == null) goto L_0x0241;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:58:0x0241, code lost:
                r6 = new io.ktor.http.content.c(io.ktor.http.v.c0.w());
             */
            /* JADX WARNING: Code restructure failed: missing block: B:59:0x024c, code lost:
                r5.L$0 = r11;
                r5.L$1 = r4;
                r5.L$2 = r8;
                r5.L$3 = r13;
                r5.L$4 = r0;
                r5.L$5 = r7;
                r5.L$6 = r3;
                r5.L$7 = r6;
                r5.label = 2;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:60:0x0264, code lost:
                if (r11.E(r6, r5) != r2) goto L_0x0267;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:61:0x0266, code lost:
                return r2;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:62:0x0267, code lost:
                r2 = r4;
                r4 = r7;
                r7 = r6;
                r6 = r5;
                r5 = r3;
                r3 = r13;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:64:0x026f, code lost:
                return kotlin.x.a;
             */
            /* JADX WARNING: Multi-variable type inference failed */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r27) {
                /*
                    r26 = this;
                    r1 = r26
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r2 = r1.label
                    r3 = 0
                    switch(r2) {
                        case 0: goto L_0x0089;
                        case 1: goto L_0x0047;
                        case 2: goto L_0x0014;
                        default: goto L_0x000c;
                    }
                L_0x000c:
                    java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                    java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                    r0.<init>(r1)
                    throw r0
                L_0x0014:
                    r0 = r3
                    r2 = r3
                    r4 = r3
                    r5 = r3
                    r6 = r3
                    r7 = r3
                    r8 = r3
                    java.lang.Object r9 = r1.L$7
                    r6 = r9
                    io.ktor.http.content.j r6 = (io.ktor.http.content.j) r6
                    java.lang.Object r5 = r1.L$6
                    java.lang.Object r9 = r1.L$5
                    r4 = r9
                    java.util.List r4 = (java.util.List) r4
                    java.lang.Object r9 = r1.L$4
                    r0 = r9
                    java.util.List r0 = (java.util.List) r0
                    java.lang.Object r9 = r1.L$3
                    r3 = r9
                    java.util.List r3 = (java.util.List) r3
                    java.lang.Object r9 = r1.L$2
                    r8 = r9
                    java.lang.String r8 = (java.lang.String) r8
                    java.lang.Object r2 = r1.L$1
                    java.lang.Object r9 = r1.L$0
                    r7 = r9
                    io.ktor.util.pipeline.d r7 = (io.ktor.util.pipeline.d) r7
                    kotlin.p.b(r27)
                    r11 = r7
                    r7 = r6
                    r6 = r1
                    r1 = r27
                    goto L_0x026d
                L_0x0047:
                    r2 = r3
                    r4 = r3
                    r5 = r3
                    r6 = r3
                    r7 = r3
                    r8 = 0
                    r9 = r8
                    r10 = r3
                    r11 = r3
                    r12 = r3
                    r13 = r3
                    java.lang.Object r14 = r1.L$9
                    r5 = r14
                    io.ktor.features.g$b r5 = (io.ktor.features.g.b) r5
                    java.lang.Object r6 = r1.L$8
                    java.lang.Object r14 = r1.L$7
                    java.util.Iterator r14 = (java.util.Iterator) r14
                    java.lang.Object r15 = r1.L$6
                    r10 = r15
                    java.lang.Iterable r10 = (java.lang.Iterable) r10
                    java.lang.Object r15 = r1.L$5
                    r7 = r15
                    java.util.List r7 = (java.util.List) r7
                    java.lang.Object r15 = r1.L$4
                    r2 = r15
                    java.util.List r2 = (java.util.List) r2
                    java.lang.Object r15 = r1.L$3
                    r13 = r15
                    java.util.List r13 = (java.util.List) r13
                    java.lang.Object r15 = r1.L$2
                    r12 = r15
                    java.lang.String r12 = (java.lang.String) r12
                    java.lang.Object r4 = r1.L$1
                    java.lang.Object r15 = r1.L$0
                    r11 = r15
                    io.ktor.util.pipeline.d r11 = (io.ktor.util.pipeline.d) r11
                    kotlin.p.b(r27)
                    r3 = r1
                    r16 = r2
                    r1 = r27
                    r2 = r0
                    r0 = r1
                    goto L_0x021b
                L_0x0089:
                    kotlin.p.b(r27)
                    io.ktor.util.pipeline.d r2 = r1.p$
                    java.lang.Object r4 = r1.p$0
                    boolean r5 = r4 instanceof io.ktor.http.content.j
                    if (r5 == 0) goto L_0x0097
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0097:
                    r5 = r2
                    r6 = 0
                    java.lang.Object r7 = r5.getContext()
                    io.ktor.application.b r7 = (io.ktor.application.b) r7
                    io.ktor.request.d r5 = r7.getRequest()
                    io.ktor.http.s r6 = io.ktor.http.s.V0
                    java.lang.String r6 = r6.c()
                    java.lang.String r5 = io.ktor.request.e.f(r5, r6)
                    java.util.List r6 = io.ktor.http.r.c(r5)     // Catch:{ BadContentTypeFormatException -> 0x0270 }
                    r7 = 0
                    java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ BadContentTypeFormatException -> 0x0270 }
                    r9 = 10
                    int r9 = kotlin.collections.r.r(r6, r9)     // Catch:{ BadContentTypeFormatException -> 0x0270 }
                    r8.<init>(r9)     // Catch:{ BadContentTypeFormatException -> 0x0270 }
                    r9 = r6
                    r10 = 0
                    java.util.Iterator r11 = r9.iterator()     // Catch:{ BadContentTypeFormatException -> 0x0270 }
                L_0x00c6:
                    boolean r12 = r11.hasNext()     // Catch:{ BadContentTypeFormatException -> 0x0270 }
                    if (r12 == 0) goto L_0x00fc
                    java.lang.Object r12 = r11.next()     // Catch:{ BadContentTypeFormatException -> 0x00f7 }
                    r13 = r12
                    io.ktor.http.k r13 = (io.ktor.http.k) r13     // Catch:{ BadContentTypeFormatException -> 0x00f7 }
                    r14 = 0
                    io.ktor.features.i r15 = new io.ktor.features.i     // Catch:{ BadContentTypeFormatException -> 0x00f7 }
                    io.ktor.http.c$b r3 = io.ktor.http.c.e     // Catch:{ BadContentTypeFormatException -> 0x00f7 }
                    r16 = r0
                    java.lang.String r0 = r13.d()     // Catch:{ BadContentTypeFormatException -> 0x00f7 }
                    io.ktor.http.c r0 = r3.b(r0)     // Catch:{ BadContentTypeFormatException -> 0x00f7 }
                    r17 = r4
                    double r3 = r13.c()     // Catch:{ BadContentTypeFormatException -> 0x00f4 }
                    r15.<init>(r0, r3)     // Catch:{ BadContentTypeFormatException -> 0x00f4 }
                    r8.add(r15)     // Catch:{ BadContentTypeFormatException -> 0x00f4 }
                    r0 = r16
                    r4 = r17
                    r3 = 0
                    goto L_0x00c6
                L_0x00f4:
                    r0 = move-exception
                    goto L_0x0273
                L_0x00f7:
                    r0 = move-exception
                    r17 = r4
                    goto L_0x0273
                L_0x00fc:
                    r16 = r0
                    r17 = r4
                    r0 = r8
                    io.ktor.features.g r3 = r1.$feature
                    java.util.List r3 = r3.d
                    r4 = 0
                    r6 = r0
                    java.util.Iterator r7 = r3.iterator()
                L_0x0111:
                    boolean r8 = r7.hasNext()
                    if (r8 == 0) goto L_0x0130
                    java.lang.Object r8 = r7.next()
                    r9 = r8
                    kotlin.jvm.functions.p r9 = (kotlin.jvm.functions.p) r9
                    r10 = r6
                    r11 = 0
                    r12 = r2
                    r13 = 0
                    java.lang.Object r14 = r12.getContext()
                    io.ktor.application.b r14 = (io.ktor.application.b) r14
                    java.lang.Object r12 = r9.invoke(r14, r10)
                    r6 = r12
                    java.util.List r6 = (java.util.List) r6
                    goto L_0x0111
                L_0x0130:
                    java.util.List r3 = kotlin.collections.y.N(r6)
                    java.util.List r3 = io.ktor.features.h.a(r3)
                    boolean r4 = r3.isEmpty()
                    if (r4 == 0) goto L_0x014a
                    io.ktor.features.g r4 = r1.$feature
                    java.util.List r4 = r4.c()
                    r23 = r0
                    goto L_0x01c7
                L_0x014a:
                    r4 = r3
                    r6 = 0
                    java.util.ArrayList r7 = new java.util.ArrayList
                    r7.<init>()
                    r8 = r4
                    r9 = 0
                    java.util.Iterator r10 = r8.iterator()
                L_0x0159:
                    boolean r11 = r10.hasNext()
                    if (r11 == 0) goto L_0x01c1
                    java.lang.Object r11 = r10.next()
                    r12 = r11
                    io.ktor.features.i r12 = (io.ktor.features.i) r12
                    r13 = 0
                    io.ktor.http.c r14 = r12.a()
                    io.ktor.features.g r15 = r1.$feature
                    java.util.List r15 = r15.c()
                    r18 = 0
                    java.util.ArrayList r19 = new java.util.ArrayList
                    r19.<init>()
                    r20 = r19
                    r19 = r15
                    r21 = 0
                    java.util.Iterator r22 = r19.iterator()
                L_0x0182:
                    boolean r23 = r22.hasNext()
                    if (r23 == 0) goto L_0x01b3
                    r23 = r0
                    java.lang.Object r0 = r22.next()
                    r24 = r0
                    io.ktor.features.g$b r24 = (io.ktor.features.g.b) r24
                    r25 = 0
                    io.ktor.http.c r1 = r24.a()
                    boolean r1 = r1.h(r14)
                    java.lang.Boolean r1 = kotlin.coroutines.jvm.internal.b.a(r1)
                    boolean r1 = r1.booleanValue()
                    if (r1 == 0) goto L_0x01ac
                    r1 = r20
                    r1.add(r0)
                    goto L_0x01ae
                L_0x01ac:
                    r1 = r20
                L_0x01ae:
                    r0 = r23
                    r1 = r26
                    goto L_0x0182
                L_0x01b3:
                    r23 = r0
                    r1 = r20
                    r0 = r1
                    kotlin.collections.v.x(r7, r0)
                    r1 = r26
                    r0 = r23
                    goto L_0x0159
                L_0x01c1:
                    r23 = r0
                    java.util.List r4 = kotlin.collections.y.N(r7)
                L_0x01c7:
                    r0 = r4
                    r1 = r0
                    r4 = 0
                    java.util.Iterator r6 = r1.iterator()
                    r7 = r0
                    r10 = r1
                    r11 = r2
                    r2 = r3
                    r9 = r4
                    r12 = r5
                    r14 = r6
                    r1 = r16
                    r4 = r17
                    r13 = r23
                    r3 = r26
                    r0 = r27
                L_0x01e0:
                    boolean r5 = r14.hasNext()
                    if (r5 == 0) goto L_0x0229
                    java.lang.Object r6 = r14.next()
                    r5 = r6
                    io.ktor.features.g$b r5 = (io.ktor.features.g.b) r5
                    r8 = 0
                    io.ktor.features.f r15 = r5.b()
                    r27 = r0
                    io.ktor.http.c r0 = r5.a()
                    r3.L$0 = r11
                    r3.L$1 = r4
                    r3.L$2 = r12
                    r3.L$3 = r13
                    r3.L$4 = r2
                    r3.L$5 = r7
                    r3.L$6 = r10
                    r3.L$7 = r14
                    r3.L$8 = r6
                    r3.L$9 = r5
                    r16 = r2
                    r2 = 1
                    r3.label = r2
                    java.lang.Object r0 = r15.convertForSend(r11, r0, r4, r3)
                    if (r0 != r1) goto L_0x0218
                    return r1
                L_0x0218:
                    r2 = r1
                    r1 = r27
                L_0x021b:
                    if (r0 == 0) goto L_0x0224
                    r5 = r3
                    r8 = r12
                    r3 = r0
                    r0 = r16
                    goto L_0x0235
                L_0x0224:
                    r0 = r1
                    r1 = r2
                    r2 = r16
                    goto L_0x01e0
                L_0x0229:
                    r27 = r0
                    r16 = r2
                    r2 = r1
                    r5 = r3
                    r8 = r12
                    r0 = r16
                    r3 = 0
                    r1 = r27
                L_0x0235:
                    if (r3 == 0) goto L_0x0241
                    r6 = r3
                    r9 = 0
                    io.ktor.http.content.j r6 = io.ktor.http.content.b.a(r11, r6)
                    if (r6 == 0) goto L_0x0241
                    goto L_0x024c
                L_0x0241:
                    io.ktor.http.content.c r6 = new io.ktor.http.content.c
                    io.ktor.http.v$a r9 = io.ktor.http.v.c0
                    io.ktor.http.v r9 = r9.w()
                    r6.<init>(r9)
                L_0x024c:
                    r5.L$0 = r11
                    r5.L$1 = r4
                    r5.L$2 = r8
                    r5.L$3 = r13
                    r5.L$4 = r0
                    r5.L$5 = r7
                    r5.L$6 = r3
                    r5.L$7 = r6
                    r9 = 2
                    r5.label = r9
                    java.lang.Object r9 = r11.E(r6, r5)
                    if (r9 != r2) goto L_0x0267
                    return r2
                L_0x0267:
                    r2 = r4
                    r4 = r7
                    r7 = r6
                    r6 = r5
                    r5 = r3
                    r3 = r13
                L_0x026d:
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0270:
                    r0 = move-exception
                    r17 = r4
                L_0x0273:
                    io.ktor.features.BadRequestException r1 = new io.ktor.features.BadRequestException
                    java.lang.StringBuilder r3 = new java.lang.StringBuilder
                    r3.<init>()
                    java.lang.String r4 = "Illegal Accept header format: "
                    r3.append(r4)
                    r3.append(r5)
                    java.lang.String r3 = r3.toString()
                    r1.<init>(r3, r0)
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.g.c.b.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }

        @kotlin.coroutines.jvm.internal.f(c = "io.ktor.features.ContentNegotiation$Feature$install$3", f = "ContentNegotiation.kt", l = {169, 172}, m = "invokeSuspend")
        /* renamed from: io.ktor.features.g$c$c  reason: collision with other inner class name */
        /* compiled from: ContentNegotiation.kt */
        public static final class C0245c extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<io.ktor.request.c, io.ktor.application.b>, io.ktor.request.c, d<? super x>, Object> {
            final /* synthetic */ g $feature;
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            int label;
            private io.ktor.util.pipeline.d p$;
            private io.ktor.request.c p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0245c(g gVar, d dVar) {
                super(3, dVar);
                this.$feature = gVar;
            }

            @NotNull
            public final d<x> create(@NotNull io.ktor.util.pipeline.d<io.ktor.request.c, io.ktor.application.b> dVar, @NotNull io.ktor.request.c cVar, @NotNull d<? super x> dVar2) {
                k.f(dVar, "$this$create");
                k.f(cVar, "receive");
                k.f(dVar2, "continuation");
                C0245c cVar2 = new C0245c(this.$feature, dVar2);
                cVar2.p$ = dVar;
                cVar2.p$0 = cVar;
                return cVar2;
            }

            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return ((C0245c) create((io.ktor.util.pipeline.d) obj, (io.ktor.request.c) obj2, (d) obj3)).invokeSuspend(x.a);
            }

            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: io.ktor.features.g$b} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v11, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: io.ktor.http.c} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v12, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: io.ktor.request.c} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v13, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v18, resolved type: io.ktor.util.pipeline.d} */
            /* JADX WARNING: Multi-variable type inference failed */
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
                        case 0: goto L_0x004f;
                        case 1: goto L_0x0032;
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
                    r2 = r3
                    r4 = r3
                    java.lang.Object r0 = r14.L$4
                    java.lang.Object r5 = r14.L$3
                    r3 = r5
                    io.ktor.features.g$b r3 = (io.ktor.features.g.b) r3
                    java.lang.Object r5 = r14.L$2
                    r2 = r5
                    io.ktor.http.c r2 = (io.ktor.http.c) r2
                    java.lang.Object r5 = r14.L$1
                    r1 = r5
                    io.ktor.request.c r1 = (io.ktor.request.c) r1
                    java.lang.Object r5 = r14.L$0
                    r4 = r5
                    io.ktor.util.pipeline.d r4 = (io.ktor.util.pipeline.d) r4
                    kotlin.p.b(r15)
                    goto L_0x0105
                L_0x0032:
                    r1 = r3
                    r4 = r3
                    r5 = r3
                    java.lang.Object r6 = r14.L$3
                    r3 = r6
                    io.ktor.features.g$b r3 = (io.ktor.features.g.b) r3
                    java.lang.Object r6 = r14.L$2
                    r4 = r6
                    io.ktor.http.c r4 = (io.ktor.http.c) r4
                    java.lang.Object r6 = r14.L$1
                    r1 = r6
                    io.ktor.request.c r1 = (io.ktor.request.c) r1
                    java.lang.Object r6 = r14.L$0
                    r5 = r6
                    io.ktor.util.pipeline.d r5 = (io.ktor.util.pipeline.d) r5
                    kotlin.p.b(r15)
                    r6 = r15
                    goto L_0x00e3
                L_0x004f:
                    kotlin.p.b(r15)
                    io.ktor.util.pipeline.d r1 = r14.p$
                    io.ktor.request.c r4 = r14.p$0
                    java.lang.Object r5 = r1.l()
                    io.ktor.request.c r5 = (io.ktor.request.c) r5
                    java.lang.Object r5 = r5.c()
                    boolean r5 = r5 instanceof io.ktor.utils.io.i
                    if (r5 != 0) goto L_0x0067
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0067:
                    java.lang.Object r5 = r1.l()
                    io.ktor.request.c r5 = (io.ktor.request.c) r5
                    kotlin.reflect.c r5 = r5.a()
                    java.lang.Class<io.ktor.utils.io.i> r6 = io.ktor.utils.io.i.class
                    kotlin.reflect.c r6 = kotlin.jvm.internal.a0.b(r6)
                    boolean r5 = kotlin.jvm.internal.k.a(r5, r6)
                    if (r5 == 0) goto L_0x0080
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0080:
                    r5 = r1
                    r6 = 0
                    java.lang.Object r7 = r5.getContext()     // Catch:{ BadContentTypeFormatException -> 0x0114 }
                    io.ktor.application.b r7 = (io.ktor.application.b) r7     // Catch:{ BadContentTypeFormatException -> 0x0114 }
                    io.ktor.request.d r5 = r7.getRequest()     // Catch:{ BadContentTypeFormatException -> 0x0114 }
                    io.ktor.http.c r5 = io.ktor.request.e.c(r5)     // Catch:{ BadContentTypeFormatException -> 0x0114 }
                    io.ktor.http.c r5 = r5.j()     // Catch:{ BadContentTypeFormatException -> 0x0114 }
                    io.ktor.features.g r6 = r14.$feature
                    java.util.List r6 = r6.c()
                    r7 = 0
                    java.util.Iterator r8 = r6.iterator()
                L_0x00a2:
                    boolean r9 = r8.hasNext()
                    if (r9 == 0) goto L_0x00c4
                    java.lang.Object r9 = r8.next()
                    r10 = r9
                    io.ktor.features.g$b r10 = (io.ktor.features.g.b) r10
                    r11 = 0
                    io.ktor.http.c r12 = r10.a()
                    boolean r10 = r5.h(r12)
                    java.lang.Boolean r10 = kotlin.coroutines.jvm.internal.b.a(r10)
                    boolean r10 = r10.booleanValue()
                    if (r10 == 0) goto L_0x00a2
                    r3 = r9
                    goto L_0x00c5
                L_0x00c4:
                L_0x00c5:
                    io.ktor.features.g$b r3 = (io.ktor.features.g.b) r3
                    if (r3 == 0) goto L_0x010e
                    io.ktor.features.f r6 = r3.b()
                    r14.L$0 = r1
                    r14.L$1 = r4
                    r14.L$2 = r5
                    r14.L$3 = r3
                    r14.label = r2
                    java.lang.Object r6 = r6.convertForReceive(r1, r14)
                    if (r6 != r0) goto L_0x00df
                    return r0
                L_0x00df:
                    r13 = r5
                    r5 = r1
                    r1 = r4
                    r4 = r13
                L_0x00e3:
                    if (r6 == 0) goto L_0x0108
                    io.ktor.request.c r7 = new io.ktor.request.c
                    kotlin.reflect.n r8 = r1.b()
                    r7.<init>(r8, r6, r2)
                    r14.L$0 = r5
                    r14.L$1 = r1
                    r14.L$2 = r4
                    r14.L$3 = r3
                    r14.L$4 = r6
                    r2 = 2
                    r14.label = r2
                    java.lang.Object r2 = r5.E(r7, r14)
                    if (r2 != r0) goto L_0x0102
                    return r0
                L_0x0102:
                    r2 = r4
                    r4 = r5
                    r0 = r6
                L_0x0105:
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0108:
                    io.ktor.features.UnsupportedMediaTypeException r0 = new io.ktor.features.UnsupportedMediaTypeException
                    r0.<init>(r4)
                    throw r0
                L_0x010e:
                    io.ktor.features.UnsupportedMediaTypeException r0 = new io.ktor.features.UnsupportedMediaTypeException
                    r0.<init>(r5)
                    throw r0
                L_0x0114:
                    r0 = move-exception
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder
                    r2.<init>()
                    java.lang.String r3 = "Illegal Content-Type header format: "
                    r2.append(r3)
                    r3 = r1
                    r5 = 0
                    java.lang.Object r6 = r3.getContext()
                    io.ktor.application.b r6 = (io.ktor.application.b) r6
                    io.ktor.request.d r3 = r6.getRequest()
                    io.ktor.http.o r3 = r3.getHeaders()
                    io.ktor.http.s r5 = io.ktor.http.s.V0
                    java.lang.String r5 = r5.s()
                    java.lang.String r3 = r3.get(r5)
                    r2.append(r3)
                    java.lang.String r2 = r2.toString()
                    io.ktor.features.BadRequestException r3 = new io.ktor.features.BadRequestException
                    r3.<init>(r2, r0)
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.features.g.c.C0245c.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }
}
