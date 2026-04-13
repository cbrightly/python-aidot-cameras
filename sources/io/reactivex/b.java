package io.reactivex;

import io.reactivex.plugins.a;

/* compiled from: Completable */
public abstract class b {
    public static b a(Throwable error) {
        io.reactivex.internal.functions.b.e(error, "error is null");
        return a.j(new io.reactivex.internal.operators.completable.a(error));
    }
}
