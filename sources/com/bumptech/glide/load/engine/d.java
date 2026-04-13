package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.cache.a;
import com.bumptech.glide.load.i;
import java.io.File;

/* compiled from: DataCacheWriter */
public class d<DataType> implements a.b {
    private final com.bumptech.glide.load.d<DataType> a;
    private final DataType b;
    private final i c;

    d(com.bumptech.glide.load.d<DataType> encoder, DataType data, i options) {
        this.a = encoder;
        this.b = data;
        this.c = options;
    }

    public boolean d(@NonNull File file) {
        return this.a.a(this.b, file, this.c);
    }
}
