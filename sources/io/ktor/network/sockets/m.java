package io.ktor.network.sockets;

import io.ktor.network.selector.g;
import io.ktor.network.selector.j;
import io.ktor.network.selector.k;
import io.ktor.network.selector.l;
import io.ktor.network.sockets.l;
import io.ktor.network.sockets.q;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.z;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ServerSocketImpl.kt */
public final class m implements l, k {
    @NotNull
    private final z c;
    @NotNull
    private final ServerSocketChannel d;
    @NotNull
    private final io.ktor.network.selector.m f;
    private final /* synthetic */ l q;

    @f(c = "io.ktor.network.sockets.ServerSocketImpl", f = "ServerSocketImpl.kt", l = {34}, m = "acceptSuspend")
    /* compiled from: ServerSocketImpl.kt */
    public static final class a extends d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(m mVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = mVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a(this);
        }
    }

    @NotNull
    public g D() {
        return this.q.D();
    }

    public int X() {
        return this.q.X();
    }

    public void Z(@NotNull j jVar, boolean z) {
        kotlin.jvm.internal.k.f(jVar, "interest");
        this.q.Z(jVar, z);
    }

    public m(@NotNull ServerSocketChannel channel, @NotNull io.ktor.network.selector.m selector) {
        kotlin.jvm.internal.k.f(channel, "channel");
        kotlin.jvm.internal.k.f(selector, "selector");
        this.q = new l(channel);
        this.d = channel;
        this.f = selector;
        if (!getChannel().isBlocking()) {
            this.c = e2.b((z1) null, 1, (Object) null);
            return;
        }
        throw new IllegalArgumentException("channel need to be configured as non-blocking".toString());
    }

    @NotNull
    /* renamed from: i */
    public ServerSocketChannel getChannel() {
        return this.d;
    }

    @NotNull
    /* renamed from: j */
    public z L0() {
        return this.c;
    }

    @Nullable
    public Object Q0(@NotNull kotlin.coroutines.d<? super n> $completion) {
        SocketChannel it = getChannel().accept();
        if (it != null) {
            return g(it);
        }
        return a($completion);
    }

    /* Debug info: failed to restart local var, previous not found, register: 7 */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0055  */
    @org.jetbrains.annotations.Nullable
    final /* synthetic */ java.lang.Object a(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.network.sockets.n> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof io.ktor.network.sockets.m.a
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.network.sockets.m$a r0 = (io.ktor.network.sockets.m.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.network.sockets.m$a r0 = new io.ktor.network.sockets.m$a
            r0.<init>(r7, r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0033;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r3 = r0.L$0
            io.ktor.network.sockets.m r3 = (io.ktor.network.sockets.m) r3
            kotlin.p.b(r1)
            goto L_0x004b
        L_0x0033:
            kotlin.p.b(r1)
            r3 = r7
        L_0x0037:
            io.ktor.network.selector.j r4 = io.ktor.network.selector.j.ACCEPT
            r5 = 1
            r3.Z(r4, r5)
            io.ktor.network.selector.m r6 = r3.f
            r0.L$0 = r3
            r0.label = r5
            java.lang.Object r4 = r6.g(r3, r4, r0)
            if (r4 != r2) goto L_0x004b
            return r2
        L_0x004b:
            java.nio.channels.ServerSocketChannel r4 = r3.getChannel()
            java.nio.channels.SocketChannel r4 = r4.accept()
            if (r4 == 0) goto L_0x005c
            r2 = r4
            r4 = 0
            io.ktor.network.sockets.n r5 = r3.g(r2)
            return r5
        L_0x005c:
            goto L_0x0037
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.m.a(kotlin.coroutines.d):java.lang.Object");
    }

    private final n g(SocketChannel nioChannel) {
        Z(j.ACCEPT, false);
        Socket socket = nioChannel.socket();
        if (socket == null) {
            kotlin.jvm.internal.k.n();
        }
        nioChannel.configureBlocking(false);
        socket.setTcpNoDelay(true);
        return new p(nioChannel, socket, this.f, (q.e) null, 8, (DefaultConstructorMarker) null);
    }

    public void close() {
        try {
            getChannel().close();
            this.f.c(this);
            L0().complete();
        } catch (Throwable cause) {
            L0().a(cause);
        }
    }

    public void dispose() {
        l.a.a(this);
    }
}
