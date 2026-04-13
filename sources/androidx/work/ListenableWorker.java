package androidx.work;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Network;
import android.net.Uri;
import androidx.annotation.IntRange;
import androidx.annotation.Keep;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;

public abstract class ListenableWorker {
    @NonNull
    private Context mAppContext;
    private boolean mRunInForeground;
    private volatile boolean mStopped;
    private boolean mUsed;
    @NonNull
    private WorkerParameters mWorkerParams;

    @MainThread
    @NonNull
    public abstract ListenableFuture<Result> startWork();

    @SuppressLint({"BanKeepAnnotation"})
    @Keep
    public ListenableWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        if (appContext == null) {
            throw new IllegalArgumentException("Application Context is null");
        } else if (workerParams != null) {
            this.mAppContext = appContext;
            this.mWorkerParams = workerParams;
        } else {
            throw new IllegalArgumentException("WorkerParameters is null");
        }
    }

    @NonNull
    public final Context getApplicationContext() {
        return this.mAppContext;
    }

    @NonNull
    public final UUID getId() {
        return this.mWorkerParams.getId();
    }

    @NonNull
    public final Data getInputData() {
        return this.mWorkerParams.getInputData();
    }

    @NonNull
    public final Set<String> getTags() {
        return this.mWorkerParams.getTags();
    }

    @RequiresApi(24)
    @NonNull
    public final List<Uri> getTriggeredContentUris() {
        return this.mWorkerParams.getTriggeredContentUris();
    }

    @RequiresApi(24)
    @NonNull
    public final List<String> getTriggeredContentAuthorities() {
        return this.mWorkerParams.getTriggeredContentAuthorities();
    }

    @RequiresApi(28)
    @Nullable
    public final Network getNetwork() {
        return this.mWorkerParams.getNetwork();
    }

    @IntRange(from = 0)
    public final int getRunAttemptCount() {
        return this.mWorkerParams.getRunAttemptCount();
    }

    @NonNull
    public ListenableFuture<Void> setProgressAsync(@NonNull Data data) {
        return this.mWorkerParams.getProgressUpdater().updateProgress(getApplicationContext(), getId(), data);
    }

    @NonNull
    public final ListenableFuture<Void> setForegroundAsync(@NonNull ForegroundInfo foregroundInfo) {
        this.mRunInForeground = true;
        return this.mWorkerParams.getForegroundUpdater().setForegroundAsync(getApplicationContext(), getId(), foregroundInfo);
    }

    @NonNull
    public ListenableFuture<ForegroundInfo> getForegroundInfoAsync() {
        SettableFuture<ForegroundInfo> future = SettableFuture.create();
        future.setException(new IllegalStateException("Expedited WorkRequests require a ListenableWorker to provide an implementation for `getForegroundInfoAsync()`"));
        return future;
    }

    public final boolean isStopped() {
        return this.mStopped;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void stop() {
        this.mStopped = true;
        onStopped();
    }

    public void onStopped() {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final boolean isUsed() {
        return this.mUsed;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public final void setUsed() {
        this.mUsed = true;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isRunInForeground() {
        return this.mRunInForeground;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setRunInForeground(boolean runInForeground) {
        this.mRunInForeground = runInForeground;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Executor getBackgroundExecutor() {
        return this.mWorkerParams.getBackgroundExecutor();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public TaskExecutor getTaskExecutor() {
        return this.mWorkerParams.getTaskExecutor();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkerFactory getWorkerFactory() {
        return this.mWorkerParams.getWorkerFactory();
    }

    public static abstract class Result {
        @NonNull
        public abstract Data getOutputData();

        @NonNull
        public static Result success() {
            return new Success();
        }

        @NonNull
        public static Result success(@NonNull Data outputData) {
            return new Success(outputData);
        }

        @NonNull
        public static Result retry() {
            return new Retry();
        }

        @NonNull
        public static Result failure() {
            return new Failure();
        }

        @NonNull
        public static Result failure(@NonNull Data outputData) {
            return new Failure(outputData);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        Result() {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static final class Success extends Result {
            private final Data mOutputData;

            public Success() {
                this(Data.EMPTY);
            }

            public Success(@NonNull Data outputData) {
                this.mOutputData = outputData;
            }

            @NonNull
            public Data getOutputData() {
                return this.mOutputData;
            }

            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                return this.mOutputData.equals(((Success) o).mOutputData);
            }

            public int hashCode() {
                return (Success.class.getName().hashCode() * 31) + this.mOutputData.hashCode();
            }

            public String toString() {
                return "Success {mOutputData=" + this.mOutputData + '}';
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static final class Failure extends Result {
            private final Data mOutputData;

            public Failure() {
                this(Data.EMPTY);
            }

            public Failure(@NonNull Data outputData) {
                this.mOutputData = outputData;
            }

            @NonNull
            public Data getOutputData() {
                return this.mOutputData;
            }

            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                return this.mOutputData.equals(((Failure) o).mOutputData);
            }

            public int hashCode() {
                return (Failure.class.getName().hashCode() * 31) + this.mOutputData.hashCode();
            }

            public String toString() {
                return "Failure {mOutputData=" + this.mOutputData + '}';
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static final class Retry extends Result {
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                return true;
            }

            public int hashCode() {
                return Retry.class.getName().hashCode();
            }

            @NonNull
            public Data getOutputData() {
                return Data.EMPTY;
            }

            public String toString() {
                return "Retry";
            }
        }
    }
}
