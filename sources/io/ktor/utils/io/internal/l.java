package io.ktor.utils.io.internal;

import io.ktor.utils.io.h;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SequentialCopyTo.kt */
public final class l {

    @f(c = "io.ktor.utils.io.internal.SequentialCopyToKt", f = "SequentialCopyTo.kt", l = {23, 27, 62}, m = "copyToSequentialImpl")
    /* compiled from: SequentialCopyTo.kt */
    public static final class a extends d {
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
            return l.a((h) null, (h) null, 0, this);
        }
    }

    @f(c = "io.ktor.utils.io.internal.SequentialCopyToKt", f = "SequentialCopyTo.kt", l = {47, 53}, m = "copyToTail")
    /* compiled from: SequentialCopyTo.kt */
    public static final class b extends d {
        int I$0;
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        b(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return l.b((h) null, (h) null, 0, this);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0098, code lost:
        r3.L$0 = r4;
        r3.L$1 = r5;
        r3.J$0 = r0;
        r3.J$1 = r10;
        r3.label = 1;
        r13 = r4.I(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00a8, code lost:
        if (r13 != r7) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00aa, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ab, code lost:
        r14 = r0;
        r0 = r2;
        r1 = r3;
        r3 = r7;
        r2 = r13;
        r7 = r5;
        r5 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b8, code lost:
        if (((java.lang.Boolean) r2).booleanValue() != false) goto L_0x00bb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00bb, code lost:
        r12 = r5.i0(r7, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00c1, code lost:
        if (r12 != r8) goto L_0x00ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00c3, code lost:
        r1.L$0 = r5;
        r1.L$1 = r7;
        r1.J$0 = r14;
        r1.J$1 = r10;
        r1.J$2 = r12;
        r1.label = 2;
        r2 = b(r5, r7, r10, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00d4, code lost:
        if (r2 != r3) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00d6, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00d7, code lost:
        r18 = r12;
        r13 = r5;
        r12 = r7;
        r4 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00dd, code lost:
        r16 = ((java.lang.Number) r2).longValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00e5, code lost:
        if (r16 != r8) goto L_0x00f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00e7, code lost:
        r7 = r12;
        r5 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00ef, code lost:
        return kotlin.coroutines.jvm.internal.b.d(r14 - r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00f0, code lost:
        r8 = r3;
        r7 = r12;
        r3 = r0;
        r18 = r4;
        r4 = r1;
        r5 = r13;
        r0 = r14;
        r14 = r16;
        r12 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0103, code lost:
        if (r7.R() != 0) goto L_0x0183;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0105, code lost:
        r4 = r7.S();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0118, code lost:
        if (r4.b().invoke().booleanValue() == false) goto L_0x011f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x011a, code lost:
        r20 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x011f, code lost:
        r1.L$0 = r5;
        r1.L$1 = r7;
        r1.J$0 = r14;
        r1.J$1 = r10;
        r1.J$2 = r12;
        r1.L$2 = r4;
        r1.label = 3;
        r8 = r1;
        r20 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x013d, code lost:
        if (io.ktor.utils.io.p.a().compareAndSet(r4, (java.lang.Object) null, r8) == false) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x014d, code lost:
        if (r4.b().invoke().booleanValue() == false) goto L_0x015c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0157, code lost:
        if (io.ktor.utils.io.p.a().compareAndSet(r4, r8, (java.lang.Object) null) == false) goto L_0x015c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0159, code lost:
        r0 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x015c, code lost:
        r0 = kotlin.coroutines.intrinsics.c.d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0164, code lost:
        if (r0 != kotlin.coroutines.intrinsics.c.d()) goto L_0x0169;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0166, code lost:
        kotlin.coroutines.jvm.internal.h.c(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0169, code lost:
        if (r0 != r3) goto L_0x016c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x016b, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x016c, code lost:
        r0 = r20;
        r8 = r7;
        r18 = r10;
        r10 = r12;
        r12 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0176, code lost:
        r7 = r8;
        r18 = r10;
        r10 = r12;
        r12 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0182, code lost:
        throw new java.lang.IllegalStateException();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0183, code lost:
        r20 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0186, code lost:
        r0 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0188, code lost:
        r4 = r1;
        r8 = r3;
        r3 = r0;
        r0 = r14;
        r14 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x018d, code lost:
        r10 = r10 - r14;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r7;
        r7 = r8;
        r8 = 0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object a(@org.jetbrains.annotations.NotNull io.ktor.utils.io.h r20, @org.jetbrains.annotations.NotNull io.ktor.utils.io.h r21, long r22, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r24) {
        /*
            r0 = r24
            boolean r1 = r0 instanceof io.ktor.utils.io.internal.l.a
            if (r1 == 0) goto L_0x0015
            r1 = r0
            io.ktor.utils.io.internal.l$a r1 = (io.ktor.utils.io.internal.l.a) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0015
            int r2 = r2 - r3
            r1.label = r2
            goto L_0x001a
        L_0x0015:
            io.ktor.utils.io.internal.l$a r1 = new io.ktor.utils.io.internal.l$a
            r1.<init>(r0)
        L_0x001a:
            java.lang.Object r2 = r1.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r1.label
            r6 = 0
            r7 = 0
            r8 = 0
            switch(r4) {
                case 0: goto L_0x0083;
                case 1: goto L_0x0069;
                case 2: goto L_0x0051;
                case 3: goto L_0x0032;
                default: goto L_0x0029;
            }
        L_0x0029:
            r3 = r2
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x0032:
            r10 = r8
            r12 = r8
            r4 = r7
            java.lang.Object r14 = r1.L$2
            r4 = r14
            io.ktor.utils.io.p r4 = (io.ktor.utils.io.p) r4
            long r10 = r1.J$2
            long r12 = r1.J$1
            long r14 = r1.J$0
            java.lang.Object r7 = r1.L$1
            io.ktor.utils.io.h r7 = (io.ktor.utils.io.h) r7
            java.lang.Object r5 = r1.L$0
            io.ktor.utils.io.h r5 = (io.ktor.utils.io.h) r5
            kotlin.p.b(r2)
            r8 = r7
            r7 = r6
            r6 = r2
            r2 = 0
            goto L_0x0176
        L_0x0051:
            r4 = r8
            r6 = r8
            long r4 = r1.J$2
            long r6 = r1.J$1
            long r10 = r1.J$0
            java.lang.Object r12 = r1.L$1
            io.ktor.utils.io.h r12 = (io.ktor.utils.io.h) r12
            java.lang.Object r13 = r1.L$0
            io.ktor.utils.io.h r13 = (io.ktor.utils.io.h) r13
            kotlin.p.b(r2)
            r14 = r10
            r10 = r6
            r6 = r2
            goto L_0x00dd
        L_0x0069:
            r4 = r8
            long r4 = r1.J$1
            long r6 = r1.J$0
            java.lang.Object r10 = r1.L$1
            io.ktor.utils.io.h r10 = (io.ktor.utils.io.h) r10
            java.lang.Object r11 = r1.L$0
            io.ktor.utils.io.h r11 = (io.ktor.utils.io.h) r11
            kotlin.p.b(r2)
            r14 = r6
            r7 = r10
            r12 = 1
            r6 = r2
            r18 = r4
            r5 = r11
            r10 = r18
            goto L_0x00b2
        L_0x0083:
            kotlin.p.b(r2)
            r4 = r20
            r5 = r21
            if (r4 == r5) goto L_0x008d
            r6 = 1
        L_0x008d:
            if (r6 == 0) goto L_0x0198
            r6 = r22
            r10 = r6
            r6 = r2
            r7 = r3
            r2 = r0
            r3 = r1
            r0 = r22
        L_0x0098:
            r3.L$0 = r4
            r3.L$1 = r5
            r3.J$0 = r0
            r3.J$1 = r10
            r12 = 1
            r3.label = r12
            java.lang.Object r13 = r4.I(r3)
            if (r13 != r7) goto L_0x00ab
            return r7
        L_0x00ab:
            r14 = r0
            r0 = r2
            r1 = r3
            r3 = r7
            r2 = r13
            r7 = r5
            r5 = r4
        L_0x00b2:
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 != 0) goto L_0x00bb
            goto L_0x00e9
        L_0x00bb:
            long r12 = r5.i0(r7, r10)
            int r2 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1))
            if (r2 != 0) goto L_0x00ff
            r1.L$0 = r5
            r1.L$1 = r7
            r1.J$0 = r14
            r1.J$1 = r10
            r1.J$2 = r12
            r2 = 2
            r1.label = r2
            java.lang.Object r2 = b(r5, r7, r10, r1)
            if (r2 != r3) goto L_0x00d7
            return r3
        L_0x00d7:
            r18 = r12
            r13 = r5
            r12 = r7
            r4 = r18
        L_0x00dd:
            java.lang.Number r2 = (java.lang.Number) r2
            long r16 = r2.longValue()
            int r2 = (r16 > r8 ? 1 : (r16 == r8 ? 0 : -1))
            if (r2 != 0) goto L_0x00f0
            r7 = r12
            r5 = r13
        L_0x00e9:
            long r2 = r14 - r10
            java.lang.Long r2 = kotlin.coroutines.jvm.internal.b.d(r2)
            return r2
        L_0x00f0:
            r8 = r3
            r7 = r12
            r2 = 0
            r3 = r0
            r18 = r4
            r4 = r1
            r5 = r13
            r0 = r14
            r14 = r16
            r12 = r18
            goto L_0x018d
        L_0x00ff:
            int r2 = r7.R()
            if (r2 != 0) goto L_0x0183
            io.ktor.utils.io.p r4 = r7.S()
            r2 = 0
            kotlin.jvm.functions.a r16 = r4.b()
            java.lang.Object r16 = r16.invoke()
            java.lang.Boolean r16 = (java.lang.Boolean) r16
            boolean r16 = r16.booleanValue()
            if (r16 == 0) goto L_0x011f
            r20 = r0
            r2 = 0
            goto L_0x0186
        L_0x011f:
            r1.L$0 = r5
            r1.L$1 = r7
            r1.J$0 = r14
            r1.J$1 = r10
            r1.J$2 = r12
            r1.L$2 = r4
            r8 = 3
            r1.label = r8
            r8 = r1
            r9 = 0
            r20 = r0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = io.ktor.utils.io.p.a
            r21 = r2
            r2 = 0
            boolean r0 = r0.compareAndSet(r4, r2, r8)
            if (r0 == 0) goto L_0x017d
            kotlin.jvm.functions.a r0 = r4.b()
            java.lang.Object r0 = r0.invoke()
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x015c
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = io.ktor.utils.io.p.a
            boolean r0 = r0.compareAndSet(r4, r8, r2)
            if (r0 == 0) goto L_0x015c
            kotlin.x r0 = kotlin.x.a
            goto L_0x0160
        L_0x015c:
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
        L_0x0160:
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            if (r0 != r8) goto L_0x0169
            kotlin.coroutines.jvm.internal.h.c(r1)
        L_0x0169:
            if (r0 != r3) goto L_0x016c
            return r3
        L_0x016c:
            r0 = r20
            r8 = r7
            r7 = r21
            r18 = r10
            r10 = r12
            r12 = r18
        L_0x0176:
            r7 = r8
            r18 = r10
            r10 = r12
            r12 = r18
            goto L_0x0188
        L_0x017d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r0.<init>()
            throw r0
        L_0x0183:
            r20 = r0
            r2 = 0
        L_0x0186:
            r0 = r20
        L_0x0188:
            r4 = r1
            r8 = r3
            r3 = r0
            r0 = r14
            r14 = r12
        L_0x018d:
            long r10 = r10 - r14
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r7
            r7 = r8
            r8 = 0
            goto L_0x0098
        L_0x0198:
            r3 = r2
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Failed requirement."
            java.lang.String r6 = r6.toString()
            r2.<init>(r6)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.internal.l.a(io.ktor.utils.io.h, io.ktor.utils.io.h, long, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: io.ktor.utils.io.core.internal.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: io.ktor.utils.io.h} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: io.ktor.utils.io.h} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v10, resolved type: io.ktor.utils.io.core.internal.a} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0090, code lost:
        r4 = ((java.lang.Number) r4).intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0097, code lost:
        if (r4 != -1) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0099, code lost:
        r2 = io.ktor.utils.io.core.internal.a.z4;
        r3.e1(r2.c());
        r5 = kotlin.coroutines.jvm.internal.b.d(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a8, code lost:
        r3.e1(r2.c());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00af, code lost:
        return r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r0.L$0 = r8;
        r0.L$1 = r9;
        r0.J$0 = r10;
        r0.L$2 = r3;
        r0.I$0 = r4;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c1, code lost:
        if (r9.j0(r3, r0) != r2) goto L_0x00c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c3, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c4, code lost:
        r2 = r3;
        r3 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r4 = kotlin.coroutines.jvm.internal.b.d((long) r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00cb, code lost:
        r2.e1(io.ktor.utils.io.core.internal.a.z4.c());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00d4, code lost:
        return r4;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object b(@org.jetbrains.annotations.NotNull io.ktor.utils.io.h r8, @org.jetbrains.annotations.NotNull io.ktor.utils.io.h r9, long r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r12) {
        /*
            boolean r0 = r12 instanceof io.ktor.utils.io.internal.l.b
            if (r0 == 0) goto L_0x0013
            r0 = r12
            io.ktor.utils.io.internal.l$b r0 = (io.ktor.utils.io.internal.l.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.internal.l$b r0 = new io.ktor.utils.io.internal.l$b
            r0.<init>(r12)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0061;
                case 1: goto L_0x004a;
                case 2: goto L_0x002d;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002d:
            r2 = r4
            r3 = 0
            int r3 = r0.I$0
            java.lang.Object r4 = r0.L$2
            r2 = r4
            io.ktor.utils.io.core.internal.a r2 = (io.ktor.utils.io.core.internal.a) r2
            long r10 = r0.J$0
            java.lang.Object r4 = r0.L$1
            r9 = r4
            io.ktor.utils.io.h r9 = (io.ktor.utils.io.h) r9
            java.lang.Object r4 = r0.L$0
            r8 = r4
            io.ktor.utils.io.h r8 = (io.ktor.utils.io.h) r8
            kotlin.p.b(r1)     // Catch:{ all -> 0x0047 }
            goto L_0x00c6
        L_0x0047:
            r3 = move-exception
            goto L_0x00d9
        L_0x004a:
            r3 = r4
            java.lang.Object r4 = r0.L$2
            r3 = r4
            io.ktor.utils.io.core.internal.a r3 = (io.ktor.utils.io.core.internal.a) r3
            long r10 = r0.J$0
            java.lang.Object r4 = r0.L$1
            r9 = r4
            io.ktor.utils.io.h r9 = (io.ktor.utils.io.h) r9
            java.lang.Object r4 = r0.L$0
            r8 = r4
            io.ktor.utils.io.h r8 = (io.ktor.utils.io.h) r8
            kotlin.p.b(r1)     // Catch:{ all -> 0x00d5 }
            r4 = r1
            goto L_0x0090
        L_0x0061:
            kotlin.p.b(r1)
            io.ktor.utils.io.core.internal.a$f r3 = io.ktor.utils.io.core.internal.a.z4
            io.ktor.utils.io.pool.d r3 = r3.c()
            java.lang.Object r3 = r3.p0()
            io.ktor.utils.io.core.internal.a r3 = (io.ktor.utils.io.core.internal.a) r3
            int r4 = r3.l()     // Catch:{ all -> 0x00d5 }
            long r4 = (long) r4     // Catch:{ all -> 0x00d5 }
            long r4 = kotlin.ranges.n.f(r10, r4)     // Catch:{ all -> 0x00d5 }
            int r4 = (int) r4     // Catch:{ all -> 0x00d5 }
            r3.P(r4)     // Catch:{ all -> 0x00d5 }
            r0.L$0 = r8     // Catch:{ all -> 0x00d5 }
            r0.L$1 = r9     // Catch:{ all -> 0x00d5 }
            r0.J$0 = r10     // Catch:{ all -> 0x00d5 }
            r0.L$2 = r3     // Catch:{ all -> 0x00d5 }
            r4 = 1
            r0.label = r4     // Catch:{ all -> 0x00d5 }
            java.lang.Object r4 = r8.V(r3, r0)     // Catch:{ all -> 0x00d5 }
            if (r4 != r2) goto L_0x0090
            return r2
        L_0x0090:
            java.lang.Number r4 = (java.lang.Number) r4     // Catch:{ all -> 0x00d5 }
            int r4 = r4.intValue()     // Catch:{ all -> 0x00d5 }
            r5 = -1
            if (r4 != r5) goto L_0x00b0
            io.ktor.utils.io.core.internal.a$f r2 = io.ktor.utils.io.core.internal.a.z4     // Catch:{ all -> 0x00d5 }
            io.ktor.utils.io.pool.d r5 = r2.c()     // Catch:{ all -> 0x00d5 }
            r3.e1(r5)     // Catch:{ all -> 0x00d5 }
            r5 = 0
            java.lang.Long r5 = kotlin.coroutines.jvm.internal.b.d(r5)     // Catch:{ all -> 0x00d5 }
            io.ktor.utils.io.pool.d r2 = r2.c()
            r3.e1(r2)
            return r5
        L_0x00b0:
            r0.L$0 = r8     // Catch:{ all -> 0x00d5 }
            r0.L$1 = r9     // Catch:{ all -> 0x00d5 }
            r0.J$0 = r10     // Catch:{ all -> 0x00d5 }
            r0.L$2 = r3     // Catch:{ all -> 0x00d5 }
            r0.I$0 = r4     // Catch:{ all -> 0x00d5 }
            r5 = 2
            r0.label = r5     // Catch:{ all -> 0x00d5 }
            java.lang.Object r5 = r9.j0(r3, r0)     // Catch:{ all -> 0x00d5 }
            if (r5 != r2) goto L_0x00c4
            return r2
        L_0x00c4:
            r2 = r3
            r3 = r4
        L_0x00c6:
            long r4 = (long) r3
            java.lang.Long r4 = kotlin.coroutines.jvm.internal.b.d(r4)     // Catch:{ all -> 0x0047 }
            io.ktor.utils.io.core.internal.a$f r5 = io.ktor.utils.io.core.internal.a.z4
            io.ktor.utils.io.pool.d r5 = r5.c()
            r2.e1(r5)
            return r4
        L_0x00d5:
            r2 = move-exception
            r7 = r3
            r3 = r2
            r2 = r7
        L_0x00d9:
            io.ktor.utils.io.core.internal.a$f r4 = io.ktor.utils.io.core.internal.a.z4
            io.ktor.utils.io.pool.d r4 = r4.c()
            r2.e1(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.internal.l.b(io.ktor.utils.io.h, io.ktor.utils.io.h, long, kotlin.coroutines.d):java.lang.Object");
    }
}
