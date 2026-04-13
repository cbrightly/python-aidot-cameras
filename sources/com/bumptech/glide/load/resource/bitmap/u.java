package com.bumptech.glide.load.resource.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.util.i;

/* compiled from: LazyBitmapDrawableResource */
public final class u implements t<BitmapDrawable>, p {
    private final Resources c;
    private final t<Bitmap> d;

    @Nullable
    public static t<BitmapDrawable> c(@NonNull Resources resources, @Nullable t<Bitmap> bitmapResource) {
        if (bitmapResource == null) {
            return null;
        }
        return new u(resources, bitmapResource);
    }

    private u(@NonNull Resources resources, @NonNull t<Bitmap> bitmapResource) {
        this.c = (Resources) i.d(resources);
        this.d = (t) i.d(bitmapResource);
    }

    @NonNull
    public Class<BitmapDrawable> a() {
        return BitmapDrawable.class;
    }

    @NonNull
    /* renamed from: b */
    public BitmapDrawable get() {
        return new BitmapDrawable(this.c, this.d.get());
    }

    public int getSize() {
        return this.d.getSize();
    }

    public void recycle() {
        this.d.recycle();
    }

    public void initialize() {
        t<Bitmap> tVar = this.d;
        if (tVar instanceof p) {
            ((p) tVar).initialize();
        }
    }
}
