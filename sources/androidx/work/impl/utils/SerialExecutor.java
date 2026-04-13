package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;

public class SerialExecutor implements Executor {
    private volatile Runnable mActive;
    private final Executor mExecutor;
    private final Object mLock = new Object();
    private final ArrayDeque<Task> mTasks = new ArrayDeque<>();

    public SerialExecutor(@NonNull Executor executor) {
        this.mExecutor = executor;
    }

    public void execute(@NonNull Runnable command) {
        synchronized (this.mLock) {
            this.mTasks.add(new Task(this, command));
            if (this.mActive == null) {
                scheduleNext();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void scheduleNext() {
        synchronized (this.mLock) {
            Runnable poll = this.mTasks.poll();
            this.mActive = poll;
            if (poll != null) {
                this.mExecutor.execute(this.mActive);
            }
        }
    }

    public boolean hasPendingTasks() {
        boolean z;
        synchronized (this.mLock) {
            z = !this.mTasks.isEmpty();
        }
        return z;
    }

    @VisibleForTesting
    @NonNull
    public Executor getDelegatedExecutor() {
        return this.mExecutor;
    }

    public static class Task implements Runnable {
        final Runnable mRunnable;
        final SerialExecutor mSerialExecutor;

        Task(@NonNull SerialExecutor serialExecutor, @NonNull Runnable runnable) {
            this.mSerialExecutor = serialExecutor;
            this.mRunnable = runnable;
        }

        public void run() {
            try {
                this.mRunnable.run();
            } finally {
                this.mSerialExecutor.scheduleNext();
            }
        }
    }
}
