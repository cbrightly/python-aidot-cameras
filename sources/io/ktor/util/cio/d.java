package io.ktor.util.cio;

import io.ktor.utils.io.i;
import kotlin.coroutines.jvm.internal.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Readers.kt */
public final class d {

    @f(c = "io.ktor.util.cio.ReadersKt", f = "Readers.kt", l = {15}, m = "toByteArray")
    /* compiled from: Readers.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        int I$0;
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
            return d.a((i) null, 0, this);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object a(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r7, int r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super byte[]> r9) {
        /*
            boolean r0 = r9 instanceof io.ktor.util.cio.d.a
            if (r0 == 0) goto L_0x0013
            r0 = r9
            io.ktor.util.cio.d$a r0 = (io.ktor.util.cio.d.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.util.cio.d$a r0 = new io.ktor.util.cio.d$a
            r0.<init>(r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            int r8 = r0.I$0
            java.lang.Object r2 = r0.L$0
            r7 = r2
            io.ktor.utils.io.i r7 = (io.ktor.utils.io.i) r7
            kotlin.p.b(r1)
            r3 = r1
            goto L_0x0049
        L_0x0038:
            kotlin.p.b(r1)
            long r5 = (long) r8
            r0.L$0 = r7
            r0.I$0 = r8
            r0.label = r4
            java.lang.Object r3 = io.ktor.utils.io.k.f(r7, r5, r0)
            if (r3 != r2) goto L_0x0049
            return r2
        L_0x0049:
            io.ktor.utils.io.core.q r3 = (io.ktor.utils.io.core.q) r3
            r2 = 0
            r5 = 0
            byte[] r2 = io.ktor.utils.io.core.g0.c(r3, r2, r4, r5)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.util.cio.d.a(io.ktor.utils.io.i, int, kotlin.coroutines.d):java.lang.Object");
    }

    public static /* synthetic */ Object b(i iVar, int i, kotlin.coroutines.d dVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = Integer.MAX_VALUE;
        }
        return a(iVar, i, dVar);
    }
}
