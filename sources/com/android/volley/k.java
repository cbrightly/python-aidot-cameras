package com.android.volley;

import androidx.annotation.Nullable;
import com.android.volley.a;

/* compiled from: Response */
public class k<T> {
    @Nullable
    public final T a;
    @Nullable
    public final a.C0017a b;
    @Nullable
    public final VolleyError c;
    public boolean d;

    /* compiled from: Response */
    public interface a {
        void onErrorResponse(VolleyError volleyError);
    }

    /* compiled from: Response */
    public interface b<T> {
        void onResponse(T t);
    }

    public static <T> k<T> c(@Nullable T result, @Nullable a.C0017a cacheEntry) {
        return new k<>(result, cacheEntry);
    }

    public static <T> k<T> a(VolleyError error) {
        return new k<>(error);
    }

    public boolean b() {
        return this.c == null;
    }

    private k(@Nullable T result, @Nullable a.C0017a cacheEntry) {
        this.d = false;
        this.a = result;
        this.b = cacheEntry;
        this.c = null;
    }

    private k(VolleyError error) {
        this.d = false;
        this.a = null;
        this.b = null;
        this.c = error;
    }
}
