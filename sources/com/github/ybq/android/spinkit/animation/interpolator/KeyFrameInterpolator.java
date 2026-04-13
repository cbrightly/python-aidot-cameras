package com.github.ybq.android.spinkit.animation.interpolator;

import android.animation.TimeInterpolator;
import android.view.animation.Interpolator;

public class KeyFrameInterpolator implements Interpolator {
    private float[] fractions;
    private TimeInterpolator interpolator;

    public static KeyFrameInterpolator easeInOut(float... fractions2) {
        KeyFrameInterpolator interpolator2 = new KeyFrameInterpolator(Ease.inOut(), new float[0]);
        interpolator2.setFractions(fractions2);
        return interpolator2;
    }

    public static KeyFrameInterpolator pathInterpolator(float controlX1, float controlY1, float controlX2, float controlY2, float... fractions2) {
        KeyFrameInterpolator interpolator2 = new KeyFrameInterpolator(PathInterpolatorCompat.create(controlX1, controlY1, controlX2, controlY2), new float[0]);
        interpolator2.setFractions(fractions2);
        return interpolator2;
    }

    public KeyFrameInterpolator(TimeInterpolator interpolator2, float... fractions2) {
        this.interpolator = interpolator2;
        this.fractions = fractions2;
    }

    public void setFractions(float... fractions2) {
        this.fractions = fractions2;
    }

    public float getInterpolation(float input) {
        if (this.fractions.length > 1) {
            int i = 0;
            while (true) {
                float[] fArr = this.fractions;
                if (i >= fArr.length - 1) {
                    break;
                }
                float start = fArr[i];
                float end = fArr[i + 1];
                float duration = end - start;
                if (input < start || input > end) {
                    i++;
                } else {
                    return (this.interpolator.getInterpolation((input - start) / duration) * duration) + start;
                }
            }
        }
        return this.interpolator.getInterpolation(input);
    }
}
