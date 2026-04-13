package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.k0;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.functions.a;
import kotlin.reflect.jvm.internal.impl.builtins.functions.b;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.j;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.name.c;
import kotlin.reflect.jvm.internal.impl.resolve.constants.w;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: functionTypes.kt */
public final class f {
    public static final boolean l(@NotNull b0 $this$isFunctionType) {
        k.f($this$isFunctionType, "$this$isFunctionType");
        h c = $this$isFunctionType.I0().c();
        return (c != null ? e(c) : null) == b.d.Function;
    }

    public static final boolean m(@NotNull b0 $this$isSuspendFunctionType) {
        k.f($this$isSuspendFunctionType, "$this$isSuspendFunctionType");
        h c = $this$isSuspendFunctionType.I0().c();
        return (c != null ? e(c) : null) == b.d.SuspendFunction;
    }

    public static final boolean k(@NotNull b0 $this$isBuiltinFunctionalType) {
        k.f($this$isBuiltinFunctionalType, "$this$isBuiltinFunctionalType");
        h c = $this$isBuiltinFunctionalType.I0().c();
        b.d kind = c != null ? e(c) : null;
        return kind == b.d.Function || kind == b.d.SuspendFunction;
    }

    public static final boolean j(@NotNull b0 $this$isBuiltinExtensionFunctionalType) {
        k.f($this$isBuiltinExtensionFunctionalType, "$this$isBuiltinExtensionFunctionalType");
        return k($this$isBuiltinExtensionFunctionalType) && n($this$isBuiltinExtensionFunctionalType);
    }

    private static final boolean n(@NotNull b0 $this$isTypeAnnotatedWithExtensionFunctionType) {
        g annotations = $this$isTypeAnnotatedWithExtensionFunctionType.getAnnotations();
        kotlin.reflect.jvm.internal.impl.name.b bVar = g.h.A;
        k.b(bVar, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
        return annotations.c(bVar) != null;
    }

    @Nullable
    public static final b.d e(@NotNull m $this$getFunctionalClassKind) {
        k.f($this$getFunctionalClassKind, "$this$getFunctionalClassKind");
        if (($this$getFunctionalClassKind instanceof e) && g.I0($this$getFunctionalClassKind)) {
            return f(a.k($this$getFunctionalClassKind));
        }
        return null;
    }

    private static final b.d f(@NotNull c $this$getFunctionalClassKind) {
        if (!$this$getFunctionalClassKind.f() || $this$getFunctionalClassKind.e()) {
            return null;
        }
        a.C0343a aVar = kotlin.reflect.jvm.internal.impl.builtins.functions.a.a;
        String b = $this$getFunctionalClassKind.i().b();
        k.b(b, "shortName().asString()");
        kotlin.reflect.jvm.internal.impl.name.b e = $this$getFunctionalClassKind.l().e();
        k.b(e, "toSafe().parent()");
        return aVar.b(b, e);
    }

    @Nullable
    public static final b0 g(@NotNull b0 $this$getReceiverTypeFromFunctionType) {
        k.f($this$getReceiverTypeFromFunctionType, "$this$getReceiverTypeFromFunctionType");
        if (!k($this$getReceiverTypeFromFunctionType)) {
            throw new AssertionError("Not a function type: " + $this$getReceiverTypeFromFunctionType);
        } else if (n($this$getReceiverTypeFromFunctionType)) {
            return ((w0) y.S($this$getReceiverTypeFromFunctionType.H0())).getType();
        } else {
            return null;
        }
    }

    @NotNull
    public static final b0 h(@NotNull b0 $this$getReturnTypeFromFunctionType) {
        k.f($this$getReturnTypeFromFunctionType, "$this$getReturnTypeFromFunctionType");
        if (k($this$getReturnTypeFromFunctionType)) {
            b0 type = ((w0) y.d0($this$getReturnTypeFromFunctionType.H0())).getType();
            k.b(type, "arguments.last().type");
            return type;
        }
        throw new AssertionError("Not a function type: " + $this$getReturnTypeFromFunctionType);
    }

    @NotNull
    public static final List<w0> i(@NotNull b0 $this$getValueParameterTypesFromFunctionType) {
        k.f($this$getValueParameterTypesFromFunctionType, "$this$getValueParameterTypesFromFunctionType");
        if (k($this$getValueParameterTypesFromFunctionType)) {
            List arguments = $this$getValueParameterTypesFromFunctionType.H0();
            int first = j($this$getValueParameterTypesFromFunctionType);
            boolean z = true;
            int last = arguments.size() - 1;
            if (first > last) {
                z = false;
            }
            if (z) {
                return arguments.subList(first, last);
            }
            throw new AssertionError("Not an exact function type: " + $this$getValueParameterTypesFromFunctionType);
        }
        throw new AssertionError("Not a function type: " + $this$getValueParameterTypesFromFunctionType);
    }

    @Nullable
    public static final kotlin.reflect.jvm.internal.impl.name.f c(@NotNull b0 $this$extractParameterNameFromFunctionTypeArgument) {
        String it;
        k.f($this$extractParameterNameFromFunctionTypeArgument, "$this$extractParameterNameFromFunctionTypeArgument");
        g annotations = $this$extractParameterNameFromFunctionTypeArgument.getAnnotations();
        kotlin.reflect.jvm.internal.impl.name.b bVar = g.h.B;
        k.b(bVar, "KotlinBuiltIns.FQ_NAMES.parameterName");
        kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotation = annotations.c(bVar);
        if (annotation == null) {
            return null;
        }
        Object r0 = y.r0(annotation.a().values());
        if (!(r0 instanceof w)) {
            r0 = null;
        }
        w wVar = (w) r0;
        if (!(wVar == null || (it = (String) wVar.b()) == null)) {
            if (!kotlin.reflect.jvm.internal.impl.name.f.j(it)) {
                it = null;
            }
            if (it != null) {
                return kotlin.reflect.jvm.internal.impl.name.f.f(it);
            }
        }
        return null;
    }

    @NotNull
    public static final List<w0> d(@Nullable b0 receiverType, @NotNull List<? extends b0> parameterTypes, @Nullable List<kotlin.reflect.jvm.internal.impl.name.f> parameterNames, @NotNull b0 returnType, @NotNull g builtIns) {
        kotlin.reflect.jvm.internal.impl.name.f name;
        Iterable $this$mapIndexedTo$iv;
        b0 typeToUse;
        List<kotlin.reflect.jvm.internal.impl.name.f> list = parameterNames;
        g gVar = builtIns;
        k.f(parameterTypes, "parameterTypes");
        k.f(returnType, "returnType");
        k.f(gVar, "builtIns");
        ArrayList arguments = new ArrayList(parameterTypes.size() + (receiverType != null ? 1 : 0) + 1);
        kotlin.reflect.jvm.internal.impl.utils.a.a(arguments, receiverType != null ? kotlin.reflect.jvm.internal.impl.types.typeUtil.a.a(receiverType) : null);
        Iterable $this$mapIndexedTo$iv2 = parameterTypes;
        int index$iv = 0;
        for (T next : $this$mapIndexedTo$iv2) {
            int index$iv2 = index$iv + 1;
            if (index$iv < 0) {
                q.q();
            }
            b0 type = (b0) next;
            if (list == null || (name = list.get(index$iv)) == null || name.h()) {
                name = null;
            }
            if (name != null) {
                kotlin.reflect.jvm.internal.impl.name.b bVar = g.h.B;
                k.b(bVar, "KotlinBuiltIns.FQ_NAMES.parameterName");
                kotlin.reflect.jvm.internal.impl.name.f f = kotlin.reflect.jvm.internal.impl.name.f.f("name");
                String b = name.b();
                $this$mapIndexedTo$iv = $this$mapIndexedTo$iv2;
                k.b(b, "name.asString()");
                typeToUse = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.m(type, g.b.a(y.m0(type.getAnnotations(), new j(gVar, bVar, k0.c(t.a(f, new w(b)))))));
            } else {
                $this$mapIndexedTo$iv = $this$mapIndexedTo$iv2;
                typeToUse = type;
            }
            arguments.add(kotlin.reflect.jvm.internal.impl.types.typeUtil.a.a(typeToUse));
            List<? extends b0> list2 = parameterTypes;
            list = parameterNames;
            b0 b0Var = returnType;
            index$iv = index$iv2;
            $this$mapIndexedTo$iv2 = $this$mapIndexedTo$iv;
        }
        arguments.add(kotlin.reflect.jvm.internal.impl.types.typeUtil.a.a(returnType));
        return arguments;
    }

    public static /* synthetic */ i0 b(g gVar, g gVar2, b0 b0Var, List list, List list2, b0 b0Var2, boolean z, int i, Object obj) {
        return a(gVar, gVar2, b0Var, list, list2, b0Var2, (i & 64) != 0 ? false : z);
    }

    @NotNull
    public static final i0 a(@NotNull g builtIns, @NotNull g annotations, @Nullable b0 receiverType, @NotNull List<? extends b0> parameterTypes, @Nullable List<kotlin.reflect.jvm.internal.impl.name.f> parameterNames, @NotNull b0 returnType, boolean suspendFunction) {
        g typeAnnotations;
        k.f(builtIns, "builtIns");
        k.f(annotations, "annotations");
        k.f(parameterTypes, "parameterTypes");
        k.f(returnType, "returnType");
        List arguments = d(receiverType, parameterTypes, parameterNames, returnType, builtIns);
        int size = parameterTypes.size();
        int parameterCount = receiverType == null ? size : size + 1;
        e classDescriptor = suspendFunction ? builtIns.Z(parameterCount) : builtIns.C(parameterCount);
        k.b(classDescriptor, "if (suspendFunction) bui…tFunction(parameterCount)");
        if (receiverType != null) {
            g.e eVar = g.h;
            kotlin.reflect.jvm.internal.impl.name.b bVar = eVar.A;
            k.b(bVar, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
            if (annotations.c(bVar) == null) {
                g.a aVar = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b;
                kotlin.reflect.jvm.internal.impl.name.b bVar2 = eVar.A;
                k.b(bVar2, "KotlinBuiltIns.FQ_NAMES.extensionFunctionType");
                typeAnnotations = aVar.a(y.m0(annotations, new j(builtIns, bVar2, l0.f())));
                return c0.g(typeAnnotations, classDescriptor, arguments);
            }
        }
        typeAnnotations = annotations;
        return c0.g(typeAnnotations, classDescriptor, arguments);
    }
}
