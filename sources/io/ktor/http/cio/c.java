package io.ktor.http.cio;

import io.ktor.utils.io.core.a0;
import io.ktor.utils.io.i;
import io.ktor.utils.io.l;
import io.ktor.utils.io.q;
import io.ktor.utils.io.v;
import io.ktor.utils.io.w;
import java.nio.charset.CharsetEncoder;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.text.s;
import kotlin.x;
import kotlinx.coroutines.s1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ChunkedTransferEncoding.kt */
public final class c {
    private static final io.ktor.utils.io.pool.d<StringBuilder> a = new a(2048);
    /* access modifiers changed from: private */
    public static final byte[] b;
    private static final byte[] c;

    @f(c = "io.ktor.http.cio.ChunkedTransferEncodingKt", f = "ChunkedTransferEncoding.kt", l = {63, 79, 85}, m = "decodeChunked")
    /* compiled from: ChunkedTransferEncoding.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        long J$0;
        long J$1;
        long J$2;
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
            return c.b((i) null, (l) null, 0, this);
        }
    }

    @f(c = "io.ktor.http.cio.ChunkedTransferEncodingKt", f = "ChunkedTransferEncoding.kt", l = {130, 141}, m = "encodeChunked")
    /* compiled from: ChunkedTransferEncoding.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        d(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return c.c((l) null, (i) null, this);
        }
    }

    /* compiled from: ChunkedTransferEncoding.kt */
    public static final class a extends io.ktor.utils.io.pool.b<StringBuilder> {
        a(int $super_call_param$0) {
            super($super_call_param$0);
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: t */
        public StringBuilder l() {
            return new StringBuilder(128);
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: s */
        public StringBuilder g(@NotNull StringBuilder instance) {
            k.f(instance, "instance");
            s.j(instance);
            return instance;
        }
    }

    static {
        CharsetEncoder newEncoder = kotlin.text.c.a.newEncoder();
        k.b(newEncoder, "charset.newEncoder()");
        b = io.ktor.utils.io.charsets.a.f(newEncoder, "\r\n", 0, "\r\n".length());
        CharsetEncoder newEncoder2 = kotlin.text.c.a.newEncoder();
        k.b(newEncoder2, "charset.newEncoder()");
        c = io.ktor.utils.io.charsets.a.f(newEncoder2, "0\r\n\r\n", 0, "0\r\n\r\n".length());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v18, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v24, resolved type: java.lang.StringBuilder} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0187  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x019c A[Catch:{ all -> 0x0218 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x01fc A[Catch:{ all -> 0x0218 }] */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object b(@org.jetbrains.annotations.NotNull io.ktor.utils.io.i r25, @org.jetbrains.annotations.NotNull io.ktor.utils.io.l r26, long r27, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r29) {
        /*
            r1 = r29
            boolean r0 = r1 instanceof io.ktor.http.cio.c.b
            if (r0 == 0) goto L_0x0015
            r0 = r1
            io.ktor.http.cio.c$b r0 = (io.ktor.http.cio.c.b) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0015
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001a
        L_0x0015:
            io.ktor.http.cio.c$b r0 = new io.ktor.http.cio.c$b
            r0.<init>(r1)
        L_0x001a:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r2.label
            r9 = 1
            r10 = 0
            r12 = 0
            switch(r4) {
                case 0: goto L_0x009a;
                case 1: goto L_0x007d;
                case 2: goto L_0x005a;
                case 3: goto L_0x0032;
                default: goto L_0x002a;
            }
        L_0x002a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0032:
            r13 = r10
            r4 = r12
            r15 = r10
            long r12 = r2.J$2
            long r14 = r2.J$1
            java.lang.Object r7 = r2.L$2
            r4 = r7
            java.lang.StringBuilder r4 = (java.lang.StringBuilder) r4
            long r5 = r2.J$0
            java.lang.Object r7 = r2.L$1
            io.ktor.utils.io.l r7 = (io.ktor.utils.io.l) r7
            java.lang.Object r8 = r2.L$0
            io.ktor.utils.io.i r8 = (io.ktor.utils.io.i) r8
            kotlin.p.b(r3)     // Catch:{ all -> 0x0097 }
            r9 = r4
            r19 = r14
            r11 = 2
            r4 = r1
            r1 = r8
            r14 = r12
            r8 = r3
            r23 = r7
            r7 = r2
            r2 = r23
            goto L_0x0194
        L_0x005a:
            r4 = r10
            r6 = r12
            r7 = r10
            long r4 = r2.J$2
            long r14 = r2.J$1
            java.lang.Object r7 = r2.L$2
            r6 = r7
            java.lang.StringBuilder r6 = (java.lang.StringBuilder) r6
            long r7 = r2.J$0
            java.lang.Object r12 = r2.L$1
            io.ktor.utils.io.l r12 = (io.ktor.utils.io.l) r12
            java.lang.Object r13 = r2.L$0
            io.ktor.utils.io.i r13 = (io.ktor.utils.io.i) r13
            kotlin.p.b(r3)     // Catch:{ all -> 0x0076 }
            r9 = r13
            goto L_0x0156
        L_0x0076:
            r0 = move-exception
            r4 = r6
            r5 = r7
            r7 = r12
            r8 = r13
            goto L_0x024a
        L_0x007d:
            r4 = r12
            r5 = r10
            long r14 = r2.J$1
            java.lang.Object r5 = r2.L$2
            r4 = r5
            java.lang.StringBuilder r4 = (java.lang.StringBuilder) r4
            long r5 = r2.J$0
            java.lang.Object r7 = r2.L$1
            io.ktor.utils.io.l r7 = (io.ktor.utils.io.l) r7
            java.lang.Object r8 = r2.L$0
            io.ktor.utils.io.i r8 = (io.ktor.utils.io.i) r8
            kotlin.p.b(r3)     // Catch:{ all -> 0x0097 }
            r12 = r8
            r8 = r7
            r7 = r3
            goto L_0x00d8
        L_0x0097:
            r0 = move-exception
            goto L_0x024a
        L_0x009a:
            kotlin.p.b(r3)
            io.ktor.utils.io.pool.d<java.lang.StringBuilder> r4 = a
            java.lang.Object r4 = r4.p0()
            java.lang.StringBuilder r4 = (java.lang.StringBuilder) r4
            r5 = 0
            r7 = r3
            r8 = r4
            r14 = r5
            r3 = r27
            r5 = r1
            r6 = r2
            r1 = r25
            r2 = r26
        L_0x00b2:
            kotlin.text.s.j(r8)     // Catch:{ all -> 0x023c }
            r12 = 128(0x80, float:1.794E-43)
            r6.L$0 = r1     // Catch:{ all -> 0x023c }
            r6.L$1 = r2     // Catch:{ all -> 0x023c }
            r6.J$0 = r3     // Catch:{ all -> 0x023c }
            r6.L$2 = r8     // Catch:{ all -> 0x023c }
            r6.J$1 = r14     // Catch:{ all -> 0x023c }
            r6.label = r9     // Catch:{ all -> 0x023c }
            java.lang.Object r12 = r1.i(r8, r12, r6)     // Catch:{ all -> 0x023c }
            if (r12 != r0) goto L_0x00cb
            return r0
        L_0x00cb:
            r23 = r12
            r12 = r1
            r1 = r5
            r24 = r8
            r8 = r2
            r2 = r6
            r5 = r3
            r4 = r24
            r3 = r23
        L_0x00d8:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0237 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0237 }
            if (r3 == 0) goto L_0x022f
            int r3 = r4.length()     // Catch:{ all -> 0x0237 }
            if (r3 != 0) goto L_0x00e8
            r3 = r9
            goto L_0x00e9
        L_0x00e8:
            r3 = 0
        L_0x00e9:
            if (r3 != 0) goto L_0x0227
            int r3 = r4.length()     // Catch:{ all -> 0x0237 }
            if (r3 != r9) goto L_0x00ff
            r3 = 0
            char r13 = r4.charAt(r3)     // Catch:{ all -> 0x0237 }
            r3 = 48
            if (r13 != r3) goto L_0x00ff
            r19 = r10
            goto L_0x0103
        L_0x00ff:
            long r19 = io.ktor.http.cio.internals.d.k(r4)     // Catch:{ all -> 0x0237 }
        L_0x0103:
            r25 = r19
            r17 = -1
            int r3 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r3 == 0) goto L_0x012f
            long r19 = r5 - r14
            r9 = r25
            int r3 = (r9 > r19 ? 1 : (r9 == r19 ? 0 : -1))
            if (r3 > 0) goto L_0x0115
            goto L_0x0131
        L_0x0115:
            io.ktor.utils.io.k.a(r12)     // Catch:{ all -> 0x0237 }
            io.ktor.http.cio.ParserException r0 = new io.ktor.http.cio.ParserException     // Catch:{ all -> 0x0237 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0237 }
            r3.<init>()     // Catch:{ all -> 0x0237 }
            java.lang.String r11 = "Invalid chunk: chunk-encoded content is larger than expected "
            r3.append(r11)     // Catch:{ all -> 0x0237 }
            r3.append(r5)     // Catch:{ all -> 0x0237 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0237 }
            r0.<init>(r3)     // Catch:{ all -> 0x0237 }
            throw r0     // Catch:{ all -> 0x0237 }
        L_0x012f:
            r9 = r25
        L_0x0131:
            r19 = 0
            int r3 = (r9 > r19 ? 1 : (r9 == r19 ? 0 : -1))
            if (r3 <= 0) goto L_0x016a
            r2.L$0 = r12     // Catch:{ all -> 0x0237 }
            r2.L$1 = r8     // Catch:{ all -> 0x0237 }
            r2.J$0 = r5     // Catch:{ all -> 0x0237 }
            r2.L$2 = r4     // Catch:{ all -> 0x0237 }
            r2.J$1 = r14     // Catch:{ all -> 0x0237 }
            r2.J$2 = r9     // Catch:{ all -> 0x0237 }
            r3 = 2
            r2.label = r3     // Catch:{ all -> 0x0237 }
            java.lang.Object r3 = io.ktor.utils.io.j.a(r12, r8, r9, r2)     // Catch:{ all -> 0x0237 }
            if (r3 != r0) goto L_0x014d
            return r0
        L_0x014d:
            r3 = r7
            r23 = r5
            r6 = r4
            r4 = r9
            r9 = r12
            r12 = r8
            r7 = r23
        L_0x0156:
            r12.flush()     // Catch:{ all -> 0x0163 }
            long r14 = r14 + r4
            r23 = r4
            r4 = r6
            r5 = r7
            r8 = r9
            r7 = r12
            r9 = r23
            goto L_0x016d
        L_0x0163:
            r0 = move-exception
            r4 = r6
            r5 = r7
            r8 = r9
            r7 = r12
            goto L_0x024a
        L_0x016a:
            r3 = r7
            r7 = r8
            r8 = r12
        L_0x016d:
            kotlin.text.s.j(r4)     // Catch:{ all -> 0x0225 }
            r2.L$0 = r8     // Catch:{ all -> 0x0225 }
            r2.L$1 = r7     // Catch:{ all -> 0x0225 }
            r2.J$0 = r5     // Catch:{ all -> 0x0225 }
            r2.L$2 = r4     // Catch:{ all -> 0x0225 }
            r2.J$1 = r14     // Catch:{ all -> 0x0225 }
            r2.J$2 = r9     // Catch:{ all -> 0x0225 }
            r11 = 3
            r2.label = r11     // Catch:{ all -> 0x0225 }
            r11 = 2
            java.lang.Object r12 = r8.i(r4, r11, r2)     // Catch:{ all -> 0x0225 }
            if (r12 != r0) goto L_0x0187
            return r0
        L_0x0187:
            r19 = r14
            r14 = r9
            r9 = r4
            r4 = r1
            r1 = r8
            r8 = r3
            r3 = r12
            r23 = r7
            r7 = r2
            r2 = r23
        L_0x0194:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0218 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0218 }
            if (r3 == 0) goto L_0x01fc
            int r3 = r9.length()     // Catch:{ all -> 0x0218 }
            if (r3 <= 0) goto L_0x01a4
            r3 = 1
            goto L_0x01a5
        L_0x01a4:
            r3 = 0
        L_0x01a5:
            if (r3 != 0) goto L_0x01f4
            r21 = 0
            int r3 = (r14 > r21 ? 1 : (r14 == r21 ? 0 : -1))
            if (r3 != 0) goto L_0x01e3
            r16 = -1
            int r0 = (r5 > r16 ? 1 : (r5 == r16 ? 0 : -1))
            if (r0 == 0) goto L_0x01d7
            int r0 = (r19 > r5 ? 1 : (r19 == r5 ? 0 : -1))
            if (r0 != 0) goto L_0x01b8
            goto L_0x01d7
        L_0x01b8:
            io.ktor.utils.io.k.a(r1)     // Catch:{ all -> 0x0218 }
            java.io.EOFException r0 = new java.io.EOFException     // Catch:{ all -> 0x0218 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0218 }
            r3.<init>()     // Catch:{ all -> 0x0218 }
            java.lang.String r10 = "Corrupted chunk-encoded content: steam ended before the expected number of bytes ("
            r3.append(r10)     // Catch:{ all -> 0x0218 }
            r3.append(r5)     // Catch:{ all -> 0x0218 }
            java.lang.String r10 = ") were decoded."
            r3.append(r10)     // Catch:{ all -> 0x0218 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0218 }
            r0.<init>(r3)     // Catch:{ all -> 0x0218 }
            throw r0     // Catch:{ all -> 0x0218 }
        L_0x01d7:
            io.ktor.utils.io.pool.d<java.lang.StringBuilder> r0 = a
            r0.N0(r9)
            io.ktor.utils.io.m.a(r2)
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x01e3:
            r16 = -1
            r14 = r19
            r10 = r21
            r23 = r5
            r5 = r4
            r3 = r23
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = 1
            goto L_0x00b2
        L_0x01f4:
            java.io.EOFException r0 = new java.io.EOFException     // Catch:{ all -> 0x0218 }
            java.lang.String r3 = "Invalid chunk: content block should end with CR+LF"
            r0.<init>(r3)     // Catch:{ all -> 0x0218 }
            throw r0     // Catch:{ all -> 0x0218 }
        L_0x01fc:
            java.io.EOFException r0 = new java.io.EOFException     // Catch:{ all -> 0x0218 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0218 }
            r3.<init>()     // Catch:{ all -> 0x0218 }
            java.lang.String r10 = "Invalid chunk: content block of size "
            r3.append(r10)     // Catch:{ all -> 0x0218 }
            r3.append(r14)     // Catch:{ all -> 0x0218 }
            java.lang.String r10 = " ended unexpectedly"
            r3.append(r10)     // Catch:{ all -> 0x0218 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0218 }
            r0.<init>(r3)     // Catch:{ all -> 0x0218 }
            throw r0     // Catch:{ all -> 0x0218 }
        L_0x0218:
            r0 = move-exception
            r3 = r8
            r14 = r19
            r8 = r1
            r1 = r4
            r4 = r9
            r23 = r7
            r7 = r2
            r2 = r23
            goto L_0x024a
        L_0x0225:
            r0 = move-exception
            goto L_0x024a
        L_0x0227:
            java.io.EOFException r0 = new java.io.EOFException     // Catch:{ all -> 0x0237 }
            java.lang.String r3 = "Invalid chunk size: empty"
            r0.<init>(r3)     // Catch:{ all -> 0x0237 }
            throw r0     // Catch:{ all -> 0x0237 }
        L_0x022f:
            java.io.EOFException r0 = new java.io.EOFException     // Catch:{ all -> 0x0237 }
            java.lang.String r3 = "Chunked stream has ended unexpectedly: no chunk size"
            r0.<init>(r3)     // Catch:{ all -> 0x0237 }
            throw r0     // Catch:{ all -> 0x0237 }
        L_0x0237:
            r0 = move-exception
            r3 = r7
            r7 = r8
            r8 = r12
            goto L_0x024a
        L_0x023c:
            r0 = move-exception
            r23 = r8
            r8 = r1
            r1 = r5
            r24 = r7
            r7 = r2
            r2 = r6
            r5 = r3
            r3 = r24
            r4 = r23
        L_0x024a:
            r7.d(r0)     // Catch:{ all -> 0x024f }
            throw r0     // Catch:{ all -> 0x024f }
        L_0x024f:
            r0 = move-exception
            io.ktor.utils.io.pool.d<java.lang.StringBuilder> r9 = a
            r9.N0(r4)
            io.ktor.utils.io.m.a(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.c.b(io.ktor.utils.io.i, io.ktor.utils.io.l, long, kotlin.coroutines.d):java.lang.Object");
    }

    @f(c = "io.ktor.http.cio.ChunkedTransferEncodingKt$encodeChunked$2", f = "ChunkedTransferEncoding.kt", l = {120}, m = "invokeSuspend")
    /* renamed from: io.ktor.http.cio.c$c  reason: collision with other inner class name */
    /* compiled from: ChunkedTransferEncoding.kt */
    public static final class C0250c extends kotlin.coroutines.jvm.internal.l implements p<w, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ l $output;
        Object L$0;
        int label;
        private w p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0250c(l lVar, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$output = lVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            C0250c cVar = new C0250c(this.$output, dVar);
            w wVar = (w) obj;
            cVar.p$ = (w) obj;
            return cVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((C0250c) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    w $this$reader = this.p$;
                    l lVar = this.$output;
                    i channel = $this$reader.getChannel();
                    this.L$0 = $this$reader;
                    this.label = 1;
                    if (c.c(lVar, channel, this) != d) {
                        w wVar = $this$reader;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    w $this$reader2 = (w) this.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    @Nullable
    public static final Object d(@NotNull l output, @NotNull g coroutineContext, @NotNull kotlin.coroutines.d<? super v> $completion) {
        return q.c(s1.c, coroutineContext, false, new C0250c(output, (kotlin.coroutines.d) null));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: io.ktor.utils.io.core.a0} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: io.ktor.utils.io.i} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: io.ktor.utils.io.l} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: io.ktor.utils.io.core.a0} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: io.ktor.utils.io.l} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object c(@org.jetbrains.annotations.NotNull io.ktor.utils.io.l r7, @org.jetbrains.annotations.NotNull io.ktor.utils.io.i r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r9) {
        /*
            boolean r0 = r9 instanceof io.ktor.http.cio.c.d
            if (r0 == 0) goto L_0x0013
            r0 = r9
            io.ktor.http.cio.c$d r0 = (io.ktor.http.cio.c.d) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.http.cio.c$d r0 = new io.ktor.http.cio.c$d
            r0.<init>(r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0056;
                case 1: goto L_0x0042;
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
            io.ktor.utils.io.core.a0 r2 = (io.ktor.utils.io.core.a0) r2
            java.lang.Object r3 = r0.L$1
            r8 = r3
            io.ktor.utils.io.i r8 = (io.ktor.utils.io.i) r8
            java.lang.Object r3 = r0.L$0
            r7 = r3
            io.ktor.utils.io.l r7 = (io.ktor.utils.io.l) r7
            kotlin.p.b(r1)     // Catch:{ all -> 0x0040 }
            goto L_0x008e
        L_0x0040:
            r3 = move-exception
            goto L_0x00a0
        L_0x0042:
            r3 = r4
            java.lang.Object r4 = r0.L$2
            r3 = r4
            io.ktor.utils.io.core.a0 r3 = (io.ktor.utils.io.core.a0) r3
            java.lang.Object r4 = r0.L$1
            r8 = r4
            io.ktor.utils.io.i r8 = (io.ktor.utils.io.i) r8
            java.lang.Object r4 = r0.L$0
            r7 = r4
            io.ktor.utils.io.l r7 = (io.ktor.utils.io.l) r7
            kotlin.p.b(r1)     // Catch:{ all -> 0x009c }
            goto L_0x007b
        L_0x0056:
            kotlin.p.b(r1)
            io.ktor.utils.io.core.a0$c r3 = io.ktor.utils.io.core.a0.I4
            io.ktor.utils.io.pool.d r3 = r3.b()
            java.lang.Object r3 = r3.p0()
            io.ktor.utils.io.core.a0 r3 = (io.ktor.utils.io.core.a0) r3
            io.ktor.http.cio.c$e r5 = new io.ktor.http.cio.c$e     // Catch:{ all -> 0x009c }
            r5.<init>(r7, r3, r4)     // Catch:{ all -> 0x009c }
            r0.L$0 = r7     // Catch:{ all -> 0x009c }
            r0.L$1 = r8     // Catch:{ all -> 0x009c }
            r0.L$2 = r3     // Catch:{ all -> 0x009c }
            r4 = 1
            r0.label = r4     // Catch:{ all -> 0x009c }
            java.lang.Object r4 = r8.c(r5, r0)     // Catch:{ all -> 0x009c }
            if (r4 != r2) goto L_0x007b
            return r2
        L_0x007b:
            byte[] r4 = c     // Catch:{ all -> 0x009c }
            r0.L$0 = r7     // Catch:{ all -> 0x009c }
            r0.L$1 = r8     // Catch:{ all -> 0x009c }
            r0.L$2 = r3     // Catch:{ all -> 0x009c }
            r5 = 2
            r0.label = r5     // Catch:{ all -> 0x009c }
            java.lang.Object r4 = io.ktor.utils.io.m.b(r7, r4, r0)     // Catch:{ all -> 0x009c }
            if (r4 != r2) goto L_0x008d
            return r2
        L_0x008d:
            r2 = r3
        L_0x008e:
        L_0x008f:
            r7.flush()
            io.ktor.utils.io.core.a0$c r3 = io.ktor.utils.io.core.a0.I4
            io.ktor.utils.io.pool.d r3 = r3.b()
            r2.e1(r3)
            goto L_0x00a5
        L_0x009c:
            r2 = move-exception
            r6 = r3
            r3 = r2
            r2 = r6
        L_0x00a0:
            r7.d(r3)     // Catch:{ all -> 0x00a8 }
            goto L_0x008f
        L_0x00a5:
            kotlin.x r3 = kotlin.x.a
            return r3
        L_0x00a8:
            r3 = move-exception
            r7.flush()
            io.ktor.utils.io.core.a0$c r4 = io.ktor.utils.io.core.a0.I4
            io.ktor.utils.io.pool.d r4 = r4.b()
            r2.e1(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.c.c(io.ktor.utils.io.l, io.ktor.utils.io.i, kotlin.coroutines.d):java.lang.Object");
    }

    @f(c = "io.ktor.http.cio.ChunkedTransferEncodingKt$encodeChunked$4", f = "ChunkedTransferEncoding.kt", l = {131, 178, 179, 180, 189, 190, 191}, m = "invokeSuspend")
    /* compiled from: ChunkedTransferEncoding.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.l implements p<io.ktor.utils.io.x, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ l $output;
        final /* synthetic */ a0 $view;
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        private io.ktor.utils.io.x p$;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(l lVar, a0 a0Var, kotlin.coroutines.d dVar) {
            super(2, dVar);
            this.$output = lVar;
            this.$view = a0Var;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            k.f(dVar, "completion");
            e eVar = new e(this.$output, this.$view, dVar);
            io.ktor.utils.io.x xVar = (io.ktor.utils.io.x) obj;
            eVar.p$ = (io.ktor.utils.io.x) obj;
            return eVar;
        }

        public final Object invoke(Object obj, Object obj2) {
            return ((e) create(obj, (kotlin.coroutines.d) obj2)).invokeSuspend(x.a);
        }

        /* Debug info: failed to restart local var, previous not found, register: 17 */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v15, resolved type: io.ktor.utils.io.x} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v12, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v19, resolved type: io.ktor.utils.io.core.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: io.ktor.utils.io.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v14, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v17, resolved type: io.ktor.utils.io.core.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v15, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: io.ktor.utils.io.x} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v23, resolved type: io.ktor.utils.io.core.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v18, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v16, resolved type: io.ktor.utils.io.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v19, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v20, resolved type: io.ktor.utils.io.core.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v20, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v15, resolved type: io.ktor.utils.io.x} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v16, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v20, resolved type: io.ktor.utils.io.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v18, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v19, resolved type: io.ktor.utils.io.x} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v23, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v19, resolved type: io.ktor.utils.io.core.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v24, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v27, resolved type: io.ktor.utils.io.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v25, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: io.ktor.utils.io.core.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v28, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v23, resolved type: io.ktor.utils.io.core.a0} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v29, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v30, resolved type: io.ktor.utils.io.l} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v31, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: io.ktor.utils.io.l} */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0137, code lost:
            r7.L$0 = r6;
            r7.label = 1;
            r8 = r6.k(4088, r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0141, code lost:
            if (r8 != r2) goto L_0x0144;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0143, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x014a, code lost:
            if (((java.lang.Boolean) r8).booleanValue() == false) goto L_0x01d1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x014c, code lost:
            r8 = io.ktor.utils.io.u.a.a(r6, 0, 1, (java.lang.Object) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0150, code lost:
            if (r8 == null) goto L_0x01ce;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0152, code lost:
            r10 = r7.$output;
            r9 = r7.$view;
            r12 = r8;
            r12 = r12.s() - r12.o();
            r9.J();
            io.ktor.http.cio.internals.d.l(r9, r12);
            io.ktor.utils.io.core.i.e(r9, 3338);
            r7.L$0 = r6;
            r7.L$1 = r8;
            r7.L$2 = r10;
            r7.L$3 = r9;
            r7.L$4 = r7;
            r7.I$0 = r12;
            r7.label = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0183, code lost:
            if (r10.m(r9, r7) != r2) goto L_0x0186;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0185, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0186, code lost:
            r13 = r7;
            r16 = r10;
            r10 = r6;
            r6 = r8;
            r8 = r12;
            r12 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x018e, code lost:
            r7.L$0 = r10;
            r7.L$1 = r6;
            r7.L$2 = r12;
            r7.L$3 = r9;
            r7.L$4 = r13;
            r7.I$0 = r8;
            r7.label = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x01a1, code lost:
            if (r12.m(r6, r13) != r2) goto L_0x01a4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x01a3, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x01a4, code lost:
            r14 = io.ktor.http.cio.c.a();
            r7.L$0 = r10;
            r7.L$1 = r6;
            r7.L$2 = r12;
            r7.L$3 = r9;
            r7.I$0 = r8;
            r7.label = 4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x01b9, code lost:
            if (io.ktor.utils.io.m.b(r12, r14, r13) != r2) goto L_0x01bc;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x01bb, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x01bc, code lost:
            r16 = r6;
            r6 = r2;
            r2 = r9;
            r9 = r10;
            r10 = r8;
            r8 = r7;
            r7 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x01c5, code lost:
            r12.flush();
            r2 = r6;
            r7 = r8;
            r6 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x01d0, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x01d1, code lost:
            r3 = io.ktor.utils.io.u.a.a(r6, 0, 1, (java.lang.Object) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x01d5, code lost:
            if (r3 == null) goto L_0x0255;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x01d7, code lost:
            r5 = r3;
            r4 = false;
            r3 = r7.$output;
            r8 = r7.$view;
            r10 = r5;
            r10 = r10.s() - r10.o();
            r8.J();
            io.ktor.http.cio.internals.d.l(r8, r10);
            io.ktor.utils.io.core.i.e(r8, 3338);
            r7.L$0 = r6;
            r7.L$1 = r5;
            r7.L$2 = r3;
            r7.L$3 = r8;
            r7.L$4 = r7;
            r7.I$0 = r10;
            r7.label = 5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x020a, code lost:
            if (r3.m(r8, r7) != r2) goto L_0x020d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x020c, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x020d, code lost:
            r11 = r7;
            r16 = r6;
            r6 = r3;
            r3 = false;
            r9 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x0214, code lost:
            r7.L$0 = r9;
            r7.L$1 = r5;
            r7.L$2 = r6;
            r7.L$3 = r8;
            r7.L$4 = r11;
            r7.I$0 = r10;
            r7.label = 6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0227, code lost:
            if (r6.m(r5, r11) != r2) goto L_0x022a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0229, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x022a, code lost:
            r16 = r3;
            r3 = r2;
            r2 = r10;
            r10 = r9;
            r9 = r7;
            r7 = r5;
            r5 = r4;
            r4 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x0234, code lost:
            r12 = io.ktor.http.cio.c.a();
            r9.L$0 = r10;
            r9.L$1 = r7;
            r9.L$2 = r6;
            r9.L$3 = r8;
            r9.I$0 = r2;
            r9.label = 7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x0249, code lost:
            if (io.ktor.utils.io.m.b(r6, r12, r11) != r3) goto L_0x024c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x024b, code lost:
            return r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x024c, code lost:
            r3 = r5;
            r5 = r7;
            r7 = r9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x024f, code lost:
            r6.flush();
            r6 = r10;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0257, code lost:
            return kotlin.x.a;
         */
        /* JADX WARNING: Multi-variable type inference failed */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r18) {
            /*
                r17 = this;
                r0 = r17
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r0.label
                r3 = 1
                r4 = 0
                r5 = 0
                switch(r2) {
                    case 0: goto L_0x012d;
                    case 1: goto L_0x011d;
                    case 2: goto L_0x00ef;
                    case 3: goto L_0x00c1;
                    case 4: goto L_0x0098;
                    case 5: goto L_0x006c;
                    case 6: goto L_0x003f;
                    case 7: goto L_0x0016;
                    default: goto L_0x000e;
                }
            L_0x000e:
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r1.<init>(r2)
                throw r1
            L_0x0016:
                r1 = r4
                r2 = r4
                r3 = r4
                r4 = r5
                r6 = r5
                r7 = r5
                int r1 = r0.I$0
                java.lang.Object r8 = r0.L$3
                r6 = r8
                io.ktor.utils.io.core.a0 r6 = (io.ktor.utils.io.core.a0) r6
                java.lang.Object r8 = r0.L$2
                r4 = r8
                io.ktor.utils.io.l r4 = (io.ktor.utils.io.l) r4
                java.lang.Object r8 = r0.L$1
                r5 = r8
                io.ktor.utils.io.core.a0 r5 = (io.ktor.utils.io.core.a0) r5
                java.lang.Object r8 = r0.L$0
                r7 = r8
                io.ktor.utils.io.x r7 = (io.ktor.utils.io.x) r7
                kotlin.p.b(r18)
                r8 = r6
                r10 = r7
                r7 = r0
                r6 = r4
                r4 = r2
                r2 = r1
                r1 = r18
                goto L_0x024f
            L_0x003f:
                r2 = r4
                r3 = r4
                r6 = r5
                r7 = r5
                r8 = r5
                int r2 = r0.I$0
                java.lang.Object r9 = r0.L$4
                io.ktor.http.cio.c$e r9 = (io.ktor.http.cio.c.e) r9
                java.lang.Object r10 = r0.L$3
                r7 = r10
                io.ktor.utils.io.core.a0 r7 = (io.ktor.utils.io.core.a0) r7
                java.lang.Object r10 = r0.L$2
                r6 = r10
                io.ktor.utils.io.l r6 = (io.ktor.utils.io.l) r6
                java.lang.Object r10 = r0.L$1
                r5 = r10
                io.ktor.utils.io.core.a0 r5 = (io.ktor.utils.io.core.a0) r5
                java.lang.Object r10 = r0.L$0
                r8 = r10
                io.ktor.utils.io.x r8 = (io.ktor.utils.io.x) r8
                kotlin.p.b(r18)
                r10 = r8
                r11 = r9
                r9 = r0
                r8 = r7
                r7 = r5
                r5 = r4
                r3 = r1
                r1 = r18
                goto L_0x0234
            L_0x006c:
                r2 = r4
                r3 = r4
                r6 = r5
                r7 = r5
                r8 = r5
                int r2 = r0.I$0
                java.lang.Object r9 = r0.L$4
                io.ktor.http.cio.c$e r9 = (io.ktor.http.cio.c.e) r9
                java.lang.Object r10 = r0.L$3
                r7 = r10
                io.ktor.utils.io.core.a0 r7 = (io.ktor.utils.io.core.a0) r7
                java.lang.Object r10 = r0.L$2
                r6 = r10
                io.ktor.utils.io.l r6 = (io.ktor.utils.io.l) r6
                java.lang.Object r10 = r0.L$1
                r5 = r10
                io.ktor.utils.io.core.a0 r5 = (io.ktor.utils.io.core.a0) r5
                java.lang.Object r10 = r0.L$0
                r8 = r10
                io.ktor.utils.io.x r8 = (io.ktor.utils.io.x) r8
                kotlin.p.b(r18)
                r10 = r2
                r11 = r9
                r2 = r1
                r9 = r8
                r1 = r18
                r8 = r7
                r7 = r0
                goto L_0x0214
            L_0x0098:
                r2 = r5
                r6 = r5
                r7 = r4
                r8 = r4
                r9 = r5
                r10 = r5
                int r8 = r0.I$0
                java.lang.Object r11 = r0.L$3
                r2 = r11
                io.ktor.utils.io.core.a0 r2 = (io.ktor.utils.io.core.a0) r2
                java.lang.Object r11 = r0.L$2
                r10 = r11
                io.ktor.utils.io.l r10 = (io.ktor.utils.io.l) r10
                java.lang.Object r11 = r0.L$1
                r6 = r11
                io.ktor.utils.io.core.a0 r6 = (io.ktor.utils.io.core.a0) r6
                java.lang.Object r11 = r0.L$0
                r9 = r11
                io.ktor.utils.io.x r9 = (io.ktor.utils.io.x) r9
                kotlin.p.b(r18)
                r11 = r7
                r12 = r10
                r7 = r6
                r10 = r8
                r8 = r0
                r6 = r1
                r1 = r18
                goto L_0x01c5
            L_0x00c1:
                r2 = r5
                r6 = r5
                r7 = r4
                r8 = r4
                r9 = r5
                r10 = r5
                int r8 = r0.I$0
                java.lang.Object r11 = r0.L$4
                io.ktor.http.cio.c$e r11 = (io.ktor.http.cio.c.e) r11
                java.lang.Object r12 = r0.L$3
                r2 = r12
                io.ktor.utils.io.core.a0 r2 = (io.ktor.utils.io.core.a0) r2
                java.lang.Object r12 = r0.L$2
                r10 = r12
                io.ktor.utils.io.l r10 = (io.ktor.utils.io.l) r10
                java.lang.Object r12 = r0.L$1
                r6 = r12
                io.ktor.utils.io.core.a0 r6 = (io.ktor.utils.io.core.a0) r6
                java.lang.Object r12 = r0.L$0
                r9 = r12
                io.ktor.utils.io.x r9 = (io.ktor.utils.io.x) r9
                kotlin.p.b(r18)
                r12 = r10
                r13 = r11
                r11 = r7
                r10 = r9
                r7 = r0
                r9 = r2
                r2 = r1
                r1 = r18
                goto L_0x01a4
            L_0x00ef:
                r2 = r5
                r6 = r5
                r7 = r4
                r8 = r4
                r9 = r5
                r10 = r5
                int r8 = r0.I$0
                java.lang.Object r11 = r0.L$4
                io.ktor.http.cio.c$e r11 = (io.ktor.http.cio.c.e) r11
                java.lang.Object r12 = r0.L$3
                r2 = r12
                io.ktor.utils.io.core.a0 r2 = (io.ktor.utils.io.core.a0) r2
                java.lang.Object r12 = r0.L$2
                r10 = r12
                io.ktor.utils.io.l r10 = (io.ktor.utils.io.l) r10
                java.lang.Object r12 = r0.L$1
                r6 = r12
                io.ktor.utils.io.core.a0 r6 = (io.ktor.utils.io.core.a0) r6
                java.lang.Object r12 = r0.L$0
                r9 = r12
                io.ktor.utils.io.x r9 = (io.ktor.utils.io.x) r9
                kotlin.p.b(r18)
                r12 = r10
                r13 = r11
                r11 = r7
                r10 = r9
                r7 = r0
                r9 = r2
                r2 = r1
                r1 = r18
                goto L_0x018e
            L_0x011d:
                r2 = r5
                java.lang.Object r6 = r0.L$0
                r2 = r6
                io.ktor.utils.io.x r2 = (io.ktor.utils.io.x) r2
                kotlin.p.b(r18)
                r8 = r18
                r7 = r0
                r6 = r2
                r2 = r1
                r1 = r8
                goto L_0x0144
            L_0x012d:
                kotlin.p.b(r18)
                io.ktor.utils.io.x r2 = r0.p$
                r7 = r0
                r6 = r2
                r2 = r1
                r1 = r18
            L_0x0137:
                r8 = 4088(0xff8, float:5.729E-42)
                r7.L$0 = r6
                r7.label = r3
                java.lang.Object r8 = r6.k(r8, r7)
                if (r8 != r2) goto L_0x0144
                return r2
            L_0x0144:
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                if (r8 == 0) goto L_0x01d1
                io.ktor.utils.io.core.a0 r8 = io.ktor.utils.io.u.a.a(r6, r4, r3, r5)
                if (r8 == 0) goto L_0x01ce
                io.ktor.utils.io.l r10 = r7.$output
                io.ktor.utils.io.core.a0 r9 = r7.$view
                r11 = 0
                r12 = r8
                r13 = 0
                int r14 = r12.s()
                int r15 = r12.o()
                int r14 = r14 - r15
                r12 = r14
                r9.J()
                io.ktor.http.cio.internals.d.l(r9, r12)
                r13 = 3338(0xd0a, float:4.678E-42)
                r14 = r9
                r15 = 0
                io.ktor.utils.io.core.i.e(r14, r13)
                r7.L$0 = r6
                r7.L$1 = r8
                r7.L$2 = r10
                r7.L$3 = r9
                r7.L$4 = r7
                r7.I$0 = r12
                r13 = 2
                r7.label = r13
                java.lang.Object r13 = r10.m(r9, r7)
                if (r13 != r2) goto L_0x0186
                return r2
            L_0x0186:
                r13 = r7
                r16 = r10
                r10 = r6
                r6 = r8
                r8 = r12
                r12 = r16
            L_0x018e:
                r7.L$0 = r10
                r7.L$1 = r6
                r7.L$2 = r12
                r7.L$3 = r9
                r7.L$4 = r13
                r7.I$0 = r8
                r14 = 3
                r7.label = r14
                java.lang.Object r14 = r12.m(r6, r13)
                if (r14 != r2) goto L_0x01a4
                return r2
            L_0x01a4:
                byte[] r14 = io.ktor.http.cio.c.b
                r7.L$0 = r10
                r7.L$1 = r6
                r7.L$2 = r12
                r7.L$3 = r9
                r7.I$0 = r8
                r15 = 4
                r7.label = r15
                java.lang.Object r13 = io.ktor.utils.io.m.b(r12, r14, r13)
                if (r13 != r2) goto L_0x01bc
                return r2
            L_0x01bc:
                r16 = r6
                r6 = r2
                r2 = r9
                r9 = r10
                r10 = r8
                r8 = r7
                r7 = r16
            L_0x01c5:
                r12.flush()
                r2 = r6
                r7 = r8
                r6 = r9
                goto L_0x0137
            L_0x01ce:
                kotlin.x r2 = kotlin.x.a
                return r2
            L_0x01d1:
                io.ktor.utils.io.core.a0 r3 = io.ktor.utils.io.u.a.a(r6, r4, r3, r5)
                if (r3 == 0) goto L_0x0255
                r5 = r3
                r4 = 0
                io.ktor.utils.io.l r3 = r7.$output
                io.ktor.utils.io.core.a0 r8 = r7.$view
                r9 = 0
                r10 = r5
                r11 = 0
                int r12 = r10.s()
                int r13 = r10.o()
                int r12 = r12 - r13
                r10 = r12
                r8.J()
                io.ktor.http.cio.internals.d.l(r8, r10)
                r11 = 3338(0xd0a, float:4.678E-42)
                r12 = r8
                r13 = 0
                io.ktor.utils.io.core.i.e(r12, r11)
                r7.L$0 = r6
                r7.L$1 = r5
                r7.L$2 = r3
                r7.L$3 = r8
                r7.L$4 = r7
                r7.I$0 = r10
                r11 = 5
                r7.label = r11
                java.lang.Object r11 = r3.m(r8, r7)
                if (r11 != r2) goto L_0x020d
                return r2
            L_0x020d:
                r11 = r7
                r16 = r6
                r6 = r3
                r3 = r9
                r9 = r16
            L_0x0214:
                r7.L$0 = r9
                r7.L$1 = r5
                r7.L$2 = r6
                r7.L$3 = r8
                r7.L$4 = r11
                r7.I$0 = r10
                r12 = 6
                r7.label = r12
                java.lang.Object r12 = r6.m(r5, r11)
                if (r12 != r2) goto L_0x022a
                return r2
            L_0x022a:
                r16 = r3
                r3 = r2
                r2 = r10
                r10 = r9
                r9 = r7
                r7 = r5
                r5 = r4
                r4 = r16
            L_0x0234:
                byte[] r12 = io.ktor.http.cio.c.b
                r9.L$0 = r10
                r9.L$1 = r7
                r9.L$2 = r6
                r9.L$3 = r8
                r9.I$0 = r2
                r13 = 7
                r9.label = r13
                java.lang.Object r11 = io.ktor.utils.io.m.b(r6, r12, r11)
                if (r11 != r3) goto L_0x024c
                return r3
            L_0x024c:
                r3 = r5
                r5 = r7
                r7 = r9
            L_0x024f:
                r6.flush()
                r6 = r10
            L_0x0255:
                kotlin.x r2 = kotlin.x.a
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.c.e.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
