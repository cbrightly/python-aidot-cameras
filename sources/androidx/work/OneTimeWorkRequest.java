package androidx.work;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.work.WorkRequest;
import java.util.ArrayList;
import java.util.List;

public final class OneTimeWorkRequest extends WorkRequest {
    @NonNull
    public static OneTimeWorkRequest from(@NonNull Class<? extends ListenableWorker> workerClass) {
        return (OneTimeWorkRequest) new Builder(workerClass).build();
    }

    @NonNull
    public static List<OneTimeWorkRequest> from(@NonNull List<Class<? extends ListenableWorker>> workerClasses) {
        List<OneTimeWorkRequest> workList = new ArrayList<>(workerClasses.size());
        for (Class<? extends ListenableWorker> workerClass : workerClasses) {
            workList.add((OneTimeWorkRequest) new Builder(workerClass).build());
        }
        return workList;
    }

    OneTimeWorkRequest(Builder builder) {
        super(builder.mId, builder.mWorkSpec, builder.mTags);
    }

    public static final class Builder extends WorkRequest.Builder<Builder, OneTimeWorkRequest> {
        public Builder(@NonNull Class<? extends ListenableWorker> workerClass) {
            super(workerClass);
            this.mWorkSpec.inputMergerClassName = OverwritingInputMerger.class.getName();
        }

        @NonNull
        public Builder setInputMerger(@NonNull Class<? extends InputMerger> inputMerger) {
            this.mWorkSpec.inputMergerClassName = inputMerger.getName();
            return this;
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public OneTimeWorkRequest buildInternal() {
            if (!this.mBackoffCriteriaSet || Build.VERSION.SDK_INT < 23 || !this.mWorkSpec.constraints.requiresDeviceIdle()) {
                return new OneTimeWorkRequest(this);
            }
            throw new IllegalArgumentException("Cannot set backoff criteria on an idle mode job");
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public Builder getThis() {
            return this;
        }
    }
}
