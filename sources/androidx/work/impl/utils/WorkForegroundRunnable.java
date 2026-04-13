package androidx.work.impl.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.core.os.BuildCompat;
import androidx.work.ForegroundInfo;
import androidx.work.ForegroundUpdater;
import androidx.work.ListenableWorker;
import androidx.work.Logger;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WorkForegroundRunnable implements Runnable {
    static final String TAG = Logger.tagWithPrefix("WorkForegroundRunnable");
    final Context mContext;
    final ForegroundUpdater mForegroundUpdater;
    final SettableFuture<Void> mFuture = SettableFuture.create();
    final TaskExecutor mTaskExecutor;
    final WorkSpec mWorkSpec;
    final ListenableWorker mWorker;

    @SuppressLint({"LambdaLast"})
    public WorkForegroundRunnable(@NonNull Context context, @NonNull WorkSpec workSpec, @NonNull ListenableWorker worker, @NonNull ForegroundUpdater foregroundUpdater, @NonNull TaskExecutor taskExecutor) {
        this.mContext = context;
        this.mWorkSpec = workSpec;
        this.mWorker = worker;
        this.mForegroundUpdater = foregroundUpdater;
        this.mTaskExecutor = taskExecutor;
    }

    @NonNull
    public ListenableFuture<Void> getFuture() {
        return this.mFuture;
    }

    @SuppressLint({"UnsafeExperimentalUsageError"})
    public void run() {
        if (!this.mWorkSpec.expedited || BuildCompat.isAtLeastS()) {
            this.mFuture.set(null);
            return;
        }
        final SettableFuture<ForegroundInfo> foregroundFuture = SettableFuture.create();
        this.mTaskExecutor.getMainThreadExecutor().execute(new Runnable() {
            public void run() {
                foregroundFuture.setFuture(WorkForegroundRunnable.this.mWorker.getForegroundInfoAsync());
            }
        });
        foregroundFuture.addListener(new Runnable() {
            public void run() {
                try {
                    ForegroundInfo foregroundInfo = (ForegroundInfo) foregroundFuture.get();
                    if (foregroundInfo != null) {
                        Logger.get().debug(WorkForegroundRunnable.TAG, String.format("Updating notification for %s", new Object[]{WorkForegroundRunnable.this.mWorkSpec.workerClassName}), new Throwable[0]);
                        WorkForegroundRunnable.this.mWorker.setRunInForeground(true);
                        WorkForegroundRunnable workForegroundRunnable = WorkForegroundRunnable.this;
                        workForegroundRunnable.mFuture.setFuture(workForegroundRunnable.mForegroundUpdater.setForegroundAsync(workForegroundRunnable.mContext, workForegroundRunnable.mWorker.getId(), foregroundInfo));
                        return;
                    }
                    throw new IllegalStateException(String.format("Worker was marked important (%s) but did not provide ForegroundInfo", new Object[]{WorkForegroundRunnable.this.mWorkSpec.workerClassName}));
                } catch (Throwable throwable) {
                    WorkForegroundRunnable.this.mFuture.setException(throwable);
                }
            }
        }, this.mTaskExecutor.getMainThreadExecutor());
    }
}
