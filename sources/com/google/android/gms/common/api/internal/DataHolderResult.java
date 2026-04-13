package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class DataHolderResult implements Result, Releasable {
    @NonNull
    @KeepForSdk
    protected final DataHolder mDataHolder;
    @NonNull
    @KeepForSdk
    protected final Status mStatus;

    @KeepForSdk
    protected DataHolderResult(@NonNull DataHolder dataHolder) {
        this(dataHolder, new Status(dataHolder.getStatusCode()));
    }

    @KeepForSdk
    protected DataHolderResult(@NonNull DataHolder dataHolder, @NonNull Status status) {
        this.mStatus = status;
        this.mDataHolder = dataHolder;
    }

    @NonNull
    @KeepForSdk
    public Status getStatus() {
        return this.mStatus;
    }

    @KeepForSdk
    public void release() {
        DataHolder dataHolder = this.mDataHolder;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }
}
