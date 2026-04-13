package kotlin.reflect.jvm.internal.impl.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.w;
import kotlin.reflect.jvm.internal.impl.load.kotlin.k;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.d;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: methodSignatureMapping.kt */
public final class t {
    @NotNull
    public static final String b(@NotNull u $this$computeJvmDescriptor, boolean withReturnType, boolean withName) {
        String str;
        k.f($this$computeJvmDescriptor, "$this$computeJvmDescriptor");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        if (withName) {
            if ($this$computeJvmDescriptor instanceof l) {
                str = "<init>";
            } else {
                str = $this$computeJvmDescriptor.getName().b();
                k.b(str, "name.asString()");
            }
            $this$buildString.append(str);
        }
        $this$buildString.append("(");
        for (w0 parameter : $this$computeJvmDescriptor.g()) {
            k.b(parameter, "parameter");
            b0 type = parameter.getType();
            k.b(type, "parameter.type");
            a($this$buildString, type);
        }
        $this$buildString.append(")");
        if (withReturnType) {
            if (a0.d($this$computeJvmDescriptor)) {
                $this$buildString.append(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
            } else {
                b0 returnType = $this$computeJvmDescriptor.getReturnType();
                if (returnType == null) {
                    k.n();
                }
                k.b(returnType, "returnType!!");
                a($this$buildString, returnType);
            }
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    public static /* synthetic */ String c(u uVar, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return b(uVar, z, z2);
    }

    public static final boolean e(@NotNull a f) {
        u overridden;
        k.f(f, "f");
        if (!(f instanceof u) || ((u) f).g().size() != 1 || w.m((b) f) || (!k.a(((u) f).getName().b(), "remove"))) {
            return false;
        }
        u a = ((u) f).a();
        k.b(a, "f.original");
        List<w0> g = a.g();
        k.b(g, "f.original.valueParameters");
        Object q0 = y.q0(g);
        k.b(q0, "f.original.valueParameters.single()");
        b0 type = ((w0) q0).getType();
        k.b(type, "f.original.valueParameters.single().type");
        k g2 = g(type);
        d dVar = null;
        if (!(g2 instanceof k.c)) {
            g2 = null;
        }
        k.c cVar = (k.c) g2;
        if (cVar != null) {
            dVar = cVar.a();
        }
        if (dVar != d.INT || (overridden = kotlin.reflect.jvm.internal.impl.load.java.d.c((u) f)) == null) {
            return false;
        }
        u a2 = overridden.a();
        kotlin.jvm.internal.k.b(a2, "overridden.original");
        List<w0> g3 = a2.g();
        kotlin.jvm.internal.k.b(g3, "overridden.original.valueParameters");
        Object q02 = y.q0(g3);
        kotlin.jvm.internal.k.b(q02, "overridden.original.valueParameters.single()");
        b0 type2 = ((w0) q02).getType();
        kotlin.jvm.internal.k.b(type2, "overridden.original.valueParameters.single().type");
        k overriddenParameterType = g(type2);
        m b = overridden.b();
        kotlin.jvm.internal.k.b(b, "overridden.containingDeclaration");
        if (!kotlin.jvm.internal.k.a(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.k(b), g.h.V.j()) || !(overriddenParameterType instanceof k.b) || !kotlin.jvm.internal.k.a(((k.b) overriddenParameterType).a(), "java/lang/Object")) {
            return false;
        }
        return true;
    }

    @Nullable
    public static final String d(@NotNull a $this$computeJvmSignature) {
        kotlin.jvm.internal.k.f($this$computeJvmSignature, "$this$computeJvmSignature");
        v $this$signatures = v.a;
        if (c.E($this$computeJvmSignature)) {
            return null;
        }
        m b = $this$computeJvmSignature.b();
        if (!(b instanceof e)) {
            b = null;
        }
        e classDescriptor = (e) b;
        if (classDescriptor == null) {
            return null;
        }
        f name = classDescriptor.getName();
        kotlin.jvm.internal.k.b(name, "classDescriptor.name");
        if (name.h()) {
            return null;
        }
        a a = $this$computeJvmSignature.a();
        if (!(a instanceof n0)) {
            a = null;
        }
        n0 n0Var = (n0) a;
        if (n0Var != null) {
            return $this$signatures.l(classDescriptor, c(n0Var, false, false, 3, (Object) null));
        }
        return null;
    }

    @NotNull
    public static final String f(@NotNull e $this$internalName) {
        kotlin.jvm.internal.k.f($this$internalName, "$this$internalName");
        kotlin.reflect.jvm.internal.impl.builtins.jvm.c cVar = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m;
        kotlin.reflect.jvm.internal.impl.name.c j = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j($this$internalName).j();
        kotlin.jvm.internal.k.b(j, "fqNameSafe.toUnsafe()");
        kotlin.reflect.jvm.internal.impl.name.a it = cVar.x(j);
        if (it == null) {
            return a0.c($this$internalName, (w) null, 2, (Object) null);
        }
        kotlin.reflect.jvm.internal.impl.resolve.jvm.c b = kotlin.reflect.jvm.internal.impl.resolve.jvm.c.b(it);
        kotlin.jvm.internal.k.b(b, "JvmClassName.byClassId(it)");
        String f = b.f();
        kotlin.jvm.internal.k.b(f, "JvmClassName.byClassId(it).internalName");
        return f;
    }

    private static final void a(@NotNull StringBuilder $this$appendErasedType, b0 type) {
        $this$appendErasedType.append(g(type));
    }

    @NotNull
    public static final k g(@NotNull b0 $this$mapToJvmType) {
        kotlin.jvm.internal.k.f($this$mapToJvmType, "$this$mapToJvmType");
        return (k) a0.g($this$mapToJvmType, m.a, y.c, x.a, (i) null, (q) null, 32, (Object) null);
    }
}
