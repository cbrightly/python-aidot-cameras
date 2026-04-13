package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.core.util.Pools;
import com.bumptech.glide.g;
import com.bumptech.glide.load.engine.cache.a;
import com.bumptech.glide.load.engine.cache.h;
import com.bumptech.glide.load.engine.g;
import com.bumptech.glide.load.engine.o;
import com.bumptech.glide.load.f;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.m;
import com.bumptech.glide.util.e;
import com.bumptech.glide.util.pool.FactoryPools;
import java.util.Map;
import java.util.concurrent.Executor;

/* compiled from: Engine */
public class j implements l, h.a, o.a {
    private static final boolean a = Log.isLoggable("Engine", 2);
    private final q b;
    private final n c;
    private final h d;
    private final b e;
    private final w f;
    private final c g;
    private final a h;
    private final a i;

    public j(h memoryCache, a.C0027a diskCacheFactory, com.bumptech.glide.load.engine.executor.a diskCacheExecutor, com.bumptech.glide.load.engine.executor.a sourceExecutor, com.bumptech.glide.load.engine.executor.a sourceUnlimitedExecutor, com.bumptech.glide.load.engine.executor.a animationExecutor, boolean isActiveResourceRetentionAllowed) {
        this(memoryCache, diskCacheFactory, diskCacheExecutor, sourceExecutor, sourceUnlimitedExecutor, animationExecutor, (q) null, (n) null, (a) null, (b) null, (a) null, (w) null, isActiveResourceRetentionAllowed);
    }

    @VisibleForTesting
    j(h cache, a.C0027a diskCacheFactory, com.bumptech.glide.load.engine.executor.a diskCacheExecutor, com.bumptech.glide.load.engine.executor.a sourceExecutor, com.bumptech.glide.load.engine.executor.a sourceUnlimitedExecutor, com.bumptech.glide.load.engine.executor.a animationExecutor, q jobs, n keyFactory, a activeResources, b engineJobFactory, a decodeJobFactory, w resourceRecycler, boolean isActiveResourceRetentionAllowed) {
        a activeResources2;
        n keyFactory2;
        q jobs2;
        b engineJobFactory2;
        a decodeJobFactory2;
        w resourceRecycler2;
        h hVar = cache;
        this.d = hVar;
        c cVar = new c(diskCacheFactory);
        this.g = cVar;
        if (activeResources == null) {
            activeResources2 = new a(isActiveResourceRetentionAllowed);
        } else {
            boolean z = isActiveResourceRetentionAllowed;
            activeResources2 = activeResources;
        }
        this.i = activeResources2;
        activeResources2.f(this);
        if (keyFactory == null) {
            keyFactory2 = new n();
        } else {
            keyFactory2 = keyFactory;
        }
        this.c = keyFactory2;
        if (jobs == null) {
            jobs2 = new q();
        } else {
            jobs2 = jobs;
        }
        this.b = jobs2;
        if (engineJobFactory == null) {
            engineJobFactory2 = new b(diskCacheExecutor, sourceExecutor, sourceUnlimitedExecutor, animationExecutor, this, this);
        } else {
            engineJobFactory2 = engineJobFactory;
        }
        this.e = engineJobFactory2;
        if (decodeJobFactory == null) {
            decodeJobFactory2 = new a(cVar);
        } else {
            decodeJobFactory2 = decodeJobFactory;
        }
        this.h = decodeJobFactory2;
        if (resourceRecycler == null) {
            resourceRecycler2 = new w();
        } else {
            resourceRecycler2 = resourceRecycler;
        }
        this.f = resourceRecycler2;
        hVar.e(this);
    }

    public <R> d f(com.bumptech.glide.d glideContext, Object model, f signature, int width, int height, Class<?> resourceClass, Class<R> transcodeClass, g priority, i diskCacheStrategy, Map<Class<?>, m<?>> transformations, boolean isTransformationRequired, boolean isScaleOnlyOrNoTransform, i options, boolean isMemoryCacheable, boolean useUnlimitedSourceExecutorPool, boolean useAnimationPool, boolean onlyRetrieveFromCache, com.bumptech.glide.request.g cb, Executor callbackExecutor) {
        long startTime = a ? e.b() : 0;
        m key = this.c.a(model, signature, width, height, transformations, resourceClass, transcodeClass, options);
        synchronized (this) {
            try {
                EngineResource<?> memoryResource = i(key, isMemoryCacheable, startTime);
                if (memoryResource == null) {
                    try {
                        d l = l(glideContext, model, signature, width, height, resourceClass, transcodeClass, priority, diskCacheStrategy, transformations, isTransformationRequired, isScaleOnlyOrNoTransform, options, isMemoryCacheable, useUnlimitedSourceExecutorPool, useAnimationPool, onlyRetrieveFromCache, cb, callbackExecutor, key, startTime);
                        return l;
                    } catch (Throwable th) {
                        memoryResource = th;
                        com.bumptech.glide.request.g gVar = cb;
                        while (true) {
                            try {
                                break;
                            } catch (Throwable th2) {
                                memoryResource = th2;
                            }
                        }
                        throw memoryResource;
                    }
                } else {
                    long j = startTime;
                    cb.b(memoryResource, com.bumptech.glide.load.a.MEMORY_CACHE, false);
                    return null;
                }
            } catch (Throwable th3) {
                memoryResource = th3;
                com.bumptech.glide.request.g gVar2 = cb;
                m mVar = key;
                long j2 = startTime;
                while (true) {
                    break;
                }
                throw memoryResource;
            }
        }
    }

    private <R> d l(com.bumptech.glide.d glideContext, Object model, f signature, int width, int height, Class<?> resourceClass, Class<R> transcodeClass, g priority, i diskCacheStrategy, Map<Class<?>, m<?>> transformations, boolean isTransformationRequired, boolean isScaleOnlyOrNoTransform, i options, boolean isMemoryCacheable, boolean useUnlimitedSourceExecutorPool, boolean useAnimationPool, boolean onlyRetrieveFromCache, com.bumptech.glide.request.g cb, Executor callbackExecutor, m key, long startTime) {
        com.bumptech.glide.request.g gVar = cb;
        Executor executor = callbackExecutor;
        m mVar = key;
        long j = startTime;
        k<?> a2 = this.b.a(mVar, onlyRetrieveFromCache);
        if (a2 != null) {
            a2.a(gVar, executor);
            if (a) {
                j("Added to existing load", j, mVar);
            }
            return new d(gVar, a2);
        }
        k a3 = this.e.a(key, isMemoryCacheable, useUnlimitedSourceExecutorPool, useAnimationPool, onlyRetrieveFromCache);
        k kVar = a3;
        k<?> kVar2 = a2;
        m mVar2 = mVar;
        DecodeJob<R> decodeJob = this.h.a(glideContext, model, key, signature, width, height, resourceClass, transcodeClass, priority, diskCacheStrategy, transformations, isTransformationRequired, isScaleOnlyOrNoTransform, onlyRetrieveFromCache, options, a3);
        this.b.c(mVar2, kVar);
        k kVar3 = kVar;
        m mVar3 = mVar2;
        com.bumptech.glide.request.g gVar2 = cb;
        kVar3.a(gVar2, callbackExecutor);
        kVar3.s(decodeJob);
        if (a) {
            j("Started new load", startTime, mVar3);
        } else {
            long j2 = startTime;
        }
        return new d(gVar2, kVar3);
    }

    @Nullable
    private o<?> i(m key, boolean isMemoryCacheable, long startTime) {
        if (!isMemoryCacheable) {
            return null;
        }
        EngineResource<?> active = g(key);
        if (active != null) {
            if (a) {
                j("Loaded resource from active resources", startTime, key);
            }
            return active;
        }
        EngineResource<?> cached = h(key);
        if (cached == null) {
            return null;
        }
        if (a) {
            j("Loaded resource from cache", startTime, key);
        }
        return cached;
    }

    private static void j(String log, long startTime, f key) {
        Log.v("Engine", log + " in " + e.a(startTime) + "ms, key: " + key);
    }

    @Nullable
    private o<?> g(f key) {
        EngineResource<?> active = this.i.e(key);
        if (active != null) {
            active.b();
        }
        return active;
    }

    private o<?> h(f key) {
        EngineResource<?> cached = e(key);
        if (cached != null) {
            cached.b();
            this.i.a(key, cached);
        }
        return cached;
    }

    private o<?> e(f key) {
        EngineResource<?> cached = this.d.c(key);
        if (cached == null) {
            return null;
        }
        if (cached instanceof o) {
            return (o) cached;
        }
        return new o<>(cached, true, true, key, this);
    }

    public void k(t<?> resource) {
        if (resource instanceof o) {
            ((o) resource).e();
            return;
        }
        throw new IllegalArgumentException("Cannot release anything but an EngineResource");
    }

    public synchronized void b(k<?> engineJob, f key, o<?> resource) {
        if (resource != null) {
            if (resource.d()) {
                this.i.a(key, resource);
            }
        }
        this.b.d(key, engineJob);
    }

    public synchronized void c(k<?> engineJob, f key) {
        this.b.d(key, engineJob);
    }

    public void a(@NonNull t<?> resource) {
        this.f.a(resource, true);
    }

    public void d(f cacheKey, o<?> resource) {
        this.i.d(cacheKey);
        if (resource.d()) {
            this.d.b(cacheKey, resource);
        } else {
            this.f.a(resource, false);
        }
    }

    /* compiled from: Engine */
    public class d {
        private final k<?> a;
        private final com.bumptech.glide.request.g b;

        d(com.bumptech.glide.request.g cb, k<?> engineJob) {
            this.b = cb;
            this.a = engineJob;
        }

        public void a() {
            synchronized (j.this) {
                this.a.r(this.b);
            }
        }
    }

    /* compiled from: Engine */
    public static class c implements g.e {
        private final a.C0027a a;
        private volatile com.bumptech.glide.load.engine.cache.a b;

        c(a.C0027a factory) {
            this.a = factory;
        }

        public com.bumptech.glide.load.engine.cache.a a() {
            if (this.b == null) {
                synchronized (this) {
                    if (this.b == null) {
                        this.b = this.a.build();
                    }
                    if (this.b == null) {
                        this.b = new com.bumptech.glide.load.engine.cache.b();
                    }
                }
            }
            return this.b;
        }
    }

    @VisibleForTesting
    /* compiled from: Engine */
    public static class a {
        final g.e a;
        final Pools.Pool<g<?>> b = FactoryPools.d(150, new C0033a());
        private int c;

        /* renamed from: com.bumptech.glide.load.engine.j$a$a  reason: collision with other inner class name */
        /* compiled from: Engine */
        public class C0033a implements FactoryPools.d<g<?>> {
            C0033a() {
            }

            /* renamed from: a */
            public g<?> create() {
                a aVar = a.this;
                return new g<>(aVar.a, aVar.b);
            }
        }

        a(g.e diskCacheProvider) {
            this.a = diskCacheProvider;
        }

        /* access modifiers changed from: package-private */
        public <R> g<R> a(com.bumptech.glide.d glideContext, Object model, m loadKey, f signature, int width, int height, Class<?> resourceClass, Class<R> transcodeClass, com.bumptech.glide.g priority, i diskCacheStrategy, Map<Class<?>, m<?>> transformations, boolean isTransformationRequired, boolean isScaleOnlyOrNoTransform, boolean onlyRetrieveFromCache, i options, g.b<R> callback) {
            g gVar = (g) com.bumptech.glide.util.i.d(this.b.acquire());
            int i = this.c;
            int i2 = i;
            this.c = i + 1;
            return gVar.w(glideContext, model, loadKey, signature, width, height, resourceClass, transcodeClass, priority, diskCacheStrategy, transformations, isTransformationRequired, isScaleOnlyOrNoTransform, onlyRetrieveFromCache, options, callback, i2);
        }
    }

    @VisibleForTesting
    /* compiled from: Engine */
    public static class b {
        final com.bumptech.glide.load.engine.executor.a a;
        final com.bumptech.glide.load.engine.executor.a b;
        final com.bumptech.glide.load.engine.executor.a c;
        final com.bumptech.glide.load.engine.executor.a d;
        final l e;
        final o.a f;
        final Pools.Pool<k<?>> g = FactoryPools.d(150, new a());

        /* compiled from: Engine */
        public class a implements FactoryPools.d<k<?>> {
            a() {
            }

            /* renamed from: a */
            public k<?> create() {
                b bVar = b.this;
                return new k(bVar.a, bVar.b, bVar.c, bVar.d, bVar.e, bVar.f, bVar.g);
            }
        }

        b(com.bumptech.glide.load.engine.executor.a diskCacheExecutor, com.bumptech.glide.load.engine.executor.a sourceExecutor, com.bumptech.glide.load.engine.executor.a sourceUnlimitedExecutor, com.bumptech.glide.load.engine.executor.a animationExecutor, l engineJobListener, o.a resourceListener) {
            this.a = diskCacheExecutor;
            this.b = sourceExecutor;
            this.c = sourceUnlimitedExecutor;
            this.d = animationExecutor;
            this.e = engineJobListener;
            this.f = resourceListener;
        }

        /* access modifiers changed from: package-private */
        public <R> k<R> a(f key, boolean isMemoryCacheable, boolean useUnlimitedSourceGeneratorPool, boolean useAnimationPool, boolean onlyRetrieveFromCache) {
            return ((k) com.bumptech.glide.util.i.d(this.g.acquire())).l(key, isMemoryCacheable, useUnlimitedSourceGeneratorPool, useAnimationPool, onlyRetrieveFromCache);
        }
    }
}
