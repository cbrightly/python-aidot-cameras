package kotlin.reflect.jvm.internal.impl.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.e0;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.p;
import kotlin.reflect.jvm.internal.impl.descriptors.p0;
import kotlin.reflect.jvm.internal.impl.descriptors.q;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.x0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: DescriptorUtils */
public class c {
    public static final f a = f.f("values");
    public static final f b = f.f("valueOf");
    public static final b c;
    public static final b d;
    public static final b e;
    public static final b f;
    public static final b g;
    public static final b h = new b("kotlin.Result");
    public static final b i = new b("kotlin.jvm.JvmName");

    private static /* synthetic */ void a(int i2) {
        String str;
        int i3;
        Throwable th;
        switch (i2) {
            case 4:
            case 7:
            case 9:
            case 10:
            case 20:
            case 38:
            case 40:
            case 41:
            case 45:
            case 47:
            case 48:
            case 49:
            case 56:
            case 58:
            case 65:
            case 69:
            case 75:
            case 76:
            case 78:
            case 81:
            case 86:
            case 88:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i2) {
            case 4:
            case 7:
            case 9:
            case 10:
            case 20:
            case 38:
            case 40:
            case 41:
            case 45:
            case 47:
            case 48:
            case 49:
            case 56:
            case 58:
            case 65:
            case 69:
            case 75:
            case 76:
            case 78:
            case 81:
            case 86:
            case 88:
                i3 = 2;
                break;
            default:
                i3 = 3;
                break;
        }
        Object[] objArr = new Object[i3];
        switch (i2) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 11:
            case 12:
            case 13:
            case 19:
            case 21:
            case 22:
            case 32:
            case 33:
            case 34:
            case 53:
            case 54:
            case 55:
            case 57:
            case 74:
            case 87:
            case 89:
                objArr[0] = "descriptor";
                break;
            case 4:
            case 7:
            case 9:
            case 10:
            case 20:
            case 38:
            case 40:
            case 41:
            case 45:
            case 47:
            case 48:
            case 49:
            case 56:
            case 58:
            case 65:
            case 69:
            case 75:
            case 76:
            case 78:
            case 81:
            case 86:
            case 88:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorUtils";
                break;
            case 14:
                objArr[0] = "first";
                break;
            case 15:
                objArr[0] = "second";
                break;
            case 16:
            case 17:
                objArr[0] = "aClass";
                break;
            case 18:
                objArr[0] = "kotlinType";
                break;
            case 23:
                objArr[0] = "declarationDescriptor";
                break;
            case 24:
            case 26:
                objArr[0] = "subClass";
                break;
            case 25:
            case 27:
            case 31:
                objArr[0] = "superClass";
                break;
            case 28:
            case 30:
            case 43:
            case 60:
                objArr[0] = IjkMediaMeta.IJKM_KEY_TYPE;
                break;
            case 29:
                objArr[0] = "other";
                break;
            case 35:
                objArr[0] = "classKind";
                break;
            case 36:
            case 37:
            case 39:
            case 42:
            case 46:
            case 50:
            case 61:
            case 62:
            case 63:
            case 70:
            case 71:
                objArr[0] = "classDescriptor";
                break;
            case 44:
                objArr[0] = "typeConstructor";
                break;
            case 51:
                objArr[0] = "innerClassName";
                break;
            case 52:
                objArr[0] = FirebaseAnalytics.Param.LOCATION;
                break;
            case 59:
                objArr[0] = "variable";
                break;
            case 64:
                objArr[0] = "f";
                break;
            case 66:
                objArr[0] = "current";
                break;
            case 67:
                objArr[0] = "result";
                break;
            case 68:
                objArr[0] = "memberDescriptor";
                break;
            case 72:
            case 73:
                objArr[0] = "annotated";
                break;
            case 77:
            case 79:
            case 82:
            case 84:
                objArr[0] = "scope";
                break;
            case 80:
            case 83:
            case 85:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "containingDeclaration";
                break;
        }
        switch (i2) {
            case 4:
                objArr[1] = "getFqNameSafe";
                break;
            case 7:
                objArr[1] = "getFqNameUnsafe";
                break;
            case 9:
            case 10:
                objArr[1] = "getFqNameFromTopLevelClass";
                break;
            case 20:
                objArr[1] = "getContainingModule";
                break;
            case 38:
                objArr[1] = "getSuperclassDescriptors";
                break;
            case 40:
            case 41:
                objArr[1] = "getSuperClassType";
                break;
            case 45:
                objArr[1] = "getClassDescriptorForTypeConstructor";
                break;
            case 47:
            case 48:
            case 49:
                objArr[1] = "getDefaultConstructorVisibility";
                break;
            case 56:
                objArr[1] = "unwrapFakeOverride";
                break;
            case 58:
                objArr[1] = "unwrapFakeOverrideToAnyDeclaration";
                break;
            case 65:
                objArr[1] = "getAllOverriddenDescriptors";
                break;
            case 69:
                objArr[1] = "getAllOverriddenDeclarations";
                break;
            case 75:
            case 76:
                objArr[1] = "getContainingSourceFile";
                break;
            case 78:
                objArr[1] = "getAllDescriptors";
                break;
            case 81:
                objArr[1] = "getFunctionByName";
                break;
            case 86:
                objArr[1] = "getPropertyByName";
                break;
            case 88:
                objArr[1] = "getDirectMember";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorUtils";
                break;
        }
        switch (i2) {
            case 1:
                objArr[2] = "isLocal";
                break;
            case 2:
                objArr[2] = "getFqName";
                break;
            case 3:
                objArr[2] = "getFqNameSafe";
                break;
            case 4:
            case 7:
            case 9:
            case 10:
            case 20:
            case 38:
            case 40:
            case 41:
            case 45:
            case 47:
            case 48:
            case 49:
            case 56:
            case 58:
            case 65:
            case 69:
            case 75:
            case 76:
            case 78:
            case 81:
            case 86:
            case 88:
                break;
            case 5:
                objArr[2] = "getFqNameSafeIfPossible";
                break;
            case 6:
                objArr[2] = "getFqNameUnsafe";
                break;
            case 8:
                objArr[2] = "getFqNameFromTopLevelClass";
                break;
            case 11:
                objArr[2] = "isExtension";
                break;
            case 12:
                objArr[2] = "isOverride";
                break;
            case 13:
                objArr[2] = "isStaticDeclaration";
                break;
            case 14:
            case 15:
                objArr[2] = "areInSameModule";
                break;
            case 16:
            case 17:
                objArr[2] = "getParentOfType";
                break;
            case 18:
            case 21:
                objArr[2] = "getContainingModuleOrNull";
                break;
            case 19:
                objArr[2] = "getContainingModule";
                break;
            case 22:
                objArr[2] = "getContainingClass";
                break;
            case 23:
                objArr[2] = "isAncestor";
                break;
            case 24:
            case 25:
                objArr[2] = "isDirectSubclass";
                break;
            case 26:
            case 27:
                objArr[2] = "isSubclass";
                break;
            case 28:
            case 29:
                objArr[2] = "isSameClass";
                break;
            case 30:
            case 31:
                objArr[2] = "isSubtypeOfClass";
                break;
            case 32:
                objArr[2] = "isAnonymousObject";
                break;
            case 33:
                objArr[2] = "isAnonymousFunction";
                break;
            case 34:
                objArr[2] = "isEnumEntry";
                break;
            case 35:
                objArr[2] = "isKindOf";
                break;
            case 36:
                objArr[2] = "hasAbstractMembers";
                break;
            case 37:
                objArr[2] = "getSuperclassDescriptors";
                break;
            case 39:
                objArr[2] = "getSuperClassType";
                break;
            case 42:
                objArr[2] = "getSuperClassDescriptor";
                break;
            case 43:
                objArr[2] = "getClassDescriptorForType";
                break;
            case 44:
                objArr[2] = "getClassDescriptorForTypeConstructor";
                break;
            case 46:
                objArr[2] = "getDefaultConstructorVisibility";
                break;
            case 50:
            case 51:
            case 52:
                objArr[2] = "getInnerClassByName";
                break;
            case 53:
                objArr[2] = "isStaticNestedClass";
                break;
            case 54:
                objArr[2] = "isTopLevelOrInnerClass";
                break;
            case 55:
                objArr[2] = "unwrapFakeOverride";
                break;
            case 57:
                objArr[2] = "unwrapFakeOverrideToAnyDeclaration";
                break;
            case 59:
            case 60:
                objArr[2] = "shouldRecordInitializerForProperty";
                break;
            case 61:
                objArr[2] = "classCanHaveAbstractFakeOverride";
                break;
            case 62:
                objArr[2] = "classCanHaveAbstractDeclaration";
                break;
            case 63:
                objArr[2] = "classCanHaveOpenMembers";
                break;
            case 64:
                objArr[2] = "getAllOverriddenDescriptors";
                break;
            case 66:
            case 67:
                objArr[2] = "collectAllOverriddenDescriptors";
                break;
            case 68:
                objArr[2] = "getAllOverriddenDeclarations";
                break;
            case 70:
                objArr[2] = "isSingletonOrAnonymousObject";
                break;
            case 71:
                objArr[2] = "canHaveDeclaredConstructors";
                break;
            case 72:
                objArr[2] = "getJvmName";
                break;
            case 73:
                objArr[2] = "findJvmNameAnnotation";
                break;
            case 74:
                objArr[2] = "getContainingSourceFile";
                break;
            case 77:
                objArr[2] = "getAllDescriptors";
                break;
            case 79:
            case 80:
                objArr[2] = "getFunctionByName";
                break;
            case 82:
            case 83:
                objArr[2] = "getFunctionByNameOrNull";
                break;
            case 84:
            case 85:
                objArr[2] = "getPropertyByName";
                break;
            case 87:
                objArr[2] = "getDirectMember";
                break;
            case 89:
                objArr[2] = "isMethodOfAny";
                break;
            default:
                objArr[2] = "getDispatchReceiverParameterIfNeeded";
                break;
        }
        String format = String.format(str, objArr);
        switch (i2) {
            case 4:
            case 7:
            case 9:
            case 10:
            case 20:
            case 38:
            case 40:
            case 41:
            case 45:
            case 47:
            case 48:
            case 49:
            case 56:
            case 58:
            case 65:
            case 69:
            case 75:
            case 76:
            case 78:
            case 81:
            case 86:
            case 88:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    static {
        b bVar = new b("kotlin.coroutines");
        c = bVar;
        b c2 = bVar.c(f.f("experimental"));
        d = c2;
        e = c2.c(f.f("intrinsics"));
        f = c2.c(f.f("Continuation"));
        g = bVar.c(f.f("Continuation"));
    }

    @Nullable
    public static l0 l(@NotNull m containingDeclaration) {
        if (containingDeclaration == null) {
            a(0);
        }
        if (containingDeclaration instanceof e) {
            return ((e) containingDeclaration).F0();
        }
        return null;
    }

    public static boolean E(@NotNull m descriptor) {
        if (descriptor == null) {
            a(1);
        }
        for (m current = descriptor; current != null; current = current.b()) {
            if (u(current) || y(current)) {
                return true;
            }
        }
        return false;
    }

    public static boolean y(m current) {
        return (current instanceof q) && ((q) current).getVisibility() == z0.f;
    }

    @NotNull
    public static kotlin.reflect.jvm.internal.impl.name.c m(@NotNull m descriptor) {
        if (descriptor == null) {
            a(2);
        }
        b safe = o(descriptor);
        return safe != null ? safe.j() : p(descriptor);
    }

    @NotNull
    public static b n(@NotNull m descriptor) {
        if (descriptor == null) {
            a(3);
        }
        b safe = o(descriptor);
        b l = safe != null ? safe : p(descriptor).l();
        if (l == null) {
            a(4);
        }
        return l;
    }

    @Nullable
    private static b o(@NotNull m descriptor) {
        if (descriptor == null) {
            a(5);
        }
        if ((descriptor instanceof y) || u.r(descriptor)) {
            return b.a;
        }
        if (descriptor instanceof e0) {
            return ((e0) descriptor).e();
        }
        if (descriptor instanceof b0) {
            return ((b0) descriptor).e();
        }
        return null;
    }

    @NotNull
    private static kotlin.reflect.jvm.internal.impl.name.c p(@NotNull m descriptor) {
        if (descriptor == null) {
            a(6);
        }
        m containingDeclaration = descriptor.b();
        if (containingDeclaration != null) {
            kotlin.reflect.jvm.internal.impl.name.c c2 = m(containingDeclaration).c(descriptor.getName());
            if (c2 == null) {
                a(7);
            }
            return c2;
        }
        throw new AssertionError("Not package/module descriptor doesn't have containing declaration: " + descriptor);
    }

    public static boolean J(@Nullable m descriptor) {
        return descriptor != null && (descriptor.b() instanceof b0);
    }

    public static boolean b(@NotNull m first, @NotNull m second) {
        if (first == null) {
            a(14);
        }
        if (second == null) {
            a(15);
        }
        return g(first).equals(g(second));
    }

    @Nullable
    public static <D extends m> D q(@Nullable m descriptor, @NotNull Class<D> aClass) {
        if (aClass == null) {
            a(16);
        }
        return r(descriptor, aClass, true);
    }

    @Nullable
    public static <D extends m> D r(@Nullable m descriptor, @NotNull Class<D> aClass, boolean strict) {
        if (aClass == null) {
            a(17);
        }
        if (descriptor == null) {
            return null;
        }
        if (strict) {
            descriptor = descriptor.b();
        }
        while (descriptor != null) {
            if (aClass.isInstance(descriptor)) {
                return descriptor;
            }
            descriptor = descriptor.b();
        }
        return null;
    }

    @Nullable
    public static y i(@NotNull kotlin.reflect.jvm.internal.impl.types.b0 kotlinType) {
        if (kotlinType == null) {
            a(18);
        }
        h descriptor = kotlinType.I0().c();
        if (descriptor == null) {
            return null;
        }
        return h(descriptor);
    }

    @NotNull
    public static y g(@NotNull m descriptor) {
        if (descriptor == null) {
            a(19);
        }
        y module = h(descriptor);
        if (module != null) {
            if (module == null) {
                a(20);
            }
            return module;
        }
        throw new AssertionError("Descriptor without a containing module: " + descriptor);
    }

    @Nullable
    public static y h(@NotNull m descriptor) {
        if (descriptor == null) {
            a(21);
        }
        while (descriptor != null) {
            if (descriptor instanceof y) {
                return (y) descriptor;
            }
            if (descriptor instanceof e0) {
                return ((e0) descriptor).w0();
            }
            descriptor = descriptor.b();
        }
        return null;
    }

    public static boolean z(@NotNull e subClass, @NotNull e superClass) {
        if (subClass == null) {
            a(24);
        }
        if (superClass == null) {
            a(25);
        }
        for (kotlin.reflect.jvm.internal.impl.types.b0 superType : subClass.i().b()) {
            if (F(superType, superClass.a())) {
                return true;
            }
        }
        return false;
    }

    public static boolean H(@NotNull e subClass, @NotNull e superClass) {
        if (subClass == null) {
            a(26);
        }
        if (superClass == null) {
            a(27);
        }
        return I(subClass.m(), superClass.a());
    }

    private static boolean F(@NotNull kotlin.reflect.jvm.internal.impl.types.b0 type, @NotNull m other) {
        if (type == null) {
            a(28);
        }
        if (other == null) {
            a(29);
        }
        m descriptor = type.I0().c();
        if (descriptor == null) {
            return false;
        }
        m originalDescriptor = descriptor.a();
        if (!(originalDescriptor instanceof h) || !(other instanceof h) || !((h) other).i().equals(((h) originalDescriptor).i())) {
            return false;
        }
        return true;
    }

    public static boolean I(@NotNull kotlin.reflect.jvm.internal.impl.types.b0 type, @NotNull m superClass) {
        if (type == null) {
            a(30);
        }
        if (superClass == null) {
            a(31);
        }
        if (F(type, superClass)) {
            return true;
        }
        for (kotlin.reflect.jvm.internal.impl.types.b0 superType : type.I0().b()) {
            if (I(superType, superClass)) {
                return true;
            }
        }
        return false;
    }

    public static boolean x(@Nullable m descriptor) {
        return D(descriptor, kotlin.reflect.jvm.internal.impl.descriptors.f.OBJECT) && ((e) descriptor).V();
    }

    public static boolean G(@Nullable m descriptor) {
        return D(descriptor, kotlin.reflect.jvm.internal.impl.descriptors.f.CLASS) && ((e) descriptor).p() == w.SEALED;
    }

    public static boolean u(@NotNull m descriptor) {
        if (descriptor == null) {
            a(32);
        }
        return v(descriptor) && descriptor.getName().equals(kotlin.reflect.jvm.internal.impl.name.h.a);
    }

    public static boolean B(@NotNull m descriptor) {
        if (descriptor == null) {
            a(34);
        }
        return D(descriptor, kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_ENTRY);
    }

    public static boolean A(@Nullable m descriptor) {
        return D(descriptor, kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_CLASS);
    }

    public static boolean t(@Nullable m descriptor) {
        return D(descriptor, kotlin.reflect.jvm.internal.impl.descriptors.f.ANNOTATION_CLASS);
    }

    public static boolean C(@Nullable m descriptor) {
        return D(descriptor, kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE);
    }

    public static boolean v(@Nullable m descriptor) {
        return D(descriptor, kotlin.reflect.jvm.internal.impl.descriptors.f.CLASS);
    }

    public static boolean w(@Nullable m descriptor) {
        return v(descriptor) || A(descriptor);
    }

    private static boolean D(@Nullable m descriptor, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.f classKind) {
        if (classKind == null) {
            a(35);
        }
        return (descriptor instanceof e) && ((e) descriptor).h() == classKind;
    }

    @Nullable
    public static e s(@NotNull e classDescriptor) {
        if (classDescriptor == null) {
            a(42);
        }
        for (kotlin.reflect.jvm.internal.impl.types.b0 type : classDescriptor.i().b()) {
            e superClassDescriptor = e(type);
            if (superClassDescriptor.h() != kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE) {
                return superClassDescriptor;
            }
        }
        return null;
    }

    @NotNull
    public static e e(@NotNull kotlin.reflect.jvm.internal.impl.types.b0 type) {
        if (type == null) {
            a(43);
        }
        return f(type.I0());
    }

    @NotNull
    public static e f(@NotNull u0 typeConstructor) {
        if (typeConstructor == null) {
            a(44);
        }
        h descriptor = typeConstructor.c();
        if (descriptor instanceof e) {
            e eVar = (e) descriptor;
            if (eVar == null) {
                a(45);
            }
            return eVar;
        }
        throw new AssertionError("Classifier descriptor of a type should be of type ClassDescriptor: " + typeConstructor);
    }

    @NotNull
    public static a1 k(@NotNull e classDescriptor) {
        if (classDescriptor == null) {
            a(46);
        }
        kotlin.reflect.jvm.internal.impl.descriptors.f classKind = classDescriptor.h();
        if (classKind == kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_CLASS || classKind.isSingleton() || G(classDescriptor)) {
            a1 a1Var = z0.a;
            if (a1Var == null) {
                a(47);
            }
            return a1Var;
        } else if (u(classDescriptor)) {
            a1 a1Var2 = z0.l;
            if (a1Var2 == null) {
                a(48);
            }
            return a1Var2;
        } else if (classKind == kotlin.reflect.jvm.internal.impl.descriptors.f.CLASS || classKind == kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE || classKind == kotlin.reflect.jvm.internal.impl.descriptors.f.ANNOTATION_CLASS) {
            a1 a1Var3 = z0.e;
            if (a1Var3 == null) {
                a(49);
            }
            return a1Var3;
        } else {
            throw new AssertionError();
        }
    }

    @NotNull
    public static <D extends kotlin.reflect.jvm.internal.impl.descriptors.b> D L(@NotNull D descriptor) {
        if (descriptor == null) {
            a(55);
        }
        while (descriptor.h() == b.a.FAKE_OVERRIDE) {
            Collection d2 = descriptor.d();
            if (!d2.isEmpty()) {
                descriptor = (kotlin.reflect.jvm.internal.impl.descriptors.b) d2.iterator().next();
            } else {
                throw new IllegalStateException("Fake override should have at least one overridden descriptor: " + descriptor);
            }
        }
        return descriptor;
    }

    @NotNull
    public static <D extends q> D M(@NotNull D descriptor) {
        if (descriptor == null) {
            a(57);
        }
        if (descriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.b) {
            return L((kotlin.reflect.jvm.internal.impl.descriptors.b) descriptor);
        }
        if (descriptor == null) {
            a(58);
        }
        return descriptor;
    }

    public static boolean K(@NotNull x0 variable, @NotNull kotlin.reflect.jvm.internal.impl.types.b0 type) {
        if (variable == null) {
            a(59);
        }
        if (type == null) {
            a(60);
        }
        if (variable.K() || d0.a(type)) {
            return false;
        }
        if (c1.b(type)) {
            return true;
        }
        g builtIns = a.h(variable);
        if (!g.C0(type)) {
            kotlin.reflect.jvm.internal.impl.types.checker.g gVar = kotlin.reflect.jvm.internal.impl.types.checker.g.a;
            if (!gVar.b(builtIns.Y(), type) && !gVar.b(builtIns.M().m(), type) && !gVar.b(builtIns.j(), type) && !kotlin.reflect.jvm.internal.impl.builtins.m.e.d(type)) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public static <D extends kotlin.reflect.jvm.internal.impl.descriptors.a> Set<D> d(@NotNull D f2) {
        if (f2 == null) {
            a(64);
        }
        Set<D> result = new LinkedHashSet<>();
        c(f2.a(), result);
        return result;
    }

    private static <D extends kotlin.reflect.jvm.internal.impl.descriptors.a> void c(@NotNull D current, @NotNull Set<D> result) {
        if (current == null) {
            a(66);
        }
        if (result == null) {
            a(67);
        }
        if (!result.contains(current)) {
            for (kotlin.reflect.jvm.internal.impl.descriptors.a callableDescriptor : current.a().d()) {
                D descriptor = callableDescriptor.a();
                c(descriptor, result);
                result.add(descriptor);
            }
        }
    }

    @NotNull
    public static p0 j(@NotNull m descriptor) {
        if (descriptor == null) {
            a(74);
        }
        if (descriptor instanceof k0) {
            descriptor = ((k0) descriptor).Q();
        }
        if (descriptor instanceof p) {
            p0 b2 = ((p) descriptor).n().b();
            if (b2 == null) {
                a(75);
            }
            return b2;
        }
        p0 p0Var = p0.a;
        if (p0Var == null) {
            a(76);
        }
        return p0Var;
    }
}
