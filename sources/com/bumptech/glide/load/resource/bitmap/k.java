package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.f;
import java.security.MessageDigest;

/* compiled from: CircleCrop */
public class k extends f {
    private static final byte[] b = "com.bumptech.glide.load.resource.bitmap.CircleCrop.1".getBytes(f.a);

    /* access modifiers changed from: protected */
    public Bitmap a(@NonNull e pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return z.d(pool, toTransform, outWidth, outHeight);
    }

    public boolean equals(Object o) {
        return o instanceof k;
    }

    public int hashCode() {
        return "com.bumptech.glide.load.resource.bitmap.CircleCrop.1".hashCode();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(b);
    }
}
