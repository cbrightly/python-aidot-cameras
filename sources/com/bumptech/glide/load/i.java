package com.bumptech.glide.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.bumptech.glide.util.CachedHashCodeArrayMap;
import java.security.MessageDigest;

/* compiled from: Options */
public final class i implements f {
    private final ArrayMap<h<?>, Object> b = new CachedHashCodeArrayMap();

    public void b(@NonNull i other) {
        this.b.putAll(other.b);
    }

    @NonNull
    public <T> i c(@NonNull h<T> option, @NonNull T value) {
        this.b.put(option, value);
        return this;
    }

    @Nullable
    public <T> T a(@NonNull h<T> option) {
        return this.b.containsKey(option) ? this.b.get(option) : option.c();
    }

    public boolean equals(Object o) {
        if (o instanceof i) {
            return this.b.equals(((i) o).b);
        }
        return false;
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        for (int i = 0; i < this.b.size(); i++) {
            d((h) this.b.keyAt(i), this.b.valueAt(i), messageDigest);
        }
    }

    public String toString() {
        return "Options{values=" + this.b + '}';
    }

    private static <T> void d(@NonNull h<T> option, @NonNull Object value, @NonNull MessageDigest md) {
        option.g(value, md);
    }
}
