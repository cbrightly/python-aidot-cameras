package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: constantValues.kt */
public final class r extends g<b> {
    public static final a b = new a((DefaultConstructorMarker) null);

    /* compiled from: constantValues.kt */
    public static abstract class b {

        /* renamed from: kotlin.reflect.jvm.internal.impl.resolve.constants.r$b$b  reason: collision with other inner class name */
        /* compiled from: constantValues.kt */
        public static final class C0408b extends b {
            @NotNull
            private final f a;

            public boolean equals(@Nullable Object obj) {
                if (this != obj) {
                    return (obj instanceof C0408b) && k.a(this.a, ((C0408b) obj).a);
                }
                return true;
            }

            public int hashCode() {
                f fVar = this.a;
                if (fVar != null) {
                    return fVar.hashCode();
                }
                return 0;
            }

            @NotNull
            public String toString() {
                return "NormalClass(value=" + this.a + ")";
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public C0408b(@NotNull f value) {
                super((DefaultConstructorMarker) null);
                k.f(value, "value");
                this.a = value;
            }

            @NotNull
            public final f c() {
                return this.a;
            }

            @NotNull
            public final kotlin.reflect.jvm.internal.impl.name.a b() {
                return this.a.d();
            }

            public final int a() {
                return this.a.c();
            }
        }

        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* compiled from: constantValues.kt */
        public static final class a extends b {
            @NotNull
            private final b0 a;

            public boolean equals(@Nullable Object obj) {
                if (this != obj) {
                    return (obj instanceof a) && k.a(this.a, ((a) obj).a);
                }
                return true;
            }

            public int hashCode() {
                b0 b0Var = this.a;
                if (b0Var != null) {
                    return b0Var.hashCode();
                }
                return 0;
            }

            @NotNull
            public String toString() {
                return "LocalClass(type=" + this.a + ")";
            }

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(@NotNull b0 type) {
                super((DefaultConstructorMarker) null);
                k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
                this.a = type;
            }

            @NotNull
            public final b0 a() {
                return this.a;
            }
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public r(@NotNull b value) {
        super(value);
        k.f(value, "value");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public r(@NotNull f value) {
        this((b) new b.C0408b(value));
        k.f(value, "value");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public r(@NotNull kotlin.reflect.jvm.internal.impl.name.a classId, int arrayDimensions) {
        this(new f(classId, arrayDimensions));
        k.f(classId, "classId");
    }

    @NotNull
    public b0 a(@NotNull y module) {
        k.f(module, "module");
        g b2 = g.b.b();
        e G = module.j().G();
        k.b(G, "module.builtIns.kClass");
        return c0.g(b2, G, p.b(new y0(c(module))));
    }

    @NotNull
    public final b0 c(@NotNull y module) {
        k.f(module, "module");
        b bVar = (b) b();
        if (bVar instanceof b.a) {
            return ((b.a) b()).a();
        }
        if (bVar instanceof b.C0408b) {
            f c = ((b.C0408b) b()).c();
            kotlin.reflect.jvm.internal.impl.name.a classId = c.a();
            int arrayDimensions = c.b();
            e descriptor = t.a(module, classId);
            if (descriptor != null) {
                i0 m = descriptor.m();
                k.b(m, "descriptor.defaultType");
                b0 n = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.n(m);
                for (int i = 0; i < arrayDimensions; i++) {
                    int i2 = i;
                    i0 m2 = module.j().m(h1.INVARIANT, n);
                    k.b(m2, "module.builtIns.getArray…Variance.INVARIANT, type)");
                    n = m2;
                }
                return n;
            }
            i0 j = u.j("Unresolved type: " + classId + " (arrayDimensions=" + arrayDimensions + ')');
            k.b(j, "ErrorUtils.createErrorTy…sions=$arrayDimensions)\")");
            return j;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* compiled from: constantValues.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final g<?> a(@NotNull b0 argumentType) {
            k.f(argumentType, "argumentType");
            if (d0.a(argumentType)) {
                return null;
            }
            b0 type = argumentType;
            int arrayDimensions = 0;
            while (kotlin.reflect.jvm.internal.impl.builtins.g.e0(type)) {
                b0 type2 = ((w0) kotlin.collections.y.q0(type.H0())).getType();
                k.b(type2, "type.arguments.single().type");
                type = type2;
                arrayDimensions++;
            }
            h descriptor = type.I0().c();
            if (descriptor instanceof e) {
                kotlin.reflect.jvm.internal.impl.name.a classId = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.i(descriptor);
                if (classId != null) {
                    return new r(classId, arrayDimensions);
                }
                return new r((b) new b.a(argumentType));
            } else if (!(descriptor instanceof t0)) {
                return null;
            } else {
                kotlin.reflect.jvm.internal.impl.name.a m = kotlin.reflect.jvm.internal.impl.name.a.m(kotlin.reflect.jvm.internal.impl.builtins.g.h.a.l());
                k.b(m, "ClassId.topLevel(KotlinB…ns.FQ_NAMES.any.toSafe())");
                return new r(m, 0);
            }
        }
    }
}
