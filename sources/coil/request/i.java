package coil.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.MainThread;
import androidx.annotation.Px;
import androidx.lifecycle.Lifecycle;
import coil.decode.e;
import coil.decode.l;
import coil.fetch.g;
import coil.memory.MemoryCache;
import coil.request.j;
import coil.request.l;
import coil.size.OriginalSize;
import coil.size.PixelSize;
import coil.size.Size;
import coil.size.f;
import coil.size.g;
import coil.target.ImageViewTarget;
import coil.target.c;
import coil.transform.d;
import coil.transition.CrossfadeTransition;
import coil.util.h;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.x;
import kotlinx.coroutines.i0;
import okhttp3.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageRequest.kt */
public final class i {
    /* access modifiers changed from: private */
    @Nullable
    public final Integer A;
    /* access modifiers changed from: private */
    @Nullable
    public final Drawable B;
    /* access modifiers changed from: private */
    @Nullable
    public final Integer C;
    /* access modifiers changed from: private */
    @Nullable
    public final Drawable D;
    /* access modifiers changed from: private */
    @Nullable
    public final Integer E;
    /* access modifiers changed from: private */
    @Nullable
    public final Drawable F;
    @NotNull
    private final e G;
    @NotNull
    private final d H;
    @NotNull
    private final Context a;
    @NotNull
    private final Object b;
    @Nullable
    private final coil.target.b c;
    @Nullable
    private final b d;
    @Nullable
    private final MemoryCache.Key e;
    @Nullable
    private final MemoryCache.Key f;
    @Nullable
    private final ColorSpace g;
    @Nullable
    private final n<g<?>, Class<?>> h;
    @Nullable
    private final e i;
    @NotNull
    private final List<d> j;
    @NotNull
    private final u k;
    @NotNull
    private final l l;
    @NotNull
    private final Lifecycle m;
    @NotNull
    private final f n;
    @NotNull
    private final coil.size.e o;
    @NotNull
    private final i0 p;
    @NotNull
    private final coil.transition.b q;
    @NotNull
    private final coil.size.b r;
    @NotNull
    private final Bitmap.Config s;
    private final boolean t;
    private final boolean u;
    private final boolean v;
    private final boolean w;
    @NotNull
    private final c x;
    @NotNull
    private final c y;
    @NotNull
    private final c z;

    /* compiled from: ImageRequest.kt */
    public interface b {
        @MainThread
        void a(@NotNull i iVar);

        @MainThread
        void b(@NotNull i iVar);

        @MainThread
        void c(@NotNull i iVar, @NotNull Throwable th);

        @MainThread
        void d(@NotNull i iVar, @NotNull j.a aVar);
    }

    public /* synthetic */ i(Context context, Object obj, coil.target.b bVar, b bVar2, MemoryCache.Key key, MemoryCache.Key key2, ColorSpace colorSpace, n nVar, e eVar, List list, u uVar, l lVar, Lifecycle lifecycle, f fVar, coil.size.e eVar2, i0 i0Var, coil.transition.b bVar3, coil.size.b bVar4, Bitmap.Config config, boolean z2, boolean z3, boolean z4, boolean z5, c cVar, c cVar2, c cVar3, Integer num, Drawable drawable, Integer num2, Drawable drawable2, Integer num3, Drawable drawable3, e eVar3, d dVar, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, obj, bVar, bVar2, key, key2, colorSpace, nVar, eVar, list, uVar, lVar, lifecycle, fVar, eVar2, i0Var, bVar3, bVar4, config, z2, z3, z4, z5, cVar, cVar2, cVar3, num, drawable, num2, drawable2, num3, drawable3, eVar3, dVar);
    }

    private i(Context context, Object data, coil.target.b target, b listener, MemoryCache.Key memoryCacheKey, MemoryCache.Key placeholderMemoryCacheKey, ColorSpace colorSpace, n<? extends g<?>, ? extends Class<?>> fetcher, e decoder, List<? extends d> transformations, u headers, l parameters, Lifecycle lifecycle, f sizeResolver, coil.size.e scale, i0 dispatcher, coil.transition.b transition, coil.size.b precision, Bitmap.Config bitmapConfig, boolean allowConversionToBitmap, boolean allowHardware, boolean allowRgb565, boolean premultipliedAlpha, c memoryCachePolicy, c diskCachePolicy, c networkCachePolicy, Integer placeholderResId, Drawable placeholderDrawable, Integer errorResId, Drawable errorDrawable, Integer fallbackResId, Drawable fallbackDrawable, e defined, d defaults) {
        this.a = context;
        this.b = data;
        this.c = target;
        this.d = listener;
        this.e = memoryCacheKey;
        this.f = placeholderMemoryCacheKey;
        this.g = colorSpace;
        this.h = fetcher;
        this.i = decoder;
        this.j = transformations;
        this.k = headers;
        this.l = parameters;
        this.m = lifecycle;
        this.n = sizeResolver;
        this.o = scale;
        this.p = dispatcher;
        this.q = transition;
        this.r = precision;
        this.s = bitmapConfig;
        this.t = allowConversionToBitmap;
        this.u = allowHardware;
        this.v = allowRgb565;
        this.w = premultipliedAlpha;
        this.x = memoryCachePolicy;
        this.y = diskCachePolicy;
        this.z = networkCachePolicy;
        this.A = placeholderResId;
        this.B = placeholderDrawable;
        this.C = errorResId;
        this.D = errorDrawable;
        this.E = fallbackResId;
        this.F = fallbackDrawable;
        this.G = defined;
        this.H = defaults;
    }

    @NotNull
    public final Context l() {
        return this.a;
    }

    @NotNull
    public final Object m() {
        return this.b;
    }

    @Nullable
    public final coil.target.b I() {
        return this.c;
    }

    @Nullable
    public final b x() {
        return this.d;
    }

    @Nullable
    public final MemoryCache.Key y() {
        return this.e;
    }

    @Nullable
    public final MemoryCache.Key D() {
        return this.f;
    }

    @Nullable
    public final ColorSpace k() {
        return this.g;
    }

    @Nullable
    public final n<g<?>, Class<?>> u() {
        return this.h;
    }

    @Nullable
    public final e n() {
        return this.i;
    }

    @NotNull
    public final List<d> J() {
        return this.j;
    }

    @NotNull
    public final u v() {
        return this.k;
    }

    @NotNull
    public final l B() {
        return this.l;
    }

    @NotNull
    public final Lifecycle w() {
        return this.m;
    }

    @NotNull
    public final f H() {
        return this.n;
    }

    @NotNull
    public final coil.size.e G() {
        return this.o;
    }

    @NotNull
    public final i0 r() {
        return this.p;
    }

    @NotNull
    public final coil.transition.b K() {
        return this.q;
    }

    @NotNull
    public final coil.size.b E() {
        return this.r;
    }

    @NotNull
    public final Bitmap.Config j() {
        return this.s;
    }

    public final boolean g() {
        return this.t;
    }

    public final boolean h() {
        return this.u;
    }

    public final boolean i() {
        return this.v;
    }

    public final boolean F() {
        return this.w;
    }

    @NotNull
    public final c z() {
        return this.x;
    }

    @NotNull
    public final c q() {
        return this.y;
    }

    @NotNull
    public final c A() {
        return this.z;
    }

    @NotNull
    public final e p() {
        return this.G;
    }

    @NotNull
    public final d o() {
        return this.H;
    }

    @Nullable
    public final Drawable C() {
        return h.c(this, this.B, this.A, this.H.j());
    }

    @Nullable
    public final Drawable s() {
        return h.c(this, this.D, this.C, this.H.f());
    }

    @Nullable
    public final Drawable t() {
        return h.c(this, this.F, this.E, this.H.g());
    }

    public static /* synthetic */ a M(i iVar, Context context, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            context = iVar.a;
        }
        return iVar.L(context);
    }

    @NotNull
    public final a L(@NotNull Context context) {
        k.e(context, "context");
        return new a(this, context);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof i) || !k.a(this.a, ((i) other).a) || !k.a(this.b, ((i) other).b) || !k.a(this.c, ((i) other).c) || !k.a(this.d, ((i) other).d) || !k.a(this.e, ((i) other).e) || !k.a(this.f, ((i) other).f) || ((Build.VERSION.SDK_INT >= 26 && !k.a(this.g, ((i) other).g)) || !k.a(this.h, ((i) other).h) || !k.a(this.i, ((i) other).i) || !k.a(this.j, ((i) other).j) || !k.a(this.k, ((i) other).k) || !k.a(this.l, ((i) other).l) || !k.a(this.m, ((i) other).m) || !k.a(this.n, ((i) other).n) || this.o != ((i) other).o || !k.a(this.p, ((i) other).p) || !k.a(this.q, ((i) other).q) || this.r != ((i) other).r || this.s != ((i) other).s || this.t != ((i) other).t || this.u != ((i) other).u || this.v != ((i) other).v || this.w != ((i) other).w || this.x != ((i) other).x || this.y != ((i) other).y || this.z != ((i) other).z || !k.a(this.A, ((i) other).A) || !k.a(this.B, ((i) other).B) || !k.a(this.C, ((i) other).C) || !k.a(this.D, ((i) other).D) || !k.a(this.E, ((i) other).E) || !k.a(this.F, ((i) other).F) || !k.a(this.G, ((i) other).G) || !k.a(this.H, ((i) other).H))) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = ((this.a.hashCode() * 31) + this.b.hashCode()) * 31;
        coil.target.b bVar = this.c;
        int i2 = 0;
        int result2 = (result + (bVar == null ? 0 : bVar.hashCode())) * 31;
        b bVar2 = this.d;
        int result3 = (result2 + (bVar2 == null ? 0 : bVar2.hashCode())) * 31;
        MemoryCache.Key key = this.e;
        int result4 = (result3 + (key == null ? 0 : key.hashCode())) * 31;
        MemoryCache.Key key2 = this.f;
        int result5 = (result4 + (key2 == null ? 0 : key2.hashCode())) * 31;
        ColorSpace colorSpace = this.g;
        int result6 = (result5 + (colorSpace == null ? 0 : colorSpace.hashCode())) * 31;
        n<g<?>, Class<?>> nVar = this.h;
        int result7 = (result6 + (nVar == null ? 0 : nVar.hashCode())) * 31;
        e eVar = this.i;
        int result8 = (((((((((((((((((((((((((((((((((((result7 + (eVar == null ? 0 : eVar.hashCode())) * 31) + this.j.hashCode()) * 31) + this.k.hashCode()) * 31) + this.l.hashCode()) * 31) + this.m.hashCode()) * 31) + this.n.hashCode()) * 31) + this.o.hashCode()) * 31) + this.p.hashCode()) * 31) + this.q.hashCode()) * 31) + this.r.hashCode()) * 31) + this.s.hashCode()) * 31) + l.a(this.t)) * 31) + l.a(this.u)) * 31) + l.a(this.v)) * 31) + l.a(this.w)) * 31) + this.x.hashCode()) * 31) + this.y.hashCode()) * 31) + this.z.hashCode()) * 31;
        Integer num = this.A;
        int result9 = (result8 + (num == null ? 0 : num.intValue())) * 31;
        Drawable drawable = this.B;
        int result10 = (result9 + (drawable == null ? 0 : drawable.hashCode())) * 31;
        Integer num2 = this.C;
        int result11 = (result10 + (num2 == null ? 0 : num2.intValue())) * 31;
        Drawable drawable2 = this.D;
        int result12 = (result11 + (drawable2 == null ? 0 : drawable2.hashCode())) * 31;
        Integer num3 = this.E;
        int result13 = (result12 + (num3 == null ? 0 : num3.intValue())) * 31;
        Drawable drawable3 = this.F;
        if (drawable3 != null) {
            i2 = drawable3.hashCode();
        }
        return ((((result13 + i2) * 31) + this.G.hashCode()) * 31) + this.H.hashCode();
    }

    @NotNull
    public String toString() {
        return "ImageRequest(context=" + this.a + ", data=" + this.b + ", target=" + this.c + ", listener=" + this.d + ", memoryCacheKey=" + this.e + ", placeholderMemoryCacheKey=" + this.f + ", colorSpace=" + this.g + ", fetcher=" + this.h + ", decoder=" + this.i + ", transformations=" + this.j + ", headers=" + this.k + ", parameters=" + this.l + ", lifecycle=" + this.m + ", sizeResolver=" + this.n + ", scale=" + this.o + ", dispatcher=" + this.p + ", transition=" + this.q + ", precision=" + this.r + ", bitmapConfig=" + this.s + ", allowConversionToBitmap=" + this.t + ", allowHardware=" + this.u + ", allowRgb565=" + this.v + ", premultipliedAlpha=" + this.w + ", memoryCachePolicy=" + this.x + ", diskCachePolicy=" + this.y + ", networkCachePolicy=" + this.z + ", placeholderResId=" + this.A + ", placeholderDrawable=" + this.B + ", errorResId=" + this.C + ", errorDrawable=" + this.D + ", fallbackResId=" + this.E + ", fallbackDrawable=" + this.F + ", defined=" + this.G + ", defaults=" + this.H + ')';
    }

    /* compiled from: ImageRequest.kt */
    public static final class a {
        @Nullable
        private c A;
        @Nullable
        @DrawableRes
        private Integer B;
        @Nullable
        private Drawable C;
        @Nullable
        @DrawableRes
        private Integer D;
        @Nullable
        private Drawable E;
        @Nullable
        @DrawableRes
        private Integer F;
        @Nullable
        private Drawable G;
        @Nullable
        private Lifecycle H;
        @Nullable
        private f I;
        @Nullable
        private coil.size.e J;
        @NotNull
        private final Context a;
        @NotNull
        private d b;
        @Nullable
        private Object c;
        @Nullable
        private coil.target.b d;
        @Nullable
        private b e;
        @Nullable
        private MemoryCache.Key f;
        @Nullable
        private MemoryCache.Key g;
        @Nullable
        private ColorSpace h;
        @Nullable
        private n<? extends g<?>, ? extends Class<?>> i;
        @Nullable
        private e j;
        @NotNull
        private List<? extends d> k;
        @Nullable
        private u.a l;
        @Nullable
        private l.a m;
        @Nullable
        private Lifecycle n;
        @Nullable
        private f o;
        @Nullable
        private coil.size.e p;
        @Nullable
        private i0 q;
        @Nullable
        private coil.transition.b r;
        @Nullable
        private coil.size.b s;
        @Nullable
        private Bitmap.Config t;
        @Nullable
        private Boolean u;
        @Nullable
        private Boolean v;
        private boolean w;
        private boolean x;
        @Nullable
        private c y;
        @Nullable
        private c z;

        public a(@NotNull Context context) {
            k.e(context, "context");
            this.a = context;
            this.b = d.b;
            this.c = null;
            this.d = null;
            this.e = null;
            this.f = null;
            this.g = null;
            if (Build.VERSION.SDK_INT >= 26) {
                this.h = null;
            }
            this.i = null;
            this.j = null;
            this.k = q.g();
            this.l = null;
            this.m = null;
            this.n = null;
            this.o = null;
            this.p = null;
            this.q = null;
            this.r = null;
            this.s = null;
            this.t = null;
            this.u = null;
            this.v = null;
            this.w = true;
            this.x = true;
            this.y = null;
            this.z = null;
            this.A = null;
            this.B = null;
            this.C = null;
            this.D = null;
            this.E = null;
            this.F = null;
            this.G = null;
            this.H = null;
            this.I = null;
            this.J = null;
        }

        public a(@NotNull i request, @NotNull Context context) {
            k.e(request, Progress.REQUEST);
            k.e(context, "context");
            this.a = context;
            this.b = request.o();
            this.c = request.m();
            this.d = request.I();
            this.e = request.x();
            this.f = request.y();
            this.g = request.D();
            if (Build.VERSION.SDK_INT >= 26) {
                this.h = request.k();
            }
            this.i = request.u();
            this.j = request.n();
            this.k = request.J();
            this.l = request.v().f();
            this.m = request.B().e();
            this.n = request.p().f();
            this.o = request.p().k();
            this.p = request.p().j();
            this.q = request.p().e();
            this.r = request.p().l();
            this.s = request.p().i();
            this.t = request.p().c();
            this.u = request.p().a();
            this.v = request.p().b();
            this.w = request.F();
            this.x = request.g();
            this.y = request.p().g();
            this.z = request.p().d();
            this.A = request.p().h();
            this.B = request.A;
            this.C = request.B;
            this.D = request.C;
            this.E = request.D;
            this.F = request.E;
            this.G = request.F;
            if (request.l() == context) {
                this.H = request.w();
                this.I = request.H();
                this.J = request.G();
                return;
            }
            this.H = null;
            this.I = null;
            this.J = null;
        }

        @NotNull
        public final a e(@Nullable Object data) {
            this.c = data;
            return this;
        }

        @NotNull
        public final a j(@Nullable MemoryCache.Key key) {
            this.f = key;
            return this;
        }

        @NotNull
        public final a i(@Nullable b listener) {
            this.e = listener;
            return this;
        }

        @NotNull
        public final a z(@NotNull d... transformations) {
            k.e(transformations, "transformations");
            return y(kotlin.collections.l.U(transformations));
        }

        @NotNull
        public final a y(@NotNull List<? extends d> transformations) {
            k.e(transformations, "transformations");
            this.k = y.C0(transformations);
            return this;
        }

        @NotNull
        public final a s(@Px int size) {
            return t(size, size);
        }

        @NotNull
        public final a t(@Px int width, @Px int height) {
            return u(new PixelSize(width, height));
        }

        @NotNull
        public final a u(@NotNull Size size) {
            k.e(size, "size");
            return v(f.a.a(size));
        }

        @NotNull
        public final a v(@NotNull f resolver) {
            k.e(resolver, "resolver");
            this.o = resolver;
            m();
            return this;
        }

        @NotNull
        public final a a(boolean enable) {
            this.u = Boolean.valueOf(enable);
            return this;
        }

        public static /* synthetic */ a r(a aVar, String str, Object obj, String str2, int i2, Object obj2) {
            if ((i2 & 4) != 0) {
                str2 = obj == null ? null : obj.toString();
            }
            return aVar.q(str, obj, str2);
        }

        @NotNull
        public final a q(@NotNull String key, @Nullable Object value, @Nullable String cacheKey) {
            k.e(key, CacheEntity.KEY);
            l.a $this$setParameter_u24lambda_u2d26_u24lambda_u2d25 = this.m;
            if ($this$setParameter_u24lambda_u2d26_u24lambda_u2d25 == null) {
                $this$setParameter_u24lambda_u2d26_u24lambda_u2d25 = new l.a();
            }
            $this$setParameter_u24lambda_u2d26_u24lambda_u2d25.b(key, value, cacheKey);
            x xVar = x.a;
            this.m = $this$setParameter_u24lambda_u2d26_u24lambda_u2d25;
            return this;
        }

        @NotNull
        public final a k(@Nullable Drawable drawable) {
            this.C = drawable;
            this.B = 0;
            return this;
        }

        @NotNull
        public final a g(@Nullable Drawable drawable) {
            this.E = drawable;
            this.D = 0;
            return this;
        }

        @NotNull
        public final a h(@Nullable Drawable drawable) {
            this.G = drawable;
            this.F = 0;
            return this;
        }

        @NotNull
        public final a w(@NotNull ImageView imageView) {
            k.e(imageView, "imageView");
            return x(new ImageViewTarget(imageView));
        }

        @NotNull
        public final a x(@Nullable coil.target.b target) {
            this.d = target;
            m();
            return this;
        }

        @NotNull
        public final a d(boolean enable) {
            return c(enable ? 100 : 0);
        }

        @NotNull
        public final a c(int durationMillis) {
            return A(durationMillis > 0 ? new CrossfadeTransition(durationMillis, false, 2, (DefaultConstructorMarker) null) : coil.transition.b.b);
        }

        @NotNull
        public final a A(@NotNull coil.transition.b transition) {
            k.e(transition, "transition");
            this.r = transition;
            return this;
        }

        @NotNull
        public final a f(@NotNull d defaults) {
            k.e(defaults, "defaults");
            this.b = defaults;
            l();
            return this;
        }

        @NotNull
        public final i b() {
            Context context = this.a;
            Object obj = this.c;
            if (obj == null) {
                obj = k.a;
            }
            Object obj2 = obj;
            coil.target.b bVar = this.d;
            b bVar2 = this.e;
            MemoryCache.Key key = this.f;
            MemoryCache.Key key2 = this.g;
            ColorSpace colorSpace = this.h;
            n<? extends g<?>, ? extends Class<?>> nVar = this.i;
            e eVar = this.j;
            List<? extends d> list = this.k;
            u.a aVar = this.l;
            l lVar = null;
            u p2 = coil.util.f.p(aVar == null ? null : aVar.f());
            l.a aVar2 = this.m;
            if (aVar2 != null) {
                lVar = aVar2.a();
            }
            l o2 = coil.util.f.o(lVar);
            Lifecycle lifecycle = this.n;
            if (lifecycle == null && (lifecycle = this.H) == null) {
                lifecycle = n();
            }
            Lifecycle lifecycle2 = lifecycle;
            f fVar = this.o;
            if (fVar == null && (fVar = this.I) == null) {
                fVar = p();
            }
            f fVar2 = fVar;
            coil.size.e eVar2 = this.p;
            if (eVar2 == null && (eVar2 = this.J) == null) {
                eVar2 = o();
            }
            coil.size.e eVar3 = eVar2;
            i0 i0Var = this.q;
            if (i0Var == null) {
                i0Var = this.b.e();
            }
            i0 i0Var2 = i0Var;
            coil.transition.b bVar3 = this.r;
            if (bVar3 == null) {
                bVar3 = this.b.l();
            }
            coil.transition.b bVar4 = bVar3;
            coil.size.b bVar5 = this.s;
            if (bVar5 == null) {
                bVar5 = this.b.k();
            }
            coil.size.b bVar6 = bVar5;
            Bitmap.Config config = this.t;
            if (config == null) {
                config = this.b.c();
            }
            Bitmap.Config config2 = config;
            boolean z2 = this.x;
            Boolean bool = this.u;
            boolean a2 = bool == null ? this.b.a() : bool.booleanValue();
            Boolean bool2 = this.v;
            boolean b2 = bool2 == null ? this.b.b() : bool2.booleanValue();
            boolean z3 = this.w;
            c cVar = this.y;
            if (cVar == null) {
                cVar = this.b.h();
            }
            c cVar2 = cVar;
            c cVar3 = this.z;
            if (cVar3 == null) {
                cVar3 = this.b.d();
            }
            c cVar4 = cVar3;
            c cVar5 = this.A;
            if (cVar5 == null) {
                cVar5 = this.b.i();
            }
            c cVar6 = cVar5;
            e eVar4 = r35;
            boolean z4 = z2;
            Lifecycle lifecycle3 = lifecycle2;
            l lVar2 = o2;
            List<? extends d> list2 = list;
            e eVar5 = eVar;
            n<? extends g<?>, ? extends Class<?>> nVar2 = nVar;
            ColorSpace colorSpace2 = colorSpace;
            MemoryCache.Key key3 = key2;
            MemoryCache.Key key4 = key;
            b bVar7 = bVar2;
            e eVar6 = new e(this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.v, this.y, this.z, this.A);
            d dVar = this.b;
            k.d(p2, "orEmpty()");
            return new i(context, obj2, bVar, bVar7, key4, key3, colorSpace2, nVar2, eVar5, list2, p2, lVar2, lifecycle3, fVar2, eVar3, i0Var2, bVar4, bVar6, config2, z4, a2, b2, z3, cVar2, cVar4, cVar6, this.B, this.C, this.D, this.E, this.F, this.G, eVar4, dVar, (DefaultConstructorMarker) null);
        }

        private final void m() {
            this.H = null;
            this.I = null;
            this.J = null;
        }

        private final void l() {
            this.J = null;
        }

        private final Lifecycle n() {
            coil.target.b target = this.d;
            Lifecycle c2 = coil.util.e.c(target instanceof c ? ((c) target).getView().getContext() : this.a);
            return c2 == null ? GlobalLifecycle.a : c2;
        }

        private final f p() {
            coil.target.b target = this.d;
            if (!(target instanceof c)) {
                return new coil.size.a(this.a);
            }
            View view = ((c) target).getView();
            if (view instanceof ImageView) {
                ImageView.ScaleType it = ((ImageView) view).getScaleType();
                if (it == ImageView.ScaleType.CENTER || it == ImageView.ScaleType.MATRIX) {
                    return f.a.a(OriginalSize.c);
                }
            }
            return g.a.b(coil.size.g.b, view, false, 2, (Object) null);
        }

        private final coil.size.e o() {
            f sizeResolver = this.o;
            if (sizeResolver instanceof coil.size.g) {
                View view = ((coil.size.g) sizeResolver).getView();
                if (view instanceof ImageView) {
                    return coil.util.f.h((ImageView) view);
                }
            }
            coil.target.b target = this.d;
            if (target instanceof c) {
                View view2 = ((c) target).getView();
                if (view2 instanceof ImageView) {
                    return coil.util.f.h((ImageView) view2);
                }
            }
            return coil.size.e.FILL;
        }
    }
}
