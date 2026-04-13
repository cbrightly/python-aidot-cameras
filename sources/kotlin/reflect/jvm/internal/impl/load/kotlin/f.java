package kotlin.reflect.jvm.internal.impl.load.kotlin;

import java.util.HashSet;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.b1;
import kotlin.reflect.jvm.internal.impl.types.model.g;
import kotlin.reflect.jvm.internal.impl.types.model.h;
import kotlin.reflect.jvm.internal.impl.types.model.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: inlineClassMapping.kt */
public final class f {
    @Nullable
    public static final g a(@NotNull b1 $this$computeExpandedTypeForInlineClass, @NotNull g inlineClassType) {
        k.f($this$computeExpandedTypeForInlineClass, "$this$computeExpandedTypeForInlineClass");
        k.f(inlineClassType, "inlineClassType");
        return b($this$computeExpandedTypeForInlineClass, inlineClassType, new HashSet());
    }

    private static final g b(@NotNull b1 $this$computeExpandedTypeInner, g kotlinType, HashSet<kotlin.reflect.jvm.internal.impl.types.model.k> visitedClassifiers) {
        g b;
        g expandedUpperBound;
        kotlin.reflect.jvm.internal.impl.types.model.k classifier = $this$computeExpandedTypeInner.H(kotlinType);
        if (!visitedClassifiers.add(classifier)) {
            return null;
        }
        l typeParameter = $this$computeExpandedTypeInner.h(classifier);
        if (typeParameter != null) {
            g b2 = b($this$computeExpandedTypeInner, $this$computeExpandedTypeInner.D(typeParameter), visitedClassifiers);
            if (b2 == null) {
                return null;
            }
            g expandedUpperBound2 = b2;
            if ($this$computeExpandedTypeInner.v(expandedUpperBound2) || !$this$computeExpandedTypeInner.W(kotlinType)) {
                expandedUpperBound = expandedUpperBound2;
            } else {
                expandedUpperBound = $this$computeExpandedTypeInner.c0(expandedUpperBound2);
            }
            return expandedUpperBound;
        }
        if ($this$computeExpandedTypeInner.p(classifier)) {
            g underlyingType = $this$computeExpandedTypeInner.Q(kotlinType);
            if (underlyingType == null || (b = b($this$computeExpandedTypeInner, underlyingType, visitedClassifiers)) == null) {
                return null;
            }
            g expandedUnderlyingType = b;
            if (!$this$computeExpandedTypeInner.v(kotlinType)) {
                return expandedUnderlyingType;
            }
            if (!$this$computeExpandedTypeInner.v(expandedUnderlyingType) && (!(expandedUnderlyingType instanceof h) || !$this$computeExpandedTypeInner.z((h) expandedUnderlyingType))) {
                return $this$computeExpandedTypeInner.c0(expandedUnderlyingType);
            }
        }
        return kotlinType;
    }
}
