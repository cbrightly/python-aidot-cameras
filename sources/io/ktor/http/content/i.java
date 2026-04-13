package io.ktor.http.content;

import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Multipart.kt */
public final class i {

    @f(c = "io.ktor.http.content.MultipartKt", f = "Multipart.kt", l = {107, 108}, m = "forEachPart")
    /* compiled from: Multipart.kt */
    public static final class a extends d {
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
            return i.a((g) null, (p<? super k, ? super kotlin.coroutines.d<? super x>, ? extends Object>) null, this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: kotlin.jvm.functions.p<? super io.ktor.http.content.k, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: io.ktor.http.content.g} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: kotlin.jvm.functions.p<? super io.ktor.http.content.k, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends java.lang.Object>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: io.ktor.http.content.g} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0060 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object a(@org.jetbrains.annotations.NotNull io.ktor.http.content.g r6, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.p<? super io.ktor.http.content.k, ? super kotlin.coroutines.d<? super kotlin.x>, ? extends java.lang.Object> r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r8) {
        /*
            boolean r0 = r8 instanceof io.ktor.http.content.i.a
            if (r0 == 0) goto L_0x0013
            r0 = r8
            io.ktor.http.content.i$a r0 = (io.ktor.http.content.i.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.http.content.i$a r0 = new io.ktor.http.content.i$a
            r0.<init>(r8)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004f;
                case 1: goto L_0x003f;
                case 2: goto L_0x002b;
                default: goto L_0x0023;
            }
        L_0x0023:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002b:
            r3 = 0
            java.lang.Object r4 = r0.L$2
            r3 = r4
            io.ktor.http.content.k r3 = (io.ktor.http.content.k) r3
            java.lang.Object r4 = r0.L$1
            r7 = r4
            kotlin.jvm.functions.p r7 = (kotlin.jvm.functions.p) r7
            java.lang.Object r4 = r0.L$0
            r6 = r4
            io.ktor.http.content.g r6 = (io.ktor.http.content.g) r6
            kotlin.p.b(r1)
            goto L_0x007d
        L_0x003f:
            java.lang.Object r3 = r0.L$1
            r7 = r3
            kotlin.jvm.functions.p r7 = (kotlin.jvm.functions.p) r7
            java.lang.Object r3 = r0.L$0
            r6 = r3
            io.ktor.http.content.g r6 = (io.ktor.http.content.g) r6
            kotlin.p.b(r1)
            r3 = r2
            r2 = r1
            goto L_0x0065
        L_0x004f:
            kotlin.p.b(r1)
        L_0x0052:
            r0.L$0 = r6
            r0.L$1 = r7
            r3 = 1
            r0.label = r3
            java.lang.Object r3 = r6.a(r0)
            if (r3 != r2) goto L_0x0061
            return r2
        L_0x0061:
            r5 = r2
            r2 = r1
            r1 = r3
            r3 = r5
        L_0x0065:
            io.ktor.http.content.k r1 = (io.ktor.http.content.k) r1
            if (r1 == 0) goto L_0x007e
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r1
            r4 = 2
            r0.label = r4
            java.lang.Object r4 = r7.invoke(r1, r0)
            if (r4 != r3) goto L_0x0079
            return r3
        L_0x0079:
            r5 = r3
            r3 = r1
            r1 = r2
            r2 = r5
        L_0x007d:
            goto L_0x0052
        L_0x007e:
            kotlin.x r1 = kotlin.x.a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.http.content.i.a(io.ktor.http.content.g, kotlin.jvm.functions.p, kotlin.coroutines.d):java.lang.Object");
    }
}
