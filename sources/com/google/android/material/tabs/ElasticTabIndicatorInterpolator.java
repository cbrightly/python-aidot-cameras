package com.google.android.material.tabs;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import com.google.android.material.animation.AnimationUtils;

public class ElasticTabIndicatorInterpolator extends TabIndicatorInterpolator {
    ElasticTabIndicatorInterpolator() {
    }

    private static float decInterp(@FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        return (float) Math.sin((((double) fraction) * 3.141592653589793d) / 2.0d);
    }

    private static float accInterp(@FloatRange(from = 0.0d, to = 1.0d) float fraction) {
        return (float) (1.0d - Math.cos((((double) fraction) * 3.141592653589793d) / 2.0d));
    }

    /* access modifiers changed from: package-private */
    public void setIndicatorBoundsForOffset(TabLayout tabLayout, View startTitle, View endTitle, float offset, @NonNull Drawable indicator) {
        float rightFraction;
        float leftFraction;
        RectF startIndicator = TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, startTitle);
        RectF endIndicator = TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, endTitle);
        if (startIndicator.left < endIndicator.left) {
            leftFraction = accInterp(offset);
            rightFraction = decInterp(offset);
        } else {
            leftFraction = decInterp(offset);
            rightFraction = accInterp(offset);
        }
        indicator.setBounds(AnimationUtils.lerp((int) startIndicator.left, (int) endIndicator.left, leftFraction), indicator.getBounds().top, AnimationUtils.lerp((int) startIndicator.right, (int) endIndicator.right, rightFraction), indicator.getBounds().bottom);
    }
}
