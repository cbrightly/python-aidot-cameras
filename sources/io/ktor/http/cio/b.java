package io.ktor.http.cio;

import io.ktor.http.cio.j;
import java.io.File;
import java.io.FileInputStream;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import kotlinx.coroutines.channels.w;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CIOMultipartData.kt */
public final class b implements io.ktor.http.content.g, o0 {
    private final w<j> c;
    @NotNull
    private final kotlin.coroutines.g d;
    private final int f;
    private final int q;

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.CIOMultipartDataBase", f = "CIOMultipartData.kt", l = {55}, m = "eventToData")
    /* compiled from: CIOMultipartData.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(b bVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.b((j) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.CIOMultipartDataBase", f = "CIOMultipartData.kt", l = {68, 76, 79, 98, 125}, m = "partToData")
    /* renamed from: io.ktor.http.cio.b$b  reason: collision with other inner class name */
    /* compiled from: CIOMultipartData.kt */
    public static final class C0249b extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$10;
        Object L$11;
        Object L$12;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        Object L$8;
        Object L$9;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0249b(b bVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.c((j.b) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.CIOMultipartDataBase", f = "CIOMultipartData.kt", l = {35, 38}, m = "readPart")
    /* compiled from: CIOMultipartData.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(b bVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.http.cio.CIOMultipartDataBase", f = "CIOMultipartData.kt", l = {44, 45}, m = "readPartSuspend")
    /* compiled from: CIOMultipartData.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(b bVar, kotlin.coroutines.d dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.d(this);
        }
    }

    public b(@NotNull kotlin.coroutines.g coroutineContext, @NotNull io.ktor.utils.io.i channel, @NotNull CharSequence contentType, @Nullable Long contentLength, int formFieldLimit, int inMemoryFileUploadLimit) {
        k.f(coroutineContext, "coroutineContext");
        k.f(channel, "channel");
        k.f(contentType, "contentType");
        this.d = coroutineContext;
        this.f = formFieldLimit;
        this.q = inMemoryFileUploadLimit;
        this.c = k.f(this, channel, contentType, contentLength);
    }

    @NotNull
    public kotlin.coroutines.g getCoroutineContext() {
        return this.d;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ b(kotlin.coroutines.g r8, io.ktor.utils.io.i r9, java.lang.CharSequence r10, java.lang.Long r11, int r12, int r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r15 = r14 & 16
            if (r15 == 0) goto L_0x0008
            r12 = 65536(0x10000, float:9.18355E-41)
            r5 = r12
            goto L_0x0009
        L_0x0008:
            r5 = r12
        L_0x0009:
            r12 = r14 & 32
            if (r12 == 0) goto L_0x000f
            r6 = r5
            goto L_0x0010
        L_0x000f:
            r6 = r13
        L_0x0010:
            r0 = r7
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.b.<init>(kotlin.coroutines.g, io.ktor.utils.io.i, java.lang.CharSequence, java.lang.Long, int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* Debug info: failed to restart local var, previous not found, register: 7 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.http.content.k> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof io.ktor.http.cio.b.i
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.http.cio.b$i r0 = (io.ktor.http.cio.b.i) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.http.cio.b$i r0 = new io.ktor.http.cio.b$i
            r0.<init>(r7, r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0044;
                case 1: goto L_0x0034;
                case 2: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            java.lang.Object r2 = r0.L$0
            io.ktor.http.cio.b r2 = (io.ktor.http.cio.b) r2
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x007e
        L_0x0034:
            r3 = 0
            java.lang.Object r4 = r0.L$1
            r3 = r4
            io.ktor.http.cio.j r3 = (io.ktor.http.cio.j) r3
            java.lang.Object r4 = r0.L$0
            io.ktor.http.cio.b r4 = (io.ktor.http.cio.b) r4
            kotlin.p.b(r1)
            r5 = r2
            r2 = r1
            goto L_0x0068
        L_0x0044:
            kotlin.p.b(r1)
            r3 = r2
            r2 = r7
        L_0x0049:
            kotlinx.coroutines.channels.w<io.ktor.http.cio.j> r4 = r2.c
            java.lang.Object r4 = r4.poll()
            io.ktor.http.cio.j r4 = (io.ktor.http.cio.j) r4
            if (r4 == 0) goto L_0x0072
            r0.L$0 = r2
            r0.L$1 = r4
            r5 = 1
            r0.label = r5
            java.lang.Object r5 = r2.b(r4, r0)
            if (r5 != r3) goto L_0x0062
            return r3
        L_0x0062:
            r6 = r2
            r2 = r1
            r1 = r5
            r5 = r3
            r3 = r4
            r4 = r6
        L_0x0068:
            io.ktor.http.content.k r1 = (io.ktor.http.content.k) r1
            if (r1 == 0) goto L_0x006e
            r5 = 0
            return r1
        L_0x006e:
            r1 = r2
            r2 = r4
            r3 = r5
            goto L_0x0049
        L_0x0072:
            r0.L$0 = r2
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r2.d(r0)
            if (r4 != r3) goto L_0x007e
            return r3
        L_0x007e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.b.a(kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 8 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: io.ktor.http.cio.b} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object d(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.http.content.k> r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof io.ktor.http.cio.b.j
            if (r0 == 0) goto L_0x0013
            r0 = r9
            io.ktor.http.cio.b$j r0 = (io.ktor.http.cio.b.j) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.http.cio.b$j r0 = new io.ktor.http.cio.b$j
            r0.<init>(r8, r9)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x004c;
                case 1: goto L_0x003f;
                case 2: goto L_0x002d;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002d:
            r3 = r4
            java.lang.Object r5 = r0.L$1
            r3 = r5
            io.ktor.http.cio.j r3 = (io.ktor.http.cio.j) r3
            java.lang.Object r5 = r0.L$0
            io.ktor.http.cio.b r5 = (io.ktor.http.cio.b) r5
            kotlin.p.b(r1)     // Catch:{ ClosedReceiveChannelException -> 0x004a }
            r6 = r5
            r5 = r3
            r3 = r2
            r2 = r1
            goto L_0x0077
        L_0x003f:
            java.lang.Object r3 = r0.L$0
            r5 = r3
            io.ktor.http.cio.b r5 = (io.ktor.http.cio.b) r5
            kotlin.p.b(r1)     // Catch:{ ClosedReceiveChannelException -> 0x004a }
            r3 = r2
            r2 = r1
            goto L_0x0063
        L_0x004a:
            r2 = move-exception
            goto L_0x008b
        L_0x004c:
            kotlin.p.b(r1)
            r5 = r8
        L_0x0050:
            kotlinx.coroutines.channels.w<io.ktor.http.cio.j> r3 = r5.c     // Catch:{ ClosedReceiveChannelException -> 0x004a }
            r0.L$0 = r5     // Catch:{ ClosedReceiveChannelException -> 0x004a }
            r6 = 1
            r0.label = r6     // Catch:{ ClosedReceiveChannelException -> 0x004a }
            java.lang.Object r3 = r3.C(r0)     // Catch:{ ClosedReceiveChannelException -> 0x004a }
            if (r3 != r2) goto L_0x005f
            return r2
        L_0x005f:
            r7 = r2
            r2 = r1
            r1 = r3
            r3 = r7
        L_0x0063:
            io.ktor.http.cio.j r1 = (io.ktor.http.cio.j) r1     // Catch:{ ClosedReceiveChannelException -> 0x0087 }
            r0.L$0 = r5     // Catch:{ ClosedReceiveChannelException -> 0x0087 }
            r0.L$1 = r1     // Catch:{ ClosedReceiveChannelException -> 0x0087 }
            r6 = 2
            r0.label = r6     // Catch:{ ClosedReceiveChannelException -> 0x0087 }
            java.lang.Object r6 = r5.b(r1, r0)     // Catch:{ ClosedReceiveChannelException -> 0x0087 }
            if (r6 != r3) goto L_0x0073
            return r3
        L_0x0073:
            r7 = r5
            r5 = r1
            r1 = r6
            r6 = r7
        L_0x0077:
            io.ktor.http.content.k r1 = (io.ktor.http.content.k) r1     // Catch:{ ClosedReceiveChannelException -> 0x0081 }
            if (r1 == 0) goto L_0x007d
            r3 = 0
            return r1
        L_0x007d:
            r1 = r2
            r2 = r3
            r5 = r6
            goto L_0x0050
        L_0x0081:
            r1 = move-exception
            r5 = r6
            r7 = r2
            r2 = r1
            r1 = r7
            goto L_0x008b
        L_0x0087:
            r1 = move-exception
            r7 = r2
            r2 = r1
            r1 = r7
        L_0x008b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.b.d(kotlin.coroutines.d):java.lang.Object");
    }

    /* Debug info: failed to restart local var, previous not found, register: 5 */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: io.ktor.http.cio.j} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        return (io.ktor.http.content.k) r3;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object b(@org.jetbrains.annotations.NotNull io.ktor.http.cio.j r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.http.content.k> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof io.ktor.http.cio.b.a
            if (r0 == 0) goto L_0x0013
            r0 = r7
            io.ktor.http.cio.b$a r0 = (io.ktor.http.cio.b.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.http.cio.b$a r0 = new io.ktor.http.cio.b$a
            r0.<init>(r5, r7)
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
            java.lang.Object r2 = r0.L$1
            r6 = r2
            io.ktor.http.cio.j r6 = (io.ktor.http.cio.j) r6
            java.lang.Object r2 = r0.L$0
            io.ktor.http.cio.b r2 = (io.ktor.http.cio.b) r2
            kotlin.p.b(r1)     // Catch:{ all -> 0x0039 }
            r3 = r1
            goto L_0x0056
        L_0x0039:
            r3 = move-exception
            goto L_0x0062
        L_0x003b:
            kotlin.p.b(r1)
            boolean r3 = r6 instanceof io.ktor.http.cio.j.b     // Catch:{ all -> 0x0060 }
            if (r3 == 0) goto L_0x0059
            r3 = r6
            io.ktor.http.cio.j$b r3 = (io.ktor.http.cio.j.b) r3     // Catch:{ all -> 0x0060 }
            r0.L$0 = r5     // Catch:{ all -> 0x0060 }
            r0.L$1 = r6     // Catch:{ all -> 0x0060 }
            r4 = 1
            r0.label = r4     // Catch:{ all -> 0x0060 }
            java.lang.Object r3 = r5.c(r3, r0)     // Catch:{ all -> 0x0060 }
            if (r3 != r2) goto L_0x0055
            return r2
        L_0x0055:
            r2 = r5
        L_0x0056:
            io.ktor.http.content.k r3 = (io.ktor.http.content.k) r3     // Catch:{ all -> 0x0039 }
            goto L_0x005e
        L_0x0059:
            r6.a()     // Catch:{ all -> 0x0060 }
            r3 = 0
            r2 = r5
        L_0x005e:
            return r3
        L_0x0060:
            r3 = move-exception
            r2 = r5
        L_0x0062:
            r6.a()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.b.b(io.ktor.http.cio.j, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0282, code lost:
        return new io.ktor.http.content.k.a(new io.ktor.http.cio.b.e(io.ktor.utils.io.streams.b.b(new java.io.ByteArrayInputStream(r4.array(), r4.arrayOffset(), r4.remaining()), (io.ktor.utils.io.pool.d) null, 1, (java.lang.Object) null)), new io.ktor.http.cio.b.f(r11), new io.ktor.http.cio.a(r9));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0283, code lost:
        r2.L$0 = r8;
        r2.L$1 = r4;
        r2.L$2 = r0;
        r2.L$3 = r9;
        r2.L$4 = r11;
        r2.label = 5;
        r1 = io.ktor.utils.io.k.f(r4.b(), (long) r8.f, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x029b, code lost:
        if (r1 != r3) goto L_0x029e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x029d, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x029e, code lost:
        r3 = r0;
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x02a0, code lost:
        r1 = (io.ktor.utils.io.core.q) r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02bb, code lost:
        return new io.ktor.http.content.k.b(io.ktor.utils.io.core.a.l1(r1, 0, 0, 3, (java.lang.Object) null), new io.ktor.http.cio.b.g(r4), new io.ktor.http.cio.a(r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02bc, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02bd, code lost:
        r1.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02c0, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00f9, code lost:
        r0 = (io.ktor.http.cio.f) r0;
        r9 = r0.e(com.didichuxing.doraemonkit.okgo.model.HttpHeaders.HEAD_KEY_CONTENT_DISPOSITION);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0101, code lost:
        if (r9 == null) goto L_0x010e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0103, code lost:
        r9 = io.ktor.http.b.h.a(r9.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x010e, code lost:
        r9 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x010f, code lost:
        if (r9 == null) goto L_0x0118;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0111, code lost:
        r11 = r9.c("filename");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0118, code lost:
        r11 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0119, code lost:
        if (r11 == null) goto L_0x0283;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x011b, code lost:
        r12 = java.nio.ByteBuffer.allocate(r8.q);
        r13 = r4.b();
        kotlin.jvm.internal.k.b(r12, "buffer");
        r2.L$0 = r8;
        r2.L$1 = r4;
        r2.L$2 = r0;
        r2.L$3 = r9;
        r2.L$4 = r11;
        r2.L$5 = r12;
        r2.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x013b, code lost:
        if (r13.v(r12, r2) != r3) goto L_0x013e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x013d, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x013e, code lost:
        r17 = r11;
        r11 = r4;
        r4 = r12;
        r12 = r8;
        r8 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0149, code lost:
        if (r4.remaining() <= 0) goto L_0x0180;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x014b, code lost:
        r13 = r11.b();
        kotlin.jvm.internal.k.b(r4, "buffer");
        r2.L$0 = r12;
        r2.L$1 = r11;
        r2.L$2 = r0;
        r2.L$3 = r9;
        r2.L$4 = r8;
        r2.L$5 = r4;
        r2.label = 3;
        r5 = r13.v(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0164, code lost:
        if (r5 != r3) goto L_0x0167;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0166, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0167, code lost:
        r17 = r9;
        r9 = r0;
        r0 = r5;
        r5 = r8;
        r8 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0175, code lost:
        if (((java.lang.Number) r0).intValue() != -1) goto L_0x0179;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0177, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0179, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x017a, code lost:
        r17 = r8;
        r8 = r5;
        r5 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0180, code lost:
        r5 = r9;
        r9 = r0;
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0183, code lost:
        r4.flip();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0187, code lost:
        if (r0 != false) goto L_0x0259;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0189, code lost:
        r0 = java.io.File.createTempFile("file-upload", ".tmp");
        r7 = new java.io.FileOutputStream(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r13 = r7.getChannel();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        r13.truncate(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x019f, code lost:
        r14 = r0;
        r15 = r4;
        r1 = r8;
        r8 = r9;
        r0 = null;
        r4 = r12;
        r9 = r5;
        r12 = r7;
        r5 = null;
        r10 = r12;
        r7 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x01af, code lost:
        if (r15.hasRemaining() == false) goto L_0x01b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x01b1, code lost:
        r13.write(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x01b5, code lost:
        r15.clear();
        r16 = r3;
        r3 = r11.b();
        kotlin.jvm.internal.k.b(r15, "buffer");
        r2.L$0 = r4;
        r2.L$1 = r11;
        r2.L$2 = r8;
        r2.L$3 = r9;
        r2.L$4 = r1;
        r2.L$5 = r15;
        r2.L$6 = r14;
        r2.L$7 = r12;
        r2.L$8 = r0;
        r2.L$9 = r10;
        r2.L$10 = r7;
        r2.L$11 = r5;
        r2.L$12 = r13;
        r19 = r0;
        r2.label = 4;
        r0 = r3.v(r15, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x01e4, code lost:
        r3 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x01e6, code lost:
        if (r0 != r3) goto L_0x01e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01e8, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01e9, code lost:
        r16 = r15;
        r15 = r14;
        r14 = r13;
        r13 = r12;
        r12 = r11;
        r11 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
        r19 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01fa, code lost:
        if (((java.lang.Number) r0).intValue() != -1) goto L_0x022f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01fc, code lost:
        r0 = kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        kotlin.io.b.a(r7, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0201, code lost:
        kotlin.io.b.a(r13, r11);
        r0 = new kotlin.jvm.internal.w();
        r0.element = false;
        r1 = kotlin.i.b(new io.ktor.http.cio.b.h(r0, r15));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x022a, code lost:
        return new io.ktor.http.content.k.a(new io.ktor.http.cio.b.c(r1), new io.ktor.http.cio.b.d(r0, r1, r12, r15), new io.ktor.http.cio.a(r8));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x022b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x022c, code lost:
        r1 = r0;
        r7 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        r16.flip();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0232, code lost:
        r1 = r19;
        r0 = r11;
        r11 = r12;
        r12 = r13;
        r13 = r14;
        r14 = r15;
        r15 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x023d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x023e, code lost:
        r1 = r0;
        r12 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0241, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0242, code lost:
        r1 = r0;
        r12 = r7;
        r7 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0250, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0251, code lost:
        r1 = r0;
        r7 = r7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00e2  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object c(@org.jetbrains.annotations.NotNull io.ktor.http.cio.j.b r19, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super io.ktor.http.content.k> r20) {
        /*
            r18 = this;
            r1 = r18
            r0 = r20
            boolean r2 = r0 instanceof io.ktor.http.cio.b.C0249b
            if (r2 == 0) goto L_0x0017
            r2 = r0
            io.ktor.http.cio.b$b r2 = (io.ktor.http.cio.b.C0249b) r2
            int r3 = r2.label
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r3 & r4
            if (r5 == 0) goto L_0x0017
            int r3 = r3 - r4
            r2.label = r3
            goto L_0x001c
        L_0x0017:
            io.ktor.http.cio.b$b r2 = new io.ktor.http.cio.b$b
            r2.<init>(r1, r0)
        L_0x001c:
            java.lang.Object r0 = r2.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r2.label
            r5 = 3
            java.lang.String r6 = "buffer"
            r7 = 1
            r10 = 0
            switch(r4) {
                case 0: goto L_0x00e2;
                case 1: goto L_0x00d6;
                case 2: goto L_0x00b6;
                case 3: goto L_0x0099;
                case 4: goto L_0x004f;
                case 5: goto L_0x0036;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0036:
            java.lang.Object r3 = r2.L$4
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r3 = r2.L$3
            io.ktor.http.b r3 = (io.ktor.http.b) r3
            java.lang.Object r3 = r2.L$2
            io.ktor.http.cio.f r3 = (io.ktor.http.cio.f) r3
            java.lang.Object r4 = r2.L$1
            io.ktor.http.cio.j$b r4 = (io.ktor.http.cio.j.b) r4
            java.lang.Object r2 = r2.L$0
            io.ktor.http.cio.b r2 = (io.ktor.http.cio.b) r2
            kotlin.p.b(r0)
            goto L_0x02a0
        L_0x004f:
            java.lang.Object r4 = r2.L$12
            java.nio.channels.FileChannel r4 = (java.nio.channels.FileChannel) r4
            java.lang.Object r5 = r2.L$11
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            java.lang.Object r7 = r2.L$10
            java.io.Closeable r7 = (java.io.Closeable) r7
            java.lang.Object r10 = r2.L$9
            java.io.FileOutputStream r10 = (java.io.FileOutputStream) r10
            java.lang.Object r11 = r2.L$8
            java.lang.Throwable r11 = (java.lang.Throwable) r11
            java.lang.Object r12 = r2.L$7
            java.io.Closeable r12 = (java.io.Closeable) r12
            java.lang.Object r13 = r2.L$6
            java.io.File r13 = (java.io.File) r13
            java.lang.Object r14 = r2.L$5
            java.nio.ByteBuffer r14 = (java.nio.ByteBuffer) r14
            java.lang.Object r15 = r2.L$4
            java.lang.String r15 = (java.lang.String) r15
            java.lang.Object r9 = r2.L$3
            io.ktor.http.b r9 = (io.ktor.http.b) r9
            java.lang.Object r8 = r2.L$2
            io.ktor.http.cio.f r8 = (io.ktor.http.cio.f) r8
            r19 = r4
            java.lang.Object r4 = r2.L$1
            io.ktor.http.cio.j$b r4 = (io.ktor.http.cio.j.b) r4
            r16 = r4
            java.lang.Object r4 = r2.L$0
            io.ktor.http.cio.b r4 = (io.ktor.http.cio.b) r4
            kotlin.p.b(r0)     // Catch:{ all -> 0x0095 }
            r1 = r15
            r15 = r13
            r13 = r12
            r12 = r16
            r16 = r14
            r14 = r19
            goto L_0x01f1
        L_0x0095:
            r0 = move-exception
            r1 = r0
            goto L_0x0245
        L_0x0099:
            java.lang.Object r4 = r2.L$5
            java.nio.ByteBuffer r4 = (java.nio.ByteBuffer) r4
            java.lang.Object r5 = r2.L$4
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r8 = r2.L$3
            io.ktor.http.b r8 = (io.ktor.http.b) r8
            java.lang.Object r9 = r2.L$2
            io.ktor.http.cio.f r9 = (io.ktor.http.cio.f) r9
            java.lang.Object r11 = r2.L$1
            io.ktor.http.cio.j$b r11 = (io.ktor.http.cio.j.b) r11
            java.lang.Object r12 = r2.L$0
            io.ktor.http.cio.b r12 = (io.ktor.http.cio.b) r12
            kotlin.p.b(r0)
            goto L_0x016e
        L_0x00b6:
            java.lang.Object r4 = r2.L$5
            java.nio.ByteBuffer r4 = (java.nio.ByteBuffer) r4
            java.lang.Object r8 = r2.L$4
            java.lang.String r8 = (java.lang.String) r8
            java.lang.Object r9 = r2.L$3
            io.ktor.http.b r9 = (io.ktor.http.b) r9
            java.lang.Object r11 = r2.L$2
            io.ktor.http.cio.f r11 = (io.ktor.http.cio.f) r11
            java.lang.Object r12 = r2.L$1
            io.ktor.http.cio.j$b r12 = (io.ktor.http.cio.j.b) r12
            java.lang.Object r13 = r2.L$0
            io.ktor.http.cio.b r13 = (io.ktor.http.cio.b) r13
            kotlin.p.b(r0)
            r0 = r11
            r11 = r12
            r12 = r13
            goto L_0x0145
        L_0x00d6:
            java.lang.Object r4 = r2.L$1
            io.ktor.http.cio.j$b r4 = (io.ktor.http.cio.j.b) r4
            java.lang.Object r8 = r2.L$0
            io.ktor.http.cio.b r8 = (io.ktor.http.cio.b) r8
            kotlin.p.b(r0)
            goto L_0x00f9
        L_0x00e2:
            kotlin.p.b(r0)
            kotlinx.coroutines.w0 r0 = r19.c()
            r2.L$0 = r1
            r4 = r19
            r2.L$1 = r4
            r2.label = r7
            java.lang.Object r0 = r0.r(r2)
            if (r0 != r3) goto L_0x00f8
            return r3
        L_0x00f8:
            r8 = r1
        L_0x00f9:
            io.ktor.http.cio.f r0 = (io.ktor.http.cio.f) r0
            java.lang.String r9 = "Content-Disposition"
            java.lang.CharSequence r9 = r0.e(r9)
            if (r9 == 0) goto L_0x010e
            io.ktor.http.b$a r11 = io.ktor.http.b.h
            java.lang.String r9 = r9.toString()
            io.ktor.http.b r9 = r11.a(r9)
            goto L_0x010f
        L_0x010e:
            r9 = r10
        L_0x010f:
            if (r9 == 0) goto L_0x0118
            java.lang.String r11 = "filename"
            java.lang.String r11 = r9.c(r11)
            goto L_0x0119
        L_0x0118:
            r11 = r10
        L_0x0119:
            if (r11 == 0) goto L_0x0283
            int r12 = r8.q
            java.nio.ByteBuffer r12 = java.nio.ByteBuffer.allocate(r12)
            io.ktor.utils.io.i r13 = r4.b()
            kotlin.jvm.internal.k.b(r12, r6)
            r2.L$0 = r8
            r2.L$1 = r4
            r2.L$2 = r0
            r2.L$3 = r9
            r2.L$4 = r11
            r2.L$5 = r12
            r14 = 2
            r2.label = r14
            java.lang.Object r13 = r13.v(r12, r2)
            if (r13 != r3) goto L_0x013e
            return r3
        L_0x013e:
            r17 = r11
            r11 = r4
            r4 = r12
            r12 = r8
            r8 = r17
        L_0x0145:
            int r13 = r4.remaining()
            if (r13 <= 0) goto L_0x0180
            io.ktor.utils.io.i r13 = r11.b()
            kotlin.jvm.internal.k.b(r4, r6)
            r2.L$0 = r12
            r2.L$1 = r11
            r2.L$2 = r0
            r2.L$3 = r9
            r2.L$4 = r8
            r2.L$5 = r4
            r2.label = r5
            java.lang.Object r5 = r13.v(r4, r2)
            if (r5 != r3) goto L_0x0167
            return r3
        L_0x0167:
            r17 = r9
            r9 = r0
            r0 = r5
            r5 = r8
            r8 = r17
        L_0x016e:
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
            r13 = -1
            if (r0 != r13) goto L_0x0179
            r0 = r7
            goto L_0x017a
        L_0x0179:
            r0 = 0
        L_0x017a:
            r17 = r8
            r8 = r5
            r5 = r17
            goto L_0x0183
        L_0x0180:
            r5 = r9
            r9 = r0
            r0 = 0
        L_0x0183:
            r4.flip()
            if (r0 != 0) goto L_0x0259
            java.lang.String r0 = "file-upload"
            java.lang.String r7 = ".tmp"
            java.io.File r0 = java.io.File.createTempFile(r0, r7)
            java.io.FileOutputStream r7 = new java.io.FileOutputStream
            r7.<init>(r0)
            java.nio.channels.FileChannel r13 = r7.getChannel()     // Catch:{ all -> 0x0250 }
            r14 = 0
            r13.truncate(r14)     // Catch:{ all -> 0x0241 }
            r14 = r0
            r15 = r4
            r1 = r8
            r8 = r9
            r0 = r10
            r4 = r12
            r9 = r5
            r12 = r7
            r5 = r0
            r10 = r12
            r7 = r13
        L_0x01aa:
        L_0x01ab:
            boolean r16 = r15.hasRemaining()     // Catch:{ all -> 0x0095 }
            if (r16 == 0) goto L_0x01b5
            r13.write(r15)     // Catch:{ all -> 0x0095 }
            goto L_0x01ab
        L_0x01b5:
            r15.clear()     // Catch:{ all -> 0x0095 }
            r16 = r3
            io.ktor.utils.io.i r3 = r11.b()     // Catch:{ all -> 0x0095 }
            kotlin.jvm.internal.k.b(r15, r6)     // Catch:{ all -> 0x0095 }
            r2.L$0 = r4     // Catch:{ all -> 0x0095 }
            r2.L$1 = r11     // Catch:{ all -> 0x0095 }
            r2.L$2 = r8     // Catch:{ all -> 0x0095 }
            r2.L$3 = r9     // Catch:{ all -> 0x0095 }
            r2.L$4 = r1     // Catch:{ all -> 0x0095 }
            r2.L$5 = r15     // Catch:{ all -> 0x0095 }
            r2.L$6 = r14     // Catch:{ all -> 0x0095 }
            r2.L$7 = r12     // Catch:{ all -> 0x0095 }
            r2.L$8 = r0     // Catch:{ all -> 0x0095 }
            r2.L$9 = r10     // Catch:{ all -> 0x0095 }
            r2.L$10 = r7     // Catch:{ all -> 0x0095 }
            r2.L$11 = r5     // Catch:{ all -> 0x0095 }
            r2.L$12 = r13     // Catch:{ all -> 0x0095 }
            r19 = r0
            r0 = 4
            r2.label = r0     // Catch:{ all -> 0x0095 }
            java.lang.Object r0 = r3.v(r15, r2)     // Catch:{ all -> 0x0095 }
            r3 = r16
            if (r0 != r3) goto L_0x01e9
            return r3
        L_0x01e9:
            r16 = r15
            r15 = r14
            r14 = r13
            r13 = r12
            r12 = r11
            r11 = r19
        L_0x01f1:
            java.lang.Number r0 = (java.lang.Number) r0     // Catch:{ all -> 0x023d }
            int r0 = r0.intValue()     // Catch:{ all -> 0x023d }
            r19 = r1
            r1 = -1
            if (r0 != r1) goto L_0x022f
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x023d }
            kotlin.io.b.a(r7, r5)     // Catch:{ all -> 0x022b }
            kotlin.io.b.a(r13, r11)
            kotlin.jvm.internal.w r0 = new kotlin.jvm.internal.w
            r0.<init>()
            r1 = 0
            r0.element = r1
            io.ktor.http.cio.b$h r1 = new io.ktor.http.cio.b$h
            r1.<init>(r0, r15)
            kotlin.g r1 = kotlin.i.b(r1)
            io.ktor.http.content.k$a r2 = new io.ktor.http.content.k$a
            io.ktor.http.cio.b$c r3 = new io.ktor.http.cio.b$c
            r3.<init>(r1)
            io.ktor.http.cio.b$d r4 = new io.ktor.http.cio.b$d
            r4.<init>(r0, r1, r12, r15)
            io.ktor.http.cio.a r0 = new io.ktor.http.cio.a
            r0.<init>(r8)
            r2.<init>(r3, r4, r0)
            return r2
        L_0x022b:
            r0 = move-exception
            r1 = r0
            r7 = r13
            goto L_0x0252
        L_0x022f:
            r16.flip()     // Catch:{ all -> 0x023d }
            r1 = r19
            r0 = r11
            r11 = r12
            r12 = r13
            r13 = r14
            r14 = r15
            r15 = r16
            goto L_0x01aa
        L_0x023d:
            r0 = move-exception
            r1 = r0
            r12 = r13
            goto L_0x0245
        L_0x0241:
            r0 = move-exception
            r1 = r0
            r12 = r7
            r7 = r13
        L_0x0245:
            throw r1     // Catch:{ all -> 0x0246 }
        L_0x0246:
            r0 = move-exception
            r2 = r0
            kotlin.io.b.a(r7, r1)     // Catch:{ all -> 0x024c }
            throw r2     // Catch:{ all -> 0x024c }
        L_0x024c:
            r0 = move-exception
            r1 = r0
            r7 = r12
            goto L_0x0252
        L_0x0250:
            r0 = move-exception
            r1 = r0
        L_0x0252:
            throw r1     // Catch:{ all -> 0x0253 }
        L_0x0253:
            r0 = move-exception
            r2 = r0
            kotlin.io.b.a(r7, r1)
            throw r2
        L_0x0259:
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            byte[] r1 = r4.array()
            int r2 = r4.arrayOffset()
            int r3 = r4.remaining()
            r0.<init>(r1, r2, r3)
            io.ktor.utils.io.core.w r0 = io.ktor.utils.io.streams.b.b(r0, r10, r7, r10)
            io.ktor.http.content.k$a r1 = new io.ktor.http.content.k$a
            io.ktor.http.cio.b$e r2 = new io.ktor.http.cio.b$e
            r2.<init>(r0)
            io.ktor.http.cio.b$f r0 = new io.ktor.http.cio.b$f
            r0.<init>(r11)
            io.ktor.http.cio.a r3 = new io.ktor.http.cio.a
            r3.<init>(r9)
            r1.<init>(r2, r0, r3)
            return r1
        L_0x0283:
            io.ktor.utils.io.i r1 = r4.b()
            int r6 = r8.f
            long r6 = (long) r6
            r2.L$0 = r8
            r2.L$1 = r4
            r2.L$2 = r0
            r2.L$3 = r9
            r2.L$4 = r11
            r8 = 5
            r2.label = r8
            java.lang.Object r1 = io.ktor.utils.io.k.f(r1, r6, r2)
            if (r1 != r3) goto L_0x029e
            return r3
        L_0x029e:
            r3 = r0
            r0 = r1
        L_0x02a0:
            r1 = r0
            io.ktor.utils.io.core.q r1 = (io.ktor.utils.io.core.q) r1
            io.ktor.http.content.k$b r0 = new io.ktor.http.content.k$b     // Catch:{ all -> 0x02bc }
            r2 = 0
            java.lang.String r2 = io.ktor.utils.io.core.a.l1(r1, r2, r2, r5, r10)     // Catch:{ all -> 0x02bc }
            io.ktor.http.cio.b$g r5 = new io.ktor.http.cio.b$g     // Catch:{ all -> 0x02bc }
            r5.<init>(r4)     // Catch:{ all -> 0x02bc }
            io.ktor.http.cio.a r4 = new io.ktor.http.cio.a     // Catch:{ all -> 0x02bc }
            r4.<init>(r3)     // Catch:{ all -> 0x02bc }
            r0.<init>(r2, r5, r4)     // Catch:{ all -> 0x02bc }
            r1.release()
            return r0
        L_0x02bc:
            r0 = move-exception
            r1.release()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.cio.b.c(io.ktor.http.cio.j$b, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: CIOMultipartData.kt */
    public static final class h extends l implements kotlin.jvm.functions.a<io.ktor.utils.io.core.w> {
        final /* synthetic */ kotlin.jvm.internal.w $closed;
        final /* synthetic */ File $tmp;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(kotlin.jvm.internal.w wVar, File file) {
            super(0);
            this.$closed = wVar;
            this.$tmp = file;
        }

        @NotNull
        public final io.ktor.utils.io.core.w invoke() {
            if (!this.$closed.element) {
                return io.ktor.utils.io.streams.b.b(new FileInputStream(this.$tmp), (io.ktor.utils.io.pool.d) null, 1, (Object) null);
            }
            throw new IllegalStateException("Already disposed");
        }
    }

    /* compiled from: CIOMultipartData.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<io.ktor.utils.io.core.w> {
        final /* synthetic */ kotlin.g $lazyInput;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(kotlin.g gVar) {
            super(0);
            this.$lazyInput = gVar;
        }

        @NotNull
        public final io.ktor.utils.io.core.w invoke() {
            return (io.ktor.utils.io.core.w) this.$lazyInput.getValue();
        }
    }

    /* compiled from: CIOMultipartData.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ kotlin.jvm.internal.w $closed;
        final /* synthetic */ kotlin.g $lazyInput;
        final /* synthetic */ j.b $part;
        final /* synthetic */ File $tmp;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(kotlin.jvm.internal.w wVar, kotlin.g gVar, j.b bVar, File file) {
            super(0);
            this.$closed = wVar;
            this.$lazyInput = gVar;
            this.$part = bVar;
            this.$tmp = file;
        }

        public final void invoke() {
            this.$closed.element = true;
            if (this.$lazyInput.isInitialized()) {
                ((io.ktor.utils.io.core.w) this.$lazyInput.getValue()).close();
            }
            this.$part.a();
            this.$tmp.delete();
        }
    }

    /* compiled from: CIOMultipartData.kt */
    public static final class e extends l implements kotlin.jvm.functions.a<io.ktor.utils.io.core.w> {
        final /* synthetic */ io.ktor.utils.io.core.w $input;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(io.ktor.utils.io.core.w wVar) {
            super(0);
            this.$input = wVar;
        }

        @NotNull
        public final io.ktor.utils.io.core.w invoke() {
            return this.$input;
        }
    }

    /* compiled from: CIOMultipartData.kt */
    public static final class f extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ j.b $part;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(j.b bVar) {
            super(0);
            this.$part = bVar;
        }

        public final void invoke() {
            this.$part.a();
        }
    }

    /* compiled from: CIOMultipartData.kt */
    public static final class g extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ j.b $part;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(j.b bVar) {
            super(0);
            this.$part = bVar;
        }

        public final void invoke() {
            this.$part.a();
        }
    }
}
