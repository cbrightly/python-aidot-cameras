package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.Operation;
import androidx.work.WorkInfo;
import androidx.work.impl.OperationImpl;
import androidx.work.impl.Scheduler;
import androidx.work.impl.Schedulers;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.WorkSpecDao;
import java.util.LinkedList;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class CancelWorkRunnable implements Runnable {
    private final OperationImpl mOperation = new OperationImpl();

    /* access modifiers changed from: package-private */
    public abstract void runInternal();

    public Operation getOperation() {
        return this.mOperation;
    }

    public void run() {
        try {
            runInternal();
            this.mOperation.setState(Operation.SUCCESS);
        } catch (Throwable throwable) {
            this.mOperation.setState(new Operation.State.FAILURE(throwable));
        }
    }

    /* access modifiers changed from: package-private */
    public void cancel(WorkManagerImpl workManagerImpl, String workSpecId) {
        iterativelyCancelWorkAndDependents(workManagerImpl.getWorkDatabase(), workSpecId);
        workManagerImpl.getProcessor().stopAndCancelWork(workSpecId);
        for (Scheduler scheduler : workManagerImpl.getSchedulers()) {
            scheduler.cancel(workSpecId);
        }
    }

    /* access modifiers changed from: package-private */
    public void reschedulePendingWorkers(WorkManagerImpl workManagerImpl) {
        Schedulers.schedule(workManagerImpl.getConfiguration(), workManagerImpl.getWorkDatabase(), workManagerImpl.getSchedulers());
    }

    private void iterativelyCancelWorkAndDependents(WorkDatabase workDatabase, String workSpecId) {
        WorkSpecDao workSpecDao = workDatabase.workSpecDao();
        DependencyDao dependencyDao = workDatabase.dependencyDao();
        LinkedList<String> idsToProcess = new LinkedList<>();
        idsToProcess.add(workSpecId);
        while (!idsToProcess.isEmpty()) {
            String id = idsToProcess.remove();
            WorkInfo.State state = workSpecDao.getState(id);
            if (!(state == WorkInfo.State.SUCCEEDED || state == WorkInfo.State.FAILED)) {
                workSpecDao.setState(WorkInfo.State.CANCELLED, id);
            }
            idsToProcess.addAll(dependencyDao.getDependentWorkIds(id));
        }
    }

    public static CancelWorkRunnable forId(@NonNull final UUID id, @NonNull final WorkManagerImpl workManagerImpl) {
        return new CancelWorkRunnable() {
            /* JADX INFO: finally extract failed */
            /* access modifiers changed from: package-private */
            @WorkerThread
            public void runInternal() {
                WorkDatabase workDatabase = WorkManagerImpl.this.getWorkDatabase();
                workDatabase.beginTransaction();
                try {
                    cancel(WorkManagerImpl.this, id.toString());
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                    reschedulePendingWorkers(WorkManagerImpl.this);
                } catch (Throwable th) {
                    workDatabase.endTransaction();
                    throw th;
                }
            }
        };
    }

    public static CancelWorkRunnable forTag(@NonNull final String tag, @NonNull final WorkManagerImpl workManagerImpl) {
        return new CancelWorkRunnable() {
            /* JADX INFO: finally extract failed */
            /* access modifiers changed from: package-private */
            @WorkerThread
            public void runInternal() {
                WorkDatabase workDatabase = WorkManagerImpl.this.getWorkDatabase();
                workDatabase.beginTransaction();
                try {
                    for (String workSpecId : workDatabase.workSpecDao().getUnfinishedWorkWithTag(tag)) {
                        cancel(WorkManagerImpl.this, workSpecId);
                    }
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                    reschedulePendingWorkers(WorkManagerImpl.this);
                } catch (Throwable th) {
                    workDatabase.endTransaction();
                    throw th;
                }
            }
        };
    }

    public static CancelWorkRunnable forName(@NonNull final String name, @NonNull final WorkManagerImpl workManagerImpl, final boolean allowReschedule) {
        return new CancelWorkRunnable() {
            /* JADX INFO: finally extract failed */
            /* access modifiers changed from: package-private */
            @WorkerThread
            public void runInternal() {
                WorkDatabase workDatabase = WorkManagerImpl.this.getWorkDatabase();
                workDatabase.beginTransaction();
                try {
                    for (String workSpecId : workDatabase.workSpecDao().getUnfinishedWorkWithName(name)) {
                        cancel(WorkManagerImpl.this, workSpecId);
                    }
                    workDatabase.setTransactionSuccessful();
                    workDatabase.endTransaction();
                    if (allowReschedule) {
                        reschedulePendingWorkers(WorkManagerImpl.this);
                    }
                } catch (Throwable th) {
                    workDatabase.endTransaction();
                    throw th;
                }
            }
        };
    }

    public static CancelWorkRunnable forAll(@NonNull final WorkManagerImpl workManagerImpl) {
        return new CancelWorkRunnable() {
            /* access modifiers changed from: package-private */
            @WorkerThread
            public void runInternal() {
                WorkDatabase workDatabase = WorkManagerImpl.this.getWorkDatabase();
                workDatabase.beginTransaction();
                try {
                    for (String workSpecId : workDatabase.workSpecDao().getAllUnfinishedWork()) {
                        cancel(WorkManagerImpl.this, workSpecId);
                    }
                    new PreferenceUtils(WorkManagerImpl.this.getWorkDatabase()).setLastCancelAllTimeMillis(System.currentTimeMillis());
                    workDatabase.setTransactionSuccessful();
                } finally {
                    workDatabase.endTransaction();
                }
            }
        };
    }
}
