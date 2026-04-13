package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WorkTimer {
    private static final String TAG = Logger.tagWithPrefix("WorkTimer");
    private final ThreadFactory mBackgroundThreadFactory;
    private final ScheduledExecutorService mExecutorService;
    final Map<String, TimeLimitExceededListener> mListeners = new HashMap();
    final Object mLock = new Object();
    final Map<String, WorkTimerRunnable> mTimerMap = new HashMap();

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface TimeLimitExceededListener {
        void onTimeLimitExceeded(@NonNull String str);
    }

    public WorkTimer() {
        AnonymousClass1 r0 = new ThreadFactory() {
            private int mThreadsCreated = 0;

            public Thread newThread(@NonNull Runnable r) {
                Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setName("WorkManager-WorkTimer-thread-" + this.mThreadsCreated);
                this.mThreadsCreated = this.mThreadsCreated + 1;
                return thread;
            }
        };
        this.mBackgroundThreadFactory = r0;
        this.mExecutorService = Executors.newSingleThreadScheduledExecutor(r0);
    }

    public void startTimer(@NonNull String workSpecId, long processingTimeMillis, @NonNull TimeLimitExceededListener listener) {
        synchronized (this.mLock) {
            Logger.get().debug(TAG, String.format("Starting timer for %s", new Object[]{workSpecId}), new Throwable[0]);
            stopTimer(workSpecId);
            WorkTimerRunnable runnable = new WorkTimerRunnable(this, workSpecId);
            this.mTimerMap.put(workSpecId, runnable);
            this.mListeners.put(workSpecId, listener);
            this.mExecutorService.schedule(runnable, processingTimeMillis, TimeUnit.MILLISECONDS);
        }
    }

    public void stopTimer(@NonNull String workSpecId) {
        synchronized (this.mLock) {
            if (this.mTimerMap.remove(workSpecId) != null) {
                Logger.get().debug(TAG, String.format("Stopping timer for %s", new Object[]{workSpecId}), new Throwable[0]);
                this.mListeners.remove(workSpecId);
            }
        }
    }

    public void onDestroy() {
        if (!this.mExecutorService.isShutdown()) {
            this.mExecutorService.shutdownNow();
        }
    }

    @VisibleForTesting
    @NonNull
    public synchronized Map<String, WorkTimerRunnable> getTimerMap() {
        return this.mTimerMap;
    }

    @VisibleForTesting
    @NonNull
    public synchronized Map<String, TimeLimitExceededListener> getListeners() {
        return this.mListeners;
    }

    @VisibleForTesting
    @NonNull
    public ScheduledExecutorService getExecutorService() {
        return this.mExecutorService;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class WorkTimerRunnable implements Runnable {
        static final String TAG = "WrkTimerRunnable";
        private final String mWorkSpecId;
        private final WorkTimer mWorkTimer;

        WorkTimerRunnable(@NonNull WorkTimer workTimer, @NonNull String workSpecId) {
            this.mWorkTimer = workTimer;
            this.mWorkSpecId = workSpecId;
        }

        public void run() {
            synchronized (this.mWorkTimer.mLock) {
                if (this.mWorkTimer.mTimerMap.remove(this.mWorkSpecId) != null) {
                    TimeLimitExceededListener listener = this.mWorkTimer.mListeners.remove(this.mWorkSpecId);
                    if (listener != null) {
                        listener.onTimeLimitExceeded(this.mWorkSpecId);
                    }
                } else {
                    Logger.get().debug(TAG, String.format("Timer with %s is already marked as complete.", new Object[]{this.mWorkSpecId}), new Throwable[0]);
                }
            }
        }
    }
}
