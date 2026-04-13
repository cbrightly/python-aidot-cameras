package io.reactivex.internal.fuseable;

import java.util.concurrent.Callable;

/* compiled from: ScalarCallable */
public interface e<T> extends Callable<T> {
    T call();
}
