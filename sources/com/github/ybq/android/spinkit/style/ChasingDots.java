package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import android.view.animation.LinearInterpolator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import meshsdk.cache.CacheHandler;

public class ChasingDots extends SpriteContainer {
    public Sprite[] onCreateChild() {
        return new Sprite[]{new Dot(), new Dot()};
    }

    public void onChildCreated(Sprite... sprites) {
        super.onChildCreated(sprites);
        if (Build.VERSION.SDK_INT >= 24) {
            sprites[1].setAnimationDelay(1000);
        } else {
            sprites[1].setAnimationDelay(-1000);
        }
    }

    public ValueAnimator onCreateAnimation() {
        return new SpriteAnimatorBuilder(this).rotate(new float[]{0.0f, 1.0f}, 0, 360).duration(CacheHandler.delayTime).interpolator(new LinearInterpolator()).build();
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        Rect bounds2 = clipSquare(bounds);
        int drawW = (int) (((float) bounds2.width()) * 0.6f);
        Sprite childAt = getChildAt(0);
        int i = bounds2.right;
        int i2 = bounds2.top;
        childAt.setDrawBounds(i - drawW, i2, i, i2 + drawW);
        Sprite childAt2 = getChildAt(1);
        int i3 = bounds2.right;
        int i4 = bounds2.bottom;
        childAt2.setDrawBounds(i3 - drawW, i4 - drawW, i3, i4);
    }

    public class Dot extends CircleSprite {
        Dot() {
            setScale(0.0f);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fractions = {0.0f, 0.5f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(0.0f);
            return spriteAnimatorBuilder.scale(fractions, valueOf, Float.valueOf(1.0f), valueOf).duration(CacheHandler.delayTime).easeInOut(fractions).build();
        }
    }
}
