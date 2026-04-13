package com.google.android.gms.measurement.internal;

import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.internal.measurement.zzbr;
import com.google.android.gms.internal.measurement.zzqx;
import com.google.firebase.messaging.Constants;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzfk implements Runnable {
    final /* synthetic */ zzbr zza;
    final /* synthetic */ ServiceConnection zzb;
    final /* synthetic */ zzfl zzc;

    zzfk(zzfl zzfl, zzbr zzbr, ServiceConnection serviceConnection) {
        this.zzc = zzfl;
        this.zza = zzbr;
        this.zzb = serviceConnection;
    }

    public final void run() {
        Bundle bundle;
        zzfl zzfl = this.zzc;
        zzfm zzfm = zzfl.zza;
        String zza2 = zzfl.zzb;
        zzbr zzbr = this.zza;
        ServiceConnection serviceConnection = this.zzb;
        zzfm.zza.zzaB().zzg();
        Bundle bundle2 = new Bundle();
        bundle2.putString("package_name", zza2);
        try {
            bundle = zzbr.zzd(bundle2);
            if (bundle == null) {
                zzfm.zza.zzaA().zzd().zza("Install Referrer Service returned a null response");
                bundle = null;
            }
        } catch (Exception e) {
            zzfm.zza.zzaA().zzd().zzb("Exception occurred while retrieving the Install Referrer", e.getMessage());
            bundle = null;
        }
        zzfm.zza.zzaB().zzg();
        zzge.zzO();
        if (bundle != null) {
            long j = bundle.getLong("install_begin_timestamp_seconds", 0) * 1000;
            if (j == 0) {
                zzfm.zza.zzaA().zzk().zza("Service response is missing Install Referrer install timestamp");
            } else {
                String string = bundle.getString("install_referrer");
                if (string == null || string.isEmpty()) {
                    zzfm.zza.zzaA().zzd().zza("No referrer defined in Install Referrer response");
                } else {
                    zzfm.zza.zzaA().zzj().zzb("InstallReferrer API result", string);
                    zzlo zzv = zzfm.zza.zzv();
                    Uri parse = Uri.parse("?".concat(string));
                    zzqx.zzc();
                    Bundle zzs = zzv.zzs(parse, zzfm.zza.zzf().zzs((String) null, zzeh.zzaw));
                    if (zzs == null) {
                        zzfm.zza.zzaA().zzd().zza("No campaign params defined in Install Referrer result");
                    } else {
                        String string2 = zzs.getString("medium");
                        if (string2 != null && !"(not set)".equalsIgnoreCase(string2) && !"organic".equalsIgnoreCase(string2)) {
                            long j2 = bundle.getLong("referrer_click_timestamp_seconds", 0) * 1000;
                            if (j2 == 0) {
                                zzfm.zza.zzaA().zzd().zza("Install Referrer is missing click timestamp for ad campaign");
                            } else {
                                zzs.putLong("click_timestamp", j2);
                            }
                        }
                        if (j == zzfm.zza.zzm().zzd.zza()) {
                            zzfm.zza.zzaA().zzj().zza("Logging Install Referrer campaign from module while it may have already been logged.");
                        }
                        if (zzfm.zza.zzJ()) {
                            zzfm.zza.zzm().zzd.zzb(j);
                            zzfm.zza.zzaA().zzj().zzb("Logging Install Referrer campaign from gmscore with ", "referrer API v2");
                            zzs.putString("_cis", "referrer API v2");
                            zzfm.zza.zzq().zzF("auto", Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, zzs, zza2);
                        }
                    }
                }
            }
        }
        ConnectionTracker.getInstance().unbindService(zzfm.zza.zzaw(), serviceConnection);
    }
}
