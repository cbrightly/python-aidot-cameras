package kotlin.reflect.jvm.internal.impl.name;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.jvm.functions.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: FqNameUnsafe */
public final class c {
    private static final f a = f.k("<root>");
    private static final Pattern b = Pattern.compile("\\.");
    private static final l<String, f> c = new a();
    @NotNull
    private final String d;
    private transient b e;
    private transient c f;
    private transient f g;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
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
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "safe";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/name/FqNameUnsafe";
                break;
            case 9:
                objArr[0] = "name";
                break;
            case 15:
                objArr[0] = "segment";
                break;
            case 16:
                objArr[0] = "shortName";
                break;
            default:
                objArr[0] = "fqName";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "asString";
                break;
            case 5:
            case 6:
                objArr[1] = "toSafe";
                break;
            case 7:
            case 8:
                objArr[1] = "parent";
                break;
            case 10:
            case 11:
                objArr[1] = "shortName";
                break;
            case 12:
            case 13:
                objArr[1] = "shortNameOrSpecial";
                break;
            case 14:
                objArr[1] = "pathSegments";
                break;
            case 17:
                objArr[1] = "toString";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/name/FqNameUnsafe";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
                break;
            case 9:
                objArr[2] = "child";
                break;
            case 15:
                objArr[2] = "startsWith";
                break;
            case 16:
                objArr[2] = "topLevel";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    /* compiled from: FqNameUnsafe */
    public static final class a implements l<String, f> {
        a() {
        }

        /* renamed from: a */
        public f invoke(String name) {
            return f.e(name);
        }
    }

    c(@NotNull String fqName, @NotNull b safe) {
        if (fqName == null) {
            a(0);
        }
        if (safe == null) {
            a(1);
        }
        this.d = fqName;
        this.e = safe;
    }

    public c(@NotNull String fqName) {
        if (fqName == null) {
            a(2);
        }
        this.d = fqName;
    }

    private c(@NotNull String fqName, c parent, f shortName) {
        if (fqName == null) {
            a(3);
        }
        this.d = fqName;
        this.f = parent;
        this.g = shortName;
    }

    private void d() {
        int lastDot = this.d.lastIndexOf(46);
        if (lastDot >= 0) {
            this.g = f.e(this.d.substring(lastDot + 1));
            this.f = new c(this.d.substring(0, lastDot));
            return;
        }
        this.g = f.e(this.d);
        this.f = b.a.j();
    }

    @NotNull
    public String b() {
        String str = this.d;
        if (str == null) {
            a(4);
        }
        return str;
    }

    public boolean f() {
        return this.e != null || b().indexOf(60) < 0;
    }

    @NotNull
    public b l() {
        b bVar = this.e;
        if (bVar != null) {
            if (bVar == null) {
                a(5);
            }
            return bVar;
        }
        b bVar2 = new b(this);
        this.e = bVar2;
        if (bVar2 == null) {
            a(6);
        }
        return bVar2;
    }

    public boolean e() {
        return this.d.isEmpty();
    }

    @NotNull
    public c g() {
        c cVar = this.f;
        if (cVar != null) {
            if (cVar == null) {
                a(7);
            }
            return cVar;
        } else if (!e()) {
            d();
            c cVar2 = this.f;
            if (cVar2 == null) {
                a(8);
            }
            return cVar2;
        } else {
            throw new IllegalStateException("root");
        }
    }

    @NotNull
    public c c(@NotNull f name) {
        String childFqName;
        if (name == null) {
            a(9);
        }
        if (e()) {
            childFqName = name.b();
        } else {
            childFqName = this.d + "." + name.b();
        }
        return new c(childFqName, this, name);
    }

    @NotNull
    public f i() {
        f fVar = this.g;
        if (fVar != null) {
            if (fVar == null) {
                a(10);
            }
            return fVar;
        } else if (!e()) {
            d();
            f fVar2 = this.g;
            if (fVar2 == null) {
                a(11);
            }
            return fVar2;
        } else {
            throw new IllegalStateException("root");
        }
    }

    @NotNull
    public f j() {
        if (e()) {
            f fVar = a;
            if (fVar == null) {
                a(12);
            }
            return fVar;
        }
        f i = i();
        if (i == null) {
            a(13);
        }
        return i;
    }

    @NotNull
    public List<f> h() {
        List<f> emptyList = e() ? Collections.emptyList() : kotlin.collections.l.H(b.split(this.d), c);
        if (emptyList == null) {
            a(14);
        }
        return emptyList;
    }

    public boolean k(@NotNull f segment) {
        if (segment == null) {
            a(15);
        }
        if (e()) {
            return false;
        }
        int firstDot = this.d.indexOf(46);
        return this.d.regionMatches(0, segment.b(), 0, firstDot == -1 ? this.d.length() : firstDot);
    }

    @NotNull
    public static c m(@NotNull f shortName) {
        if (shortName == null) {
            a(16);
        }
        return new c(shortName.b(), b.a.j(), shortName);
    }

    @NotNull
    public String toString() {
        String b2 = e() ? a.b() : this.d;
        if (b2 == null) {
            a(17);
        }
        return b2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o instanceof c) && this.d.equals(((c) o).d)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.d.hashCode();
    }
}
