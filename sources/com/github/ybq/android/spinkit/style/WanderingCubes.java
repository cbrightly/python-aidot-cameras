package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

public class WanderingCubes extends SpriteContainer {
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Cube(0), new Cube(3)};
    }

    public void onChildCreated(Sprite... sprites) {
        super.onChildCreated(sprites);
        if (Build.VERSION.SDK_INT < 24) {
            sprites[1].setAnimationDelay(-900);
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        Rect bounds2 = clipSquare(bounds);
        super.onBoundsChange(bounds2);
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            int i2 = bounds2.left;
            sprite.setDrawBounds(i2, bounds2.top, (bounds2.width() / 4) + i2, bounds2.top + (bounds2.height() / 4));
        }
    }

    public class Cube extends RectSprite {
        int startFrame;

        public Cube(int startFrame2) {
            this.startFrame = startFrame2;
        }

        public ValueAnimator onCreateAnimation() {
            float[] fractions = {0.0f, 0.25f, 0.5f, 0.51f, 0.75f, 1.0f};
            SpriteAnimatorBuilder rotate = new SpriteAnimatorBuilder(this).rotate(fractions, 0, -90, -179, -180, -270, -360);
            Float valueOf = Float.valueOf(0.0f);
            Float valueOf2 = Float.valueOf(0.75f);
            SpriteAnimatorBuilder translateYPercentage = rotate.translateXPercentage(fractions, valueOf, valueOf2, valueOf2, valueOf2, valueOf, valueOf).translateYPercentage(fractions, valueOf, valueOf, valueOf2, valueOf2, valueOf2, valueOf);
            Float valueOf3 = Float.valueOf(1.0f);
            Float valueOf4 = Float.valueOf(0.5f);
            SpriteAnimatorBuilder builder = translateYPercentage.scale(fractions, valueOf3, valueOf4, valueOf3, valueOf3, valueOf4, valueOf3).duration(1800).easeInOut(fractions);
            if (Build.VERSION.SDK_INT >= 24) {
                builder.startFrame(this.startFrame);
            }
            return builder.build();
        }
    }
}
