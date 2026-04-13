package androidx.camera.core.impl.utils.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class FutureChain<V> implements ListenableFuture<V> {
    @Nullable
    CallbackToFutureAdapter.Completer<V> mCompleter;
    @NonNull
    private final ListenableFuture<V> mDelegate;

    @NonNull
    public static <V> FutureChain<V> from(@NonNull ListenableFuture<V> future) {
        return future instanceof FutureChain ? (FutureChain) future : new FutureChain<>(future);
    }

    @NonNull
    public final <T> FutureChain<T> transformAsync(@NonNull AsyncFunction<? super V, T> function, @NonNull Executor executor) {
        return (FutureChain) Futures.transformAsync(this, function, executor);
    }

    @NonNull
    public final <T> FutureChain<T> transform(@NonNull Function<? super V, T> function, @NonNull Executor executor) {
        return (FutureChain) Futures.transform(this, function, executor);
    }

    public final void addCallback(@NonNull FutureCallback<? super V> callback, @NonNull Executor executor) {
        Futures.addCallback(this, callback, executor);
    }

    FutureChain(@NonNull ListenableFuture<V> delegate) {
        this.mDelegate = (ListenableFuture) Preconditions.checkNotNull(delegate);
    }

    FutureChain() {
        this.mDelegate = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver<V>() {
            public Object attachCompleter(@NonNull CallbackToFutureAdapter.Completer<V> completer) {
                Preconditions.checkState(FutureChain.this.mCompleter == null, "The result can only set once!");
                FutureChain.this.mCompleter = completer;
                return "FutureChain[" + FutureChain.this + "]";
            }
        });
    }

    public void addListener(@NonNull Runnable listener, @NonNull Executor executor) {
        this.mDelegate.addListener(listener, executor);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return this.mDelegate.cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return this.mDelegate.isCancelled();
    }

    public boolean isDone() {
        return this.mDelegate.isDone();
    }

    @Nullable
    public V get() {
        return this.mDelegate.get();
    }

    @Nullable
    public V get(long timeout, @NonNull TimeUnit unit) {
        return this.mDelegate.get(timeout, unit);
    }

    /* access modifiers changed from: package-private */
    public boolean set(@Nullable V value) {
        CallbackToFutureAdapter.Completer<V> completer = this.mCompleter;
        if (completer != null) {
            return completer.set(value);
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean setException(@NonNull Throwable throwable) {
        CallbackToFutureAdapter.Completer<V> completer = this.mCompleter;
        if (completer != null) {
            return completer.setException(throwable);
        }
        return false;
    }
}
