package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;

public class Wave extends SpriteContainer {
    public Sprite[] onCreateChild() {
        WaveItem[] waveItems = new WaveItem[5];
        for (int i = 0; i < waveItems.length; i++) {
            waveItems[i] = new WaveItem();
            if (Build.VERSION.SDK_INT >= 24) {
                waveItems[i].setAnimationDelay((i * 100) + 600);
            } else {
                waveItems[i].setAnimationDelay((i * 100) - 1200);
            }
        }
        return waveItems;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        Rect bounds2 = clipSquare(bounds);
        int rw = bounds2.width() / getChildCount();
        int width = ((bounds2.width() / 5) * 3) / 5;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            int l = bounds2.left + (i * rw) + (rw / 5);
            sprite.setDrawBounds(l, bounds2.top, l + width, bounds2.bottom);
        }
    }

    public class WaveItem extends RectSprite {
        WaveItem() {
            setScaleY(0.4f);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fractions = {0.0f, 0.2f, 0.4f, 1.0f};
            SpriteAnimatorBuilder spriteAnimatorBuilder = new SpriteAnimatorBuilder(this);
            Float valueOf = Float.valueOf(0.4f);
            return spriteAnimatorBuilder.scaleY(fractions, valueOf, Float.valueOf(1.0f), valueOf, valueOf).duration(1200).easeInOut(fractions).build();
        }
    }
}
