package io.ktor.http.cio;

import io.ktor.utils.io.core.d0;
import io.ktor.utils.io.core.n;
import io.ktor.utils.io.i;
import io.ktor.utils.io.l;
import io.ktor.utils.io.t;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharsetEncoder;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.w;
import kotlin.x;
import kotlinx.coroutines.channels.s;
import kotlinx.coroutines.channels.u;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Multipart.kt */
public final class k {
    /* access modifiers changed from: private */
    public static final ByteBuffer a;
    /* access modifiers changed from: private */
    public static final ByteBuffer b;

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.MultipartKt", f = "Multipart.kt", l = {396, 399}, m = "copyUntilBoundary")
    /* compiled from: Multipart.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        long J$0;
        long J$1;
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
            return k.c((String) null, (ByteBuffer) null, (i) null, (p<? super ByteBuffer, ? super kotlin.coroutines.d<? super x>, ? extends Object>) null, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.MultipartKt", f = "Multipart.kt", l = {179, 181}, m = "parsePartBodyImpl")
    /* compiled from: Multipart.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        long J$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        c(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return k.h((ByteBuffer) null, (i) null, (l) null, (f) null, 0, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.MultipartKt", f = "Multipart.kt", l = {148}, m = "parsePartHeadersImpl")
    /* compiled from: Multipart.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        e(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return k.j((i) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.MultipartKt", f = "Multipart.kt", l = {201, 205}, m = "skipBoundary")
    /* compiled from: Multipart.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        g(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return k.l((ByteBuffer) null, (i) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.MultipartKt$parsePreambleImpl$2", f = "Multipart.kt", l = {}, m = "invokeSuspend")
    /* compiled from: Multipart.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.l implements p<ByteBuffer, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ n $output;
        int label;
        private ByteBuffer p$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(n nVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$output = nVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            f fVar = new f(this.$output, dVar);
            ByteBuffer byteBuffer = (ByteBuffer) obj;
            fVar.p$0 = (ByteBuffer) obj;
            return fVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((f) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    d0.a(this.$output, this.p$0);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: io.ktor.http.cio.internals.b} */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005f, code lost:
        r1 = (io.ktor.http.cio.f) r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0061, code lost:
        if (r1 == null) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0063, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006b, code lost:
        throw new java.io.EOFException("Failed to parse multipart headers: unexpected end of stream");
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object j(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r10, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.http.cio.f> r11) {
        /*
            boolean r0 = r11 instanceof io.ktor.http.cio.k.e
            if (r0 == 0) goto L_0x0013
            r0 = r11
            io.ktor.http.cio.k$e r0 = (io.ktor.http.cio.k.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.http.cio.k$e r0 = new io.ktor.http.cio.k$e
            r0.<init>(r11)
        L_0x0018:
            java.lang.Object r7 = r0.result
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            int r1 = r0.label
            r2 = 0
            switch(r1) {
                case 0: goto L_0x0040;
                case 1: goto L_0x002d;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002d:
            r1 = r2
            java.lang.Object r2 = r0.L$1
            r1 = r2
            io.ktor.http.cio.internals.b r1 = (io.ktor.http.cio.internals.b) r1
            java.lang.Object r2 = r0.L$0
            r10 = r2
            io.ktor.utils.io.i r10 = (io.ktor.utils.io.i) r10
            kotlin.p.b(r7)     // Catch:{ all -> 0x003e }
            r9 = r1
            r1 = r7
            goto L_0x005f
        L_0x003e:
            r2 = move-exception
            goto L_0x006e
        L_0x0040:
            kotlin.p.b(r7)
            io.ktor.http.cio.internals.b r1 = new io.ktor.http.cio.internals.b
            r3 = 1
            r1.<init>(r2, r3, r2)
            r9 = r1
            r4 = 0
            r5 = 4
            r6 = 0
            r0.L$0 = r10     // Catch:{ all -> 0x006c }
            r0.L$1 = r9     // Catch:{ all -> 0x006c }
            r0.label = r3     // Catch:{ all -> 0x006c }
            r1 = r10
            r2 = r9
            r3 = r4
            r4 = r0
            java.lang.Object r1 = io.ktor.http.cio.i.h(r1, r2, r3, r4, r5, r6)     // Catch:{ all -> 0x006c }
            if (r1 != r8) goto L_0x005f
            return r8
        L_0x005f:
            io.ktor.http.cio.f r1 = (io.ktor.http.cio.f) r1     // Catch:{ all -> 0x006c }
            if (r1 == 0) goto L_0x0064
            return r1
        L_0x0064:
            java.io.EOFException r1 = new java.io.EOFException     // Catch:{ all -> 0x006c }
            java.lang.String r2 = "Failed to parse multipart headers: unexpected end of stream"
            r1.<init>(r2)     // Catch:{ all -> 0x006c }
            throw r1     // Catch:{ all -> 0x006c }
        L_0x006c:
            r2 = move-exception
            r1 = r9
        L_0x006e:
            r1.o()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.k.j(io.ktor.utils.io.i, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: java.lang.Long} */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b9, code lost:
        r0 = ((java.lang.Number) r0).longValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x010f, code lost:
        r0 = ((java.lang.Number) r0).longValue();
        r4 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0117, code lost:
        r9.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x011f, code lost:
        return kotlin.coroutines.jvm.internal.b.d(r0);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0031  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object h(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r17, @org.jetbrains.annotations.NotNull io.ktor.utils.io.i r18, @org.jetbrains.annotations.NotNull io.ktor.utils.io.l r19, @org.jetbrains.annotations.NotNull io.ktor.http.cio.f r20, long r21, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r23) {
        /*
            r7 = r17
            r8 = r18
            r9 = r19
            r10 = r20
            r11 = r21
            r13 = r23
            boolean r0 = r13 instanceof io.ktor.http.cio.k.c
            if (r0 == 0) goto L_0x001f
            r0 = r13
            io.ktor.http.cio.k$c r0 = (io.ktor.http.cio.k.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x001f
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0024
        L_0x001f:
            io.ktor.http.cio.k$c r0 = new io.ktor.http.cio.k$c
            r0.<init>(r13)
        L_0x0024:
            r14 = r0
            java.lang.Object r15 = r14.result
            java.lang.Object r6 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r14.label
            r1 = 0
            switch(r0) {
                case 0: goto L_0x007d;
                case 1: goto L_0x005c;
                case 2: goto L_0x0039;
                default: goto L_0x0031;
            }
        L_0x0031:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0039:
            r0 = r1
            java.lang.Object r1 = r14.L$4
            r0 = r1
            java.lang.Long r0 = (java.lang.Long) r0
            long r1 = r14.J$0
            java.lang.Object r3 = r14.L$3
            io.ktor.http.cio.f r3 = (io.ktor.http.cio.f) r3
            java.lang.Object r4 = r14.L$2
            io.ktor.utils.io.l r4 = (io.ktor.utils.io.l) r4
            java.lang.Object r5 = r14.L$1
            io.ktor.utils.io.i r5 = (io.ktor.utils.io.i) r5
            java.lang.Object r6 = r14.L$0
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            kotlin.p.b(r15)
            r16 = r0
            r11 = r1
            r10 = r3
            r9 = r4
            r0 = r15
            goto L_0x010f
        L_0x005c:
            r0 = r1
            java.lang.Object r1 = r14.L$4
            r0 = r1
            java.lang.Long r0 = (java.lang.Long) r0
            long r1 = r14.J$0
            java.lang.Object r3 = r14.L$3
            io.ktor.http.cio.f r3 = (io.ktor.http.cio.f) r3
            java.lang.Object r4 = r14.L$2
            io.ktor.utils.io.l r4 = (io.ktor.utils.io.l) r4
            java.lang.Object r5 = r14.L$1
            io.ktor.utils.io.i r5 = (io.ktor.utils.io.i) r5
            java.lang.Object r6 = r14.L$0
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            kotlin.p.b(r15)
            r11 = r1
            r10 = r3
            r9 = r4
            r4 = r0
            r0 = r15
            goto L_0x00b9
        L_0x007d:
            kotlin.p.b(r15)
            java.lang.String r0 = "Content-Length"
            java.lang.CharSequence r0 = r10.e(r0)
            if (r0 == 0) goto L_0x0091
            long r2 = io.ktor.http.cio.internals.d.i(r0)
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r2)
            goto L_0x0092
        L_0x0091:
            r0 = r1
        L_0x0092:
            r4 = r0
            if (r4 == 0) goto L_0x00e4
            long r0 = r4.longValue()
            int r0 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r0 > 0) goto L_0x00c0
            long r0 = r4.longValue()
            r14.L$0 = r7
            r14.L$1 = r8
            r14.L$2 = r9
            r14.L$3 = r10
            r14.J$0 = r11
            r14.L$4 = r4
            r2 = 1
            r14.label = r2
            java.lang.Object r0 = io.ktor.utils.io.j.a(r8, r9, r0, r14)
            if (r0 != r6) goto L_0x00b7
            return r6
        L_0x00b7:
            r6 = r7
            r5 = r8
        L_0x00b9:
            java.lang.Number r0 = (java.lang.Number) r0
            long r0 = r0.longValue()
            goto L_0x0117
        L_0x00c0:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Multipart part content length limit of "
            r1.append(r2)
            r1.append(r11)
            java.lang.String r2 = " exceeded (actual size is "
            r1.append(r2)
            r1.append(r4)
            r2 = 41
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00e4:
            io.ktor.http.cio.k$d r3 = new io.ktor.http.cio.k$d
            r3.<init>(r9, r1)
            r14.L$0 = r7
            r14.L$1 = r8
            r14.L$2 = r9
            r14.L$3 = r10
            r14.J$0 = r11
            r14.L$4 = r4
            r0 = 2
            r14.label = r0
            java.lang.String r0 = "part"
            r1 = r17
            r2 = r18
            r16 = r4
            r4 = r21
            r7 = r6
            r6 = r14
            java.lang.Object r0 = c(r0, r1, r2, r3, r4, r6)
            if (r0 != r7) goto L_0x010c
            return r7
        L_0x010c:
            r6 = r17
            r5 = r8
        L_0x010f:
            java.lang.Number r0 = (java.lang.Number) r0
            long r0 = r0.longValue()
            r4 = r16
        L_0x0117:
            r9.flush()
            java.lang.Long r2 = kotlin.coroutines.jvm.internal.b.d(r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.k.h(java.nio.ByteBuffer, io.ktor.utils.io.i, io.ktor.utils.io.l, io.ktor.http.cio.f, long, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object i(ByteBuffer byteBuffer, i iVar, l lVar, f fVar, long j, kotlin.coroutines.d dVar, int i, Object obj) {
        return h(byteBuffer, iVar, lVar, fVar, (i & 16) != 0 ? Long.MAX_VALUE : j, dVar);
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.MultipartKt$parsePartBodyImpl$size$1", f = "Multipart.kt", l = {181}, m = "invokeSuspend")
    /* compiled from: Multipart.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<ByteBuffer, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ l $output;
        Object L$0;
        int label;
        private ByteBuffer p$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(l lVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$output = lVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            d dVar2 = new d(this.$output, dVar);
            ByteBuffer byteBuffer = (ByteBuffer) obj;
            dVar2.p$0 = (ByteBuffer) obj;
            return dVar2;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((d) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    ByteBuffer it = this.p$0;
                    l lVar = this.$output;
                    this.L$0 = it;
                    this.label = 1;
                    if (lVar.h(it, this) != d) {
                        ByteBuffer byteBuffer = it;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    ByteBuffer it2 = (ByteBuffer) this.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: io.ktor.utils.io.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: kotlin.jvm.internal.w} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x007c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object l(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r6, @org.jetbrains.annotations.NotNull io.ktor.utils.io.i r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Boolean> r8) {
        /*
            boolean r0 = r8 instanceof io.ktor.http.cio.k.g
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.http.cio.k$g r0 = (io.ktor.http.cio.k.g) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.http.cio.k$g r0 = new io.ktor.http.cio.k$g
            r0.<init>(r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x004e;
                case 1: goto L_0x0040;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            r2 = r4
            java.lang.Object r3 = r0.L$2
            r2 = r3
            kotlin.jvm.internal.w r2 = (kotlin.jvm.internal.w) r2
            java.lang.Object r3 = r0.L$1
            r7 = r3
            io.ktor.utils.io.i r7 = (io.ktor.utils.io.i) r7
            java.lang.Object r3 = r0.L$0
            r6 = r3
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            kotlin.p.b(r1)
            goto L_0x007e
        L_0x0040:
            java.lang.Object r3 = r0.L$1
            r7 = r3
            io.ktor.utils.io.i r7 = (io.ktor.utils.io.i) r7
            java.lang.Object r3 = r0.L$0
            r6 = r3
            java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
            kotlin.p.b(r1)
            goto L_0x005f
        L_0x004e:
            kotlin.p.b(r1)
            r0.L$0 = r6
            r0.L$1 = r7
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = io.ktor.utils.io.r.f(r7, r6, r0)
            if (r3 != r2) goto L_0x005f
            return r2
        L_0x005f:
            kotlin.jvm.internal.w r3 = new kotlin.jvm.internal.w
            r3.<init>()
            r5 = 0
            r3.element = r5
            io.ktor.http.cio.k$h r5 = new io.ktor.http.cio.k$h
            r5.<init>(r3, r4)
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r3
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r7.l(r5, r0)
            if (r4 != r2) goto L_0x007d
            return r2
        L_0x007d:
            r2 = r3
        L_0x007e:
            boolean r3 = r2.element
            java.lang.Boolean r3 = kotlin.coroutines.jvm.internal.b.a(r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.k.l(java.nio.ByteBuffer, io.ktor.utils.io.i, kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.MultipartKt$skipBoundary$2", f = "Multipart.kt", l = {206, 217}, m = "invokeSuspend")
    /* compiled from: Multipart.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.l implements p<t, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ w $result;
        Object L$0;
        Object L$1;
        int label;
        private t p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(w wVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$result = wVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            h hVar = new h(this.$result, dVar);
            t tVar = (t) obj;
            hVar.p$ = (t) obj;
            return hVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((h) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: io.ktor.utils.io.t} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v12, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: io.ktor.utils.io.t} */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r8.label
                java.lang.String r2 = "Failed to pass multipart boundary: unexpected end of stream"
                r3 = 0
                r4 = 45
                r5 = 2
                r6 = 1
                switch(r1) {
                    case 0: goto L_0x0032;
                    case 1: goto L_0x0028;
                    case 2: goto L_0x0018;
                    default: goto L_0x0010;
                }
            L_0x0010:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0018:
                r0 = r3
                r1 = r3
                java.lang.Object r3 = r8.L$1
                r0 = r3
                java.nio.ByteBuffer r0 = (java.nio.ByteBuffer) r0
                java.lang.Object r3 = r8.L$0
                r1 = r3
                io.ktor.utils.io.t r1 = (io.ktor.utils.io.t) r1
                kotlin.p.b(r9)
                goto L_0x007f
            L_0x0028:
                r1 = r3
                java.lang.Object r3 = r8.L$0
                r1 = r3
                io.ktor.utils.io.t r1 = (io.ktor.utils.io.t) r1
                kotlin.p.b(r9)
                goto L_0x0042
            L_0x0032:
                kotlin.p.b(r9)
                io.ktor.utils.io.t r1 = r8.p$
                r8.L$0 = r1
                r8.label = r6
                java.lang.Object r3 = r1.k(r6, r8)
                if (r3 != r0) goto L_0x0042
                return r0
            L_0x0042:
                r3 = 0
                java.nio.ByteBuffer r3 = r1.a(r3, r6)
                if (r3 == 0) goto L_0x00a3
                int r7 = r3.position()
                byte r7 = r3.get(r7)
                if (r7 == r4) goto L_0x0056
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0056:
                int r7 = r3.remaining()
                if (r7 <= r6) goto L_0x0071
                int r7 = r3.position()
                int r7 = r7 + r6
                byte r7 = r3.get(r7)
                if (r7 != r4) goto L_0x0071
                kotlin.jvm.internal.w r0 = r8.$result
                r0.element = r6
                r1.w(r5)
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0071:
                r8.L$0 = r1
                r8.L$1 = r3
                r8.label = r5
                java.lang.Object r7 = r1.k(r5, r8)
                if (r7 != r0) goto L_0x007e
                return r0
            L_0x007e:
                r0 = r3
            L_0x007f:
                java.nio.ByteBuffer r3 = r1.a(r6, r6)
                if (r3 == 0) goto L_0x009d
                r2 = r3
                int r3 = r2.position()
                byte r3 = r2.get(r3)
                if (r3 != r4) goto L_0x009a
                kotlin.jvm.internal.w r3 = r8.$result
                r3.element = r6
                r1.w(r5)
                kotlin.x r3 = kotlin.x.a
                return r3
            L_0x009a:
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x009d:
                java.io.IOException r3 = new java.io.IOException
                r3.<init>(r2)
                throw r3
            L_0x00a3:
                java.io.IOException r0 = new java.io.IOException
                r0.<init>(r2)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.k.h.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @NotNull
    public static final kotlinx.coroutines.channels.w<j> f(@NotNull o0 $this$parseMultipart, @NotNull i input, @NotNull CharSequence contentType, @Nullable Long contentLength) {
        kotlin.jvm.internal.k.f($this$parseMultipart, "$this$parseMultipart");
        kotlin.jvm.internal.k.f(input, "input");
        kotlin.jvm.internal.k.f(contentType, "contentType");
        if (kotlin.text.x.M0(contentType, "multipart/", false, 2, (Object) null)) {
            return g($this$parseMultipart, e(contentType), input, contentLength);
        }
        throw new IOException("Failed to parse multipart: Content-Type should be multipart/* but it is " + contentType);
    }

    static {
        CharsetEncoder newEncoder = kotlin.text.c.a.newEncoder();
        kotlin.jvm.internal.k.b(newEncoder, "charset.newEncoder()");
        ByteBuffer wrap = ByteBuffer.wrap(io.ktor.utils.io.charsets.a.f(newEncoder, "\r\n", 0, "\r\n".length()));
        if (wrap == null) {
            kotlin.jvm.internal.k.n();
        }
        a = wrap;
        ByteBuffer allocate = ByteBuffer.allocate(8192);
        if (allocate == null) {
            kotlin.jvm.internal.k.n();
        }
        b = allocate;
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.MultipartKt$parseMultipart$1", f = "Multipart.kt", l = {319, 322, 325, 332, 335, 338, 343, 347, 352, 362, 370, 370, 373, 375}, m = "invokeSuspend")
    /* compiled from: Multipart.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements p<u<? super j>, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ ByteBuffer $boundaryPrefixed;
        final /* synthetic */ i $input;
        final /* synthetic */ Long $totalLength;
        long J$0;
        long J$1;
        long J$2;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        int label;
        private u p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(i iVar, ByteBuffer byteBuffer, Long l, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$input = iVar;
            this.$boundaryPrefixed = byteBuffer;
            this.$totalLength = l;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            kotlin.jvm.internal.k.f(dVar, "completion");
            b bVar = new b(this.$input, this.$boundaryPrefixed, this.$totalLength, dVar);
            u uVar = (u) obj;
            bVar.p$ = (u) obj;
            return bVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((b) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* Debug info: failed to restart local var, previous not found, register: 34 */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v3, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v7, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v15, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v16, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v21, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v45, resolved type: io.ktor.utils.io.core.n} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v22, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v34, resolved type: java.nio.ByteBuffer} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v23, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v26, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v24, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v48, resolved type: io.ktor.utils.io.core.n} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v25, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v38, resolved type: java.nio.ByteBuffer} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v26, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v29, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v27, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v51, resolved type: io.ktor.utils.io.core.n} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v28, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v42, resolved type: java.nio.ByteBuffer} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v29, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v32, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v18, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v32, resolved type: java.nio.ByteBuffer} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v21, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v35, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v23, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v35, resolved type: java.nio.ByteBuffer} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v26, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v39, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v28, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v38, resolved type: java.nio.ByteBuffer} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v31, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v43, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v47, resolved type: kotlinx.coroutines.w} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v16, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v61, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v20, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v17, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v67, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v45, resolved type: kotlinx.coroutines.w} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v18, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v51, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v22, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v28, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v47, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v10, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v32, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v49, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v50, resolved type: io.ktor.http.cio.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v34, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v48, resolved type: kotlinx.coroutines.w} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v35, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v22, resolved type: io.ktor.utils.io.f} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v34, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v21, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v44, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v57, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v30, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v31, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v35, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v34, resolved type: kotlinx.coroutines.channels.u} */
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:100:0x04d4, code lost:
            if (r0 != r2) goto L_0x04d7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:101:0x04d6, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:102:0x04d7, code lost:
            r1 = r35;
            r6 = r10;
            r9 = r8;
            r8 = r12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:103:0x04dc, code lost:
            r35 = r1;
            r1 = new io.ktor.http.cio.j.a((io.ktor.utils.io.core.q) r0);
            r11.L$0 = r8;
            r11.J$0 = r13;
            r11.L$1 = r6;
            r11.L$2 = r3;
            r11.L$3 = r15;
            r11.J$1 = r4;
            r11.J$2 = r9;
            r11.label = 12;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:104:0x04fb, code lost:
            if (r12.D(r1, r11) != r2) goto L_0x04fe;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:105:0x04fd, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:106:0x04fe, code lost:
            r0 = r35;
            r1 = r13;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:107:0x0501, code lost:
            r1 = r0;
            r12 = r8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:108:0x0504, code lost:
            r1 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:110:0x050e, code lost:
            throw new java.io.IOException("Failed to parse multipart: prologue is too long");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:111:0x050f, code lost:
            r35 = r1;
            r0 = r11.$input;
            r11.L$0 = r12;
            r11.J$0 = r13;
            r11.L$1 = r10;
            r11.L$2 = r3;
            r11.L$3 = r15;
            r11.label = 13;
            r0 = io.ktor.utils.io.k.g(r0, r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:112:0x0525, code lost:
            if (r0 != r2) goto L_0x0528;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:113:0x0527, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:114:0x0528, code lost:
            r1 = r35;
            r4 = r10;
            r5 = r12;
            r6 = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:115:0x052d, code lost:
            r0 = (io.ktor.utils.io.core.q) r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:116:0x0539, code lost:
            if ((!r0.w0()) == false) goto L_0x055e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:117:0x053b, code lost:
            r8 = new io.ktor.http.cio.j.a(r0);
            r11.L$0 = r5;
            r11.J$0 = r13;
            r11.L$1 = r4;
            r11.L$2 = r3;
            r11.L$3 = r6;
            r11.L$4 = r0;
            r11.label = 14;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:118:0x0554, code lost:
            if (r5.D(r8, r11) != r2) goto L_0x0557;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:119:0x0556, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x0557, code lost:
            r30 = r3;
            r3 = r0;
            r0 = r30;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:121:0x055c, code lost:
            r12 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x055e, code lost:
            r12 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x0561, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:125:0x0562, code lost:
            r0 = r1;
            r1 = r2;
            r2 = r3;
            r3 = r10;
            r10 = r11;
            r4 = r12;
            r5 = r13;
            r9 = r15;
            r14 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:126:0x0574, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:127:0x0575, code lost:
            r8 = r0;
            r2 = r4;
            r5 = r13;
            r0 = r35;
            r4 = r1;
            r1 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:128:0x0581, code lost:
            r35 = r2;
            r29 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:130:?, code lost:
            r1.j();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:131:0x058f, code lost:
            throw new java.util.concurrent.CancellationException("Multipart processing has been cancelled");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:132:0x0590, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:133:0x0591, code lost:
            r8 = r0;
            r2 = r4;
            r5 = r13;
            r0 = r35;
            r4 = r1;
            r1 = r29;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:134:0x059a, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:135:0x059b, code lost:
            r35 = r2;
            r8 = r0;
            r2 = r4;
            r5 = r13;
            r0 = r35;
            r4 = r1;
            r1 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:136:0x05a8, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:137:0x05a9, code lost:
            r35 = r2;
            r8 = r0;
            r2 = r4;
            r5 = r13;
            r4 = r15;
            r1 = r3;
            r0 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:138:0x05b6, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:139:0x05b7, code lost:
            r8 = r0;
            r1 = r2;
            r14 = r3;
            r2 = r4;
            r5 = r13;
            r4 = r15;
            r0 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:140:0x05c0, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:141:0x05c1, code lost:
            r35 = r1;
            r8 = r0;
            r1 = r2;
            r14 = r3;
            r2 = r4;
            r5 = r13;
            r4 = r15;
            r0 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:149:0x05e3, code lost:
            throw new java.io.IOException("Failed to parse multipart: boundary line is too long");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x030b, code lost:
            if (r1.f1() <= 0) goto L_0x0327;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x030d, code lost:
            r6 = new io.ktor.http.cio.j.c(r1.e1());
            r7.L$0 = r3;
            r7.J$0 = r4;
            r7.L$1 = r2;
            r7.L$2 = r1;
            r7.label = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x0324, code lost:
            if (r3.D(r6, r7) != r0) goto L_0x0327;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0326, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0327, code lost:
            r6 = r7.$input;
            r7.L$0 = r3;
            r7.J$0 = r4;
            r7.L$1 = r2;
            r7.L$2 = r1;
            r7.label = 3;
            r6 = io.ktor.http.cio.k.l(r2, r6, r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0338, code lost:
            if (r6 != r0) goto L_0x033b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x033a, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0341, code lost:
            if (((java.lang.Boolean) r6).booleanValue() == false) goto L_0x0346;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x0345, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x0346, code lost:
            r9 = io.ktor.http.cio.k.a().duplicate();
            r10 = r7;
            r5 = r4;
            r4 = r3;
            r3 = r2;
            r2 = r1;
            r1 = r0;
            r0 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x0357, code lost:
            r11 = r10.$input;
            r12 = io.ktor.http.cio.k.b();
            kotlin.jvm.internal.k.b(r9, "trailingBuffer");
            r10.L$0 = r4;
            r10.J$0 = r5;
            r10.L$1 = r3;
            r10.L$2 = r2;
            r10.L$3 = r9;
            r10.label = 4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0372, code lost:
            if (io.ktor.utils.io.r.d(r11, r12, r9, r10) != r1) goto L_0x0375;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x0374, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0375, code lost:
            r11 = r10.$input;
            r12 = io.ktor.http.cio.k.b();
            kotlin.jvm.internal.k.b(r9, "trailingBuffer");
            r10.L$0 = r4;
            r10.J$0 = r5;
            r10.L$1 = r3;
            r10.L$2 = r2;
            r10.L$3 = r9;
            r10.label = 5;
            r11 = io.ktor.utils.io.r.d(r11, r12, r9, r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x038f, code lost:
            if (r11 != r1) goto L_0x0392;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0391, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x0398, code lost:
            if (((java.lang.Number) r11).intValue() != 0) goto L_0x05dc;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x039a, code lost:
            r11 = r10.$input;
            r12 = io.ktor.http.cio.k.b();
            r10.L$0 = r4;
            r10.J$0 = r5;
            r10.L$1 = r3;
            r10.L$2 = r2;
            r10.L$3 = r9;
            r10.label = 6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x03b1, code lost:
            if (io.ktor.utils.io.r.f(r11, r12, r10) != r1) goto L_0x03b4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x03b3, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x03b4, code lost:
            r13 = io.ktor.utils.io.g.b(false, 1, r14);
            r15 = kotlinx.coroutines.y.b(r14, 1, r14);
            r12 = new io.ktor.http.cio.j.b(r15, r13);
            r10.L$0 = r4;
            r10.J$0 = r5;
            r10.L$1 = r3;
            r10.L$2 = r2;
            r10.L$3 = r9;
            r10.L$4 = r13;
            r10.L$5 = r15;
            r10.L$6 = r12;
            r10.label = 7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x03da, code lost:
            if (r4.D(r12, r10) != r1) goto L_0x03dd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x03dc, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x03dd, code lost:
            r11 = r9;
            r9 = r3;
            r3 = r4;
            r4 = r5;
            r6 = r15;
            r30 = r1;
            r1 = r0;
            r0 = r30;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x03e7, code lost:
            r15 = r14;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
            r14 = r10.$input;
            r10.L$0 = r3;
            r10.J$0 = r4;
            r10.L$1 = r9;
            r10.L$2 = r2;
            r10.L$3 = r11;
            r10.L$4 = r13;
            r10.L$5 = r6;
            r10.L$6 = r12;
            r10.L$7 = r15;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x03fd, code lost:
            r35 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
            r10.label = 8;
            r1 = io.ktor.http.cio.k.j(r14, r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:0x0407, code lost:
            if (r1 != r0) goto L_0x040a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:0x0409, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x040a, code lost:
            r14 = r3;
            r3 = r2;
            r2 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
            r1 = (io.ktor.http.cio.f) r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:76:0x0414, code lost:
            if (r6.x(r1) == false) goto L_0x0581;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:77:0x0416, code lost:
            r15 = r10.$boundaryPrefixed;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x0418, code lost:
            r35 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
            r2 = r10.$input;
            r10.L$0 = r14;
            r10.J$0 = r4;
            r10.L$1 = r9;
            r10.L$2 = r3;
            r10.L$3 = r11;
            r10.L$4 = r13;
            r10.L$5 = r6;
            r10.L$6 = r12;
            r10.L$7 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:0x0434, code lost:
            r29 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
            r10.label = 9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:84:0x0448, code lost:
            if (io.ktor.http.cio.k.i(r15, r2, r13, r1, 0, r10, 16, (java.lang.Object) null) != r0) goto L_0x044b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:85:0x044a, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:86:0x044b, code lost:
            r3 = r1;
            r15 = r11;
            r2 = r29;
            r1 = r0;
            r11 = r9;
            r9 = r12;
            r12 = r14;
            r0 = r35;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:87:0x0455, code lost:
            io.ktor.utils.io.m.a(r13);
            r14 = r10.$boundaryPrefixed;
            r35 = r0;
            r0 = r10.$input;
            r10.L$0 = r12;
            r10.J$0 = r4;
            r10.L$1 = r11;
            r10.L$2 = r2;
            r10.L$3 = r15;
            r10.L$4 = r13;
            r10.L$5 = r6;
            r10.L$6 = r9;
            r10.L$7 = r3;
            r20 = r2;
            r10.label = 10;
            r0 = io.ktor.http.cio.k.l(r14, r0, r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:88:0x047c, code lost:
            if (r0 != r1) goto L_0x047f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:89:0x047e, code lost:
            return r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:90:0x047f, code lost:
            r2 = r1;
            r1 = r35;
            r30 = r4;
            r4 = r3;
            r5 = r13;
            r3 = r20;
            r13 = r30;
            r32 = r11;
            r11 = r10;
            r10 = r32;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:92:0x0495, code lost:
            if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x0562;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:94:0x0499, code lost:
            if (r11.$totalLength == null) goto L_0x050f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:95:0x049b, code lost:
            r4 = r11.$input.t() - r13;
            r8 = r11.$totalLength.longValue() - r4;
            r35 = r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:96:0x04b1, code lost:
            if (r8 > ((long) Integer.MAX_VALUE)) goto L_0x0507;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:98:0x04b7, code lost:
            if (r8 <= 0) goto L_0x0504;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:99:0x04b9, code lost:
            r11.L$0 = r12;
            r11.J$0 = r13;
            r11.L$1 = r10;
            r11.L$2 = r3;
            r11.L$3 = r15;
            r11.J$1 = r4;
            r11.J$2 = r8;
            r11.L$4 = r12;
            r11.label = 11;
            r0 = io.ktor.utils.io.k.e(r11.$input, (int) r8, r11);
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r35) {
            /*
                r34 = this;
                r7 = r34
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r7.label
                java.lang.String r8 = "trailingBuffer"
                r10 = 2
                r11 = 0
                r14 = 0
                switch(r1) {
                    case 0: goto L_0x02c6;
                    case 1: goto L_0x02ab;
                    case 2: goto L_0x028f;
                    case 3: goto L_0x0271;
                    case 4: goto L_0x0246;
                    case 5: goto L_0x021a;
                    case 6: goto L_0x01ef;
                    case 7: goto L_0x01b2;
                    case 8: goto L_0x015e;
                    case 9: goto L_0x010b;
                    case 10: goto L_0x00cc;
                    case 11: goto L_0x0099;
                    case 12: goto L_0x006b;
                    case 13: goto L_0x0044;
                    case 14: goto L_0x001a;
                    default: goto L_0x0012;
                }
            L_0x0012:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x001a:
                r0 = r14
                r1 = r11
                r3 = r14
                r4 = r14
                r5 = r14
                r6 = r14
                java.lang.Object r8 = r7.L$4
                r3 = r8
                io.ktor.utils.io.core.q r3 = (io.ktor.utils.io.core.q) r3
                java.lang.Object r8 = r7.L$3
                r6 = r8
                java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
                java.lang.Object r8 = r7.L$2
                r0 = r8
                io.ktor.utils.io.core.n r0 = (io.ktor.utils.io.core.n) r0
                java.lang.Object r8 = r7.L$1
                r4 = r8
                java.nio.ByteBuffer r4 = (java.nio.ByteBuffer) r4
                long r1 = r7.J$0
                java.lang.Object r8 = r7.L$0
                r5 = r8
                kotlinx.coroutines.channels.u r5 = (kotlinx.coroutines.channels.u) r5
                kotlin.p.b(r35)
                r13 = r1
                r11 = r7
                r1 = r35
                goto L_0x055c
            L_0x0044:
                r1 = r14
                r2 = r11
                r4 = r14
                r5 = r14
                r6 = r14
                java.lang.Object r8 = r7.L$3
                r6 = r8
                java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
                java.lang.Object r8 = r7.L$2
                r1 = r8
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r8 = r7.L$1
                r4 = r8
                java.nio.ByteBuffer r4 = (java.nio.ByteBuffer) r4
                long r2 = r7.J$0
                java.lang.Object r8 = r7.L$0
                r5 = r8
                kotlinx.coroutines.channels.u r5 = (kotlinx.coroutines.channels.u) r5
                kotlin.p.b(r35)
                r13 = r2
                r11 = r7
                r2 = r0
                r3 = r1
                r0 = r35
                r1 = r0
                goto L_0x052d
            L_0x006b:
                r0 = r14
                r1 = r11
                r3 = r11
                r5 = r14
                r6 = r14
                r8 = r14
                r9 = r11
                long r9 = r7.J$2
                long r3 = r7.J$1
                java.lang.Object r11 = r7.L$3
                r8 = r11
                java.nio.ByteBuffer r8 = (java.nio.ByteBuffer) r8
                java.lang.Object r11 = r7.L$2
                r0 = r11
                io.ktor.utils.io.core.n r0 = (io.ktor.utils.io.core.n) r0
                java.lang.Object r11 = r7.L$1
                r5 = r11
                java.nio.ByteBuffer r5 = (java.nio.ByteBuffer) r5
                long r1 = r7.J$0
                java.lang.Object r11 = r7.L$0
                r6 = r11
                kotlinx.coroutines.channels.u r6 = (kotlinx.coroutines.channels.u) r6
                kotlin.p.b(r35)
                r11 = r7
                r15 = r8
                r8 = r6
                r6 = r5
                r4 = r3
                r3 = r0
                r0 = r35
                goto L_0x0501
            L_0x0099:
                r1 = r14
                r2 = r11
                r4 = r11
                r6 = r14
                r8 = r14
                r9 = r14
                r10 = r11
                java.lang.Object r12 = r7.L$4
                kotlinx.coroutines.channels.u r12 = (kotlinx.coroutines.channels.u) r12
                long r10 = r7.J$2
                long r4 = r7.J$1
                java.lang.Object r13 = r7.L$3
                r9 = r13
                java.nio.ByteBuffer r9 = (java.nio.ByteBuffer) r9
                java.lang.Object r13 = r7.L$2
                r1 = r13
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r13 = r7.L$1
                r6 = r13
                java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
                long r2 = r7.J$0
                java.lang.Object r13 = r7.L$0
                r8 = r13
                kotlinx.coroutines.channels.u r8 = (kotlinx.coroutines.channels.u) r8
                kotlin.p.b(r35)
                r13 = r2
                r15 = r9
                r9 = r10
                r2 = r0
                r3 = r1
                r11 = r7
                r0 = r35
                r1 = r0
                goto L_0x04dc
            L_0x00cc:
                r1 = r14
                r2 = r11
                r4 = r14
                r5 = r14
                r6 = r14
                r10 = r14
                r15 = r14
                r16 = r14
                r17 = r14
                java.lang.Object r9 = r7.L$7
                r4 = r9
                io.ktor.http.cio.f r4 = (io.ktor.http.cio.f) r4
                java.lang.Object r9 = r7.L$6
                io.ktor.http.cio.j$b r9 = (io.ktor.http.cio.j.b) r9
                java.lang.Object r15 = r7.L$5
                r6 = r15
                kotlinx.coroutines.w r6 = (kotlinx.coroutines.w) r6
                java.lang.Object r15 = r7.L$4
                r5 = r15
                io.ktor.utils.io.f r5 = (io.ktor.utils.io.f) r5
                java.lang.Object r15 = r7.L$3
                java.nio.ByteBuffer r15 = (java.nio.ByteBuffer) r15
                java.lang.Object r13 = r7.L$2
                r1 = r13
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r13 = r7.L$1
                r10 = r13
                java.nio.ByteBuffer r10 = (java.nio.ByteBuffer) r10
                long r2 = r7.J$0
                java.lang.Object r13 = r7.L$0
                kotlinx.coroutines.channels.u r13 = (kotlinx.coroutines.channels.u) r13
                kotlin.p.b(r35)
                r11 = r7
                r12 = r13
                r13 = r2
                r2 = r0
                r3 = r1
                r0 = r35
                r1 = r0
                goto L_0x048f
            L_0x010b:
                r1 = r14
                r2 = r11
                r4 = r14
                r5 = r14
                r6 = r14
                r9 = r14
                r10 = r14
                r13 = r14
                r15 = r14
                java.lang.Object r11 = r7.L$7
                r4 = r11
                io.ktor.http.cio.f r4 = (io.ktor.http.cio.f) r4
                java.lang.Object r11 = r7.L$6
                r10 = r11
                io.ktor.http.cio.j$b r10 = (io.ktor.http.cio.j.b) r10
                java.lang.Object r11 = r7.L$5
                r6 = r11
                kotlinx.coroutines.w r6 = (kotlinx.coroutines.w) r6
                java.lang.Object r11 = r7.L$4
                r5 = r11
                io.ktor.utils.io.f r5 = (io.ktor.utils.io.f) r5
                java.lang.Object r11 = r7.L$3
                java.nio.ByteBuffer r11 = (java.nio.ByteBuffer) r11
                java.lang.Object r12 = r7.L$2
                r1 = r12
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r12 = r7.L$1
                r9 = r12
                java.nio.ByteBuffer r9 = (java.nio.ByteBuffer) r9
                long r2 = r7.J$0
                java.lang.Object r12 = r7.L$0
                kotlinx.coroutines.channels.u r12 = (kotlinx.coroutines.channels.u) r12
                kotlin.p.b(r35)     // Catch:{ all -> 0x0155 }
                r13 = r5
                r15 = r11
                r11 = r9
                r9 = r10
                r10 = r7
                r30 = r0
                r0 = r35
                r31 = r1
                r1 = r30
                r32 = r2
                r2 = r31
                r3 = r4
                r4 = r32
                goto L_0x0455
            L_0x0155:
                r0 = move-exception
                r8 = r0
                r14 = r12
                r0 = r35
                r12 = r10
                r10 = r7
                goto L_0x05cb
            L_0x015e:
                r1 = r14
                r2 = 0
                r4 = r2
                r2 = r14
                r3 = r14
                r6 = r14
                r9 = r14
                r10 = r14
                r11 = r14
                r12 = r14
                java.lang.Object r13 = r7.L$7
                r2 = r13
                io.ktor.http.cio.f r2 = (io.ktor.http.cio.f) r2
                java.lang.Object r13 = r7.L$6
                r10 = r13
                io.ktor.http.cio.j$b r10 = (io.ktor.http.cio.j.b) r10
                java.lang.Object r13 = r7.L$5
                r6 = r13
                kotlinx.coroutines.w r6 = (kotlinx.coroutines.w) r6
                java.lang.Object r13 = r7.L$4
                r3 = r13
                io.ktor.utils.io.f r3 = (io.ktor.utils.io.f) r3
                java.lang.Object r13 = r7.L$3
                r12 = r13
                java.nio.ByteBuffer r12 = (java.nio.ByteBuffer) r12
                java.lang.Object r13 = r7.L$2
                r1 = r13
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r13 = r7.L$1
                r9 = r13
                java.nio.ByteBuffer r9 = (java.nio.ByteBuffer) r9
                long r4 = r7.J$0
                java.lang.Object r13 = r7.L$0
                r11 = r13
                kotlinx.coroutines.channels.u r11 = (kotlinx.coroutines.channels.u) r11
                kotlin.p.b(r35)     // Catch:{ all -> 0x01a2 }
                r15 = r2
                r13 = r3
                r14 = r11
                r11 = r12
                r2 = r35
                r3 = r1
                r12 = r10
                r1 = r2
                r10 = r7
                goto L_0x040e
            L_0x01a2:
                r0 = move-exception
                r8 = r0
                r14 = r11
                r11 = r12
                r0 = r35
                r12 = r10
                r10 = r7
                r30 = r4
                r4 = r2
                r5 = r3
                r2 = r30
                goto L_0x05cb
            L_0x01b2:
                r1 = r14
                r2 = 0
                r4 = r2
                r2 = r14
                r3 = r14
                r6 = r14
                r9 = r14
                r10 = r14
                r11 = r14
                java.lang.Object r12 = r7.L$6
                r9 = r12
                io.ktor.http.cio.j$b r9 = (io.ktor.http.cio.j.b) r9
                java.lang.Object r12 = r7.L$5
                r3 = r12
                kotlinx.coroutines.w r3 = (kotlinx.coroutines.w) r3
                java.lang.Object r12 = r7.L$4
                r2 = r12
                io.ktor.utils.io.f r2 = (io.ktor.utils.io.f) r2
                java.lang.Object r12 = r7.L$3
                r11 = r12
                java.nio.ByteBuffer r11 = (java.nio.ByteBuffer) r11
                java.lang.Object r12 = r7.L$2
                r1 = r12
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r12 = r7.L$1
                r6 = r12
                java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
                long r4 = r7.J$0
                java.lang.Object r12 = r7.L$0
                r10 = r12
                kotlinx.coroutines.channels.u r10 = (kotlinx.coroutines.channels.u) r10
                kotlin.p.b(r35)
                r13 = r2
                r12 = r9
                r2 = r1
                r9 = r6
                r1 = r35
                r6 = r3
                r3 = r10
                r10 = r7
                goto L_0x03e7
            L_0x01ef:
                r1 = r14
                r2 = 0
                r4 = r2
                r2 = r14
                r3 = r14
                r6 = r14
                java.lang.Object r9 = r7.L$3
                r6 = r9
                java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
                java.lang.Object r9 = r7.L$2
                r1 = r9
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r9 = r7.L$1
                r2 = r9
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                long r4 = r7.J$0
                java.lang.Object r9 = r7.L$0
                r3 = r9
                kotlinx.coroutines.channels.u r3 = (kotlinx.coroutines.channels.u) r3
                kotlin.p.b(r35)
                r9 = r6
                r10 = r7
                r5 = r4
                r4 = r3
                r3 = r2
                r2 = r1
                r1 = r0
                r0 = r35
                goto L_0x03b4
            L_0x021a:
                r1 = r14
                r2 = 0
                r4 = r2
                r2 = r14
                r3 = r14
                r6 = r14
                java.lang.Object r9 = r7.L$3
                r6 = r9
                java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
                java.lang.Object r9 = r7.L$2
                r1 = r9
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r9 = r7.L$1
                r2 = r9
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                long r4 = r7.J$0
                java.lang.Object r9 = r7.L$0
                r3 = r9
                kotlinx.coroutines.channels.u r3 = (kotlinx.coroutines.channels.u) r3
                kotlin.p.b(r35)
                r11 = r35
                r9 = r6
                r10 = r7
                r5 = r4
                r4 = r3
                r3 = r2
                r2 = r1
                r1 = r0
                r0 = r11
                goto L_0x0392
            L_0x0246:
                r1 = r14
                r2 = 0
                r4 = r2
                r2 = r14
                r3 = r14
                r6 = r14
                java.lang.Object r9 = r7.L$3
                r6 = r9
                java.nio.ByteBuffer r6 = (java.nio.ByteBuffer) r6
                java.lang.Object r9 = r7.L$2
                r1 = r9
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r9 = r7.L$1
                r2 = r9
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                long r4 = r7.J$0
                java.lang.Object r9 = r7.L$0
                r3 = r9
                kotlinx.coroutines.channels.u r3 = (kotlinx.coroutines.channels.u) r3
                kotlin.p.b(r35)
                r9 = r6
                r10 = r7
                r5 = r4
                r4 = r3
                r3 = r2
                r2 = r1
                r1 = r0
                r0 = r35
                goto L_0x0375
            L_0x0271:
                r1 = r14
                r2 = 0
                r4 = r2
                r2 = r14
                r3 = r14
                java.lang.Object r6 = r7.L$2
                r1 = r6
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r6 = r7.L$1
                r2 = r6
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                long r4 = r7.J$0
                java.lang.Object r6 = r7.L$0
                r3 = r6
                kotlinx.coroutines.channels.u r3 = (kotlinx.coroutines.channels.u) r3
                kotlin.p.b(r35)
                r6 = r35
                goto L_0x033b
            L_0x028f:
                r1 = r14
                r2 = 0
                r4 = r2
                r2 = r14
                r3 = r14
                java.lang.Object r6 = r7.L$2
                r1 = r6
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r6 = r7.L$1
                r2 = r6
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                long r4 = r7.J$0
                java.lang.Object r6 = r7.L$0
                r3 = r6
                kotlinx.coroutines.channels.u r3 = (kotlinx.coroutines.channels.u) r3
                kotlin.p.b(r35)
                goto L_0x0327
            L_0x02ab:
                r1 = r14
                r2 = 0
                r4 = r2
                r2 = r14
                r3 = r14
                java.lang.Object r6 = r7.L$2
                r1 = r6
                io.ktor.utils.io.core.n r1 = (io.ktor.utils.io.core.n) r1
                java.lang.Object r6 = r7.L$1
                r2 = r6
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                long r4 = r7.J$0
                java.lang.Object r6 = r7.L$0
                r3 = r6
                kotlinx.coroutines.channels.u r3 = (kotlinx.coroutines.channels.u) r3
                kotlin.p.b(r35)
                goto L_0x0307
            L_0x02c6:
                kotlin.p.b(r35)
                kotlinx.coroutines.channels.u r9 = r7.p$
                io.ktor.utils.io.i r1 = r7.$input
                long r11 = r1.t()
                java.nio.ByteBuffer r1 = r7.$boundaryPrefixed
                java.nio.ByteBuffer r1 = r1.duplicate()
                if (r1 != 0) goto L_0x02dc
                kotlin.jvm.internal.k.n()
            L_0x02dc:
                r2 = r1
                r3 = 0
                r2.position(r10)
                r13 = r1
                r1 = 0
                r2 = 1
                io.ktor.utils.io.core.n r15 = io.ktor.utils.io.core.f0.b(r1, r2, r14)
                io.ktor.utils.io.i r3 = r7.$input
                r4 = 8192(0x2000, double:4.0474E-320)
                r7.L$0 = r9
                r7.J$0 = r11
                r7.L$1 = r13
                r7.L$2 = r15
                r7.label = r2
                r1 = r13
                r2 = r3
                r3 = r15
                r6 = r34
                java.lang.Object r1 = io.ktor.http.cio.k.c("preamble/prologue", r1, r2, new io.ktor.http.cio.k.f(r3, (kotlin.coroutines.d) null), r4, r6)
                if (r1 != r0) goto L_0x0303
                return r0
            L_0x0303:
                r3 = r9
                r4 = r11
                r2 = r13
                r1 = r15
            L_0x0307:
                int r6 = r1.f1()
                if (r6 <= 0) goto L_0x0327
                io.ktor.http.cio.j$c r6 = new io.ktor.http.cio.j$c
                io.ktor.utils.io.core.q r9 = r1.e1()
                r6.<init>(r9)
                r7.L$0 = r3
                r7.J$0 = r4
                r7.L$1 = r2
                r7.L$2 = r1
                r7.label = r10
                java.lang.Object r6 = r3.D(r6, r7)
                if (r6 != r0) goto L_0x0327
                return r0
            L_0x0327:
                io.ktor.utils.io.i r6 = r7.$input
                r7.L$0 = r3
                r7.J$0 = r4
                r7.L$1 = r2
                r7.L$2 = r1
                r9 = 3
                r7.label = r9
                java.lang.Object r6 = io.ktor.http.cio.k.l(r2, r6, r7)
                if (r6 != r0) goto L_0x033b
                return r0
            L_0x033b:
                java.lang.Boolean r6 = (java.lang.Boolean) r6
                boolean r6 = r6.booleanValue()
                if (r6 == 0) goto L_0x0346
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0346:
                java.nio.ByteBuffer r6 = io.ktor.http.cio.k.b
                java.nio.ByteBuffer r6 = r6.duplicate()
                r9 = r6
                r10 = r7
                r5 = r4
                r4 = r3
                r3 = r2
                r2 = r1
                r1 = r0
                r0 = r35
            L_0x0357:
                io.ktor.utils.io.i r11 = r10.$input
                java.nio.ByteBuffer r12 = io.ktor.http.cio.k.a
                kotlin.jvm.internal.k.b(r9, r8)
                r10.L$0 = r4
                r10.J$0 = r5
                r10.L$1 = r3
                r10.L$2 = r2
                r10.L$3 = r9
                r13 = 4
                r10.label = r13
                java.lang.Object r11 = io.ktor.utils.io.r.d(r11, r12, r9, r10)
                if (r11 != r1) goto L_0x0375
                return r1
            L_0x0375:
                io.ktor.utils.io.i r11 = r10.$input
                java.nio.ByteBuffer r12 = io.ktor.http.cio.k.a
                kotlin.jvm.internal.k.b(r9, r8)
                r10.L$0 = r4
                r10.J$0 = r5
                r10.L$1 = r3
                r10.L$2 = r2
                r10.L$3 = r9
                r13 = 5
                r10.label = r13
                java.lang.Object r11 = io.ktor.utils.io.r.d(r11, r12, r9, r10)
                if (r11 != r1) goto L_0x0392
                return r1
            L_0x0392:
                java.lang.Number r11 = (java.lang.Number) r11
                int r11 = r11.intValue()
                if (r11 != 0) goto L_0x05dc
                io.ktor.utils.io.i r11 = r10.$input
                java.nio.ByteBuffer r12 = io.ktor.http.cio.k.a
                r10.L$0 = r4
                r10.J$0 = r5
                r10.L$1 = r3
                r10.L$2 = r2
                r10.L$3 = r9
                r13 = 6
                r10.label = r13
                java.lang.Object r11 = io.ktor.utils.io.r.f(r11, r12, r10)
                if (r11 != r1) goto L_0x03b4
                return r1
            L_0x03b4:
                r11 = 0
                r12 = 1
                io.ktor.utils.io.f r13 = io.ktor.utils.io.g.b(r11, r12, r14)
                kotlinx.coroutines.w r15 = kotlinx.coroutines.y.b(r14, r12, r14)
                io.ktor.http.cio.j$b r12 = new io.ktor.http.cio.j$b
                r12.<init>(r15, r13)
                r10.L$0 = r4
                r10.J$0 = r5
                r10.L$1 = r3
                r10.L$2 = r2
                r10.L$3 = r9
                r10.L$4 = r13
                r10.L$5 = r15
                r10.L$6 = r12
                r11 = 7
                r10.label = r11
                java.lang.Object r11 = r4.D(r12, r10)
                if (r11 != r1) goto L_0x03dd
                return r1
            L_0x03dd:
                r11 = r9
                r9 = r3
                r3 = r4
                r4 = r5
                r6 = r15
                r30 = r1
                r1 = r0
                r0 = r30
            L_0x03e7:
                r15 = r14
                io.ktor.utils.io.i r14 = r10.$input     // Catch:{ all -> 0x05c0 }
                r10.L$0 = r3     // Catch:{ all -> 0x05c0 }
                r10.J$0 = r4     // Catch:{ all -> 0x05c0 }
                r10.L$1 = r9     // Catch:{ all -> 0x05c0 }
                r10.L$2 = r2     // Catch:{ all -> 0x05c0 }
                r10.L$3 = r11     // Catch:{ all -> 0x05c0 }
                r10.L$4 = r13     // Catch:{ all -> 0x05c0 }
                r10.L$5 = r6     // Catch:{ all -> 0x05c0 }
                r10.L$6 = r12     // Catch:{ all -> 0x05c0 }
                r10.L$7 = r15     // Catch:{ all -> 0x05c0 }
                r35 = r1
                r1 = 8
                r10.label = r1     // Catch:{ all -> 0x05b6 }
                java.lang.Object r1 = io.ktor.http.cio.k.j(r14, r10)     // Catch:{ all -> 0x05b6 }
                if (r1 != r0) goto L_0x040a
                return r0
            L_0x040a:
                r14 = r3
                r3 = r2
                r2 = r35
            L_0x040e:
                io.ktor.http.cio.f r1 = (io.ktor.http.cio.f) r1     // Catch:{ all -> 0x05a8 }
                boolean r15 = r6.x(r1)     // Catch:{ all -> 0x059a }
                if (r15 == 0) goto L_0x0581
                java.nio.ByteBuffer r15 = r10.$boundaryPrefixed     // Catch:{ all -> 0x059a }
                r35 = r2
                io.ktor.utils.io.i r2 = r10.$input     // Catch:{ all -> 0x0574 }
                r24 = 0
                r27 = 16
                r28 = 0
                r10.L$0 = r14     // Catch:{ all -> 0x0574 }
                r10.J$0 = r4     // Catch:{ all -> 0x0574 }
                r10.L$1 = r9     // Catch:{ all -> 0x0574 }
                r10.L$2 = r3     // Catch:{ all -> 0x0574 }
                r10.L$3 = r11     // Catch:{ all -> 0x0574 }
                r10.L$4 = r13     // Catch:{ all -> 0x0574 }
                r10.L$5 = r6     // Catch:{ all -> 0x0574 }
                r10.L$6 = r12     // Catch:{ all -> 0x0574 }
                r10.L$7 = r1     // Catch:{ all -> 0x0574 }
                r29 = r3
                r3 = 9
                r10.label = r3     // Catch:{ all -> 0x0590 }
                r20 = r15
                r21 = r2
                r22 = r13
                r23 = r1
                r26 = r10
                java.lang.Object r2 = io.ktor.http.cio.k.i(r20, r21, r22, r23, r24, r26, r27, r28)     // Catch:{ all -> 0x0590 }
                if (r2 != r0) goto L_0x044b
                return r0
            L_0x044b:
                r3 = r1
                r15 = r11
                r2 = r29
                r1 = r0
                r11 = r9
                r9 = r12
                r12 = r14
                r0 = r35
            L_0x0455:
                io.ktor.utils.io.m.a(r13)
                java.nio.ByteBuffer r14 = r10.$boundaryPrefixed
                r35 = r0
                io.ktor.utils.io.i r0 = r10.$input
                r10.L$0 = r12
                r10.J$0 = r4
                r10.L$1 = r11
                r10.L$2 = r2
                r10.L$3 = r15
                r10.L$4 = r13
                r10.L$5 = r6
                r10.L$6 = r9
                r10.L$7 = r3
                r20 = r2
                r2 = 10
                r10.label = r2
                java.lang.Object r0 = io.ktor.http.cio.k.l(r14, r0, r10)
                if (r0 != r1) goto L_0x047f
                return r1
            L_0x047f:
                r2 = r1
                r1 = r35
                r30 = r4
                r4 = r3
                r5 = r13
                r3 = r20
                r13 = r30
                r32 = r11
                r11 = r10
                r10 = r32
            L_0x048f:
                java.lang.Boolean r0 = (java.lang.Boolean) r0
                boolean r0 = r0.booleanValue()
                if (r0 == 0) goto L_0x0562
                java.lang.Long r0 = r11.$totalLength
                if (r0 == 0) goto L_0x050f
                io.ktor.utils.io.i r0 = r11.$input
                long r4 = r0.t()
                long r4 = r4 - r13
                java.lang.Long r0 = r11.$totalLength
                long r8 = r0.longValue()
                long r8 = r8 - r4
                r0 = 2147483647(0x7fffffff, float:NaN)
                r35 = r1
                long r0 = (long) r0
                int r0 = (r8 > r0 ? 1 : (r8 == r0 ? 0 : -1))
                if (r0 > 0) goto L_0x0507
                r18 = 0
                int r0 = (r8 > r18 ? 1 : (r8 == r18 ? 0 : -1))
                if (r0 <= 0) goto L_0x0504
                io.ktor.utils.io.i r0 = r11.$input
                int r1 = (int) r8
                r11.L$0 = r12
                r11.J$0 = r13
                r11.L$1 = r10
                r11.L$2 = r3
                r11.L$3 = r15
                r11.J$1 = r4
                r11.J$2 = r8
                r11.L$4 = r12
                r6 = 11
                r11.label = r6
                java.lang.Object r0 = io.ktor.utils.io.k.e(r0, r1, r11)
                if (r0 != r2) goto L_0x04d7
                return r2
            L_0x04d7:
                r1 = r35
                r6 = r10
                r9 = r8
                r8 = r12
            L_0x04dc:
                io.ktor.utils.io.core.q r0 = (io.ktor.utils.io.core.q) r0
                r35 = r1
                io.ktor.http.cio.j$a r1 = new io.ktor.http.cio.j$a
                r1.<init>(r0)
                r11.L$0 = r8
                r11.J$0 = r13
                r11.L$1 = r6
                r11.L$2 = r3
                r11.L$3 = r15
                r11.J$1 = r4
                r11.J$2 = r9
                r0 = 12
                r11.label = r0
                java.lang.Object r0 = r12.D(r1, r11)
                if (r0 != r2) goto L_0x04fe
                return r2
            L_0x04fe:
                r0 = r35
                r1 = r13
            L_0x0501:
                r1 = r0
                r12 = r8
                goto L_0x055f
            L_0x0504:
                r1 = r35
                goto L_0x055f
            L_0x0507:
                java.io.IOException r0 = new java.io.IOException
                java.lang.String r1 = "Failed to parse multipart: prologue is too long"
                r0.<init>(r1)
                throw r0
            L_0x050f:
                r35 = r1
                io.ktor.utils.io.i r0 = r11.$input
                r11.L$0 = r12
                r11.J$0 = r13
                r11.L$1 = r10
                r11.L$2 = r3
                r11.L$3 = r15
                r1 = 13
                r11.label = r1
                java.lang.Object r0 = io.ktor.utils.io.k.g(r0, r11)
                if (r0 != r2) goto L_0x0528
                return r2
            L_0x0528:
                r1 = r35
                r4 = r10
                r5 = r12
                r6 = r15
            L_0x052d:
                io.ktor.utils.io.core.q r0 = (io.ktor.utils.io.core.q) r0
                r8 = r0
                r9 = 0
                boolean r10 = r8.w0()
                r17 = 1
                r8 = r10 ^ 1
                if (r8 == 0) goto L_0x055e
                io.ktor.http.cio.j$a r8 = new io.ktor.http.cio.j$a
                r8.<init>(r0)
                r11.L$0 = r5
                r11.J$0 = r13
                r11.L$1 = r4
                r11.L$2 = r3
                r11.L$3 = r6
                r11.L$4 = r0
                r9 = 14
                r11.label = r9
                java.lang.Object r8 = r5.D(r8, r11)
                if (r8 != r2) goto L_0x0557
                return r2
            L_0x0557:
                r30 = r3
                r3 = r0
                r0 = r30
            L_0x055c:
                r12 = r5
                goto L_0x055f
            L_0x055e:
                r12 = r5
            L_0x055f:
                kotlin.x r0 = kotlin.x.a
                return r0
            L_0x0562:
                r35 = r1
                r17 = 1
                r18 = 0
                r0 = r35
                r1 = r2
                r2 = r3
                r3 = r10
                r10 = r11
                r4 = r12
                r5 = r13
                r9 = r15
                r14 = 0
                goto L_0x0357
            L_0x0574:
                r0 = move-exception
                r29 = r3
                r8 = r0
                r2 = r4
                r5 = r13
                r0 = r35
                r4 = r1
                r1 = r29
                goto L_0x05cb
            L_0x0581:
                r35 = r2
                r29 = r3
                r1.j()     // Catch:{ all -> 0x0590 }
                java.util.concurrent.CancellationException r0 = new java.util.concurrent.CancellationException     // Catch:{ all -> 0x0590 }
                java.lang.String r2 = "Multipart processing has been cancelled"
                r0.<init>(r2)     // Catch:{ all -> 0x0590 }
                throw r0     // Catch:{ all -> 0x0590 }
            L_0x0590:
                r0 = move-exception
                r8 = r0
                r2 = r4
                r5 = r13
                r0 = r35
                r4 = r1
                r1 = r29
                goto L_0x05cb
            L_0x059a:
                r0 = move-exception
                r35 = r2
                r29 = r3
                r8 = r0
                r2 = r4
                r5 = r13
                r0 = r35
                r4 = r1
                r1 = r29
                goto L_0x05cb
            L_0x05a8:
                r0 = move-exception
                r35 = r2
                r29 = r3
                r8 = r0
                r2 = r4
                r5 = r13
                r4 = r15
                r1 = r29
                r0 = r35
                goto L_0x05cb
            L_0x05b6:
                r0 = move-exception
                r8 = r0
                r1 = r2
                r14 = r3
                r2 = r4
                r5 = r13
                r4 = r15
                r0 = r35
                goto L_0x05cb
            L_0x05c0:
                r0 = move-exception
                r35 = r1
                r8 = r0
                r1 = r2
                r14 = r3
                r2 = r4
                r5 = r13
                r4 = r15
                r0 = r35
            L_0x05cb:
                boolean r13 = r6.a(r8)
                if (r13 == 0) goto L_0x05d8
                if (r4 == 0) goto L_0x05d8
                r4.j()
                kotlin.x r13 = kotlin.x.a
            L_0x05d8:
                r5.d(r8)
                throw r8
            L_0x05dc:
                java.io.IOException r1 = new java.io.IOException
                java.lang.String r8 = "Failed to parse multipart: boundary line is too long"
                r1.<init>(r8)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.k.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @NotNull
    public static final kotlinx.coroutines.channels.w<j> g(@NotNull o0 $this$parseMultipart, @NotNull ByteBuffer boundaryPrefixed, @NotNull i input, @Nullable Long totalLength) {
        kotlin.jvm.internal.k.f($this$parseMultipart, "$this$parseMultipart");
        kotlin.jvm.internal.k.f(boundaryPrefixed, "boundaryPrefixed");
        kotlin.jvm.internal.k.f(input, "input");
        return s.d($this$parseMultipart, (kotlin.coroutines.g) null, 0, new b(input, boundaryPrefixed, totalLength, (kotlin.coroutines.d) null), 3, (Object) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v9, resolved type: kotlin.jvm.functions.p<? super java.nio.ByteBuffer, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: io.ktor.utils.io.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v9, resolved type: java.nio.ByteBuffer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v9, resolved type: java.lang.String} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00d7 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00d8  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final /* synthetic */ java.lang.Object c(@org.jetbrains.annotations.NotNull java.lang.String r19, @org.jetbrains.annotations.NotNull java.nio.ByteBuffer r20, @org.jetbrains.annotations.NotNull io.ktor.utils.io.i r21, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.p<? super java.nio.ByteBuffer, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends java.lang.Object> r22, long r23, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Long> r25) {
        /*
            r1 = r25
            boolean r0 = r1 instanceof io.ktor.http.cio.k.a
            if (r0 == 0) goto L_0x0015
            r0 = r1
            io.ktor.http.cio.k$a r0 = (io.ktor.http.cio.k.a) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0015
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001a
        L_0x0015:
            io.ktor.http.cio.k$a r0 = new io.ktor.http.cio.k$a
            r0.<init>(r1)
        L_0x001a:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r2.label
            r5 = 0
            r7 = 0
            switch(r4) {
                case 0: goto L_0x009e;
                case 1: goto L_0x0069;
                case 2: goto L_0x0032;
                default: goto L_0x0029;
            }
        L_0x0029:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0032:
            r4 = 0
            int r4 = r2.I$0
            long r5 = r2.J$1
            java.lang.Object r8 = r2.L$4
            r7 = r8
            java.nio.ByteBuffer r7 = (java.nio.ByteBuffer) r7
            long r8 = r2.J$0
            java.lang.Object r10 = r2.L$3
            kotlin.jvm.functions.p r10 = (kotlin.jvm.functions.p) r10
            java.lang.Object r11 = r2.L$2
            io.ktor.utils.io.i r11 = (io.ktor.utils.io.i) r11
            java.lang.Object r12 = r2.L$1
            java.nio.ByteBuffer r12 = (java.nio.ByteBuffer) r12
            java.lang.Object r13 = r2.L$0
            java.lang.String r13 = (java.lang.String) r13
            kotlin.p.b(r3)     // Catch:{ all -> 0x0060 }
            r16 = r7
            r7 = r1
            r1 = r13
            r13 = r16
            r17 = r11
            r11 = r2
            r2 = r12
            r12 = r3
            r3 = r17
            goto L_0x0128
        L_0x0060:
            r0 = move-exception
            r16 = r7
            r7 = r1
            r1 = r13
            r13 = r16
            goto L_0x0180
        L_0x0069:
            r4 = r5
            r6 = r7
            long r4 = r2.J$1
            java.lang.Object r7 = r2.L$4
            java.nio.ByteBuffer r7 = (java.nio.ByteBuffer) r7
            long r8 = r2.J$0
            java.lang.Object r6 = r2.L$3
            r10 = r6
            kotlin.jvm.functions.p r10 = (kotlin.jvm.functions.p) r10
            java.lang.Object r6 = r2.L$2
            r11 = r6
            io.ktor.utils.io.i r11 = (io.ktor.utils.io.i) r11
            java.lang.Object r6 = r2.L$1
            r12 = r6
            java.nio.ByteBuffer r12 = (java.nio.ByteBuffer) r12
            java.lang.Object r6 = r2.L$0
            r13 = r6
            java.lang.String r13 = (java.lang.String) r13
            kotlin.p.b(r3)     // Catch:{ all -> 0x0094 }
            r5 = r4
            r4 = r10
            r16 = r8
            r9 = r3
            r8 = r11
            r10 = r16
            goto L_0x00e7
        L_0x0094:
            r0 = move-exception
            r5 = r4
            r16 = r7
            r7 = r1
            r1 = r13
            r13 = r16
            goto L_0x0180
        L_0x009e:
            kotlin.p.b(r3)
            io.ktor.utils.io.pool.d r4 = io.ktor.network.util.b.a()
            java.lang.Object r4 = r4.p0()
            java.nio.ByteBuffer r4 = (java.nio.ByteBuffer) r4
            r5 = 0
            r7 = r1
            r8 = r2
            r9 = r3
            r12 = r4
            r10 = r5
            r1 = r19
            r2 = r20
            r3 = r21
            r4 = r22
            r5 = r23
        L_0x00bc:
            r12.clear()     // Catch:{ all -> 0x0174 }
            r8.L$0 = r1     // Catch:{ all -> 0x0174 }
            r8.L$1 = r2     // Catch:{ all -> 0x0174 }
            r8.L$2 = r3     // Catch:{ all -> 0x0174 }
            r8.L$3 = r4     // Catch:{ all -> 0x0174 }
            r8.J$0 = r5     // Catch:{ all -> 0x0174 }
            r8.L$4 = r12     // Catch:{ all -> 0x0174 }
            r8.J$1 = r10     // Catch:{ all -> 0x0174 }
            r13 = 1
            r8.label = r13     // Catch:{ all -> 0x0174 }
            java.lang.Object r13 = io.ktor.utils.io.r.d(r3, r2, r12, r8)     // Catch:{ all -> 0x0174 }
            if (r13 != r0) goto L_0x00d8
            return r0
        L_0x00d8:
            r16 = r13
            r13 = r1
            r1 = r7
            r7 = r12
            r12 = r2
            r2 = r8
            r8 = r3
            r3 = r16
            r17 = r5
            r5 = r10
            r10 = r17
        L_0x00e7:
            java.lang.Number r3 = (java.lang.Number) r3     // Catch:{ all -> 0x0165 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x0165 }
            if (r3 > 0) goto L_0x00fb
            java.lang.Long r0 = kotlin.coroutines.jvm.internal.b.d(r5)     // Catch:{ all -> 0x0165 }
            io.ktor.utils.io.pool.d r3 = io.ktor.network.util.b.a()
            r3.N0(r7)
            return r0
        L_0x00fb:
            r7.flip()     // Catch:{ all -> 0x0165 }
            r2.L$0 = r13     // Catch:{ all -> 0x0165 }
            r2.L$1 = r12     // Catch:{ all -> 0x0165 }
            r2.L$2 = r8     // Catch:{ all -> 0x0165 }
            r2.L$3 = r4     // Catch:{ all -> 0x0165 }
            r2.J$0 = r10     // Catch:{ all -> 0x0165 }
            r2.L$4 = r7     // Catch:{ all -> 0x0165 }
            r2.J$1 = r5     // Catch:{ all -> 0x0165 }
            r2.I$0 = r3     // Catch:{ all -> 0x0165 }
            r14 = 2
            r2.label = r14     // Catch:{ all -> 0x0165 }
            java.lang.Object r14 = r4.invoke(r7, r2)     // Catch:{ all -> 0x0165 }
            if (r14 != r0) goto L_0x0118
            return r0
        L_0x0118:
            r16 = r7
            r7 = r1
            r1 = r13
            r13 = r16
            r17 = r10
            r11 = r2
            r10 = r4
            r2 = r12
            r4 = r3
            r3 = r8
            r12 = r9
            r8 = r17
        L_0x0128:
            long r14 = (long) r4
            long r5 = r5 + r14
            int r14 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r14 > 0) goto L_0x0138
            r4 = r10
            r16 = r8
            r8 = r11
            r10 = r5
            r5 = r16
            r9 = r12
            r12 = r13
            goto L_0x00bc
        L_0x0138:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x015c }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x015c }
            r14.<init>()     // Catch:{ all -> 0x015c }
            java.lang.String r15 = "Multipart "
            r14.append(r15)     // Catch:{ all -> 0x015c }
            r14.append(r1)     // Catch:{ all -> 0x015c }
            java.lang.String r15 = " limit of "
            r14.append(r15)     // Catch:{ all -> 0x015c }
            r14.append(r8)     // Catch:{ all -> 0x015c }
            java.lang.String r15 = " bytes exceeded"
            r14.append(r15)     // Catch:{ all -> 0x015c }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x015c }
            r0.<init>(r14)     // Catch:{ all -> 0x015c }
            throw r0     // Catch:{ all -> 0x015c }
        L_0x015c:
            r0 = move-exception
            r16 = r12
            r12 = r2
            r2 = r11
            r11 = r3
            r3 = r16
            goto L_0x0180
        L_0x0165:
            r0 = move-exception
            r3 = r9
            r16 = r7
            r7 = r1
            r1 = r13
            r13 = r16
            r17 = r10
            r10 = r4
            r11 = r8
            r8 = r17
            goto L_0x0180
        L_0x0174:
            r0 = move-exception
            r13 = r12
            r12 = r2
            r2 = r8
            r16 = r10
            r11 = r3
            r10 = r4
            r3 = r9
            r8 = r5
            r5 = r16
        L_0x0180:
            io.ktor.utils.io.pool.d r4 = io.ktor.network.util.b.a()
            r4.N0(r13)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.k.c(java.lang.String, java.nio.ByteBuffer, io.ktor.utils.io.i, kotlin.jvm.functions.p, long, kotlin.coroutines.d):java.lang.Object");
    }

    private static final int d(CharSequence contentType) {
        int state = 0;
        int paramNameCount = 0;
        int length = contentType.length();
        for (int i = 0; i < length; i++) {
            char ch = contentType.charAt(i);
            switch (state) {
                case 0:
                    if (ch != ';') {
                        break;
                    } else {
                        state = 1;
                        paramNameCount = 0;
                        break;
                    }
                case 1:
                    if (ch == '=') {
                        state = 2;
                        break;
                    } else if (ch == ';') {
                        paramNameCount = 0;
                        break;
                    } else if (ch == ',') {
                        state = 0;
                        break;
                    } else if (ch == ' ') {
                        continue;
                    } else if (paramNameCount != 0 || !kotlin.text.x.J0(contentType, "boundary=", i, true)) {
                        paramNameCount++;
                        break;
                    } else {
                        return i;
                    }
                    break;
                case 2:
                    switch (ch) {
                        case '\"':
                            state = 3;
                            break;
                        case ',':
                            state = 0;
                            break;
                        case ';':
                            state = 1;
                            paramNameCount = 0;
                            break;
                    }
                case 3:
                    if (ch != '\"') {
                        if (ch != '\\') {
                            break;
                        } else {
                            state = 4;
                            break;
                        }
                    } else {
                        state = 1;
                        paramNameCount = 0;
                        break;
                    }
                case 4:
                    state = 3;
                    break;
            }
        }
        return -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a0, code lost:
        r5 = r5 + 1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00da A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00db  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.nio.ByteBuffer e(@org.jetbrains.annotations.NotNull java.lang.CharSequence r10) {
        /*
            java.lang.String r0 = "contentType"
            kotlin.jvm.internal.k.f(r10, r0)
            int r0 = d(r10)
            r1 = -1
            if (r0 == r1) goto L_0x00e3
            int r1 = r0 + 9
            r2 = 74
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.allocate(r2)
            java.lang.String r3 = "ByteBuffer.allocate(74)"
            kotlin.jvm.internal.k.b(r2, r3)
            r3 = 13
            r2.put(r3)
            r3 = 10
            r2.put(r3)
            r3 = 45
            r2.put(r3)
            r2.put(r3)
            r3 = 0
            int r4 = r10.length()
            r5 = r1
        L_0x0031:
            if (r5 >= r4) goto L_0x00d0
            char r6 = r10.charAt(r5)
            r7 = 65535(0xffff, float:9.1834E-41)
            r8 = r6 & r7
            r7 = r7 & r8
            r9 = 127(0x7f, float:1.78E-43)
            if (r7 > r9) goto L_0x00a5
            java.lang.String r7 = "Failed to parse multipart: boundary shouldn't be longer than 70 characters"
            switch(r3) {
                case 0: goto L_0x0095;
                case 1: goto L_0x0076;
                case 2: goto L_0x0059;
                case 3: goto L_0x0047;
                default: goto L_0x0046;
            }
        L_0x0046:
            goto L_0x00a0
        L_0x0047:
            boolean r9 = r2.hasRemaining()
            if (r9 == 0) goto L_0x0053
            byte r7 = (byte) r8
            r2.put(r7)
            r3 = 2
            goto L_0x00a0
        L_0x0053:
            java.io.IOException r4 = new java.io.IOException
            r4.<init>(r7)
            throw r4
        L_0x0059:
            r9 = 92
            if (r6 != r9) goto L_0x005f
            r3 = 3
            goto L_0x00a0
        L_0x005f:
            r9 = 34
            if (r6 != r9) goto L_0x0065
            goto L_0x00d0
        L_0x0065:
            boolean r9 = r2.hasRemaining()
            if (r9 == 0) goto L_0x0070
            byte r7 = (byte) r8
            r2.put(r7)
            goto L_0x00a0
        L_0x0070:
            java.io.IOException r4 = new java.io.IOException
            r4.<init>(r7)
            throw r4
        L_0x0076:
            r9 = 32
            if (r6 == r9) goto L_0x0094
            r9 = 44
            if (r6 == r9) goto L_0x0094
            r9 = 59
            if (r6 != r9) goto L_0x0083
            goto L_0x0094
        L_0x0083:
            boolean r9 = r2.hasRemaining()
            if (r9 == 0) goto L_0x008e
            byte r7 = (byte) r8
            r2.put(r7)
            goto L_0x00a0
        L_0x008e:
            java.io.IOException r4 = new java.io.IOException
            r4.<init>(r7)
            throw r4
        L_0x0094:
            goto L_0x00d0
        L_0x0095:
            switch(r6) {
                case 32: goto L_0x00a0;
                case 34: goto L_0x009f;
                case 44: goto L_0x009e;
                case 59: goto L_0x009e;
                default: goto L_0x0098;
            }
        L_0x0098:
            r3 = 1
            byte r7 = (byte) r8
            r2.put(r7)
            goto L_0x00a0
        L_0x009e:
            goto L_0x00d0
        L_0x009f:
            r3 = 2
        L_0x00a0:
            int r5 = r5 + 1
            goto L_0x0031
        L_0x00a5:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r7 = "Failed to parse multipart: wrong boundary byte 0x"
            r4.append(r7)
            r7 = 16
            int r7 = kotlin.text.a.a(r7)
            java.lang.String r7 = java.lang.Integer.toString(r8, r7)
            java.lang.String r9 = "java.lang.Integer.toStri…(this, checkRadix(radix))"
            kotlin.jvm.internal.k.b(r7, r9)
            r4.append(r7)
            java.lang.String r7 = " - should be 7bit character"
            r4.append(r7)
            java.lang.String r4 = r4.toString()
            java.io.IOException r7 = new java.io.IOException
            r7.<init>(r4)
            throw r7
        L_0x00d0:
            r2.flip()
            int r4 = r2.remaining()
            r5 = 4
            if (r4 == r5) goto L_0x00db
            return r2
        L_0x00db:
            java.io.IOException r4 = new java.io.IOException
            java.lang.String r5 = "Empty multipart boundary is not allowed"
            r4.<init>(r5)
            throw r4
        L_0x00e3:
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r2 = "Failed to parse multipart: Content-Type's boundary parameter is missing"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.k.e(java.lang.CharSequence):java.nio.ByteBuffer");
    }
}
