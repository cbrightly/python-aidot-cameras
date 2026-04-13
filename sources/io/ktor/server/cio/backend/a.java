package io.ktor.server.cio.backend;

import io.ktor.http.cio.m;
import io.ktor.network.sockets.n;
import io.ktor.server.cio.f;
import io.ktor.server.cio.g;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.h;
import kotlinx.coroutines.j;
import kotlinx.coroutines.n0;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.w;
import kotlinx.coroutines.y;
import kotlinx.coroutines.z;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpServer.kt */
public final class a {
    @NotNull
    public static final f a(@NotNull o0 o0Var, @NotNull g gVar, @NotNull q<? super d, ? super m, ? super kotlin.coroutines.d<? super x>, ? extends Object> qVar) {
        o0 o0Var2 = o0Var;
        k.f(o0Var2, "$this$httpServer");
        g gVar2 = gVar;
        k.f(gVar2, "settings");
        q<? super d, ? super m, ? super kotlin.coroutines.d<? super x>, ? extends Object> qVar2 = qVar;
        k.f(qVar2, "handler");
        w b2 = y.b((z1) null, 1, (Object) null);
        z b3 = e2.b((z1) null, 1, (Object) null);
        z1 c2 = h.c(o0Var2, new n0("server-root-" + gVar.c()), q0.UNDISPATCHED, new e(b3, (kotlin.coroutines.d) null));
        io.ktor.network.selector.a aVar = new io.ktor.network.selector.a(o0Var.getCoroutineContext());
        io.ktor.http.cio.internals.g gVar3 = new io.ktor.http.cio.internals.g(gVar.a() * 1000, (kotlin.jvm.functions.a) null, 2, (DefaultConstructorMarker) null);
        org.slf4j.b i = org.slf4j.c.i(f.class);
        z1 d2 = j.d(o0Var2, c2.plus(new n0("accept-" + gVar.c())), (q0) null, new d(aVar, gVar2, b2, c2, i, gVar3, qVar2, (kotlin.coroutines.d) null), 2, (Object) null);
        d2.t(new C0262a(b2, b3, gVar3));
        z1 z1Var = c2;
        z1.a.d(c2, true, false, new b(gVar3), 2, (Object) null);
        z1Var.t(new c(aVar));
        return new f(z1Var, d2, b2);
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.backend.HttpServerKt$httpServer$serverJob$1", f = "HttpServer.kt", l = {33}, m = "invokeSuspend")
    /* compiled from: HttpServer.kt */
    public static final class e extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ z $serverLatch;
        Object L$0;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(z zVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$serverLatch = zVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            e eVar = new e(this.$serverLatch, dVar);
            o0 o0Var = (o0) obj;
            eVar.p$ = (o0) obj;
            return eVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((e) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o0 $this$launch = this.p$;
                    z zVar = this.$serverLatch;
                    this.L$0 = $this$launch;
                    this.label = 1;
                    if (zVar.J(this) != d) {
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
            return x.a;
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.server.cio.backend.HttpServerKt$httpServer$acceptJob$1", f = "HttpServer.kt", l = {56, 78, 78}, m = "invokeSuspend")
    /* compiled from: HttpServer.kt */
    public static final class d extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ q $handler;
        final /* synthetic */ org.slf4j.b $logger;
        final /* synthetic */ io.ktor.network.selector.a $selector;
        final /* synthetic */ z1 $serverJob;
        final /* synthetic */ g $settings;
        final /* synthetic */ w $socket;
        final /* synthetic */ io.ktor.http.cio.internals.g $timeout;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        private o0 p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(io.ktor.network.selector.a aVar, g gVar, w wVar, z1 z1Var, org.slf4j.b bVar, io.ktor.http.cio.internals.g gVar2, q qVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$selector = aVar;
            this.$settings = gVar;
            this.$socket = wVar;
            this.$serverJob = z1Var;
            this.$logger = bVar;
            this.$timeout = gVar2;
            this.$handler = qVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            d dVar2 = new d(this.$selector, this.$settings, this.$socket, this.$serverJob, this.$logger, this.$timeout, this.$handler, dVar);
            o0 o0Var = (o0) obj;
            dVar2.p$ = (o0) obj;
            return dVar2;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((d) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* Debug info: failed to restart local var, previous not found, register: 17 */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: java.io.Closeable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v1, resolved type: java.io.Closeable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: java.io.Closeable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v3, resolved type: java.io.Closeable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.io.Closeable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: io.ktor.network.sockets.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v3, resolved type: io.ktor.network.sockets.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v10, resolved type: io.ktor.network.sockets.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v19, resolved type: io.ktor.network.sockets.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: io.ktor.network.sockets.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: java.io.Closeable} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v25, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v12, resolved type: kotlinx.coroutines.o0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v14, resolved type: io.ktor.network.sockets.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v15, resolved type: io.ktor.network.sockets.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v16, resolved type: io.ktor.network.sockets.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: kotlinx.coroutines.o0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v21, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v12, resolved type: kotlinx.coroutines.o0} */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            r13.L$0 = r8;
            r13.L$1 = r12;
            r13.L$2 = r11;
            r13.L$3 = r9;
            r13.L$4 = r10;
            r13.label = r4;
            r0 = r9.Q0(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0128, code lost:
            if (r0 != r6) goto L_0x012b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x012a, code lost:
            return r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x012b, code lost:
            r0 = (io.ktor.network.sockets.n) r0;
            io.ktor.server.cio.backend.c.c(r10, new io.ktor.server.cio.backend.b(io.ktor.network.sockets.s.b(r0), io.ktor.network.sockets.s.d(r0, r3, r4, (java.lang.Object) null), r0.w(), r0.getLocalAddress()), r13.$timeout, r13.$handler).t(new io.ktor.server.cio.backend.a.d.C0263a(r0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0156, code lost:
            r1 = r17;
            r3 = false;
            r4 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x015b, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x015c, code lost:
            r3 = r8;
            r8 = r12;
            r16 = r7;
            r7 = r0;
            r0 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0165, code lost:
            r3 = r8;
            r8 = r12;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x0185 A[Catch:{ all -> 0x0199 }, RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:42:0x0186 A[Catch:{ all -> 0x0199 }] */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x01ba A[Catch:{ all -> 0x0199 }, RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x01bb A[Catch:{ all -> 0x0199 }] */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r18) {
            /*
                r17 = this;
                r1 = r17
                java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
                int r0 = r1.label
                r3 = 0
                r4 = 1
                r5 = 0
                switch(r0) {
                    case 0: goto L_0x00b6;
                    case 1: goto L_0x0069;
                    case 2: goto L_0x003e;
                    case 3: goto L_0x0016;
                    default: goto L_0x000e;
                }
            L_0x000e:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0016:
                r0 = r3
                r2 = r5
                r3 = r5
                r6 = r5
                java.lang.Object r7 = r1.L$5
                java.lang.Throwable r7 = (java.lang.Throwable) r7
                java.lang.Object r8 = r1.L$4
                r2 = r8
                kotlinx.coroutines.o0 r2 = (kotlinx.coroutines.o0) r2
                java.lang.Object r8 = r1.L$3
                r6 = r8
                io.ktor.network.sockets.l r6 = (io.ktor.network.sockets.l) r6
                java.lang.Object r8 = r1.L$2
                java.lang.Throwable r8 = (java.lang.Throwable) r8
                java.lang.Object r8 = r1.L$1
                java.io.Closeable r8 = (java.io.Closeable) r8
                java.lang.Object r9 = r1.L$0
                r3 = r9
                kotlinx.coroutines.o0 r3 = (kotlinx.coroutines.o0) r3
                kotlin.p.b(r18)     // Catch:{ all -> 0x0062 }
                r13 = r1
                r10 = r2
                r2 = r18
                goto L_0x01bc
            L_0x003e:
                r0 = r3
                r2 = r5
                r3 = r5
                r6 = r5
                java.lang.Object r7 = r1.L$4
                r2 = r7
                kotlinx.coroutines.o0 r2 = (kotlinx.coroutines.o0) r2
                java.lang.Object r7 = r1.L$3
                r6 = r7
                io.ktor.network.sockets.l r6 = (io.ktor.network.sockets.l) r6
                java.lang.Object r7 = r1.L$2
                java.lang.Throwable r7 = (java.lang.Throwable) r7
                java.lang.Object r8 = r1.L$1
                java.io.Closeable r8 = (java.io.Closeable) r8
                java.lang.Object r9 = r1.L$0
                r3 = r9
                kotlinx.coroutines.o0 r3 = (kotlinx.coroutines.o0) r3
                kotlin.p.b(r18)     // Catch:{ all -> 0x0062 }
                r13 = r1
                r10 = r2
                r2 = r18
                goto L_0x0189
            L_0x0062:
                r0 = move-exception
                r2 = r18
                r13 = r1
                r1 = r0
                goto L_0x01cc
            L_0x0069:
                r6 = r3
                r0 = r5
                r7 = r5
                r8 = r5
                java.lang.Object r9 = r1.L$4
                kotlinx.coroutines.o0 r9 = (kotlinx.coroutines.o0) r9
                java.lang.Object r0 = r1.L$3
                r8 = r0
                io.ktor.network.sockets.l r8 = (io.ktor.network.sockets.l) r8
                java.lang.Object r0 = r1.L$2
                r10 = r0
                java.lang.Throwable r10 = (java.lang.Throwable) r10
                java.lang.Object r0 = r1.L$1
                r11 = r0
                java.io.Closeable r11 = (java.io.Closeable) r11
                java.lang.Object r0 = r1.L$0
                r7 = r0
                kotlinx.coroutines.o0 r7 = (kotlinx.coroutines.o0) r7
                kotlin.p.b(r18)     // Catch:{ ClosedChannelException -> 0x00a6, all -> 0x0095 }
                r0 = r18
                r13 = r1
                r12 = r11
                r11 = r10
                r10 = r9
                r9 = r8
                r8 = r7
                r7 = r6
                r6 = r2
                r2 = r0
                goto L_0x012b
            L_0x0095:
                r0 = move-exception
                r13 = r1
                r3 = r7
                r7 = r0
                r0 = r6
                r6 = r2
                r2 = r18
                r16 = r9
                r9 = r8
                r8 = r11
                r11 = r10
                r10 = r16
                goto L_0x01a2
            L_0x00a6:
                r0 = move-exception
                r13 = r1
                r3 = r7
                r7 = r6
                r6 = r2
                r2 = r18
                r16 = r9
                r9 = r8
                r8 = r11
                r11 = r10
                r10 = r16
                goto L_0x0167
            L_0x00b6:
                kotlin.p.b(r18)
                kotlinx.coroutines.o0 r6 = r1.p$
                io.ktor.network.selector.a r0 = r1.$selector
                io.ktor.network.sockets.o r0 = io.ktor.network.sockets.g.a(r0)
                io.ktor.network.sockets.t r7 = r0.b()
                io.ktor.server.cio.g r0 = r1.$settings
                java.lang.String r8 = r0.b()
                io.ktor.server.cio.g r0 = r1.$settings
                int r9 = r0.c()
                r10 = 0
                r11 = 4
                r12 = 0
                io.ktor.network.sockets.l r8 = io.ktor.network.sockets.t.c(r7, r8, r9, r10, r11, r12)
                r0 = r8
                r7 = 0
                kotlinx.coroutines.w r9 = r1.$socket     // Catch:{ all -> 0x01c5 }
                r9.x(r0)     // Catch:{ all -> 0x01c5 }
                kotlin.coroutines.g r9 = r6.getCoroutineContext()     // Catch:{ all -> 0x01c5 }
                kotlinx.coroutines.z1 r10 = r1.$serverJob     // Catch:{ all -> 0x01c5 }
                kotlinx.coroutines.z r10 = kotlinx.coroutines.v2.a(r10)     // Catch:{ all -> 0x01c5 }
                kotlin.coroutines.g r9 = r9.plus(r10)     // Catch:{ all -> 0x01c5 }
                io.ktor.server.engine.o r10 = new io.ktor.server.engine.o     // Catch:{ all -> 0x01c5 }
                org.slf4j.b r11 = r1.$logger     // Catch:{ all -> 0x01c5 }
                java.lang.String r12 = "logger"
                kotlin.jvm.internal.k.b(r11, r12)     // Catch:{ all -> 0x01c5 }
                r10.<init>((org.slf4j.b) r11)     // Catch:{ all -> 0x01c5 }
                kotlin.coroutines.g r9 = r9.plus(r10)     // Catch:{ all -> 0x01c5 }
                kotlinx.coroutines.n0 r10 = new kotlinx.coroutines.n0     // Catch:{ all -> 0x01c5 }
                java.lang.String r11 = "request"
                r10.<init>(r11)     // Catch:{ all -> 0x01c5 }
                kotlin.coroutines.g r9 = r9.plus(r10)     // Catch:{ all -> 0x01c5 }
                kotlinx.coroutines.o0 r9 = kotlinx.coroutines.p0.a(r9)     // Catch:{ all -> 0x01c5 }
                r13 = r1
                r11 = r5
                r12 = r8
                r10 = r9
                r9 = r0
                r8 = r6
                r6 = r2
                r2 = r18
            L_0x0117:
                r13.L$0 = r8     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r13.L$1 = r12     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r13.L$2 = r11     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r13.L$3 = r9     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r13.L$4 = r10     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r13.label = r4     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                java.lang.Object r0 = r9.Q0(r13)     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                if (r0 != r6) goto L_0x012b
                return r6
            L_0x012b:
                io.ktor.network.sockets.n r0 = (io.ktor.network.sockets.n) r0     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                io.ktor.server.cio.backend.b r14 = new io.ktor.server.cio.backend.b     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                io.ktor.utils.io.i r15 = io.ktor.network.sockets.s.b(r0)     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                io.ktor.utils.io.l r1 = io.ktor.network.sockets.s.d(r0, r3, r4, r5)     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                java.net.SocketAddress r3 = r0.w()     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                java.net.SocketAddress r4 = r0.getLocalAddress()     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r14.<init>(r15, r1, r3, r4)     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r1 = r14
                io.ktor.http.cio.internals.g r3 = r13.$timeout     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                kotlin.jvm.functions.q r4 = r13.$handler     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                kotlinx.coroutines.z1 r3 = io.ktor.server.cio.backend.c.c(r10, r1, r3, r4)     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                io.ktor.server.cio.backend.a$d$a r4 = new io.ktor.server.cio.backend.a$d$a     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r4.<init>(r0)     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r3.t(r4)     // Catch:{ ClosedChannelException -> 0x0164, all -> 0x015b }
                r1 = r17
                r3 = 0
                r4 = 1
                goto L_0x0117
            L_0x015b:
                r0 = move-exception
                r3 = r8
                r8 = r12
                r16 = r7
                r7 = r0
                r0 = r16
                goto L_0x01a2
            L_0x0164:
                r0 = move-exception
                r3 = r8
                r8 = r12
            L_0x0167:
                kotlin.coroutines.g r1 = r3.getCoroutineContext()     // Catch:{ all -> 0x019c }
                r4 = 1
                kotlinx.coroutines.e2.d(r1, r5, r4, r5)     // Catch:{ all -> 0x019c }
                r9.close()     // Catch:{ all -> 0x0199 }
                r13.L$0 = r3     // Catch:{ all -> 0x0199 }
                r13.L$1 = r8     // Catch:{ all -> 0x0199 }
                r13.L$2 = r11     // Catch:{ all -> 0x0199 }
                r13.L$3 = r9     // Catch:{ all -> 0x0199 }
                r13.L$4 = r10     // Catch:{ all -> 0x0199 }
                r0 = 2
                r13.label = r0     // Catch:{ all -> 0x0199 }
                java.lang.Object r0 = io.ktor.network.sockets.s.a(r9, r13)     // Catch:{ all -> 0x0199 }
                if (r0 != r6) goto L_0x0186
                return r6
            L_0x0186:
                r0 = r7
                r6 = r9
                r7 = r11
            L_0x0189:
                kotlin.coroutines.g r1 = r10.getCoroutineContext()     // Catch:{ all -> 0x0199 }
                r4 = 1
                kotlinx.coroutines.e2.d(r1, r5, r4, r5)     // Catch:{ all -> 0x0199 }
                kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x0199 }
                kotlin.io.b.a(r8, r7)
                return r0
            L_0x0199:
                r0 = move-exception
                r1 = r0
                goto L_0x01cc
            L_0x019c:
                r0 = move-exception
                r16 = r7
                r7 = r0
                r0 = r16
            L_0x01a2:
                r9.close()     // Catch:{ all -> 0x0199 }
                r13.L$0 = r3     // Catch:{ all -> 0x0199 }
                r13.L$1 = r8     // Catch:{ all -> 0x0199 }
                r13.L$2 = r11     // Catch:{ all -> 0x0199 }
                r13.L$3 = r9     // Catch:{ all -> 0x0199 }
                r13.L$4 = r10     // Catch:{ all -> 0x0199 }
                r13.L$5 = r7     // Catch:{ all -> 0x0199 }
                r1 = 3
                r13.label = r1     // Catch:{ all -> 0x0199 }
                java.lang.Object r1 = io.ktor.network.sockets.s.a(r9, r13)     // Catch:{ all -> 0x0199 }
                if (r1 != r6) goto L_0x01bb
                return r6
            L_0x01bb:
                r6 = r9
            L_0x01bc:
                kotlin.coroutines.g r1 = r10.getCoroutineContext()     // Catch:{ all -> 0x0199 }
                r4 = 1
                kotlinx.coroutines.e2.d(r1, r5, r4, r5)     // Catch:{ all -> 0x0199 }
                throw r7     // Catch:{ all -> 0x0199 }
            L_0x01c5:
                r0 = move-exception
                r13 = r17
                r2 = r18
                r1 = r0
                r3 = r6
            L_0x01cc:
                throw r1     // Catch:{ all -> 0x01cd }
            L_0x01cd:
                r0 = move-exception
                r4 = r0
                kotlin.io.b.a(r8, r1)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.server.cio.backend.a.d.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* renamed from: io.ktor.server.cio.backend.a$d$a  reason: collision with other inner class name */
        /* compiled from: HttpServer.kt */
        public static final class C0263a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
            final /* synthetic */ n $client;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0263a(n nVar) {
                super(1);
                this.$client = nVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((Throwable) obj);
                return x.a;
            }

            public final void invoke(@Nullable Throwable it) {
                this.$client.close();
            }
        }
    }

    /* renamed from: io.ktor.server.cio.backend.a$a  reason: collision with other inner class name */
    /* compiled from: HttpServer.kt */
    public static final class C0262a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ z $serverLatch;
        final /* synthetic */ w $socket;
        final /* synthetic */ io.ktor.http.cio.internals.g $timeout;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0262a(w wVar, z zVar, io.ktor.http.cio.internals.g gVar) {
            super(1);
            this.$socket = wVar;
            this.$serverLatch = zVar;
            this.$timeout = gVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable cause) {
            if (cause != null) {
                this.$socket.a(cause);
            }
            this.$serverLatch.complete();
            this.$timeout.b();
        }
    }

    /* compiled from: HttpServer.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ io.ktor.http.cio.internals.g $timeout;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(io.ktor.http.cio.internals.g gVar) {
            super(1);
            this.$timeout = gVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            this.$timeout.a();
        }
    }

    /* compiled from: HttpServer.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
        final /* synthetic */ io.ktor.network.selector.a $selector;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(io.ktor.network.selector.a aVar) {
            super(1);
            this.$selector = aVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return x.a;
        }

        public final void invoke(@Nullable Throwable it) {
            this.$selector.close();
        }
    }
}
