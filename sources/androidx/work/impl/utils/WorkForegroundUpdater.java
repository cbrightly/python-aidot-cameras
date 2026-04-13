package androidx.work.impl.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.ForegroundInfo;
import androidx.work.ForegroundUpdater;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.foreground.SystemForegroundDispatcher;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WorkForegroundUpdater implements ForegroundUpdater {
    private static final String TAG = Logger.tagWithPrefix("WMFgUpdater");
    final ForegroundProcessor mForegroundProcessor;
    private final TaskExecutor mTaskExecutor;
    final WorkSpecDao mWorkSpecDao;

    public WorkForegroundUpdater(@NonNull WorkDatabase workDatabase, @NonNull ForegroundProcessor foregroundProcessor, @NonNull TaskExecutor taskExecutor) {
        this.mForegroundProcessor = foregroundProcessor;
        this.mTaskExecutor = taskExecutor;
        this.mWorkSpecDao = workDatabase.workSpecDao();
    }

    @NonNull
    public ListenableFuture<Void> setForegroundAsync(@NonNull Context context, @NonNull UUID id, @NonNull ForegroundInfo foregroundInfo) {
        SettableFuture<Void> future = SettableFuture.create();
        final SettableFuture<Void> settableFuture = future;
        final UUID uuid = id;
        final ForegroundInfo foregroundInfo2 = foregroundInfo;
        final Context context2 = context;
        this.mTaskExecutor.executeOnBackgroundThread(new Runnable() {
            public void run() {
                try {
                    if (!settableFuture.isCancelled()) {
                        String workSpecId = uuid.toString();
                        WorkInfo.State state = WorkForegroundUpdater.this.mWorkSpecDao.getState(workSpecId);
                        if (state == null || state.isFinished()) {
                            throw new IllegalStateException("Calls to setForegroundAsync() must complete before a ListenableWorker signals completion of work by returning an instance of Result.");
                        }
                        WorkForegroundUpdater.this.mForegroundProcessor.startForeground(workSpecId, foregroundInfo2);
                        context2.startService(SystemForegroundDispatcher.createNotifyIntent(context2, workSpecId, foregroundInfo2));
                    }
                    settableFuture.set(null);
                } catch (Throwable throwable) {
                    settableFuture.setException(throwable);
                }
            }
        });
        return future;
    }
}
