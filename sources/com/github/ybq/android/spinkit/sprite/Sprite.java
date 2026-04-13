package com.github.ybq.android.spinkit.sprite;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import com.github.ybq.android.spinkit.animation.AnimationUtils;
import com.github.ybq.android.spinkit.animation.FloatProperty;
import com.github.ybq.android.spinkit.animation.IntProperty;
import com.leedarson.bean.Constants;

public abstract class Sprite extends Drawable implements ValueAnimator.AnimatorUpdateListener, Animatable, Drawable.Callback {
    public static final Property<Sprite, Integer> ALPHA = new IntProperty<Sprite>("alpha") {
        public void setValue(Sprite object, int value) {
            object.setAlpha(value);
        }

        public Integer get(Sprite object) {
            return Integer.valueOf(object.getAlpha());
        }
    };
    public static final Property<Sprite, Integer> ROTATE = new IntProperty<Sprite>(Constants.ACTION_ROTATE) {
        public void setValue(Sprite object, int value) {
            object.setRotate(value);
        }

        public Integer get(Sprite object) {
            return Integer.valueOf(object.getRotate());
        }
    };
    public static final Property<Sprite, Integer> ROTATE_X = new IntProperty<Sprite>("rotateX") {
        public void setValue(Sprite object, int value) {
            object.setRotateX(value);
        }

        public Integer get(Sprite object) {
            return Integer.valueOf(object.getRotateX());
        }
    };
    public static final Property<Sprite, Integer> ROTATE_Y = new IntProperty<Sprite>("rotateY") {
        public void setValue(Sprite object, int value) {
            object.setRotateY(value);
        }

        public Integer get(Sprite object) {
            return Integer.valueOf(object.getRotateY());
        }
    };
    public static final Property<Sprite, Float> SCALE = new FloatProperty<Sprite>("scale") {
        public void setValue(Sprite object, float value) {
            object.setScale(value);
        }

        public Float get(Sprite object) {
            return Float.valueOf(object.getScale());
        }
    };
    public static final Property<Sprite, Float> SCALE_X = new FloatProperty<Sprite>("scaleX") {
        public void setValue(Sprite object, float value) {
            object.setScaleX(value);
        }

        public Float get(Sprite object) {
            return Float.valueOf(object.getScaleX());
        }
    };
    public static final Property<Sprite, Float> SCALE_Y = new FloatProperty<Sprite>("scaleY") {
        public void setValue(Sprite object, float value) {
            object.setScaleY(value);
        }

        public Float get(Sprite object) {
            return Float.valueOf(object.getScaleY());
        }
    };
    public static final Property<Sprite, Integer> TRANSLATE_X = new IntProperty<Sprite>("translateX") {
        public void setValue(Sprite object, int value) {
            object.setTranslateX(value);
        }

        public Integer get(Sprite object) {
            return Integer.valueOf(object.getTranslateX());
        }
    };
    public static final Property<Sprite, Float> TRANSLATE_X_PERCENTAGE = new FloatProperty<Sprite>("translateXPercentage") {
        public void setValue(Sprite object, float value) {
            object.setTranslateXPercentage(value);
        }

        public Float get(Sprite object) {
            return Float.valueOf(object.getTranslateXPercentage());
        }
    };
    public static final Property<Sprite, Integer> TRANSLATE_Y = new IntProperty<Sprite>("translateY") {
        public void setValue(Sprite object, int value) {
            object.setTranslateY(value);
        }

        public Integer get(Sprite object) {
            return Integer.valueOf(object.getTranslateY());
        }
    };
    public static final Property<Sprite, Float> TRANSLATE_Y_PERCENTAGE = new FloatProperty<Sprite>("translateYPercentage") {
        public void setValue(Sprite object, float value) {
            object.setTranslateYPercentage(value);
        }

        public Float get(Sprite object) {
            return Float.valueOf(object.getTranslateYPercentage());
        }
    };
    private static final Rect ZERO_BOUNDS_RECT = new Rect();
    private int alpha = 255;
    private int animationDelay;
    private ValueAnimator animator;
    protected Rect drawBounds = ZERO_BOUNDS_RECT;
    private Camera mCamera = new Camera();
    private Matrix mMatrix = new Matrix();
    private float pivotX;
    private float pivotY;
    private int rotate;
    private int rotateX;
    private int rotateY;
    private float scale = 1.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private int translateX;
    private float translateXPercentage;
    private int translateY;
    private float translateYPercentage;

    /* access modifiers changed from: protected */
    public abstract void drawSelf(Canvas canvas);

    public abstract int getColor();

    public abstract ValueAnimator onCreateAnimation();

    public abstract void setColor(int i);

    public void setAlpha(int alpha2) {
        this.alpha = alpha2;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public int getOpacity() {
        return -3;
    }

    public float getTranslateXPercentage() {
        return this.translateXPercentage;
    }

    public void setTranslateXPercentage(float translateXPercentage2) {
        this.translateXPercentage = translateXPercentage2;
    }

    public float getTranslateYPercentage() {
        return this.translateYPercentage;
    }

    public void setTranslateYPercentage(float translateYPercentage2) {
        this.translateYPercentage = translateYPercentage2;
    }

    public int getTranslateX() {
        return this.translateX;
    }

    public void setTranslateX(int translateX2) {
        this.translateX = translateX2;
    }

    public int getTranslateY() {
        return this.translateY;
    }

    public void setTranslateY(int translateY2) {
        this.translateY = translateY2;
    }

    public int getRotate() {
        return this.rotate;
    }

    public void setRotate(int rotate2) {
        this.rotate = rotate2;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale2) {
        this.scale = scale2;
        setScaleX(scale2);
        setScaleY(scale2);
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public void setScaleX(float scaleX2) {
        this.scaleX = scaleX2;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(float scaleY2) {
        this.scaleY = scaleY2;
    }

    public int getRotateX() {
        return this.rotateX;
    }

    public void setRotateX(int rotateX2) {
        this.rotateX = rotateX2;
    }

    public int getRotateY() {
        return this.rotateY;
    }

    public void setRotateY(int rotateY2) {
        this.rotateY = rotateY2;
    }

    public float getPivotX() {
        return this.pivotX;
    }

    public void setPivotX(float pivotX2) {
        this.pivotX = pivotX2;
    }

    public float getPivotY() {
        return this.pivotY;
    }

    public void setPivotY(float pivotY2) {
        this.pivotY = pivotY2;
    }

    public int getAnimationDelay() {
        return this.animationDelay;
    }

    public Sprite setAnimationDelay(int animationDelay2) {
        this.animationDelay = animationDelay2;
        return this;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void start() {
        if (!AnimationUtils.isStarted(this.animator)) {
            ValueAnimator obtainAnimation = obtainAnimation();
            this.animator = obtainAnimation;
            if (obtainAnimation != null) {
                AnimationUtils.start((Animator) obtainAnimation);
                invalidateSelf();
            }
        }
    }

    public ValueAnimator obtainAnimation() {
        if (this.animator == null) {
            this.animator = onCreateAnimation();
        }
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null) {
            valueAnimator.addUpdateListener(this);
            this.animator.setStartDelay((long) this.animationDelay);
        }
        return this.animator;
    }

    public void stop() {
        if (AnimationUtils.isStarted(this.animator)) {
            this.animator.removeAllUpdateListeners();
            this.animator.end();
            reset();
        }
    }

    public void reset() {
        this.scale = 1.0f;
        this.rotateX = 0;
        this.rotateY = 0;
        this.translateX = 0;
        this.translateY = 0;
        this.rotate = 0;
        this.translateXPercentage = 0.0f;
        this.translateYPercentage = 0.0f;
    }

    public boolean isRunning() {
        return AnimationUtils.isRunning(this.animator);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        setDrawBounds(bounds);
    }

    public void setDrawBounds(Rect drawBounds2) {
        setDrawBounds(drawBounds2.left, drawBounds2.top, drawBounds2.right, drawBounds2.bottom);
    }

    public void setDrawBounds(int left, int top, int right, int bottom) {
        this.drawBounds = new Rect(left, top, right, bottom);
        setPivotX((float) getDrawBounds().centerX());
        setPivotY((float) getDrawBounds().centerY());
    }

    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public Rect getDrawBounds() {
        return this.drawBounds;
    }

    public void draw(Canvas canvas) {
        int tx = getTranslateX();
        int tx2 = tx == 0 ? (int) (((float) getBounds().width()) * getTranslateXPercentage()) : tx;
        int ty = getTranslateY();
        canvas.translate((float) tx2, (float) (ty == 0 ? (int) (((float) getBounds().height()) * getTranslateYPercentage()) : ty));
        canvas.scale(getScaleX(), getScaleY(), getPivotX(), getPivotY());
        canvas.rotate((float) getRotate(), getPivotX(), getPivotY());
        if (!(getRotateX() == 0 && getRotateY() == 0)) {
            this.mCamera.save();
            this.mCamera.rotateX((float) getRotateX());
            this.mCamera.rotateY((float) getRotateY());
            this.mCamera.getMatrix(this.mMatrix);
            this.mMatrix.preTranslate(-getPivotX(), -getPivotY());
            this.mMatrix.postTranslate(getPivotX(), getPivotY());
            this.mCamera.restore();
            canvas.concat(this.mMatrix);
        }
        drawSelf(canvas);
    }

    public Rect clipSquare(Rect rect) {
        int min = Math.min(rect.width(), rect.height());
        int cx = rect.centerX();
        int cy = rect.centerY();
        int r = min / 2;
        return new Rect(cx - r, cy - r, cx + r, cy + r);
    }
}
