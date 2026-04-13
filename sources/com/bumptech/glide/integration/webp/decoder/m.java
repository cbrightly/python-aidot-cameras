package com.bumptech.glide.integration.webp.decoder;

import android.content.Context;
import android.graphics.Bitmap;
import com.bumptech.glide.b;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.resource.bitmap.e;
import java.security.MessageDigest;

/* compiled from: WebpDrawableTransformation */
public class m implements com.bumptech.glide.load.m<WebpDrawable> {
    private final com.bumptech.glide.load.m<Bitmap> b;

    /* JADX WARNING: type inference failed for: r2v0, types: [com.bumptech.glide.load.m<android.graphics.Bitmap>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public m(com.bumptech.glide.load.m<android.graphics.Bitmap> r2) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.Object r0 = com.bumptech.glide.util.i.d(r2)
            com.bumptech.glide.load.m r0 = (com.bumptech.glide.load.m) r0
            r1.b = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.integration.webp.decoder.m.<init>(com.bumptech.glide.load.m):void");
    }

    public t<WebpDrawable> transform(Context context, t<WebpDrawable> resource, int outWidth, int outHeight) {
        WebpDrawable drawable = resource.get();
        Resource<Bitmap> bitmapResource = new e(drawable.e(), b.c(context).f());
        Resource<Bitmap> transformed = this.b.transform(context, bitmapResource, outWidth, outHeight);
        if (!bitmapResource.equals(transformed)) {
            bitmapResource.recycle();
        }
        drawable.n(this.b, transformed.get());
        return resource;
    }

    public boolean equals(Object o) {
        if (o instanceof m) {
            return this.b.equals(((m) o).b);
        }
        return false;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        this.b.updateDiskCacheKey(messageDigest);
    }
}
