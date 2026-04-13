package kotlin.reflect.jvm.internal.impl.resolve.jvm;

import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmClassName */
public class c {
    private final String a;
    private b b;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 3:
            case 6:
            case 7:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 6:
            case 7:
            case 8:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "classId";
                break;
            case 2:
            case 4:
                objArr[0] = "fqName";
                break;
            case 3:
            case 6:
            case 7:
            case 8:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/resolve/jvm/JvmClassName";
                break;
            default:
                objArr[0] = "internalName";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "byFqNameWithoutInnerClasses";
                break;
            case 6:
                objArr[1] = "getFqNameForClassNameWithoutDollars";
                break;
            case 7:
                objArr[1] = "getPackageFqName";
                break;
            case 8:
                objArr[1] = "getInternalName";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/jvm/JvmClassName";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "byClassId";
                break;
            case 2:
            case 4:
                objArr[2] = "byFqNameWithoutInnerClasses";
                break;
            case 3:
            case 6:
            case 7:
            case 8:
                break;
            case 5:
                objArr[2] = "<init>";
                break;
            default:
                objArr[2] = "byInternalName";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 3:
            case 6:
            case 7:
            case 8:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    @NotNull
    public static c d(@NotNull String internalName) {
        if (internalName == null) {
            a(0);
        }
        return new c(internalName);
    }

    @NotNull
    public static c b(@NotNull a classId) {
        if (classId == null) {
            a(1);
        }
        b packageFqName = classId.h();
        String relativeClassName = classId.i().b().replace('.', '$');
        if (packageFqName.d()) {
            return new c(relativeClassName);
        }
        return new c(packageFqName.b().replace('.', '/') + "/" + relativeClassName);
    }

    @NotNull
    public static c c(@NotNull b fqName) {
        if (fqName == null) {
            a(2);
        }
        c r = new c(fqName.b().replace('.', '/'));
        r.b = fqName;
        return r;
    }

    private c(@NotNull String internalName) {
        if (internalName == null) {
            a(5);
        }
        this.a = internalName;
    }

    @NotNull
    public b e() {
        return new b(this.a.replace('/', '.'));
    }

    @NotNull
    public b g() {
        int lastSlash = this.a.lastIndexOf("/");
        if (lastSlash != -1) {
            return new b(this.a.substring(0, lastSlash).replace('/', '.'));
        }
        b bVar = b.a;
        if (bVar == null) {
            a(7);
        }
        return bVar;
    }

    @NotNull
    public String f() {
        String str = this.a;
        if (str == null) {
            a(8);
        }
        return str;
    }

    public String toString() {
        return this.a;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.a.equals(((c) o).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
