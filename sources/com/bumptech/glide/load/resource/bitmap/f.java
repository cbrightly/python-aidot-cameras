package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.b;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.m;
import com.bumptech.glide.util.j;

/* compiled from: BitmapTransformation */
public abstract class f implements m<Bitmap> {
    /* access modifiers changed from: protected */
    public abstract Bitmap a(@NonNull e eVar, @NonNull Bitmap bitmap, int i, int i2);

    @NonNull
    public final t<Bitmap> transform(@NonNull Context context, @NonNull t<Bitmap> resource, int outWidth, int outHeight) {
        if (j.t(outWidth, outHeight)) {
            e bitmapPool = b.c(context).f();
            Bitmap toTransform = resource.get();
            Bitmap transformed = a(bitmapPool, toTransform, outWidth == Integer.MIN_VALUE ? toTransform.getWidth() : outWidth, outHeight == Integer.MIN_VALUE ? toTransform.getHeight() : outHeight);
            if (toTransform.equals(transformed)) {
                return resource;
            }
            return e.c(transformed, bitmapPool);
        }
        throw new IllegalArgumentException("Cannot apply transformation on width: " + outWidth + " or height: " + outHeight + " less than or equal to zero and not Target.SIZE_ORIGINAL");
    }
}
