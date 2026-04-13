package com.google.android.material.animation;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Property;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.WeakHashMap;

public class DrawableAlphaProperty extends Property<Drawable, Integer> {
    public static final Property<Drawable, Integer> DRAWABLE_ALPHA_COMPAT = new DrawableAlphaProperty();
    private final WeakHashMap<Drawable, Integer> alphaCache = new WeakHashMap<>();

    private DrawableAlphaProperty() {
        super(Integer.class, "drawableAlphaCompat");
    }

    @Nullable
    public Integer get(@NonNull Drawable object) {
        if (Build.VERSION.SDK_INT >= 19) {
            return Integer.valueOf(object.getAlpha());
        }
        if (this.alphaCache.containsKey(object)) {
            return this.alphaCache.get(object);
        }
        return 255;
    }

    public void set(@NonNull Drawable object, @NonNull Integer value) {
        if (Build.VERSION.SDK_INT < 19) {
            this.alphaCache.put(object, value);
        }
        object.setAlpha(value.intValue());
    }
}
