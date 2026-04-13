package com.airbnb.lottie.model;

import android.graphics.PointF;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: DocumentData */
public class b {
    public String a;
    public String b;
    public float c;
    public a d;
    public int e;
    public float f;
    public float g;
    @ColorInt
    public int h;
    @ColorInt
    public int i;
    public float j;
    public boolean k;
    @Nullable
    public PointF l;
    @Nullable
    public PointF m;

    /* compiled from: DocumentData */
    public enum a {
        LEFT_ALIGN,
        RIGHT_ALIGN,
        CENTER
    }

    public b(String text, String fontName, float size, a justification, int tracking, float lineHeight, float baselineShift, @ColorInt int color, @ColorInt int strokeColor, float strokeWidth, boolean strokeOverFill, PointF boxPosition, PointF boxSize) {
        a(text, fontName, size, justification, tracking, lineHeight, baselineShift, color, strokeColor, strokeWidth, strokeOverFill, boxPosition, boxSize);
    }

    public b() {
    }

    public void a(String text, String fontName, float size, a justification, int tracking, float lineHeight, float baselineShift, @ColorInt int color, @ColorInt int strokeColor, float strokeWidth, boolean strokeOverFill, PointF boxPosition, PointF boxSize) {
        this.a = text;
        this.b = fontName;
        this.c = size;
        this.d = justification;
        this.e = tracking;
        this.f = lineHeight;
        this.g = baselineShift;
        this.h = color;
        this.i = strokeColor;
        this.j = strokeWidth;
        this.k = strokeOverFill;
        this.l = boxPosition;
        this.m = boxSize;
    }

    public int hashCode() {
        long temp = (long) Float.floatToRawIntBits(this.f);
        return (((((((((int) (((float) (((this.a.hashCode() * 31) + this.b.hashCode()) * 31)) + this.c)) * 31) + this.d.ordinal()) * 31) + this.e) * 31) + ((int) ((temp >>> 32) ^ temp))) * 31) + this.h;
    }
}
