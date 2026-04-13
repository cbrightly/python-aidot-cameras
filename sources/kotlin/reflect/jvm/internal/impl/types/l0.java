package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SpecialTypes.kt */
public final class l0 {
    @Nullable
    public static final a a(@NotNull b0 $this$getAbbreviatedType) {
        k.f($this$getAbbreviatedType, "$this$getAbbreviatedType");
        g1 L0 = $this$getAbbreviatedType.L0();
        if (!(L0 instanceof a)) {
            L0 = null;
        }
        return (a) L0;
    }

    @Nullable
    public static final i0 b(@NotNull b0 $this$getAbbreviation) {
        k.f($this$getAbbreviation, "$this$getAbbreviation");
        a a = a($this$getAbbreviation);
        if (a != null) {
            return a.U0();
        }
        return null;
    }

    @NotNull
    public static final i0 h(@NotNull i0 $this$withAbbreviation, @NotNull i0 abbreviatedType) {
        k.f($this$withAbbreviation, "$this$withAbbreviation");
        k.f(abbreviatedType, "abbreviatedType");
        if (d0.a($this$withAbbreviation)) {
            return $this$withAbbreviation;
        }
        return new a($this$withAbbreviation, abbreviatedType);
    }

    public static final boolean c(@NotNull b0 $this$isDefinitelyNotNullType) {
        k.f($this$isDefinitelyNotNullType, "$this$isDefinitelyNotNullType");
        return $this$isDefinitelyNotNullType.L0() instanceof l;
    }

    @NotNull
    public static final i0 g(@NotNull i0 $this$makeSimpleTypeDefinitelyNotNullOrNotNull) {
        k.f($this$makeSimpleTypeDefinitelyNotNullOrNotNull, "$this$makeSimpleTypeDefinitelyNotNullOrNotNull");
        i0 a = l.d.a($this$makeSimpleTypeDefinitelyNotNullOrNotNull);
        if (a == null) {
            a = f($this$makeSimpleTypeDefinitelyNotNullOrNotNull);
        }
        return a != null ? a : $this$makeSimpleTypeDefinitelyNotNullOrNotNull.P0(false);
    }

    @NotNull
    public static final g1 e(@NotNull g1 $this$makeDefinitelyNotNullOrNotNull) {
        k.f($this$makeDefinitelyNotNullOrNotNull, "$this$makeDefinitelyNotNullOrNotNull");
        g1 a = l.d.a($this$makeDefinitelyNotNullOrNotNull);
        if (a == null) {
            a = f($this$makeDefinitelyNotNullOrNotNull);
        }
        return a != null ? a : $this$makeDefinitelyNotNullOrNotNull.M0(false);
    }

    private static final i0 f(@NotNull b0 $this$makeIntersectionTypeDefinitelyNotNullOrNotNull) {
        a0 definitelyNotNullConstructor;
        u0 I0 = $this$makeIntersectionTypeDefinitelyNotNullOrNotNull.I0();
        if (!(I0 instanceof a0)) {
            I0 = null;
        }
        a0 typeConstructor = (a0) I0;
        if (typeConstructor == null || (definitelyNotNullConstructor = d(typeConstructor)) == null) {
            return null;
        }
        return definitelyNotNullConstructor.f();
    }

    private static final a0 d(@NotNull a0 $this$makeDefinitelyNotNullOrNotNull) {
        boolean changed$iv = false;
        Iterable<b0> $this$mapTo$iv$iv$iv = $this$makeDefinitelyNotNullOrNotNull.b();
        List destination$iv$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv$iv, 10));
        for (b0 it$iv : $this$mapTo$iv$iv$iv) {
            if (c1.l(it$iv)) {
                changed$iv = true;
                it$iv = e(it$iv.L0());
            }
            destination$iv$iv$iv.add(it$iv);
        }
        List newSupertypes$iv = destination$iv$iv$iv;
        if (!changed$iv) {
            return null;
        }
        return new a0(newSupertypes$iv);
    }
}
