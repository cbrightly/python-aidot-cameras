package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.p;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.load.java.components.l;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y;
import kotlin.reflect.jvm.internal.impl.types.y0;
import kotlin.reflect.jvm.internal.impl.types.z0;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RawType.kt */
public final class f extends z0 {
    private static final a c;
    private static final a d;
    public static final f e = new f();

    static {
        l lVar = l.COMMON;
        c = d.f(lVar, false, (t0) null, 3, (Object) null).g(b.FLEXIBLE_LOWER_BOUND);
        d = d.f(lVar, false, (t0) null, 3, (Object) null).g(b.FLEXIBLE_UPPER_BOUND);
    }

    private f() {
    }

    @NotNull
    /* renamed from: m */
    public y0 e(@NotNull b0 key) {
        k.f(key, CacheEntity.KEY);
        return new y0(l(key));
    }

    private final b0 l(b0 type) {
        b0 b0Var;
        h declaration = type.I0().c();
        if (declaration instanceof t0) {
            return l(d.c((t0) declaration, (t0) null, (kotlin.jvm.functions.a) null, 3, (Object) null));
        }
        if (declaration instanceof e) {
            h declarationForUpper = y.d(type).I0().c();
            if (declarationForUpper instanceof e) {
                n<i0, Boolean> k = k(y.c(type), (e) declaration, c);
                i0 lower = k.component1();
                boolean isRawL = k.component2().booleanValue();
                n<i0, Boolean> k2 = k(y.d(type), (e) declarationForUpper, d);
                i0 upper = k2.component1();
                boolean isRawU = k2.component2().booleanValue();
                if (isRawL || isRawU) {
                    b0Var = new g(lower, upper);
                } else {
                    b0Var = c0.d(lower, upper);
                }
                return b0Var;
            }
            throw new IllegalStateException(("For some reason declaration for upper bound is not a class " + "but \"" + declarationForUpper + "\" while for lower it's \"" + declaration + StringUtil.DOUBLE_QUOTE).toString());
        }
        throw new IllegalStateException(("Unexpected declaration kind: " + declaration).toString());
    }

    /* access modifiers changed from: private */
    public final n<i0, Boolean> k(i0 type, e declaration, a attr) {
        i0 i0Var = type;
        e eVar = declaration;
        if (type.I0().getParameters().isEmpty()) {
            return t.a(i0Var, false);
        }
        if (g.e0(type)) {
            w0 componentTypeProjection = type.H0().get(0);
            h1 c2 = componentTypeProjection.c();
            b0 type2 = componentTypeProjection.getType();
            k.b(type2, "componentTypeProjection.type");
            return t.a(c0.i(type.getAnnotations(), type.I0(), p.b(new y0(c2, l(type2))), type.J0(), (i) null, 16, (Object) null), false);
        }
        if (d0.a(type)) {
            return t.a(u.j("Raw error type: " + type.I0()), false);
        }
        kotlin.reflect.jvm.internal.impl.resolve.scopes.h memberScope = eVar.m0(e);
        k.b(memberScope, "declaration.getMemberScope(RawSubstitution)");
        kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations = type.getAnnotations();
        u0 i = declaration.i();
        k.b(i, "declaration.typeConstructor");
        u0 i2 = declaration.i();
        k.b(i2, "declaration.typeConstructor");
        Iterable<t0> $this$mapTo$iv$iv = i2.getParameters();
        k.b($this$mapTo$iv$iv, "declaration.typeConstructor.parameters");
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (t0 parameter : $this$mapTo$iv$iv) {
            f fVar = e;
            k.b(parameter, "parameter");
            t0 t0Var = parameter;
            arrayList.add(j(fVar, parameter, attr, (b0) null, 4, (Object) null));
        }
        return t.a(c0.k(annotations, i, arrayList, type.J0(), memberScope, new a(eVar, i0Var, attr)), true);
    }

    /* compiled from: RawType.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, i0> {
        final /* synthetic */ a $attr;
        final /* synthetic */ e $declaration;
        final /* synthetic */ i0 $type;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(e eVar, i0 i0Var, a aVar) {
            super(1);
            this.$declaration = eVar;
            this.$type = i0Var;
            this.$attr = aVar;
        }

        @Nullable
        public final i0 invoke(@NotNull i kotlinTypeRefiner) {
            kotlin.reflect.jvm.internal.impl.name.a classId;
            e refinedClassDescriptor;
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            e eVar = this.$declaration;
            if (!(eVar instanceof e)) {
                eVar = null;
            }
            if (eVar == null || (classId = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.i(eVar)) == null || (refinedClassDescriptor = kotlinTypeRefiner.a(classId)) == null || k.a(refinedClassDescriptor, this.$declaration)) {
                return null;
            }
            return (i0) f.e.k(this.$type, refinedClassDescriptor, this.$attr).getFirst();
        }
    }

    public static /* synthetic */ w0 j(f fVar, t0 t0Var, a aVar, b0 b0Var, int i, Object obj) {
        if ((i & 4) != 0) {
            b0Var = d.c(t0Var, (t0) null, (kotlin.jvm.functions.a) null, 3, (Object) null);
        }
        return fVar.i(t0Var, aVar, b0Var);
    }

    @NotNull
    public final w0 i(@NotNull t0 parameter, @NotNull a attr, @NotNull b0 erasedUpperBound) {
        k.f(parameter, "parameter");
        k.f(attr, "attr");
        k.f(erasedUpperBound, "erasedUpperBound");
        switch (e.a[attr.c().ordinal()]) {
            case 1:
                return new y0(h1.INVARIANT, erasedUpperBound);
            case 2:
            case 3:
                if (!parameter.y().getAllowsOutPosition()) {
                    return new y0(h1.INVARIANT, kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(parameter).J());
                }
                List<t0> parameters = erasedUpperBound.I0().getParameters();
                k.b(parameters, "erasedUpperBound.constructor.parameters");
                if (!parameters.isEmpty()) {
                    return new y0(h1.OUT_VARIANCE, erasedUpperBound);
                }
                return d.d(parameter, attr);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public boolean f() {
        return false;
    }
}
