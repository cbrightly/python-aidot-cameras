package io.ktor.server.engine;

import io.ktor.application.g;
import io.ktor.features.BadRequestException;
import io.ktor.features.NotFoundException;
import io.ktor.features.UnsupportedMediaTypeException;
import io.ktor.features.e;
import io.ktor.http.v;
import io.ktor.server.engine.v;
import io.ktor.util.cio.ChannelIOException;
import io.ktor.util.t;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeoutException;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import kotlinx.coroutines.TimeoutCancellationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DefaultEnginePipeline.kt */
public final class m {

    @f(c = "io.ktor.server.engine.DefaultEnginePipelineKt", f = "DefaultEnginePipeline.kt", l = {120}, m = "tryRespondError")
    /* compiled from: DefaultEnginePipeline.kt */
    public static final class c extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        c(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return m.f((io.ktor.util.pipeline.d<x, io.ktor.application.b>) null, (v) null, this);
        }
    }

    @NotNull
    public static final s b(@NotNull io.ktor.application.d environment) {
        String url;
        k.f(environment, "environment");
        s pipeline = new s();
        io.ktor.config.b a2 = environment.getConfig().a("ktor.deployment.shutdown.url");
        if (!(a2 == null || (url = a2.a()) == null)) {
            v vVar = (v) g.b(pipeline, v.b.b, new a(url));
        }
        pipeline.p(s.p0.b(), new b((kotlin.coroutines.d) null));
        return pipeline;
    }

    /* compiled from: DefaultEnginePipeline.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<v.a, x> {
        final /* synthetic */ String $url;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(String str) {
            super(1);
            this.$url = str;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((v.a) obj);
            return x.a;
        }

        public final void invoke(@NotNull v.a $this$install) {
            k.f($this$install, "$receiver");
            $this$install.c(this.$url);
        }
    }

    @f(c = "io.ktor.server.engine.DefaultEnginePipelineKt$defaultEnginePipeline$2", f = "DefaultEnginePipeline.kt", l = {120, 123, 54, 41, 54, 47, 54, 54}, m = "invokeSuspend")
    /* compiled from: DefaultEnginePipeline.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements q<io.ktor.util.pipeline.d<x, io.ktor.application.b>, x, kotlin.coroutines.d<? super x>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        private io.ktor.util.pipeline.d p$;
        private x p$0;

        b(kotlin.coroutines.d dVar) {
            super(3, dVar);
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@NotNull io.ktor.util.pipeline.d<x, io.ktor.application.b> dVar, @NotNull x xVar, @NotNull kotlin.coroutines.d<? super x> dVar2) {
            k.f(dVar, "$this$create");
            k.f(xVar, "it");
            k.f(dVar2, "continuation");
            b bVar = new b(dVar2);
            bVar.p$ = dVar;
            bVar.p$0 = xVar;
            return bVar;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((b) create((io.ktor.util.pipeline.d) obj, (x) obj2, (kotlin.coroutines.d) obj3)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v11, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: io.ktor.util.pipeline.d} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v15, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: io.ktor.util.pipeline.d} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v21, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v20, resolved type: io.ktor.util.pipeline.d} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v25, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v23, resolved type: io.ktor.util.pipeline.d} */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0124, code lost:
            if (((io.ktor.application.b) r5.getContext()).b().b() != null) goto L_0x0151;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x0126, code lost:
            r6 = (io.ktor.application.b) r5.getContext();
            r1 = io.ktor.http.v.c0.x();
            r7 = r6.b().a();
            r10.L$0 = r5;
            r10.L$1 = r4;
            r10.L$2 = r6;
            r10.L$3 = r1;
            r10.label = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x014c, code lost:
            if (r7.e(r6, r1, r10) != r0) goto L_0x014f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x014e, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x014f, code lost:
            r1 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0151, code lost:
            r1 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
            r2 = ((io.ktor.application.b) r1.getContext()).getRequest().c();
            r10.L$0 = r1;
            r10.L$1 = r4;
            r10.label = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x016e, code lost:
            if (io.ktor.utils.io.k.d(r2, r10) != r0) goto L_0x0171;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0170, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0171, code lost:
            r0 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x0174, code lost:
            r0 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
            r2 = ((io.ktor.application.b) r5.getContext()).getRequest().c();
            r10.L$0 = r5;
            r10.L$1 = r1;
            r10.label = 7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:0x01b5, code lost:
            if (io.ktor.utils.io.k.d(r2, r10) != r0) goto L_0x01b8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x01b7, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x01b8, code lost:
            r0 = r1;
            r1 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:0x01bc, code lost:
            r0 = r1;
            r1 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
            r2 = ((io.ktor.application.b) r5.getContext()).getRequest().c();
            r10.L$0 = r5;
            r10.L$1 = r1;
            r10.label = 5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x01fc, code lost:
            if (io.ktor.utils.io.k.d(r2, r10) != r0) goto L_0x01ff;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x01fe, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:0x01ff, code lost:
            r0 = r1;
            r1 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:0x0204, code lost:
            r0 = r1;
            r1 = r5;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                r10 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r10.label
                r2 = 0
                r3 = 0
                switch(r1) {
                    case 0: goto L_0x00dd;
                    case 1: goto L_0x00c1;
                    case 2: goto L_0x00a4;
                    case 3: goto L_0x0090;
                    case 4: goto L_0x006f;
                    case 5: goto L_0x005b;
                    case 6: goto L_0x003f;
                    case 7: goto L_0x002b;
                    case 8: goto L_0x0013;
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
                java.lang.Object r2 = r10.L$2
                java.lang.Throwable r2 = (java.lang.Throwable) r2
                java.lang.Object r3 = r10.L$1
                r0 = r3
                kotlin.x r0 = (kotlin.x) r0
                java.lang.Object r3 = r10.L$0
                r1 = r3
                io.ktor.util.pipeline.d r1 = (io.ktor.util.pipeline.d) r1
                kotlin.p.b(r11)     // Catch:{ all -> 0x0028 }
                goto L_0x0231
            L_0x0028:
                r3 = move-exception
                goto L_0x0234
            L_0x002b:
                r0 = r3
                r1 = r3
                java.lang.Object r2 = r10.L$1
                r0 = r2
                kotlin.x r0 = (kotlin.x) r0
                java.lang.Object r2 = r10.L$0
                r1 = r2
                io.ktor.util.pipeline.d r1 = (io.ktor.util.pipeline.d) r1
                kotlin.p.b(r11)     // Catch:{ all -> 0x003c }
                goto L_0x0172
            L_0x003c:
                r2 = move-exception
                goto L_0x0175
            L_0x003f:
                r1 = r3
                r4 = r3
                r5 = r3
                java.lang.Object r6 = r10.L$3
                r4 = r6
                io.ktor.features.c$d r4 = (io.ktor.features.c.d) r4
                java.lang.Object r6 = r10.L$2
                r3 = r6
                java.lang.Throwable r3 = (java.lang.Throwable) r3
                java.lang.Object r6 = r10.L$1
                r1 = r6
                kotlin.x r1 = (kotlin.x) r1
                java.lang.Object r6 = r10.L$0
                r5 = r6
                io.ktor.util.pipeline.d r5 = (io.ktor.util.pipeline.d) r5
                kotlin.p.b(r11)     // Catch:{ all -> 0x008b }
                goto L_0x0197
            L_0x005b:
                r0 = r3
                r1 = r3
                java.lang.Object r2 = r10.L$1
                r0 = r2
                kotlin.x r0 = (kotlin.x) r0
                java.lang.Object r2 = r10.L$0
                r1 = r2
                io.ktor.util.pipeline.d r1 = (io.ktor.util.pipeline.d) r1
                kotlin.p.b(r11)     // Catch:{ all -> 0x006c }
                goto L_0x0172
            L_0x006c:
                r2 = move-exception
                goto L_0x0175
            L_0x006f:
                r1 = r3
                r4 = r3
                r5 = r3
                java.lang.Object r6 = r10.L$3
                r4 = r6
                io.ktor.features.c$d r4 = (io.ktor.features.c.d) r4
                java.lang.Object r6 = r10.L$2
                r3 = r6
                io.ktor.util.cio.ChannelIOException r3 = (io.ktor.util.cio.ChannelIOException) r3
                java.lang.Object r6 = r10.L$1
                r1 = r6
                kotlin.x r1 = (kotlin.x) r1
                java.lang.Object r6 = r10.L$0
                r5 = r6
                io.ktor.util.pipeline.d r5 = (io.ktor.util.pipeline.d) r5
                kotlin.p.b(r11)     // Catch:{ all -> 0x008b }
                goto L_0x01de
            L_0x008b:
                r2 = move-exception
                r4 = r1
                r1 = r5
                goto L_0x020e
            L_0x0090:
                r0 = r3
                r1 = r3
                java.lang.Object r2 = r10.L$1
                r0 = r2
                kotlin.x r0 = (kotlin.x) r0
                java.lang.Object r2 = r10.L$0
                r1 = r2
                io.ktor.util.pipeline.d r1 = (io.ktor.util.pipeline.d) r1
                kotlin.p.b(r11)     // Catch:{ all -> 0x00a1 }
                goto L_0x0172
            L_0x00a1:
                r2 = move-exception
                goto L_0x0175
            L_0x00a4:
                r1 = r3
                r4 = r3
                r5 = r3
                r6 = r3
                java.lang.Object r7 = r10.L$3
                r1 = r7
                io.ktor.http.v r1 = (io.ktor.http.v) r1
                java.lang.Object r7 = r10.L$2
                r6 = r7
                io.ktor.application.b r6 = (io.ktor.application.b) r6
                java.lang.Object r7 = r10.L$1
                r4 = r7
                kotlin.x r4 = (kotlin.x) r4
                java.lang.Object r7 = r10.L$0
                r5 = r7
                io.ktor.util.pipeline.d r5 = (io.ktor.util.pipeline.d) r5
                kotlin.p.b(r11)     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                goto L_0x014f
            L_0x00c1:
                r1 = r3
                r4 = r3
                r5 = r3
                r6 = r3
                java.lang.Object r7 = r10.L$3
                r1 = r7
                io.ktor.application.b r1 = (io.ktor.application.b) r1
                java.lang.Object r7 = r10.L$2
                r6 = r7
                io.ktor.util.pipeline.c r6 = (io.ktor.util.pipeline.c) r6
                java.lang.Object r7 = r10.L$1
                r4 = r7
                kotlin.x r4 = (kotlin.x) r4
                java.lang.Object r7 = r10.L$0
                r5 = r7
                io.ktor.util.pipeline.d r5 = (io.ktor.util.pipeline.d) r5
                kotlin.p.b(r11)     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                goto L_0x0113
            L_0x00dd:
                kotlin.p.b(r11)
                io.ktor.util.pipeline.d r5 = r10.p$
                kotlin.x r4 = r10.p$0
                r1 = r5
                r2 = 0
                java.lang.Object r6 = r1.getContext()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.application.b r6 = (io.ktor.application.b) r6     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.application.a r1 = r6.a()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r2 = r5
                r6 = 0
                java.lang.Object r7 = r2.getContext()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.application.b r7 = (io.ktor.application.b) r7     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r2 = r7
                r6 = r1
                r1 = 0
                kotlin.x r7 = kotlin.x.a     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r10.L$0 = r5     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r10.L$1 = r4     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r10.L$2 = r6     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r10.L$3 = r2     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r8 = 1
                r10.label = r8     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                java.lang.Object r7 = r6.e(r2, r7, r10)     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                if (r7 != r0) goto L_0x0110
                return r0
            L_0x0110:
                r9 = r2
                r2 = r1
                r1 = r9
            L_0x0113:
                r1 = r5
                r2 = 0
                java.lang.Object r6 = r1.getContext()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.application.b r6 = (io.ktor.application.b) r6     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.response.a r1 = r6.b()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.http.v r1 = r1.b()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                if (r1 != 0) goto L_0x0151
                r1 = r5
                r2 = 0
                java.lang.Object r6 = r1.getContext()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.application.b r6 = (io.ktor.application.b) r6     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.http.v$a r1 = io.ktor.http.v.c0     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.http.v r1 = r1.x()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r2 = 0
                io.ktor.response.a r7 = r6.b()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                io.ktor.response.d r7 = r7.a()     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r10.L$0 = r5     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r10.L$1 = r4     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r10.L$2 = r6     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r10.L$3 = r1     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                r8 = 2
                r10.label = r8     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                java.lang.Object r3 = r7.e(r6, r1, r10)     // Catch:{ ChannelIOException -> 0x01bf, all -> 0x0178 }
                if (r3 != r0) goto L_0x014f
                return r0
            L_0x014f:
                r1 = r5
                goto L_0x0152
            L_0x0151:
                r1 = r5
            L_0x0152:
                r2 = r1
                r3 = 0
                java.lang.Object r5 = r2.getContext()     // Catch:{ all -> 0x0173 }
                io.ktor.application.b r5 = (io.ktor.application.b) r5     // Catch:{ all -> 0x0173 }
                io.ktor.request.d r2 = r5.getRequest()     // Catch:{ all -> 0x0173 }
                io.ktor.utils.io.i r2 = r2.c()     // Catch:{ all -> 0x0173 }
                r10.L$0 = r1     // Catch:{ all -> 0x0173 }
                r10.L$1 = r4     // Catch:{ all -> 0x0173 }
                r3 = 3
                r10.label = r3     // Catch:{ all -> 0x0173 }
                java.lang.Object r2 = io.ktor.utils.io.k.d(r2, r10)     // Catch:{ all -> 0x0173 }
                if (r2 != r0) goto L_0x0171
                return r0
            L_0x0171:
                r0 = r4
            L_0x0172:
                goto L_0x0175
            L_0x0173:
                r0 = move-exception
                r0 = r4
            L_0x0175:
                goto L_0x0208
            L_0x0178:
                r1 = move-exception
                io.ktor.features.c$d r2 = io.ktor.features.c.d.a     // Catch:{ all -> 0x020b }
                r6 = 0
                io.ktor.server.engine.m$b$b r7 = new io.ktor.server.engine.m$b$b     // Catch:{ all -> 0x020b }
                r7.<init>(r3, r10, r5, r1)     // Catch:{ all -> 0x020b }
                r10.L$0 = r5     // Catch:{ all -> 0x020b }
                r10.L$1 = r4     // Catch:{ all -> 0x020b }
                r10.L$2 = r1     // Catch:{ all -> 0x020b }
                r10.L$3 = r2     // Catch:{ all -> 0x020b }
                r3 = 6
                r10.label = r3     // Catch:{ all -> 0x020b }
                java.lang.Object r3 = r2.a(r5, r7, r10)     // Catch:{ all -> 0x020b }
                if (r3 != r0) goto L_0x0193
                return r0
            L_0x0193:
                r3 = r1
                r1 = r4
                r4 = r2
                r2 = r6
            L_0x0197:
                r2 = r5
                r3 = 0
                java.lang.Object r4 = r2.getContext()     // Catch:{ all -> 0x01bb }
                io.ktor.application.b r4 = (io.ktor.application.b) r4     // Catch:{ all -> 0x01bb }
                io.ktor.request.d r2 = r4.getRequest()     // Catch:{ all -> 0x01bb }
                io.ktor.utils.io.i r2 = r2.c()     // Catch:{ all -> 0x01bb }
                r10.L$0 = r5     // Catch:{ all -> 0x01bb }
                r10.L$1 = r1     // Catch:{ all -> 0x01bb }
                r3 = 7
                r10.label = r3     // Catch:{ all -> 0x01bb }
                java.lang.Object r2 = io.ktor.utils.io.k.d(r2, r10)     // Catch:{ all -> 0x01bb }
                if (r2 != r0) goto L_0x01b8
                return r0
            L_0x01b8:
                r0 = r1
                r1 = r5
                goto L_0x0172
            L_0x01bb:
                r0 = move-exception
                r0 = r1
                r1 = r5
                goto L_0x0175
            L_0x01bf:
                r1 = move-exception
                io.ktor.features.c$d r2 = io.ktor.features.c.d.a     // Catch:{ all -> 0x020b }
                r6 = 0
                io.ktor.server.engine.m$b$a r7 = new io.ktor.server.engine.m$b$a     // Catch:{ all -> 0x020b }
                r7.<init>(r3, r10, r5, r1)     // Catch:{ all -> 0x020b }
                r10.L$0 = r5     // Catch:{ all -> 0x020b }
                r10.L$1 = r4     // Catch:{ all -> 0x020b }
                r10.L$2 = r1     // Catch:{ all -> 0x020b }
                r10.L$3 = r2     // Catch:{ all -> 0x020b }
                r3 = 4
                r10.label = r3     // Catch:{ all -> 0x020b }
                java.lang.Object r3 = r2.a(r5, r7, r10)     // Catch:{ all -> 0x020b }
                if (r3 != r0) goto L_0x01da
                return r0
            L_0x01da:
                r3 = r1
                r1 = r4
                r4 = r2
                r2 = r6
            L_0x01de:
                r2 = r5
                r3 = 0
                java.lang.Object r4 = r2.getContext()     // Catch:{ all -> 0x0203 }
                io.ktor.application.b r4 = (io.ktor.application.b) r4     // Catch:{ all -> 0x0203 }
                io.ktor.request.d r2 = r4.getRequest()     // Catch:{ all -> 0x0203 }
                io.ktor.utils.io.i r2 = r2.c()     // Catch:{ all -> 0x0203 }
                r10.L$0 = r5     // Catch:{ all -> 0x0203 }
                r10.L$1 = r1     // Catch:{ all -> 0x0203 }
                r3 = 5
                r10.label = r3     // Catch:{ all -> 0x0203 }
                java.lang.Object r2 = io.ktor.utils.io.k.d(r2, r10)     // Catch:{ all -> 0x0203 }
                if (r2 != r0) goto L_0x01ff
                return r0
            L_0x01ff:
                r0 = r1
                r1 = r5
                goto L_0x0172
            L_0x0203:
                r0 = move-exception
                r0 = r1
                r1 = r5
                goto L_0x0175
            L_0x0208:
                kotlin.x r2 = kotlin.x.a
                return r2
            L_0x020b:
                r1 = move-exception
                r2 = r1
                r1 = r5
            L_0x020e:
                r3 = r1
                r5 = 0
                java.lang.Object r6 = r3.getContext()     // Catch:{ all -> 0x0232 }
                io.ktor.application.b r6 = (io.ktor.application.b) r6     // Catch:{ all -> 0x0232 }
                io.ktor.request.d r3 = r6.getRequest()     // Catch:{ all -> 0x0232 }
                io.ktor.utils.io.i r3 = r3.c()     // Catch:{ all -> 0x0232 }
                r10.L$0 = r1     // Catch:{ all -> 0x0232 }
                r10.L$1 = r4     // Catch:{ all -> 0x0232 }
                r10.L$2 = r2     // Catch:{ all -> 0x0232 }
                r5 = 8
                r10.label = r5     // Catch:{ all -> 0x0232 }
                java.lang.Object r3 = io.ktor.utils.io.k.d(r3, r10)     // Catch:{ all -> 0x0232 }
                if (r3 != r0) goto L_0x0230
                return r0
            L_0x0230:
                r0 = r4
            L_0x0231:
                goto L_0x0234
            L_0x0232:
                r0 = move-exception
                r0 = r4
            L_0x0234:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.m.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* compiled from: DefaultEnginePipeline.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<io.ktor.util.pipeline.d<x, io.ktor.application.b>, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ ChannelIOException $error$inlined;
            final /* synthetic */ io.ktor.util.pipeline.d $this_intercept$inlined;
            int label;
            private io.ktor.util.pipeline.d p$;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(kotlin.coroutines.d dVar, b bVar, io.ktor.util.pipeline.d dVar2, ChannelIOException channelIOException) {
                super(2, dVar);
                this.this$0 = bVar;
                this.$this_intercept$inlined = dVar2;
                this.$error$inlined = channelIOException;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                k.f(dVar, "completion");
                a aVar = new a(dVar, this.this$0, this.$this_intercept$inlined, this.$error$inlined);
                io.ktor.util.pipeline.d dVar2 = (io.ktor.util.pipeline.d) obj;
                aVar.p$ = (io.ktor.util.pipeline.d) obj;
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
                        io.ktor.util.pipeline.d $this$withMDCBlock = this.p$;
                        m.e(((io.ktor.application.b) $this$withMDCBlock.getContext()).a().F(), (io.ktor.application.b) $this$withMDCBlock.getContext(), this.$error$inlined);
                        return x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* renamed from: io.ktor.server.engine.m$b$b  reason: collision with other inner class name */
        /* compiled from: DefaultEnginePipeline.kt */
        public static final class C0272b extends kotlin.coroutines.jvm.internal.l implements p<io.ktor.util.pipeline.d<x, io.ktor.application.b>, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ Throwable $error$inlined;
            final /* synthetic */ io.ktor.util.pipeline.d $this_intercept$inlined;
            Object L$0;
            int label;
            private io.ktor.util.pipeline.d p$;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0272b(kotlin.coroutines.d dVar, b bVar, io.ktor.util.pipeline.d dVar2, Throwable th) {
                super(2, dVar);
                this.this$0 = bVar;
                this.$this_intercept$inlined = dVar2;
                this.$error$inlined = th;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                k.f(dVar, "completion");
                C0272b bVar = new C0272b(dVar, this.this$0, this.$this_intercept$inlined, this.$error$inlined);
                io.ktor.util.pipeline.d dVar2 = (io.ktor.util.pipeline.d) obj;
                bVar.p$ = (io.ktor.util.pipeline.d) obj;
                return bVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((C0272b) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        io.ktor.util.pipeline.d $this$withMDCBlock = this.p$;
                        m.e(((io.ktor.application.b) $this$withMDCBlock.getContext()).a().F(), (io.ktor.application.b) $this$withMDCBlock.getContext(), this.$error$inlined);
                        Throwable th = this.$error$inlined;
                        this.L$0 = $this$withMDCBlock;
                        this.label = 1;
                        if (m.d($this$withMDCBlock, th, this) != d) {
                            io.ktor.util.pipeline.d dVar = $this$withMDCBlock;
                            break;
                        } else {
                            return d;
                        }
                    case 1:
                        io.ktor.util.pipeline.d $this$withMDCBlock2 = (io.ktor.util.pipeline.d) this.L$0;
                        kotlin.p.b($result);
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                return x.a;
            }
        }
    }

    @Nullable
    public static final io.ktor.http.v c(@NotNull Throwable cause) {
        k.f(cause, "cause");
        if (cause instanceof BadRequestException) {
            return io.ktor.http.v.c0.c();
        }
        if (cause instanceof NotFoundException) {
            return io.ktor.http.v.c0.x();
        }
        if (cause instanceof UnsupportedMediaTypeException) {
            return io.ktor.http.v.c0.V();
        }
        if (!(cause instanceof TimeoutException) && !(cause instanceof TimeoutCancellationException)) {
            return null;
        }
        return io.ktor.http.v.c0.k();
    }

    @Nullable
    static final /* synthetic */ Object d(@NotNull io.ktor.util.pipeline.d<x, io.ktor.application.b> $this$handleFailure, @NotNull Throwable cause, @NotNull kotlin.coroutines.d<? super x> $completion) {
        io.ktor.http.v c2 = c(cause);
        if (c2 == null) {
            c2 = io.ktor.http.v.c0.n();
        }
        Object f = f($this$handleFailure, c2, $completion);
        if (f == kotlin.coroutines.intrinsics.c.d()) {
            return f;
        }
        return x.a;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object f(@org.jetbrains.annotations.NotNull io.ktor.util.pipeline.d<kotlin.x, io.ktor.application.b> r7, @org.jetbrains.annotations.NotNull io.ktor.http.v r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r9) {
        /*
            boolean r0 = r9 instanceof io.ktor.server.engine.m.c
            if (r0 == 0) goto L_0x0013
            r0 = r9
            io.ktor.server.engine.m$c r0 = (io.ktor.server.engine.m.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.server.engine.m$c r0 = new io.ktor.server.engine.m$c
            r0.<init>(r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0040;
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
            r3 = 0
            java.lang.Object r4 = r0.L$2
            r2 = r4
            io.ktor.application.b r2 = (io.ktor.application.b) r2
            java.lang.Object r4 = r0.L$1
            r8 = r4
            io.ktor.http.v r8 = (io.ktor.http.v) r8
            java.lang.Object r4 = r0.L$0
            r7 = r4
            io.ktor.util.pipeline.d r7 = (io.ktor.util.pipeline.d) r7
            kotlin.p.b(r1)     // Catch:{ ResponseAlreadySentException -> 0x007b }
            goto L_0x007a
        L_0x0040:
            kotlin.p.b(r1)
            r3 = r7
            r4 = 0
            java.lang.Object r5 = r3.getContext()     // Catch:{ ResponseAlreadySentException -> 0x007b }
            io.ktor.application.b r5 = (io.ktor.application.b) r5     // Catch:{ ResponseAlreadySentException -> 0x007b }
            io.ktor.response.a r3 = r5.b()     // Catch:{ ResponseAlreadySentException -> 0x007b }
            io.ktor.http.v r3 = r3.b()     // Catch:{ ResponseAlreadySentException -> 0x007b }
            if (r3 != 0) goto L_0x007c
            r3 = r7
            r4 = 0
            java.lang.Object r5 = r3.getContext()     // Catch:{ ResponseAlreadySentException -> 0x007b }
            io.ktor.application.b r5 = (io.ktor.application.b) r5     // Catch:{ ResponseAlreadySentException -> 0x007b }
            r3 = r5
            r4 = 0
            io.ktor.response.a r5 = r3.b()     // Catch:{ ResponseAlreadySentException -> 0x007b }
            io.ktor.response.d r5 = r5.a()     // Catch:{ ResponseAlreadySentException -> 0x007b }
            r0.L$0 = r7     // Catch:{ ResponseAlreadySentException -> 0x007b }
            r0.L$1 = r8     // Catch:{ ResponseAlreadySentException -> 0x007b }
            r0.L$2 = r3     // Catch:{ ResponseAlreadySentException -> 0x007b }
            r6 = 1
            r0.label = r6     // Catch:{ ResponseAlreadySentException -> 0x007b }
            java.lang.Object r5 = r5.e(r3, r8, r0)     // Catch:{ ResponseAlreadySentException -> 0x007b }
            if (r5 != r2) goto L_0x0078
            return r2
        L_0x0078:
            r2 = r3
            r3 = r4
        L_0x007a:
            goto L_0x007c
        L_0x007b:
            r2 = move-exception
        L_0x007c:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.engine.m.f(io.ktor.util.pipeline.d, io.ktor.http.v, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public static final void e(@NotNull io.ktor.application.d $this$logFailure, io.ktor.application.b call, Throwable cause) {
        String logString;
        try {
            Object status = call.b().b();
            if (status == null) {
                status = "Unhandled";
            }
            try {
                logString = e.c(call.getRequest());
            } catch (Throwable cause2) {
                logString = "(request error: " + cause2 + ')';
            }
            if (cause instanceof CancellationException) {
                $this$logFailure.f().info(status + ": " + logString + ", cancelled");
            } else if (cause instanceof ClosedChannelException) {
                $this$logFailure.f().info(status + ": " + logString + ", channel closed");
            } else if (cause instanceof ChannelIOException) {
                $this$logFailure.f().info(status + ": " + logString + ", channel failed");
            } else if (cause instanceof BadRequestException) {
                $this$logFailure.f().debug(status + ": " + logString, cause);
            } else if (cause instanceof NotFoundException) {
                $this$logFailure.f().debug(status + ": " + logString, cause);
            } else if (cause instanceof UnsupportedMediaTypeException) {
                $this$logFailure.f().debug(status + ": " + logString, cause);
            } else {
                $this$logFailure.f().error(status + ": " + logString, cause);
            }
        } catch (OutOfMemoryError e) {
            try {
                t.a($this$logFailure.f(), cause);
            } catch (OutOfMemoryError e2) {
                System.err.print("OutOfMemoryError: ");
                System.err.println(cause.getMessage());
            }
        }
    }
}
