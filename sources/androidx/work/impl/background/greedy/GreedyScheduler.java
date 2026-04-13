package androidx.work.impl.background.greedy;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.Configuration;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.ProcessUtils;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class GreedyScheduler implements Scheduler, WorkConstraintsCallback, ExecutionListener {
    private static final String TAG = Logger.tagWithPrefix("GreedyScheduler");
    private final Set<WorkSpec> mConstrainedWorkSpecs = new HashSet();
    private final Context mContext;
    private DelayedWorkTracker mDelayedWorkTracker;
    Boolean mInDefaultProcess;
    private final Object mLock;
    private boolean mRegisteredExecutionListener;
    private final WorkConstraintsTracker mWorkConstraintsTracker;
    private final WorkManagerImpl mWorkManagerImpl;

    public GreedyScheduler(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor taskExecutor, @NonNull WorkManagerImpl workManagerImpl) {
        this.mContext = context;
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkConstraintsTracker = new WorkConstraintsTracker(context, taskExecutor, this);
        this.mDelayedWorkTracker = new DelayedWorkTracker(this, configuration.getRunnableScheduler());
        this.mLock = new Object();
    }

    @VisibleForTesting
    public GreedyScheduler(@NonNull Context context, @NonNull WorkManagerImpl workManagerImpl, @NonNull WorkConstraintsTracker workConstraintsTracker) {
        this.mContext = context;
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkConstraintsTracker = workConstraintsTracker;
        this.mLock = new Object();
    }

    @VisibleForTesting
    public void setDelayedWorkTracker(@NonNull DelayedWorkTracker delayedWorkTracker) {
        this.mDelayedWorkTracker = delayedWorkTracker;
    }

    public boolean hasLimitedSchedulingSlots() {
        return false;
    }

    public void schedule(@NonNull WorkSpec... workSpecs) {
        int i;
        WorkSpec[] workSpecArr = workSpecs;
        if (this.mInDefaultProcess == null) {
            checkDefaultProcess();
        }
        if (!this.mInDefaultProcess.booleanValue()) {
            Logger.get().info(TAG, "Ignoring schedule request in a secondary process", new Throwable[0]);
            return;
        }
        registerExecutionListenerIfNeeded();
        Set<WorkSpec> constrainedWorkSpecs = new HashSet<>();
        Set<String> constrainedWorkSpecIds = new HashSet<>();
        int length = workSpecArr.length;
        int i2 = 0;
        while (i2 < length) {
            WorkSpec workSpec = workSpecArr[i2];
            long nextRunTime = workSpec.calculateNextRunTime();
            long now = System.currentTimeMillis();
            if (workSpec.state != WorkInfo.State.ENQUEUED) {
                i = length;
            } else if (now < nextRunTime) {
                DelayedWorkTracker delayedWorkTracker = this.mDelayedWorkTracker;
                if (delayedWorkTracker != null) {
                    delayedWorkTracker.schedule(workSpec);
                    i = length;
                } else {
                    i = length;
                }
            } else if (workSpec.hasConstraints()) {
                int i3 = Build.VERSION.SDK_INT;
                if (i3 >= 23 && workSpec.constraints.requiresDeviceIdle()) {
                    Logger.get().debug(TAG, String.format("Ignoring WorkSpec %s, Requires device idle.", new Object[]{workSpec}), new Throwable[0]);
                    i = length;
                } else if (i3 < 24 || !workSpec.constraints.hasContentUriTriggers()) {
                    constrainedWorkSpecs.add(workSpec);
                    constrainedWorkSpecIds.add(workSpec.id);
                    i = length;
                } else {
                    Logger.get().debug(TAG, String.format("Ignoring WorkSpec %s, Requires ContentUri triggers.", new Object[]{workSpec}), new Throwable[0]);
                    i = length;
                }
            } else {
                i = length;
                Logger.get().debug(TAG, String.format("Starting work for %s", new Object[]{workSpec.id}), new Throwable[0]);
                this.mWorkManagerImpl.startWork(workSpec.id);
            }
            i2++;
            length = i;
        }
        synchronized (this.mLock) {
            if (!constrainedWorkSpecs.isEmpty()) {
                Logger.get().debug(TAG, String.format("Starting tracking for [%s]", new Object[]{TextUtils.join(",", constrainedWorkSpecIds)}), new Throwable[0]);
                this.mConstrainedWorkSpecs.addAll(constrainedWorkSpecs);
                this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
            }
        }
    }

    private void checkDefaultProcess() {
        this.mInDefaultProcess = Boolean.valueOf(ProcessUtils.isDefaultProcess(this.mContext, this.mWorkManagerImpl.getConfiguration()));
    }

    public void cancel(@NonNull String workSpecId) {
        if (this.mInDefaultProcess == null) {
            checkDefaultProcess();
        }
        if (!this.mInDefaultProcess.booleanValue()) {
            Logger.get().info(TAG, "Ignoring schedule request in non-main process", new Throwable[0]);
            return;
        }
        registerExecutionListenerIfNeeded();
        Logger.get().debug(TAG, String.format("Cancelling work ID %s", new Object[]{workSpecId}), new Throwable[0]);
        DelayedWorkTracker delayedWorkTracker = this.mDelayedWorkTracker;
        if (delayedWorkTracker != null) {
            delayedWorkTracker.unschedule(workSpecId);
        }
        this.mWorkManagerImpl.stopWork(workSpecId);
    }

    public void onAllConstraintsMet(@NonNull List<String> workSpecIds) {
        for (String workSpecId : workSpecIds) {
            Logger.get().debug(TAG, String.format("Constraints met: Scheduling work ID %s", new Object[]{workSpecId}), new Throwable[0]);
            this.mWorkManagerImpl.startWork(workSpecId);
        }
    }

    public void onAllConstraintsNotMet(@NonNull List<String> workSpecIds) {
        for (String workSpecId : workSpecIds) {
            Logger.get().debug(TAG, String.format("Constraints not met: Cancelling work ID %s", new Object[]{workSpecId}), new Throwable[0]);
            this.mWorkManagerImpl.stopWork(workSpecId);
        }
    }

    public void onExecuted(@NonNull String workSpecId, boolean needsReschedule) {
        removeConstraintTrackingFor(workSpecId);
    }

    private void removeConstraintTrackingFor(@NonNull String workSpecId) {
        synchronized (this.mLock) {
            Iterator<WorkSpec> it = this.mConstrainedWorkSpecs.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WorkSpec constrainedWorkSpec = it.next();
                if (constrainedWorkSpec.id.equals(workSpecId)) {
                    Logger.get().debug(TAG, String.format("Stopping tracking for %s", new Object[]{workSpecId}), new Throwable[0]);
                    this.mConstrainedWorkSpecs.remove(constrainedWorkSpec);
                    this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
                    break;
                }
            }
        }
    }

    private void registerExecutionListenerIfNeeded() {
        if (!this.mRegisteredExecutionListener) {
            this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
            this.mRegisteredExecutionListener = true;
        }
    }
}
