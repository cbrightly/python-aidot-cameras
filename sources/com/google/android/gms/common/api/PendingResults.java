package com.google.android.gms.common.api;

import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.OptionalPendingResultImpl;
import com.google.android.gms.common.api.internal.StatusPendingResult;
import com.google.android.gms.common.internal.Preconditions;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public final class PendingResults {
    private PendingResults() {
    }

    @NonNull
    public static PendingResult<Status> canceledPendingResult() {
        StatusPendingResult statusPendingResult = new StatusPendingResult(Looper.getMainLooper());
        statusPendingResult.cancel();
        return statusPendingResult;
    }

    @NonNull
    @KeepForSdk
    public static <R extends Result> PendingResult<R> immediateFailedResult(@NonNull R result, @NonNull GoogleApiClient apiClient) {
        Preconditions.checkNotNull(result, "Result must not be null");
        Preconditions.checkArgument(!result.getStatus().isSuccess(), "Status code must not be SUCCESS");
        zag zag = new zag(apiClient, result);
        zag.setResult(result);
        return zag;
    }

    @NonNull
    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(@NonNull R result) {
        Preconditions.checkNotNull(result, "Result must not be null");
        zah zah = new zah((GoogleApiClient) null);
        zah.setResult(result);
        return new OptionalPendingResultImpl(zah);
    }

    @NonNull
    public static <R extends Result> PendingResult<R> canceledPendingResult(@NonNull R result) {
        boolean z;
        Preconditions.checkNotNull(result, "Result must not be null");
        if (result.getStatus().getStatusCode() == 16) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Status code must be CommonStatusCodes.CANCELED");
        zaf zaf = new zaf(result);
        zaf.cancel();
        return zaf;
    }

    @NonNull
    @KeepForSdk
    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(@NonNull R result, @NonNull GoogleApiClient apiClient) {
        Preconditions.checkNotNull(result, "Result must not be null");
        zah zah = new zah(apiClient);
        zah.setResult(result);
        return new OptionalPendingResultImpl(zah);
    }

    @NonNull
    public static PendingResult<Status> immediatePendingResult(@NonNull Status result) {
        Preconditions.checkNotNull(result, "Result must not be null");
        StatusPendingResult statusPendingResult = new StatusPendingResult(Looper.getMainLooper());
        statusPendingResult.setResult(result);
        return statusPendingResult;
    }

    @NonNull
    @KeepForSdk
    public static PendingResult<Status> immediatePendingResult(@NonNull Status result, @NonNull GoogleApiClient apiClient) {
        Preconditions.checkNotNull(result, "Result must not be null");
        StatusPendingResult statusPendingResult = new StatusPendingResult(apiClient);
        statusPendingResult.setResult(result);
        return statusPendingResult;
    }
}
