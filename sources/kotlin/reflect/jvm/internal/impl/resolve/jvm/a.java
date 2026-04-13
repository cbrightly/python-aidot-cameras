package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;

/* compiled from: inlineClassManglingRules.kt */
public final class a {
    public static final boolean f(@NotNull b descriptor) {
        k.f(descriptor, "descriptor");
        d constructorDescriptor = (d) (!(descriptor instanceof d) ? null : descriptor);
        if (constructorDescriptor == null || z0.h(constructorDescriptor.getVisibility())) {
            return false;
        }
        e X = constructorDescriptor.X();
        k.b(X, "constructorDescriptor.constructedClass");
        if (X.isInline() || c.G(constructorDescriptor.X())) {
            return false;
        }
        List<w0> g = constructorDescriptor.g();
        k.b(g, "constructorDescriptor.valueParameters");
        if ((g instanceof Collection) && g.isEmpty()) {
            return false;
        }
        for (w0 it : g) {
            k.b(it, "it");
            b0 type = it.getType();
            k.b(type, "it.type");
            if (e(type)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean b(@NotNull m $this$isInlineClassThatRequiresMangling) {
        k.f($this$isInlineClassThatRequiresMangling, "$this$isInlineClassThatRequiresMangling");
        return kotlin.reflect.jvm.internal.impl.resolve.e.b($this$isInlineClassThatRequiresMangling) && !a((e) $this$isInlineClassThatRequiresMangling);
    }

    public static final boolean c(@NotNull b0 $this$isInlineClassThatRequiresMangling) {
        k.f($this$isInlineClassThatRequiresMangling, "$this$isInlineClassThatRequiresMangling");
        h c = $this$isInlineClassThatRequiresMangling.I0().c();
        return c != null && b(c);
    }

    private static final boolean e(@NotNull b0 $this$requiresFunctionNameMangling) {
        return c($this$requiresFunctionNameMangling) || d($this$requiresFunctionNameMangling);
    }

    private static final boolean a(e classDescriptor) {
        return k.a(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(classDescriptor), c.h);
    }

    private static final boolean d(@NotNull b0 $this$isTypeParameterWithUpperBoundThatRequiresMangling) {
        h c = $this$isTypeParameterWithUpperBoundThatRequiresMangling.I0().c();
        if (!(c instanceof t0)) {
            c = null;
        }
        t0 descriptor = (t0) c;
        if (descriptor != null) {
            return e(kotlin.reflect.jvm.internal.impl.types.typeUtil.a.g(descriptor));
        }
        return false;
    }
}
