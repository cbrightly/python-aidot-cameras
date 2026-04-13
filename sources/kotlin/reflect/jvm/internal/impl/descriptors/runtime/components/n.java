package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReflectKotlinClass.kt */
public final class n {
    public static final n a = new n();

    private n() {
    }

    @NotNull
    public final String c(@NotNull Method method) {
        k.f(method, FirebaseAnalytics.Param.METHOD);
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Class parameterType : method.getParameterTypes()) {
            k.b(parameterType, "parameterType");
            sb.append(b.c(parameterType));
        }
        sb.append(")");
        Class<?> returnType = method.getReturnType();
        k.b(returnType, "method.returnType");
        sb.append(b.c(returnType));
        String sb2 = sb.toString();
        k.b(sb2, "sb.toString()");
        return sb2;
    }

    @NotNull
    public final String a(@NotNull Constructor<?> constructor) {
        k.f(constructor, "constructor");
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Class parameterType : constructor.getParameterTypes()) {
            k.b(parameterType, "parameterType");
            sb.append(b.c(parameterType));
        }
        sb.append(")V");
        String sb2 = sb.toString();
        k.b(sb2, "sb.toString()");
        return sb2;
    }

    @NotNull
    public final String b(@NotNull Field field) {
        k.f(field, "field");
        Class<?> type = field.getType();
        k.b(type, "field.type");
        return b.c(type);
    }
}
