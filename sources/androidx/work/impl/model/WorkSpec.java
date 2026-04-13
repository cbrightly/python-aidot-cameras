package androidx.work.impl.model;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.arch.core.util.Function;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.Logger;
import androidx.work.OutOfQuotaPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@Entity(indices = {@Index({"schedule_requested_at"}), @Index({"period_start_time"})})
public final class WorkSpec {
    public static final long SCHEDULE_NOT_REQUESTED_YET = -1;
    private static final String TAG = Logger.tagWithPrefix("WorkSpec");
    public static final Function<List<WorkInfoPojo>, List<WorkInfo>> WORK_INFO_MAPPER = new Function<List<WorkInfoPojo>, List<WorkInfo>>() {
        public List<WorkInfo> apply(List<WorkInfoPojo> input) {
            if (input == null) {
                return null;
            }
            List<WorkInfo> output = new ArrayList<>(input.size());
            for (WorkInfoPojo in : input) {
                output.add(in.toWorkInfo());
            }
            return output;
        }
    };
    @ColumnInfo(name = "backoff_delay_duration")
    public long backoffDelayDuration;
    @ColumnInfo(name = "backoff_policy")
    @NonNull
    public BackoffPolicy backoffPolicy;
    @NonNull
    @Embedded
    public Constraints constraints;
    @ColumnInfo(name = "run_in_foreground")
    public boolean expedited;
    @ColumnInfo(name = "flex_duration")
    public long flexDuration;
    @ColumnInfo(name = "id")
    @NonNull
    @PrimaryKey
    public String id;
    @ColumnInfo(name = "initial_delay")
    public long initialDelay;
    @ColumnInfo(name = "input")
    @NonNull
    public Data input;
    @ColumnInfo(name = "input_merger_class_name")
    public String inputMergerClassName;
    @ColumnInfo(name = "interval_duration")
    public long intervalDuration;
    @ColumnInfo(name = "minimum_retention_duration")
    public long minimumRetentionDuration;
    @ColumnInfo(name = "out_of_quota_policy")
    @NonNull
    public OutOfQuotaPolicy outOfQuotaPolicy;
    @ColumnInfo(name = "output")
    @NonNull
    public Data output;
    @ColumnInfo(name = "period_start_time")
    public long periodStartTime;
    @ColumnInfo(name = "run_attempt_count")
    @IntRange(from = 0)
    public int runAttemptCount;
    @ColumnInfo(name = "schedule_requested_at")
    public long scheduleRequestedAt;
    @ColumnInfo(name = "state")
    @NonNull
    public WorkInfo.State state = WorkInfo.State.ENQUEUED;
    @ColumnInfo(name = "worker_class_name")
    @NonNull
    public String workerClassName;

    public WorkSpec(@NonNull String id2, @NonNull String workerClassName2) {
        Data data = Data.EMPTY;
        this.input = data;
        this.output = data;
        this.constraints = Constraints.NONE;
        this.backoffPolicy = BackoffPolicy.EXPONENTIAL;
        this.backoffDelayDuration = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
        this.scheduleRequestedAt = -1;
        this.outOfQuotaPolicy = OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST;
        this.id = id2;
        this.workerClassName = workerClassName2;
    }

    public WorkSpec(@NonNull WorkSpec other) {
        Data data = Data.EMPTY;
        this.input = data;
        this.output = data;
        this.constraints = Constraints.NONE;
        this.backoffPolicy = BackoffPolicy.EXPONENTIAL;
        this.backoffDelayDuration = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
        this.scheduleRequestedAt = -1;
        this.outOfQuotaPolicy = OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST;
        this.id = other.id;
        this.workerClassName = other.workerClassName;
        this.state = other.state;
        this.inputMergerClassName = other.inputMergerClassName;
        this.input = new Data(other.input);
        this.output = new Data(other.output);
        this.initialDelay = other.initialDelay;
        this.intervalDuration = other.intervalDuration;
        this.flexDuration = other.flexDuration;
        this.constraints = new Constraints(other.constraints);
        this.runAttemptCount = other.runAttemptCount;
        this.backoffPolicy = other.backoffPolicy;
        this.backoffDelayDuration = other.backoffDelayDuration;
        this.periodStartTime = other.periodStartTime;
        this.minimumRetentionDuration = other.minimumRetentionDuration;
        this.scheduleRequestedAt = other.scheduleRequestedAt;
        this.expedited = other.expedited;
        this.outOfQuotaPolicy = other.outOfQuotaPolicy;
    }

    public void setBackoffDelayDuration(long backoffDelayDuration2) {
        if (backoffDelayDuration2 > WorkRequest.MAX_BACKOFF_MILLIS) {
            Logger.get().warning(TAG, "Backoff delay duration exceeds maximum value", new Throwable[0]);
            backoffDelayDuration2 = WorkRequest.MAX_BACKOFF_MILLIS;
        }
        if (backoffDelayDuration2 < 10000) {
            Logger.get().warning(TAG, "Backoff delay duration less than minimum value", new Throwable[0]);
            backoffDelayDuration2 = 10000;
        }
        this.backoffDelayDuration = backoffDelayDuration2;
    }

    public boolean isPeriodic() {
        return this.intervalDuration != 0;
    }

    public boolean isBackedOff() {
        return this.state == WorkInfo.State.ENQUEUED && this.runAttemptCount > 0;
    }

    public void setPeriodic(long intervalDuration2) {
        if (intervalDuration2 < PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS) {
            Logger.get().warning(TAG, String.format("Interval duration lesser than minimum allowed value; Changed to %s", new Object[]{Long.valueOf(PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS)}), new Throwable[0]);
            intervalDuration2 = PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS;
        }
        setPeriodic(intervalDuration2, intervalDuration2);
    }

    public void setPeriodic(long intervalDuration2, long flexDuration2) {
        if (intervalDuration2 < PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS) {
            Logger.get().warning(TAG, String.format("Interval duration lesser than minimum allowed value; Changed to %s", new Object[]{Long.valueOf(PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS)}), new Throwable[0]);
            intervalDuration2 = PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS;
        }
        if (flexDuration2 < PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS) {
            Logger.get().warning(TAG, String.format("Flex duration lesser than minimum allowed value; Changed to %s", new Object[]{Long.valueOf(PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS)}), new Throwable[0]);
            flexDuration2 = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS;
        }
        if (flexDuration2 > intervalDuration2) {
            Logger.get().warning(TAG, String.format("Flex duration greater than interval duration; Changed to %s", new Object[]{Long.valueOf(intervalDuration2)}), new Throwable[0]);
            flexDuration2 = intervalDuration2;
        }
        this.intervalDuration = intervalDuration2;
        this.flexDuration = flexDuration2;
    }

    public long calculateNextRunTime() {
        long delay;
        boolean isLinearBackoff = false;
        if (isBackedOff()) {
            if (this.backoffPolicy == BackoffPolicy.LINEAR) {
                isLinearBackoff = true;
            }
            if (isLinearBackoff) {
                delay = this.backoffDelayDuration * ((long) this.runAttemptCount);
            } else {
                delay = (long) Math.scalb((float) this.backoffDelayDuration, this.runAttemptCount - 1);
            }
            return this.periodStartTime + Math.min(WorkRequest.MAX_BACKOFF_MILLIS, delay);
        }
        long offset = 0;
        if (isPeriodic()) {
            long now = System.currentTimeMillis();
            long j = this.periodStartTime;
            long start = j == 0 ? this.initialDelay + now : j;
            long j2 = this.flexDuration;
            long j3 = this.intervalDuration;
            if (j2 != j3) {
                isLinearBackoff = true;
            }
            if (isLinearBackoff) {
                if (j == 0) {
                    offset = j2 * -1;
                }
                return j3 + start + offset;
            }
            if (j != 0) {
                offset = j3;
            }
            return start + offset;
        }
        long start2 = this.periodStartTime;
        if (start2 == 0) {
            start2 = System.currentTimeMillis();
        }
        return this.initialDelay + start2;
    }

    public boolean hasConstraints() {
        return !Constraints.NONE.equals(this.constraints);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkSpec workSpec = (WorkSpec) o;
        if (this.initialDelay != workSpec.initialDelay || this.intervalDuration != workSpec.intervalDuration || this.flexDuration != workSpec.flexDuration || this.runAttemptCount != workSpec.runAttemptCount || this.backoffDelayDuration != workSpec.backoffDelayDuration || this.periodStartTime != workSpec.periodStartTime || this.minimumRetentionDuration != workSpec.minimumRetentionDuration || this.scheduleRequestedAt != workSpec.scheduleRequestedAt || this.expedited != workSpec.expedited || !this.id.equals(workSpec.id) || this.state != workSpec.state || !this.workerClassName.equals(workSpec.workerClassName)) {
            return false;
        }
        String str = this.inputMergerClassName;
        if (str == null ? workSpec.inputMergerClassName != null : !str.equals(workSpec.inputMergerClassName)) {
            return false;
        }
        if (!this.input.equals(workSpec.input) || !this.output.equals(workSpec.output) || !this.constraints.equals(workSpec.constraints) || this.backoffPolicy != workSpec.backoffPolicy) {
            return false;
        }
        if (this.outOfQuotaPolicy == workSpec.outOfQuotaPolicy) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = ((((this.id.hashCode() * 31) + this.state.hashCode()) * 31) + this.workerClassName.hashCode()) * 31;
        String str = this.inputMergerClassName;
        int hashCode = str != null ? str.hashCode() : 0;
        long j = this.initialDelay;
        long j2 = this.intervalDuration;
        long j3 = this.flexDuration;
        long j4 = this.backoffDelayDuration;
        long j5 = this.periodStartTime;
        long j6 = this.minimumRetentionDuration;
        long j7 = this.scheduleRequestedAt;
        return ((((((((((((((((((((((((((((result + hashCode) * 31) + this.input.hashCode()) * 31) + this.output.hashCode()) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31) + this.constraints.hashCode()) * 31) + this.runAttemptCount) * 31) + this.backoffPolicy.hashCode()) * 31) + ((int) (j4 ^ (j4 >>> 32)))) * 31) + ((int) (j5 ^ (j5 >>> 32)))) * 31) + ((int) (j6 ^ (j6 >>> 32)))) * 31) + ((int) (j7 ^ (j7 >>> 32)))) * 31) + (this.expedited ? 1 : 0)) * 31) + this.outOfQuotaPolicy.hashCode();
    }

    @NonNull
    public String toString() {
        return "{WorkSpec: " + this.id + "}";
    }

    public static class IdAndState {
        @ColumnInfo(name = "id")
        public String id;
        @ColumnInfo(name = "state")
        public WorkInfo.State state;

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof IdAndState)) {
                return false;
            }
            IdAndState that = (IdAndState) o;
            if (this.state != that.state) {
                return false;
            }
            return this.id.equals(that.id);
        }

        public int hashCode() {
            return (this.id.hashCode() * 31) + this.state.hashCode();
        }
    }

    public static class WorkInfoPojo {
        @ColumnInfo(name = "id")
        public String id;
        @ColumnInfo(name = "output")
        public Data output;
        @Relation(entity = WorkProgress.class, entityColumn = "work_spec_id", parentColumn = "id", projection = {"progress"})
        public List<Data> progress;
        @ColumnInfo(name = "run_attempt_count")
        public int runAttemptCount;
        @ColumnInfo(name = "state")
        public WorkInfo.State state;
        @Relation(entity = WorkTag.class, entityColumn = "work_spec_id", parentColumn = "id", projection = {"tag"})
        public List<String> tags;

        @NonNull
        public WorkInfo toWorkInfo() {
            Data progress2;
            List<Data> list = this.progress;
            if (list == null || list.isEmpty()) {
                progress2 = Data.EMPTY;
            } else {
                progress2 = this.progress.get(0);
            }
            return new WorkInfo(UUID.fromString(this.id), this.state, this.output, this.tags, progress2, this.runAttemptCount);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof WorkInfoPojo)) {
                return false;
            }
            WorkInfoPojo that = (WorkInfoPojo) o;
            if (this.runAttemptCount != that.runAttemptCount) {
                return false;
            }
            String str = this.id;
            if (str == null ? that.id != null : !str.equals(that.id)) {
                return false;
            }
            if (this.state != that.state) {
                return false;
            }
            Data data = this.output;
            if (data == null ? that.output != null : !data.equals(that.output)) {
                return false;
            }
            List<String> list = this.tags;
            if (list == null ? that.tags != null : !list.equals(that.tags)) {
                return false;
            }
            List<Data> list2 = this.progress;
            if (list2 != null) {
                return list2.equals(that.progress);
            }
            if (that.progress == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            String str = this.id;
            int i = 0;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            WorkInfo.State state2 = this.state;
            int result = (hashCode + (state2 != null ? state2.hashCode() : 0)) * 31;
            Data data = this.output;
            int result2 = (((result + (data != null ? data.hashCode() : 0)) * 31) + this.runAttemptCount) * 31;
            List<String> list = this.tags;
            int result3 = (result2 + (list != null ? list.hashCode() : 0)) * 31;
            List<Data> list2 = this.progress;
            if (list2 != null) {
                i = list2.hashCode();
            }
            return result3 + i;
        }
    }
}
