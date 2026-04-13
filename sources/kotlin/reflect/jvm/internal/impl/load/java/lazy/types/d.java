package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.load.java.components.l;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.n0;
import kotlin.reflect.jvm.internal.impl.types.o0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaTypeResolver.kt */
public final class d {
    /* access modifiers changed from: private */
    public static final b a = new b("java.lang.Class");

    @NotNull
    public static final w0 d(@NotNull t0 typeParameter, @NotNull a attr) {
        k.f(typeParameter, "typeParameter");
        k.f(attr, "attr");
        if (attr.d() == l.SUPERTYPE) {
            return new y0(o0.a(typeParameter));
        }
        return new n0(typeParameter);
    }

    public static /* synthetic */ a f(l lVar, boolean z, t0 t0Var, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            t0Var = null;
        }
        return e(lVar, z, t0Var);
    }

    @NotNull
    public static final a e(@NotNull l $this$toAttributes, boolean isForAnnotationParameter, @Nullable t0 upperBoundForTypeParameter) {
        k.f($this$toAttributes, "$this$toAttributes");
        return new a($this$toAttributes, (b) null, isForAnnotationParameter, upperBoundForTypeParameter, 2, (DefaultConstructorMarker) null);
    }

    /* compiled from: JavaTypeResolver.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ t0 $this_getErasedUpperBound;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(t0 t0Var) {
            super(0);
            this.$this_getErasedUpperBound = t0Var;
        }

        @NotNull
        public final i0 invoke() {
            i0 j = u.j("Can't compute erased upper bound of type parameter `" + this.$this_getErasedUpperBound + '`');
            k.b(j, "ErrorUtils.createErrorTy… type parameter `$this`\")");
            return j;
        }
    }

    public static /* synthetic */ b0 c(t0 t0Var, t0 t0Var2, kotlin.jvm.functions.a aVar, int i, Object obj) {
        if ((i & 1) != 0) {
            t0Var2 = null;
        }
        if ((i & 2) != 0) {
            aVar = new a(t0Var);
        }
        return b(t0Var, t0Var2, aVar);
    }

    @NotNull
    public static final b0 b(@NotNull t0 $this$getErasedUpperBound, @Nullable t0 potentiallyRecursiveTypeParameter, @NotNull kotlin.jvm.functions.a<? extends b0> defaultValue) {
        k.f($this$getErasedUpperBound, "$this$getErasedUpperBound");
        k.f(defaultValue, "defaultValue");
        if ($this$getErasedUpperBound == potentiallyRecursiveTypeParameter) {
            return (b0) defaultValue.invoke();
        }
        List<b0> upperBounds = $this$getErasedUpperBound.getUpperBounds();
        k.b(upperBounds, "upperBounds");
        b0 firstUpperBound = (b0) y.S(upperBounds);
        if (firstUpperBound.I0().c() instanceof e) {
            k.b(firstUpperBound, "firstUpperBound");
            return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.n(firstUpperBound);
        }
        t0 stopAt = potentiallyRecursiveTypeParameter != null ? potentiallyRecursiveTypeParameter : $this$getErasedUpperBound;
        h c = firstUpperBound.I0().c();
        if (c != null) {
            while (true) {
                t0 current = (t0) c;
                if (!(!k.a(current, stopAt))) {
                    return (b0) defaultValue.invoke();
                }
                List<b0> upperBounds2 = current.getUpperBounds();
                k.b(upperBounds2, "current.upperBounds");
                b0 nextUpperBound = (b0) y.S(upperBounds2);
                if (nextUpperBound.I0().c() instanceof e) {
                    k.b(nextUpperBound, "nextUpperBound");
                    return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.n(nextUpperBound);
                }
                h c2 = nextUpperBound.I0().c();
                if (c2 != null) {
                    c = c2;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
                }
            }
        } else {
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeParameterDescriptor");
        }
    }
}
