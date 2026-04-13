package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.b;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.m;
import java.security.MessageDigest;

/* compiled from: GifDrawableTransformation */
public class e implements m<GifDrawable> {
    private final m<Bitmap> b;

    /* JADX WARNING: type inference failed for: r2v0, types: [com.bumptech.glide.load.m<android.graphics.Bitmap>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public e(com.bumptech.glide.load.m<android.graphics.Bitmap> r2) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = com.bumptech.glide.util.i.d(r2)
            com.bumptech.glide.load.m r0 = (com.bumptech.glide.load.m) r0
            r1.b = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.gif.e.<init>(com.bumptech.glide.load.m):void");
    }

    @NonNull
    public t<GifDrawable> transform(@NonNull Context context, @NonNull t<GifDrawable> resource, int outWidth, int outHeight) {
        GifDrawable drawable = resource.get();
        Resource<Bitmap> bitmapResource = new com.bumptech.glide.load.resource.bitmap.e(drawable.e(), b.c(context).f());
        Resource<Bitmap> transformed = this.b.transform(context, bitmapResource, outWidth, outHeight);
        if (!bitmapResource.equals(transformed)) {
            bitmapResource.recycle();
        }
        drawable.m(this.b, transformed.get());
        return resource;
    }

    public boolean equals(Object o) {
        if (o instanceof e) {
            return this.b.equals(((e) o).b);
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
