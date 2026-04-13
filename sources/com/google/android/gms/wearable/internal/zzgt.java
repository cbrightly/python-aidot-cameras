package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.wearable.Channel;

public final class zzgt extends zzgm<Channel.GetOutputStreamResult> {
    private final zzbr zzeu;

    public zzgt(BaseImplementation.ResultHolder<Channel.GetOutputStreamResult> resultHolder, zzbr zzbr) {
        super(resultHolder);
        this.zzeu = (zzbr) Preconditions.checkNotNull(zzbr);
    }

    public final void zza(zzdo zzdo) {
        zzbl zzbl;
        if (zzdo.zzdr != null) {
            zzbl = new zzbl(new ParcelFileDescriptor.AutoCloseOutputStream(zzdo.zzdr));
            this.zzeu.zza(new zzbm(zzbl));
        } else {
            zzbl = null;
        }
        zza(new zzbh(new Status(zzdo.statusCode), zzbl));
    }
}
