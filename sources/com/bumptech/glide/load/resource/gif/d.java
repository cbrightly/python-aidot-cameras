package com.bumptech.glide.load.resource.gif;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.resource.drawable.b;

/* compiled from: GifDrawableResource */
public class d extends b<GifDrawable> implements p {
    public d(GifDrawable drawable) {
        super(drawable);
    }

    @NonNull
    public Class<GifDrawable> a() {
        return GifDrawable.class;
    }

    public int getSize() {
        return ((GifDrawable) this.c).i();
    }

    public void recycle() {
        ((GifDrawable) this.c).stop();
        ((GifDrawable) this.c).k();
    }

    public void initialize() {
        ((GifDrawable) this.c).e().prepareToDraw();
    }
}
