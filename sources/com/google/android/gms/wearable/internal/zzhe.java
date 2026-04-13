package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.MessageApi;

public final class zzhe extends zzgm<MessageApi.SendMessageResult> {
    public zzhe(BaseImplementation.ResultHolder<MessageApi.SendMessageResult> resultHolder) {
        super(resultHolder);
    }

    public final void zza(zzga zzga) {
        zza(new zzey(zzgd.zzb(zzga.statusCode), zzga.zzeh));
    }
}
