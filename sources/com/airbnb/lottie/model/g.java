package com.airbnb.lottie.model;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.collection.LruCache;
import com.airbnb.lottie.c0;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: LottieCompositionCache */
public class g {
    private static final g a = new g();
    private final LruCache<String, c0> b = new LruCache<>(20);

    public static g b() {
        return a;
    }

    @VisibleForTesting
    g() {
    }

    @Nullable
    public c0 a(@Nullable String cacheKey) {
        if (cacheKey == null) {
            return null;
        }
        return this.b.get(cacheKey);
    }

    public void c(@Nullable String cacheKey, c0 composition) {
        if (cacheKey != null) {
            this.b.put(cacheKey, composition);
        }
    }
}
