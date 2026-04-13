package io.ktor.network.sockets;

import io.ktor.utils.io.g;
import io.ktor.utils.io.i;
import io.ktor.utils.io.l;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sockets.kt */
public final class s {

    @f(c = "io.ktor.network.sockets.SocketsKt", f = "Sockets.kt", l = {40}, m = "awaitClosed")
    /* compiled from: Sockets.kt */
    public static final class a extends d {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return s.a((d) null, this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: io.ktor.network.sockets.d} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object a(@org.jetbrains.annotations.NotNull io.ktor.network.sockets.d r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r6) {
        /*
            boolean r0 = r6 instanceof io.ktor.network.sockets.s.a
            if (r0 == 0) goto L_0x0013
            r0 = r6
            io.ktor.network.sockets.s$a r0 = (io.ktor.network.sockets.s.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.network.sockets.s$a r0 = new io.ktor.network.sockets.s$a
            r0.<init>(r6)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0034;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r2 = r0.L$0
            r5 = r2
            io.ktor.network.sockets.d r5 = (io.ktor.network.sockets.d) r5
            kotlin.p.b(r1)
            goto L_0x0047
        L_0x0034:
            kotlin.p.b(r1)
            kotlinx.coroutines.z1 r3 = r5.L0()
            r0.L$0 = r5
            r4 = 1
            r0.label = r4
            java.lang.Object r3 = r3.J(r0)
            if (r3 != r2) goto L_0x0047
            return r2
        L_0x0047:
            kotlinx.coroutines.z1 r2 = r5.L0()
            boolean r2 = r2.isCancelled()
            if (r2 != 0) goto L_0x0055
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x0055:
            kotlinx.coroutines.z1 r2 = r5.L0()
            java.util.concurrent.CancellationException r2 = r2.n()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.network.sockets.s.a(io.ktor.network.sockets.d, kotlin.coroutines.d):java.lang.Object");
    }

    @NotNull
    public static final i b(@NotNull c $this$openReadChannel) {
        k.f($this$openReadChannel, "$this$openReadChannel");
        io.ktor.utils.io.f it = g.a(false);
        $this$openReadChannel.a(it);
        return it;
    }

    @NotNull
    public static final l c(@NotNull e $this$openWriteChannel, boolean autoFlush) {
        k.f($this$openWriteChannel, "$this$openWriteChannel");
        io.ktor.utils.io.f it = g.a(autoFlush);
        $this$openWriteChannel.g(it);
        return it;
    }

    public static /* synthetic */ l d(e eVar, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return c(eVar, z);
    }
}
