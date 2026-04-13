package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.model.b;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: DocumentDataParser */
public class i implements n0<b> {
    public static final i a = new i();
    private static final a.C0011a b = a.C0011a.a("t", "f", "s", "j", "tr", "lh", "ls", "fc", "sc", "sw", "of", "ps", "sz");

    private i() {
    }

    /* renamed from: b */
    public b a(a reader, float scale) {
        String text = null;
        String fontName = null;
        float size = 0.0f;
        b.a justification = b.a.CENTER;
        int tracking = 0;
        float lineHeight = 0.0f;
        float baselineShift = 0.0f;
        int fillColor = 0;
        int strokeColor = 0;
        float strokeWidth = 0.0f;
        boolean strokeOverFill = true;
        PointF boxPosition = null;
        PointF boxSize = null;
        reader.g();
        while (reader.l()) {
            switch (reader.x(b)) {
                case 0:
                    PointF pointF = boxSize;
                    text = reader.s();
                    break;
                case 1:
                    PointF pointF2 = boxSize;
                    fontName = reader.s();
                    break;
                case 2:
                    size = (float) reader.n();
                    boxPosition = boxPosition;
                    boxSize = boxSize;
                    break;
                case 3:
                    PointF boxPosition2 = boxPosition;
                    PointF boxSize2 = boxSize;
                    int justificationInt = reader.o();
                    if (justificationInt <= b.a.CENTER.ordinal() && justificationInt >= 0) {
                        justification = b.a.values()[justificationInt];
                        boxPosition = boxPosition2;
                        boxSize = boxSize2;
                        break;
                    } else {
                        justification = b.a.CENTER;
                        boxPosition = boxPosition2;
                        boxSize = boxSize2;
                        break;
                    }
                case 4:
                    PointF pointF3 = boxSize;
                    tracking = reader.o();
                    break;
                case 5:
                    lineHeight = (float) reader.n();
                    boxPosition = boxPosition;
                    boxSize = boxSize;
                    break;
                case 6:
                    baselineShift = (float) reader.n();
                    boxPosition = boxPosition;
                    boxSize = boxSize;
                    break;
                case 7:
                    PointF pointF4 = boxSize;
                    fillColor = s.d(reader);
                    break;
                case 8:
                    PointF pointF5 = boxSize;
                    strokeColor = s.d(reader);
                    break;
                case 9:
                    strokeWidth = (float) reader.n();
                    boxPosition = boxPosition;
                    boxSize = boxSize;
                    break;
                case 10:
                    PointF pointF6 = boxPosition;
                    PointF pointF7 = boxSize;
                    strokeOverFill = reader.m();
                    break;
                case 11:
                    reader.c();
                    boxPosition = new PointF(((float) reader.n()) * scale, ((float) reader.n()) * scale);
                    reader.i();
                    boxSize = boxSize;
                    break;
                case 12:
                    reader.c();
                    PointF pointF8 = boxSize;
                    boxSize = new PointF(((float) reader.n()) * scale, ((float) reader.n()) * scale);
                    reader.i();
                    boxPosition = boxPosition;
                    break;
                default:
                    PointF pointF9 = boxPosition;
                    PointF pointF10 = boxSize;
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
        return new b(text, fontName, size, justification, tracking, lineHeight, baselineShift, fillColor, strokeColor, strokeWidth, strokeOverFill, boxPosition, boxSize);
    }
}
