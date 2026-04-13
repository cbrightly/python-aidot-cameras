package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;

public class RotatingPlane extends RectSprite {
    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        setDrawBounds(clipSquare(bounds));
    }

    public ValueAnimator onCreateAnimation() {
        float[] fractions = {0.0f, 0.5f, 1.0f};
        return new SpriteAnimatorBuilder(this).rotateX(fractions, 0, -180, -180).rotateY(fractions, 0, 0, -180).duration(1200).easeInOut(fractions).build();
    }
}
