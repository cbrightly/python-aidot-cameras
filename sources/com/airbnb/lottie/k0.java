package com.airbnb.lottie;

import androidx.annotation.Nullable;
import java.util.Arrays;

/* compiled from: LottieResult */
public final class k0<V> {
    @Nullable
    private final V a;
    @Nullable
    private final Throwable b;

    public k0(V value) {
        this.a = value;
        this.b = null;
    }

    public k0(Throwable exception) {
        this.b = exception;
        this.a = null;
    }

    @Nullable
    public V b() {
        return this.a;
    }

    @Nullable
    public Throwable a() {
        return this.b;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof k0)) {
            return false;
        }
        LottieResult<?> that = (k0) o;
        if (b() != null && b().equals(that.b())) {
            return true;
        }
        if (a() == null || that.a() == null) {
            return false;
        }
        return a().toString().equals(a().toString());
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{b(), a()});
    }
}
