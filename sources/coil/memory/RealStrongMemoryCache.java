package coil.memory;

import android.graphics.Bitmap;
import coil.bitmap.e;
import coil.memory.MemoryCache;
import coil.memory.o;
import coil.util.m;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StrongMemoryCache.kt */
public final class RealStrongMemoryCache implements r {
    @NotNull
    public static final a b = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public final u c;
    /* access modifiers changed from: private */
    @NotNull
    public final e d;
    @Nullable
    private final m e;
    @NotNull
    private final RealStrongMemoryCache$cache$1 f;

    public RealStrongMemoryCache(@NotNull u weakMemoryCache, @NotNull e referenceCounter, int maxSize, @Nullable m logger) {
        k.e(weakMemoryCache, "weakMemoryCache");
        k.e(referenceCounter, "referenceCounter");
        this.c = weakMemoryCache;
        this.d = referenceCounter;
        this.e = logger;
        this.f = new RealStrongMemoryCache$cache$1(this, maxSize);
    }

    public int h() {
        return this.f.size();
    }

    public int g() {
        return this.f.maxSize();
    }

    @Nullable
    public synchronized o.a b(@NotNull MemoryCache.Key key) {
        k.e(key, CacheEntity.KEY);
        return (o.a) this.f.get(key);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0025, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void c(@org.jetbrains.annotations.NotNull coil.memory.MemoryCache.Key r4, @org.jetbrains.annotations.NotNull android.graphics.Bitmap r5, boolean r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "key"
            kotlin.jvm.internal.k.e(r4, r0)     // Catch:{ all -> 0x0037 }
            java.lang.String r0 = "bitmap"
            kotlin.jvm.internal.k.e(r5, r0)     // Catch:{ all -> 0x0037 }
            int r0 = coil.util.c.a(r5)     // Catch:{ all -> 0x0037 }
            int r1 = r3.g()     // Catch:{ all -> 0x0037 }
            if (r0 <= r1) goto L_0x0026
            coil.memory.RealStrongMemoryCache$cache$1 r1 = r3.f     // Catch:{ all -> 0x0037 }
            java.lang.Object r1 = r1.remove(r4)     // Catch:{ all -> 0x0037 }
            coil.memory.RealStrongMemoryCache$b r1 = (coil.memory.RealStrongMemoryCache.b) r1     // Catch:{ all -> 0x0037 }
            if (r1 != 0) goto L_0x0024
            coil.memory.u r2 = r3.c     // Catch:{ all -> 0x0037 }
            r2.d(r4, r5, r6, r0)     // Catch:{ all -> 0x0037 }
        L_0x0024:
            monitor-exit(r3)
            return
        L_0x0026:
            coil.bitmap.e r1 = r3.d     // Catch:{ all -> 0x0037 }
            r1.c(r5)     // Catch:{ all -> 0x0037 }
            coil.memory.RealStrongMemoryCache$cache$1 r1 = r3.f     // Catch:{ all -> 0x0037 }
            coil.memory.RealStrongMemoryCache$b r2 = new coil.memory.RealStrongMemoryCache$b     // Catch:{ all -> 0x0037 }
            r2.<init>(r5, r6, r0)     // Catch:{ all -> 0x0037 }
            r1.put(r4, r2)     // Catch:{ all -> 0x0037 }
            monitor-exit(r3)
            return
        L_0x0037:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: coil.memory.RealStrongMemoryCache.c(coil.memory.MemoryCache$Key, android.graphics.Bitmap, boolean):void");
    }

    public synchronized void f() {
        m $this$log$iv = this.e;
        if ($this$log$iv != null) {
            if ($this$log$iv.b() <= 2) {
                $this$log$iv.a("RealStrongMemoryCache", 2, "clearMemory", (Throwable) null);
            }
        }
        this.f.trimToSize(-1);
    }

    public synchronized void a(int level) {
        m $this$log$iv = this.e;
        if ($this$log$iv != null) {
            if ($this$log$iv.b() <= 2) {
                $this$log$iv.a("RealStrongMemoryCache", 2, k.l("trimMemory, level=", Integer.valueOf(level)), (Throwable) null);
            }
        }
        if (level >= 40) {
            f();
        } else {
            boolean z = false;
            if (10 <= level && level < 20) {
                z = true;
            }
            if (z) {
                this.f.trimToSize(h() / 2);
            }
        }
    }

    /* compiled from: StrongMemoryCache.kt */
    public static final class b implements o.a {
        @NotNull
        private final Bitmap a;
        private final boolean b;
        private final int c;

        public b(@NotNull Bitmap bitmap, boolean isSampled, int size) {
            k.e(bitmap, "bitmap");
            this.a = bitmap;
            this.b = isSampled;
            this.c = size;
        }

        @NotNull
        public Bitmap b() {
            return this.a;
        }

        public boolean a() {
            return this.b;
        }

        public final int c() {
            return this.c;
        }
    }

    /* compiled from: StrongMemoryCache.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
