package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.DataApi;

public final class zzgx extends zzgm<DataApi.GetFdForAssetResult> {
    public zzgx(BaseImplementation.ResultHolder<DataApi.GetFdForAssetResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzee zzee) {
        zza(new zzci(zzgd.zzb(zzee.statusCode), zzee.zzdz));
    }
}
