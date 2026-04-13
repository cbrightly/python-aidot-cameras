package androidx.work;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.work.WorkInfo;
import androidx.work.impl.model.WorkSpec;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class WorkRequest {
    public static final long DEFAULT_BACKOFF_DELAY_MILLIS = 30000;
    @SuppressLint({"MinMaxConstant"})
    public static final long MAX_BACKOFF_MILLIS = 18000000;
    @SuppressLint({"MinMaxConstant"})
    public static final long MIN_BACKOFF_MILLIS = 10000;
    @NonNull
    private UUID mId;
    @NonNull
    private Set<String> mTags;
    @NonNull
    private WorkSpec mWorkSpec;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected WorkRequest(@NonNull UUID id, @NonNull WorkSpec workSpec, @NonNull Set<String> tags) {
        this.mId = id;
        this.mWorkSpec = workSpec;
        this.mTags = tags;
    }

    @NonNull
    public UUID getId() {
        return this.mId;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public String getStringId() {
        return this.mId.toString();
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public WorkSpec getWorkSpec() {
        return this.mWorkSpec;
    }

    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Set<String> getTags() {
        return this.mTags;
    }

    public static abstract class Builder<B extends Builder<?, ?>, W extends WorkRequest> {
        boolean mBackoffCriteriaSet = false;
        UUID mId = UUID.randomUUID();
        Set<String> mTags = new HashSet();
        WorkSpec mWorkSpec;
        Class<? extends ListenableWorker> mWorkerClass;

        /* access modifiers changed from: package-private */
        @NonNull
        public abstract W buildInternal();

        /* access modifiers changed from: package-private */
        @NonNull
        public abstract B getThis();

        Builder(@NonNull Class<? extends ListenableWorker> workerClass) {
            this.mWorkerClass = workerClass;
            this.mWorkSpec = new WorkSpec(this.mId.toString(), workerClass.getName());
            addTag(workerClass.getName());
        }

        @NonNull
        public final B setBackoffCriteria(@NonNull BackoffPolicy backoffPolicy, long backoffDelay, @NonNull TimeUnit timeUnit) {
            this.mBackoffCriteriaSet = true;
            WorkSpec workSpec = this.mWorkSpec;
            workSpec.backoffPolicy = backoffPolicy;
            workSpec.setBackoffDelayDuration(timeUnit.toMillis(backoffDelay));
            return getThis();
        }

        @RequiresApi(26)
        @NonNull
        public final B setBackoffCriteria(@NonNull BackoffPolicy backoffPolicy, @NonNull Duration duration) {
            this.mBackoffCriteriaSet = true;
            WorkSpec workSpec = this.mWorkSpec;
            workSpec.backoffPolicy = backoffPolicy;
            workSpec.setBackoffDelayDuration(duration.toMillis());
            return getThis();
        }

        @NonNull
        public final B setConstraints(@NonNull Constraints constraints) {
            this.mWorkSpec.constraints = constraints;
            return getThis();
        }

        @NonNull
        public final B setInputData(@NonNull Data inputData) {
            this.mWorkSpec.input = inputData;
            return getThis();
        }

        @NonNull
        public final B addTag(@NonNull String tag) {
            this.mTags.add(tag);
            return getThis();
        }

        @NonNull
        public final B keepResultsForAtLeast(long duration, @NonNull TimeUnit timeUnit) {
            this.mWorkSpec.minimumRetentionDuration = timeUnit.toMillis(duration);
            return getThis();
        }

        @RequiresApi(26)
        @NonNull
        public final B keepResultsForAtLeast(@NonNull Duration duration) {
            this.mWorkSpec.minimumRetentionDuration = duration.toMillis();
            return getThis();
        }

        @NonNull
        public B setInitialDelay(long duration, @NonNull TimeUnit timeUnit) {
            this.mWorkSpec.initialDelay = timeUnit.toMillis(duration);
            if (DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE - System.currentTimeMillis() > this.mWorkSpec.initialDelay) {
                return getThis();
            }
            throw new IllegalArgumentException("The given initial delay is too large and will cause an overflow!");
        }

        @RequiresApi(26)
        @NonNull
        public B setInitialDelay(@NonNull Duration duration) {
            this.mWorkSpec.initialDelay = duration.toMillis();
            if (DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE - System.currentTimeMillis() > this.mWorkSpec.initialDelay) {
                return getThis();
            }
            throw new IllegalArgumentException("The given initial delay is too large and will cause an overflow!");
        }

        @SuppressLint({"MissingGetterMatchingBuilder"})
        @NonNull
        public B setExpedited(@NonNull OutOfQuotaPolicy policy) {
            WorkSpec workSpec = this.mWorkSpec;
            workSpec.expedited = true;
            workSpec.outOfQuotaPolicy = policy;
            return getThis();
        }

        @NonNull
        public final W build() {
            W returnValue = buildInternal();
            Constraints constraints = this.mWorkSpec.constraints;
            int i = Build.VERSION.SDK_INT;
            boolean hasUnsupportedConstraints = (i >= 24 && constraints.hasContentUriTriggers()) || constraints.requiresBatteryNotLow() || constraints.requiresCharging() || (i >= 23 && constraints.requiresDeviceIdle());
            WorkSpec workSpec = this.mWorkSpec;
            if (workSpec.expedited) {
                if (hasUnsupportedConstraints) {
                    throw new IllegalArgumentException("Expedited jobs only support network and storage constraints");
                } else if (workSpec.initialDelay > 0) {
                    throw new IllegalArgumentException("Expedited jobs cannot be delayed");
                }
            }
            this.mId = UUID.randomUUID();
            WorkSpec workSpec2 = new WorkSpec(this.mWorkSpec);
            this.mWorkSpec = workSpec2;
            workSpec2.id = this.mId.toString();
            return returnValue;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @VisibleForTesting
        @NonNull
        public final B setInitialState(@NonNull WorkInfo.State state) {
            this.mWorkSpec.state = state;
            return getThis();
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @VisibleForTesting
        @NonNull
        public final B setInitialRunAttemptCount(int runAttemptCount) {
            this.mWorkSpec.runAttemptCount = runAttemptCount;
            return getThis();
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @VisibleForTesting
        @NonNull
        public final B setPeriodStartTime(long periodStartTime, @NonNull TimeUnit timeUnit) {
            this.mWorkSpec.periodStartTime = timeUnit.toMillis(periodStartTime);
            return getThis();
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        @VisibleForTesting
        @NonNull
        public final B setScheduleRequestedAt(long scheduleRequestedAt, @NonNull TimeUnit timeUnit) {
            this.mWorkSpec.scheduleRequestedAt = timeUnit.toMillis(scheduleRequestedAt);
            return getThis();
        }
    }
}
