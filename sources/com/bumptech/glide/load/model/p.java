package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pools;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.ModelLoaderRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: ModelLoaderRegistry */
public class p {
    private final r a;
    private final a b;

    public p(@NonNull Pools.Pool<List<Throwable>> throwableListPool) {
        this(new r(throwableListPool));
    }

    private p(@NonNull r multiModelLoaderFactory) {
        this.b = new a();
        this.a = multiModelLoaderFactory;
    }

    public synchronized <Model, Data> void a(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull o<? extends Model, ? extends Data> factory) {
        this.a.b(modelClass, dataClass, factory);
        this.b.a();
    }

    @NonNull
    public <A> List<n<A, ?>> d(@NonNull A model) {
        List<ModelLoader<A, ?>> modelLoaders = e(b(model));
        if (!modelLoaders.isEmpty()) {
            int size = modelLoaders.size();
            boolean isEmpty = true;
            List<ModelLoader<A, ?>> filteredLoaders = Collections.emptyList();
            for (int i = 0; i < size; i++) {
                ModelLoader<A, ?> loader = (n) modelLoaders.get(i);
                if (loader.a(model)) {
                    if (isEmpty) {
                        filteredLoaders = new ArrayList<>(size - i);
                        isEmpty = false;
                    }
                    filteredLoaders.add(loader);
                }
            }
            if (filteredLoaders.isEmpty() == 0) {
                return filteredLoaders;
            }
            throw new Registry.NoModelLoaderAvailableException(model, modelLoaders);
        }
        throw new Registry.NoModelLoaderAvailableException(model);
    }

    @NonNull
    public synchronized List<Class<?>> c(@NonNull Class<?> modelClass) {
        return this.a.g(modelClass);
    }

    @NonNull
    private synchronized <A> List<n<A, ?>> e(@NonNull Class<A> modelClass) {
        List<ModelLoader<A, ?>> loaders;
        loaders = this.b.b(modelClass);
        if (loaders == null) {
            loaders = Collections.unmodifiableList(this.a.e(modelClass));
            this.b.c(modelClass, loaders);
        }
        return loaders;
    }

    @NonNull
    private static <A> Class<A> b(@NonNull A model) {
        return model.getClass();
    }

    /* compiled from: ModelLoaderRegistry */
    public static class a {
        private final Map<Class<?>, C0037a<?>> a = new HashMap();

        a() {
        }

        public void a() {
            this.a.clear();
        }

        public <Model> void c(Class<Model> modelClass, List<n<Model, ?>> loaders) {
            if (((C0037a) this.a.put(modelClass, new C0037a(loaders))) != null) {
                throw new IllegalStateException("Already cached loaders for model: " + modelClass);
            }
        }

        @Nullable
        public <Model> List<n<Model, ?>> b(Class<Model> modelClass) {
            ModelLoaderRegistry.ModelLoaderCache.Entry<Model> entry = (C0037a) this.a.get(modelClass);
            if (entry == null) {
                return null;
            }
            return entry.a;
        }

        /* renamed from: com.bumptech.glide.load.model.p$a$a  reason: collision with other inner class name */
        /* compiled from: ModelLoaderRegistry */
        public static class C0037a<Model> {
            final List<n<Model, ?>> a;

            public C0037a(List<n<Model, ?>> loaders) {
                this.a = loaders;
            }
        }
    }
}
