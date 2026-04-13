package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.s;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmAbi */
public final class r {
    public static final b a = new b("kotlin.jvm.JvmField");
    public static final a b = a.m(new b("kotlin.reflect.jvm.internal.ReflectionFactoryImpl"));

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 9:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 9:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 9:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/JvmAbi";
                break;
            case 2:
                objArr[0] = "typeAliasName";
                break;
            case 4:
            case 5:
                objArr[0] = "name";
                break;
            case 6:
            case 8:
                objArr[0] = "propertyName";
                break;
            case 10:
                objArr[0] = "propertyDescriptor";
                break;
            case 11:
            case 12:
                objArr[0] = "companionObject";
                break;
            case 13:
                objArr[0] = "memberDescriptor";
                break;
            default:
                objArr[0] = "baseName";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getSyntheticMethodNameForAnnotatedProperty";
                break;
            case 3:
                objArr[1] = "getSyntheticMethodNameForAnnotatedTypeAlias";
                break;
            case 7:
                objArr[1] = "getterName";
                break;
            case 9:
                objArr[1] = "setterName";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/JvmAbi";
                break;
        }
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 9:
                break;
            case 2:
                objArr[2] = "getSyntheticMethodNameForAnnotatedTypeAlias";
                break;
            case 4:
                objArr[2] = "isGetterName";
                break;
            case 5:
                objArr[2] = "isSetterName";
                break;
            case 6:
                objArr[2] = "getterName";
                break;
            case 8:
                objArr[2] = "setterName";
                break;
            case 10:
                objArr[2] = "isPropertyWithBackingFieldInOuterClass";
                break;
            case 11:
                objArr[2] = "isClassCompanionObjectWithBackingFieldsInOuter";
                break;
            case 12:
                objArr[2] = "isMappedIntrinsicCompanionObject";
                break;
            case 13:
                objArr[2] = "hasJvmFieldAnnotation";
                break;
            default:
                objArr[2] = "getSyntheticMethodNameForAnnotatedProperty";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 1:
            case 3:
            case 7:
            case 9:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    public static boolean e(@NotNull String name) {
        if (name == null) {
            a(4);
        }
        return name.startsWith("get") || name.startsWith("is");
    }

    public static boolean h(@NotNull String name) {
        if (name == null) {
            a(5);
        }
        return name.startsWith("set");
    }

    @NotNull
    public static String b(@NotNull String propertyName) {
        String str;
        if (propertyName == null) {
            a(6);
        }
        if (j(propertyName)) {
            str = propertyName;
        } else {
            str = "get" + kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.a.a(propertyName);
        }
        if (str == null) {
            a(7);
        }
        return str;
    }

    @NotNull
    public static String i(@NotNull String propertyName) {
        if (propertyName == null) {
            a(8);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("set");
        sb.append(j(propertyName) ? propertyName.substring("is".length()) : kotlin.reflect.jvm.internal.impl.util.capitalizeDecapitalize.a.a(propertyName));
        String sb2 = sb.toString();
        if (sb2 == null) {
            a(9);
        }
        return sb2;
    }

    public static boolean j(String name) {
        if (!name.startsWith("is") || name.length() == "is".length()) {
            return false;
        }
        char c = name.charAt("is".length());
        if ('a' > c || c > 'z') {
            return true;
        }
        return false;
    }

    public static boolean g(@NotNull i0 propertyDescriptor) {
        if (propertyDescriptor == null) {
            a(10);
        }
        if (propertyDescriptor.h() == b.a.FAKE_OVERRIDE) {
            return false;
        }
        if (d(propertyDescriptor.b())) {
            return true;
        }
        if (!c.x(propertyDescriptor.b()) || !c(propertyDescriptor)) {
            return false;
        }
        return true;
    }

    public static boolean d(@NotNull m companionObject) {
        if (companionObject == null) {
            a(11);
        }
        return c.x(companionObject) && c.w(companionObject.b()) && !f((e) companionObject);
    }

    public static boolean f(@NotNull e companionObject) {
        if (companionObject == null) {
            a(12);
        }
        return kotlin.reflect.jvm.internal.impl.builtins.c.b.b(companionObject);
    }

    public static boolean c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b memberDescriptor) {
        s field;
        if (memberDescriptor == null) {
            a(13);
        }
        if (!(memberDescriptor instanceof i0) || (field = ((i0) memberDescriptor).s0()) == null || !field.getAnnotations().I(a)) {
            return memberDescriptor.getAnnotations().I(a);
        }
        return true;
    }
}
