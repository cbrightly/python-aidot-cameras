package kotlin.reflect.full;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.m;
import kotlin.reflect.jvm.internal.i;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.n0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import kotlin.reflect.jvm.internal.w;
import kotlin.reflect.jvm.internal.y;
import kotlin.reflect.n;
import kotlin.reflect.p;
import org.jetbrains.annotations.NotNull;

/* compiled from: KClassifiers.kt */
public final class d {
    public static /* synthetic */ n c(kotlin.reflect.d dVar, List list, boolean z, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = q.g();
        }
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            list2 = q.g();
        }
        return b(dVar, list, z, list2);
    }

    @NotNull
    public static final n b(@NotNull kotlin.reflect.d $this$createType, @NotNull List<p> arguments, boolean nullable, @NotNull List<? extends Annotation> annotations) {
        h descriptor;
        g typeAnnotations;
        k.f($this$createType, "$this$createType");
        k.f(arguments, "arguments");
        k.f(annotations, "annotations");
        i iVar = (i) (!($this$createType instanceof i) ? null : $this$createType);
        if (iVar == null || (descriptor = iVar.c()) == null) {
            throw new y("Cannot create type for an unsupported classifier: " + $this$createType + " (" + $this$createType.getClass() + ')');
        }
        u0 typeConstructor = descriptor.i();
        k.b(typeConstructor, "descriptor.typeConstructor");
        List parameters = typeConstructor.getParameters();
        k.b(parameters, "typeConstructor.parameters");
        if (parameters.size() == arguments.size()) {
            if (annotations.isEmpty()) {
                typeAnnotations = g.b.b();
            } else {
                typeAnnotations = g.b.b();
            }
            return new w(a(typeAnnotations, typeConstructor, arguments, nullable), new a($this$createType));
        }
        throw new IllegalArgumentException("Class declares " + parameters.size() + " type parameters, but " + arguments.size() + " were provided.");
    }

    /* compiled from: KClassifiers.kt */
    public static final class a extends l implements kotlin.jvm.functions.a {
        final /* synthetic */ kotlin.reflect.d $this_createType;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(kotlin.reflect.d dVar) {
            super(0);
            this.$this_createType = dVar;
        }

        @NotNull
        public final Void invoke() {
            throw new m("An operation is not implemented: " + ("Java type is not yet supported for types created with createType (classifier = " + this.$this_createType + ')'));
        }
    }

    private static final i0 a(g typeAnnotations, u0 typeConstructor, List<p> arguments, boolean nullable) {
        Object obj;
        List parameters = typeConstructor.getParameters();
        k.b(parameters, "typeConstructor.parameters");
        Iterable $this$mapIndexedTo$iv$iv = arguments;
        ArrayList arrayList = new ArrayList(r.r($this$mapIndexedTo$iv$iv, 10));
        int index$iv$iv = 0;
        for (T next : $this$mapIndexedTo$iv$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                q.q();
            }
            p typeProjection = (p) next;
            w wVar = (w) typeProjection.a();
            b0 type = wVar != null ? wVar.e() : null;
            kotlin.reflect.r b = typeProjection.b();
            if (b == null) {
                t0 t0Var = parameters.get(index$iv$iv);
                k.b(t0Var, "parameters[index]");
                obj = new n0(t0Var);
            } else {
                switch (c.a[b.ordinal()]) {
                    case 1:
                        h1 h1Var = h1.INVARIANT;
                        if (type == null) {
                            k.n();
                        }
                        obj = new y0(h1Var, type);
                        break;
                    case 2:
                        h1 h1Var2 = h1.IN_VARIANCE;
                        if (type == null) {
                            k.n();
                        }
                        obj = new y0(h1Var2, type);
                        break;
                    case 3:
                        h1 h1Var3 = h1.OUT_VARIANCE;
                        if (type == null) {
                            k.n();
                        }
                        obj = new y0(h1Var3, type);
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
            arrayList.add(obj);
            index$iv$iv = index$iv$iv2;
        }
        return c0.i(typeAnnotations, typeConstructor, arrayList, nullable, (kotlin.reflect.jvm.internal.impl.types.checker.i) null, 16, (Object) null);
    }

    @NotNull
    public static final n d(@NotNull kotlin.reflect.d $this$starProjectedType) {
        h descriptor;
        k.f($this$starProjectedType, "$this$starProjectedType");
        i iVar = (i) (!($this$starProjectedType instanceof i) ? null : $this$starProjectedType);
        if (iVar == null || (descriptor = iVar.c()) == null) {
            return c($this$starProjectedType, (List) null, false, (List) null, 7, (Object) null);
        }
        u0 i = descriptor.i();
        k.b(i, "descriptor.typeConstructor");
        List<t0> parameters = i.getParameters();
        k.b(parameters, "descriptor.typeConstructor.parameters");
        if (parameters.isEmpty()) {
            return c($this$starProjectedType, (List) null, false, (List) null, 7, (Object) null);
        }
        Iterable<t0> $this$mapTo$iv$iv = parameters;
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (t0 t0Var : $this$mapTo$iv$iv) {
            arrayList.add(p.b.c());
        }
        return c($this$starProjectedType, arrayList, false, (List) null, 6, (Object) null);
    }
}
