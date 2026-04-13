package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.DataApi;

public final class zzgv extends zzgm<DataApi.DataItemResult> {
    public zzgv(BaseImplementation.ResultHolder<DataApi.DataItemResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzec zzec) {
        zza(new zzcg(zzgd.zzb(zzec.statusCode), zzec.zzdy));
    }
}
