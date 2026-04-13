package com.github.ybq.android.spinkit.style;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.view.animation.LinearInterpolator;
import com.github.ybq.android.spinkit.animation.SpriteAnimatorBuilder;
import com.github.ybq.android.spinkit.sprite.RectSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.sprite.SpriteContainer;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class FoldingCube extends SpriteContainer {
    private boolean wrapContent = false;

    public Sprite[] onCreateChild() {
        Cube[] cubes = new Cube[4];
        for (int i = 0; i < cubes.length; i++) {
            cubes[i] = new Cube();
            if (Build.VERSION.SDK_INT >= 24) {
                cubes[i].setAnimationDelay(i * IjkMediaCodecInfo.RANK_SECURE);
            } else {
                cubes[i].setAnimationDelay((i * IjkMediaCodecInfo.RANK_SECURE) - 1200);
            }
        }
        return cubes;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        Rect bounds2 = clipSquare(bounds);
        int size = Math.min(bounds2.width(), bounds2.height());
        if (this.wrapContent) {
            size = (int) Math.sqrt((double) ((size * size) / 2));
            int oW = (bounds2.width() - size) / 2;
            int oH = (bounds2.height() - size) / 2;
            bounds2 = new Rect(bounds2.left + oW, bounds2.top + oH, bounds2.right - oW, bounds2.bottom - oH);
        }
        int px = bounds2.left + (size / 2) + 1;
        int py = bounds2.top + (size / 2) + 1;
        for (int i = 0; i < getChildCount(); i++) {
            Sprite sprite = getChildAt(i);
            sprite.setDrawBounds(bounds2.left, bounds2.top, px, py);
            sprite.setPivotX((float) sprite.getDrawBounds().right);
            sprite.setPivotY((float) sprite.getDrawBounds().bottom);
        }
    }

    public void drawChild(Canvas canvas) {
        Rect bounds = clipSquare(getBounds());
        for (int i = 0; i < getChildCount(); i++) {
            int count = canvas.save();
            canvas.rotate((float) ((i * 90) + 45), (float) bounds.centerX(), (float) bounds.centerY());
            getChildAt(i).draw(canvas);
            canvas.restoreToCount(count);
        }
    }

    public class Cube extends RectSprite {
        Cube() {
            setAlpha(0);
            setRotateX(-180);
        }

        public ValueAnimator onCreateAnimation() {
            float[] fractions = {0.0f, 0.1f, 0.25f, 0.75f, 0.9f, 1.0f};
            return new SpriteAnimatorBuilder(this).alpha(fractions, 0, 0, 255, 255, 0, 0).rotateX(fractions, -180, -180, 0, 0, 0, 0).rotateY(fractions, 0, 0, 0, 0, 180, 180).duration(2400).interpolator(new LinearInterpolator()).build();
        }
    }
}
