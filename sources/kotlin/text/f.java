package kotlin.text;

import kotlin.jvm.internal.k;
import kotlin.ranges.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Regex.kt */
public final class f {
    @NotNull
    private final String a;
    @NotNull
    private final i b;

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return k.a(this.a, fVar.a) && k.a(this.b, fVar.b);
    }

    public int hashCode() {
        String str = this.a;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        i iVar = this.b;
        if (iVar != null) {
            i = iVar.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "MatchGroup(value=" + this.a + ", range=" + this.b + ")";
    }

    public f(@NotNull String value, @NotNull i range) {
        k.e(value, "value");
        k.e(range, "range");
        this.a = value;
        this.b = range;
    }

    @NotNull
    public final String a() {
        return this.a;
    }
}
