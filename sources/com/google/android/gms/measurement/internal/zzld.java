package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzft;
import com.google.android.gms.internal.measurement.zzgd;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzld {
    zzgd zza;
    List zzb;
    List zzc;
    long zzd;
    final /* synthetic */ zzlg zze;

    /* synthetic */ zzld(zzlg zzlg, zzlc zzlc) {
        this.zze = zzlg;
    }

    private static final long zzb(zzft zzft) {
        return ((zzft.zzd() / 1000) / 60) / 60;
    }

    public final boolean zza(long j, zzft zzft) {
        Preconditions.checkNotNull(zzft);
        if (this.zzc == null) {
            this.zzc = new ArrayList();
        }
        if (this.zzb == null) {
            this.zzb = new ArrayList();
        }
        if (!this.zzc.isEmpty() && zzb((zzft) this.zzc.get(0)) != zzb(zzft)) {
            return false;
        }
        long zzbz = this.zzd + ((long) zzft.zzbz());
        this.zze.zzg();
        if (zzbz >= ((long) Math.max(0, ((Integer) zzeh.zzi.zza((Object) null)).intValue()))) {
            return false;
        }
        this.zzd = zzbz;
        this.zzc.add(zzft);
        this.zzb.add(Long.valueOf(j));
        int size = this.zzc.size();
        this.zze.zzg();
        if (size >= Math.max(1, ((Integer) zzeh.zzj.zza((Object) null)).intValue())) {
            return false;
        }
        return true;
    }
}
