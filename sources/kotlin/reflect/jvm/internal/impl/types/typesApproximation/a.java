package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CapturedTypeApproximation.kt */
public final class a<T> {
    private final T a;
    private final T b;

    public final T a() {
        return this.a;
    }

    public final T b() {
        return this.b;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        return k.a(this.a, aVar.a) && k.a(this.b, aVar.b);
    }

    public int hashCode() {
        T t = this.a;
        int i = 0;
        int hashCode = (t != null ? t.hashCode() : 0) * 31;
        T t2 = this.b;
        if (t2 != null) {
            i = t2.hashCode();
        }
        return hashCode + i;
    }

    @NotNull
    public String toString() {
        return "ApproximationBounds(lower=" + this.a + ", upper=" + this.b + ")";
    }

    public a(T lower, T upper) {
        this.a = lower;
        this.b = upper;
    }

    public final T c() {
        return this.a;
    }

    public final T d() {
        return this.b;
    }
}
