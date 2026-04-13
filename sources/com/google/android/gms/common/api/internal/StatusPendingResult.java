package com.google.android.gms.common.api.internal;

import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public class StatusPendingResult extends BasePendingResult<Status> {
    @Deprecated
    public StatusPendingResult(@NonNull Looper looper) {
        super(looper);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final /* bridge */ /* synthetic */ Result createFailedResult(@NonNull Status status) {
        return status;
    }

    @KeepForSdk
    public StatusPendingResult(@NonNull GoogleApiClient apiClient) {
        super(apiClient);
    }
}
