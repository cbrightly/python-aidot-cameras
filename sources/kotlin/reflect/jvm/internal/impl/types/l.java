package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.checker.p;
import kotlin.reflect.jvm.internal.impl.types.model.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: SpecialTypes.kt */
public final class l extends n implements k, d {
    public static final a d = new a((DefaultConstructorMarker) null);
    @NotNull
    private final i0 f;

    private l(i0 original) {
        this.f = original;
    }

    public /* synthetic */ l(i0 original, DefaultConstructorMarker $constructor_marker) {
        this(original);
    }

    @NotNull
    public final i0 U0() {
        return this.f;
    }

    /* compiled from: SpecialTypes.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final l a(@NotNull g1 type) {
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            if (type instanceof l) {
                return (l) type;
            }
            if (!b(type)) {
                return null;
            }
            if (!(type instanceof v) || k.a(((v) type).Q0().I0(), ((v) type).R0().I0())) {
                return new l(y.c(type), (DefaultConstructorMarker) null);
            }
            throw new AssertionError("DefinitelyNotNullType for flexible type (" + type + ") can be created only from type variable with the same constructor for bounds");
        }

        private final boolean b(g1 type) {
            return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.b(type) && !p.a.a(type);
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public i0 R0() {
        return this.f;
    }

    public boolean J0() {
        return false;
    }

    public boolean u() {
        R0().I0();
        return R0().I0().c() instanceof t0;
    }

    @NotNull
    public b0 c0(@NotNull b0 replacement) {
        k.f(replacement, "replacement");
        return l0.e(replacement.L0());
    }

    @NotNull
    /* renamed from: V0 */
    public l Q0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        return new l(R0().Q0(newAnnotations));
    }

    @NotNull
    /* renamed from: P0 */
    public i0 M0(boolean newNullability) {
        return newNullability ? R0().P0(newNullability) : this;
    }

    @NotNull
    public String toString() {
        return R0() + "!!";
    }

    @NotNull
    /* renamed from: W0 */
    public l T0(@NotNull i0 delegate) {
        k.f(delegate, "delegate");
        return new l(delegate);
    }
}
