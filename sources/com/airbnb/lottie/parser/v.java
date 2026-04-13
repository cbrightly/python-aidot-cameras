package com.airbnb.lottie.parser;

import android.graphics.Rect;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.j;
import com.airbnb.lottie.model.animatable.k;
import com.airbnb.lottie.model.animatable.l;
import com.airbnb.lottie.model.layer.e;
import com.airbnb.lottie.parser.moshi.a;
import java.util.Collections;
import java.util.List;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: LayerParser */
public class v {
    private static final a.C0011a a = a.C0011a.a("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", IjkMediaPlayer.OnNativeInvokeListener.ARG_IP, "op", "tm", "cl", "hd");
    private static final a.C0011a b = a.C0011a.a("d", "a");
    private static final a.C0011a c = a.C0011a.a("ty", "nm");

    public static e a(c0 composition) {
        Rect bounds = composition.b();
        List emptyList = Collections.emptyList();
        e.a aVar = e.a.PRE_COMP;
        List emptyList2 = Collections.emptyList();
        l lVar = r3;
        l lVar2 = new l();
        return new e(emptyList, composition, "__container", -1, aVar, -1, (String) null, emptyList2, lVar, 0, 0, 0, 0.0f, 0.0f, (float) bounds.width(), (float) bounds.height(), (j) null, (k) null, Collections.emptyList(), e.b.NONE, (b) null, false, (com.airbnb.lottie.model.content.a) null, (j) null);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.airbnb.lottie.model.layer.e b(com.airbnb.lottie.parser.moshi.a r65, com.airbnb.lottie.c0 r66) {
        /*
            r0 = r65
            r15 = r66
            java.lang.String r1 = "UNSET"
            r2 = 0
            r3 = 0
            r4 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = -1
            r13 = 1065353216(0x3f800000, float:1.0)
            r14 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            com.airbnb.lottie.model.layer.e$b r22 = com.airbnb.lottie.model.layer.e.b.NONE
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            java.util.ArrayList r27 = new java.util.ArrayList
            r27.<init>()
            r41 = r27
            java.util.ArrayList r27 = new java.util.ArrayList
            r27.<init>()
            r42 = r27
            r65.g()
            r43 = r2
            r44 = r3
            r45 = r4
            r47 = r6
            r48 = r7
            r49 = r8
            r50 = r9
            r51 = r10
            r52 = r11
            r54 = r13
            r55 = r14
            r56 = r16
            r12 = r18
            r57 = r19
            r58 = r20
            r59 = r21
            r60 = r22
            r61 = r23
            r62 = r24
            r63 = r25
            r64 = r26
            r14 = r1
        L_0x0066:
            boolean r1 = r65.l()
            if (r1 == 0) goto L_0x029b
            com.airbnb.lottie.parser.moshi.a$a r1 = a
            int r1 = r0.x(r1)
            switch(r1) {
                case 0: goto L_0x028b;
                case 1: goto L_0x027e;
                case 2: goto L_0x0274;
                case 3: goto L_0x0250;
                case 4: goto L_0x0243;
                case 5: goto L_0x0230;
                case 6: goto L_0x021d;
                case 7: goto L_0x020f;
                case 8: goto L_0x0205;
                case 9: goto L_0x01b8;
                case 10: goto L_0x0195;
                case 11: goto L_0x0172;
                case 12: goto L_0x0134;
                case 13: goto L_0x00cd;
                case 14: goto L_0x00c5;
                case 15: goto L_0x00bd;
                case 16: goto L_0x00af;
                case 17: goto L_0x00a1;
                case 18: goto L_0x0099;
                case 19: goto L_0x0091;
                case 20: goto L_0x008b;
                case 21: goto L_0x0086;
                case 22: goto L_0x0081;
                default: goto L_0x0075;
            }
        L_0x0075:
            r10 = r41
            r11 = r42
            r65.z()
            r65.E()
            goto L_0x0295
        L_0x0081:
            boolean r57 = r65.m()
            goto L_0x0066
        L_0x0086:
            java.lang.String r12 = r65.s()
            goto L_0x0066
        L_0x008b:
            r1 = 0
            com.airbnb.lottie.model.animatable.b r64 = com.airbnb.lottie.parser.d.f(r0, r15, r1)
            goto L_0x0066
        L_0x0091:
            double r1 = r65.n()
            float r1 = (float) r1
            r17 = r1
            goto L_0x0066
        L_0x0099:
            double r1 = r65.n()
            float r1 = (float) r1
            r56 = r1
            goto L_0x0066
        L_0x00a1:
            double r1 = r65.n()
            float r3 = com.airbnb.lottie.utils.h.e()
            double r3 = (double) r3
            double r1 = r1 * r3
            float r1 = (float) r1
            r51 = r1
            goto L_0x0066
        L_0x00af:
            double r1 = r65.n()
            float r3 = com.airbnb.lottie.utils.h.e()
            double r3 = (double) r3
            double r1 = r1 * r3
            float r1 = (float) r1
            r50 = r1
            goto L_0x0066
        L_0x00bd:
            double r1 = r65.n()
            float r1 = (float) r1
            r55 = r1
            goto L_0x0066
        L_0x00c5:
            double r1 = r65.n()
            float r1 = (float) r1
            r54 = r1
            goto L_0x0066
        L_0x00cd:
            r65.c()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
        L_0x00d5:
            boolean r2 = r65.l()
            if (r2 == 0) goto L_0x011b
            r65.g()
        L_0x00de:
            boolean r2 = r65.l()
            if (r2 == 0) goto L_0x0117
            com.airbnb.lottie.parser.moshi.a$a r2 = c
            int r2 = r0.x(r2)
            switch(r2) {
                case 0: goto L_0x00fc;
                case 1: goto L_0x00f4;
                default: goto L_0x00ed;
            }
        L_0x00ed:
            r65.z()
            r65.E()
            goto L_0x00de
        L_0x00f4:
            java.lang.String r2 = r65.s()
            r1.add(r2)
            goto L_0x00de
        L_0x00fc:
            int r2 = r65.o()
            r3 = 29
            if (r2 != r3) goto L_0x0109
            com.airbnb.lottie.model.content.a r58 = com.airbnb.lottie.parser.e.b(r65, r66)
            goto L_0x00de
        L_0x0109:
            r3 = 25
            if (r2 != r3) goto L_0x00de
            com.airbnb.lottie.parser.k r3 = new com.airbnb.lottie.parser.k
            r3.<init>()
            com.airbnb.lottie.parser.j r59 = r3.b(r0, r15)
            goto L_0x00de
        L_0x0117:
            r65.j()
            goto L_0x00d5
        L_0x011b:
            r65.i()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            r15.a(r2)
            goto L_0x0066
        L_0x0134:
            r65.g()
        L_0x0137:
            boolean r1 = r65.l()
            if (r1 == 0) goto L_0x016d
            com.airbnb.lottie.parser.moshi.a$a r1 = b
            int r1 = r0.x(r1)
            switch(r1) {
                case 0: goto L_0x0168;
                case 1: goto L_0x014d;
                default: goto L_0x0146;
            }
        L_0x0146:
            r65.z()
            r65.E()
            goto L_0x0137
        L_0x014d:
            r65.c()
            boolean r1 = r65.l()
            if (r1 == 0) goto L_0x015a
            com.airbnb.lottie.model.animatable.k r63 = com.airbnb.lottie.parser.b.a(r65, r66)
        L_0x015a:
            boolean r1 = r65.l()
            if (r1 == 0) goto L_0x0164
            r65.E()
            goto L_0x015a
        L_0x0164:
            r65.i()
            goto L_0x0137
        L_0x0168:
            com.airbnb.lottie.model.animatable.j r62 = com.airbnb.lottie.parser.d.d(r65, r66)
            goto L_0x0137
        L_0x016d:
            r65.j()
            goto L_0x0066
        L_0x0172:
            r65.c()
        L_0x0175:
            boolean r1 = r65.l()
            if (r1 == 0) goto L_0x018c
            com.airbnb.lottie.model.content.c r1 = com.airbnb.lottie.parser.h.a(r65, r66)
            if (r1 == 0) goto L_0x0187
            r11 = r42
            r11.add(r1)
            goto L_0x0189
        L_0x0187:
            r11 = r42
        L_0x0189:
            r42 = r11
            goto L_0x0175
        L_0x018c:
            r11 = r42
            r65.i()
            r10 = r41
            goto L_0x0295
        L_0x0195:
            r11 = r42
            r65.c()
        L_0x019a:
            boolean r1 = r65.l()
            if (r1 == 0) goto L_0x01aa
            com.airbnb.lottie.model.content.h r1 = com.airbnb.lottie.parser.x.a(r65, r66)
            r10 = r41
            r10.add(r1)
            goto L_0x019a
        L_0x01aa:
            r10 = r41
            int r1 = r10.size()
            r15.r(r1)
            r65.i()
            goto L_0x0295
        L_0x01b8:
            r10 = r41
            r11 = r42
            int r1 = r65.o()
            com.airbnb.lottie.model.layer.e$b[] r2 = com.airbnb.lottie.model.layer.e.b.values()
            int r2 = r2.length
            if (r1 < r2) goto L_0x01dd
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Unsupported matte type: "
            r2.append(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            r15.a(r2)
            goto L_0x0295
        L_0x01dd:
            com.airbnb.lottie.model.layer.e$b[] r2 = com.airbnb.lottie.model.layer.e.b.values()
            r60 = r2[r1]
            int[] r2 = com.airbnb.lottie.parser.v.a.a
            int r3 = r60.ordinal()
            r2 = r2[r3]
            switch(r2) {
                case 1: goto L_0x01f5;
                case 2: goto L_0x01ef;
                default: goto L_0x01ee;
            }
        L_0x01ee:
            goto L_0x01fb
        L_0x01ef:
            java.lang.String r2 = "Unsupported matte type: Luma Inverted"
            r15.a(r2)
            goto L_0x01fb
        L_0x01f5:
            java.lang.String r2 = "Unsupported matte type: Luma"
            r15.a(r2)
        L_0x01fb:
            r2 = 1
            r15.r(r2)
            r41 = r10
            r42 = r11
            goto L_0x0066
        L_0x0205:
            r10 = r41
            r11 = r42
            com.airbnb.lottie.model.animatable.l r61 = com.airbnb.lottie.parser.c.g(r65, r66)
            goto L_0x0066
        L_0x020f:
            r10 = r41
            r11 = r42
            java.lang.String r1 = r65.s()
            int r49 = android.graphics.Color.parseColor(r1)
            goto L_0x0066
        L_0x021d:
            r10 = r41
            r11 = r42
            int r1 = r65.o()
            float r1 = (float) r1
            float r2 = com.airbnb.lottie.utils.h.e()
            float r1 = r1 * r2
            int r1 = (int) r1
            r48 = r1
            goto L_0x0066
        L_0x0230:
            r10 = r41
            r11 = r42
            int r1 = r65.o()
            float r1 = (float) r1
            float r2 = com.airbnb.lottie.utils.h.e()
            float r1 = r1 * r2
            int r1 = (int) r1
            r47 = r1
            goto L_0x0066
        L_0x0243:
            r10 = r41
            r11 = r42
            int r1 = r65.o()
            long r1 = (long) r1
            r52 = r1
            goto L_0x0066
        L_0x0250:
            r10 = r41
            r11 = r42
            int r1 = r65.o()
            com.airbnb.lottie.model.layer.e$a r2 = com.airbnb.lottie.model.layer.e.a.UNKNOWN
            int r2 = r2.ordinal()
            if (r1 >= r2) goto L_0x026c
            com.airbnb.lottie.model.layer.e$a[] r2 = com.airbnb.lottie.model.layer.e.a.values()
            r43 = r2[r1]
            r41 = r10
            r42 = r11
            goto L_0x0066
        L_0x026c:
            com.airbnb.lottie.model.layer.e$a r43 = com.airbnb.lottie.model.layer.e.a.UNKNOWN
            r41 = r10
            r42 = r11
            goto L_0x0066
        L_0x0274:
            r10 = r41
            r11 = r42
            java.lang.String r44 = r65.s()
            goto L_0x0066
        L_0x027e:
            r10 = r41
            r11 = r42
            int r1 = r65.o()
            long r1 = (long) r1
            r45 = r1
            goto L_0x0066
        L_0x028b:
            r10 = r41
            r11 = r42
            java.lang.String r14 = r65.s()
            goto L_0x0066
        L_0x0295:
            r41 = r10
            r42 = r11
            goto L_0x0066
        L_0x029b:
            r10 = r41
            r11 = r42
            r65.j()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r9 = r1
            r8 = 0
            int r1 = (r56 > r8 ? 1 : (r56 == r8 ? 0 : -1))
            if (r1 <= 0) goto L_0x02c6
            com.airbnb.lottie.value.a r13 = new com.airbnb.lottie.value.a
            java.lang.Float r3 = java.lang.Float.valueOf(r8)
            java.lang.Float r4 = java.lang.Float.valueOf(r8)
            r5 = 0
            r6 = 0
            java.lang.Float r7 = java.lang.Float.valueOf(r56)
            r1 = r13
            r2 = r66
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r9.add(r1)
        L_0x02c6:
            int r1 = (r17 > r8 ? 1 : (r17 == r8 ? 0 : -1))
            if (r1 <= 0) goto L_0x02cd
            r13 = r17
            goto L_0x02d2
        L_0x02cd:
            float r1 = r66.f()
            r13 = r1
        L_0x02d2:
            com.airbnb.lottie.value.a r16 = new com.airbnb.lottie.value.a
            r1 = 1065353216(0x3f800000, float:1.0)
            java.lang.Float r3 = java.lang.Float.valueOf(r1)
            java.lang.Float r4 = java.lang.Float.valueOf(r1)
            r5 = 0
            java.lang.Float r7 = java.lang.Float.valueOf(r13)
            r1 = r16
            r2 = r66
            r6 = r56
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r9.add(r1)
            com.airbnb.lottie.value.a r2 = new com.airbnb.lottie.value.a
            java.lang.Float r3 = java.lang.Float.valueOf(r8)
            java.lang.Float r4 = java.lang.Float.valueOf(r8)
            r6 = 2139095039(0x7f7fffff, float:3.4028235E38)
            java.lang.Float r6 = java.lang.Float.valueOf(r6)
            r8 = r2
            r7 = r9
            r9 = r66
            r41 = r10
            r10 = r3
            r3 = r11
            r11 = r4
            r4 = r12
            r12 = r5
            r5 = r14
            r14 = r6
            r8.<init>(r9, r10, r11, r12, r13, r14)
            r7.add(r2)
            java.lang.String r6 = ".ai"
            boolean r6 = r5.endsWith(r6)
            if (r6 != 0) goto L_0x0323
            java.lang.String r6 = "ai"
            boolean r6 = r6.equals(r4)
            if (r6 == 0) goto L_0x0328
        L_0x0323:
            java.lang.String r6 = "Convert your Illustrator layers to shape layers."
            r15.a(r6)
        L_0x0328:
            com.airbnb.lottie.model.layer.e r6 = new com.airbnb.lottie.model.layer.e
            r14 = r6
            r15 = r3
            r16 = r66
            r17 = r5
            r18 = r45
            r20 = r43
            r21 = r52
            r23 = r44
            r24 = r41
            r25 = r61
            r26 = r47
            r27 = r48
            r28 = r49
            r29 = r54
            r30 = r55
            r31 = r50
            r32 = r51
            r33 = r62
            r34 = r63
            r35 = r7
            r36 = r60
            r37 = r64
            r38 = r57
            r39 = r58
            r40 = r59
            r14.<init>(r15, r16, r17, r18, r20, r21, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.v.b(com.airbnb.lottie.parser.moshi.a, com.airbnb.lottie.c0):com.airbnb.lottie.model.layer.e");
    }

    /* compiled from: LayerParser */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[e.b.values().length];
            a = iArr;
            try {
                iArr[e.b.LUMA.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[e.b.LUMA_INVERTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }
}
