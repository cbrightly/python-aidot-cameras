package io.ktor.network.sockets;

import io.ktor.network.selector.k;
import io.ktor.network.selector.m;
import io.ktor.network.sockets.q;
import io.ktor.utils.io.b0;
import io.ktor.utils.io.y;
import io.ktor.utils.io.z;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.n0;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CIOReader.kt */
public final class h {

    @f(c = "io.ktor.network.sockets.CIOReaderKt$attachForReadingImpl$1", f = "CIOReader.kt", l = {32, 49}, m = "invokeSuspend")
    /* compiled from: CIOReader.kt */
    public static final class b extends l implements p<z, d<? super x>, Object> {
        final /* synthetic */ ByteBuffer $buffer;
        final /* synthetic */ io.ktor.utils.io.f $channel;
        final /* synthetic */ ReadableByteChannel $nioChannel;
        final /* synthetic */ io.ktor.utils.io.pool.d $pool;
        final /* synthetic */ k $selectable;
        final /* synthetic */ m $selector;
        final /* synthetic */ q.e $socketOptions;
        Object L$0;
        Object L$1;
        int label;
        private z p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(q.e eVar, ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer, io.ktor.utils.io.f fVar, k kVar, m mVar, io.ktor.utils.io.pool.d dVar, d dVar2) {
            super(2, dVar2);
            this.$socketOptions = eVar;
            this.$nioChannel = readableByteChannel;
            this.$buffer = byteBuffer;
            this.$channel = fVar;
            this.$selectable = kVar;
            this.$selector = mVar;
            this.$pool = dVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            b bVar = new b(this.$socketOptions, this.$nioChannel, this.$buffer, this.$channel, this.$selectable, this.$selector, this.$pool, dVar);
            z zVar = (z) obj;
            bVar.p$ = (z) obj;
            return bVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((b) create(obj, (d) obj2)).invokeSuspend(x.a);
        }

        /* Debug info: failed to restart local var, previous not found, register: 11 */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v14, resolved type: kotlin.jvm.internal.x} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: io.ktor.utils.io.z} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: io.ktor.utils.io.z} */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0050, code lost:
            r6 = kotlin.coroutines.jvm.internal.b.d(r6.l());
         */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x005a A[Catch:{ all -> 0x00d5 }] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x005f A[Catch:{ all -> 0x00d5 }] */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0077  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x00e4 A[SYNTHETIC, Splitter:B:52:0x00e4] */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r12) {
            /*
                r11 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r11.label
                r2 = 0
                r3 = 0
                switch(r1) {
                    case 0: goto L_0x003d;
                    case 1: goto L_0x0027;
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
                java.lang.Object r5 = r11.L$1
                r1 = r5
                kotlin.jvm.internal.x r1 = (kotlin.jvm.internal.x) r1
                java.lang.Object r5 = r11.L$0
                r4 = r5
                io.ktor.utils.io.z r4 = (io.ktor.utils.io.z) r4
                kotlin.p.b(r12)     // Catch:{ all -> 0x0039 }
                r5 = r4
                r4 = r1
                r1 = r11
                goto L_0x00c5
            L_0x0027:
                r1 = r3
                r4 = r3
                java.lang.Object r5 = r11.L$1
                r1 = r5
                kotlin.jvm.internal.x r1 = (kotlin.jvm.internal.x) r1
                java.lang.Object r5 = r11.L$0
                r4 = r5
                io.ktor.utils.io.z r4 = (io.ktor.utils.io.z) r4
                kotlin.p.b(r12)     // Catch:{ all -> 0x0039 }
                r5 = r4
                r4 = r11
                goto L_0x007b
            L_0x0039:
                r0 = move-exception
                r1 = r11
                goto L_0x00d6
            L_0x003d:
                kotlin.p.b(r12)
                io.ktor.utils.io.z r1 = r11.p$
                r4 = r1
                r1 = r11
            L_0x0044:
                kotlin.jvm.internal.x r5 = new kotlin.jvm.internal.x     // Catch:{ all -> 0x00d5 }
                r5.<init>()     // Catch:{ all -> 0x00d5 }
                r5.element = r2     // Catch:{ all -> 0x00d5 }
                io.ktor.network.sockets.q$e r6 = r1.$socketOptions     // Catch:{ all -> 0x00d5 }
                if (r6 == 0) goto L_0x005f
                long r6 = r6.l()     // Catch:{ all -> 0x00d5 }
                java.lang.Long r6 = kotlin.coroutines.jvm.internal.b.d(r6)     // Catch:{ all -> 0x00d5 }
                if (r6 == 0) goto L_0x005f
                long r6 = r6.longValue()     // Catch:{ all -> 0x00d5 }
                goto L_0x0064
            L_0x005f:
                r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            L_0x0064:
                io.ktor.network.sockets.h$b$a r8 = new io.ktor.network.sockets.h$b$a     // Catch:{ all -> 0x00d5 }
                r8.<init>(r1, r5, r3)     // Catch:{ all -> 0x00d5 }
                r1.L$0 = r4     // Catch:{ all -> 0x00d5 }
                r1.L$1 = r5     // Catch:{ all -> 0x00d5 }
                r9 = 1
                r1.label = r9     // Catch:{ all -> 0x00d5 }
                java.lang.Object r6 = io.ktor.network.util.c.a(r4, r6, r8, r1)     // Catch:{ all -> 0x00d5 }
                if (r6 != r0) goto L_0x0077
                return r0
            L_0x0077:
                r10 = r4
                r4 = r1
                r1 = r5
                r5 = r10
            L_0x007b:
                int r6 = r1.element     // Catch:{ all -> 0x00d1 }
                r7 = -1
                if (r6 != r7) goto L_0x00a4
                io.ktor.utils.io.f r0 = r4.$channel     // Catch:{ all -> 0x00d1 }
                io.ktor.utils.io.m.a(r0)     // Catch:{ all -> 0x00d1 }
                io.ktor.utils.io.pool.d r0 = r4.$pool
                java.nio.ByteBuffer r1 = r4.$buffer
                r0.N0(r1)
                java.nio.channels.ReadableByteChannel r0 = r4.$nioChannel
                boolean r1 = r0 instanceof java.nio.channels.SocketChannel
                if (r1 == 0) goto L_0x009f
                java.nio.channels.SocketChannel r0 = (java.nio.channels.SocketChannel) r0     // Catch:{ ClosedChannelException -> 0x009e }
                java.net.Socket r0 = r0.socket()     // Catch:{ ClosedChannelException -> 0x009e }
                r0.shutdownInput()     // Catch:{ ClosedChannelException -> 0x009e }
                goto L_0x009f
            L_0x009e:
                r0 = move-exception
            L_0x009f:
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x00a4:
                io.ktor.network.selector.k r6 = r4.$selectable     // Catch:{ all -> 0x00d1 }
                io.ktor.network.selector.j r7 = io.ktor.network.selector.j.READ     // Catch:{ all -> 0x00d1 }
                r6.Z(r7, r2)     // Catch:{ all -> 0x00d1 }
                java.nio.ByteBuffer r6 = r4.$buffer     // Catch:{ all -> 0x00d1 }
                r6.flip()     // Catch:{ all -> 0x00d1 }
                io.ktor.utils.io.f r6 = r4.$channel     // Catch:{ all -> 0x00d1 }
                java.nio.ByteBuffer r7 = r4.$buffer     // Catch:{ all -> 0x00d1 }
                r4.L$0 = r5     // Catch:{ all -> 0x00d1 }
                r4.L$1 = r1     // Catch:{ all -> 0x00d1 }
                r8 = 2
                r4.label = r8     // Catch:{ all -> 0x00d1 }
                java.lang.Object r6 = r6.h(r7, r4)     // Catch:{ all -> 0x00d1 }
                if (r6 != r0) goto L_0x00c2
                return r0
            L_0x00c2:
                r10 = r4
                r4 = r1
                r1 = r10
            L_0x00c5:
                java.nio.ByteBuffer r6 = r1.$buffer     // Catch:{ all -> 0x00ce }
                r6.clear()     // Catch:{ all -> 0x00ce }
                r4 = r5
                goto L_0x0044
            L_0x00ce:
                r0 = move-exception
                r4 = r5
                goto L_0x00d6
            L_0x00d1:
                r0 = move-exception
                r1 = r4
                r4 = r5
                goto L_0x00d6
            L_0x00d5:
                r0 = move-exception
            L_0x00d6:
                io.ktor.utils.io.pool.d r2 = r1.$pool
                java.nio.ByteBuffer r3 = r1.$buffer
                r2.N0(r3)
                java.nio.channels.ReadableByteChannel r2 = r1.$nioChannel
                boolean r3 = r2 instanceof java.nio.channels.SocketChannel
                if (r3 == 0) goto L_0x00ef
                java.nio.channels.SocketChannel r2 = (java.nio.channels.SocketChannel) r2     // Catch:{ ClosedChannelException -> 0x00ee }
                java.net.Socket r2 = r2.socket()     // Catch:{ ClosedChannelException -> 0x00ee }
                r2.shutdownInput()     // Catch:{ ClosedChannelException -> 0x00ee }
                goto L_0x00ef
            L_0x00ee:
                r2 = move-exception
            L_0x00ef:
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.h.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @f(c = "io.ktor.network.sockets.CIOReaderKt$attachForReadingImpl$1$1", f = "CIOReader.kt", l = {38}, m = "invokeSuspend")
        /* compiled from: CIOReader.kt */
        public static final class a extends l implements p<o0, d<? super x>, Object> {
            final /* synthetic */ kotlin.jvm.internal.x $rc;
            Object L$0;
            int label;
            private o0 p$;
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar, kotlin.jvm.internal.x xVar, d dVar) {
                super(2, dVar);
                this.this$0 = bVar;
                this.$rc = xVar;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                kotlin.jvm.internal.k.f(dVar, "completion");
                a aVar = new a(this.this$0, this.$rc, dVar);
                o0 o0Var = (o0) obj;
                aVar.p$ = (o0) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((a) create(obj, (d) obj2)).invokeSuspend(x.a);
            }

            /* Debug info: failed to restart local var, previous not found, register: 7 */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: kotlinx.coroutines.o0} */
            /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
                jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
                	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
                	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
                */
            /* JADX WARNING: Multi-variable type inference failed */
            /* JADX WARNING: Removed duplicated region for block: B:13:0x005f  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0037  */
            @org.jetbrains.annotations.Nullable
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
                    goto L_0x0059
                L_0x001c:
                    kotlin.p.b(r8)
                    kotlinx.coroutines.o0 r1 = r7.p$
                    r2 = r7
                L_0x0022:
                    kotlin.jvm.internal.x r3 = r2.$rc
                    io.ktor.network.sockets.h$b r4 = r2.this$0
                    java.nio.channels.ReadableByteChannel r5 = r4.$nioChannel
                    java.nio.ByteBuffer r4 = r4.$buffer
                    int r4 = r5.read(r4)
                    r3.element = r4
                    kotlin.jvm.internal.x r3 = r2.$rc
                    int r3 = r3.element
                    if (r3 != 0) goto L_0x0059
                    io.ktor.network.sockets.h$b r3 = r2.this$0
                    io.ktor.utils.io.f r3 = r3.$channel
                    r3.flush()
                    io.ktor.network.sockets.h$b r3 = r2.this$0
                    io.ktor.network.selector.k r3 = r3.$selectable
                    io.ktor.network.selector.j r4 = io.ktor.network.selector.j.READ
                    r5 = 1
                    r3.Z(r4, r5)
                    io.ktor.network.sockets.h$b r3 = r2.this$0
                    io.ktor.network.selector.m r6 = r3.$selector
                    io.ktor.network.selector.k r3 = r3.$selectable
                    r2.L$0 = r1
                    r2.label = r5
                    java.lang.Object r3 = r6.g(r3, r4, r2)
                    if (r3 != r0) goto L_0x0059
                    return r0
                L_0x0059:
                    kotlin.jvm.internal.x r3 = r2.$rc
                    int r3 = r3.element
                    if (r3 == 0) goto L_0x0062
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0062:
                    goto L_0x0022
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.h.b.a.invokeSuspend(java.lang.Object):java.lang.Object");
            }
        }
    }

    @NotNull
    public static final y b(@NotNull o0 $this$attachForReadingImpl, @NotNull io.ktor.utils.io.f channel, @NotNull ReadableByteChannel nioChannel, @NotNull k selectable, @NotNull m selector, @NotNull io.ktor.utils.io.pool.d<ByteBuffer> pool, @Nullable q.e socketOptions) {
        o0 o0Var = $this$attachForReadingImpl;
        io.ktor.utils.io.f fVar = channel;
        kotlin.jvm.internal.k.f(o0Var, "$this$attachForReadingImpl");
        kotlin.jvm.internal.k.f(fVar, "channel");
        kotlin.jvm.internal.k.f(nioChannel, "nioChannel");
        kotlin.jvm.internal.k.f(selectable, "selectable");
        kotlin.jvm.internal.k.f(selector, "selector");
        kotlin.jvm.internal.k.f(pool, "pool");
        g plus = d1.d().plus(new n0("cio-from-nio-reader"));
        b bVar = r1;
        b bVar2 = new b(socketOptions, nioChannel, pool.p0(), channel, selectable, selector, pool, (d) null);
        return io.ktor.utils.io.q.d(o0Var, plus, fVar, bVar);
    }

    @f(c = "io.ktor.network.sockets.CIOReaderKt$attachForReadingDirectImpl$1", f = "CIOReader.kt", l = {76}, m = "invokeSuspend")
    /* compiled from: CIOReader.kt */
    public static final class a extends l implements p<z, d<? super x>, Object> {
        final /* synthetic */ io.ktor.utils.io.f $channel;
        final /* synthetic */ ReadableByteChannel $nioChannel;
        final /* synthetic */ k $selectable;
        final /* synthetic */ m $selector;
        final /* synthetic */ q.e $socketOptions;
        Object L$0;
        int label;
        private z p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(k kVar, io.ktor.utils.io.f fVar, q.e eVar, ReadableByteChannel readableByteChannel, m mVar, d dVar) {
            super(2, dVar);
            this.$selectable = kVar;
            this.$channel = fVar;
            this.$socketOptions = eVar;
            this.$nioChannel = readableByteChannel;
            this.$selector = mVar;
        }

        @NotNull
        public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            a aVar = new a(this.$selectable, this.$channel, this.$socketOptions, this.$nioChannel, this.$selector, dVar);
            z zVar = (z) obj;
            aVar.p$ = (z) obj;
            return aVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((a) create(obj, (d) obj2)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            io.ktor.utils.io.m.a(r7.$channel);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0045, code lost:
            r1 = r7.$nioChannel;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0049, code lost:
            if ((r1 instanceof java.nio.channels.SocketChannel) == false) goto L_0x0057;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            ((java.nio.channels.SocketChannel) r1).socket().shutdownInput();
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r7.label
                r2 = 0
                switch(r1) {
                    case 0: goto L_0x001e;
                    case 1: goto L_0x0012;
                    default: goto L_0x000a;
                }
            L_0x000a:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0012:
                r0 = r2
                java.lang.Object r1 = r7.L$0
                r0 = r1
                io.ktor.utils.io.z r0 = (io.ktor.utils.io.z) r0
                kotlin.p.b(r8)     // Catch:{ all -> 0x001c }
                goto L_0x0040
            L_0x001c:
                r1 = move-exception
                goto L_0x0060
            L_0x001e:
                kotlin.p.b(r8)
                io.ktor.utils.io.z r1 = r7.p$
                io.ktor.network.selector.k r3 = r7.$selectable     // Catch:{ all -> 0x005c }
                io.ktor.network.selector.j r4 = io.ktor.network.selector.j.READ     // Catch:{ all -> 0x005c }
                r5 = 0
                r3.Z(r4, r5)     // Catch:{ all -> 0x005c }
                io.ktor.utils.io.f r3 = r7.$channel     // Catch:{ all -> 0x005c }
                io.ktor.network.sockets.h$a$a r4 = new io.ktor.network.sockets.h$a$a     // Catch:{ all -> 0x005c }
                r4.<init>(r7, r1, r2)     // Catch:{ all -> 0x005c }
                r7.L$0 = r1     // Catch:{ all -> 0x005c }
                r2 = 1
                r7.label = r2     // Catch:{ all -> 0x005c }
                java.lang.Object r2 = r3.n(r4, r7)     // Catch:{ all -> 0x005c }
                if (r2 != r0) goto L_0x003f
                return r0
            L_0x003f:
                r0 = r1
            L_0x0040:
                io.ktor.utils.io.f r1 = r7.$channel     // Catch:{ all -> 0x001c }
                io.ktor.utils.io.m.a(r1)     // Catch:{ all -> 0x001c }
                java.nio.channels.ReadableByteChannel r1 = r7.$nioChannel
                boolean r2 = r1 instanceof java.nio.channels.SocketChannel
                if (r2 == 0) goto L_0x0057
                java.nio.channels.SocketChannel r1 = (java.nio.channels.SocketChannel) r1     // Catch:{ ClosedChannelException -> 0x0056 }
                java.net.Socket r1 = r1.socket()     // Catch:{ ClosedChannelException -> 0x0056 }
                r1.shutdownInput()     // Catch:{ ClosedChannelException -> 0x0056 }
                goto L_0x0057
            L_0x0056:
                r1 = move-exception
            L_0x0057:
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x005c:
                r0 = move-exception
                r6 = r1
                r1 = r0
                r0 = r6
            L_0x0060:
                java.nio.channels.ReadableByteChannel r2 = r7.$nioChannel
                boolean r3 = r2 instanceof java.nio.channels.SocketChannel
                if (r3 == 0) goto L_0x0072
                java.nio.channels.SocketChannel r2 = (java.nio.channels.SocketChannel) r2     // Catch:{ ClosedChannelException -> 0x0071 }
                java.net.Socket r2 = r2.socket()     // Catch:{ ClosedChannelException -> 0x0071 }
                r2.shutdownInput()     // Catch:{ ClosedChannelException -> 0x0071 }
                goto L_0x0072
            L_0x0071:
                r2 = move-exception
            L_0x0072:
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.h.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        @f(c = "io.ktor.network.sockets.CIOReaderKt$attachForReadingDirectImpl$1$1", f = "CIOReader.kt", l = {80}, m = "invokeSuspend")
        /* renamed from: io.ktor.network.sockets.h$a$a  reason: collision with other inner class name */
        /* compiled from: CIOReader.kt */
        public static final class C0255a extends l implements p<b0, d<? super x>, Object> {
            final /* synthetic */ z $this_writer;
            Object L$0;
            Object L$1;
            int label;
            private b0 p$;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0255a(a aVar, z zVar, d dVar) {
                super(2, dVar);
                this.this$0 = aVar;
                this.$this_writer = zVar;
            }

            @NotNull
            public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                kotlin.jvm.internal.k.f(dVar, "completion");
                C0255a aVar = new C0255a(this.this$0, this.$this_writer, dVar);
                b0 b0Var = (b0) obj;
                aVar.p$ = (b0) obj;
                return aVar;
            }

            public final Object invoke(Object obj, Object obj2) {
                return ((C0255a) create(obj, (d) obj2)).invokeSuspend(x.a);
            }

            /* Debug info: failed to restart local var, previous not found, register: 11 */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: kotlin.jvm.internal.x} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Object} */
            /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: io.ktor.utils.io.b0} */
            /* JADX WARNING: Code restructure failed: missing block: B:8:0x003c, code lost:
                r6 = kotlin.coroutines.jvm.internal.b.d(r6.l());
             */
            /* JADX WARNING: Multi-variable type inference failed */
            /* JADX WARNING: Removed duplicated region for block: B:10:0x0046  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x004b  */
            /* JADX WARNING: Removed duplicated region for block: B:15:0x0063  */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r12) {
                /*
                    r11 = this;
                    java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                    int r1 = r11.label
                    r2 = 0
                    switch(r1) {
                        case 0: goto L_0x0024;
                        case 1: goto L_0x0012;
                        default: goto L_0x000a;
                    }
                L_0x000a:
                    java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                    java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                    r0.<init>(r1)
                    throw r0
                L_0x0012:
                    r1 = r2
                    r3 = r2
                    java.lang.Object r4 = r11.L$1
                    r1 = r4
                    kotlin.jvm.internal.x r1 = (kotlin.jvm.internal.x) r1
                    java.lang.Object r4 = r11.L$0
                    r3 = r4
                    io.ktor.utils.io.b0 r3 = (io.ktor.utils.io.b0) r3
                    kotlin.p.b(r12)
                    r4 = r3
                    r3 = r11
                    goto L_0x0067
                L_0x0024:
                    kotlin.p.b(r12)
                    io.ktor.utils.io.b0 r1 = r11.p$
                    r3 = r1
                    r1 = r11
                L_0x002b:
                    kotlin.jvm.internal.x r4 = new kotlin.jvm.internal.x
                    r4.<init>()
                    r5 = 0
                    r4.element = r5
                    io.ktor.utils.io.z r5 = r1.$this_writer
                    io.ktor.network.sockets.h$a r6 = r1.this$0
                    io.ktor.network.sockets.q$e r6 = r6.$socketOptions
                    if (r6 == 0) goto L_0x004b
                    long r6 = r6.l()
                    java.lang.Long r6 = kotlin.coroutines.jvm.internal.b.d(r6)
                    if (r6 == 0) goto L_0x004b
                    long r6 = r6.longValue()
                    goto L_0x0050
                L_0x004b:
                    r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                L_0x0050:
                    io.ktor.network.sockets.h$a$a$a r8 = new io.ktor.network.sockets.h$a$a$a
                    r8.<init>(r1, r3, r4, r2)
                    r1.L$0 = r3
                    r1.L$1 = r4
                    r9 = 1
                    r1.label = r9
                    java.lang.Object r5 = io.ktor.network.util.c.a(r5, r6, r8, r1)
                    if (r5 != r0) goto L_0x0063
                    return r0
                L_0x0063:
                    r10 = r3
                    r3 = r1
                    r1 = r4
                    r4 = r10
                L_0x0067:
                    int r5 = r1.element
                    r6 = -1
                    if (r5 != r6) goto L_0x0070
                    kotlin.x r0 = kotlin.x.a
                    return r0
                L_0x0070:
                    r4.b(r5)
                    r1 = r3
                    r3 = r4
                    goto L_0x002b
                */
                throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.h.a.C0255a.invokeSuspend(java.lang.Object):java.lang.Object");
            }

            @f(c = "io.ktor.network.sockets.CIOReaderKt$attachForReadingDirectImpl$1$1$1", f = "CIOReader.kt", l = {86, 94}, m = "invokeSuspend")
            /* renamed from: io.ktor.network.sockets.h$a$a$a  reason: collision with other inner class name */
            /* compiled from: CIOReader.kt */
            public static final class C0256a extends l implements p<o0, d<? super x>, Object> {
                final /* synthetic */ kotlin.jvm.internal.x $rc;
                final /* synthetic */ b0 $this_writeSuspendSession;
                Object L$0;
                Object L$1;
                int label;
                private o0 p$;
                final /* synthetic */ C0255a this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                C0256a(C0255a aVar, b0 b0Var, kotlin.jvm.internal.x xVar, d dVar) {
                    super(2, dVar);
                    this.this$0 = aVar;
                    this.$this_writeSuspendSession = b0Var;
                    this.$rc = xVar;
                }

                @NotNull
                public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
                    kotlin.jvm.internal.k.f(dVar, "completion");
                    C0256a aVar = new C0256a(this.this$0, this.$this_writeSuspendSession, this.$rc, dVar);
                    o0 o0Var = (o0) obj;
                    aVar.p$ = (o0) obj;
                    return aVar;
                }

                public final Object invoke(Object obj, Object obj2) {
                    return ((C0256a) create(obj, (d) obj2)).invokeSuspend(x.a);
                }

                /* Debug info: failed to restart local var, previous not found, register: 8 */
                /* JADX WARNING: Code restructure failed: missing block: B:20:0x00ae, code lost:
                    if (r1.$rc.element != 0) goto L_0x00b0;
                 */
                @org.jetbrains.annotations.Nullable
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
                    /*
                        r8 = this;
                        java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                        int r1 = r8.label
                        r2 = 0
                        switch(r1) {
                            case 0: goto L_0x0035;
                            case 1: goto L_0x0024;
                            case 2: goto L_0x0012;
                            default: goto L_0x000a;
                        }
                    L_0x000a:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L_0x0012:
                        r1 = r2
                        java.lang.Object r3 = r8.L$1
                        r1 = r3
                        io.ktor.utils.io.core.a0 r1 = (io.ktor.utils.io.core.a0) r1
                        java.lang.Object r3 = r8.L$0
                        r2 = r3
                        kotlinx.coroutines.o0 r2 = (kotlinx.coroutines.o0) r2
                        kotlin.p.b(r9)
                        r3 = r1
                        r1 = r8
                        goto L_0x00aa
                    L_0x0024:
                        r1 = r2
                        java.lang.Object r3 = r8.L$1
                        r1 = r3
                        io.ktor.utils.io.core.a0 r1 = (io.ktor.utils.io.core.a0) r1
                        java.lang.Object r3 = r8.L$0
                        r2 = r3
                        kotlinx.coroutines.o0 r2 = (kotlinx.coroutines.o0) r2
                        kotlin.p.b(r9)
                        r3 = r1
                        r1 = r8
                        goto L_0x006b
                    L_0x0035:
                        kotlin.p.b(r9)
                        kotlinx.coroutines.o0 r1 = r8.p$
                        r2 = r1
                        r1 = r8
                    L_0x003c:
                        io.ktor.utils.io.b0 r3 = r1.$this_writeSuspendSession
                        r4 = 1
                        io.ktor.utils.io.core.a0 r3 = r3.a(r4)
                        if (r3 != 0) goto L_0x006c
                        io.ktor.network.sockets.h$a$a r5 = r1.this$0
                        io.ktor.network.sockets.h$a r5 = r5.this$0
                        io.ktor.utils.io.f r5 = r5.$channel
                        boolean r5 = r5.f()
                        if (r5 == 0) goto L_0x0053
                        goto L_0x00b0
                    L_0x0053:
                        io.ktor.network.sockets.h$a$a r5 = r1.this$0
                        io.ktor.network.sockets.h$a r5 = r5.this$0
                        io.ktor.utils.io.f r5 = r5.$channel
                        r5.flush()
                        io.ktor.utils.io.b0 r5 = r1.$this_writeSuspendSession
                        r1.L$0 = r2
                        r1.L$1 = r3
                        r1.label = r4
                        java.lang.Object r4 = r5.c(r4, r1)
                        if (r4 != r0) goto L_0x006b
                        return r0
                    L_0x006b:
                        goto L_0x00aa
                    L_0x006c:
                        kotlin.jvm.internal.x r5 = r1.$rc
                        io.ktor.network.sockets.h$a$a r6 = r1.this$0
                        io.ktor.network.sockets.h$a r6 = r6.this$0
                        java.nio.channels.ReadableByteChannel r6 = r6.$nioChannel
                        int r6 = io.ktor.utils.io.nio.a.a(r6, r3)
                        r5.element = r6
                        kotlin.jvm.internal.x r5 = r1.$rc
                        int r5 = r5.element
                        if (r5 != 0) goto L_0x00aa
                        io.ktor.network.sockets.h$a$a r5 = r1.this$0
                        io.ktor.network.sockets.h$a r5 = r5.this$0
                        io.ktor.utils.io.f r5 = r5.$channel
                        r5.flush()
                        io.ktor.network.sockets.h$a$a r5 = r1.this$0
                        io.ktor.network.sockets.h$a r5 = r5.this$0
                        io.ktor.network.selector.k r5 = r5.$selectable
                        io.ktor.network.selector.j r6 = io.ktor.network.selector.j.READ
                        r5.Z(r6, r4)
                        io.ktor.network.sockets.h$a$a r4 = r1.this$0
                        io.ktor.network.sockets.h$a r4 = r4.this$0
                        io.ktor.network.selector.m r5 = r4.$selector
                        io.ktor.network.selector.k r4 = r4.$selectable
                        r1.L$0 = r2
                        r1.L$1 = r3
                        r7 = 2
                        r1.label = r7
                        java.lang.Object r4 = r5.g(r4, r6, r1)
                        if (r4 != r0) goto L_0x00aa
                        return r0
                    L_0x00aa:
                        kotlin.jvm.internal.x r4 = r1.$rc
                        int r4 = r4.element
                        if (r4 == 0) goto L_0x003c
                    L_0x00b0:
                        kotlin.x r0 = kotlin.x.a
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.h.a.C0255a.C0256a.invokeSuspend(java.lang.Object):java.lang.Object");
                }
            }
        }
    }

    @NotNull
    public static final y a(@NotNull o0 $this$attachForReadingDirectImpl, @NotNull io.ktor.utils.io.f channel, @NotNull ReadableByteChannel nioChannel, @NotNull k selectable, @NotNull m selector, @Nullable q.e socketOptions) {
        kotlin.jvm.internal.k.f($this$attachForReadingDirectImpl, "$this$attachForReadingDirectImpl");
        kotlin.jvm.internal.k.f(channel, "channel");
        kotlin.jvm.internal.k.f(nioChannel, "nioChannel");
        kotlin.jvm.internal.k.f(selectable, "selectable");
        kotlin.jvm.internal.k.f(selector, "selector");
        return io.ktor.utils.io.q.d($this$attachForReadingDirectImpl, d1.d().plus(new n0("cio-from-nio-reader")), channel, new a(selectable, channel, socketOptions, nioChannel, selector, (d) null));
    }
}
