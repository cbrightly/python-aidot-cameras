package com.trello.rxlifecycle3;

import com.trello.rxlifecycle3.internal.a;
import io.reactivex.e;
import io.reactivex.i;
import io.reactivex.l;
import io.reactivex.o;
import io.reactivex.p;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
/* compiled from: LifecycleTransformer */
public final class c<T> implements p<T, T>, i<T, T> {
    final l<?> a;

    c(l<?> observable) {
        a.a(observable, "observable == null");
        this.a = observable;
    }

    public o<T> a(l<T> upstream) {
        return upstream.d0(this.a);
    }

    public org.reactivestreams.a<T> b(e<T> upstream) {
        return upstream.O(this.a.i0(io.reactivex.a.LATEST));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.a.equals(((c) o).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return "LifecycleTransformer{observable=" + this.a + '}';
    }
}
