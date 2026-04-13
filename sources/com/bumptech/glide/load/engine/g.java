package com.bumptech.glide.load.engine;

import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.util.Pools;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.e;
import com.bumptech.glide.load.engine.h;
import com.bumptech.glide.load.i;
import com.bumptech.glide.load.l;
import com.bumptech.glide.load.m;
import com.bumptech.glide.util.pool.FactoryPools;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: DecodeJob */
public class g<R> implements e.a, Runnable, Comparable<g<?>>, FactoryPools.e {
    private b<R> A4;
    private int B4;
    private h C4;
    private C0032g D4;
    private long E4;
    private boolean F4;
    private Object G4;
    private Thread H4;
    private com.bumptech.glide.load.f I4;
    private com.bumptech.glide.load.f J4;
    private Object K4;
    private com.bumptech.glide.load.a L4;
    private com.bumptech.glide.load.data.d<?> M4;
    private volatile e N4;
    private volatile boolean O4;
    private volatile boolean P4;
    private boolean Q4;
    private com.bumptech.glide.load.f a1;
    private m a2;
    private final f<R> c = new f<>();
    private final List<Throwable> d = new ArrayList();
    private final com.bumptech.glide.util.pool.b f = com.bumptech.glide.util.pool.b.a();
    private com.bumptech.glide.d p0;
    private com.bumptech.glide.g p1;
    private int p2;
    private int p3;
    private i p4;
    private final e q;
    private final Pools.Pool<g<?>> x;
    private final d<?> y = new d<>();
    private final f z = new f();
    private i z4;

    /* compiled from: DecodeJob */
    public interface b<R> {
        void b(t<R> tVar, com.bumptech.glide.load.a aVar, boolean z);

        void c(GlideException glideException);

        void e(g<?> gVar);
    }

    /* compiled from: DecodeJob */
    public interface e {
        com.bumptech.glide.load.engine.cache.a a();
    }

    /* renamed from: com.bumptech.glide.load.engine.g$g  reason: collision with other inner class name */
    /* compiled from: DecodeJob */
    public enum C0032g {
        INITIALIZE,
        SWITCH_TO_SOURCE_SERVICE,
        DECODE_DATA
    }

    /* compiled from: DecodeJob */
    public enum h {
        INITIALIZE,
        RESOURCE_CACHE,
        DATA_CACHE,
        SOURCE,
        ENCODE,
        FINISHED
    }

    g(e diskCacheProvider, Pools.Pool<g<?>> pool) {
        this.q = diskCacheProvider;
        this.x = pool;
    }

    /* access modifiers changed from: package-private */
    public g<R> w(com.bumptech.glide.d glideContext, Object model, m loadKey, com.bumptech.glide.load.f signature, int width, int height, Class<?> resourceClass, Class<R> transcodeClass, com.bumptech.glide.g priority, i diskCacheStrategy, Map<Class<?>, m<?>> transformations, boolean isTransformationRequired, boolean isScaleOnlyOrNoTransform, boolean onlyRetrieveFromCache, i options, b<R> callback, int order) {
        int i = width;
        int i2 = height;
        i iVar = diskCacheStrategy;
        this.c.u(glideContext, model, signature, i, i2, iVar, resourceClass, transcodeClass, priority, options, transformations, isTransformationRequired, isScaleOnlyOrNoTransform, this.q);
        this.p0 = glideContext;
        this.a1 = signature;
        this.p1 = priority;
        this.a2 = loadKey;
        this.p2 = i;
        this.p3 = i2;
        this.p4 = iVar;
        this.F4 = onlyRetrieveFromCache;
        this.z4 = options;
        this.A4 = callback;
        this.B4 = order;
        this.D4 = C0032g.INITIALIZE;
        this.G4 = model;
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean P() {
        h firstStage = m(h.INITIALIZE);
        return firstStage == h.RESOURCE_CACHE || firstStage == h.DATA_CACHE;
    }

    /* access modifiers changed from: package-private */
    public void I(boolean isRemovedFromQueue) {
        if (this.z.d(isRemovedFromQueue)) {
            K();
        }
    }

    private void F() {
        if (this.z.b()) {
            K();
        }
    }

    private void G() {
        if (this.z.c()) {
            K();
        }
    }

    private void K() {
        this.z.e();
        this.y.a();
        this.c.a();
        this.O4 = false;
        this.p0 = null;
        this.a1 = null;
        this.z4 = null;
        this.p1 = null;
        this.a2 = null;
        this.A4 = null;
        this.C4 = null;
        this.N4 = null;
        this.H4 = null;
        this.I4 = null;
        this.K4 = null;
        this.L4 = null;
        this.M4 = null;
        this.E4 = 0;
        this.P4 = false;
        this.G4 = null;
        this.d.clear();
        this.x.release(this);
    }

    /* renamed from: f */
    public int compareTo(@NonNull g<?> other) {
        int result = q() - other.q();
        if (result == 0) {
            return this.B4 - other.B4;
        }
        return result;
    }

    private int q() {
        return this.p1.ordinal();
    }

    public void b() {
        this.P4 = true;
        e local = this.N4;
        if (local != null) {
            local.cancel();
        }
    }

    public void run() {
        com.bumptech.glide.util.pool.a.b("DecodeJob#run(model=%s)", this.G4);
        DataFetcher<?> localFetcher = this.M4;
        try {
            if (this.P4) {
                D();
                if (localFetcher != null) {
                    localFetcher.b();
                }
                com.bumptech.glide.util.pool.a.d();
                return;
            }
            N();
            if (localFetcher != null) {
                localFetcher.b();
            }
            com.bumptech.glide.util.pool.a.d();
        } catch (CallbackException e2) {
            throw e2;
        } catch (Throwable e3) {
            if (localFetcher != null) {
                localFetcher.b();
            }
            com.bumptech.glide.util.pool.a.d();
            throw e3;
        }
    }

    private void N() {
        switch (a.a[this.D4.ordinal()]) {
            case 1:
                this.C4 = m(h.INITIALIZE);
                this.N4 = l();
                L();
                return;
            case 2:
                L();
                return;
            case 3:
                k();
                return;
            default:
                throw new IllegalStateException("Unrecognized run reason: " + this.D4);
        }
    }

    private e l() {
        switch (a.b[this.C4.ordinal()]) {
            case 1:
                return new u(this.c, this);
            case 2:
                return new b(this.c, this);
            case 3:
                return new x(this.c, this);
            case 4:
                return null;
            default:
                throw new IllegalStateException("Unrecognized stage: " + this.C4);
        }
    }

    private void L() {
        this.H4 = Thread.currentThread();
        this.E4 = com.bumptech.glide.util.e.b();
        boolean isStarted = false;
        while (!this.P4 && this.N4 != null) {
            boolean b2 = this.N4.b();
            isStarted = b2;
            if (b2) {
                break;
            }
            this.C4 = m(this.C4);
            this.N4 = l();
            if (this.C4 == h.SOURCE) {
                c();
                return;
            }
        }
        if ((this.C4 == h.FINISHED || this.P4) && !isStarted) {
            D();
        }
    }

    private void D() {
        O();
        this.A4.c(new GlideException("Failed to load resource", (List<Throwable>) new ArrayList(this.d)));
        G();
    }

    private void B(t<R> resource, com.bumptech.glide.load.a dataSource, boolean isLoadedFromAlternateCacheKey) {
        O();
        this.A4.b(resource, dataSource, isLoadedFromAlternateCacheKey);
    }

    private void O() {
        Throwable lastThrown;
        this.f.c();
        if (this.O4) {
            if (this.d.isEmpty()) {
                lastThrown = null;
            } else {
                List<Throwable> list = this.d;
                lastThrown = list.get(list.size() - 1);
            }
            throw new IllegalStateException("Already notified", lastThrown);
        }
        this.O4 = true;
    }

    private h m(h current) {
        switch (a.b[current.ordinal()]) {
            case 1:
                if (this.p4.a()) {
                    return h.DATA_CACHE;
                }
                return m(h.DATA_CACHE);
            case 2:
                return this.F4 ? h.FINISHED : h.SOURCE;
            case 3:
            case 4:
                return h.FINISHED;
            case 5:
                if (this.p4.b()) {
                    return h.RESOURCE_CACHE;
                }
                return m(h.RESOURCE_CACHE);
            default:
                throw new IllegalArgumentException("Unrecognized stage: " + current);
        }
    }

    public void c() {
        this.D4 = C0032g.SWITCH_TO_SOURCE_SERVICE;
        this.A4.e(this);
    }

    public void e(com.bumptech.glide.load.f sourceKey, Object data, com.bumptech.glide.load.data.d<?> fetcher, com.bumptech.glide.load.a dataSource, com.bumptech.glide.load.f attemptedKey) {
        this.I4 = sourceKey;
        this.K4 = data;
        this.M4 = fetcher;
        this.L4 = dataSource;
        this.J4 = attemptedKey;
        boolean z2 = false;
        if (sourceKey != this.c.c().get(0)) {
            z2 = true;
        }
        this.Q4 = z2;
        if (Thread.currentThread() != this.H4) {
            this.D4 = C0032g.DECODE_DATA;
            this.A4.e(this);
            return;
        }
        com.bumptech.glide.util.pool.a.a("DecodeJob.decodeFromRetrievedData");
        try {
            k();
        } finally {
            com.bumptech.glide.util.pool.a.d();
        }
    }

    public void a(com.bumptech.glide.load.f attemptedKey, Exception e2, com.bumptech.glide.load.data.d<?> fetcher, com.bumptech.glide.load.a dataSource) {
        fetcher.b();
        GlideException exception = new GlideException("Fetching data failed", (Throwable) e2);
        exception.setLoggingDetails(attemptedKey, dataSource, fetcher.a());
        this.d.add(exception);
        if (Thread.currentThread() != this.H4) {
            this.D4 = C0032g.SWITCH_TO_SOURCE_SERVICE;
            this.A4.e(this);
            return;
        }
        L();
    }

    private void k() {
        if (Log.isLoggable("DecodeJob", 2)) {
            long j = this.E4;
            A("Retrieved data", j, "data: " + this.K4 + ", cache key: " + this.I4 + ", fetcher: " + this.M4);
        }
        Resource<R> resource = null;
        try {
            resource = h(this.M4, this.K4, this.L4);
        } catch (GlideException e2) {
            e2.setLoggingDetails(this.J4, this.L4);
            this.d.add(e2);
        }
        if (resource != null) {
            C(resource, this.L4, this.Q4);
        } else {
            L();
        }
    }

    private void C(t<R> resource, com.bumptech.glide.load.a dataSource, boolean isLoadedFromAlternateCacheKey) {
        if (resource instanceof p) {
            ((p) resource).initialize();
        }
        s<R> sVar = resource;
        s<R> sVar2 = null;
        if (this.y.c()) {
            sVar2 = s.c(resource);
            sVar = sVar2;
        }
        B(sVar, dataSource, isLoadedFromAlternateCacheKey);
        this.C4 = h.ENCODE;
        try {
            if (this.y.c()) {
                this.y.b(this.q, this.z4);
            }
            F();
        } finally {
            if (sVar2 != null) {
                sVar2.f();
            }
        }
    }

    private <Data> t<R> h(com.bumptech.glide.load.data.d<?> fetcher, Data data, com.bumptech.glide.load.a dataSource) {
        if (data == null) {
            fetcher.b();
            return null;
        }
        try {
            long startTime = com.bumptech.glide.util.e.b();
            Resource<R> result = j(data, dataSource);
            if (Log.isLoggable("DecodeJob", 2)) {
                y("Decoded result " + result, startTime);
            }
            return result;
        } finally {
            fetcher.b();
        }
    }

    private <Data> t<R> j(Data data, com.bumptech.glide.load.a dataSource) {
        return M(data, dataSource, this.c.h(data.getClass()));
    }

    @NonNull
    private i p(com.bumptech.glide.load.a dataSource) {
        i options = this.z4;
        if (Build.VERSION.SDK_INT < 26) {
            return options;
        }
        boolean isHardwareConfigSafe = dataSource == com.bumptech.glide.load.a.RESOURCE_DISK_CACHE || this.c.w();
        com.bumptech.glide.load.h hVar = com.bumptech.glide.load.resource.bitmap.m.e;
        Boolean isHardwareConfigAllowed = (Boolean) options.a(hVar);
        if (isHardwareConfigAllowed != null && (!isHardwareConfigAllowed.booleanValue() || isHardwareConfigSafe)) {
            return options;
        }
        i options2 = new i();
        options2.b(this.z4);
        options2.c(hVar, Boolean.valueOf(isHardwareConfigSafe));
        return options2;
    }

    private <Data, ResourceType> t<R> M(Data data, com.bumptech.glide.load.a dataSource, r<Data, ResourceType, R> path) {
        i options = p(dataSource);
        com.bumptech.glide.load.data.e l = this.p0.i().l(data);
        try {
            return path.a(l, options, this.p2, this.p3, new c(dataSource));
        } finally {
            l.b();
        }
    }

    private void y(String message, long startTime) {
        A(message, startTime, (String) null);
    }

    private void A(String message, long startTime, String extraArgs) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append(" in ");
        sb.append(com.bumptech.glide.util.e.a(startTime));
        sb.append(", load key: ");
        sb.append(this.a2);
        if (extraArgs != null) {
            str = ", " + extraArgs;
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        Log.v("DecodeJob", sb.toString());
    }

    @NonNull
    public com.bumptech.glide.util.pool.b d() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public <Z> t<Z> H(com.bumptech.glide.load.a dataSource, @NonNull t<Z> decoded) {
        t<Z> tVar;
        Transformation<Z> appliedTransformation;
        ResourceEncoder<Z> encoder;
        com.bumptech.glide.load.c encodeStrategy;
        com.bumptech.glide.load.f key;
        com.bumptech.glide.load.a aVar = dataSource;
        t<Z> tVar2 = decoded;
        Class<?> cls = decoded.get().getClass();
        t<Z> tVar3 = decoded;
        if (aVar != com.bumptech.glide.load.a.RESOURCE_DISK_CACHE) {
            Transformation<Z> appliedTransformation2 = this.c.r(cls);
            appliedTransformation = appliedTransformation2;
            tVar = appliedTransformation2.transform(this.p0, tVar2, this.p2, this.p3);
        } else {
            appliedTransformation = null;
            tVar = tVar3;
        }
        if (!tVar2.equals(tVar)) {
            decoded.recycle();
        }
        if (this.c.v(tVar)) {
            ResourceEncoder<Z> encoder2 = this.c.n(tVar);
            encoder = encoder2;
            encodeStrategy = encoder2.b(this.z4);
        } else {
            encoder = null;
            encodeStrategy = com.bumptech.glide.load.c.NONE;
        }
        t<Z> tVar4 = tVar;
        boolean isFromAlternateCacheKey = !this.c.x(this.I4);
        if (!this.p4.d(isFromAlternateCacheKey, aVar, encodeStrategy)) {
            boolean z2 = isFromAlternateCacheKey;
            com.bumptech.glide.load.c cVar = encodeStrategy;
            return tVar4;
        } else if (encoder != null) {
            switch (a.c[encodeStrategy.ordinal()]) {
                case 1:
                    com.bumptech.glide.load.c cVar2 = encodeStrategy;
                    key = new c(this.I4, this.a1);
                    break;
                case 2:
                    boolean z3 = isFromAlternateCacheKey;
                    com.bumptech.glide.load.c cVar3 = encodeStrategy;
                    key = new v(this.c.b(), this.I4, this.a1, this.p2, this.p3, appliedTransformation, cls, this.z4);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown strategy: " + encodeStrategy);
            }
            Resource<Z> lockedResult = s.c(tVar);
            this.y.d(key, encoder, lockedResult);
            return lockedResult;
        } else {
            throw new Registry.NoResultEncoderAvailableException(tVar.get().getClass());
        }
    }

    /* compiled from: DecodeJob */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;
        static final /* synthetic */ int[] c;

        static {
            int[] iArr = new int[com.bumptech.glide.load.c.values().length];
            c = iArr;
            try {
                iArr[com.bumptech.glide.load.c.SOURCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                c[com.bumptech.glide.load.c.TRANSFORMED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            int[] iArr2 = new int[h.values().length];
            b = iArr2;
            try {
                iArr2[h.RESOURCE_CACHE.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                b[h.DATA_CACHE.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                b[h.SOURCE.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            try {
                b[h.FINISHED.ordinal()] = 4;
            } catch (NoSuchFieldError e6) {
            }
            try {
                b[h.INITIALIZE.ordinal()] = 5;
            } catch (NoSuchFieldError e7) {
            }
            int[] iArr3 = new int[C0032g.values().length];
            a = iArr3;
            try {
                iArr3[C0032g.INITIALIZE.ordinal()] = 1;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[C0032g.SWITCH_TO_SOURCE_SERVICE.ordinal()] = 2;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[C0032g.DECODE_DATA.ordinal()] = 3;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    /* compiled from: DecodeJob */
    public final class c<Z> implements h.a<Z> {
        private final com.bumptech.glide.load.a a;

        c(com.bumptech.glide.load.a dataSource) {
            this.a = dataSource;
        }

        @NonNull
        public t<Z> a(@NonNull t<Z> decoded) {
            return g.this.H(this.a, decoded);
        }
    }

    /* compiled from: DecodeJob */
    public static class f {
        private boolean a;
        private boolean b;
        private boolean c;

        f() {
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean d(boolean isRemovedFromQueue) {
            this.a = true;
            return a(isRemovedFromQueue);
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean b() {
            this.b = true;
            return a(false);
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean c() {
            this.c = true;
            return a(false);
        }

        /* access modifiers changed from: package-private */
        public synchronized void e() {
            this.b = false;
            this.a = false;
            this.c = false;
        }

        private boolean a(boolean isRemovedFromQueue) {
            return (this.c || isRemovedFromQueue || this.b) && this.a;
        }
    }

    /* compiled from: DecodeJob */
    public static class d<Z> {
        private com.bumptech.glide.load.f a;
        private l<Z> b;
        private s<Z> c;

        d() {
        }

        /* access modifiers changed from: package-private */
        public <X> void d(com.bumptech.glide.load.f key, l<X> encoder, s<X> toEncode) {
            this.a = key;
            this.b = encoder;
            this.c = toEncode;
        }

        /* access modifiers changed from: package-private */
        public void b(e diskCacheProvider, i options) {
            com.bumptech.glide.util.pool.a.a("DecodeJob.encode");
            try {
                diskCacheProvider.a().a(this.a, new d(this.b, this.c, options));
            } finally {
                this.c.f();
                com.bumptech.glide.util.pool.a.d();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean c() {
            return this.c != null;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            this.a = null;
            this.b = null;
            this.c = null;
        }
    }
}
