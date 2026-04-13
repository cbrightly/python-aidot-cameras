package io.reactivex.disposables;

import io.reactivex.internal.disposables.d;
import io.reactivex.internal.functions.b;

/* compiled from: Disposables */
public final class c {
    public static b b(Runnable run) {
        b.e(run, "run is null");
        return new e(run);
    }

    public static b a() {
        return d.INSTANCE;
    }
}
