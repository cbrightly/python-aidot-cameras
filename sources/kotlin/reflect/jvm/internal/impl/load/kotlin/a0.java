package kotlin.reflect.jvm.internal.impl.load.kotlin;

import com.meituan.robust.Constants;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.f;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.c;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.t;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.c;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.b1;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.checker.r;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.utils.d;
import kotlin.text.w;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: typeSignatureMapping.kt */
public final class a0 {
    private static final <T> T a(@NotNull l<T> $this$boxTypeIfNeeded, T possiblyPrimitiveType, boolean needBoxedType) {
        return needBoxedType ? $this$boxTypeIfNeeded.b(possiblyPrimitiveType) : possiblyPrimitiveType;
    }

    public static /* synthetic */ Object g(b0 b0Var, l lVar, y yVar, w wVar, i iVar, q qVar, int i, Object obj) {
        return f(b0Var, lVar, yVar, wVar, iVar, (i & 32) != 0 ? d.b() : qVar);
    }

    @NotNull
    public static final <T> T f(@NotNull b0 kotlinType, @NotNull l<T> factory, @NotNull y mode, @NotNull w<? extends T> typeMappingConfiguration, @Nullable i<T> descriptorTypeWriter, @NotNull q<? super b0, ? super T, ? super y, x> writeGenericType) {
        Object jvmType;
        e enumClassIfEnumEntry;
        b0 expandedType;
        Object arrayElementType;
        k.f(kotlinType, "kotlinType");
        k.f(factory, "factory");
        k.f(mode, "mode");
        k.f(typeMappingConfiguration, "typeMappingConfiguration");
        k.f(writeGenericType, "writeGenericType");
        b0 newType = typeMappingConfiguration.d(kotlinType);
        if (newType != null) {
            return f(newType, factory, mode, typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
        }
        if (f.m(kotlinType) != 0) {
            return f(kotlin.reflect.jvm.internal.impl.builtins.k.b(kotlinType, typeMappingConfiguration.e()), factory, mode, typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
        }
        Object builtInType = e(r.a, kotlinType, factory, mode);
        if (builtInType != null) {
            Object jvmType2 = a(factory, builtInType, mode.c());
            writeGenericType.invoke(kotlinType, jvmType2, mode);
            return jvmType2;
        }
        u0 constructor = kotlinType.I0();
        if (constructor instanceof kotlin.reflect.jvm.internal.impl.types.a0) {
            return f(a.n(typeMappingConfiguration.g(((kotlin.reflect.jvm.internal.impl.types.a0) constructor).b())), factory, mode, typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
        }
        h descriptor = constructor.c();
        if (descriptor != null) {
            k.b(descriptor, "constructor.declarationD…structor of $kotlinType\")");
            if (u.r(descriptor)) {
                Object jvmType3 = factory.d("error/NonExistentClass");
                typeMappingConfiguration.f(kotlinType, (e) descriptor);
                if (descriptorTypeWriter != null) {
                    descriptorTypeWriter.c(jvmType3);
                }
                return jvmType3;
            } else if (!(descriptor instanceof e) || !g.e0(kotlinType)) {
                if (descriptor instanceof e) {
                    if (!((e) descriptor).isInline() || mode.b() || (expandedType = (b0) f.a(r.a, kotlinType)) == null) {
                        if (!mode.d() || !g.t0((e) descriptor)) {
                            e a = ((e) descriptor).a();
                            k.b(a, "descriptor.original");
                            jvmType = typeMappingConfiguration.a(a);
                            if (jvmType == null) {
                                if (((e) descriptor).h() == kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_ENTRY) {
                                    m b = ((e) descriptor).b();
                                    if (b != null) {
                                        enumClassIfEnumEntry = (e) b;
                                    } else {
                                        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                                    }
                                } else {
                                    enumClassIfEnumEntry = (e) descriptor;
                                }
                                e a2 = enumClassIfEnumEntry.a();
                                k.b(a2, "enumClassIfEnumEntry.original");
                                jvmType = factory.d(b(a2, typeMappingConfiguration));
                            }
                        } else {
                            jvmType = factory.e();
                        }
                        writeGenericType.invoke(kotlinType, jvmType, mode);
                        return jvmType;
                    }
                    return f(expandedType, factory, mode.f(), typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
                } else if (descriptor instanceof t0) {
                    Object type = f(a.g((t0) descriptor), factory, mode, typeMappingConfiguration, (i) null, d.b());
                    if (descriptorTypeWriter != null) {
                        kotlin.reflect.jvm.internal.impl.name.f name = descriptor.getName();
                        k.b(name, "descriptor.getName()");
                        descriptorTypeWriter.d(name, type);
                    }
                    return type;
                } else {
                    throw new UnsupportedOperationException("Unknown type " + kotlinType);
                }
            } else if (kotlinType.H0().size() == 1) {
                w0 memberProjection = kotlinType.H0().get(0);
                b0 memberType = memberProjection.getType();
                k.b(memberType, "memberProjection.type");
                if (memberProjection.c() == h1.IN_VARIANCE) {
                    arrayElementType = factory.d("java/lang/Object");
                    if (descriptorTypeWriter != null) {
                        i $this$apply = descriptorTypeWriter;
                        $this$apply.b();
                        $this$apply.c(arrayElementType);
                        $this$apply.a();
                    }
                } else {
                    if (descriptorTypeWriter != null) {
                        descriptorTypeWriter.b();
                    }
                    h1 c = memberProjection.c();
                    k.b(c, "memberProjection.projectionKind");
                    arrayElementType = f(memberType, factory, mode.e(c), typeMappingConfiguration, descriptorTypeWriter, writeGenericType);
                    if (descriptorTypeWriter != null) {
                        descriptorTypeWriter.a();
                    }
                }
                return factory.a(Constants.ARRAY_TYPE + factory.c(arrayElementType));
            } else {
                throw new UnsupportedOperationException("arrays must have one type argument");
            }
        } else {
            throw new UnsupportedOperationException("no descriptor for type constructor of " + kotlinType);
        }
    }

    public static final boolean d(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a descriptor) {
        k.f(descriptor, "descriptor");
        if (descriptor instanceof l) {
            return true;
        }
        b0 returnType = descriptor.getReturnType();
        if (returnType == null) {
            k.n();
        }
        if (g.J0(returnType)) {
            b0 returnType2 = descriptor.getReturnType();
            if (returnType2 == null) {
                k.n();
            }
            if (c1.l(returnType2) || (descriptor instanceof j0)) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Nullable
    public static final <T> T e(@NotNull b1 $this$mapBuiltInType, @NotNull kotlin.reflect.jvm.internal.impl.types.model.g type, @NotNull l<T> typeFactory, @NotNull y mode) {
        boolean z;
        b1 b1Var = $this$mapBuiltInType;
        l<T> lVar = typeFactory;
        k.f(b1Var, "$this$mapBuiltInType");
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        k.f(lVar, "typeFactory");
        k.f(mode, "mode");
        kotlin.reflect.jvm.internal.impl.types.model.k constructor = $this$mapBuiltInType.H(type);
        if (!b1Var.N(constructor)) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.builtins.h primitiveType = b1Var.s(constructor);
        if (primitiveType != null) {
            kotlin.reflect.jvm.internal.impl.resolve.jvm.d dVar = kotlin.reflect.jvm.internal.impl.resolve.jvm.d.get(primitiveType);
            k.b(dVar, "JvmPrimitiveType.get(primitiveType)");
            String desc = dVar.getDesc();
            k.b(desc, "JvmPrimitiveType.get(primitiveType).desc");
            return a(lVar, lVar.a(desc), $this$mapBuiltInType.v(type) || t.j($this$mapBuiltInType, type));
        }
        kotlin.reflect.jvm.internal.impl.builtins.h arrayElementType = b1Var.C(constructor);
        if (arrayElementType != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Constants.ARRAY_TYPE);
            kotlin.reflect.jvm.internal.impl.resolve.jvm.d dVar2 = kotlin.reflect.jvm.internal.impl.resolve.jvm.d.get(arrayElementType);
            k.b(dVar2, "JvmPrimitiveType.get(arrayElementType)");
            sb.append(dVar2.getDesc());
            return lVar.a(sb.toString());
        }
        if (b1Var.c(constructor)) {
            c p1 = b1Var.J(constructor);
            kotlin.reflect.jvm.internal.impl.name.a classId = p1 != null ? kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m.x(p1) : null;
            if (classId != null) {
                if (!mode.a()) {
                    List<c.a> m = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m.m();
                    if (!(m instanceof Collection) || !m.isEmpty()) {
                        Iterator<T> it = m.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (k.a(((c.a) it.next()).d(), classId)) {
                                    z = true;
                                    break;
                                }
                            } else {
                                z = false;
                                break;
                            }
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        return null;
                    }
                }
                kotlin.reflect.jvm.internal.impl.resolve.jvm.c b = kotlin.reflect.jvm.internal.impl.resolve.jvm.c.b(classId);
                k.b(b, "JvmClassName.byClassId(classId)");
                String f = b.f();
                k.b(f, "JvmClassName.byClassId(classId).internalName");
                return lVar.d(f);
            }
        }
        return null;
    }

    public static /* synthetic */ String c(e eVar, w wVar, int i, Object obj) {
        if ((i & 2) != 0) {
            wVar = x.a;
        }
        return b(eVar, wVar);
    }

    @NotNull
    public static final String b(@NotNull e klass, @NotNull w<?> typeMappingConfiguration) {
        k.f(klass, "klass");
        k.f(typeMappingConfiguration, "typeMappingConfiguration");
        String it = typeMappingConfiguration.b(klass);
        if (it != null) {
            return it;
        }
        m container = klass.b();
        k.b(container, "klass.containingDeclaration");
        kotlin.reflect.jvm.internal.impl.name.f c = kotlin.reflect.jvm.internal.impl.name.h.c(klass.getName());
        k.b(c, "SpecialNames.safeIdentifier(klass.name)");
        String name = c.d();
        k.b(name, "SpecialNames.safeIdentifier(klass.name).identifier");
        if (container instanceof kotlin.reflect.jvm.internal.impl.descriptors.b0) {
            b fqName = ((kotlin.reflect.jvm.internal.impl.descriptors.b0) container).e();
            if (fqName.d()) {
                return name;
            }
            StringBuilder sb = new StringBuilder();
            String b = fqName.b();
            k.b(b, "fqName.asString()");
            sb.append(w.G(b, '.', '/', false, 4, (Object) null));
            sb.append('/');
            sb.append(name);
            return sb.toString();
        }
        e containerClass = (e) (!(container instanceof e) ? null : container);
        if (containerClass != null) {
            String containerInternalName = typeMappingConfiguration.c(containerClass);
            if (containerInternalName == null) {
                containerInternalName = b(containerClass, typeMappingConfiguration);
            }
            return containerInternalName + '$' + name;
        }
        throw new IllegalArgumentException("Unexpected container: " + container + " for " + klass);
    }
}
