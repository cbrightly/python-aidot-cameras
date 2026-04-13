package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.NoWhenBranchMatchedException;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.f;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.j;
import kotlin.reflect.jvm.internal.impl.metadata.k;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.metadata.x;
import kotlin.reflect.jvm.internal.impl.types.h1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ProtoEnumFlags.kt */
public final class c0 {
    public static final c0 a = new c0();

    private c0() {
    }

    @NotNull
    public final b.a b(@Nullable j memberKind) {
        if (memberKind != null) {
            switch (b0.a[memberKind.ordinal()]) {
                case 1:
                    return b.a.DECLARATION;
                case 2:
                    return b.a.FAKE_OVERRIDE;
                case 3:
                    return b.a.DELEGATION;
                case 4:
                    return b.a.SYNTHESIZED;
            }
        }
        return b.a.DECLARATION;
    }

    @NotNull
    public final w c(@Nullable k modality) {
        if (modality != null) {
            switch (b0.c[modality.ordinal()]) {
                case 1:
                    return w.FINAL;
                case 2:
                    return w.OPEN;
                case 3:
                    return w.ABSTRACT;
                case 4:
                    return w.SEALED;
            }
        }
        return w.FINAL;
    }

    @NotNull
    public final a1 f(@Nullable x visibility) {
        a1 a1Var;
        if (visibility != null) {
            switch (b0.e[visibility.ordinal()]) {
                case 1:
                    a1Var = z0.d;
                    break;
                case 2:
                    a1Var = z0.a;
                    break;
                case 3:
                    a1Var = z0.b;
                    break;
                case 4:
                    a1Var = z0.c;
                    break;
                case 5:
                    a1Var = z0.e;
                    break;
                case 6:
                    a1Var = z0.f;
                    break;
            }
        }
        a1Var = z0.a;
        kotlin.jvm.internal.k.b(a1Var, "when (visibility) {\n    …isibilities.PRIVATE\n    }");
        return a1Var;
    }

    @NotNull
    public final f a(@Nullable c.C0384c kind) {
        if (kind != null) {
            switch (b0.f[kind.ordinal()]) {
                case 1:
                    return f.CLASS;
                case 2:
                    return f.INTERFACE;
                case 3:
                    return f.ENUM_CLASS;
                case 4:
                    return f.ENUM_ENTRY;
                case 5:
                    return f.ANNOTATION_CLASS;
                case 6:
                case 7:
                    return f.OBJECT;
            }
        }
        return f.CLASS;
    }

    @NotNull
    public final h1 e(@NotNull s.c variance) {
        kotlin.jvm.internal.k.f(variance, "variance");
        switch (b0.h[variance.ordinal()]) {
            case 1:
                return h1.IN_VARIANCE;
            case 2:
                return h1.OUT_VARIANCE;
            case 3:
                return h1.INVARIANT;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public final h1 d(@NotNull q.b.c projection) {
        kotlin.jvm.internal.k.f(projection, "projection");
        switch (b0.i[projection.ordinal()]) {
            case 1:
                return h1.IN_VARIANCE;
            case 2:
                return h1.OUT_VARIANCE;
            case 3:
                return h1.INVARIANT;
            case 4:
                throw new IllegalArgumentException("Only IN, OUT and INV are supported. Actual argument: " + projection);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
