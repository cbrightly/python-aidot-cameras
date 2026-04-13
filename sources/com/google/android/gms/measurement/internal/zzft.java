package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzr;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzft implements zzr {
    final /* synthetic */ zzfv zza;

    zzft(zzfv zzfv) {
        this.zza = zzfv;
    }

    public final void zza(int i, String str, List list, boolean z, boolean z2) {
        zzes zzes;
        switch (i - 1) {
            case 0:
                zzes = this.zza.zzt.zzaA().zzc();
                break;
            case 1:
                if (!z) {
                    if (z2) {
                        zzes = this.zza.zzt.zzaA().zzd();
                        break;
                    } else {
                        zzes = this.zza.zzt.zzaA().zze();
                        break;
                    }
                } else {
                    zzes = this.zza.zzt.zzaA().zzh();
                    break;
                }
            case 3:
                zzes = this.zza.zzt.zzaA().zzj();
                break;
            case 4:
                if (!z) {
                    if (z2) {
                        zzes = this.zza.zzt.zzaA().zzk();
                        break;
                    } else {
                        zzes = this.zza.zzt.zzaA().zzl();
                        break;
                    }
                } else {
                    zzes = this.zza.zzt.zzaA().zzm();
                    break;
                }
            default:
                zzes = this.zza.zzt.zzaA().zzi();
                break;
        }
        switch (list.size()) {
            case 1:
                zzes.zzb(str, list.get(0));
                return;
            case 2:
                zzes.zzc(str, list.get(0), list.get(1));
                return;
            case 3:
                zzes.zzd(str, list.get(0), list.get(1), list.get(2));
                return;
            default:
                zzes.zza(str);
                return;
        }
    }
}
