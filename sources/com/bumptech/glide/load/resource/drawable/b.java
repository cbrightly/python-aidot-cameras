package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.util.i;

/* compiled from: DrawableResource */
public abstract class b<T extends Drawable> implements t<T>, p {
    protected final T c;

    public b(T drawable) {
        this.c = (Drawable) i.d(drawable);
    }

    @NonNull
    /* renamed from: b */
    public final T get() {
        Drawable.ConstantState state = this.c.getConstantState();
        if (state == null) {
            return this.c;
        }
        return state.newDrawable();
    }

    public void initialize() {
        T t = this.c;
        if (t instanceof BitmapDrawable) {
            ((BitmapDrawable) t).getBitmap().prepareToDraw();
        } else if (t instanceof GifDrawable) {
            ((GifDrawable) t).e().prepareToDraw();
        }
    }
}
