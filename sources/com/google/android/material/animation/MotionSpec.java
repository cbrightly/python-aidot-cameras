package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.Property;
import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.collection.SimpleArrayMap;
import java.util.ArrayList;
import java.util.List;

public class MotionSpec {
    private static final String TAG = "MotionSpec";
    private final SimpleArrayMap<String, PropertyValuesHolder[]> propertyValues = new SimpleArrayMap<>();
    private final SimpleArrayMap<String, MotionTiming> timings = new SimpleArrayMap<>();

    public boolean hasTiming(String name) {
        return this.timings.get(name) != null;
    }

    public MotionTiming getTiming(String name) {
        if (hasTiming(name)) {
            return this.timings.get(name);
        }
        throw new IllegalArgumentException();
    }

    public void setTiming(String name, @Nullable MotionTiming timing) {
        this.timings.put(name, timing);
    }

    public boolean hasPropertyValues(String name) {
        return this.propertyValues.get(name) != null;
    }

    @NonNull
    public PropertyValuesHolder[] getPropertyValues(String name) {
        if (hasPropertyValues(name)) {
            return clonePropertyValuesHolder(this.propertyValues.get(name));
        }
        throw new IllegalArgumentException();
    }

    public void setPropertyValues(String name, PropertyValuesHolder[] values) {
        this.propertyValues.put(name, values);
    }

    @NonNull
    private PropertyValuesHolder[] clonePropertyValuesHolder(@NonNull PropertyValuesHolder[] values) {
        PropertyValuesHolder[] ret = new PropertyValuesHolder[values.length];
        for (int i = 0; i < values.length; i++) {
            ret[i] = values[i].clone();
        }
        return ret;
    }

    @NonNull
    public <T> ObjectAnimator getAnimator(@NonNull String name, @NonNull T target, @NonNull Property<T, ?> property) {
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(target, getPropertyValues(name));
        animator.setProperty(property);
        getTiming(name).apply(animator);
        return animator;
    }

    public long getTotalDuration() {
        long duration = 0;
        int count = this.timings.size();
        for (int i = 0; i < count; i++) {
            MotionTiming timing = this.timings.valueAt(i);
            duration = Math.max(duration, timing.getDelay() + timing.getDuration());
        }
        return duration;
    }

    @Nullable
    public static MotionSpec createFromAttribute(@NonNull Context context, @NonNull TypedArray attributes, @StyleableRes int index) {
        int resourceId;
        if (!attributes.hasValue(index) || (resourceId = attributes.getResourceId(index, 0)) == 0) {
            return null;
        }
        return createFromResource(context, resourceId);
    }

    @Nullable
    public static MotionSpec createFromResource(@NonNull Context context, @AnimatorRes int id) {
        try {
            Animator animator = AnimatorInflater.loadAnimator(context, id);
            if (animator instanceof AnimatorSet) {
                return createSpecFromAnimators(((AnimatorSet) animator).getChildAnimations());
            }
            if (animator == null) {
                return null;
            }
            List<Animator> animators = new ArrayList<>();
            animators.add(animator);
            return createSpecFromAnimators(animators);
        } catch (Exception e) {
            Log.w(TAG, "Can't load animation resource ID #0x" + Integer.toHexString(id), e);
            return null;
        }
    }

    @NonNull
    private static MotionSpec createSpecFromAnimators(@NonNull List<Animator> animators) {
        MotionSpec spec = new MotionSpec();
        int count = animators.size();
        for (int i = 0; i < count; i++) {
            addInfoFromAnimator(spec, animators.get(i));
        }
        return spec;
    }

    private static void addInfoFromAnimator(@NonNull MotionSpec spec, Animator animator) {
        if (animator instanceof ObjectAnimator) {
            ObjectAnimator anim = (ObjectAnimator) animator;
            spec.setPropertyValues(anim.getPropertyName(), anim.getValues());
            spec.setTiming(anim.getPropertyName(), MotionTiming.createFromAnimator(anim));
            return;
        }
        throw new IllegalArgumentException("Animator must be an ObjectAnimator: " + animator);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MotionSpec)) {
            return false;
        }
        return this.timings.equals(((MotionSpec) o).timings);
    }

    public int hashCode() {
        return this.timings.hashCode();
    }

    @NonNull
    public String toString() {
        return 10 + getClass().getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " timings: " + this.timings + "}\n";
    }
}
