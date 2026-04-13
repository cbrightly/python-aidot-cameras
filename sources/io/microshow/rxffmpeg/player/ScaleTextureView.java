package io.microshow.rxffmpeg.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;

public class ScaleTextureView extends TextureView {
    private float actionX;
    private float actionY;
    private float degree;
    private boolean enabledRotation;
    private boolean enabledTouch;
    private boolean enabledTranslation;
    private int moveType;
    private float rotation;
    private float scale;
    private float spacing;
    private float translationX;
    private float translationY;

    public ScaleTextureView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ScaleTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.scale = 1.0f;
        this.enabledTouch = true;
        this.enabledRotation = true;
        this.enabledTranslation = true;
        setClickable(true);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabledTouch) {
            switch (event.getAction() & 255) {
                case 0:
                    this.moveType = 1;
                    this.actionX = event.getRawX();
                    this.actionY = event.getRawY();
                    break;
                case 1:
                case 6:
                    this.moveType = 0;
                    break;
                case 2:
                    int i = this.moveType;
                    if (i != 1) {
                        if (i == 2) {
                            float spacing2 = (this.scale * getSpacing(event)) / this.spacing;
                            this.scale = spacing2;
                            setScaleX(spacing2);
                            setScaleY(this.scale);
                            if (this.enabledRotation) {
                                float degree2 = (this.rotation + getDegree(event)) - this.degree;
                                this.rotation = degree2;
                                if (degree2 > 360.0f) {
                                    this.rotation = degree2 - 360.0f;
                                }
                                float f = this.rotation;
                                if (f < -360.0f) {
                                    this.rotation = f + 360.0f;
                                }
                                setRotation(this.rotation);
                                break;
                            }
                        }
                    } else if (this.enabledTranslation) {
                        this.translationX = (this.translationX + event.getRawX()) - this.actionX;
                        this.translationY = (this.translationY + event.getRawY()) - this.actionY;
                        setTranslationX(this.translationX);
                        setTranslationY(this.translationY);
                        this.actionX = event.getRawX();
                        this.actionY = event.getRawY();
                        break;
                    }
                    break;
                case 5:
                    this.moveType = 2;
                    this.spacing = getSpacing(event);
                    this.degree = getDegree(event);
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private float getSpacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private float getDegree(MotionEvent event) {
        return (float) Math.toDegrees(Math.atan2((double) (event.getY(0) - event.getY(1)), (double) (event.getX(0) - event.getX(1))));
    }

    public void setEnabledTouch(boolean enabled) {
        this.enabledTouch = enabled;
    }

    public void setEnabledRotation(boolean enabled) {
        this.enabledRotation = enabled;
    }

    public void setEnabledTranslation(boolean enabled) {
        this.enabledTranslation = enabled;
    }

    public void reset(boolean saveEnabled) {
        this.translationX = 0.0f;
        this.translationY = 0.0f;
        this.scale = 1.0f;
        this.rotation = 0.0f;
        this.actionX = 0.0f;
        this.actionY = 0.0f;
        this.spacing = 0.0f;
        this.degree = 0.0f;
        this.moveType = 0;
        if (!saveEnabled) {
            this.enabledTouch = true;
            this.enabledRotation = true;
            this.enabledTranslation = true;
        }
        setScaleX(1.0f);
        setScaleY(1.0f);
        setRotation(0.0f);
        setTranslationX(0.0f);
        setTranslationY(0.0f);
    }
}
