package io.ktor.utils.io;

import io.ktor.utils.io.internal.n;
import java.io.IOException;
import java.nio.ByteBuffer;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Delimited.kt */
public final class r {

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.DelimitedKt", f = "Delimited.kt", l = {38}, m = "readUntilDelimiter")
    /* compiled from: Delimited.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return r.d((i) null, (ByteBuffer) null, (ByteBuffer) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.DelimitedKt", f = "Delimited.kt", l = {67, 97}, m = "readUntilDelimiterSuspend")
    /* compiled from: Delimited.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        int I$1;
        int I$2;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        c(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return r.e((i) null, (ByteBuffer) null, (ByteBuffer) null, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.DelimitedKt", f = "Delimited.kt", l = {51}, m = "skipDelimiter")
    /* compiled from: Delimited.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        e(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return r.f((i) null, (ByteBuffer) null, this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: kotlin.jvm.internal.x} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object d(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r7, @org.jetbrains.annotations.NotNull java.nio.ByteBuffer r8, @org.jetbrains.annotations.NotNull java.nio.ByteBuffer r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r10) {
        /*
            boolean r0 = r10 instanceof io.ktor.utils.io.r.a
            if (r0 == 0) goto L_0x0013
            r0 = r10
            io.ktor.utils.io.r$a r0 = (io.ktor.utils.io.r.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.r$a r0 = new io.ktor.utils.io.r$a
            r0.<init>(r10)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004c;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r2 = 0
            r3 = r2
            java.lang.Object r4 = r0.L$4
            r3 = r4
            kotlin.jvm.internal.w r3 = (kotlin.jvm.internal.w) r3
            java.lang.Object r4 = r0.L$3
            r2 = r4
            kotlin.jvm.internal.x r2 = (kotlin.jvm.internal.x) r2
            java.lang.Object r4 = r0.L$2
            r9 = r4
            java.nio.ByteBuffer r9 = (java.nio.ByteBuffer) r9
            java.lang.Object r4 = r0.L$1
            r8 = r4
            java.nio.ByteBuffer r8 = (java.nio.ByteBuffer) r8
            java.lang.Object r4 = r0.L$0
            r7 = r4
            io.ktor.utils.io.i r7 = (io.ktor.utils.io.i) r7
            kotlin.p.b(r1)
            r5 = r3
            r3 = r1
            goto L_0x00a4
        L_0x004c:
            kotlin.p.b(r1)
            boolean r3 = r8.hasRemaining()
            java.lang.String r4 = "Failed requirement."
            if (r3 == 0) goto L_0x00bf
            r3 = 1
            r5 = 0
            if (r8 == r9) goto L_0x005d
            r6 = r3
            goto L_0x005e
        L_0x005d:
            r6 = r5
        L_0x005e:
            if (r6 == 0) goto L_0x00b5
            kotlin.jvm.internal.x r4 = new kotlin.jvm.internal.x
            r4.<init>()
            r4.element = r5
            kotlin.jvm.internal.w r6 = new kotlin.jvm.internal.w
            r6.<init>()
            r6.element = r5
            r5 = r6
            io.ktor.utils.io.r$b r6 = new io.ktor.utils.io.r$b
            r6.<init>(r8, r9, r5, r4)
            r7.u(r6)
            int r6 = r4.element
            if (r6 != 0) goto L_0x0083
            boolean r6 = r7.y()
            if (r6 == 0) goto L_0x0083
            r2 = -1
            goto L_0x00af
        L_0x0083:
            boolean r6 = r9.hasRemaining()
            if (r6 == 0) goto L_0x00ad
            boolean r6 = r5.element
            if (r6 == 0) goto L_0x008e
            goto L_0x00ad
        L_0x008e:
            int r6 = r4.element
            r0.L$0 = r7
            r0.L$1 = r8
            r0.L$2 = r9
            r0.L$3 = r4
            r0.L$4 = r5
            r0.label = r3
            java.lang.Object r3 = e(r7, r8, r9, r6, r0)
            if (r3 != r2) goto L_0x00a3
            return r2
        L_0x00a3:
            r2 = r4
        L_0x00a4:
            java.lang.Number r3 = (java.lang.Number) r3
            int r3 = r3.intValue()
            r4 = r2
            r2 = r3
            goto L_0x00af
        L_0x00ad:
            int r2 = r4.element
        L_0x00af:
            java.lang.Integer r2 = kotlin.coroutines.jvm.internal.b.c(r2)
            return r2
        L_0x00b5:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = r4.toString()
            r2.<init>(r3)
            throw r2
        L_0x00bf:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = r4.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.r.d(io.ktor.utils.io.i, java.nio.ByteBuffer, java.nio.ByteBuffer, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: Delimited.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<s, x> {
        final /* synthetic */ kotlin.jvm.internal.x $copied;
        final /* synthetic */ ByteBuffer $delimiter;
        final /* synthetic */ ByteBuffer $dst;
        final /* synthetic */ w $endFound;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, w wVar, kotlin.jvm.internal.x xVar) {
            super(1);
            this.$delimiter = byteBuffer;
            this.$dst = byteBuffer2;
            this.$endFound = wVar;
            this.$copied = xVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((s) obj);
            return x.a;
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0031 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:3:0x0011  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void invoke(@org.jetbrains.annotations.NotNull io.ktor.utils.io.s r5) {
            /*
                r4 = this;
                java.lang.String r0 = "$receiver"
                kotlin.jvm.internal.k.f(r5, r0)
            L_0x0005:
                java.nio.ByteBuffer r0 = r4.$delimiter
                java.nio.ByteBuffer r1 = r4.$dst
                int r0 = io.ktor.utils.io.r.i(r5, r0, r1)
                if (r0 != 0) goto L_0x0011
                goto L_0x0031
            L_0x0011:
                if (r0 >= 0) goto L_0x001a
                kotlin.jvm.internal.w r1 = r4.$endFound
                r2 = 1
                r1.element = r2
                int r1 = -r0
                goto L_0x001b
            L_0x001a:
                r1 = r0
            L_0x001b:
                kotlin.jvm.internal.x r2 = r4.$copied
                int r3 = r2.element
                int r3 = r3 + r1
                r2.element = r3
                java.nio.ByteBuffer r2 = r4.$dst
                boolean r2 = r2.hasRemaining()
                if (r2 == 0) goto L_0x0031
                kotlin.jvm.internal.w r2 = r4.$endFound
                boolean r2 = r2.element
                if (r2 == 0) goto L_0x0005
            L_0x0031:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.r.b.invoke(io.ktor.utils.io.s):void");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: kotlin.jvm.internal.w} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object f(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r5, @org.jetbrains.annotations.NotNull java.nio.ByteBuffer r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r7) {
        /*
            boolean r0 = r7 instanceof io.ktor.utils.io.r.e
            if (r0 == 0) goto L_0x0013
            r0 = r7
            io.ktor.utils.io.r$e r0 = (io.ktor.utils.io.r.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.utils.io.r$e r0 = new io.ktor.utils.io.r$e
            r0.<init>(r7)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003f;
                case 1: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r2 = 0
            java.lang.Object r3 = r0.L$2
            r2 = r3
            kotlin.jvm.internal.w r2 = (kotlin.jvm.internal.w) r2
            java.lang.Object r3 = r0.L$1
            r6 = r3
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            java.lang.Object r3 = r0.L$0
            r5 = r3
            io.ktor.utils.io.i r5 = (io.ktor.utils.io.i) r5
            kotlin.p.b(r1)
            goto L_0x006d
        L_0x003f:
            kotlin.p.b(r1)
            boolean r3 = r6.hasRemaining()
            if (r3 == 0) goto L_0x0071
            kotlin.jvm.internal.w r3 = new kotlin.jvm.internal.w
            r3.<init>()
            r4 = 0
            r3.element = r4
            io.ktor.utils.io.r$f r4 = new io.ktor.utils.io.r$f
            r4.<init>(r3, r6)
            r5.u(r4)
            boolean r4 = r3.element
            if (r4 != 0) goto L_0x006e
            r0.L$0 = r5
            r0.L$1 = r6
            r0.L$2 = r3
            r4 = 1
            r0.label = r4
            java.lang.Object r4 = g(r5, r6, r0)
            if (r4 != r2) goto L_0x006c
            return r2
        L_0x006c:
            r2 = r3
        L_0x006d:
            r3 = r2
        L_0x006e:
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x0071:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "Failed requirement."
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.r.f(io.ktor.utils.io.i, java.nio.ByteBuffer, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: Delimited.kt */
    public static final class f extends l implements kotlin.jvm.functions.l<s, x> {
        final /* synthetic */ ByteBuffer $delimiter;
        final /* synthetic */ w $found;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(w wVar, ByteBuffer byteBuffer) {
            super(1);
            this.$found = wVar;
            this.$delimiter = byteBuffer;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((s) obj);
            return x.a;
        }

        public final void invoke(@NotNull s $this$lookAhead) {
            k.f($this$lookAhead, "$receiver");
            this.$found.element = r.j($this$lookAhead, this.$delimiter) == this.$delimiter.remaining();
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.DelimitedKt$skipDelimiterSuspend$2", f = "Delimited.kt", l = {57}, m = "invokeSuspend")
    /* compiled from: Delimited.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.l implements p<t, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ ByteBuffer $delimiter;
        Object L$0;
        int label;
        private t p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(ByteBuffer byteBuffer, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$delimiter = byteBuffer;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            g gVar = new g(this.$delimiter, dVar);
            t tVar = (t) obj;
            gVar.p$ = (t) obj;
            return gVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((g) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: io.ktor.utils.io.t} */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r5) {
            /*
                r4 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r4.label
                switch(r1) {
                    case 0: goto L_0x001b;
                    case 1: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0011:
                r0 = 0
                java.lang.Object r1 = r4.L$0
                r0 = r1
                io.ktor.utils.io.t r0 = (io.ktor.utils.io.t) r0
                kotlin.p.b(r5)
                goto L_0x0033
            L_0x001b:
                kotlin.p.b(r5)
                io.ktor.utils.io.t r1 = r4.p$
                java.nio.ByteBuffer r2 = r4.$delimiter
                int r2 = r2.remaining()
                r4.L$0 = r1
                r3 = 1
                r4.label = r3
                java.lang.Object r2 = r1.k(r2, r4)
                if (r2 != r0) goto L_0x0032
                return r0
            L_0x0032:
                r0 = r1
            L_0x0033:
                java.nio.ByteBuffer r1 = r4.$delimiter
                int r1 = io.ktor.utils.io.r.j(r0, r1)
                java.nio.ByteBuffer r2 = r4.$delimiter
                int r2 = r2.remaining()
                if (r1 != r2) goto L_0x0044
                kotlin.x r1 = kotlin.x.a
                return r1
            L_0x0044:
                java.io.IOException r1 = new java.io.IOException
                java.lang.String r2 = "Broken delimiter occurred"
                r1.<init>(r2)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.r.g.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Nullable
    static final /* synthetic */ Object g(@NotNull i $this$skipDelimiterSuspend, @NotNull ByteBuffer delimiter, @NotNull kotlin.coroutines.d<? super x> $completion) {
        Object l = $this$skipDelimiterSuspend.l(new g(delimiter, (kotlin.coroutines.d) null), $completion);
        if (l == kotlin.coroutines.intrinsics.c.d()) {
            return l;
        }
        return x.a;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: kotlin.jvm.internal.w} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0071  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0030  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object e(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r19, @org.jetbrains.annotations.NotNull java.nio.ByteBuffer r20, @org.jetbrains.annotations.NotNull java.nio.ByteBuffer r21, int r22, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Integer> r23) {
        /*
            r7 = r19
            r8 = r20
            r9 = r21
            r10 = r22
            r11 = r23
            boolean r0 = r11 instanceof io.ktor.utils.io.r.c
            if (r0 == 0) goto L_0x001d
            r0 = r11
            io.ktor.utils.io.r$c r0 = (io.ktor.utils.io.r.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x001d
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0022
        L_0x001d:
            io.ktor.utils.io.r$c r0 = new io.ktor.utils.io.r$c
            r0.<init>(r11)
        L_0x0022:
            r12 = r0
            java.lang.Object r13 = r12.result
            java.lang.Object r14 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r12.label
            r15 = 0
            r1 = 0
            switch(r0) {
                case 0: goto L_0x0071;
                case 1: goto L_0x0058;
                case 2: goto L_0x0038;
                default: goto L_0x0030;
            }
        L_0x0030:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0038:
            r0 = r1
            r1 = r15
            int r2 = r12.I$2
            int r1 = r12.I$1
            java.lang.Object r3 = r12.L$3
            r0 = r3
            kotlin.jvm.internal.w r0 = (kotlin.jvm.internal.w) r0
            int r3 = r12.I$0
            java.lang.Object r4 = r12.L$2
            java.nio.ByteBuffer r4 = (java.nio.ByteBuffer) r4
            java.lang.Object r5 = r12.L$1
            java.nio.ByteBuffer r5 = (java.nio.ByteBuffer) r5
            java.lang.Object r6 = r12.L$0
            io.ktor.utils.io.i r6 = (io.ktor.utils.io.i) r6
            kotlin.p.b(r13)
            r7 = r6
            r6 = r13
            goto L_0x00ee
        L_0x0058:
            r0 = r1
            java.lang.Object r1 = r12.L$3
            r0 = r1
            kotlin.jvm.internal.w r0 = (kotlin.jvm.internal.w) r0
            int r1 = r12.I$0
            java.lang.Object r2 = r12.L$2
            java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
            java.lang.Object r3 = r12.L$1
            java.nio.ByteBuffer r3 = (java.nio.ByteBuffer) r3
            java.lang.Object r4 = r12.L$0
            io.ktor.utils.io.i r4 = (io.ktor.utils.io.i) r4
            kotlin.p.b(r13)
            r5 = r13
            goto L_0x00bd
        L_0x0071:
            kotlin.p.b(r13)
            r6 = 1
            if (r8 == r9) goto L_0x0079
            r0 = r6
            goto L_0x007a
        L_0x0079:
            r0 = r15
        L_0x007a:
            java.lang.String r1 = "Failed requirement."
            if (r0 == 0) goto L_0x0120
            if (r10 < 0) goto L_0x0082
            r0 = r6
            goto L_0x0083
        L_0x0082:
            r0 = r15
        L_0x0083:
            if (r0 == 0) goto L_0x0116
            kotlin.jvm.internal.w r0 = new kotlin.jvm.internal.w
            r0.<init>()
            r0.element = r15
            r5 = r0
            io.ktor.utils.io.r$d r4 = new io.ktor.utils.io.r$d
            r16 = 0
            r0 = r4
            r1 = r19
            r2 = r22
            r3 = r20
            r15 = r4
            r4 = r21
            r17 = r5
            r11 = r6
            r6 = r16
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r12.L$0 = r7
            r12.L$1 = r8
            r12.L$2 = r9
            r12.I$0 = r10
            r0 = r17
            r12.L$3 = r0
            r12.label = r11
            java.lang.Object r1 = r7.l(r15, r12)
            if (r1 != r14) goto L_0x00b8
            return r14
        L_0x00b8:
            r5 = r1
            r4 = r7
            r3 = r8
            r2 = r9
            r1 = r10
        L_0x00bd:
            java.lang.Number r5 = (java.lang.Number) r5
            int r5 = r5.intValue()
            if (r5 <= 0) goto L_0x0101
            boolean r6 = r4.f()
            if (r6 == 0) goto L_0x0101
            boolean r6 = r0.element
            if (r6 != 0) goto L_0x0101
            r12.L$0 = r4
            r12.L$1 = r3
            r12.L$2 = r2
            r12.I$0 = r1
            r12.L$3 = r0
            r12.I$1 = r5
            r12.I$2 = r5
            r6 = 2
            r12.label = r6
            java.lang.Object r6 = r4.v(r2, r12)
            if (r6 != r14) goto L_0x00e8
            return r14
        L_0x00e8:
            r7 = r4
            r4 = r2
            r2 = r5
            r5 = r3
            r3 = r1
            r1 = r2
        L_0x00ee:
            java.lang.Number r6 = (java.lang.Number) r6
            int r6 = r6.intValue()
            r8 = 0
            int r6 = kotlin.ranges.n.b(r6, r8)
            int r2 = r2 + r6
            r6 = r1
            r1 = r3
            r3 = r5
            r5 = r2
            r2 = r4
            r4 = r7
            goto L_0x0111
        L_0x0101:
            if (r5 != 0) goto L_0x0110
            boolean r6 = r4.y()
            if (r6 == 0) goto L_0x0110
            r6 = -1
            r18 = r6
            r6 = r5
            r5 = r18
            goto L_0x0111
        L_0x0110:
            r6 = r5
        L_0x0111:
            java.lang.Integer r5 = kotlin.coroutines.jvm.internal.b.c(r5)
            return r5
        L_0x0116:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0120:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.r.e(io.ktor.utils.io.i, java.nio.ByteBuffer, java.nio.ByteBuffer, int, kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.DelimitedKt$readUntilDelimiterSuspend$copied$1", f = "Delimited.kt", l = {71, 81}, m = "invokeSuspend")
    /* compiled from: Delimited.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<t, kotlin.coroutines.d<? super Integer>, Object> {
        final /* synthetic */ int $copied0;
        final /* synthetic */ ByteBuffer $delimiter;
        final /* synthetic */ ByteBuffer $dst;
        final /* synthetic */ w $endFound;
        final /* synthetic */ i $this_readUntilDelimiterSuspend;
        int I$0;
        int I$1;
        Object L$0;
        int label;
        private t p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(i iVar, int i, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, w wVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$this_readUntilDelimiterSuspend = iVar;
            this.$copied0 = i;
            this.$delimiter = byteBuffer;
            this.$dst = byteBuffer2;
            this.$endFound = wVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            d dVar2 = new d(this.$this_readUntilDelimiterSuspend, this.$copied0, this.$delimiter, this.$dst, this.$endFound, dVar);
            t tVar = (t) obj;
            dVar2.p$ = (t) obj;
            return dVar2;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((d) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* Debug info: failed to restart local var, previous not found, register: 10 */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: io.ktor.utils.io.t} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: io.ktor.utils.io.t} */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0048, code lost:
            r9 = r4;
            r4 = r1;
            r1 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x004b, code lost:
            r6 = io.ktor.utils.io.r.b(r4, r5.$delimiter, r5.$dst);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0053, code lost:
            if (r6 != 0) goto L_0x008c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0061, code lost:
            if (io.ktor.utils.io.r.a(r4, r5.$delimiter) != r5.$delimiter.remaining()) goto L_0x0068;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0063, code lost:
            r5.$endFound.element = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x006e, code lost:
            if (r5.$this_readUntilDelimiterSuspend.f() == false) goto L_0x0071;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0071, code lost:
            r7 = r5.$delimiter.remaining();
            r5.L$0 = r4;
            r5.I$0 = r1;
            r5.I$1 = r6;
            r5.label = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0084, code lost:
            if (r4.k(r7, r5) != r0) goto L_0x0087;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0086, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0087, code lost:
            r9 = r4;
            r4 = r1;
            r1 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x008c, code lost:
            if (r6 > 0) goto L_0x0094;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x008e, code lost:
            r5.$endFound.element = true;
            r2 = -r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0094, code lost:
            r2 = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0095, code lost:
            r9 = r4;
            r4 = r1 + r2;
            r1 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a0, code lost:
            if (r5.$dst.hasRemaining() == false) goto L_0x00a8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a6, code lost:
            if (r5.$endFound.element == false) goto L_0x003a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a8, code lost:
            r9 = r4;
            r4 = r1;
            r1 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00af, code lost:
            return kotlin.coroutines.jvm.internal.b.c(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x003a, code lost:
            r5.L$0 = r1;
            r5.I$0 = r4;
            r5.label = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0045, code lost:
            if (r1.k(1, r5) != r0) goto L_0x0048;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0047, code lost:
            return r0;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                r10 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r10.label
                r2 = 0
                r3 = 1
                r4 = 0
                switch(r1) {
                    case 0: goto L_0x0032;
                    case 1: goto L_0x0025;
                    case 2: goto L_0x0014;
                    default: goto L_0x000c;
                }
            L_0x000c:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0014:
                r1 = r2
                r5 = r2
                int r5 = r10.I$1
                int r1 = r10.I$0
                java.lang.Object r6 = r10.L$0
                r4 = r6
                io.ktor.utils.io.t r4 = (io.ktor.utils.io.t) r4
                kotlin.p.b(r11)
                r6 = r5
                r5 = r10
                goto L_0x0087
            L_0x0025:
                r1 = r2
                int r1 = r10.I$0
                java.lang.Object r5 = r10.L$0
                r4 = r5
                io.ktor.utils.io.t r4 = (io.ktor.utils.io.t) r4
                kotlin.p.b(r11)
                r5 = r10
                goto L_0x004b
            L_0x0032:
                kotlin.p.b(r11)
                io.ktor.utils.io.t r1 = r10.p$
                int r4 = r10.$copied0
                r5 = r10
            L_0x003a:
                r5.L$0 = r1
                r5.I$0 = r4
                r5.label = r3
                java.lang.Object r6 = r1.k(r3, r5)
                if (r6 != r0) goto L_0x0048
                return r0
            L_0x0048:
                r9 = r4
                r4 = r1
                r1 = r9
            L_0x004b:
                java.nio.ByteBuffer r6 = r5.$delimiter
                java.nio.ByteBuffer r7 = r5.$dst
                int r6 = io.ktor.utils.io.r.i(r4, r6, r7)
                if (r6 != 0) goto L_0x008b
                java.nio.ByteBuffer r7 = r5.$delimiter
                int r7 = io.ktor.utils.io.r.h(r4, r7)
                java.nio.ByteBuffer r8 = r5.$delimiter
                int r8 = r8.remaining()
                if (r7 != r8) goto L_0x0068
                kotlin.jvm.internal.w r0 = r5.$endFound
                r0.element = r3
                goto L_0x00ab
            L_0x0068:
                io.ktor.utils.io.i r7 = r5.$this_readUntilDelimiterSuspend
                boolean r7 = r7.f()
                if (r7 == 0) goto L_0x0071
                goto L_0x00ab
            L_0x0071:
                java.nio.ByteBuffer r7 = r5.$delimiter
                int r7 = r7.remaining()
                r5.L$0 = r4
                r5.I$0 = r1
                r5.I$1 = r6
                r8 = 2
                r5.label = r8
                java.lang.Object r7 = r4.k(r7, r5)
                if (r7 != r0) goto L_0x0087
                return r0
            L_0x0087:
                r9 = r4
                r4 = r1
                r1 = r9
                goto L_0x009a
            L_0x008b:
                if (r6 > 0) goto L_0x0094
                kotlin.jvm.internal.w r2 = r5.$endFound
                r2.element = r3
                int r2 = -r6
                goto L_0x0095
            L_0x0094:
                r2 = r6
            L_0x0095:
                int r1 = r1 + r2
                r9 = r4
                r4 = r1
                r1 = r9
            L_0x009a:
                java.nio.ByteBuffer r7 = r5.$dst
                boolean r7 = r7.hasRemaining()
                if (r7 == 0) goto L_0x00a8
                kotlin.jvm.internal.w r7 = r5.$endFound
                boolean r7 = r7.element
                if (r7 == 0) goto L_0x003a
            L_0x00a8:
                r9 = r4
                r4 = r1
                r1 = r9
            L_0x00ab:
                java.lang.Integer r0 = kotlin.coroutines.jvm.internal.b.c(r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.r.d.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* access modifiers changed from: private */
    public static final int i(@NotNull s $this$tryCopyUntilDelimiter, ByteBuffer delimiter, ByteBuffer dst) {
        int i;
        boolean endFound = false;
        ByteBuffer buffer = $this$tryCopyUntilDelimiter.a(0, 1);
        if (buffer == null) {
            return 0;
        }
        int index = n.b(buffer, delimiter);
        if (index != -1) {
            int found = Math.min(buffer.remaining() - index, delimiter.remaining());
            int notKnown = delimiter.remaining() - found;
            if (notKnown == 0) {
                endFound = true;
                i = n.e(dst, buffer, buffer.position() + index);
            } else {
                ByteBuffer remembered = buffer.duplicate();
                ByteBuffer next = $this$tryCopyUntilDelimiter.a(index + found, 1);
                if (next == null) {
                    k.b(remembered, "remembered");
                    i = n.e(dst, remembered, remembered.position() + index);
                } else if (!n.f(next, delimiter, found)) {
                    k.b(remembered, "remembered");
                    i = n.e(dst, remembered, remembered.position() + index + 1);
                } else if (next.remaining() >= notKnown) {
                    endFound = true;
                    k.b(remembered, "remembered");
                    i = n.e(dst, remembered, remembered.position() + index);
                } else {
                    k.b(remembered, "remembered");
                    i = n.e(dst, remembered, remembered.position() + index);
                }
            }
        } else {
            i = n.d(dst, buffer, 0, 2, (Object) null);
        }
        int size = i;
        $this$tryCopyUntilDelimiter.w(size);
        return endFound ? -size : size;
    }

    /* access modifiers changed from: private */
    public static final int j(@NotNull s $this$tryEnsureDelimiter, ByteBuffer delimiter) {
        int found = h($this$tryEnsureDelimiter, delimiter);
        if (found == -1) {
            throw new IOException("Failed to skip delimiter: actual bytes differ from delimiter bytes");
        } else if (found < delimiter.remaining()) {
            return found;
        } else {
            $this$tryEnsureDelimiter.w(delimiter.remaining());
            return delimiter.remaining();
        }
    }

    /* access modifiers changed from: private */
    public static final int h(@NotNull s $this$startsWithDelimiter, ByteBuffer delimiter) {
        ByteBuffer a2 = $this$startsWithDelimiter.a(0, 1);
        if (a2 == null) {
            return 0;
        }
        ByteBuffer buffer = a2;
        int index = n.b(buffer, delimiter);
        if (index != 0) {
            return -1;
        }
        int found = Math.min(buffer.remaining() - index, delimiter.remaining());
        int notKnown = delimiter.remaining() - found;
        if (notKnown > 0) {
            ByteBuffer next = $this$startsWithDelimiter.a(index + found, notKnown);
            if (next == null) {
                return found;
            }
            if (!n.f(next, delimiter, found)) {
                return -1;
            }
        }
        return delimiter.remaining();
    }
}
