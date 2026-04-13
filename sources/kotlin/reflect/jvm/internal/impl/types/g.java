package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.c;
import kotlin.reflect.jvm.internal.impl.types.model.h;
import kotlin.reflect.jvm.internal.impl.types.model.i;
import kotlin.reflect.jvm.internal.impl.types.model.j;
import kotlin.reflect.jvm.internal.impl.types.model.k;
import kotlin.reflect.jvm.internal.impl.types.model.m;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: AbstractTypeChecker.kt */
public abstract class g implements m {
    /* access modifiers changed from: private */
    public int a;
    private boolean b;
    @Nullable
    private ArrayDeque<h> c;
    @Nullable
    private Set<h> d;

    /* compiled from: AbstractTypeChecker.kt */
    public enum a {
        CHECK_ONLY_LOWER,
        CHECK_SUBTYPE_AND_LOWER,
        SKIP_LOWER
    }

    @NotNull
    public abstract k H(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    @NotNull
    public abstract h T(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    @NotNull
    public abstract j f(@NotNull i iVar, int i);

    public abstract boolean g0(@NotNull k kVar, @NotNull k kVar2);

    @Nullable
    public abstract List<h> i0(@NotNull h hVar, @NotNull k kVar);

    @Nullable
    public abstract j j0(@NotNull h hVar, int i);

    @NotNull
    public abstract h m(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    public abstract boolean n0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    public abstract boolean p0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    public abstract boolean q0(@NotNull h hVar);

    public abstract boolean r0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    public abstract boolean s0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    public abstract boolean t0();

    public abstract boolean u0(@NotNull h hVar);

    public abstract boolean v0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    public abstract boolean w0();

    @NotNull
    public abstract kotlin.reflect.jvm.internal.impl.types.model.g x0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    @NotNull
    public abstract kotlin.reflect.jvm.internal.impl.types.model.g y0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar);

    @NotNull
    public abstract b z0(@NotNull h hVar);

    @NotNull
    public a k0(@NotNull h subType, @NotNull c superType) {
        kotlin.jvm.internal.k.f(subType, "subType");
        kotlin.jvm.internal.k.f(superType, "superType");
        return a.CHECK_SUBTYPE_AND_LOWER;
    }

    @Nullable
    public Boolean f0(@NotNull kotlin.reflect.jvm.internal.impl.types.model.g subType, @NotNull kotlin.reflect.jvm.internal.impl.types.model.g superType) {
        kotlin.jvm.internal.k.f(subType, "subType");
        kotlin.jvm.internal.k.f(superType, "superType");
        return null;
    }

    @Nullable
    public final ArrayDeque<h> l0() {
        return this.c;
    }

    @Nullable
    public final Set<h> m0() {
        return this.d;
    }

    public final void o0() {
        if (!this.b) {
            this.b = true;
            if (this.c == null) {
                this.c = new ArrayDeque<>(4);
            }
            if (this.d == null) {
                this.d = kotlin.reflect.jvm.internal.impl.utils.j.c.a();
                return;
            }
            return;
        }
        throw new AssertionError("Assertion failed");
    }

    public final void h0() {
        ArrayDeque<h> arrayDeque = this.c;
        if (arrayDeque == null) {
            kotlin.jvm.internal.k.n();
        }
        arrayDeque.clear();
        Set<h> set = this.d;
        if (set == null) {
            kotlin.jvm.internal.k.n();
        }
        set.clear();
        this.b = false;
    }

    /* compiled from: AbstractTypeChecker.kt */
    public static abstract class b {
        @NotNull
        public abstract h a(@NotNull g gVar, @NotNull kotlin.reflect.jvm.internal.impl.types.model.g gVar2);

        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* compiled from: AbstractTypeChecker.kt */
        public static final class c extends b {
            public static final c a = new c();

            private c() {
                super((DefaultConstructorMarker) null);
            }

            public /* bridge */ /* synthetic */ h a(g gVar, kotlin.reflect.jvm.internal.impl.types.model.g gVar2) {
                return (h) b(gVar, gVar2);
            }

            @NotNull
            public Void b(@NotNull g context, @NotNull kotlin.reflect.jvm.internal.impl.types.model.g type) {
                kotlin.jvm.internal.k.f(context, "context");
                kotlin.jvm.internal.k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                throw new UnsupportedOperationException("Should not be called");
            }
        }

        /* compiled from: AbstractTypeChecker.kt */
        public static final class d extends b {
            public static final d a = new d();

            private d() {
                super((DefaultConstructorMarker) null);
            }

            @NotNull
            public h a(@NotNull g context, @NotNull kotlin.reflect.jvm.internal.impl.types.model.g type) {
                kotlin.jvm.internal.k.f(context, "context");
                kotlin.jvm.internal.k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                return context.m(type);
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.types.g$b$b  reason: collision with other inner class name */
        /* compiled from: AbstractTypeChecker.kt */
        public static final class C0426b extends b {
            public static final C0426b a = new C0426b();

            private C0426b() {
                super((DefaultConstructorMarker) null);
            }

            @NotNull
            public h a(@NotNull g context, @NotNull kotlin.reflect.jvm.internal.impl.types.model.g type) {
                kotlin.jvm.internal.k.f(context, "context");
                kotlin.jvm.internal.k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                return context.T(type);
            }
        }

        /* compiled from: AbstractTypeChecker.kt */
        public static abstract class a extends b {
            public a() {
                super((DefaultConstructorMarker) null);
            }
        }
    }
}
