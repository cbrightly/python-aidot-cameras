package io.reactivex.subjects;

import io.reactivex.l;
import io.reactivex.q;

/* compiled from: Subject */
public abstract class d<T> extends l<T> implements q<T> {
    public final d<T> n0() {
        if (this instanceof c) {
            return this;
        }
        return new c(this);
    }
}
