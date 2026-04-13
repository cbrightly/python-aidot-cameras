package kotlin.reflect.jvm.internal.impl.name;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: FqName */
public final class b {
    public static final b a = new b("");
    @NotNull
    private final c b;
    private transient b c;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[0] = "fqName";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/name/FqName";
                break;
            case 8:
                objArr[0] = "name";
                break;
            case 12:
                objArr[0] = "segment";
                break;
            case 13:
                objArr[0] = "shortName";
                break;
            default:
                objArr[0] = "names";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "asString";
                break;
            case 5:
                objArr[1] = "toUnsafe";
                break;
            case 6:
            case 7:
                objArr[1] = "parent";
                break;
            case 9:
                objArr[1] = "shortName";
                break;
            case 10:
                objArr[1] = "shortNameOrSpecial";
                break;
            case 11:
                objArr[1] = "pathSegments";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/name/FqName";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                break;
            case 8:
                objArr[2] = "child";
                break;
            case 12:
                objArr[2] = "startsWith";
                break;
            case 13:
                objArr[2] = "topLevel";
                break;
            default:
                objArr[2] = "fromSegments";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    public b(@NotNull String fqName) {
        if (fqName == null) {
            a(1);
        }
        this.b = new c(fqName, this);
    }

    public b(@NotNull c fqName) {
        if (fqName == null) {
            a(2);
        }
        this.b = fqName;
    }

    private b(@NotNull c fqName, b parent) {
        if (fqName == null) {
            a(3);
        }
        this.b = fqName;
        this.c = parent;
    }

    @NotNull
    public String b() {
        String b2 = this.b.b();
        if (b2 == null) {
            a(4);
        }
        return b2;
    }

    @NotNull
    public c j() {
        c cVar = this.b;
        if (cVar == null) {
            a(5);
        }
        return cVar;
    }

    public boolean d() {
        return this.b.e();
    }

    @NotNull
    public b e() {
        b bVar = this.c;
        if (bVar != null) {
            if (bVar == null) {
                a(6);
            }
            return bVar;
        } else if (!d()) {
            b bVar2 = new b(this.b.g());
            this.c = bVar2;
            if (bVar2 == null) {
                a(7);
            }
            return bVar2;
        } else {
            throw new IllegalStateException("root");
        }
    }

    @NotNull
    public b c(@NotNull f name) {
        if (name == null) {
            a(8);
        }
        return new b(this.b.c(name), this);
    }

    @NotNull
    public f g() {
        f i = this.b.i();
        if (i == null) {
            a(9);
        }
        return i;
    }

    @NotNull
    public f h() {
        f j = this.b.j();
        if (j == null) {
            a(10);
        }
        return j;
    }

    @NotNull
    public List<f> f() {
        List<f> h = this.b.h();
        if (h == null) {
            a(11);
        }
        return h;
    }

    public boolean i(@NotNull f segment) {
        if (segment == null) {
            a(12);
        }
        return this.b.k(segment);
    }

    @NotNull
    public static b k(@NotNull f shortName) {
        if (shortName == null) {
            a(13);
        }
        return new b(c.m(shortName));
    }

    public String toString() {
        return this.b.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o instanceof b) && this.b.equals(((b) o).b)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.b.hashCode();
    }
}
