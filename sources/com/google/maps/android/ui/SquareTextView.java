package com.google.maps.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class SquareTextView extends AppCompatTextView {
    private int mOffsetLeft = 0;
    private int mOffsetTop = 0;

    public SquareTextView(Context context) {
        super(context);
    }

    public SquareTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int dimension = Math.max(width, height);
        if (width > height) {
            this.mOffsetTop = width - height;
            this.mOffsetLeft = 0;
        } else {
            this.mOffsetTop = 0;
            this.mOffsetLeft = height - width;
        }
        setMeasuredDimension(dimension, dimension);
    }

    public void draw(Canvas canvas) {
        canvas.translate((float) (this.mOffsetLeft / 2), (float) (this.mOffsetTop / 2));
        super.draw(canvas);
    }
}
