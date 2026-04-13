package kotlin.reflect.jvm.internal.impl.name;

import org.jetbrains.annotations.NotNull;

/* compiled from: Name */
public final class f implements Comparable<f> {
    @NotNull
    private final String c;
    private final boolean d;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 1:
            case 2:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
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
                objArr[0] = "kotlin/reflect/jvm/internal/impl/name/Name";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "asString";
                break;
            case 2:
                objArr[1] = "getIdentifier";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/name/Name";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                break;
            case 3:
                objArr[2] = "identifier";
                break;
            case 4:
                objArr[2] = "isValidIdentifier";
                break;
            case 5:
                objArr[2] = "special";
                break;
            case 6:
                objArr[2] = "guessByFirstCharacter";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    private f(@NotNull String name, boolean special) {
        if (name == null) {
            a(0);
        }
        this.c = name;
        this.d = special;
    }

    @NotNull
    public String b() {
        String str = this.c;
        if (str == null) {
            a(1);
        }
        return str;
    }

    @NotNull
    public String d() {
        if (!this.d) {
            String b = b();
            if (b == null) {
                a(2);
            }
            return b;
        }
        throw new IllegalStateException("not identifier: " + this);
    }

    public boolean h() {
        return this.d;
    }

    /* renamed from: c */
    public int compareTo(f that) {
        return this.c.compareTo(that.c);
    }

    @NotNull
    public static f f(@NotNull String name) {
        if (name == null) {
            a(3);
        }
        return new f(name, false);
    }

    public static boolean j(@NotNull String name) {
        if (name == null) {
            a(4);
        }
        if (name.isEmpty() || name.startsWith("<")) {
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (ch == '.' || ch == '/' || ch == '\\') {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public static f k(@NotNull String name) {
        if (name == null) {
            a(5);
        }
        if (name.startsWith("<")) {
            return new f(name, true);
        }
        throw new IllegalArgumentException("special name must start with '<': " + name);
    }

    @NotNull
    public static f e(@NotNull String name) {
        if (name == null) {
            a(6);
        }
        if (name.startsWith("<")) {
            return k(name);
        }
        return f(name);
    }

    public String toString() {
        return this.c;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof f)) {
            return false;
        }
        f name1 = (f) o;
        if (this.d == name1.d && this.c.equals(name1.c)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.c.hashCode() * 31) + (this.d ? 1 : 0);
    }
}
