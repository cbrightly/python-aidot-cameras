package kotlin;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KotlinVersion.kt */
public final class e implements Comparable<e> {
    @NotNull
    public static final e c = f.a();
    @NotNull
    public static final a d = new a((DefaultConstructorMarker) null);
    private final int f;
    private final int q;
    private final int x;
    private final int y;

    public e(int major, int minor, int patch) {
        this.q = major;
        this.x = minor;
        this.y = patch;
        this.f = b(major, minor, patch);
    }

    private final int b(int major, int minor, int patch) {
        if (major >= 0 && 255 >= major && minor >= 0 && 255 >= minor && patch >= 0 && 255 >= patch) {
            return (major << 16) + (minor << 8) + patch;
        }
        throw new IllegalArgumentException(("Version components are out of range: " + major + '.' + minor + '.' + patch).toString());
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.q);
        sb.append('.');
        sb.append(this.x);
        sb.append('.');
        sb.append(this.y);
        return sb.toString();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        e otherVersion = (e) (!(other instanceof e) ? null : other);
        if (otherVersion == null) {
            return false;
        }
        if (this.f == otherVersion.f) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.f;
    }

    /* renamed from: a */
    public int compareTo(@NotNull e other) {
        k.e(other, "other");
        return this.f - other.f;
    }

    /* compiled from: KotlinVersion.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
