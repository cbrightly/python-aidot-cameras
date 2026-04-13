package kotlin.reflect.jvm.internal.impl.load.java.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.load.java.structure.j;
import kotlin.reflect.jvm.internal.impl.load.java.structure.p;
import kotlin.reflect.jvm.internal.impl.load.java.structure.q;
import kotlin.reflect.jvm.internal.impl.load.java.structure.v;
import kotlin.reflect.jvm.internal.impl.load.java.structure.y;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.g;
import kotlin.reflect.jvm.internal.impl.resolve.i;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.r;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DescriptorResolverUtils */
public final class a {
    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 18:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 18:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 7:
            case 13:
                objArr[0] = "membersFromSupertypes";
                break;
            case 2:
            case 8:
            case 14:
                objArr[0] = "membersFromCurrent";
                break;
            case 3:
            case 9:
            case 15:
                objArr[0] = "classDescriptor";
                break;
            case 4:
            case 10:
            case 16:
                objArr[0] = "errorReporter";
                break;
            case 5:
            case 11:
            case 17:
                objArr[0] = "overridingUtil";
                break;
            case 18:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/components/DescriptorResolverUtils";
                break;
            case 20:
                objArr[0] = "annotationClass";
                break;
            case 21:
                objArr[0] = "member";
                break;
            case 22:
            case 23:
                objArr[0] = FirebaseAnalytics.Param.METHOD;
                break;
            default:
                objArr[0] = "name";
                break;
        }
        switch (i) {
            case 18:
                objArr[1] = "resolveOverrides";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/DescriptorResolverUtils";
                break;
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "resolveOverridesForStaticMembers";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                objArr[2] = "resolveOverrides";
                break;
            case 18:
                break;
            case 19:
            case 20:
                objArr[2] = "getAnnotationParameterByName";
                break;
            case 21:
                objArr[2] = "isObjectMethodInInterface";
                break;
            case 22:
                objArr[2] = "isObjectMethod";
                break;
            case 23:
                objArr[2] = "isMethodWithOneObjectParameter";
                break;
            default:
                objArr[2] = "resolveOverridesForNonStaticMembers";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 18:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    @NotNull
    public static <D extends b> Collection<D> g(@NotNull f name, @NotNull Collection<D> membersFromSupertypes, @NotNull Collection<D> membersFromCurrent, @NotNull e classDescriptor, @NotNull r errorReporter, @NotNull i overridingUtil) {
        if (name == null) {
            a(0);
        }
        if (membersFromSupertypes == null) {
            a(1);
        }
        if (membersFromCurrent == null) {
            a(2);
        }
        if (classDescriptor == null) {
            a(3);
        }
        if (errorReporter == null) {
            a(4);
        }
        if (overridingUtil == null) {
            a(5);
        }
        return f(name, membersFromSupertypes, membersFromCurrent, classDescriptor, errorReporter, overridingUtil, false);
    }

    @NotNull
    public static <D extends b> Collection<D> h(@NotNull f name, @NotNull Collection<D> membersFromSupertypes, @NotNull Collection<D> membersFromCurrent, @NotNull e classDescriptor, @NotNull r errorReporter, @NotNull i overridingUtil) {
        if (name == null) {
            a(6);
        }
        if (membersFromSupertypes == null) {
            a(7);
        }
        if (membersFromCurrent == null) {
            a(8);
        }
        if (classDescriptor == null) {
            a(9);
        }
        if (errorReporter == null) {
            a(10);
        }
        if (overridingUtil == null) {
            a(11);
        }
        return f(name, membersFromSupertypes, membersFromCurrent, classDescriptor, errorReporter, overridingUtil, true);
    }

    @NotNull
    private static <D extends b> Collection<D> f(@NotNull f name, @NotNull Collection<D> membersFromSupertypes, @NotNull Collection<D> membersFromCurrent, @NotNull e classDescriptor, @NotNull r errorReporter, @NotNull i overridingUtil, boolean isStaticContext) {
        if (name == null) {
            a(12);
        }
        if (membersFromSupertypes == null) {
            a(13);
        }
        if (membersFromCurrent == null) {
            a(14);
        }
        if (classDescriptor == null) {
            a(15);
        }
        if (errorReporter == null) {
            a(16);
        }
        if (overridingUtil == null) {
            a(17);
        }
        Set<D> result = new LinkedHashSet<>();
        overridingUtil.w(name, membersFromSupertypes, membersFromCurrent, classDescriptor, new C0357a(errorReporter, result, isStaticContext));
        return result;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.components.a$a  reason: collision with other inner class name */
    /* compiled from: DescriptorResolverUtils */
    public static final class C0357a extends g {
        final /* synthetic */ r a;
        final /* synthetic */ Set b;
        final /* synthetic */ boolean c;

        private static /* synthetic */ void f(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "fromSuper";
                    break;
                case 2:
                    objArr[0] = "fromCurrent";
                    break;
                case 3:
                    objArr[0] = "member";
                    break;
                case 4:
                    objArr[0] = "overridden";
                    break;
                default:
                    objArr[0] = "fakeOverride";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/components/DescriptorResolverUtils$1";
            switch (i) {
                case 1:
                case 2:
                    objArr[2] = "conflict";
                    break;
                case 3:
                case 4:
                    objArr[2] = "setOverriddenDescriptors";
                    break;
                default:
                    objArr[2] = "addFakeOverride";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        C0357a(r rVar, Set set, boolean z) {
            this.a = rVar;
            this.b = set;
            this.c = z;
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.components.a$a$a  reason: collision with other inner class name */
        /* compiled from: DescriptorResolverUtils */
        public class C0358a implements l<b, x> {
            private static /* synthetic */ void a(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"descriptor", "kotlin/reflect/jvm/internal/impl/load/java/components/DescriptorResolverUtils$1$1", "invoke"}));
            }

            C0358a() {
            }

            /* renamed from: b */
            public x invoke(@NotNull b descriptor) {
                if (descriptor == null) {
                    a(0);
                }
                C0357a.this.a.a(descriptor);
                return x.a;
            }
        }

        public void a(@NotNull b fakeOverride) {
            if (fakeOverride == null) {
                f(0);
            }
            i.L(fakeOverride, new C0358a());
            this.b.add(fakeOverride);
        }

        public void e(@NotNull b fromSuper, @NotNull b fromCurrent) {
            if (fromSuper == null) {
                f(1);
            }
            if (fromCurrent == null) {
                f(2);
            }
        }

        public void d(@NotNull b member, @NotNull Collection<? extends b> overridden) {
            if (member == null) {
                f(3);
            }
            if (overridden == null) {
                f(4);
            }
            if (!this.c || member.h() == b.a.FAKE_OVERRIDE) {
                super.d(member, overridden);
            }
        }
    }

    @Nullable
    public static w0 b(@NotNull f name, @NotNull e annotationClass) {
        if (name == null) {
            a(19);
        }
        if (annotationClass == null) {
            a(20);
        }
        Collection<d> f = annotationClass.f();
        if (f.size() != 1) {
            return null;
        }
        for (w0 parameter : f.iterator().next().g()) {
            if (parameter.getName().equals(name)) {
                return parameter;
            }
        }
        return null;
    }

    public static boolean e(@NotNull p member) {
        if (member == null) {
            a(21);
        }
        return member.I().D() && (member instanceof q) && d((q) member);
    }

    private static boolean d(@NotNull q method) {
        if (method == null) {
            a(22);
        }
        String name = method.getName().b();
        if (name.equals("toString") || name.equals("hashCode")) {
            return method.g().isEmpty();
        }
        if (name.equals("equals")) {
            return c(method);
        }
        return false;
    }

    private static boolean c(@NotNull q method) {
        if (method == null) {
            a(23);
        }
        List<y> g = method.g();
        if (g.size() == 1) {
            v type = g.get(0).getType();
            if (type instanceof j) {
                kotlin.reflect.jvm.internal.impl.load.java.structure.i classifier = ((j) type).a();
                if (classifier instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.g) {
                    kotlin.reflect.jvm.internal.impl.name.b classFqName = ((kotlin.reflect.jvm.internal.impl.load.java.structure.g) classifier).e();
                    if (classFqName == null || !classFqName.b().equals("java.lang.Object")) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
