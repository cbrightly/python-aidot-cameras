package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.renderer.b;
import kotlin.reflect.jvm.internal.impl.renderer.i;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.a1;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.e1;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.v0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y;
import kotlin.reflect.jvm.internal.impl.types.y0;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: CapturedTypeApproximation.kt */
public final class c {
    private static final w0 g(@NotNull d $this$toTypeProjection) {
        if ($this$toTypeProjection.d()) {
            e $fun$removeProjectionIfRedundant$2 = new e($this$toTypeProjection);
            if (k.a($this$toTypeProjection.a(), $this$toTypeProjection.b())) {
                return new y0($this$toTypeProjection.a());
            }
            if (g.w0($this$toTypeProjection.a()) && $this$toTypeProjection.c().y() != h1.IN_VARIANCE) {
                return new y0($fun$removeProjectionIfRedundant$2.invoke(h1.OUT_VARIANCE), $this$toTypeProjection.b());
            }
            if (g.y0($this$toTypeProjection.b())) {
                return new y0($fun$removeProjectionIfRedundant$2.invoke(h1.IN_VARIANCE), $this$toTypeProjection.a());
            }
            return new y0($fun$removeProjectionIfRedundant$2.invoke(h1.OUT_VARIANCE), $this$toTypeProjection.b());
        }
        kotlin.reflect.jvm.internal.impl.renderer.c descriptorRenderer = kotlin.reflect.jvm.internal.impl.renderer.c.k.b(d.INSTANCE);
        throw new AssertionError("Only consistent enhanced type projection can be converted to type projection, but " + '[' + descriptorRenderer.r($this$toTypeProjection.c()) + ": <" + descriptorRenderer.x($this$toTypeProjection.a()) + ", " + descriptorRenderer.x($this$toTypeProjection.b()) + ">]" + " was found");
    }

    /* compiled from: CapturedTypeApproximation.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<i, x> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            k.f($this$withOptions, "$receiver");
            $this$withOptions.n(b.a.a);
        }
    }

    /* compiled from: CapturedTypeApproximation.kt */
    public static final class e extends l implements kotlin.jvm.functions.l<h1, h1> {
        final /* synthetic */ d $this_toTypeProjection;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(d dVar) {
            super(1);
            this.$this_toTypeProjection = dVar;
        }

        @NotNull
        public final h1 invoke(@NotNull h1 variance) {
            k.f(variance, "variance");
            return variance == this.$this_toTypeProjection.c().y() ? h1.INVARIANT : variance;
        }
    }

    private static final d f(@NotNull w0 $this$toTypeArgument, t0 typeParameter) {
        switch (b.a[TypeSubstitutor.c(typeParameter.y(), $this$toTypeArgument).ordinal()]) {
            case 1:
                b0 type = $this$toTypeArgument.getType();
                k.b(type, IjkMediaMeta.IJKM_KEY_TYPE);
                b0 type2 = $this$toTypeArgument.getType();
                k.b(type2, IjkMediaMeta.IJKM_KEY_TYPE);
                return new d(typeParameter, type, type2);
            case 2:
                b0 type3 = $this$toTypeArgument.getType();
                k.b(type3, IjkMediaMeta.IJKM_KEY_TYPE);
                i0 K = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(typeParameter).K();
                k.b(K, "typeParameter.builtIns.nullableAnyType");
                return new d(typeParameter, type3, K);
            case 3:
                i0 J = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(typeParameter).J();
                k.b(J, "typeParameter.builtIns.nothingType");
                b0 type4 = $this$toTypeArgument.getType();
                k.b(type4, IjkMediaMeta.IJKM_KEY_TYPE);
                return new d(typeParameter, J, type4);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Nullable
    public static final w0 b(@Nullable w0 typeProjection, boolean approximateContravariant) {
        if (typeProjection == null) {
            return null;
        }
        if (typeProjection.b()) {
            return typeProjection;
        }
        b0 type = typeProjection.getType();
        k.b(type, "typeProjection.type");
        if (!c1.c(type, b.INSTANCE)) {
            return typeProjection;
        }
        h1 howThisTypeIsUsed = typeProjection.c();
        k.b(howThisTypeIsUsed, "typeProjection.projectionKind");
        if (howThisTypeIsUsed == h1.OUT_VARIANCE) {
            return new y0(howThisTypeIsUsed, a(type).d());
        }
        if (approximateContravariant) {
            return new y0(howThisTypeIsUsed, a(type).c());
        }
        return e(typeProjection);
    }

    /* compiled from: CapturedTypeApproximation.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<g1, Boolean> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((g1) obj));
        }

        public final boolean invoke(g1 it) {
            k.b(it, "it");
            return kotlin.reflect.jvm.internal.impl.resolve.calls.inference.d.d(it);
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.types.typesApproximation.c$c  reason: collision with other inner class name */
    /* compiled from: CapturedTypeApproximation.kt */
    public static final class C0429c extends v0 {
        C0429c() {
        }

        @Nullable
        public w0 j(@NotNull u0 key) {
            k.f(key, CacheEntity.KEY);
            kotlin.reflect.jvm.internal.impl.resolve.calls.inference.b capturedTypeConstructor = (kotlin.reflect.jvm.internal.impl.resolve.calls.inference.b) (!(key instanceof kotlin.reflect.jvm.internal.impl.resolve.calls.inference.b) ? null : key);
            if (capturedTypeConstructor == null) {
                return null;
            }
            if (capturedTypeConstructor.getProjection().b()) {
                return new y0(h1.OUT_VARIANCE, capturedTypeConstructor.getProjection().getType());
            }
            return capturedTypeConstructor.getProjection();
        }
    }

    private static final w0 e(w0 typeProjection) {
        TypeSubstitutor typeSubstitutor = TypeSubstitutor.g(new C0429c());
        k.b(typeSubstitutor, "TypeSubstitutor.create(o…ojection\n        }\n    })");
        return typeSubstitutor.r(typeProjection);
    }

    @NotNull
    public static final a<b0> a(@NotNull b0 type) {
        Object obj;
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (y.b(type)) {
            a boundsForFlexibleLower = a(y.c(type));
            a boundsForFlexibleUpper = a(y.d(type));
            return new a<>(e1.b(c0.d(y.c(boundsForFlexibleLower.c()), y.d(boundsForFlexibleUpper.c())), type), e1.b(c0.d(y.c(boundsForFlexibleLower.d()), y.d(boundsForFlexibleUpper.d())), type));
        }
        u0 typeConstructor = type.I0();
        if (kotlin.reflect.jvm.internal.impl.resolve.calls.inference.d.d(type)) {
            if (typeConstructor != null) {
                w0 typeProjection = ((kotlin.reflect.jvm.internal.impl.resolve.calls.inference.b) typeConstructor).getProjection();
                a $fun$makeNullableIfNeeded$1 = new a(type);
                b0 type2 = typeProjection.getType();
                k.b(type2, "typeProjection.type");
                b0 bound = $fun$makeNullableIfNeeded$1.invoke(type2);
                switch (b.b[typeProjection.c().ordinal()]) {
                    case 1:
                        i0 K = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(type).K();
                        k.b(K, "type.builtIns.nullableAnyType");
                        return new a<>(bound, K);
                    case 2:
                        i0 J = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(type).J();
                        k.b(J, "type.builtIns.nothingType");
                        return new a<>($fun$makeNullableIfNeeded$1.invoke((b0) J), bound);
                    default:
                        throw new AssertionError("Only nontrivial projections should have been captured, not: " + typeProjection);
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor");
            }
        } else if (type.H0().isEmpty() || type.H0().size() != typeConstructor.getParameters().size()) {
            return new a<>(type, type);
        } else {
            ArrayList lowerBoundArguments = new ArrayList();
            ArrayList upperBoundArguments = new ArrayList();
            List<w0> H0 = type.H0();
            List<t0> parameters = typeConstructor.getParameters();
            k.b(parameters, "typeConstructor.parameters");
            for (n next : kotlin.collections.y.K0(H0, parameters)) {
                w0 typeProjection2 = (w0) next.component1();
                t0 typeParameter = (t0) next.component2();
                k.b(typeParameter, "typeParameter");
                d typeArgument = f(typeProjection2, typeParameter);
                if (typeProjection2.b()) {
                    lowerBoundArguments.add(typeArgument);
                    upperBoundArguments.add(typeArgument);
                } else {
                    a<d> c = c(typeArgument);
                    lowerBoundArguments.add(c.a());
                    upperBoundArguments.add(c.b());
                }
            }
            ArrayList arrayList = lowerBoundArguments;
            boolean lowerBoundIsTrivial = true;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!((d) it.next()).d()) {
                            break;
                        }
                    } else {
                        lowerBoundIsTrivial = false;
                        break;
                    }
                }
            } else {
                lowerBoundIsTrivial = false;
            }
            if (lowerBoundIsTrivial) {
                obj = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.f(type).J();
                k.b(obj, "type.builtIns.nothingType");
            } else {
                obj = d(type, lowerBoundArguments);
            }
            return new a<>(obj, d(type, upperBoundArguments));
        }
    }

    /* compiled from: CapturedTypeApproximation.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<b0, b0> {
        final /* synthetic */ b0 $type;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(b0 b0Var) {
            super(1);
            this.$type = b0Var;
        }

        @NotNull
        public final b0 invoke(@NotNull b0 $this$makeNullableIfNeeded) {
            k.f($this$makeNullableIfNeeded, "$this$makeNullableIfNeeded");
            b0 q = c1.q($this$makeNullableIfNeeded, this.$type.J0());
            k.b(q, "TypeUtils.makeNullableIf…s, type.isMarkedNullable)");
            return q;
        }
    }

    private static final b0 d(@NotNull b0 $this$replaceTypeArguments, List<d> newTypeArguments) {
        if ($this$replaceTypeArguments.H0().size() == newTypeArguments.size()) {
            Iterable<d> $this$mapTo$iv$iv = newTypeArguments;
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (d it : $this$mapTo$iv$iv) {
                arrayList.add(g(it));
            }
            return a1.d($this$replaceTypeArguments, arrayList, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.g) null, 2, (Object) null);
        }
        throw new AssertionError("Incorrect type arguments " + newTypeArguments);
    }

    private static final a<d> c(d typeArgument) {
        a<b0> a2 = a(typeArgument.a());
        a<b0> a3 = a(typeArgument.b());
        return new a<>(new d(typeArgument.c(), a2.b(), a3.a()), new d(typeArgument.c(), a2.a(), a3.b()));
    }
}
