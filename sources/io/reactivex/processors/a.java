package io.reactivex.processors;

import io.reactivex.e;
import io.reactivex.h;

/* compiled from: FlowableProcessor */
public abstract class a<T> extends e<T> implements h<T>, org.reactivestreams.a, h {
    public final a<T> W() {
        if (this instanceof d) {
            return this;
        }
        return new d(this);
    }
}
