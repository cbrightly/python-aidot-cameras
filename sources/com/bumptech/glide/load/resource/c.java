package com.bumptech.glide.load.resource;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.m;
import java.security.MessageDigest;

/* compiled from: UnitTransformation */
public final class c<T> implements m<T> {
    private static final m<?> b = new c();

    @NonNull
    public static <T> c<T> a() {
        return (c) b;
    }

    private c() {
    }

    @NonNull
    public t<T> transform(@NonNull Context context, @NonNull t<T> resource, int outWidth, int outHeight) {
        return resource;
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
