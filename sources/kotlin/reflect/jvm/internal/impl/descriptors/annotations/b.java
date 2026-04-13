package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import org.jetbrains.annotations.NotNull;

/* compiled from: AnnotatedImpl */
public class b implements a {
    private final g c;

    private static /* synthetic */ void u(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 1:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotatedImpl";
                break;
            default:
                objArr[0] = "annotations";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getAnnotations";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/descriptors/annotations/AnnotatedImpl";
                break;
        }
        switch (i) {
            case 1:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 1:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    public b(@NotNull g annotations) {
        if (annotations == null) {
            u(0);
        }
        this.c = annotations;
    }

    @NotNull
    public g getAnnotations() {
        g gVar = this.c;
        if (gVar == null) {
            u(1);
        }
        return gVar;
    }
}
