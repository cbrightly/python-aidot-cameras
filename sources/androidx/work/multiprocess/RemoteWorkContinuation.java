package androidx.work.multiprocess;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.work.OneTimeWorkRequest;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Collections;
import java.util.List;

public abstract class RemoteWorkContinuation {
    /* access modifiers changed from: protected */
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public abstract RemoteWorkContinuation combineInternal(@NonNull List<RemoteWorkContinuation> list);

    @NonNull
    public abstract ListenableFuture<Void> enqueue();

    @NonNull
    public abstract RemoteWorkContinuation then(@NonNull List<OneTimeWorkRequest> list);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected RemoteWorkContinuation() {
    }

    @NonNull
    public final RemoteWorkContinuation then(@NonNull OneTimeWorkRequest work) {
        return then((List<OneTimeWorkRequest>) Collections.singletonList(work));
    }

    @NonNull
    public static RemoteWorkContinuation combine(@NonNull List<RemoteWorkContinuation> continuations) {
        return continuations.get(0).combineInternal(continuations);
    }
}
