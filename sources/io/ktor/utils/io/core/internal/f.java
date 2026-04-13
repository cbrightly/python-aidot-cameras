package io.ktor.utils.io.core.internal;

import com.alibaba.fastjson.asm.Opcodes;
import java.io.EOFException;
import java.nio.ByteBuffer;
import kotlin.coroutines.jvm.internal.d;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import kotlin.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: UTF8.kt */
public final class f {

    @kotlin.coroutines.jvm.internal.f(c = "io.ktor.utils.io.core.internal.UTF8Kt", f = "UTF8.kt", l = {35}, m = "decodeUTF8LineLoopSuspend")
    /* compiled from: UTF8.kt */
    public static final class a extends d {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return f.b((Appendable) null, 0, (p<? super Integer, ? super kotlin.coroutines.d<? super io.ktor.utils.io.core.a>, ? extends Object>) null, this);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v21, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v17, resolved type: kotlin.jvm.internal.w} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v51, resolved type: kotlin.jvm.internal.w} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v23, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v23, resolved type: kotlin.jvm.internal.x} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v24, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v6, resolved type: kotlin.jvm.internal.x} */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01a1, code lost:
        if (kotlin.coroutines.jvm.internal.b.a(r8).booleanValue() != false) goto L_0x01b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01a3, code lost:
        r42.g(r3 - r28);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01aa, code lost:
        r39 = r5;
        r3 = r21;
        r8 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01b2, code lost:
        r11 = r42;
        r39 = r5;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00ae A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:217:0x04ae  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object b(@org.jetbrains.annotations.NotNull java.lang.Appendable r41, int r42, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.p<? super java.lang.Integer, ? super kotlin.coroutines.d<? super io.ktor.utils.io.core.a>, ? extends java.lang.Object> r43, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.Boolean> r44) {
        /*
            r0 = r44
            boolean r1 = r0 instanceof io.ktor.utils.io.core.internal.f.a
            if (r1 == 0) goto L_0x0015
            r1 = r0
            io.ktor.utils.io.core.internal.f$a r1 = (io.ktor.utils.io.core.internal.f.a) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0015
            int r2 = r2 - r3
            r1.label = r2
            goto L_0x001a
        L_0x0015:
            io.ktor.utils.io.core.internal.f$a r1 = new io.ktor.utils.io.core.internal.f$a
            r1.<init>(r0)
        L_0x001a:
            java.lang.Object r2 = r1.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r1.label
            r5 = 0
            r6 = 0
            r7 = 1
            switch(r4) {
                case 0: goto L_0x005f;
                case 1: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x0030:
            r4 = r5
            r8 = r5
            r9 = r5
            r10 = r5
            java.lang.Object r11 = r1.L$5
            r9 = r11
            kotlin.jvm.internal.w r9 = (kotlin.jvm.internal.w) r9
            java.lang.Object r11 = r1.L$4
            r8 = r11
            kotlin.jvm.internal.w r8 = (kotlin.jvm.internal.w) r8
            java.lang.Object r11 = r1.L$3
            r4 = r11
            kotlin.jvm.internal.x r4 = (kotlin.jvm.internal.x) r4
            java.lang.Object r11 = r1.L$2
            r10 = r11
            kotlin.jvm.internal.x r10 = (kotlin.jvm.internal.x) r10
            java.lang.Object r11 = r1.L$1
            kotlin.jvm.functions.p r11 = (kotlin.jvm.functions.p) r11
            int r12 = r1.I$0
            java.lang.Object r13 = r1.L$0
            java.lang.Appendable r13 = (java.lang.Appendable) r13
            kotlin.p.b(r2)
            r14 = r9
            r15 = r10
            r9 = r3
            r10 = r4
            r3 = r0
            r4 = r1
            r1 = r12
            r12 = r8
            r8 = r2
            goto L_0x00b8
        L_0x005f:
            kotlin.p.b(r2)
            kotlin.jvm.internal.x r4 = new kotlin.jvm.internal.x
            r4.<init>()
            r4.element = r6
            kotlin.jvm.internal.x r8 = new kotlin.jvm.internal.x
            r8.<init>()
            r8.element = r7
            kotlin.jvm.internal.w r9 = new kotlin.jvm.internal.w
            r9.<init>()
            r9.element = r6
            kotlin.jvm.internal.w r10 = new kotlin.jvm.internal.w
            r10.<init>()
            r10.element = r6
            r13 = r4
            r11 = r9
            r12 = r10
            r4 = r1
            r9 = r3
            r10 = r8
            r1 = r42
            r3 = r0
            r8 = r2
            r0 = r41
            r2 = r43
        L_0x008c:
            boolean r14 = r12.element
            if (r14 != 0) goto L_0x04e1
            int r14 = r10.element
            if (r14 == 0) goto L_0x04e1
            java.lang.Integer r14 = kotlin.coroutines.jvm.internal.b.c(r14)
            r4.L$0 = r0
            r4.I$0 = r1
            r4.L$1 = r2
            r4.L$2 = r13
            r4.L$3 = r10
            r4.L$4 = r11
            r4.L$5 = r12
            r4.label = r7
            java.lang.Object r14 = r2.invoke(r14, r4)
            if (r14 != r9) goto L_0x00af
            return r9
        L_0x00af:
            r15 = r13
            r13 = r0
            r40 = r11
            r11 = r2
            r2 = r14
            r14 = r12
            r12 = r40
        L_0x00b8:
            io.ktor.utils.io.core.a r2 = (io.ktor.utils.io.core.a) r2
            if (r2 == 0) goto L_0x04d3
            r41 = r2
            r6 = 1
            r16 = 0
            r0 = 1
            r5 = r41
            io.ktor.utils.io.core.internal.a r17 = io.ktor.utils.io.core.internal.g.g(r5, r6)
            if (r17 == 0) goto L_0x04b2
            r18 = r6
            r41 = r17
            r7 = r18
            r17 = r0
        L_0x00d2:
            r0 = r41
            r19 = 0
            int r20 = r0.s()     // Catch:{ all -> 0x049b }
            int r21 = r0.o()     // Catch:{ all -> 0x049b }
            int r20 = r20 - r21
            r42 = r20
            r19 = 0
            r43 = r2
            r2 = r42
            if (r2 < r7) goto L_0x0438
            r0 = r41
            r20 = 0
            r21 = 0
            r42 = r0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r44 = r42
            r26 = 0
            java.nio.ByteBuffer r27 = r44.n()     // Catch:{ all -> 0x0413 }
            int r28 = r44.o()     // Catch:{ all -> 0x0413 }
            java.lang.Integer r28 = kotlin.coroutines.jvm.internal.b.c(r28)     // Catch:{ all -> 0x0413 }
            int r29 = r44.s()     // Catch:{ all -> 0x0413 }
            java.lang.Integer r29 = kotlin.coroutines.jvm.internal.b.c(r29)     // Catch:{ all -> 0x0413 }
            int r29 = r29.intValue()     // Catch:{ all -> 0x0413 }
            int r28 = r28.intValue()     // Catch:{ all -> 0x0413 }
            r30 = r29
            r29 = 0
            r31 = r3
            r3 = r28
        L_0x0125:
            r32 = -1
            r33 = r4
            r4 = r30
            if (r3 >= r4) goto L_0x03bc
            r30 = r27
            r34 = 0
            r35 = r30
            r36 = 0
            r37 = r6
            r6 = r35
            r35 = r7
            byte r7 = r6.get(r3)     // Catch:{ all -> 0x03b3 }
            r6 = r7 & 255(0xff, float:3.57E-43)
            r7 = r6 & 128(0x80, float:1.794E-43)
            r30 = r8
            java.lang.String r8 = " exceeded"
            r34 = r9
            java.lang.String r9 = "Too many characters in line: limit "
            if (r7 != 0) goto L_0x01f2
            if (r23 != 0) goto L_0x01e7
            char r7 = (char) r6
            java.lang.Character r7 = kotlin.coroutines.jvm.internal.b.b(r7)     // Catch:{ all -> 0x01e0 }
            char r7 = r7.charValue()     // Catch:{ all -> 0x01e0 }
            r36 = 0
            switch(r7) {
                case 10: goto L_0x0175;
                case 13: goto L_0x0164;
                default: goto L_0x015f;
            }
        L_0x015f:
            r38 = r11
            boolean r11 = r12.element     // Catch:{ all -> 0x01db }
            goto L_0x0185
        L_0x0164:
            boolean r8 = r12.element     // Catch:{ all -> 0x017e }
            if (r8 == 0) goto L_0x016f
            r8 = 1
            r14.element = r8     // Catch:{ all -> 0x017e }
            r38 = r11
            r8 = 0
            goto L_0x0198
        L_0x016f:
            r8 = 1
            r12.element = r8     // Catch:{ all -> 0x017e }
            r38 = r11
            goto L_0x0198
        L_0x0175:
            r8 = 1
            r14.element = r8     // Catch:{ all -> 0x017e }
            r21 = 1
            r38 = r11
            r8 = 0
            goto L_0x0198
        L_0x017e:
            r0 = move-exception
            r39 = r5
            r38 = r11
            goto L_0x0422
        L_0x0185:
            if (r11 == 0) goto L_0x018c
            r8 = 1
            r14.element = r8     // Catch:{ all -> 0x01b8 }
            r8 = 0
            goto L_0x0198
        L_0x018c:
            int r11 = r15.element     // Catch:{ all -> 0x01db }
            if (r11 == r1) goto L_0x01bd
            int r11 = r11 + 1
            r15.element = r11     // Catch:{ all -> 0x01b8 }
            r13.append(r7)     // Catch:{ all -> 0x01b8 }
            r8 = 1
        L_0x0198:
            java.lang.Boolean r7 = kotlin.coroutines.jvm.internal.b.a(r8)     // Catch:{ all -> 0x01b8 }
            boolean r7 = r7.booleanValue()     // Catch:{ all -> 0x01b8 }
            if (r7 != 0) goto L_0x01b2
            int r7 = r3 - r28
            r11 = r42
            r11.g(r7)     // Catch:{ all -> 0x01b8 }
            r39 = r5
            r3 = r21
            r8 = r32
            goto L_0x03dd
        L_0x01b2:
            r11 = r42
            r39 = r5
            goto L_0x039b
        L_0x01b8:
            r0 = move-exception
            r39 = r5
            goto L_0x0422
        L_0x01bd:
            r11 = r42
            r42 = r7
            io.ktor.utils.io.core.BufferLimitExceededException r7 = new io.ktor.utils.io.core.BufferLimitExceededException     // Catch:{ all -> 0x01db }
            r39 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0411 }
            r5.<init>()     // Catch:{ all -> 0x0411 }
            r5.append(r9)     // Catch:{ all -> 0x0411 }
            r5.append(r1)     // Catch:{ all -> 0x0411 }
            r5.append(r8)     // Catch:{ all -> 0x0411 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0411 }
            r7.<init>(r5)     // Catch:{ all -> 0x0411 }
            throw r7     // Catch:{ all -> 0x0411 }
        L_0x01db:
            r0 = move-exception
            r39 = r5
            goto L_0x0422
        L_0x01e0:
            r0 = move-exception
            r39 = r5
            r38 = r11
            goto L_0x0422
        L_0x01e7:
            r39 = r5
            r38 = r11
            r11 = r42
            j(r23)     // Catch:{ all -> 0x0411 }
            r5 = 0
            throw r5     // Catch:{ all -> 0x0411 }
        L_0x01f2:
            r39 = r5
            r38 = r11
            r11 = r42
            if (r23 != 0) goto L_0x0227
            r5 = 128(0x80, float:1.794E-43)
            r7 = r6
            r8 = 1
        L_0x01fe:
            r9 = 6
            if (r8 > r9) goto L_0x0210
            r9 = r7 & r5
            if (r9 == 0) goto L_0x020f
            int r9 = ~r5     // Catch:{ all -> 0x0411 }
            r7 = r7 & r9
            int r5 = r5 >> 1
            int r23 = r23 + 1
            int r8 = r8 + 1
            goto L_0x01fe
        L_0x020f:
        L_0x0210:
            r8 = r23
            int r23 = r23 + -1
            int r9 = r4 - r3
            if (r8 <= r9) goto L_0x0221
            int r9 = r3 - r28
            r11.g(r9)     // Catch:{ all -> 0x0411 }
            r3 = r21
            goto L_0x03dd
        L_0x0221:
            r24 = r7
            r25 = r8
            goto L_0x039b
        L_0x0227:
            int r5 = r24 << 6
            r7 = r6 & 127(0x7f, float:1.78E-43)
            r5 = r5 | r7
            int r23 = r23 + -1
            if (r23 != 0) goto L_0x0397
            boolean r7 = g(r5)     // Catch:{ all -> 0x0411 }
            if (r7 == 0) goto L_0x02aa
            char r7 = (char) r5     // Catch:{ all -> 0x0411 }
            java.lang.Character r7 = kotlin.coroutines.jvm.internal.b.b(r7)     // Catch:{ all -> 0x0411 }
            char r7 = r7.charValue()     // Catch:{ all -> 0x0411 }
            r24 = 0
            switch(r7) {
                case 10: goto L_0x025a;
                case 13: goto L_0x0249;
                default: goto L_0x0244;
            }     // Catch:{ all -> 0x0411 }
        L_0x0244:
            r36 = r6
            boolean r6 = r12.element     // Catch:{ all -> 0x0411 }
            goto L_0x0263
        L_0x0249:
            boolean r8 = r12.element     // Catch:{ all -> 0x0411 }
            if (r8 == 0) goto L_0x0254
            r8 = 1
            r14.element = r8     // Catch:{ all -> 0x0411 }
            r36 = r6
            r8 = 0
            goto L_0x0276
        L_0x0254:
            r8 = 1
            r12.element = r8     // Catch:{ all -> 0x0411 }
            r36 = r6
            goto L_0x0276
        L_0x025a:
            r8 = 1
            r14.element = r8     // Catch:{ all -> 0x0411 }
            r21 = 1
            r36 = r6
            r8 = 0
            goto L_0x0276
        L_0x0263:
            if (r6 == 0) goto L_0x026a
            r6 = 1
            r14.element = r6     // Catch:{ all -> 0x0411 }
            r8 = 0
            goto L_0x0276
        L_0x026a:
            int r6 = r15.element     // Catch:{ all -> 0x0411 }
            if (r6 == r1) goto L_0x0290
            int r6 = r6 + 1
            r15.element = r6     // Catch:{ all -> 0x0411 }
            r13.append(r7)     // Catch:{ all -> 0x0411 }
            r8 = 1
        L_0x0276:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.b.a(r8)     // Catch:{ all -> 0x0411 }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x0411 }
            if (r6 != 0) goto L_0x034a
            int r6 = r3 - r28
            int r6 = r6 - r25
            r7 = 1
            int r6 = r6 + r7
            r11.g(r6)     // Catch:{ all -> 0x0411 }
            r3 = r21
            r8 = r32
            goto L_0x03dd
        L_0x0290:
            io.ktor.utils.io.core.BufferLimitExceededException r6 = new io.ktor.utils.io.core.BufferLimitExceededException     // Catch:{ all -> 0x0411 }
            r42 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0411 }
            r7.<init>()     // Catch:{ all -> 0x0411 }
            r7.append(r9)     // Catch:{ all -> 0x0411 }
            r7.append(r1)     // Catch:{ all -> 0x0411 }
            r7.append(r8)     // Catch:{ all -> 0x0411 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0411 }
            r6.<init>(r7)     // Catch:{ all -> 0x0411 }
            throw r6     // Catch:{ all -> 0x0411 }
        L_0x02aa:
            r36 = r6
            boolean r6 = h(r5)     // Catch:{ all -> 0x0411 }
            if (r6 == 0) goto L_0x0392
            int r6 = f(r5)     // Catch:{ all -> 0x0411 }
            char r6 = (char) r6     // Catch:{ all -> 0x0411 }
            java.lang.Character r6 = kotlin.coroutines.jvm.internal.b.b(r6)     // Catch:{ all -> 0x0411 }
            char r6 = r6.charValue()     // Catch:{ all -> 0x0411 }
            r7 = 0
            switch(r6) {
                case 10: goto L_0x02d9;
                case 13: goto L_0x02ca;
                default: goto L_0x02c5;
            }     // Catch:{ all -> 0x0411 }
        L_0x02c5:
            r42 = r7
            boolean r7 = r12.element     // Catch:{ all -> 0x0411 }
            goto L_0x02e2
        L_0x02ca:
            r42 = r7
            boolean r7 = r12.element     // Catch:{ all -> 0x0411 }
            if (r7 == 0) goto L_0x02d5
            r7 = 1
            r14.element = r7     // Catch:{ all -> 0x0411 }
            r7 = 0
            goto L_0x02f5
        L_0x02d5:
            r7 = 1
            r12.element = r7     // Catch:{ all -> 0x0411 }
            goto L_0x02f5
        L_0x02d9:
            r42 = r7
            r7 = 1
            r14.element = r7     // Catch:{ all -> 0x0411 }
            r21 = 1
            r7 = 0
            goto L_0x02f5
        L_0x02e2:
            if (r7 == 0) goto L_0x02e9
            r7 = 1
            r14.element = r7     // Catch:{ all -> 0x0411 }
            r7 = 0
            goto L_0x02f5
        L_0x02e9:
            int r7 = r15.element     // Catch:{ all -> 0x0411 }
            if (r7 == r1) goto L_0x0378
            int r7 = r7 + 1
            r15.element = r7     // Catch:{ all -> 0x0411 }
            r13.append(r6)     // Catch:{ all -> 0x0411 }
            r7 = 1
        L_0x02f5:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.b.a(r7)     // Catch:{ all -> 0x0411 }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x0411 }
            if (r6 == 0) goto L_0x0369
            int r6 = i(r5)     // Catch:{ all -> 0x0411 }
            char r6 = (char) r6     // Catch:{ all -> 0x0411 }
            java.lang.Character r6 = kotlin.coroutines.jvm.internal.b.b(r6)     // Catch:{ all -> 0x0411 }
            char r6 = r6.charValue()     // Catch:{ all -> 0x0411 }
            r7 = 0
            switch(r6) {
                case 10: goto L_0x0323;
                case 13: goto L_0x0316;
                default: goto L_0x0311;
            }     // Catch:{ all -> 0x0411 }
        L_0x0311:
            r42 = r7
            boolean r7 = r12.element     // Catch:{ all -> 0x0411 }
            goto L_0x032b
        L_0x0316:
            boolean r8 = r12.element     // Catch:{ all -> 0x0411 }
            if (r8 == 0) goto L_0x031f
            r8 = 1
            r14.element = r8     // Catch:{ all -> 0x0411 }
            r8 = 0
            goto L_0x033e
        L_0x031f:
            r8 = 1
            r12.element = r8     // Catch:{ all -> 0x0411 }
            goto L_0x033e
        L_0x0323:
            r8 = 1
            r14.element = r8     // Catch:{ all -> 0x0411 }
            r8 = 1
            r21 = r8
            r8 = 0
            goto L_0x033e
        L_0x032b:
            if (r7 == 0) goto L_0x0332
            r7 = 1
            r14.element = r7     // Catch:{ all -> 0x0411 }
            r8 = 0
            goto L_0x033e
        L_0x0332:
            int r7 = r15.element     // Catch:{ all -> 0x0411 }
            if (r7 == r1) goto L_0x034f
            int r7 = r7 + 1
            r15.element = r7     // Catch:{ all -> 0x0411 }
            r13.append(r6)     // Catch:{ all -> 0x0411 }
            r8 = 1
        L_0x033e:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.b.a(r8)     // Catch:{ all -> 0x0411 }
            boolean r6 = r6.booleanValue()     // Catch:{ all -> 0x0411 }
            if (r6 != 0) goto L_0x034a
            goto L_0x0369
        L_0x034a:
            r5 = 0
            r24 = r5
            goto L_0x039b
        L_0x034f:
            io.ktor.utils.io.core.BufferLimitExceededException r7 = new io.ktor.utils.io.core.BufferLimitExceededException     // Catch:{ all -> 0x0411 }
            r24 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0411 }
            r6.<init>()     // Catch:{ all -> 0x0411 }
            r6.append(r9)     // Catch:{ all -> 0x0411 }
            r6.append(r1)     // Catch:{ all -> 0x0411 }
            r6.append(r8)     // Catch:{ all -> 0x0411 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0411 }
            r7.<init>(r6)     // Catch:{ all -> 0x0411 }
            throw r7     // Catch:{ all -> 0x0411 }
        L_0x0369:
            int r6 = r3 - r28
            int r6 = r6 - r25
            r7 = 1
            int r6 = r6 + r7
            r11.g(r6)     // Catch:{ all -> 0x0411 }
            r3 = r21
            r8 = r32
            goto L_0x03dd
        L_0x0378:
            io.ktor.utils.io.core.BufferLimitExceededException r7 = new io.ktor.utils.io.core.BufferLimitExceededException     // Catch:{ all -> 0x0411 }
            r18 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0411 }
            r6.<init>()     // Catch:{ all -> 0x0411 }
            r6.append(r9)     // Catch:{ all -> 0x0411 }
            r6.append(r1)     // Catch:{ all -> 0x0411 }
            r6.append(r8)     // Catch:{ all -> 0x0411 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0411 }
            r7.<init>(r6)     // Catch:{ all -> 0x0411 }
            throw r7     // Catch:{ all -> 0x0411 }
        L_0x0392:
            k(r5)     // Catch:{ all -> 0x0411 }
            r6 = 0
            throw r6     // Catch:{ all -> 0x0411 }
        L_0x0397:
            r36 = r6
            r24 = r5
        L_0x039b:
            int r3 = r3 + 1
            r42 = r11
            r8 = r30
            r9 = r34
            r7 = r35
            r6 = r37
            r11 = r38
            r5 = r39
            r30 = r4
            r4 = r33
            goto L_0x0125
        L_0x03b3:
            r0 = move-exception
            r39 = r5
            r30 = r8
            r38 = r11
            goto L_0x0422
        L_0x03bc:
            r39 = r5
            r37 = r6
            r35 = r7
            r30 = r8
            r34 = r9
            r38 = r11
            r11 = r42
            int r3 = r4 - r28
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r3)     // Catch:{ all -> 0x0411 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x0411 }
            r4 = r44
            r4.g(r3)     // Catch:{ all -> 0x0411 }
            r3 = r21
            r8 = 0
        L_0x03dd:
            r10.element = r8     // Catch:{ all -> 0x0411 }
            if (r3 <= 0) goto L_0x03e4
            r0.g(r3)     // Catch:{ all -> 0x0411 }
        L_0x03e4:
            boolean r4 = r14.element     // Catch:{ all -> 0x0411 }
            if (r4 == 0) goto L_0x03ea
            r4 = 0
            goto L_0x03f1
        L_0x03ea:
            int r4 = r10.element     // Catch:{ all -> 0x0411 }
            r5 = 1
            int r4 = kotlin.ranges.n.b(r4, r5)     // Catch:{ all -> 0x0411 }
        L_0x03f1:
            r10.element = r4     // Catch:{ all -> 0x0411 }
            java.lang.Integer r0 = kotlin.coroutines.jvm.internal.b.c(r4)     // Catch:{ all -> 0x0411 }
            int r0 = r0.intValue()     // Catch:{ all -> 0x0411 }
            r7 = r0
            r0 = r41
            r3 = 0
            int r4 = r0.s()     // Catch:{ all -> 0x040a }
            int r5 = r0.o()     // Catch:{ all -> 0x040a }
            int r4 = r4 - r5
            goto L_0x0449
        L_0x040a:
            r0 = move-exception
            r3 = r41
            r5 = r39
            goto L_0x04ac
        L_0x0411:
            r0 = move-exception
            goto L_0x0422
        L_0x0413:
            r0 = move-exception
            r31 = r3
            r33 = r4
            r39 = r5
            r37 = r6
            r35 = r7
            r30 = r8
            r38 = r11
        L_0x0422:
            r3 = r41
            r4 = 0
            int r5 = r3.s()     // Catch:{ all -> 0x042f }
            int r6 = r3.o()     // Catch:{ all -> 0x042f }
            int r5 = r5 - r6
            throw r0     // Catch:{ all -> 0x042f }
        L_0x042f:
            r0 = move-exception
            r3 = r41
            r7 = r35
            r5 = r39
            goto L_0x04ac
        L_0x0438:
            r31 = r3
            r33 = r4
            r39 = r5
            r37 = r6
            r35 = r7
            r30 = r8
            r34 = r9
            r38 = r11
            r4 = r2
        L_0x0449:
            r17 = 0
            if (r4 != 0) goto L_0x045a
            r3 = r41
            r5 = r39
            io.ktor.utils.io.core.internal.a r0 = io.ktor.utils.io.core.internal.g.i(r5, r3)     // Catch:{ all -> 0x0458 }
            goto L_0x0479
        L_0x0458:
            r0 = move-exception
            goto L_0x04ac
        L_0x045a:
            r3 = r41
            r5 = r39
            if (r4 < r7) goto L_0x0472
            r0 = r3
            r6 = 0
            int r8 = r0.l()     // Catch:{ all -> 0x0458 }
            int r9 = r0.m()     // Catch:{ all -> 0x0458 }
            int r8 = r8 - r9
            r0 = 8
            if (r8 >= r0) goto L_0x0470
            goto L_0x0472
        L_0x0470:
            r0 = r3
            goto L_0x0479
        L_0x0472:
            io.ktor.utils.io.core.internal.g.d(r5, r3)     // Catch:{ all -> 0x0458 }
            io.ktor.utils.io.core.internal.a r0 = io.ktor.utils.io.core.internal.g.g(r5, r7)     // Catch:{ all -> 0x0458 }
        L_0x0479:
            if (r0 != 0) goto L_0x047d
            goto L_0x0482
        L_0x047d:
            r3 = r0
            r17 = 1
            if (r7 > 0) goto L_0x0489
        L_0x0482:
            if (r17 == 0) goto L_0x0487
            io.ktor.utils.io.core.internal.g.d(r5, r3)
        L_0x0487:
            goto L_0x04c0
        L_0x0489:
            r2 = r43
            r41 = r3
            r8 = r30
            r3 = r31
            r4 = r33
            r9 = r34
            r6 = r37
            r11 = r38
            goto L_0x00d2
        L_0x049b:
            r0 = move-exception
            r43 = r2
            r31 = r3
            r33 = r4
            r37 = r6
            r35 = r7
            r30 = r8
            r38 = r11
            r3 = r41
        L_0x04ac:
            if (r17 == 0) goto L_0x04b1
            io.ktor.utils.io.core.internal.g.d(r5, r3)
        L_0x04b1:
            throw r0
        L_0x04b2:
            r43 = r2
            r31 = r3
            r33 = r4
            r37 = r6
            r30 = r8
            r34 = r9
            r38 = r11
        L_0x04c0:
            r11 = r12
            r0 = r13
            r12 = r14
            r13 = r15
            r8 = r30
            r3 = r31
            r4 = r33
            r9 = r34
            r2 = r38
            r5 = 0
            r6 = 0
            r7 = 1
            goto L_0x008c
        L_0x04d3:
            r31 = r3
            r33 = r4
            r30 = r8
            r38 = r11
            r11 = r12
            r0 = r13
            r12 = r14
            r13 = r15
            r2 = r38
        L_0x04e1:
            int r5 = r10.element
            r6 = 1
            if (r5 > r6) goto L_0x04fb
            boolean r5 = r11.element
            if (r5 == 0) goto L_0x04ec
            r12.element = r6
        L_0x04ec:
            int r5 = r13.element
            if (r5 > 0) goto L_0x04f6
            boolean r5 = r12.element
            if (r5 == 0) goto L_0x04f5
            goto L_0x04f6
        L_0x04f5:
            r6 = 0
        L_0x04f6:
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.b.a(r6)
            return r5
        L_0x04fb:
            l(r5)
            r5 = 0
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.core.internal.f.b(java.lang.Appendable, int, kotlin.jvm.functions.p, kotlin.coroutines.d):java.lang.Object");
    }

    private static final Void l(int size) {
        throw new EOFException("Premature end of stream: expected " + size + " bytes to decode UTF-8 char");
    }

    public static final int c(@NotNull ByteBuffer $this$encodeUTF8, @NotNull CharSequence text, int from, int to, int dstOffset, int dstLimit) {
        CharSequence charSequence = text;
        k.f($this$encodeUTF8, "$this$encodeUTF8");
        k.f(charSequence, "text");
        int lastCharIndex = Math.min(to, from + 65535);
        int resultLimit = n.e(dstLimit, 65535);
        int character = from;
        int resultPosition = dstOffset;
        while (resultPosition < resultLimit && character < lastCharIndex) {
            int index = character + 1;
            int character2 = charSequence.charAt(character) & 65535;
            if ((65408 & character2) == 0) {
                ByteBuffer $this$iv = $this$encodeUTF8;
                $this$iv.put(resultPosition, (byte) character2);
                character = index;
                resultPosition++;
            } else {
                return d($this$encodeUTF8, text, index - 1, lastCharIndex, from, resultPosition, resultLimit, dstOffset);
            }
        }
        return c.b(w.a((short) (character - from)), w.a((short) (resultPosition - dstOffset)));
    }

    private static final int d(@NotNull ByteBuffer $this$encodeUTF8Stage1, CharSequence text, int index1, int lastCharIndex, int from, int resultPosition1, int resultLimit, int dstOffset) {
        int index;
        byte value$iv$iv;
        CharSequence charSequence = text;
        int i = lastCharIndex;
        int stage1Limit = resultLimit - 3;
        int index2 = index1;
        int resultPosition = resultPosition1;
        while (stage1Limit - resultPosition > 0 && index2 < i) {
            int index3 = index2 + 1;
            char character = charSequence.charAt(index2);
            if (!Character.isHighSurrogate(character)) {
                index2 = index3;
                index = character;
            } else if (index3 == i || !Character.isLowSurrogate(charSequence.charAt(index3))) {
                index2 = index3;
                index = 63;
            } else {
                int index4 = index3 + 1;
                index = a(character, charSequence.charAt(index3));
                index2 = index4;
            }
            ByteBuffer $this$putUtf8Char$iv = $this$encodeUTF8Stage1;
            if (index >= 0 && 127 >= index) {
                $this$putUtf8Char$iv.put(resultPosition, (byte) index);
                value$iv$iv = 1;
            } else if (128 <= index && 2047 >= index) {
                $this$putUtf8Char$iv.put(resultPosition, (byte) (((index >> 6) & 31) | Opcodes.CHECKCAST));
                $this$putUtf8Char$iv.put(resultPosition + 1, (byte) ((index & 63) | 128));
                value$iv$iv = 2;
            } else if (2048 <= index && 65535 >= index) {
                $this$putUtf8Char$iv.put(resultPosition, (byte) (((index >> 12) & 15) | 224));
                $this$putUtf8Char$iv.put(resultPosition + 1, (byte) ((63 & (index >> 6)) | 128));
                $this$putUtf8Char$iv.put(resultPosition + 2, (byte) ((index & 63) | 128));
                value$iv$iv = 3;
            } else if (65536 > index || 1114111 < index) {
                k(index);
                throw null;
            } else {
                $this$putUtf8Char$iv.put(resultPosition, (byte) (((index >> 18) & 7) | 240));
                $this$putUtf8Char$iv.put(resultPosition + 1, (byte) (((index >> 12) & 63) | 128));
                $this$putUtf8Char$iv.put(resultPosition + 2, (byte) ((63 & (index >> 6)) | 128));
                $this$putUtf8Char$iv.put(resultPosition + 3, (byte) ((index & 63) | 128));
                value$iv$iv = 4;
            }
            resultPosition += value$iv$iv;
        }
        if (resultPosition == stage1Limit) {
            return e($this$encodeUTF8Stage1, text, index2, lastCharIndex, from, resultPosition, resultLimit, dstOffset);
        }
        return c.b(w.a((short) (index2 - from)), w.a((short) (resultPosition - dstOffset)));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: char} */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0142, code lost:
        throw null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0147, code lost:
        throw null;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static final int e(@org.jetbrains.annotations.NotNull java.nio.ByteBuffer r20, java.lang.CharSequence r21, int r22, int r23, int r24, int r25, int r26, int r27) {
        /*
            r0 = r21
            r1 = r23
            r2 = r22
            r3 = r25
        L_0x0008:
            int r4 = r26 - r3
            if (r4 <= 0) goto L_0x0148
            if (r2 < r1) goto L_0x0011
            goto L_0x0148
        L_0x0011:
            int r5 = r2 + 1
            char r2 = r0.charAt(r2)
            boolean r6 = java.lang.Character.isHighSurrogate(r2)
            if (r6 != 0) goto L_0x0020
            r6 = r2
            goto L_0x003f
        L_0x0020:
            if (r5 == r1) goto L_0x003d
            char r6 = r0.charAt(r5)
            boolean r6 = java.lang.Character.isLowSurrogate(r6)
            if (r6 != 0) goto L_0x002d
            goto L_0x003d
        L_0x002d:
            int r6 = r5 + 1
            char r5 = r0.charAt(r5)
            int r5 = a(r2, r5)
            r19 = r6
            r6 = r5
            r5 = r19
            goto L_0x003f
        L_0x003d:
            r6 = 63
        L_0x003f:
            r8 = 0
            r11 = 65536(0x10000, float:9.18355E-41)
            r12 = 1114111(0x10ffff, float:1.561202E-39)
            r13 = 2048(0x800, float:2.87E-42)
            r14 = 65535(0xffff, float:9.1834E-41)
            r15 = 2047(0x7ff, float:2.868E-42)
            r9 = 127(0x7f, float:1.78E-43)
            r16 = 3
            r17 = 2
            r10 = 128(0x80, float:1.794E-43)
            r7 = 1
            if (r7 <= r6) goto L_0x005b
            goto L_0x005f
        L_0x005b:
            if (r9 < r6) goto L_0x005f
            r8 = r7
            goto L_0x0074
        L_0x005f:
            if (r10 <= r6) goto L_0x0062
            goto L_0x0067
        L_0x0062:
            if (r15 < r6) goto L_0x0067
            r8 = r17
            goto L_0x0074
        L_0x0067:
            if (r13 <= r6) goto L_0x006a
            goto L_0x006f
        L_0x006a:
            if (r14 < r6) goto L_0x006f
            r8 = r16
            goto L_0x0074
        L_0x006f:
            if (r11 > r6) goto L_0x0143
            if (r12 < r6) goto L_0x0143
            r8 = 4
        L_0x0074:
            if (r8 <= r4) goto L_0x007b
            int r5 = r5 + -1
            r2 = r5
            goto L_0x0149
        L_0x007b:
            r8 = r20
            r18 = 0
            if (r6 >= 0) goto L_0x0083
            goto L_0x008f
        L_0x0083:
            if (r9 < r6) goto L_0x008f
            byte r9 = (byte) r6
            r10 = r8
            r11 = 0
            r10.put(r3, r9)
            r9 = r7
            goto L_0x0138
        L_0x008f:
            if (r10 <= r6) goto L_0x0092
            goto L_0x00b7
        L_0x0092:
            if (r15 < r6) goto L_0x00b7
            int r7 = r6 >> 6
            r7 = r7 & 31
            r7 = r7 | 192(0xc0, float:2.69E-43)
            byte r7 = (byte) r7
            r9 = r8
            r11 = 0
            r12 = r9
            r13 = 0
            r12.put(r3, r7)
            int r7 = r3 + 1
            r9 = r6 & 63
            r9 = r9 | r10
            byte r9 = (byte) r9
            r10 = r8
            r11 = 0
            r12 = r10
            r13 = 0
            r12.put(r7, r9)
            r9 = r17
            goto L_0x0138
        L_0x00b7:
            if (r13 <= r6) goto L_0x00ba
            goto L_0x00f0
        L_0x00ba:
            if (r14 < r6) goto L_0x00f0
            int r7 = r6 >> 12
            r7 = r7 & 15
            r7 = r7 | 224(0xe0, float:3.14E-43)
            byte r7 = (byte) r7
            r9 = r8
            r11 = 0
            r12 = r9
            r13 = 0
            r12.put(r3, r7)
            int r7 = r3 + 1
            int r9 = r6 >> 6
            r11 = 63
            r9 = r9 & r11
            r9 = r9 | r10
            byte r9 = (byte) r9
            r11 = r8
            r12 = 0
            r13 = r11
            r14 = 0
            r13.put(r7, r9)
            int r7 = r3 + 2
            r9 = r6 & 63
            r9 = r9 | r10
            byte r9 = (byte) r9
            r10 = r8
            r11 = 0
            r12 = r10
            r13 = 0
            r12.put(r7, r9)
            r9 = r16
            goto L_0x0138
        L_0x00f0:
            if (r11 > r6) goto L_0x013e
            if (r12 < r6) goto L_0x013e
            int r7 = r6 >> 18
            r7 = r7 & 7
            r7 = r7 | 240(0xf0, float:3.36E-43)
            byte r7 = (byte) r7
            r9 = r8
            r11 = 0
            r12 = r9
            r13 = 0
            r12.put(r3, r7)
            int r7 = r3 + 1
            int r9 = r6 >> 12
            r11 = 63
            r9 = r9 & r11
            r9 = r9 | r10
            byte r9 = (byte) r9
            r11 = r8
            r12 = 0
            r13 = r11
            r14 = 0
            r13.put(r7, r9)
            int r7 = r3 + 2
            int r9 = r6 >> 6
            r11 = 63
            r9 = r9 & r11
            r9 = r9 | r10
            byte r9 = (byte) r9
            r11 = r8
            r12 = 0
            r13 = r11
            r14 = 0
            r13.put(r7, r9)
            int r7 = r3 + 3
            r9 = r6 & 63
            r9 = r9 | r10
            byte r9 = (byte) r9
            r10 = r8
            r11 = 0
            r12 = r10
            r13 = 0
            r12.put(r7, r9)
            r9 = 4
        L_0x0138:
            r7 = r9
            int r3 = r3 + r7
            r2 = r5
            goto L_0x0008
        L_0x013e:
            k(r6)
            r7 = 0
            throw r7
        L_0x0143:
            r7 = 0
            k(r6)
            throw r7
        L_0x0148:
        L_0x0149:
            int r4 = r2 - r24
            short r4 = (short) r4
            short r4 = kotlin.w.a(r4)
            int r5 = r3 - r27
            short r5 = (short) r5
            short r5 = kotlin.w.a(r5)
            int r4 = io.ktor.utils.io.core.internal.c.b(r4, r5)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.core.internal.f.e(java.nio.ByteBuffer, java.lang.CharSequence, int, int, int, int, int, int):int");
    }

    @NotNull
    public static final Void j(int byteCount) {
        throw new MalformedUTF8InputException("Expected " + byteCount + " more character bytes");
    }

    @NotNull
    public static final Void k(int value) {
        throw new IllegalArgumentException("Malformed code-point " + value + " found");
    }

    public static final boolean g(int cp) {
        return (cp >>> 16) == 0;
    }

    public static final boolean h(int codePoint) {
        return codePoint <= 1114111;
    }

    public static final int i(int cp) {
        return (cp & 1023) + 56320;
    }

    public static final int f(int cp) {
        return (cp >>> 10) + 55232;
    }

    public static final int a(char high, char low) {
        return ((high - 55232) << 10) | (low - 56320);
    }
}
