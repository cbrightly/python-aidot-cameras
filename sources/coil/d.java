package coil;

import android.content.Context;
import coil.bitmap.g;
import coil.bitmap.h;
import coil.c;
import coil.memory.MemoryCache;
import coil.memory.o;
import coil.memory.p;
import coil.memory.r;
import coil.memory.u;
import coil.request.f;
import coil.request.i;
import coil.request.j;
import coil.util.l;
import coil.util.m;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.e;
import okhttp3.z;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageLoader.kt */
public interface d {
    @NotNull
    public static final b a = b.a;

    @NotNull
    f a(@NotNull i iVar);

    @Nullable
    Object b(@NotNull i iVar, @NotNull kotlin.coroutines.d<? super j> dVar);

    @NotNull
    MemoryCache c();

    /* compiled from: ImageLoader.kt */
    public static final class a {
        /* access modifiers changed from: private */
        @NotNull
        public final Context a;
        @NotNull
        private coil.request.d b = coil.request.d.b;
        @Nullable
        private e.a c = null;
        @Nullable
        private c.d d = null;
        @Nullable
        private b e = null;
        @NotNull
        private l f = new l(false, false, false, 7, (DefaultConstructorMarker) null);
        @Nullable
        private m g = null;
        @Nullable
        private o h = null;
        private double i;
        private double j;
        private boolean k;
        private boolean l;

        public a(@NotNull Context context) {
            k.e(context, "context");
            Context applicationContext = context.getApplicationContext();
            k.d(applicationContext, "context.applicationContext");
            this.a = applicationContext;
            coil.util.o oVar = coil.util.o.a;
            this.i = oVar.e(applicationContext);
            this.j = oVar.f();
            this.k = true;
            this.l = true;
        }

        @NotNull
        public final a g(@NotNull kotlin.jvm.functions.a<? extends z> initializer) {
            k.e(initializer, "initializer");
            return e(initializer);
        }

        @NotNull
        public final a e(@NotNull kotlin.jvm.functions.a<? extends e.a> initializer) {
            k.e(initializer, "initializer");
            this.c = coil.util.f.m(initializer);
            return this;
        }

        @NotNull
        public final a f(@NotNull b registry) {
            k.e(registry, "registry");
            this.e = registry;
            return this;
        }

        @NotNull
        public final d b() {
            o oVar = this.h;
            if (oVar == null) {
                oVar = d();
            }
            o memoryCache = oVar;
            Context context = this.a;
            coil.request.d dVar = this.b;
            coil.bitmap.c a2 = memoryCache.a();
            e.a aVar = this.c;
            if (aVar == null) {
                aVar = c();
            }
            e.a aVar2 = aVar;
            c.d dVar2 = this.d;
            if (dVar2 == null) {
                dVar2 = c.d.b;
            }
            c.d dVar3 = dVar2;
            b bVar = this.e;
            if (bVar == null) {
                bVar = new b();
            }
            return new e(context, dVar, a2, memoryCache, aVar2, dVar3, bVar, this.f, this.g);
        }

        /* renamed from: coil.d$a$a  reason: collision with other inner class name */
        /* compiled from: ImageLoader.kt */
        public static final class C0002a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<e.a> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0002a(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final e.a invoke() {
                z.a aVar = new z.a();
                coil.util.j jVar = coil.util.j.a;
                z c = aVar.d(coil.util.j.a(this.this$0.a)).c();
                k.d(c, "Builder()\n                .cache(CoilUtils.createDefaultCache(applicationContext))\n                .build()");
                return c;
            }
        }

        private final e.a c() {
            return coil.util.f.m(new C0002a(this));
        }

        private final o d() {
            coil.bitmap.c bitmapPool;
            u weakMemoryCache;
            coil.bitmap.e referenceCounter;
            long availableMemorySize = coil.util.o.a.b(this.a, this.i);
            int bitmapPoolSize = (int) (((double) availableMemorySize) * (this.k ? this.j : 0.0d));
            int memoryCacheSize = (int) (availableMemorySize - ((long) bitmapPoolSize));
            if (bitmapPoolSize == 0) {
                bitmapPool = new coil.bitmap.f();
            } else {
                bitmapPool = new h(bitmapPoolSize, (Set) null, (coil.bitmap.d) null, this.g, 6, (DefaultConstructorMarker) null);
            }
            if (this.l) {
                weakMemoryCache = new p(this.g);
            } else {
                weakMemoryCache = coil.memory.e.a;
            }
            if (this.k) {
                referenceCounter = new coil.bitmap.i(weakMemoryCache, bitmapPool, this.g);
            } else {
                referenceCounter = g.a;
            }
            return new o(r.a.a(weakMemoryCache, referenceCounter, memoryCacheSize, this.g), weakMemoryCache, referenceCounter, bitmapPool);
        }
    }

    /* compiled from: ImageLoader.kt */
    public static final class b {
        static final /* synthetic */ b a = new b();

        private b() {
        }
    }
}
