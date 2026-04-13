package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.x0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: inlineClassesUtils.kt */
public final class e {
    @Nullable
    public static final w0 f(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e $this$underlyingRepresentation) {
        d B;
        List<w0> g;
        k.f($this$underlyingRepresentation, "$this$underlyingRepresentation");
        if (!$this$underlyingRepresentation.isInline() || (B = $this$underlyingRepresentation.B()) == null || (g = B.g()) == null) {
            return null;
        }
        return (w0) y.s0(g);
    }

    public static final boolean b(@NotNull m $this$isInlineClass) {
        k.f($this$isInlineClass, "$this$isInlineClass");
        return ($this$isInlineClass instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) && ((kotlin.reflect.jvm.internal.impl.descriptors.e) $this$isInlineClass).isInline();
    }

    @Nullable
    public static final w0 g(@NotNull b0 $this$unsubstitutedUnderlyingParameter) {
        k.f($this$unsubstitutedUnderlyingParameter, "$this$unsubstitutedUnderlyingParameter");
        Object $this$safeAs$iv = $this$unsubstitutedUnderlyingParameter.I0().c();
        kotlin.reflect.jvm.internal.impl.descriptors.e eVar = (kotlin.reflect.jvm.internal.impl.descriptors.e) (!($this$safeAs$iv instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) ? null : $this$safeAs$iv);
        if (eVar != null) {
            return f(eVar);
        }
        return null;
    }

    public static final boolean c(@NotNull b0 $this$isInlineClassType) {
        k.f($this$isInlineClassType, "$this$isInlineClassType");
        h c = $this$isInlineClassType.I0().c();
        if (c != null) {
            return b(c);
        }
        return false;
    }

    @Nullable
    public static final b0 e(@NotNull b0 $this$substitutedUnderlyingType) {
        k.f($this$substitutedUnderlyingType, "$this$substitutedUnderlyingType");
        w0 parameter = g($this$substitutedUnderlyingType);
        if (parameter == null) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.resolve.scopes.h l = $this$substitutedUnderlyingType.l();
        f name = parameter.getName();
        k.b(name, "parameter.name");
        i0 i0Var = (i0) y.r0(l.e(name, kotlin.reflect.jvm.internal.impl.incremental.components.d.FOR_ALREADY_TRACKED));
        if (i0Var != null) {
            return i0Var.getType();
        }
        return null;
    }

    public static final boolean a(@NotNull a $this$isGetterOfUnderlyingPropertyOfInlineClass) {
        k.f($this$isGetterOfUnderlyingPropertyOfInlineClass, "$this$isGetterOfUnderlyingPropertyOfInlineClass");
        if ($this$isGetterOfUnderlyingPropertyOfInlineClass instanceof j0) {
            i0 Q = ((j0) $this$isGetterOfUnderlyingPropertyOfInlineClass).Q();
            k.b(Q, "correspondingProperty");
            if (d(Q)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean d(@NotNull x0 $this$isUnderlyingPropertyOfInlineClass) {
        k.f($this$isUnderlyingPropertyOfInlineClass, "$this$isUnderlyingPropertyOfInlineClass");
        m containingDeclaration = $this$isUnderlyingPropertyOfInlineClass.b();
        k.b(containingDeclaration, "this.containingDeclaration");
        if (!b(containingDeclaration)) {
            return false;
        }
        if (containingDeclaration != null) {
            w0 f = f((kotlin.reflect.jvm.internal.impl.descriptors.e) containingDeclaration);
            return k.a(f != null ? f.getName() : null, $this$isUnderlyingPropertyOfInlineClass.getName());
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
    }
}
