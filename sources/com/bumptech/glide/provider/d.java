package com.bumptech.glide.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.bumptech.glide.util.h;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ModelToResourceClassCache */
public class d {
    private final AtomicReference<h> a = new AtomicReference<>();
    private final ArrayMap<h, List<Class<?>>> b = new ArrayMap<>();

    @Nullable
    public List<Class<?>> a(@NonNull Class<?> modelClass, @NonNull Class<?> resourceClass, @NonNull Class<?> transcodeClass) {
        List<Class<?>> result;
        h key = this.a.getAndSet((Object) null);
        if (key == null) {
            key = new h(modelClass, resourceClass, transcodeClass);
        } else {
            key.a(modelClass, resourceClass, transcodeClass);
        }
        synchronized (this.b) {
            result = this.b.get(key);
        }
        this.a.set(key);
        return result;
    }

    public void b(@NonNull Class<?> modelClass, @NonNull Class<?> resourceClass, @NonNull Class<?> transcodeClass, @NonNull List<Class<?>> resourceClasses) {
        synchronized (this.b) {
            this.b.put(new h(modelClass, resourceClass, transcodeClass), resourceClasses);
        }
    }
}
