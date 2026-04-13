package com.bumptech.glide.util.pool;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import java.util.ArrayList;
import java.util.List;

public final class FactoryPools {
    private static final f<Object> a = new a();

    public interface d<T> {
        T create();
    }

    public interface e {
        @NonNull
        b d();
    }

    public interface f<T> {
        void reset(@NonNull T t);
    }

    public class a implements f<Object> {
        a() {
        }

        public void reset(@NonNull Object object) {
        }
    }

    @NonNull
    public static <T extends e> Pools.Pool<T> d(int size, @NonNull d<T> factory) {
        return a(new Pools.SynchronizedPool(size), factory);
    }

    @NonNull
    public static <T> Pools.Pool<List<T>> e() {
        return f(20);
    }

    @NonNull
    public static <T> Pools.Pool<List<T>> f(int size) {
        return b(new Pools.SynchronizedPool(size), new b(), new c());
    }

    public class b implements d<List<T>> {
        b() {
        }

        @NonNull
        /* renamed from: a */
        public List<T> create() {
            return new ArrayList();
        }
    }

    public class c implements f<List<T>> {
        c() {
        }

        /* renamed from: a */
        public void reset(@NonNull List<T> object) {
            object.clear();
        }
    }

    @NonNull
    private static <T extends e> Pools.Pool<T> a(@NonNull Pools.Pool<T> pool, @NonNull d<T> factory) {
        return b(pool, factory, c());
    }

    @NonNull
    private static <T> Pools.Pool<T> b(@NonNull Pools.Pool<T> pool, @NonNull d<T> factory, @NonNull f<T> resetter) {
        return new FactoryPool(pool, factory, resetter);
    }

    @NonNull
    private static <T> f<T> c() {
        return a;
    }

    public static final class FactoryPool<T> implements Pools.Pool<T> {
        private final d<T> a;
        private final f<T> b;
        private final Pools.Pool<T> c;

        FactoryPool(@NonNull Pools.Pool<T> pool, @NonNull d<T> factory, @NonNull f<T> resetter) {
            this.c = pool;
            this.a = factory;
            this.b = resetter;
        }

        public T acquire() {
            T result = this.c.acquire();
            if (result == null) {
                result = this.a.create();
                if (Log.isLoggable("FactoryPools", 2)) {
                    Log.v("FactoryPools", "Created new " + result.getClass());
                }
            }
            if (result instanceof e) {
                ((e) result).d().b(false);
            }
            return result;
        }

        public boolean release(@NonNull T instance) {
            if (instance instanceof e) {
                ((e) instance).d().b(true);
            }
            this.b.reset(instance);
            return this.c.release(instance);
        }
    }
}
