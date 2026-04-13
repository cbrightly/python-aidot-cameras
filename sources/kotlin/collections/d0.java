package kotlin.collections;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IndexedValue.kt */
public final class d0<T> {
    private final int a;
    private final T b;

    public final int a() {
        return this.a;
    }

    public final T b() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof d0)) {
            return false;
        }
        d0 d0Var = (d0) obj;
        return this.a == d0Var.a && k.a(this.b, d0Var.b);
    }

    public int hashCode() {
        int i = this.a * 31;
        T t = this.b;
        return i + (t != null ? t.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "IndexedValue(index=" + this.a + ", value=" + this.b + ")";
    }

    public d0(int index, T value) {
        this.a = index;
        this.b = value;
    }

    public final int c() {
        return this.a;
    }

    public final T d() {
        return this.b;
    }
}
