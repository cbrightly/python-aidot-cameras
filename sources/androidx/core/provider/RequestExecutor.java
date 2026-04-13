package androidx.core.provider;

import android.os.Handler;
import android.os.Process;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.core.util.Preconditions;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RequestExecutor {
    private RequestExecutor() {
    }

    static <T> void execute(@NonNull Executor executor, @NonNull Callable<T> callable, @NonNull Consumer<T> consumer) {
        executor.execute(new ReplyRunnable(CalleeHandler.create(), callable, consumer));
    }

    static <T> T submit(@NonNull ExecutorService executor, @NonNull Callable<T> callable, @IntRange(from = 0) int timeoutMillis) {
        try {
            return executor.submit(callable).get((long) timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e2) {
            throw e2;
        } catch (TimeoutException e3) {
            throw new InterruptedException("timeout");
        }
    }

    static ThreadPoolExecutor createDefaultExecutor(@NonNull String threadName, int threadPriority, @IntRange(from = 0) int keepAliveTimeInMillis) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 1, (long) keepAliveTimeInMillis, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new DefaultThreadFactory(threadName, threadPriority));
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    static Executor createHandlerExecutor(@NonNull Handler handler) {
        return new HandlerExecutor(handler);
    }

    public static class HandlerExecutor implements Executor {
        private final Handler mHandler;

        HandlerExecutor(@NonNull Handler handler) {
            this.mHandler = (Handler) Preconditions.checkNotNull(handler);
        }

        public void execute(@NonNull Runnable command) {
            if (!this.mHandler.post((Runnable) Preconditions.checkNotNull(command))) {
                throw new RejectedExecutionException(this.mHandler + " is shutting down");
            }
        }
    }

    public static class ReplyRunnable<T> implements Runnable {
        @NonNull
        private Callable<T> mCallable;
        @NonNull
        private Consumer<T> mConsumer;
        @NonNull
        private Handler mHandler;

        ReplyRunnable(@NonNull Handler handler, @NonNull Callable<T> callable, @NonNull Consumer<T> consumer) {
            this.mCallable = callable;
            this.mConsumer = consumer;
            this.mHandler = handler;
        }

        public void run() {
            Exception e;
            try {
                e = this.mCallable.call();
            } catch (Exception e2) {
                e = null;
            }
            final Exception result = e;
            final Consumer<T> consumer = this.mConsumer;
            this.mHandler.post(new Runnable() {
                public void run() {
                    consumer.accept(result);
                }
            });
        }
    }

    public static class DefaultThreadFactory implements ThreadFactory {
        private int mPriority;
        private String mThreadName;

        DefaultThreadFactory(@NonNull String threadName, int priority) {
            this.mThreadName = threadName;
            this.mPriority = priority;
        }

        public Thread newThread(Runnable runnable) {
            return new ProcessPriorityThread(runnable, this.mThreadName, this.mPriority);
        }

        public static class ProcessPriorityThread extends Thread {
            private final int mPriority;

            ProcessPriorityThread(Runnable target, String name, int priority) {
                super(target, name);
                this.mPriority = priority;
            }

            public void run() {
                Process.setThreadPriority(this.mPriority);
                super.run();
            }
        }
    }
}
