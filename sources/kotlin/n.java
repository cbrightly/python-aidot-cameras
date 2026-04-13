package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Tuples.kt */
public final class n<A, B> implements Serializable {
    private final A first;
    private final B second;

    public static /* synthetic */ n copy$default(n nVar, A a, B b, int i, Object obj) {
        if ((i & 1) != 0) {
            a = nVar.first;
        }
        if ((i & 2) != 0) {
            b = nVar.second;
        }
        return nVar.copy(a, b);
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    @NotNull
    public final n<A, B> copy(A a, B b) {
        return new n<>(a, b);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof n)) {
            return false;
        }
        n nVar = (n) obj;
        return k.a(this.first, nVar.first) && k.a(this.second, nVar.second);
    }

    public int hashCode() {
        A a = this.first;
        int i = 0;
        int hashCode = (a != null ? a.hashCode() : 0) * 31;
        B b = this.second;
        if (b != null) {
            i = b.hashCode();
        }
        return hashCode + i;
    }

    public n(A first2, B second2) {
        this.first = first2;
        this.second = second2;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    @NotNull
    public String toString() {
        return '(' + this.first + ", " + this.second + ')';
    }
}
