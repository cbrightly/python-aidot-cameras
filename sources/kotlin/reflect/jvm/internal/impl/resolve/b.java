package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collections;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.d0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.e0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.f0;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.l;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DescriptorFactory */
public class b {
    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 12:
            case 23:
            case 25:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 12:
            case 23:
            case 25:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
            case 8:
            case 14:
            case 16:
            case 18:
            case 30:
                objArr[0] = "annotations";
                break;
            case 2:
            case 5:
            case 9:
                objArr[0] = "parameterAnnotations";
                break;
            case 6:
            case 11:
            case 19:
                objArr[0] = "sourceElement";
                break;
            case 10:
                objArr[0] = "visibility";
                break;
            case 12:
            case 23:
            case 25:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorFactory";
                break;
            case 20:
                objArr[0] = "containingClass";
                break;
            case 21:
                objArr[0] = "source";
                break;
            case 22:
            case 24:
                objArr[0] = "enumClass";
                break;
            case 26:
            case 27:
            case 28:
                objArr[0] = "descriptor";
                break;
            case 29:
                objArr[0] = "owner";
                break;
            default:
                objArr[0] = "propertyDescriptor";
                break;
        }
        switch (i) {
            case 12:
                objArr[1] = "createSetter";
                break;
            case 23:
                objArr[1] = "createEnumValuesMethod";
                break;
            case 25:
                objArr[1] = "createEnumValueOfMethod";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorFactory";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[2] = "createSetter";
                break;
            case 12:
            case 23:
            case 25:
                break;
            case 13:
            case 14:
                objArr[2] = "createDefaultGetter";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "createGetter";
                break;
            case 20:
            case 21:
                objArr[2] = "createPrimaryConstructorForObject";
                break;
            case 22:
                objArr[2] = "createEnumValuesMethod";
                break;
            case 24:
                objArr[2] = "createEnumValueOfMethod";
                break;
            case 26:
                objArr[2] = "isEnumValuesMethod";
                break;
            case 27:
                objArr[2] = "isEnumValueOfMethod";
                break;
            case 28:
                objArr[2] = "isEnumSpecialMethod";
                break;
            case 29:
            case 30:
                objArr[2] = "createExtensionReceiverParameterForCallable";
                break;
            default:
                objArr[2] = "createDefaultSetter";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 12:
            case 23:
            case 25:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* compiled from: DescriptorFactory */
    public static class a extends f {
        private static /* synthetic */ void u(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "source";
                    break;
                default:
                    objArr[0] = "containingClass";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/DescriptorFactory$DefaultClassConstructorDescriptor";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull e containingClass, @NotNull o0 source) {
            super(containingClass, (l) null, g.b.b(), true, b.a.DECLARATION, source);
            if (containingClass == null) {
                u(0);
            }
            if (source == null) {
                u(1);
            }
            h1(Collections.emptyList(), c.k(containingClass));
        }
    }

    @NotNull
    public static d0 c(@NotNull i0 propertyDescriptor, @NotNull g annotations, @NotNull g parameterAnnotations) {
        if (propertyDescriptor == null) {
            a(0);
        }
        if (annotations == null) {
            a(1);
        }
        if (parameterAnnotations == null) {
            a(2);
        }
        return j(propertyDescriptor, annotations, parameterAnnotations, true, false, false, propertyDescriptor.n());
    }

    @NotNull
    public static d0 j(@NotNull i0 propertyDescriptor, @NotNull g annotations, @NotNull g parameterAnnotations, boolean isDefault, boolean isExternal, boolean isInline, @NotNull o0 sourceElement) {
        if (propertyDescriptor == null) {
            a(3);
        }
        if (annotations == null) {
            a(4);
        }
        if (parameterAnnotations == null) {
            a(5);
        }
        if (sourceElement == null) {
            a(6);
        }
        return k(propertyDescriptor, annotations, parameterAnnotations, isDefault, isExternal, isInline, propertyDescriptor.getVisibility(), sourceElement);
    }

    @NotNull
    public static d0 k(@NotNull i0 propertyDescriptor, @NotNull g annotations, @NotNull g parameterAnnotations, boolean isDefault, boolean isExternal, boolean isInline, @NotNull a1 visibility, @NotNull o0 sourceElement) {
        g gVar = parameterAnnotations;
        if (propertyDescriptor == null) {
            a(7);
        }
        if (annotations == null) {
            a(8);
        }
        if (gVar == null) {
            a(9);
        }
        if (visibility == null) {
            a(10);
        }
        if (sourceElement == null) {
            a(11);
        }
        d0 setterDescriptor = new d0(propertyDescriptor, annotations, propertyDescriptor.p(), visibility, isDefault, isExternal, isInline, b.a.DECLARATION, (k0) null, sourceElement);
        setterDescriptor.K0(d0.I0(setterDescriptor, propertyDescriptor.getType(), parameterAnnotations));
        return setterDescriptor;
    }

    @NotNull
    public static c0 b(@NotNull i0 propertyDescriptor, @NotNull g annotations) {
        if (propertyDescriptor == null) {
            a(13);
        }
        if (annotations == null) {
            a(14);
        }
        return g(propertyDescriptor, annotations, true, false, false);
    }

    @NotNull
    public static c0 g(@NotNull i0 propertyDescriptor, @NotNull g annotations, boolean isDefault, boolean isExternal, boolean isInline) {
        if (propertyDescriptor == null) {
            a(15);
        }
        if (annotations == null) {
            a(16);
        }
        return h(propertyDescriptor, annotations, isDefault, isExternal, isInline, propertyDescriptor.n());
    }

    @NotNull
    public static c0 h(@NotNull i0 propertyDescriptor, @NotNull g annotations, boolean isDefault, boolean isExternal, boolean isInline, @NotNull o0 sourceElement) {
        if (propertyDescriptor == null) {
            a(17);
        }
        if (annotations == null) {
            a(18);
        }
        if (sourceElement == null) {
            a(19);
        }
        return new c0(propertyDescriptor, annotations, propertyDescriptor.p(), propertyDescriptor.getVisibility(), isDefault, isExternal, isInline, b.a.DECLARATION, (j0) null, sourceElement);
    }

    @NotNull
    public static f i(@NotNull e containingClass, @NotNull o0 source) {
        if (containingClass == null) {
            a(20);
        }
        if (source == null) {
            a(21);
        }
        return new a(containingClass, source);
    }

    @NotNull
    public static n0 e(@NotNull e enumClass) {
        if (enumClass == null) {
            a(22);
        }
        f0 f1 = f0.d1(enumClass, g.b.b(), c.a, b.a.SYNTHESIZED, enumClass.n()).J0((l0) null, (l0) null, Collections.emptyList(), Collections.emptyList(), kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(enumClass).m(h1.INVARIANT, enumClass.m()), w.FINAL, z0.e);
        if (f1 == null) {
            a(23);
        }
        return f1;
    }

    @NotNull
    public static n0 d(@NotNull e enumClass) {
        e eVar = enumClass;
        if (eVar == null) {
            a(24);
        }
        g.a aVar = g.b;
        f0 valueOf = f0.d1(eVar, aVar.b(), c.b, b.a.SYNTHESIZED, enumClass.n());
        f0 f0Var = valueOf;
        f0 f1 = f0Var.J0((l0) null, (l0) null, Collections.emptyList(), Collections.singletonList(new kotlin.reflect.jvm.internal.impl.descriptors.impl.k0(valueOf, (w0) null, 0, aVar.b(), kotlin.reflect.jvm.internal.impl.name.f.f("value"), kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(enumClass).Y(), false, false, false, (b0) null, enumClass.n())), enumClass.m(), w.FINAL, z0.e);
        if (f1 == null) {
            a(25);
        }
        return f1;
    }

    public static boolean n(@NotNull u descriptor) {
        if (descriptor == null) {
            a(26);
        }
        return descriptor.getName().equals(c.a) && l(descriptor);
    }

    public static boolean m(@NotNull u descriptor) {
        if (descriptor == null) {
            a(27);
        }
        return descriptor.getName().equals(c.b) && l(descriptor);
    }

    private static boolean l(@NotNull u descriptor) {
        if (descriptor == null) {
            a(28);
        }
        return descriptor.h() == b.a.SYNTHESIZED && c.A(descriptor.b());
    }

    @Nullable
    public static l0 f(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a owner, @Nullable b0 receiverParameterType, @NotNull g annotations) {
        if (owner == null) {
            a(29);
        }
        if (annotations == null) {
            a(30);
        }
        if (receiverParameterType == null) {
            return null;
        }
        return new e0(owner, new kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.b(owner, receiverParameterType, (d) null), annotations);
    }
}
