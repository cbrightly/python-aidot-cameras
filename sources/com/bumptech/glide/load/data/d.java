package com.bumptech.glide.load.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.g;

/* compiled from: DataFetcher */
public interface d<T> {

    /* compiled from: DataFetcher */
    public interface a<T> {
        void c(@NonNull Exception exc);

        void e(@Nullable T t);
    }

    @NonNull
    Class<T> a();

    void b();

    void cancel();

    void d(@NonNull g gVar, @NonNull a<? super T> aVar);

    @NonNull
    com.bumptech.glide.load.a getDataSource();
}
