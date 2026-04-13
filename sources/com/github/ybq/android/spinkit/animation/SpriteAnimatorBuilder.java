package com.github.ybq.android.spinkit.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Log;
import android.util.Property;
import android.view.animation.Interpolator;
import com.github.ybq.android.spinkit.animation.interpolator.KeyFrameInterpolator;
import com.github.ybq.android.spinkit.sprite.Sprite;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import meshsdk.cache.CacheHandler;

public class SpriteAnimatorBuilder {
    private static final String TAG = "SpriteAnimatorBuilder";
    private long duration = CacheHandler.delayTime;
    private Map<String, FrameData> fds = new HashMap();
    private Interpolator interpolator;
    private int repeatCount = -1;
    private Sprite sprite;
    private int startFrame = 0;

    public class FrameData<T> {
        float[] fractions;
        Property property;
        T[] values;

        public FrameData(float[] fractions2, Property property2, T[] values2) {
            this.fractions = fractions2;
            this.property = property2;
            this.values = values2;
        }
    }

    public class IntFrameData extends FrameData<Integer> {
        public IntFrameData(float[] fractions, Property property, Integer[] values) {
            super(fractions, property, values);
        }
    }

    public class FloatFrameData extends FrameData<Float> {
        public FloatFrameData(float[] fractions, Property property, Float[] values) {
            super(fractions, property, values);
        }
    }

    public SpriteAnimatorBuilder(Sprite sprite2) {
        this.sprite = sprite2;
    }

    public SpriteAnimatorBuilder scale(float[] fractions, Float... scale) {
        holder(fractions, (Property) Sprite.SCALE, scale);
        return this;
    }

    public SpriteAnimatorBuilder alpha(float[] fractions, Integer... alpha) {
        holder(fractions, (Property) Sprite.ALPHA, alpha);
        return this;
    }

    public SpriteAnimatorBuilder scaleX(float[] fractions, Float... scaleX) {
        holder(fractions, (Property) Sprite.SCALE, scaleX);
        return this;
    }

    public SpriteAnimatorBuilder scaleY(float[] fractions, Float... scaleY) {
        holder(fractions, (Property) Sprite.SCALE_Y, scaleY);
        return this;
    }

    public SpriteAnimatorBuilder rotateX(float[] fractions, Integer... rotateX) {
        holder(fractions, (Property) Sprite.ROTATE_X, rotateX);
        return this;
    }

    public SpriteAnimatorBuilder rotateY(float[] fractions, Integer... rotateY) {
        holder(fractions, (Property) Sprite.ROTATE_Y, rotateY);
        return this;
    }

    public SpriteAnimatorBuilder translateX(float[] fractions, Integer... translateX) {
        holder(fractions, (Property) Sprite.TRANSLATE_X, translateX);
        return this;
    }

    public SpriteAnimatorBuilder translateY(float[] fractions, Integer... translateY) {
        holder(fractions, (Property) Sprite.TRANSLATE_Y, translateY);
        return this;
    }

    public SpriteAnimatorBuilder rotate(float[] fractions, Integer... rotate) {
        holder(fractions, (Property) Sprite.ROTATE, rotate);
        return this;
    }

    public SpriteAnimatorBuilder translateXPercentage(float[] fractions, Float... translateXPercentage) {
        holder(fractions, (Property) Sprite.TRANSLATE_X_PERCENTAGE, translateXPercentage);
        return this;
    }

    public SpriteAnimatorBuilder translateYPercentage(float[] fractions, Float... translateYPercentage) {
        holder(fractions, (Property) Sprite.TRANSLATE_Y_PERCENTAGE, translateYPercentage);
        return this;
    }

    private void holder(float[] fractions, Property property, Float[] values) {
        ensurePair(fractions.length, values.length);
        this.fds.put(property.getName(), new FloatFrameData(fractions, property, values));
    }

    private void holder(float[] fractions, Property property, Integer[] values) {
        ensurePair(fractions.length, values.length);
        this.fds.put(property.getName(), new IntFrameData(fractions, property, values));
    }

    private void ensurePair(int fractionsLength, int valuesLength) {
        if (fractionsLength != valuesLength) {
            throw new IllegalStateException(String.format(Locale.getDefault(), "The fractions.length must equal values.length, fraction.length[%d], values.length[%d]", new Object[]{Integer.valueOf(fractionsLength), Integer.valueOf(valuesLength)}));
        }
    }

    public SpriteAnimatorBuilder interpolator(Interpolator interpolator2) {
        this.interpolator = interpolator2;
        return this;
    }

    public SpriteAnimatorBuilder easeInOut(float... fractions) {
        interpolator(KeyFrameInterpolator.easeInOut(fractions));
        return this;
    }

    public SpriteAnimatorBuilder duration(long duration2) {
        this.duration = duration2;
        return this;
    }

    public SpriteAnimatorBuilder repeatCount(int repeatCount2) {
        this.repeatCount = repeatCount2;
        return this;
    }

    public SpriteAnimatorBuilder startFrame(int startFrame2) {
        if (startFrame2 < 0) {
            Log.w(TAG, "startFrame should always be non-negative");
            startFrame2 = 0;
        }
        this.startFrame = startFrame2;
        return this;
    }

    public ObjectAnimator build() {
        PropertyValuesHolder[] holders = new PropertyValuesHolder[this.fds.size()];
        int i = 0;
        for (Map.Entry<String, FrameData> fd : this.fds.entrySet()) {
            FrameData data = fd.getValue();
            Keyframe[] keyframes = new Keyframe[data.fractions.length];
            float[] fractions = data.fractions;
            float startF = fractions[this.startFrame];
            int j = this.startFrame;
            while (true) {
                int i2 = this.startFrame;
                T[] tArr = data.values;
                if (j >= tArr.length + i2) {
                    break;
                }
                int key = j - i2;
                int vk = j % tArr.length;
                float fraction = fractions[vk] - startF;
                if (fraction < 0.0f) {
                    fraction += fractions[fractions.length - 1];
                }
                if (data instanceof IntFrameData) {
                    keyframes[key] = Keyframe.ofInt(fraction, ((Integer) tArr[vk]).intValue());
                } else if (data instanceof FloatFrameData) {
                    keyframes[key] = Keyframe.ofFloat(fraction, ((Float) tArr[vk]).floatValue());
                } else {
                    keyframes[key] = Keyframe.ofObject(fraction, tArr[vk]);
                }
                j++;
            }
            holders[i] = PropertyValuesHolder.ofKeyframe(data.property, keyframes);
            i++;
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this.sprite, holders);
        animator.setDuration(this.duration);
        animator.setRepeatCount(this.repeatCount);
        animator.setInterpolator(this.interpolator);
        return animator;
    }
}
