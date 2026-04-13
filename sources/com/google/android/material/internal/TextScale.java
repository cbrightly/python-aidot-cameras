package com.google.android.material.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;
import java.util.Map;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class TextScale extends Transition {
    private static final String PROPNAME_SCALE = "android:textscale:scale";

    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(@NonNull TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view instanceof TextView) {
            transitionValues.values.put(PROPNAME_SCALE, Float.valueOf(((TextView) view).getScaleX()));
        }
    }

    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        if (!(startValues == null || endValues == null || !(startValues.view instanceof TextView))) {
            View view = endValues.view;
            if (view instanceof TextView) {
                final TextView view2 = (TextView) view;
                Map<String, Object> startVals = startValues.values;
                Map<String, Object> endVals = endValues.values;
                float f = 1.0f;
                float startSize = startVals.get(PROPNAME_SCALE) != null ? ((Float) startVals.get(PROPNAME_SCALE)).floatValue() : 1.0f;
                if (endVals.get(PROPNAME_SCALE) != null) {
                    f = ((Float) endVals.get(PROPNAME_SCALE)).floatValue();
                }
                float endSize = f;
                if (startSize == endSize) {
                    return null;
                }
                ValueAnimator animator = ValueAnimator.ofFloat(new float[]{startSize, endSize});
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                        float animatedValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        view2.setScaleX(animatedValue);
                        view2.setScaleY(animatedValue);
                    }
                });
                return animator;
            }
        }
        return null;
    }
}
