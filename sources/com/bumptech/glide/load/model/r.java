package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.load.model.n;
import com.bumptech.glide.util.i;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: MultiModelLoaderFactory */
public class r {
    private static final c a = new c();
    private static final n<Object, Object> b = new a();
    private final List<b<?, ?>> c;
    private final c d;
    private final Set<b<?, ?>> e;
    private final Pools.Pool<List<Throwable>> f;

    public r(@NonNull Pools.Pool<List<Throwable>> throwableListPool) {
        this(throwableListPool, a);
    }

    @VisibleForTesting
    r(@NonNull Pools.Pool<List<Throwable>> throwableListPool, @NonNull c factory) {
        this.c = new ArrayList();
        this.e = new HashSet();
        this.f = throwableListPool;
        this.d = factory;
    }

    /* access modifiers changed from: package-private */
    public synchronized <Model, Data> void b(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull o<? extends Model, ? extends Data> factory) {
        a(modelClass, dataClass, factory, true);
    }

    private <Model, Data> void a(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull o<? extends Model, ? extends Data> factory, boolean append) {
        MultiModelLoaderFactory.Entry<Model, Data> entry = new b<>(modelClass, dataClass, factory);
        List<b<?, ?>> list = this.c;
        list.add(append ? list.size() : 0, entry);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized <Model> List<n<Model, ?>> e(@NonNull Class<Model> modelClass) {
        List<ModelLoader<Model, ?>> loaders;
        try {
            loaders = new ArrayList<>();
            Iterator<b<?, ?>> it = this.c.iterator();
            while (it.hasNext()) {
                MultiModelLoaderFactory.Entry<?, ?> entry = (b) it.next();
                if (!this.e.contains(entry)) {
                    if (entry.a(modelClass)) {
                        this.e.add(entry);
                        loaders.add(c(entry));
                        this.e.remove(entry);
                    }
                }
            }
        } catch (Throwable t) {
            this.e.clear();
            throw t;
        }
        return loaders;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public synchronized List<Class<?>> g(@NonNull Class<?> modelClass) {
        List<Class<?>> result;
        result = new ArrayList<>();
        Iterator<b<?, ?>> it = this.c.iterator();
        while (it.hasNext()) {
            MultiModelLoaderFactory.Entry<?, ?> entry = (b) it.next();
            if (!result.contains(entry.b) && entry.a(modelClass)) {
                result.add(entry.b);
            }
        }
        return result;
    }

    @NonNull
    public synchronized <Model, Data> n<Model, Data> d(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass) {
        try {
            List<ModelLoader<Model, Data>> loaders = new ArrayList<>();
            boolean ignoredAnyEntries = false;
            Iterator<b<?, ?>> it = this.c.iterator();
            while (it.hasNext()) {
                MultiModelLoaderFactory.Entry<?, ?> entry = (b) it.next();
                if (this.e.contains(entry)) {
                    ignoredAnyEntries = true;
                } else if (entry.b(modelClass, dataClass)) {
                    this.e.add(entry);
                    loaders.add(c(entry));
                    this.e.remove(entry);
                }
            }
            if (loaders.size() > 1) {
                return this.d.a(loaders, this.f);
            } else if (loaders.size() == 1) {
                return (n) loaders.get(0);
            } else if (ignoredAnyEntries) {
                return f();
            } else {
                throw new Registry.NoModelLoaderAvailableException((Class<?>) modelClass, (Class<?>) dataClass);
            }
        } catch (Throwable t) {
            this.e.clear();
            throw t;
        }
    }

    @NonNull
    private <Model, Data> n<Model, Data> c(@NonNull b<?, ?> entry) {
        return (n) i.d(entry.c.b(this));
    }

    @NonNull
    private static <Model, Data> n<Model, Data> f() {
        return b;
    }

    /* compiled from: MultiModelLoaderFactory */
    public static class b<Model, Data> {
        private final Class<Model> a;
        final Class<Data> b;
        final o<? extends Model, ? extends Data> c;

        public b(@NonNull Class<Model> modelClass, @NonNull Class<Data> dataClass, @NonNull o<? extends Model, ? extends Data> factory) {
            this.a = modelClass;
            this.b = dataClass;
            this.c = factory;
        }

        public boolean b(@NonNull Class<?> modelClass, @NonNull Class<?> dataClass) {
            return a(modelClass) && this.b.isAssignableFrom(dataClass);
        }

        public boolean a(@NonNull Class<?> modelClass) {
            return this.a.isAssignableFrom(modelClass);
        }
    }

    /* compiled from: MultiModelLoaderFactory */
    public static class c {
        c() {
        }

        @NonNull
        public <Model, Data> q<Model, Data> a(@NonNull List<n<Model, Data>> modelLoaders, @NonNull Pools.Pool<List<Throwable>> throwableListPool) {
            return new q<>(modelLoaders, throwableListPool);
        }
    }

    /* compiled from: MultiModelLoaderFactory */
    public static class a implements n<Object, Object> {
        a() {
        }

        @Nullable
        public n.a<Object> b(@NonNull Object o, int width, int height, @NonNull com.bumptech.glide.load.i options) {
            return null;
        }

        public boolean a(@NonNull Object o) {
            return false;
        }
    }
}
