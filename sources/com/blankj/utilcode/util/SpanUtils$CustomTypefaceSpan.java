package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

@SuppressLint({"ParcelCreator"})
public class SpanUtils$CustomTypefaceSpan extends TypefaceSpan {
    private final Typeface c;

    public void updateDrawState(TextPaint textPaint) {
        a(textPaint, this.c);
    }

    public void updateMeasureState(TextPaint paint) {
        a(paint, this.c);
    }

    private void a(Paint paint, Typeface tf) {
        int oldStyle;
        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }
        int fake = (~tf.getStyle()) & oldStyle;
        if ((fake & 1) != 0) {
            paint.setFakeBoldText(true);
        }
        if ((fake & 2) != 0) {
            paint.setTextSkewX(-0.25f);
        }
        paint.getShader();
        paint.setTypeface(tf);
    }
}
