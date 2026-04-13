package com.google.android.gms.measurement.internal;

import android.util.Log;
import androidx.exifinterface.media.ExifInterface;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzer implements Runnable {
    final /* synthetic */ int zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ Object zzc;
    final /* synthetic */ Object zzd;
    final /* synthetic */ Object zze;
    final /* synthetic */ zzeu zzf;

    zzer(zzeu zzeu, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzf = zzeu;
        this.zza = i;
        this.zzb = str;
        this.zzc = obj;
        this.zzd = obj2;
        this.zze = obj3;
    }

    public final void run() {
        zzfj zzm = this.zzf.zzt.zzm();
        if (zzm.zzy()) {
            zzeu zzeu = this.zzf;
            if (zzeu.zza == 0) {
                if (zzeu.zzt.zzf().zzy()) {
                    zzeu zzeu2 = this.zzf;
                    zzeu2.zzt.zzay();
                    zzeu2.zza = 'C';
                } else {
                    zzeu zzeu3 = this.zzf;
                    zzeu3.zzt.zzay();
                    zzeu3.zza = 'c';
                }
            }
            zzeu zzeu4 = this.zzf;
            if (zzeu4.zzb < 0) {
                zzeu4.zzt.zzf().zzh();
                zzeu4.zzb = 77000;
            }
            char charAt = "01VDIWEA?".charAt(this.zza);
            zzeu zzeu5 = this.zzf;
            String str = ExifInterface.GPS_MEASUREMENT_2D + charAt + zzeu5.zza + zzeu5.zzb + ":" + zzeu.zzo(true, this.zzb, this.zzc, this.zzd, this.zze);
            if (str.length() > 1024) {
                str = this.zzb.substring(0, 1024);
            }
            zzfh zzfh = zzm.zzb;
            if (zzfh != null) {
                zzfh.zzb(str, 1);
                return;
            }
            return;
        }
        Log.println(6, this.zzf.zzr(), "Persisted config not initialized. Not logging error/warn");
    }
}
