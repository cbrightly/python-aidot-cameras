package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.i;
import java.security.MessageDigest;
import java.util.Map;

/* compiled from: EngineKey */
public class m implements f {
    private final Object b;
    private final int c;
    private final int d;
    private final Class<?> e;
    private final Class<?> f;
    private final f g;
    private final Map<Class<?>, com.bumptech.glide.load.m<?>> h;
    private final i i;
    private int j;

    m(Object model, f signature, int width, int height, Map<Class<?>, com.bumptech.glide.load.m<?>> transformations, Class<?> resourceClass, Class<?> transcodeClass, i options) {
        this.b = com.bumptech.glide.util.i.d(model);
        this.g = (f) com.bumptech.glide.util.i.e(signature, "Signature must not be null");
        this.c = width;
        this.d = height;
        this.h = (Map) com.bumptech.glide.util.i.d(transformations);
        this.e = (Class) com.bumptech.glide.util.i.e(resourceClass, "Resource class must not be null");
        this.f = (Class) com.bumptech.glide.util.i.e(transcodeClass, "Transcode class must not be null");
        this.i = (i) com.bumptech.glide.util.i.d(options);
    }

    public boolean equals(Object o) {
        if (!(o instanceof m)) {
            return false;
        }
        m other = (m) o;
        if (!this.b.equals(other.b) || !this.g.equals(other.g) || this.d != other.d || this.c != other.c || !this.h.equals(other.h) || !this.e.equals(other.e) || !this.f.equals(other.f) || !this.i.equals(other.i)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (this.j == 0) {
            int hashCode = this.b.hashCode();
            this.j = hashCode;
            int hashCode2 = (hashCode * 31) + this.g.hashCode();
            this.j = hashCode2;
            int i2 = (hashCode2 * 31) + this.c;
            this.j = i2;
            int i3 = (i2 * 31) + this.d;
            this.j = i3;
            int hashCode3 = (i3 * 31) + this.h.hashCode();
            this.j = hashCode3;
            int hashCode4 = (hashCode3 * 31) + this.e.hashCode();
            this.j = hashCode4;
            int hashCode5 = (hashCode4 * 31) + this.f.hashCode();
            this.j = hashCode5;
            this.j = (hashCode5 * 31) + this.i.hashCode();
        }
        return this.j;
    }

    public String toString() {
        return "EngineKey{model=" + this.b + ", width=" + this.c + ", height=" + this.d + ", resourceClass=" + this.e + ", transcodeClass=" + this.f + ", signature=" + this.g + ", hashCode=" + this.j + ", transformations=" + this.h + ", options=" + this.i + '}';
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        throw new UnsupportedOperationException();
    }
}
