package io.reactivex;

import io.reactivex.disposables.b;
import io.reactivex.functions.d;

/* compiled from: ObservableEmitter */
public interface m<T> extends d<T> {
    boolean isDisposed();

    void setCancellable(d dVar);

    void setDisposable(b bVar);
}
