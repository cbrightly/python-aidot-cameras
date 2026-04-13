package org.hamcrest.internal;

import org.hamcrest.d;

/* compiled from: SelfDescribingValue */
public class b<T> implements d {
    private T c;

    public b(T value) {
        this.c = value;
    }

    public void describeTo(org.hamcrest.b description) {
        description.c(this.c);
    }
}
