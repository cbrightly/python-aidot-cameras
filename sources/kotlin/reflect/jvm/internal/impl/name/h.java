package kotlin.reflect.jvm.internal.impl.name;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SpecialNames */
public class h {
    public static final f a = f.k("<no name provided>");
    public static final f b = f.k("<root package>");
    public static final f c = f.f("Companion");
    public static final f d = f.f("no_name_in_PSI_3d19d79d_1ba9_4cd0_b7f5_b46aa3cd5d40");
    public static final f e = f.k("<anonymous>");

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 1:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/name/SpecialNames";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/name/SpecialNames";
                break;
            default:
                objArr[1] = "safeIdentifier";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "isSafeIdentifier";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 1:
                th = new IllegalArgumentException(format);
                break;
            default:
                th = new IllegalStateException(format);
                break;
        }
        throw th;
    }

    @NotNull
    public static f c(@Nullable f name) {
        f fVar = (name == null || name.h()) ? d : name;
        if (fVar == null) {
            a(0);
        }
        return fVar;
    }

    public static boolean b(@NotNull f name) {
        if (name == null) {
            a(1);
        }
        return !name.b().isEmpty() && !name.h();
    }
}
