package androidx.work.impl.utils;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.work.WorkInfo;
import androidx.work.WorkQuery;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.futures.SettableFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class StatusRunnable<T> implements Runnable {
    private final SettableFuture<T> mFuture = SettableFuture.create();

    /* access modifiers changed from: package-private */
    @WorkerThread
    public abstract T runInternal();

    public void run() {
        try {
            this.mFuture.set(runInternal());
        } catch (Throwable throwable) {
            this.mFuture.setException(throwable);
        }
    }

    @NonNull
    public ListenableFuture<T> getFuture() {
        return this.mFuture;
    }

    @NonNull
    public static StatusRunnable<List<WorkInfo>> forStringIds(@NonNull final WorkManagerImpl workManager, @NonNull final List<String> ids) {
        return new StatusRunnable<List<WorkInfo>>() {
            public List<WorkInfo> runInternal() {
                return WorkSpec.WORK_INFO_MAPPER.apply(WorkManagerImpl.this.getWorkDatabase().workSpecDao().getWorkStatusPojoForIds(ids));
            }
        };
    }

    @NonNull
    public static StatusRunnable<WorkInfo> forUUID(@NonNull final WorkManagerImpl workManager, @NonNull final UUID id) {
        return new StatusRunnable<WorkInfo>() {
            /* access modifiers changed from: package-private */
            public WorkInfo runInternal() {
                WorkSpec.WorkInfoPojo workInfoPojo = WorkManagerImpl.this.getWorkDatabase().workSpecDao().getWorkStatusPojoForId(id.toString());
                if (workInfoPojo != null) {
                    return workInfoPojo.toWorkInfo();
                }
                return null;
            }
        };
    }

    @NonNull
    public static StatusRunnable<List<WorkInfo>> forTag(@NonNull final WorkManagerImpl workManager, @NonNull final String tag) {
        return new StatusRunnable<List<WorkInfo>>() {
            /* access modifiers changed from: package-private */
            public List<WorkInfo> runInternal() {
                return WorkSpec.WORK_INFO_MAPPER.apply(WorkManagerImpl.this.getWorkDatabase().workSpecDao().getWorkStatusPojoForTag(tag));
            }
        };
    }

    @NonNull
    public static StatusRunnable<List<WorkInfo>> forUniqueWork(@NonNull final WorkManagerImpl workManager, @NonNull final String name) {
        return new StatusRunnable<List<WorkInfo>>() {
            /* access modifiers changed from: package-private */
            public List<WorkInfo> runInternal() {
                return WorkSpec.WORK_INFO_MAPPER.apply(WorkManagerImpl.this.getWorkDatabase().workSpecDao().getWorkStatusPojoForName(name));
            }
        };
    }

    @NonNull
    public static StatusRunnable<List<WorkInfo>> forWorkQuerySpec(@NonNull final WorkManagerImpl workManager, @NonNull final WorkQuery querySpec) {
        return new StatusRunnable<List<WorkInfo>>() {
            /* access modifiers changed from: package-private */
            public List<WorkInfo> runInternal() {
                return WorkSpec.WORK_INFO_MAPPER.apply(WorkManagerImpl.this.getWorkDatabase().rawWorkInfoDao().getWorkInfoPojos(RawQueries.workQueryToRawQuery(querySpec)));
            }
        };
    }
}
