package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.s;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.v;
import org.jetbrains.annotations.NotNull;

/* compiled from: IntersectionType.kt */
public final class f {
    @NotNull
    public static final g1 a(@NotNull List<? extends g1> types) {
        i0 i0Var;
        k.f(types, "types");
        switch (types.size()) {
            case 0:
                throw new IllegalStateException("Expected some types".toString());
            case 1:
                return (g1) y.q0(types);
            default:
                boolean hasFlexibleTypes = false;
                boolean hasErrorType = false;
                Iterable<g1> $this$mapTo$iv$iv = types;
                List arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                for (g1 it : $this$mapTo$iv$iv) {
                    hasErrorType = hasErrorType || d0.a(it);
                    if (it instanceof i0) {
                        i0Var = (i0) it;
                    } else if (!(it instanceof v)) {
                        throw new NoWhenBranchMatchedException();
                    } else if (s.a(it)) {
                        return it;
                    } else {
                        hasFlexibleTypes = true;
                        i0Var = ((v) it).Q0();
                    }
                    arrayList.add(i0Var);
                }
                List lowerBounds = arrayList;
                if (hasErrorType) {
                    i0 j = u.j("Intersection of error types: " + types);
                    k.b(j, "ErrorUtils.createErrorTy… of error types: $types\")");
                    return j;
                } else if (!hasFlexibleTypes) {
                    return x.a.c(lowerBounds);
                } else {
                    Iterable<g1> $this$mapTo$iv$iv2 = types;
                    List arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
                    for (g1 it2 : $this$mapTo$iv$iv2) {
                        arrayList2.add(kotlin.reflect.jvm.internal.impl.types.y.d(it2));
                    }
                    List upperBounds = arrayList2;
                    x xVar = x.a;
                    return c0.d(xVar.c(lowerBounds), xVar.c(upperBounds));
                }
        }
    }
}
