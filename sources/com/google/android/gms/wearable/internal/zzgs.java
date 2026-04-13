package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.Channel;

public final class zzgs extends zzgm<Channel.GetInputStreamResult> {
    private final zzbr zzeu;

    public zzgs(BaseImplementation.ResultHolder<Channel.GetInputStreamResult> resultHolder, zzbr zzbr) {
        super(resultHolder);
        this.zzeu = (zzbr) Preconditions.checkNotNull(zzbr);
    }

    public final void zza(zzdm zzdm) {
        zzbj zzbj;
        if (zzdm.zzdr != null) {
            zzbj = new zzbj(new ParcelFileDescriptor.AutoCloseInputStream(zzdm.zzdr));
            this.zzeu.zza(new zzbk(zzbj));
        } else {
            zzbj = null;
        }
        zza(new zzbg(new Status(zzdm.statusCode), zzbj));
    }
}
