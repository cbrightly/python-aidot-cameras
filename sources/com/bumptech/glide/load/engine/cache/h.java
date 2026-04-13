package com.bumptech.glide.load.engine.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.t;
import com.bumptech.glide.load.f;

/* compiled from: MemoryCache */
public interface h {

    /* compiled from: MemoryCache */
    public interface a {
        void a(@NonNull t<?> tVar);
    }

    void a(int i);

    @Nullable
    t<?> b(@NonNull f fVar, @Nullable t<?> tVar);

    @Nullable
    t<?> c(@NonNull f fVar);

    void d();

    void e(@NonNull a aVar);
}
