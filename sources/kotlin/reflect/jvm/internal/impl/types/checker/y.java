package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.d;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.typesApproximation.c;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.v0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.text.s;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: utils.kt */
public final class y {
    @Nullable
    public static final b0 c(@NotNull b0 subtype, @NotNull b0 supertype, @NotNull w typeCheckingProcedureCallbacks) {
        boolean z;
        boolean z2;
        boolean z3;
        b0 b0Var;
        b0 b0Var2 = subtype;
        w wVar = typeCheckingProcedureCallbacks;
        k.f(b0Var2, "subtype");
        k.f(supertype, "supertype");
        k.f(wVar, "typeCheckingProcedureCallbacks");
        ArrayDeque queue = new ArrayDeque();
        queue.add(new t(b0Var2, (t) null));
        u0 supertypeConstructor = supertype.I0();
        while (!queue.isEmpty()) {
            t lastPathNode = (t) queue.poll();
            b0 currentSubtype = lastPathNode.b();
            u0 constructor = currentSubtype.I0();
            if (wVar.c(constructor, supertypeConstructor)) {
                b0 substituted = currentSubtype;
                boolean isAnyMarkedNullable = currentSubtype.J0();
                t currentPathNode = lastPathNode.a();
                while (currentPathNode != null) {
                    b0 currentType = currentPathNode.b();
                    List<w0> H0 = currentType.H0();
                    if (!(H0 instanceof Collection) || !H0.isEmpty()) {
                        Iterator<T> it = H0.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z = false;
                                break;
                            }
                            if (((w0) it.next()).c() != h1.INVARIANT) {
                                z = true;
                                break;
                            }
                            b0 b0Var3 = subtype;
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        z3 = true;
                        z2 = false;
                        b0 l = d.f(v0.c.a(currentType), false, 1, (Object) null).c().l(substituted, h1.INVARIANT);
                        k.b(l, "TypeConstructorSubstitut…uted, Variance.INVARIANT)");
                        b0Var = a(l);
                    } else {
                        z3 = true;
                        z2 = false;
                        b0 l2 = v0.c.a(currentType).c().l(substituted, h1.INVARIANT);
                        k.b(l2, "TypeConstructorSubstitut…uted, Variance.INVARIANT)");
                        b0Var = l2;
                    }
                    substituted = b0Var;
                    isAnyMarkedNullable = (isAnyMarkedNullable || currentType.J0()) ? z3 : z2;
                    currentPathNode = currentPathNode.a();
                    b0 b0Var4 = subtype;
                }
                u0 substitutedConstructor = substituted.I0();
                if (wVar.c(substitutedConstructor, supertypeConstructor)) {
                    return c1.p(substituted, isAnyMarkedNullable);
                }
                throw new AssertionError("Type constructors should be equals!\n" + "substitutedSuperType: " + b(substitutedConstructor) + ", \n\n" + "supertype: " + b(supertypeConstructor) + " \n" + wVar.c(substitutedConstructor, supertypeConstructor));
            }
            for (b0 immediateSupertype : constructor.b()) {
                k.b(immediateSupertype, "immediateSupertype");
                queue.add(new t(immediateSupertype, lastPathNode));
            }
            b0 b0Var5 = subtype;
        }
        return null;
    }

    private static final b0 a(@NotNull b0 $this$approximate) {
        return c.a($this$approximate).d();
    }

    /* compiled from: utils.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<String, StringBuilder> {
        final /* synthetic */ StringBuilder $this_buildString;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(StringBuilder sb) {
            super(1);
            this.$this_buildString = sb;
        }

        @NotNull
        public final StringBuilder invoke(@NotNull String $this$unaryPlus) {
            k.f($this$unaryPlus, "$this$unaryPlus");
            StringBuilder sb = this.$this_buildString;
            sb.append($this$unaryPlus);
            k.b(sb, "append(value)");
            return s.i(sb);
        }
    }

    private static final String b(@NotNull u0 $this$debugInfo) {
        StringBuilder $this$buildString = new StringBuilder();
        a $fun$unaryPlus$1 = new a($this$buildString);
        $fun$unaryPlus$1.invoke("type: " + $this$debugInfo);
        $fun$unaryPlus$1.invoke("hashCode: " + $this$debugInfo.hashCode());
        $fun$unaryPlus$1.invoke("javaClass: " + $this$debugInfo.getClass().getCanonicalName());
        for (m declarationDescriptor = $this$debugInfo.c(); declarationDescriptor != null; declarationDescriptor = declarationDescriptor.b()) {
            $fun$unaryPlus$1.invoke("fqName: " + kotlin.reflect.jvm.internal.impl.renderer.c.f.r(declarationDescriptor));
            $fun$unaryPlus$1.invoke("javaClass: " + declarationDescriptor.getClass().getCanonicalName());
        }
        String sb = $this$buildString.toString();
        k.b(sb, "StringBuilder().apply(builderAction).toString()");
        return sb;
    }
}
