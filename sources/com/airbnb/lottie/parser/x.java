package com.airbnb.lottie.parser;

/* compiled from: MaskParser */
public class x {
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0092, code lost:
        if (r5.equals("a") != false) goto L_0x0096;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static com.airbnb.lottie.model.content.h a(com.airbnb.lottie.parser.moshi.a r12, com.airbnb.lottie.c0 r13) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r12.g()
        L_0x0007:
            boolean r4 = r12.l()
            if (r4 == 0) goto L_0x00c9
            java.lang.String r4 = r12.r()
            int r5 = r4.hashCode()
            r6 = 0
            r7 = 3
            r8 = 1
            r9 = 2
            r10 = -1
            switch(r5) {
                case 111: goto L_0x003c;
                case 3588: goto L_0x0032;
                case 104433: goto L_0x0028;
                case 3357091: goto L_0x001e;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x0046
        L_0x001e:
            java.lang.String r5 = "mode"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x001d
            r5 = r6
            goto L_0x0047
        L_0x0028:
            java.lang.String r5 = "inv"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x001d
            r5 = r7
            goto L_0x0047
        L_0x0032:
            java.lang.String r5 = "pt"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x001d
            r5 = r8
            goto L_0x0047
        L_0x003c:
            java.lang.String r5 = "o"
            boolean r5 = r4.equals(r5)
            if (r5 == 0) goto L_0x001d
            r5 = r9
            goto L_0x0047
        L_0x0046:
            r5 = r10
        L_0x0047:
            switch(r5) {
                case 0: goto L_0x0061;
                case 1: goto L_0x005b;
                case 2: goto L_0x0055;
                case 3: goto L_0x004f;
                default: goto L_0x004a;
            }
        L_0x004a:
            r12.E()
            goto L_0x00c7
        L_0x004f:
            boolean r3 = r12.m()
            goto L_0x00c7
        L_0x0055:
            com.airbnb.lottie.model.animatable.d r2 = com.airbnb.lottie.parser.d.h(r12, r13)
            goto L_0x00c7
        L_0x005b:
            com.airbnb.lottie.model.animatable.h r1 = com.airbnb.lottie.parser.d.k(r12, r13)
            goto L_0x00c7
        L_0x0061:
            java.lang.String r5 = r12.s()
            int r11 = r5.hashCode()
            switch(r11) {
                case 97: goto L_0x008c;
                case 105: goto L_0x0082;
                case 110: goto L_0x0078;
                case 115: goto L_0x006d;
                default: goto L_0x006c;
            }
        L_0x006c:
            goto L_0x0095
        L_0x006d:
            java.lang.String r6 = "s"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x006c
            r6 = r8
            goto L_0x0096
        L_0x0078:
            java.lang.String r6 = "n"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x006c
            r6 = r9
            goto L_0x0096
        L_0x0082:
            java.lang.String r6 = "i"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x006c
            r6 = r7
            goto L_0x0096
        L_0x008c:
            java.lang.String r7 = "a"
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x006c
            goto L_0x0096
        L_0x0095:
            r6 = r10
        L_0x0096:
            switch(r6) {
                case 0: goto L_0x00c3;
                case 1: goto L_0x00c0;
                case 2: goto L_0x00bd;
                case 3: goto L_0x00b5;
                default: goto L_0x0099;
            }
        L_0x0099:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Unknown mask mode "
            r5.append(r6)
            r5.append(r4)
            java.lang.String r6 = ". Defaulting to Add."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.airbnb.lottie.utils.d.c(r5)
            com.airbnb.lottie.model.content.h$a r0 = com.airbnb.lottie.model.content.h.a.MASK_MODE_ADD
            goto L_0x00c6
        L_0x00b5:
            java.lang.String r5 = "Animation contains intersect masks. They are not supported but will be treated like add masks."
            r13.a(r5)
            com.airbnb.lottie.model.content.h$a r0 = com.airbnb.lottie.model.content.h.a.MASK_MODE_INTERSECT
            goto L_0x00c6
        L_0x00bd:
            com.airbnb.lottie.model.content.h$a r0 = com.airbnb.lottie.model.content.h.a.MASK_MODE_NONE
            goto L_0x00c6
        L_0x00c0:
            com.airbnb.lottie.model.content.h$a r0 = com.airbnb.lottie.model.content.h.a.MASK_MODE_SUBTRACT
            goto L_0x00c6
        L_0x00c3:
            com.airbnb.lottie.model.content.h$a r0 = com.airbnb.lottie.model.content.h.a.MASK_MODE_ADD
        L_0x00c6:
        L_0x00c7:
            goto L_0x0007
        L_0x00c9:
            r12.j()
            com.airbnb.lottie.model.content.h r4 = new com.airbnb.lottie.model.content.h
            r4.<init>(r0, r1, r2, r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.x.a(com.airbnb.lottie.parser.moshi.a, com.airbnb.lottie.c0):com.airbnb.lottie.model.content.h");
    }
}
