package com.bumptech.glide.load.engine;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.data.d;
import com.bumptech.glide.load.f;

/* compiled from: DataFetcherGenerator */
public interface e {

    /* compiled from: DataFetcherGenerator */
    public interface a {
        void a(f fVar, Exception exc, d<?> dVar, com.bumptech.glide.load.a aVar);

        void c();

        void e(f fVar, @Nullable Object obj, d<?> dVar, com.bumptech.glide.load.a aVar, f fVar2);
    }

    boolean b();

    void cancel();
}
