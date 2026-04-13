package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.b;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.m;
import com.bumptech.glide.util.j;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/* compiled from: ResourceCacheKey */
public final class v implements f {
    private static final com.bumptech.glide.util.f<Class<?>, byte[]> b = new com.bumptech.glide.util.f<>(50);
    private final b c;
    private final f d;
    private final f e;
    private final int f;
    private final int g;
    private final Class<?> h;
    private final i i;
    private final m<?> j;

    v(b arrayPool, f sourceKey, f signature, int width, int height, m<?> appliedTransformation, Class<?> decodedResourceClass, i options) {
        this.c = arrayPool;
        this.d = sourceKey;
        this.e = signature;
        this.f = width;
        this.g = height;
        this.j = appliedTransformation;
        this.h = decodedResourceClass;
        this.i = options;
    }

    public boolean equals(Object o) {
        if (!(o instanceof v)) {
            return false;
        }
        v other = (v) o;
        if (this.g != other.g || this.f != other.f || !j.c(this.j, other.j) || !this.h.equals(other.h) || !this.d.equals(other.d) || !this.e.equals(other.e) || !this.i.equals(other.i)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = (((((this.d.hashCode() * 31) + this.e.hashCode()) * 31) + this.f) * 31) + this.g;
        m<?> mVar = this.j;
        if (mVar != null) {
            result = (result * 31) + mVar.hashCode();
        }
        return (((result * 31) + this.h.hashCode()) * 31) + this.i.hashCode();
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        byte[] dimensions = (byte[]) this.c.f(8, byte[].class);
        ByteBuffer.wrap(dimensions).putInt(this.f).putInt(this.g).array();
        this.e.updateDiskCacheKey(messageDigest);
        this.d.updateDiskCacheKey(messageDigest);
        messageDigest.update(dimensions);
        m<?> mVar = this.j;
        if (mVar != null) {
            mVar.updateDiskCacheKey(messageDigest);
        }
        this.i.updateDiskCacheKey(messageDigest);
        messageDigest.update(a());
        this.c.put(dimensions);
    }

    private byte[] a() {
        com.bumptech.glide.util.f<Class<?>, byte[]> fVar = b;
        byte[] result = fVar.g(this.h);
        if (result != null) {
            return result;
        }
        byte[] result2 = this.h.getName().getBytes(f.a);
        fVar.k(this.h, result2);
        return result2;
    }

    public String toString() {
        return "ResourceCacheKey{sourceKey=" + this.d + ", signature=" + this.e + ", width=" + this.f + ", height=" + this.g + ", decodedResourceClass=" + this.h + ", transformation='" + this.j + '\'' + ", options=" + this.i + '}';
    }
}
