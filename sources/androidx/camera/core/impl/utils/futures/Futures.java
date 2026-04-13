package androidx.camera.core.impl.utils.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.ImmediateFuture;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

public final class Futures {
    private static final Function<?, ?> IDENTITY_FUNCTION = new Function<Object, Object>() {
        public Object apply(Object input) {
            return input;
        }
    };

    @NonNull
    public static <V> ListenableFuture<V> immediateFuture(@Nullable V value) {
        if (value == null) {
            return ImmediateFuture.nullFuture();
        }
        return new ImmediateFuture.ImmediateSuccessfulFuture(value);
    }

    @NonNull
    public static <V> ListenableFuture<V> immediateFailedFuture(@NonNull Throwable cause) {
        return new ImmediateFuture.ImmediateFailedFuture(cause);
    }

    @NonNull
    public static <V> ScheduledFuture<V> immediateFailedScheduledFuture(@NonNull Throwable cause) {
        return new ImmediateFuture.ImmediateFailedScheduledFuture(cause);
    }

    @NonNull
    public static <I, O> ListenableFuture<O> transformAsync(@NonNull ListenableFuture<I> input, @NonNull AsyncFunction<? super I, ? extends O> function, @NonNull Executor executor) {
        ChainingListenableFuture<I, O> output = new ChainingListenableFuture<>(function, input);
        input.addListener(output, executor);
        return output;
    }

    @NonNull
    public static <I, O> ListenableFuture<O> transform(@NonNull ListenableFuture<I> input, @NonNull final Function<? super I, ? extends O> function, @NonNull Executor executor) {
        Preconditions.checkNotNull(function);
        return transformAsync(input, new AsyncFunction<I, O>() {
            public ListenableFuture<O> apply(I input) {
                return Futures.immediateFuture(Function.this.apply(input));
            }
        }, executor);
    }

    public static <V> void propagate(@NonNull ListenableFuture<V> input, @NonNull CallbackToFutureAdapter.Completer<V> completer) {
        propagateTransform(input, IDENTITY_FUNCTION, completer, CameraXExecutors.directExecutor());
    }

    public static <I, O> void propagateTransform(@NonNull ListenableFuture<I> input, @NonNull Function<? super I, ? extends O> function, @NonNull CallbackToFutureAdapter.Completer<O> completer, @NonNull Executor executor) {
        propagateTransform(true, input, function, completer, executor);
    }

    /* access modifiers changed from: private */
    public static <I, O> void propagateTransform(boolean propagateCancellation, @NonNull final ListenableFuture<I> input, @NonNull final Function<? super I, ? extends O> function, @NonNull final CallbackToFutureAdapter.Completer<O> completer, @NonNull Executor executor) {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(completer);
        Preconditions.checkNotNull(executor);
        addCallback(input, new FutureCallback<I>() {
            public void onSuccess(@Nullable I result) {
                try {
                    CallbackToFutureAdapter.Completer.this.set(function.apply(result));
                } catch (Throwable t) {
                    CallbackToFutureAdapter.Completer.this.setException(t);
                }
            }

            public void onFailure(Throwable t) {
                CallbackToFutureAdapter.Completer.this.setException(t);
            }
        }, executor);
        if (propagateCancellation) {
            completer.addCancellationListener(new Runnable() {
                public void run() {
                    ListenableFuture.this.cancel(true);
                }
            }, CameraXExecutors.directExecutor());
        }
    }

    @NonNull
    public static <V> ListenableFuture<V> nonCancellationPropagating(@NonNull ListenableFuture<V> future) {
        Preconditions.checkNotNull(future);
        if (future.isDone()) {
            return future;
        }
        return CallbackToFutureAdapter.getFuture(new a(future));
    }

    @NonNull
    public static <V> ListenableFuture<List<V>> successfulAsList(@NonNull Collection<? extends ListenableFuture<? extends V>> futures) {
        return new ListFuture(new ArrayList(futures), false, CameraXExecutors.directExecutor());
    }

    @NonNull
    public static <V> ListenableFuture<List<V>> allAsList(@NonNull Collection<? extends ListenableFuture<? extends V>> futures) {
        return new ListFuture(new ArrayList(futures), true, CameraXExecutors.directExecutor());
    }

    public static <V> void addCallback(@NonNull ListenableFuture<V> future, @NonNull FutureCallback<? super V> callback, @NonNull Executor executor) {
        Preconditions.checkNotNull(callback);
        future.addListener(new CallbackListener(future, callback), executor);
    }

    public static final class CallbackListener<V> implements Runnable {
        final FutureCallback<? super V> mCallback;
        final Future<V> mFuture;

        CallbackListener(Future<V> future, FutureCallback<? super V> callback) {
            this.mFuture = future;
            this.mCallback = callback;
        }

        public void run() {
            try {
                this.mCallback.onSuccess(Futures.getDone(this.mFuture));
            } catch (ExecutionException e) {
                this.mCallback.onFailure(e.getCause());
            } catch (Error | RuntimeException e2) {
                this.mCallback.onFailure(e2);
            }
        }

        public String toString() {
            return getClass().getSimpleName() + "," + this.mCallback;
        }
    }

    @Nullable
    public static <V> V getDone(@NonNull Future<V> future) {
        boolean isDone = future.isDone();
        Preconditions.checkState(isDone, "Future was expected to be done, " + future);
        return getUninterruptibly(future);
    }

    @Nullable
    public static <V> V getUninterruptibly(@NonNull Future<V> future) {
        V v;
        boolean interrupted = false;
        while (true) {
            try {
                v = future.get();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            } catch (Throwable th) {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        return v;
    }

    private Futures() {
    }
}
