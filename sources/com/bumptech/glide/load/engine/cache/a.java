package com.bumptech.glide.load.engine.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.f;
import java.io.File;

/* compiled from: DiskCache */
public interface a {

    /* renamed from: com.bumptech.glide.load.engine.cache.a$a  reason: collision with other inner class name */
    /* compiled from: DiskCache */
    public interface C0027a {
        @Nullable
        a build();
    }

    /* compiled from: DiskCache */
    public interface b {
        boolean d(@NonNull File file);
    }

    void a(f fVar, b bVar);

    @Nullable
    File b(f fVar);
}
