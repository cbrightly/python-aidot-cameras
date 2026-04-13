package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.model.b;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import kotlin.reflect.jvm.internal.impl.types.v0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: NewCapturedType.kt */
public final class m {
    @Nullable
    public static final i0 a(@NotNull i0 type, @NotNull b status) {
        g1 lowerType;
        w0 it;
        b bVar = status;
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.f(bVar, "status");
        if (type.H0().size() != type.I0().getParameters().size()) {
            return null;
        }
        List arguments = type.H0();
        List list = arguments;
        boolean z = true;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it2 = list.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (((w0) it2.next()).c() == h1.INVARIANT) {
                        it = 1;
                        continue;
                    } else {
                        it = null;
                        continue;
                    }
                    if (it == null) {
                        z = false;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (z) {
            return null;
        }
        List<t0> parameters = type.I0().getParameters();
        k.b(parameters, "type.constructor.parameters");
        Iterable<n> $this$mapTo$iv$iv = y.K0(arguments, parameters);
        List arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (n $dstr$projection$parameter : $this$mapTo$iv$iv) {
            w0 projection = (w0) $dstr$projection$parameter.component1();
            t0 parameter = (t0) $dstr$projection$parameter.component2();
            if (projection.c() != h1.INVARIANT) {
                if (projection.b() || projection.c() != h1.IN_VARIANCE) {
                    lowerType = null;
                } else {
                    lowerType = projection.getType().L0();
                }
                k.b(parameter, "parameter");
                projection = a.a(new k(bVar, lowerType, projection, parameter));
            }
            arrayList.add(projection);
            i0 i0Var = type;
        }
        List capturedArguments = arrayList;
        TypeSubstitutor substitutor = v0.c.b(type.I0(), capturedArguments).c();
        int size = arguments.size();
        int i = 0;
        while (i < size) {
            int index = i;
            w0 oldProjection = arguments.get(index);
            w0 newProjection = (w0) capturedArguments.get(index);
            if (oldProjection.c() != h1.INVARIANT) {
                t0 t0Var = type.I0().getParameters().get(index);
                k.b(t0Var, "type.constructor.parameters[index]");
                Iterable<b0> $this$mapTo$iv = t0Var.getUpperBounds();
                k.b($this$mapTo$iv, "type.constructor.parameters[index].upperBounds");
                List arrayList2 = new ArrayList();
                for (b0 it3 : $this$mapTo$iv) {
                    arrayList2.add(n.b.a().h(substitutor.l(it3, h1.INVARIANT).L0()));
                    b bVar2 = status;
                }
                List capturedTypeSupertypes = arrayList2;
                if (!oldProjection.b() && oldProjection.c() == h1.OUT_VARIANCE) {
                    capturedTypeSupertypes.add(n.b.a().h(oldProjection.getType().L0()));
                }
                b0 type2 = newProjection.getType();
                if (type2 != null) {
                    ((k) type2).I0().h(capturedTypeSupertypes);
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedType");
                }
            }
            i = index + 1;
            b bVar3 = status;
        }
        return c0.i(type.getAnnotations(), type.I0(), capturedArguments, type.J0(), (i) null, 16, (Object) null);
    }
}
