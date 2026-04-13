package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;

public final class zzgo extends zzgm<Status> {
    public zzgo(BaseImplementation.ResultHolder<Status> resultHolder) {
        super(resultHolder);
    }

    public final void zzb(zzbt zzbt) {
        zza(new Status(zzbt.statusCode));
    }
}
