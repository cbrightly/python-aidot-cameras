package androidx.work.impl;

import android.content.Context;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.content.ContextCompat;
import androidx.work.Configuration;
import androidx.work.ForegroundInfo;
import androidx.work.Logger;
import androidx.work.WorkerParameters;
import androidx.work.impl.WorkerWrapper;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class Processor implements ExecutionListener, ForegroundProcessor {
    private static final String FOREGROUND_WAKELOCK_TAG = "ProcessorForegroundLck";
    private static final String TAG = Logger.tagWithPrefix("Processor");
    private Context mAppContext;
    private Set<String> mCancelledIds;
    private Configuration mConfiguration;
    private Map<String, WorkerWrapper> mEnqueuedWorkMap = new HashMap();
    @Nullable
    private PowerManager.WakeLock mForegroundLock;
    private Map<String, WorkerWrapper> mForegroundWorkMap = new HashMap();
    private final Object mLock;
    private final List<ExecutionListener> mOuterListeners;
    private List<Scheduler> mSchedulers;
    private WorkDatabase mWorkDatabase;
    private TaskExecutor mWorkTaskExecutor;

    public Processor(@NonNull Context appContext, @NonNull Configuration configuration, @NonNull TaskExecutor workTaskExecutor, @NonNull WorkDatabase workDatabase, @NonNull List<Scheduler> schedulers) {
        this.mAppContext = appContext;
        this.mConfiguration = configuration;
        this.mWorkTaskExecutor = workTaskExecutor;
        this.mWorkDatabase = workDatabase;
        this.mSchedulers = schedulers;
        this.mCancelledIds = new HashSet();
        this.mOuterListeners = new ArrayList();
        this.mForegroundLock = null;
        this.mLock = new Object();
    }

    public boolean startWork(@NonNull String id) {
        return startWork(id, (WorkerParameters.RuntimeExtras) null);
    }

    public boolean startWork(@NonNull String id, @Nullable WorkerParameters.RuntimeExtras runtimeExtras) {
        synchronized (this.mLock) {
            if (isEnqueued(id)) {
                Logger.get().debug(TAG, String.format("Work %s is already enqueued for processing", new Object[]{id}), new Throwable[0]);
                return false;
            }
            WorkerWrapper workWrapper = new WorkerWrapper.Builder(this.mAppContext, this.mConfiguration, this.mWorkTaskExecutor, this, this.mWorkDatabase, id).withSchedulers(this.mSchedulers).withRuntimeExtras(runtimeExtras).build();
            ListenableFuture<Boolean> future = workWrapper.getFuture();
            future.addListener(new FutureListener(this, id, future), this.mWorkTaskExecutor.getMainThreadExecutor());
            this.mEnqueuedWorkMap.put(id, workWrapper);
            this.mWorkTaskExecutor.getBackgroundExecutor().execute(workWrapper);
            Logger.get().debug(TAG, String.format("%s: processing %s", new Object[]{getClass().getSimpleName(), id}), new Throwable[0]);
            return true;
        }
    }

    public void startForeground(@NonNull String workSpecId, @NonNull ForegroundInfo foregroundInfo) {
        synchronized (this.mLock) {
            Logger.get().info(TAG, String.format("Moving WorkSpec (%s) to the foreground", new Object[]{workSpecId}), new Throwable[0]);
            WorkerWrapper wrapper = this.mEnqueuedWorkMap.remove(workSpecId);
            if (wrapper != null) {
                if (this.mForegroundLock == null) {
                    PowerManager.WakeLock newWakeLock = WakeLocks.newWakeLock(this.mAppContext, FOREGROUND_WAKELOCK_TAG);
                    this.mForegroundLock = newWakeLock;
                    newWakeLock.acquire();
                }
                this.mForegroundWorkMap.put(workSpecId, wrapper);
                ContextCompat.startForegroundService(this.mAppContext, SystemForegroundDispatcher.createStartForegroundIntent(this.mAppContext, workSpecId, foregroundInfo));
            }
        }
    }

    public boolean stopForegroundWork(@NonNull String id) {
        boolean interrupt;
        synchronized (this.mLock) {
            Logger.get().debug(TAG, String.format("Processor stopping foreground work %s", new Object[]{id}), new Throwable[0]);
            interrupt = interrupt(id, this.mForegroundWorkMap.remove(id));
        }
        return interrupt;
    }

    public boolean stopWork(@NonNull String id) {
        boolean interrupt;
        synchronized (this.mLock) {
            Logger.get().debug(TAG, String.format("Processor stopping background work %s", new Object[]{id}), new Throwable[0]);
            interrupt = interrupt(id, this.mEnqueuedWorkMap.remove(id));
        }
        return interrupt;
    }

    public boolean stopAndCancelWork(@NonNull String id) {
        boolean interrupted;
        synchronized (this.mLock) {
            boolean z = true;
            Logger.get().debug(TAG, String.format("Processor cancelling %s", new Object[]{id}), new Throwable[0]);
            this.mCancelledIds.add(id);
            WorkerWrapper wrapper = this.mForegroundWorkMap.remove(id);
            if (wrapper == null) {
                z = false;
            }
            boolean isForegroundWork = z;
            if (wrapper == null) {
                wrapper = this.mEnqueuedWorkMap.remove(id);
            }
            interrupted = interrupt(id, wrapper);
            if (isForegroundWork) {
                stopForegroundService();
            }
        }
        return interrupted;
    }

    public void stopForeground(@NonNull String workSpecId) {
        synchronized (this.mLock) {
            this.mForegroundWorkMap.remove(workSpecId);
            stopForegroundService();
        }
    }

    public boolean isCancelled(@NonNull String id) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mCancelledIds.contains(id);
        }
        return contains;
    }

    public boolean hasWork() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mEnqueuedWorkMap.isEmpty()) {
                if (this.mForegroundWorkMap.isEmpty()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public boolean isEnqueued(@NonNull String workSpecId) {
        boolean z;
        synchronized (this.mLock) {
            if (!this.mEnqueuedWorkMap.containsKey(workSpecId)) {
                if (!this.mForegroundWorkMap.containsKey(workSpecId)) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public boolean isEnqueuedInForeground(@NonNull String workSpecId) {
        boolean containsKey;
        synchronized (this.mLock) {
            containsKey = this.mForegroundWorkMap.containsKey(workSpecId);
        }
        return containsKey;
    }

    public void addExecutionListener(@NonNull ExecutionListener executionListener) {
        synchronized (this.mLock) {
            this.mOuterListeners.add(executionListener);
        }
    }

    public void removeExecutionListener(@NonNull ExecutionListener executionListener) {
        synchronized (this.mLock) {
            this.mOuterListeners.remove(executionListener);
        }
    }

    public void onExecuted(@NonNull String workSpecId, boolean needsReschedule) {
        synchronized (this.mLock) {
            this.mEnqueuedWorkMap.remove(workSpecId);
            Logger.get().debug(TAG, String.format("%s %s executed; reschedule = %s", new Object[]{getClass().getSimpleName(), workSpecId, Boolean.valueOf(needsReschedule)}), new Throwable[0]);
            for (ExecutionListener executionListener : this.mOuterListeners) {
                executionListener.onExecuted(workSpecId, needsReschedule);
            }
        }
    }

    private void stopForegroundService() {
        synchronized (this.mLock) {
            if (!(!this.mForegroundWorkMap.isEmpty())) {
                try {
                    this.mAppContext.startService(SystemForegroundDispatcher.createStopForegroundIntent(this.mAppContext));
                } catch (Throwable throwable) {
                    Logger.get().error(TAG, "Unable to stop foreground service", throwable);
                }
                PowerManager.WakeLock wakeLock = this.mForegroundLock;
                if (wakeLock != null) {
                    wakeLock.release();
                    this.mForegroundLock = null;
                }
            }
        }
    }

    private static boolean interrupt(@NonNull String id, @Nullable WorkerWrapper wrapper) {
        if (wrapper != null) {
            wrapper.interrupt();
            Logger.get().debug(TAG, String.format("WorkerWrapper interrupted for %s", new Object[]{id}), new Throwable[0]);
            return true;
        }
        Logger.get().debug(TAG, String.format("WorkerWrapper could not be found for %s", new Object[]{id}), new Throwable[0]);
        return false;
    }

    public static class FutureListener implements Runnable {
        @NonNull
        private ExecutionListener mExecutionListener;
        @NonNull
        private ListenableFuture<Boolean> mFuture;
        @NonNull
        private String mWorkSpecId;

        FutureListener(@NonNull ExecutionListener executionListener, @NonNull String workSpecId, @NonNull ListenableFuture<Boolean> future) {
            this.mExecutionListener = executionListener;
            this.mWorkSpecId = workSpecId;
            this.mFuture = future;
        }

        public void run() {
            boolean needsReschedule;
            try {
                needsReschedule = this.mFuture.get().booleanValue();
            } catch (InterruptedException | ExecutionException e) {
                needsReschedule = true;
            }
            this.mExecutionListener.onExecuted(this.mWorkSpecId, needsReschedule);
        }
    }
}
