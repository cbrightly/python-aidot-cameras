package com.bumptech.glide.signature;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.f;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

/* compiled from: ObjectKey */
public final class d implements f {
    private final Object b;

    public d(@NonNull Object object) {
        this.b = i.d(object);
    }

    public String toString() {
        return "ObjectKey{object=" + this.b + '}';
    }

    public boolean equals(Object o) {
        if (o instanceof d) {
            return this.b.equals(((d) o).b);
        }
        return false;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(this.b.toString().getBytes(f.a));
    }
}
