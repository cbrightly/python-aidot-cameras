package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Observable;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public final class ConstantObservable<T> implements Observable<T> {
    private static final ConstantObservable<Object> NULL_OBSERVABLE = new ConstantObservable<>((Object) null);
    private static final String TAG = "ConstantObservable";
    private final ListenableFuture<T> mValueFuture;

    @NonNull
    public static <U> Observable<U> withValue(@Nullable U value) {
        if (value == null) {
            return NULL_OBSERVABLE;
        }
        return new ConstantObservable(value);
    }

    private ConstantObservable(@Nullable T value) {
        this.mValueFuture = Futures.immediateFuture(value);
    }

    @NonNull
    public ListenableFuture<T> fetchData() {
        return this.mValueFuture;
    }

    public void addObserver(@NonNull Executor executor, @NonNull Observable.Observer<? super T> observer) {
        this.mValueFuture.addListener(new e(this, observer), executor);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$addObserver$0 */
    public /* synthetic */ void a(Observable.Observer observer) {
        try {
            observer.onNewData(this.mValueFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            observer.onError(e);
        }
    }

    public void removeObserver(@NonNull Observable.Observer<? super T> observer) {
    }
}
