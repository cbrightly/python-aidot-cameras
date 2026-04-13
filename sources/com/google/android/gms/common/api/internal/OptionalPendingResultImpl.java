package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.TransformedResult;
import java.util.concurrent.TimeUnit;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class OptionalPendingResultImpl<R extends Result> extends OptionalPendingResult<R> {
    private final BasePendingResult zaa;

    public OptionalPendingResultImpl(@NonNull PendingResult pendingResult) {
        this.zaa = (BasePendingResult) pendingResult;
    }

    public final void addStatusListener(@NonNull PendingResult.StatusListener statusListener) {
        this.zaa.addStatusListener(statusListener);
    }

    @NonNull
    public final R await() {
        return this.zaa.await();
    }

    public final void cancel() {
        this.zaa.cancel();
    }

    @NonNull
    public final R get() {
        if (this.zaa.isReady()) {
            return this.zaa.await(0, TimeUnit.MILLISECONDS);
        }
        throw new IllegalStateException("Result is not available. Check that isDone() returns true before calling get().");
    }

    public final boolean isCanceled() {
        return this.zaa.isCanceled();
    }

    public final boolean isDone() {
        return this.zaa.isReady();
    }

    public final void setResultCallback(@NonNull ResultCallback<? super R> resultCallback) {
        this.zaa.setResultCallback(resultCallback);
    }

    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        return this.zaa.then(resultTransform);
    }

    @NonNull
    public final R await(long j, @NonNull TimeUnit timeUnit) {
        return this.zaa.await(j, timeUnit);
    }

    public final void setResultCallback(@NonNull ResultCallback<? super R> resultCallback, long j, @NonNull TimeUnit timeUnit) {
        this.zaa.setResultCallback(resultCallback, j, timeUnit);
    }
}
