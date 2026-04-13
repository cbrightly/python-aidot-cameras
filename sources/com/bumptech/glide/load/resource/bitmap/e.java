package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.j;

/* compiled from: BitmapResource */
public class e implements t<Bitmap>, p {
    private final Bitmap c;
    private final com.bumptech.glide.load.engine.bitmap_recycle.e d;

    @Nullable
    public static e c(@Nullable Bitmap bitmap, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.e bitmapPool) {
        if (bitmap == null) {
            return null;
        }
        return new e(bitmap, bitmapPool);
    }

    public e(@NonNull Bitmap bitmap, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.e bitmapPool) {
        this.c = (Bitmap) i.e(bitmap, "Bitmap must not be null");
        this.d = (com.bumptech.glide.load.engine.bitmap_recycle.e) i.e(bitmapPool, "BitmapPool must not be null");
    }

    @NonNull
    public Class<Bitmap> a() {
        return Bitmap.class;
    }

    @NonNull
    /* renamed from: b */
    public Bitmap get() {
        return this.c;
    }

    public int getSize() {
        return j.g(this.c);
    }

    public void recycle() {
        this.d.b(this.c);
    }

    public void initialize() {
        this.c.prepareToDraw();
    }
}
