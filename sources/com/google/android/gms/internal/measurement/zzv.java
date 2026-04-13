package com.google.android.gms.internal.measurement;

import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.List;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzv extends zzai {
    private final zzz zza;

    public zzv(zzz zzz) {
        super("internal.registerCallback");
        this.zza = zzz;
    }

    public final zzap zza(zzg zzg, List list) {
        int i;
        zzh.zzh(this.zzd, 3, list);
        String zzi = zzg.zzb((zzap) list.get(0)).zzi();
        zzap zzb = zzg.zzb((zzap) list.get(1));
        if (zzb instanceof zzao) {
            zzap zzb2 = zzg.zzb((zzap) list.get(2));
            if (zzb2 instanceof zzam) {
                zzam zzam = (zzam) zzb2;
                if (zzam.zzt(IjkMediaMeta.IJKM_KEY_TYPE)) {
                    String zzi2 = zzam.zzf(IjkMediaMeta.IJKM_KEY_TYPE).zzi();
                    if (zzam.zzt(Progress.PRIORITY)) {
                        i = zzh.zzb(zzam.zzf(Progress.PRIORITY).zzh().doubleValue());
                    } else {
                        i = 1000;
                    }
                    this.zza.zza(zzi, i, (zzao) zzb, zzi2);
                    return zzap.zzf;
                }
                throw new IllegalArgumentException("Undefined rule type");
            }
            throw new IllegalArgumentException("Invalid callback params");
        }
        throw new IllegalArgumentException("Invalid callback type");
    }
}
