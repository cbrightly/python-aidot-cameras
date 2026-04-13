package com.github.ybq.android.spinkit.sprite;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class CircleLayoutContainer extends SpriteContainer {
    public void drawChild(Canvas canvas) {
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            int count = canvas.save();
            canvas.rotate((float) ((i * 360) / getChildCount()), (float) getBounds().centerX(), (float) getBounds().centerY());
            sprite.draw(canvas);
            canvas.restoreToCount(count);
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        Rect bounds2 = clipSquare(bounds);
        int radius = (int) (((((double) bounds2.width()) * 3.141592653589793d) / 3.5999999046325684d) / ((double) getChildCount()));
        int left = bounds2.centerX() - radius;
        int right = bounds2.centerX() + radius;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            int i2 = bounds2.top;
            sprite.setDrawBounds(left, i2, right, (radius * 2) + i2);
        }
    }
}
