package kotlin.reflect.jvm.internal;

import com.meituan.robust.Constants;
import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.c;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.builtins.h;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.j;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.s;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.f;
import kotlin.reflect.jvm.internal.impl.load.java.r;
import kotlin.reflect.jvm.internal.impl.load.java.structure.l;
import kotlin.reflect.jvm.internal.impl.load.java.w;
import kotlin.reflect.jvm.internal.impl.load.kotlin.t;
import kotlin.reflect.jvm.internal.impl.metadata.d;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.e;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: RuntimeTypeMapper.kt */
public final class e0 {
    private static final a a;
    public static final e0 b = new e0();

    static {
        a m = a.m(new b(Constants.LANG_VOID));
        k.b(m, "ClassId.topLevel(FqName(\"java.lang.Void\"))");
        a = m;
    }

    private e0() {
    }

    @NotNull
    public final c g(@NotNull u possiblySubstitutedFunction) {
        Method method;
        e.b signature;
        e.b signature2;
        k.f(possiblySubstitutedFunction, "possiblySubstitutedFunction");
        kotlin.reflect.jvm.internal.impl.descriptors.b L = c.L(possiblySubstitutedFunction);
        k.b(L, "DescriptorUtils.unwrapFa…siblySubstitutedFunction)");
        u function = ((u) L).a();
        k.b(function, "DescriptorUtils.unwrapFa…titutedFunction).original");
        if (function instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b) {
            o proto = ((kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b) function).Y();
            if ((proto instanceof i) && (signature2 = kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i.b.e((i) proto, ((kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b) function).G(), ((kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b) function).C())) != null) {
                return new c.e(signature2);
            }
            if (!(proto instanceof d) || (signature = kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i.b.b((d) proto, ((kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b) function).G(), ((kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b) function).C())) == null) {
                return d(function);
            }
            m b2 = possiblySubstitutedFunction.b();
            k.b(b2, "possiblySubstitutedFunction.containingDeclaration");
            if (kotlin.reflect.jvm.internal.impl.resolve.e.b(b2)) {
                return new c.e(signature);
            }
            return new c.d(signature);
        }
        l lVar = null;
        if (function instanceof f) {
            o0 n = ((f) function).n();
            if (!(n instanceof kotlin.reflect.jvm.internal.impl.load.java.sources.a)) {
                n = null;
            }
            kotlin.reflect.jvm.internal.impl.load.java.sources.a aVar = (kotlin.reflect.jvm.internal.impl.load.java.sources.a) n;
            l c = aVar != null ? aVar.c() : null;
            if (c instanceof s) {
                lVar = c;
            }
            s sVar = (s) lVar;
            if (sVar != null && (method = sVar.J()) != null) {
                return new c.C0329c(method);
            }
            throw new y("Incorrect resolution sequence for Java method " + function);
        } else if (function instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.c) {
            o0 n2 = ((kotlin.reflect.jvm.internal.impl.load.java.descriptors.c) function).n();
            if (!(n2 instanceof kotlin.reflect.jvm.internal.impl.load.java.sources.a)) {
                n2 = null;
            }
            kotlin.reflect.jvm.internal.impl.load.java.sources.a aVar2 = (kotlin.reflect.jvm.internal.impl.load.java.sources.a) n2;
            if (aVar2 != null) {
                lVar = aVar2.c();
            }
            l element = lVar;
            if (element instanceof kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.m) {
                return new c.b(((kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.m) element).J());
            }
            if ((element instanceof j) && ((j) element).l()) {
                return new c.a(((j) element).n());
            }
            throw new y("Incorrect resolution sequence for Java constructor " + function + " (" + element + ')');
        } else if (b(function)) {
            return d(function);
        } else {
            throw new y("Unknown origin of " + function + " (" + function.getClass() + ')');
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: kotlin.reflect.jvm.internal.c$e} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.reflect.Method} */
    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.reflect.jvm.internal.d f(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.i0 r11) {
        /*
            r10 = this;
            java.lang.String r0 = "possiblyOverriddenProperty"
            kotlin.jvm.internal.k.f(r11, r0)
            kotlin.reflect.jvm.internal.impl.descriptors.b r0 = kotlin.reflect.jvm.internal.impl.resolve.c.L(r11)
            java.lang.String r1 = "DescriptorUtils.unwrapFa…ssiblyOverriddenProperty)"
            kotlin.jvm.internal.k.b(r0, r1)
            kotlin.reflect.jvm.internal.impl.descriptors.i0 r0 = (kotlin.reflect.jvm.internal.impl.descriptors.i0) r0
            kotlin.reflect.jvm.internal.impl.descriptors.i0 r0 = r0.a()
            java.lang.String r1 = "DescriptorUtils.unwrapFa…rriddenProperty).original"
            kotlin.jvm.internal.k.b(r0, r1)
            boolean r1 = r0 instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i
            r2 = 0
            if (r1 == 0) goto L_0x004f
            r1 = r0
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i r1 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i) r1
            kotlin.reflect.jvm.internal.impl.metadata.n r1 = r1.Y()
            kotlin.reflect.jvm.internal.impl.protobuf.h$f<kotlin.reflect.jvm.internal.impl.metadata.n, kotlin.reflect.jvm.internal.impl.metadata.jvm.a$d> r3 = kotlin.reflect.jvm.internal.impl.metadata.jvm.a.d
            java.lang.String r4 = "JvmProtoBuf.propertySignature"
            kotlin.jvm.internal.k.b(r3, r4)
            java.lang.Object r3 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.f.a(r1, r3)
            r8 = r3
            kotlin.reflect.jvm.internal.impl.metadata.jvm.a$d r8 = (kotlin.reflect.jvm.internal.impl.metadata.jvm.a.d) r8
            if (r8 == 0) goto L_0x00d9
            kotlin.reflect.jvm.internal.d$c r9 = new kotlin.reflect.jvm.internal.d$c
            r2 = r0
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i r2 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i) r2
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r6 = r2.G()
            r2 = r0
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i r2 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i) r2
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.h r7 = r2.C()
            r2 = r9
            r3 = r0
            r4 = r1
            r5 = r8
            r2.<init>(r3, r4, r5, r6, r7)
            return r9
        L_0x004f:
            boolean r1 = r0 instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.g
            if (r1 == 0) goto L_0x00d9
            r1 = r0
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.g r1 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.g) r1
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r1 = r1.n()
            boolean r3 = r1 instanceof kotlin.reflect.jvm.internal.impl.load.java.sources.a
            if (r3 != 0) goto L_0x005f
            r1 = r2
        L_0x005f:
            kotlin.reflect.jvm.internal.impl.load.java.sources.a r1 = (kotlin.reflect.jvm.internal.impl.load.java.sources.a) r1
            if (r1 == 0) goto L_0x0068
            kotlin.reflect.jvm.internal.impl.load.java.structure.l r1 = r1.c()
            goto L_0x0069
        L_0x0068:
            r1 = r2
        L_0x0069:
            boolean r3 = r1 instanceof kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.p
            if (r3 == 0) goto L_0x007b
            kotlin.reflect.jvm.internal.d$a r2 = new kotlin.reflect.jvm.internal.d$a
            r3 = r1
            kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.p r3 = (kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.p) r3
            java.lang.reflect.Field r3 = r3.J()
            r2.<init>(r3)
            goto L_0x00b4
        L_0x007b:
            boolean r3 = r1 instanceof kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.s
            if (r3 == 0) goto L_0x00b5
            kotlin.reflect.jvm.internal.d$b r3 = new kotlin.reflect.jvm.internal.d$b
            r4 = r1
            kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.s r4 = (kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.s) r4
            java.lang.reflect.Method r4 = r4.J()
            kotlin.reflect.jvm.internal.impl.descriptors.k0 r5 = r0.getSetter()
            if (r5 == 0) goto L_0x0093
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r5 = r5.n()
            goto L_0x0094
        L_0x0093:
            r5 = r2
        L_0x0094:
            boolean r6 = r5 instanceof kotlin.reflect.jvm.internal.impl.load.java.sources.a
            if (r6 != 0) goto L_0x0099
            r5 = r2
        L_0x0099:
            kotlin.reflect.jvm.internal.impl.load.java.sources.a r5 = (kotlin.reflect.jvm.internal.impl.load.java.sources.a) r5
            if (r5 == 0) goto L_0x00a2
            kotlin.reflect.jvm.internal.impl.load.java.structure.l r5 = r5.c()
            goto L_0x00a3
        L_0x00a2:
            r5 = r2
        L_0x00a3:
            boolean r6 = r5 instanceof kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.s
            if (r6 != 0) goto L_0x00a8
            r5 = r2
        L_0x00a8:
            kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.s r5 = (kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.s) r5
            if (r5 == 0) goto L_0x00b0
            java.lang.reflect.Method r2 = r5.J()
        L_0x00b0:
            r3.<init>(r4, r2)
            r2 = r3
        L_0x00b4:
            return r2
        L_0x00b5:
            kotlin.reflect.jvm.internal.y r2 = new kotlin.reflect.jvm.internal.y
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Incorrect resolution sequence for Java field "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r4 = " (source = "
            r3.append(r4)
            r3.append(r1)
            r4 = 41
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x00d9:
            kotlin.reflect.jvm.internal.impl.descriptors.j0 r1 = r0.getGetter()
            if (r1 != 0) goto L_0x00e4
            kotlin.jvm.internal.k.n()
        L_0x00e4:
            r3 = 0
            kotlin.reflect.jvm.internal.c$e r1 = r10.d(r1)
            kotlin.reflect.jvm.internal.impl.descriptors.k0 r3 = r0.getSetter()
            if (r3 == 0) goto L_0x00f5
            r2 = r3
            r3 = 0
            kotlin.reflect.jvm.internal.c$e r2 = r10.d(r2)
        L_0x00f5:
            kotlin.reflect.jvm.internal.d$d r3 = new kotlin.reflect.jvm.internal.d$d
            r3.<init>(r1, r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.e0.f(kotlin.reflect.jvm.internal.impl.descriptors.i0):kotlin.reflect.jvm.internal.d");
    }

    private final boolean b(u descriptor) {
        if (kotlin.reflect.jvm.internal.impl.resolve.b.m(descriptor) || kotlin.reflect.jvm.internal.impl.resolve.b.n(descriptor)) {
            return true;
        }
        if (!k.a(descriptor.getName(), kotlin.reflect.jvm.internal.impl.builtins.jvm.a.f.a()) || !descriptor.g().isEmpty()) {
            return false;
        }
        return true;
    }

    private final c.e d(u descriptor) {
        return new c.e(new e.b(e(descriptor), t.c(descriptor, false, false, 1, (Object) null)));
    }

    private final String e(kotlin.reflect.jvm.internal.impl.descriptors.b descriptor) {
        String g = w.g(descriptor);
        if (g == null) {
            if (descriptor instanceof j0) {
                g = r.b(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.p(descriptor).getName().b());
            } else if (descriptor instanceof k0) {
                g = r.i(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.p(descriptor).getName().b());
            } else {
                g = descriptor.getName().b();
            }
            k.b(g, "when (descriptor) {\n    …name.asString()\n        }");
        }
        return g;
    }

    @NotNull
    public final a c(@NotNull Class<?> klass) {
        k.f(klass, "klass");
        if (klass.isArray()) {
            Class<?> componentType = klass.getComponentType();
            k.b(componentType, "klass.componentType");
            h it = a(componentType);
            if (it != null) {
                return new a(g.b, it.getArrayTypeName());
            }
            a m = a.m(g.h.h.l());
            k.b(m, "ClassId.topLevel(KotlinB….FQ_NAMES.array.toSafe())");
            return m;
        } else if (k.a(klass, Void.TYPE)) {
            return a;
        } else {
            h it2 = a(klass);
            if (it2 != null) {
                return new a(g.b, it2.getTypeName());
            }
            a classId = kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.b(klass);
            if (!classId.k()) {
                kotlin.reflect.jvm.internal.impl.builtins.jvm.c cVar = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m;
                b b2 = classId.b();
                k.b(b2, "classId.asSingleFqName()");
                a it3 = cVar.v(b2);
                if (it3 != null) {
                    return it3;
                }
            }
            return classId;
        }
    }

    private final h a(@NotNull Class<?> $this$primitiveType) {
        if (!$this$primitiveType.isPrimitive()) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.resolve.jvm.d dVar = kotlin.reflect.jvm.internal.impl.resolve.jvm.d.get($this$primitiveType.getSimpleName());
        k.b(dVar, "JvmPrimitiveType.get(simpleName)");
        return dVar.getPrimitiveType();
    }
}
