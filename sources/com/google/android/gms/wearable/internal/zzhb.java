package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.DataApi;
import java.util.List;
import java.util.concurrent.FutureTask;

public final class zzhb extends zzgm<DataApi.DataItemResult> {
    private final List<FutureTask<Boolean>> zzev;

    zzhb(BaseImplementation.ResultHolder<DataApi.DataItemResult> resultHolder, List<FutureTask<Boolean>> list) {
        super(resultHolder);
        this.zzev = list;
    }

    public final void zza(zzfu zzfu) {
        zza(new zzcg(zzgd.zzb(zzfu.statusCode), zzfu.zzdy));
        if (zzfu.statusCode != 0) {
            for (FutureTask<Boolean> cancel : this.zzev) {
                cancel.cancel(true);
            }
        }
    }
}
