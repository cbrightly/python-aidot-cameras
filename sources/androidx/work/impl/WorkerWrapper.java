package androidx.work.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.work.Configuration;
import androidx.work.Data;
import androidx.work.InputMerger;
import androidx.work.ListenableWorker;
import androidx.work.Logger;
import androidx.work.WorkInfo;
import androidx.work.WorkerParameters;
import androidx.work.impl.background.systemalarm.RescheduleReceiver;
import androidx.work.impl.foreground.ForegroundProcessor;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.utils.PackageManagerHelper;
import androidx.work.impl.utils.WorkForegroundRunnable;
import androidx.work.impl.utils.WorkForegroundUpdater;
import androidx.work.impl.utils.WorkProgressUpdater;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WorkerWrapper implements Runnable {
    static final String TAG = Logger.tagWithPrefix("WorkerWrapper");
    Context mAppContext;
    private Configuration mConfiguration;
    private DependencyDao mDependencyDao;
    private ForegroundProcessor mForegroundProcessor;
    @NonNull
    SettableFuture<Boolean> mFuture = SettableFuture.create();
    @Nullable
    ListenableFuture<ListenableWorker.Result> mInnerFuture = null;
    private volatile boolean mInterrupted;
    @NonNull
    ListenableWorker.Result mResult = ListenableWorker.Result.failure();
    private WorkerParameters.RuntimeExtras mRuntimeExtras;
    private List<Scheduler> mSchedulers;
    private List<String> mTags;
    private WorkDatabase mWorkDatabase;
    private String mWorkDescription;
    WorkSpec mWorkSpec;
    private WorkSpecDao mWorkSpecDao;
    private String mWorkSpecId;
    private WorkTagDao mWorkTagDao;
    TaskExecutor mWorkTaskExecutor;
    ListenableWorker mWorker;

    WorkerWrapper(@NonNull Builder builder) {
        this.mAppContext = builder.mAppContext;
        this.mWorkTaskExecutor = builder.mWorkTaskExecutor;
        this.mForegroundProcessor = builder.mForegroundProcessor;
        this.mWorkSpecId = builder.mWorkSpecId;
        this.mSchedulers = builder.mSchedulers;
        this.mRuntimeExtras = builder.mRuntimeExtras;
        this.mWorker = builder.mWorker;
        this.mConfiguration = builder.mConfiguration;
        WorkDatabase workDatabase = builder.mWorkDatabase;
        this.mWorkDatabase = workDatabase;
        this.mWorkSpecDao = workDatabase.workSpecDao();
        this.mDependencyDao = this.mWorkDatabase.dependencyDao();
        this.mWorkTagDao = this.mWorkDatabase.workTagDao();
    }

    @NonNull
    public ListenableFuture<Boolean> getFuture() {
        return this.mFuture;
    }

    @WorkerThread
    public void run() {
        List<String> tagsForWorkSpecId = this.mWorkTagDao.getTagsForWorkSpecId(this.mWorkSpecId);
        this.mTags = tagsForWorkSpecId;
        this.mWorkDescription = createWorkDescription(tagsForWorkSpecId);
        runWorker();
    }

    private void runWorker() {
        Data input;
        if (!tryCheckForInterruptionAndResolve()) {
            this.mWorkDatabase.beginTransaction();
            try {
                WorkSpec workSpec = this.mWorkSpecDao.getWorkSpec(this.mWorkSpecId);
                this.mWorkSpec = workSpec;
                if (workSpec == null) {
                    Logger.get().error(TAG, String.format("Didn't find WorkSpec for id %s", new Object[]{this.mWorkSpecId}), new Throwable[0]);
                    resolve(false);
                    this.mWorkDatabase.setTransactionSuccessful();
                } else if (workSpec.state != WorkInfo.State.ENQUEUED) {
                    resolveIncorrectStatus();
                    this.mWorkDatabase.setTransactionSuccessful();
                    Logger.get().debug(TAG, String.format("%s is not in ENQUEUED state. Nothing more to do.", new Object[]{this.mWorkSpec.workerClassName}), new Throwable[0]);
                    this.mWorkDatabase.endTransaction();
                } else {
                    if (workSpec.isPeriodic() || this.mWorkSpec.isBackedOff()) {
                        long now = System.currentTimeMillis();
                        WorkSpec workSpec2 = this.mWorkSpec;
                        if (!(workSpec2.periodStartTime == 0) && now < workSpec2.calculateNextRunTime()) {
                            Logger.get().debug(TAG, String.format("Delaying execution for %s because it is being executed before schedule.", new Object[]{this.mWorkSpec.workerClassName}), new Throwable[0]);
                            resolve(true);
                            this.mWorkDatabase.setTransactionSuccessful();
                            this.mWorkDatabase.endTransaction();
                            return;
                        }
                    }
                    this.mWorkDatabase.setTransactionSuccessful();
                    this.mWorkDatabase.endTransaction();
                    if (this.mWorkSpec.isPeriodic()) {
                        input = this.mWorkSpec.input;
                    } else {
                        InputMerger inputMerger = this.mConfiguration.getInputMergerFactory().createInputMergerWithDefaultFallback(this.mWorkSpec.inputMergerClassName);
                        if (inputMerger == null) {
                            Logger.get().error(TAG, String.format("Could not create Input Merger %s", new Object[]{this.mWorkSpec.inputMergerClassName}), new Throwable[0]);
                            setFailedAndResolve();
                            return;
                        }
                        List<Data> inputs = new ArrayList<>();
                        inputs.add(this.mWorkSpec.input);
                        inputs.addAll(this.mWorkSpecDao.getInputsFromPrerequisites(this.mWorkSpecId));
                        input = inputMerger.merge(inputs);
                    }
                    WorkerParameters workerParameters = new WorkerParameters(UUID.fromString(this.mWorkSpecId), input, this.mTags, this.mRuntimeExtras, this.mWorkSpec.runAttemptCount, this.mConfiguration.getExecutor(), this.mWorkTaskExecutor, this.mConfiguration.getWorkerFactory(), new WorkProgressUpdater(this.mWorkDatabase, this.mWorkTaskExecutor), new WorkForegroundUpdater(this.mWorkDatabase, this.mForegroundProcessor, this.mWorkTaskExecutor));
                    if (this.mWorker == null) {
                        this.mWorker = this.mConfiguration.getWorkerFactory().createWorkerWithDefaultFallback(this.mAppContext, this.mWorkSpec.workerClassName, workerParameters);
                    }
                    ListenableWorker listenableWorker = this.mWorker;
                    if (listenableWorker == null) {
                        Logger.get().error(TAG, String.format("Could not create Worker %s", new Object[]{this.mWorkSpec.workerClassName}), new Throwable[0]);
                        setFailedAndResolve();
                    } else if (listenableWorker.isUsed()) {
                        Logger.get().error(TAG, String.format("Received an already-used Worker %s; WorkerFactory should return new instances", new Object[]{this.mWorkSpec.workerClassName}), new Throwable[0]);
                        setFailedAndResolve();
                    } else {
                        this.mWorker.setUsed();
                        if (!trySetRunning()) {
                            resolveIncorrectStatus();
                        } else if (!tryCheckForInterruptionAndResolve()) {
                            final SettableFuture<ListenableWorker.Result> future = SettableFuture.create();
                            WorkForegroundRunnable workForegroundRunnable = new WorkForegroundRunnable(this.mAppContext, this.mWorkSpec, this.mWorker, workerParameters.getForegroundUpdater(), this.mWorkTaskExecutor);
                            this.mWorkTaskExecutor.getMainThreadExecutor().execute(workForegroundRunnable);
                            final ListenableFuture<Void> runExpedited = workForegroundRunnable.getFuture();
                            runExpedited.addListener(new Runnable() {
                                public void run() {
                                    try {
                                        runExpedited.get();
                                        Logger.get().debug(WorkerWrapper.TAG, String.format("Starting work for %s", new Object[]{WorkerWrapper.this.mWorkSpec.workerClassName}), new Throwable[0]);
                                        WorkerWrapper workerWrapper = WorkerWrapper.this;
                                        workerWrapper.mInnerFuture = workerWrapper.mWorker.startWork();
                                        future.setFuture(WorkerWrapper.this.mInnerFuture);
                                    } catch (Throwable e) {
                                        future.setException(e);
                                    }
                                }
                            }, this.mWorkTaskExecutor.getMainThreadExecutor());
                            final String workDescription = this.mWorkDescription;
                            future.addListener(new Runnable() {
                                @SuppressLint({"SyntheticAccessor"})
                                public void run() {
                                    try {
                                        ListenableWorker.Result result = (ListenableWorker.Result) future.get();
                                        if (result == null) {
                                            Logger.get().error(WorkerWrapper.TAG, String.format("%s returned a null result. Treating it as a failure.", new Object[]{WorkerWrapper.this.mWorkSpec.workerClassName}), new Throwable[0]);
                                        } else {
                                            Logger.get().debug(WorkerWrapper.TAG, String.format("%s returned a %s result.", new Object[]{WorkerWrapper.this.mWorkSpec.workerClassName, result}), new Throwable[0]);
                                            WorkerWrapper.this.mResult = result;
                                        }
                                    } catch (CancellationException exception) {
                                        Logger.get().info(WorkerWrapper.TAG, String.format("%s was cancelled", new Object[]{workDescription}), exception);
                                    } catch (InterruptedException | ExecutionException exception2) {
                                        Logger.get().error(WorkerWrapper.TAG, String.format("%s failed because it threw an exception/error", new Object[]{workDescription}), exception2);
                                    } catch (Throwable th) {
                                        WorkerWrapper.this.onWorkFinished();
                                        throw th;
                                    }
                                    WorkerWrapper.this.onWorkFinished();
                                }
                            }, this.mWorkTaskExecutor.getBackgroundExecutor());
                        }
                    }
                }
            } finally {
                this.mWorkDatabase.endTransaction();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onWorkFinished() {
        if (!tryCheckForInterruptionAndResolve()) {
            this.mWorkDatabase.beginTransaction();
            try {
                WorkInfo.State state = this.mWorkSpecDao.getState(this.mWorkSpecId);
                this.mWorkDatabase.workProgressDao().delete(this.mWorkSpecId);
                if (state == null) {
                    resolve(false);
                } else if (state == WorkInfo.State.RUNNING) {
                    handleResult(this.mResult);
                } else if (!state.isFinished()) {
                    rescheduleAndResolve();
                }
                this.mWorkDatabase.setTransactionSuccessful();
            } finally {
                this.mWorkDatabase.endTransaction();
            }
        }
        List<Scheduler> list = this.mSchedulers;
        if (list != null) {
            for (Scheduler scheduler : list) {
                scheduler.cancel(this.mWorkSpecId);
            }
            Schedulers.schedule(this.mConfiguration, this.mWorkDatabase, this.mSchedulers);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void interrupt() {
        this.mInterrupted = true;
        tryCheckForInterruptionAndResolve();
        boolean isDone = false;
        ListenableFuture<ListenableWorker.Result> listenableFuture = this.mInnerFuture;
        if (listenableFuture != null) {
            isDone = listenableFuture.isDone();
            this.mInnerFuture.cancel(true);
        }
        ListenableWorker listenableWorker = this.mWorker;
        if (listenableWorker == null || isDone) {
            Logger.get().debug(TAG, String.format("WorkSpec %s is already done. Not interrupting.", new Object[]{this.mWorkSpec}), new Throwable[0]);
            return;
        }
        listenableWorker.stop();
    }

    private void resolveIncorrectStatus() {
        WorkInfo.State status = this.mWorkSpecDao.getState(this.mWorkSpecId);
        if (status == WorkInfo.State.RUNNING) {
            Logger.get().debug(TAG, String.format("Status for %s is RUNNING;not doing any work and rescheduling for later execution", new Object[]{this.mWorkSpecId}), new Throwable[0]);
            resolve(true);
            return;
        }
        Logger.get().debug(TAG, String.format("Status for %s is %s; not doing any work", new Object[]{this.mWorkSpecId, status}), new Throwable[0]);
        resolve(false);
    }

    private boolean tryCheckForInterruptionAndResolve() {
        if (!this.mInterrupted) {
            return false;
        }
        Logger.get().debug(TAG, String.format("Work interrupted for %s", new Object[]{this.mWorkDescription}), new Throwable[0]);
        WorkInfo.State currentState = this.mWorkSpecDao.getState(this.mWorkSpecId);
        if (currentState == null) {
            resolve(false);
        } else {
            resolve(!currentState.isFinished());
        }
        return true;
    }

    /* JADX INFO: finally extract failed */
    private void resolve(boolean needsReschedule) {
        ListenableWorker listenableWorker;
        this.mWorkDatabase.beginTransaction();
        try {
            if (!this.mWorkDatabase.workSpecDao().hasUnfinishedWork()) {
                PackageManagerHelper.setComponentEnabled(this.mAppContext, RescheduleReceiver.class, false);
            }
            if (needsReschedule) {
                this.mWorkSpecDao.setState(WorkInfo.State.ENQUEUED, this.mWorkSpecId);
                this.mWorkSpecDao.markWorkSpecScheduled(this.mWorkSpecId, -1);
            }
            if (!(this.mWorkSpec == null || (listenableWorker = this.mWorker) == null || !listenableWorker.isRunInForeground())) {
                this.mForegroundProcessor.stopForeground(this.mWorkSpecId);
            }
            this.mWorkDatabase.setTransactionSuccessful();
            this.mWorkDatabase.endTransaction();
            this.mFuture.set(Boolean.valueOf(needsReschedule));
        } catch (Throwable th) {
            this.mWorkDatabase.endTransaction();
            throw th;
        }
    }

    private void handleResult(ListenableWorker.Result result) {
        if (result instanceof ListenableWorker.Result.Success) {
            Logger.get().info(TAG, String.format("Worker result SUCCESS for %s", new Object[]{this.mWorkDescription}), new Throwable[0]);
            if (this.mWorkSpec.isPeriodic()) {
                resetPeriodicAndResolve();
            } else {
                setSucceededAndResolve();
            }
        } else if (result instanceof ListenableWorker.Result.Retry) {
            Logger.get().info(TAG, String.format("Worker result RETRY for %s", new Object[]{this.mWorkDescription}), new Throwable[0]);
            rescheduleAndResolve();
        } else {
            Logger.get().info(TAG, String.format("Worker result FAILURE for %s", new Object[]{this.mWorkDescription}), new Throwable[0]);
            if (this.mWorkSpec.isPeriodic()) {
                resetPeriodicAndResolve();
            } else {
                setFailedAndResolve();
            }
        }
    }

    private boolean trySetRunning() {
        boolean setToRunning = false;
        this.mWorkDatabase.beginTransaction();
        try {
            if (this.mWorkSpecDao.getState(this.mWorkSpecId) == WorkInfo.State.ENQUEUED) {
                this.mWorkSpecDao.setState(WorkInfo.State.RUNNING, this.mWorkSpecId);
                this.mWorkSpecDao.incrementWorkSpecRunAttemptCount(this.mWorkSpecId);
                setToRunning = true;
            }
            this.mWorkDatabase.setTransactionSuccessful();
            return setToRunning;
        } finally {
            this.mWorkDatabase.endTransaction();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setFailedAndResolve() {
        this.mWorkDatabase.beginTransaction();
        try {
            iterativelyFailWorkAndDependents(this.mWorkSpecId);
            this.mWorkSpecDao.setOutput(this.mWorkSpecId, ((ListenableWorker.Result.Failure) this.mResult).getOutputData());
            this.mWorkDatabase.setTransactionSuccessful();
        } finally {
            this.mWorkDatabase.endTransaction();
            resolve(false);
        }
    }

    private void iterativelyFailWorkAndDependents(String workSpecId) {
        LinkedList<String> idsToProcess = new LinkedList<>();
        idsToProcess.add(workSpecId);
        while (!idsToProcess.isEmpty()) {
            String id = idsToProcess.remove();
            if (this.mWorkSpecDao.getState(id) != WorkInfo.State.CANCELLED) {
                this.mWorkSpecDao.setState(WorkInfo.State.FAILED, id);
            }
            idsToProcess.addAll(this.mDependencyDao.getDependentWorkIds(id));
        }
    }

    private void rescheduleAndResolve() {
        this.mWorkDatabase.beginTransaction();
        try {
            this.mWorkSpecDao.setState(WorkInfo.State.ENQUEUED, this.mWorkSpecId);
            this.mWorkSpecDao.setPeriodStartTime(this.mWorkSpecId, System.currentTimeMillis());
            this.mWorkSpecDao.markWorkSpecScheduled(this.mWorkSpecId, -1);
            this.mWorkDatabase.setTransactionSuccessful();
        } finally {
            this.mWorkDatabase.endTransaction();
            resolve(true);
        }
    }

    private void resetPeriodicAndResolve() {
        this.mWorkDatabase.beginTransaction();
        try {
            this.mWorkSpecDao.setPeriodStartTime(this.mWorkSpecId, System.currentTimeMillis());
            this.mWorkSpecDao.setState(WorkInfo.State.ENQUEUED, this.mWorkSpecId);
            this.mWorkSpecDao.resetWorkSpecRunAttemptCount(this.mWorkSpecId);
            this.mWorkSpecDao.markWorkSpecScheduled(this.mWorkSpecId, -1);
            this.mWorkDatabase.setTransactionSuccessful();
        } finally {
            this.mWorkDatabase.endTransaction();
            resolve(false);
        }
    }

    private void setSucceededAndResolve() {
        this.mWorkDatabase.beginTransaction();
        try {
            this.mWorkSpecDao.setState(WorkInfo.State.SUCCEEDED, this.mWorkSpecId);
            this.mWorkSpecDao.setOutput(this.mWorkSpecId, ((ListenableWorker.Result.Success) this.mResult).getOutputData());
            long currentTimeMillis = System.currentTimeMillis();
            for (String dependentWorkId : this.mDependencyDao.getDependentWorkIds(this.mWorkSpecId)) {
                if (this.mWorkSpecDao.getState(dependentWorkId) == WorkInfo.State.BLOCKED && this.mDependencyDao.hasCompletedAllPrerequisites(dependentWorkId)) {
                    Logger.get().info(TAG, String.format("Setting status to enqueued for %s", new Object[]{dependentWorkId}), new Throwable[0]);
                    this.mWorkSpecDao.setState(WorkInfo.State.ENQUEUED, dependentWorkId);
                    this.mWorkSpecDao.setPeriodStartTime(dependentWorkId, currentTimeMillis);
                }
            }
            this.mWorkDatabase.setTransactionSuccessful();
        } finally {
            this.mWorkDatabase.endTransaction();
            resolve(false);
        }
    }

    private String createWorkDescription(List<String> tags) {
        StringBuilder sb = new StringBuilder("Work [ id=");
        sb.append(this.mWorkSpecId);
        StringBuilder sb2 = sb.append(", tags={ ");
        boolean first = true;
        for (String tag : tags) {
            if (first) {
                first = false;
            } else {
                sb2.append(", ");
            }
            sb2.append(tag);
        }
        sb2.append(" } ]");
        return sb2.toString();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class Builder {
        @NonNull
        Context mAppContext;
        @NonNull
        Configuration mConfiguration;
        @NonNull
        ForegroundProcessor mForegroundProcessor;
        @NonNull
        WorkerParameters.RuntimeExtras mRuntimeExtras = new WorkerParameters.RuntimeExtras();
        List<Scheduler> mSchedulers;
        @NonNull
        WorkDatabase mWorkDatabase;
        @NonNull
        String mWorkSpecId;
        @NonNull
        TaskExecutor mWorkTaskExecutor;
        @Nullable
        ListenableWorker mWorker;

        public Builder(@NonNull Context context, @NonNull Configuration configuration, @NonNull TaskExecutor workTaskExecutor, @NonNull ForegroundProcessor foregroundProcessor, @NonNull WorkDatabase database, @NonNull String workSpecId) {
            this.mAppContext = context.getApplicationContext();
            this.mWorkTaskExecutor = workTaskExecutor;
            this.mForegroundProcessor = foregroundProcessor;
            this.mConfiguration = configuration;
            this.mWorkDatabase = database;
            this.mWorkSpecId = workSpecId;
        }

        @NonNull
        public Builder withSchedulers(@NonNull List<Scheduler> schedulers) {
            this.mSchedulers = schedulers;
            return this;
        }

        @NonNull
        public Builder withRuntimeExtras(@Nullable WorkerParameters.RuntimeExtras runtimeExtras) {
            if (runtimeExtras != null) {
                this.mRuntimeExtras = runtimeExtras;
            }
            return this;
        }

        @VisibleForTesting
        @NonNull
        public Builder withWorker(@NonNull ListenableWorker worker) {
            this.mWorker = worker;
            return this;
        }

        @NonNull
        public WorkerWrapper build() {
            return new WorkerWrapper(this);
        }
    }
}
