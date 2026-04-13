package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.ArrayList;
import java.util.Collection;
import kotlin.collections.l0;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import kotlin.reflect.jvm.internal.impl.types.v0;
import org.jetbrains.annotations.NotNull;

/* compiled from: mappingUtil.kt */
public final class j {
    @NotNull
    public static final v0 a(@NotNull e from, @NotNull e to) {
        k.f(from, "from");
        k.f(to, TypedValues.TransitionType.S_TO);
        if (from.o().size() == to.o().size()) {
            v0.a aVar = v0.c;
            Iterable<t0> $this$mapTo$iv$iv = from.o();
            k.b($this$mapTo$iv$iv, "from.declaredTypeParameters");
            Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (t0 p1 : $this$mapTo$iv$iv) {
                destination$iv$iv.add(p1.i());
            }
            Iterable<t0> $this$mapTo$iv$iv2 = to.o();
            k.b($this$mapTo$iv$iv2, "to.declaredTypeParameters");
            Collection destination$iv$iv2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
            for (t0 it : $this$mapTo$iv$iv2) {
                k.b(it, "it");
                i0 m = it.m();
                k.b(m, "it.defaultType");
                destination$iv$iv2.add(a.a(m));
            }
            return v0.a.d(aVar, l0.o(y.K0(destination$iv$iv, destination$iv$iv2)), false, 2, (Object) null);
        }
        throw new AssertionError(from + " and " + to + " should have same number of type parameters, " + "but " + from.o().size() + " / " + to.o().size() + " found");
    }
}
