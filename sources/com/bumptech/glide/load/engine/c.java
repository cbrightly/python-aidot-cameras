package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.f;
import java.security.MessageDigest;

/* compiled from: DataCacheKey */
public final class c implements f {
    private final f b;
    private final f c;

    c(f sourceKey, f signature) {
        this.b = sourceKey;
        this.c = signature;
    }

    public boolean equals(Object o) {
        if (!(o instanceof c)) {
            return false;
        }
        c other = (c) o;
        if (!this.b.equals(other.b) || !this.c.equals(other.c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.b.hashCode() * 31) + this.c.hashCode();
    }

    public String toString() {
        return "DataCacheKey{sourceKey=" + this.b + ", signature=" + this.c + '}';
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        this.b.updateDiskCacheKey(messageDigest);
        this.c.updateDiskCacheKey(messageDigest);
    }
}
