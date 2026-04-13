package androidx.work.impl.background.systemalarm;

import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.Logger;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.background.systemalarm.SystemAlarmDispatcher;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.WakeLocks;
import androidx.work.impl.utils.WorkTimer;
import java.util.Collections;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class DelayMetCommandHandler implements WorkConstraintsCallback, ExecutionListener, WorkTimer.TimeLimitExceededListener {
    private static final int STATE_INITIAL = 0;
    private static final int STATE_START_REQUESTED = 1;
    private static final int STATE_STOP_REQUESTED = 2;
    private static final String TAG = Logger.tagWithPrefix("DelayMetCommandHandler");
    private final Context mContext;
    private int mCurrentState = 0;
    private final SystemAlarmDispatcher mDispatcher;
    private boolean mHasConstraints = false;
    private final Object mLock = new Object();
    private final int mStartId;
    @Nullable
    private PowerManager.WakeLock mWakeLock;
    private final WorkConstraintsTracker mWorkConstraintsTracker;
    private final String mWorkSpecId;

    DelayMetCommandHandler(@NonNull Context context, int startId, @NonNull String workSpecId, @NonNull SystemAlarmDispatcher dispatcher) {
        this.mContext = context;
        this.mStartId = startId;
        this.mDispatcher = dispatcher;
        this.mWorkSpecId = workSpecId;
        this.mWorkConstraintsTracker = new WorkConstraintsTracker(context, dispatcher.getTaskExecutor(), this);
    }

    public void onAllConstraintsMet(@NonNull List<String> workSpecIds) {
        if (workSpecIds.contains(this.mWorkSpecId)) {
            synchronized (this.mLock) {
                if (this.mCurrentState == 0) {
                    this.mCurrentState = 1;
                    Logger.get().debug(TAG, String.format("onAllConstraintsMet for %s", new Object[]{this.mWorkSpecId}), new Throwable[0]);
                    if (this.mDispatcher.getProcessor().startWork(this.mWorkSpecId)) {
                        this.mDispatcher.getWorkTimer().startTimer(this.mWorkSpecId, 600000, this);
                    } else {
                        cleanUp();
                    }
                } else {
                    Logger.get().debug(TAG, String.format("Already started work for %s", new Object[]{this.mWorkSpecId}), new Throwable[0]);
                }
            }
        }
    }

    public void onExecuted(@NonNull String workSpecId, boolean needsReschedule) {
        Logger.get().debug(TAG, String.format("onExecuted %s, %s", new Object[]{workSpecId, Boolean.valueOf(needsReschedule)}), new Throwable[0]);
        cleanUp();
        if (needsReschedule) {
            Intent reschedule = CommandHandler.createScheduleWorkIntent(this.mContext, this.mWorkSpecId);
            SystemAlarmDispatcher systemAlarmDispatcher = this.mDispatcher;
            systemAlarmDispatcher.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(systemAlarmDispatcher, reschedule, this.mStartId));
        }
        if (this.mHasConstraints) {
            Intent intent = CommandHandler.createConstraintsChangedIntent(this.mContext);
            SystemAlarmDispatcher systemAlarmDispatcher2 = this.mDispatcher;
            systemAlarmDispatcher2.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(systemAlarmDispatcher2, intent, this.mStartId));
        }
    }

    public void onTimeLimitExceeded(@NonNull String workSpecId) {
        Logger.get().debug(TAG, String.format("Exceeded time limits on execution for %s", new Object[]{workSpecId}), new Throwable[0]);
        stopWork();
    }

    public void onAllConstraintsNotMet(@NonNull List<String> list) {
        stopWork();
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public void handleProcessWork() {
        this.mWakeLock = WakeLocks.newWakeLock(this.mContext, String.format("%s (%s)", new Object[]{this.mWorkSpecId, Integer.valueOf(this.mStartId)}));
        Logger logger = Logger.get();
        String str = TAG;
        logger.debug(str, String.format("Acquiring wakelock %s for WorkSpec %s", new Object[]{this.mWakeLock, this.mWorkSpecId}), new Throwable[0]);
        this.mWakeLock.acquire();
        WorkSpec workSpec = this.mDispatcher.getWorkManager().getWorkDatabase().workSpecDao().getWorkSpec(this.mWorkSpecId);
        if (workSpec == null) {
            stopWork();
            return;
        }
        boolean hasConstraints = workSpec.hasConstraints();
        this.mHasConstraints = hasConstraints;
        if (!hasConstraints) {
            Logger.get().debug(str, String.format("No constraints for %s", new Object[]{this.mWorkSpecId}), new Throwable[0]);
            onAllConstraintsMet(Collections.singletonList(this.mWorkSpecId));
            return;
        }
        this.mWorkConstraintsTracker.replace(Collections.singletonList(workSpec));
    }

    private void stopWork() {
        synchronized (this.mLock) {
            if (this.mCurrentState < 2) {
                this.mCurrentState = 2;
                Logger logger = Logger.get();
                String str = TAG;
                logger.debug(str, String.format("Stopping work for WorkSpec %s", new Object[]{this.mWorkSpecId}), new Throwable[0]);
                Intent stopWork = CommandHandler.createStopWorkIntent(this.mContext, this.mWorkSpecId);
                SystemAlarmDispatcher systemAlarmDispatcher = this.mDispatcher;
                systemAlarmDispatcher.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(systemAlarmDispatcher, stopWork, this.mStartId));
                if (this.mDispatcher.getProcessor().isEnqueued(this.mWorkSpecId)) {
                    Logger.get().debug(str, String.format("WorkSpec %s needs to be rescheduled", new Object[]{this.mWorkSpecId}), new Throwable[0]);
                    Intent reschedule = CommandHandler.createScheduleWorkIntent(this.mContext, this.mWorkSpecId);
                    SystemAlarmDispatcher systemAlarmDispatcher2 = this.mDispatcher;
                    systemAlarmDispatcher2.postOnMainThread(new SystemAlarmDispatcher.AddRunnable(systemAlarmDispatcher2, reschedule, this.mStartId));
                } else {
                    Logger.get().debug(str, String.format("Processor does not have WorkSpec %s. No need to reschedule ", new Object[]{this.mWorkSpecId}), new Throwable[0]);
                }
            } else {
                Logger.get().debug(TAG, String.format("Already stopped work for %s", new Object[]{this.mWorkSpecId}), new Throwable[0]);
            }
        }
    }

    private void cleanUp() {
        synchronized (this.mLock) {
            this.mWorkConstraintsTracker.reset();
            this.mDispatcher.getWorkTimer().stopTimer(this.mWorkSpecId);
            PowerManager.WakeLock wakeLock = this.mWakeLock;
            if (wakeLock != null && wakeLock.isHeld()) {
                Logger.get().debug(TAG, String.format("Releasing wakelock %s for WorkSpec %s", new Object[]{this.mWakeLock, this.mWorkSpecId}), new Throwable[0]);
                this.mWakeLock.release();
            }
        }
    }
}
