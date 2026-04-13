package com.bumptech.glide.provider;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.util.Pools;
import com.bumptech.glide.load.engine.LoadPath;
import com.bumptech.glide.load.engine.h;
import com.bumptech.glide.load.engine.r;
import com.bumptech.glide.load.resource.transcode.g;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: LoadPathCache */
public class c {
    private static final r<?, ?, ?> a = new r(Object.class, Object.class, Object.class, Collections.singletonList(new h(Object.class, Object.class, Object.class, Collections.emptyList(), new g(), (Pools.Pool<List<Throwable>>) null)), (Pools.Pool<List<Throwable>>) null);
    private final ArrayMap<com.bumptech.glide.util.h, r<?, ?, ?>> b = new ArrayMap<>();
    private final AtomicReference<com.bumptech.glide.util.h> c = new AtomicReference<>();

    public boolean c(@Nullable r<?, ?, ?> path) {
        return a.equals(path);
    }

    @Nullable
    public <Data, TResource, Transcode> r<Data, TResource, Transcode> a(Class<Data> dataClass, Class<TResource> resourceClass, Class<Transcode> transcodeClass) {
        LoadPath<?, ?, ?> result;
        com.bumptech.glide.util.h key = b(dataClass, resourceClass, transcodeClass);
        synchronized (this.b) {
            result = (r) this.b.get(key);
        }
        this.c.set(key);
        return result;
    }

    public void d(Class<?> dataClass, Class<?> resourceClass, Class<?> transcodeClass, @Nullable r<?, ?, ?> loadPath) {
        synchronized (this.b) {
            this.b.put(new com.bumptech.glide.util.h(dataClass, resourceClass, transcodeClass), loadPath != null ? loadPath : a);
        }
    }

    private com.bumptech.glide.util.h b(Class<?> dataClass, Class<?> resourceClass, Class<?> transcodeClass) {
        com.bumptech.glide.util.h key = this.c.getAndSet((Object) null);
        if (key == null) {
            key = new com.bumptech.glide.util.h();
        }
        key.a(dataClass, resourceClass, transcodeClass);
        return key;
    }
}
