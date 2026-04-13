package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.DataApi;

public final class zzgp extends zzgm<DataApi.DeleteDataItemsResult> {
    public zzgp(BaseImplementation.ResultHolder<DataApi.DeleteDataItemsResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzdg zzdg) {
        zza(new zzch(zzgd.zzb(zzdg.statusCode), zzdg.zzdh));
    }
}
