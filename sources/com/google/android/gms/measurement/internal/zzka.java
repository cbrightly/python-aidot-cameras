package com.google.android.gms.measurement.internal;

import android.content.pm.PackageManager;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzka extends zzkt {
    public final zzff zza;
    public final zzff zzb;
    public final zzff zzc;
    public final zzff zzd;
    public final zzff zze;
    private final Map zzg = new HashMap();

    zzka(zzlg zzlg) {
        super(zzlg);
        zzfj zzm = this.zzt.zzm();
        zzm.getClass();
        this.zza = new zzff(zzm, "last_delete_stale", 0);
        zzfj zzm2 = this.zzt.zzm();
        zzm2.getClass();
        this.zzb = new zzff(zzm2, "backoff", 0);
        zzfj zzm3 = this.zzt.zzm();
        zzm3.getClass();
        this.zzc = new zzff(zzm3, "last_upload", 0);
        zzfj zzm4 = this.zzt.zzm();
        zzm4.getClass();
        this.zzd = new zzff(zzm4, "last_upload_attempt", 0);
        zzfj zzm5 = this.zzt.zzm();
        zzm5.getClass();
        this.zze = new zzff(zzm5, "midnight_offset", 0);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @Deprecated
    public final Pair zza(String str) {
        zzjz zzjz;
        zzg();
        long elapsedRealtime = this.zzt.zzax().elapsedRealtime();
        zzjz zzjz2 = (zzjz) this.zzg.get(str);
        if (zzjz2 != null && elapsedRealtime < zzjz2.zzc) {
            return new Pair(zzjz2.zza, Boolean.valueOf(zzjz2.zzb));
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        long zzi = this.zzt.zzf().zzi(str, zzeh.zza) + elapsedRealtime;
        try {
            long zzi2 = this.zzt.zzf().zzi(str, zzeh.zzb);
            AdvertisingIdClient.Info info = null;
            if (zzi2 > 0) {
                try {
                    info = AdvertisingIdClient.getAdvertisingIdInfo(this.zzt.zzaw());
                } catch (PackageManager.NameNotFoundException e) {
                    if (zzjz2 != null) {
                        if (elapsedRealtime < zzjz2.zzc + zzi2) {
                            return new Pair(zzjz2.zza, Boolean.valueOf(zzjz2.zzb));
                        }
                    }
                }
            } else {
                info = AdvertisingIdClient.getAdvertisingIdInfo(this.zzt.zzaw());
            }
            if (info == null) {
                return new Pair("00000000-0000-0000-0000-000000000000", false);
            }
            String id = info.getId();
            if (id != null) {
                zzjz = new zzjz(id, info.isLimitAdTrackingEnabled(), zzi);
            } else {
                zzjz = new zzjz("", info.isLimitAdTrackingEnabled(), zzi);
            }
            this.zzg.put(str, zzjz);
            AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
            return new Pair(zzjz.zza, Boolean.valueOf(zzjz.zzb));
        } catch (Exception e2) {
            this.zzt.zzaA().zzc().zzb("Unable to get advertising id", e2);
            zzjz = new zzjz("", false, zzi);
        }
    }

    /* access modifiers changed from: protected */
    public final boolean zzb() {
        return false;
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    public final Pair zzd(String str, zzai zzai) {
        if (zzai.zzi(zzah.AD_STORAGE)) {
            return zza(str);
        }
        return new Pair("", false);
    }

    /* access modifiers changed from: package-private */
    @WorkerThread
    @Deprecated
    public final String zzf(String str, boolean z) {
        String str2;
        zzg();
        if (z) {
            str2 = (String) zza(str).first;
        } else {
            str2 = "00000000-0000-0000-0000-000000000000";
        }
        MessageDigest zzF = zzlo.zzF();
        if (zzF == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzF.digest(str2.getBytes()))});
    }
}
