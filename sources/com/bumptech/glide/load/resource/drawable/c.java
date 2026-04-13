package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;

/* compiled from: NonOwnedDrawableResource */
public final class c extends b<Drawable> {
    @Nullable
    static t<Drawable> c(@Nullable Drawable drawable) {
        if (drawable != null) {
            return new c(drawable);
        }
        return null;
    }

    private c(Drawable drawable) {
        super(drawable);
    }

    @NonNull
    public Class<Drawable> a() {
        return this.c.getClass();
    }

    public int getSize() {
        return Math.max(1, this.c.getIntrinsicWidth() * this.c.getIntrinsicHeight() * 4);
    }

    public void recycle() {
    }
}
