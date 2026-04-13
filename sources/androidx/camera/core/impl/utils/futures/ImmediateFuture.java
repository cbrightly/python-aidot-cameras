package androidx.camera.core.impl.utils.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Logger;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class ImmediateFuture<V> implements ListenableFuture<V> {
    private static final String TAG = "ImmediateFuture";

    @Nullable
    public abstract V get();

    ImmediateFuture() {
    }

    public static <V> ListenableFuture<V> nullFuture() {
        return ImmediateSuccessfulFuture.NULL_FUTURE;
    }

    public void addListener(@NonNull Runnable listener, @NonNull Executor executor) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        try {
            executor.execute(listener);
        } catch (RuntimeException e) {
            Logger.e(TAG, "Experienced RuntimeException while attempting to notify " + listener + " on Executor " + executor, e);
        }
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }

    @Nullable
    public V get(long timeout, @NonNull TimeUnit unit) {
        Preconditions.checkNotNull(unit);
        return get();
    }

    public static final class ImmediateSuccessfulFuture<V> extends ImmediateFuture<V> {
        static final ImmediateFuture<Object> NULL_FUTURE = new ImmediateSuccessfulFuture((Object) null);
        @Nullable
        private final V mValue;

        ImmediateSuccessfulFuture(@Nullable V value) {
            this.mValue = value;
        }

        @Nullable
        public V get() {
            return this.mValue;
        }

        public String toString() {
            return super.toString() + "[status=SUCCESS, result=[" + this.mValue + "]]";
        }
    }

    public static class ImmediateFailedFuture<V> extends ImmediateFuture<V> {
        @NonNull
        private final Throwable mCause;

        ImmediateFailedFuture(@NonNull Throwable cause) {
            this.mCause = cause;
        }

        @Nullable
        public V get() {
            throw new ExecutionException(this.mCause);
        }

        @NonNull
        public String toString() {
            return super.toString() + "[status=FAILURE, cause=[" + this.mCause + "]]";
        }
    }

    public static final class ImmediateFailedScheduledFuture<V> extends ImmediateFailedFuture<V> implements ScheduledFuture<V> {
        ImmediateFailedScheduledFuture(@NonNull Throwable cause) {
            super(cause);
        }

        public long getDelay(@NonNull TimeUnit timeUnit) {
            return 0;
        }

        public int compareTo(@NonNull Delayed delayed) {
            return -1;
        }
    }
}
