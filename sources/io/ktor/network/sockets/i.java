package io.ktor.network.sockets;

import io.ktor.network.selector.k;
import io.ktor.network.selector.m;
import io.ktor.network.sockets.q;
import io.ktor.utils.io.t;
import io.ktor.utils.io.v;
import io.ktor.utils.io.w;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.n0;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CIOWriter.kt */
public final class i {

    @f(c = "io.ktor.network.sockets.CIOWriterKt$attachForWritingDirectImpl$1", f = "CIOWriter.kt", l = {74}, m = "invokeSuspend")
    /* compiled from: CIOWriter.kt */
    public static final class a extends l implements p<w, d<? super x>, Object> {
        final /* synthetic */ io.ktor.utils.io.f $channel;
        final /* synthetic */ WritableByteChannel $nioChannel;
        final /* synthetic */ k $selectable;
        final /* synthetic */ m $selector;
        final /* synthetic */ q.e $socketOptions;
        Object L$0;
        int label;
        private w p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(k kVar, io.ktor.utils.io.f fVar, q.e eVar, WritableByteChannel writableByteChannel, m mVar, d dVar) {
            super(2, dVar);
            this.$selectable = kVar;
            this.$channel = fVar;
            this.$socketOptions = eVar;
            this.$nioChannel = writableByteChannel;
            this.$selector = mVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            a aVar = new a(this.$selectable, this.$channel, this.$socketOptions, this.$nioChannel, this.$selector, dVar);
            w wVar = (w) obj;
            aVar.p$ = (w) obj;
            return aVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((a) create(obj, (d) obj2)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0040, code lost:
            r7.$selectable.Z(io.ktor.network.selector.j.WRITE, false);
            r1 = r7.$nioChannel;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x004b, code lost:
            if ((r1 instanceof java.nio.channels.SocketChannel) == false) goto L_0x0059;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
            ((java.nio.channels.SocketChannel) r1).socket().shutdownOutput();
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r7.label
                r2 = 0
                r3 = 0
                switch(r1) {
                    case 0: goto L_0x001f;
                    case 1: goto L_0x0013;
                    default: goto L_0x000b;
                }
            L_0x000b:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0013:
                r0 = r2
                java.lang.Object r1 = r7.L$0
                r0 = r1
                io.ktor.utils.io.w r0 = (io.ktor.utils.io.w) r0
                kotlin.p.b(r8)     // Catch:{ all -> 0x001d }
                goto L_0x0040
            L_0x001d:
                r1 = move-exception
                goto L_0x0062
            L_0x001f:
                kotlin.p.b(r8)
                io.ktor.utils.io.w r1 = r7.p$
                io.ktor.network.selector.k r4 = r7.$selectable
                io.ktor.network.selector.j r5 = io.ktor.network.selector.j.WRITE
                r4.Z(r5, r3)
                io.ktor.utils.io.f r4 = r7.$channel     // Catch:{ all -> 0x005e }
                io.ktor.network.sockets.i$a$a r5 = new io.ktor.network.sockets.i$a$a     // Catch:{ all -> 0x005e }
                r5.<init>(r7, r1, r2)     // Catch:{ all -> 0x005e }
                r7.L$0 = r1     // Catch:{ all -> 0x005e }
                r2 = 1
                r7.label = r2     // Catch:{ all -> 0x005e }
                java.lang.Object r2 = r4.l(r5, r7)     // Catch:{ all -> 0x005e }
                if (r2 != r0) goto L_0x003f
                return r0
            L_0x003f:
                r0 = r1
            L_0x0040:
                io.ktor.network.selector.k r1 = r7.$selectable
                io.ktor.network.selector.j r2 = io.ktor.network.selector.j.WRITE
                r1.Z(r2, r3)
                java.nio.channels.WritableByteChannel r1 = r7.$nioChannel
                boolean r2 = r1 instanceof java.nio.channels.SocketChannel
                if (r2 == 0) goto L_0x0059
                java.nio.channels.SocketChannel r1 = (java.nio.channels.SocketChannel) r1     // Catch:{ ClosedChannelException -> 0x0058 }
                java.net.Socket r1 = r1.socket()     // Catch:{ ClosedChannelException -> 0x0058 }
                r1.shutdownOutput()     // Catch:{ ClosedChannelException -> 0x0058 }
                goto L_0x0059
            L_0x0058:
                r1 = move-exception
            L_0x0059:
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x005e:
                r0 = move-exception
                r6 = r1
                r1 = r0
                r0 = r6
            L_0x0062:
                io.ktor.network.selector.k r2 = r7.$selectable
                io.ktor.network.selector.j r4 = io.ktor.network.selector.j.WRITE
                r2.Z(r4, r3)
                java.nio.channels.WritableByteChannel r2 = r7.$nioChannel
                boolean r3 = r2 instanceof java.nio.channels.SocketChannel
                if (r3 == 0) goto L_0x007b
                java.nio.channels.SocketChannel r2 = (java.nio.channels.SocketChannel) r2     // Catch:{ ClosedChannelException -> 0x007a }
                java.net.Socket r2 = r2.socket()     // Catch:{ ClosedChannelException -> 0x007a }
                r2.shutdownOutput()     // Catch:{ ClosedChannelException -> 0x007a }
                goto L_0x007b
            L_0x007a:
                r2 = move-exception
            L_0x007b:
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.i.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @f(c = "io.ktor.network.sockets.CIOWriterKt$attachForWritingDirectImpl$1$1", f = "CIOWriter.kt", l = {79, 86}, m = "invokeSuspend")
        /* renamed from: io.ktor.network.sockets.i$a$a  reason: collision with other inner class name */
        /* compiled from: CIOWriter.kt */
        public static final class C0257a extends l implements p<t, d<? super x>, Object> {
            final /* synthetic */ w $this_reader;
            Object L$0;
            Object L$1;
            Object L$2;
            int label;
            private t p$;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0257a(a aVar, w wVar, d dVar) {
                super(2, dVar);
                this.this$0 = aVar;
                this.$this_reader = wVar;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                kotlin.jvm.internal.k.f(dVar, "completion");
                C0257a aVar = new C0257a(this.this$0, this.$this_reader, dVar);
                t tVar = (t) obj;
                aVar.p$ = (t) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((C0257a) create(obj, (d) obj2)).invokeSuspend(x.a);
            }

            /* Debug info: failed to restart local var, previous not found, register: 13 */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: io.ktor.utils.io.t} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v11, resolved type: kotlin.jvm.internal.x} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v16, resolved type: java.nio.ByteBuffer} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: io.ktor.utils.io.t} */
            /* JADX WARNING: Code restructure failed: missing block: B:10:0x005b, code lost:
                if (r5 != r0) goto L_0x005e;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x005d, code lost:
                return r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:12:0x005e, code lost:
                r12 = r0;
                r0 = r14;
                r14 = r5;
                r5 = r4;
                r4 = r1;
                r1 = r12;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x006a, code lost:
                if (((java.lang.Boolean) r14).booleanValue() != false) goto L_0x006f;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:16:0x006e, code lost:
                return kotlin.x.a;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:17:0x006f, code lost:
                r14 = r0;
                r0 = r1;
                r1 = r4;
                r4 = r5;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:18:0x0074, code lost:
                r5 = r4;
                r4 = r1;
                r1 = r6;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:20:0x007b, code lost:
                if (r1.hasRemaining() == false) goto L_0x00bb;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:21:0x007d, code lost:
                r6 = new kotlin.jvm.internal.x();
                r6.element = 0;
                r7 = r4.$this_reader;
                r8 = r4.this$0.$socketOptions;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:22:0x008a, code lost:
                if (r8 == null) goto L_0x009b;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:23:0x008c, code lost:
                r8 = kotlin.coroutines.jvm.internal.b.d(r8.l());
             */
            /* JADX WARNING: Code restructure failed: missing block: B:24:0x0094, code lost:
                if (r8 == null) goto L_0x009b;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:25:0x0096, code lost:
                r8 = r8.longValue();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:26:0x009b, code lost:
                r8 = com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a0, code lost:
                r10 = new io.ktor.network.sockets.i.a.C0257a.C0258a(r4, r6, r1, (kotlin.coroutines.d) null);
                r4.L$0 = r5;
                r4.L$1 = r1;
                r4.L$2 = r6;
                r4.label = 2;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b2, code lost:
                if (io.ktor.network.util.c.a(r7, r8, r10, r4) != r0) goto L_0x00b5;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b4, code lost:
                return r0;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b5, code lost:
                r5.w(r6.element);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:31:0x00bb, code lost:
                r1 = r4;
                r4 = r5;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:7:0x0049, code lost:
                r6 = r4.a(0, 1);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:8:0x004f, code lost:
                if (r6 != null) goto L_0x0074;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x0051, code lost:
                r1.L$0 = r4;
                r1.L$1 = r6;
                r1.label = 1;
                r5 = r4.k(1, r1);
             */
            /* JADX WARNING: Multi-variable type inference failed */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r14) {
                /*
                    r13 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r13.label
                    r2 = 0
                    r3 = 0
                    switch(r1) {
                        case 0: goto L_0x0042;
                        case 1: goto L_0x002d;
                        case 2: goto L_0x0013;
                        default: goto L_0x000b;
                    }
                L_0x000b:
                    java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                    java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                    r0.<init>(r1)
                    throw r0
                L_0x0013:
                    r1 = r3
                    r4 = r3
                    r5 = r3
                    java.lang.Object r6 = r13.L$2
                    r5 = r6
                    kotlin.jvm.internal.x r5 = (kotlin.jvm.internal.x) r5
                    java.lang.Object r6 = r13.L$1
                    r1 = r6
                    java.nio.ByteBuffer r1 = (java.nio.ByteBuffer) r1
                    java.lang.Object r6 = r13.L$0
                    r4 = r6
                    io.ktor.utils.io.t r4 = (io.ktor.utils.io.t) r4
                    kotlin.p.b(r14)
                    r6 = r5
                    r5 = r4
                    r4 = r13
                    goto L_0x00b5
                L_0x002d:
                    r1 = r3
                    r4 = r3
                    java.lang.Object r5 = r13.L$1
                    r1 = r5
                    java.nio.ByteBuffer r1 = (java.nio.ByteBuffer) r1
                    java.lang.Object r5 = r13.L$0
                    r4 = r5
                    io.ktor.utils.io.t r4 = (io.ktor.utils.io.t) r4
                    kotlin.p.b(r14)
                    r6 = r1
                    r5 = r4
                    r4 = r13
                    r1 = r0
                    r0 = r14
                    goto L_0x0064
                L_0x0042:
                    kotlin.p.b(r14)
                    io.ktor.utils.io.t r1 = r13.p$
                    r4 = r1
                    r1 = r13
                L_0x0049:
                    r5 = 1
                    java.nio.ByteBuffer r6 = r4.a(r2, r5)
                    if (r6 != 0) goto L_0x0074
                    r1.L$0 = r4
                    r1.L$1 = r6
                    r1.label = r5
                    java.lang.Object r5 = r4.k(r5, r1)
                    if (r5 != r0) goto L_0x005e
                    return r0
                L_0x005e:
                    r12 = r0
                    r0 = r14
                    r14 = r5
                    r5 = r4
                    r4 = r1
                    r1 = r12
                L_0x0064:
                    java.lang.Boolean r14 = (java.lang.Boolean) r14
                    boolean r14 = r14.booleanValue()
                    if (r14 != 0) goto L_0x006f
                    kotlin.x r14 = kotlin.x.a
                    return r14
                L_0x006f:
                    r14 = r0
                    r0 = r1
                    r1 = r4
                    r4 = r5
                    goto L_0x0049
                L_0x0074:
                    r5 = r4
                    r4 = r1
                    r1 = r6
                L_0x0077:
                    boolean r6 = r1.hasRemaining()
                    if (r6 == 0) goto L_0x00bb
                    kotlin.jvm.internal.x r6 = new kotlin.jvm.internal.x
                    r6.<init>()
                    r6.element = r2
                    io.ktor.utils.io.w r7 = r4.$this_reader
                    io.ktor.network.sockets.i$a r8 = r4.this$0
                    io.ktor.network.sockets.q$e r8 = r8.$socketOptions
                    if (r8 == 0) goto L_0x009b
                    long r8 = r8.l()
                    java.lang.Long r8 = kotlin.coroutines.jvm.internal.b.d(r8)
                    if (r8 == 0) goto L_0x009b
                    long r8 = r8.longValue()
                    goto L_0x00a0
                L_0x009b:
                    r8 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                L_0x00a0:
                    io.ktor.network.sockets.i$a$a$a r10 = new io.ktor.network.sockets.i$a$a$a
                    r10.<init>(r4, r6, r1, r3)
                    r4.L$0 = r5
                    r4.L$1 = r1
                    r4.L$2 = r6
                    r11 = 2
                    r4.label = r11
                    java.lang.Object r7 = io.ktor.network.util.c.a(r7, r8, r10, r4)
                    if (r7 != r0) goto L_0x00b5
                    return r0
                L_0x00b5:
                    int r7 = r6.element
                    r5.w(r7)
                    goto L_0x0077
                L_0x00bb:
                    r1 = r4
                    r4 = r5
                    goto L_0x0049
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.i.a.C0257a.invokeSuspend(java.lang.Object):java.lang.Object");
            }

            @f(c = "io.ktor.network.sockets.CIOWriterKt$attachForWritingDirectImpl$1$1$1", f = "CIOWriter.kt", l = {91}, m = "invokeSuspend")
            /* renamed from: io.ktor.network.sockets.i$a$a$a  reason: collision with other inner class name */
            /* compiled from: CIOWriter.kt */
            public static final class C0258a extends l implements p<o0, d<? super x>, Object> {
                final /* synthetic */ ByteBuffer $buffer;
                final /* synthetic */ kotlin.jvm.internal.x $rc;
                Object L$0;
                int label;
                private o0 p$;
                final /* synthetic */ C0257a this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0258a(C0257a aVar, kotlin.jvm.internal.x xVar, ByteBuffer byteBuffer, d dVar) {
                    super(2, dVar);
                    this.this$0 = aVar;
                    this.$rc = xVar;
                    this.$buffer = byteBuffer;
                }

                @NotNull
                public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                    kotlin.jvm.internal.k.f(dVar, "completion");
                    C0258a aVar = new C0258a(this.this$0, this.$rc, this.$buffer, dVar);
                    o0 o0Var = (o0) obj;
                    aVar.p$ = (o0) obj;
                    return aVar;
                }

                public final Object invoke(Object obj, Object obj2) {
                    return ((C0258a) create(obj, (d) obj2)).invokeSuspend(x.a);
                }

                /* Debug info: failed to restart local var, previous not found, register: 7 */
                /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
                /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: kotlinx.coroutines.o0} */
                /* JADX WARNING: Multi-variable type inference failed */
                /* JADX WARNING: Removed duplicated region for block: B:8:0x0039  */
                @org.jetbrains.annotations.Nullable
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
                    /*
                        r7 = this;
                        java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                        int r1 = r7.label
                        switch(r1) {
                            case 0: goto L_0x001c;
                            case 1: goto L_0x0011;
                            default: goto L_0x0009;
                        }
                    L_0x0009:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L_0x0011:
                        r1 = 0
                        java.lang.Object r2 = r7.L$0
                        r1 = r2
                        kotlinx.coroutines.o0 r1 = (kotlinx.coroutines.o0) r1
                        kotlin.p.b(r8)
                        r2 = r7
                        goto L_0x0058
                    L_0x001c:
                        kotlin.p.b(r8)
                        kotlinx.coroutines.o0 r1 = r7.p$
                        r2 = r7
                    L_0x0022:
                        kotlin.jvm.internal.x r3 = r2.$rc
                        io.ktor.network.sockets.i$a$a r4 = r2.this$0
                        io.ktor.network.sockets.i$a r4 = r4.this$0
                        java.nio.channels.WritableByteChannel r4 = r4.$nioChannel
                        java.nio.ByteBuffer r5 = r2.$buffer
                        int r4 = r4.write(r5)
                        r3.element = r4
                        kotlin.jvm.internal.x r3 = r2.$rc
                        int r3 = r3.element
                        if (r3 != 0) goto L_0x0058
                        io.ktor.network.sockets.i$a$a r3 = r2.this$0
                        io.ktor.network.sockets.i$a r3 = r3.this$0
                        io.ktor.network.selector.k r3 = r3.$selectable
                        io.ktor.network.selector.j r4 = io.ktor.network.selector.j.WRITE
                        r5 = 1
                        r3.Z(r4, r5)
                        io.ktor.network.sockets.i$a$a r3 = r2.this$0
                        io.ktor.network.sockets.i$a r3 = r3.this$0
                        io.ktor.network.selector.m r6 = r3.$selector
                        io.ktor.network.selector.k r3 = r3.$selectable
                        r2.L$0 = r1
                        r2.label = r5
                        java.lang.Object r3 = r6.g(r3, r4, r2)
                        if (r3 != r0) goto L_0x0058
                        return r0
                    L_0x0058:
                        java.nio.ByteBuffer r3 = r2.$buffer
                        boolean r3 = r3.hasRemaining()
                        if (r3 == 0) goto L_0x0066
                        kotlin.jvm.internal.x r3 = r2.$rc
                        int r3 = r3.element
                        if (r3 == 0) goto L_0x0022
                    L_0x0066:
                        kotlin.x r0 = kotlin.x.a
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.i.a.C0257a.C0258a.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }
        }
    }

    @NotNull
    public static final v a(@NotNull o0 $this$attachForWritingDirectImpl, @NotNull io.ktor.utils.io.f channel, @NotNull WritableByteChannel nioChannel, @NotNull k selectable, @NotNull m selector, @Nullable q.e socketOptions) {
        kotlin.jvm.internal.k.f($this$attachForWritingDirectImpl, "$this$attachForWritingDirectImpl");
        kotlin.jvm.internal.k.f(channel, "channel");
        kotlin.jvm.internal.k.f(nioChannel, "nioChannel");
        kotlin.jvm.internal.k.f(selectable, "selectable");
        kotlin.jvm.internal.k.f(selector, "selector");
        return io.ktor.utils.io.q.b($this$attachForWritingDirectImpl, d1.d().plus(new n0("cio-to-nio-writer")), channel, new a(selectable, channel, socketOptions, nioChannel, selector, (d) null));
    }
}
