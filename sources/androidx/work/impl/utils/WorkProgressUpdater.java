package androidx.work.impl.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.Data;
import androidx.work.Logger;
import androidx.work.ProgressUpdater;
import androidx.work.WorkInfo;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.model.WorkProgress;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WorkProgressUpdater implements ProgressUpdater {
    static final String TAG = Logger.tagWithPrefix("WorkProgressUpdater");
    final TaskExecutor mTaskExecutor;
    final WorkDatabase mWorkDatabase;

    public WorkProgressUpdater(@NonNull WorkDatabase workDatabase, @NonNull TaskExecutor taskExecutor) {
        this.mWorkDatabase = workDatabase;
        this.mTaskExecutor = taskExecutor;
    }

    @NonNull
    public ListenableFuture<Void> updateProgress(@NonNull Context context, @NonNull final UUID id, @NonNull final Data data) {
        final SettableFuture<Void> future = SettableFuture.create();
        this.mTaskExecutor.executeOnBackgroundThread(new Runnable() {
            public void run() {
                String workSpecId = id.toString();
                Logger logger = Logger.get();
                String str = WorkProgressUpdater.TAG;
                logger.debug(str, String.format("Updating progress for %s (%s)", new Object[]{id, data}), new Throwable[0]);
                WorkProgressUpdater.this.mWorkDatabase.beginTransaction();
                try {
                    WorkSpec workSpec = WorkProgressUpdater.this.mWorkDatabase.workSpecDao().getWorkSpec(workSpecId);
                    if (workSpec != null) {
                        if (workSpec.state == WorkInfo.State.RUNNING) {
                            WorkProgressUpdater.this.mWorkDatabase.workProgressDao().insert(new WorkProgress(workSpecId, data));
                        } else {
                            Logger.get().warning(str, String.format("Ignoring setProgressAsync(...). WorkSpec (%s) is not in a RUNNING state.", new Object[]{workSpecId}), new Throwable[0]);
                        }
                        future.set(null);
                        WorkProgressUpdater.this.mWorkDatabase.setTransactionSuccessful();
                        WorkProgressUpdater.this.mWorkDatabase.endTransaction();
                        return;
                    }
                    throw new IllegalStateException("Calls to setProgressAsync() must complete before a ListenableWorker signals completion of work by returning an instance of Result.");
                } catch (Throwable th) {
                    WorkProgressUpdater.this.mWorkDatabase.endTransaction();
                    throw th;
                }
            }
        });
        return future;
    }
}
