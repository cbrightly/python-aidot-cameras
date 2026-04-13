package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.constants.g;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AnnotationDescriptorImpl */
public class d implements c {
    private final b0 a;
    private final Map<f, g<?>> b;
    private final o0 c;

    private static /* synthetic */ void b(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 3:
            case 4:
            case 5:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "valueArguments";
                break;
            case 2:
                objArr[0] = "source";
                break;
            case 3:
            case 4:
            case 5:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotationDescriptorImpl";
                break;
            default:
                objArr[0] = "annotationType";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getType";
                break;
            case 4:
                objArr[1] = "getAllValueArguments";
                break;
            case 5:
                objArr[1] = "getSource";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotationDescriptorImpl";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    public d(@NotNull b0 annotationType, @NotNull Map<f, g<?>> valueArguments, @NotNull o0 source) {
        if (annotationType == null) {
            b(0);
        }
        if (valueArguments == null) {
            b(1);
        }
        if (source == null) {
            b(2);
        }
        this.a = annotationType;
        this.b = valueArguments;
        this.c = source;
    }

    @NotNull
    public b0 getType() {
        b0 b0Var = this.a;
        if (b0Var == null) {
            b(3);
        }
        return b0Var;
    }

    @Nullable
    public b e() {
        return c.a.a(this);
    }

    @NotNull
    public Map<f, g<?>> a() {
        Map<f, g<?>> map = this.b;
        if (map == null) {
            b(4);
        }
        return map;
    }

    @NotNull
    public o0 n() {
        o0 o0Var = this.c;
        if (o0Var == null) {
            b(5);
        }
        return o0Var;
    }

    public String toString() {
        return kotlin.reflect.jvm.internal.impl.renderer.c.f.s(this, (e) null);
    }
}
