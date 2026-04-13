package kotlin.reflect.jvm.internal.impl.builtins;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.p;
import kotlin.collections.r;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.f;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.m;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.y;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: suspendFunctionTypes.kt */
public final class k {
    private static final y a;
    private static final y b;

    static {
        kotlin.reflect.jvm.internal.impl.descriptors.y q = u.q();
        kotlin.jvm.internal.k.b(q, "ErrorUtils.getErrorModule()");
        b bVar = c.d;
        kotlin.jvm.internal.k.b(bVar, "DescriptorUtils.COROUTIN…KAGE_FQ_NAME_EXPERIMENTAL");
        m mVar = new m(q, bVar);
        f fVar = f.INTERFACE;
        kotlin.reflect.jvm.internal.impl.name.f g = c.f.g();
        o0 o0Var = o0.a;
        j jVar = kotlin.reflect.jvm.internal.impl.storage.b.b;
        y $this$apply = new y(mVar, fVar, false, false, g, o0Var, jVar);
        w wVar = w.ABSTRACT;
        $this$apply.C0(wVar);
        a1 a1Var = z0.e;
        $this$apply.H0(a1Var);
        g.a aVar = g.b;
        g b2 = aVar.b();
        h1 h1Var = h1.IN_VARIANCE;
        j jVar2 = jVar;
        $this$apply.G0(p.b(j0.J0($this$apply, b2, false, h1Var, kotlin.reflect.jvm.internal.impl.name.f.f(ExifInterface.GPS_DIRECTION_TRUE), 0, jVar2)));
        $this$apply.l0();
        a = $this$apply;
        kotlin.reflect.jvm.internal.impl.descriptors.y q2 = u.q();
        kotlin.jvm.internal.k.b(q2, "ErrorUtils.getErrorModule()");
        b bVar2 = c.c;
        kotlin.jvm.internal.k.b(bVar2, "DescriptorUtils.COROUTINES_PACKAGE_FQ_NAME_RELEASE");
        y yVar = new y(new m(q2, bVar2), fVar, false, false, c.g.g(), o0Var, jVar);
        y $this$apply2 = yVar;
        $this$apply2.C0(wVar);
        $this$apply2.H0(a1Var);
        $this$apply2.G0(p.b(j0.J0($this$apply2, aVar.b(), false, h1Var, kotlin.reflect.jvm.internal.impl.name.f.f(ExifInterface.GPS_DIRECTION_TRUE), 0, jVar2)));
        $this$apply2.l0();
        b = yVar;
    }

    @NotNull
    public static final i0 b(@NotNull b0 suspendFunType, boolean isReleaseCoroutines) {
        u0 u0Var;
        kotlin.jvm.internal.k.f(suspendFunType, "suspendFunType");
        if (f.m(suspendFunType)) {
            g f = a.f(suspendFunType);
            g annotations = suspendFunType.getAnnotations();
            b0 g = f.g(suspendFunType);
            Iterable<w0> $this$mapTo$iv$iv = f.i(suspendFunType);
            Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (w0 p1 : $this$mapTo$iv$iv) {
                destination$iv$iv.add(p1.getType());
            }
            g b2 = g.b.b();
            if (isReleaseCoroutines) {
                u0Var = b.i();
            } else {
                u0Var = a.i();
            }
            kotlin.jvm.internal.k.b(u0Var, "if (isReleaseCoroutines)…ERIMENTAL.typeConstructor");
            List o0 = kotlin.collections.y.o0(destination$iv$iv, c0.i(b2, u0Var, p.b(a.a(f.h(suspendFunType))), false, (i) null, 16, (Object) null));
            i0 K = a.f(suspendFunType).K();
            kotlin.jvm.internal.k.b(K, "suspendFunType.builtIns.nullableAnyType");
            return f.b(f, annotations, g, o0, (List) null, K, false, 64, (Object) null).P0(suspendFunType.J0());
        }
        throw new AssertionError("This type should be suspend function type: " + suspendFunType);
    }

    public static final boolean a(@Nullable b name, boolean isReleaseCoroutines) {
        if (isReleaseCoroutines) {
            return kotlin.jvm.internal.k.a(name, c.g);
        }
        return kotlin.jvm.internal.k.a(name, c.f);
    }
}
