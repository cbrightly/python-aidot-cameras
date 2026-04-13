package com.google.android.gms.internal.location;

import androidx.annotation.Nullable;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final class zzaf implements BaseImplementation.ResultHolder {
    private final TaskCompletionSource zza;

    public zzaf(TaskCompletionSource taskCompletionSource) {
        Preconditions.checkNotNull(taskCompletionSource);
        this.zza = taskCompletionSource;
    }

    public final void setFailedResult(@Nullable Status status) {
        if (status != null) {
            this.zza.setException(new ApiException(status));
        }
    }

    public final /* synthetic */ void setResult(Object obj) {
        TaskUtil.setResultOrApiException((Status) obj, null, this.zza);
    }
}
