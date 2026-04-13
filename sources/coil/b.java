package coil;

import coil.decode.e;
import coil.fetch.g;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ComponentRegistry.kt */
public final class b {
    @NotNull
    private final List<coil.intercept.b> a;
    @NotNull
    private final List<n<coil.map.b<? extends Object, ?>, Class<? extends Object>>> b;
    @NotNull
    private final List<n<g<? extends Object>, Class<? extends Object>>> c;
    @NotNull
    private final List<e> d;

    public /* synthetic */ b(List list, List list2, List list3, List list4, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, list2, list3, list4);
    }

    private b(List<? extends coil.intercept.b> interceptors, List<? extends n<? extends coil.map.b<? extends Object, ?>, ? extends Class<? extends Object>>> mappers, List<? extends n<? extends g<? extends Object>, ? extends Class<? extends Object>>> fetchers, List<? extends e> decoders) {
        this.a = interceptors;
        this.b = mappers;
        this.c = fetchers;
        this.d = decoders;
    }

    @NotNull
    public final List<coil.intercept.b> c() {
        return this.a;
    }

    @NotNull
    public final List<n<coil.map.b<? extends Object, ?>, Class<? extends Object>>> d() {
        return this.b;
    }

    @NotNull
    public final List<n<g<? extends Object>, Class<? extends Object>>> b() {
        return this.c;
    }

    @NotNull
    public final List<e> a() {
        return this.d;
    }

    public b() {
        this(q.g(), q.g(), q.g(), q.g());
    }

    @NotNull
    public final a e() {
        return new a(this);
    }

    /* compiled from: ComponentRegistry.kt */
    public static final class a {
        @NotNull
        private final List<coil.intercept.b> a;
        @NotNull
        private final List<n<coil.map.b<? extends Object, ?>, Class<? extends Object>>> b;
        @NotNull
        private final List<n<g<? extends Object>, Class<? extends Object>>> c;
        @NotNull
        private final List<e> d;

        public a() {
            this.a = new ArrayList();
            this.b = new ArrayList();
            this.c = new ArrayList();
            this.d = new ArrayList();
        }

        public a(@NotNull b registry) {
            k.e(registry, "registry");
            this.a = y.F0(registry.c());
            this.b = y.F0(registry.d());
            this.c = y.F0(registry.b());
            this.d = y.F0(registry.a());
        }

        @NotNull
        public final <T> a c(@NotNull coil.map.b<T, ?> mapper, @NotNull Class<T> type) {
            k.e(mapper, "mapper");
            k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
            this.b.add(t.a(mapper, type));
            return this;
        }

        @NotNull
        public final <T> a b(@NotNull g<T> fetcher, @NotNull Class<T> type) {
            k.e(fetcher, "fetcher");
            k.e(type, IjkMediaMeta.IJKM_KEY_TYPE);
            this.c.add(t.a(fetcher, type));
            return this;
        }

        @NotNull
        public final a a(@NotNull e decoder) {
            k.e(decoder, "decoder");
            this.d.add(decoder);
            return this;
        }

        @NotNull
        public final b d() {
            return new b(y.C0(this.a), y.C0(this.b), y.C0(this.c), y.C0(this.d), (DefaultConstructorMarker) null);
        }
    }
}
