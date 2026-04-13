package io.ktor.utils.io.jvm.javaio;

import io.ktor.utils.io.i;
import io.ktor.utils.io.q;
import io.ktor.utils.io.z;
import java.io.InputStream;
import java.nio.ByteBuffer;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.s1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Reading.kt */
public final class d {
    public static /* synthetic */ i b(InputStream inputStream, g gVar, io.ktor.utils.io.pool.d dVar, int i, Object obj) {
        if ((i & 1) != 0) {
            gVar = d1.b();
        }
        return a(inputStream, gVar, dVar);
    }

    @f(c = "io.ktor.utils.io.jvm.javaio.ReadingKt$toByteReadChannel$1", f = "Reading.kt", l = {61}, m = "invokeSuspend")
    /* compiled from: Reading.kt */
    public static final class a extends l implements p<z, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ io.ktor.utils.io.pool.d $pool;
        final /* synthetic */ InputStream $this_toByteReadChannel;
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        private z p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(InputStream inputStream, io.ktor.utils.io.pool.d dVar, kotlin.coroutines.d dVar2) {
            super(2, dVar2);
            this.$this_toByteReadChannel = inputStream;
            this.$pool = dVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            a aVar = new a(this.$this_toByteReadChannel, this.$pool, dVar);
            z zVar = (z) obj;
            aVar.p$ = (z) obj;
            return aVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((a) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* Debug info: failed to restart local var, previous not found, register: 9 */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: java.nio.ByteBuffer} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: io.ktor.utils.io.z} */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r2.clear();
            r4 = r3.$this_toByteReadChannel.read(r2.array(), r2.arrayOffset() + r2.position(), r2.remaining());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0051, code lost:
            if (r4 >= 0) goto L_0x0054;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0054, code lost:
            if (r4 != 0) goto L_0x0057;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0057, code lost:
            r2.position(r2.position() + r4);
            r2.flip();
            r5 = r1.getChannel();
            r3.L$0 = r1;
            r3.L$1 = r2;
            r3.I$0 = r4;
            r3.label = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0073, code lost:
            if (r5.h(r2, r3) != r0) goto L_0x0076;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0075, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0076, code lost:
            r8 = r4;
            r4 = r3;
            r3 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0079, code lost:
            r3 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x007b, code lost:
            r0 = th;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                r9 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r9.label
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
                r1 = 0
                r2 = r1
                r3 = 0
                int r3 = r9.I$0
                java.lang.Object r4 = r9.L$1
                r2 = r4
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                java.lang.Object r4 = r9.L$0
                r1 = r4
                io.ktor.utils.io.z r1 = (io.ktor.utils.io.z) r1
                kotlin.p.b(r10)     // Catch:{ all -> 0x0025 }
                r4 = r9
                goto L_0x0079
            L_0x0025:
                r0 = move-exception
                r3 = r9
                goto L_0x007c
            L_0x0028:
                kotlin.p.b(r10)
                io.ktor.utils.io.z r1 = r9.p$
                io.ktor.utils.io.pool.d r2 = r9.$pool
                java.lang.Object r2 = r2.p0()
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                r3 = r9
            L_0x0036:
                r2.clear()     // Catch:{ all -> 0x007b }
                java.io.InputStream r4 = r3.$this_toByteReadChannel     // Catch:{ all -> 0x007b }
                byte[] r5 = r2.array()     // Catch:{ all -> 0x007b }
                int r6 = r2.arrayOffset()     // Catch:{ all -> 0x007b }
                int r7 = r2.position()     // Catch:{ all -> 0x007b }
                int r6 = r6 + r7
                int r7 = r2.remaining()     // Catch:{ all -> 0x007b }
                int r4 = r4.read(r5, r6, r7)     // Catch:{ all -> 0x007b }
                if (r4 >= 0) goto L_0x0054
                goto L_0x0084
            L_0x0054:
                if (r4 != 0) goto L_0x0057
                goto L_0x0036
            L_0x0057:
                int r5 = r2.position()     // Catch:{ all -> 0x007b }
                int r5 = r5 + r4
                r2.position(r5)     // Catch:{ all -> 0x007b }
                r2.flip()     // Catch:{ all -> 0x007b }
                io.ktor.utils.io.l r5 = r1.getChannel()     // Catch:{ all -> 0x007b }
                r3.L$0 = r1     // Catch:{ all -> 0x007b }
                r3.L$1 = r2     // Catch:{ all -> 0x007b }
                r3.I$0 = r4     // Catch:{ all -> 0x007b }
                r6 = 1
                r3.label = r6     // Catch:{ all -> 0x007b }
                java.lang.Object r5 = r5.h(r2, r3)     // Catch:{ all -> 0x007b }
                if (r5 != r0) goto L_0x0076
                return r0
            L_0x0076:
                r8 = r4
                r4 = r3
                r3 = r8
            L_0x0079:
                r3 = r4
                goto L_0x0036
            L_0x007b:
                r0 = move-exception
            L_0x007c:
                io.ktor.utils.io.l r4 = r1.getChannel()     // Catch:{ all -> 0x0093 }
                r4.d(r0)     // Catch:{ all -> 0x0093 }
            L_0x0084:
                io.ktor.utils.io.pool.d r0 = r3.$pool
                r0.N0(r2)
                java.io.InputStream r0 = r3.$this_toByteReadChannel
                r0.close()
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0093:
                r0 = move-exception
                io.ktor.utils.io.pool.d r4 = r3.$pool
                r4.N0(r2)
                java.io.InputStream r4 = r3.$this_toByteReadChannel
                r4.close()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.jvm.javaio.d.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @NotNull
    public static final i a(@NotNull InputStream $this$toByteReadChannel, @NotNull g context, @NotNull io.ktor.utils.io.pool.d<ByteBuffer> pool) {
        k.f($this$toByteReadChannel, "$this$toByteReadChannel");
        k.f(context, "context");
        k.f(pool, "pool");
        return q.e(s1.c, context, true, new a($this$toByteReadChannel, pool, (kotlin.coroutines.d) null)).getChannel();
    }
}
