package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.Processor;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpecDao;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class StopWorkRunnable implements Runnable {
    private static final String TAG = Logger.tagWithPrefix("StopWorkRunnable");
    private final boolean mStopInForeground;
    private final WorkManagerImpl mWorkManagerImpl;
    private final String mWorkSpecId;

    public StopWorkRunnable(@NonNull WorkManagerImpl workManagerImpl, @NonNull String workSpecId, boolean stopInForeground) {
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkSpecId = workSpecId;
        this.mStopInForeground = stopInForeground;
    }

    public void run() {
        boolean isStopped;
        WorkDatabase workDatabase = this.mWorkManagerImpl.getWorkDatabase();
        Processor processor = this.mWorkManagerImpl.getProcessor();
        WorkSpecDao workSpecDao = workDatabase.workSpecDao();
        workDatabase.beginTransaction();
        try {
            boolean isForegroundWork = processor.isEnqueuedInForeground(this.mWorkSpecId);
            if (this.mStopInForeground) {
                isStopped = this.mWorkManagerImpl.getProcessor().stopForegroundWork(this.mWorkSpecId);
            } else {
                if (!isForegroundWork && workSpecDao.getState(this.mWorkSpecId) == WorkInfo.State.RUNNING) {
                    workSpecDao.setState(WorkInfo.State.ENQUEUED, this.mWorkSpecId);
                }
                isStopped = this.mWorkManagerImpl.getProcessor().stopWork(this.mWorkSpecId);
            }
            Logger.get().debug(TAG, String.format("StopWorkRunnable for %s; Processor.stopWork = %s", new Object[]{this.mWorkSpecId, Boolean.valueOf(isStopped)}), new Throwable[0]);
            workDatabase.setTransactionSuccessful();
        } finally {
            workDatabase.endTransaction();
        }
    }
}
