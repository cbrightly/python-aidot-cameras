package io.ktor.utils.io;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.ktor.utils.io.a;
import io.ktor.utils.io.internal.l;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteReadChannelJVM.kt */
public final class j {

    @f(c = "io.ktor.utils.io.ByteReadChannelJVMKt", f = "ByteReadChannelJVM.kt", l = {293, 296}, m = "copyToImpl")
    /* compiled from: ByteReadChannelJVM.kt */
    public static final class a extends d {
        int I$0;
        int I$1;
        long J$0;
        long J$1;
        long J$2;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return j.b((i) null, (l) null, 0, this);
        }
    }

    @f(c = "io.ktor.utils.io.ByteReadChannelJVMKt", f = "ByteReadChannelJVM.kt", l = {255}, m = "joinToImplSuspend")
    /* compiled from: ByteReadChannelJVM.kt */
    public static final class b extends d {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        b(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return j.d((i) null, (l) null, false, this);
        }
    }

    @Nullable
    public static final Object c(@NotNull i $this$joinTo, @NotNull l dst, boolean closeOnEnd, @NotNull kotlin.coroutines.d<? super x> $completion) {
        if (!(dst != $this$joinTo)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        } else if (!($this$joinTo instanceof a) || !(dst instanceof a)) {
            Object d = d($this$joinTo, dst, closeOnEnd, $completion);
            return d == c.d() ? d : x.a;
        } else {
            Object p0 = ((a) dst).p0((a) $this$joinTo, closeOnEnd, $completion);
            return p0 == c.d() ? p0 : x.a;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: io.ktor.utils.io.l} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object d(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r6, @org.jetbrains.annotations.NotNull io.ktor.utils.io.l r7, boolean r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r9) {
        /*
            boolean r0 = r9 instanceof io.ktor.utils.io.j.b
            if (r0 == 0) goto L_0x0013
            r0 = r9
            io.ktor.utils.io.j$b r0 = (io.ktor.utils.io.j.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.j$b r0 = new io.ktor.utils.io.j$b
            r0.<init>(r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            boolean r8 = r0.Z$0
            java.lang.Object r2 = r0.L$1
            r7 = r2
            io.ktor.utils.io.l r7 = (io.ktor.utils.io.l) r7
            java.lang.Object r2 = r0.L$0
            r6 = r2
            io.ktor.utils.io.i r6 = (io.ktor.utils.io.i) r6
            kotlin.p.b(r1)
            goto L_0x0053
        L_0x003b:
            kotlin.p.b(r1)
            r3 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r0.L$0 = r6
            r0.L$1 = r7
            r0.Z$0 = r8
            r5 = 1
            r0.label = r5
            java.lang.Object r3 = a(r6, r7, r3, r0)
            if (r3 != r2) goto L_0x0053
            return r2
        L_0x0053:
            if (r8 == 0) goto L_0x0059
            io.ktor.utils.io.m.a(r7)
            goto L_0x005c
        L_0x0059:
            r7.flush()
        L_0x005c:
            kotlin.x r2 = kotlin.x.a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.j.d(io.ktor.utils.io.i, io.ktor.utils.io.l, boolean, kotlin.coroutines.d):java.lang.Object");
    }

    @Nullable
    public static final Object a(@NotNull i $this$copyTo, @NotNull l dst, long limit, @NotNull kotlin.coroutines.d<? super Long> $completion) {
        boolean z = true;
        if ($this$copyTo != dst) {
            if (limit < 0) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            } else if (($this$copyTo instanceof a) && (dst instanceof a)) {
                return ((a) dst).e0((a) $this$copyTo, limit, (a.c) null, $completion);
            } else {
                if (!($this$copyTo instanceof h) || !(dst instanceof h)) {
                    return b($this$copyTo, dst, limit, $completion);
                }
                return l.a((h) $this$copyTo, (h) dst, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, $completion);
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v13, resolved type: io.ktor.utils.io.core.a0} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v17, resolved type: io.ktor.utils.io.core.a0} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00ca A[SYNTHETIC, Splitter:B:25:0x00ca] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object b(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r20, @org.jetbrains.annotations.NotNull io.ktor.utils.io.l r21, long r22, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r24) {
        /*
            r1 = r24
            boolean r0 = r1 instanceof io.ktor.utils.io.j.a
            if (r0 == 0) goto L_0x0015
            r0 = r1
            io.ktor.utils.io.j$a r0 = (io.ktor.utils.io.j.a) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0015
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001a
        L_0x0015:
            io.ktor.utils.io.j$a r0 = new io.ktor.utils.io.j$a
            r0.<init>(r1)
        L_0x001a:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r2.label
            r6 = 0
            r7 = 0
            r9 = 0
            switch(r4) {
                case 0: goto L_0x0095;
                case 1: goto L_0x0065;
                case 2: goto L_0x0033;
                default: goto L_0x002a;
            }
        L_0x002a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0033:
            r10 = r7
            r4 = r9
            r9 = r6
            r12 = r7
            int r6 = r2.I$1
            long r10 = r2.J$2
            long r12 = r2.J$1
            int r9 = r2.I$0
            java.lang.Object r14 = r2.L$2
            r4 = r14
            io.ktor.utils.io.core.a0 r4 = (io.ktor.utils.io.core.a0) r4
            long r14 = r2.J$0
            java.lang.Object r5 = r2.L$1
            io.ktor.utils.io.l r5 = (io.ktor.utils.io.l) r5
            java.lang.Object r7 = r2.L$0
            io.ktor.utils.io.i r7 = (io.ktor.utils.io.i) r7
            kotlin.p.b(r3)     // Catch:{ all -> 0x0062 }
            r8 = r3
            r17 = r5
            r5 = r1
            r1 = r7
            r7 = r2
            r2 = r17
            r18 = r9
            r9 = r4
            r3 = r14
            r14 = r12
            r12 = r18
            goto L_0x014c
        L_0x0062:
            r0 = move-exception
            goto L_0x0182
        L_0x0065:
            r4 = 0
            r7 = r4
            r10 = r4
            long r4 = r2.J$2
            long r7 = r2.J$1
            int r6 = r2.I$0
            java.lang.Object r10 = r2.L$2
            r9 = r10
            io.ktor.utils.io.core.a0 r9 = (io.ktor.utils.io.core.a0) r9
            long r14 = r2.J$0
            java.lang.Object r10 = r2.L$1
            io.ktor.utils.io.l r10 = (io.ktor.utils.io.l) r10
            java.lang.Object r11 = r2.L$0
            io.ktor.utils.io.i r11 = (io.ktor.utils.io.i) r11
            kotlin.p.b(r3)     // Catch:{ all -> 0x008e }
            r20 = r3
            r12 = r4
            r4 = r9
            r5 = r10
            r9 = r6
            r17 = r7
            r6 = r14
            r14 = r17
            goto L_0x00fd
        L_0x008e:
            r0 = move-exception
            r4 = r9
            r5 = r10
            r7 = r11
            r9 = r6
            goto L_0x0182
        L_0x0095:
            kotlin.p.b(r3)
            io.ktor.utils.io.core.a0$c r4 = io.ktor.utils.io.core.a0.I4
            io.ktor.utils.io.pool.d r4 = r4.b()
            java.lang.Object r4 = r4.p0()
            io.ktor.utils.io.core.a0 r4 = (io.ktor.utils.io.core.a0) r4
            boolean r5 = r21.A()
            r6 = 1
            r5 = r5 ^ r6
            r6 = 0
            r8 = r4
            r11 = r5
            r9 = r6
            r5 = r1
            r6 = r2
            r7 = r3
            r1 = r20
            r2 = r21
            r3 = r22
        L_0x00b9:
            long r12 = r3 - r9
            r14 = 0
            int r16 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r16 != 0) goto L_0x00ca
            r14 = r3
            r3 = r7
            r4 = r8
            r7 = r1
            r1 = r5
            r5 = r2
            r2 = r6
            goto L_0x010f
        L_0x00ca:
            int r14 = r8.l()     // Catch:{ all -> 0x0179 }
            long r14 = (long) r14     // Catch:{ all -> 0x0179 }
            long r14 = java.lang.Math.min(r14, r12)     // Catch:{ all -> 0x0179 }
            int r14 = (int) r14     // Catch:{ all -> 0x0179 }
            r8.P(r14)     // Catch:{ all -> 0x0179 }
            r6.L$0 = r1     // Catch:{ all -> 0x0179 }
            r6.L$1 = r2     // Catch:{ all -> 0x0179 }
            r6.J$0 = r3     // Catch:{ all -> 0x0179 }
            r6.L$2 = r8     // Catch:{ all -> 0x0179 }
            r6.I$0 = r11     // Catch:{ all -> 0x0179 }
            r6.J$1 = r9     // Catch:{ all -> 0x0179 }
            r6.J$2 = r12     // Catch:{ all -> 0x0179 }
            r14 = 1
            r6.label = r14     // Catch:{ all -> 0x0179 }
            java.lang.Object r15 = r1.r(r8, r6)     // Catch:{ all -> 0x0179 }
            if (r15 != r0) goto L_0x00ef
            return r0
        L_0x00ef:
            r20 = r7
            r17 = r11
            r11 = r1
            r1 = r5
            r5 = r2
            r2 = r6
            r6 = r3
            r4 = r8
            r3 = r15
            r14 = r9
            r9 = r17
        L_0x00fd:
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ all -> 0x0173 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x0173 }
            r8 = -1
            if (r3 != r8) goto L_0x0121
            r3 = r20
            r17 = r11
            r11 = r9
            r9 = r14
            r14 = r6
            r7 = r17
        L_0x010f:
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r9)     // Catch:{ all -> 0x011d }
            io.ktor.utils.io.core.a0$c r6 = io.ktor.utils.io.core.a0.I4
            io.ktor.utils.io.pool.d r6 = r6.b()
            r4.e1(r6)
            return r0
        L_0x011d:
            r0 = move-exception
            r9 = r11
            goto L_0x0182
        L_0x0121:
            r2.L$0 = r11     // Catch:{ all -> 0x0173 }
            r2.L$1 = r5     // Catch:{ all -> 0x0173 }
            r2.J$0 = r6     // Catch:{ all -> 0x0173 }
            r2.L$2 = r4     // Catch:{ all -> 0x0173 }
            r2.I$0 = r9     // Catch:{ all -> 0x0173 }
            r2.J$1 = r14     // Catch:{ all -> 0x0173 }
            r2.J$2 = r12     // Catch:{ all -> 0x0173 }
            r2.I$1 = r3     // Catch:{ all -> 0x0173 }
            r8 = 2
            r2.label = r8     // Catch:{ all -> 0x0173 }
            java.lang.Object r8 = r5.m(r4, r2)     // Catch:{ all -> 0x0173 }
            if (r8 != r0) goto L_0x013b
            return r0
        L_0x013b:
            r8 = r20
            r17 = r5
            r5 = r1
            r1 = r11
            r10 = r12
            r12 = r9
            r9 = r4
            r18 = r6
            r7 = r2
            r6 = r3
            r2 = r17
            r3 = r18
        L_0x014c:
            r20 = r3
            long r3 = (long) r6
            long r3 = r3 + r14
            if (r12 == 0) goto L_0x016a
            int r13 = r1.e()     // Catch:{ all -> 0x015c }
            if (r13 != 0) goto L_0x016a
            r2.flush()     // Catch:{ all -> 0x015c }
            goto L_0x016a
        L_0x015c:
            r0 = move-exception
            r14 = r20
            r3 = r8
            r4 = r9
            r9 = r12
            r17 = r7
            r7 = r1
            r1 = r5
            r5 = r2
            r2 = r17
            goto L_0x0182
        L_0x016a:
            r6 = r7
            r7 = r8
            r8 = r9
            r11 = r12
            r9 = r3
            r3 = r20
            goto L_0x00b9
        L_0x0173:
            r0 = move-exception
            r3 = r20
            r14 = r6
            r7 = r11
            goto L_0x0182
        L_0x0179:
            r0 = move-exception
            r14 = r3
            r3 = r7
            r4 = r8
            r9 = r11
            r7 = r1
            r1 = r5
            r5 = r2
            r2 = r6
        L_0x0182:
            r5.d(r0)     // Catch:{ all -> 0x0187 }
            throw r0     // Catch:{ all -> 0x0187 }
        L_0x0187:
            r0 = move-exception
            io.ktor.utils.io.core.a0$c r6 = io.ktor.utils.io.core.a0.I4
            io.ktor.utils.io.pool.d r6 = r6.b()
            r4.e1(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.j.b(io.ktor.utils.io.i, io.ktor.utils.io.l, long, kotlin.coroutines.d):java.lang.Object");
    }
}
