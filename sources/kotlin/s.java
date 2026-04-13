package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Tuples.kt */
public final class s<A, B, C> implements Serializable {
    private final A first;
    private final B second;
    private final C third;

    public static /* synthetic */ s copy$default(s sVar, A a, B b, C c, int i, Object obj) {
        if ((i & 1) != 0) {
            a = sVar.first;
        }
        if ((i & 2) != 0) {
            b = sVar.second;
        }
        if ((i & 4) != 0) {
            c = sVar.third;
        }
        return sVar.copy(a, b, c);
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public final C component3() {
        return this.third;
    }

    @NotNull
    public final s<A, B, C> copy(A a, B b, C c) {
        return new s<>(a, b, c);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        return k.a(this.first, sVar.first) && k.a(this.second, sVar.second) && k.a(this.third, sVar.third);
    }

    public int hashCode() {
        A a = this.first;
        int i = 0;
        int hashCode = (a != null ? a.hashCode() : 0) * 31;
        B b = this.second;
        int hashCode2 = (hashCode + (b != null ? b.hashCode() : 0)) * 31;
        C c = this.third;
        if (c != null) {
            i = c.hashCode();
        }
        return hashCode2 + i;
    }

    public s(A first2, B second2, C third2) {
        this.first = first2;
        this.second = second2;
        this.third = third2;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public final C getThird() {
        return this.third;
    }

    @NotNull
    public String toString() {
        return '(' + this.first + ", " + this.second + ", " + this.third + ')';
    }
}
