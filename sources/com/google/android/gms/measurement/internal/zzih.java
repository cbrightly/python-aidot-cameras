package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.internal.measurement.zzqx;
import com.google.firebase.messaging.Constants;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public final class zzih implements Runnable {
    final /* synthetic */ boolean zza;
    final /* synthetic */ Uri zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ String zzd;
    final /* synthetic */ zzii zze;

    zzih(zzii zzii, boolean z, Uri uri, String str, String str2) {
        this.zze = zzii;
        this.zza = z;
        this.zzb = uri;
        this.zzc = str;
        this.zzd = str2;
    }

    public final void run() {
        Bundle bundle;
        zzii zzii = this.zze;
        boolean z = this.zza;
        Uri uri = this.zzb;
        String str = this.zzc;
        String str2 = this.zzd;
        zzii.zza.zzg();
        try {
            zzlo zzv = zzii.zza.zzt.zzv();
            zzqx.zzc();
            zzag zzf = zzii.zza.zzt.zzf();
            zzeg zzeg = zzeh.zzav;
            boolean zzs = zzf.zzs((String) null, zzeg);
            if (TextUtils.isEmpty(str2)) {
                bundle = null;
            } else {
                if (!str2.contains("gclid") && !str2.contains("utm_campaign") && !str2.contains("utm_source") && !str2.contains("utm_medium") && !str2.contains("utm_id") && !str2.contains("dclid") && !str2.contains("srsltid")) {
                    if (zzs) {
                        if (str2.contains("sfmc_id")) {
                            zzs = true;
                        }
                    }
                    zzv.zzt.zzaA().zzc().zza("Activity created with data 'referrer' without required params");
                    bundle = null;
                }
                bundle = zzv.zzs(Uri.parse("https://google.com/search?".concat(String.valueOf(str2))), zzs);
                if (bundle != null) {
                    bundle.putString("_cis", "referrer");
                }
            }
            if (z) {
                zzlo zzv2 = zzii.zza.zzt.zzv();
                zzqx.zzc();
                Bundle zzs2 = zzv2.zzs(uri, zzii.zza.zzt.zzf().zzs((String) null, zzeg));
                if (zzs2 != null) {
                    zzs2.putString("_cis", "intent");
                    if (!zzs2.containsKey("gclid") && bundle != null && bundle.containsKey("gclid")) {
                        zzs2.putString("_cer", String.format("gclid=%s", new Object[]{bundle.getString("gclid")}));
                    }
                    zzii.zza.zzG(str, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, zzs2);
                    zzii.zza.zzb.zza(str, zzs2);
                }
            }
            if (!TextUtils.isEmpty(str2)) {
                zzii.zza.zzt.zzaA().zzc().zzb("Activity created with referrer", str2);
                if (zzii.zza.zzt.zzf().zzs((String) null, zzeh.zzaa)) {
                    if (bundle != null) {
                        zzii.zza.zzG(str, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundle);
                        zzii.zza.zzb.zza(str, bundle);
                    } else {
                        zzii.zza.zzt.zzaA().zzc().zzb("Referrer does not contain valid parameters", str2);
                    }
                    zzii.zza.zzW("auto", "_ldl", (Object) null, true);
                } else if (!str2.contains("gclid") || (!str2.contains("utm_campaign") && !str2.contains("utm_source") && !str2.contains("utm_medium") && !str2.contains("utm_term") && !str2.contains("utm_content"))) {
                    zzii.zza.zzt.zzaA().zzc().zza("Activity created with data 'referrer' without required params");
                } else if (!TextUtils.isEmpty(str2)) {
                    zzii.zza.zzW("auto", "_ldl", str2, true);
                }
            }
        } catch (RuntimeException e) {
            zzii.zza.zzt.zzaA().zzd().zzb("Throwable caught in handleReferrerForOnActivityCreated", e);
        }
    }
}
