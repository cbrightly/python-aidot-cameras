package com.airbnb.lottie.model;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Pair;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: MutablePair */
public class i<T> {
    @Nullable
    T a;
    @Nullable
    T b;

    public void b(T first, T second) {
        this.a = first;
        this.b = second;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair) o;
        if (!a(p.first, this.a) || !a(p.second, this.b)) {
            return false;
        }
        return true;
    }

    private static boolean a(Object a2, Object b2) {
        return a2 == b2 || (a2 != null && a2.equals(b2));
    }

    public int hashCode() {
        T t = this.a;
        int i = 0;
        int hashCode = t == null ? 0 : t.hashCode();
        T t2 = this.b;
        if (t2 != null) {
            i = t2.hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        return "Pair{" + this.a + " " + this.b + "}";
    }
}
