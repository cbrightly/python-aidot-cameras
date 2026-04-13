package io.ktor.server.engine;

import com.google.android.gms.actions.SearchIntents;
import io.ktor.http.content.g;
import io.ktor.http.content.j;
import io.ktor.http.content.k;
import io.ktor.http.s;
import io.ktor.http.y;
import io.ktor.http.z;
import io.ktor.request.e;
import io.ktor.utils.io.i;
import java.nio.charset.Charset;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.p;
import kotlin.x;
import kotlinx.coroutines.d1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultTransform.kt */
public final class n {
    /* access modifiers changed from: private */
    public static final kotlin.reflect.c<? extends Object>[] a = {a0.b(byte[].class), a0.b(String.class), a0.b(y.class)};

    @f(c = "io.ktor.server.engine.DefaultTransformKt", f = "DefaultTransform.kt", l = {112}, m = "readText")
    /* compiled from: DefaultTransform.kt */
    public static final class c extends d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        c(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return n.f((i) null, (Charset) null, this);
        }
    }

    @f(c = "io.ktor.server.engine.DefaultTransformKt$installDefaultTransformations$1", f = "DefaultTransform.kt", l = {34}, m = "invokeSuspend")
    /* compiled from: DefaultTransform.kt */
    public static final class a extends l implements q<io.ktor.util.pipeline.d<Object, io.ktor.application.b>, Object, kotlin.coroutines.d<? super x>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        private io.ktor.util.pipeline.d p$;
        private Object p$0;

        a(kotlin.coroutines.d dVar) {
            super(3, dVar);
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<Object, io.ktor.application.b> dVar, @NotNull Object obj, @NotNull kotlin.coroutines.d<? super x> dVar2) {
            k.f(dVar, "$this$create");
            k.f(obj, "value");
            k.f(dVar2, "continuation");
            a aVar = new a(dVar2);
            aVar.p$ = dVar;
            aVar.p$0 = obj;
            return aVar;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((a) create((io.ktor.util.pipeline.d) obj, obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    p.b($result);
                    io.ktor.util.pipeline.d $this$intercept = this.p$;
                    Object value = this.p$0;
                    j transformed = io.ktor.http.content.b.a($this$intercept, value);
                    if (transformed != null) {
                        this.L$0 = $this$intercept;
                        this.L$1 = value;
                        this.L$2 = transformed;
                        this.label = 1;
                        if ($this$intercept.E(transformed, this) != d) {
                            j jVar = transformed;
                            break;
                        } else {
                            return d;
                        }
                    }
                    break;
                case 1:
                    j transformed2 = (j) this.L$2;
                    Object value2 = this.L$1;
                    io.ktor.util.pipeline.d $this$intercept2 = (io.ktor.util.pipeline.d) this.L$0;
                    p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    public static final void d(@NotNull io.ktor.response.d $this$installDefaultTransformations) {
        k.f($this$installDefaultTransformations, "$this$installDefaultTransformations");
        $this$installDefaultTransformations.p(io.ktor.response.d.p3.b(), new a((kotlin.coroutines.d) null));
    }

    @f(c = "io.ktor.server.engine.DefaultTransformKt$installDefaultTransformations$2", f = "DefaultTransform.kt", l = {48, 53, 59, 64, 81}, m = "invokeSuspend")
    /* compiled from: DefaultTransform.kt */
    public static final class b extends l implements q<io.ktor.util.pipeline.d<io.ktor.request.c, io.ktor.application.b>, io.ktor.request.c, kotlin.coroutines.d<? super x>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        private io.ktor.util.pipeline.d p$;
        private io.ktor.request.c p$0;

        b(kotlin.coroutines.d dVar) {
            super(3, dVar);
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<io.ktor.request.c, io.ktor.application.b> dVar, @NotNull io.ktor.request.c cVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
            k.f(dVar, "$this$create");
            k.f(cVar, SearchIntents.EXTRA_QUERY);
            k.f(dVar2, "continuation");
            b bVar = new b(dVar2);
            bVar.p$ = dVar;
            bVar.p$0 = cVar;
            return bVar;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((b) create((io.ktor.util.pipeline.d) obj, (io.ktor.request.c) obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v11, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v19, resolved type: io.ktor.utils.io.i} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: io.ktor.request.c} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v25, resolved type: io.ktor.util.pipeline.d} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v22, resolved type: io.ktor.utils.io.i} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: io.ktor.request.c} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v28, resolved type: io.ktor.util.pipeline.d} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v11, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v17, resolved type: io.ktor.request.c} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v20, resolved type: io.ktor.util.pipeline.d} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v20, resolved type: io.ktor.request.c} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v9, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v15, resolved type: io.ktor.util.pipeline.d} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v26, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: io.ktor.request.c} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v27, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v23, resolved type: io.ktor.util.pipeline.d} */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00f2, code lost:
            r8 = r3;
            r3 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x015e, code lost:
            r8 = r3;
            r3 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x01ec, code lost:
            r4 = io.ktor.http.d0.d((java.lang.String) r7, 0, 0, 6, (java.lang.Object) null);
            r3 = r8;
            r8 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x0235, code lost:
            r15 = r4;
            r4 = r10.m();
            r3 = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x026c, code lost:
            if (r4 == null) goto L_0x0299;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:0x026e, code lost:
            r5 = new io.ktor.request.c(r2.b(), r4, kotlin.collections.l.r(io.ktor.server.engine.n.a(), r2.a()));
            r1.L$0 = r8;
            r1.L$1 = r2;
            r1.L$2 = r3;
            r1.L$3 = r4;
            r1.label = 5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x0292, code lost:
            if (r8.E(r5, r1) != r0) goto L_0x0295;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:75:0x0294, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:76:0x0295, code lost:
            r0 = r2;
            r2 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:0x0297, code lost:
            r8 = r2;
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x029b, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r17) {
            /*
                r16 = this;
                r1 = r16
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r1.label
                r3 = 0
                r4 = 0
                switch(r2) {
                    case 0: goto L_0x00ac;
                    case 1: goto L_0x0094;
                    case 2: goto L_0x007b;
                    case 3: goto L_0x005b;
                    case 4: goto L_0x002e;
                    case 5: goto L_0x0015;
                    default: goto L_0x000d;
                }
            L_0x000d:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r2)
                throw r0
            L_0x0015:
                r0 = r4
                r2 = r4
                r3 = r4
                java.lang.Object r4 = r1.L$3
                java.lang.Object r5 = r1.L$2
                r3 = r5
                io.ktor.utils.io.i r3 = (io.ktor.utils.io.i) r3
                java.lang.Object r5 = r1.L$1
                r0 = r5
                io.ktor.request.c r0 = (io.ktor.request.c) r0
                java.lang.Object r5 = r1.L$0
                r2 = r5
                io.ktor.util.pipeline.d r2 = (io.ktor.util.pipeline.d) r2
                kotlin.p.b(r17)
                goto L_0x0297
            L_0x002e:
                r2 = r4
                r5 = r4
                r6 = r4
                r7 = r3
                r8 = r4
                r9 = r4
                java.lang.Object r10 = r1.L$6
                r5 = r10
                io.ktor.http.z r5 = (io.ktor.http.z) r5
                java.lang.Object r10 = r1.L$5
                io.ktor.http.z r10 = (io.ktor.http.z) r10
                java.lang.Object r11 = r1.L$4
                r6 = r11
                io.ktor.http.y$a r6 = (io.ktor.http.y.a) r6
                java.lang.Object r11 = r1.L$3
                r9 = r11
                io.ktor.http.c r9 = (io.ktor.http.c) r9
                java.lang.Object r11 = r1.L$2
                r4 = r11
                io.ktor.utils.io.i r4 = (io.ktor.utils.io.i) r4
                java.lang.Object r11 = r1.L$1
                r2 = r11
                io.ktor.request.c r2 = (io.ktor.request.c) r2
                java.lang.Object r11 = r1.L$0
                r8 = r11
                io.ktor.util.pipeline.d r8 = (io.ktor.util.pipeline.d) r8
                kotlin.p.b(r17)
                goto L_0x0235
            L_0x005b:
                r2 = r4
                r5 = r4
                r6 = r4
                r7 = r4
                java.lang.Object r8 = r1.L$3
                r6 = r8
                io.ktor.http.c r6 = (io.ktor.http.c) r6
                java.lang.Object r8 = r1.L$2
                r7 = r8
                io.ktor.utils.io.i r7 = (io.ktor.utils.io.i) r7
                java.lang.Object r8 = r1.L$1
                r2 = r8
                io.ktor.request.c r2 = (io.ktor.request.c) r2
                java.lang.Object r8 = r1.L$0
                r5 = r8
                io.ktor.util.pipeline.d r5 = (io.ktor.util.pipeline.d) r5
                kotlin.p.b(r17)
                r8 = r7
                r7 = r17
                goto L_0x01ec
            L_0x007b:
                r2 = r4
                r3 = r4
                java.lang.Object r5 = r1.L$2
                r4 = r5
                io.ktor.utils.io.i r4 = (io.ktor.utils.io.i) r4
                java.lang.Object r5 = r1.L$1
                r2 = r5
                io.ktor.request.c r2 = (io.ktor.request.c) r2
                java.lang.Object r5 = r1.L$0
                r3 = r5
                io.ktor.util.pipeline.d r3 = (io.ktor.util.pipeline.d) r3
                kotlin.p.b(r17)
                r5 = r4
                r4 = r17
                goto L_0x015e
            L_0x0094:
                r2 = r4
                r3 = r4
                java.lang.Object r5 = r1.L$2
                r4 = r5
                io.ktor.utils.io.i r4 = (io.ktor.utils.io.i) r4
                java.lang.Object r5 = r1.L$1
                r2 = r5
                io.ktor.request.c r2 = (io.ktor.request.c) r2
                java.lang.Object r5 = r1.L$0
                r3 = r5
                io.ktor.util.pipeline.d r3 = (io.ktor.util.pipeline.d) r3
                kotlin.p.b(r17)
                r5 = r4
                r4 = r17
                goto L_0x00f2
            L_0x00ac:
                kotlin.p.b(r17)
                io.ktor.util.pipeline.d r8 = r1.p$
                io.ktor.request.c r2 = r1.p$0
                java.lang.Object r5 = r2.c()
                boolean r6 = r5 instanceof io.ktor.utils.io.i
                if (r6 != 0) goto L_0x00bc
                r5 = r4
            L_0x00bc:
                io.ktor.utils.io.i r5 = (io.ktor.utils.io.i) r5
                if (r5 == 0) goto L_0x029c
                kotlin.reflect.c r6 = r2.a()
                java.lang.Class<io.ktor.utils.io.i> r7 = io.ktor.utils.io.i.class
                kotlin.reflect.c r7 = kotlin.jvm.internal.a0.b(r7)
                boolean r7 = kotlin.jvm.internal.k.a(r6, r7)
                if (r7 == 0) goto L_0x00d4
                r3 = r5
                r4 = r3
                goto L_0x026b
            L_0x00d4:
                java.lang.Class<byte[]> r7 = byte[].class
                kotlin.reflect.c r7 = kotlin.jvm.internal.a0.b(r7)
                boolean r7 = kotlin.jvm.internal.k.a(r6, r7)
                r9 = 1
                if (r7 == 0) goto L_0x00f6
                r1.L$0 = r8
                r1.L$1 = r2
                r1.L$2 = r5
                r1.label = r9
                java.lang.Object r3 = io.ktor.util.cio.d.b(r5, r3, r1, r9, r4)
                if (r3 != r0) goto L_0x00f0
                return r0
            L_0x00f0:
                r4 = r3
                r3 = r8
            L_0x00f2:
                r8 = r3
                r3 = r5
                goto L_0x026b
            L_0x00f6:
                java.lang.Class<java.io.InputStream> r7 = java.io.InputStream.class
                kotlin.reflect.c r7 = kotlin.jvm.internal.a0.b(r7)
                boolean r7 = kotlin.jvm.internal.k.a(r6, r7)
                if (r7 == 0) goto L_0x0109
                java.io.InputStream r4 = io.ktor.utils.io.jvm.javaio.b.b(r5, r4, r9, r4)
                r3 = r5
                goto L_0x026b
            L_0x0109:
                java.lang.Class<io.ktor.http.content.g> r7 = io.ktor.http.content.g.class
                kotlin.reflect.c r7 = kotlin.jvm.internal.a0.b(r7)
                boolean r7 = kotlin.jvm.internal.k.a(r6, r7)
                if (r7 == 0) goto L_0x011c
                io.ktor.http.content.g r4 = io.ktor.server.engine.n.e(r8, r5)
                r3 = r5
                goto L_0x026b
            L_0x011c:
                java.lang.Class<java.lang.String> r7 = java.lang.String.class
                kotlin.reflect.c r7 = kotlin.jvm.internal.a0.b(r7)
                boolean r7 = kotlin.jvm.internal.k.a(r6, r7)
                java.lang.String r10 = "Illegal Content-Type header format: "
                if (r7 == 0) goto L_0x018b
                r3 = r8
                r4 = 0
                java.lang.Object r6 = r3.getContext()
                io.ktor.application.b r6 = (io.ktor.application.b) r6
                r3 = r6
                r4 = 0
                r6 = 0
                r7 = r8
                r9 = 0
                java.lang.Object r11 = r7.getContext()     // Catch:{ BadContentTypeFormatException -> 0x0162 }
                io.ktor.application.b r11 = (io.ktor.application.b) r11     // Catch:{ BadContentTypeFormatException -> 0x0162 }
                io.ktor.request.d r7 = r11.getRequest()     // Catch:{ BadContentTypeFormatException -> 0x0162 }
                java.nio.charset.Charset r7 = io.ktor.request.e.b(r7)     // Catch:{ BadContentTypeFormatException -> 0x0162 }
                if (r7 == 0) goto L_0x014a
                goto L_0x014c
            L_0x014a:
                java.nio.charset.Charset r7 = kotlin.text.c.f
            L_0x014c:
                r1.L$0 = r8
                r1.L$1 = r2
                r1.L$2 = r5
                r3 = 2
                r1.label = r3
                java.lang.Object r3 = io.ktor.server.engine.n.f(r5, r7, r1)
                if (r3 != r0) goto L_0x015c
                return r0
            L_0x015c:
                r4 = r3
                r3 = r8
            L_0x015e:
                r8 = r3
                r3 = r5
                goto L_0x026b
            L_0x0162:
                r0 = move-exception
                io.ktor.features.BadRequestException r6 = new io.ktor.features.BadRequestException
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                r7.append(r10)
                io.ktor.request.d r9 = r3.getRequest()
                io.ktor.http.o r9 = r9.getHeaders()
                io.ktor.http.s r10 = io.ktor.http.s.V0
                java.lang.String r10 = r10.s()
                java.lang.String r9 = r9.get(r10)
                r7.append(r9)
                java.lang.String r7 = r7.toString()
                r6.<init>(r7, r0)
                throw r6
            L_0x018b:
                java.lang.Class<io.ktor.http.y> r7 = io.ktor.http.y.class
                kotlin.reflect.c r7 = kotlin.jvm.internal.a0.b(r7)
                boolean r6 = kotlin.jvm.internal.k.a(r6, r7)
                if (r6 == 0) goto L_0x026a
                r6 = r8
                r7 = 0
                java.lang.Object r11 = r6.getContext()
                r6 = r11
                io.ktor.application.b r6 = (io.ktor.application.b) r6
                r7 = 0
                r11 = 0
                r12 = r8
                r13 = 0
                java.lang.Object r14 = r12.getContext()     // Catch:{ BadContentTypeFormatException -> 0x0241 }
                io.ktor.application.b r14 = (io.ktor.application.b) r14     // Catch:{ BadContentTypeFormatException -> 0x0241 }
                io.ktor.request.d r12 = r14.getRequest()     // Catch:{ BadContentTypeFormatException -> 0x0241 }
                io.ktor.http.c r10 = io.ktor.request.e.c(r12)     // Catch:{ BadContentTypeFormatException -> 0x0241 }
                r6 = r10
                io.ktor.http.c$a r7 = io.ktor.http.c.a.t
                io.ktor.http.c r7 = r7.a()
                boolean r7 = r6.h(r7)
                if (r7 == 0) goto L_0x01f7
                r7 = r8
                r9 = 0
                java.lang.Object r10 = r7.getContext()
                io.ktor.application.b r10 = (io.ktor.application.b) r10
                io.ktor.request.d r7 = r10.getRequest()
                java.nio.charset.Charset r7 = io.ktor.request.e.b(r7)
                if (r7 == 0) goto L_0x01d5
                goto L_0x01d7
            L_0x01d5:
                java.nio.charset.Charset r7 = kotlin.text.c.f
            L_0x01d7:
                r1.L$0 = r8
                r1.L$1 = r2
                r1.L$2 = r5
                r1.L$3 = r6
                r9 = 3
                r1.label = r9
                java.lang.Object r7 = io.ktor.server.engine.n.f(r5, r7, r1)
                if (r7 != r0) goto L_0x01e9
                return r0
            L_0x01e9:
                r15 = r8
                r8 = r5
                r5 = r15
            L_0x01ec:
                java.lang.String r7 = (java.lang.String) r7
                r9 = 6
                io.ktor.http.y r4 = io.ktor.http.d0.d(r7, r3, r3, r9, r4)
                r3 = r8
                r8 = r5
                goto L_0x026b
            L_0x01f7:
                io.ktor.http.c$c r7 = io.ktor.http.c.C0247c.i
                io.ktor.http.c r7 = r7.a()
                boolean r7 = r6.h(r7)
                if (r7 == 0) goto L_0x023e
                io.ktor.http.y$a r7 = io.ktor.http.y.b
                r10 = 0
                io.ktor.http.z r11 = new io.ktor.http.z
                r11.<init>(r3, r9, r4)
                r3 = r11
                r9 = 0
                io.ktor.http.content.g r12 = io.ktor.server.engine.n.e(r8, r5)
                io.ktor.server.engine.n$b$a r13 = new io.ktor.server.engine.n$b$a
                r13.<init>(r3, r4)
                r1.L$0 = r8
                r1.L$1 = r2
                r1.L$2 = r5
                r1.L$3 = r6
                r1.L$4 = r7
                r1.L$5 = r11
                r1.L$6 = r3
                r4 = 4
                r1.label = r4
                java.lang.Object r4 = io.ktor.http.content.i.a(r12, r13, r1)
                if (r4 != r0) goto L_0x022e
                return r0
            L_0x022e:
                r4 = r5
                r5 = r3
                r3 = r9
                r9 = r6
                r6 = r7
                r7 = r10
                r10 = r11
            L_0x0235:
                io.ktor.http.y r3 = r10.m()
                r15 = r4
                r4 = r3
                r3 = r15
                goto L_0x026b
            L_0x023e:
                r3 = r5
                goto L_0x026b
            L_0x0241:
                r0 = move-exception
                io.ktor.features.BadRequestException r3 = new io.ktor.features.BadRequestException
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r10)
                io.ktor.request.d r9 = r6.getRequest()
                io.ktor.http.o r9 = r9.getHeaders()
                io.ktor.http.s r10 = io.ktor.http.s.V0
                java.lang.String r10 = r10.s()
                java.lang.String r9 = r9.get(r10)
                r4.append(r9)
                java.lang.String r4 = r4.toString()
                r3.<init>(r4, r0)
                throw r3
            L_0x026a:
                r3 = r5
            L_0x026b:
                if (r4 == 0) goto L_0x0299
                io.ktor.request.c r5 = new io.ktor.request.c
                kotlin.reflect.n r6 = r2.b()
                kotlin.reflect.c[] r7 = io.ktor.server.engine.n.a
                kotlin.reflect.c r9 = r2.a()
                boolean r7 = kotlin.collections.l.r(r7, r9)
                r5.<init>(r6, r4, r7)
                r1.L$0 = r8
                r1.L$1 = r2
                r1.L$2 = r3
                r1.L$3 = r4
                r6 = 5
                r1.label = r6
                java.lang.Object r5 = r8.E(r5, r1)
                if (r5 != r0) goto L_0x0295
                return r0
            L_0x0295:
                r0 = r2
                r2 = r8
            L_0x0297:
                r8 = r2
                r2 = r0
            L_0x0299:
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x029c:
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.n.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @f(c = "io.ktor.server.engine.DefaultTransformKt$installDefaultTransformations$2$transformed$2$1", f = "DefaultTransform.kt", l = {}, m = "invokeSuspend")
        /* compiled from: DefaultTransform.kt */
        public static final class a extends l implements kotlin.jvm.functions.p<io.ktor.http.content.k, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ z $this_build;
            int label;
            private io.ktor.http.content.k p$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(z zVar, kotlin.coroutines.d dVar) {
                super(2, dVar);
                this.$this_build = zVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                k.f(dVar, "completion");
                a aVar = new a(this.$this_build, dVar);
                io.ktor.http.content.k kVar = (io.ktor.http.content.k) obj;
                aVar.p$0 = (io.ktor.http.content.k) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((a) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                String partName;
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        p.b($result);
                        io.ktor.http.content.k part = this.p$0;
                        if ((part instanceof k.b) && (partName = part.d()) != null) {
                            this.$this_build.a(partName, ((k.b) part).e());
                        }
                        part.b().invoke();
                        return x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    public static final void c(@NotNull io.ktor.request.b $this$installDefaultTransformations) {
        kotlin.jvm.internal.k.f($this$installDefaultTransformations, "$this$installDefaultTransformations");
        $this$installDefaultTransformations.p(io.ktor.request.b.a1.a(), new b((kotlin.coroutines.d) null));
    }

    /* access modifiers changed from: private */
    public static final g e(@NotNull io.ktor.util.pipeline.d<?, io.ktor.application.b> $this$multiPartData, i rc) {
        io.ktor.request.d request = $this$multiPartData.getContext().getRequest();
        s sVar = s.V0;
        String contentType = e.f(request, sVar.s());
        if (contentType != null) {
            String f = e.f($this$multiPartData.getContext().getRequest(), sVar.r());
            return new io.ktor.http.cio.b($this$multiPartData.getCoroutineContext().plus(d1.d()), rc, contentType, f != null ? Long.valueOf(Long.parseLong(f)) : null, 0, 0, 48, (DefaultConstructorMarker) null);
        }
        throw new IllegalStateException("Content-Type header is required for multipart processing");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: java.nio.charset.Charset} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0065 A[Catch:{ all -> 0x0080 }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006d A[Catch:{ all -> 0x0080 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object f(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r6, @org.jetbrains.annotations.NotNull java.nio.charset.Charset r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.String> r8) {
        /*
            boolean r0 = r8 instanceof io.ktor.server.engine.n.c
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.server.engine.n$c r0 = (io.ktor.server.engine.n.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.engine.n$c r0 = new io.ktor.server.engine.n$c
            r0.<init>(r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003a;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r2 = r0.L$1
            r7 = r2
            java.nio.charset.Charset r7 = (java.nio.charset.Charset) r7
            java.lang.Object r2 = r0.L$0
            r6 = r2
            io.ktor.utils.io.i r6 = (io.ktor.utils.io.i) r6
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x0059
        L_0x003a:
            kotlin.p.b(r1)
            boolean r3 = r6.y()
            if (r3 == 0) goto L_0x0046
            java.lang.String r2 = ""
            return r2
        L_0x0046:
            r3 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r0.L$0 = r6
            r0.L$1 = r7
            r5 = 1
            r0.label = r5
            java.lang.Object r3 = io.ktor.utils.io.k.f(r6, r3, r0)
            if (r3 != r2) goto L_0x0059
            return r2
        L_0x0059:
            r2 = r3
            io.ktor.utils.io.core.q r2 = (io.ktor.utils.io.core.q) r2
            java.nio.charset.Charset r3 = kotlin.text.c.a     // Catch:{ all -> 0x0080 }
            boolean r3 = kotlin.jvm.internal.k.a(r7, r3)     // Catch:{ all -> 0x0080 }
            if (r3 == 0) goto L_0x006d
            r3 = 3
            r4 = 0
            r5 = 0
            java.lang.String r3 = io.ktor.utils.io.core.a.l1(r2, r5, r5, r3, r4)     // Catch:{ all -> 0x0080 }
            goto L_0x007a
        L_0x006d:
            java.io.InputStream r3 = io.ktor.utils.io.streams.d.a(r2)     // Catch:{ all -> 0x0080 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ all -> 0x0080 }
            r4.<init>(r3, r7)     // Catch:{ all -> 0x0080 }
            java.lang.String r3 = kotlin.io.k.c(r4)     // Catch:{ all -> 0x0080 }
        L_0x007a:
            r2.release()
            return r3
        L_0x0080:
            r3 = move-exception
            r2.release()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.n.f(io.ktor.utils.io.i, java.nio.charset.Charset, kotlin.coroutines.d):java.lang.Object");
    }
}
