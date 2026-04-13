package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: numbers.kt */
public final class f {
    @NotNull
    private final String a;
    private final int b;

    @NotNull
    public final String a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof f)) {
            return false;
        }
        f fVar = (f) obj;
        return k.a(this.a, fVar.a) && this.b == fVar.b;
    }

    public int hashCode() {
        String str = this.a;
        return ((str != null ? str.hashCode() : 0) * 31) + this.b;
    }

    @NotNull
    public String toString() {
        return "NumberWithRadix(number=" + this.a + ", radix=" + this.b + ")";
    }

    public f(@NotNull String number, int radix) {
        k.f(number, "number");
        this.a = number;
        this.b = radix;
    }
}
