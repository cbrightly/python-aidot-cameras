package kotlin.reflect.jvm.internal.impl.name;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassId */
public final class a {
    private final b a;
    private final b b;
    private final boolean c;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 9:
            case 13:
            case 14:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 9:
            case 13:
            case 14:
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
                objArr[0] = "packageFqName";
                break;
            case 2:
                objArr[0] = "relativeClassName";
                break;
            case 4:
                objArr[0] = "topLevelName";
                break;
            case 5:
            case 6:
            case 7:
            case 9:
            case 13:
            case 14:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/name/ClassId";
                break;
            case 8:
                objArr[0] = "name";
                break;
            case 10:
                objArr[0] = "segment";
                break;
            case 11:
            case 12:
                objArr[0] = TypedValues.Custom.S_STRING;
                break;
            default:
                objArr[0] = "topLevelFqName";
                break;
        }
        switch (i) {
            case 5:
                objArr[1] = "getPackageFqName";
                break;
            case 6:
                objArr[1] = "getRelativeClassName";
                break;
            case 7:
                objArr[1] = "getShortClassName";
                break;
            case 9:
                objArr[1] = "asSingleFqName";
                break;
            case 13:
            case 14:
                objArr[1] = "asString";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/name/ClassId";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
                objArr[2] = "<init>";
                break;
            case 5:
            case 6:
            case 7:
            case 9:
            case 13:
            case 14:
                break;
            case 8:
                objArr[2] = "createNestedClassId";
                break;
            case 10:
                objArr[2] = "startsWith";
                break;
            case 11:
            case 12:
                objArr[2] = "fromString";
                break;
            default:
                objArr[2] = "topLevel";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 5:
            case 6:
            case 7:
            case 9:
            case 13:
            case 14:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    @NotNull
    public static a m(@NotNull b topLevelFqName) {
        if (topLevelFqName == null) {
            a(0);
        }
        return new a(topLevelFqName.e(), topLevelFqName.g());
    }

    public a(@NotNull b packageFqName, @NotNull b relativeClassName, boolean local) {
        if (packageFqName == null) {
            a(1);
        }
        if (relativeClassName == null) {
            a(2);
        }
        this.a = packageFqName;
        if (relativeClassName.d()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Class name must not be root: ");
            sb.append(packageFqName);
            sb.append(local ? " (local)" : "");
            throw new AssertionError(sb.toString());
        }
        this.b = relativeClassName;
        this.c = local;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public a(@NotNull b packageFqName, @NotNull f topLevelName) {
        this(packageFqName, b.k(topLevelName), false);
        if (packageFqName == null) {
            a(3);
        }
        if (topLevelName == null) {
            a(4);
        }
    }

    @NotNull
    public b h() {
        b bVar = this.a;
        if (bVar == null) {
            a(5);
        }
        return bVar;
    }

    @NotNull
    public b i() {
        b bVar = this.b;
        if (bVar == null) {
            a(6);
        }
        return bVar;
    }

    @NotNull
    public f j() {
        f g = this.b.g();
        if (g == null) {
            a(7);
        }
        return g;
    }

    public boolean k() {
        return this.c;
    }

    @NotNull
    public a d(@NotNull f name) {
        if (name == null) {
            a(8);
        }
        return new a(h(), this.b.c(name), this.c);
    }

    @Nullable
    public a g() {
        b parent = this.b.e();
        if (parent.d()) {
            return null;
        }
        return new a(h(), parent, this.c);
    }

    public boolean l() {
        return !this.b.e().d();
    }

    @NotNull
    public b b() {
        if (this.a.d()) {
            b bVar = this.b;
            if (bVar == null) {
                a(9);
            }
            return bVar;
        }
        return new b(this.a.b() + "." + this.b.b());
    }

    @NotNull
    public static a e(@NotNull String string) {
        if (string == null) {
            a(11);
        }
        return f(string, false);
    }

    @NotNull
    public static a f(@NotNull String string, boolean isLocal) {
        if (string == null) {
            a(12);
        }
        return new a(new b(x.a1(string, '/', "").replace('/', '.')), new b(x.S0(string, '/', string)), isLocal);
    }

    @NotNull
    public String c() {
        if (this.a.d()) {
            String b2 = this.b.b();
            if (b2 == null) {
                a(13);
            }
            return b2;
        }
        String str = this.a.b().replace('.', '/') + "/" + this.b.b();
        if (str == null) {
            a(14);
        }
        return str;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        a id = (a) o;
        if (!this.a.equals(id.a) || !this.b.equals(id.b) || this.c != id.c) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((this.a.hashCode() * 31) + this.b.hashCode()) * 31) + Boolean.valueOf(this.c).hashCode();
    }

    public String toString() {
        if (!this.a.d()) {
            return c();
        }
        return "/" + c();
    }
}
