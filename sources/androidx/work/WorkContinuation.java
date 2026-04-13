package androidx.work;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.lifecycle.LiveData;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

public abstract class WorkContinuation {
    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public abstract WorkContinuation combineInternal(@NonNull List<WorkContinuation> list);

    @NonNull
    public abstract Operation enqueue();

    @NonNull
    public abstract ListenableFuture<List<WorkInfo>> getWorkInfos();

    @NonNull
    public abstract LiveData<List<WorkInfo>> getWorkInfosLiveData();

    @NonNull
    public abstract WorkContinuation then(@NonNull List<OneTimeWorkRequest> list);

    @NonNull
    public final WorkContinuation then(@NonNull OneTimeWorkRequest work) {
        return then((List<OneTimeWorkRequest>) Collections.singletonList(work));
    }

    @NonNull
    public static WorkContinuation combine(@NonNull List<WorkContinuation> continuations) {
        return continuations.get(0).combineInternal(continuations);
    }
}
