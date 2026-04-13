package com.github.ybq.android.spinkit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.github.ybq.android.spinkit.animation.AnimationUtils;

public abstract class SpriteContainer extends Sprite {
    private int color;
    private Sprite[] sprites = onCreateChild();

    public abstract Sprite[] onCreateChild();

    public SpriteContainer() {
        initCallBack();
        onChildCreated(this.sprites);
    }

    private void initCallBack() {
        Sprite[] spriteArr = this.sprites;
        if (spriteArr != null) {
            for (Sprite sprite : spriteArr) {
                sprite.setCallback(this);
            }
        }
    }

    public void onChildCreated(Sprite... sprites2) {
    }

    public int getChildCount() {
        Sprite[] spriteArr = this.sprites;
        if (spriteArr == null) {
            return 0;
        }
        return spriteArr.length;
    }

    public Sprite getChildAt(int index) {
        Sprite[] spriteArr = this.sprites;
        if (spriteArr == null) {
            return null;
        }
        return spriteArr[index];
    }

    public void setColor(int color2) {
        this.color = color2;
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setColor(color2);
        }
    }

    public int getColor() {
        return this.color;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawChild(canvas);
    }

    public void drawChild(Canvas canvas) {
        Sprite[] spriteArr = this.sprites;
        if (spriteArr != null) {
            for (Sprite sprite : spriteArr) {
                int count = canvas.save();
                sprite.draw(canvas);
                canvas.restoreToCount(count);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawSelf(Canvas canvas) {
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        for (Sprite sprite : this.sprites) {
            sprite.setBounds(bounds);
        }
    }

    public void start() {
        super.start();
        AnimationUtils.start(this.sprites);
    }

    public void stop() {
        super.stop();
        AnimationUtils.stop(this.sprites);
    }

    public boolean isRunning() {
        return AnimationUtils.isRunning(this.sprites) || super.isRunning();
    }

    public ValueAnimator onCreateAnimation() {
        return null;
    }
}
