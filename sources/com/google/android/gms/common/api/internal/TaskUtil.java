package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.ApiExceptionUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class TaskUtil {
    @KeepForSdk
    public static void setResultOrApiException(@NonNull Status status, @NonNull TaskCompletionSource<Void> completionSource) {
        setResultOrApiException(status, (Object) null, completionSource);
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public static Task<Void> toVoidTaskThatFailsOnFalse(@NonNull Task<Boolean> task) {
        return task.continueWith(new zacx());
    }

    @ResultIgnorabilityUnspecified
    @KeepForSdk
    public static <ResultT> boolean trySetResultOrApiException(@NonNull Status status, @Nullable ResultT result, @NonNull TaskCompletionSource<ResultT> completionSource) {
        if (status.isSuccess()) {
            return completionSource.trySetResult(result);
        }
        return completionSource.trySetException(ApiExceptionUtil.fromStatus(status));
    }

    @KeepForSdk
    public static <ResultT> void setResultOrApiException(@NonNull Status status, @Nullable ResultT result, @NonNull TaskCompletionSource<ResultT> completionSource) {
        if (status.isSuccess()) {
            completionSource.setResult(result);
        } else {
            completionSource.setException(ApiExceptionUtil.fromStatus(status));
        }
    }
}
