package kotlin.jvm;

import com.meituan.robust.Constants;
import java.lang.annotation.Annotation;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.d;
import kotlin.jvm.internal.k;
import kotlin.reflect.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JvmClassMapping.kt */
public final class a {
    @NotNull
    public static final <T> Class<T> b(@NotNull c<T> $this$java) {
        k.e($this$java, "$this$java");
        Class<?> b = ((d) $this$java).b();
        if (b != null) {
            return b;
        }
        throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<T>");
    }

    @Nullable
    public static final <T> Class<T> d(@NotNull c<T> $this$javaPrimitiveType) {
        k.e($this$javaPrimitiveType, "$this$javaPrimitiveType");
        Class thisJClass = ((d) $this$javaPrimitiveType).b();
        if (thisJClass.isPrimitive()) {
            return thisJClass;
        }
        String name = thisJClass.getName();
        switch (name.hashCode()) {
            case -2056817302:
                if (name.equals(Constants.LANG_INT)) {
                    return Integer.TYPE;
                }
                break;
            case -527879800:
                if (name.equals(Constants.LANG_FLOAT)) {
                    return Float.TYPE;
                }
                break;
            case -515992664:
                if (name.equals(Constants.LANG_SHORT)) {
                    return Short.TYPE;
                }
                break;
            case 155276373:
                if (name.equals("java.lang.Character")) {
                    return Character.TYPE;
                }
                break;
            case 344809556:
                if (name.equals(Constants.LANG_BOOLEAN)) {
                    return Boolean.TYPE;
                }
                break;
            case 398507100:
                if (name.equals(Constants.LANG_BYTE)) {
                    return Byte.TYPE;
                }
                break;
            case 398795216:
                if (name.equals(Constants.LANG_LONG)) {
                    return Long.TYPE;
                }
                break;
            case 399092968:
                if (name.equals(Constants.LANG_VOID)) {
                    return Void.TYPE;
                }
                break;
            case 761287205:
                if (name.equals(Constants.LANG_DOUBLE)) {
                    return Double.TYPE;
                }
                break;
        }
        return null;
    }

    @NotNull
    public static final <T> Class<T> c(@NotNull c<T> $this$javaObjectType) {
        k.e($this$javaObjectType, "$this$javaObjectType");
        Class thisJClass = ((d) $this$javaObjectType).b();
        if (!thisJClass.isPrimitive()) {
            return thisJClass;
        }
        String name = thisJClass.getName();
        switch (name.hashCode()) {
            case -1325958191:
                if (name.equals(Constants.DOUBLE)) {
                    return Double.class;
                }
                break;
            case 104431:
                if (name.equals(Constants.INT)) {
                    return Integer.class;
                }
                break;
            case 3039496:
                if (name.equals(Constants.BYTE)) {
                    return Byte.class;
                }
                break;
            case 3052374:
                if (name.equals(Constants.CHAR)) {
                    return Character.class;
                }
                break;
            case 3327612:
                if (name.equals(Constants.LONG)) {
                    return Long.class;
                }
                break;
            case 3625364:
                if (name.equals(Constants.VOID)) {
                    return Void.class;
                }
                break;
            case 64711720:
                if (name.equals("boolean")) {
                    return Boolean.class;
                }
                break;
            case 97526364:
                if (name.equals("float")) {
                    return Float.class;
                }
                break;
            case 109413500:
                if (name.equals(Constants.SHORT)) {
                    return Short.class;
                }
                break;
        }
        return thisJClass;
    }

    @NotNull
    public static final <T> c<T> e(@NotNull Class<T> $this$kotlin) {
        k.e($this$kotlin, "$this$kotlin");
        return a0.b($this$kotlin);
    }

    @NotNull
    public static final <T extends Annotation> c<? extends T> a(@NotNull T $this$annotationClass) {
        k.e($this$annotationClass, "$this$annotationClass");
        Class<? extends Annotation> annotationType = $this$annotationClass.annotationType();
        k.d(annotationType, "(this as java.lang.annot…otation).annotationType()");
        c<? extends T> e = e(annotationType);
        if (e != null) {
            return e;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.reflect.KClass<out T>");
    }
}
