package io.ktor.utils.io;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.ktor.utils.io.core.q;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteReadChannel.kt */
public final class k {

    @f(c = "io.ktor.utils.io.ByteReadChannelKt", f = "ByteReadChannel.kt", l = {258}, m = "copyAndClose")
    /* compiled from: ByteReadChannel.kt */
    public static final class a extends d {
        long J$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return k.b((i) null, (l) null, 0, this);
        }
    }

    @Nullable
    public static final Object e(@NotNull i $this$readPacket, int size, @NotNull kotlin.coroutines.d<? super q> $completion) {
        return $this$readPacket.o(size, 0, $completion);
    }

    @Nullable
    public static final Object f(@NotNull i $this$readRemaining, long limit, @NotNull kotlin.coroutines.d<? super q> $completion) {
        return $this$readRemaining.p(limit, 0, $completion);
    }

    @Nullable
    public static final Object g(@NotNull i $this$readRemaining, @NotNull kotlin.coroutines.d<? super q> $completion) {
        return $this$readRemaining.p(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, 0, $completion);
    }

    public static final boolean a(@NotNull i $this$cancel) {
        kotlin.jvm.internal.k.f($this$cancel, "$this$cancel");
        return $this$cancel.b((Throwable) null);
    }

    @Nullable
    public static final Object d(@NotNull i $this$discard, @NotNull kotlin.coroutines.d<? super Long> $completion) {
        return $this$discard.g(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, $completion);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: io.ktor.utils.io.l} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object b(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r5, @org.jetbrains.annotations.NotNull io.ktor.utils.io.l r6, long r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r9) {
        /*
            boolean r0 = r9 instanceof io.ktor.utils.io.k.a
            if (r0 == 0) goto L_0x0013
            r0 = r9
            io.ktor.utils.io.k$a r0 = (io.ktor.utils.io.k.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.k$a r0 = new io.ktor.utils.io.k$a
            r0.<init>(r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003c;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            long r7 = r0.J$0
            java.lang.Object r2 = r0.L$1
            r6 = r2
            io.ktor.utils.io.l r6 = (io.ktor.utils.io.l) r6
            java.lang.Object r2 = r0.L$0
            r5 = r2
            io.ktor.utils.io.i r5 = (io.ktor.utils.io.i) r5
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x004f
        L_0x003c:
            kotlin.p.b(r1)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.J$0 = r7
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = io.ktor.utils.io.j.a(r5, r6, r7, r0)
            if (r3 != r2) goto L_0x004f
            return r2
        L_0x004f:
            java.lang.Number r3 = (java.lang.Number) r3
            long r2 = r3.longValue()
            io.ktor.utils.io.m.a(r6)
            java.lang.Long r4 = kotlin.coroutines.jvm.internal.b.d(r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.k.b(io.ktor.utils.io.i, io.ktor.utils.io.l, long, kotlin.coroutines.d):java.lang.Object");
    }

    public static /* synthetic */ Object c(i iVar, l lVar, long j, kotlin.coroutines.d dVar, int i, Object obj) {
        if ((i & 2) != 0) {
            j = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        return b(iVar, lVar, j, dVar);
    }
}
