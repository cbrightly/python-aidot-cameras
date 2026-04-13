package com.bumptech.glide.load.resource;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.util.i;

/* compiled from: SimpleResource */
public class b<T> implements t<T> {
    protected final T c;

    public b(@NonNull T data) {
        this.c = i.d(data);
    }

    @NonNull
    public Class<T> a() {
        return this.c.getClass();
    }

    @NonNull
    public final T get() {
        return this.c;
    }

    public final int getSize() {
        return 1;
    }

    public void recycle() {
    }
}
