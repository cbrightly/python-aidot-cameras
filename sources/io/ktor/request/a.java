package io.ktor.request;

import io.ktor.application.b;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.reflect.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ApplicationReceiveFunctions.kt */
public final class a {
    private static final io.ktor.util.a<f> a = new io.ktor.util.a<>("DoubleReceivePreventionToken");

    @f(c = "io.ktor.request.ApplicationReceiveFunctionsKt", f = "ApplicationReceiveFunctions.kt", l = {110}, m = "receive")
    /* renamed from: io.ktor.request.a$a  reason: collision with other inner class name */
    /* compiled from: ApplicationReceiveFunctions.kt */
    public static final class C0259a extends d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        C0259a(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return a.a((b) null, (n) null, this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v2, resolved type: kotlin.reflect.n} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0023  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object a(@org.jetbrains.annotations.NotNull io.ktor.application.b r13, @org.jetbrains.annotations.NotNull kotlin.reflect.n r14, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super T> r15) {
        /*
            boolean r0 = r15 instanceof io.ktor.request.a.C0259a
            if (r0 == 0) goto L_0x0013
            r0 = r15
            io.ktor.request.a$a r0 = (io.ktor.request.a.C0259a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            io.ktor.request.a$a r0 = new io.ktor.request.a$a
            r0.<init>(r15)
        L_0x0018:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004a;
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
            r4 = r2
            java.lang.Object r5 = r0.L$4
            r4 = r5
            io.ktor.request.c r4 = (io.ktor.request.c) r4
            java.lang.Object r2 = r0.L$3
            java.lang.Object r5 = r0.L$2
            r3 = r5
            io.ktor.request.f r3 = (io.ktor.request.f) r3
            java.lang.Object r5 = r0.L$1
            r14 = r5
            kotlin.reflect.n r14 = (kotlin.reflect.n) r14
            java.lang.Object r5 = r0.L$0
            r13 = r5
            io.ktor.application.b r13 = (io.ktor.application.b) r13
            kotlin.p.b(r1)
            r6 = r4
            r4 = r1
            goto L_0x00a5
        L_0x004a:
            kotlin.p.b(r1)
            java.lang.Class<io.ktor.request.c> r3 = io.ktor.request.c.class
            kotlin.reflect.c r3 = kotlin.jvm.internal.a0.b(r3)
            boolean r3 = kotlin.jvm.internal.k.a(r14, r3)
            r4 = 1
            r3 = r3 ^ r4
            if (r3 == 0) goto L_0x00d3
            io.ktor.util.b r3 = r13.getAttributes()
            io.ktor.util.a<io.ktor.request.f> r5 = a
            java.lang.Object r3 = r3.e(r5)
            io.ktor.request.f r3 = (io.ktor.request.f) r3
            if (r3 != 0) goto L_0x0072
            io.ktor.util.b r6 = r13.getAttributes()
            io.ktor.request.f r7 = io.ktor.request.f.a
            r6.b(r5, r7)
        L_0x0072:
            if (r3 == 0) goto L_0x0076
            r5 = r3
            goto L_0x007e
        L_0x0076:
            io.ktor.request.d r5 = r13.getRequest()
            io.ktor.utils.io.i r5 = r5.c()
        L_0x007e:
            io.ktor.request.c r12 = new io.ktor.request.c
            r9 = 0
            r10 = 4
            r11 = 0
            r6 = r12
            r7 = r14
            r8 = r5
            r6.<init>(r7, r8, r9, r10, r11)
            io.ktor.request.d r7 = r13.getRequest()
            io.ktor.request.b r7 = r7.a()
            r0.L$0 = r13
            r0.L$1 = r14
            r0.L$2 = r3
            r0.L$3 = r5
            r0.L$4 = r6
            r0.label = r4
            java.lang.Object r4 = r7.e(r13, r6, r0)
            if (r4 != r2) goto L_0x00a4
            return r2
        L_0x00a4:
            r2 = r5
        L_0x00a5:
            io.ktor.request.c r4 = (io.ktor.request.c) r4
            java.lang.Object r5 = r4.c()
            io.ktor.request.f r7 = io.ktor.request.f.a
            if (r5 == r7) goto L_0x00cd
            kotlin.reflect.c r7 = kotlin.reflect.jvm.b.b(r14)
            boolean r7 = r7.k(r5)
            if (r7 == 0) goto L_0x00c7
            if (r5 == 0) goto L_0x00bf
            return r5
        L_0x00bf:
            kotlin.TypeCastException r7 = new kotlin.TypeCastException
            java.lang.String r8 = "null cannot be cast to non-null type T"
            r7.<init>(r8)
            throw r7
        L_0x00c7:
            io.ktor.features.CannotTransformContentToTypeException r7 = new io.ktor.features.CannotTransformContentToTypeException
            r7.<init>((kotlin.reflect.n) r14)
            throw r7
        L_0x00cd:
            io.ktor.request.RequestAlreadyConsumedException r7 = new io.ktor.request.RequestAlreadyConsumedException
            r7.<init>()
            throw r7
        L_0x00d3:
            r2 = 0
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.String r3 = "ApplicationReceiveRequest can't be received"
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.request.a.a(io.ktor.application.b, kotlin.reflect.n, kotlin.coroutines.d):java.lang.Object");
    }
}
