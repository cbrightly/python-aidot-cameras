package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.f;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.j;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: RoundedCorners */
public final class x extends f {
    private static final byte[] b = "com.bumptech.glide.load.resource.bitmap.RoundedCorners".getBytes(f.a);
    private final int c;

    public x(int roundingRadius) {
        i.a(roundingRadius > 0, "roundingRadius must be greater than 0.");
        this.c = roundingRadius;
    }

    /* access modifiers changed from: protected */
    public Bitmap a(@NonNull e pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return z.o(pool, toTransform, this.c);
    }

    public boolean equals(Object o) {
        if (!(o instanceof x) || this.c != ((x) o).c) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return j.n("com.bumptech.glide.load.resource.bitmap.RoundedCorners".hashCode(), j.m(this.c));
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(b);
        messageDigest.update(ByteBuffer.allocate(4).putInt(this.c).array());
    }
}
