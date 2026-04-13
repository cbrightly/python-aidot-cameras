package com.bumptech.glide.load;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.util.i;
import java.security.MessageDigest;

/* compiled from: Option */
public final class h<T> {
    private static final b<Object> a = new a();
    private final T b;
    private final b<T> c;
    private final String d;
    private volatile byte[] e;

    /* compiled from: Option */
    public interface b<T> {
        void a(@NonNull byte[] bArr, @NonNull T t, @NonNull MessageDigest messageDigest);
    }

    /* compiled from: Option */
    public class a implements b<Object> {
        a() {
        }

        public void a(@NonNull byte[] keyBytes, @NonNull Object value, @NonNull MessageDigest messageDigest) {
        }
    }

    @NonNull
    public static <T> h<T> e(@NonNull String key) {
        return new h<>(key, (Object) null, b());
    }

    @NonNull
    public static <T> h<T> f(@NonNull String key, @NonNull T defaultValue) {
        return new h<>(key, defaultValue, b());
    }

    @NonNull
    public static <T> h<T> a(@NonNull String key, @Nullable T defaultValue, @NonNull b<T> cacheKeyUpdater) {
        return new h<>(key, defaultValue, cacheKeyUpdater);
    }

    private h(@NonNull String key, @Nullable T defaultValue, @NonNull b<T> cacheKeyUpdater) {
        this.d = i.b(key);
        this.b = defaultValue;
        this.c = (b) i.d(cacheKeyUpdater);
    }

    @Nullable
    public T c() {
        return this.b;
    }

    public void g(@NonNull T value, @NonNull MessageDigest messageDigest) {
        this.c.a(d(), value, messageDigest);
    }

    @NonNull
    private byte[] d() {
        if (this.e == null) {
            this.e = this.d.getBytes(f.a);
        }
        return this.e;
    }

    public boolean equals(Object o) {
        if (o instanceof h) {
            return this.d.equals(((h) o).d);
        }
        return false;
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    @NonNull
    private static <T> b<T> b() {
        return a;
    }

    public String toString() {
        return "Option{key='" + this.d + '\'' + '}';
    }
}
