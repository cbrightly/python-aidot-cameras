package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class PendingResultUtil {
    private static final zas zaa = new zao();

    @KeepForSdk
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public interface ResultConverter<R extends Result, T> {
        @KeepForSdk
        @Nullable
        T convert(@NonNull R r);
    }

    @NonNull
    @KeepForSdk
    public static <R extends Result, T extends Response<R>> Task<T> toResponseTask(@NonNull PendingResult<R> pendingResult, @NonNull T response) {
        return toTask(pendingResult, new zaq(response));
    }

    @NonNull
    @KeepForSdk
    public static <R extends Result, T> Task<T> toTask(@NonNull PendingResult<R> pendingResult, @NonNull ResultConverter<R, T> resultConverter) {
        zas zas = zaa;
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        pendingResult.addStatusListener(new zap(pendingResult, taskCompletionSource, resultConverter, zas));
        return taskCompletionSource.getTask();
    }

    @NonNull
    @KeepForSdk
    public static <R extends Result> Task<Void> toVoidTask(@NonNull PendingResult<R> pendingResult) {
        return toTask(pendingResult, new zar());
    }
}
