package com.google.maps.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.google.maps.android.R;

public class BubbleDrawable extends Drawable {
    private int mColor = -1;
    private final Drawable mMask;
    private final Drawable mShadow;

    public BubbleDrawable(Context context) {
        this.mMask = ContextCompat.getDrawable(context, R.drawable.amu_bubble_mask);
        this.mShadow = ContextCompat.getDrawable(context, R.drawable.amu_bubble_shadow);
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public void draw(Canvas canvas) {
        this.mMask.draw(canvas);
        canvas.drawColor(this.mColor, PorterDuff.Mode.SRC_IN);
        this.mShadow.draw(canvas);
    }

    public void setAlpha(int alpha) {
        throw new UnsupportedOperationException();
    }

    public void setColorFilter(ColorFilter cf) {
        throw new UnsupportedOperationException();
    }

    public int getOpacity() {
        return -3;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        this.mMask.setBounds(left, top, right, bottom);
        this.mShadow.setBounds(left, top, right, bottom);
    }

    public void setBounds(Rect bounds) {
        this.mMask.setBounds(bounds);
        this.mShadow.setBounds(bounds);
    }

    public boolean getPadding(Rect padding) {
        return this.mMask.getPadding(padding);
    }
}
