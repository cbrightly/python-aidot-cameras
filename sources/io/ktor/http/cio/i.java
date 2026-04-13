package io.ktor.http.cio;

import io.ktor.http.cio.internals.e;
import io.ktor.http.u;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.text.x;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpParser.kt */
public final class i {
    private static final io.ktor.http.cio.internals.a<String> a = io.ktor.http.cio.internals.a.a.a(q.j(Constants.HTTP_10, Constants.HTTP_11));

    @f(c = "io.ktor.http.cio.HttpParserKt", f = "HttpParser.kt", l = {102}, m = "parseHeaders")
    /* compiled from: HttpParser.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return i.g((io.ktor.utils.io.i) null, (io.ktor.http.cio.internals.b) null, (e) null, this);
        }
    }

    @f(c = "io.ktor.http.cio.HttpParserKt", f = "HttpParser.kt", l = {28, 46}, m = "parseRequest")
    /* compiled from: HttpParser.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;

        c(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return i.k((io.ktor.utils.io.i) null, this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v12, resolved type: io.ktor.http.cio.internals.b} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: io.ktor.utils.io.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: java.lang.CharSequence} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v17, resolved type: java.lang.CharSequence} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: io.ktor.http.u} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v16, resolved type: io.ktor.http.cio.internals.b} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: io.ktor.utils.io.i} */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r3.L$0 = r1;
        r3.L$1 = r8;
        r3.L$2 = r9;
        r3.label = 1;
        r10 = r1.i(r8, 8192, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a1, code lost:
        if (r10 != r0) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a3, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a4, code lost:
        r18 = r4;
        r4 = r3;
        r3 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b2, code lost:
        if (((java.lang.Boolean) r3).booleanValue() != false) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b4, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b5, code lost:
        r10.c(r9.length());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c4, code lost:
        if (r10.b() != r10.a()) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c6, code lost:
        r3 = r4;
        r4 = r8;
        r8 = r9;
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00cb, code lost:
        r3 = i(r9, r10);
        r11 = l(r9, r10);
        r12 = m(r9, r10);
        io.ktor.http.cio.internals.f.c(r9, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00e2, code lost:
        if (r10.b() != r10.a()) goto L_0x0144;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00e8, code lost:
        if (r11.length() != 0) goto L_0x00ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ea, code lost:
        r13 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ec, code lost:
        r13 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ed, code lost:
        if (r13 != false) goto L_0x013c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f3, code lost:
        if (r12.length() != 0) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00f5, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00f6, code lost:
        if (r5 != false) goto L_0x0134;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f8, code lost:
        r4.L$0 = r1;
        r4.L$1 = r9;
        r4.L$2 = r10;
        r4.L$3 = r3;
        r4.L$4 = r11;
        r4.L$5 = r12;
        r4.label = 2;
        r5 = g(r1, r9, r10, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x010b, code lost:
        if (r5 != r0) goto L_0x010e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x010d, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x010e, code lost:
        r17 = r1;
        r1 = r2;
        r6 = r3;
        r2 = r4;
        r3 = r5;
        r15 = r9;
        r16 = r10;
        r4 = r11;
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        r13 = (io.ktor.http.cio.f) r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x011c, code lost:
        if (r13 == null) goto L_0x0129;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0128, code lost:
        return new io.ktor.http.cio.m(r6, r4, r5, r13, r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0129, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x012a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x012b, code lost:
        r4 = r2;
        r3 = r8;
        r8 = r15;
        r9 = r16;
        r10 = r17;
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x013b, code lost:
        throw new io.ktor.http.cio.ParserException("HTTP version is not specified");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0143, code lost:
        throw new io.ktor.http.cio.ParserException("URI is not specified");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x016a, code lost:
        throw new io.ktor.http.cio.ParserException("Extra characters in request line: " + r9.subSequence(r10.b(), r10.a()).toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x016b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x016c, code lost:
        r3 = r8;
        r8 = r9;
        r9 = r10;
        r10 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0171, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0172, code lost:
        r10 = r1;
        r18 = r4;
        r4 = r3;
        r3 = r18;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object k(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r19, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.http.cio.m> r20) {
        /*
            r1 = r20
            boolean r0 = r1 instanceof io.ktor.http.cio.i.c
            if (r0 == 0) goto L_0x0015
            r0 = r1
            io.ktor.http.cio.i$c r0 = (io.ktor.http.cio.i.c) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0015
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001a
        L_0x0015:
            io.ktor.http.cio.i$c r0 = new io.ktor.http.cio.i$c
            r0.<init>(r1)
        L_0x001a:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r2.label
            r5 = 0
            r6 = 1
            r7 = 0
            switch(r4) {
                case 0: goto L_0x007e;
                case 1: goto L_0x005f;
                case 2: goto L_0x0032;
                default: goto L_0x0029;
            }
        L_0x0029:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0032:
            r0 = r7
            r4 = r7
            r5 = r7
            r6 = r7
            r8 = r7
            java.lang.Object r9 = r2.L$5
            r5 = r9
            java.lang.CharSequence r5 = (java.lang.CharSequence) r5
            java.lang.Object r9 = r2.L$4
            r4 = r9
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            java.lang.Object r9 = r2.L$3
            r6 = r9
            io.ktor.http.u r6 = (io.ktor.http.u) r6
            java.lang.Object r9 = r2.L$2
            io.ktor.http.cio.internals.e r9 = (io.ktor.http.cio.internals.e) r9
            java.lang.Object r0 = r2.L$1
            r8 = r0
            io.ktor.http.cio.internals.b r8 = (io.ktor.http.cio.internals.b) r8
            java.lang.Object r0 = r2.L$0
            r10 = r0
            io.ktor.utils.io.i r10 = (io.ktor.utils.io.i) r10
            kotlin.p.b(r3)     // Catch:{ all -> 0x0079 }
            r15 = r8
            r16 = r9
            r17 = r10
            r8 = r3
            goto L_0x0119
        L_0x005f:
            r4 = r7
            r8 = r7
            java.lang.Object r9 = r2.L$2
            io.ktor.http.cio.internals.e r9 = (io.ktor.http.cio.internals.e) r9
            java.lang.Object r4 = r2.L$1
            r8 = r4
            io.ktor.http.cio.internals.b r8 = (io.ktor.http.cio.internals.b) r8
            java.lang.Object r4 = r2.L$0
            r10 = r4
            io.ktor.utils.io.i r10 = (io.ktor.utils.io.i) r10
            kotlin.p.b(r3)     // Catch:{ all -> 0x0079 }
            r4 = r2
            r2 = r1
            r1 = r10
            r10 = r9
            r9 = r8
            r8 = r3
            goto L_0x00ac
        L_0x0079:
            r0 = move-exception
            r4 = r2
            r2 = r1
            goto L_0x0178
        L_0x007e:
            kotlin.p.b(r3)
            io.ktor.http.cio.internals.b r4 = new io.ktor.http.cio.internals.b
            r4.<init>(r7, r6, r7)
            io.ktor.http.cio.internals.e r8 = new io.ktor.http.cio.internals.e
            r8.<init>(r5, r5)
            r9 = r8
            r8 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r19
        L_0x0092:
            r10 = 8192(0x2000, float:1.14794E-41)
            r3.L$0 = r1     // Catch:{ all -> 0x0171 }
            r3.L$1 = r8     // Catch:{ all -> 0x0171 }
            r3.L$2 = r9     // Catch:{ all -> 0x0171 }
            r3.label = r6     // Catch:{ all -> 0x0171 }
            java.lang.Object r10 = r1.i(r8, r10, r3)     // Catch:{ all -> 0x0171 }
            if (r10 != r0) goto L_0x00a4
            return r0
        L_0x00a4:
            r18 = r4
            r4 = r3
            r3 = r10
            r10 = r9
            r9 = r8
            r8 = r18
        L_0x00ac:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x016b }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x016b }
            if (r3 != 0) goto L_0x00b5
            return r7
        L_0x00b5:
            int r3 = r9.length()     // Catch:{ all -> 0x016b }
            r10.c(r3)     // Catch:{ all -> 0x016b }
            int r3 = r10.b()     // Catch:{ all -> 0x016b }
            int r11 = r10.a()     // Catch:{ all -> 0x016b }
            if (r3 != r11) goto L_0x00cb
            r3 = r4
            r4 = r8
            r8 = r9
            r9 = r10
            goto L_0x0092
        L_0x00cb:
            io.ktor.http.u r3 = i(r9, r10)     // Catch:{ all -> 0x016b }
            java.lang.CharSequence r11 = l(r9, r10)     // Catch:{ all -> 0x016b }
            java.lang.CharSequence r12 = m(r9, r10)     // Catch:{ all -> 0x016b }
            io.ktor.http.cio.internals.f.c(r9, r10)     // Catch:{ all -> 0x016b }
            int r13 = r10.b()     // Catch:{ all -> 0x016b }
            int r14 = r10.a()     // Catch:{ all -> 0x016b }
            if (r13 != r14) goto L_0x0144
            int r13 = r11.length()     // Catch:{ all -> 0x016b }
            if (r13 != 0) goto L_0x00ec
            r13 = r6
            goto L_0x00ed
        L_0x00ec:
            r13 = r5
        L_0x00ed:
            if (r13 != 0) goto L_0x013c
            int r13 = r12.length()     // Catch:{ all -> 0x016b }
            if (r13 != 0) goto L_0x00f6
            r5 = r6
        L_0x00f6:
            if (r5 != 0) goto L_0x0134
            r4.L$0 = r1     // Catch:{ all -> 0x016b }
            r4.L$1 = r9     // Catch:{ all -> 0x016b }
            r4.L$2 = r10     // Catch:{ all -> 0x016b }
            r4.L$3 = r3     // Catch:{ all -> 0x016b }
            r4.L$4 = r11     // Catch:{ all -> 0x016b }
            r4.L$5 = r12     // Catch:{ all -> 0x016b }
            r5 = 2
            r4.label = r5     // Catch:{ all -> 0x016b }
            java.lang.Object r5 = g(r1, r9, r10, r4)     // Catch:{ all -> 0x016b }
            if (r5 != r0) goto L_0x010e
            return r0
        L_0x010e:
            r17 = r1
            r1 = r2
            r6 = r3
            r2 = r4
            r3 = r5
            r15 = r9
            r16 = r10
            r4 = r11
            r5 = r12
        L_0x0119:
            r13 = r3
            io.ktor.http.cio.f r13 = (io.ktor.http.cio.f) r13     // Catch:{ all -> 0x012a }
            if (r13 == 0) goto L_0x0129
            io.ktor.http.cio.m r0 = new io.ktor.http.cio.m     // Catch:{ all -> 0x012a }
            r9 = r0
            r10 = r6
            r11 = r4
            r12 = r5
            r14 = r15
            r9.<init>(r10, r11, r12, r13, r14)     // Catch:{ all -> 0x012a }
            return r0
        L_0x0129:
            return r7
        L_0x012a:
            r0 = move-exception
            r4 = r2
            r3 = r8
            r8 = r15
            r9 = r16
            r10 = r17
            r2 = r1
            goto L_0x0178
        L_0x0134:
            io.ktor.http.cio.ParserException r0 = new io.ktor.http.cio.ParserException     // Catch:{ all -> 0x016b }
            java.lang.String r5 = "HTTP version is not specified"
            r0.<init>(r5)     // Catch:{ all -> 0x016b }
            throw r0     // Catch:{ all -> 0x016b }
        L_0x013c:
            io.ktor.http.cio.ParserException r0 = new io.ktor.http.cio.ParserException     // Catch:{ all -> 0x016b }
            java.lang.String r5 = "URI is not specified"
            r0.<init>(r5)     // Catch:{ all -> 0x016b }
            throw r0     // Catch:{ all -> 0x016b }
        L_0x0144:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x016b }
            r0.<init>()     // Catch:{ all -> 0x016b }
            java.lang.String r5 = "Extra characters in request line: "
            r0.append(r5)     // Catch:{ all -> 0x016b }
            int r5 = r10.b()     // Catch:{ all -> 0x016b }
            int r6 = r10.a()     // Catch:{ all -> 0x016b }
            java.lang.CharSequence r5 = r9.subSequence(r5, r6)     // Catch:{ all -> 0x016b }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x016b }
            r0.append(r5)     // Catch:{ all -> 0x016b }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x016b }
            io.ktor.http.cio.ParserException r5 = new io.ktor.http.cio.ParserException     // Catch:{ all -> 0x016b }
            r5.<init>(r0)     // Catch:{ all -> 0x016b }
            throw r5     // Catch:{ all -> 0x016b }
        L_0x016b:
            r0 = move-exception
            r3 = r8
            r8 = r9
            r9 = r10
            r10 = r1
            goto L_0x0178
        L_0x0171:
            r0 = move-exception
            r10 = r1
            r18 = r4
            r4 = r3
            r3 = r18
        L_0x0178:
            r8.o()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.i.k(io.ktor.utils.io.i, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: io.ktor.http.cio.f} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object g(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r24, @org.jetbrains.annotations.NotNull io.ktor.http.cio.internals.b r25, @org.jetbrains.annotations.NotNull io.ktor.http.cio.internals.e r26, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.http.cio.f> r27) {
        /*
            r1 = r27
            boolean r0 = r1 instanceof io.ktor.http.cio.i.a
            if (r0 == 0) goto L_0x0015
            r0 = r1
            io.ktor.http.cio.i$a r0 = (io.ktor.http.cio.i.a) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0015
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001a
        L_0x0015:
            io.ktor.http.cio.i$a r0 = new io.ktor.http.cio.i$a
            r0.<init>(r1)
        L_0x001a:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r2.label
            r5 = 8192(0x2000, float:1.14794E-41)
            r6 = 0
            switch(r4) {
                case 0: goto L_0x005a;
                case 1: goto L_0x0034;
                default: goto L_0x0029;
            }
        L_0x0029:
            r7 = r25
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0034:
            r4 = r6
            java.lang.Object r7 = r2.L$3
            r4 = r7
            io.ktor.http.cio.f r4 = (io.ktor.http.cio.f) r4
            java.lang.Object r7 = r2.L$2
            io.ktor.http.cio.internals.e r7 = (io.ktor.http.cio.internals.e) r7
            java.lang.Object r8 = r2.L$1
            io.ktor.http.cio.internals.b r8 = (io.ktor.http.cio.internals.b) r8
            java.lang.Object r9 = r2.L$0
            io.ktor.utils.io.i r9 = (io.ktor.utils.io.i) r9
            kotlin.p.b(r3)     // Catch:{ all -> 0x0054 }
            r17 = r4
            r4 = r1
            r1 = r9
            r9 = r3
            r23 = r7
            r7 = r2
            r2 = r23
            goto L_0x0086
        L_0x0054:
            r0 = move-exception
            r17 = r4
            r4 = r1
            goto L_0x010a
        L_0x005a:
            kotlin.p.b(r3)
            io.ktor.http.cio.f r4 = new io.ktor.http.cio.f
            r7 = r25
            r4.<init>(r7)
            r8 = r3
            r9 = r4
            r3 = r1
            r4 = r2
            r1 = r24
            r2 = r26
        L_0x006c:
            r4.L$0 = r1     // Catch:{ all -> 0x00fe }
            r4.L$1 = r7     // Catch:{ all -> 0x00fe }
            r4.L$2 = r2     // Catch:{ all -> 0x00fe }
            r4.L$3 = r9     // Catch:{ all -> 0x00fe }
            r10 = 1
            r4.label = r10     // Catch:{ all -> 0x00fe }
            java.lang.Object r10 = r1.i(r7, r5, r4)     // Catch:{ all -> 0x00fe }
            if (r10 != r0) goto L_0x007f
            return r0
        L_0x007f:
            r17 = r9
            r9 = r8
            r8 = r7
            r7 = r4
            r4 = r3
            r3 = r10
        L_0x0086:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00f5 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00f5 }
            if (r3 != 0) goto L_0x0092
            r17.j()     // Catch:{ all -> 0x00f5 }
            return r6
        L_0x0092:
            int r3 = r8.length()     // Catch:{ all -> 0x00f5 }
            r2.c(r3)     // Catch:{ all -> 0x00f5 }
            int r3 = r2.a()     // Catch:{ all -> 0x00f5 }
            int r10 = r2.b()     // Catch:{ all -> 0x00f5 }
            int r3 = r3 - r10
            if (r3 != 0) goto L_0x00a5
            return r17
        L_0x00a5:
            if (r3 >= r5) goto L_0x00e9
            int r10 = r2.b()     // Catch:{ all -> 0x00f5 }
            r15 = r10
            int r10 = d(r8, r2)     // Catch:{ all -> 0x00f5 }
            r14 = r10
            int r11 = io.ktor.http.cio.internals.d.d(r8, r15, r14)     // Catch:{ all -> 0x00f5 }
            int r10 = r2.a()     // Catch:{ all -> 0x00f5 }
            r13 = r10
            f(r8, r2)     // Catch:{ all -> 0x00f5 }
            int r10 = r2.b()     // Catch:{ all -> 0x00f5 }
            int r12 = r2.a()     // Catch:{ all -> 0x00f5 }
            int r16 = io.ktor.http.cio.internals.d.d(r8, r10, r12)     // Catch:{ all -> 0x00f5 }
            r18 = r12
            r12 = r16
            r2.d(r13)     // Catch:{ all -> 0x00f5 }
            r19 = r10
            r10 = r17
            r20 = r13
            r13 = r15
            r21 = r14
            r22 = r15
            r15 = r19
            r16 = r18
            r10.i(r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x00f5 }
            r3 = r4
            r4 = r7
            r7 = r8
            r8 = r9
            r9 = r17
            goto L_0x006c
        L_0x00e9:
            java.lang.String r0 = "Header line length limit exceeded"
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00f5 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00f5 }
            r5.<init>(r0)     // Catch:{ all -> 0x00f5 }
            throw r5     // Catch:{ all -> 0x00f5 }
        L_0x00f5:
            r0 = move-exception
            r3 = r9
            r9 = r1
            r23 = r7
            r7 = r2
            r2 = r23
            goto L_0x010a
        L_0x00fe:
            r0 = move-exception
            r17 = r9
            r9 = r1
            r23 = r7
            r7 = r2
            r2 = r4
            r4 = r3
            r3 = r8
            r8 = r23
        L_0x010a:
            r17.j()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.i.g(io.ktor.utils.io.i, io.ktor.http.cio.internals.b, io.ktor.http.cio.internals.e, kotlin.coroutines.d):java.lang.Object");
    }

    public static /* synthetic */ Object h(io.ktor.utils.io.i iVar, io.ktor.http.cio.internals.b bVar, e eVar, kotlin.coroutines.d dVar, int i, Object obj) {
        if ((i & 4) != 0) {
            eVar = new e(0, 0);
        }
        return g(iVar, bVar, eVar, dVar);
    }

    /* compiled from: HttpParser.kt */
    public static final class b extends l implements p<Character, Integer, Boolean> {
        public static final b INSTANCE = new b();

        b() {
            super(2);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(invoke(((Character) obj).charValue(), ((Number) obj2).intValue()));
        }

        public final boolean invoke(char ch, int $noName_1) {
            return ch == ' ';
        }
    }

    private static final u i(CharSequence text, e range) {
        io.ktor.http.cio.internals.f.c(text, range);
        u exact = (u) y.s0(io.ktor.http.cio.internals.a.b(io.ktor.http.cio.internals.d.c(), text, range.b(), range.a(), false, b.INSTANCE, 8, (Object) null));
        if (exact == null) {
            return j(text, range);
        }
        range.d(range.b() + exact.i().length());
        return exact;
    }

    private static final u j(CharSequence text, e range) {
        return new u(io.ktor.http.cio.internals.f.b(text, range).toString());
    }

    private static final CharSequence l(CharSequence text, e range) {
        io.ktor.http.cio.internals.f.c(text, range);
        int start = range.b();
        int spaceOrEnd = io.ktor.http.cio.internals.f.a(text, range);
        int length = spaceOrEnd - start;
        if (length <= 0) {
            return "";
        }
        if (length == 1 && text.charAt(start) == '/') {
            range.d(spaceOrEnd);
            return "/";
        }
        CharSequence s = text.subSequence(start, spaceOrEnd);
        range.d(spaceOrEnd);
        return s;
    }

    private static final CharSequence m(CharSequence text, e range) {
        io.ktor.http.cio.internals.f.c(text, range);
        if (range.b() < range.a()) {
            String exact = (String) y.s0(io.ktor.http.cio.internals.a.b(a, text, range.b(), range.a(), false, d.INSTANCE, 8, (Object) null));
            if (exact == null) {
                return io.ktor.http.cio.internals.f.b(text, range);
            }
            range.d(range.b() + exact.length());
            return exact;
        }
        throw new IllegalStateException(("Failed to parse version: " + text).toString());
    }

    /* compiled from: HttpParser.kt */
    public static final class d extends l implements p<Character, Integer, Boolean> {
        public static final d INSTANCE = new d();

        d() {
            super(2);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            return Boolean.valueOf(invoke(((Character) obj).charValue(), ((Number) obj2).intValue()));
        }

        public final boolean invoke(char ch, int $noName_1) {
            return ch == ' ';
        }
    }

    public static final int d(@NotNull io.ktor.http.cio.internals.b text, @NotNull e range) {
        k.f(text, "text");
        k.f(range, "range");
        int index = range.b();
        int end = range.a();
        while (index < end) {
            char ch = text.charAt(index);
            if (ch == ':') {
                range.d(index + 1);
                return index;
            } else if (!b(ch)) {
                index++;
            } else {
                e(text, index, range.b(), ch);
                throw null;
            }
        }
        c(text, range);
        throw null;
    }

    private static final Void e(io.ktor.http.cio.internals.b text, int index, int start, char ch) {
        if (index == start) {
            throw new ParserException("Multiline headers via line folding is not supported since it is deprecated as per RFC7230.");
        }
        a(text, ch);
        throw null;
    }

    public static final void f(@NotNull io.ktor.http.cio.internals.b text, @NotNull e range) {
        k.f(text, "text");
        k.f(range, "range");
        int start = range.b();
        int end = range.a();
        int index = io.ktor.http.cio.internals.f.d(text, start, end);
        if (index >= end) {
            range.d(end);
            return;
        }
        int valueStart = index;
        int valueLastIndex = index;
        while (index < end) {
            char ch = text.charAt(index);
            if (!(ch == 9 || ch == ' ')) {
                if (k.g(ch, 32) >= 0) {
                    valueLastIndex = index;
                } else {
                    a(text, ch);
                    throw null;
                }
            }
            index++;
        }
        range.d(valueStart);
        range.c(valueLastIndex + 1);
    }

    private static final Void c(CharSequence text, e range) {
        throw new ParserException("No colon in HTTP header in " + text.subSequence(range.b(), range.a()).toString() + " in builder: \n" + text);
    }

    private static final Void a(CharSequence text, char ch) {
        throw new ParserException("Character with code " + (ch & 255) + " is not allowed in header names, \n" + text);
    }

    private static final boolean b(char ch) {
        return k.g(ch, 32) <= 0 || x.R("\"(),/:;<=>?@[\\]{}", ch, false, 2, (Object) null);
    }
}
