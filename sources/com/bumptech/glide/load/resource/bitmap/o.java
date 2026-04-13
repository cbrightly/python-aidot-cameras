package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import com.bumptech.glide.b;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.m;
import java.security.MessageDigest;

/* compiled from: DrawableTransformation */
public class o implements m<Drawable> {
    private final m<Bitmap> b;
    private final boolean c;

    public o(m<Bitmap> wrapped, boolean isRequired) {
        this.b = wrapped;
        this.c = isRequired;
    }

    public m<BitmapDrawable> a() {
        return this;
    }

    @NonNull
    public t<Drawable> transform(@NonNull Context context, @NonNull t<Drawable> resource, int outWidth, int outHeight) {
        e bitmapPool = b.c(context).f();
        Drawable drawable = resource.get();
        Resource<Bitmap> bitmapResourceToTransform = n.a(bitmapPool, drawable, outWidth, outHeight);
        if (bitmapResourceToTransform != null) {
            Resource<Bitmap> transformedBitmapResource = this.b.transform(context, bitmapResourceToTransform, outWidth, outHeight);
            if (!transformedBitmapResource.equals(bitmapResourceToTransform)) {
                return b(context, transformedBitmapResource);
            }
            transformedBitmapResource.recycle();
            return resource;
        } else if (!this.c) {
            return resource;
        } else {
            throw new IllegalArgumentException("Unable to convert " + drawable + " to a Bitmap");
        }
    }

    private t<Drawable> b(Context context, t<Bitmap> transformed) {
        return u.c(context.getResources(), transformed);
    }

    public boolean equals(Object o) {
        if (o instanceof o) {
            return this.b.equals(((o) o).b);
        }
        return false;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.b.updateDiskCacheKey(messageDigest);
    }
}
