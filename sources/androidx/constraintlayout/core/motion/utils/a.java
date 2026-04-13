package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.utils.TypedValues;

/* compiled from: TypedValues */
public final /* synthetic */ class a {
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(java.lang.String r2) {
        /*
            int r0 = r2.hashCode()
            r1 = -1
            switch(r0) {
                case -1310311125: goto L_0x00e8;
                case -1249320806: goto L_0x00dd;
                case -1249320805: goto L_0x00d1;
                case -1249320804: goto L_0x00c5;
                case -1225497657: goto L_0x00ba;
                case -1225497656: goto L_0x00af;
                case -1225497655: goto L_0x00a4;
                case -1001078227: goto L_0x0099;
                case -987906986: goto L_0x008e;
                case -987906985: goto L_0x0082;
                case -908189618: goto L_0x0075;
                case -908189617: goto L_0x0068;
                case -880905839: goto L_0x005b;
                case -4379043: goto L_0x0050;
                case 92909918: goto L_0x0045;
                case 97692013: goto L_0x0039;
                case 579057826: goto L_0x002e;
                case 803192288: goto L_0x0022;
                case 1167159411: goto L_0x0016;
                case 1941332754: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x00f3
        L_0x000a:
            java.lang.String r0 = "visibility"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 1
            goto L_0x00f4
        L_0x0016:
            java.lang.String r0 = "pivotTarget"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 19
            goto L_0x00f4
        L_0x0022:
            java.lang.String r0 = "pathRotate"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 15
            goto L_0x00f4
        L_0x002e:
            java.lang.String r0 = "curveFit"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 0
            goto L_0x00f4
        L_0x0039:
            java.lang.String r0 = "frame"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 17
            goto L_0x00f4
        L_0x0045:
            java.lang.String r0 = "alpha"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 2
            goto L_0x00f4
        L_0x0050:
            java.lang.String r0 = "elevation"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 6
            goto L_0x00f4
        L_0x005b:
            java.lang.String r0 = "target"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 18
            goto L_0x00f4
        L_0x0068:
            java.lang.String r0 = "scaleY"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 11
            goto L_0x00f4
        L_0x0075:
            java.lang.String r0 = "scaleX"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 10
            goto L_0x00f4
        L_0x0082:
            java.lang.String r0 = "pivotY"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 13
            goto L_0x00f4
        L_0x008e:
            java.lang.String r0 = "pivotX"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 12
            goto L_0x00f4
        L_0x0099:
            java.lang.String r0 = "progress"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 14
            goto L_0x00f4
        L_0x00a4:
            java.lang.String r0 = "translationZ"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 5
            goto L_0x00f4
        L_0x00af:
            java.lang.String r0 = "translationY"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 4
            goto L_0x00f4
        L_0x00ba:
            java.lang.String r0 = "translationX"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 3
            goto L_0x00f4
        L_0x00c5:
            java.lang.String r0 = "rotationZ"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 9
            goto L_0x00f4
        L_0x00d1:
            java.lang.String r0 = "rotationY"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 8
            goto L_0x00f4
        L_0x00dd:
            java.lang.String r0 = "rotationX"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 7
            goto L_0x00f4
        L_0x00e8:
            java.lang.String r0 = "easing"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0008
            r0 = 16
            goto L_0x00f4
        L_0x00f3:
            r0 = r1
        L_0x00f4:
            switch(r0) {
                case 0: goto L_0x0131;
                case 1: goto L_0x012e;
                case 2: goto L_0x012b;
                case 3: goto L_0x0128;
                case 4: goto L_0x0125;
                case 5: goto L_0x0122;
                case 6: goto L_0x011f;
                case 7: goto L_0x011c;
                case 8: goto L_0x0119;
                case 9: goto L_0x0116;
                case 10: goto L_0x0113;
                case 11: goto L_0x0110;
                case 12: goto L_0x010d;
                case 13: goto L_0x010a;
                case 14: goto L_0x0107;
                case 15: goto L_0x0104;
                case 16: goto L_0x0101;
                case 17: goto L_0x00fe;
                case 18: goto L_0x00fb;
                case 19: goto L_0x00f8;
                default: goto L_0x00f7;
            }
        L_0x00f7:
            return r1
        L_0x00f8:
            r0 = 318(0x13e, float:4.46E-43)
            return r0
        L_0x00fb:
            r0 = 101(0x65, float:1.42E-43)
            return r0
        L_0x00fe:
            r0 = 100
            return r0
        L_0x0101:
            r0 = 317(0x13d, float:4.44E-43)
            return r0
        L_0x0104:
            r0 = 316(0x13c, float:4.43E-43)
            return r0
        L_0x0107:
            r0 = 315(0x13b, float:4.41E-43)
            return r0
        L_0x010a:
            r0 = 314(0x13a, float:4.4E-43)
            return r0
        L_0x010d:
            r0 = 313(0x139, float:4.39E-43)
            return r0
        L_0x0110:
            r0 = 312(0x138, float:4.37E-43)
            return r0
        L_0x0113:
            r0 = 311(0x137, float:4.36E-43)
            return r0
        L_0x0116:
            r0 = 310(0x136, float:4.34E-43)
            return r0
        L_0x0119:
            r0 = 309(0x135, float:4.33E-43)
            return r0
        L_0x011c:
            r0 = 308(0x134, float:4.32E-43)
            return r0
        L_0x011f:
            r0 = 307(0x133, float:4.3E-43)
            return r0
        L_0x0122:
            r0 = 306(0x132, float:4.29E-43)
            return r0
        L_0x0125:
            r0 = 305(0x131, float:4.27E-43)
            return r0
        L_0x0128:
            r0 = 304(0x130, float:4.26E-43)
            return r0
        L_0x012b:
            r0 = 303(0x12f, float:4.25E-43)
            return r0
        L_0x012e:
            r0 = 302(0x12e, float:4.23E-43)
            return r0
        L_0x0131:
            r0 = 301(0x12d, float:4.22E-43)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.utils.a.a(java.lang.String):int");
    }

    public static int b(int name) {
        switch (name) {
            case 100:
            case 301:
            case 302:
                return 2;
            case 101:
            case TypedValues.AttributesType.TYPE_EASING:
            case TypedValues.AttributesType.TYPE_PIVOT_TARGET:
                return 8;
            case 303:
            case 304:
            case 305:
            case 306:
            case 307:
            case 308:
            case 309:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 315:
            case TypedValues.AttributesType.TYPE_PATH_ROTATE:
                return 4;
            default:
                return -1;
        }
    }
}
